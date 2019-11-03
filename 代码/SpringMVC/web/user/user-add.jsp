<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <title>User</title>
</head>
<body>
<h1>user/userlist.jsp</h1>
<form action="user/add">
    <table>
        <tr>
            <td>姓名</td>
            <td><input type="text" name="name"></td>
        </tr>

        <tr>
            <td>年龄</td>
            <td><input type="number" name="age"></td>
        </tr>

        <tr>
            <td>备注</td>
            <td><input type="text" name="remark"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="注册">
            </td>

        </tr>
    </table>
</form>
</body>
</html>
