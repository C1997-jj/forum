package com.forum.entity;

import java.util.Date;

public class Essay {
    private Integer id;
    private Integer userID;//作者
    private Integer type;
    private String content;//内容
    private String images;
    private Integer PV;//浏览量
    private Integer likeCount;//点赞量
    private Integer commentCount;//评论量
    private Integer status;//
    private Date createDate;//创建时间


    public Essay() {
        this.id = 0;
        this.userID = 0;
        this.type = 0;
        this.content = "";
        this.images = "";
        this.PV = 0;
        this.likeCount = 0;
        this.commentCount = 0;
        this.status = 0;
        this.createDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userId) {
        this.userID = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getPV() {
        return PV;
    }

    public void setPV(Integer pv) {
        this.PV = PV;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
