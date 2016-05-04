package com.laozhang.struts2.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	
	static {
		//读取properties文件中的数据库连接信息
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("/resource/system.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取数据库连接驱动
		driver = prop.getProperty("jdbc.driver");
		//获取数据库连接URL地址
		url = prop.getProperty("jdbc.url");
		//获取数据库连接用户名
		username = prop.getProperty("jdbc.username");
		//获取数据库连接密码
		password = prop.getProperty("jdbc.password");
		//加载数据库驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	public static void releas(Connection conn, Statement st, ResultSet rs) {
		if (null != rs) {
			try {
				//关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			rs = null;
		}
		
		if (null != st) {
			try {
				//关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if (null != conn) {
			try {
				//关闭Connection数据库连接对象
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	
	
}
