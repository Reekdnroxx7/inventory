Ext.namespace("League");
//字典配置
//方法
League.query = function(){
	// var params = League.getParams("queryBar");
	League.store.load({
		// params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", League.store.proxy.reader.jsonData.message );
			}
		}
	});
};

League.getParams = function (){
	var docks= League.gridPanel.getDockedItems('toolbar[dock="top"]');
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

League.reset = function(){
	var docks= League.gridPanel.getDockedItems('toolbar[dock="top"]');
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

League.save = function (data){
	Ext.Ajax.request({
		 url: 'league.ajax?method=saveLeague',
		params:data,
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				Ext.Msg.alert("信息","操作成功");
				League.store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
};

League.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			Ext.Ajax.request({
			    url: 'league.ajax?method=deleteLeague',
			    params:rec.data,
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.JSON.decode(text);
			        if(result.success){
			        	Ext.Msg.alert("信息","删除成功");
						League.store.reload();
			        }else{
			        	Ext.Msg.alert("错误",result.message);
			        }
			    }
			});
		}
	});
};

League.setAllStatus = function (flag){

			Ext.Ajax.request({
				url: 'league.ajax?method=setAllStatus',
				params:{flag:flag},
				success: function(response){
					var text = response.responseText;
					var result = Ext.JSON.decode(text);
					if(result.success){
						Ext.Msg.alert("信息","操作成功");
						League.store.reload();
					}else{
						Ext.Msg.alert("错误",result.message);
					}
				}
			});
};


League.refresh = function (flag){

	Ext.Ajax.request({
		url: 'league.ajax?method=refreshLeague',
		params:{flag:flag},
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				Ext.Msg.alert("信息","操作成功");
				League.store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
};


League.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'league_name',
		'league_cn_name',
		'regionid',
		'region_name',
		'region_cn_name',
		'disable'
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "league.ajax?method=listLeague",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});

League.store.on("beforeload",function (source, operation) {
    var params = League.getParams("queryBar");
    operation.params = Ext.apply(operation.params || {}, params);
});


League.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'league管理',
	store:League.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'主键',
		dataIndex:'id',
		hidden:false,
		width:200
	},
	{
		text:'英文',
		dataIndex:'league_name',
		width:300
	},
	{
		text:'中文',
		dataIndex:'league_cn_name',
		width:400
	},
	{
		text:'',
		dataIndex:'regionid',
		hidden:true,
		width:200

	},
	{
		text:'',
		dataIndex:'region_name',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'region_cn_name',
		hidden:true,
		width:200
	},
	{
		text:'禁用',
		dataIndex:'disable',
		width:200,
		renderer : function (val) {
			if(val){
				return "是";
			}
			return "否";
		}
	},
	{
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[ {iconCls:'Controlstartblue',tooltip:'启用',handler:function(grid, rowIndex, colIndex){
			var rec = grid.getStore().getAt(rowIndex);
			rec.data.disable = false;
			League.save(rec.data);
		},isDisabled : function(v,r,c,i,record){
			return !record.data.disable ;
		}},'-',
			{iconCls:'Controlpause',tooltip:'禁用',handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				rec.data.disable = true;
				League.save(rec.data);
			},isDisabled : function(v,r,c,i,record){
				return record.data.disable;
			}}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
            'ID/名称:', {
                xtype: 'textfield', name: 'pattern', enableKeyEvents: true, listeners: {
                    'specialkey': function (field, e) {
                        if (e.getKey() == Ext.EventObject.ENTER) {
                            League.query();
                        }
                    }
                }
            }
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:League.query,iconCls:'Magnifier'},'-',
	        {text:'刷新',iconCls:'Pagerefresh',handler:League.refresh},
			{
				text: '全部禁用', iconCls: 'Controlpause', handler: function () {
				League.setAllStatus(true);
			}
			},
			{text:'全部启用',iconCls:'Controlstartblue',handler:function (){
				League.setAllStatus(false);
			}
			}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: League.store,
        dock: 'bottom',
        displayInfo: true
    }]
});
League.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
	     {fieldLabel:'',xtype:'textfield',name:'league_name',allowBlank:false},
	     {fieldLabel:'',xtype:'textfield',name:'league_cn_name',allowBlank:false},
	     {fieldLabel:'',xtype:'textfield',name:'regionid',allowBlank:false},
	     {fieldLabel:'',xtype:'textfield',name:'region_name',allowBlank:false},
	     {fieldLabel:'',xtype:'textfield',name:'region_cn_name',allowBlank:false},
	     {fieldLabel:'',xtype:'textfield',name:'disable',allowBlank:false},
         new Ext.form.Hidden({id:'_saveType'}),
       new Ext.form.Hidden({name:'_saveType'})
	]
});



League.formWin = Ext.create('Ext.window.Window',{
	title:'league管理',
    layout: 'fit',
    items: League.formPanel,
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
             {text:'提交',handler:League.save},
             {text:'取消',handler:function(){League.formWin.hide();}}
    ]
});
