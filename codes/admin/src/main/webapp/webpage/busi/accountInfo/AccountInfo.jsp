<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx}/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/busi/accountInfo/AccountInfo.js"></script>
<head>
<title>spinage_account_info管理</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [AccountInfo.gridPanel]
			});
			AccountInfo.store.load();
		});
</script>
</body>
</html>
