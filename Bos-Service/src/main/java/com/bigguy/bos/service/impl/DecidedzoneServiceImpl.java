package com.bigguy.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigguy.bos.dao.IDecidedzoneDao;
import com.bigguy.bos.dao.ISubareaDao;
import com.bigguy.bos.domain.Decidedzone;
import com.bigguy.bos.domain.Subarea;
import com.bigguy.bos.service.IDecidedzoneService;
import com.bigguy.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Autowired
	IDecidedzoneDao dao;
	
	@Autowired
	ISubareaDao subareaDao;
	
	public void save(Decidedzone model, String[] subareaId) {
	
		dao.save(model); 		// 保存定区的信息
		// 将分区关联区 -- 两种方法
		/*
		 * 1.用分区主动关联
		 * 2.用定区主动
		 */
		for (String id : subareaId) {
//			model.getSubareas().add(subarea); 	// 让定区主动维护外键 
			Subarea subarea = subareaDao.findById(id);
			subarea.setDecidedzone(model);
		}
		
	}

	@Override
	public List<Decidedzone> findAll() {
		return dao.findAll();
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

}
