Ext.namespace("Icon");
Icon.query = function(){
	var params = Icon.getParams('queryBar');
	Icon.store.load({
		params : params ,
		callback : function(r, options, success) {
			if (success == false) {
				Ext.Msg.alert("错误", store.proxy.reader.jsonData.message);
			}
		}
	});
};

Icon.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var rec = grid.getStore().getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'icon.ajax?method=delete',
			    params:rec.data,
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.JSON.decode(text);
			        if(result.success){
			        	Ext.Msg.alert("信息","删除成功");
			        	Icon.store.reload();
			        }else{
			        	Ext.Msg.alert("错误",result.message);
			        }
			    }
			});
		}
	});	
};

Icon.refresh = function (){
	Ext.Ajax.request({
	    url: 'icon.ajax?method=refresh',
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	Ext.Msg.alert("信息","刷新成功");
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
};


Icon.getParams = function(id){
	var bar = Ext.getCmp(id);
	var params= {};
	bar.items.each(
			function(item,index){
				if(Ext.getCmp(item['id'])){
					var name = item['id'];
					if(Ext.getCmp(name).getValue){
						var value = Ext.getCmp(item['id']).getValue();
						params[name]=value;
					}
				}
			}	
	);
	return params;
}
Icon.store = Ext.create('Ext.data.Store', {
    fields:['css', 'name', 'path'],
    proxy: {
    	type: 'ajax',
        url : "icon.ajax?method=list",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
Icon.store.load();
Icon.columns = [
       {xtype: 'rownumberer'},
       { text: '样式',  dataIndex: 'css' },
       { text: '名称', dataIndex: 'name' },
       { text: '图标',renderer : function (v,meta,record)
    	   { return "<span class='"+record.data.css+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";}
       },
       { text: '路径', dataIndex: 'path',hidden:true },
       {  xtype:'actioncolumn',
    	  text : '操作',
    	  align: 'center',
    	  items:[
    	         {iconCls:'Applicationedit',tooltip:'修改',handler:function(grid, rowIndex, colIndex){
    					var rec = grid.getStore().getAt(rowIndex);
    					Icon.detailWin.showUpdate(rec);
    				}
		    	  },
    	         '-',
    		  {iconCls:'Delete',tooltip:'删除',handler:Icon.del}
    	  ]
       }
];

Icon.cssQueryField = new Ext.form.field.Text({
		id:'css|like',
		name:'css|like'
});

Icon.gridPanel = Ext.create('Ext.grid.Panel', {
    title:'图标管理',
    store: Icon.store,
    columns:Icon.columns,
    dockedItems: [{
    	id:'queryBar',
    	xtype: 'toolbar',
	    dock: 'top',
	    items: ['样式：',Icon.cssQueryField]
    },{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Icon.query,iconCls:'Magnifier'},
	        {text:'添加',handler:function(){Icon.detailWin.showAdd();},iconCls:'add'},
	        {text:'刷新',handler:function(){Icon.refresh();},iconCls:'Pagerefresh'}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Icon.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});



