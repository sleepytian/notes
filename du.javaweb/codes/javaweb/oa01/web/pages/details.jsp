<%@ page import="beans.Department" %><%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/3
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门详情</title>
</head>
<body>
<h1>部门详情</h1>
<hr>
<%
    // 获取请求域中的信息
    Department department = (Department) request.getAttribute("detailedDept");

    // 输出信息

%>

部门编号:<%=department.getDno()%><br>
部门名称:<%=department.getDname()%><br>
部门位置:<%=department.getLoc()%><br>

<hr>
<!-- 返回上一个历史页面 -->
<input type="button" value="返回列表" onclick="window.history.back();">
</body>
</html>
