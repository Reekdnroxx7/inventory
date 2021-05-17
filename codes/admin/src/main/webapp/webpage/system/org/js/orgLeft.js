Ext.define('Org', {
			extend : 'Ext.data.TreeModel',
			fields : ['id', 'type', 'address', 'zipCode', 'master', 'phone',
					'fax', 'email', 'level', 'code', 'children',/*{
						name : 'leaf',
						convert : function(value, record) {
							return record.data.type == '2';
						}
					}, */{
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
var treeStore = new Ext.data.TreeStore( {
	nodeParam : 'orgId',
	proxy : {
		type : 'ajax',
		url : 'org.ajax?method=list'
	},
	model : 'Org',
//	root : {
//		expanded : true,// 根节点是否展开
//		id : '-1',
//		name:'本机构',
//		leaf : false
//	},
	sorters : [ {
		property : 'code'
	}
	
	]
});
treeStore.load();
var org_panel = Ext.create("Ext.tree.Panel", {
			// 如果超出范围带自动滚动条
			autoScroll : true,
			store : treeStore,
			rootVisible : false,// 显示根目录，默认为false
			displayField : 'name',
			border : false,
			animate : true,
			lines : true,
			listeners : {
				"itemclick" : function(t, record, item, index, event, eOpts) // 点击处理事件
				{
					if(record.data.id ==-1) return ;// 不可以修改本机构
					showEdit(record);
				},
				"itemcontextmenu" : function(t, record, item, index, event,
						eOpts) {
					event.preventDefault();// 关闭默认的菜单，以避免弹出两个菜单
//					if(record.data.id ==-1) return ;// 不可以修改本机构
					record.expand(0)
					this.setRecord(record);
					this.contextMenu.showAt(event.getXY());
				}
				,
				"load" : function(panel, node, records, successful, eOpts ){
					
					org_panel.expandAll();
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
//			onRender:function(config){
//		    	this.callParent(arguments);
//		    	this.getPicker().expandAll();
//		    }
		});
function showEdit(record) {
	centerPanel.getForm().loadRecord(record);
	var parentRec = treeStore.getById(record.data.parentId);
	org_panel.setParent(parent);
	if (parentRec) {
		parentName.setValue(parentRec.data.name);
	};
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
				url : 'org.ajax?method=delete',
				params : rec.data,
				success : function(response) {
					var text = response.responseText;
					var result = Ext.JSON.decode(text);
					if (result.success) {
						var parent = treeStore.getNodeById(rec.data.parentId);
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