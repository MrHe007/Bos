package com.bigguy.bos.service;

import java.util.List;

import com.bigguy.bos.domain.Decidedzone;
import com.bigguy.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model, String[] subareaId);

	public List<Decidedzone> findAll();

	public void pageQuery(PageBean pageBean);

}
