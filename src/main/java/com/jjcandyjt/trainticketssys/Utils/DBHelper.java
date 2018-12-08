package com.jjcandyjt.trainticketssys.Utils;

import java.sql.*;

public class DBHelper {
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
