package com.bigguy.bos.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bigguy.bos.dao.impl.SubareaDaoeImpl;
import com.bigguy.bos.domain.Region;
import com.bigguy.bos.domain.Subarea;
import com.bigguy.bos.service.ISubareaService;
import com.bigguy.bos.utils.FileUtils;
import com.bigguy.bos.web.action.base.BaseAction;

@Controller
public class SubareaAction extends BaseAction<Subarea> {

	@Autowired
	ISubareaService service;
	
	
	public String getSubareaListAjax() {
		
		// 得到没有被关联定区的的分区
		
		List<Subarea> list = service.getAvailableSubareaList();
		
		String java2json = java2json(list, new String[]{"decidedzone","region"});
		
		prinToPage(java2json); 		// 输出到页面 
		
		return NONE;
	}
	
	
	public String exportXls() throws IOException {
		
		// 将 subarea 导出成 xls 文件
		// 这里假设是将所有的 分区 数据都导出来
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("分区数据");
		
		HSSFRow headRow = sheet.createRow(0); 	// 创建一行 , row 表示是行号
		headRow.createCell(0).setCellValue("id");
		headRow.createCell(1).setCellValue("region_id");
		headRow.createCell(2).setCellValue("addresskey");
		headRow.createCell(3).setCellValue("startnum");
		headRow.createCell(4).setCellValue("endnum");
		headRow.createCell(5).setCellValue("single");
		headRow.createCell(6).setCellValue("position");
		
		List<Subarea> subareas = service.findAll();
		
		for (Subarea subarea : subareas) {
			
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			dataRow.createCell(3).setCellValue(subarea.getStartnum());
			dataRow.createCell(4).setCellValue(subarea.getEndnum());
			dataRow.createCell(5).setCellValue(subarea.getSingle());
			dataRow.createCell(6).setCellValue(subarea.getPosition());
		}
		
		String filename = "分区数据表.xls"; 		// 有中文，需要根据浏览器自动转换编码
		
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		// 需要重新改变编码
		filename = FileUtils.encodeDownloadFilename(filename, request.getHeader("User-Agent"));
		
		System.out.println(filename);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		// 下载需要设置一个流 ， 两个头
		response.setContentType(contentType);
		response.setHeader("content-disposition", "attachement;filename="+filename);
		
		book.write(response.getOutputStream());
		
		return NONE;
	}
	
	
	public String pageQuery() {
		
		System.out.println(model);
		System.out.println(pageBean);

		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		
		String addresskey = model.getAddresskey();
		
		if(StringUtils.isNotBlank(addresskey)) {
			criteria.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		
		// 多表查询，携带请求参数
		Region region = model.getRegion(); 
		if(region!=null ) {
			// 如果不为空，说明是多表查询
			
			criteria.createAlias("region", "r"); 		// 发送的sql 为连接，此时为另一张表起别名
			
			if(StringUtils.isNotBlank(region.getProvince())) {
				criteria.add(Restrictions.like("r.province", "%"+region.getProvince()+"%"));
			}
			
			if(StringUtils.isNotBlank(region.getCity())) {
				criteria.add(Restrictions.like("r.city", "%"+region.getCity()+"%"));
			}
			
			if(StringUtils.isNotBlank(region.getDistrict())) {
				criteria.add(Restrictions.like("r.district","%"+region.getDistrict()+"%"));
			}
		}
		
		service.pageQuery(pageBean); 		// 通过引用改变内部的值 
		
		String json = java2json(pageBean, new String[] {"decidedzone","subareas","currentPage"});
		
		prinToPage(json);
		
		return NONE;
	}
	
	public String saveSubarea() {
		
		service.save(model); 
		
		return "subareaList";
	}
	
}



