package com.npc.lottery.user.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;

public class BackstageCreditInformation extends BaseLotteryAction{

    /**
     * 
     */
    private static final long serialVersionUID = 1919419787984837617L;
    private Logger logger = Logger.getLogger(BackstageCreditInformation.class);
    private String type="userSet";
    private IUserCommissionLogic userCommissionLogic;
    private IChiefStaffExtLogic chiefStaffExtLogic = null;// 总监
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IUserCommissionDefault userCommissionDefaultLogic;
    private ManagerUser userInfo;
    
    public String queryCreditInformation(){
        userInfo = getInfo();
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
        boolean isSys = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般不创建用户
        {
            boolean isBranch = userInfoNew.getUserType().equals(
                    ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            boolean isStockholder = userInfoNew.getUserType().equals(
                    ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
            boolean isGenAgent = userInfoNew.getUserType().equals(
                    ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
            boolean isAgent = userInfoNew.getUserType().equals(
                    ManagerStaff.USER_TYPE_AGENT);// 代理
            if(isBranch){
                BranchStaffExt branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("managerStaffID",userInfoNew.getID());
                Integer totalCredit=branchStaffExt.getTotalCreditLine();
                this.getRequest().setAttribute("totalCredit", totalCredit);
                this.getRequest().setAttribute("avalilableCredit", branchStaffExt.getAvailableCreditLine());
            }else if(isStockholder){
                StockholderStaffExt  stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",userInfoNew.getID());
                Integer totalCredit=stockholderStaffExt.getTotalCreditLine();
                this.getRequest().setAttribute("totalCredit", totalCredit);
                this.getRequest().setAttribute("avalilableCredit", stockholderStaffExt.getAvailableCreditLine());
            }else if(isGenAgent){
                GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",userInfoNew.getID());
                Integer totalCredit=genAgentStaffExt.getTotalCreditLine();
                this.getRequest().setAttribute("totalCredit", totalCredit);
                this.getRequest().setAttribute("avalilableCredit", genAgentStaffExt.getAvailableCreditLine());
            }else if(isAgent){
                AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("managerStaffID",userInfoNew.getID());
                Integer totalCredit=agentStaffExt.getTotalCreditLine();
                this.getRequest().setAttribute("totalCredit", totalCredit);
                this.getRequest().setAttribute("avalilableCredit", agentStaffExt.getAvailableCreditLine());
            }
            Map<String,UserCommission> map=new HashMap<String,UserCommission>();
            List<UserCommission> userCommission = userCommissionLogic
                    .queryCommission(userInfoNew.getID(),userInfoNew.getUserType());
            map=convertCommissionListToMap(userCommission);
            
            
            String jsonCommission=JSONObject.toJSONString(map);
            try {
                jsonCommission=URLEncoder.encode(jsonCommission,"utf-8");
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
            }
           
            this.getRequest().setAttribute("commissions", map);
            /*List<UserCommission> gdCommission=new ArrayList<UserCommission>();
            List<UserCommission> cqCommission=new ArrayList<UserCommission>();
            List<UserCommission> bjCommission=new ArrayList<UserCommission>();
            List<UserCommission> hkCommission=new ArrayList<UserCommission>();
            for (int i = 0; i < userCommission.size(); i++) {
                UserCommission  uc=userCommission.get(i);
                if(Constant.COMMISSION_GD.equals(uc.getPlayType()))
                {
                    gdCommission.add(uc);
                    
                }
                else if(Constant.COMMISSION_CQ.equals(uc.getPlayType()))
                {
                    cqCommission.add(uc);
                }
                else if(Constant.COMMISSION_BJ.equals(uc.getPlayType()))
                {
                	bjCommission.add(uc);
                }
                else if(Constant.COMMISSION_HK.equals(uc.getPlayType()))
                {
                    hkCommission.add(uc);
                }
            }
            this.getRequest().setAttribute("gdCommission", gdCommission);
            this.getRequest().setAttribute("cqCommission", cqCommission);
            this.getRequest().setAttribute("bjCommission", bjCommission);
            this.getRequest().setAttribute("hkCommission", hkCommission);*/
            return SUCCESS;
        }   
        return SUCCESS;
    }
    
    private Map<String,UserCommission> convertCommissionListToMap(List<UserCommission> commissions)
    {
    	 Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    	for (int i = 0; i < commissions.size(); i++) {
    		UserCommission	userCommsion=commissions.get(i);
    		
    		String key=userCommsion.getPlayFinalType();
    		key=key.replace("-", "_");
    		
    		map.put(key, userCommsion);
		}
    	return map;
    	
    }

    private ManagerUser getInfo() {
        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        return userInfo;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IUserCommissionLogic getUserCommissionLogic() {
        return userCommissionLogic;
    }

    public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
        this.userCommissionLogic = userCommissionLogic;
    }

    public IChiefStaffExtLogic getChiefStaffExtLogic() {
        return chiefStaffExtLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
        return stockholderStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
    }

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
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

    public IUserCommissionDefault getUserCommissionDefaultLogic() {
        return userCommissionDefaultLogic;
    }

    public void setUserCommissionDefaultLogic(
            IUserCommissionDefault userCommissionDefaultLogic) {
        this.userCommissionDefaultLogic = userCommissionDefaultLogic;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }
    
    
}
