<%@ page import="bean.Student" %>
<%@page contentType="text/html; charset=utf-8" %>

<%--
关于EL表达式中的运算符。
    1. 算数运算符
    2. 关系运算符
        == != > >= < <= eq
    3. 逻辑运算符
        ！ && || not and or （注意 !和not 都是取反）
    4. 条件运算符
        ?: (三目运算符)
    5. 取值运算符
        [] 和 .
    6. empty运算符
--%>

${10 + 20}<br>
<%--EL表达式中，+运算符只能做求和运算，不会进行字符串拼接操作。--%>
EL表达式中加号运算符计算字符串：${10 + "20"}<br>
<%--使用加号计算字符串，下面的这一行解除注释后悔导致500错误，并且报错“数字格式化异常”--%>
<%--加号两边不是数字的时候，一定会转成数字，转换不成功，就抛出异常。（报错）--> numberFormatException --%>
\${10+"abc"}<br>
\${"abc"+"def"}<br>


<hr>
<%--关系运算符--%>
<%--下面第二行就失去了关系运算符的作用。--%>
${"abc" == "abc"} <br>
${"abc"} == ${"abc"} <br>

<%--比较一下实例对象的关系。--%>
<%
    Object obj = new Object();
    request.setAttribute("obj1", obj);
    request.setAttribute("obj2", obj);
%>
比较两个实例对象的关系：${obj1 == obj2}<br>


<%--注意：比较两个内容相同的字符串，结果是true。--%>
<%--因为：EL表达式中的==符号调用了equals函数。--%>
<%
    String a = "hello";
    String b = "hello";
    request.setAttribute("a", a);
    request.setAttribute("b", b);
%>
比较两个字符串：${a == b}<br>

<%--如何判断是不是调用了equals函数？ 重写equals函数，调用equals()的时候输出一段话。--%>
<%
    Student student1 = new Student("01", "zhangSan");
    Student student2 = new Student("01", "zhangSan");

    request.setAttribute("s1", student1);
    request.setAttribute("s2",student2);
%>
测试==运算符是否调用了equals函数：${s1 == s2}<br>
<%--另外，eq运算符调用的也是equals函数。--%>
使用eq运算符：${s1 eq s2}<br> 这个运算符也调用了equals函数。

<%--对于!=符号，也会调用equals函数。--%>
${s1 != s2}

<hr>
<%--empty运算符，判断是否为空。为空的话，返回true，否则返回false。--%>
判断param.username是否为空：${empty param.username} <br>
判断是否不为空：${!empty param.username} <br>
使用not判断是否不为空：${not empty param.username} <br>

<%--下面的返回值是false。原因：true和false是bool，和null不同。--%>
使用不存在的参数和null比较：${empty param.password == null} 其实就算在url用name=value提交了password，这里也是false，详情看上一行的注释。<br>

${param.password == null} <br>

<%--调减运算符--%>
${empty param.username ? "对不起，用户名不能为空" : "用户名不为空"} <br>
