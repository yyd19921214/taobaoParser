/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.entity;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class DynamicMem {
    private String Nickname;//昵称
    private long ID;//成员ID
    private String Url;//成员主页
    private Date parseDate;//抓取时间（更新时用）
    public DynamicMem() {

    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
    
     public Date getParseDate(){
        return parseDate;
    }
    
    public void setParseDate(Date date){
        this.parseDate=date;
    }
}
