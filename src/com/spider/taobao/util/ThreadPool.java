package com.spider.taobao.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
    private int size=5;
    private ExecutorService pool=null;
    
    public ThreadPool(){        
        pool=Executors.newFixedThreadPool(size);
    }
    
    public ThreadPool(int size){
        if(size<=0){
            System.out.println("Wrong pool size \t"+size+"\t!");
            System.exit(-1);
        }
        this.size=size;
        pool=Executors.newFixedThreadPool(this.size);
    }
    
    public Future<?> submit(Runnable r){
        if(pool!=null){
            return pool.submit(r);
        }else{
            System.out.println("ThreadPool is null!");
        }
        return null;
    }
    
    public void shutdown(){
        if(pool!=null){
            pool.shutdown();
        }else{
            System.out.println("ThreadPool is null!");
        }
    }

    public int getSize() {
        return size;
    }
}
