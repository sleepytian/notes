<%@ page import="bean.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: tian
  Date: 2023/2/13
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式从数组中取数据</title>
</head>
<body>
<%
  // 创建一个数组
  String[] str = {"zhangsan", "lisi", "wangwu"};
  // 向域中存储数组。
  request.setAttribute("strings", str);
%>
使用EL表达式获取数组中的值：<br>
输出数组本身：${strings}<br>
数组中的第二个值：${strings[1]}<br>
如果取不出数据，将会显示空白：${strings[100]}，不会出现数组下标越界的问题。（因为有空处理）<br>
<hr>

<%
// 现在换成对象数组。
  User user1 = new User();
  User user2 = new User();

  user1.setUsername("user1");
  user2.setUsername("user2Name");

  // 创建数组
  User[] users = {user1, user2};

  // 添加到请求域中。
  request.setAttribute("userArr",users);
%>
使用EL表达式从域中获取users数组：<br>
${userArr}<br>
获取第二个User：${userArr[1]}<br>
获取第一个User的username：${userArr[0].username}<br>
<hr>

<%
  // 现在吧数组换成List
  List<String> stringList = new ArrayList<>();
  stringList.add("hello");
  stringList.add("world");
  // 保存到域中。
  request.setAttribute("stringList",stringList);
%>
List集合也是通过下标的方式取数据的。<br>
使用EL表达式获取List：<br>
获取list本身：${stringList}<br>
获取元素：${stringList[1]}<br>
<hr>

<%
  // 现在换成Set集合。
  Set<String> stringSet = new HashSet<>();
  stringSet.add("hello");
  stringSet.add("world");
  stringSet.add("java");
%>
从set集合中获取数据：<br>
取出set集合：${stringList}<br>
使用【下标】的方式无法获取set集合的元素。


</body>
</html>
