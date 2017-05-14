package com.spider.taobao.entity;

import java.util.Date;

public class BPUser {
    private Long id;
    private String name;
    private Date joinDate;
    private String url;
    private String Tag;
    private String focusUrl;
    private String gender;
    private String area;
    private Integer fansNum;
    private Integer focusNum;
    private Integer visitNum;
    private Date parseDate;
    
    public BPUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(Integer focusNum) {
        this.focusNum = focusNum;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    public Date getParseDate() {
        return parseDate;
    }

    public void setParseDate(Date parseDate) {
        this.parseDate = parseDate;
    }
    
    public String getTag(){
        return Tag;
    }
    
    public void setTag(String Tag){
        this.Tag=Tag;
    }
    
    public String getFocusUrl(){
        return focusUrl;
    }
    
    public void setFocusUrl(String focusurl){
        this.focusUrl=focusurl;
    }
    
}
