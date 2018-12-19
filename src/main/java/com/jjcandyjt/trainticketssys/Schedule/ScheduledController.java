package com.jjcandyjt.trainticketssys.Schedule;

import com.jjcandyjt.trainticketssys.Controller.Order_Controller;
import com.jjcandyjt.trainticketssys.Controller.TS_Controller;
import com.jjcandyjt.trainticketssys.Utils.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class ScheduledController {
    @Autowired
    Order_Controller order_controller;

    @Autowired
    TS_Controller ts_controller;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void deleOvertime(){
        order_controller.DelOrder();
        System.out.println("刷新超时订单");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateTic(){
        //更新车次
        //DBHelper.selectAll();
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE)+1;
        ts_controller.DaliySet(year,month,date+7);
    }

}
