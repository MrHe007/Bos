package com.bigguy.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bigguy.bos.dao.IUserDao;
import com.bigguy.bos.dao.base.impl.BaseDaoImpl;
import com.bigguy.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	public User findUserByUsernameAndPassword(String username, String password) {

		String hql = "FROM User u where u.username= ? AND u.password = ?"; 		// hql 查询语句
		
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		
		if( list!=null && list.size()>0) {
			return list.get(0); 
		}
		
		return null;
	}

	public void executeUpdate(String queryName, Object... objs) {
		
		Session session = this.getSessionFactory().getCurrentSession(); 	// 得到的 session 是指 Hibernate 中处理事物的 session
		Query query = session.getNamedQuery(queryName);
		
		int i = 0;
		for (Object object : objs) {
			query.setParameter(i++, object);
		}
		
		query.executeUpdate(); 			// query 中手动执行的方法 
		
	}

}
