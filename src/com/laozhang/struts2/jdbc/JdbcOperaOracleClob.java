package com.laozhang.struts2.jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

/*1 CREATE TABLE TEST_CLOB ( ID NUMBER(3), CLOBCOL CLOB)
2 
3 CREATE TABLE TEST_BLOB ( ID NUMBER(3), BLOBCOL BLOB)*/

/**
 * Oracle中字符型大型对象（Character Large Object）数据处理
 * @author zhang chengfu
 *
 */
public class JdbcOperaOracleClob {
	
	
	@Test
	public void clobInsert() throws Exception  {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//开启事务，设定不自动提交
		conn.setAutoCommit(false);
		try {
			//插入一个空的CLOB对象
			String sql = "INSERT INTO TEST_CLOB VALUES (?, EMPTY_CLOB())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//查询此CLOB对象并锁定
			sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//取出此CLOB对象
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//向CLOB对象中写入数据
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//这种方式获取的路径，其中的空格会被使用“%20”代替
				String path = JdbcOperaClob.class.getClassLoader().getResource("data.txt").getPath();
				//将“%20”替换回空格
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			//正式提交
			conn.commit();
			System.out.println("插入成功");
		} catch (Exception e) {
			//出错回滚
			conn.rollback();
			throw e;
		} finally {
			//恢复原提交状态
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * CLOB对象读取
	 */
	@Test
	public void clobRead() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//开启事务，设定不自动提交
		conn.setAutoCommit(false);
		try {
			//查询CLOB对象
			String sql = "SELECT * FROM TEST_CLOB WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//获取CLOB对象
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//以字符形式输出
				BufferedReader in = new BufferedReader(clob.getCharacterStream());
				BufferedWriter out = new BufferedWriter(new FileWriter("D:\\2.txt"));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				out.close();
				in.close();
			}
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			//恢复原提交状态
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * 修改CLOB对象（是在原CLOB对象基础上进行覆盖式的修改）
	 * @throws Exception
	 */
	@Test
	public void clobModify() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//开启事务，设定不自动提交
		conn.setAutoCommit(false);
		try {
			//查询CLOB对象并锁定
			String sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//获取此CLOB对象 
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//进行覆盖式修改
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//这种方式获取的路径，其中的空格会被使用“%20”代替
				String path = JdbcOperaClob.class.getClassLoader().getResource("data2.txt").getPath();
				//将“%20”替换回空格
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			//提交事务
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			//恢复原提交状态
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * 替换CLOB对象（将原CLOB对象清除，换成一个全新的CLOB对象）
	 * @throws Exception
	 */
	@Test
	public void clobReplace() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//开启事务，设定不自动提交
		conn.setAutoCommit(false);
		try {
			//清空原CLOB对象
			String sql = "UPDATE TEST_CLOB SET CLOBCOL=EMPTY_CLOB() WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			
			//查询CLOB对象并锁定
			sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//获取此CLOB对象
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//更新数据
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//这种方式获取的路径，其中的空格会被使用“%20”代替
				String path = JdbcOperaClob.class.getClassLoader().getResource("db.properties").getPath();
				//将“%20”替换回空格
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			
			//提交事务
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			//恢复原提交状态
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
}
