<%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/6
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<a href="/web01/cookie/generateCookie">从服务端生成cookie然后发送给浏览器.</a>
<hr>
<a href="<%=request.getContextPath()%>/receiveCookie">从浏览器发送cookie到服务器.</a>
</body>
</html>
