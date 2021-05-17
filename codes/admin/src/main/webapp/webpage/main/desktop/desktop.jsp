<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/context/mytags.jsp"%>
<%@include file="/WEB-INF/jsp/context/extjs.jsp"%>
<%@include file="/WEB-INF/jsp/context/system.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>jeecp</title>
		<link href="plug-in\common\css\icon.css" rel="stylesheet"
			type="text/css" />		
		<link rel="stylesheet" type="text/css"
			href="${ctx }/plug-in\extjs4.2\css\desktop.css" />
<script type="text/javascript" src="${ctx}/oa\system\dict\js\dict.js"></script>
<script type="text/javascript" src="${ctx}/oa\system\dict\js\dictGroup.js"></script> 
	</head>

	<body>
		<script type="text/javascript">
Ext.Loader.setPath( {
	'Ext.ux.desktop' : 'plug-in/extjs4.2/js',
	'MyDesktop' : 'webpage/main/desktop'
});
Ext.require('MyDesktop.App')
var myDesktopApp;
Ext.onReady(function() {
	myDesktopApp = new MyDesktop.App();
});
</script>

	</body>
</html>
