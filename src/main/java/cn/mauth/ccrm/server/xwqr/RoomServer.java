package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.rep.xwqr.SysRoomRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServer extends BaseServer<SysRoom,SysRoomRepository> {
	public RoomServer(SysRoomRepository repository) {
		super(repository);
	}

	public int SetRoomStatus(int roomId, int status, int startWritingId) {
		return getRepository().updateStatus(roomId, status, startWritingId);
	}
}
