Ext.namespace("${entityName}");
//字典配置
<#list fields as field>
<#if field.dictGroupCode?exists && field.dictGroupCode?length !=0 >
${entityName}.${field.javaFieldName}Box = Ext.create("Sys.DictBox", {
			fieldLabel : '${field.displayName}',
			name : '${field.javaFieldName}',
			groupCode : '${field.dictGroupCode}'
		});
</#if>
</#list>
//方法
${entityName}.query = function(){

	${entityName}.store.load({
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", ${entityName}.store.proxy.reader.jsonData.message );
			}
		}
	});
};

${entityName}.getParams = function (){
	var docks= ${entityName}.gridPanel.getDockedItems('toolbar[dock="top"]');
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

${entityName}.reset = function(){
	var docks= ${entityName}.gridPanel.getDockedItems('toolbar[dock="top"]');
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

${entityName}.save = function (){
	${entityName}.formPanel.getForm().submit({
		 clientValidation: true,
		 url: '${entityName?uncap_first}.ajax?method=save${entityName}',
		 success: function(form, action) {
			 ${entityName}.formWin.hide();
			 ${entityName}.store.reload();
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

${entityName}.del = function (grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var store = grid.getStore();
			var rec = store.getAt(rowIndex);
			Ext.Ajax.request({
			    url: '${entityName?uncap_first}.ajax?method=delete${entityName}',
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

${entityName}.store = Ext.create('Ext.data.Store',{
	fields:[
	<#list fields as field>
		'${field.javaFieldName}'<#if field_has_next>,</#if>
	</#list>
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "${entityName?uncap_first}.ajax?method=list${entityName}",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
${entityName}.store.on("beforeload",function (source, operation) {
	var params = ${entityName}.getParams("queryBar");
	operation.params = Ext.apply(operation.params || {}, params);
});

${entityName}.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'${description}',
	store:${entityName}.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	<#list fields as field>
	{
		<#if field.dictGroupCode?exists && field.dictGroupCode?length !=0 >
	    renderer:function(v){return ${entityName}.${field.javaFieldName}Box.getDictValue(v);},
	    </#if>
		text:'${field.displayName}',
		dataIndex:'${field.javaFieldName}',
		<#if field.showType = 1>
		hidden:true,
		</#if>
		width:200
	},
	</#list>
	{
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[{iconCls : 'Applicationedit',tooltip : '修改',handler :
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		${entityName}.formWin.showUpdate(rec);}},
		'-',
		{iconCls:'delete',tooltip:'删除',handler:${entityName}.del}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
			<#assign first = 'true'/>
			<#list fields as field>
				<#if field.queryType !='no'>
				<#if first != 'true'>
					,'-',
				</#if>
				 <#if field.xtype == 'dictcombobox'>
				 	'${field.displayName}:',new Sys.DictBox({name : '${field.javaFieldName}|eq',groupCode : '${field.dictGroupCode}'})
				 <#else>
				 	<#if field.queryType == 'between'>
				 	'${field.displayName}:',{ xtype:'${field.xtype}',<#if field.xtype == 'datefield'>format:'Ymd',</#if>name:'${field.javaFieldName}|ge'},'至',{ xtype:'${field.xtype}',<#if field.xtype == 'datefield'>format:'Ymd',</#if> name:'${field.javaFieldName}|le'}
				 	<#else>
					'${field.displayName}:',{ xtype:'${field.xtype}',<#if field.xtype == 'datefield'>format:'Ymd',</#if> name:'${field.javaFieldName}|${field.queryType}'}
				 	</#if>
				 </#if>
	       		<#assign first = 'false'/>
	       		</#if>
	        </#list>
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:${entityName}.query,iconCls:'Magnifier'},'-',
	        {text:'添加',handler:function(){	${entityName}.formWin.showAdd();},iconCls:'add'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:${entityName}.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: ${entityName}.store,
        dock: 'bottom',
        displayInfo: true
    }]
});
${entityName}.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
		<#list fields as field>
		<#if field.editable>
		 <#if field.xtype == 'dictcombobox'>
		 ${entityName}.${field.javaFieldName}Box,
		 <#else>
	     {fieldLabel:'${field.displayName}',xtype:'${field.xtype}',<#if field.xtype == 'datefield'>format:'Ymd',</#if>name:'${field.javaFieldName}',allowBlank:false},
		 </#if>
        </#if>
        </#list>
       <#if primaryKey??>
            <#if primaryKey.editable>
            <#else>
         new Ext.form.Hidden({name:'${primaryKey.javaFieldName}'}),
            </#if>
       </#if>
       new Ext.form.Hidden({name:'_saveType'})
	]
});



${entityName}.formWin = Ext.create('Ext.window.Window',{
	title:'${description}',
    layout: 'fit',
    items: ${entityName}.formPanel,
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
             {text:'提交',handler:${entityName}.save},
             {text:'取消',handler:function(){${entityName}.formWin.hide();}}
    ]
});
