/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.entity;

/**
 *
 * @author user
 */
public class MemListConfig {
    private Integer configId;
    private Integer finishPage;
    private Integer maxPage;
    private Integer memnum;
    public MemListConfig(){
        
    }
    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getFinishPage() {
        return finishPage;
    }

    public void setFinishPage(Integer finishPage) {
        this.finishPage = finishPage;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public Integer getMemnum() {
        return memnum;
    }

    public void setMemnum(Integer memnum) {
        this.memnum = memnum;
    }
}
