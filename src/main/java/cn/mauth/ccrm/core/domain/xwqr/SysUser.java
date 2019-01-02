package cn.mauth.ccrm.core.domain.xwqr;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;

import javax.persistence.*;

@Entity
public class SysUser implements UserDetails, Serializable {
	//用户微信关注状态1 默认 未关注
	public static final Integer ATTATIONSTATUSDEFUAL=1;
	//用户微信关注状态2 已经关注
	public static final Integer ATTATIONED=2;
	//用户微信关注状态2 停用
	public static final Integer ATTATONSTOP=3;

	//区分管理员
	public static Integer ADMINTYPEADMIN=1;
	//普通用户
	public static Integer ADMINTYPEADCOMM=2;
	//合同流失审批权限 为默认
	public static Integer APPROVALCOMM=1;
	//合同流失审批权限 拥有审批权限
	public static Integer APPROVALYES=2;

	//查询权限 为默认
	public static Integer QUERYDEP=1;
	//查询权限 拥有查询权限
	public static Integer QUERYMORE=3;
	//查询权限 拥有查询权限
	public static Integer QUERYENT=2;


	//微信账号是否同步 为默认
	public static Integer SYNCOMM=1;
	//微信账号是否同步 拥有查询权限
	public static Integer SYNYYES=2;

	//领导自我审批合同 1、为默认
	public static Integer SELFAPPROVALCOMM=1;
	//领导自我审批合同 2、拥有查询权限
	public static Integer SELFAPPROVALYEAS=2;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 50)
	private String userId;
	private String userType;
	private Integer distributorTypeId;
	@Column(length = 20)
	private String realName;
	@Column(length = 50)
	private String password;
	@Column(length = 200)
	private String email;
	@Column(length = 20)
	private String mobilePhone;
	//微信号（扩展企业微信）
	@Column(length = 200)
	private String wechatId;
	@Column(length = 20)
	private String phone;
	@Column(length = 20)
	private String qq;
	@ManyToOne
	@JoinColumn(name = "staff_id")
	private SysStaff staff;
	@Column(length = 2000,columnDefinition = "text")
	private String positionIds;
	@Column(length = 2000,columnDefinition = "text")
	private String positionNames;
	//单位名称
	@Column(length = 2000,columnDefinition = "text")
	private String companyName;
	//微信是否关注微信，1、未关注，2、关注(: 1=已关注，2=已禁用，4=未关注)
	@Column(length = 20)
	private Integer attentionStatus;
	//微信用户是否同步 1、未同步；2、已结同步
	@Column(length = 20)
	private Integer sysWeixinStatus;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private SysDepartment department;
	//1、为启用；2、为停用
	@Column(length = 20)
	private Integer userState;
	@Column(length = 20)
	private boolean state;
	//管理员类型：1、为管理员，2、位普通用户
	@Column(length = 20)
	private Integer adminType;
	//分店
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private SysEnterprise enterprise;
	//添加一个父节点
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SysUser parent;
	@ManyToMany
	@JoinTable(name = "sys_user_role",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<SysRole> roles;
	@Transient
	private  Set<GrantedAuthority> authorities;
	@Transient
    private  boolean accountNonExpired;
	@Transient
    private  boolean accountNonLocked;
	@Transient
    private  boolean credentialsNonExpired;
    //查看公司数据权限
	@Column(length = 2000,columnDefinition = "text")
    private String compnayIds;
    //合同流失审批权限;1、为默认；2、审批
	@Column(length = 20)
    private Integer approvalCpidStatus;
    //是否用户查询其他公司数据权限：1、默认；2、拥有权限
	@Column(length = 20)
    private Integer queryOtherDataStatus;
    //免单权限，1、无；2、有权限
	@Column(length = 20)
    private Integer selfApproval;
    //利润审批权限 金额
	@Column(length = 20)
    private Integer approvalMoney;
    //客户业务类型:1、业务员；2、信审；3、后勤；4、其他
	@Column(length = 20)
    private Integer bussiType;
	@Column(length = 20)
    private Integer resourceIndexId;
    //对应1:1商家Id
	@ManyToOne
	@JoinColumn(name = "weixin_account_id")
    private WeixinAccount weixinAccount;
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SysUser() {
	}

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("userId: ").append(this.userId).append("; ");
        sb.append("Password: [PROTECTED]; ").append(this.password).append(": ");
        sb.append("realName: ").append(this.realName).append("; ");
        sb.append("email: ").append(this.email).append("; ");
        sb.append("state: ").append(this.state).append("; ");
        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

        if ( null !=authorities  && !authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }
		return super.toString();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public SysStaff getStaff() {
		return staff;
	}

	public void setStaff(SysStaff staff) {
		this.staff = staff;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {

		this.authorities = sortAuthorities(authorities);
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}



	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<GrantedAuthority> authorities) {
	        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
	        SortedSet<GrantedAuthority> sortedAuthorities =
	            new TreeSet<GrantedAuthority>(new AuthorityComparator());

	        for (GrantedAuthority grantedAuthority : authorities) {
	            sortedAuthorities.add(grantedAuthority);
	        }

	        return sortedAuthorities;
	    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

	public SysDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getAttentionStatus() {
		return attentionStatus;
	}

	public void setAttentionStatus(Integer attentionStatus) {
		this.attentionStatus = attentionStatus;
	}

	public String getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}

	public String getPositionNames() {
		return positionNames;
	}

	public void setPositionNames(String positionNames) {
		this.positionNames = positionNames;
	}


	public SysEnterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(SysEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getAdminType() {
		return adminType;
	}

	public void setAdminType(Integer adminType) {
		this.adminType = adminType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompnayIds() {
		return compnayIds;
	}

	public void setCompnayIds(String compnayIds) {
		this.compnayIds = compnayIds;
	}

	public SysUser getParent() {
		return parent;
	}

	public void setParent(SysUser parent) {
		this.parent = parent;
	}

	public Integer getApprovalCpidStatus() {
		return approvalCpidStatus;
	}

	public void setApprovalCpidStatus(Integer approvalCpidStatus) {
		this.approvalCpidStatus = approvalCpidStatus;
	}

	public Integer getQueryOtherDataStatus() {
		return queryOtherDataStatus;
	}

	public void setQueryOtherDataStatus(Integer queryOtherDataStatus) {
		this.queryOtherDataStatus = queryOtherDataStatus;
	}

	public Integer getSysWeixinStatus() {
		return sysWeixinStatus;
	}

	public void setSysWeixinStatus(Integer sysWeixinStatus) {
		this.sysWeixinStatus = sysWeixinStatus;
	}

	public Integer getSelfApproval() {
		return selfApproval;
	}

	public void setSelfApproval(Integer selfApproval) {
		this.selfApproval = selfApproval;
	}

	public Integer getApprovalMoney() {
		return approvalMoney;
	}

	public void setApprovalMoney(Integer approvalMoney) {
		this.approvalMoney = approvalMoney;
	}

	public Integer getBussiType() {
		return bussiType;
	}

	public void setBussiType(Integer bussiType) {
		this.bussiType = bussiType;
	}

	public Integer getResourceIndexId() {
		return resourceIndexId;
	}

	public void setResourceIndexId(Integer resourceIndexId) {
		this.resourceIndexId = resourceIndexId;
	}

	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	public void setWeixinAccount(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}


	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getDistributorTypeId() {
		return distributorTypeId;
	}

	public void setDistributorTypeId(Integer distributorTypeId) {
		this.distributorTypeId = distributorTypeId;
	}
}
