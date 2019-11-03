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
