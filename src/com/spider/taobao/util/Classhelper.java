/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GW_2
 */
public class Classhelper {
    
    public Classhelper(){
    }
    
    /**
     * 
     * @param className 类路径的名字
     * @return 返回根据className指明的类信息
     */
    public static Class getclass(String className){
        Class c=null;
        try {
            c=Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    /**
     * 
     * @param name 类路径
     * @return 不带参数的反射创建对象
     */
    public static Object getInstance(String name){
        Class c=getclass(name);
        Object o=null;
        try {
             o=c.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }

    /**
     * 
     * @param name 类路径
     * @param classParas Class类信息参数列表
     *  如果是基本数据类型是可以使用其Tpye类型，如果用class字段是无效的
     *  如果是非数据类型可以使用的class字段来创建其Class类信息对象，这些都要遵守。
     * @param paras      实际参数列表数据
     * @return           返回Object引用的对象，实际实际创建出来的对象，如果要使用可以强制转换为自己想要的对象
     * 
     * 带参数的反射创建对象
     */
    public static Object getInstance(String name,Class classParas[],Object paras[]){
        Object o=null;
        try {
            Class c=getclass(name);
            Constructor con=c.getConstructor(classParas);//获取使用当前构造方法来创建对象的Constructor对象，用它来获取构造函数的一些
            try {
                //信息
                o=con.newInstance(paras);//传入当前构造函数要的参数列表
            } catch (InstantiationException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return o;//返回这个用Object引用的对象
    }    
    
    /**
     * 
     * @param name 类路劲
     * @param paras    实际参数列表数据
     * @return         返回Object引用的对象，实际实际创建出来的对象，如果要使用可以强制转换为自己想要的对象
     * 
     * 带参数的反射创建对象
     */
    public static Object getInstance(String name,Object paras[]){
        Object o=null;
        try {
            Class c=getclass(name);
            int len=paras.length;
            Class classParas[]=new Class[len];
            for(int i=0;i<len;++i){
                classParas[i]=paras[i].getClass();//返回类信息
                System.out.println("classParas[i]="+classParas[i]);
            }
            Constructor con=c.getConstructor(classParas);//获取使用当前构造方法来创建对象的Constructor对象，用它来获取构造函数的一些
            try {
                //信息
                o=con.newInstance(paras);//传入当前构造函数要的参数列表
            } catch (InstantiationException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Classhelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return o;//返回这个用Object引用的对象
    } 
    

}