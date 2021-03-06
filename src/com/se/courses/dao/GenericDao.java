package com.se.courses.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * 基于泛型的通用Dao层实现
 * @author BO
 *
 * @param <T>
 */
@Repository
public abstract class GenericDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> clazz;
	/**
	 * 使用反射获取子类声明的具体泛型类型，使子类无需传入泛型类型参数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDao() {
		Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
	}
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}
	public void refresh(T entity) {
		sessionFactory.getCurrentSession().refresh(entity);
	}
	public void persist(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().persist(entity);
	}
	public void delete(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(entity);
	}

	public void update(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(entity);
	}
	
	public void merge(T entity) {
		sessionFactory.getCurrentSession().merge(entity);
	}

	public T find(long id) {
		// TODO Auto-generated method stub
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createCriteria(clazz)
				.addOrder(org.hibernate.criterion.Order.asc("id")).list();
	}
	@SuppressWarnings("unchecked")
	public List<T> list(int firstResult, int maxResults) {
		return sessionFactory.getCurrentSession().createCriteria(clazz)
				.addOrder(org.hibernate.criterion.Order.asc("id")).setFirstResult(firstResult).setMaxResults(maxResults)
				.list();
	}
	
	/**
	 * 批量添加信息
	 * @param entities
	 */
	public int batching(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			sessionFactory.getCurrentSession().persist(entities.get(i));
			if ((i % 20) == 0) {
				sessionFactory.getCurrentSession().flush();
			}
		}
		return entities.size();
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
