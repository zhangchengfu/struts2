package com.laozhang.struts2.jdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class JdbcOperaOracleBlob {
	/**
	 * 向数据库中插入一个新的BLOB对象
	 * @throws Exception
	 */
	@Test
	public void blobInsert() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean defaultCommit = true;
		try {
			conn = JdbcUtil.getConnection();
			//得到数据库事务处理的默认提交方式
			defaultCommit = conn.getAutoCommit();
			//开启事务
			conn.setAutoCommit(false);
			//插入一个空的BLOB对象
			String sql = "INSERT INTO TEST_BLOB VALUES (?, EMPTY_BLOB())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//查询此BLOB对象并锁定。注意: 必 须加for update锁定该行，直至该行被修改完毕，保证不产生并发冲突
			sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//取出此BLOB对象 ，并强制转换成Oracle的BLOB对象
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//使用IO向BLOB对象中写入数据
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("01.jpg"));
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
			//出错回滚事务
			conn.rollback();
			throw e;
		} finally {
			//恢复数据库事务处理的默认提交方式
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * 修改BLOB对象（是在原BLOB对象基础上进行覆盖式的修改）
	 * @throws Exception
	 */
	@Test
	public void blobModify() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean defaultCommit = true;
		try {
			conn = JdbcUtil.getConnection();
			defaultCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			//查询此BLOB对象并锁定。注意: 必 须加for update锁定该行，直至该行被修改完毕，保证不产生并发冲突
			String sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//取出此BLOB对象 ，并强制转换成Oracle的BLOB对象
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//使用IO向BLOB对象中写入数据
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("02.jpg"));
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
			//出错回滚事务
			conn.rollback();
			throw e;
		} finally {
			//恢复数据库事务处理的默认提交方式
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * 替换BLOB对象（将原BLOB对象清除，换成一个全新的BLOB对象）
	 * @throws Exception
	 */
	@Test
	public void blobReplace() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean defaultCommit = true;
		try {
			conn = JdbcUtil.getConnection();
			defaultCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			//清空原BLOB对象
			String sql = "UPDATE TEST_BLOB SET BLOBCOL=EMPTY_BLOB() WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//查询此BLOB对象并锁定。注意: 必 须加for update锁定该行，直至该行被修改完毕，保证不产生并发冲突
			sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//取出此BLOB对象 ，并强制转换成Oracle的BLOB对象
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//使用IO向BLOB对象中写入数据
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("01.jpg"));
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
			//出错回滚事务
			conn.rollback();
			throw e;
		} finally {
			//恢复数据库事务处理的默认提交方式
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * BLOB对象读取
	 * @throws Exception
	 */
	@Test
	public void blobRead() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean defaultCommit = true;
		try {
			conn = JdbcUtil.getConnection();
			defaultCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			//查询BLOB对象
			String sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//取出此BLOB对象 ，并强制转换成Oracle的BLOB对象
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//以二进制流的形式输出
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:/1.jpg"));
				BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
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
			//出错回滚事务
			conn.rollback();
			throw e;
		} finally {
			//恢复数据库事务处理的默认提交方式
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
}

/**
 * JDBC中使用事务
 * 
 * Connection.setAutoCommit(false);//开启事务(start transaction)
 * Connection.rollback();//回滚事务(rollback)
 * Connection.commit();//提交事务(commit)
 * 
 */
