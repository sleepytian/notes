<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>

应用的根路径：${pageContext.request.contextPath}<br>

<%--request 是JSP九大对象之一。--%>
<%--request.getParameter("parameyerName")可以获取请求参数。--%>
<%--用户在浏览器提交数据，Servlet通过获取请求参数获取数据。--%>
用户名：<%=request.getParameter("username")%><br>
<%--通过EL表达式内置对象param获取用户名，注意写法，不用加引号。--%>
使用EL表达式获取用户名：${param.username}<br>

<%--假设用户提交的数据有多个name--%>
<%--http://localhost:8080/m1/pages/j6.jsp?username=zhangsan&h=h1&h=h2&h=h3--%>
<%--当提交了checkbox的时候，将会出现多个同name的value--%>
EL表达式获取checkbox（param版本）：${param.h}<br>
<%--显然，使用param获取checkbox参数的时候，只会获取第一个参数。--%>
<%--有多个值的时候，使用getParameterValues()--%>
<%--param获取的是请求参数中的第一个参数，如果需要所有元素，要使用paramValues--%>
EL表达式使用paramValues获取checkbox数据：${paramValues.h} （打印出来是一维数组。）<br>
现在遍历数组获取元素：${paramValues.h[0]}、${paramValues.h[1]}、${paramValues.h[2]}。 使用数组的方式查看数据即可。

<%--EL表达式中的隐含对象：initParam--%>
<%--对应的是xml文件中的上下文初始化参数。使用ServletContext对象获取，对应的JSP九大内置对象之一是application。--%>
xml文件中的上下文参数配置：<%=application.getInitParameter("paramName")%><br>
用EL表达式做到同样的事情：${initParam.paramName}<br>




</html>


