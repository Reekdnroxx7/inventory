Ext.namespace("UserRole");
UserRole.roleDict = new Sys.DictBox({groupCode:'sys_user_role.role_type'});
UserRole.query = function(){
	var record = UserRole.gridWin.getRecord();
	if(record){
		var params = {};
		UserRole.store.load({
			params: record.data
		});
	}
};
UserRole.save = function (){
	var records =  UserRole.store.getModifiedRecords();
	if(records.length == 0){
		UserRole.gridWin.hide();
		return ;
	}
	var roles = [];
	for(var i = 0 ;i < records.length;i++){
		roles[i] = Ext.JSON.encode(records[i].data);
	}
	params = UserRole.gridWin.getRecord().data;
	params.roles = roles;
	Ext.Ajax.request({
	    url: 'user.ajax?method=saveUserRoles',
	    params:params,
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	UserRole.gridWin.hide();
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
};

UserRole.store = Ext.create("Ext.data.Store",{
	fields:['userRoleId','roleName','roleId','auth','roleType'],
	pageSize : 0,
	proxy: {
    	type: 'ajax',
        url : "user.ajax?method=listUserRole",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    },
    sorters:[{property:'roleId'}]
});

UserRole.gridPanel = Ext.create('Ext.grid.Panel',{
	width:400,
	height:300,
	store:UserRole.store,
	rowLines:true,
	selmodel : Ext.create('Ext.selection.CheckboxModel'),
	columnLines : true,
	columns:[
		{
			xtype : 'checkcolumn',
			text:'授权',
			dataIndex:'auth',
			editor:new 	Ext.form.field.Checkbox()
		},
	 {
		text:'授权',
		dataIndex:'roleType',
		width: 150,
		renderer:function(v){return UserRole.roleDict.getDictValue(v)},
		editor: new Sys.DictBox({groupCode:'sys_user_role.role_type',store:UserRole.roleDict.getStore()})
	},{
		text:'角色名称',
		dataIndex:'roleName',
		flex:1
		
	}],
	plugins: [Ext.create('Ext.grid.plugin.CellEditing', { 
		clicksToEdit: 1 
		})]
});

UserRole.gridWin = Ext.create('Ext.window.Window',{
	title:'用户角色',
    layout: 'fit',
    items: UserRole.gridPanel,
    closeAction : 'hide',
    showRecord : function(record){
    	this.record = record;
    	this.show();
    	UserRole.query();
    },
	getRecord: function(){
		return this.record;
	},
	buttons:[{text:'确定',handler:UserRole.save},
	         {text:'取消',handler:function(){UserRole.gridWin.hide()}}]
});





