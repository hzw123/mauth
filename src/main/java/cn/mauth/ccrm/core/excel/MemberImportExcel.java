package cn.mauth.ccrm.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberImportExcel {
	private final static Logger log= LoggerFactory.getLogger(MemberImportExcel.class);
	public static String headers[] = new String[] { 
		"姓名","电话","性别", "生日"
	};
	private MemberServer memberServer;
	private MemLogServer memLogServer;
	public MemberImportExcel(MemberServer memberServer, MemLogServer memLogServer)
	{
		this.memberServer=memberServer;
		this.memLogServer=memLogServer;
	}
	/**
	 * 功能描述：根据文档的内容判断上传文档是否为空
	 * @param file
	 * @return
	 */
	public static boolean validateDocument(File file){
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			int sheehtsNum = hssfWorkbook.getNumberOfSheets();
			if (sheehtsNum > 0) {
				boolean state=false;
				for (int i = 0; i < sheehtsNum; i++) {
					HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
					int lastRowNum = sheet.getLastRowNum();
					if(lastRowNum>1){
						state=true;
						break ;
					}else{
						state=false;
						continue;
					}
				}
				if(state){
					return true;
				}else{
					return false;
				}
			}else{
				log.info("文档内容为空！");
				return false;
			}
		} catch (FileNotFoundException e) {
			log.info("找不到文档！");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			log.info("文档IO发送错误！");
			return false;
		}
	}
	/**
	 * 功能描述：判断文档头部模板是否规范
	 * 1、判断文档头部长度是否一致
	 * 2、判断文档内容顺序是否一致
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean validateForm(File file) throws IOException {
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			int sheehtsNum = hssfWorkbook.getNumberOfSheets();
			if (sheehtsNum > 0) {
				for (int i = 0; i < sheehtsNum; i++) {
					HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
					int lastRowNum = sheet.getLastRowNum();
					if(lastRowNum>1){
						for (int rowJ = 0; rowJ <lastRowNum; rowJ++) {
							if(rowJ>=1){
								break;
							}
							HSSFRow row = sheet.getRow(rowJ);
							short lastCellNum = row.getLastCellNum();
							//判断excel数据的头部长度是否一致
							if(lastCellNum==headers.length){
								for (int colJ = 0; colJ <lastCellNum; colJ++) {
									HSSFCell cell = row.getCell(colJ);
									String value = cell.getStringCellValue();
									for (int headerJ = 0; headerJ < lastCellNum; headerJ++) {
										if(value.equals(headers[headerJ])){
											break;
										}
									}
								}
							}else{//判断头部
								log.info("sheet"+rowJ+"中文档模板的头部不一致！");
								return false;
							}
							
						}
					}else{
						log.info("sheet"+i+"为空！");
					}
				}
			}else{
				log.info("文档内容为空！");
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// 创建输入
		return true;
	}
	/**
	 * 功能描述：通过excel获取联系人信息
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<String> validateMember(File file) throws IOException {
		List<String> errorMessages=new ArrayList<String>();
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			int sheehtsNum = hssfWorkbook.getNumberOfSheets();
			if (sheehtsNum > 0) {
				for (int i = 0; i < sheehtsNum; i++) {
					HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
					int lastRowNum = sheet.getLastRowNum();
					if (lastRowNum >= 1) {
						for (int rowJ = 1; rowJ <= lastRowNum; rowJ++) {
							String errorMessage=new String();
							HSSFRow row = sheet.getRow(rowJ);
							//姓名
							HSSFCell nameCell = row.getCell(0);
							Boolean nameStatus=false;
							if(null!=nameCell){
								nameCell.setCellType(Cell.CELL_TYPE_STRING);
								String name = nameCell.getStringCellValue();
								if(null!=name){
									int length = name.trim().length();
									if(length>0&&length<50){
										nameStatus=true;
									}else{
										errorMessage=errorMessage+" 会员姓名格式错误,";
									}
								}else{
									errorMessage=errorMessage+" 会员姓名为空,";
								}
							}else{
								errorMessage=errorMessage+" 会员姓名为空,";
							}
							//电话
							HSSFCell mobilePhoneCell = row.getCell(1);
							Boolean mobilePhoneStatus=false;
							if(null!=mobilePhoneCell){
								mobilePhoneCell.setCellType(Cell.CELL_TYPE_STRING);
								String mobilePhoneValue = mobilePhoneCell.getStringCellValue();
								if(null!=mobilePhoneValue){
									boolean checkMobileStatus = checkMobile(mobilePhoneValue);
									if(checkMobileStatus==true){
										long countSqlResult = memberServer.getRepository().findByMobilePhone(mobilePhoneValue).size();
										if(countSqlResult>0){
											errorMessage=errorMessage+" 联系电话会员信息已经存在,";
										}else{
											mobilePhoneStatus=true;
										}
									}else{
										errorMessage=errorMessage+" 电话号码格式错误,";
									}
								}else{
									errorMessage=errorMessage+" 电话号码格式错误,";
								}
							}else{
								errorMessage=errorMessage+" 电话号码格式错误,";
							}
							//性别
							HSSFCell sexCell = row.getCell(2);
							Boolean sexStatus=false;
							if(null!=sexCell){
								sexCell.setCellType(Cell.CELL_TYPE_STRING);
								String sqrNoCellValue = sexCell.getStringCellValue();
								if(null!=sqrNoCellValue&&sqrNoCellValue.trim().length()>0){
									int length = sqrNoCellValue.trim().length();
									if(length>0&&length<5){
										sexStatus=true;
									}
								}
							}
							if(sexStatus==false){
								errorMessage=errorMessage+" 性别内容错误,";
							}
							//生日
							HSSFCell birthdayDateCell = row.getCell(3);
							boolean birthdayDateStatus=false;
							if(null!=birthdayDateCell){
								int cellType = birthdayDateCell.getCellType();
								if(cellType==Cell.CELL_TYPE_NUMERIC){
									if(HSSFDateUtil.isCellDateFormatted(birthdayDateCell)){
										Date dateCellValue = birthdayDateCell.getDateCellValue();
										if(null!=dateCellValue){
											birthdayDateStatus=true;
										}
									}
								}
								if(Cell.CELL_TYPE_STRING==cellType){
									String stringCellValue = birthdayDateCell.getStringCellValue();
									if(null!=stringCellValue){
										Date string2Date = DateUtil.string2Date(stringCellValue);
										birthdayDateStatus=true;
									}
								}
							}
							if(birthdayDateStatus==false){
								errorMessage=errorMessage+"工厂订单日期错误";
							}
							if(nameStatus==true&&mobilePhoneStatus==true&&sexStatus==true&&birthdayDateStatus==true){
							}else{
								String sn="第"+rowJ+"行:";
								errorMessages.add(sn+errorMessage);
							}
						}
					} else {
						log.info("sheet" + i + "为空！");
					}
				}
			} else {
				log.info("文档内容为空！");
			}
			is.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// 创建输入
		return errorMessages;
	}
	/**
	 * 功能描述：通过excel获取联系人信息
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean saveMembers(File file, MemCard memberCard, SysEnterprise enterprise, SysUser user) throws IOException {
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			int sheehtsNum = hssfWorkbook.getNumberOfSheets();
			if (sheehtsNum > 0) {
				for (int i = 0; i < sheehtsNum; i++) {
					HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
					int lastRowNum = sheet.getLastRowNum();
					if (lastRowNum >= 1) {
						for (int rowJ = 1; rowJ <= lastRowNum; rowJ++) {
							Mem member=new Mem();
							HSSFRow row = sheet.getRow(rowJ);
							// 判断excel数据的头部长度是否一致
							//姓名
							HSSFCell nameCell = row.getCell(0);
							if(null!=nameCell){
								nameCell.setCellType(Cell.CELL_TYPE_STRING);
								String name = nameCell.getStringCellValue();
								if(null!=name){
									int length = name.trim().length();
									if(length>0&&length<50){
										member.setName(name);
									}
								}
							}
							//电话
							HSSFCell mobilePhoneCell = row.getCell(1);
							if(null!=mobilePhoneCell){
								mobilePhoneCell.setCellType(Cell.CELL_TYPE_STRING);
								String mobilePhoneValue = mobilePhoneCell.getStringCellValue();
								if(null!=mobilePhoneValue){
									boolean checkMobileStatus = checkMobile(mobilePhoneValue);
									if(checkMobileStatus==true){
										long countSqlResult = memberServer.getRepository().findByMobilePhone(mobilePhoneValue).size();
										if(countSqlResult<=0){
											member.setMobilePhone(mobilePhoneValue);
										}
									}
								}
							}
							//性别
							HSSFCell sexCell = row.getCell(2);
							if(null!=sexCell){
								sexCell.setCellType(Cell.CELL_TYPE_STRING);
								String sexValue = sexCell.getStringCellValue();
								if(null!=sexValue&&sexValue.trim().length()>0){
									int length = sexValue.trim().length();
									if(length>0&&length<5){
										member.setSex(sexValue);
									}
								}
							}
							//生日
							HSSFCell birthdayDateCell = row.getCell(3);
							Date birthdayDate=null;
							if(null!=birthdayDateCell){
								int cellType = birthdayDateCell.getCellType();
								if(cellType==Cell.CELL_TYPE_NUMERIC){
									if(HSSFDateUtil.isCellDateFormatted(birthdayDateCell)){
										Date dateCellValue = birthdayDateCell.getDateCellValue();
										if(null!=dateCellValue){
											birthdayDate=dateCellValue;
										}
									}
								}
								if(Cell.CELL_TYPE_STRING==cellType){
									String stringCellValue = birthdayDateCell.getStringCellValue();
									if(null!=stringCellValue){
										Date string2Date = DateUtil.string2Date(stringCellValue);
										birthdayDate=string2Date;
									}
								}
							}
							String no=memberServer.getNo();
							member.setNo(no);
							member.setBirthday(birthdayDate);
							member.setCreateTime(new Date());
							member.setModifyTime(new Date());
							member.setRemainderPoint(0);
							member.setBalance(Double.valueOf(0));
							member.setStartBalance(0.0);
							member.setTotalCardMoney(0.0);
							member.setTotalStartMoney(0.0);
							member.setTotalBuy(0);
							member.setConsumeMoney(0.0);
							member.setOnlineBookingNum(0);
							member.setMemAuthStatus(Mem.MEMAUTHCOMM);
							member.setState(0);
							member.setEnterprise(enterprise);
							member.setTotalStromNum(0);
							member.setCreator(user.getUsername());
							
							member.setMemberCard(memberCard);
							memberServer.save(member);
							//保存会员操作日志
							//memLogServer.saveMemberOperatorLog(member.getDbid(), "管理员通过excel导入创建会员", "");
						}
					} else {
						log.info("sheet" + i + "为空！");
					}
				}
			} else {
				log.info("文档内容为空！");
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}// 创建输入
		return true;
	}
	/**
	 * 验证手机号码格式
	 * @param mobilePhone
	 * @return
	 */
	private static  boolean checkMobile(String mobilePhone){
		String regex="^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$";
		Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobilePhone);
        boolean isMatch = m.matches();
        return isMatch;
	}
}
