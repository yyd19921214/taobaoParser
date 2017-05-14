package com.spider.taobao.entity;

public class UserListConfig {
    private Integer configId;
    private Integer finishPage;
    private Integer maxPage;
    private Integer usernum;

    public UserListConfig() {
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

    public Integer getUsernum() {
        return usernum;
    }

    public void setUsernum(Integer usernum) {
        this.usernum = usernum;
    }
    
    
}
