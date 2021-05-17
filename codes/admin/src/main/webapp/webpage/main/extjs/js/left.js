//系统菜单树根节点
Ext.define('Menu', {
    extend: 'Ext.data.Model',
    fields: ["id",'type',
             {name:'transcode',mapping:'transcode'},
             {name:'leaf',convert:function(value,record){
            	 return record.data.type == '0';
             }},
             {name:'text',mapping:'name'},
             {name:'iconCls',convert:function(value,record){
            	 if(record.data.icon){
            		 return record.data.icon;
            	 }else{
            		 if(record.data.type == '0'){
            			 return 'Applicationviewdetail';
            		 }else{
            			 return 'folder';
            		 }
            	 }
             }}
             ,{name:'sort'}
             ]   
});
var treeStore = new Ext.data.TreeStore({
	 nodeParam : 'menuId',
	 proxy: { 
         type: 'ajax',  
         url: 'common.ajax?method=menuTree'    	
     },
     model:'Menu',
     root:{ 
         name:'根节点', 
         expanded : true,//根节点是否展开 
         id:'1' 
     } ,
     sorters:[{property:'sort'}]
});

var menu_panel = Ext.create("Ext.tree.Panel",{
	// 如果超出范围带自动滚动条
	autoScroll : true,
	store : treeStore,
	// 默认根目录不显示
	rootVisible : false,
	border : false,
	animate : true,
	lines : true,
	listeners : {
		"itemclick" : function(t, record, item, index, event, eOpts ) // 点击处理事件
		{
			// 叶子节点点击不进入链接
			if (record.data.leaf) {
				// 显示叶子节点菜单
				showContent(record);
				event.stopEvent();
			} 
		}	
	}
});

var west_panel = new Ext.Panel({
	region:'west',
    id:'west-panel',
	name:'west-panel',
    split:true,
    width: 200,
    minSize: 175,
    maxSize: 400,
    title:'导航菜单', 
    collapsible: true,
    margins:'0 0 5 5',
    autoScroll:true,
    animCollapse:false,
    animate: false,
    collapseMode:'mini', 
    items:  menu_panel                         
});


function showContent(record){
	var node = record.data;
	var id = node.id;
	var n = center_panel.getComponent(id);
	// 如果没有打开
	if(!n){
		if(center_panel.items.getCount() >= 10){
			Ext.Msg.alert("窗口提示","为了确保浏览效果，页面不允许同时打开超过10个窗口，请关闭不需要的窗口后再操作！");
			return ;
		}
		else{
			n = center_panel.add({
				'id':node.id,
				'title':node.text,
				closable:true, // 通过html载入目标页
				html:'<iframe scrolling="auto" id="'+'iframe-'+node.id+'" frameborder="0" width="100%" height="100%" src="common.do?method=toMenu&menuId='+node.id+'"></iframe>'
			});
		}
	}
	center_panel.setActiveTab(n);
}

