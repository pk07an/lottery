package com.npc.lottery.user.action;

import java.util.Date;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;


public class BaseUserAction extends BaseLotteryAction{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    protected ICommonUserLogic commonUserLogic;
    //protected ISubAccountInfoLogic subAccountInfoLogic;
    protected IMemberStaffExtLogic memberStaffExtLogic;
    protected IAgentStaffExtLogic agentStaffExtLogic = null; // 代理
    protected IGenAgentStaffExtLogic genAgentStaffExtLogic = null; // 总代理
    protected IStockholderStaffExtLogic stockholderStaffExtLogic = null; // 股东
    protected IBranchStaffExtLogic branchStaffExtLogic;// 分公司
    protected IChiefStaffExtLogic chiefStaffExtLogic = null;// 总监
    //add by peter
    protected IReplenishAutoSetLogLogic replenishAutoSetLogLogic;
    

	protected void getFailuere(String name) {
	        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
	        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
	                + Constant.COLOR_RED + "'>'"+name+"'</font>");
	    }

	    protected ManagerUser getInfo() {
	        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
	                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
	        return userInfo;
	    }

		public ICommonUserLogic getCommonUserLogic() {
			return commonUserLogic;
		}

		public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
			this.commonUserLogic = commonUserLogic;
		}
		 protected ManagerStaff getBackManagerUser(ManagerStaff loginUser) {
			 
			  boolean isSub = loginUser.getUserType().equals(ManagerStaff.USER_TYPE_SUB);

		      	if(isSub)
		    	{
		      		SubAccountInfo subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",loginUser.getAccount());
		      		if(subAccountInfo !=null){
		      			String parentType=subAccountInfo.getParentUserType();
		                if(ManagerStaff.USER_TYPE_CHIEF.equals(parentType))// 总监类型
		                {
		                	return chiefStaffExtLogic.queryChiefStaffExt("managerStaffID",subAccountInfo.getParentStaff());
		                }
		                else if(ManagerStaff.USER_TYPE_BRANCH.equals(parentType))
		                {
		                	return branchStaffExtLogic.queryBranchStaffExt("managerStaffID",subAccountInfo.getParentStaff());
		                }
		                else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(parentType))
		                {
		                	return stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",subAccountInfo.getParentStaff());
		                }
		                else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(parentType))
		                {
		                	return genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",subAccountInfo.getParentStaff());
		                }
		                else if(ManagerStaff.USER_TYPE_AGENT.equals(parentType))
		                {
		                	return agentStaffExtLogic.queryAgenStaffExt("managerStaffID",subAccountInfo.getParentStaff());
		                }
		               
		           }
		    		return null;
		    	}
		      	else 
		      		return loginUser;
			  
		  }
	
	/**
	 * add by peter for log the change log on user modify
	 * 
	 * @param currentUser
	 * @param changeSubType
	 * @param orginalValue
	 * @param newValue
	 * @return
	 */
	protected ReplenishAutoSetLog setChangeLog(ManagerUser currentUser, String changeSubType, String orginalValue, String newValue,
			ManagerStaff modifyUser) {
		ReplenishAutoSetLog log = new ReplenishAutoSetLog();
		log.setChangeType(Constant.CHANGE_LOG_CHANGE_TYPE_USERINFO_UPDATE);
		log.setChangeSubType(changeSubType);

		log.setCreateUserID(modifyUser.getID());
		log.setShopID(currentUser.getShopsInfo().getID());
		log.setCreateTime(new Date());
		log.setNewValue(newValue);
		log.setOrginalValue(orginalValue);
		log.setCreateUserType(Integer.valueOf(modifyUser.getUserType()));
		log.setIp(currentUser.getLoginIp());

		log.setType(modifyUser.getUserTypeName());
		log.setTypeCode(modifyUser.getAccount());
		log.setMoneyOrgin(0);
		log.setMoneyNew(0);

		//更新的用户信息
		log.setUpdateUserID(currentUser.getID());
		log.setUpdateUserType(Integer.valueOf(currentUser.getUserType()));
		return log;
	}

		public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
			return replenishAutoSetLogLogic;
		}

		public void setReplenishAutoSetLogLogic(IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
			this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
		}
		
}
