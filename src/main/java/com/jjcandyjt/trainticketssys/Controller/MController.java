package com.jjcandyjt.trainticketssys.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjcandyjt.trainticketssys.Utils.Alg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
@ResponseBody
public class MController {
//    @Autowired
//    private JdbcTemplate template;
//
//    public static String _Ticket="_Ticket";
//
//    @GetMapping(value="/FindTrain")
//    public List FindTrain(@RequestParam String scity,@RequestParam String ecity,@RequestParam String dtime){
//        List<Map<String,Object>> list=template.queryForList("select * from FindTrain " +
//                "where scity='"+scity+"'and ecity='"+ecity+"'and dtime between '"+dtime+"' and '"+dtime+" 23:59'");
//        for(Map<String,Object> e:list){
//            String sdate=((Timestamp)e.get("dtime")).toString();
//            String atime=((Timestamp)e.get("atime")).toString();
//            e.put("dtime",Alg.date2format(sdate));
//            e.put("atime",Alg.date2format(atime));
//        }
//        return list;
//    }
//
////    @GetMapping(value="/FindSeat")
////    public List FindSeat(@RequestParam String Tno_c, @RequestParam String sdate, @RequestParam short sSorder_c, @RequestParam short eSorder_c, @RequestParam int mile){
////        eSorder_c--;
////        List<Map<String,Object>> list=null;
////        synchronized (_Ticket){
////            list=template.queryForList("SELECT MIN(tleft) tleft,_Seat.* FROM "+_Ticket+",_Seat WHERE" +
////                    " _Seat.Seatno=_Ticket.Seatno AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c " +
////                    "BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c)+" GROUP BY _Seat.Seatno,_Seat.Seatname,_Seat.price");
////        }
////        short Ano=(short)template.queryForMap("SELECT * FROM _Train WHERE Tno='"+Tno_c+"'").get("Ano");
////        char thead=Tno_c.charAt(0);
////        for(Map<String,Object> e:list){
////            double price=Alg.mile2price(mile,Ano,thead,Double.parseDouble(e.get("price").toString()));
////            e.put("price",price);
////        }
////        return list;
////    }
//    @GetMapping(value="/FindSeat")
//    public List FindSeat(@RequestParam String Tno_c,@RequestParam short Ano_c, @RequestParam String sdate, @RequestParam short sSorder_c, @RequestParam short eSorder_c, @RequestParam int mile){
//        eSorder_c--;
//        List<Map<String,Object>> list=null;
//        synchronized (_Ticket){
//            list=template.queryForList("SELECT MIN(tleft) tleft,_Seat.* FROM "+_Ticket+",_Seat WHERE" +
//                    " _Seat.Seatno=_Ticket.Seatno AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c " +
//                    "BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c)+" GROUP BY _Seat.Seatno,_Seat.Seatname,_Seat.price");
//        }
//        char thead=Tno_c.charAt(0);
//        for(Map<String,Object> e:list){
//            double price=Alg.mile2price(mile,Ano_c,thead,Double.parseDouble(e.get("price").toString()));
//            e.put("price",price);
//        }
//        System.out.println(list);
//        return list;
//    }
//
//    @GetMapping(value="/SetSeat")
//    public boolean SetSeat(short Seatno,String Tno_c,String sdate,short sSorder_c,short eSorder_c,int count){
//        eSorder_c--;
//        boolean flag=false;
//        synchronized (_Ticket){
//            Map m=template.queryForMap("SELECT MIN(tleft) tleft FROM "+_Ticket+" WHERE" +
//                    " Seatno="+Short.toString(Seatno)+" AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c));
//            if(((int)m.get("tleft")+count)<0) flag=false;
//            template.update("UPDATE "+_Ticket+" SET tleft=tleft+"+Integer.toString(count)+ "WHERE " +
//                    "Seatno="+Short.toString(Seatno)+" AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c));
//            flag=true;
//        }
//        return flag;
//    }
//
//    @PostMapping(value="/TakeOrder")
//    public String TakeOrder(@RequestParam short Seatno,
//                             @RequestParam String Tno_c,
//                             @RequestParam String Uno,
//                             @RequestParam String sdate,
//                             @RequestParam short sSorder_c,
//                             @RequestParam short eSorder_c,
//                             @RequestParam double price,
//                             @RequestParam String sdtime,
//                             @RequestParam String eatime,
//                             @RequestParam String passergers){
//
//
//        JSONArray passergerslist= JSONObject.parseArray(passergers);
//        List<Map> passergers2=passergerslist.toJavaList(Map.class);
//        Date now=Calendar.getInstance().getTime();
//        String otime=Alg.sdtf.format(now);
//        String Ono=otime+Uno;
//        int Pcount=passergers2.size();
//        String total=Double.toString(price*Pcount);
//        if(SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,-Pcount)==false) return "fail";
//        template.update("INSERT INTO _Order VALUES('"+Ono+"','"+Uno+"',"+Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+
//                ",'"+Tno_c+"',"+Short.toString(eSorder_c)+",'"+otime+"',NULL,NULL,"+total+",'"+sdtime+"','"+eatime+"',"+Pcount+")");
//        System.out.println(passergers2);
//        for(Map e:passergers2){
//            String Pid=(String)e.get("Pid");
//            String Pname=(String)e.get("Pname");
//            String PTel=(String)e.get("PTel");
//            Integer Ptype=(Integer)e.get("Ptype") ;
//            template.update("INSERT INTO _Passenger VALUES('"+Ono+"','"+Pid+"','"+Pname+"',"+Integer.toString(Ptype)+",0,"+PTel+")");
//        }
//        return Ono;
//    }
//
//    @GetMapping(value="/PayOrder")
//    public boolean PayOrder(@RequestParam String Ono){
//        Date now=Calendar.getInstance().getTime();
//        String ptime=Alg.sdtf.format(now);
//        Map m=template.queryForMap("SELECT * FROM _Order WHERE Ono='"+Ono+"' AND ptime IS NULL");
//        if(m==null) return false;
//        template.update("UPDATE _Order SET ptime='"+ptime+"'WHERE Ono='"+Ono+"'");
//        template.update("UPDATE _Passenger SET available=1 WHERE Ono='"+Ono+"'");
//        return true;
//    }
//
//    @GetMapping(value="/CancelOrder")
//    public boolean CancelOrder(@RequestParam String Ono){
//        Date now=Calendar.getInstance().getTime();
//        String checktime=Alg.addMinute(now,120);
//        String rtime=Alg.sdtf.format(now);
//        Map m=template.queryForMap("SELECT * FROM _Order WHERE Ono='"+Ono+"' AND Ptime IS NOT NULL AND rtime IS NULL");
//        if(m==null) return false;
//        short Seatno=(short)m.get("Seatno");
//        short sSorder_c=(short)m.get("sSorder_c");
//        short eSorder_c=(short)m.get("eSorder_c");
//        String Tno_c=(String)m.get("Tno_c");
//        String sdate=m.get("sdate").toString();
//        short Pcount=(short)m.get("Pcount");
//        try {
//            m=template.queryForMap("SELECT * FROM _TSS WHERE Tno_c='"+Tno_c+"' AND sdate='"+sdate+"' AND sSorder_c="+Short.toString(sSorder_c)+" AND dtime>'"+checktime+"'");
//        }catch (Exception e){
//            return false;
//        }
//        SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,Pcount);
//        template.update("UPDATE _Passenger SET available=0 WHERE Ono='"+Ono+"'");
//        template.update("UPDATE _Order SET rtime='"+rtime+"'WHERE Ono='"+Ono+"'");
//        return true;
//    }
//
//    @GetMapping(value = "/getOrders")
//    public List getOrders(@RequestParam String username){
//
//            return template.queryForList("select * from _ORDER where Uno=?",new Object[]{username});
//
//    }
//    @GetMapping(value="/ChangeOrder")
//    public boolean ChangeOrder(@RequestParam short Seatno,
//                               @RequestParam String Tno_c,
//                               @RequestParam String sdate,
//                               @RequestParam short sSorder_c,
//                               @RequestParam short eSorder_c,
//                               @RequestParam double price,
//                               @RequestParam String sdtime,
//                               @RequestParam String eatime,
//                               @RequestParam String Ono,
//                               @RequestParam String Uno){
//        Date now=Calendar.getInstance().getTime();
//        String ptime= Alg.sdtf.format(now);
//        Map m=template.queryForMap("SELECT Uno FROM _Order WHERE Ono='"+Ono+"' AND Uno='"+Uno+"' AND ptime IS NOT NULL AND rtime IS NULL");
//        if(m==null) return false;
//        String Ono1=ptime+Uno;
//        List<Map<String, Object>> passergers=template.queryForList("SELECT * FROM _Passenger WHERE Ono='"+Ono+"'");
//        int Pcount=passergers.size();
//        String total=Double.toString(price*Pcount);
//        if(SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,-Pcount)==false) return false;
//        template.update("INSERT INTO _Order VALUES('"+Ono1+"','"+Uno+"',"+Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+
//                ",'"+Tno_c+"',"+Short.toString(eSorder_c)+",'"+ptime+"','"+ptime+"',NULL,"+total+",'"+sdtime+"','"+eatime+"',"+Pcount+")");
//        for(Map e:passergers){
//            String Pid=(String)e.get("Pid");
//            String Pname=(String)e.get("Pname");
//            short Ptype=(short)e.get("Ptype") ;
//            String Ptel=(String)e.get("Ptel");
//            template.update("INSERT INTO _Passenger VALUES('"+Ono1+"','"+Pid+"','"+Pname+"',"+Short.toString(Ptype)+",0,'"+Ptel+"')");
//        }
//        CancelOrder(Ono);
//        return true;
//    }
//
//    @GetMapping(value="/GetOrder")
//    public List GetOrder(@RequestParam String Uno,@RequestParam int otype){
//        List<Map<String,Object>> list=null;
//        switch (otype){
//            case 1:
//                list=template.queryForList("SELECT * FROM _Order WHERE Uno='"+Uno+"'AND ptime IS NULL ORDER BY otime DESC");
//                break;
//            case 2:
//                list=template.queryForList("SELECT * FROM _Order WHERE Uno='"+Uno+"'AND ptime IS NOT NULL AND rtime IS NULL ORDER BY otime DESC");
//                break;
//            case 3:
//                list=template.queryForList("SELECT * FROM _Order WHERE Uno='"+Uno+"'AND rtime IS NOT NULL ORDER BY otime DESC");
//                break;
//            default:
//                list=template.queryForList("SELECT * FROM _Order WHERE Uno='"+Uno+"' ORDER BY otime DESC");
//                break;
//        }
//        for(Map e:list){
//            short sSorder_c=(short)e.get("sSorder_c");
//            short eSorder_c=(short)e.get("eSorder_c");
//            String sdate=e.get("sdate").toString();
//            String Tno_c=(String)e.get("Tno_c");
//            String sSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
//                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(sSorder_c)).get("Sname_c");
//            String eSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
//                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(eSorder_c)).get("Sname_c");
//            e.put("sSname",sSname);
//            e.put("eSname",eSname);
//            e.put("otime",Alg.date2format(((Timestamp)e.get("otime")).toString()));
//            switch (otype){
//                case 3:
//                    e.put("rtime",Alg.date2format(((Timestamp)e.get("rtime")).toString()));
//                case 2:
//                    e.put("ptime",Alg.date2format(((Timestamp)e.get("ptime")).toString()));break;
//            }
//            e.put("sdtime",Alg.date2format(((Timestamp)e.get("sdtime")).toString()));
//            e.put("eatime",Alg.date2format(((Timestamp)e.get("eatime")).toString()));
//        }
//        return list;
//    }
//    @GetMapping(value="/f1")
//    public int DaliySet(@RequestParam int year,@RequestParam int month,@RequestParam int date) {
//        Calendar calendar=Calendar.getInstance();
//        calendar.set(year,month-1,date,0,0,0);
//        Date now=calendar.getTime();
//        String sdate=Alg.sdf.format(now);
//        List<Map<String,Object>> list=template.queryForList("SELECT * FROM FindTSpot");
//        for(Map e:list){
//            String Tno_c=(String)e.get("Tno_c");
//            short sSorder_c=(short)e.get("sSorder_c");
//            String Sname_c=(String)e.get("Sname_c");
//            String Scity_c=(String)e.get("Scity_c");
//            int mile=(int)e.get("mile");
//            int arrive=(int)e.get("arrive");
//            int stay=(int)e.get("stay");
//            short Ano_c=(short)e.get("Ano_c");
//            String atime=Alg.addMinute(now,arrive);
//            String dtime=Alg.addMinute(now,arrive+stay);
//            template.update("INSERT INTO _TSS VALUES('"+
//                    Tno_c+"','"+sdate+"','"+sSorder_c+"','"+Sname_c+"','"+Scity_c+"','"+dtime+"','"+atime+"',"+Integer.toString(mile)+","+Short.toString(Ano_c)+")");
//            List<Map<String,Object>> list1=template.queryForList("SELECT Seatno,tcount tleft FROM _TSeat WHERE Tno='"+Tno_c+"'");
//            for(Map e1:list1){
//                short Seatno=(short)e1.get("Seatno");
//                int tleft=(int)e1.get("tleft");
//                template.update("INSERT INTO _Ticket VALUES("+
//                        Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+",'"+Tno_c+"',"+Integer.toString(tleft)+")");
//            }
//        }
//        return 1;
//    }
//    @GetMapping(value="/GetPassengers")
//    public List GetPassengers(@RequestParam String Ono){
//        return template.queryForList("SELECT * FROM _Passenger WHERE Ono='"+Ono+"'");
//    }

}

