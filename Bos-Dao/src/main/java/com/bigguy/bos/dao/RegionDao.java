package com.bigguy.bos.dao;

import java.util.List;

import com.bigguy.bos.dao.base.IBaseDao;
import com.bigguy.bos.dao.base.impl.BaseDaoImpl;
import com.bigguy.bos.domain.Region;

public interface RegionDao extends IBaseDao<Region>{

	public void saveRegionData(List<Region> regions);

	public List<Region> findListByQ(String q);

}
