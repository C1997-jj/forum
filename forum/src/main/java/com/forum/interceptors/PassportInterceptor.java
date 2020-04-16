package com.forum.interceptors;

import com.forum.dao.LoginTicketDao;
import com.forum.dao.UserDao;
import com.forum.entity.HostHandler;
import com.forum.entity.LoginTicket;
import com.forum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 判断用户是否登录拦截器
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHandler hostHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String ticket = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")){
                ticket = cookie.getValue();
                break;
            }
        }
        if (ticket == null){
            return true;
        }
        LoginTicket loginTicket = loginTicketDao.getLoginTicketByTicket(ticket);
        if (loginTicket == null || loginTicket.getStatus() != 0 || loginTicket.getExpired().before(new Date())){
            return true;
        }
        User user = userDao.getUserByTel(loginTicket.getTel());
        hostHandler.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHandler.clear();
    }
}
