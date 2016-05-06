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
	 * �����ݿ��в���һ���µ�BLOB����
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
			//�õ����ݿ��������Ĭ���ύ��ʽ
			defaultCommit = conn.getAutoCommit();
			//��������
			conn.setAutoCommit(false);
			//����һ���յ�BLOB����
			String sql = "INSERT INTO TEST_BLOB VALUES (?, EMPTY_BLOB())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//��ѯ��BLOB����������ע��: �� ���for update�������У�ֱ�����б��޸���ϣ���֤������������ͻ
			sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//ȡ����BLOB���� ����ǿ��ת����Oracle��BLOB����
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//ʹ��IO��BLOB������д������
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("01.jpg"));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			
			//�ύ����
			conn.commit();
		} catch (Exception e) {
			//����ع�����
			conn.rollback();
			throw e;
		} finally {
			//�ָ����ݿ��������Ĭ���ύ��ʽ
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * �޸�BLOB��������ԭBLOB��������Ͻ��и���ʽ���޸ģ�
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
			//��ѯ��BLOB����������ע��: �� ���for update�������У�ֱ�����б��޸���ϣ���֤������������ͻ
			String sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//ȡ����BLOB���� ����ǿ��ת����Oracle��BLOB����
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//ʹ��IO��BLOB������д������
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("02.jpg"));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			
			//�ύ����
			conn.commit();
		} catch (Exception e) {
			//����ع�����
			conn.rollback();
			throw e;
		} finally {
			//�ָ����ݿ��������Ĭ���ύ��ʽ
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * �滻BLOB���󣨽�ԭBLOB�������������һ��ȫ�µ�BLOB����
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
			//���ԭBLOB����
			String sql = "UPDATE TEST_BLOB SET BLOBCOL=EMPTY_BLOB() WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//��ѯ��BLOB����������ע��: �� ���for update�������У�ֱ�����б��޸���ϣ���֤������������ͻ
			sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//ȡ����BLOB���� ����ǿ��ת����Oracle��BLOB����
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//ʹ��IO��BLOB������д������
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(JdbcOperaOracleBlob.class.getClassLoader().getResourceAsStream("01.jpg"));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			
			//�ύ����
			conn.commit();
		} catch (Exception e) {
			//����ع�����
			conn.rollback();
			throw e;
		} finally {
			//�ָ����ݿ��������Ĭ���ύ��ʽ
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * BLOB�����ȡ
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
			//��ѯBLOB����
			String sql = "SELECT BLOBCOL FROM TEST_BLOB WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//ȡ����BLOB���� ����ǿ��ת����Oracle��BLOB����
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				//�Զ�����������ʽ���
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:/1.jpg"));
				BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			
			//�ύ����
			conn.commit();
		} catch (Exception e) {
			//����ع�����
			conn.rollback();
			throw e;
		} finally {
			//�ָ����ݿ��������Ĭ���ύ��ʽ
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
}

/**
 * JDBC��ʹ������
 * 
 * Connection.setAutoCommit(false);//��������(start transaction)
 * Connection.rollback();//�ع�����(rollback)
 * Connection.commit();//�ύ����(commit)
 * 
 */
