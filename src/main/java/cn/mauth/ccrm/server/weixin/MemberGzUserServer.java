package cn.mauth.ccrm.server.weixin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberGzUserServer{

	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private EnterpriseServer enterpriseServer;

	public void saveMember(WeixinGzuserInfo weixinGzuserinfo){
		try {
			if(weixinGzuserinfo==null){
				return ;
			}
			List<Mem> members = memberServer.getRepository().findByMicroId(weixinGzuserinfo.getOpenid());
			//新增会员信息
			if(null==members||members.size()<=0){
				Integer memberShipLevelId = 1;
				Mem member=new Mem();
				String no = member.getNo();
				if(null==no||no.trim().length()<=0){
					no=DateUtil.generatedName(new Date());
					member.setNo(no);
				}
				if(null!=weixinGzuserinfo.getNickname()){
					member.setName(weixinGzuserinfo.getNickname());
				}
				member.setMobilePhone(null);
				member.setBirthday(null);
				if(null!=weixinGzuserinfo.getSex()){
					String sex = weixinGzuserinfo.getSex();
					if(sex.equals("1")){
						member.setSex("男");
					}
					if(sex.equals("2")){
						member.setSex("女");
					}
				}else{
					member.setSex("未知");
				}
				
				member.setNote("关注时添加会员");
				
				if(null==member.getRemainderPoint()||member.getRemainderPoint()<=0){
					member.setRemainderPoint(0);
				}
				member.setRemainderPoint(0);			
				member.setBalance(Double.valueOf(0));
				member.setTotalBuy(0);
				member.setConsumeMoney(Double.valueOf(0));
				member.setLastBuyDate(new Date());
				
				
				member.setCreateTime(new Date());
				member.setModifyTime(new Date());
				//设置企业号ID
				Integer enterpriseId = weixinGzuserinfo.getEnterpriseId();
				SysEnterprise enterprise=new SysEnterprise();
				if(null!=enterpriseId){
					enterprise = enterpriseServer.findById(enterpriseId).get();
					member.setEnterprise(enterprise);
				}
				member.setMicroId(weixinGzuserinfo.getOpenid());
				//是否同步到微信平台
				member.setWeixinGzuserinfo(weixinGzuserinfo);
				
				member.setLastContactDate(new Date());
				
				member.setMemAuthStatus(Mem.MEMAUTHCOMM);
				member.setMemAuthDate(null);
				member.setMemAuthDate(null);
				
				//会员状态
				member.setNickName(weixinGzuserinfo.getNickname());

				memberServer.save(member);
				SysUser user=new SysUser();
				user.setRealName("管理员");
				memLogServer.saveMemberOperatorLog(member.getDbid(), OperateType.CreateMember, "微信自助注册", "微信自助", 0, enterprise.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
