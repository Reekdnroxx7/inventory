/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.User', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'user',

	init : function() {
		this.launcher = {
			text : '用户管理',
			iconCls : 'user'
		};
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid-win');
		if (!win) {
			Ext.namespace("User");

			User.orgBox = new Sys.OrgBox({
						fieldLabel : '机构',
						name : 'orgId',
						width : 300
					});
			User.query = function() {
				var params = User.getParams();
				User.store.load({
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
			User.save = function() {
				User.formPanel.getForm().submit({
							clientValidation : true,
							url : 'userController.ajax?method=saveUser',
							success : function(form, action) {
								User.formWin.hide();
								User.store.reload();
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
			};
			User.del = function(grid, rowIndex, colIndex) {
				Ext.Msg.confirm("提示", "你确定要删除该记录么?", function(confirm) {
					if ("yes" == confirm) {
						var store = grid.getStore();
						var rec = store.getAt(rowIndex);
						Ext.Ajax.request({
									url : 'userController.ajax?method=deleteUser',
									params : rec.data,
									success : function(response) {
										var text = response.responseText;
										var result = Ext.JSON.decode(text);
										if (result.success) {
											Ext.Msg.alert("信息", "删除成功");
											store.reload();
										} else {
											Ext.Msg.alert("错误", result.message);
										}
									}
								});
					}
				});

			};

			User.store = Ext.create('Ext.data.Store', {
						fields : ['loginName', 'name', 'email', 'mobile',
								'orgId'],
						proxy : {
							type : 'ajax',
							url : "userController.ajax?method=listUser",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
						}
					});
			User.store.load();
			User.gridPanel = Ext.create('Ext.grid.Panel', {
//						title : '人员管理',
						store : User.store,
						rowLines : true,
						columnLines : true,
						columns : [{
							xtype : 'actioncolumn',
							text : '操作',
							align : 'center',
							items : [{
										iconCls : 'Pageadd',
										tooltip : '分配角色',
										handler : function(grid, rowIndex,
												colIndex) {
											var rec = grid.getStore()
													.getAt(rowIndex);
											UserRole.gridWin.showRecord(rec);
										}
									}, '-', {
										iconCls : 'delete',
										tooltip : '删除',
										handler : User.del
									}]
						}, {
							text : '登录名',
							dataIndex : 'loginName'
						}, {
							text : '用户名',
							dataIndex : 'name'
						}, {
							text : '所属机构',
							dataIndex : 'orgId',
							renderer : function(v) {
								return User.orgBox.getDictValue(v);
							}
						}, {
							text : '邮箱',
							dataIndex : 'email',
							width : 200
						}, {
							text : '手机',
							dataIndex : 'mobile'
						}],
						dockedItems : [{
									xtype : 'toolbar',
									dock : 'top',
									items : ['登陆名:', {
												xtype : 'textfield',
												name : 'loginName|like'
											}, '用户名:', {
												xtype : 'textfield',
												name : 'name|like'
											}]
								}, {
									xtype : 'toolbar',
									dock : 'top',
									items : [{
												text : '查询',
												handler : User.query,
												iconCls : 'Magnifier'
											}, {
												text : '添加',
												handler : function() {
													User.formWin.showAdd()
												},
												iconCls : 'add'
											}]
								}, {
									xtype : 'pagingtoolbar',
									store : User.store,
									dock : 'bottom',
									displayInfo : true
								}]
					});

			User.formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding : 10,
						defaultType : 'textfield',
						items : [{
									fieldLabel : '登陆名称',
									name : 'loginName',
									allowBlank : false,
									width : 300
								}, {
									fieldLabel : '用户名',
									name : 'name',
									allowBlank : false,
									width : 300
								}, User.orgBox, {
									fieldLabel : '邮箱',
									name : 'email',
									width : 300
								}, {
									fieldLabel : '手机',
									name : 'mobile',
									width : 300
								}, new Ext.form.Hidden({
											name : '_saveType'
										}), new Ext.form.Hidden({
											name : 'id'
										})]
					});

			User.formWin = Ext.create('Ext.window.Window', {
						title : '人员管理',
						layout : 'fit',
						items : User.formPanel,
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
							form.getForm.loadRecord(record);
							form.findField("_saveType").setValue("update");
							this.show();
						},
						buttons : [{
									text : '提交',
									handler : User.save
								}, {
									text : '取消',
									handler : function() {
										User.formWin.hide();
									}
								}]
					});

			User.getParams = function() {
				var docks = User.gridPanel
						.getDockedItems('toolbar[dock="top"]');
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

			Ext.namespace("UserRole");
			UserRole.roleDict = new Sys.DictBox({
						groupCode : 'sys_user_role.role_type'
					});
			UserRole.query = function() {
				var record = UserRole.gridWin.getRecord();
				if (record) {
					var params = {};
					UserRole.store.load({
								params : record.data
							});
				}
			};
			UserRole.save = function() {
				var records = UserRole.store.getModifiedRecords();
				if (records.length == 0) {
					UserRole.gridWin.hide();
					return;
				}
				var roles = [];
				for (var i = 0; i < records.length; i++) {
					roles[i] = Ext.JSON.encode(records[i].data);
				}
				params = UserRole.gridWin.getRecord().data;
				params.roles = roles;
				Ext.Ajax.request({
							url : 'userController.ajax?method=saveUserRoles',
							params : params,
							success : function(response) {
								var text = response.responseText;
								var result = Ext.JSON.decode(text);
								if (result.success) {
									UserRole.gridWin.hide();
								} else {
									Ext.Msg.alert("错误", result.message);
								}
							}
						});
			};

			UserRole.store = Ext.create("Ext.data.Store", {
						fields : ['userRoleId', 'roleName', 'roleId', 'auth',
								'roleType'],
						pageSize : 0,
						proxy : {
							type : 'ajax',
							url : "userController.ajax?method=listUserRole",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
						},
						sorters : [{
									property : 'roleId'
								}]
					});

			UserRole.gridPanel = Ext.create('Ext.grid.Panel', {
						width : 400,
						height : 300,
						store : UserRole.store,
						rowLines : true,
						selmodel : Ext.create('Ext.selection.CheckboxModel'),
						columnLines : true,
						columns : [{
									xtype : 'checkcolumn',
									text : '授权',
									dataIndex : 'auth',
									editor : new Ext.form.field.Checkbox()
								}, {
									text : '授权',
									dataIndex : 'roleType',
									width : 150,
									renderer : function(v) {
										return UserRole.roleDict
												.getDictValue(v)
									},
									editor : new Sys.DictBox({
												groupCode : 'sys_user_role.role_type',
												store : UserRole.roleDict
														.getStore()
											})
								}, {
									text : '角色名称',
									dataIndex : 'roleName',
									flex : 1

								}],
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 1
								})]
					});

			UserRole.gridWin = Ext.create('Ext.window.Window', {
						title : '字典管理',
						layout : 'fit',
						items : UserRole.gridPanel,
						closeAction : 'hide',
						showRecord : function(record) {
							this.record = record;
							this.show();
							UserRole.query();
						},
						getRecord : function() {
							return this.record;
						},
						buttons : [{
									text : '确定',
									handler : UserRole.save
								}, {
									text : '取消',
									handler : function() {
										UserRole.gridWin.hide()
									}
								}]
					});

			win = desktop.createWindow({
						id : 'user',
						title : '用户管理',
						width : 740,
						height : 480,
						iconCls : 'user',
						animCollapse : false,
						constrainHeader : true,
						layout : 'fit',
						items : User.gridPanel

					});

		}
		return win;
	}
});
