<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><body>
<script type="text/javascript">
    //判断如果当前页面不为主框架，则将主框架进行跳转
    var target_url = "<%=request.getContextPath()%>";
    target_url += "/index.do"

    window.location.href = target_url;

</script>
</body>
</html>