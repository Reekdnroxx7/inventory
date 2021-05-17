Ext.namespace("DatasourceConfig");
//字典配置
DatasourceConfig.dbTypeBox = Ext.create("Sys.DictBox", {
			fieldLabel : '数据库类型',
			name : 'dbType',
			groupCode : 'codeg_datasource_config.db_type'
		});
//方法
DatasourceConfig.query = function(){
	var params = DatasourceConfig.getParams("queryBar");
	DatasourceConfig.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", DatasourceConfig.store.proxy.reader.jsonData.message );
			}
		}
	});
};

DatasourceConfig.getParams = function (){
	var docks= DatasourceConfig.gridPanel.getDockedItems('toolbar[dock="top"]');
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

DatasourceConfig.reset = function(){
	var docks= DatasourceConfig.gridPanel.getDockedItems('toolbar[dock="top"]');
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

DatasourceConfig.save = function (){
	DatasourceConfig.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'datasourceConfig.ajax?method=saveDatasourceConfig',
		 success: function(form, action) {
			 DatasourceConfig.formWin.hide();
			 DatasourceConfig.store.reload({
				callback : function(r, option, success){
					if(success){
					var record = DatasourceConfig.store.getById(action.result.obj.id);
					if(record){
						DatasourceConfig.gridPanel.getSelectionModel( ).select(record);
					}
					}
				}
			 });
//			 DatasourceConfig.gridPanel.getSelectionModel( ).select(action.result.obj);
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

DatasourceConfig.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'datasourceConfig.ajax?method=deleteDatasourceConfig',
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

DatasourceConfig.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',	
		'url',	
		'dbType',	
		'userName',	
		'password',	
		'host'	
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "datasourceConfig.ajax?method=listDatasourceConfig",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
DatasourceConfig.store.load();

DatasourceConfig.gridPanel = Ext.create('Ext.grid.Panel',{	
	store:DatasourceConfig.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'主键',
		dataIndex:'id',
		hidden:true,
		width:550
	},	
	{
		text:'数据库url',
		dataIndex:'url',
		width:400
	},	
	{
	    renderer:function(v){return DatasourceConfig.dbTypeBox.getDictValue(v);},
		text:'数据库类型',
		dataIndex:'dbType',
		hidden:true,
		width:200
	},	
	{
		text:'数据库用户名',
		dataIndex:'userName',
		hidden:true,
		width:200
	},	
	{
		text:'数据库密码',
		dataIndex:'password',
		hidden:true,
		width:200
	},	
	{
		text:'用户端ip',
		dataIndex:'host',
		hidden:true,
		width:200
	},	
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[{iconCls : 'Applicationedit',tooltip : '修改',handler :  
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		DatasourceConfig.formWin.showUpdate(rec);},
		isDisabled: function( view, rowIdx, colIdx, item, record){
			return !record.data.host;
		}},
		'-',
		{iconCls:'delete',tooltip:'删除',handler:DatasourceConfig.del,
			isDisabled: function( view, rowIdx, colIdx, item, record){
				return !record.data.host;
			}
		} 
		       ]
     }],
	dockedItems:[/*{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'数据库url:',{ xtype:'textfield', name:'url|like'}
					,'-',
				 	'数据库类型:',new Sys.DictBox({name : 'dbType|eq',groupCode : 'codeg_datasource_config.db_type'})
		       ]
	},*/{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
//	        {text:'查询',handler:DatasourceConfig.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	DatasourceConfig.formWin.showAdd();},iconCls:'add'},'-',
//	        {text:'重置',iconCls:'Pagerefresh',handler:DatasourceConfig.reset}
	    ]
    },/*{
        xtype: 'pagingtoolbar',
        store: DatasourceConfig.store,  
        dock: 'bottom',
        displayInfo: true
    }*/],
    listeners:{
    	select:function( grid, record, index, eOpts ){
    		CodegTable.query(record);
    	}
    }
});
DatasourceConfig.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
         new Ext.form.Hidden({name:'id'}),
	     {fieldLabel:'数据库url',xtype:'textfield',name:'url',allowBlank:false,width:750,
          listeners:{
        	  'change':function( f, newValue, oldValue, eOpts ){
        		  if(newValue.indexOf("mysql") > 0 ){
        			  DatasourceConfig.dbTypeBox.setValue("mysql");
        		  }
        		  if(newValue.indexOf("oracle") > 0 ){
        			  DatasourceConfig.dbTypeBox.setValue("oracle");
        		  }
        	  }
          }
	     },        
		 DatasourceConfig.dbTypeBox,
	     {fieldLabel:'数据库用户名',xtype:'textfield',name:'userName',allowBlank:true},        
	     {fieldLabel:'数据库密码',xtype:'textfield',name:'password',allowBlank:true},        
       new Ext.form.Hidden({name:'_saveType'})
	]
});

	
	
DatasourceConfig.formWin = Ext.create('Ext.window.Window',{
	title:'数据库配置管理',
    layout: 'fit',
    items: DatasourceConfig.formPanel,
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
             {text:'提交',handler:DatasourceConfig.save},
             {text:'取消',handler:function(){DatasourceConfig.formWin.hide();}}
    ]
});
