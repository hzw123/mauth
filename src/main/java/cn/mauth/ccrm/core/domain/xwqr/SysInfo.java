package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class SysInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private String nameImage;
	private String loginLogo;
	private String infoLogo;
	//创建用户时是否同步用户信息至微信企业号1、默认不同步；2、同步
	private Integer wxUserStatus;
	//系统是否开启利润审批权限，1、默认（不开启）；2、开启
	private Integer grofitAprovalStatus;
	public SysInfo() {
	}

	public SysInfo(String name, String nameImage, String ss,
				   String infoLogo) {
		this.name = name;
		this.nameImage = nameImage;
		this.infoLogo = infoLogo;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameImage() {
		return this.nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}


	public String getLoginLogo() {
		return loginLogo;
	}

	public void setLoginLogo(String loginLogo) {
		this.loginLogo = loginLogo;
	}

	public String getInfoLogo() {
		return this.infoLogo;
	}

	public void setInfoLogo(String infoLogo) {
		this.infoLogo = infoLogo;
	}

	public Integer getWxUserStatus() {
		return wxUserStatus;
	}

	public void setWxUserStatus(Integer wxUserStatus) {
		this.wxUserStatus = wxUserStatus;
	}

	public Integer getGrofitAprovalStatus() {
		return grofitAprovalStatus;
	}

	public void setGrofitAprovalStatus(Integer grofitAprovalStatus) {
		this.grofitAprovalStatus = grofitAprovalStatus;
	}
	
}
