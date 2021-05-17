Icon.save = function(){
	Icon.detailPanel.getForm().submit({
		 clientValidation: true,
		 url: 'icon.ajax?method=save',
		 success: function(form, action) {
			 Icon.detailWin.hide();
			 Icon.store.reload();
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
}
Icon.cssDetailField = new Ext.form.field.Text({
	fieldLabel:'图标样式',
	id:'css',
	allowBlank : false,
	name:'css'
});

Icon.nameDetailField = new Ext.form.field.Text({
	fieldLabel:'图标名称',
	id:'name',
	allowBlank : false,
	name:'name'
});
Icon.pathDetailField = new Ext.form.field.File({
	id:'filepath',
	name:'filepath',
	allowBlank : false,
	buttonText:'选择图片'
});

Icon._saveType = new Ext.form.Hidden({name:'_saveType',id:'saveType',value:'add'});


Icon.detailPanel = Ext.create('Ext.form.Panel', {
    bodyPadding: 10,
    items: [Icon.cssDetailField,Icon.nameDetailField,Icon.pathDetailField,Icon._saveType]
});

Icon.detailWin = Ext.create('Ext.window.Window', {
	title:'图标管理',
    layout: 'fit',
    items: Icon.detailPanel,
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
    buttons:[{text:'提交',handler:Icon.save}]
    
});



