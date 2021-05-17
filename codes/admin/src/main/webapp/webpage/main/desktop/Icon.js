/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.Icon', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'icon',

	init : function() {
		this.launcher = {
			text : '图标管理',
			iconCls : 'picture'
		};
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('icon');
		if (!win) {

			Ext.namespace("Icon");
			Icon.query = function() {
				var params = Icon.getParams('queryBar');
				Icon.store.load({
							params : params,
							callback : function(r, options, success) {
								if (success == false) {
									Ext.Msg
											.alert(
													"错误",
													store.proxy.reader.jsonData.message);
								}
							}
						});
			}

			Icon.del = function(grid, rowIndex, colIndex) {
				Ext.Msg.confirm("提示", "你确定要删除该记录么?", function(confirm) {
					if ("yes" == confirm) {
						var rec = grid.getStore().getAt(rowIndex);
						Ext.Ajax.request({
									url : 'iconController.ajax?method=delete',
									params : rec.data,
									success : function(response) {
										var text = response.responseText;
										var result = Ext.JSON.decode(text);
										if (result.success) {
											Ext.Msg.alert("信息", "删除成功");
											Icon.store.reload();
										} else {
											Ext.Msg.alert("错误", result.message);
										}
									}
								});
					}
				});
			}

			Icon.refresh = function() {
				Ext.Ajax.request({
							url : 'iconController.ajax?method=refresh',
							success : function(response) {
								var text = response.responseText;
								var result = Ext.JSON.decode(text);
								if (result.success) {
									Ext.Msg.alert("信息", "刷新成功");
								} else {
									Ext.Msg.alert("错误", result.message);
								}
							}
						});
			}

			Icon.getParams = function(id) {
				var bar = Ext.getCmp(id);
				var params = {};
				bar.items.each(function(item, index) {
							if (Ext.getCmp(item['id'])) {
								var name = item['id'];
								if (Ext.getCmp(name).getValue) {
									var value = Ext.getCmp(item['id'])
											.getValue();
									params[name] = value;
								}
							}
						});
				return params;
			}
			Icon.store = Ext.create('Ext.data.Store', {
						fields : ['css', 'name', 'path'],
						proxy : {
							type : 'ajax',
							url : "iconController.ajax?method=list",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
						}
					});
			Icon.store.load();
			Icon.columns = [{
						xtype : 'rownumberer'
					}, {
						text : '样式',
						dataIndex : 'css'
					}, {
						text : '名称',
						dataIndex : 'name'
					}, {
						text : '图标',
						renderer : function(v, meta, record) {
							return "<span class='" + record.data.css
									+ "'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
						}
					}, {
						text : '路径',
						dataIndex : 'path',
						hidden : true
					}, {
						xtype : 'actioncolumn',
						text : '操作',
						align : 'center',
						items : [{
									iconCls : 'Applicationedit',
									tooltip : '修改',
									handler : function(grid, rowIndex, colIndex) {
										var rec = grid.getStore()
												.getAt(rowIndex);
										Icon.detailWin.showUpdate(rec);
									}
								}, '-', {
									iconCls : 'delete',
									tooltip : '删除',
									handler : Icon.del
								}]
					}];

			Icon.cssQueryField = new Ext.form.field.Text({
						name : 'css|like'
					});

			Icon.gridPanel = Ext.create('Ext.grid.Panel', {
//						title : '图标管理',
						store : Icon.store,
						columns : Icon.columns,
						dockedItems : [{
									xtype : 'toolbar',
									dock : 'top',
									items : ['样式：', Icon.cssQueryField]
								}, {
									xtype : 'toolbar',
									dock : 'top',
									items : [{
												text : '查询',
												handler : Icon.query,
												iconCls : 'Magnifier'
											}, {
												text : '添加',
												handler : function() {
													Icon.detailWin.showAdd()
												},
												iconCls : 'add'
											}, {
												text : '刷新',
												handler : function() {
													Icon.refresh()
												},
												iconCls : 'Pagerefresh'
											}]
								}, {
									xtype : 'pagingtoolbar',
									store : Icon.store,
									dock : 'bottom',
									displayInfo : true
								}]
					});

			Icon.save = function() {
				Icon.detailPanel.getForm().submit({
							clientValidation : true,
							url : 'iconController.ajax?method=save',
							success : function(form, action) {
								Icon.detailWin.hide();
								Icon.store.reload();
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
			Icon.cssDetailField = new Ext.form.field.Text({
						fieldLabel : '图标样式',
						allowBlank : false,
						name : 'css'
					});

			Icon.nameDetailField = new Ext.form.field.Text({
						fieldLabel : '图标名称',
						allowBlank : false,
						name : 'name'
					});
			Icon.pathDetailField = new Ext.form.field.File({
						name : 'filepath',
						allowBlank : false,
						buttonText : '选择图片'
					});

			Icon._saveType = new Ext.form.Hidden({
						name : '_saveType',
						value : 'add'
					});

			Icon.detailPanel = Ext.create('Ext.form.Panel', {
						bodyPadding : 10,
						items : [Icon.cssDetailField, Icon.nameDetailField,
								Icon.pathDetailField, Icon._saveType]
					});

			Icon.detailWin = Ext.create('Ext.window.Window', {
						title : '图标管理',
						layout : 'fit',
						items : Icon.detailPanel,
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
							form.getForm().findField("_saveType")
									.setValue("update");
							this.show();
						},
						buttons : [{
									text : '提交',
									handler : Icon.save
								}]

					})

			win = desktop.createWindow({
						id : 'icon',
						title : '图标管理',
						width : 740,
						height : 480,
						iconCls : 'picture',
						animCollapse : false,
						constrainHeader : true,
						layout : 'fit',
						items : Icon.gridPanel

					});
		}
		return win;
	}
});
