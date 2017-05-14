package com.spider.taobao.entity;

import java.util.Date;

public class DiscussionReply {
    private Long id;
    private Long dId;
    private String refName;
    private Long replyId;
    private String replyName;
    private Date replyDate;

    public DiscussionReply() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }    
    
    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
    
    
}
