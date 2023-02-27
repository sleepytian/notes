<%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/3
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Index</title>
</head>
<body>
<h1>index</h1>
<hr>

<form action="<%=request.getContextPath()%>/user/login" method="post">
  <label for="username">用户名: </label><input type="text" value="admin" id="username" name="username"><br>
  <label for="password">密码: </label><input type="text" value="123" id="password" name="password"><br>
  <label>
    <input type="checkbox" name="autoLogin" value="1">
    十天内免登录
  </label><br>
  <input type="submit">
</form>


<!-- 前端发送请求的时候,如果路径是绝对路径,应该以 /项目名 开始 -->
<!-- 使用request的getContextPath()获取webapp的根目录 -->
<!-- 这里输出的内容相当于一个"/oa",也就是根目录 -->
<!-- 注意不要在路径中加空格,否则url中会添加空格, 而在jsp脚本块中可以加空格 -->
<a href="<%= request.getContextPath() %>/user/login">进入部门列表</a>


</body>
</html>
