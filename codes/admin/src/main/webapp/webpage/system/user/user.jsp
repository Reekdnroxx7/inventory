<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx }/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/system/user/js/user.js"></script>
<script type="text/javascript" src="${ctx}/webpage/system/user/js/userRole.js"></script>
<!-- <script type="text/javascript" src="${adminPath}/system/user/js/userDetail.js"></script> -->
<head>
<title>人员信息</title>
</head>
<body>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [User.gridPanel]
			});		
		});
</script>
</body>
</html>
