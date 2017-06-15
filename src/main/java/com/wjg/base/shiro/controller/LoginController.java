package com.wjg.base.shiro.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wjg on 2017/5/19.
 * shiro login handler
 */
@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("loginCheck")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam(value = "remember",required = false) String remember){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            boolean rememberMe = StringUtils.isNotEmpty(remember) && "1".equals(remember);
            UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe);
            try{
                currentUser.login(token);
            }catch (AuthenticationException e){
                logger.error(e.toString(),e);
                return "/login";
            }
        }
        return "redirect:/index.html";
    }

    @RequestMapping("login")
    public String toLogin(){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated() || currentUser.isRemembered()){
            return "redirect:/index.html";
        }
        return "/login";
    }
}
