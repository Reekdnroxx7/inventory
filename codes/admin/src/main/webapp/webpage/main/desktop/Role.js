/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.Role', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'role',

	init : function() {
		this.launcher = {
			text : '角色管理',
			iconCls : 'role'
		};
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('role');
		if (!win) {
			var store = Ext.create('Ext.data.Store', {

						fields : ['name'],
						proxy : {
							type : 'ajax',
							url : "roleController.ajax?method=listRole",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
						}
					});
			store.load();
			var columns = [{
						xtype : 'rownumberer'
					}, {
						hidden : true,
						text : '角色ID',
						dataIndex : 'id'
					}, {
						text : '角色名称',
						dataIndex : 'name'
					}, {
						xtype : 'actioncolumn',
						text : '操作',
						align : 'center',
						items : [{
									tooltip : '删除',
									handler : del,
									iconCls : 'delete'
								}, '-', {
									tooltip : '编辑',
									handler : function(grid, rowIndex, colIndex) {
										var rec = grid.getStore()
												.getAt(rowIndex);
										detailWin.showUpdate(rec);
									},
									iconCls : 'Applicationedit'
								}, '-', {
									iconCls : 'CogAdd',
									tooltip : '菜单设置',
									handler : showMenu
								}]
					}];

			var grid_panel = Ext.create('Ext.grid.Panel', {
//						title : '角色列表',
						region : 'west',
						width : 400,
						split : true,
						store : store,
						columns : columns,
						dockedItems : [{
									xtype : 'toolbar',
									dock : 'top',
									items : ['角色名称:', {
												xtype : 'textfield',
												name : 'name|like'
											}]
								}, {
									xtype : 'toolbar',
									dock : 'top',
									items : [{
												text : '查询',
												handler : query,
												iconCls : 'Magnifier'
											}, {
												text : '角色录入',
												handler : function() {
													detailWin.showAdd()
												},
												iconCls : 'add'
											}]
								}, {
									xtype : 'pagingtoolbar',
									store : store,
									dock : 'bottom',
									displayInfo : true
								}]
					});
			function showMenu(grid, rowIndex, colIndex) {
				var rec = grid.getStore().getAt(rowIndex);
				center_panel.loadRecord(rec);
			}
			function del(grid, rowIndex) {
				Ext.Msg.confirm("提示", "你确定要删除该记录么?", function(confirm) {
					if ("yes" == confirm) {
						var rec = grid_panel.getStore().getAt(rowIndex);
						Ext.Ajax.request({
									url : 'roleController.ajax?method=delete',
									params : rec.data,
									success : function(response) {
										var text = response.responseText;
										var result = Ext.JSON.decode(text);
										if (result.success) {
											Ext.Msg.alert("信息", "删除成功");
											store.reload();
											treeStore.reload();
										} else {
											Ext.Msg.alert("错误", result.message);
										}
									}
								});
					}
				});

			}

			function query() {
				var params = getParams();
				store.load({
							params : params,
							callback : function(records, operation, success) {
								if (success == false) {
									Ext.Msg
											.alert(
													"错误",
													User.store.proxy.reader.jsonData.message);
								}
							}
						});
			}
			function getParams() {
				var docks = grid_panel.getDockedItems('toolbar[dock="top"]');
				var params = {};
				for (var i = 0; i < docks.length; i++) {
					var bar = docks[i];
					bar.items.each(function(item, index) {
								if (item.getValue) {
									var name = item['name'];
									var value = item.getValue();
									params[name] = value;
								}
							});
				}
				return params;
			}
			Ext.define('Menu', {
						extend : 'Ext.data.TreeModel',
						fields : ["menuId", 'type', {
									name : 'location',
									mapping : 'href'
								}, {
									name : 'leaf',
									convert : function(value, record) {
										return record.data.type == '0';
									}
								}, {
									name : 'name',
									mapping : 'name'
								}, {
									name : 'iconCls',
									mapping : 'icon'
								}, {
									name : 'href',
									mapping : 'none'
								}, {
									name : 'type',
									mapping : 'type'
								}, {
									name : 'url',
									mapping : 'href'
								}, {
									name : 'sort',
									mapping : 'sort'
								}, {
									name : 'parentIds'
								}, {
									name : 'level'
								}]
					});
			var treeStore = new Ext.data.TreeStore({
						nodeParam : 'menuId',
						proxy : {
							type : 'ajax',
							url : 'roleController.ajax?method=listMenu'
						},
						model : 'Menu',
						root : {
							checked : false,
							name : '顶级菜单',
							expanded : true,// 根节点是否展开
							iconCls : 'folder',
							icon : 'folder',
							menuId : '1',
							leaf : 'false',
							type : '1'
						},
						sorters : [{
									property : 'sort'
								}]
					});

			var center_panel = Ext.create("Ext.tree.Panel", {
				region : 'center',
				title : '菜单列表',
				// 如果超出范围带自动滚动条
				autoScroll : true,
				store : treeStore,
				// 默认根目录不显示
				rootVisible : false,
				displayField : 'name',
				border : false,
				animate : true,
				lines : true,
				buttonAlign : 'center',
				buttons : [{
							text : '保存',
							handler : function() {
								var record = center_panel.getRecord();
								submit(record)
							}
						}],
				loadRecord : function(rec) {
					this.record = rec;
					treeStore.load({
								params : rec.data
							});
				},
				getRecord : function() {
					return this.record;
				},
				viewConfig : {
					onCheckboxChange : function(e, t) {
						var item = e.getTarget(this.getItemSelector(), this
										.getTargetEl()), record;
						if (item) {
							record = this.getRecord(item);
							var check = !record.get('checked');
							record.set('checked', check);
							if (check) {
								record.bubble(function(parentNode) {
											parentNode.set('checked', true);
										});
								record.cascadeBy(function(node) {
											node.set('checked', true);
										});
								record.expand();
								record.expandChildren();
							} else {
								record.collapse();
								record.collapseChildren();
								record.cascadeBy(function(node) {
											node.set('checked', false);
										});
								record.bubble(function(parentNode) {
									var childHasChecked = false;
									var childNodes = parentNode.childNodes;
									if (childNodes || childNodes.length > 0) {
										for (var i = 0; i < childNodes.length; i++) {
											if (childNodes[i].data.checked) {
												childHasChecked = true;
												break;
											}
										}
									}
									if (!childHasChecked) {
										parentNode.set('checked', false);
									}
								});

							}
						}
					}
				}
			});

			function submit(rec) {
				var nodes = center_panel.getChecked();
				var menuIds = new Array();
				Ext.each(nodes, function(node) {
							var id = node.data.menuId;
							menuIds.push(id);
						});
				Ext.Ajax.request({
							url : 'roleController.ajax?method=saveRoleMenu',
							params : {
								roleId : rec.data.id,
								menuIds : menuIds
							},
							success : function() {
								Ext.Msg.alert("信息", '提交成功');
							}
						});
			}
			var id = new Ext.form.field.Text({
//						id : 'id',
						name : 'id',
						hidden : true
					});
			var nameDetailField = new Ext.form.field.Text({
						fieldLabel : '角色名称',
//						id : 'name',
						allowBlank : false,
						name : 'name'
					});
			var _saveType = new Ext.form.Hidden({
						name : '_saveType',
//						id : 'saveType',
						value : 'add'
					});
			var detailPanel = Ext.create("Ext.form.Panel", {
						bodyPadding : 10,
						items : [id, nameDetailField, _saveType]
					});
			var detailWin = Ext.create('Ext.window.Window', {
						title : '角色录入',
						layout : 'fit',
						items : detailPanel,
						closeAction : 'hide',
						showAdd : function() {
							var form = this.child('form');
							form.getForm().reset();
							form.getForm().findField("_saveType")
									.setValue("add");
							this.show();
						},
						showUpdate : function(record) {
							var form = this.child('form');
							form.getForm().loadRecord(record);
							_saveType.setValue('update');
							this.show();
						},
						buttons : [{
									text : '提交',
									handler : save
								}, {
									text : '取消',
									handler : function() {
										detailWin.hide();
									}
								}]
					})
			function save() {
				detailPanel.getForm().submit({
							clientValidation : true,
							url : 'roleController.ajax?method=save',
							success : function(form, action) {
								Ext.Msg.alert("信息", "操作成功");
								store.reload();
								detailWin.hide();
							},
							failure : function(form, action) {
								switch (action.failureType) {
									case Ext.form.action.Action.CLIENT_INVALID :
										Ext.Msg.alert('失败', '无效的值');
										break;
									case Ext.form.action.Action.CONNECT_FAILURE :
										Ext.Msg.alert('失败', '网络连接失败');
										break;
									case Ext.form.action.Action.SERVER_INVALID :
										Ext.Msg.alert('失败',
												action.result.message);
								}
							}
						});
			}

			win = desktop.createWindow({
						id : 'role',
						title : '角色管理',
						width : 740,
						height : 480,
						iconCls : 'role',
						animCollapse : false,
						constrainHeader : true,
						layout : 'border',
						items : [grid_panel, center_panel]
					});
		}
		return win;
	}
});
