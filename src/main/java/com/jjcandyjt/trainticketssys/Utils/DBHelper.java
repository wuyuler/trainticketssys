package com.jjcandyjt.trainticketssys.Utils;

import java.sql.*;

public class DBHelper {
    public static String SEAT="_Seat";
    public static String SEATNAME="Seatname";
    public static String SEATPRICE="price";
    public static String SEATNO="Seatno";

    public static String SPOT="_Spot";
    public static String SPOT_NO="Sno";
    public static String SPOT_NAME="Sname";
    public static String SPOT_CITYNAME="cityname";


    public static String Train="_Train";
    public static String Train_TNO="Tno";
    public static String Train_AIRNO="Ano";


    public static String TRAINSEAT="_TSeat";
    public static String TRAINSEAT_TNO="Tno";
    public static String TRAINSEAT_SEATNO="Seatno";
    public static String TRAINSEAT_TCOUNT="tcount";


    public static String TRAINSPOT="_TSpot";
    //public static String TRAINSPOT_Tno="Tno";
    //public static String TRAINSPOT_Sno="_TSpot";
    public static String TRAINSPOT_Sorder="Sorder";//站序
    public static String TRAINSPOT_ARRIVE="arrive";
    public static String TRAINSPOT_STAY="stay";
    public static String TRAINSPOT_MILE="mile";


    public static String USER="_USER";
    public static String USER_USERNAME="Uno";
    public static String USER_PW="Upw";
    public static String USER_NAME="Uname";
    public static String USER_IDCARD="UUid";
    public static String USER_TEL="Utel";

    public static String SuperUser="superuser";

    public static Connection getConn() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=TrainTicketSys";
        String username = "sa";
        String password = "admin1600200010";
        Connection conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("连接成功!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void selectAll(){
        Connection conn = getConn();

        String sql = "select * from Student";
        PreparedStatement pstmt;
        ResultSet ret;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ret=pstmt.executeQuery();
            String Sname=null;
            while(ret.next()){
                Sname=ret.getString(1);
                System.out.println(Sname);
            }

            ret.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //登录


    public static void main(String[] args) {
      Connection connection=DBHelper.getConn();
    }
}
