Ext.namespace("Team");
//字典配置
//方法
Team.query = function(){
	Team.store.load({
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Team.store.proxy.reader.jsonData.message );
			}
		}
	});
};

Team.getParams = function (){
	var docks= Team.gridPanel.getDockedItems('toolbar[dock="top"]');
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

Team.reset = function(){
	var docks= Team.gridPanel.getDockedItems('toolbar[dock="top"]');
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

Team.save = function (rec){

	Team.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'team.ajax?method=addTranslate',
		 success: function(form, action) {
			 rec.commit();
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

Team.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'team.ajax?method=deleteTeam',
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

Team.translate = function(){
	Ext.Ajax.request({
		url: 'team.ajax?method=translate',
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				Ext.Msg.alert("信息","翻译成功");
                Team.store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
}

Team.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'name',
		"translations",
		{ name : "zh_CN",convert: function(v,r){
			if(v){
				return v;
			}
			var v = r.get("translations");
			if(v){
				return v["zh-CN"];
			}
		}},
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "team.ajax?method=listTeam",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});

Team.store.on("beforeload",function (source, operation) {
    var params = Team.getParams();
    operation.params = Ext.apply(operation.params || {}, params);
});

Team.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'翻译',
	store:Team.store,
	rowLines:true,
	columnLines : true,
	// selModel  : new Ext.selection.CheckboxModel(),
	columns:[
	{
		text:'标准名称',
		dataIndex:'id',
		width:200
	},
	{
		text:'中文',
		dataIndex:'zh_CN',
		// renderer : function(value){
		// 	if(value ){
		// 		var v = value["zh-CN"];
		// 		if(v){
		// 			return v;
		// 		}
		// 	}
		// 	return "";
		// },
		width:200
	},
	{
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[
		{iconCls:'delete',tooltip:'删除',handler:Team.del}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'名称:',{ xtype:'textfield', name:'_id|like'}
                    ,'-',
					'未翻译:',{ xtype:'checkbox', name:'translate'}

		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Team.query,iconCls:'Magnifier'},'-',
	        {text:'翻译',handler:Team.translate,iconCls:'Transmit'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:Team.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Team.store,
        dock: 'bottom',
        displayInfo: true
    }],
	listeners : {
		"itemclick" :function( me, record, item, index, e, eOpts ){
			Team.formPanel.update(record);
		}
	}
});
Team.formPanel = Ext.create('Ext.form.Panel',{
	defaultAlign : 'south',
	buttonAlign : 'south',
	region : 'south',
	bodyPadding: 10,
	defaultType:'textfield',

	items:[
		{fieldLabel:'英文',xtype:'textfield',name:'name', readOnly :true,fieldStyle:'background:#E6E6E6'},
		{fieldLabel:'中文',xtype:'textareafield',id:'zh_CN',name:"value", anchor: '95%', listeners:{
			"change":function (me) {
				var record = Team.formPanel.record;
				if(record) {
					var value = me.getValue();
					record.data["translations"]["zh-CN"] = value;
					record.set("zh_CN",value);
				}
			},
			"blur": function( me, e, eOpts ){
				var record = Team.formPanel.record;
				if(record){
					if(record.dirty){
						Team.save(record);
					}
				}
			}
		}},
		new Ext.form.Hidden({name:"id"}),
		new Ext.form.Hidden({name:"locale",value:"zh-CN"}),
        new Ext.form.Hidden({name:'_saveType'})
	],
	update:function(record){
		this.record = record;
		this.getForm().loadRecord(record);
    }
});


var centerPanel = new Ext.create('Ext.panel.Panel', {
	region : 'center',
	xtype : 'panel',
	autoScroll : true,
	items : Team.gridPanel
});


// Team.formWin = Ext.create('Ext.window.Window',{
// 	title:'翻译',
//     layout: 'fit',
//     items: Team.formPanel,
//     closeAction : 'hide',
//     showAdd: function(){
//     	var form = this.child('form');
//     	form.getForm().reset();
// 		form.getForm().findField("_saveType").setValue("add");
//     	this.show();
//     },
//     showUpdate:function(record){
//     	var form = this.child('form');
// 		form.getForm().loadRecord(record);
// 	    form.getForm().findField("_saveType").setValue("update");
//     	this.show();
//     },
//     buttons:[
//              {text:'提交',handler:Team.save},
//              {text:'取消',handler:function(){Team.formWin.hide();}}
//     ]
// });
