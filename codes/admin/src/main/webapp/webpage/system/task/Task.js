Ext.namespace("Task");
//字典配置
//方法
Task.query = function(){
	var params = Task.getParams("queryBar");
	Task.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Task.store.proxy.reader.jsonData.message );
			}
		}
	});
};

Task.statusBox = Ext.create("Sys.DictBox", {
	groupCode : 'sys_task_status'
});

Task.getParams = function (){
	var docks= Task.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.getValue){
					var name = item['name'];
					var value = item.getValue();
					if(item.xtype == 'datefield'){
						value=item.getRawValue();
					}
					if(value){
						params[name]=value;
					}
				}
			}	
		);
	}	
	return params;
};

Task.reset = function(){
	var docks= Task.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.setValue){
					var name = item['name'];
					item.setValue('');
				}
			}	
		);
	}	
};

Task.save = function (){
	Task.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'task.ajax?method=saveTask',
		 success: function(form, action) {
			 Task.formWin.hide();
			 Task.store.reload();
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

Task.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'task.ajax?method=deleteTask',
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

Task.changeStatus = function (rec){
//	  var rec = grid.getStore().getAt(rowIndex);
		var data = rec.data;
		
		Ext.Ajax.request({
		    url: 'task.ajax?method=updateStatus',
		    params:data,
		    success: function(response){
		        var text = response.responseText;
		        var result = Ext.JSON.decode(text);
		        if(result.success){
		        	Ext.Msg.alert("信息","操作成功");
		        	Task.store.reload();
		        }else{
		        	Ext.Msg.alert("错误",result.message);
		        }
		    }
		});
};

Task.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',	
		'name',	
		'beanName',	
		'methodName',	
		'cronExpression',	
		'status'	
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "task.ajax?method=listTask",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
Task.store.load();

Task.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'SYS_TASK管理',
	store:Task.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'主键',
		dataIndex:'id',
		hidden:true,
		width:200
	},	
	{
		text:'名称',
		dataIndex:'name',
		width:200
	},	
	{
		text:'beanId',
		dataIndex:'beanName',
		width:200
	},	
	{
		text:'方法名',
		dataIndex:'methodName',
		width:200
	},	
	{
		text:'cron表达式',
		dataIndex:'cronExpression',
		width:200
	},	
	{
		text:'状态',
		dataIndex:'status',
		renderer : function(v) {
			return Task.statusBox.getDictValue(v)
		},
		width:200
	},	
	{
		text:'',
		dataIndex:'createTime',
		hidden:true,
		width:200
	},	
	{
		text:'',
		dataIndex:'updateTime',
		hidden:true,
		width:200
	},	
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		width : 200,
		items:[
		       {iconCls:'Controlstartblue',tooltip:'启动',handler:function(grid, rowIndex, colIndex){
		    	   var rec = grid.getStore().getAt(rowIndex);
		    	   rec.data.status = "0";
		    	   Task.changeStatus(rec);
		       },isDisabled : function(v,r,c,i,record){
		    	   return record.data.status == "0";
		       }},'-',
		       {iconCls:'Controlpause',tooltip:'暂停',handler:function(grid, rowIndex, colIndex){
		    	   var rec = grid.getStore().getAt(rowIndex);
		    	   rec.data.status = "1";
		    	   Task.changeStatus(rec);
		       },isDisabled : function(v,r,c,i,record){
		    	  return record.data.status != "0";
		       }},'-',
		       {iconCls : 'Applicationedit',tooltip : '修改',handler :  
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		Task.formWin.showUpdate(rec);}},
		'-',
		{iconCls:'delete',tooltip:'删除',handler:Task.del} 
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'名称:',{ xtype:'textfield', name:'name|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Task.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	Task.formWin.showAdd();},iconCls:'add'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:Task.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Task.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});
Task.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
	     {fieldLabel:'名称',xtype:'textfield',name:'name',allowBlank:false,value:'測試'},        
	     {fieldLabel:'beanId',xtype:'textfield',name:'beanName',allowBlank:false,value:'com.beasy.web.manage.sys.demo.DemoTask'},        
	     {fieldLabel:'方法名',xtype:'textfield',name:'methodName',allowBlank:false,value:'execute'},        
	     {fieldLabel:'cron表达式',xtype:'textfield',name:'cronExpression',allowBlank:false,value:'0/5 * * * * *'},        
//	     {fieldLabel:'状态',xtype:'textfield',name:'status',allowBlank:false},      
//	     {fieldLabel:'',xtype:'datefield',format:'Ymd',name:'createTime',allowBlank:false},        
//	     {fieldLabel:'',xtype:'datefield',format:'Ymd',name:'updateTime',allowBlank:false},        
	    new Ext.form.Hidden({name:'id'}),
	    new Ext.form.Hidden({name:'status'}),
        new Ext.form.Hidden({name:'_saveType'})
	]
});

	
	
Task.formWin = Ext.create('Ext.window.Window',{
	title:'SYS_TASK管理',
    layout: 'fit',
    items: Task.formPanel,
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
             {text:'提交',handler:Task.save},
             {text:'取消',handler:function(){Task.formWin.hide();}}
    ]
});
