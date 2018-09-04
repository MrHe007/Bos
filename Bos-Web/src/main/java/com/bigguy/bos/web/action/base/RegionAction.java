package com.bigguy.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import com.bigguy.bos.domain.Region;
import com.bigguy.bos.service.RegionService;
import com.bigguy.bos.utils.PinYin4jUtils;

import net.sf.json.JSONObject;

public class RegionAction extends BaseAction<Region>{

	private File regionFile; 	// 接受 excle 文件
	
	
	@Autowired
	RegionService service;
	
	private String q; 		// combobox 设置的 mode 格式
	
	public String listAjax() {
		
		List<Region> allRegions  = null;
		
		if(StringUtils.isBlank(q)) { 	// 为空， 或者为空字符
			allRegions = service.getAllRegions();
			
		}else {
			// 可以通过关键字的短码查询 
			allRegions = service.getListByQ(q);
		}
		
		
		String json = java2json(allRegions, new String[] {"subareas"});
		
		prinToPage(json);
		
		
		return NONE;
	}
	
	public String getRegionList() {
		// 得到 json 数据，返回 json 数据 
		
		List<Region> regions = service.getAllRegions();
		
		service.pageQuery(pageBean); 		// 会将数据封装到 pageBean 中，通过引用返回来 
		
		String json = java2json(pageBean,new String[] {"subareas"});		// 设置某些字段，不让其传到客户端 
		
		prinToPage(json); 		// 数据写到对象页面中 
		
		return NONE;
	}
	
	
	public String importXls() throws FileNotFoundException, IOException {
		// 首先先提取出 excel 里的文件数据
		
		
		HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(regionFile));
		
		HSSFSheet sheet = workBook.getSheetAt(0); 		// 得到第1页
		
		List<Region> regions  = new ArrayList<>();
		
		for (Row row : sheet) { 	// 每一行数据

			String id = row.getCell(0).getStringCellValue(); 
			String province = row.getCell(1).getStringCellValue(); 
			String city = row.getCell(2).getStringCellValue(); 
			String district = row.getCell(3).getStringCellValue(); 
			String postcode = row.getCell(4).getStringCellValue(); 
			
				
			
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			
			province = province.substring(0, province.length()-1); 		//  去掉最后一个省字
			city = city.substring(0, city.length()-1); 		//  去掉最后一个省字
			district = district.substring(0, district.length()-1); 		//  去掉最后一个省字
		
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);	
			
			
			String shortcode = StringUtils.join(headByString);
			String citycode = PinYin4jUtils.hanziToPinyin(city,""); // 默认是 shi jia zhuan  , 中国文字有分隔符 
			
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			
			regions.add(region); 	// 将数据添加到集合中
		}
		
		service.saveRegionData(regions);
		
		return NONE;
	}


	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}


}
