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
 * Oracle���ַ��ʹ��Ͷ���Character Large Object�����ݴ���
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
		//���������趨���Զ��ύ
		conn.setAutoCommit(false);
		try {
			//����һ���յ�CLOB����
			String sql = "INSERT INTO TEST_CLOB VALUES (?, EMPTY_CLOB())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			//��ѯ��CLOB��������
			sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//ȡ����CLOB����
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//��CLOB������д������
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//���ַ�ʽ��ȡ��·�������еĿո�ᱻʹ�á�%20������
				String path = JdbcOperaClob.class.getClassLoader().getResource("data.txt").getPath();
				//����%20���滻�ؿո�
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			//��ʽ�ύ
			conn.commit();
			System.out.println("����ɹ�");
		} catch (Exception e) {
			//����ع�
			conn.rollback();
			throw e;
		} finally {
			//�ָ�ԭ�ύ״̬
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * CLOB�����ȡ
	 */
	@Test
	public void clobRead() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//���������趨���Զ��ύ
		conn.setAutoCommit(false);
		try {
			//��ѯCLOB����
			String sql = "SELECT * FROM TEST_CLOB WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//��ȡCLOB����
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//���ַ���ʽ���
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
			//�ָ�ԭ�ύ״̬
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * �޸�CLOB��������ԭCLOB��������Ͻ��и���ʽ���޸ģ�
	 * @throws Exception
	 */
	@Test
	public void clobModify() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//���������趨���Զ��ύ
		conn.setAutoCommit(false);
		try {
			//��ѯCLOB��������
			String sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//��ȡ��CLOB���� 
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//���и���ʽ�޸�
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//���ַ�ʽ��ȡ��·�������еĿո�ᱻʹ�á�%20������
				String path = JdbcOperaClob.class.getClassLoader().getResource("data2.txt").getPath();
				//����%20���滻�ؿո�
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
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
			conn.rollback();
			throw e;
		} finally {
			//�ָ�ԭ�ύ״̬
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
	
	/**
	 * �滻CLOB���󣨽�ԭCLOB�������������һ��ȫ�µ�CLOB����
	 * @throws Exception
	 */
	@Test
	public void clobReplace() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConnection();
		boolean defaultCommit = conn.getAutoCommit();
		//���������趨���Զ��ύ
		conn.setAutoCommit(false);
		try {
			//���ԭCLOB����
			String sql = "UPDATE TEST_CLOB SET CLOBCOL=EMPTY_CLOB() WHERE ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.executeUpdate();
			
			//��ѯCLOB��������
			sql = "SELECT CLOBCOL FROM TEST_CLOB WHERE ID=? FOR UPDATE";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//��ȡ��CLOB����
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				//��������
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				//���ַ�ʽ��ȡ��·�������еĿո�ᱻʹ�á�%20������
				String path = JdbcOperaClob.class.getClassLoader().getResource("db.properties").getPath();
				//����%20���滻�ؿո�
				path = path.replaceAll("%20", " ");
				BufferedReader in = new BufferedReader(new FileReader(path));
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
			conn.rollback();
			throw e;
		} finally {
			//�ָ�ԭ�ύ״̬
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.releas(conn, stmt, rs);
		}
	}
}
