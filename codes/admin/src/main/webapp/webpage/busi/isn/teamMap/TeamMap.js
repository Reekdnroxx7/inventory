Ext.namespace("TeamMap");
//字典配置
//方法
TeamMap.query = function(){
	var params = TeamMap.getParams("queryBar");
	TeamMap.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", TeamMap.store.proxy.reader.jsonData.message );
			}
		}
	});
};

TeamMap.getParams = function (){
	var docks= TeamMap.gridPanel.getDockedItems('toolbar[dock="top"]');
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

TeamMap.reset = function(){
	var docks= TeamMap.gridPanel.getDockedItems('toolbar[dock="top"]');
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

TeamMap.save = function (){
	TeamMap.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'isnTeamMap.ajax?method=saveTeamMap',
		 success: function(form, action) {
			 TeamMap.formWin.hide();
			 TeamMap.store.reload();
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

TeamMap.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'isnTeamMap.ajax?method=deleteTeamMap',
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

TeamMap.store = Ext.create('Ext.data.Store',{
	fields:[
		"id",
		'src_name',
		'std_name'	
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "isnTeamMap.ajax?method=listTeamMap",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
TeamMap.store.load();

TeamMap.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'isn_team_map管理',
	store:TeamMap.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'智博网球队名',
		dataIndex:'src_name',
		width:200
	},	
	{
		text:'标准球队名',
		dataIndex:'std_name',
		width:200
	},	
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[
		{iconCls:'delete',tooltip:'删除',handler:TeamMap.del} 
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'智博网球队名:',{ xtype:'textfield', name:'src_name|like'}
					,'-',
					'标准队名:',{ xtype:'textfield', name:'std_name|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:TeamMap.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	TeamMap.formWin.showAdd();},iconCls:'add'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:TeamMap.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: TeamMap.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});
TeamMap.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
	     {fieldLabel:'智博网球队名',xtype:'textfield',name:'src_name',allowBlank:false},
	     {fieldLabel:'标准队名',xtype:'textfield',name:'std_name',allowBlank:false},
       new Ext.form.Hidden({name:'_saveType'})
	]
});

	
	
TeamMap.formWin = Ext.create('Ext.window.Window',{
	title:'isn_team_map管理',
    layout: 'fit',
    items: TeamMap.formPanel,
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
             {text:'提交',handler:TeamMap.save},
             {text:'取消',handler:function(){TeamMap.formWin.hide();}}
    ]
});
