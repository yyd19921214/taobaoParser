package com.spider.taobao.entity;

import java.util.Date;

public class DiscussionListConfig {
    private Integer configId;
    private Integer finishPage;
    private Integer maxPage;
    private Integer discussionNum;
    private Date date;

    public DiscussionListConfig() {
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

    public Integer getDiscussionNum() {
        return discussionNum;
    }

    public void setDiscussionNum(Integer discussionNum) {
        this.discussionNum = discussionNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
