package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemTradingSnapshot implements Serializable {
	private static final long serialVersionUID = 1L;
	//储值成功
	public static Integer STORESUCCESS=1;
	//消费成功
	public static Integer XIAOFEISUCCESS=1;
	//储值失败
	public static Integer STORESFAILURE=2;
	//消费失败
	public static Integer XIAOFEIFAILURE=2;
	
	
	//交易类型 储值
	public static Integer TRADINGTYPESTORE=1;
	//交易类型 储值
	public static Integer TRADINGTYPEXIAOFEI=2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(columnDefinition = "datetime")
	private Date tradingTime;
	private String money;
	private Integer memberId;
	private Integer status;
	private Integer tradingType;
	private Integer tradingId;

	public MemTradingSnapshot() {
	}

	public MemTradingSnapshot(Date tradingTime, String money, Integer memberId,
							  Integer status, Integer tradingType, Integer tradingId) {
		this.tradingTime = tradingTime;
		this.money = money;
		this.memberId = memberId;
		this.status = status;
		this.tradingType = tradingType;
		this.tradingId = tradingId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Date getTradingTime() {
		return this.tradingTime;
	}

	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTradingType() {
		return this.tradingType;
	}

	public void setTradingType(Integer tradingType) {
		this.tradingType = tradingType;
	}

	public Integer getTradingId() {
		return this.tradingId;
	}

	public void setTradingId(Integer tradingId) {
		this.tradingId = tradingId;
	}

}
