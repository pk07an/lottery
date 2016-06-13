package com.npc.lottery.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Tools;


public class UserAgentStaffAction extends BaseUserAction {

    /**
     * 
     */
    Logger logger = Logger.getLogger(UserAgentStaffAction.class);
    private static final long serialVersionUID = 4729446049533533569L;
    private String type = "userAgent";
    private List<UserCommission> commissions;

    private StockholderStaffExt stockholderStaffExt;

    private ChiefStaffExt chiefStaffExt;

    private BranchStaffExt branchStaffExt;
    private List<String> genAgentRateCount = new ArrayList<String>();// 总代理占成初始化
    private List<String> agentRateCount = new ArrayList<String>();// 代理占成初始化
    private IUserCommissionLogic userCommissionLogic; 
    private GenAgentStaffExt genAgentStaffExt;
    private AgentStaffExt agentStaffExt;
    private ManagerUser userInfo;
    private static final String ACCOUNT = "account";
    private int alreadyCreditLine;
    private List<UserCommission> commissionsList;// 上级退水
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private String searchUserStatus="0";
    private String  searchValue=null;
    private String  searchType=null;
    private String qUserID;
    private ISubAccountInfoLogic subAccountInfoLogic;
    private IMemberStaffExtLogic memberStaffExtLogic;
    private IAgentStaffExtLogic agentStaffExtLogic = null; // 代理
    private IGenAgentStaffExtLogic genAgentStaffExtLogic = null; // 总代理
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null; // 股东
    private IBranchStaffExtLogic branchStaffExtLogic;// 分公司
    private IChiefStaffExtLogic chiefStaffExtLogic = null;// 总监
    
    //add by peter
    private String searchAccount;
    private String parentAccount;
    
    /*
     * 分面查询全部代理用户
     */
    public String queryAgentStaff() {
    	ManagerStaff userInfo = getInfo();
  /*      subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));*/
    	String queryAccount=this.getRequest().getParameter("account");
    	String queryType=this.getRequest().getParameter("type");
    	if(queryAccount!=null&&queryType!=null)
    	{
    		 ManagerStaff requestUserInfo=null;
    		 //searchUserStatus=null;
    		if(ManagerStaff.USER_TYPE_STOCKHOLDER.equalsIgnoreCase(queryType))
        	 {
        		 requestUserInfo= stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, queryAccount);
        	 }
        	 else if(ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(queryType))
        	 {
        		 requestUserInfo= branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, queryAccount);
        	 }
        	 else if(ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(queryType))
        	 {
        		 requestUserInfo=genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, queryAccount);
        	 }
    		
    	 if(requestUserInfo!=null)
    		 userInfo=requestUserInfo;
    	 
    	}
     String account=null;
   	 String chName=null;
   	 if("account".equals(searchType)&&!GenericValidator.isBlankOrNull(searchValue))
   	 {
   		 account= searchValue;
   	 }else if("chName".equals(searchType)&&!GenericValidator.isBlankOrNull(searchValue))
   	 {
   		 chName =searchValue;
   	 }
        
        Page<AgentStaffExt> page = new Page<AgentStaffExt>(20);
        //List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        /*filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_AGENT));*/
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = agentStaffExtLogic.findPage(page,userInfo, searchUserStatus, account, chName);
        this.getRequest().setAttribute("page", page);
        
        //add by peter 
        this.setSearchAccount(this.getRequest().getParameter("account"));
        return SUCCESS;
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
   
    /**
     * 进入创建代理页面
     * 
     * @return
     */
    public String saveAgentStaff() {
       ManagerUser userInfo = getInfo();
        
       
       boolean isGenAgent=userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT);// 股東类型
       boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB); 
       boolean isSubGenAgent=false;
       GenAgentStaffExt defaultGenAgent=new GenAgentStaffExt();       
       String managerAccount=userInfo.getAccount();
       if(isSub){
       	SubAccountInfo subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
       	 if(subAccountInfo!=null)
       	 {
       		isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType());// 股东
       	 }
       	managerAccount=subAccountInfo.getManagerStaff().getAccount();         
       } 
       else
       	managerAccount=userInfo.getAccount();
      
      
       List<ManagerUser>     selManagers=new ArrayList<ManagerUser>();
       //add by peter 把获得的上级管理列表过滤，只留下启动的管理对象
       //如果searchAccount有值，过滤该账户下的用户
       String searchAccount = this.getRequest().getParameter("searchAccount");
       if(StringUtils.isNotEmpty(searchAccount)){
       		managerAccount = searchAccount;
       }
       List<ManagerUser>     selManagersDB= commonUserLogic.queryBelowManager(managerAccount, ManagerStaff.USER_TYPE_GEN_AGENT); 
       if (selManagersDB != null && selManagersDB.size() > 0) {
     	  for (ManagerUser managerUser : selManagersDB) {
     		  if ("0".equals(managerUser.getFlag())) {
     			  selManagers.add(managerUser);
     		  }
     	  }
       }
       //add by peter
 	  String parentAccount = this.getRequest().getParameter("parentAccount");
 	  if(StringUtils.isNotEmpty(parentAccount)){
 		  for(ManagerUser managerUser:selManagers){
 			  if(parentAccount.equals(managerUser.getAccount())){
 				selManagers = new ArrayList<ManagerUser>();
 				selManagers.add(managerUser);
 				  break;
 			  }
 		  }
 	  }
       if(selManagers!=null&&selManagers.size()>0)
       {
     	  ManagerUser mu=selManagers.get(0);
     	 defaultGenAgent=genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, mu.getAccount()); 
       }
       if(genAgentStaffExt!=null&&genAgentStaffExt.getAccount()!=null)
       {
    	   defaultGenAgent=genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, genAgentStaffExt.getAccount());
     	  
       }
       this.getRequest().setAttribute("selList", selManagers);
       this.getRequest().setAttribute("isGenAgentOrSub", isGenAgent||isSubGenAgent);
       this.getRequest().setAttribute("selectGenAgent", defaultGenAgent);
       
       return SUCCESS;
 
    }

    /**
     * 选择用户的时候初始化数据到页面
     * 
     * @return
     */
    public String saveFindAgent() {
        userInfo = getInfo();
        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));
        
        
        int availableMoney = 0;// 可用信用额度
        int totalMoney = 0;// 总信用额度用了多少
        int percentCount = 0;
        int remainingMoery = 0;
        genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT,
                getRequest().getParameter(ACCOUNT));
        this.getRequest().setAttribute("selaccount",
                getRequest().getParameter(ACCOUNT));
        if (genAgentStaffExt != null) {
            // 可用信用额度
            availableMoney = genAgentStaffExt.getTotalCreditLine();
            // 占成还剩多少
            percentCount += genAgentStaffExt.getGenAgentRate();
            // 算出一共用了多少信用额度
            for (AgentStaffExt AeStaffExt : genAgentStaffExt
                    .getAgentStaffExtsSet()) {
                totalMoney += AeStaffExt.getTotalCreditLine();
            }
            remainingMoery = availableMoney - totalMoney;// 还剩多少可用
            // 初始化占成
            for (int i = 5; i <= percentCount; i += 5) {
                genAgentRateCount.add(String.valueOf(i) + "%");
                agentRateCount.add(String.valueOf(i) + "%");
            }
            boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 总监类型
            boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司类型
            boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股东
            boolean isSub = ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType());// 总代理
            boolean isSubChief = false;
            boolean isSubBranch = false;
            boolean isSubStockholder = false;
            if(isSub){
                if(subAccountInfo !=null){
                    isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 总监类型
                    isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 分公司类型
                    isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType());// 股东
               }
            } 
            
            
            if (isChief || isSubChief) {// 总监进来
                // 查询总监
                try {
                    if(isChief){
                        chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT,
                                userInfo.getAccount()); 
                    }else if(isSubChief){
                        chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("managerStaffID",subAccountInfo.getParentStaff()); 
                    }
                   
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法saveFindAgent时出现错误 "
                            + e.getMessage());
                }
            } else if (isBranch || isSubBranch) {// 分公司进来
                try {
                    if(isBranch){
                        branchStaffExt = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, userInfo.getAccount()); 
                    }else if(isSubBranch){
                        branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("managerStaffID",subAccountInfo.getParentStaff());
                    }
                   
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法saveFindAgent时出现错误 "
                            + e.getMessage());
                }
            } else if (isStockholder || isSubStockholder) {// 股东进来
                try {
                    if(isStockholder){
                        stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT,userInfo.getAccount()); 
                    }else if(isSubStockholder){
                        stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",subAccountInfo.getParentStaff()); 
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法saveFindAgent时出现错误 "
                            + e.getMessage());
                }
            }
            commissions = userCommissionLogic.queryCommission(genAgentStaffExt
                    .getID(),genAgentStaffExt.getUserType()); // 查询分公司退水
            getRequest().setAttribute("percentCount", percentCount);// 占成还剩多少
            getRequest().setAttribute("remainingMoery", remainingMoery);// 还剩多少可用
            getRequest().setAttribute("availableMoney", availableMoney);// 可用信用额度
            getRequest().setAttribute("typeUser", userInfo.getUserType());// 标记类型
            return SUCCESS;
        }
        getFailuere("上级用户不存在");
        return "failure";

    }

    /**
     * 代理提交注册信息
     * 
     * @return
     */
    public String saveAgent() {
        
    	GenAgentStaffExt sdeSat = new GenAgentStaffExt();
        sdeSat = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT,
                agentStaffExt.getGenAgentStaffExt().getAccount());
        if (sdeSat == null) {
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>上级用户为空</font>");
            return "failure";
        }
        
       
        // 判断总信用额度不能超过剩下的信用额度
        if (agentStaffExt.getTotalCreditLine() > sdeSat.getAvailableCreditLine()) {
        	getFailuere("判断总信用额度不能超过剩下的信用额度");
            return "failure";
        }

        // 如果上级用户走飞为禁止，下级必须是禁止
        if (agentStaffExt.getReplenishment().equals(ManagerStaff.REPLENIS_USE)) {
            boolean isReplen = agentStaffExt.getReplenishment().equals(sdeSat.getReplenishment());
            if (!isReplen) {
            	getFailuere("如果上级用户走飞为禁止，下级必须是禁止");
                return "failure";
            }
        }
        AgentStaffExt staffExt = new AgentStaffExt();
        staffExt = agentStaffExtLogic.queryAgenStaffExt(ACCOUNT,agentStaffExt.getAccount());
        if(staffExt!=null)
        {
        	getFailuere("用戶 已經存在");
            return "failure";
        }
           agentStaffExt.setChiefStaff(sdeSat.getStockholderStaffExt().getBranchStaffExt().getChiefStaffExt().getID());
           agentStaffExt.setAvailableCreditLine(agentStaffExt.getTotalCreditLine());
        // 去后台判断用户是否存在
          
            ManagerUser  userInfo = getInfo();
            agentStaffExt.setCreateDate(new Date());
            agentStaffExt.setUserType(ManagerStaff.USER_TYPE_AGENT);
            //MD5 md5 = new MD5();
            String userPwdOrignMd5 = agentStaffExt.getUserPwd().trim();
            agentStaffExt.setUserPwd(userPwdOrignMd5);
            agentStaffExt.setGenAgentStaffExt(sdeSat);
            StockholderStaffExt stock=sdeSat.getStockholderStaffExt();
            agentStaffExt.setStockholderStaff(stock.getID());
            agentStaffExt.setBranchStaff(stock.getBranchStaffExt().getID());
            agentStaffExt.setParentStaffQry(sdeSat.getID())  ;
            agentStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_GEN_AGENT);
            agentStaffExt.setFlag("0");
            AgentStaffExt agenExt = new AgentStaffExt();
            BeanUtils.copyProperties(agentStaffExt, agenExt);
            int available = sdeSat.getAvailableCreditLine() - agentStaffExt.getTotalCreditLine();
            sdeSat.setAvailableCreditLine(available);
            agenExt.setGenAgentStaffExt(sdeSat);
            //genAgentStaffExtLogic.updateGenAgentStaffExt(sdeSat);          
            Long agentID=agentStaffExtLogic.saveUserAgentStaff(agenExt, userInfo.getID());        
            this.setqUserID(Tools.encodeWithKey(agentID+""));        
            return SUCCESS;
 
    }

    /**
     * 修改代理
     * 
     * @return
     */
    public String updateFindByAgent() {        
    	String account = getRequest().getParameter(ACCOUNT).trim();
        //ManagerUser userInfo = getInfo();  
    
        // 查出用户数据
       agentStaffExt = agentStaffExtLogic.queryAgenStaffExt(ACCOUNT, account);
       GenAgentStaffExt gen=agentStaffExt.getGenAgentStaffExt();
       StockholderStaffExt stock=gen.getStockholderStaffExt();
       BranchStaffExt branch=stock.getBranchStaffExt();
       //int maxRate=commonUserLogic.queryBelowMaxRate(agentStaffExt.getID(), ManagerStaff.USER_TYPE_AGENT);

     
       
       int maxRestrictRate=100-branch.getChiefRate()-stock.getBranchRate()-gen.getShareholderRate();
       //int minRestrictRate=maxRate;
       //int userMaxRate=100-maxRate-branch.getChiefRate()-stock.getBranchRate()-gen.getShareholderRate();
       //if(userMaxRate>maxRestrictRate)
       //	userMaxRate=maxRestrictRate;
       //this.getRequest().setAttribute("maxRate",userMaxRate );
       this.getRequest().setAttribute("maxRestrictRate", maxRestrictRate);
       //this.getRequest().setAttribute("minRestrictRate", minRestrictRate);
       
		// 用户下面是否有投注
		// boolean hasBet=commonUserLogic.queryUserTreeHasBet(agentStaffExt.getID(), ManagerStaff.USER_TYPE_AGENT);
		// this.getRequest().setAttribute("hasBet", hasBet);
       return SUCCESS;
}
    /*
     * 提交修改数据
     */
    
    public String updateAgent() {
       
    	ManagerUser info = this.getCurrentManagerUser();
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
    	
       // 查出用户数据
       AgentStaffExt agentStaffExtSearch = agentStaffExtLogic.queryAgenStaffExt(ACCOUNT, agentStaffExt.getAccount()); 
       
		// add by peter
		if (StringUtils.isNotEmpty(agentStaffExt.getUserPwd())) {
			agentStaffExt.setPasswordUpdateDate(new Date());
			agentStaffExt.setPasswordResetFlag("Y");
		}
       agentStaffExtLogic.updateUserAgentStaff(agentStaffExt,userInfoNew.getUserType(),this.getCurrentManagerUser());
       
       agentStaffExt.setManagerStaffID(agentStaffExtSearch.getManagerStaffID());
       //处理主帐号禁止补货状态时，把下属的子帐号全部投为禁止补货
       if(Constant.CLOSE.equals(agentStaffExt.getReplenishment())){
	       	ManagerUser cUserInfo = new ManagerUser();
	       	cUserInfo.setAccount(agentStaffExt.getAccount());
	       	cUserInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
	       	subAccountInfoLogic.handleReplenishmentByParent(cUserInfo);
	       	
	      //把该用户的自动补货设置改为关闭
	    	String shopId = info.getShopsInfo().getID()+""; 
	    	replenishAutoLogic.updateReplenishAutoSetByUser(info.getShopsInfo().getID().toString(), agentStaffExt.getManagerStaffID(), 
	    			agentStaffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
	    	replenishAutoLogic.updateReplenishAutoSetForClose(shopId, agentStaffExt.getManagerStaffID(), agentStaffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
       }

        return SUCCESS;
  
    }
    
    private IReplenishAutoLogic replenishAutoLogic;
    
	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}
	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
	}


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UserCommission> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<UserCommission> commissions) {
        this.commissions = commissions;
    }

    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
        return stockholderStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
    }

    public StockholderStaffExt getStockholderStaffExt() {
        return stockholderStaffExt;
    }

    public void setStockholderStaffExt(StockholderStaffExt stockholderStaffExt) {
        this.stockholderStaffExt = stockholderStaffExt;
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

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
    }

    public BranchStaffExt getBranchStaffExt() {
        return branchStaffExt;
    }

    public void setBranchStaffExt(BranchStaffExt branchStaffExt) {
        this.branchStaffExt = branchStaffExt;
    }

    public List<String> getGenAgentRateCount() {
        return genAgentRateCount;
    }

    public void setGenAgentRateCount(List<String> genAgentRateCount) {
        this.genAgentRateCount = genAgentRateCount;
    }

    public List<String> getAgentRateCount() {
        return agentRateCount;
    }

    public void setAgentRateCount(List<String> agentRateCount) {
        this.agentRateCount = agentRateCount;
    }

    public IUserCommissionLogic getUserCommissionLogic() {
        return userCommissionLogic;
    }

    public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
        this.userCommissionLogic = userCommissionLogic;
    }

    public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
        return genAgentStaffExtLogic;
    }

    public void setGenAgentStaffExtLogic(
            IGenAgentStaffExtLogic genAgentStaffExtLogic) {
        this.genAgentStaffExtLogic = genAgentStaffExtLogic;
    }

    public GenAgentStaffExt getGenAgentStaffExt() {
        return genAgentStaffExt;
    }

    public void setGenAgentStaffExt(GenAgentStaffExt genAgentStaffExt) {
        this.genAgentStaffExt = genAgentStaffExt;
    }

    public IAgentStaffExtLogic getAgentStaffExtLogic() {
        return agentStaffExtLogic;
    }

    public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
        this.agentStaffExtLogic = agentStaffExtLogic;
    }

    public AgentStaffExt getAgentStaffExt() {
        return agentStaffExt;
    }

    public void setAgentStaffExt(AgentStaffExt agentStaffExt) {
        this.agentStaffExt = agentStaffExt;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }

    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
    }

    public int getAlreadyCreditLine() {
        return alreadyCreditLine;
    }

    public void setAlreadyCreditLine(int alreadyCreditLine) {
        this.alreadyCreditLine = alreadyCreditLine;
    }

    public List<UserCommission> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommission> commissionsList) {
        this.commissionsList = commissionsList;
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

    public ISubAccountInfoLogic getSubAccountInfoLogic() {
        return subAccountInfoLogic;
    }

    public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
        this.subAccountInfoLogic = subAccountInfoLogic;
    }
	public String getSearchUserStatus() {
		return searchUserStatus;
	}
	public void setSearchUserStatus(String searchUserStatus) {
		this.searchUserStatus = searchUserStatus;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getqUserID() {
		return qUserID;
	}
	public void setqUserID(String qUserID) {
		this.qUserID = qUserID;
	}
	public String getSearchAccount() {
		return searchAccount;
	}
	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
	public String getParentAccount() {
		return parentAccount;
	}
	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

    
}
