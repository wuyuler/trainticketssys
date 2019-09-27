package com.jjcandyjt.trainticketssys.Service;


import com.jjcandyjt.trainticketssys.Utils.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SrcService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    //添加座位
//    public int addSeat(String seatname,float prize){
//        System.out.println(prize);
//        //1表示成功,0表示失败
//        //try{
//            return jdbcTemplate.update("insert into "+ DBHelper.SEAT+"("+ DBHelper.SEATNAME+","+ DBHelper.SEATPRICE+") values(?,?)",new Object[]{seatname,prize});
////        }catch (Exception e){
////            return -1;
////        }
//    }
    //获取座位信息
    public List<Map<String, Object>> getSeats(){
        return jdbcTemplate.queryForList("select * from "+DBHelper.SEAT+"");
    }

    //修改座位价格
    public int editSeatPrize(String seatname, float prize) {
            return jdbcTemplate.update("update "+ DBHelper.SEAT+" set "+ DBHelper.SEATPRICE+"=? where "+ DBHelper.SEATNAME+"=?",new Object[]{prize,seatname});
    }

//    //删除座位
//    public int deleSeat(String seatname) {
//        try {
//            return jdbcTemplate.update("delete from "+ DBHelper.SEAT+" where "+ DBHelper.SEATNAME+"=?",new Object[]{seatname});
//        }catch (Exception e){
//            return -1;
//        }
//
//    }


    //获取所有站点

    //添加站点
    public int addSpot(String spotname,String cityname,String Sno){
        try {
            return  jdbcTemplate.update("insert into "+ DBHelper.SPOT+" values (?,?,?)",new Object[]{Sno,spotname,cityname});
        }catch (Exception e){
            return -1;
        }
    }

    public int editSpotName(String spotname, int spotid) {
        try {
            return  jdbcTemplate.update("update "+ DBHelper.SPOT+" set "+ DBHelper.SPOT_NAME+"=? where "+ DBHelper.SPOT_NO+"=?",new Object[]{spotname,spotid});
        }catch (Exception e){
            return -1;
        }
    }

    public int deleSpot(int spotid) {
        try {
            return  jdbcTemplate.update("delete "+ DBHelper.SPOT+" where "+ DBHelper.SPOT_NO+"=?",new Object[]{spotid});
        }catch (Exception e){
            return -1;
        }
    }

    public List getSpots(String spotname) {

        if(spotname.isEmpty()){
            return jdbcTemplate.queryForList("select * from "+ DBHelper.SPOT+"");
        }else {
            return jdbcTemplate.queryForList("select * from "+ DBHelper.SPOT+" where Sname like ?",new Object[]{"%"+spotname+"%"});
        }

    }

    public void updateSpotNo(String trainid){
        List<Map<java.lang.String,java.lang.Object>> list=jdbcTemplate.queryForList("select * from "+ DBHelper.TRAINSPOT+" where trainid=? order by "+ DBHelper.TRAINSPOT_MILE+" ",new Object[]{trainid});
        for(int i=0;i<list.size();i++){
            list.get(i).put("no",i+1);
            updateTrainSpot(list.get(i));
        }
    }
    public int  updateTrainSpot(Map trianspot){
        try {
            return jdbcTemplate.update("update "+ DBHelper.TRAINSPOT+" set "+ DBHelper.TRAINSPOT_MILE+"=?,"+ DBHelper.TRAINSPOT_Sorder+"=?,arrivetime=?,staytime=? where "+ DBHelper.Train_TNO+"=? and "+ DBHelper.SPOT_NO+"=?",
                    new Object[]{trianspot.get("miles"),trianspot.get("no"),trianspot.get("arrivetime"),trianspot.get("staytime"),trianspot.get("trainid"),trianspot.get("spotid")});
        }catch (Exception e){
            return -1;
        }

    }
    public void InsertSeatAndSpotlist(String trainid,List<Map> seatlist,List<Map> spotlist){

        for(int i=0;i<seatlist.size();i++){
            Integer seatno =jdbcTemplate.queryForObject("select "+ DBHelper.SEATNO+" from "+ DBHelper.SEAT+" where "+ DBHelper.SEATNAME+"=?",Integer.class,new Object[]{seatlist.get(i).get("seatname")});
            jdbcTemplate.update("insert into "+ DBHelper.TRAINSEAT+" values(?,?,?)",new Object[]{trainid,seatno,seatlist.get(i).get("count")});
        }
        Collections.sort(spotlist, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                return (Integer)o1.get("miles")-(Integer)o2.get("miles");
            }
        });
        for(int i=0;i<spotlist.size();i++){
            Integer spotid=jdbcTemplate.queryForObject("select "+ DBHelper.SPOT_NO+" from "+ DBHelper.SPOT+" where "+ DBHelper.SPOT_NAME+"=?",Integer.class,new Object[]{spotlist.get(i).get("spotname")});
            Integer arrivetime = (Integer) spotlist.get(i).get("arrivetime");
            Integer staytime = (Integer) spotlist.get(i).get("staytime");
            Integer miles=(Integer)spotlist.get(i).get("miles");
            System.out.println(miles);
            jdbcTemplate.update("Insert into "+ DBHelper.TRAINSPOT+" values(?,?,?,?,?,?)",new Object[]{trainid,spotid,i+1,arrivetime,staytime,miles});
        }
    }
    public void addTrain(String trainid, int airid, List<Map> seatlist, List<Map> spotlist) {
        int c1=jdbcTemplate.update("insert into "+ DBHelper.Train+" values(?,?)",new Object[]{trainid,airid});
        InsertSeatAndSpotlist(trainid,seatlist,spotlist);

    }

    public List getPartTrain(String trainid) {
        System.out.println("查询车次");
        if(trainid.isEmpty())
            return jdbcTemplate.queryForList("select "+ DBHelper.Train_TNO+","+ DBHelper.Train_AIRNO+" from "+ DBHelper.Train+"");
        else
            return jdbcTemplate.queryForList("select "+ DBHelper.Train_TNO+","+ DBHelper.Train_AIRNO+" from "+ DBHelper.Train+" where "+ DBHelper.Train_TNO+" like ?",new Object[]{"%"+trainid+"%"});

    }

    public Map getTrainInfo(String trainid) {
        Map res=new HashMap();
        Integer airid=jdbcTemplate.queryForObject("select "+ DBHelper.Train_AIRNO+" from "+ DBHelper.Train+" where "+ DBHelper.Train_TNO+"=?",Integer.class,new Object[]{trainid});
        List<Map<java.lang.String,java.lang.Object>> spotlist=jdbcTemplate.queryForList("select * from "+ DBHelper.TRAINSPOT+" where "+ DBHelper.Train_TNO+"=? order by Sorder",new Object[]{trainid});
        for(int i=0;i<spotlist.size();i++){
            Short spotid=(Short) spotlist.get(i).get("Sno");
            String spotname=jdbcTemplate.queryForObject("select "+ DBHelper.SPOT_NAME+" from "+ DBHelper.SPOT+" where "+ DBHelper.SPOT_NO+"=? ",String.class,new Object[]{spotid});
            spotlist.get(i).put("spotname",spotname);
        }
        List<Map<java.lang.String,java.lang.Object>> seatlist=jdbcTemplate.queryForList("select * from "+ DBHelper.TRAINSEAT+" where "+ DBHelper.Train_TNO+"=?",new Object[]{trainid});
        for(int i=0;i<seatlist.size();i++){
            Short Seatno=(Short)seatlist.get(i).get("Seatno");
            String seatname = jdbcTemplate.queryForObject("select "+ DBHelper.SEATNAME+" from "+ DBHelper.SEAT+" where "+ DBHelper.SEATNO+"=?",String.class,new Object[]{Seatno});
            seatlist.get(i).put("seatname",seatname);
        }
        res.put("airid",airid);
        res.put("seatlist",seatlist);
        res.put("spotlist",spotlist);
        return res;
    }

    public void updateTrain(String trainid, int airid, List<Map> seatlist2, List<Map> spotlist2) {

        jdbcTemplate.update("delete from "+ DBHelper.TRAINSEAT+" where "+ DBHelper.Train_TNO+"=?",new Object[]{trainid});
        jdbcTemplate.update("delete from "+ DBHelper.TRAINSPOT+" where "+ DBHelper.Train_TNO+"=?",new Object[]{trainid});
        InsertSeatAndSpotlist(trainid,seatlist2,spotlist2);
        jdbcTemplate.update("update "+ DBHelper.Train+" set "+ DBHelper.Train_AIRNO+"=? where "+ DBHelper.Train_TNO+"=?",new Object[]{airid,trainid});
    }


    public List getAllCity() {
        return jdbcTemplate.queryForList("select distinct Scity FROM _Spot");
    }
}
