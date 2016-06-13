package com.npc.lottery.user.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.npc.lottery.common.Constant;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IMemberStaffLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;

public class UserManagerAjax extends BaseUserAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1152979921471246144L;
    private Logger logger = Logger.getLogger(UserManagerAjax.class);
    private IChiefStaffExtLogic chiefStaffExtLogic = null;
    private Object object;
    private IBranchStaffExtLogic branchStaffExtLogic;
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null; // 股东
    private IGenAgentStaffExtLogic genAgentStaffExtLogic = null; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic = null; // 代理
    private IManagerStaffLogic managerStaffLogic;
    private IMemberStaffLogic memberStaffLogic;
    private IHKPeriodsInfoLogic skperiodsInfoLogic = null;
    private String account;
    private String userType;
    private String status;
    public String queryUserName() {
        object = managerStaffLogic.findManagerStaffByAccount(getRequest().getParameter("account"));
        Map<String, String> map = new HashMap<String, String>();
        String sCount;
        if (object == null) {
            sCount = "0";
        } else {
            sCount = "1";
        }
        map.put("count", sCount);
        return this.ajaxJson(map);
    }
    
    public String ajaxQueryMemberName() {
        String account = getRequest().getParameter("account");
        object = memberStaffLogic.findMemberStaffByAccount(account);
        Map<String, String> map = new HashMap<String, String>();
        String sCount;
        if (object == null) {
            sCount = "0";
        } else {
            sCount = "1";
        }
        map.put("count", sCount);
        return this.ajaxJson(map);
    }
    
    public String queryChsName() {
    	String chsName = getRequest().getParameter("account");
    	if (StringUtils.isNotBlank(chsName)) {
    		try {
				chsName = java.net.URLDecoder.decode(chsName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
        object = managerStaffLogic.findManagerStaffByChsName(chsName);
        Map<String, String> map = new HashMap<String, String>();
        String sCount;
        if (object == null) {
            sCount = "0";
        } else {
            sCount = "1";
        }
        map.put("count", sCount);
        return this.ajaxJson(map);
    }
    public String ajaxQueryStockholerName(){
       /* int moneyCount = 0;//可用信用额度
        int countMoney = 0;//总信用额度用了多少
        int percentCount = 0;
            branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("account", getRequest().getParameter("account"));
            moneyCount = branchStaffExt.getTotalCreditLine();//可用信用额度
            percentCount += branchStaffExt.getChiefRate()+branchStaffExt.getCompanyRate();// 占成还剩多少
            for (StockholderStaffExt staffExt : branchStaffExt.getStockholderStaffExtSet()) {
                countMoney += staffExt.getTotalCreditLine();
            }
        Map<String,String> map = new HashMap<String, String>();
        if(branchStaffExt == null){
            map.put("moneyCount", null);
            map.put("countMoney",null);
        }else{
            int moery = 0;
            moery = moneyCount - countMoney;
            map.put("moneyCount",String.valueOf(moneyCount));
            map.put("countMoney", String.valueOf(moery));
            map.put("percentCount", String.valueOf(100-percentCount));
        }*/
        return this.ajaxJson("");
    }
    //后台用户密码对比
    public String ajaxQueryPassword() {
        String pwd = getRequest().getParameter("pwd");
        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        //MD5 md5 = new MD5();
        String userPwdOrignMd5 = pwd;
        Map<String, String> map = new HashMap<String, String>();
        String sCount;
        if (userPwdOrignMd5.equals(userInfo.getUserPwd())) {
            sCount = "0";
        } else {
            sCount = "1";
        }
        map.put("count", sCount);
        return this.ajaxJson(map);
    }
    //盘期号码
    public String ajaxQueryPeriodsNum() {
        String num = getRequest().getParameter("num");
        object = skperiodsInfoLogic.queryByHKPeriods("periodsInfo", num);
        Map<String, String> map = new HashMap<String, String>();
        String sCount;
        if (object == null) {
            sCount = "0";
        } else {
            sCount = "1";
        }
        map.put("count", sCount);
        return this.ajaxJson(map);
    }

	// 查询当天有没有投注
	public String ajaxQueryUserTreeHasBet() {
		String id = getRequest().getParameter("id");
		String userType = getRequest().getParameter("userType");
		Map<String, String> map = new HashMap<String, String>();
		boolean hasBet = commonUserLogic.queryUserTreeHasBet(Long.valueOf(id), userType);
		map.put("hasBet", String.valueOf(hasBet));
		return this.ajaxJson(map);
	}
	
	// 查询最高可设占成
	public String ajaxQueryBelowMaxRate() {
		String id = getRequest().getParameter("id");
		String userType = getRequest().getParameter("userType");
		Map<String, String> map = new HashMap<String, String>();
		 int maxRate = commonUserLogic.queryBelowMaxRate(Long.valueOf(id), userType);
		map.put("maxRate", String.valueOf(100-maxRate));
		return this.ajaxJson(map);
	}
    
    
    public String ajaxUpdateUserStatus()
    {
    	 Map<String, String> map = new HashMap<String, String>();    	 
         // 如果上级用户禁止或者冻结，下级用户全部改成禁止或者冻结
         boolean isUserFlagForbid = ManagerStaff.FLAG_FORBID.equals(status);
         boolean isUserFlagFreeze = ManagerStaff.FLAG_FREEZE.equals(status);
         
            if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
        	 {
            	//if (isUserFlagForbid || isUserFlagFreeze) 
            	//{
            		updateTreeStatus(account,userType,status);
            	//}
            	//else
            	//{
            		BranchStaffExt dbBranch =  branchStaffExtLogic.queryBranchStaffExt("account",account);
            		dbBranch.setFlag(status);
            		branchStaffExtLogic.updateBranchStaffExt(dbBranch);
            	//}
        	 }
        	 else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
        	 {
        		// if (isUserFlagForbid || isUserFlagFreeze) 
             	//{
        			 updateTreeStatus(account,userType,status);
             	//}
        		//else
        		// {
        		 StockholderStaffExt stock=	stockholderStaffExtLogic.queryStockholderStaffExt("account",account);
        		 stock.setFlag(status);
        		 stockholderStaffExtLogic.updateStockholderStaffExt(stock);
        			 
        		// }
        		 
        	 }
        	 else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
        	 {
        		// if (isUserFlagForbid || isUserFlagFreeze) 
              	//{
        			 updateTreeStatus(account,userType,status);
              	//}
         		//else
         		// {
         		GenAgentStaffExt genAgent=	 genAgentStaffExtLogic.queryGenAgentStaffExt("account",account);
         		genAgent.setFlag(status);
         		genAgentStaffExtLogic.updateGenAgentStaffExt(genAgent);
         		// }
        	 }
        	 else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
        	 {
        		// if (isUserFlagForbid || isUserFlagFreeze) 
              //	{
        			 updateTreeStatus(account,userType,status);
              //	}
         		//else
         		// {
         			AgentStaffExt agent= agentStaffExtLogic.queryAgenStaffExt("account",account);
         			agent.setFlag(status);
         			agentStaffExtLogic.updateAgentStaffExt(agent);
         		// }
        	 }
        	 else if(MemberStaff.USER_TYPE_MEMBER.equals(userType))
        	 {
        		MemberStaff member=memberStaffLogic.findMemberStaffByAccount(account);
        		member.setFlag(status);
        		memberStaffLogic.updateMemberStaff(member);
        		updateTreeStatus(account,userType,status);
        	 }
        	 else if(ManagerStaff.USER_TYPE_SUB.equals(userType))
        	 {
        		 SubAccountInfo  entity=subAccountInfoLogic.querySubAccountInfo("account",account);
        		 entity.setFlag(status);
        		 subAccountInfoLogic.updateSubAccountInfo(entity);
        		 String managerType = String.valueOf(Integer.valueOf(entity.getParentUserType())+1);
        		 updateTreeStatus(account,managerType,status);
        	 }
        	 
        	 
  
    	
    	 return this.ajaxJson(map);
    }
    
    private void updateTreeStatus(String account,String userType,String status)
    {
    	 ManagerUser userInfo = this.getCurrentManagerUser();
    	//子帐号处理*********START
    	 ManagerUser userInfoNew = new ManagerUser();
    	 try {
    	 	BeanUtils.copyProperties(userInfoNew, userInfo);
    	 } catch (Exception e) {
    		 logger.info("userInfoSys里出错"+ e.getMessage());
    	 }
    	    if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    	 	userInfoNew = getSubAccountParent(userInfoNew);	
    	    }	
    	 //子帐号处理*********END
    	 
    	 boolean isUserFlagForbid = ManagerStaff.FLAG_FORBID.equals(status);
         boolean isUserFlagFreeze = ManagerStaff.FLAG_FREEZE.equals(status);
         boolean isUserFlagUsed = ManagerStaff.FLAG_USE.equals(status);
    	if(isUserFlagForbid)
     	  {
     		  commonUserLogic.updateBelowForbid(account, userType,userInfoNew.getUserType());
     		  
     	  }
     	  else if(isUserFlagFreeze)
     	  {
     		  commonUserLogic.updateBelowFreeze(account, userType,userInfoNew.getUserType());
     	  }
     	  else if(isUserFlagUsed)
     	  {
     		 commonUserLogic.updateBelowUsed(account, userType,userInfoNew.getUserType());
     	  }
    	
    }
    public IChiefStaffExtLogic getChiefStaffExtLogic() {
        return chiefStaffExtLogic;
    }


    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }


    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
    }

    public IManagerStaffLogic getManagerStaffLogic() {
        return managerStaffLogic;
    }

    public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
        this.managerStaffLogic = managerStaffLogic;
    }

    public IMemberStaffLogic getMemberStaffLogic() {
        return memberStaffLogic;
    }

    public void setMemberStaffLogic(IMemberStaffLogic memberStaffLogic) {
        this.memberStaffLogic = memberStaffLogic;
    }

    public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
        return skperiodsInfoLogic;
    }

    public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
        this.skperiodsInfoLogic = skperiodsInfoLogic;
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}

	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}

	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}

	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}

	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}

	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}
    
}
