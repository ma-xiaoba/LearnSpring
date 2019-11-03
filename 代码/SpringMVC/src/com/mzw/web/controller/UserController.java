package com.mzw.web.controller;

import com.mzw.bean.User;
import com.mzw.service.UserService;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller //注册Bean到spring IOC上下文中
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "selectId")
    public String selectById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.selectById(id);
        request.setAttribute("user",user);
        return "user";
    }

    @RequestMapping(value = "index")     //  user/index
    public String index(@RequestParam(name = "age",required = false,defaultValue = "20")int age){
        System.out.println(age);
        return "/index.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "index1/{id}")
    public String index1(@PathVariable("id")int id){
        System.out.println("id的值是："+id);
        return "ID = "+id;
    }


    /**
     * 添加用用户
     */
    @RequestMapping(value = "add")
    public String add(User user, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "age")int age) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println(user);
        int num = userService.insert(user);
        System.out.println("添加 "+num+" 个用户，年龄 age = "+age);
        request.setAttribute("user",user);
        return "/user/user-view.jsp";
    }

    /**
     *  转发
     * @return
     */
    @RequestMapping(value = "t1")
    public String t1(){
        return "forward:/index.jsp";
    }

    /**
     *  重定向
     * @return
     */
    @RequestMapping(value = "t2")
    public String t2(){
        return "redirect:/index.jsp";
    }





    /************************************************************************/
    /**
     * 处理模型数据
     */

    /***
     * Map
     * @return
     */
    @RequestMapping(value = "map/{id}")
    public String selectById(@PathVariable("id")int id, HttpServletRequest request, Map map){
        User user = userService.selectById(id);
        map.put("id",id);
        map.put("user",user);
        return "/user/user-view.jsp";
    }

    /***
     * Model
     * @return
     */
    @RequestMapping(value = "model/{id}")
    public String selectById1(@PathVariable("id")int id, HttpServletRequest request, Model model){
        User user = userService.selectById(id);
        model.addAttribute("id",id);
        model.addAttribute("user",user);
        return "/user/user-view.jsp";
    }

    /***
     * Model
     * @return
     */
    @RequestMapping(value = "modelAndView/{id}")
    public ModelAndView selectById3(@PathVariable("id")int id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.selectById(id);
        modelAndView.setViewName("/user/user-view.jsp");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
