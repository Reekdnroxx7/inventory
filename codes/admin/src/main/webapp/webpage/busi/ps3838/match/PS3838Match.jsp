<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx}/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/busi/sbobet/match/SbobetMatchDialog.js"></script>
<script type="text/javascript" src="${ctx}/webpage/busi/ps3838/match/PS3838Match.js"></script>
<head>
<title>ps3838_match管理</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [PS3838Match.gridPanel]
			});		
		});
</script>
</body>
</html>
