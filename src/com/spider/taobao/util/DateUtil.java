package com.spider.taobao.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int compare(Date _x,Date _y){
        int rs=0;
        
        Date initDate=new Date("1970/1/1");
        
        Calendar cx=Calendar.getInstance();
        cx.setTime(initDate);
        cx.set(Calendar.YEAR, _x.getYear());
        cx.set(Calendar.MONTH, _x.getMonth());
        cx.set(Calendar.DAY_OF_MONTH, _x.getDate());
        
        Calendar cy=Calendar.getInstance();
        cy.setTime(initDate);
        cy.set(Calendar.YEAR, _y.getYear());
        cy.set(Calendar.MONTH, _y.getMonth());
        cy.set(Calendar.DAY_OF_MONTH, _y.getDate());
        
        rs=cx.compareTo(cy);
        
        return rs;
    }
    
    public static String getStringByDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String dateStr=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);
        return dateStr;
    }
    
    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.MONTH)+1);
    }
    
    public static int getDAY(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static Date add(Date date,int field,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field,num);
        return calendar.getTime();
    }
}
