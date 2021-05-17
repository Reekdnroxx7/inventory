<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><body>
<script type="text/javascript">
	    //判断如果当前页面不为主框架，则将主框架进行跳转
	  	var tagert_URL = "<%=request.getContextPath()%>";
	  	tagert_URL += "/_login.do"
	    if(self==top){
	    	window.location.href = tagert_URL;
	    }else{
	    	top.location.href = tagert_URL;
	    }
 </script>
</body>
</html>