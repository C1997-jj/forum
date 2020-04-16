package com.forum.controller;

import com.forum.service.EssayService;
import com.forum.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 发布控制层
 */
@Controller
public class EssayController {

    @Autowired
    private EssayService essayService;

    @RequestMapping(path = "/user/addEssay/",method = RequestMethod.POST)
    @ResponseBody
    public String addEssay(@RequestParam("type")Integer type,@RequestParam("content")String content,@RequestParam("images")String images){
        try {
            Map<String,Object> map = essayService.addEssay(type,content,images);
            return JSONUtil.toJSONString(200,map);
        }catch (Exception e) {
            return JSONUtil.toJSONString(500,"发布失败");
        }
    }
}
