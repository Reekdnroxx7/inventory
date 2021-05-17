<%@ page language="java"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>

<!DOCTYPE html>
<html>
<script type="text/javascript">

</script>
	<link href="${ctx }/plug-in/common/css/icon.css" rel="stylesheet"
		type="text/css" />
	<script type="text/javascript" src="${ctx}/webpage/system/org/js/orgLeft.js">
</script>
	<script type="text/javascript" src="${ctx}/webpage/system/org/js/orgCenter.js">
</script>
	<head>
		<title>机构</title>
	</head>
	<body>
		<script type="text/javascript">
Ext.onReady(function() {
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [ westPanel, centerPanel ]
	});
});

</script>
	</body>
</html>
