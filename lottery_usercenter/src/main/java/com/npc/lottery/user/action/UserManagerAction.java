package com.npc.lottery.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.util.MD5;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.opensymphony.oscache.util.StringUtil;

public class UserManagerAction extends BaseUserAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5094085239655210014L;
    Logger logger = Logger.getLogger(UserManagerAction.class);
    private IChiefStaffExtLogic chiefStaffExtLogic = null;
    private ChiefStaffExt chiefStaffExt = null;
    private List<ChiefStaffExt> listChiefSE = null;
    private String type="userSet";
    private String userOldPassword;
    private String newPassword;
    private String newPasswordOne;
    private ManagerUser userInfo;
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IManagerStaffLogic managerStaffLogic;
    private IMemberStaffExtLogic memberStaffExtLogic;
    private ISubAccountInfoLogic subAccountInfoLogic;
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private ICommonUserLogic commonUserLogic;
    
    private String account;
    private String userType;
    private String searchUserStatus="0";
    
    
    public String queryChiefStaff() {
        listChiefSE = chiefStaffExtLogic.queryAllChiefStaffExt("userType", "2");
        return SUCCESS;
    }

    public String userChiefStaffRegister() {
        return SUCCESS;
    }

    public String chiefStaffRegister() throws InterruptedException {
        ChiefStaffExt staffExt = new ChiefStaffExt();
        String account = chiefStaffExt.getAccount();
        staffExt = chiefStaffExtLogic.queryChiefStaffExt("account", account);
        if (staffExt == null) {
            chiefStaffExt.setCreateDate(new Date());
            MD5 md5 = new MD5();
            String userPwdOrignMd5 = md5
                    .getMD5ofStr(chiefStaffExt.getUserPwd()).trim();
            chiefStaffExt.setUserPwd(userPwdOrignMd5);
            chiefStaffExt.setShopsCode("1110");
            ChiefStaffExt chiefStaff = new ChiefStaffExt();
            BeanUtils.copyProperties(chiefStaffExt, chiefStaff);
            chiefStaffExtLogic.saveChiefStaffExt(chiefStaff);
        } else {
            return "failure";
        }
        return SUCCESS;
    }
    
    public String updateFindPassword(){
        userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));
       
		if (!StringUtil.isEmpty(getRequest().getParameter("isPasswordExpire"))) {
			getRequest().setAttribute("isPasswordExpire", getRequest().getParameter("isPasswordExpire"));
		}
		if (!StringUtil.isEmpty(getRequest().getParameter("isPasswordReset"))) {
			getRequest().setAttribute("isPasswordReset", getRequest().getParameter("isPasswordReset"));
		}
        
        return SUCCESS;
    }
    /**
     * 修改密碼
     * @return
     */
    public String updatePassword(){
         userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
            boolean isSys = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SYS);// 系统类型
            boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
            boolean isChief = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_CHIEF);// 总监类型
            boolean isBranch = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            boolean isStockholder = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
            boolean isGenAgent = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
            boolean isAgent = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_AGENT);// 代理
            boolean isSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 代理
            String passwordInfo = null;
            if(isSys || isManager){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                    return "failure";
                }
                ManagerStaff managerStaff = managerStaffLogic.findManagerStaffByAccount(userInfo.getAccount());
                if(managerStaff != null){
                    //MD5 md5 = new MD5();
                    String userOldPwdOrignMd5 = userOldPassword;
                    String userNewPwdOrignMd5 = newPassword;
                    //與數據庫對比舊
                    if(!managerStaff.getUserPwd().equals(userOldPwdOrignMd5)){
                        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                                + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                        return "failure";
                    }
                    managerStaff.setUserPwd(userNewPwdOrignMd5);
                    //add by peter set the value of new field passwordUpdateDate
                    managerStaff.setPasswordUpdateDate(new Date());
                    managerStaff.setPasswordResetFlag("N");
                    try {
                        managerStaffLogic.updateManagerStaff(managerStaff);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("执行" + this.getClass().getSimpleName()
                                + "中的方法updatePassword时出现错误 "
                                + e.getMessage());
                    }
                    passwordInfo = "密码修改成功,请重新登录";
                    getRequest().setAttribute("passwordInfo",passwordInfo);
                    return SUCCESS;
                }
                
            }else if(isChief){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
               ChiefStaffExt chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("account", userInfo.getAccount());
               if(chiefStaffExt != null){
                   //MD5 md5 = new MD5();
                   String userOldPwdOrignMd5 = userOldPassword;
                   String userNewPwdOrignMd5 = newPassword;
                   //與數據庫對比舊
                   if(!chiefStaffExt.getUserPwd().equals(userOldPwdOrignMd5)){
                       getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                       getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                               + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                       return "failure";
                   }
                   chiefStaffExt.setUserPwd(userNewPwdOrignMd5);
                   //add by peter set the value of new field passwordUpdateDate
                   chiefStaffExt.setPasswordUpdateDate(new Date());
                   chiefStaffExt.setPasswordResetFlag("N");
                   try {
                       chiefStaffExtLogic.updateChiefStaffExt(chiefStaffExt);
                   } catch (Exception e) {
                       e.printStackTrace();
                       logger.error("执行" + this.getClass().getSimpleName()
                               + "中的方法updatePassword时出现错误 "
                               + e.getMessage());
                   }
                   passwordInfo = "密码修改成功,请重新登录";
                   getRequest().setAttribute("passwordInfo",passwordInfo);
                   return SUCCESS;
               }
                
            }else if(isBranch){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
               BranchStaffExt branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("account", userInfo.getAccount());
               if(branchStaffExt != null){
                   //MD5 md5 = new MD5();
                   String userOldPwdOrignMd5 = userOldPassword;
                   String userNewPwdOrignMd5 = newPassword;
                   //與數據庫對比舊
                   if(!branchStaffExt.getUserPwd().equals(userOldPwdOrignMd5)){
                       getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                       getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                               + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                       return "failure";
                   }
                   branchStaffExt.setUserPwd(userNewPwdOrignMd5);
                   //add by peter set the value of new field passwordUpdateDate
                   branchStaffExt.setPasswordUpdateDate(new Date());
                   branchStaffExt.setPasswordResetFlag("N");
                   try {
                       branchStaffExtLogic.updateBranchStaffExt(branchStaffExt);
                   } catch (Exception e) {
                       e.printStackTrace();
                       logger.error("执行" + this.getClass().getSimpleName()
                               + "中的方法updatePassword时出现错误 "
                               + e.getMessage());
                   }
                   passwordInfo = "密码修改成功,请重新登录";
                   getRequest().setAttribute("passwordInfo",passwordInfo);
                   return SUCCESS;
                  
               }
            }else if(isStockholder){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
               StockholderStaffExt stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("account", userInfo.getAccount());
               if(stockholderStaffExt != null){
                   //MD5 md5 = new MD5();
                   String userOldPwdOrignMd5 = userOldPassword;
                   String userNewPwdOrignMd5 = newPassword;
                   //與數據庫對比舊
                   if(!stockholderStaffExt.getUserPwd().equals(userOldPwdOrignMd5)){
                       getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                       getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                               + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                       return "failure";
                   }
                   stockholderStaffExt.setUserPwd(userNewPwdOrignMd5);
                   //add by peter set the value of new field passwordUpdateDate
                   stockholderStaffExt.setPasswordUpdateDate(new Date());
                   stockholderStaffExt.setPasswordResetFlag("N");
                   try {
                       stockholderStaffExtLogic.updateStockholderStaffExt(stockholderStaffExt);
                   } catch (Exception e) {
                       e.printStackTrace();
                       logger.error("执行" + this.getClass().getSimpleName()
                               + "中的方法updatePassword时出现错误 "
                               + e.getMessage());
                   }
                   passwordInfo = "密码修改成功,请重新登录";
                   getRequest().setAttribute("passwordInfo",passwordInfo);
                   return SUCCESS;
               }
            }else if(isGenAgent){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
               GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("account", userInfo.getAccount());
               if(genAgentStaffExt != null){
                   //MD5 md5 = new MD5();
                   String userOldPwdOrignMd5 = userOldPassword;
                   String userNewPwdOrignMd5 = newPassword;
                   //與數據庫對比舊
                   if(!genAgentStaffExt.getUserPwd().equals(userOldPwdOrignMd5)){
                       getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                       getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                               + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                       return "failure";
                   }
                   genAgentStaffExt.setUserPwd(userNewPwdOrignMd5);
                   //add by peter set the value of new field passwordUpdateDate
                   genAgentStaffExt.setPasswordUpdateDate(new Date());
                   genAgentStaffExt.setPasswordResetFlag("N");
                   try {
                       genAgentStaffExtLogic.updateGenAgentStaffExt(genAgentStaffExt);
                   } catch (Exception e) {
                       e.printStackTrace();
                       logger.error("执行" + this.getClass().getSimpleName()
                               + "中的方法updatePassword时出现错误 "
                               + e.getMessage());
                   }
                   passwordInfo = "密码修改成功,请重新登录";
                   getRequest().setAttribute("passwordInfo",passwordInfo);
                   return SUCCESS;
               }
                
            }else if(isAgent){
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
                AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("account", userInfo.getAccount());
               if(agentStaffExt != null){
                   //MD5 md5 = new MD5();
                   String userOldPwdOrignMd5 = userOldPassword;
                   String userNewPwdOrignMd5 = newPassword;
                   //與數據庫對比舊
                   if(!agentStaffExt.getUserPwd().equals(userOldPwdOrignMd5)){
                       getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                       getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                               + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                       return "failure";
                   }
                   agentStaffExt.setUserPwd(userNewPwdOrignMd5);
                   //add by peter set the value of new field passwordUpdateDate
                   agentStaffExt.setPasswordUpdateDate(new Date());
                   agentStaffExt.setPasswordResetFlag("N");
                   try {
                       agentStaffExtLogic.updateAgentStaffExt(agentStaffExt);
                   } catch (Exception e) {
                       e.printStackTrace();
                       logger.error("执行" + this.getClass().getSimpleName()
                               + "中的方法updatePassword时出现错误 "
                               + e.getMessage());
                   }
                   passwordInfo = "密码修改成功,请重新登录";
                   getRequest().setAttribute("passwordInfo",passwordInfo);
                   return SUCCESS;
               }
            }else if (isSub) {
                if(!newPassword.equals(newPasswordOne)){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>兩次密碼不一樣！</font>");
                    return "failure";
                }
                SubAccountInfo accountInfo = subAccountInfoLogic.querySubAccountInfo("account", userInfo.getAccount());
                if(accountInfo != null){
                    //MD5 md5 = new MD5();
                    String userOldPwdOrignMd5 = userOldPassword;
                    String userNewPwdOrignMd5 = newPassword;
                    //與數據庫對比舊
                    if(!accountInfo.getUserPwd().equals(userOldPwdOrignMd5)){
                        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                                + Constant.COLOR_RED + "'>舊密碼不一樣！</font>");
                        return "failure";
                    }
                    accountInfo.setUserPwd(userNewPwdOrignMd5);
                    //add by peter set the value of new field passwordUpdateDate
                    accountInfo.setPasswordUpdateDate(new Date());
                    accountInfo.setPasswordResetFlag("N");
                    try {
                        subAccountInfoLogic.updateSubAccountInfo(accountInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("执行" + this.getClass().getSimpleName()
                                + "中的方法updatePassword时出现错误 "
                                + e.getMessage());
                    }
                    passwordInfo = "密码修改成功,请重新登录";
                    getRequest().setAttribute("passwordInfo",passwordInfo);
                    return SUCCESS;
                }
            }
        return "failure";
    }
    
    public IChiefStaffExtLogic getChiefStaffExtLogic() {
        return chiefStaffExtLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    public ChiefStaffExt getChiefStaffExt() {
        return chiefStaffExt;
    }

    public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
        this.chiefStaffExt = chiefStaffExt;
    }

    public List<ChiefStaffExt> getListChiefSE() {
        return listChiefSE;
    }

    public void setListChiefSE(List<ChiefStaffExt> listChiefSE) {
        this.listChiefSE = listChiefSE;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getUserOldPassword() {
        return userOldPassword;
    }

    public void setUserOldPassword(String userOldPassword) {
        this.userOldPassword = userOldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordOne() {
        return newPasswordOne;
    }

    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }

    public IManagerStaffLogic getManagerStaffLogic() {
        return managerStaffLogic;
    }

    public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
        this.managerStaffLogic = managerStaffLogic;
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

    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
        return stockholderStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
    }

    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
    }

    public ISubAccountInfoLogic getSubAccountInfoLogic() {
        return subAccountInfoLogic;
    }

    public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
        this.subAccountInfoLogic = subAccountInfoLogic;
    }

    public SubAccountInfo getSubAccountInfo() {
        return subAccountInfo;
    }

    public void setSubAccountInfo(SubAccountInfo subAccountInfo) {
        this.subAccountInfo = subAccountInfo;
    }

    public IAuthorizLogic getAuthorizLogic() {
        return authorizLogic;
    }

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public String getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(String replenishment) {
        this.replenishment = replenishment;
    }

    public String getOffLineAccount() {
        return offLineAccount;
    }

    public void setOffLineAccount(String offLineAccount) {
        this.offLineAccount = offLineAccount;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getCrossReport() {
        return crossReport;
    }

    public void setCrossReport(String crossReport) {
        this.crossReport = crossReport;
    }

    public String getClassifyReport() {
        return classifyReport;
    }

    public void setClassifyReport(String classifyReport) {
        this.classifyReport = classifyReport;
    }
    
    public Map<String,String> getAutoSub(SubAccountInfo subAccountInfo){
        Map<String,String> autoSubMap = new HashMap<String, String>();
        if(subAccountInfo != null){
            List<String> authoriz = new ArrayList<String>();
            authoriz = authorizLogic.findSubRole(subAccountInfo.getID(),subAccountInfo.getUserType());
            if(ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{autoSubMap.put("subAccount",subAccount);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{autoSubMap.put("crossReport",crossReport);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{autoSubMap.put("classifyReport",classifyReport);}
                }
            }else if(ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{autoSubMap.put("subAccount",subAccount);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{autoSubMap.put("crossReport",crossReport);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{
                        autoSubMap.put("subAccount",subAccount);
                    }
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{
                        autoSubMap.put("crossReport",crossReport);
                    }
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{
                        autoSubMap.put("replenishment",replenishment);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{
                        autoSubMap.put("subAccount",subAccount);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{
                        autoSubMap.put("crossReport",crossReport);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }
                }
            }
        }else{
            autoSubMap.put("replenishment",replenishment);
            autoSubMap.put("offLineAccount",offLineAccount);
            autoSubMap.put("subAccount",subAccount);
            autoSubMap.put("crossReport",crossReport);
            autoSubMap.put("classifyReport",classifyReport);
        }
        return autoSubMap;
    }
    public String queryUserTree()
    {
 
    	
    	Map<String,String> userTree=commonUserLogic.queryManagerUserTree(account, userType,searchUserStatus);
    	this.getRequest().setAttribute("treeMap", userTree);
    	this.getRequest().setAttribute("userType", userType);
    	this.getRequest().setAttribute("account", account);
    	return SUCCESS;
    }

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
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

	public String getSearchUserStatus() {
		return searchUserStatus;
	}

	public void setSearchUserStatus(String searchUserStatus) {
		this.searchUserStatus = searchUserStatus;
	}
    
}
