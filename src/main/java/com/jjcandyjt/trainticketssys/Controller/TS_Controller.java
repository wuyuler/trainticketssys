package com.jjcandyjt.trainticketssys.Controller;

import com.jjcandyjt.trainticketssys.Utils.Alg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class TS_Controller {
    @Autowired
    private JdbcTemplate template;


    @GetMapping(value="/FindTrain")
    public List FindTrain(@RequestParam String scity, @RequestParam String ecity, @RequestParam String dtime){
        Date now= Calendar.getInstance().getTime();
        String checktime=Alg.addMinute(now,30);
        List<Map<String,Object>> list=template.queryForList("select * from FindTrain " +
                "where scity='"+scity+"'and ecity='"+ecity+"'and dtime between '"+dtime+"' and '"+dtime+" 23:59' and dtime>'"+checktime+"'");
        for(Map<String,Object> e:list){
            String sdate=((Timestamp)e.get("dtime")).toString();
            String atime=((Timestamp)e.get("atime")).toString();
            e.put("dtime",Alg.date2format(sdate));
            e.put("atime",Alg.date2format(atime));
        }
        return list;
    }

    @GetMapping(value="/FindSeat")
    public List FindSeat(@RequestParam String Tno_c,@RequestParam short Ano_c, @RequestParam String sdate,
                         @RequestParam short sSorder_c, @RequestParam short eSorder_c, @RequestParam int mile){
        eSorder_c--;
        List<Map<String,Object>> list=null;
        synchronized (Order_Controller.TLock){
            list=template.queryForList("SELECT MIN(tleft) tleft,_Seat.* FROM _Ticket,_Seat WHERE" +
                    " _Seat.Seatno=_Ticket.Seatno AND sdate='"+sdate+"' AND Tno_c='"+Tno_c+"' AND sSorder_c " +
                    "BETWEEN "+Short.toString(sSorder_c)+" AND "+Short.toString(eSorder_c)+" GROUP BY _Seat.Seatno,_Seat.Seatname,_Seat.price");
        }
        char thead=Tno_c.charAt(0);
        for(Map<String,Object> e:list){
            double price=Alg.mile2price(mile,Ano_c,thead,Double.parseDouble(e.get("price").toString()));
            e.put("price",price);
        }
        return list;
    }

    @GetMapping(value="/f1")
    public int DaliySet(@RequestParam int year,@RequestParam int month,@RequestParam int date) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month-1,date,0,0,0);
        Date now=calendar.getTime();
        String sdate=Alg.sdf.format(now);
        List<Map<String,Object>> list=template.queryForList("SELECT * FROM FindTSpot");
        for(Map e:list){
            String Tno_c=(String)e.get("Tno_c");
            short sSorder_c=(short)e.get("sSorder_c");
            String Sname_c=(String)e.get("Sname_c");
            String Scity_c=(String)e.get("Scity_c");
            int mile=(int)e.get("mile");
            int arrive=(int)e.get("arrive");
            int stay=(int)e.get("stay");
            short Ano_c=(short)e.get("Ano_c");
            String atime= Alg.addMinute(now,arrive);
            String dtime=Alg.addMinute(now,arrive+stay);
            template.update("INSERT INTO _TSS VALUES('"+
                    Tno_c+"','"+sdate+"','"+sSorder_c+"','"+Sname_c+"','"+Scity_c+"','"+dtime+"','"+atime+"',"+Integer.toString(mile)+","+Short.toString(Ano_c)+")");
            List<Map<String,Object>> list1=template.queryForList("SELECT Seatno,tcount tleft FROM _TSeat WHERE Tno='"+Tno_c+"'");
            for(Map e1:list1){
                short Seatno=(short)e1.get("Seatno");
                int tleft=(int)e1.get("tleft");
                template.update("INSERT INTO _Ticket VALUES("+
                        Short.toString(Seatno)+",'"+sdate+"',"+Short.toString(sSorder_c)+",'"+Tno_c+"',"+Integer.toString(tleft)+")");
            }
        }
        return 1;
    }

}
