package com.bigguy.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.bigguy.bos.dao.base.IBaseDao;
import com.bigguy.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private Class<T> entityClass;
	
	@Resource//根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//在父类（BaseDaoImpl）的构造方法中动态获得entityClass
	public BaseDaoImpl() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得父类上声明的泛型数组
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	public void pageQuery(PageBean pageBean) {
		
		// 通过传入 beaen ,改变 bean 的引用
		int currentPage = pageBean.getCurrentPage();  		// controller 传入的参数
		int pageSize = pageBean.getPageSize();				// controller 传入的参数
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();	// controller 设置的参数
			
		detachedCriteria.setProjection(Projections.rowCount());		// 谁知查询方式, 表示查询 total 数据总数
		// select count(*) from bc_staff;
		
		
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		
		pageBean.setTotal(count.intValue());		// 在上面查询出了总数
		
		detachedCriteria.setProjection(null); 		// 指定hibernate 框架发出sql 形式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		
		pageBean.setRows(rows); 		// 将查询的数据保存到 bean 中
	
	}

	public void executeUpdate(String queryName, Object... objs) {

		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		
		int i = 0;
		
		for (Object object : objs) {
			query.setParameter(i++, object);
		}
		query.executeUpdate(); 		// 值更新 
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
	}
	
}
