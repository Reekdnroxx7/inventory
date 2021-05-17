/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.Org', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'org',

	init : function() {
		this.launcher = {
			text : '机构管理',
			iconCls : 'icon-grid'
		};
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('org');
		if (!win) {
			Ext.define('Org', {
						extend : 'Ext.data.TreeModel',
						fields : ['id', 'type', 'address', 'zipCode', 'master',
								'phone', 'fax', 'email', 'level', 'code', {
									name : 'leaf',
									convert : function(value, record) {
										return record.data.type == '2';
									}
								}, {
									name : 'parentIds'
								}, {
									name : 'text',
									mapping : 'name'
								}, {
									name : 'name'
								}, {
									name : 'type',
									mapping : 'type'
								}]
					});
			var treeStore = new Ext.data.TreeStore({
						nodeParam : 'orgId',
						proxy : {
							type : 'ajax',
							url : 'orgController.ajax?method=list'
						},
						model : 'Org',
						root : {
							name : '北京市总公司',
							expanded : true,// 根节点是否展开
							id : '1',
							leaf : 'false',
							type : '1'
						},
						sorters : [{
									property : 'code'
								}]
					});
			var org_panel = Ext.create("Ext.tree.Panel", {
				// 如果超出范围带自动滚动条
				autoScroll : true,
				store : treeStore,
				rootVisible : true,// 显示根目录，默认为false
				displayField : 'name',
				border : false,
				animate : true,
				lines : true,
				listeners : {
					"itemclick" : function(t, record, item, index, event, eOpts) // 点击处理事件
					{
						showEdit(record);
					},
					"itemcontextmenu" : function(t, record, item, index, event,
							eOpts) {
						event.preventDefault();// 关闭默认的菜单，以避免弹出两个菜单
						record.expand(0)
						this.setRecord(record);
						this.contextMenu.showAt(event.getXY());
					}
				},
				contextMenu : Ext.create('Ext.menu.Menu', {
							xtype : 'menu',
							width : 100,
							margin : '0 0 10 0',
							items : [{
										text : '添加机构',

										iconCls : 'Add',
										handler : function() {
											var record = org_panel.getRecord();
											showAdd(record);

										}
									}, '-', {
										text : '删除机构',
										iconCls : 'Delete',
										handler : function() {
											var record = org_panel.getRecord();
											delRecord(record);
										}
									}]
						}),
				setRecord : function(record) {
					this.record = record;
				},
				getRecord : function() {
					return this.record;
				},
				setParent : function(record) {
					this.parent = record;
				},
				getParent : function() {
					return this.parent;
				}
			});
			function showEdit(record) {
				centerPanel.getForm().loadRecord(record);
				var parentRec = treeStore.getById(record.data.parentId);
				org_panel.setParent(parent);
				if (parentRec) {
					parentName.setValue(parentRec.data.name);
				}
				Ext.getCmp("save").setText('修改');
				_saveType.setValue('update');

			}
			function showAdd(parent) {
				centerPanel.form.reset();
				org_panel.setParent(parent);
				parentName.setValue(parent.data.name);
				parentId.setValue(parent.data.id);
				level.setValue(parent.data.level + 1);
				Ext.getCmp("save").setText('添加');
			}

			function delRecord(rec) {
				Ext.Ajax.request({
							url : 'orgController.ajax?method=delete',
							params : rec.data,
							success : function(response) {
								var text = response.responseText;
								var result = Ext.JSON.decode(text);
								if (result.success) {
									var parent = treeStore
											.getNodeById(rec.data.parentId);
									parent.removeChild(rec);
									Ext.Msg.alert("信息", "删除成功");
									centerPanel.form.reset();
								} else {
									Ext.Msg.alert("错误", result.message);
								}
							}
						});
			}
			var westPanel = new Ext.create('Ext.panel.Panel', {
						id : westPanel,
						title : '机构',
						region : 'west',
						width : 200,
						minSize : 175,
						maxSize : 400,
						xtype : 'panel',
						margins : '0 0 5 5',
						split : true,
						autoScroll : true,
						animCollapse : false,
						animate : false,
						collapseMode : 'mini',
						items : org_panel,
						collapsible : true
					});
			var parentName = new Ext.form.field.Text({
						fieldLabel : '上级机构:',
						width : 450,
						id : 'parentName',
						readOnly : true,
						fieldStyle : 'background:#E6E6E6',
						name : 'parentName'
					});
			var orgId = new Ext.form.field.Text({
						fieldLabel : '机构ID:',
						width : 450,
						id : 'id',
						readOnly : true,
						// hidden : true,
						fieldStyle : 'background:#E6E6E6',
						name : 'id'
					});
			var parentId = new Ext.form.field.Text({
						fieldLabel : '上级机构:',
						width : 450,
						id : 'parentId',
						readOnly : true,
						hidden : true,
						fieldStyle : 'background:#E6E6E6',
						name : 'parentId'
					});

			var code = new Ext.form.field.Text({
						fieldLabel : '区域编码:',
						width : 450,
						id : 'code',
						name : 'code'
					});
			var nameField = new Ext.form.field.Text({
						fieldLabel : '机构名称:',
						width : 450,
						id : 'name',
						allowBlank : false,
						name : 'name'
					});
			var level = new Ext.form.field.Text({
						fieldLabel : '机构等级:',
						width : 450,
						id : 'level',
						hidden : true,
						name : 'level'
					});
			var type = new Ext.form.RadioGroup({
						fieldLabel : "机构类型",
						horizontal : true,
						vertical : true,
						anchor : '83%',
						items : [{
									boxLabel : '公司',
									inputValue : '1',
									name : 'type',
									checked : 'true'
								}, {
									boxLabel : '部门',
									name : 'type',
									inputValue : '2'
								}, {
									boxLabel : '小组',
									inputValue : '3',
									name : 'type'
								}]
					});

			var address = new Ext.form.field.Text({
						fieldLabel : '联系地址:',
						width : 450,
						id : 'address',
						name : 'address'
					});
			var zipCode = new Ext.form.field.Text({
						fieldLabel : '邮政编码:',
						width : 450,
						id : 'zipCode',
						name : 'zipCode'
					});
			var master = new Ext.form.field.Text({
						fieldLabel : '负责人:',
						width : 450,
						id : 'master',
						name : 'master'
					});
			var phone = new Ext.form.field.Text({
						fieldLabel : '电话:',
						width : 450,
						id : 'phone',
						name : 'phone'
					});
			var fax = new Ext.form.field.Text({
						fieldLabel : '传真:',
						width : 450,
						id : 'fax',
						name : 'fax'
					});
			var email = new Ext.form.field.Text({
						fieldLabel : '邮箱:',
						width : 450,
						id : 'email',
						name : 'email'
					});
			var _saveType = new Ext.form.Hidden({
						name : '_saveType',
						id : 'saveType',
						value : 'add'
					});
			var centerPanel = Ext.create('Ext.form.Panel', {
						title : '机构维护',
						bodyPadding : 10,
						defaultAlign : 'center',
						buttonAlign : 'center',
						region : 'center',
						items : [parentName, orgId, parentId, level, code,
								nameField, type, address, zipCode, master,
								phone, fax, email, _saveType],
						buttons : [{
									id : 'save',
									text : '添加',
									handler : save
								}]
					});
			function save() {
				centerPanel.getForm().submit({
							clientValidation : true,
							url : 'orgController.ajax?method=save',
							success : function(form, action, record) {
								Ext.Msg.alert('提示', '操作成功', function() {
										});
								treeStore.reload();
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
						id : 'org',
						title : '机构管理',
						width : 740,
						height : 480,
						iconCls : 'icon-grid',
						animCollapse : false,
						constrainHeader : true,
						layout : 'border',
						items : [westPanel, centerPanel]

					});
		}
		return win;
	}
});
