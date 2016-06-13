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
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
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

public class UserGenAgentStaffExt extends BaseUserAction {

    /**
     * 
     */
    Logger logger = Logger.getLogger(UserGenAgentStaffExt.class);
    private static final long serialVersionUID = 8041465447915733613L;
    private String type = "userGenAgent";
    private List<UserCommission> commissions;

    private List<String> genAgentRateCount = new ArrayList<String>();
    private List<String> shareholderRateCount = new ArrayList<String>();
    private IUserCommissionLogic userCommissionLogic;
    private ManagerUser userInfo;
    private ChiefStaffExt chiefStaffExt;
    private BranchStaffExt branchStaffExt;
    private StockholderStaffExt stockholderStaffExt;
    private static final String ACCOUNT = "account";
    private GenAgentStaffExt genAgentStaffExt;
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

    private String searchAccount;
    private String parentAccount;


	/*
     * 分面查询全部总代理用户
     */
    public String queryGenAgentStaff() {
    	ManagerStaff    userInfo = getInfo();
/*        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
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
        
        
        Page<GenAgentStaffExt> page = new Page<GenAgentStaffExt>(20);
       /* List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_GEN_AGENT));*/
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = genAgentStaffExtLogic.findPage(page,userInfo, searchUserStatus, account, chName);
        this.getRequest().setAttribute("page", page);
        //add by peter 
        this.setSearchAccount(this.getRequest().getParameter("account"));
        return SUCCESS;
    }


    /**
     * 点注册时，数据初始化
     * 
     * @return
     */
    public String saveGenAgentStaff() {
        ManagerUser userInfo = getInfo();
        
      /*boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
*/      boolean isStock=userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股東类型
        boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB); 
        boolean isSubStock=false;
        StockholderStaffExt defaultStock=new StockholderStaffExt();
        
        String managerAccount=userInfo.getAccount();
        if(isSub){
        	SubAccountInfo subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        	 if(subAccountInfo!=null)
        	 {
               isSubStock = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType());// 股东
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
        List<ManagerUser>     selManagersDB= commonUserLogic.queryBelowManager(managerAccount, ManagerStaff.USER_TYPE_STOCKHOLDER); 
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
      	  defaultStock=stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, mu.getAccount()); 
        }
        if(stockholderStaffExt!=null&&stockholderStaffExt.getAccount()!=null)
        {
      	  defaultStock=stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, stockholderStaffExt.getAccount()); 
      	  
        }
        this.getRequest().setAttribute("selList", selManagers);
        this.getRequest().setAttribute("isStockOrSub", isStock||isSubStock);
        this.getRequest().setAttribute("selectStock", defaultStock);
        
        return SUCCESS;

    }

    /**
     * 选择用户的时候初始化数据到页面
     * 
     * @return
     */
    public String saveFindGenAgent() {
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
        stockholderStaffExt = stockholderStaffExtLogic
                .queryStockholderStaffExt(ACCOUNT,
                        getRequest().getParameter(ACCOUNT));
        this.getRequest().setAttribute("selaccount",
                getRequest().getParameter(ACCOUNT));
        if (stockholderStaffExt != null) {
            // 可用信用额度
            availableMoney = stockholderStaffExt.getTotalCreditLine();
            // 占成还剩多少
            percentCount +=  stockholderStaffExt.getShareholderRate();
            // 算出一共用了多少信用额度
            for (GenAgentStaffExt genAgentStaffExt : stockholderStaffExt
                    .getGenAgentStaffExtSet()) {
                totalMoney += genAgentStaffExt.getTotalCreditLine();
            }
            remainingMoery = availableMoney - totalMoney;// 还剩多少可用
            // 初始化占成
            for (int i = 5; i <= percentCount; i += 5) {
                genAgentRateCount.add(String.valueOf(i) + "%");
                shareholderRateCount.add(String.valueOf(i) + "%");
            }
            commissions = userCommissionLogic
                    .queryCommission(stockholderStaffExt.getID(),stockholderStaffExt.getUserType()); // 查询分公司退水
        }
        boolean isChief = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isSub = ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType());// 子账号
        boolean isSubChief = false;
        boolean isSubBranch = false;
        if(isSub){
            if(subAccountInfo !=null){
                isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 总监类型
                isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 分公司类型
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
                        + "中的方法saveGenAgentStaff时出现错误 "
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
                        + "中的方法saveGenAgentStaff时出现错误 "
                        + e.getMessage());
            }
        }
        getRequest().setAttribute("percentCount", percentCount);// 占成还剩多少
        getRequest().setAttribute("remainingMoery", remainingMoery);// 还剩多少可用
        getRequest().setAttribute("availableMoney", availableMoney);// 可用信用额度
        getRequest().setAttribute("typeUser", userInfo.getUserType());// 标记类型
        return SUCCESS;
    }

    /**
     * 总代理注册
     * genAgentStaffRegiste
     * @return
     */
    public String saveGenAgent() {
        
        StockholderStaffExt sdeSat = new StockholderStaffExt();
        sdeSat = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT,
                genAgentStaffExt.getStockholderStaffExt().getAccount());
        if (sdeSat == null) {
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>上级用户为空</font>");
            return "failure";
        }
        
       
        // 判断总信用额度不能超过剩下的信用额度
        if (genAgentStaffExt.getTotalCreditLine() > sdeSat.getAvailableCreditLine()) {
            getLoggerInfo("判断总信用额度不能超过剩下的信用额度");
            return "failure";
        }

        // 如果上级用户走飞为禁止，下级必须是禁止
        if (genAgentStaffExt.getReplenishment().equals(ManagerStaff.REPLENIS_USE)) {
            boolean isReplen = genAgentStaffExt.getReplenishment().equals(sdeSat.getReplenishment());
            if (!isReplen) {
                getLoggerInfo("如果上级用户走飞为禁止，下级必须是禁止");
                return "failure";
            }
        }
       /* GenAgentStaffExt staffExt = new GenAgentStaffExt();
        staffExt = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT,genAgentStaffExt.getAccount());
        if(staffExt!=null)
        {
        	getLoggerInfo("用戶 已經存在");
            return "failure";
        }*/
        genAgentStaffExt.setAvailableCreditLine(genAgentStaffExt.getTotalCreditLine());
        // 去后台判断用户是否存在
        
            ManagerUser  userInfo = getInfo();
            genAgentStaffExt.setCreateDate(new Date());
            genAgentStaffExt.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
            //MD5 md5 = new MD5();
            String userPwdOrignMd5 = genAgentStaffExt.getUserPwd().trim();
            genAgentStaffExt.setUserPwd(userPwdOrignMd5);
           
            StockholderStaffExt ext = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT,genAgentStaffExt.getStockholderStaffExt().getAccount());

            genAgentStaffExt.setChiefStaff(ext.getBranchStaffExt().getChiefStaffExt().getID());
            genAgentStaffExt.setBranchStaff(ext.getBranchStaffExt().getID());
            genAgentStaffExt.setStockholderStaffExt(ext);
            genAgentStaffExt.setParentStaffQry(sdeSat.getID());
            genAgentStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_STOCKHOLDER);
            genAgentStaffExt.setFlag("0");
            GenAgentStaffExt genAgenExt = new GenAgentStaffExt();
            BeanUtils.copyProperties(genAgentStaffExt, genAgenExt);
            int available = sdeSat.getAvailableCreditLine() - genAgentStaffExt.getTotalCreditLine();
            ext.setAvailableCreditLine(available);
            genAgenExt.setStockholderStaffExt(ext);
           // stockholderStaffExtLogic.updateStockholderStaffExt(ext);
            
            Long genAgentId=genAgentStaffExtLogic.saveUserGenAgentStaff(genAgenExt,userInfo.getID());           
            this.setqUserID(Tools.encodeWithKey(genAgentId+""));
            
            return SUCCESS;
 
    }

    private void getLoggerInfo(String info) {
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>'"+info+"'</font>");
    }

    /**
     * 跳转修改页面初始化数据
     * 
     * @return
     */
    public String updateFindByGenAgent() {
        String account = getRequest().getParameter(ACCOUNT).trim();   
        // 查出用户数据
        genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT, account);
        
      //int maxRate=commonUserLogic.queryBelowMaxRate(genAgentStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
      StockholderStaffExt stock=genAgentStaffExt.getStockholderStaffExt();
        BranchStaffExt branch= stock.getBranchStaffExt();
       
        
        int maxRestrictRate=100-branch.getChiefRate()-stock.getBranchRate();
        //int minRestrictRate=maxRate;
        //int userMaxRate=100-maxRate-branch.getChiefRate()-stock.getBranchRate();
		 //if(userMaxRate>maxRestrictRate)
		 //userMaxRate=maxRestrictRate;
        
        //this.getRequest().setAttribute("maxRate",userMaxRate );
        this.getRequest().setAttribute("maxRestrictRate", maxRestrictRate);
       // this.getRequest().setAttribute("minRestrictRate", minRestrictRate);
        
        //用户下面是否有投注
		// boolean hasBet=commonUserLogic.queryUserTreeHasBet(genAgentStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
		// this.getRequest().setAttribute("hasBet", hasBet);
        
        
        return SUCCESS;
    }

    /**
     * 修改用户信息
     * 
     * @return
     */
    public String updateGenAgent() {
    	
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
       
        GenAgentStaffExt genAStaffExt =  genAgentStaffExtLogic.queryGenAgentStaffExt(ACCOUNT,genAgentStaffExt.getAccount());
        // 获去页面占成是否大于100%占成+
        if (genAStaffExt != null) {
            
            int availableMoney =genAStaffExt.getStockholderStaffExt().getAvailableCreditLine();

            //判断总信用额度不能超过剩下的信用额度
           

            //genAgentStaffExt.setStockholderStaffExt(genAStaffExt.getStockholderStaffExt());
            //genAgentStaffExt.setBranchStaff(genAStaffExt.getStockholderStaffExt().getBranchStaffExt().getID());
            //genAgentStaffExt.setChiefStaff(genAStaffExt.getStockholderStaffExt().getBranchStaffExt().getChiefStaffExt().getID());
            

               
                if(StringUtils.isNotEmpty(genAgentStaffExt.getUserPwd())){   
                    //MD5 md5 = new MD5();
                    String userPwdOrignMd5 = genAgentStaffExt.getUserPwd().trim();
                    genAStaffExt.setUserPwd(userPwdOrignMd5);
                    //add by peter
                    genAStaffExt.setPasswordUpdateDate(new Date());
                    genAStaffExt.setPasswordResetFlag("Y");
                }
                int tempFlag = 0;
                tempFlag = genAgentStaffExt.getTotalCreditLine() - genAStaffExt.getTotalCreditLine();
                int newTotalCreditLine = genAgentStaffExt.getTotalCreditLine();
            	int orginalTotalCreditLine = genAStaffExt.getTotalCreditLine();
                /*if(availableMoney-tempFlag<0){
                    getFailuere("总信用额度不能超过剩下的信用额度");
                    return "failure";
                }*/
                
                
                if(tempFlag < 0){
                	Integer changedCred=genAgentStaffExt.getTotalCreditLine();
                	Integer userCred= genAStaffExt.getTotalCreditLine()- genAStaffExt.getAvailableCreditLine();
                	if(changedCred<userCred)
                	{
                		getFailuere("修改总代理 信用额度 小于 已经分配的值");
                		 return "failure";   
                	}
                	else
                	{
                		if(availableMoney-tempFlag<0){
                            getFailuere("修改後信用额度不能 大于 上級的可用信用额度");
                            return "failure";
                         }
                   	 else{
                   		genAgentStaffExt.setAvailableCreditLine(genAStaffExt.getAvailableCreditLine()+tempFlag);
                   	 }
                	}
                	/*
                    if(tempFlag+genAStaffExt.getAvailableCreditLine()<0){
                    	genAStaffExt.setAvailableCreditLine(genAgentStaffExt.getTotalCreditLine());
                    }else{
                    	genAStaffExt.setAvailableCreditLine(tempFlag+genAStaffExt.getAvailableCreditLine());
                    }
                */}else{
                	genAgentStaffExt.setAvailableCreditLine(tempFlag+genAStaffExt.getAvailableCreditLine());
                }
              //如果修改信用额度少于当前可用信用额度，下级所有清零
                /*if(genAgentStaffExt.getTotalCreditLine() < genAStaffExt.getAvailableCreditLine()){
                	
                	 commonUserLogic.clearBelowAvailableCreditLine(genAStaffExt.getAccount(), ManagerStaff.USER_TYPE_GEN_AGENT);
                	
                }*/
                
             // 佔成修改限制
                StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
                BranchStaffExt branch=stock.getBranchStaffExt();
                
                
                if(genAgentStaffExt.getShareholderRate()!=null)
                {
                	
                	int maxRate=commonUserLogic.queryBelowMaxRate(genAStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
                	if(genAgentStaffExt.getShareholderRate()>100-maxRate-stock.getBranchRate()-branch.getChiefRate())
                	
                	{
                		getFailuere("超過總代理 可以修改的最大值！！");
            		 return "failure";
            		 }   
                }
                          
                    genAStaffExt.getStockholderStaffExt().setAvailableCreditLine(availableMoney-tempFlag);//上级的可用信用额度
                    
                    if(genAgentStaffExt.getRateRestrict()!=null)
                    {
                    genAStaffExt.setRateRestrict(genAgentStaffExt.getRateRestrict());
                    }
                    genAStaffExt.setChsName(genAgentStaffExt.getChsName());
                    if(genAgentStaffExt.getShareholderRate()!=null)
                    {
                    genAStaffExt.setShareholderRate(genAgentStaffExt.getShareholderRate());
                    }
                    genAStaffExt.setTotalCreditLine(genAgentStaffExt.getTotalCreditLine());
                    if(genAgentStaffExt.getReplenishment()!=null)
                    {
                    genAStaffExt.setReplenishment(genAgentStaffExt.getReplenishment());
                    }
                    genAStaffExt.setAvailableCreditLine(genAgentStaffExt.getAvailableCreditLine());
                    if(genAgentStaffExt.getBelowRateLimit()!=null)
                    {
                    genAStaffExt.setBelowRateLimit(genAgentStaffExt.getBelowRateLimit());
                    }
                    if("0".equals(genAgentStaffExt.getRateRestrict()))
                    {
                    	 genAStaffExt.setBelowRateLimit(null);
                    }
                    genAStaffExt.setFlag(genAgentStaffExt.getFlag());
                    
                    genAgentStaffExtLogic.updateUserGenAgentStaff(genAStaffExt,userInfoNew.getUserType());
                    //genAgentStaffExtLogic.updateGenAgentStaffExt(genAStaffExt);
                    //stockholderStaffExtLogic.updateStockholderStaffExt(genAStaffExt.getStockholderStaffExt());
                    //如果信用额度由修改，增加修改信息到日志表 add by peter
                    if(tempFlag!=0){
                    	ReplenishAutoSetLog changeLog = this.setChangeLog(this.getCurrentManagerUser(), "TOTAL_CREDITLINE", String.valueOf(orginalTotalCreditLine), String.valueOf(newTotalCreditLine),genAStaffExt);
                    	replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
                    }
                    
                  //处理主帐号禁止补货状态时，把下属的子帐号全部投为禁止补货
                    if(Constant.CLOSE.equals(genAgentStaffExt.getReplenishment())){
                    	ManagerUser cUserInfo = new ManagerUser();
                    	cUserInfo.setAccount(genAgentStaffExt.getAccount());
                    	cUserInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
                    	subAccountInfoLogic.handleReplenishmentByParent(cUserInfo);
                    	
                    	//把该用户的自动补货设置改为关闭
                    	
                    	String shopId = info.getShopsInfo().getID()+""; 
                    	replenishAutoLogic.updateReplenishAutoSetByUser(shopId, genAStaffExt.getManagerStaffID(), 
                    			genAStaffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
                    	replenishAutoLogic.updateReplenishAutoSetForClose(shopId, genAStaffExt.getManagerStaffID(), genAStaffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
                    }

                return SUCCESS;
        }
        getFailuere("修改失败");
        return "failure";
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

    public List<String> getGenAgentRateCount() {
        return genAgentRateCount;
    }

    public void setGenAgentRateCount(List<String> genAgentRateCount) {
        this.genAgentRateCount = genAgentRateCount;
    }

    public List<String> getShareholderRateCount() {
        return shareholderRateCount;
    }

    public void setShareholderRateCount(List<String> shareholderRateCount) {
        this.shareholderRateCount = shareholderRateCount;
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

    public IAgentStaffExtLogic getAgentStaffExtLogic() {
        return agentStaffExtLogic;
    }

    public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
        this.agentStaffExtLogic = agentStaffExtLogic;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }

    public IChiefStaffExtLogic getChiefStaffExtLogic() {
        return chiefStaffExtLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
    }

    public ChiefStaffExt getChiefStaffExt() {
        return chiefStaffExt;
    }

    public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
        this.chiefStaffExt = chiefStaffExt;
    }

    public BranchStaffExt getBranchStaffExt() {
        return branchStaffExt;
    }

    public void setBranchStaffExt(BranchStaffExt branchStaffExt) {
        this.branchStaffExt = branchStaffExt;
    }

    public StockholderStaffExt getStockholderStaffExt() {
        return stockholderStaffExt;
    }

    public void setStockholderStaffExt(StockholderStaffExt stockholderStaffExt) {
        this.stockholderStaffExt = stockholderStaffExt;
    }

    public GenAgentStaffExt getGenAgentStaffExt() {
        return genAgentStaffExt;
    }

    public void setGenAgentStaffExt(GenAgentStaffExt genAgentStaffExt) {
        this.genAgentStaffExt = genAgentStaffExt;
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
