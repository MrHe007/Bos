package com.bigguy.bos.dao.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.bigguy.bos.utils.PinYin4jUtils;

public class TestPOI {
	
//	@Test
	public void test() throws FileNotFoundException, IOException {
		
		String path = "J:\\java 视频\\javaEE视频\\物流管理项目\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls";
		
		// 包装一个 excel 文件对象
		HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(new File(path)));
		
		// 得到sheet 页面
		HSSFSheet sheet = workBook.getSheetAt(0); 
		
		for (Row row : sheet) { 	// 遍历每行
			for (Cell cell : row) { 	// 遍历每行的每个单元格 
				String value = cell.getStringCellValue(); 	// 得到每一个 cell 的文本值
				System.out.print(value +"  ");
			}
			System.out.println();
		}
		
	}
	
//	@Test
	public void testPingYing() {
		
		String province = "河北省";
		String city = "石家庄市";
		String district = "桥西区";
		
		
		province = province.substring(0, province.length()-1);
		city = city.substring(0, city.length()-1);
		district = district.substring(0, district.length()-1);
		
		String info = province + city + district;
		
		// StringUtils 下的方法 
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		
		System.out.println(headByString);
		System.out.println(StringUtils.join(headByString, ""));
		System.out.println(PinYin4jUtils.hanziToPinyin(city));
		
	}

}
