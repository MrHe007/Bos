package com.bigguy.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bigguy.bos.dao.RegionDao;
import com.bigguy.bos.dao.base.impl.BaseDaoImpl;
import com.bigguy.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

	public void saveRegionData(List<Region> regions) {
		
		for (Region region : regions) {
			// 这里使用 saveOrUpdate() 而不使用 save()  方法目的是，如果重复导入，数据添加会失败 
			this.getHibernateTemplate().saveOrUpdate(region); 		// 保存到数据库中 
		}
		
	}

	public List<Region> findListByQ(String q) {
		String sql = "FROM Region r WHERE r.shortcode LIKE ? "+
				" OR r.citycode LIKE ? OR r.province LIKE ? "+
				" OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(sql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		
		return list;
	}

}
