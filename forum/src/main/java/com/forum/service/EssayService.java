package com.forum.service;

import com.forum.dao.EssayDao;
import com.forum.entity.Essay;
import com.forum.entity.HostHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;

public class EssayService {

    @Autowired
    private SenstiveService senstiveService;

    @Autowired
    private EssayDao essayDao;

    @Autowired
    private HostHandler hostHandler;

    public Map<String, Object> addEssay(Integer type, String content, String images) {
        Map<String,Object> map = new HashMap<>();
        Integer userID = getThreadUserID();
        content = HtmlUtils.htmlEscape(content);
        content = senstiveService.filter(content);
        Essay essay = new Essay();
        essay.setUserID(userID);
        essay.setContent(content);
        essay.setType(type);
        essay.setImages(images);
        essayDao.addEssay(essay);
        map.put("message","发送成功");
        return map;
    }

    /**
     * 获取线程Id
     * @return
     */
    private Integer getThreadUserID(){
        return hostHandler.getUser().getId();
    }
}
