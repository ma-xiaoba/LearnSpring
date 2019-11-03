<%--
  Created by IntelliJ IDEA.
  User: mzw
  Date: 2019/10/30
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>

    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <base href="<%=basePath %>"/>
    <title>transfer</title>


    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>

<script type="application/javascript">
    function ajaxUpload() {
        var formData = new FormData($("#ajaxForm")[0]);
        $.ajax({
            type: "post",
            url: "file/ajaxUpload",
            data: formData,
            cache: false, //不需要缓存
            contentType: false, // 不设置请求头
            processData:false, //不处理发送数据
            success: function (msg) {
                $("#headImage").attr("src",msg);//图片显示
            }
        });
    }
</script>
<form action="file/upload" method="post" enctype="multipart/form-data">
    请选择要上传的文件
    <input type="file" name="file"><br>
    <input type="submit" value="Upload">
</form>
<br>
<a href="file/download?name=2019年4月30日.png">2019年4月30日.png(后台操作)</a>


<br>
=======================================================<br>
批量文件上传<br>
<form action="file/uploadBatch" method="post" enctype="multipart/form-data">
    请选择要上传的文件1
    <input type="file" name="file"><br>
    请选择要上传的文件2
    <input type="file" name="file"><br>
    请选择要上传的文件3
    <input type="file" name="file"><br>
    请选择要上传的文件4
    <input type="file" name="file"><br>
    <input type="submit" value="Upload"><br>
</form>

=======================================================<br>
AJAX上传<br>
用户注册选择头像功能<br>

<form id="ajaxForm" action="file/ajaxUpload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" onchange="ajaxUpload()"><br>
    头像： <img id="headImage" src="images/1.jpg" width="50px" height="50px"><br>
    用户名：<input type="text" name="name"><br>
    密码：<input type="text" name="password"><br>
    <input type="submit" value="注册"><br>
</form>

</body>
</html>
