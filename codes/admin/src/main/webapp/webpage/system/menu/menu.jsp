<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<link href="${ctx }/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/system/menu/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/webpage/system/menu/js/menuDetail.js"></script>
<script type="text/javascript" src="${ctx}/webpage/system/menu/js/menuOperation.js"></script>
<head>
<title>菜单信息</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [Menu.menu_panel]
			});		
		});
</script>
</body>
</html>
