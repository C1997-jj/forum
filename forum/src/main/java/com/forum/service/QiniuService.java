package com.forum.service;

import com.alibaba.fastjson.JSONObject;
import com.forum.utils.FileUtil;
import com.forum.utils.UuidUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class QiniuService {

	 //设置好账号的ACCESS_KEY和SECRET_KEY
	private String ACCESS_KEY = "3jst1NgIks-WdFLMPigMbNQ9hpq0KXfF-vZ8vAjb";
	private String SECRET_KEY = "1K-jeNFR5wUzMKJ471s-838RJ5D71nKysc3aHVjk";
    //要上传的空间
    private String bucketname = "fwb1";
    
    //密钥配置
    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private Configuration configuration = new Configuration(Zone.zone1());
    //创建上传对象
    private UploadManager uploadManager = new UploadManager(configuration);
    //配置域名
    private static String QINIU_IMAGE_DOMAIN = "q0oruvejk.bkt.clouddn.com";
    
    //简单上传,使用默认策略,只需要设置上传的空间名就可以了
    private String getUpToken() {
        return auth.uploadToken(bucketname);
    }
    
    //上传图片
    public Map<String, Object>  saveImage(MultipartFile file) throws IOException {
    	Map<String, Object> map = new HashMap<>();
    	try {
        	int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
            	map.put("fileType", "非法文件");
            	return map;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!FileUtil.isFileAllowed(fileExt)) {
            	map.put("fileType", "文件类型不支持");
                return map;
            }
            
            String fileName = UuidUtil.getRandomUUID() + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
            	String imageUrl = JSONObject.parseObject(res.bodyString()).getString("key");
            	map.put("file", imageUrl);
            }
            return map;
        } catch (QiniuException e) {
        	map.put("1", "发布失败");
            return map;
        }
    }
    
    //上传图片
    public Map<String, Object> saveImage(MultipartFile[] files) throws IOException {
    	Map<String, Object> map = new HashMap<>();
    	StringBuffer stringBuffer = new StringBuffer();
    	try {
            for(MultipartFile file : files) {
            	int dotPos = file.getOriginalFilename().lastIndexOf(".");
                if (dotPos < 0) {
                	continue ;
                }
                String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
                if (!FileUtil.isFileAllowed(fileExt)) {
                    continue ;
                }
                
                String fileName = UuidUtil.getRandomUUID() + "." + fileExt;
                //调用put方法上传
                Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
                //打印返回的信息
                if (res.isOK() && res.isJson()) {
                	String imageUrl = JSONObject.parseObject(res.bodyString()).getString("key");
                	stringBuffer.append(imageUrl + "-");
                	System.out.println("图片文件" + imageUrl);
                } else {
                    continue ;
                }
            }
            map.put("fileList", stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString());
            return map;
        } catch (QiniuException e) {
        	map.put("1", "发布失败");
            return map;
        }
    }
    
    
	
    //获取图片路径
    private String dealImageUrl(String file) {
    	try {
    		String fileName = "http://" + QINIU_IMAGE_DOMAIN + "/" + file;
        	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        	long expireInSeconds = 3600 * 24 * 60;//2个月，可以自定义链接过期时间
        	return auth.privateDownloadUrl(fileName, expireInSeconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    //处理单张图片
    public String dealOnlyImage(String filePath) {
    	return dealImageUrl(filePath);
    }
    
}
