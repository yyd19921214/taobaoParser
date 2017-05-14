package com.spider.taobao.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateService {
    protected Configuration conf;
    protected SessionFactory sf;
    protected Session sess;
    protected Transaction tran;
    
    public HibernateService(){
        conf=null;
        sf=null;
        sess=null;
        tran=null;
        
        startService();
    }
    
    public void startService(){
        conf=new Configuration().configure();
        sf=conf.buildSessionFactory();
        sess=sf.openSession();
    } 
    
    public void stopService(){
        if(sess!=null){
            sess.close();
        }
    }
    
    public boolean persist(Object object){
        boolean rs=false;
        if(sess!=null){
            sess.persist(object);
        }else{
            System.out.println("Please Start HibernateService first!");
        }
        return rs;
    }

    public boolean insert(Object object){
        boolean rs=false;
        if(sess!=null){
            tran= sess.beginTransaction();            
            sess.save(object);
            try{                
                tran.commit();
                rs=true;
            }catch(Exception e){
                System.out.println("Rolling back...");
                tran.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start HibernateService first!");
        }
        return rs;
    }    
    
    public boolean save(Object object){
        boolean rs=false;
        if(sess!=null){
            tran= sess.beginTransaction();            
            sess.saveOrUpdate(object);
            try{                
                tran.commit();
                rs=true;
            }catch(Exception e){
                System.out.println("Rolling back...");
                tran.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start HibernateService first!");
        }
        return rs;
    }
    
    public Object get(Class theClass,Serializable id){
        Object object=null;
        if(sess!=null){        
            tran= sess.beginTransaction();            
            object=sess.get(theClass, id);
            try{
                tran.commit();
            }catch(Exception e){
                System.out.println("Rolling back...");
                tran.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start HibernateService first!");
        }
        return object;
    }
    
    public void evict(Object object){
        if(sess!=null){        
            sess.evict(object);
        }else{
            System.out.println("Please Start HibernateService first!");
        }
    }
    
    public void delete(Object object){
        if(sess!=null){        
            sess.delete(object);
        }else{
            System.out.println("Please Start HibernateService first!");
        }
    }
    
    public void cleanDB(){
        if(sess!=null){
            tran= sess.beginTransaction();           
            String hql= "drop Table Product\n"
                      + "drop Table SellerEx\n";
            sess.createSQLQuery(hql).executeUpdate();
            try{
                tran.commit();    
                System.out.println("DB has been cleaned!");
            }catch(Exception e){
                System.out.println("Rolling back...");
                tran.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("Please Start HibernateService first!");
        }
    }
}
