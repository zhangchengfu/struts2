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
			//获取一个数据库连接
			conn = JdbcUtil.getConnection();
			//要执行的SQL命令
			String sql = "insert into USER_INFO(USER_ID,PASSWORD,NAME,SEX) values(?,?,?,?)";
			//通过conn对象获取负责执行SQL命令的Statement对象
			//st = conn.createStatement();
			st = conn.prepareStatement(sql);
			st.setString(1, "2008");
			st.setString(2, "123456");
			st.setString(3, "zhangsan");
			st.setString(4, "1");
			//执行插入操作，executeUpdate方法返回成功的条数
			int num = st.executeUpdate(sql);
			if (num > 0) {
				System.out.println("插入成功");
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
				System.out.println("更新成功");
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
				System.out.println("删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcUtil.releas(conn, st, rs);
		}
	}
	
	
}
