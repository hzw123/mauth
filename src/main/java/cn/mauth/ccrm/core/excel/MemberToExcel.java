package cn.mauth.ccrm.core.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.core.domain.mem.Mem;

@Component
public class MemberToExcel {

	public  String writeExcel(String fileName,List<Mem> members) throws IOException{
		Workbook wb = new HSSFWorkbook();
		String filePath = getFilePath(fileName);
		FileOutputStream fileOutputStream=new FileOutputStream(filePath);
		
		Sheet sheet = getSheet(wb, fileName);
		
		CellStyle cellStyle = getTitleStyle(wb);
		
		CellStyle quesionStyle = getQuesionStyle(wb);
		Row rowTitle = getRowTitle(sheet, cellStyle);
		for (int i=0;i<members.size();i++) {
			CellStyle contentStyle = getContentStyle(wb);
			Mem member = members.get(i);
			getRowValue(sheet, contentStyle,member,i+1);
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
		rootPath=rootPath+System.getProperty("file.separator")+fileName+"会员信息.xls";
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
		sheet.setColumnWidth(13,10*256);
		sheet.setColumnWidth(14,14*256);
		sheet.setColumnWidth(15,14*256);
		
		
		Cell xuehao = row.createCell(0);
		xuehao.setCellStyle(cellStyle);
		xuehao.setCellValue("姓名");
		
		Cell dealDateCell = row.createCell(1);
		dealDateCell.setCellStyle(cellStyle);
		dealDateCell.setCellValue("电话");
		
		Cell brandCell = row.createCell(2);
		brandCell.setCellStyle(cellStyle);
		brandCell.setCellValue("会员卡级别");
		
		Cell onwerCompanyCell = row.createCell(3);
		onwerCompanyCell.setCellStyle(cellStyle);
		onwerCompanyCell.setCellValue("会员卡号");
		
		Cell xiaoshouwangdian = row.createCell(4);
		xiaoshouwangdian.setCellStyle(cellStyle);
		xiaoshouwangdian.setCellValue("性别");
		
		Cell xiaoshoubumen = row.createCell(5);
		xiaoshoubumen.setCellStyle(cellStyle);
		xiaoshoubumen.setCellValue("状态");
		
		Cell chexingbumen = row.createCell(6);
		chexingbumen.setCellStyle(cellStyle);
		chexingbumen.setCellValue("余额");
		
		Cell overTime = row.createCell(7);
		overTime.setCellStyle(cellStyle);
		overTime.setCellValue("剩余积分");
		
		Cell xiaoshouriqiCell = row.createCell(8);
		xiaoshouriqiCell.setCellStyle(cellStyle);
		xiaoshouriqiCell.setCellValue("生日");
		
		Cell crateDate = row.createCell(9);
		crateDate.setCellStyle(cellStyle);
		crateDate.setCellValue("累计消费");
		
		Cell enaled = row.createCell(10);
		enaled.setCellStyle(cellStyle);
		enaled.setCellValue("总积分");
		
		Cell huiminPriceCell = row.createCell(11);
		huiminPriceCell.setCellStyle(cellStyle);
		huiminPriceCell.setCellValue("消费次数");
		
		Cell customerNameCell = row.createCell(12);
		customerNameCell.setCellStyle(cellStyle);
		customerNameCell.setCellValue("累计充值");
		
		Cell customerAddressCell = row.createCell(13);
		customerAddressCell.setCellStyle(cellStyle);
		customerAddressCell.setCellValue("累计充值");
		
		Cell cellLastBuyDate = row.createCell(13);
		cellLastBuyDate.setCellStyle(cellStyle);
		cellLastBuyDate.setCellValue("最后消费时间");
		
		Cell cellCreateTime = row.createCell(14);
		cellCreateTime.setCellStyle(cellStyle);
		cellCreateTime.setCellValue("创建时间");
		
		Cell cellCreator = row.createCell(15);
		cellCreator.setCellStyle(cellStyle);
		cellCreator.setCellValue("创建人");
		
		return row;
	}
	/**
	 * 创建行
	 */
	public  Row getRowValue(Sheet sheet, CellStyle cellStyle, Mem member, int indexs){
		if(null==member){
			return null;
		}
		
		Row row = sheet.createRow(indexs);
		row.setHeightInPoints((short)28);
		Cell cellName = row.createCell(0);
		cellName.setCellStyle(cellStyle);
		String name = member.getName();
		if(null!=name&&name.trim().length()>0){
			cellName.setCellValue(name);
		}else{
			cellName.setCellValue("");
		}
		//phone
		Cell cellPhone = row.createCell(1);
		cellPhone.setCellStyle(cellStyle);
		if(null!=member.getMobilePhone()){
			cellPhone.setCellValue(member.getMobilePhone());
		}else{
			cellPhone.setCellValue("");
		}
		//cardName
		Cell cellCardName = row.createCell(2);
		cellCardName.setCellStyle(cellStyle);
		if(null!=member.getMemberCard().getName()){
			cellCardName.setCellValue(member.getMemberCard().getName());
			}
		else{
			cellCardName.setCellValue("");
		}
		//金额或折扣
		Cell cellCardNo = row.createCell(3);
		cellCardNo.setCellStyle(cellStyle);
		if(null!=member.getMemberCardNo()){
			cellCardNo.setCellValue(member.getMemberCardNo());
			}
		else{
			cellCardNo.setCellValue("");
		}
		
		
		//sex
		Cell cellSex = row.createCell(4);
		cellSex.setCellStyle(cellStyle);
		if(null!=member.getSex()){
			cellSex.setCellValue(member.getSex());
			}
		else{
			cellSex.setCellValue("");
		}
		//电话
		Cell cellStatus = row.createCell(5);
		cellStatus.setCellStyle(cellStyle);
		
		int state=member.getState();

			String statusName="正常";
			if(state==9998){
				statusName="冻结";
			}
			else if(state==9999){
				statusName="注销";
			}
			cellStatus.setCellValue(statusName);
		//余额
		Cell cellBalance = row.createCell(6);
		cellBalance.setCellStyle(cellStyle);
		CellStyle cellStyleBalance = cellBalance.getCellStyle();
		cellStyleBalance.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellBalance.setCellStyle(cellStyleBalance);
		if(null!=member.getBalance()){
			cellBalance.setCellValue(member.getBalance());
		}else{
			cellBalance.setCellValue("");
		}
		
		Cell cellOveragePiont = row.createCell(7);
		cellOveragePiont.setCellStyle(cellStyle);
		CellStyle cellStyleOveragePiont = cellBalance.getCellStyle();
		cellStyleOveragePiont.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellOveragePiont.setCellStyle(cellStyleOveragePiont);
		if(null!=member.getRemainderPoint()){
			cellOveragePiont.setCellValue(member.getRemainderPoint());
		}else{
			cellOveragePiont.setCellValue("");
		}
		
		//创建人
		Cell cellBirthday = row.createCell(8);
		cellBirthday.setCellStyle(cellStyle);
		CellStyle cellStyleBirthday = cellBirthday.getCellStyle();
		cellStyleBirthday.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellBirthday.setCellStyle(cellStyleBirthday);
		if(null!=member.getBirthday()){
			cellBirthday.setCellValue(DateUtil.format(member.getBirthday()));
		}
		else{
			cellBirthday.setCellValue("");
		}
		
		//创建时间
		Cell cellTotalMoney = row.createCell(9);
		cellTotalMoney.setCellStyle(cellStyle);
		CellStyle cellStyleTotalMoney = cellBalance.getCellStyle();
		cellStyleTotalMoney.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellTotalMoney.setCellStyle(cellStyleTotalMoney);
		cellTotalMoney.setCellValue(member.getConsumeMoney());
		
		//是否启用
		Cell cellTotalPoint = row.createCell(10);
		cellTotalPoint.setCellStyle(cellStyle);
		CellStyle cellStyleTotalPoint = cellTotalPoint.getCellStyle();
		cellStyleTotalPoint.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellTotalPoint.setCellStyle(cellStyleTotalPoint);
		cellTotalPoint.setCellValue(member.getRemainderPoint());
		//是使用
		Cell cellTotalBuy = row.createCell(11);
		cellTotalBuy.setCellStyle(cellStyle);
		CellStyle cellStyleTotalBuy = cellTotalBuy.getCellStyle();
		cellStyleTotalBuy.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellTotalBuy.setCellStyle(cellStyleTotalBuy);
		cellTotalBuy.setCellValue(member.getTotalBuy());
		
		//使用时间
		Cell cellTotalStormMoney = row.createCell(12);
		cellTotalStormMoney.setCellStyle(cellStyle);
		CellStyle cellStyleTotalStormMoney=cellTotalStormMoney.getCellStyle();
		cellStyleTotalStormMoney.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellTotalStormMoney.setCellStyle(cellStyleTotalStormMoney);
		if(null!=member.getTotalCardMoney()){
			cellTotalStormMoney.setCellValue(member.getTotalStartMoney());
		}else{
			cellTotalStormMoney.setCellValue("");
		}
		
		//使用操作人
		Cell cellLastBuyDate = row.createCell(13);
		cellLastBuyDate.setCellStyle(cellStyle);
		CellStyle cellStyleLastBuyDate=cellLastBuyDate.getCellStyle();
		cellStyleLastBuyDate.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellLastBuyDate.setCellStyle(cellStyleLastBuyDate);
		if(null!=member.getLastBuyDate()){
			cellLastBuyDate.setCellValue(DateUtil.format(member.getLastBuyDate()));
		}else{
			cellLastBuyDate.setCellValue("");
		}
		
		Cell cellCreateTime = row.createCell(14);
		cellCreateTime.setCellStyle(cellStyle);
		CellStyle cellStyleCreateTime=cellCreateTime.getCellStyle();
		cellStyleLastBuyDate.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellCreateTime.setCellStyle(cellStyleLastBuyDate);
		if(null!=member.getCreateTime()){
			cellCreateTime.setCellValue(DateUtil.format(member.getCreateTime()));
		}else{
			cellCreateTime.setCellValue("");
		}
		
		Cell cellCreator = row.createCell(15);
		cellCreator.setCellStyle(cellStyle);
		CellStyle cellStyleCreator=cellCreator.getCellStyle();
		cellStyleCreator.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		cellCreator.setCellStyle(cellStyleCreator);
		if(null!=member.getCreator()){
			cellCreator.setCellValue(member.getCreator());
		}else{
			cellCreator.setCellValue("");
		}
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
		return cellStyle;
	}
}
