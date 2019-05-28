package com.shiro.controller;

import com.shiro.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "test";
    }

    @RequestMapping("/add")
    public String add(){
        return "add";
    }

    @RequestMapping("/update")
    public String update(){
        return "update";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/unAuth")
    public String unAuth(){
        return "unAuth";
    }
    /**
     *
     * @param username      用户名
     * @param password  密码
     * @param model     model用于存放登录失败的msg
     * @return
     */
    @RequestMapping("/loginAction")
    public String loginAction(String username, String password, Model model){
        /*
            登录过程分为三步：
            1、获取用户的Subject
            2、封装用户数据
            3、执行登录方法
         */
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            //login的请求会交给Realm类处理
            subject.login(token);
            return "test";
        }catch(UnknownAccountException e){
            //UnknownAccountException代表用户不存在
            model.addAttribute("msg","用户名不存在");
            return "/login";
        }catch(IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "/login";
        }
    }

    @RequestMapping("/registerAction")
    public String registerAction(String name,String password,Model model){
        userService.register(name,password);
        //注册后登录跳转
        return loginAction(name,password,model);
    }
    /*
    shiro无需配置/logout的处理请求，直接访问/logout,不需要具体实现
    @RequestMapping("/doLogout")
    public String logout(){
        return "login";
    }
    */

}
