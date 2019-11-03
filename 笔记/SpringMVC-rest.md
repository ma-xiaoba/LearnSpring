# Spring MVC-REST

## 简介

我们一般请求的url是如下:
	http://...xxx.action?id=001 &type=aaa
而REST的url风格是什么样子呢?一般它类似于: 
	http://...xxx/001
所以REST有个很明显的特点：使url变得简洁,将参数通过url传到服务端。

## 什么是REST?

**REST :即Representational State Transfer。( 资源)表现层状态转化。**是目前最流行的种互联网软件架构。它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用。

* **资源( Resources)** ：网络.上的一一个实体,或者说是网络.上的一一个具体信息。它可以是一-段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。可以用一 个URI (统-资源定位符 )指向它,每种资源对应一个特定的URI。要获取这个资源,访问它的URI就可以,因此URI即为每一一个资源的独一无二的识别符。

- **表现层( Representation)** ：把资源具体呈现出来的形式,叫做它的表现层( Representation)。比如,文本可以用txt格式表现,也可以用HTML格式、XML格式、JSON 格式表现，甚至可以采用二进制格式。

- **状态转化( State Transfer)** ：每发出一个请求,就代表了客户端和服务器的一次交互过程。HTTP协议,是一个无状态协议,即所有的状态都保存在服务器端。因此,如果客户端想要操作服务器,必须通过某种手段,让服务器端发生"状态转化”( State Transfer )。而这种转化是建立在表现层之上的,所以就是“表现层状态转化"。具体说，就是HTTP协议里面,四个表示操作方式的动词: GET、POST、 PUT、 DELETE.

  它们分别对应四种基本操作：GET用来获取资源， POST用来新建资源， PUT用来更新资源， DELETE用来删除资源。

  它是目前最流行的一种互联网软件架构。它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用。

## 什么是REST FUL?

一种软件架构风格、设计风格,而不是标准,只是提供了一-组设计原则和约束条件。 它主要用于客户端和服务器交互类的软件。基于这个风格设计的软件可以更简洁,更有层次,更易于实现缓存等机制。

### 需求示例：

/某路径/1		HTTP GET ：得到id= 1的user

/某路径/1		HTTP DELETE ：删除id = 1的user

/某路径			HTTP PUT : 更新查询id = 1的user ,进行PUT请求更新

/某路径			HTTP POST ：新增user

http://ocalhost:8080/pro/user/1 	GET

http://localhost:8080/pro/user/1	DELETE

http://localhost:8080/pro/user/1	PUT

http://localhost:8080/pro/user/		POST

### @PathVariable注解:映射URL绑定的占位符

带占位符的URL是Spring3.0新增的功能,该功能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义通过`@PathVariable`可以将URL中占位符参数绑定到控制器处理方法的入参中: URL中的{xx}占位符可以通过`@PathVariable(“xxx")`绑定到操作方法的入参中。

## REST FUL风格开发示例

浏览器form表单只支持GET与POST请求，而DELETE、PUT等method并不支持，

Spring3.0添加了-一个过滤器**HiddenHttpMethodFilter** ,可以将这些请求转换为标准的http方法,使得支
持GET、POST、 PUT与DELETE请求。

### RestFul风格web.xml配置

```xml
<filter>
	<filter-name>hiddenHttpMethodFilter</filter-name>
	<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>hiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping> 
```

 ### Controller层编写

```java
package com.mzw.web.controller;
import com.mzw.bean.User;
import com.mzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list")
    public String list(Map<String, List<User>> map){
        map.put("userList",userService.selectList());
        return "/page/user/user-list.jsp";
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public String getById(@PathVariable(value = "id")int id, Model model){
        model.addAttribute("user",userService.selectById(id));
        return "/page/user/user-view.jsp";
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id")int id){
        System.out.println("-----delete user----");
        userService.deleteById(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String add(User user){
        userService.insert(user);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.GET)
    public ModelAndView update(@PathVariable(name = "id")int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/page/user/user-update.jsp");
        modelAndView.addObject("user",userService.selectById(id));
        return modelAndView;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateToDB(User user){
        userService.update(user);
        return "redirect:/user/list";
    }
}
```

### 页面编辑

导入jstl的库`jstl-1.2.jar`

user-add.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <title>User</title>
</head>
<body>
<form action="user/" method="post">
    <table align="center" border="1">
        <tr align="center" border="1" width="800">
        <tr>
            <td>NAME</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>AGE</td>
            <td><input type="number" name="age"></td>
        </tr>
        <tr>
            <td>REMARK</td>
            <td><input type="text" name="remark"></td>
        </tr>
        <tr>
            <td>STATUS</td>
            <td><input type="number" name="status"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="添加"></td>
        </tr>
    </table>
</form>
</body>
</html>
```

user-list.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <title>User</title>
</head>
<body>
<form name="delForm" action="user/delete" method="post">
    <input type="hidden" name="_method" value="DELETE">
</form>
<script type="text/javascript ">
    function del(id) {
        if (confirm('确定要删除吗') == true) {
            var url = "user/" + id;
            document.delForm.action = url;
            document.delForm.submit();
        }
    }
</script>
<div align="center">
    <a href="page/user/user-add.jsp">添加</a>
    <table align="center" border="1" width="800">
        <tr>
            <td>ID</td>
            <td>NAME</td>
            <td>AGE</td>
            <td>REMARK</td>
            <td>STATUS</td>
            <td>OPERATE</td>
        </tr>
        <c:forEach var="u" items="${userList}">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.age}</td>
                <td>${u.remark}</td>
                <td>
                    <c:if test="${u.status==1}">
                        有效
                    </c:if>
                    <c:if test="${u.status==0}">
                        无效
                    </c:if>
                </td>
                <td>
                    <a href="javascript:del(${u.id})">delete</a>
                    <a href="user/update/${u.id}">modify</a>
                    <a href="user/${u.id}">search</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
```

user-update.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <title>User</title>
</head>
<body>
<form action="user/update" method="post">
    <input type="hidden" name="id" value="${user.id}">
    <table align="center" border="1">
        <tr align="center" border="1" width="800">
        <tr>
            <td>NAME</td>
            <td><input type="text" name="name" value="${user.name}"></td>
        </tr>
        <tr>
            <td>AGE</td>
            <td><input type="number" name="age" value="${user.age}"></td>
        </tr>
        <tr>
            <td>REMARK</td>
            <td><input type="text" name="remark" value="${user.remark}"></td>
        </tr>
        <tr>
            <td>STATUS</td>
            <td><input type="number" name="status" value="${user.status}"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="修改"></td>
        </tr>
    </table>
</form>
</body>
</html>
```

user-view.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <title>User</title>
</head>
<body>
<form action="user/add">
    <table align="center" border="1" width="800">
        <tr>
            <td>ID</td>
            <td>${user.id}</td>
        </tr>
        <tr>
            <td>NAME</td>
            <td>${user.name}</td>
        </tr>
        <tr>
            <td>AGE</td>
            <td>${user.age}</td>
        </tr>
        <tr>
            <td>REMARK</td>
            <td>${user.remark}</td>
        </tr>
        <tr>
            <td>STATUS</td>
            <td colspan="2">
                <c:if test="${user.status==1}">
                    有效
                </c:if>
                <c:if test="${user.status==0}">
                    无效
                </c:if>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
```

## 静态资源

### 问题

优雅的REST风格的资源URL不希望带.html 或.do等后缀

若将`DispatcherServlet`请求映射配置为`/ `,则Spring MVC将捕获WEB容器的所有请求,包括静态资源的请求,
SpringMVC会将他们当成-一个普通请求处理 ，因找不到对应处理器将导致错误。

### 解决

可以在SpringMVC的配置文件中配置`<mvc:default-servlet-hander/>`的方式解决静态资源的问题:

- `mvc:default-servlet-handler/ `将在SpringMVC上下文中定义一个 `DefaultServletHttpRequestHandler `,它会对进入`DispatcherServlet`的请求进行筛查,如果发现是没有经过映射的请求,就将该请求交由WEB应用服务器默认的Servlet 处理,如果不是静态资源的请求,才由`DispatcherServlet`继续处理
- 一般WEB应用服务器默认的Servlet的名称都是default。若所使用的WEB服务器的默认Servlet名称不是default ,则需要通过`default-servlet-name`属性显式指定

### 注意

`<mvc:annotation-driven/>`是告知Spring ,启用注解驱动。然后Spring会自动为我们注册上面说到多个Bean到工厂中,来处理我们的请求。

**主要有两个：**

**RequestMappingHandlerMapping**
**RequestMappingHandlerAdapter**
第一个是`HandlerMapping`的实现类,它会处理`@RequestMapping`注解,并将其注册到请求映射表中。
第二个是`HandlerAdapter`的实现类,它是处理请求的适配器,就是确定调用哪个类的哪个方法,并且构造方法参数，返回值。

#### @RequestBody

`@RequestBody`将HTTP请求正文转换为适合的`HttpMessageConverter`对象。

`@RequestBody`是作用在形参列表上,用于将前台发送过来固定格式的数据[xml 格式或者json等]封装为对应的JavaBean对象,封装时使用到的一个对象是系统默认配置的HttpMessageConverter进行解析,然后封装到形参上。

```js
$.ajax({
        url: "user/login",
        type:"POST", 
        data:' {"userName" :"admin" , " pwd"，"admin123"}',
        content -type: " application/json charset=utf-8",
        success: function(data){	//200
        	alert(data);
    	}
	});
```

```java
@RequestMapping("/login")
public void login(@RequestBody String userName ，QRequestBody String pwd){
	System.out.printIn(userName+" : "+ pwd);
}

```

这种情况是将JSON字符串中的两个变量的值分别赋予了两个字符串，(**不是很常用**)

#### @ResponseBody

`@ResponseBody`将内容或对象作为HTTP响应正文返回。

`produces={"application/json;charset=UTF-8"}`用于处理响应请求编码

```java
@ResponseBody
@RequestMapping(value="t1"，produces={" application/json; charset=UTF-8"})	
public String t1(){
	return "Hello ResponseBody ";
}
```

@ResponseBody是作用在方法上的, @ResponseBody表示该方法的返回结果直接写入HTTP response body中,一般在异步获取数据时使用【也就是AJAX】

在使用`@RequestMapping`后,返回值通常解析为跳转路径,但是加上`@ResponseBody`后返回结果不会被解析为跳转路径,而是直接写入HTTP response body中。比如异步获取json数据,加上@ResponseBody后,会直接返回json数据。
`@RequestBody`将HTTP请求正文插入方法中,使用适合的HttpMessageConverter将请求体写入某个对象。



#### HttpMessageConverter

Spring 3.X系列增加了新注解`@ResponseBody` ,` @RequestBody` `HttpMessageConverter`接口.需要开启
`<mvc:annotation-driven />` `AnnotationMethodHandlerAdapter`将会初始化7个转换器

`ByteArrayHttpMessageConverter`
`StringHttpMessageConverter`
`ResourceHttpMessageConverter`
`SourceHttpMessageConverter`
`XmlAwareFormHttpMessageConverter`
`Jaxb2RootElementHttpMessageCorverter`
`MappingJacksonHttpMessageConverter`
只要有对应协议的解析器,就可以通过注解完成协议--对象的转换工作

#### 编写对应的消息解析器

导入相应的jar（Spring默认的json协议解析由JackSon完成导入jar包）

```java
jackson-annotations-2.8.7.jar
jackson-core-2.8.7.jar
jackson-databind-2.8.7.jar
```

编写目标方法使其返回JSON对应的对象或集合

```java
@ResponseBody
@RequestMapping(value = "jackson/{id}",produces={" application/json; charset=UTF-8"})
public User testJackSon(@PathVariable(name = "id")int id){
	User user = userService.selectById(id);
    return user;
}
```

#### JackSon JSON操作



## 最佳化实践

为了便于配置文件管理。在很多配置中一般都会进行分开配置，这种配置就像各司其职一样，显得特别清晰。

### spring_core.xml

 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
">

    <!--Spring IOC 扫描指定包目录下所有类，@Component，@Service @Repository-->
    <context:component-scan base-package="com.mzw">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:exclude-filter>
    </context:component-scan>

    <!--Spring AOP启动配置 自动激活动态代理-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

    <!--配置数据源 druid-->

    <!--加载外部property文件
    location用来指定属性文件的位置
    -->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url}"/>
        <property name="username" value="${jdbc_user}"/>
        <property name="password" value="${jdbc_password}"/>
        <property name="driverClassName" value="${jdbc_driver}"/>
        <property name="filters" value="${filters}"/>

        <property name="maxActive" value="${maxActive}"/>
        <property name="initialSize" value="${initialSize}"/>
        <property name="maxWait" value="${maxWait}"/>
        <property name="minIdle" value="${minIdle}"/>

        <property name="timeBetweenEvictionRunsMillis" value="${timeBetweenEvictionRunsMillis}"/>
        <property name="minEvictableIdleTimeMillis" value="${minEvictableIdleTimeMillis}"/>

        <property name="validationQuery" value="${validationQuery}"/>
        <property name="testWhileIdle" value="${testWhileIdle}"/>
        <property name="testOnBorrow" value="${testOnBorrow}"/>
        <property name="testOnReturn" value="${testOnReturn}"/>
        <property name="maxOpenPreparedStatements"
                  value="${maxOpenPreparedStatements}"/>
        <!--<property name="removeAbandoned" value="${removeAbandoned}" /> &lt;!&ndash; 打开removeAbandoned功能 &ndash;&gt;-->
        <!--<property name="removeAbandonedTimeout" value="${removeAbandonedTimeout}" /> &lt;!&ndash; 1800秒，也就是30分钟 &ndash;&gt;-->
        <!--<property name="logAbandoned" value="${logAbandoned}" /> &lt;!&ndash; 关闭abanded连接时输出错误日志 &ndash;&gt;-->
    </bean>
    <!--spring jdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource">

        </property>
    </bean>

</beans>
 ```

### spring_mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
">

    <context:component-scan base-package="com.mzw" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
    </context:component-scan>

    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

    <!--处理静态资源-->
    <mvc:default-servlet-handler/>

    <!--启动注解驱动-->
    <mvc:annotation-driven/>
</beans>
```

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--监听：加载所有配置文件-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:spring_*.xml</param-value>
    </context-param>

    
    <!-- SpringMVC编码格式处理UTF-8 -->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--rest 格式URL处理-->
    <filter>
        <filter-name>hiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>hiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
    <!--SpringMVC配置文件加载-->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--
            配置DispatcherServlet : DispatcherServlet
            默认加载/WEB- INF/<servletName -servlet> .xml的Spring配置文件，启动WEB层的Spring容器。
            可以通过contextConfigLocation初始化参数自定义配置文件的位置和名称
        -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring_mvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
         <!--
        	/   拦截所有的请求，不包括jsp
       	 	/*  拦截所有请求（包括jsp）
    	   -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

