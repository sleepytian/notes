<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="bean.User" %>
<%--
  Created by IntelliJ IDEA.
  User: tian
  Date: 2023/2/13
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Map集合</title>
</head>
<body>
<%
    // 创建一个map集合。
    Map<String, String> map = new HashMap<>();
    map.put("username", "user");
    map.put("password", "123");

    // 将map存储到请求域中。
    request.setAttribute("map", map);
%>
<!-- 使用EL表达式取出map中的数据输出到浏览器。 -->
${map.username}
<br>
${map.password}
<br>
${map["username"]}
<br>
${map["password"]}
<br>

<%
    // 创建user和map
    User user1 = new User("name", "pwd");
    user1.setAge(10);
    Map<String, User> map1 = new HashMap<>();
    map1.put("userObj", user1);
    // 将map存入请求域。
    request.setAttribute("usermap1", map1);
%>
输出请求域中的map中的user对象的属性值。
${usermap1.userObj.username}

</body>
</html>
