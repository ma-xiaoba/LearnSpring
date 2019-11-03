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
