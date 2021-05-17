<%@page import= "org.apache.commons.lang.StringUtils"%>
<%
    String indexType="ext-all-neptune.css";
	Cookie[] cookies = request.getCookies();
	for (Cookie cookie : cookies) {
		if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
			continue;
		}
		if (cookie.getName().equalsIgnoreCase("indexType")) {
			indexType = cookie.getValue();
		}
	}
%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/plug-in/extjs4.2/resources/css/<%=indexType%>">
<script type="text/javascript"
	src="${ctx}/plug-in/extjs4.2/bootstrap.js">
</script>
<script type="text/javascript"
	src="${ctx}/plug-in/extjs4.2/ux/TreePicker.js">
</script>
<script type="text/javascript"
	src="${ctx}/plug-in/extjs4.2/locale/ext-lang-zh_CN.js">
</script>
