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
    <table>
        <tr>
            <td>姓名</td>
            <td>${user.name}</td>
        </tr>

        <tr>
            <td>年龄</td>
            <td>${user.age}</td>
        </tr>

        <tr>
            <td>备注</td>
            <td>${user.remark}</td>
        </tr>

    </table>
</body>
</html>
