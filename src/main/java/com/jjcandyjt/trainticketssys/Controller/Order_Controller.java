package com.jjcandyjt.trainticketssys.Controller;

import com.alibaba.fastjson.JSONObject;
import com.jjcandyjt.trainticketssys.Utils.Alg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class Order_Controller {
    @Autowired
    private JdbcTemplate template;

    public static String TLock="LOCK";
    public static String OLock="LOCK";
    public static final int timeoutorder=-15;
    public static final int otimebeforedepart=120;
    public static final int rtimebeforedepart=120;

    @GetMapping(value="DelOrder")
    public void DelOrder(){
        Date now=Calendar.getInstance().getTime();
        String checktime=Alg.addMinute(now,timeoutorder);
        synchronized (OLock){
            List<Map<String,Object>> list=template.queryForList("SELECT * FROM OOrder WHERE otime<'"+checktime+"'");
            //取消所有超时订单
            for(Map e:list){
                String Ono=(String)e.get("Ono");
                short Pcount=(short)e.get("Pcount");
                short Seatno=(short)e.get("Seatno");
                short sSorder_c=(short)e.get("sSorder_c");
                short eSorder_c=(short)e.get("eSorder_c");
                String Tno_c=(String)e.get("Tno_c") ;
                String sdate=e.get("sdate").toString();
                synchronized (TLock){
                    SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,Pcount);
                }
                template.update("DELETE FROM _Passenger WHERE Ono='"+Ono+"'");
                template.update("DELETE FROM _Order WHERE Ono='"+Ono+"'");
            }
        }
    }

    @GetMapping(value="/GetOrder")
    public List GetOrder(@RequestParam String Uno,@RequestParam int otype){
        List<Map<String,Object>> list=null;
        String view=null;
        switch (otype){
            case 1:
                view="OOrder"; break;
            case 2:
                view="POrder";break;
            case 3:
                view="ROrder";break;
            default:
                view="_Order";break;
        }
        list=template.queryForList("SELECT * FROM "+view+" WHERE Uno='"+Uno+"' ORDER BY otime DESC");
        for(Map e:list){
            short sSorder_c=(short)e.get("sSorder_c");
            short eSorder_c=(short)e.get("eSorder_c");
            String sdate=e.get("sdate").toString();
            String Tno_c=(String)e.get("Tno_c");
            String sSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(sSorder_c)).get("Sname_c");
            String eSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(eSorder_c)).get("Sname_c");
            e.put("sSname",sSname);
            e.put("eSname",eSname);
            e.put("otime",Alg.date2format(((Timestamp)e.get("otime")).toString()));
            switch (otype){
                case 3:
                    e.put("rtime",Alg.date2format(((Timestamp)e.get("rtime")).toString()));
                case 2:
                    e.put("ptime",Alg.date2format(((Timestamp)e.get("ptime")).toString()));break;
            }
            e.put("sdtime",Alg.date2format(((Timestamp)e.get("sdtime")).toString()));
            e.put("eatime",Alg.date2format(((Timestamp)e.get("eatime")).toString()));
        }
        return list;
    }
    @GetMapping(value="/SGetOrder")
    public List SGetOrder(@RequestParam int otype){
        List<Map<String,Object>> list=null;
        String view=null;
        switch (otype){
            case 1:
                view="OOrder"; break;
            case 2:
                view="POrder";break;
            case 3:
                view="ROrder";break;
            default:
                view="_Order";break;
        }
        list=template.queryForList("SELECT * FROM "+view+"  ORDER BY otime DESC");
        for(Map e:list){
            short sSorder_c=(short)e.get("sSorder_c");
            short eSorder_c=(short)e.get("eSorder_c");
            String sdate=e.get("sdate").toString();
            String Tno_c=(String)e.get("Tno_c");
            String sSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(sSorder_c)).get("Sname_c");
            String eSname=(String)template.queryForMap("SELECT Sname_c FROM _TSS WHERE " +
                    "sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c="+Short.toString(eSorder_c)).get("Sname_c");
            e.put("sSname",sSname);
            e.put("eSname",eSname);
            e.put("otime",Alg.date2format(((Timestamp)e.get("otime")).toString()));
            switch (otype){
                case 3:
                    e.put("rtime",Alg.date2format(((Timestamp)e.get("rtime")).toString()));
                case 2:
                    e.put("ptime",Alg.date2format(((Timestamp)e.get("ptime")).toString()));break;
            }
            e.put("sdtime",Alg.date2format(((Timestamp)e.get("sdtime")).toString()));
            e.put("eatime",Alg.date2format(((Timestamp)e.get("eatime")).toString()));
        }
        return list;
    }

    @GetMapping(value="/SetSeat")
    public void SetSeat(short Seatno,String Tno_c,String sdate,short sSorder_c,short eSorder_c,int Pcount){
        template.update("UPDATE _Ticket SET tleft=tleft+"+Integer.toString(Pcount)+ "WHERE Seatno="+
                Short.toString(Seatno)+" AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c));
    }

    @GetMapping(value="/OON")
    public boolean OON(@RequestParam short Seatno, @RequestParam String Tno_c,@RequestParam String sdate,
                       @RequestParam short sSorder_c,@RequestParam short eSorder_c,@RequestParam int Pcount,@RequestParam String checktime){
        try{
            Map m=template.queryForMap("SELECT * FROM _TSS WHERE Tno_c='"+Tno_c+
                    "' AND sdate='"+sdate+"'AND sSorder_c="+Short.toString(sSorder_c)+" AND dtime>'"+checktime+"'");
            m=template.queryForMap("SELECT MIN(tleft) tleft FROM _Ticket WHERE Seatno="+
                    Short.toString(Seatno)+" AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+ "' AND sSorder_c BETWEEN "+
                    Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c));
            if((int)m.get("tleft")<Pcount) return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @GetMapping(value="/RON")
    public boolean RON(@RequestParam String Tno_c,@RequestParam String sdate,@RequestParam short sSorder_c,@RequestParam String checktime){
        try{
            Map m=template.queryForMap("SELECT * FROM _TSS WHERE Tno_c='"+Tno_c+
                    "' AND sdate='"+sdate+"'AND sSorder_c="+Short.toString(sSorder_c)+" AND dtime>'"+checktime+"'");
        }catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    @PostMapping(value="/TakeOrder")
    public String TakeOrder(@RequestParam short Seatno,@RequestParam String Tno_c,@RequestParam String sdate,
                            @RequestParam String Uno,
                            @RequestParam short sSorder_c,@RequestParam short eSorder_c,
                            @RequestParam double total,
                            @RequestParam String sdtime,@RequestParam String eatime,
                            @RequestParam String json){

        List<Map> passergers=JSONObject.parseArray(json).toJavaList(Map.class);
        Date now=Calendar.getInstance().getTime();
        String otime=Alg.sdtf.format(now);
        String Ono=Alg.getOno(otime,Uno,false);
        int Pcount=passergers.size();
        String checktime=Alg.addMinute(now,otimebeforedepart);
        synchronized (TLock){
            if(OON(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,Pcount,checktime)==false) return "";
            SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,-Pcount);
        }
        template.update("INSERT INTO _Order VALUES('"+Ono+"','"+Uno+"',"+Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+
                ",'"+Tno_c+"',"+Short.toString(eSorder_c)+",'"+otime+"',NULL,NULL,"+Double.toString(total)+",'"+sdtime+"','"+eatime+"',"+Pcount+")");
        for(Map e:passergers){
            String Pid=(String)e.get("Pid");
            String Pname=(String)e.get("Pname");
            String PTel=(String)e.get("PTel");
            Integer Ptype=(Integer)e.get("Ptype");
            template.update("INSERT INTO _Passenger VALUES('"+Ono+"','"+Pid+"','"+Pname+"',"+Integer.toString(Ptype)+",0,"+PTel+")");
        }
        return Ono;
    }


    @GetMapping(value="/PayOrder")
    public boolean PayOrder(@RequestParam String Ono,@RequestParam String Uno){
        Date now=Calendar.getInstance().getTime();
        String ptime=Alg.sdtf.format(now);
        synchronized (OLock){
            try{
                Map m=template.queryForMap("SELECT * FROM OOrder WHERE Ono='"+Ono+"' AND Uno='"+Uno+"'");
            }catch (Exception e){
                return false;
            }
            template.update("UPDATE _Order SET ptime='"+ptime+"'WHERE Ono='"+Ono+"'");
        }
        template.update("UPDATE _Passenger SET available=1 WHERE Ono='"+Ono+"'");
        return true;
    }

    @GetMapping(value="/CancelOrder")
    public boolean CancelOrder(@RequestParam String Ono,@RequestParam String Uno){
        Date now=Calendar.getInstance().getTime();
        String rtime=Alg.sdtf.format(now);
        synchronized (OLock){
            try{
                Map m=template.queryForMap("SELECT * FROM POrder WHERE Ono='"+Ono+"' AND Uno='"+Uno+"'");
                short Seatno=(short)m.get("Seatno");
                short sSorder_c=(short)m.get("sSorder_c");
                short eSorder_c=(short)m.get("eSorder_c");
                String Tno_c=(String)m.get("Tno_c");
                String sdate=m.get("sdate").toString();
                short Pcount=(short)m.get("Pcount");
                synchronized (TLock){
                    String checktime=Alg.addMinute(now,rtimebeforedepart);
                    if(RON(Tno_c,sdate,sSorder_c,checktime)==false) return false;
                    SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,Pcount);
                }
                template.update("UPDATE _Order SET rtime='"+rtime+"'WHERE Ono='"+Ono+"'");
            }catch (Exception e){
                System.out.println(e.toString());
                return false;
            }
        }
        template.update("UPDATE _Passenger SET available=0 WHERE Ono='"+Ono+"'");
        return true;
    }

    @GetMapping(value="/ChangeOrder")
    public boolean ChangeOrder(@RequestParam short Seatno,@RequestParam String Tno_c,@RequestParam String sdate,
                               @RequestParam short sSorder_c,@RequestParam short eSorder_c,
                               @RequestParam String sdtime,@RequestParam String eatime,
                               @RequestParam double total,@RequestParam String oOno,@RequestParam String Uno){
        Date now=Calendar.getInstance().getTime();
        String ptime= Alg.sdtf.format(now);
        String Ono=Alg.getOno(ptime,Uno,true);
        List<Map<String, Object>> passergers=null;
        int Pcount=0;
        try {
            Map m=template.queryForMap("SELECT * FROM POrder WHERE Ono='"+oOno+"' AND Uno='"+Uno+"'");
            short oSeatno=(short)m.get("Seatno");
            short osSorder_c=(short)m.get("sSorder_c");
            short oeSorder_c=(short)m.get("eSorder_c");
            String oTno_c=(String)m.get("Tno_c");
            String osdate=m.get("sdate").toString();
            passergers=template.queryForList("SELECT * FROM _Passenger WHERE Ono='"+oOno+"'");
            Pcount=passergers.size();
            synchronized (TLock){
                if(RON(oTno_c,osdate,osSorder_c,Alg.addMinute(now,rtimebeforedepart))==false) return false;
                if(OON(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,Pcount,Alg.addMinute(now,otimebeforedepart))==false) return false;
                SetSeat(oSeatno,oTno_c,osdate,osSorder_c,oeSorder_c,Pcount);
                SetSeat(Seatno,Tno_c,sdate,sSorder_c,eSorder_c,-Pcount);
            }
            /*改签成功，生成新订单*/
            template.update("UPDATE _Order SET rtime='"+ptime+"' WHERE Ono='"+oOno+"'");
        }catch (Exception e){
            return false;
        }
        template.update("INSERT INTO _Order VALUES('"+Ono+"','"+Uno+"',"+Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+
                ",'"+Tno_c+"',"+Short.toString(eSorder_c)+",'"+ptime+"','"+ptime+"',NULL,"+Double.toString(total)+",'"+sdtime+"','"+eatime+"',"+Integer.toString(Pcount)+")");
        for(Map e:passergers){
            String Pid=(String)e.get("Pid");
            String Pname=(String)e.get("Pname");
            short Ptype=(short)e.get("Ptype") ;
            String Ptel=(String)e.get("Ptel");
            template.update("UPDATE _Passenger SET available=0 WHERE Ono='"+oOno+"'");
            template.update("INSERT INTO _Passenger VALUES('"+Ono+"','"+Pid+"','"+Pname+"',"+Short.toString(Ptype)+",1,'"+Ptel+"')");
        }
        return true;
    }

    @GetMapping(value="/GetPassengers")
    public List GetPassengers(@RequestParam String Ono){
        return template.queryForList("SELECT * FROM _Passenger WHERE Ono='"+Ono+"'");
    }

}
