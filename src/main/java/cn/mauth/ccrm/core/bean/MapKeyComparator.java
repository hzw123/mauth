package cn.mauth.ccrm.core.bean;

import cn.mauth.ccrm.core.domain.set.SetProductCategory;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<SetProductCategory>  {

	@Override
	public int compare(SetProductCategory o1, SetProductCategory o2) {
			return o1.getDbid().compareTo(o2.getDbid());
	}


}
