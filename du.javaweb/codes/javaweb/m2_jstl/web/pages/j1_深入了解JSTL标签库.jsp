<%@ page import="bean.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.reflect.Array" %>

<%@page contentType="text/html; charset=utf-8" %>

<%--引入标签库 这里引入的是JSTL的核心标签库。这里的prefix一般明明为c（实际上可以自己命名）--%>
<%--这里的prefix命名为c是因为后面引入的是core，不是说所有的标签库都叫c。--%>
<%--这个地址链接了一个tld配置文件，源地址在jstl/meta-inf/c.tld。实际上它是一个xml配置文件，描述了 标签 和 Java类 之间的关系--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    // 创建List对象。
    List<Student> students = new ArrayList<>();

    // 创建Student对象。
    Student student = new Student("110", "警察叔叔");
    Student student1 = new Student("120", "救护车");
    Student student2 = new Student("123", "啊对对对");

    // 添加到list集合中。
    students.add(student);
    students.add(student1);
    students.add(student2);

    // 将list存入到请求域中。
    request.setAttribute("StudentList",students);

%>

<%--将List中的元素遍历，输出学生信息到浏览器--%>
<%--使用Java代码。--%>
<%
    // 从域中获取LIst集合。
    List<Student> studentList = (List<Student>)request.getAttribute("StudentList");

    // 编写for循环，打印输出LIst内容。
    for (Student s:
         studentList) {
%>
     学生编号：<%=s.getId()%> &nbsp;&nbsp;&nbsp; 学生姓名：<%=s.getName()%> <br>
<%
    }
%>

<hr>
<p>下面是使用JSTL标签和EL表达式实现的：</p>
<%--用标签库core中的foreach标签对list集合进行遍历--%>
<%--具体信息建议查看api--%>
<%--EL表达式只能从域中取数据。--%>
<c:forEach items="${StudentList}" var="s">
    学生编号：${s.id}；学生姓名：${s.name}。<br>
</c:forEach>