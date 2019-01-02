package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetPayWay implements Serializable {
	private static final long serialVersionUID = 1L;

	//现金
	public static int CASH=1;
	public static int BACKCARD=2;
	public static int ALPAY=3;
	public static int WECHATPAY=4;
	public static int MEMBERCARD=5;
	public static int STARTCARD=6;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private Integer orderNum;
	//类型：1、为系统内置；2、系统添加
	private Integer type;
	//支付场景：1、通用；2、消费使用
	private Integer useType;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//分店

	public SetPayWay() {
	}

	public SetPayWay(String name, Integer orderNum, Date createTime,
					 Date modifyTime) {
		this.name = name;
		this.orderNum = orderNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
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

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}


}
