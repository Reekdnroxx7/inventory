Ext.namespace("CodegTable");
CodegTable.query = function(dataSource) {
	var params = {dataSourceId:dataSource.data.id};
	CodegTable.dataSource = dataSource;
	CodegTable.store.load({
				params : params,
				callback : function(records, operation, success) {
					if (success == false) {
						Ext.Msg.alert("错误",
								CodegTable.store.proxy.reader.jsonData.message);
					}
				}
	});
};
CodegTable.save = function() {
	CodegTable.formPanel.getForm().submit({
				clientValidation : true,
				url : 'datasourceConfig.ajax?method=saveCodegTable',
				success : function(form, action) {
					CodegTable.formWin.hide();
					CodegTable.store.reload();
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
CodegTable.del = function(grid, rowIndex, colIndex) {
	var store = grid.getStore();
	var rec = store.getAt(rowIndex);
	Ext.Ajax.request({
				url : 'datasourceConfig.ajax?method=deleteCodegTable',
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
};
CodegTable.store = Ext.create('Ext.data.Store', {
			fields : ['name', 'displayName','entityName'],
			proxy : {
				type : 'ajax',
				url : "datasourceConfig.ajax?method=listCodegTable",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			}
		});
//CodegTable.store.load();
CodegTable.gridPanel = Ext.create('Ext.grid.Panel', {
			title : '代码生成',
			store : CodegTable.store,			
			collapsible: true,
			rowLines : true,
			columnLines : true,
			columns : [
			         {xtype: 'rownumberer'},
			         {
						text : '表名',
						dataIndex : 'name',
						width : 200
					}, {
						text : '显示名称',
						dataIndex : 'displayName',
						width : 200
					}, {
						xtype : 'actioncolumn',
						text : '操作',
						align : 'center',
						items : [{
									iconCls : 'Pageadd',
									tooltip:'生成配置',
									handler : function(grid, rowIndex, colIndex) {
										var rec = grid.getStore()
												.getAt(rowIndex);
										CodegField.gridWin.showRecord(rec);
									}
								}]
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : ['表名:', {
									xtype : 'textfield',
									listeners:{
										change:function(f,v){
											CodegTable.store.clearFilter(false);
											if(v){
												CodegTable.store.filter(
													{filterFn: function(item) { return item.get("name").indexOf(v) >= 0 ;}}
												);
											}
										}
									}
										
								}]
					}/*, {
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									text : '查询',
									handler : CodegTable.query,
									iconCls : 'Magnifier'
								}]
					}*/]
		});

CodegTable.getParams = function() {
	var docks = CodegTable.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params = {};
	// alert(outputObject(docks[0]));
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
};

//function generate(rec) {
//	var rec = grid.getStore().getAt(rowIndex);
//	Ext.Ajax.request({
//				url : getGeneratorUrl(),
//				params : {tableName:rec.data.name},
//				success : function() {
//					Ext.Msg.alert("信息", '创建成功');
//				}
//			});
//};
