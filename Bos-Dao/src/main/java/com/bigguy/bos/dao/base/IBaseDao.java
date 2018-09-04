package com.bigguy.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.bigguy.bos.utils.PageBean;

public interface IBaseDao<T> {
	
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity	);
    public T findById(Serializable id);
    
    public List<T> findAll();
    
    public void pageQuery(PageBean pageBean);
    
    public void executeUpdate(String queryName,Object ...objs);
    
    // 通过条件返回 list
    public List<T> findByCriteria(DetachedCriteria criteria);
    
}
