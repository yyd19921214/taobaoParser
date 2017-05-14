package com.spider.taobao.entity;

import java.util.Date;

public class Focus {
    private Long id;
    private Long uid;
    private Long fid;
    private String fname;
    private Date date;
    
    public Focus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }    

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }    
}
