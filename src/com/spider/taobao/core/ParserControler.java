package com.spider.taobao.core;

import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.Discussion;
import com.spider.taobao.entity.DynamicMem;
import com.spider.taobao.entity.ReplyerInfo;
import com.spider.taobao.parser.DiscussionListParser;
import com.spider.taobao.parser.DiscussionParser;
import com.spider.taobao.parser.DynamicMemParser;
import com.spider.taobao.parser.MemListParser;
import com.spider.taobao.parser.UserInfoParser;
import com.spider.taobao.parser.UserListParser;
import com.spider.taobao.util.ThreadPool;

import test.Classhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserControler {
    UserListDao uld;
    
    public ParserControler(){
        uld = new UserListDao();
    }
    
    public void executeUserListParser(){
        UserListParser ulp=new UserListParser();
        ulp.start();
        try {
            ulp.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ParserControler.class.getName()).log(Level.SEVERE, null, ex);
        } //抓取用户列表
    }
    public void executeUserInfoParser(){
        UserInfoParser uip=new UserInfoParser();
        uip.start();
        try {
            uip.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ParserControler.class.getName()).log(Level.SEVERE, null, ex);
        }  //抓取用户详细信息
    }
    
    public void executeDiscussionListParser(){
        DiscussionListParser dlp = new DiscussionListParser();
        dlp.start();
        try {
            dlp.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ParserControler.class.getName()).log(Level.SEVERE, null, ex);
        }  //抓取帖子列表
    }
    
    public void executeDiscussionInfoParser(){
        int threadSize = 10;
        
        //多线程任务分割
        List<Discussion> dList;
        dList = uld.getDiscussionList();;//对尚未处理（抓取时间为空）或者尚未在当下处理过（处理时间小于现在时间）的用户记录进行处理        
        assignWork(threadSize, dList, "com.spider.taobao.parser.DiscussionParser");
    }
    
     public void executeMemListParser(){
        MemListParser mlp=new MemListParser();
        mlp.start();
        try {
            mlp.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ParserControler.class.getName()).log(Level.SEVERE, null, ex);
        }     //抓取动态用户列表
         
     }
     
    public void executeDynamicMemParser(){
        int threadSize = 10;
        
        //多线程任务分割
        List<DynamicMem> memList;
        memList = uld.getMemberList();//对尚未处理（抓取时间为空）或者尚未在当下处理过（处理时间小于现在时间）的用户记录进行处理        
        assignWork(threadSize, memList, "com.spider.taobao.parser.DynamicMemParser");
    }
    
    public void executeReplyerInfoParser(){
        int threadSize = 5;
        
        //多线程任务分割
        List<ReplyerInfo> riList;
        riList = uld.getReplyerList();
        assignWork(threadSize, riList, "com.spider.taobao.parser.ReplyerInfoParser");
    }    
    
//    public void stopDynamicMemParser(){
////        DynamicMemPool.shutdownNow();
//    } 
    
    private <T> void assignWork(int threadSize, List<T> workList, String parserClassName){
        Thread[] td = new Thread[threadSize];
            
        //多线程任务分割
        int listSize, blockSize;
        listSize = workList.size();
        blockSize = listSize/threadSize;  
        if(blockSize == 0){
            blockSize = 1;
            threadSize = listSize;
        }
        for(int i=0; i<threadSize; i++){
            List<T> subList = new ArrayList<T>();
            subList.addAll(workList.subList(i*blockSize,(listSize-(i+1)*blockSize)>=blockSize ? (i+1)*blockSize :listSize));
            System.out.println(subList.size()+"/"+listSize+":"+i*blockSize+"-"+((listSize-(i+1)*blockSize)>=blockSize ? (i+1)*blockSize :listSize));
            if(subList.size()>0){
                Object paras[]= new Object[1];
                paras[0]=subList;
                td[i] = (Thread)Classhelper.getInstance(parserClassName, paras);
                td[i].start();                
            }
            else{
                td[i] = null;
            }
        }

        for(int i=0; i<threadSize; i++){
            try{
                if(td[i] != null) td[i].join();                
            } catch (InterruptedException ex) {
                Logger.getLogger(ParserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }    
}
        
    
    
        
        
