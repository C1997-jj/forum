package com.forum.service;

import com.forum.dao.LoginTicketDao;
import com.forum.dao.UserDao;
import com.forum.entity.LoginTicket;
import com.forum.entity.User;
import com.forum.utils.ForumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * user逻辑层
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    /**
     * 登录逻辑层
     * @param tel
     * @param password
     * @return
     * @throws Exception
     */
    public Map<String, Object> register(String tel, String password) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        if (tel == null || "".equals(tel)){
            map.put("tel","手机不能为空");
            return map;
        }
        if (tel.length() != 11){
            map.put("tel","手机号不正确");
            return map;
        }
        if (password == null || "".equals(password)){
            map.put("password","密码不能为空");
            return map;
        }
        if (password.length() != 6){
            map.put("password","密码至少需要六位数");
            return map;
        }
        User user = userDao.getUserByTel(tel);
        if (user !=null){
            map.put("tel","手机号已经注册");
            return map;
        }
        user = new User();
        String salt = ForumUtils.getRandomUUID().substring(0,6);
        user.setSalt(salt);
        user.setTel(tel);
        user.setPassword(ForumUtils.encodeByMd5(password+salt));
        userDao.addUser(user);
        String ticket = addTicket(tel);
        map.put("ticket",ticket);
        return map;
    }
    private String addTicket(String tel){
        String ticket = ForumUtils.getRandomUUID();
        long expired = new Date().getTime() + 1000*3600*24*10;
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTel(tel);
        loginTicket.setTicket(ticket);
        loginTicket.setExpired(new Date(expired));
        loginTicketDao.addLoginTicket(loginTicket);
        return ticket;
    }
}
