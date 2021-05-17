Ext.namespace("Book");
Book.query = function(){
	var params = Book.getParams("queryBar");
	Book.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Book.store.proxy.reader.jsonData.message );
			}
		}
	});
}

Book.getParams = function (){
	var docks= Book.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.getValue){
					var name = item['name'];
					var value = item.getValue();
					if(item.getSubmitValue()){
						value = item.getSubmitValue();
					}
					params[name]=value;
				}
			}	
		);
	}	
	return params;
}
Book.save = function (){
	Book.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'mbook.ajax?method=saveBook',
		 success: function(form, action) {
			 Book.formWin.hide();
			 Book.store.reload();
		 },
		 failure: function(form, action) {
		        switch (action.failureType) {
		            case Ext.form.action.Action.CLIENT_INVALID:
		                Ext.Msg.alert('失败', '无效的值');
		                break;
		            case Ext.form.action.Action.CONNECT_FAILURE:
		                Ext.Msg.alert('失败', '网络连接失败');
		                break;
		            case Ext.form.action.Action.SERVER_INVALID:
		               Ext.Msg.alert('失败', action.result.message);
		       }
	    }
	});
};

Book.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'mbook.ajax?method=deleteBook',
			    params:rec.data,
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.JSON.decode(text);
			        if(result.success){
			        	Ext.Msg.alert("信息","删除成功");
			        	store.reload();
			        }else{
			        	Ext.Msg.alert("错误",result.message);
			        }
			    }
			});
		}		
	});
};

Book.store = Ext.create('Ext.data.Store',{
	fields:[
	 'id',
	'name',
    	'author',
    	'type',
     { name: 'date',convert: Book.formatDate }
	],
	 pageSize: 40,
	proxy: {
    	type: 'ajax',
        url : "mbook.ajax?method=listBook",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
Book.store.load();

Book.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'书籍管理',
	store:Book.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'主键',
		dataIndex:'id',
		
		width:200
	},
	{
		text:'书名',
		dataIndex:'name',
		width:200
	},
	{
		text:'作者',
		dataIndex:'author',
		width:200
	},
	{
		text:'书籍类型',
		dataIndex:'type',
		width:200
	},
	{
		text:'出版日期',
		dataIndex:'date',
		width:200
	},
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[{iconCls : 'Applicationedit',altText : '修改',handler :  
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		Book.formWin.showUpdate(rec)}},
		'-',
		{iconCls:'delete',altText:'删除',handler:Book.del} 
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
'书名:',{ xtype:'textfield', name:'name|like'},
				'出版日期起始:',{ xtype:'datefield',format:'Ymd' ,name:'date|ge'},
				'出版日期结束:',{ xtype:'datefield',format:'Ymd' ,name:'date|le'}
						       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Book.query,iconCls:'Magnifier'},
	        {text:'添加',handler:function(){	Book.formWin.showAdd()},iconCls:'add'}
	    ]
    },{
        xtype: 'pagingtoolbar',
        pageSize:5,
        store: Book.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});
Book.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
 {fieldLabel:'书名',name:'name',allowBlank:false},
 {fieldLabel:'作者',name:'author',allowBlank:false},
 {fieldLabel:'书籍类型',name:'type',allowBlank:false},
	     {fieldLabel:'出版日期',name:'date',xtype:'datefield',format:'Ymd',allowBlank:false},
	     new Ext.form.Hidden({name:'id'}),
         	       new Ext.form.Hidden({name:'_saveType'})
	]
});

	
	
Book.formWin = Ext.create('Ext.window.Window',{
	title:'书籍管理',
    layout: 'fit',
    items: Book.formPanel,
    closeAction : 'hide',
    showAdd: function(){   
    	var form = this.child('form');
    	form.getForm().reset();
		form.getForm().findField("_saveType").setValue("add");
    	this.show();
    },
    showUpdate:function(record){
    	var form = this.child('form');
		form.getForm().loadRecord(record);
	    form.getForm().findField("_saveType").setValue("update");
    	this.show();
    },
    buttons:[
             {text:'提交',handler:Book.save},
             {text:'取消',handler:function(){Book.formWin.hide();}}
    ]
});
