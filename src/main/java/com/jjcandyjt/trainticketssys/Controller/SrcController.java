package com.jjcandyjt.trainticketssys.Controller;

import com.jjcandyjt.trainticketssys.Service.SrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class SrcController {
    @Autowired
    private SrcService srcService;

    @PostMapping(value = "/addSeat")
    @ResponseBody
    public int addSeat(@RequestParam String seatname, @RequestParam String prize){
        //try {
//            System.out.println(prize);
//            System.out.println(Float.parseFloat(prize));
            return srcService.addSeat(seatname,Float.parseFloat(prize));
//        }catch (Exception s){
//            return -1;
//        }
    }
    @PostMapping(value = "/editSeatPrize")
    @ResponseBody
    public int editSeatPrize(@RequestParam String seatname, @RequestParam String prize){
            return srcService.editSeatPrize(seatname,Float.parseFloat(prize));
    }
    @PostMapping(value = "/deleSeat")
    @ResponseBody
    public int deleSeat(@RequestParam String seatname){
        return srcService.deleSeat(seatname);
    }
    @GetMapping(value = "/getSeats")
    @ResponseBody
    public List getSeats(){
        return srcService.getSeats();
    }


    @PostMapping(value = "/addSpot")
    @ResponseBody
    public int addSpot(@RequestParam String spotname,@RequestParam String cityname){
        return srcService.addSpot(spotname,cityname);
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
    public List getSpots(){
        return srcService.getSpots();
    }


}
