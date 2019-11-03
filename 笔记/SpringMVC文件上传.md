# SpringMVC文件上传

Spring MVC为文件上传提供了直接的支持,这种支持是通过即插即用的MultipartResolver实现的。

Spring用Jakarta Commons FileUpload技术实现了一个 MultipartResolver实现类:CommonsMultipartResovler

Spring MVC上下文中默认没有装配MultipartResovler ,因此默认情况下不能处理文件的上传工作,如果想使用Spring的文件上传功能,需现在上下文中配置MultipartResolver

## 配置MultipartResolver

### 导入jar

为了让CommonsMultipartResovler正确工作,必须先将
Jakarta Commons FileUpload及Jakarta Commons io的类包添加到类路径下。

```
commons-fileup1oad-1.3.2
commons-io-2.4. jar
```

### 配置

```xml
<!-- SpringMVC文件上传-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!--请求的编码格式必须和用户JSP的编码一致，以便正确读取表单中的内容。
    uploadTempDir :文件上传过程中的临时目录，上传完成后，临时文件会自动删除
    maxuploadSize:设置文件上传大小上限(单位为字节)
    -->
    <property name="defaultEncoding" value="UTF-8"/>
    <property name="maxUploadSize" value="1024000"/>
	<property name="uploadTempDir" value="uploadFile/temp"></property>
</bean>
```

defaultEncoding:必须和用户JSP的pageEncoding属性一致,以便正确解析表单的内容

## 文件上传示例

jsp

上传文件时表单要设置`enctype="multipart/form-data"`属性。

```jsp
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
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <base href="<%=basePath %>"/>
    <title>UPLOAD</title>
</head>
<body>
    
    <form action="file/upload" method="post" enctype="multipart/form-data">
        请选择要上传的文件
        <input type="file" name="file" >
        <input type="submit" value="上传">
    </form>
</body>
</html>
```

controller

```java
@Controller
@RequestMapping(value = "file")
public class FileController {
    @ResponseBody
    @RequestMapping("upload")
    public String upload(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        System.out.println(multipartFile.getContentType()); //上下文的内容
        System.out.println(multipartFile.getName());        //表单名称
        System.out.println(multipartFile.getInputStream()); //输入流对象
        System.out.println(multipartFile.getOriginalFilename());    //文件名称
        System.out.println(multipartFile.getSize());     //文件大小
        if (!multipartFile.isEmpty()) {
            String realPath = request.getServletContext().getRealPath("/uploadFile/");
            File file =new File(realPath+multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
        }
        InputStream inputStream = multipartFile.getInputStream();
        String str = new String(bytes);
        byte[] bytes1 = new byte[4096];

        return str;
    }
}
```

## 文件下载实例

默认情况下,直接通过标签请求即可下载文件。但是如果该文件的文件名为中文，在早些的浏览器上就会导致下载失败；如果使用最新的Firefox、Chrome、Opera、Safari则都可以正常下载文件名为中文的文件。

SpringMVC提供了一个ResponseEntity类型,使用它可以很方便地定义返回的HttpHeaders和HttpStatus

```java
//文件下载示例
@RequestMapping("download")
public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
	//指定对应下载文件的路径
	String path = request.getSession().getServletContext().getRealPath("/uploadFile");
    String filename = request.getParameter("name");
    String fileName = path + File.separator + filename;//"20180125040256中文文档. txt";
    //请求体
    File file = new File(fileName);
    System.out.println(file);
    //apache对java io封装提供的工具类
    byte[] body = FileUtils.readFileToByteArray(file);
    //请求头
    HttpHeaders headers = new HttpHeaders();
    //通知浏览器以attachment (下载方式)打开图片
    //下载显示的文件名，解决中文名称乱码问题
    String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
    //以另存为的方式保存文件
    headers.add("Content-Disposition", "attachment; filename="+ downloadFileName);
    //请求码
    HttpStatus status = HttpStatus.OK;
    //请求体、请求头和状态码
    return new ResponseEntity<byte[]>(body, headers, status);
}
```

## 文件批量上传

```java
 @RequestMapping(value = "uploadBatch")
public String uploadBatch(HttpServletRequest request,
@RequestParam(value ="file")MultipartFile[] files) throws IOException {
	String path = request.getServletContext().getRealPath("/uploadFile/");
    for(int i=0;i<files.length;i++){
    	MultipartFile file = files[i];
        if (!file.isEmpty()){
        	String fileName = file.getOriginalFilename();
            String dest = path+fileName;
            file.transferTo(new File(dest));
            System.out.println("【"+fileName+"】上传到==》"+path);
        }
    }
    return "/page/fileTransfer/transfer.jsp";
}
```

## Ajax文件上传后台/前台

传统的form表单提交会导致页面刷新,但是在有些情况下,我们不希望页面被刷新,这种时候我们需要使用Ajax的方式进行

**问题1** ：传统ajax只能传递一般的参数，上传文件的文件流是无法被序列化并传递
	XMLHttpRequest Level 2添加了一个新的接口FormData。利用FormData对象我们可以通过JavaScript用一些键值对来模拟一系列表单控件

```
var formData = new FormData();
```

**Jquery为例:**

需要设置关键是设置: `processData`和`contentType`

```js
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
```

```java
@ResponseBody
@RequestMapping("ajaxUpload")
public String ajaxUpload(HttpServletRequest request,
@RequestParam(value ="file")MultipartFile multipartFile) throws IOException {
	String path = request.getServletContext().getRealPath("/uploadFile/");
    String fileName = multipartFile.getOriginalFilename();
    String dest = path+fileName;
    multipartFile.transferTo(new File(dest));
    return "uploadFile/"+fileName;
}
```

