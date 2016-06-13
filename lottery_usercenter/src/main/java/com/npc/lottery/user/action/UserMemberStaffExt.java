package com.npc.lottery.user.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.MD5;
import com.npc.lottery.service.OnlineMemberService;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Tools;

public class UserMemberStaffExt extends BaseUserAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4723187893503244740L;
    private Logger logger = Logger.getLogger(UserMemberStaffExt.class);
    private String type = "userMember";
    private List<UserCommission> commissions;
    private List<UserCommissionDefault> commissionDefault;

    private StockholderStaffExt stockholderStaffExt;

    private ChiefStaffExt chiefStaffExt;

    private BranchStaffExt branchStaffExt;
    private List<String> rateCount = new ArrayList<String>();// 占成初始化
    private IUserCommissionLogic userCommissionLogic;

    private GenAgentStaffExt genAgentStaffExt;

    private AgentStaffExt agentStaffExt;
    private ManagerUser userInfo;
    private MemberStaffExt memberStaffExt;

    private IUserCommissionDefault userCommissionDefaultLogic;
    private static final String ACCOUNT = "account";
    private List<UserCommission> commissionsList;// 上级退水

    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private ICommonUserLogic commonUserLogic;
    private String searchUpper=null;
    private String searchUserStatus="0";
    private String  searchValue=null;
    private String  searchType=null;
    private String qUserID;
    
    private OnlineMemberService onlineMemberService;
   
    //add by peter
    private String searchAccount;
    private String parentAccount;
    //查询所有会员
    public String queryMemberStaff() {
    	ManagerStaff  userInfo = getInfo();
    	boolean subAgent=false;
    	SubAccountInfo	subAccountInfo = new SubAccountInfo();
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()))
    	{
    		subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
    		if(ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType()))
    		{
    			subAgent=true;
    		}
    	}
    	this.getRequest().setAttribute("agentSub", subAgent);
       /* subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
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
    	 else if(ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(queryType))
    	 {
    		 requestUserInfo=agentStaffExtLogic.queryAgenStaffExt(ACCOUNT, queryAccount);
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
       	 
       	 boolean isChief = false;
       	 boolean isBranch = false;
       	 boolean isStock = false;
       	 boolean isGenAgent = false;
       	 boolean isAgent = false;
       	 
       	 if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType()) || subAccountInfo!=null && ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()) 
       			 && ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType())){
       		isChief = true;
       	 }
       	 if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType()) || subAccountInfo!=null && ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()) 
       			 && ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType())){
       		isBranch = true;
       	 }
       	 if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType()) || subAccountInfo!=null && ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()) 
       			 && ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType())){
       		isStock = true;
       	 }
       	 if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType()) || subAccountInfo!=null && ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()) 
       			 && ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType())){
       		isGenAgent = true;
       	 }
       	 if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType()) || subAccountInfo!=null && ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType()) 
       			 && ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType())){
       		isAgent = true;
       	 }
       	 
       	 //查询用户类型列表
       	 Map<String,String> map = new TreeMap<String,String>();
       	 if(isChief==true || isBranch==true){
       		 map.put("0", "所有會員");
       		 map.put(ManagerStaff.USER_TYPE_BRANCH,"屬分公司");
       		 map.put(ManagerStaff.USER_TYPE_STOCKHOLDER,"屬股東");
       		 map.put(ManagerStaff.USER_TYPE_GEN_AGENT,"屬總代理");
       	 }else if(isStock==true){
       		 map.put(ManagerStaff.USER_TYPE_STOCKHOLDER,"屬股東");
       		 map.put(ManagerStaff.USER_TYPE_GEN_AGENT,"屬總代理");
       		 map.put("0", "所有會員");
       	 }else if(isGenAgent==true){
      		 map.put("0", "所有會員");
      		 map.put(ManagerStaff.USER_TYPE_GEN_AGENT,"屬總代理");
       	}else if(isAgent==true){
      		 map.put("0", "所有會員");
      	 }
       	 getRequest().setAttribute("userMap",map);
    	
        Page<MemberStaffExt> page = new Page<MemberStaffExt>(20);
        userInfo.getFlag();
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
       // memberStaffExtLogic.findManagerRate(userInfo);  //测试占成
       // memberStaffExtLogic.findRate(memberStaffExtLogic.queryMemberStaffExt("account","as11"));
        if("0".equals(searchUpper))
        	searchUpper=null;
        try {
            page = memberStaffExtLogic.findPage(page, userInfo, searchUserStatus,searchUpper, account, chName);
            this.getRequest().setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法queryMemberStaff时出现错误 "
                    + e.getMessage());
        }
        //add by peter 
        this.setSearchAccount(this.getRequest().getParameter("account"));
        
        Map<String, Date> onlineMemberMap = onlineMemberService.getOnlineMember(this.getCurrentManagerUser().getShopsInfo().getShopsCode());
        this.getRequest().setAttribute("onlineMember", onlineMemberMap);
        return SUCCESS;
    }

    //创建会员初始化
    public String saveInitMember() {
     
       ManagerUser userInfo = getInfo();
             
       boolean isAgent=userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT);// 股東类型
       boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB); 
       boolean isSubAgent=false;
       AgentStaffExt defaultAgent=new AgentStaffExt();       
       String managerAccount=userInfo.getAccount();
       if(isSub){
       	SubAccountInfo subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
       	 if(subAccountInfo!=null)
       	 {
       		isSubAgent = ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType());// 股东
       	 }
       	managerAccount=subAccountInfo.getManagerStaff().getAccount();         
       } 
       else
       	managerAccount=userInfo.getAccount();
      
       //如果searchAccount有值，过滤该账户下的用户
       String searchAccount = this.getRequest().getParameter("searchAccount");
       if(StringUtils.isNotEmpty(searchAccount)){
       		managerAccount = searchAccount;
       }
       List<ManagerUser> selManagersDB= commonUserLogic.queryBelowManager(managerAccount, ManagerStaff.USER_TYPE_AGENT);
       //add by peter 把获得的上级管理列表过滤，只留下启动的管理对象
       List<ManagerUser>     selManagers= new ArrayList<ManagerUser>();
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
     	 defaultAgent=agentStaffExtLogic.queryAgenStaffExt(ACCOUNT, mu.getAccount()); 
       }
       if(agentStaffExt!=null&&agentStaffExt.getAccount()!=null)
       {
    	   defaultAgent=agentStaffExtLogic.queryAgenStaffExt(ACCOUNT, agentStaffExt.getAccount());
     	  
       }
       this.getRequest().setAttribute("selList", selManagers);
       this.getRequest().setAttribute("isAgentOrSub", isAgent||isSubAgent);
       this.getRequest().setAttribute("selectAgent", defaultAgent);
       
       return SUCCESS;

    }

  
    /**
     * 选择用户的时候初始化数据到页面
     * 
     * @return
     */
    public String saveFindMember() {
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
        try {
            agentStaffExt = agentStaffExtLogic.queryAgenStaffExt(ACCOUNT,
                    getRequest().getParameter(ACCOUNT));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法saveFindMember时出现错误 "
                    + e.getMessage());
        }
        this.getRequest().setAttribute("selaccount",
                getRequest().getParameter(ACCOUNT));
        if (agentStaffExt != null) {
            // 可用信用额度
            availableMoney = agentStaffExt.getTotalCreditLine();
            // 占成还剩多少
            percentCount += agentStaffExt.getAgentRate();
            // 算出一共用了多少信用额度

            for (MemberStaffExt merStExt : agentStaffExt.getMemberStaffExtSet()) {
                totalMoney += merStExt.getTotalCreditLine();
            }
            remainingMoery = availableMoney - totalMoney;// 还剩多少可用
            // 初始化占成
            for (int i = 5; i <= percentCount; i += 5) {
                rateCount.add(String.valueOf(i) + "%");
            }
            boolean isChief = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_CHIEF);// 总监类型
            boolean isBranch = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            boolean isStockholder = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
            boolean isGenAgent = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
            boolean isSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 总代理
            boolean isSubChief = false;
            boolean isSubBranch = false;
            boolean isSubStockholder = false;
            boolean isSubGenAgent = false;
            if(isSub){
                if(subAccountInfo !=null){
                    isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 总监类型
                    isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 分公司类型
                    isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType());// 股东
                    isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType());// 总代理
               }
            } 
            
            
            if (isChief ||isSubChief) {// 总监进来
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
                            + "中的方法saveFindMember时出现错误 "
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
                            + "中的方法saveFindMember时出现错误 "
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
                            + "中的方法saveFindMember时出现错误 "
                            + e.getMessage());
                }
            } else if (isGenAgent || isSubGenAgent) {// 总代理进来
                try {
                    if(isGenAgent){
                        genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, userInfo.getAccount()); 
                    }else if(isSubGenAgent){
                        genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",subAccountInfo.getParentStaff()); 
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法saveFindMember时出现错误 "
                            + e.getMessage());
                }
            }
            commissions = userCommissionLogic.queryCommission(agentStaffExt
                    .getID(),agentStaffExt.getUserType()); // 查询分公司退水
            getRequest().setAttribute("remainingMoery", remainingMoery);// 还剩多少可用
            getRequest().setAttribute("availableMoney", availableMoney);// 可用信用额度
            getRequest().setAttribute("typeUser", userInfo.getUserType());// 标记类型
            return SUCCESS;
        }
        getFailuere("选择初始化失败");
        return "failure";
    }

    /**
     * 提交注册数据
     * 
     * @return
     */
    public String saveMember() {
    	       
    	AgentStaffExt sdeSat = new AgentStaffExt();
        sdeSat = agentStaffExtLogic.queryAgenStaffExt(ACCOUNT,memberStaffExt.getManagerStaff().getAccount());
        if (sdeSat == null) {
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>上级用户为空</font>");
            return "failure";
        }
        
       
        // 判断总信用额度不能超过剩下的信用额度
        if (memberStaffExt.getTotalCreditLine() > sdeSat.getAvailableCreditLine()) {
        	getFailuere("判断总信用额度不能超过剩下的信用额度");
            return "failure";
        }

        MemberStaffExt memberExt = new MemberStaffExt();
        memberExt = memberStaffExtLogic.queryMemberStaffExt(ACCOUNT,memberStaffExt.getAccount());
        if(memberExt!=null)
        {
        	getFailuere("用戶 已經存在");
            return "failure";
        }
           memberStaffExt.setAvailableCreditLine(memberStaffExt.getTotalCreditLine());
        // 去后台判断用户是否存在
        
            ManagerUser  userInfo = getInfo();
            memberStaffExt.setCreateDate(new Date());
            memberStaffExt.setUserType(ManagerStaff.USER_TYPE_MANAGER);
            //MD5 md5 = new MD5();
            String userPwdOrignMd5 = memberStaffExt.getUserPwd().trim();
            memberStaffExt.setUserPwd(userPwdOrignMd5);
            memberStaffExt.setParentUserType(ManagerStaff.USER_TYPE_AGENT);
            memberStaffExt.setAgentStaff(sdeSat.getID());
            memberStaffExt.setParentStaffQry(sdeSat.getID())  ;
            memberStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_AGENT);
            memberStaffExt.setFlag("0");
            memberStaffExt.setParentStaff(sdeSat.getID());
            memberStaffExt.setUserType(MemberStaff.USER_TYPE_MEMBER);
            memberStaffExt.setGenAgentStaff(sdeSat.getGenAgentStaffExt().getID());
            memberStaffExt.setStockholderStaff(sdeSat.getGenAgentStaffExt().getStockholderStaffExt().getID());
            memberStaffExt.setBranchStaff(sdeSat.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getID());
            memberStaffExt.setChiefStaff(sdeSat.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getChiefStaffExt().getID());
            MemberStaffExt membExt = new MemberStaffExt();
            BeanUtils.copyProperties(memberStaffExt, membExt);
            int available = sdeSat.getAvailableCreditLine() - memberStaffExt.getTotalCreditLine();
            sdeSat.setAvailableCreditLine(available);
            membExt.setManagerStaff(sdeSat);
            Long memberId=memberStaffExtLogic.saveUserMemberStaff(membExt, userInfo.getID());
            this.setqUserID(Tools.encodeWithKey(memberId+""));
            return SUCCESS;
 
}

    /**
     * 修改数据
     * 
     * @return
     */
    public String updateFindByMember() {
    	
    	
    	String account = getRequest().getParameter(ACCOUNT).trim();
        //ManagerUser userInfo = getInfo();     
        // 查出用户数据
       memberStaffExt = memberStaffExtLogic.queryMemberStaffExt(ACCOUNT, account);
       ManagerStaff  managerStaff=memberStaffExt.getManagerStaff();
       String managerAccount=managerStaff.getAccount();
       String managerName=managerStaff.getChsName();
       int  managerAvailable=0;
       int rate=100;
       String managerType=managerStaff.getUserType();
       int maxRate=0;
       int chiefRate=0;
       int branchRate=0;
       int stockRate=0;
       int genAgentRate=0;
	   // add by peter
	   String rateRestrict = "";
	   Integer belowRateLimit = 0;
       if (managerStaff instanceof AgentStaffExt) {
    	   agentStaffExt=(AgentStaffExt)managerStaff;
    	   GenAgentStaffExt gen=agentStaffExt.getGenAgentStaffExt();
    	   StockholderStaffExt stock=gen.getStockholderStaffExt();
    	   BranchStaffExt branch=stock.getBranchStaffExt();
    	   managerAvailable=agentStaffExt.getAvailableCreditLine();
    	   genAgentRate=agentStaffExt.getGenAgentRate();
    	   rate=genAgentRate;
    	   stockRate=gen.getShareholderRate();
    	   branchRate=stock.getBranchRate();
    	   chiefRate=branch.getChiefRate().intValue();
    	   //add by peter
    	   rateRestrict = agentStaffExt.getRateRestrict();
    	   belowRateLimit = agentStaffExt.getBelowRateLimit();
			// 如果上级代理没有设置限制占成
			if (StringUtils.isEmpty(rateRestrict) || "0".equals(rateRestrict)) {
				// 检查上上级总代理有没有限制
				rateRestrict = agentStaffExt.getGenAgentStaffExt().getRateRestrict();
				belowRateLimit = agentStaffExt.getGenAgentStaffExt().getBelowRateLimit();
				// 如果也没有
				if (StringUtils.isEmpty(rateRestrict) || "0".equals(rateRestrict)) {
					// 检查上上上级股东有没有限制
					rateRestrict = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getRateRestrict();
					
					if(StringUtils.isNotEmpty(rateRestrict) && "1".equals(rateRestrict)){
						belowRateLimit = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBelowRateLimit() - agentStaffExt.getGenAgentRate()-agentStaffExt.getGenAgentStaffExt().getShareholderRate();
					}
				}else{
					belowRateLimit = belowRateLimit - agentStaffExt.getGenAgentRate();
				}
			} 
    	   
       } 
       
       else if (managerStaff instanceof ChiefStaffExt) {
    	   
    	   chiefStaffExt=(ChiefStaffExt)managerStaff;
    	   //managerAvailable=chiefStaffExt.getAvailableCreditLine();
       } 
       else if (managerStaff instanceof BranchStaffExt) {
    	   
    	   branchStaffExt=(BranchStaffExt)managerStaff;
    	   managerAvailable=branchStaffExt.getAvailableCreditLine();
    	   
    	   rate=branchStaffExt.getChiefRate();
    	   chiefRate=branchStaffExt.getChiefRate();
    	   
       } 
       else if (managerStaff instanceof StockholderStaffExt) {
    	   stockholderStaffExt=(StockholderStaffExt)managerStaff;
    	   managerAvailable=stockholderStaffExt.getAvailableCreditLine();
    	   
    	   branchRate=stockholderStaffExt.getBranchRate();
    	   rate=branchRate;
    	   chiefRate=stockholderStaffExt.getBranchStaffExt().getChiefRate();
    	   //add by peter
    	   rateRestrict = stockholderStaffExt.getRateRestrict();
    	   belowRateLimit = stockholderStaffExt.getBelowRateLimit();
       }
       else if (managerStaff instanceof GenAgentStaffExt) {
    	   genAgentStaffExt=(GenAgentStaffExt)managerStaff;
    	   managerAvailable=genAgentStaffExt.getAvailableCreditLine();
    	   
    	   stockRate=genAgentStaffExt.getShareholderRate();
    	   rate=stockRate;
    	   branchRate=genAgentStaffExt.getStockholderStaffExt().getBranchRate();
    	   chiefRate=genAgentStaffExt.getStockholderStaffExt().getBranchStaffExt().getChiefRate();
    	   //add by peter
    	   rateRestrict = genAgentStaffExt.getRateRestrict();
    	   belowRateLimit = genAgentStaffExt.getBelowRateLimit();
			
			if (StringUtils.isEmpty(rateRestrict) || "0".equals(rateRestrict)) {
				// 检查上上上级股东有没有限制
				rateRestrict = genAgentStaffExt.getStockholderStaffExt().getRateRestrict();
				if (StringUtils.isNotEmpty(rateRestrict) && "1".equals(rateRestrict)) {
					belowRateLimit = genAgentStaffExt.getStockholderStaffExt().getBelowRateLimit() - genAgentStaffExt.getShareholderRate();
				}
			}
    	   
       }
       maxRate=chiefRate+branchRate+stockRate+genAgentRate;
       this.getRequest().setAttribute("managerAccount", managerAccount);
       this.getRequest().setAttribute("managerName", managerName);
       this.getRequest().setAttribute("managerAvailable", managerAvailable);
       this.getRequest().setAttribute("managerType", managerType);
       this.getRequest().setAttribute("rate", 100-maxRate);
       //add by peter
       this.getRequest().setAttribute("rateRestrict", rateRestrict);
       this.getRequest().setAttribute("belowRateLimit", belowRateLimit);
       boolean hasBet=commonUserLogic.queryUserTreeHasBet(memberStaffExt.getID(), MemberUser.USER_TYPE_MEMBER);
       this.getRequest().setAttribute("hasBet", hasBet);
       
       return SUCCESS;

    }

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	public String updateMember() {
		// add by peter
		if (StringUtils.isNotEmpty(memberStaffExt.getUserPwd())) {
			memberStaffExt.setPasswordUpdateDate(new Date());
			memberStaffExt.setPasswordResetFlag("Y");
		}
		//memberStaffExtLogic.updateUserMemberStaff(memberStaffExt);
		//modify by peter for log the change log
		memberStaffExtLogic.updateUserMemberStaff(memberStaffExt,this.getCurrentManagerUser());

		return SUCCESS;

	}
    //创建直属会员
    public String savaFindImmediateMember() {
           ManagerStaff userInfo = getInfo();
           ManagerStaff defaultManager=new ManagerStaff();
           String selectUserType=ManagerStaff.USER_TYPE_GEN_AGENT;
           String userType= this.getRequest().getParameter("userType");
           boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 总监類型
           boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司類型
           boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股東
           boolean isGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType());// 总代理
           LinkedHashMap<String,String> map=new LinkedHashMap<String,String>(); 
           boolean isSubChief=false;
           boolean isSubBranch=false;
           boolean isSubStock=false;
           boolean isSubGenAgent=false;
           boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);
           ManagerStaff backUser=userInfo;
           if(isSub)
           {
        	   
        	   SubAccountInfo subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        	   String parentType=subAccountInfo.getParentUserType();
        	   isSubChief=ManagerStaff.USER_TYPE_CHIEF.equals(parentType);
        	   isSubBranch=ManagerStaff.USER_TYPE_BRANCH.equals(parentType);
        	   isSubStock=ManagerStaff.USER_TYPE_STOCKHOLDER.equals(parentType);
        	   isSubGenAgent=ManagerStaff.USER_TYPE_GEN_AGENT.equals(parentType);
        	   backUser=getBackManagerUser(userInfo);
           }
             
          if (isChief||isSubChief) {// 总监进来
                // 查询总监
                //chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT,userInfo.getAccount());
               // commissionDefault = userCommissionDefaultLogic.queryCommissionDefault(chiefStaffExt.getID());
                selectUserType=ManagerStaff.USER_TYPE_BRANCH;
            	
                 //map.put("2", " 總監");
                 map.put("3", " 分公司");
                 map.put("4", "  股東");
                 map.put("5", " 總代理");
                

            } else if (isBranch||isSubBranch) {// 分公司进来
            	selectUserType=ManagerStaff.USER_TYPE_BRANCH;
          
                //commissions = userCommissionLogic.queryCommission(branchStaffExt.getID(),branchStaffExt.getUserType());// 获取分公司退水
    
                map.put("3", " 分公司");
                map.put("4", "  股東");
                map.put("5", " 總代理");
               
            } else if (isStockholder||isSubStock) {// 股东进来
               // stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT,userInfo.getAccount());
                
                selectUserType=ManagerStaff.USER_TYPE_STOCKHOLDER;
       
               
                map.put("4", "  股東");
                map.put("5", " 總代理");
               
            } else if (isGenAgent||isSubGenAgent) {// 总代理进来
                //genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, userInfo.getAccount());
                selectUserType=ManagerStaff.USER_TYPE_GEN_AGENT;
         
                
                map.put("5", " 總代理");
            }
          if(userType!=null)
      	   {
      		selectUserType=userType;
      	   }
          String realAccount=userInfo.getAccount();
          if(isSub)
          {
        	  realAccount=  backUser.getAccount();
          }
          //如果searchAccount有值，过滤该账户下的用户
          String searchAccount = this.getRequest().getParameter("searchAccount");
          if(StringUtils.isNotEmpty(searchAccount)){
        	  realAccount = searchAccount;
          }
          List<ManagerUser>     selManagersDB= commonUserLogic.queryBelowManager(realAccount, selectUserType);
          
          
          //add by peter 把获得的上级管理列表过滤，只留下启动的管理对象
          List<ManagerUser>     selManagers= new ArrayList<ManagerUser>();
          if (selManagersDB != null && selManagersDB.size() > 0) {
        	  for (ManagerUser managerUser : selManagersDB) {
        		  if ("0".equals(managerUser.getFlag())) {
        			  selManagers.add(managerUser);
        		  }
        	  }
          }
          int maxRate=0;
	       // add by peter
	   	   String rateRestrict = "";
	   	   Integer belowRateLimit = 0;
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
        	  String selectAccount=mu.getAccount();
        	  if(memberStaffExt!=null&&memberStaffExt.getManagerStaff()!=null&&memberStaffExt.getManagerStaff().getAccount()!=null)
              {
        		  if(!memberStaffExt.getManagerStaff().getAccount().isEmpty())
        		  selectAccount=memberStaffExt.getManagerStaff().getAccount();
              }
        	 if(ManagerStaff.USER_TYPE_CHIEF.equals(selectUserType))
        	  {
        		//defaultManager=chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT, selectAccount);
        		 BranchStaffExt branch=branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, selectAccount);
        		 defaultManager=branch;
        		 maxRate=branch.getChiefRate();
        		 
        	  }
        	  else if(ManagerStaff.USER_TYPE_BRANCH.equals(selectUserType))
        	  {
        		  BranchStaffExt branch=branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, selectAccount); 
        		  defaultManager=branch;
        		  maxRate=branch.getChiefRate();
        	  }
        	  else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(selectUserType))
        	  {
        		  StockholderStaffExt stock=stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, selectAccount); 
        		defaultManager=stock;
        		maxRate=stock.getBranchStaffExt().getChiefRate()+stock.getBranchRate();
        		//add by peter
        		rateRestrict = stock.getRateRestrict();
        		belowRateLimit = stock.getBelowRateLimit();
        	  }
        	  else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(selectUserType))
        	  {
        		  GenAgentStaffExt gen=genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, selectAccount); 
        		 defaultManager= gen;
        		 maxRate=gen.getShareholderRate()+gen.getStockholderStaffExt().getBranchRate()+gen.getStockholderStaffExt().getBranchStaffExt().getChiefRate(); 
        		//add by peter
        		
         		rateRestrict = gen.getRateRestrict();
         		belowRateLimit = gen.getBelowRateLimit();
				if (StringUtils.isEmpty(rateRestrict) || "0".equals(rateRestrict)) {
					rateRestrict = gen.getStockholderStaffExt().getRateRestrict();
					if (StringUtils.isNotEmpty(rateRestrict) && "1".equals(rateRestrict)) {
						belowRateLimit = gen.getStockholderStaffExt().getBelowRateLimit() - gen.getShareholderRate();
					}
				}
        	  }

          }
             this.getRequest().setAttribute("selectManager", defaultManager);
             this.getRequest().setAttribute("selList", selManagers);
             this.getRequest().setAttribute("userTypeMap", map);
             this.getRequest().setAttribute("maxRate", 100-maxRate);
             //add by peter
             this.getRequest().setAttribute("rateRestrict", rateRestrict);
             this.getRequest().setAttribute("belowRateLimit", belowRateLimit);
             if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(selectUserType))
             {
            	 return "genAgentMember";
             }
             else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(selectUserType))
             {
            	 return "stockMember";
             }
             else if(ManagerStaff.USER_TYPE_BRANCH.equals(selectUserType))
             {
            	 return "branchMember";
             }
             else if(ManagerStaff.USER_TYPE_CHIEF.equals(selectUserType))
             {
            	 
            	 return "branchMember";
             }
              //this.setqUserID(membExt.getID()+""); 
     
        return "failure";
    }
    //保存直属会员
    public String savaImmediateMember() {
       ManagerUser  userInfo = getInfo();
       MemberStaffExt memsfExt = new MemberStaffExt();
       memsfExt = memberStaffExtLogic.queryMemberStaffExt(ACCOUNT,memberStaffExt.getAccount());
       if (memsfExt != null) {
    	   getFailuere("用户已經存在");
           return "failure";
       }
       MemberStaffExt membeStaExt = new MemberStaffExt();
       memberStaffExt.setAvailableCreditLine(memberStaffExt.getTotalCreditLine());
       memberStaffExt.setRate(memberStaffExt.getRate());
       memberStaffExt.setCreateDate(new Date());
       //MD5 md5 = new MD5();
       String userPwdOrignMd5 = memberStaffExt.getUserPwd().trim();
       memberStaffExt.setUserPwd(userPwdOrignMd5);
       memberStaffExt.setUserType(MemberStaff.USER_TYPE_MEMBER);    
       memberStaffExt.setChiefStaff(userInfo.getShopsInfo().getChiefStaffExt().getID());
       memberStaffExt.setFlag("0");    
       boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 总监類型
       boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司類型
       boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股東
       boolean isGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType());// 总代理
       //ManagerStaff selectManager=new ManagerStaff();
       String selectedUserType=this.getRequest().getParameter("userType");
       int available=0;
       if(memberStaffExt.getManagerStaff()!=null&&memberStaffExt.getManagerStaff().getAccount()!=null)
       {
    	   
    	   String selectAccount=memberStaffExt.getManagerStaff().getAccount();
    	   if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(selectedUserType))
           {
    		   GenAgentStaffExt   genAgent=genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, selectAccount);
    		
    		   available=genAgent.getAvailableCreditLine();
    		   memberStaffExt.setParentUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
    		   memberStaffExt.setParentStaff(genAgent.getID()); 
    		   //userCommissions = userCommissionLogic.queryCommission(genAgent.getID(),ManagerStaff.USER_TYPE_GEN_AGENT); 
    		   memberStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_GEN_AGENT);
    		   memberStaffExt.setParentStaffQry(genAgent.getID());
    		   memberStaffExt.setGenAgentStaff(genAgent.getID());
    		   memberStaffExt.setStockholderStaff(genAgent.getStockholderStaffExt().getID());
    		   memberStaffExt.setBranchStaff(genAgent.getStockholderStaffExt().getBranchStaffExt().getID());
    		   if (memberStaffExt.getTotalCreditLine() > available) {
    	           return "failure";
    	       }
    		   else
    		   {
    			        BeanUtils.copyProperties(memberStaffExt, membeStaExt);
    			        genAgent.setAvailableCreditLine(available-memberStaffExt.getTotalCreditLine());
    	                membeStaExt.setManagerStaff(genAgent);
    	                
    		   }
    			   
           }
           else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(selectedUserType))
           {
        	StockholderStaffExt   stockholder=stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, selectAccount);
        	available=stockholder.getAvailableCreditLine();  
        	 memberStaffExt.setParentUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
        	 memberStaffExt.setParentStaff(stockholder.getID()); 
        	 //userCommissions = userCommissionLogic.queryCommission(stockholder.getID(),selectedUserType); 
        	 memberStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_STOCKHOLDER);
  		     memberStaffExt.setParentStaffQry(stockholder.getID());
  		     memberStaffExt.setStockholderStaff(stockholder.getID());
  		     memberStaffExt.setBranchStaff(stockholder.getBranchStaffExt().getID());
        	if (memberStaffExt.getTotalCreditLine() > available) {
   	           return "failure";
   	       }
   		   else
   		   {
   			        BeanUtils.copyProperties(memberStaffExt, membeStaExt);
   			        stockholder.setAvailableCreditLine(available-memberStaffExt.getTotalCreditLine());
   			         membeStaExt.setManagerStaff(stockholder);
	                
   		   }
           }
           else if(ManagerStaff.USER_TYPE_BRANCH.equals(selectedUserType))
           {
        	   BranchStaffExt  branch=branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, selectAccount);
        	   available=branch.getAvailableCreditLine();  
        	   memberStaffExt.setParentUserType(ManagerStaff.USER_TYPE_BRANCH);
        	   memberStaffExt.setParentStaff(branch.getID());
        	  // userCommissions = userCommissionLogic.queryCommission(branch.getID(),selectedUserType); 
        		 memberStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_BRANCH);
        		   memberStaffExt.setParentStaffQry(branch.getID());
        		   memberStaffExt.setBranchStaff(branch.getID());
               if (memberStaffExt.getTotalCreditLine() > available) {
       	           return "failure";
       	       }
       		   else
       		   {
       			     BeanUtils.copyProperties(memberStaffExt, membeStaExt);
       			     branch.setAvailableCreditLine(available-memberStaffExt.getTotalCreditLine());
       			     membeStaExt.setManagerStaff(branch);
 	                
       		   }
           }
    
    	   
       }
      Long  memberID=memberStaffExtLogic.saveUserMemberStaff(membeStaExt, userInfo.getID());
       
       this.setqUserID(Tools.encodeWithKey(memberID+"")); 
       
       
       
       
        if (isChief) {} else if (isBranch) {} else if (isStockholder) {} else if (isGenAgent) {}

        return SUCCESS;
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

    public List<String> getRateCount() {
        return rateCount;
    }

    public void setRateCount(List<String> rateCount) {
        this.rateCount = rateCount;
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

    public MemberStaffExt getMemberStaffExt() {
        return memberStaffExt;
    }

    public void setMemberStaffExt(MemberStaffExt memberStaffExt) {
        this.memberStaffExt = memberStaffExt;
    }

    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
    }

    public IUserCommissionDefault getUserCommissionDefaultLogic() {
        return userCommissionDefaultLogic;
    }

    public void setUserCommissionDefaultLogic(
            IUserCommissionDefault userCommissionDefaultLogic) {
        this.userCommissionDefaultLogic = userCommissionDefaultLogic;
    }

    public List<UserCommissionDefault> getCommissionDefault() {
        return commissionDefault;
    }

    public void setCommissionDefault(
            List<UserCommissionDefault> commissionDefault) {
        this.commissionDefault = commissionDefault;
    }

    public List<UserCommission> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommission> commissionsList) {
        this.commissionsList = commissionsList;
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

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}

	public String getSearchUpper() {
		return searchUpper;
	}

	public void setSearchUpper(String searchUpper) {
		this.searchUpper = searchUpper;
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
    public static void main(String[] args) {
		double a=97.3d;
	BigDecimal	big= new BigDecimal(100d-a).setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println(big.doubleValue());
		
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

	public OnlineMemberService getOnlineMemberService() {
		return onlineMemberService;
	}

	public void setOnlineMemberService(OnlineMemberService onlineMemberService) {
		this.onlineMemberService = onlineMemberService;
	}
}
