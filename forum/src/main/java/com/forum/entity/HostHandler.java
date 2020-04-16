package com.forum.entity;

import org.springframework.stereotype.Component;

@Component
public class HostHandler {
    //开启本地线程，只放用户值，保证并发时线程不会乱
    private ThreadLocal<User> local = new ThreadLocal<>();

    public User getUser() {
        return local.get();
    }

    public void setUser(User user) {
        this.local.set(user);
    }

    public void clear(){
        this.local.remove();
    }
}
