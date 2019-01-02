package cn.mauth.ccrm.weixin.core.common;

/**
 * 菜单
 */
public class MenuConditional {
	private Button[] button;
	private MatchRule matchrule;

	public MatchRule getMatchrule() {
		return matchrule;
	}

	public void setMatchrule(MatchRule matchrule) {
		this.matchrule = matchrule;
	}

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
}