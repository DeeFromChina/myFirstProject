package com.sam.dao.common.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sam.dao.common.BaseDao;
import com.sam.util.Dom4jXml;
import com.sam.util.common.BaseUtil;

public class BaseDaoImpl implements BaseDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	private String FILE_PATH = this.getClass().getClassLoader().getResource("").getPath();

	public Object findField(Class<?> t, Serializable id) throws Exception {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM ");
			String tableName = t.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length());
			hql.append(tableName + " a ");
			hql.append(" WHERE a.id=");
			hql.append(id);
			return hibernateTemplate.find(hql.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<?> findField(Class<?> t, Map<String, Object> map) throws Exception {
		try {
			Object vo = t.newInstance();
			BaseUtil.mapToObject(vo, map);
			return findFields(t, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<?> findFields(Class<?> t, Map<String, Object> map) throws Exception {
		try {
			String[] keyParams = new String[map.size()];
			Object[] valueParams = new Object[map.size()];
			StringBuffer hql = new StringBuffer();
			hql.append("FROM ");
			String tableName = t.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length());
			hql.append(tableName + " a ");
			if (map != null) {
				hql.append(" WHERE 1=1 ");
				int i = 0;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					hql.append(" AND ");
					hql.append(" a." + entry.getKey() + "=:" + entry.getKey());
					keyParams[i] = entry.getKey();
					valueParams[i] = entry.getValue();
					i++;
				}
			}
			return (List<?>) hibernateTemplate.findByNamedParam(hql.toString(), keyParams, valueParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<?> findByHql(String hql) throws Exception {
		return (List<?>) hibernateTemplate.find(hql.toString());
	}
	
	public Object getById(Class<?> t, Serializable id) throws Exception {
		return hibernateTemplate.get(t, id);
	}

	public void save(Object object) throws Exception {
		try {
			hibernateTemplate.save(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Object object) throws Exception {
		try {
			hibernateTemplate.update(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveOrUpdate(Object object) throws Exception {
		try {
			hibernateTemplate.saveOrUpdate(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) throws Exception {
		try {
			hibernateTemplate.delete(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteByIds(Serializable[] ids, Class<?> t) throws Exception {
		try {
			String tableName = t.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length());
			String sql = "DELETE FROM " + tableName + " id IN(";
			int i = 0;
			for (Serializable id : ids) {
				if (i > 0) {
					sql += ",";
				}
				sql += id;
				i++;
			}
			sql += ");";
			deleteBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> findByQuery(String path, Map<String, Object> params) throws Exception {
		try {
			FILE_PATH = FILE_PATH.replace("/WEB-INF/classes/", "");
			path = FILE_PATH + path;
			Map<String, String> map = new HashMap<String, String>();
			params.putAll(map);
			String sql = Dom4jXml.initSqlXml(path, map);
			return findBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> findBySql(String sql) throws Exception {
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	public void deleteBySql(String sql) throws Exception {
		executeSql(sql);
	}

	public void updateBySql(String sql) throws Exception {
		executeSql(sql);
	}

	private void executeSql(String sql) {
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	public void sendByBatchSql(final List<String> sqls) throws Exception {
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					Statement stmt = connection.createStatement();
					try {
						for (String sql : sqls) {
							stmt.addBatch(sql);
						}
						stmt.executeBatch();
						connection.commit();
						stmt.clearBatch();
					} catch (Exception e) {
						e.printStackTrace();
						connection.rollback();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doClose(Session session, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception ex) {
				rs = null;
				ex.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception ex) {
				stmt = null;
				ex.printStackTrace();
			}
		}
	}
}
