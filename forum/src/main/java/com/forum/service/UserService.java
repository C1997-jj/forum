package com.forum.service;

import com.forum.dao.LoginTicketDao;
import com.forum.dao.UserDao;
import com.forum.entity.HostHandler;
import com.forum.entity.LoginTicket;
import com.forum.entity.User;
import com.forum.utils.Md5Util;
import com.forum.utils.UuidUtil;
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

    @Autowired
    private HostHandler hostHandler;

    /**
     * 注册
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
        if (password.length() < 6){
            map.put("password","密码至少需要六位数");
            return map;
        }
        User user = userDao.getUserByTel(tel);
        if (user != null){
            map.put("tel","手机号已经注册");
            return map;
        }
        user = new User();
        String salt = UuidUtil.getRandomUUID().substring(0,6);
        user.setSalt(salt);
        user.setTel(tel);
        user.setPassword(Md5Util.encodeByMd5(password+salt));
        userDao.addUser(user);
        String ticket = addTicket(tel);
        map.put("ticket",ticket);
        return map;
    }
    private String addTicket(String tel){
        String ticket = UuidUtil.getRandomUUID();
        long expired = new Date().getTime() + 1000*3600*24*10;
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTel(tel);
        loginTicket.setTicket(ticket);
        loginTicket.setExpired(new Date(expired));
        loginTicketDao.addLoginTicket(loginTicket);
        return ticket;
    }

    /**
     * 登录
     * @param tel
     * @param password
     * @return
     * @throws Exception
     */
    public Map<String, Object> login(String tel, String password) throws Exception {
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
        if (password.length() < 6){
            map.put("password","密码至少需要六位数");
            return map;
        }
        User user = userDao.getUserByTel(tel);
        if (user == null){
            map.put("tel","手机号未注册");
            return map;
        }
        if (!user.getPassword().equals(Md5Util.encodeByMd5(password + user.getSalt()))){
            map.put("password","用户名或密码错误");
            return map;
        }
        LoginTicket loginTicket = loginTicketDao.getLoginTicketByTel(tel);
        updateLoginTicketStatus(0,tel);
        updateLoginTicketExpired(tel);
        map.put("ticket",loginTicket.getTicket());
        return map;
    }

    /**
     * 更新状态码
     * @param status
     * @param tel
     */
    private void updateLoginTicketStatus(Integer status, String tel){
        loginTicketDao.updateLoginTicketStatus(tel, status);
    }

    /**
     * 更新令牌时间
     * @param tel
     */
    private void updateLoginTicketExpired(String tel){
        long expired = new Date().getTime() + 1000 * 3600 * 24 * 10;
        Date time = new Date(expired);
        loginTicketDao.updateLoginTicketExpired(tel, time);
    }

    public Map<String, Object> logout() {
        String tel = hostHandler.getUser().getTel();
        updateLoginTicketStatus(1,tel);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("message","退出成功");
        return map;
    }
}
