package com.mzw.web.controller;


import com.mzw.bean.User;
import com.mzw.service.UserService;
import com.mzw.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "list")
//    public String list(Map<String, List<User>> map){
//        map.put("userList",userService.selectList());
//        return "/page/user/user-list.jsp";
//    }

    @RequestMapping(value = "list")
    public String list(@RequestParam(value = "page",required = false,defaultValue = "1")int page,
                       @RequestParam (value = "size",required = false,defaultValue = "3")int size,Map map){
        PageUtil pageUtil = userService.selectByPage(page, size);
        map.put("page",pageUtil);
        return "/page/user/user-list.jsp";
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public String getById(@PathVariable(value = "id")int id, Model model){
        model.addAttribute("user",userService.selectById(id));
        return "/page/user/user-view.jsp";
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id")int id){
        System.out.println("-----delete user----");
        userService.deleteById(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String add(User user){
        userService.insert(user);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.GET)
    public ModelAndView update(@PathVariable(name = "id")int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/page/user/user-update.jsp");
        modelAndView.addObject("user",userService.selectById(id));
        return modelAndView;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateToDB(User user){
        userService.update(user);
        return "redirect:/user/list";
    }

    @ResponseBody
    @RequestMapping(value = "t1",produces={" application/json; charset=UTF-8"})
    public String t1(){
        return "你好 ResponseBody";
    }

    @ResponseBody
    @RequestMapping(value = "t2",produces={" application/json; charset=UTF-8"})
    public String t2(){
        return "Hello ResponseBody";
    }

    @ResponseBody
    @RequestMapping(value = "jackson/{id}",produces={" application/json; charset=UTF-8"})
    public User testJackSon(@PathVariable(name = "id")int id){
        User user = userService.selectById(id);
        return user;
    }

}
