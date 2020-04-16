package com.forum.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JSONUtil {
    public static String toJSONString(Integer code){
        JSONObject root = new JSONObject();
        root.put("code",code);
        return root.toJSONString();
    }
    public static String toJSONString(Integer code,String message){
        JSONObject root = new JSONObject();
        root.put("code",code);
        root.put("message",message);
        return root.toJSONString();
    }
    public static String toJSONString(Integer code, Map<String,Object> map){
        JSONObject root = new JSONObject();
        root.put("code",code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            root.put(entry.getKey(),entry.getValue());
        }
        return root.toJSONString();
    }
}
