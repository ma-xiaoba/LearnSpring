<%--
  Created by IntelliJ IDEA.
  User: mzw
  Date: 2019/10/28
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath =request.getScheme()+"://"+request .getServerName()
          +":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath %>"/>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  </body>
</html>
