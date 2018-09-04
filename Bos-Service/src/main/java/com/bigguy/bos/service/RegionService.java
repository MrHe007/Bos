package com.bigguy.bos.service;

import java.util.List;

import com.bigguy.bos.domain.Region;
import com.bigguy.bos.utils.PageBean;

public interface RegionService {

	public void saveRegionData(List<Region> regions);

	public List<Region> getAllRegions();

	public void pageQuery(PageBean pageBean);

	public List<Region> getListByQ(String q);
	
}
