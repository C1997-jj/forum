package com.forum.controller;

import com.forum.service.QiniuService;
import com.forum.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class UploadImageController {

    @Autowired
    private QiniuService qiniuService;

    /**
     * 图片上传
     * @return
     */
    @RequestMapping(path = "/user/upload/",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file){
        try {
            Map<String,Object> map = qiniuService.saveImage(file);
            return JSONUtil.toJSONString(200,map);
        }catch (Exception e) {
            return JSONUtil.toJSONString(500,"退出失败");
        }
    }
}
