package com.spider.taobao.dao;

import com.spider.taobao.entity.BPUser;
import com.spider.taobao.entity.Discussion;
import com.spider.taobao.entity.DiscussionListConfig;
import com.spider.taobao.entity.Focus;
import com.spider.taobao.entity.UserListConfig;
import com.spider.taobao.entity.MemListConfig;
import com.spider.taobao.entity.Member;
import com.spider.taobao.entity.DynamicMem;
import com.spider.taobao.entity.ReplyerInfo;
import com.spider.taobao.util.DateUtil;
import com.spider.taobao.util.HibernateService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserListDao extends HibernateService{    

    public UserListDao() {
        super();
    }
    
    public UserListConfig getUserListConfig(){
        UserListConfig ulc=(UserListConfig) get(UserListConfig.class, 0);
        return ulc;
    }
    
    public DiscussionListConfig getDiscussionListConfig(){
        DiscussionListConfig dlc=(DiscussionListConfig) get(DiscussionListConfig.class, 0);
        return dlc;
    }
    
    public MemListConfig getMemListConfig(){
        MemListConfig mlc=(MemListConfig) get(MemListConfig.class,0);
        return mlc;
    }
    
    public List<BPUser> getBPUList(){
        List<BPUser> bpuList=new ArrayList<BPUser>();
        
        if(sess!=null){
            tran= sess.beginTransaction();
            
            Date today=new Date(DateUtil.getStringByDate(new Date()));
            String hql=String.format("SELECT BPU FROM BPUser BPU WHERE BPU.parseDate is null OR BPU.parseDate<:date");
            
            bpuList=sess.createQuery(hql).setDate("date", today).list();
            try{
                tran.commit();                
            }catch(Exception e){
                System.out.println("Failed to query BPUList' infomation!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start UserListDao first!");
        }
        
        return bpuList;
    }
    
    public List<Focus> getFList(Long id){
        List<Focus> flist=new ArrayList<Focus>();
        if(sess!=null){
            tran= sess.beginTransaction();            
            
            String hql=String.format("SELECT F FROM Focus F WHERE  F.id=%d ",id);
            
            flist=sess.createQuery(hql).list();
            try{
                tran.commit();                
            }catch(Exception e){
                System.out.println("Failed to query BPUList' infomation!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start UserListDao first!");
        }
        return flist;
    }

    public List<ReplyerInfo> getReplyerList(){
        List<ReplyerInfo> riList = new ArrayList<ReplyerInfo>();
   
        if(sess!=null){
            tran= sess.beginTransaction();
            
            String hql=String.format("SELECT R FROM ReplyerInfo R WHERE  R.shopUrl IS NOT NULL AND R.realUrl IS NULL");
            //只将有回复且未被处理过（Parsed=0）的发帖者选出
            riList=sess.createQuery(hql).list();
            try{
                tran.commit();                
            }catch(Exception e){
                System.out.println("Failed to query BPUList' infomation!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start UserListDao first!");
        }
        
        return riList;        
    }
    
    public List<Discussion> getDiscussionList(){
        List<Discussion> dList=new ArrayList<Discussion>();
        
        if(sess!=null){
            tran= sess.beginTransaction();
            
            String hql=String.format("SELECT D FROM Discussion D WHERE D.parsed=0 AND D.replyNum>0");//D.parsed=0 AND
            //只将有回复且未被处理过（Parsed=0）的帖子选出
            dList=sess.createQuery(hql).list();
            try{
                tran.commit();                
            }catch(Exception e){
                System.out.println("Failed to query BPUList' infomation!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start UserListDao first!");
        }
        
        return dList;
    }
    
    public List<DynamicMem> getMemberList() {
        List<DynamicMem> memList = new ArrayList<DynamicMem>();

        if (sess != null) {
            tran = sess.beginTransaction();

            Date today = new Date(DateUtil.getStringByDate(new Date()));
            String hql = String.format("SELECT M FROM DynamicMem M WHERE M.parseDate is null OR M.parseDate<:date");
            memList = sess.createQuery(hql).setDate("date", today).list();
            try {
                tran.commit();
            } catch (Exception e) {
                System.out.println("Failed to query MemList' infomation!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Please Start UserListDao first!");
        }

        return memList;
    }
    
//    public List<DynamicMem> getDynamicMem(){
//        List<DynamicMem> DmList=new ArrayList<DynamicMem>();
//        
//        if(sess!=null){
//            tran= sess.beginTransaction();
//            Date today = new Date(DateUtil.getStringByDate(new Date()));
//            String hql=String.format("SELECT M FROM DynamicMem M WHERE M.parseDate is null OR M.parseDate<:date");
//           
//            DmList=sess.createQuery(hql).list();
//            try{
//                tran.commit();                
//            }catch(Exception e){
//                System.out.println("Failed to query BPUList' infomation!");
//                e.printStackTrace();
//            }
//        }else{
//            System.out.println("Please Start UserListDao first!");
//        }
//        
//        return DmList;
//    }
}
