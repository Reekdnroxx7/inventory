Menu.save=function(){
	Menu.detailPanel.getForm().submit({
		 clientValidation: true,
		 url: 'menu.ajax?method=saveMenu',
		 success: function(form, action) {
		      Ext.Msg.alert("信息","操作成功");
		      if(Menu._saveType.getValue() == 'add'){
		    	  var menu = action.result.obj;
		    	  var rec = Ext.create('Sys.Menu',menu);
		    	  Menu.detailPanel.getParent().appendChild(rec);
		      }else{
		    	  Menu.detailPanel.getForm().updateRecord(); //修改后数据信息同步
		      }	
		      Menu.detailWin.hide();
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

Menu.parentName = new Ext.form.field.Text({
	fieldLabel:'父菜单:',
	width:450,
	readOnly:true,
	fieldStyle:'background:#E6E6E6',
	name:'parentName'
});
Menu.parentId = new Ext.form.field.Text({
	fieldLabel:'父菜单:',
	width:450,
	readOnly:true,
	hidden:true,
	fieldStyle:'background:#E6E6E6',
	name:'parentId'
});
Menu.nameField = new Ext.form.field.Text({
	fieldLabel:'菜单名称:',
	width:450,
	allowBlank : false,
	name:'name'
});

Menu.type = new Ext.form.RadioGroup({
	 fieldLabel : "菜单类型",
	 horizontal:true, 
	 vertical: true,
	 width:350,
	 anchor:'100%',
	 items:[{boxLabel:'功能菜单',inputValue:'0',name:'type',checked:'true'},
	        {boxLabel:'父菜单',name:'type',inputValue:'1'}],
    listeners:{
    	'change': function(t, newValue, oldValue, eOpts){
    		if(newValue.type==1){ 
    			Menu.transcode.hide();
    			Menu.target.hide();
    		}else{
    			Menu.transcode.show();
    			Menu.target.show();
    		}
    	}
    }
});

Menu.transcode = new Ext.form.field.Text({
	fieldLabel:'可访问类（多个类以逗号分隔）',
	width:450,
	name:'transcode'
});

Menu.target = new Ext.form.field.Text({
	fieldLabel:'目的地址:',
	width:450,
	name:'target'
});

Menu.icon = new Sys.IconBox({
	fieldLabel:'菜单图标:',
	name:'icon'
});

Menu.sort = new Ext.form.field.Number({
	fieldLabel:'菜单排序',
	value:30,
	name:'sort'
});

Menu.isShow = new Ext.form.RadioGroup({
	 fieldLabel : "是否显示",
	 width:350,
	 anchor:'95%',
	 items:[{boxLabel:'是',inputValue:'1',name:'isShow',checked:'true'},
	        {boxLabel:'否',name:'isShow',inputValue:'0'}]
});

Menu._saveType = new Ext.form.Hidden({name:'_saveType',id:'saveType',value:'add'});


Menu.detailPanel = Ext.create('Ext.form.Panel', {
    bodyPadding: 10,
    defaultAlign:'center',
    buttonAlign: 'center',
    region: 'center',
    items: [Menu.parentName,Menu.parentId,Menu.nameField,Menu.type,Menu.transcode,
            Menu.target,Menu.icon,Menu.sort,Menu.isShow,Menu._saveType,
            new Ext.form.Hidden({name:'parentIds'})  
    		,new Ext.form.Hidden({name:'id'})
            ],   
    showEdit:function(record){
    	this.loadRecord(record);
    	var parentRec = Menu.treeStore.getById(record.data.parentId);
    	this.setParent(parent);
    	if(parentRec){
    		Menu.parentName.setValue(parentRec.data.name);
    	};
    	Menu._saveType.setValue('update');
    },
    showAdd:function(parent){
    	this.getForm().reset();
    	this.setParent(parent);
    	Menu.parentName.setValue(parent.data.name);
    	Menu.parentId.setValue(parent.data.id);
    	Menu._saveType.setValue('add');
    },
    setParent:function(record){
    	this.parent = record;
    },
    getParent:function(){
    	return this.parent ;
    }
});


Menu.detailWin = Ext.create('Ext.window.Window', {
	title:'菜单管理',
    layout: 'fit',
    items: Menu.detailPanel,
    closeAction : 'hide',    
    showAdd:function(parent){
    	Menu.detailPanel.showAdd(parent);
    	this.show();
    },
    showEdit:function(record){
    	Menu.detailPanel.showEdit(record);
    	this.show();
    },
    buttons:[{text:'提交',handler:Menu.save},{text:'取消',handler:function(){Menu.detailWin.hide();}}]    
})









