Ext.namespace("Materiel");
//字典配置
Materiel.unitBox = Ext.create("Sys.DictBox", {
			fieldLabel : '单位',
			name : 'unit',
			groupCode : 'bom_materiel_unit'
		});
//方法
Materiel.query = function(){
	Materiel.store.load({
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Materiel.store.proxy.reader.jsonData.message );
			}
		}
	});
};

Materiel.getParams = function (){
	var docks= Materiel.gridPanel.getDockedItems('toolbar[dock="top"]');
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

Materiel.reset = function(){
	var docks= Materiel.gridPanel.getDockedItems('toolbar[dock="top"]');
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

Materiel.save = function (){
	Materiel.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'materiel.ajax?method=saveMateriel',
		 success: function(form, action) {
			 Materiel.formWin.hide();
			 Materiel.store.reload();
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

Materiel.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'materiel.ajax?method=deleteMateriel',
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

Materiel.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'procedure_name',
		'material_code',
		'material_name',
		'quantity',
		'unit',
		'position',
		'create_date',
		'create_by',
		'update_date',
		'update_by',
		'remarks'
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "materiel.ajax?method=listMateriel",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
Materiel.store.on("beforeload",function (source, operation) {
	var params = Materiel.getParams("queryBar");
	operation.params = Ext.apply(operation.params || {}, params);
});

Materiel.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'物料管理',
	store:Materiel.store,
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
		text:'工序名称',
		dataIndex:'procedure_name',
		width:200
	},
	{
		text:'物料编号',
		dataIndex:'material_code',
		width:200
	},
	{
		text:'物料名称',
		dataIndex:'material_name',
		width:200
	},
	{
		text:'数量',
		dataIndex:'quantity',
		width:200
	},
	{
	    renderer:function(v){return Materiel.unitBox.getDictValue(v);},
		text:'单位',
		dataIndex:'unit',
		width:200
	},
	{
		text:'位置',
		dataIndex:'position',
		width:200
	},
	{
		text:'创建时间',
		dataIndex:'create_date',
		hidden:true,
		width:200
	},
	{
		text:'创建人',
		dataIndex:'create_by',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'update_date',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'update_by',
		hidden:true,
		width:200
	},
	{
		text:'备注',
		dataIndex:'remarks',
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
		Materiel.formWin.showUpdate(rec);}},
		'-',
		{iconCls:'delete',tooltip:'删除',handler:Materiel.del}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'工序名称:',{ xtype:'textfield', name:'procedure_name|like'}
					,'-',
					'物料编号:',{ xtype:'textfield', name:'material_code|like'}
					,'-',
					'物料名称:',{ xtype:'textfield', name:'material_name|like'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Materiel.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	Materiel.formWin.showAdd();},iconCls:'add'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:Materiel.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Materiel.store,
        dock: 'bottom',
        displayInfo: true
    }]
});
Materiel.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
	     {fieldLabel:'工序名称',xtype:'textfield',name:'procedure_name',allowBlank:false},
	     {fieldLabel:'物料编号',xtype:'textfield',name:'material_code',allowBlank:false},
	     {fieldLabel:'物料名称',xtype:'textfield',name:'material_name',allowBlank:false},
	     {fieldLabel:'数量',xtype:'numberfield',name:'quantity',allowBlank:false},
		 Materiel.unitBox,
	     {fieldLabel:'位置',xtype:'textfield',name:'position',allowBlank:false},
         new Ext.form.Hidden({name:'id'}),
       new Ext.form.Hidden({name:'_saveType'})
	]
});



Materiel.formWin = Ext.create('Ext.window.Window',{
	title:'物料管理',
    layout: 'fit',
    items: Materiel.formPanel,
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
             {text:'提交',handler:Materiel.save},
             {text:'取消',handler:function(){Materiel.formWin.hide();}}
    ]
});
