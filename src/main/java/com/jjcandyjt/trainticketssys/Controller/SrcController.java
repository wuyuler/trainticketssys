package com.jjcandyjt.trainticketssys.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjcandyjt.trainticketssys.Domain.Train;
import com.jjcandyjt.trainticketssys.Service.SrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SrcController {
    @Autowired
    private SrcService srcService;

//    @PostMapping(value = "/addSeat")
//    @ResponseBody
//    public int addSeat(@RequestParam String seatname, @RequestParam String prize){
//            return srcService.addSeat(seatname,Float.parseFloat(prize));
//    }
    @PostMapping(value = "/editSeatPrize")
    @ResponseBody
    public int editSeatPrize(@RequestParam String seatname, @RequestParam String prize){
            return srcService.editSeatPrize(seatname,Float.parseFloat(prize));
    }
//    @PostMapping(value = "/deleSeat")
//    @ResponseBody
//    public int deleSeat(@RequestParam String seatname){
//        return srcService.deleSeat(seatname);
//    }
    @GetMapping(value = "/getSeats")
    @ResponseBody
    public List getSeats(){
        return srcService.getSeats();
    }


    @PostMapping(value = "/addSpot")
    @ResponseBody
    public int addSpot(@RequestParam String spotname,@RequestParam String cityname,@RequestParam String Sno){
        return srcService.addSpot(spotname,cityname,Sno);
    }

    @PostMapping(value = "/editSpotName")
    @ResponseBody
    public int editSpotName(@RequestParam String spotname,@RequestParam int spotid){
        return srcService.editSpotName(spotname,spotid);
    }

    @PostMapping(value = "/deleSpot")
    @ResponseBody
    public int deleSpot(@RequestParam int spotid){
        return srcService.deleSpot(spotid);
    }

    @GetMapping(value = "/getSpots")
    @ResponseBody
    public List getSpots(@RequestParam String spotname)
    {
        return srcService.getSpots(spotname);
    }

    @PostMapping(value = "/addTrain")
    @ResponseBody
    public int addTrain(@RequestParam String train){
        //Train train1=new Train();
        JSONObject jsonObject=JSONObject.parseObject(train);
        String trainid=jsonObject.getString("trainid");
        int airid=jsonObject.getInteger("airid");

        JSONArray seatlist=jsonObject.getJSONArray("seatlist");
        List<Map> seatlist2=seatlist.toJavaList(Map.class);
        JSONArray spotlist=jsonObject.getJSONArray("spotlist");
        List<Map> spotlist2=spotlist.toJavaList(Map.class);
        System.out.println(seatlist2);
        System.out.println(spotlist2);
        try {
            srcService.addTrain(trainid,airid,seatlist2,spotlist2);
        }catch (Exception e){
            return 0;
        }

        return 1;
    }
    @PostMapping(value = "/updateTrain")
    @ResponseBody
    public int updateTrain(@RequestParam String train){
        System.out.println(train);
        //Train train1=new Train();
        JSONObject jsonObject=JSONObject.parseObject(train);
        String trainid=jsonObject.getString("trainid");
        int airid=jsonObject.getInteger("airid");

        JSONArray seatlist=jsonObject.getJSONArray("seatlist");
        List<Map> seatlist2=seatlist.toJavaList(Map.class);
        JSONArray spotlist=jsonObject.getJSONArray("spotlist");
        List<Map> spotlist2=spotlist.toJavaList(Map.class);
        System.out.println(seatlist2);
        System.out.println(spotlist2);
        srcService.updateTrain(trainid,airid,seatlist2,spotlist2);
        return 1;
    }

    @GetMapping(value = "/getPartTrain")
    @ResponseBody
    public List getPartTrain(@RequestParam String trainid){
        return srcService.getPartTrain(trainid);
    }

    @GetMapping(value ="/getTrainInfo")
    @ResponseBody
    public Map getTrainInfo(@RequestParam String trainid){
        System.out.println("jjjj");

        //return trainid;
        return srcService.getTrainInfo(trainid);
    }
    @GetMapping(value ="/getAllCity")
    @ResponseBody
    public List getAllCity(){
        return srcService.getAllCity();
    }


}
