/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.entity;

import java.util.Date;

/**
 *
 * @author user
 */
public class Member {
    private String Nickname;//昵称
    private long ID;//成员ID
    private String Url;//成员主页
    private int Credits;;//积分
    private int PostNum;//帖子数量
    private int EssenceNum;//精华帖数量
    private Date parseDate;//抓取时间（更新时用）
   // private String[] BPName;//加入帮派的名称
    public Member() {

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

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int Credits) {
        this.Credits = Credits;
    }

    public int getPostNum() {
        return PostNum;
    }

    public void setPostNum(int PostNum) {
        this.PostNum = PostNum;
    }

    public int getEssenceNum() {
        return EssenceNum;
    }

    public void setEssenceNum(int EssenceNum) {
        this.EssenceNum = EssenceNum;
    }
    
    public Date getParseDate(){
        return parseDate;
    }
    
    public void setParseDate(Date date){
        this.parseDate=date;
    }

}
