package cn.mauth.ccrm.core.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;

@Component
public class OrderToExcel {
	public  String writeExcel(String fileName,List<MemStartWriting> orders) throws IOException{
		Workbook wb = new HSSFWorkbook();
		String filePath = getFilePath(fileName);
		FileOutputStream fileOutputStream=new FileOutputStream(filePath);
		
		Sheet sheet = getSheet(wb, fileName);
		
		CellStyle cellStyle = getTitleStyle(wb);
		
		CellStyle quesionStyle = getQuesionStyle(wb);
		Row rowTitle = getRowTitle(sheet, cellStyle);
		for (int i=0;i<orders.size();i++) {
			CellStyle contentStyle = getContentStyle(wb);
			MemStartWriting order = orders.get(i);
			getRowValue(sheet, contentStyle,order,i+1);
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
		rootPath=rootPath+System.getProperty("file.separator")+fileName+"订单明细.xls";
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
		sheet.setColumnWidth(7,10*256);
		sheet.setColumnWidth(8,14*256);
		sheet.setColumnWidth(9,14*256);
		sheet.setColumnWidth(10,10*256);
		
		
		Cell xuehao = row.createCell(0);
		xuehao.setCellStyle(cellStyle);
		xuehao.setCellValue("订单标号");
		
		Cell dealDateCell = row.createCell(1);
		dealDateCell.setCellStyle(cellStyle);
		dealDateCell.setCellValue("会员");
		
		Cell brandCell = row.createCell(2);
		brandCell.setCellStyle(cellStyle);
		brandCell.setCellValue("房间");
		
		Cell onwerCompanyCell = row.createCell(3);
		onwerCompanyCell.setCellStyle(cellStyle);
		onwerCompanyCell.setCellValue("开单时间");
		
		Cell xiaoshouwangdian = row.createCell(4);
		xiaoshouwangdian.setCellStyle(cellStyle);
		xiaoshouwangdian.setCellValue("订单金额");
		
		Cell xiaoshoubumen = row.createCell(5);
		xiaoshoubumen.setCellStyle(cellStyle);
		xiaoshoubumen.setCellValue("折扣金额");
		
		Cell chexingbumen = row.createCell(6);
		chexingbumen.setCellStyle(cellStyle);
		chexingbumen.setCellValue("实收金额");
		
		Cell overTime = row.createCell(7);
		overTime.setCellStyle(cellStyle);
		overTime.setCellValue("订单状态");
		
		Cell enaled = row.createCell(10);
		enaled.setCellStyle(cellStyle);
		enaled.setCellValue("收银员");
		
		return row;
	}
	/**
	 * 创建行
	 */
	public  Row getRowValue(Sheet sheet, CellStyle cellStyle, MemStartWriting order, int indexs){
		if(null==order){
			return null;
		}
		
		Row row = sheet.createRow(indexs);
		row.setHeightInPoints((short)28);
		Cell cellName = row.createCell(0);
		cellName.setCellStyle(cellStyle);
		String name = order.getOrderNo();
		if(null!=name&&name.trim().length()>0){
			cellName.setCellValue(name);
		}else{
			cellName.setCellValue("");
		}
		//phone
		Cell cellPhone = row.createCell(1);
		cellPhone.setCellStyle(cellStyle);
		if(null!=order.getMemName()){
			cellPhone.setCellValue(order.getMemName());
		}else{
			cellPhone.setCellValue("");
		}
		//cardName
		Cell cellCardName = row.createCell(2);
		cellCardName.setCellStyle(cellStyle);
		if(null!=order.getRoomName()){
			cellCardName.setCellValue(order.getRoomName());
			}
		else{
			cellCardName.setCellValue("");
		}
		//金额或折扣
		Cell cellCardNo = row.createCell(3);
		cellCardNo.setCellStyle(cellStyle);
		if(null!=order.getCreateTime()){
			cellCardNo.setCellValue(DateUtil.formatDate(order.getCreateTime()));
			}
		else{
			cellCardNo.setCellValue("");
		}
		
		
		//sex
		Cell cellSex = row.createCell(4);
		cellSex.setCellStyle(cellStyle);
		if(null!=order.getMoney()){
			cellSex.setCellValue(order.getMoney());
			}
		else{
			cellSex.setCellValue("");
		}
		//电话
		Cell cellStatus = row.createCell(5);
		cellStatus.setCellStyle(cellStyle);
		
		if(null!=order.getDiscountMoney()){
			cellStatus.setCellValue(order.getDiscountMoney());
		}
		else{
			cellStatus.setCellValue("");
		}
		//余额
		Cell cellBalance = row.createCell(6);
		cellBalance.setCellStyle(cellStyle);
		CellStyle cellStyleBalance = cellBalance.getCellStyle();
		cellStyleBalance.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellBalance.setCellStyle(cellStyleBalance);
		if(null!=order.getActualMoney()){
			cellBalance.setCellValue(order.getActualMoney());
		}else{
			cellBalance.setCellValue("");
		}
		
		Cell cellOveragePiont = row.createCell(7);
		cellOveragePiont.setCellStyle(cellStyle);
		CellStyle cellStyleOveragePiont = cellBalance.getCellStyle();
		cellStyleOveragePiont.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellOveragePiont.setCellStyle(cellStyleOveragePiont);
		if(null!=order.getState()){
			if(order.getState()==CommonState.Going){
				cellOveragePiont.setCellValue("未结账");
			}
			else if(order.getState()==CommonState.Normal){
				cellOveragePiont.setCellValue("已结账");
			}
			else if(order.getState()==CommonState.Cancel){
				cellOveragePiont.setCellValue("已取消");
			}
		}else{
			cellOveragePiont.setCellValue("");
		}
		
		//是否启用
		Cell cellTotalPoint = row.createCell(10);
		cellTotalPoint.setCellStyle(cellStyle);
		CellStyle cellStyleTotalPoint = cellTotalPoint.getCellStyle();
		cellStyleTotalPoint.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellTotalPoint.setCellStyle(cellStyleTotalPoint);
		cellTotalPoint.setCellValue(order.getCreator());
		//是使用
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
		   cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
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
		//cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		return cellStyle;
	}
}
