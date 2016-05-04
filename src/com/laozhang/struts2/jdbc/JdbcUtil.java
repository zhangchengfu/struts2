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
		//��ȡproperties�ļ��е����ݿ�������Ϣ
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("/resource/system.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ȡ���ݿ���������
		driver = prop.getProperty("jdbc.driver");
		//��ȡ���ݿ�����URL��ַ
		url = prop.getProperty("jdbc.url");
		//��ȡ���ݿ������û���
		username = prop.getProperty("jdbc.username");
		//��ȡ���ݿ���������
		password = prop.getProperty("jdbc.password");
		//�������ݿ�����
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
				//�رմ洢��ѯ�����ResultSet����
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			rs = null;
		}
		
		if (null != st) {
			try {
				//�رո���ִ��SQL�����Statement����
				st.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if (null != conn) {
			try {
				//�ر�Connection���ݿ����Ӷ���
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	
	
}
