<%--
  Created by IntelliJ IDEA.
  User: mzw
  Date: 2019/10/28
  Time: 11:32
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
    <title>User</title>
</head>
<body>
    <h1>跳转成功</h1>
    name：${user.name}<br>
    age：${user.age}<br>
    remark：${user.remark}
</body>
</html>
