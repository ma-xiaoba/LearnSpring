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
        <c:forEach var="u" items="${page.list}">
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

        <tr align="center">
            <td colspan="6">
                <c:forEach begin="1" end="${page.countPage}" var="i">
                    <a href="user/list?page=${i}&size=${page.pageSize}">第${i}页</a>&nbsp;
                </c:forEach>
            </td>

        </tr>
    </table>
</div>
</body>
</html>
