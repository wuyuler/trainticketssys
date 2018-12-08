package com.jjcandyjt.trainticketssys.Service;

import com.jjcandyjt.trainticketssys.Utils.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    //登录
    public static Map superUserloginin(String username,String password,String status){
        Connection conn = DBHelper.getConn();
        String sql = "select * from "+status+" where username = '"+username+"'and password='"+password+"'";
        System.out.println(sql);
        PreparedStatement pstmt;
        ResultSet ret;
        String idcard=null;
        String tele=null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ret=pstmt.executeQuery();

            while(ret.next()){
                idcard=ret.getString("idcard");
                tele=ret.getString("tele");
                System.out.println(idcard);
            }

            ret.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map map=new HashMap();
        Map res=new HashMap();
        if(idcard!=null&&tele!=null){
            map.put("idcard",idcard);
            map.put("tele",tele);
            map.put("name",username);
            res.put("data",map);
            res.put("condition",1);

        }
        else
            res.put("condition",-1);

        return res;
    }

    //判断注册信息的可用性
    public  int  canSign(String idcard,String username){
        String sql ="select count(*) from netuser where idcard="+"'"+idcard+"'";
        int count=jdbcTemplate.queryForObject(sql,Integer.class);
        if(count!=0)return -1;
        sql="select count(*) from netuser where username="+"'"+username+"'";
        count=jdbcTemplate.queryForObject(sql,Integer.class);
        if(count!=0)return -2;
        return 1;
    }
    //注册
    public void signin(String idcard,String username,String password,String tele){
        String sql = "insert into netuser  values(?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{idcard,username,password,tele});
    }


    public static void main(String[] args) {
        superUserloginin("124","123456","superuser");
    }
}
