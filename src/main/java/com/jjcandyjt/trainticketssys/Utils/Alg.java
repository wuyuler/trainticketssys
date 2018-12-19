package com.jjcandyjt.trainticketssys.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alg {

    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdtf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static short AUTO_INC=0;
    public static final int MAX_MILE=5600;
    public static double[] PRICE_TABLE=new double[MAX_MILE+1];

    public static String getOno(String otime,String Uno,boolean c){
        String head=c?"1":"0";
        return head+otime.replace(" ","")+Uno+Short.toString(AUTO_INC++);
    }
    public static double dist2price(int dist){
        if(dist>2500) return 0.029305*(dist-2500)+106.6702;
        else if(dist>1500) return 0.035166*(dist-1500)+71.5042;
        else if(dist>1000) return 0.041027*(dist-1000)+50.9907;
        else if(dist>500) return 0.046888*(dist-500)+27.5467;
        else if(dist>200) return 0.052749*(dist-200)+11.722;
        else return 0.05861*dist;
    }
    public static void init(){
        PRICE_TABLE[0]=0;
        for(int i=0,dist=0,index=1,t=20,sk=10;i<10;i++,sk+=10,t=10){
            for(int j=0;j<t;j++,dist+=sk){
                double temp=dist2price(dist+sk/2);
                for(int k=0;k<sk;k++) PRICE_TABLE[index++]=temp;
            }
        }
    }
    public static double mile2price(int mile,int ano,char thead,double price){
        mile=MAX_MILE>mile?mile:MAX_MILE;
        if(thead=='D') return price*0.30855*mile;
        if(thead=='G') return price*0.485*mile;
        double muti = 0.2+price + (ano > 0 ? 0.25 : 0);
        if(thead=='K'||thead=='T') muti+=0.2;
        double base = muti * PRICE_TABLE[mile];
        if (ano == 2) return 1.5 * base;
        else return base;
    }
    public static String addMinute(Date d,int minute){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE,minute);
        return sdtf.format(calendar.getTime());
    }

    public static void main(String[] args) {
        Date now= Calendar.getInstance().getTime();
        System.out.println(sdf.format(now));
    }
    public static String date2format(String date){
        return date.replace("T"," ").substring(0,16);
    }
}
