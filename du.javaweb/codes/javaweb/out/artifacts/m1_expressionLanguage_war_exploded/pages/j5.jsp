<%@ page contentType="text/html; charset=utf-8" %>

<title>EL表达式获取应用的根</title>

<%
    // JSP有九大内置对象：
    // pageContext、request、session、application、
    // response、out、config、page、exception
    /*
    其中，四个域对象，最小的是pageContext，翻译成页面上下文。通过pageContext可以获取：几乎全部的九大内置对象。
     */
%>
从下面的代码来看，pageContext.getRequest()函数是没用的，因为JSP本来就内置了Request对象。<br>
<%=pageContext.getRequest()%><br>
<%=request%>
<br>
pageContext.getRequest()存在的意义：<br>
在EL表达式中，没有request这个隐式对象。RequestScope只是代表请求范围，而不是request这个隐式对象。<br>
在EL表达式中有一个隐式对象叫做pageContext，EL表达式中的pageContext和JSP九大内置对象的pageContext是同一个对象。<br>

EL表达式中通过pageContext对象获取request对象：${pageContext.request}。<br>

如何获取应用的根？<br>
这是JSP的经典语法：<%=request.getContextPath()%><br>
现在用EL表达式获取：${pageContext.request.contextPath}<br>
