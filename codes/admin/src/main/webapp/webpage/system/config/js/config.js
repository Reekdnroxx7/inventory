Ext.namespace("Config");
Config.query = function(){
	var params = Config.getParams("queryBar");
	Config.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Config.store.proxy.reader.jsonData.message );
			}
		}
	});
}

Config.getParams = function (){
	var docks= Config.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.getValue){
					var name = item['name'];
					var value = item.getValue();
					params[name]=value;
				}
			}	
		);
	}	
	return params;
}
Config.save = function (){
	Config.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'config.ajax?method=saveConfig',
		 success: function(form, action) {
			 Config.formWin.hide();
			 Config.store.reload();
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

Config.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'config.ajax?method=deleteConfig',
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

Config.refresh = function (){
	Ext.Ajax.request({
	    url: 'config.ajax?method=refresh',
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
Config.store = Ext.create('Ext.data.Store',{
	fields:[
	'key',
    'value'
	],
	proxy: {
    	type: 'ajax',
        url : "config.ajax?method=listConfig",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
Config.store.load();

Config.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'系统参数',
	store:Config.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'键',
		dataIndex:'key',
		width:200
	},
	{
		text:'值',
		dataIndex:'value',
		width:200
	},
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[{iconCls : 'Applicationedit',altText : '修改',handler :  
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		Config.formWin.showUpdate(rec);}},
		'-',
		{iconCls:'Delete',altText:'删除',handler:Config.del} 
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
'键:',{ xtype:'textfield', name:'key|like'},
'值:',{ xtype:'textfield', name:'value|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Config.query,iconCls:'Magnifier'},
	        {text:'添加',handler:function(){	Config.formWin.showAdd();},iconCls:'add'},
	        {text:'刷新',handler:function(){Config.refresh();},iconCls:'Pagerefresh'}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Config.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});
Config.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
new Ext.form.Hidden({name:'id'}),
 {fieldLabel:'键',name:'key',allowBlank:false},
 {fieldLabel:'值',name:'value',allowBlank:false},
	       new Ext.form.Hidden({name:'_saveType'})
	]
});

	
	
Config.formWin = Ext.create('Ext.window.Window',{
	title:'系统参数',
    layout: 'fit',
    items: Config.formPanel,
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
             {text:'提交',handler:Config.save},
             {text:'取消',handler:function(){Config.formWin.hide();}}
    ]
});
