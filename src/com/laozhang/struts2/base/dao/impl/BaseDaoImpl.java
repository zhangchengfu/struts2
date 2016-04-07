package com.laozhang.struts2.base.dao.impl;

import java.io.Serializable;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.laozhang.struts2.base.dao.BaseDao;
import com.laozhang.struts2.base.model.Pagination;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private SqlMapClient sqlMapClient;
	
	

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public List getAllObject(Class clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public Object getObject(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void removeObject(Class clazz, Serializable id) {
		getHibernateTemplate().delete(getObject(clazz, id));
	}

	public void removeObject(Object o) {
		getHibernateTemplate().delete(o);
	}

	public void removeAllObject(Collection collection) {
		getHibernateTemplate().deleteAll(collection);
	}
	
	/** bulk start */
	protected int excuteBulkUpdate(String hsql) {
		return getHibernateTemplate().bulkUpdate(hsql);
	}
	
	protected int excuteBulkUpdate(String hsql, Object arg) {
		return getHibernateTemplate().bulkUpdate(hsql, arg);
	}
	
	protected int excuteBulkUpdate(String hsql, Object[] args) {
		return getHibernateTemplate().bulkUpdate(hsql, args);
	}
	
	protected int excuteUpdateByHsql(String hsql, Map paraMap) {
		if (hsql != null) {
			Query query = getSession().createQuery(hsql);
			setQueryParameters(query, paraMap);
			return query.executeUpdate();
		}
		return 0;
	}
	/** bulk end */

	public Object findBy(Class clazz, String name, Object value) {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.add(Restrictions.eq(name, value));
		criteria.setMaxResults(1);
		return criteria.uniqueResult();
	}

	public List findLike(Class clazz, String name, String value) {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
		return criteria.list();
	}

	public List findAllBy(Class clazz, String name, Object value) {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.add(Restrictions.eq(name, value));
		return criteria.list();
	}
	
	protected Pagination findPageByCriteria(final DetachedCriteria detachedCriteria,
			Pagination pagination) {
			Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
			if (!pagination.getExportAllData())
			{
				criteria.setProjection(Projections.rowCount());
				Integer iCount = (Integer) criteria.uniqueResult();
				int totalCount = ((iCount != null) ? iCount.intValue() : 0);
				criteria.setProjection(null);
				criteria.setFirstResult(
					pagination.getFirstResult()).setMaxResults(
					pagination.getPageSize());
				pagination.setTotalCount(totalCount);
			}
			if (!StringUtils.isEmpty(pagination.getSortCriterion()))
			{
				if (Pagination.SORT_DESC.equals(pagination.getSortType()))
				{
					criteria.addOrder(Order.desc(pagination.getSortCriterion()));
				}
				else
				{
					criteria.addOrder(Order.asc(pagination.getSortCriterion()));
				}
			}
			List items = criteria.list();
			pagination.setList(items);
			return pagination;
	}
	
	protected Pagination findPageByCombinedHsql(final String sqlId, final Map paraMap,
			Pagination pagination)
		{
			String hsql = getCombinedHsqlStatement(
					sqlId, paraMap);
			String countSql = convertToCountHsqlStatement(hsql);

				if (hsql != null)
				{
					// prepare sort info
					StringBuffer buffHSQL = new StringBuffer(hsql);
					if (!StringUtils.isBlank(pagination.getSortCriterion()))
					{
						// todo if the sql have order by , it will throw Exception
						buffHSQL.append(" order by ");
						buffHSQL.append(pagination.getSortCriterion());
						if (Pagination.SORT_DESC.equals(pagination.getSortType()))
						{
							buffHSQL.append(" desc");
						}
						else
						{
							buffHSQL.append(" asc");
						}
					}

					// Prepare Query
					Query query = getSession().createQuery(
						buffHSQL.toString());
					setQueryParameters(
						query, paraMap);
					if (!pagination.getExportAllData())
					{
						query.setFirstResult(pagination.getFirstResult());
						query.setMaxResults(pagination.getPageSize());
					}
					// Execute Query List
					List restList = query.list();

					// set result List info
					pagination.setList(restList);

					// Query the totalCount
					if (countSql != null || !pagination.getExportAllData())
					{
						Query countQuery = getSession().createQuery(
							countSql);
						setQueryParameters(
							countQuery, paraMap);
						Integer count = Integer.parseInt(countQuery.uniqueResult().toString());
//						Integer count = (Integer) countQuery.uniqueResult();
						pagination.setTotalCount(count.intValue());
						pagination.setLastPage((int)Math.ceil(count.doubleValue()/pagination.getPageSize()));
					}
				}

				return pagination;
	}
	
	protected Pagination findPageByCombinedHsqlForTotalCount(final String sqlId, final Map paraMap,
			Pagination pagination)
		{
			String hsql = getCombinedHsqlStatement(
					sqlId, paraMap);

				if (hsql != null)
				{
					// prepare sort info
					StringBuffer buffHSQL = new StringBuffer(hsql);
					if (!StringUtils.isBlank(pagination.getSortCriterion()))
					{
						// todo if the sql have order by , it will throw Exception
						buffHSQL.append(" order by ");
						buffHSQL.append(pagination.getSortCriterion());
						if (Pagination.SORT_DESC.equals(pagination.getSortType()))
						{
							buffHSQL.append(" desc");
						}
						else
						{
							buffHSQL.append(" asc");
						}
					}

					// Prepare Query
					Query query = getSession().createQuery(
						buffHSQL.toString());
					setQueryParameters(
						query, paraMap);
					
					List restList = query.list();
					// Query the totalCount
					pagination.setTotalCount(restList.size());
					pagination.setLastPage((int)Math.ceil((double)restList.size()/pagination.getPageSize()));
					if (!pagination.getExportAllData())
					{
						query.setFirstResult(pagination.getFirstResult());
						query.setMaxResults(pagination.getPageSize());
					}
					// Execute Query List
					restList = query.list();

					// set result List info
					pagination.setList(restList);
				}

				return pagination;
	}
	
	protected Pagination findPageByCombinedHsqlWithDistinct(final String sqlId, final Map paraMap,
			Pagination pagination)
		{
			String hsql = getCombinedHsqlStatement(
				sqlId, paraMap);

			if (hsql != null)
			{
				// prepare sort info
				StringBuffer buffHSQL = new StringBuffer(hsql);
				if (!StringUtils.isBlank(pagination.getSortCriterion()))
				{
					// todo if the sql have order by , it will throw Exception
					buffHSQL.append(" order by ");
					buffHSQL.append(pagination.getSortCriterion());
					if (Pagination.SORT_DESC.equals(pagination.getSortType()))
					{
						buffHSQL.append(" desc");
					}
					else
					{
						buffHSQL.append(" asc");
					}
				}

				// Prepare Query
				Query query = getSession().createQuery(
					buffHSQL.toString());
				setQueryParameters(
					query, paraMap);
				if (!pagination.getExportAllData())
				{
					query.setFirstResult(pagination.getFirstResult());
					query.setMaxResults(pagination.getPageSize());
				}
				// Execute Query List
				List restList = query.list();

				// set result List info
				pagination.setList(restList);

				// Query the totalCount
				Query countQuery = getSession().createQuery(
					hsql);
				setQueryParameters(
					countQuery, paraMap);
				int count = countQuery.list().size();
				pagination.setTotalCount(count);
			}

			return pagination;
		}
	
	protected List findListByCombinedHsql(final String sqlId, final Map paraMap)
	{
		List restList = null;
		String hsql = getCombinedHsqlStatement(
			sqlId, paraMap);
		if (hsql != null)
		{
			// Prepare Query
			Query query = getSession().createQuery(
				hsql);
			setQueryParameters(
				query, paraMap);

			// Execute Query List
			restList = query.list();
		}
		return restList;
	}
	
	public void flush()
	{
		getHibernateTemplate().flush();
	}

	protected List findListByCombinedSql(final String sqlId, final Map paraMap)
	{
		List restList = null;
		String sql = getCombinedHsqlStatement(
			sqlId, paraMap);
		if (sql != null)
		{
			// Prepare Query
			Query query = getSession().createSQLQuery(
				sql);
			setQueryParameters(
				query, paraMap);

			// Execute Query List
			restList = query.list();
		}
		return restList;
	}
	
	private String convertToCountHsqlStatement(String selectSQL)
	{
		if (!StringUtils.isBlank(selectSQL))
		{
			String patternStr = "\\sfrom\\s";
			Pattern pattern = Pattern.compile(patternStr);
			String[] splitArr = pattern.split(selectSQL.toLowerCase());
			if (splitArr != null && splitArr.length > 0)
			{
				int indexOfFrom = splitArr[0].length();
				return "select count(*) " + selectSQL.substring(indexOfFrom);
			}
		}
		return null;
	}
	
	private String getCombinedHsqlStatement(String sqlId, Object paramObject)
	{
		if (sqlMapClient == null)
		{
			logger.error("No IBATIS sqlMapClient setted!");
			return null;
		}

		String sql = null;
		ExtendedSqlMapClient extendedSqlMapClient = (ExtendedSqlMapClient) sqlMapClient;
		MappedStatement mappedStatement = extendedSqlMapClient.getMappedStatement(sqlId);
		if (mappedStatement != null)
		{
			//TODO
			SessionScope sessionScope = null;
			StatementScope request = new StatementScope(sessionScope);
			
			request.setStatement(mappedStatement);
			sql = mappedStatement.getSql().getSql(request, paramObject);
		}
		return sql;
	}

	public void executeSql(String sql) throws Exception {
		Statement stmt = null;
		Session session = getSession();
		try {
			stmt = session.connection().createStatement();
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			stmt.close();
		}

	}

	public String generateCheckId(String tableName, String columnName,
			boolean isDate, String prefix, int idLength) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void setQueryParameters(Query query, Map paraMap) {
		if (query != null && paraMap != null && !paraMap.isEmpty()) {
			List namedParms = Arrays.asList(query.getNamedParameters());
			Iterator iter = paraMap.keySet().iterator();
			while (iter.hasNext()) {
				String paraName = (String) iter.next();
				if (namedParms.contains(paraName)) {
					Object value = paraMap.get(paraName);
					if (value instanceof List) {
						query.setParameterList(paraName, (List)value);
					} else {
						query.setParameter(paraName, value);
					}
				}
			}
		}
	}

}
