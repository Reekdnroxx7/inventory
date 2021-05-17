<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx}/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/codeg/datasourceConfig/codegTable.js"></script>
<script type="text/javascript" src="${ctx}/webpage/codeg/datasourceConfig/codegField.js"></script>
<script type="text/javascript" src="${ctx}/webpage/codeg/datasourceConfig/DatasourceConfig.js"></script>

<head>
</head>
<body>
	<script type="text/javascript">
	
	getGeneratorUrl=function(){
	   return "mybatiscodeg.ajax?method=codeGenerate";
	}
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var westPanel = new Ext.create('Ext.panel.Panel', {
			            title:'常用数据库',
			            id : westPanel,
			            region : 'west',
			            width : 600,
			            minSize : 175,
			            maxSize : 600,
			            xtype : 'panel',
			            margins : '0 0 5 5',
			            split : true,
			            autoScroll : true,
			            animCollapse : true,
			            animate : false,
			            items : DatasourceConfig.gridPanel,
			            collapsible : true
              });
               
               
            var centerPanel = new Ext.create('Ext.panel.Panel', {
                        id : centerPanel,
                        region : 'center',
                        margins : '0 0 5 5',
                        split : true,
                        autoScroll : true,
                        animCollapse : false,
                        animate : false,
                        collapseMode : 'mini',
                        items : CodegTable.gridPanel,
                        collapsible : false
              });                     
                                
			var viewport = new Ext.Viewport({
				layout: 'border',
				items : [westPanel,centerPanel]
			});	
				
		});
</script>
</body>
</html>
