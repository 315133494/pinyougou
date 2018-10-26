<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018-10-23
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <title>二品优购</title>
</head>
<body>
欢迎来到二品优购
<%=request.getRemoteUser()%>
<br>
<a href="http://localhost:9100/cas/logout?service=http://www.baidu.com">退出登录</a>
</body>
</html>
