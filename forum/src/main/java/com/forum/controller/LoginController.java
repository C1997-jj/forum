package com.forum.controller;

import com.forum.service.UserService;
import com.forum.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录控制类
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register/",method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("tel")String tel, @RequestParam("password")String password, HttpServletResponse response){
        try {
            Map<String,Object> map = userService.register(tel,password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setMaxAge(60*24*12);
                cookie.setPath("/");
                response.addCookie(cookie);
                return JSONUtil.toJSONString(200,map);
            }else {
                return JSONUtil.toJSONString(500,map);
            }
        } catch (Exception e) {
            System.out.println(e+"------------");
            return JSONUtil.toJSONString(500,"注册失败");
        }
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(path = "/user/logout/",method = RequestMethod.GET)
    @ResponseBody
    public String logout(){
        try {
            Map<String,Object> map = userService.logout();
            return JSONUtil.toJSONString(200,map);
            }catch (Exception e) {
            return JSONUtil.toJSONString(500,"退出失败");
        }
    }
    @RequestMapping(path = "/login/",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("tel")String tel, @RequestParam("password")String password, HttpServletResponse response){
        try {
            Map<String,Object> map = userService.login(tel,password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setMaxAge(60*24*12);
                cookie.setPath("/");
                response.addCookie(cookie);
                return JSONUtil.toJSONString(200,map);
            }else {
                return JSONUtil.toJSONString(500,map);
            }
        } catch (Exception e) {
            System.out.println(e);
            return JSONUtil.toJSONString(500,"登录失败");
        }
    }
}
