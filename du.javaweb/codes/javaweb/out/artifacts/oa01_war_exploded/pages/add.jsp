<%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/3
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加新部门</title>
</head>
<body>
    <h1>添加新部门</h1>
    <hr>
    <form action="<%=request.getContextPath()%>/dept/add" method="post">
        <label for="dno">部门编号: </label><input type="text" id="dno" name="dno"> <br>
        <label for="dname">部门名称: </label><input type="text" id="dname" name="dname"> <br>
        <label for="loc">部门地址: </label><input type="text" id="loc" name="loc">
        <hr> <br>
        <input type="submit">
    </form>
</body>
</html>
