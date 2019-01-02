package cn.mauth.ccrm.core.bean;

import cn.mauth.ccrm.core.domain.set.SetItemType;

import java.util.Comparator;

public class MapItemComparator implements Comparator<SetItemType>  {

	@Override
	public int compare(SetItemType o1, SetItemType o2) {
			return o1.getDbid().compareTo(o2.getDbid());
	}


}
