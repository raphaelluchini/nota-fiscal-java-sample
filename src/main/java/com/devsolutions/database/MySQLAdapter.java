package com.devsolutions.database;


import com.mysql.jdbc.Driver;
import org.sql2o.Sql2o;

public class MySQLAdapter {
    public static String USERNAME = "root";
    public static String PASSWORD = "";
    public static String PORT_NUMBER = "3306";
    public static String HOST_NAME = "127.0.0.1";
    public static String DATABASE_NAME = "notafiscal";

    public static Sql2o connectDB() {
        return new Sql2o("jdbc:mysql://" + HOST_NAME + ":" + PORT_NUMBER + "/" + DATABASE_NAME, USERNAME, PASSWORD);
    }
}
