package com.mzw.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping(value = "file")
public class FileController {

    @ResponseBody
    @RequestMapping("upload")
    public byte[] upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException, InterruptedException {
        byte[] bytes = multipartFile.getBytes();
        System.out.println(multipartFile.getContentType()); //上下文的内容
        System.out.println(multipartFile.getName());        //表单名称
        InputStream inputStream = multipartFile.getInputStream();
        System.out.println(inputStream); //输入流对象
        System.out.println(multipartFile.getOriginalFilename());    //文件名称
        System.out.println(multipartFile.getSize());     //文件大小
        inputStream.close();
        if (!multipartFile.isEmpty()) {
            Thread.sleep(3000);
            String realPath = request.getServletContext().getRealPath("/uploadFile/");
            File file = new File(realPath + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
        }
        return bytes;
    }

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




    @RequestMapping(value = "uploadBatch")
    public String uploadBatch(HttpServletRequest request,@RequestParam(value = "file")MultipartFile[] files) throws IOException {
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


    @ResponseBody
    @RequestMapping("ajaxUpload")
    public String ajaxUpload(HttpServletRequest request,@RequestParam(value = "file")MultipartFile multipartFile) throws IOException {
        String path = request.getServletContext().getRealPath("/uploadFile/");
        String fileName = multipartFile.getOriginalFilename();
        String dest = path+fileName;
        multipartFile.transferTo(new File(dest));
        return "uploadFile/"+fileName;
    }
}
