package cn.mauth.ccrm.core.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.core.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class CouponMemberToExcel {

	public  String writeExcel(String fileName,List<MemCoupon> couponMembers) throws IOException{
		Workbook wb = new HSSFWorkbook();
		String filePath = getFilePath(fileName);
		FileOutputStream fileOutputStream=new FileOutputStream(filePath);
		
		Sheet sheet = getSheet(wb, fileName);
		
//		CellStyle cellStyle = getTitleStyle(wb);
		
//		CellStyle quesionStyle = getQuesionStyle(wb);
//		Row rowTitle = getRowTitle(sheet, cellStyle);
		for (int i=0;i<couponMembers.size();i++) {
			CellStyle contentStyle = getContentStyle(wb);
			MemCoupon couponMember = couponMembers.get(i);
			getRowValue(sheet, contentStyle,couponMember,i+1);
		}
		
		wb.write(fileOutputStream);
		
		fileOutputStream.close();
		
		return filePath;
	}

	/**
	 * 获取文件名称
	 * @param fileName
	 * @return
	 */
	public  String getFilePath(String fileName) {
		String rootPath = PathUtil.getWebRootPath();
		rootPath=rootPath+System.getProperty("file.separator") + 
				"archives"+ System.getProperty("file.separator")+
				"excel";
		File filePath=new File(rootPath);
		if(filePath.exists()==false){
			try{
				filePath.mkdir();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		rootPath=rootPath+System.getProperty("file.separator")+fileName+"导出优惠券明细.xls";
		return rootPath;
	}
	
	 /** 创建电子表格sheet
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	public static Sheet getSheet(Workbook wb,String sheetName){
		Sheet sheet = wb.createSheet(sheetName);
		return sheet;
	}
	/**
	 * 通过sheet创建标题栏目
	 * @param sheet
	 * @return
	 */
	public  Row getRowTitle(Sheet sheet,CellStyle cellStyle){
		Row row = sheet.createRow(0);
		row.setHeightInPoints((short)32);
		sheet.setColumnWidth(0,18*256);
		sheet.setColumnWidth(1,18*256);
		sheet.setColumnWidth(2,10*256);
		sheet.setColumnWidth(3,10*256);
		sheet.setColumnWidth(4,10*256);
		sheet.setColumnWidth(5,12*256);
		sheet.setColumnWidth(6,22*256);
		sheet.setColumnWidth(8,10*256);
		sheet.setColumnWidth(9,14*256);
		sheet.setColumnWidth(12,14*256);
		
		
		Cell xuehao = row.createCell(0);
		xuehao.setCellStyle(cellStyle);
		xuehao.setCellValue("名称");
		
		Cell dealDateCell = row.createCell(1);
		dealDateCell.setCellStyle(cellStyle);
		dealDateCell.setCellValue("sn码");
		
		Cell brandCell = row.createCell(2);
		brandCell.setCellStyle(cellStyle);
		brandCell.setCellValue("类型");
		
		Cell onwerCompanyCell = row.createCell(3);
		onwerCompanyCell.setCellStyle(cellStyle);
		onwerCompanyCell.setCellValue("代金券金额");
		
		Cell xiaoshouwangdian = row.createCell(4);
		xiaoshouwangdian.setCellStyle(cellStyle);
		xiaoshouwangdian.setCellValue("会员姓名");
		
		Cell xiaoshoubumen = row.createCell(5);
		xiaoshoubumen.setCellStyle(cellStyle);
		xiaoshoubumen.setCellValue("电话");
		
		Cell chexingbumen = row.createCell(6);
		chexingbumen.setCellStyle(cellStyle);
		chexingbumen.setCellValue("有效期");
		
		Cell overTime = row.createCell(7);
		overTime.setCellStyle(cellStyle);
		overTime.setCellValue("过期状态");
		
		Cell xiaoshouriqiCell = row.createCell(8);
		xiaoshouriqiCell.setCellStyle(cellStyle);
		xiaoshouriqiCell.setCellValue("创建人");
		
		Cell crateDate = row.createCell(9);
		crateDate.setCellStyle(cellStyle);
		crateDate.setCellValue("创建时间");
		
		Cell enaled = row.createCell(10);
		enaled.setCellStyle(cellStyle);
		enaled.setCellValue("是否启用");
		
		Cell huiminPriceCell = row.createCell(11);
		huiminPriceCell.setCellStyle(cellStyle);
		huiminPriceCell.setCellValue("是否使用");
		
		Cell customerNameCell = row.createCell(12);
		customerNameCell.setCellStyle(cellStyle);
		customerNameCell.setCellValue("使用时间");
		
		Cell customerAddressCell = row.createCell(13);
		customerAddressCell.setCellStyle(cellStyle);
		customerAddressCell.setCellValue("使用操作人");
		
		return row;
	}

	/**
	 * 创建行
	 */
	public  Row getRowValue(Sheet sheet, CellStyle cellStyle, MemCoupon couponMember, int indexs){
		if(null==couponMember){
			return null;
		}
		
		Row row = sheet.createRow(indexs);
		row.setHeightInPoints((short)28);
		Cell xuehao = row.createCell(0);
		xuehao.setCellStyle(cellStyle);
		String name = couponMember.getName();
		if(null!=name&&name.trim().length()>0){
			xuehao.setCellValue(name);
		}else{
			xuehao.setCellValue("");
		}
		//sn码
		Cell snCode = row.createCell(1);
		snCode.setCellStyle(cellStyle);
		if(null!=couponMember.getCode()){
			snCode.setCellValue(couponMember.getCode());
		}else{
			snCode.setCellValue("");
		}
		//类型
		Cell typeCell = row.createCell(2);
		typeCell.setCellStyle(cellStyle);
		if(null!=couponMember.getType()){
			if((int)couponMember.getType()==1){
				typeCell.setCellValue("折扣券");
			}
			if((int)couponMember.getType()==2){
				typeCell.setCellValue("代金券");
			}
			}
		else{
			typeCell.setCellValue("");
		}
		//金额或折扣
		Cell moneyRabattCell = row.createCell(3);
		moneyRabattCell.setCellStyle(cellStyle);
		
		//姓名
		Cell memberName = row.createCell(4);
		memberName.setCellStyle(cellStyle);
		CellStyle memberNameCell = memberName.getCellStyle();
		memberNameCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		memberName.setCellStyle(memberNameCell);
		//电话
		Cell phoneCell = row.createCell(5);
		phoneCell.setCellStyle(cellStyle);
		CellStyle xiaoshoubumenCell = phoneCell.getCellStyle();
		xiaoshoubumenCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		phoneCell.setCellStyle(xiaoshoubumenCell);
		//有效期
		Cell startStopTime = row.createCell(6);
		startStopTime.setCellStyle(cellStyle);
		CellStyle carOwnerByCompanyCell = startStopTime.getCellStyle();
		carOwnerByCompanyCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		startStopTime.setCellStyle(carOwnerByCompanyCell);
		if(null!=couponMember.getStartTime()||null!=couponMember.getStopTime()){
				startStopTime.setCellValue(DateUtil.format(couponMember.getStartTime())+"~"+DateUtil.format(couponMember.getStopTime()));
		}else{
			startStopTime.setCellValue("");
		}
		
		Cell overTimeCell = row.createCell(7);
		overTimeCell.setCellStyle(cellStyle);
		carOwnerByCompanyCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		overTimeCell.setCellStyle(carOwnerByCompanyCell);
		Date date=new Date();
		boolean after = date.after(couponMember.getStopTime());
		
		//创建人
		Cell cratorCell = row.createCell(8);
		cratorCell.setCellStyle(cellStyle);
		CellStyle saleDateCell = cratorCell.getCellStyle();
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cratorCell.setCellStyle(saleDateCell);
		cratorCell.setCellValue(couponMember.getCreatorName());
		
		//创建时间
		Cell createDate = row.createCell(9);
		createDate.setCellStyle(cellStyle);
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		createDate.setCellStyle(saleDateCell);
		createDate.setCellValue(DateUtil.format(couponMember.getCreateTime()));
		//是否启用
		Cell enabledCell = row.createCell(10);
		enabledCell.setCellStyle(cellStyle);
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		enabledCell.setCellStyle(saleDateCell);

		//是使用
		Cell useedCell = row.createCell(11);
		useedCell.setCellStyle(cellStyle);
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		useedCell.setCellStyle(saleDateCell);

		
		//使用时间
		Cell useDateCell = row.createCell(12);
		useDateCell.setCellStyle(cellStyle);
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		useDateCell.setCellStyle(saleDateCell);

		//使用操作人
		Cell usePersonCell = row.createCell(13);
		usePersonCell.setCellStyle(cellStyle);
		saleDateCell.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		usePersonCell.setCellStyle(saleDateCell);
		return row;
	}

	/**
	 * 标题栏目样式表
	 * @param wb
	 * @return
	 */
	public static CellStyle getTitleStyle(Workbook wb) {
		   CellStyle cellStyle = wb.createCellStyle();
		   Font font = wb.createFont();
		   cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		   cellStyle.setVerticalAlignment(VerticalAlignment.TOP);//垂直居中
		   font.setFontHeightInPoints((short)10);
	       cellStyle.setFont(font);
	       //设置边框样式
	     cellStyle.setBorderTop(BorderStyle.THIN);
	     cellStyle.setBorderBottom(BorderStyle.THIN);
	     cellStyle.setBorderLeft(BorderStyle.THIN);
	     cellStyle.setBorderRight(BorderStyle.THIN);
	     //设置边框颜色
	     cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
	     //设置背景色
	     cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置背景色
	     cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置前景色
	     cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		 return cellStyle;
	}

	/**
	 * 标题栏目样式表
	 * @param wb
	 * @return
	 */
	public static CellStyle getContentStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		  //设置边框样式
	     cellStyle.setBorderTop(BorderStyle.THIN);
	     cellStyle.setBorderBottom(BorderStyle.THIN);
	     cellStyle.setBorderLeft(BorderStyle.THIN);
	     cellStyle.setBorderRight(BorderStyle.THIN);
	     //设置边框颜色
	     cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
	     cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		 cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		 cellStyle.setWrapText(true);
		return cellStyle;
	}

	/**
	 * 标题栏目样式表
	 * @param wb
	 * @return
	 */
	public static CellStyle getQuesionStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		

		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
}
