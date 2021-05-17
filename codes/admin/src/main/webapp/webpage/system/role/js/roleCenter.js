Ext.define('Menu', {
			extend : 'Ext.data.TreeModel',
			fields : ["menuId", 'type', 'leaf','operationId',{
						name : 'location',
						mapping : 'href'
					}, /*{
						name : 'leaf',
						convert : function(value, record) {
							return record.data.type == '0';
						}
					},*/ {
						name : 'name',
						mapping : 'name'
					}, {
						name : 'iconCls',
						mapping : 'icon',
						defulatValue:'detail'
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
				url : 'role.ajax?method=listMenu'
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
				handler : function(){
	        	var record = center_panel.getRecord();
	        	submit(record);
	        }
			}],
	loadRecord:function(rec){
		this.record = rec;
		treeStore.load({
				params :  rec.data				
			});
	},
	getRecord:function(){
		return this.record;
	},
	viewConfig : {
		onCheckboxChange : function(e, t) {
			var item = e.getTarget(this.getItemSelector(), this.getTargetEl()), record;
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
	if(rec == null){
		Ext.Msg.alert("信息", '请选择角色');
		return;
	}
	var nodes = center_panel.getChecked();
	var menuIds = new Array();
	Ext.each(nodes, function(node) {
				var id = node.data.menuId;
				id += node.data.operationId ? "|"+node.data.operationId : "";
				menuIds.push(id);
			});
	Ext.Ajax.request({
				url : 'role.ajax?method=saveRoleMenu',
				params : {
					roleId:rec.data.id ,
					mAndops : menuIds					
				},
				success : function() {
					Ext.Msg.alert("信息", '提交成功');
				}
			});
}
