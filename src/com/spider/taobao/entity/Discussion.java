package com.spider.taobao.entity;

import java.util.Date;

public class Discussion {
    private Long id;
    private String title;
    private String url;
    private Long userId;
    private String userName;
    private Date time;
    private Integer replyNum;
    private Integer readNum;
    private Long lastReplyId;
    private String lastReplyName;
    private Date lastReplyTime;
    private Boolean parsed;
    
    public Discussion() {
        parsed=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Long getLastReplyId() {
        return lastReplyId;
    }

    public void setLastReplyId(Long lastReplyId) {
        this.lastReplyId = lastReplyId;
    }

    public String getLastReplyName() {
        return lastReplyName;
    }

    public void setLastReplyName(String lastReplyName) {
        this.lastReplyName = lastReplyName;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Boolean isParsed() {
        return parsed;
    }

    public void setParsed(Boolean parsed) {
        this.parsed = parsed;
    }
    
}
