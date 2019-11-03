<%--
  Created by IntelliJ IDEA.
  User: mzw
  Date: 2019/10/31
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <base href="<%=basePath %>"/>
    <title>转账页面</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>

    <script>
        function transfer() {
            var formData = new $("#form1").serialize();
            $.ajax({
                type:"post",
                url:"account/transfer",
                data:formData,
                chache:false,
                success: function (msg) {
                   alert(msg);
                }
            });
        }
    </script>
</head>
<body>
    <form id="form1">
        转出方卡号：<input type="text" name="cardId">
        转入发卡号：<input type="text" name="targetCardId">
        转账金额：<input type="text" name="balance">
        <input type="button" onclick="transfer()"value="转账">
    </form>
</body>
</html>
