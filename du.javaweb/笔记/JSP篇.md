# 一、jsp原理解析

## 1、分析使用纯Servlet开发webapp的缺陷

在Servlet中编写html/css/js代码存在什么问题？

-   编写难度大，并且麻烦。

-   java程序中写前端代码，程序耦合度高。

-   维护成本太高。

    每次修改前端代码，都要从头编译全部代码。

-   代码不美观。

---

**如何解决上述问题？** 

-   不写Servlet，让机器自动生成，而程序员只写前端代码。然后让机器把前端代码自动翻译生成Servlet程序，在自动将Java程序编译生成class文件，在使用JVM调用class中的函数。
-   上述机制就是JSP的工作原理。

# 二、JSP

当访问jsp的时候（比如默认的index.jsp），实际上底层执行的是这个jsp编译生成的class程序。这个index.jsp会被tomcat翻译生成index.java文件，再被服务器编译成index_jsp.class文件。访问index.jsp，实际上访问的是index_jsp.class文件中的函数。

jsp实际上就是一个servlet，访问index.jsp的时候，会自动翻译生成index_jsp.java，然后再自动编译生成index_jsp.class，那么index.jsp就是一个类。

index_jsp类，继承HttpJspBase类，而HttpJspBase继承的是HttpServlet类。

所以**jsp的生命周期和servlet的生命周期完全相同**。

>   一个Servlet的生命周期：
>
>   第一次访问的时候被创建，并且执行init函数，然后执行service方法。后面每次访问的时候调用service函数。在tomcat关闭的时候被销毁。

jsp和Servlet都是单例模式（假单例）。

由于声明周期和运行方式的限制，jsp文件第一次访问的时候是比较慢的。第二次开始因为有编译好的对象，所以运行速度会快很多。

**JSP是什么？**

-   JSP是Java Server Pages的缩写。（基于Java语言的服务器端的页面）
-   JSP是java程序。
-   JSP也是JavaEE的十三条字规范之一。
-   JSP是一套规范，所有的web容器、web服务器都是遵守这套规范进行翻译的的，每一个 

注意：对jsp调错的时候，还是要打开翻译出来的java文件的源代码。

## JSP和Servlet的区别

尽管jsp在本质上也是一个servlet，但是jsp和servlet的职责不一样。

-   servlet的职责是什么？

    收集数据。servlet的强项是逻辑处理、业务处理、连接后端数据库、获取和收集数据。

-   jsp的职责是什么？

    jsp的强项是做数据展示。所以职责在于数据展示。

## JSP的基础语法

1.   在JSP中直接编写的文字，都会被自动翻译到servlet类的service方法的out.write()方法中。被Java程序当做普通字符串打印输出到浏览器。

2.   在JSP中编写的HTML代码，对于JSP来说只是一个普通的字符串，但是JSP把这个字符串输出到浏览器以后，浏览器就会对html、css、js代码进行解释，从而达到前端展示的效果。

3.   如何解决乱码问题？

     使用page配置指令：`<%@ page contentType="text/html;charset=UTF-8" language="java" %>`。

     这个配置指令会让翻译的时候添加`response.setContextType("charset=UTF-8");`，从而解决中文乱码问题。*这个指令后面会详细讲*。

### 在JSP中编写Java程序

-   使用`<% Java语句 %>`在JSP中编写Java程序。

    在方法中的代码执行是有顺序的，所以在jsp的脚本块中也要注意顺序。

-   在`<%! Java语句 %>`中编写的Java语句会自动翻译到service方法之外。（使用很少）

    为什么少用？因为静态变量和成员变量（实例变量）可能会导致线程安全问题。（因为JSP就是servlet，而servlet是单例的，在多线程环境下，这个静态变量和实例变量一旦修改，必然存在线程安全问题。）

### JSP注释

在这个符号中编写的被视为Java程序，被**翻译到Servlet类的service函数内部**。

>   时刻注意：是翻译到service函数内部。

如果要注释这段语句，使用：`<%-- 这里的Java代码会被注释掉。 --%>`。使用html注释也可以取消掉Java代码的效果，但是会被写到html中。

所以**始终建议使用JSP专用注释**。

### JSP输出语句

如何向浏览器上输出一个Java变量？

在翻译后的servlet中，response.Writer变量名为out，所以可以直接用`out.print()`的方式输出内容到浏览器。

>   out是JSP九大内置对象之一，只能在service方法中使用。

如果**输出的内容中含有Java代码**，可以使用下面的语法格式：`<%= 这里是输出内容。 %>` 

-   如何理解这个输出方式？

    代码`<%="这是一段代码"%>`最后会被翻译成`out.print("这是一段代码");`。

所以说，在`<%=  %>`中不可以添加分号`;`，否则会破坏原来的语句内容。（如果实在需要用分号，使用转义字符）

>   注意别被带偏，如果只是写普通字符串的话，可以用html写的。**所以`<%=  %>`符号只要用来输出变量就可以了。**

---

>   关于JavaBean。
>
>   全称javabean，被翻译为”咖啡豆“。一个Java程序由很多bean组成。
>
>   什么是javabean？可以理解为符合某种规范的Java类：
>
>   **有无参数构造函数、属性私有化、对外提供getter/setter、实现了java.io.Serializable接口、重写了toSting()、重写了hashCode和equals方法的类。** 
>
>   javabean就是Java中的实体类，负责数据的封装。
>
>   因为JavaBean符合JavaBean规范，所以这样的类具有很强的通用性。

---

## session机制

## 什么是session？

session对应的词语叫做“会话”。用户打开浏览器，执行一系列操作，最后关闭游览器，这个过程叫做一次会话。

会话在服务器端也有一个对应的Java对象，叫做session。一个会话中包含多个请求。

在java的servlet规范中，session对应的类名是HttpSession（jakarta.servlet.http.HttpSession）

session机制属于BS结构的一部分，即使更换开发语言，同样会有session机制。session是一个规范，不同的语言对session机制都有不同的实现。

**session对象最主要的作用是保存会话状态。**（比如用户登录成功的状态）

## 为什么需要session对象保存对话状态？

因为Http协议是一种无状态协议。这样的协议可以降低服务器的压力，请求的瞬间是连接的，请求结束后连接断开，这样服务器的压力比较小。（只要B、S断开了，服务器是不知道浏览器关闭的）

**为什么不使用request或者servContext保存会话状态？**

因为前者是一次请求创建一个，后者是服务其启动的时候创建，服务器关闭的时候销毁，request的域太小，servletContext的域太大，所以只能使用session。

## session对象的实现原理

`HttpSession session = request.getSession();` 获取session对象。（如果没有，则创建一个）

在浏览器这边保存一个记号，在session对象上面保存一个相同的记号，从而实现了session和浏览器互相绑定的功能。（底层是cookie）

### session的主要特点总结：

1. session对象是存储在服务器端的。
2. 一个session对象对应一次会话。
3. 一次会话中包含了多次请求。
4. 通过`request.getSession();`获取session对象。

	`request.getSession(false);`表示从服务器中获取session对象，如果获取失败，返回一个null。

### session的实现原理：

1. 在web服务器中有一个session列表（类似于map集合），其中的key保存的是session的id（以cookie的形式保存在列表中），value存储的是对应的session对象。
2. 用户发送第一次请求的时候，服务器会创建一个新的session对象，同时给session对象生成一个id，然后web服务器会将session的id发送给浏览器，浏览器将这个id保存在自己的缓存中。
3. 用户发送第二次请求的时候，会将浏览器缓存中的session id发送给服务器，服务器获得id，从列表中查找到对应的session对象。

> 关闭浏览器的时候，session id所在的缓存会被删除，重新打开后，没有保存好的session id，从而导致开启一次新的会话。

所以说，**从session对象创建到session被销毁的过程就是一次session会话。** 

**coockie禁用**：服务器正常发送coockie给浏览器，但是浏览器不收了。这会导致每次请求都是新的session。

**coockie禁用了，session机制还可以实现吗？**

可以，需要使用url重写机制。（在url后边用分号分开，追加一个coockie id，从而拿到已经存在的session对象）。但是url重写会提高开发者的成本。（在编写任何请求路径的时候都要追加session id，从而导致成本增加）

所以大部分网站都是：如果你禁用coockie，将会不能使用webapp。

### session对象的两种销毁方式：

- 超时销毁（服务器无法检测到浏览器关闭，所以session的销毁要依靠session超时机制）
- 手动销毁（有可能系统会提供安全退出功能，这样服务器就会知道session结束，从而删除session对象）

> 在web.xml文件中，可以设置session超时时长。（默认是30分钟）
>
> ```xml
> <!--下面表示session的超时限制是30分钟。-->
> <session-config>
>     <session-timeout>30</session-timeout>
> </session-config>
> ```

## 总结：目前为止所有的域对象

-   request（对应的类名是HttpRequest）
    -   请求域（最小）
-   session（对应HttpSession）
    -   会话域
-   application（对应ServletContext）
    -   应用域（最大）（webapp下面的所有用户共享）

三个域对象都有以下三个公共方法：

```
setAttribute(); // 绑定数据
getAttribute(); // 获取数据 
removeAttribute(); // 移除数据
```

**使用原则：尽量使用小的域。** 

---

设置JSP不自动生成session对象：`<%@page session="false" % `

但是写上这个以后，jsp的内置对象也就不能用了，所以一般情况不要写。

---

