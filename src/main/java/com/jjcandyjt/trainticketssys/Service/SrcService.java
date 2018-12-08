package com.jjcandyjt.trainticketssys.Service;

import com.jjcandyjt.trainticketssys.TrainticketssysApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SrcService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加座位
    public int addSeat(String seatname,float prize){
        System.out.println(prize);
        //1表示成功,0表示失败
        int count =jdbcTemplate.update("insert into Seat(seatname,prize) values(?,?)",new Object[]{seatname,prize});
        return count;
    }
    //获取座位信息
    public List<Map<String, Object>> getSeats(){
        return jdbcTemplate.queryForList("select * from Seat");
    }

    //修改座位价格
    public int editSeatPrize(String seatname, float prize) {
            return jdbcTemplate.update("update Seat set prize=? where seatname=?",new Object[]{prize,seatname});
    }
    public static void main(String[] args) {
        SrcService srcService=new SrcService();
    }
    //删除座位
    public int deleSeat(String seatname) {
        return jdbcTemplate.update("delete from Seat where seatname=?",new Object[]{seatname});
    }


    //获取所有站点

    //添加站点
    public int addSpot(String spotname,String cityname){
        try {
            return  jdbcTemplate.update("insert into Spot(spotname,cityname) values (?,?)",new Object[]{spotname,cityname});
        }catch (Exception e){
            return -1;
        }
    }

    public int editSpotName(String spotname, int spotid) {
        try {
            return  jdbcTemplate.update("update Spot set spotname=? where spotid=?",new Object[]{spotname,spotid});
        }catch (Exception e){
            return -1;
        }
    }

    public int deleSpot(int spotid) {
        try {
            return  jdbcTemplate.update("delete Spot where spotid=?",new Object[]{spotid});
        }catch (Exception e){
            return -1;
        }
    }

    public List getSpots() {
        return jdbcTemplate.queryForList("select * from Spot");
    }
    //修改站点信息
}
