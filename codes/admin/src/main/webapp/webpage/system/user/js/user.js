Ext.namespace("User");
User.orgBox = Ext.create("Sys.OrgBox", {
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
						Ext.Msg.alert("错误",
								User.store.proxy.reader.jsonData.message);
					}
				}
			});
}
User.save = function() {
	User.formPanel.getForm().submit({
				clientValidation : true,
				url : 'user.ajax?method=saveUser',
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
							Ext.Msg.alert('失败', action.result.message);
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
								url : 'user.ajax?method=deleteUser',
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

User.resetPass = function(grid, rowIndex, colIndex) {
	Ext.Msg.confirm("提示", "确定重置该用户密码?", function(confirm) {
				if ("yes" == confirm) {
					var store = grid.getStore();
					var rec = store.getAt(rowIndex);
					Ext.Ajax.request({
								url : 'user.ajax?method=resetPassword',
								params : rec.data,
								success : function(response) {
									var text = response.responseText;
									var result = Ext.JSON.decode(text);
									if (result.success) {
										Ext.Msg.alert("信息", "删除成功");
									} else {
										Ext.Msg.alert("错误", result.message);
									}
								}
							});
				}
			});

};

User.store = Ext.create('Ext.data.Store', {
			fields : ['loginName', 'name', 'email', 'mobile', 'orgId'],
			proxy : {
				type : 'ajax',
				url : "user.ajax?method=listUser",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			}
		});
User.store.load();
User.gridPanel = Ext.create('Ext.grid.Panel', {
			title : '人员管理',
			store : User.store,
			rowLines : true,
			columnLines : true,
			columns : [{
						xtype : 'actioncolumn',
						text : '操作',
						align : 'center',
						width:200,
						items : [
						         
						         {iconCls:'Applicationedit',tooltip:'修改',handler:function(grid, rowIndex, colIndex){
				    					var rec = grid.getStore().getAt(rowIndex);
				    					User.formWin.showUpdate(rec);
				    				}
						    	  },
				    	         '-',{
									iconCls : 'Pageadd',
									tooltip : '分配角色',
									handler : function(grid, rowIndex, colIndex) {
										var rec = grid.getStore()
												.getAt(rowIndex);
										UserRole.gridWin.showRecord(rec);
									}
								},'-', 
								{
									iconCls : 'Key',
									tooltip : '重置密码',
									handler : User.resetPass
								},'-', 
								{
									iconCls : 'delete',
									tooltip : '删除',
									handler :User.del
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
				form.getForm().findField("_saveType").setValue("add");
				this.show();
			},
			showUpdate : function(record) {
				var form = this.child('form');
				form.getForm().loadRecord(record);
				form.getForm().findField("_saveType").setValue("update");
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
	var docks = User.gridPanel.getDockedItems('toolbar[dock="top"]');
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
