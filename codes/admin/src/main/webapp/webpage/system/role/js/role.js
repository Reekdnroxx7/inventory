var store = Ext.create('Ext.data.Store', {

			fields : ['name'],
			proxy : {
				type : 'ajax',
				url : "role.ajax?method=listRole",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			}
		});

var columns = [{
			xtype : 'rownumberer'
		}, {
			hidden:true,
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
							var rec = grid.getStore().getAt(rowIndex);
							detailWin.showUpdate(rec);
						},
						iconCls : 'Applicationedit'
					}]
		}];

var nameQueryField = new Ext.form.field.Text({
			id : 'name|like',
			name : 'name|like'
		});
var grid_panel = Ext.create('Ext.grid.Panel', {
			title : '角色列表',
			region : 'west',
			width : 400,
			split : true,
			store : store,
			columns : columns,
			dockedItems : [{
						id : 'queryBar',
						xtype : 'toolbar',
						dock : 'top',
						items : ['名称：', nameQueryField]
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
			}],
		listeners: {
			"itemclick": function (me, record, item, index, e, eOpts) {
				showMenu(grid_panel, index);
			}
		}
	}
);
function showMenu(grid, rowIndex) {
	var rec = grid.getStore().getAt(rowIndex);
	center_panel.loadRecord(rec);
}
function del(grid, rowIndex) {
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var rec = grid_panel.getStore().getAt(rowIndex);
			Ext.Ajax.request({
						url : 'role.ajax?method=delete',
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

}

function query() {
	var params = getParams('queryBar');
	store.load({
				params : params,
				callback : function(r, options, success) {
					if (success == false) {
						Ext.Msg.alert("错误",
					       store.proxy.reader.jsonData.message);
					}
				}
			});
}
function getParams(id) {
	var bar = Ext.getCmp(id);
	var params = {};
	bar.items.each(function(item, index) {
				if (Ext.getCmp(item['id'])) {
					var name = item['id'];
					if (Ext.getCmp(name).getValue) {
						var value = Ext.getCmp(item['id']).getValue();
						params[name] = value;
					}
				}
			});
	return params;
}