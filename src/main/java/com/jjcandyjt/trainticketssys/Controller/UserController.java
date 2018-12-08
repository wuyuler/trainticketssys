package com.jjcandyjt.trainticketssys.Controller;

import com.jjcandyjt.trainticketssys.Service.UserService;
import com.jjcandyjt.trainticketssys.Utils.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/loginin")
    public Map loginin(@RequestParam String username,@RequestParam String password,@RequestParam int status){
        Map map=null;
        if(status==2){
            map=UserService.superUserloginin(username,password,"superuser");

        }
        if(status==1){
            map=UserService.superUserloginin(username,password,"netuser");
        }
        return map;//包含身份证,联系电话
    }
    @PostMapping(value = "/signin")
    public Map signin(@RequestParam String idcard,@RequestParam String username,@RequestParam String password,@RequestParam String tele){
        Map map=new HashMap();

        int res=userService.canSign(idcard,username);
        switch (res){
            case -1:map.put("info",-1);break;
            case -2:map.put("info",-2);break;
            case 1:userService.signin(idcard,username,password,tele);map.put("info",1);break;
            default:map.put("info","未知错误");
        }
        return map;//包含身份证,联系电话
    }
    @GetMapping(value = "/test")
    public List mytest(){
        List list=jdbcTemplate.queryForList("select * from netuser");
        return list;
    }

}
