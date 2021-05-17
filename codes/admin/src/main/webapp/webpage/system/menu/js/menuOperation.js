Ext.namespace("Operation");
Operation.store = new Ext.data.Store({
	fields: ['transcode','operationCode','operationName'],
	pageSize : -1,
	proxy: {
		type: 'ajax',
	    url : "menu.ajax?method=listOperation",
	    reader: {
	        type: 'json',
	        root: 'resultList',
	        totalProperty: 'totalCount'
	    }
	}
});
Operation.transCodeStore = new Ext.data.Store({
	fields: ['key']
});
Operation.transCodeBox = Ext.create('Ext.form.ComboBox', {
    fieldLabel: '请求类',
    name:'transcode',
    store: Operation.transCodeStore,
    queryMode: 'local',
    displayField: 'key',
    valueField: 'key'
});
Operation.gridPanel = Ext.create("Ext.grid.Panel",{
	store:Operation.store,
	width:400,
	height:300,
	rowLines:true,
	loadMask : false,
 	columnLines :true,
	columns:[{xtype: 'rownumberer'},
	         {text:'访问类',dataIndex:'transcode'},
	         {text:'方法名',dataIndex:'operationCode'},
	         {text:'操作名称',dataIndex:'operationName'},
	         {
	        	 text:'操作',
	        	 xtype:'actioncolumn',
	        	 flex:1,
	        	 align: 'center',
	        	 items:[{
	        		 iconCls:'Delete',
	        		 altText:'删除',
	        		 handler:function(grid, rowIndex, colIndex){
	        			 var rec = grid.getStore().getAt(rowIndex);
	        			 Operation.delRecord(rec);
	        		 }
	        	 }]
	         }]
});

Operation.win = Ext.create('Ext.window.Window', {
	title:'权限管理',
    items: Operation.gridPanel, 
    modal:true,
    
    closeAction : 'hide',
    buttons:[{text:'添加',iconCls:'add',handler:function(){
    	Operation.detailWin.show();
//    	Operation.detailPanel.getForm().reset();
    }}],
    showMenu:function(menu){
    	this.show();
    	this.menu = menu;
    	var params = {};
    	params.menuId=  menu.data.id;
    	var split=menu.data.transcode.split(",");
    	var data = [];
    	for(var i=0; i< split.length;i++){
    		data[i] = {key:split[i]};
    	}
    	Operation.transCodeStore.loadData(data);
    	Operation.transCodeBox.setValue(split[0]);
    	Operation.store.load({params:params});
    },
	getMenu:function(){
		return this.menu;
	}
});

Operation.detailPanel = Ext.create('Ext.form.Panel', {
    bodyPadding: 10,
    defaultType: 'textfield',
    items: [Operation.transCodeBox,{
        fieldLabel: '方法名',
        name: 'operationCode',
        allowBlank: false
    },{
        fieldLabel: '操作名称',
        name: 'operationName',
        allowBlank: false
    },
    new Ext.form.Hidden({name:'_saveType',value:'add'})]
});

Operation.save = function(){
	var params = {};
	params.menuId=Operation.win.getMenu().data.id;
	var form = Operation.detailPanel.getForm();
	form.submit({
		 clientValidation: true,
		 url: 'menu.ajax?method=saveOperation',
		 params:params,
		 success: function(form, action) {
			 Operation.detailWin.hide();
			 Operation.store.reload();
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

Operation.delRecord = function(rec){
	Ext.Ajax.request({
	    url: 'menu.ajax?method=deleteOperation',
	    params:rec.data,
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	Ext.Msg.alert("信息","删除成功");
	        	Operation.store.remove(rec);
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
}

Operation.detailWin = Ext.create('Ext.window.Window', {
	title:'菜单管理',
    layout: 'fit',
    modal:true,
    items: Operation.detailPanel,
    closeAction : 'hide',
    buttons:[{text:'提交', handler:Operation.save }]
});








