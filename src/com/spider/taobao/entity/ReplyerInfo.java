/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.taobao.entity;

/**
 *
 * @author Administrator
 */
public class ReplyerInfo {

    private Long replyId;
    private String replyName;
    private String shopUrl;
    private String realUrl;
    private boolean isShopOwner;
    
    public ReplyerInfo() {
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

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    } 
    
    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }   
    
    public boolean getIsShopOwner(){
        return isShopOwner;
    }
    
    public void setIsShopOwner(boolean isShopOwner){
        this.isShopOwner = isShopOwner;
    }
}
