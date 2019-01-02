package cn.mauth.ccrm.core.domain.set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class SetCountCardItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private int countCardId;
	private int itemId;
	
	public int getCountCardId() {
		return countCardId;
	}
	public void setCountCardId(int countCardId) {
		this.countCardId = countCardId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
}
