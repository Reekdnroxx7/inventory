Ext.namespace("${entityName}");
<#list pageColumns as pc>
<#if pc.dictGroupCode?exists && pc.dictGroupCode?length !=0 >
${entityName}.${pc.fieldName}Box = Ext.create("Sys.DictBox", {
			groupCode : '${pc.dictGroupCode}'
		});
	<#else>
    </#if>
</#list>
${entityName}.query = function(){
	var params = ${entityName}.getParams("queryBar");
	${entityName}.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", ${entityName}.store.proxy.reader.jsonData.message );
			}
		}
	});
}

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
					params[name]=value;
				}
			}	
		);
	}	
	return params;
}
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
<#list pageColumns as pc>
<#if pc.dbType=93>
${entityName}.formatDate=function(v) {
　　　　　　　　if(v == null)
　　　　　　　　{
　　　　　　　　　　return null;
　　　　　　　　}
         var d = new Date();
        　　　　var str = v.toString();
        　　　　var str1 = str.replace("/Date(", "");
        　　　　var str2 = str1.replace(")/", "");
        　　　　var dd = parseInt(str2);
        　　　　d.setTime(dd);
         return Ext.util.Format.date(d,'Y-m-d');
    　　};
<#break>
</#if>
</#list>
${entityName}.store = Ext.create('Ext.data.Store',{
	fields:[
	<#list pageColumns as pc>
	<#if pc_has_next>
	<#if pc.dbType=93>{ name: '${pc.fieldName}',convert: ${entityName}.formatDate },<#else>'${pc.fieldName}',</#if>
    <#else><#if pc.dbType=93> { name: '${pc.fieldName}',convert: ${entityName}.formatDate }<#else>'${pc.fieldName}'</#if>
    </#if>
	</#list>
	],
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
${entityName}.store.load();

${entityName}.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'${description}',
	store:${entityName}.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
		<#list pageColumns as pc>
	{
<#if pc.dictGroupCode?exists && pc.dictGroupCode?length !=0 >
	    renderer:function(v){return ${entityName}.${pc.fieldName}Box.getDictValue(v)},
	    <#else>
	    </#if>
		text:'${pc.displayName}',
		dataIndex:'${pc.fieldName}',
		width:200
	},
	</#list>
	{  
		xtype:'actioncolumn',
		text : '操作',
		align: 'center',
		items:[{iconCls : 'Applicationedit',altText : '修改',handler :  
		function(grid, rowIndex, colIndex) {
		var rec = grid.getStore().getAt(rowIndex);
		${entityName}.formWin.showUpdate(rec)}},
		'-',
		{iconCls:'delete',altText:'删除',handler:${entityName}.del} 
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
			<#list queryColumns as qc>
				<#if qc_has_next>
				<#if qc.dbType=93>
				'${qc.displayName}:',{ xtype:'datefield',format:'Y-m-d', name:'${qc.fieldName}|${qc.queryType}'},
				<#else>'${qc.displayName}:',{ xtype:'textfield', name:'${qc.fieldName}|${qc.queryType}'},
				</#if>				
				<#else>
				<#if qc.dbType=93>
				'${qc.displayName}:',{ xtype:'datefield',format:'Y-m-d' ,name:'${qc.fieldName}|${qc.queryType}'}
				<#else>'${qc.displayName}:',{ xtype:'textfield', name:'${qc.fieldName}|${qc.queryType}'}
				</#if>
				</#if>
	        </#list>
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:${entityName}.query,iconCls:'Magnifier'},
	        {text:'添加',handler:function(){	${entityName}.formWin.showAdd()},iconCls:'add'}
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
		<#list pageColumns as pc>
		<#if pc.fieldName !="id" 
	    && pc.fieldName !="remarks"	
	    && pc.fieldName !="createBy"
	    && pc.fieldName !="createDate"
	    && pc.fieldName !="updateBy"
	   && pc.fieldName !="updateDate"
	    >
	    <#if pc.dbType=93>
	     {fieldLabel:'${pc.displayName}',name:'${pc.fieldName}',xtype:'datefield',format:'Y-m-d',allowBlank:false},
         <#else> {fieldLabel:'${pc.displayName}',name:'${pc.fieldName}',allowBlank:false},
           </#if>
        </#if>
        </#list>
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
