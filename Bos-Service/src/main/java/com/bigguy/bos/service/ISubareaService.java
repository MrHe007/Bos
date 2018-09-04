package com.bigguy.bos.service;

import java.util.List;

import com.bigguy.bos.domain.Subarea;
import com.bigguy.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);
	public void pageQuery(PageBean pageBean);
	public List<Subarea> findAll();
	public List<Subarea> getAvailableSubareaList();
}
