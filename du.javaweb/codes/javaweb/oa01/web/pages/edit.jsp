<%@ page import="beans.Department" %><%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/3
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改部门信息</title>
</head>
<body>
<h1>修改部门信息</h1>
<hr>

<%
    // 获取部门信息
    Department department = (Department)request.getAttribute("department");

    System.out.println("edit.jsp"+department);
%>

<form action="<%=request.getContextPath()%>/dept/commitEdit" method="post">
    <label for="dno">部门编号: </label><input type="text" id="dno" value="<%=department.getDno()%>" readonly name="dno"> <br>
    <label for="dname">部门名称: </label><input type="text" id="dname" value="<%=department.getDname()%>" name="dname"> <br>
    <label for="loc">部门地址: </label><input type="text" id="loc" value="<%=department.getLoc()%>" name="loc">
    <hr> <br>
    <input type="submit" value="提交修改">
</form>
</body>
</html>
