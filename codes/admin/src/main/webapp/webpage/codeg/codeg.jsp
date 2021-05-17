<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx}/plug-in\common\css\icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/oa/system\codeg\js\codegTable.js"></script>
<script type="text/javascript" src="${ctx}/oa/system\codeg\js\codegField.js"></script>
<head>
<title>代码生成工具</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [CodegTable.gridPanel]
			});		
		});
</script>
</body>
</html>
