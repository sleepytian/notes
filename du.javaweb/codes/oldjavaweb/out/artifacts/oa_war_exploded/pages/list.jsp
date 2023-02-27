<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.Department" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: zht17
  Date: 2023/2/3
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门列表</title>

    <style>
        table {
            margin-left: 25%;
            width: 50%;
            text-align: center;
        }

        tr, td, th {
            border: 1px black solid;
        }
    </style>
</head>
<body>
<h1>部门列表</h1>
<hr>

<script>
    window.onload = function () {
        // 删除部门的函数
        del = function (dno) {
            // 弹出确认框
            ok = confirm("确认删除部门"+dno+"嘛?");
            if (ok) {
                // 确认删除, 请求servlet执行删除操作.
                document.location.href = "<%=request.getContextPath()%>/dept/del?dno="+dno;
            }
        }
    }
</script>

<table>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>相关操作</th>
    </tr>
    <%
        // jsp脚本块
        // 先获取列表部门列表对象.
        List<Department> deptList = (List<Department>) request.getAttribute("deptList");

        int i = 1;

        for (Department department :
                deptList) {

    %>
    <tr>
        <!-- 填入信息 -->
        <td><%=i++%></td>
        <td><%=department.getDno()%>
        </td>
        <td><%=department.getDname()%>
        </td>
        <td>
            <a href="<%=request.getContextPath()%>/dept/detail?dno=<%=department.getDno()%>">详情</a>
            <a href="<%=request.getContextPath()%>/dept/edit?dno=<%=department.getDno()%>">修改</a>
            <a href="javascript:void(0);" onclick="del(<%=department.getDno()%>);">删除</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<hr>
<a href="<%=request.getContextPath()%>/pages/add.jsp">添加新部门.</a>
<hr>
username:<%=request.getSession().getAttribute("username")%><br>
password:<%=request.getSession().getAttribute("password")%>
<hr>
<a href="<%=request.getContextPath()%>/user/logout">退出登录</a>
</body>
</html>
