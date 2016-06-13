package com.npc.lottery.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
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

public class UserStockholderAction extends BaseUserAction {

    private static final String ACCOUNT = "account";
    /**
     * 
     */
    private static final long serialVersionUID = 5890825215283262181L;
    private Logger logger = Logger.getLogger(UserStockholderAction.class);
    private ChiefStaffExt chiefStaffExt;
    private BranchStaffExt branchStaffExt;
    private List<String> branchRateCount = new ArrayList<String>();
    private List<String> shareholderRateCount = new ArrayList<String>();
    private StockholderStaffExt stockholderStaffExt;
    private List<UserCommission> commissions;
    private IUserCommissionLogic userCommissionLogic;
    private String type = "userStockholder";
    private ManagerUser userInfo;
    private List<UserCommission> commissionsList;// 上级退水
    private int alreadyCreditLine;
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
    
    /**
     * 注册页面数据初始化
     * 
     * @return
     */
    public String saveStockholder() {   
            ManagerUser userInfo = getInfo();    
            boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
            boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB); 
            boolean isSubChief = false;
            boolean isSubBranch = false;
            BranchStaffExt defaultBranch=new BranchStaffExt();
            SubAccountInfo subAccountInfo = new SubAccountInfo();
            if(isSub){
            	subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
                if(subAccountInfo !=null){
                    isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 总监类型
                    isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 分公司类型
               }
            } 
                 
            if (isChief || isSubChief) {
             // 查询总监
               
                    if(isChief){
                        chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT,
                                userInfo.getAccount()); 
                    }else if(isSubChief){
                        chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("managerStaffID",subAccountInfo.getParentStaff()); 
                    }
                  
                 
                    //add by peter 把获得的上级管理列表过滤，只留下启动的管理对象
                    Set<BranchStaffExt> selManagersDB = chiefStaffExt.getBranchStaffExtSet();
                    Set<BranchStaffExt> selManagers = new HashSet<BranchStaffExt>();
                    if (selManagersDB != null && selManagersDB.size() > 0) {
                  	  for (BranchStaffExt managerUser : selManagersDB) {
                  		  if ("0".equals(managerUser.getFlag())) {
                  			  selManagers.add(managerUser);
                  		  }
                  	  }
                    }
                    
	                  //add by peter
	              	  String parentAccount = this.getRequest().getParameter("parentAccount");
	              	  if(StringUtils.isNotEmpty(parentAccount)){
	              		  for(BranchStaffExt branch:selManagers){
	              			  if(parentAccount.equals(branch.getAccount())){
	              				selManagers = new HashSet<BranchStaffExt>();
	              				selManagers.add(branch);
	              				  break;
	              			  }
	              		  }
	              	  }
                    //把过滤后的列表重新设置入内存
                    chiefStaffExt.setBranchStaffExtSet(selManagers);
                  if(branchStaffExt!=null&&branchStaffExt.getAccount()!=null)
                  {
                	  defaultBranch=branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, branchStaffExt.getAccount()); 
                	  
                  }                	  
                  else if(selManagers!=null&&selManagers.size()>0){
                	  List<BranchStaffExt>  branchList=Lists.newArrayList(selManagers);
                      defaultBranch=branchList.get(0);
                  }
                  
                  this.getRequest().setAttribute("ischiefOrchiefSub", true);
                 
                
              
            } else if (isBranch || isSubBranch) {
            	 this.getRequest().setAttribute("isbranchOrbranchSub", true);
                
                //int moneyCredit = 0; // 信用额度
                BranchStaffExt  brach=new BranchStaffExt();       
                    if(isBranch){
                    	brach = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, userInfo.getAccount()); 
                    }else if(isSubBranch){
                    	brach = branchStaffExtLogic.queryBranchStaffExt("managerStaffID",subAccountInfo.getParentStaff());
                    }
                    defaultBranch=brach;
            }
            this.getRequest().setAttribute("selectBranch", defaultBranch);
        return SUCCESS;
    }


    /*
     * 选着用户之后，初始化数据
     */
/*    public String saveFindStockholder() {
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
        int remainingMoery = 0;//还剩多少信用额度
        boolean isChief = false;
        if(subAccountInfo !=null){
            isChief = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());
        }
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
            chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT,
                    userInfo.getAccount());
        }else if(isChief){
            chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("managerStaffID",subAccountInfo.getParentStaff());
        }
        
        branchStaffExt = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT,
                getRequest().getParameter(ACCOUNT));
        this.getRequest().setAttribute("selaccount",
                getRequest().getParameter(ACCOUNT));
        if (branchStaffExt != null) {
            availableMoney = branchStaffExt.getTotalCreditLine();// 可用信用额度
            percentCount += branchStaffExt.getCompanyRate();// 占成还剩多少
            // 算出一共用了多少信用额度
            for (StockholderStaffExt staffExt : branchStaffExt
                    .getStockholderStaffExtSet()) {
                totalMoney += staffExt.getTotalCreditLine();
            }
            remainingMoery = availableMoney - totalMoney;// 还剩多少可用
            // 初始化占成
            for (int i = 5; i <= percentCount; i += 5) {
                branchRateCount.add(String.valueOf(i) + "%");
                shareholderRateCount.add(String.valueOf(i) + "%");
            }

        }
        commissions = userCommissionLogic.queryCommission(branchStaffExt
                .getID(),branchStaffExt.getUserType()); // 查询分公司退水
        getRequest().setAttribute("percentCount", percentCount);// 占成还剩多少
        getRequest().setAttribute("remainingMoery", remainingMoery);// 还剩多少可用
        getRequest().setAttribute("availableMoney", availableMoney);// 可用信用额度
        getRequest().setAttribute("typeUser", userInfo.getUserType());// 标记类型
        return SUCCESS;
    }
*/
    /*
     * 分面查询全部股东用户
     */
    public String queryStockholder() {
    	
    	ManagerStaff userInfo = getInfo();
    	String branchAccount=this.getRequest().getParameter("account");
    	if(branchAccount!=null)
    	{
    		//searchUserStatus=null;
    	 ManagerStaff requestUserInfo=branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, branchAccount);
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
    	
  /*      subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));*/
        
        
        
        Page<StockholderStaffExt> page = new Page<StockholderStaffExt>(20);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_STOCKHOLDER));
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = stockholderStaffExtLogic.findPage(page,userInfo, searchUserStatus, account, chName);
        this.getRequest().setAttribute("page", page);
        
        //add by peter 
        this.setSearchAccount(this.getRequest().getParameter("account"));
        return SUCCESS;
    }

    /**
     * 注册用户
     * stockholderRegister
     * @return
     */
    public String saveStock() {
       
        BranchStaffExt bahStEt = new BranchStaffExt();
        ManagerUser userInfo=this.getInfo();
        bahStEt = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT,stockholderStaffExt.getBranchStaffExt().getAccount());
        if (bahStEt == null) {
            getFailuere("上级用户不存在");
            return "failure";
        }
        StockholderStaffExt staffExt  = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT,stockholderStaffExt.getAccount());
        if(staffExt!=null)
        {
        	 getFailuere("股東用户已經存在");
             return "failure";
        }
        int availableMoney = bahStEt.getAvailableCreditLine();// 可用信用额度
        
        //判断总信用额度不能超过剩下的信用额度
        if(stockholderStaffExt.getTotalCreditLine() > availableMoney){
            getFailuere("股東总信用额度不能超过分公司可用信用额度！");
            return "failure";
        }
        //如果上级用户为禁止，下级必须是禁止
        if(ManagerStaff.FLAG_FORBID.equals(bahStEt.getFlag())||ManagerStaff.FLAG_FREEZE.equals(bahStEt.getFlag())){
           
                getFailuere("如果上级用户为禁止 或凍結，不能創建下級！");
                return "failure";
        }
      //如果上级用户走飞为禁止，下级必须是禁止
        if(ManagerStaff.REPLENIS_FORBID.equals(bahStEt.getReplenishment())){
          if(!ManagerStaff.REPLENIS_FORBID.equals(stockholderStaffExt.getReplenishment()))
               getFailuere("如果上级用户走飞为禁止，下级必须是禁止走飞！");
               return "failure";
        }
        //MD5 md5 = new MD5();
        String userPwdOrignMd5 = stockholderStaffExt.getUserPwd().trim();
        stockholderStaffExt.setAvailableCreditLine(stockholderStaffExt.getTotalCreditLine());
        stockholderStaffExt.setCreateDate(new Date());
        stockholderStaffExt.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);         
        stockholderStaffExt.setUserPwd(userPwdOrignMd5);
        stockholderStaffExt.setChiefStaff(bahStEt.getChiefStaffExt().getID());
        stockholderStaffExt.setParentStaffQry(bahStEt.getID())  ;
        stockholderStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_BRANCH);
       
        //stockholderStaffExt.setBranchStaffExt(bahStEt);
        stockholderStaffExt.setFlag("0");
        StockholderStaffExt stockholder = new StockholderStaffExt();
        BeanUtils.copyProperties(stockholderStaffExt, stockholder); // Copy对像属性
        int available =  availableMoney-stockholderStaffExt.getTotalCreditLine();
        bahStEt.setAvailableCreditLine(available); 
        stockholder.setBranchStaffExt(bahStEt);
        Long stockId=stockholderStaffExtLogic.saveUserStockholderStaff(stockholder, userInfo.getID());
            this.setqUserID(Tools.encodeWithKey(stockId+""));
            return SUCCESS;

    }



	/**
	 * 修改页面
	 * 
	 * @return
	 */
	public String updateFindByStockholder() {
		String account = getRequest().getParameter(ACCOUNT).trim();
		// ManagerUser userInfo = getInfo();
		stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt(ACCOUNT, account);
		// int maxRate=commonUserLogic.queryBelowMaxRate(stockholderStaffExt.getID(), ManagerStaff.USER_TYPE_STOCKHOLDER);

		this.getRequest().setAttribute("replenishment", stockholderStaffExt.getBranchStaffExt().getReplenishment());// 判断上级有没有被禁用补货，禁了后，界面的补货开关RADIO灰化

		int maxRestrictRate = 100 - stockholderStaffExt.getBranchStaffExt().getChiefRate();
		// int minRestrictRate=maxRate;
		// int userMaxRate=100-maxRate-stockholderStaffExt.getBranchStaffExt().getChiefRate();
		/*
		 * if(userMaxRate>maxRestrictRate) userMaxRate=maxRestrictRate;
		 */
		// this.getRequest().setAttribute("maxRate", userMaxRate);
		this.getRequest().setAttribute("maxRestrictRate", maxRestrictRate);
		// this.getRequest().setAttribute("minRestrictRate", minRestrictRate);

		// 用户下面是否有投注
		// boolean hasBet=commonUserLogic.queryUserTreeHasBet(stockholderStaffExt.getID(), ManagerStaff.USER_TYPE_STOCKHOLDER);
		// this.getRequest().setAttribute("hasBet", hasBet);
		// 占成修改时间限制
		int rateFlag = Tools.getMark();
		getRequest().setAttribute("rateFlag", rateFlag);

		return SUCCESS;

	}

    /**
     * 提交修改数据
     * 
     * @return
     */
    public String updateByStockholder() {
    	
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

        StockholderStaffExt staffExt =  stockholderStaffExtLogic.queryStockholderStaffExt("account",stockholderStaffExt.getAccount());
        if(staffExt==null)
        	return "failure";
        BranchStaffExt branchExt=staffExt.getBranchStaffExt();
        Integer branchAvaliable=branchExt.getAvailableCreditLine();
        
               
                int tempFlag =stockholderStaffExt.getTotalCreditLine() - staffExt.getTotalCreditLine();
                int newTotalCreditLine = stockholderStaffExt.getTotalCreditLine();
            	int orginalTotalCreditLine = staffExt.getTotalCreditLine();
                //修改信用额度
               
                if(tempFlag < 0){
                	Integer changedCred=stockholderStaffExt.getTotalCreditLine();
                	Integer userCred= staffExt.getTotalCreditLine()- staffExt.getAvailableCreditLine();
                	if(changedCred<userCred)
                	{
                		getFailuere("修改股东 信用额度 小于 已经分配的值");
                		 return "failure";   
                	}
                	else
                	{
                		stockholderStaffExt.setAvailableCreditLine(tempFlag+staffExt.getAvailableCreditLine());
                		
                	}
                	}else{
                	 if(branchAvaliable-tempFlag<0){
                         getFailuere("修改後信用额度不能 大于 上級的可用信用额度");
                         return "failure";
                      }
                	 else
                	 {
                		 stockholderStaffExt.setAvailableCreditLine(tempFlag+staffExt.getAvailableCreditLine());
                	 }
                }
             // 佔成修改限制
                
                if(stockholderStaffExt.getBranchRate()!=null)
                {
                	int maxRate=commonUserLogic.queryBelowMaxRate(staffExt.getID(), ManagerStaff.USER_TYPE_STOCKHOLDER);
                	if(stockholderStaffExt.getBranchRate()>(100-maxRate-staffExt.getBranchStaffExt().getChiefRate()))
                	{
                	 getFailuere("超過股東 可以修改的最大值！！");
            		 return "failure";
                	}
                }
                
              
                                   
                //stockholderStaffExt.setChiefStaff(staffExt.getBranchStaffExt().getChiefStaffExt().getID());
                if(!StringUtils.isEmpty(stockholderStaffExt.getUserPwd())){
                	
                   // MD5 md5 = new MD5();
                    String userPwdOrignMd5 = stockholderStaffExt.getUserPwd().trim();
                    staffExt.setUserPwd(userPwdOrignMd5);
                    //add by peter add update password date field
                    staffExt.setPasswordUpdateDate(new Date());
                    staffExt.setPasswordResetFlag("Y");
                }
                staffExt.setUpdateDate(new Date());
                staffExt.setAvailableCreditLine(stockholderStaffExt.getAvailableCreditLine());
                staffExt.setTotalCreditLine(stockholderStaffExt.getTotalCreditLine());
                if(stockholderStaffExt.getBelowRateLimit()!=null)
                {
                staffExt.setBelowRateLimit(stockholderStaffExt.getBelowRateLimit());
                }
                if(stockholderStaffExt.getRateRestrict()!=null)
                {
                staffExt.setRateRestrict(stockholderStaffExt.getRateRestrict());
                }
                if("0".equals(stockholderStaffExt.getRateRestrict()))
                {
                	staffExt.setBelowRateLimit(null);
                }
                staffExt.setChsName(stockholderStaffExt.getChsName());
                if(stockholderStaffExt.getBranchRate()!=null)
                {
                staffExt.setBranchRate(stockholderStaffExt.getBranchRate());
                }
                if(stockholderStaffExt.getReplenishment()!=null)
                {
                staffExt.setReplenishment(stockholderStaffExt.getReplenishment());
                }
                branchExt.setAvailableCreditLine(branchAvaliable-tempFlag);    
                staffExt.setBranchStaffExt(branchExt);
                //stockholderStaffExtLogic.updateStockholderStaffExt(staffExt);
               // branchStaffExtLogic.updateBranchStaffExt(branchExt);
                staffExt.setFlag(stockholderStaffExt.getFlag());
                stockholderStaffExtLogic.updateUserStockholderStaff(staffExt,userInfoNew.getUserType());
                
                //如果信用额度由修改，增加修改信息到日志表 add by peter
                if(tempFlag!=0){
                	ReplenishAutoSetLog changeLog = this.setChangeLog(this.getCurrentManagerUser(), "TOTAL_CREDITLINE", String.valueOf(orginalTotalCreditLine), String.valueOf(newTotalCreditLine),staffExt);
                	replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
                }
                
                //处理主帐号禁止补货状态时，把下属的子帐号全部投为禁止补货
                if(Constant.CLOSE.equals(stockholderStaffExt.getReplenishment())){
                	ManagerUser cUserInfo = new ManagerUser();
                	cUserInfo.setAccount(stockholderStaffExt.getAccount());
                	cUserInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
                	subAccountInfoLogic.handleReplenishmentByParent(cUserInfo);
                	
                	//把该用户的自动补货设置改为关闭
                	
                	String shopId = info.getShopsInfo().getID()+""; 
                	replenishAutoLogic.updateReplenishAutoSetByUser(shopId, staffExt.getManagerStaffID(), 
                			staffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
                	replenishAutoLogic.updateReplenishAutoSetForClose(shopId, staffExt.getManagerStaffID(), staffExt.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
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

    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
        return stockholderStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
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

    public List<String> getBranchRateCount() {
        return branchRateCount;
    }

    public void setBranchRateCount(List<String> branchRateCount) {
        this.branchRateCount = branchRateCount;
    }

    public List<String> getShareholderRateCount() {
        return shareholderRateCount;
    }

    public void setShareholderRateCount(List<String> shareholderRateCount) {
        this.shareholderRateCount = shareholderRateCount;
    }

    public StockholderStaffExt getStockholderStaffExt() {
        return stockholderStaffExt;
    }

    public void setStockholderStaffExt(StockholderStaffExt stockholderStaffExt) {
        this.stockholderStaffExt = stockholderStaffExt;
    }

    public List<UserCommission> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<UserCommission> commissions) {
        this.commissions = commissions;
    }

    public IUserCommissionLogic getUserCommissionLogic() {
        return userCommissionLogic;
    }

    public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
        this.userCommissionLogic = userCommissionLogic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
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

    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
    }

    public List<UserCommission> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommission> commissionsList) {
        this.commissionsList = commissionsList;
    }

    public int getAlreadyCreditLine() {
        return alreadyCreditLine;
    }

    public void setAlreadyCreditLine(int alreadyCreditLine) {
        this.alreadyCreditLine = alreadyCreditLine;
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
