Ext.namespace("Dict");
Dict.query = function() {
	var record = Dict.gridWin.getRecord();
	if (record) {
		var params = {};
		params.groupCode = record.data.groupCode;
		Dict.store.load({
					params : params
		});
	}
},
Dict.save = function() {
	Dict.formPanel.getForm().submit({
				clientValidation : true,
				url : 'dictGroup.ajax?method=saveDict',
				success : function(form, action) {
					Dict.formWin.hide();
					Dict.store.reload();
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

Dict.del = function(grid, rowIndex, colIndex) {
	Ext.Msg.confirm("提示", "你确定要删除该记录么?", function(confirm) {
				if ("yes" == confirm) {
					var store = grid.getStore();
					var rec = store.getAt(rowIndex);
					Ext.Ajax.request({
								url : 'dictGroup.ajax?method=deleteDict',
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




Dict.store = Ext.create('Ext.data.Store', {
			fields : ['dictCode', 'dictValue'],
			pageSize : -1,
			proxy : {
				type : 'ajax',
				url : "dictGroup.ajax?method=listDict",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			}
		});
Dict.gridPanel = Ext.create('Ext.grid.Panel', {
			width : 400,
			height : 300,
			store : Dict.store,
			columns : [{
						text : '字典值',
						dataIndex : 'dictCode'
					}, {
						text : '字典释义',
						dataIndex : 'dictValue'
					}, {
						xtype : 'actioncolumn',
						text : '操作',
						align : 'center',
						flex : 1,
						items : [{
									iconCls : 'Delete',
									tooltip : '删除',
									handler : Dict.del
								}]
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									text : '添加',
									handler : function() {
										Dict.formWin.showAdd()
									},
									iconCls : 'add'
								}]
					}]
		});
Dict.gridWin = Ext.create('Ext.window.Window', {
			title : '字典管理',
			layout : 'fit',
			items : Dict.gridPanel,
			closeAction : 'hide',
			showRecord : function(record) {
				this.record = record;
				this.show();
				Dict.query();
			},
			getRecord : function() {
				return this.record;
			},
			buttons : [{
						text : '确定',
						handler : function() {
							Dict.gridWin.hide()
						}
					}]
		})

Dict.formPanel = Ext.create('Ext.form.Panel', {
			bodyPadding : 10,
			defaultType : 'textfield',
			items : [{
						fieldLabel : '字典名称',
						name : 'groupName',
						readOnly : true,
						fieldStyle : 'background:#E6E6E6'
					}, {
						fieldLabel : '字典值',
						name : 'dictCode',
						allowBlank : false
					}, {
						fieldLabel : '字典释义',
						name : 'dictValue',
						allowBlank : false
					}, new Ext.form.Hidden({
								name : '_saveType'
							}), new Ext.form.Hidden({
								name : 'groupCode'
							}), new Ext.form.Hidden({
								name : 'id'
							})]
		});

Dict.formWin = Ext.create('Ext.window.Window', {
			title : '字典管理',
			layout : 'fit',
			items : Dict.formPanel,
			closeAction : 'hide',
			showAdd : function() {
				var form = this.child('form');
				form.getForm().reset();
				var rec = Dict.gridWin.getRecord();
				form.getForm().findField("groupName")
						.setValue(rec.data.groupName);
				form.getForm().findField("groupCode")
						.setValue(rec.data.groupCode);
				form.getForm().findField("_saveType").setValue("add");
				this.show();
			},
			showUpdate : function(record) {
				var form = this.child('form');
				form.findField("_saveType").setValue("update");
				this.show();
			},
			buttons : [{
						text : '提交',
						handler : Dict.save
					}]
		});
