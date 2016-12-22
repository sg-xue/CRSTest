package com.dianhua;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import org.python.util.PythonInterpreter;  

public class MysqlUtility {
	
//	Connection conn = null;
//	Statement stmt = null;
	
//	public MysqlUtility(){
//		String url = "jdbc:mariadb://localhost:3306/crs?"
//                + "user=root&password=yulore123&useUnicode=true&characterEncoding=UTF8";
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//			conn = DriverManager.getConnection(url);
//			stmt = conn.createStatement();
//		}catch(Exception e) {
//			
//		}
		
//	}
	public static void main(String[] args) throws Exception {
//		MysqlUtility mu = new MysqlUtility();
//		mu.updateSQL("13148832315","18210637050","2015-11-14 12:02:34","国内通话","100秒","北京");
		try{  
            System.out.println("Python script start \n");  
            Process pr = Runtime.getRuntime().exec("./getCodeFromSMS.py");  
              
            BufferedReader in = new BufferedReader(new  
                    InputStreamReader(pr.getInputStream()));  
            String line;  
            System.out.println("the result is: " + in.readLine() + "end");
            if ((line = in.readLine()) != null) {  
                System.out.println("the code from sms is: " + line);  
            }  
            in.close();  
            pr.waitFor();  
            System.out.println("Python script finish \n");  
			} catch (Exception e){  
                e.printStackTrace();  
            }
		PythonInterpreter interpreter = new PythonInterpreter();  
        interpreter.execfile("D:/Workspaces/J2EE/Test/config/input.py");
	}
	
    public void updateSQL(String tel, String recordPhone,String startTime,String callType,String talkTime, String cityName){
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        String url = "jdbc:mariadb://localhost:3306/crs?"
                + "user=root&password=yulore123&useUnicode=true&characterEncoding=UTF8";
 
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("org.mariadb.jdbc.Driver");// 动态加载mysql驱动
            // or:
//            com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
 
//            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
//            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
            sql = "insert into phone_records(own_phone,record_phone,start_time,call_type,talk_time,city) values (\'"+tel+"\',\'"+recordPhone+"\',\'"+startTime+"\',\'"+callType+"\',\'"+talkTime+"\',\'"+cityName+"\')";
//            System.out.println(sql);
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            //print result
//            if (result != -1) {
//                sql = "select * from phone_records";
//                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
//                while (rs.next()) {
//                    System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDate(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));
//                }
//            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
 
    }
    
    public void updateRecordTimeSQL(String tel, String testTime,String searchTime,String recordTime, String city){
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        String url = "jdbc:mariadb://localhost:3306/crs?"
                + "user=root&password=yulore123&useUnicode=true&characterEncoding=UTF8";
 
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("org.mariadb.jdbc.Driver");// 动态加载mysql驱动
            // or:
//            com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
 
//            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
//            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
            sql = "insert into test_records(own_phone,test_time,search_time,getRecord_time,city) values (\'"+tel+"\',\'"+testTime+"\',\'"+searchTime+"\',\'"+recordTime+"\',\'"+city+"\')";
//            System.out.println(sql);
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            //print result
//            if (result != -1) {
//                sql = "select * from phone_records";
//                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
//                while (rs.next()) {
//                    System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDate(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));
//                }
//            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
 
    }
 
}
