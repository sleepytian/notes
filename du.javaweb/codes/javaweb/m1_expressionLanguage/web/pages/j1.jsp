<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="bean.User" %>
<html>
<head>
    <title>jsp01</title>
    <meta content="text/html,utf-8">
</head>
<body>
<%
    // 创建一个user
    User user = new User("name","password");
    // 保存到域中。
    request.setAttribute("user",user);
%>

用户对象：${user}<br>
用户 名：${user.username}<br>
用户密码：${user.password}<br>

新写法：
${user["username"]}

</body>
</html>