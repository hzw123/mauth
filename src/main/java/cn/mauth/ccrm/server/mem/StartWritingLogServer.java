package cn.mauth.ccrm.server.mem;

import java.util.Date;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingLog;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.rep.mem.MemStartWritingLogRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class StartWritingLogServer extends BaseServer<MemStartWritingLog,MemStartWritingLogRepository> {
	public StartWritingLogServer(MemStartWritingLogRepository repository) {
		super(repository);
	}

	public void saveMemberOperatorLog(Integer startWritingId, String type, String note){

		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		MemStartWritingLog memLog=new MemStartWritingLog();
		memLog.setStartWritingId(startWritingId);
		memLog.setOperateDate(new Date());
		memLog.setOperator(currentUser.getRealName());
		memLog.setType(type);
		memLog.setNote(note);
		save(memLog);
	}
	public void saveMemberOperatorLog(Integer startWritingId,String type,String note,SysUser user){
		MemStartWritingLog memLog=new MemStartWritingLog();
		memLog.setStartWritingId(startWritingId);
		memLog.setOperateDate(new Date());
		memLog.setOperator(user.getRealName());
		memLog.setType(type);
		memLog.setNote(note);
		save(memLog);
	}
	public void saveMemberOperatorLog(Integer startWritingId,String type,String note,Mem user){
		MemStartWritingLog memLog=new MemStartWritingLog();
		memLog.setStartWritingId(startWritingId);
		memLog.setOperateDate(new Date());
		memLog.setOperator(user.getName());
		memLog.setType(type);
		memLog.setNote(note);
		save(memLog);
	}

	/**
	 * 功能描述：通过开店ID删除开单操作日志
	 * @param startWritingId
	 */
	public void deleteByStartWritingId(Integer startWritingId) {
		getRepository().deleteByStartWritingId(startWritingId);
	}
}
