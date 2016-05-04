package com.laozhang.struts2.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;
/**
 * JDBC CRUD
 * @author zhang chengfu
 *
 */
public class JdbcCRUDByStatement {
	
	@Test
	public void insert() {
		Connection conn = null;
		//Statement st = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//��ȡһ�����ݿ�����
			conn = JdbcUtil.getConnection();
			//Ҫִ�е�SQL����
			String sql = "insert into USER_INFO(USER_ID,PASSWORD,NAME,SEX) values(?,?,?,?)";
			//ͨ��conn�����ȡ����ִ��SQL�����Statement����
			//st = conn.createStatement();
			st = conn.prepareStatement(sql);
			st.setString(1, "2008");
			st.setString(2, "123456");
			st.setString(3, "zhangsan");
			st.setString(4, "1");
			//ִ�в��������executeUpdate�������سɹ�������
			int num = st.executeUpdate(sql);
			if (num > 0) {
				System.out.println("����ɹ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.releas(conn, st, rs);
		}
	}
	
	@Test
	public void find() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			st = conn.createStatement();
			String sql = "select * from USER_INFO where USER_ID='2008'";
			rs = st.executeQuery(sql);
			if (rs.next()) {
				System.out.println(rs.getString("USER_ID"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.releas(conn, st, rs);
		}
	}
	
	@Test
	public void update() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			st = conn.createStatement();
			String sql = "update USER_INFO set PASSWORD = '111111' where USER_ID='2008'";
			int num = st.executeUpdate(sql);
			if (num > 0) {
				System.out.println("���³ɹ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.releas(conn, st, rs);
		}
	}
	
	@Test
	public void delete() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			st = conn.createStatement();
			String sql = "delete from USER_INFO where USER_ID='2008'";
			int num = st.executeUpdate(sql);
			if (num > 0) {
				System.out.println("ɾ���ɹ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.releas(conn, st, rs);
		}
	}
	
	
}
