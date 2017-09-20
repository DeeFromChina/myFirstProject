package com.sam.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	public Object findField(Class<?> t, Serializable id) throws Exception;

	public List<?> findField(Class<?> t, Map<String, Object> map) throws Exception;

	public Object getById(Class<?> t, Serializable id) throws Exception;
	
	public void save(Object object) throws Exception;
	
	public void saveOrUpdate(Object object) throws Exception;

	public void update(Object object) throws Exception;

	public void delete(Object object) throws Exception;

	public void deleteByIds(Serializable[] ids, Class<?> t) throws Exception;
	
	public List<Map<String, Object>> findByQuery(String path, Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> findBySql(String sql) throws Exception;
	
	public void deleteBySql(String sql) throws Exception;
	
	public void updateBySql(String sql) throws Exception;
	
	public void sendByBatchSql(List<String> sqls) throws Exception;
	
	public List<?> findByHql(String hql) throws Exception;
}
