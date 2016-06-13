package com.npc.lottery.manage.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.sysmge.entity.ManagerUser;

public class MarqueeAction extends BaseLotteryAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarqueeAction.class);
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	/*
	 * 	快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球
	 *  时时彩  1-5球
	 */
	
	
	
	
	
	public String enter()
	{	
	    ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		ShopsDeclaratton shopsDeclaratton = shopsDeclarattonLogic.queryByShopsDeclaratton(new Date(),userInfo);
		this.getRequest().setAttribute("shopsDeclaratton", shopsDeclaratton);
		return "marquee";
		
	}





	public IShopsDeclarattonLogic getShopsDeclarattonLogic() {
		return shopsDeclarattonLogic;
	}





	public void setShopsDeclarattonLogic(
			IShopsDeclarattonLogic shopsDeclarattonLogic) {
		this.shopsDeclarattonLogic = shopsDeclarattonLogic;
	}
	
	
	
}

