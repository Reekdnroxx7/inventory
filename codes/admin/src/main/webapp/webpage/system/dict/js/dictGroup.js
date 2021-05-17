Ext.namespace("DictGroup");
DictGroup.query = function(){
	var params = DictGroup.getParams("queryBar");
	DictGroup.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", DictGroup.store.proxy.reader.jsonData.message );
			}
		}
	});
}
DictGroup.save = function (){
	DictGroup.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'dictGroup.ajax?method=saveDictGroup',
		 success: function(form, action) {
			 DictGroup.formWin.hide();
			 DictGroup.store.reload();
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
DictGroup.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'dictGroup.ajax?method=deleteDictGroup',
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

DictGroup.refresh = function (){
	Ext.Ajax.request({
	    url: 'dictGroup.ajax?method=refresh',
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	DictGroup.store.reload();
	        	Ext.Msg.alert("信息","刷新成功");
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
};

DictGroup.store = Ext.create('Ext.data.Store',{
	fields:['groupCode','groupName'],
	proxy: {
    	type: 'ajax',
        url : "dictGroup.ajax?method=listDictGroup",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
DictGroup.store.load();
DictGroup.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'字典管理',
	store:DictGroup.store,
	rowLines:true,
	columnLines : true,
	columns:[{
		text:'字典代码',
		dataIndex:'groupCode',
		width:200
	},{
		text:'字典名称',
		dataIndex:'groupName',
		width:200
	},{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[
		       {iconCls:'Pageadd',tooltip:'添加字典',handler:function(grid, rowIndex, colIndex){
		    	   var rec = grid.getStore().getAt(rowIndex);
		    	   Dict.gridWin.showRecord(rec);
		       }},
		       '-',
		       {iconCls:'Delete',tooltip:'删除',handler:DictGroup.del}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
		       '字典代码:',{ xtype:'textfield', name:'groupCode|like'},
		       '字典名称:',{ xtype:'textfield', name:'groupName|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:DictGroup.query,iconCls:'Magnifier'},
	        {text:'添加',handler:function(){	DictGroup.formWin.showAdd();},iconCls:'add'},
	        {text:'刷新',handler:function(){DictGroup.refresh();},iconCls:'Pagerefresh'}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: DictGroup.store,  
        dock: 'bottom',
        displayInfo: true
    }]
});

DictGroup.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
	       {fieldLabel:'字典代码',name:'groupCode',allowBlank:false	    	   
	       },
	       {fieldLabel:'字典名称',name:'groupName',allowBlank:false},
	       new Ext.form.Hidden({name:'_saveType'}),
	       new Ext.form.Hidden({name:'id'})
	]
});

	
	
DictGroup.formWin = Ext.create('Ext.window.Window',{
	title:'字典管理',
    layout: 'fit',
    items: DictGroup.formPanel,
    closeAction : 'hide',
    showAdd: function(){   
    	var form = this.child('form');
    	form.getForm().reset();
		form.getForm().findField("_saveType").setValue("add");
    	this.show();
    },
    showUpdate:function(record){
    	var form = this.child('form');
    	form.getForm.loadRecord(record);
		form.findField("_saveType").setValue("update");
    	this.show();
    },
    buttons:[
             {text:'提交',handler:DictGroup.save},
             {text:'取消',handler:function(){DictGroup.formWin.hide();}}
    ]
});


DictGroup.getParams = function (){
	var docks= DictGroup.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
//	alert(outputObject(docks[0]));
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




