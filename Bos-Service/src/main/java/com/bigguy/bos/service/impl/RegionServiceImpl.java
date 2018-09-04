package com.bigguy.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigguy.bos.dao.RegionDao;
import com.bigguy.bos.domain.Region;
import com.bigguy.bos.service.RegionService;
import com.bigguy.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {


	@Autowired
	RegionDao dao;
	
	public void saveRegionData(List<Region> regions) {
		dao.saveRegionData(regions);
	}

	@Override
	public List<Region> getAllRegions() {
		
		return dao.findAll();
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	@Override
	public List<Region> getListByQ(String q) {
		
		return dao.findListByQ(q);
		
	}

}
