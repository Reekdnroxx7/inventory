Ext.namespace("AccountInfo");
//字典配置
AccountInfo.platformBox = Ext.create("Sys.DictBox", {
			fieldLabel : '平台',
			name : 'platform',
			groupCode : 'SPINAGE_PLATFORM'
		});
//方法
AccountInfo.query = function(){
	AccountInfo.store.load({
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", AccountInfo.store.proxy.reader.jsonData.message );
			}
		}
	});
};

AccountInfo.getParams = function (){
	var docks= AccountInfo.gridPanel.getDockedItems('toolbar[dock="top"]');
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

AccountInfo.reset = function(){
	var docks= AccountInfo.gridPanel.getDockedItems('toolbar[dock="top"]');
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

AccountInfo.save = function (){
	AccountInfo.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'accountInfo.ajax?method=saveAccountInfo',
		 success: function(form, action) {
			 AccountInfo.formWin.hide();
			 AccountInfo.store.reload();
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

AccountInfo.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'accountInfo.ajax?method=deleteAccountInfo',
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

AccountInfo.getAvailableBalance = function (rec){
			Ext.Ajax.request({
				url: 'accountInfo.ajax?method=getAvailableBalance',
				params:rec.data,
				success: function(response){
					var text = response.responseText;
					var result = Ext.JSON.decode(text);
					if(result.success){
						rec.set("info",result.data.info);
						rec.set("balance",result.data.balance);
						Ext.Msg.alert("信息","刷新成功");
					}else{
						Ext.Msg.alert("错误",result.message);
					}
				}
			});
};

AccountInfo.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'platform',
		'name',
		'password',
		'tag',
		"balance",
		"info"
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "accountInfo.ajax?method=listAccountInfo",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});


AccountInfo.store.on("beforeload",function (source, operation) {
	var params = AccountInfo.getParams();
	operation.params = Ext.apply(operation.params || {}, params);
});

AccountInfo.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'账户管理',
	store:AccountInfo.store,
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
	    renderer:function(v){return AccountInfo.platformBox.getDictValue(v);},
		text:'平台',
		dataIndex:'platform',
		width:200
	},
	{
		text:'用户名',
		dataIndex:'name',
		width:200
	},
	{
		text:'密码',
		dataIndex:'password',
		width:200
	},
		{
			text:'信用',
			dataIndex:'balance',
			width:200
		},
	{
		text:'标签',
		dataIndex:'tag',
		// renderer : function(value){
		// 	va
		// },
		width:200
	},
	{
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[
			{iconCls : 'Pagerefresh',tooltip : '刷新信用',handler :
				function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					AccountInfo.getAvailableBalance(rec);}},
			'-',
			{iconCls : 'Applicationedit',tooltip : '修改',handler :
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		AccountInfo.formWin.showUpdate(rec);}},
		'-',
		{iconCls:'delete',tooltip:'删除',handler:AccountInfo.del}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'平台:',{ xtype:'textfield', name:'platform|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:AccountInfo.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	AccountInfo.formWin.showAdd();},iconCls:'add'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:AccountInfo.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: AccountInfo.store,
        dock: 'bottom',
        displayInfo: true
    }]
});
AccountInfo.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
		AccountInfo.platformBox,
	     {fieldLabel:'用户名',xtype:'textfield',name:'name',allowBlank:false},
	     {fieldLabel:'密码',xtype:'textfield',name:'password',allowBlank:false},
	     {fieldLabel:'标签',xtype:'textfield',name:'tag',allowBlank:false},
         new Ext.form.Hidden({name:'id'}),
       new Ext.form.Hidden({name:'_saveType'})
	]
});



AccountInfo.formWin = Ext.create('Ext.window.Window',{
	title:'账户管理',
    layout: 'fit',
    items: AccountInfo.formPanel,
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
             {text:'提交',handler:AccountInfo.save},
             {text:'取消',handler:function(){AccountInfo.formWin.hide();}}
    ]
});
