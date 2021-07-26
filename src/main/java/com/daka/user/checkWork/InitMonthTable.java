package com.daka.user.checkWork;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;


import java.sql.*;

@Repository
public class InitMonthTable {
    @Value(value = "${spring.datasource.driver-class-name}")
    private String driver;

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Value(value = "${spring.datasource.username}")
    private String userName;

    @Value(value = "${spring.datasource.password}")
    private String password;

    public void init(String tableName) throws SQLException, ClassNotFoundException{
        //连接数据库
        Class.forName(driver);
        //测试url中是否包含useSSL字段，没有则添加设该字段且禁用
        if( url.indexOf("?") == -1 ){
            url = url + "?useSSL=false" ;
        }
        else if( url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1 )
        {
            url = url + "&useSSL=false";
        }
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stat = conn.createStatement();
        //获取数据库表名
        ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);

        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            return;
        }
        else{
            // 先判断是否纯在表名，有则先删除表在创建表
//			stat.executeUpdate("DROP TABLE IF EXISTS sys_admin_divisions;CREATE TABLE sys_admin_divisions("
            //创建行政区划表

            stat.executeUpdate("CREATE TABLE "+tableName+"("
                    + "id Integer(11) NOT NULL AUTO_INCREMENT,"
                    + "usr_ID varchar(32) NOT NULL,"
                    + "check_in_time time DEFAULT NULL,"
                    + "check_out_time time DEFAULT NULL,"
                    + "check_date date DEFAULT NULL,"
                    + "isBeLate int(1) DEFAULT NULL,"
                    + "isLeaveEarly int(1) DEFAULT NULL,"
                    + "isFlexible int(1) DEFAULT NULL,"
                    + "isNormal int(1) DEFAULT NULL,"
                    + "isOut int(1) DEFAULT NULL,"
                    + "workHours double(10,2) DEFAULT NULL,"
                    + "workOverHours double(10,2) DEFAULT NULL,"
                    + "expression int(2) DEFAULT NULL,"
                    + "PRIMARY KEY(id)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;"
            );
        }
        // 释放资源
        stat.close();
        conn.close();
    }
}
