<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<!DOCTYPE html>
<html>
<link href="${ctx}/plug-in/common/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/webpage/busi/config/config.js"></script>
<head>
<title>订单</title>
</head>
<body>
	<script type="text/javascript">

		Ext.onReady(function() {
			Ext.tip.QuickTipManager.init();
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [Config.formPanel]
			});

			Config.load();


// .each(
////					function(item,index){
////						var name = item['name'];

////					}
//			);




		});
</script>
</body>
</html>
