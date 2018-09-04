package com.bigguy.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigguy.bos.dao.ISubareaDao;
import com.bigguy.bos.domain.Subarea;
import com.bigguy.bos.service.ISubareaService;
import com.bigguy.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {

	@Autowired
	private ISubareaDao dao;

	@Override
	public void save(Subarea model) {
		dao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	public List<Subarea> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Subarea> getAvailableSubareaList() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
		criteria.add(Restrictions.isNull("decidedzone")); 		// 如果关联的不为空 
		
		List<Subarea>  list = dao.findByCriteria(criteria);
		return list;
	}
	
}
