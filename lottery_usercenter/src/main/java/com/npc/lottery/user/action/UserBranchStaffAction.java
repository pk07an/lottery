package com.npc.lottery.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Tools;

public class UserBranchStaffAction extends BaseUserAction {

    private static final String ACCOUNT = "account";
    /**
     * 
     */
    private static final long serialVersionUID = -5884538290747047234L;
    private Logger logger = Logger.getLogger(UserBranchStaffAction.class);

    private ChiefStaffExt chiefStaffExt;
    private BranchStaffExt branchStaffExt;
    private List<BranchStaffExt> branchStaffExts;
    private List<String> companyRateCount = new ArrayList<String>();// 分公司占成
    private List<UserCommissionDefault> commissionsList;// 默认退水
    private List<UserCommission> commissions;
    private IUserCommissionDefault userCommissionDefaultLogic;
    private IUserCommissionLogic userCommissionLogic;
    private String type = "userBranch";

    private ManagerUser userInfo;
    private List<String> rateCount = new ArrayList<String>();// 占成初始化
    private MemberStaffExt memberStaffExt;
    
    private int alreadyCreditLine;
    
    private SubAccountInfo subAccountInfo;
    private String searchUserStatus="0";
    private String  searchValue=null;
    private String  searchType=null;
    private String qUserID;

   
    /**
     * 
     * 創建分公司入口页面
     * @return
     */
    public String saveBranchStaff() {

        return SUCCESS;
    }
    /**
     * 修改分公司入口页面
     * @return
     */
    public String updateFindByBranchStaff() {

        String account = getRequest().getParameter(ACCOUNT).trim();
        
        branchStaffExt = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT,account);
		// int maxRate=commonUserLogic.queryBelowMaxRate(branchStaffExt.getID(), ManagerStaff.USER_TYPE_BRANCH);
		// this.getRequest().setAttribute("maxRate", 100-maxRate);
		// //用户下面是否有投注
		// boolean hasBet=commonUserLogic.queryUserTreeHasBet(branchStaffExt.getID(), ManagerStaff.USER_TYPE_BRANCH);
		// this.getRequest().setAttribute("hasBet", hasBet);
        
        
   /*   //分公司已用信用额度
        for (StockholderStaffExt stckldStaExt : branchStaffExt.getStockholderStaffExtSet()) {
            alreadyCreditLine +=stckldStaExt.getTotalCreditLine();
        }*/

        return SUCCESS;

    }

    /**
     * 创建分公司提交
     * branchStaffRegister
     * @return
     */
    public String saveBranch() {
    	ManagerStaff userInfo = getInfo();
    	
    	boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);
  
       String account = branchStaffExt.getAccount();
        
       int chiefRate = branchStaffExt.getChiefRate();
       if(!GenericValidator.isInRange(Integer.valueOf(chiefRate), 0, 100))
       { 
    	   logger.error("非法的总监占成");
    	   return "failure";
       }
       if(GenericValidator.isBlankOrNull(account))
       {
    	   logger.error("分公司帳號不能為空");
    	   return "failure";
       }
       if(GenericValidator.isBlankOrNull(branchStaffExt.getUserPwd()))
       {
    	   logger.error("密碼不能為空");
    	   return "failure";
    	   
       }
        BranchStaffExt staffExt =  branchStaffExtLogic.queryBranchStaffExt(ACCOUNT, account); // 判断是否存在相同用户信息
        if(staffExt!=null)
        {
        	 logger.error("用户账号已经存在");
        	return "failure";
        }
        ChiefStaffExt chiefExt =null;
      	if(isSub)
    	{
      		chiefExt=(ChiefStaffExt)getBackManagerUser(userInfo);
    		
    	}
      	else
      		chiefExt = chiefStaffExtLogic.queryChiefStaffExt(ACCOUNT,userInfo.getAccount());
    	if(chiefExt==null)
    	{
    		logger.error("非法的用戶操作！！！");
     	   return "failure";
    	}
        
            //开始创建分公司
            branchStaffExt.setCreateDate(new Date());
            branchStaffExt.setUserType(ManagerStaff.USER_TYPE_BRANCH);
            branchStaffExt.setAvailableCreditLine(branchStaffExt.getTotalCreditLine());
            branchStaffExt.setChiefStaffExt(chiefExt);
            //MD5 md5 = new MD5();// 加密
            String userPwdOrignMd5 = branchStaffExt.getUserPwd().trim();
            branchStaffExt.setUserPwd(userPwdOrignMd5);
            branchStaffExt.setFlag("0");
            branchStaffExt.setParentStaffQry(chiefExt.getID());
            branchStaffExt.setParentStaffTypeQry(ManagerStaff.USER_TYPE_CHIEF);
          //创建分公司结束
            
            BranchStaffExt ext = new BranchStaffExt();
            BeanUtils.copyProperties(branchStaffExt, ext); // Copy对像属性
            Long branchId=branchStaffExtLogic.saveUserBranchStaff(ext, chiefExt.getID(), userInfo.getID());
            this.setqUserID(Tools.encodeWithKey(branchId+""));//跳轉到用戶退水頁面需要用到
        return SUCCESS;
    }
    
    
    
    /**
     * 查询所有的分公司并分页
     * 
     * @return
     */
    public String queryBranchStaff() {
    	 ManagerUser  userInfo = getInfo();
    /*     subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
         Map<String,String> autoSubMap = new HashMap<String, String>();
         autoSubMap = getAutoSub(subAccountInfo);
         getRequest().setAttribute("subAccountInfo",subAccountInfo);
         getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
         getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
         getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
         getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
         getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));*/
    	 //String userStatus=this.getRequest().getParameter("searchUserStatus");
    	 //String searchValue=this.getRequest().getParameter("searchValue");
    	 //String searchType=this.getRequest().getParameter("searchType");
    	 String account=null;
    	 String chName=null;
    	 if("account".equals(searchType)&&!GenericValidator.isBlankOrNull(searchValue))
    	 {
    		 account= searchValue;
    	 }else if("chName".equals(searchType)&&!GenericValidator.isBlankOrNull(searchValue))
    	 {
    		 chName =searchValue;
    	 }
               
        Page<BranchStaffExt> page = new Page<BranchStaffExt>(20);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_BRANCH));
        userInfo.getFlag();
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = branchStaffExtLogic.findPage(page,userInfo, searchUserStatus, account, chName);
        this.getRequest().setAttribute("page", page);
        return SUCCESS;
    }



    /**修改分公司提交
     * 
     * @return
     */
    public String updateByBranchStaff() {
    	
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
    	

        BranchStaffExt dbBranch =  branchStaffExtLogic.queryBranchStaffExt(ACCOUNT,branchStaffExt.getAccount()); // 判断是否存在相同用户信息
        if(dbBranch==null)
        {
        	logger.error("非法操作不存在的分公司");
            return "failure";    
        }
            int tempFlag = 0;
            tempFlag = branchStaffExt.getTotalCreditLine() - dbBranch.getTotalCreditLine();
            int newTotalCreditLine = branchStaffExt.getTotalCreditLine();
        	int orginalTotalCreditLine = dbBranch.getTotalCreditLine();
            //信用额度修改变小
            if(tempFlag < 0){
            	Integer changedCred=branchStaffExt.getTotalCreditLine();
            	Integer userCred= dbBranch.getTotalCreditLine()- dbBranch.getAvailableCreditLine();
            	if(changedCred<userCred)
            	{
            		 getFailuere("修改分公司 信用额度 小于 已经分配的值");
            		 return "failure";   
            	}
            	else
            	{
            		branchStaffExt.setAvailableCreditLine(dbBranch.getAvailableCreditLine()+tempFlag);
            	}
            	}else{
            	//信用額度修改變大 不能大于上级的 可用信用額度（分公司类外，总监的可用信用额度是无穷的：））
            	
            	branchStaffExt.setAvailableCreditLine(dbBranch.getAvailableCreditLine()+tempFlag);
            }         
            
            Integer chiefRate=branchStaffExt.getChiefRate();
            // 佔成修改限制
           
            if(chiefRate!=null)
            {
            	 int maxRate=commonUserLogic.queryBelowMaxRate(dbBranch.getID(), ManagerStaff.USER_TYPE_BRANCH);
            	 if(chiefRate>100-maxRate)
            	 {
            	 getFailuere("超過分公司 可以修改的最大值！！");
        		 return "failure";
            	 }
            }
            
            //开始更新分公司本身信息

            	dbBranch.setUpdateDate(new Date());
                if(!StringUtils.isEmpty(branchStaffExt.getUserPwd())){
                	 //MD5 md5 = new MD5();
                     String userPwdOrignMd5 = branchStaffExt.getUserPwd().trim();
                     branchStaffExt.setUserPwd(userPwdOrignMd5);
                     dbBranch.setUserPwd(branchStaffExt.getUserPwd());  
                    
                    //add by peter set the value of new field passwordUpdateDate
                 	dbBranch.setPasswordUpdateDate(new Date());
                 	dbBranch.setPasswordResetFlag("Y");
                }          
               
                dbBranch.setAvailableCreditLine(branchStaffExt.getAvailableCreditLine());
                dbBranch.setTotalCreditLine(branchStaffExt.getTotalCreditLine());
                if(branchStaffExt.getReplenishment()!=null)
                {
            	dbBranch.setReplenishment(branchStaffExt.getReplenishment());
                }
            	dbBranch.setFlag(branchStaffExt.getFlag());
            	dbBranch.setChsName(branchStaffExt.getChsName());
            	if(branchStaffExt.getLeftOwner()!=null)
            	{
            	dbBranch.setLeftOwner(branchStaffExt.getLeftOwner());
            	}
            	dbBranch.setOpenReport(branchStaffExt.getOpenReport());
            	if(branchStaffExt.getChiefRate()!=null)
            	{
            	dbBranch.setChiefRate(branchStaffExt.getChiefRate());
            	}
            	dbBranch.setFlag(branchStaffExt.getFlag());
            	
                branchStaffExtLogic.updateUserBranchStaff(dbBranch,userInfoNew.getUserType());
                //如果信用额度由修改，增加修改信息到日志表 add by peter
                if(tempFlag!=0){
                	ReplenishAutoSetLog changeLog = this.setChangeLog(this.getCurrentManagerUser(), "TOTAL_CREDITLINE",  String.valueOf(orginalTotalCreditLine), String.valueOf(newTotalCreditLine),dbBranch);
                	replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
                }
                
                
                //处理主帐号禁止补货状态时，把下属的子帐号全部投为禁止补货
                if(Constant.CLOSE.equals(branchStaffExt.getReplenishment())){
                	ManagerUser cUserInfo = new ManagerUser();
                	cUserInfo.setAccount(branchStaffExt.getAccount());
                	cUserInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
                	subAccountInfoLogic.handleReplenishmentByParent(cUserInfo);
                	//把该用户的自动补货设置改为关闭
                	
                	String shopId = info.getShopsInfo().getID()+""; 
                	replenishAutoLogic.updateReplenishAutoSetByUser(shopId, dbBranch.getManagerStaffID(), 
                			dbBranch.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
                	replenishAutoLogic.updateReplenishAutoSetForClose(shopId, dbBranch.getManagerStaffID(), dbBranch.getUserType(), Constant.NO_ALOW_AUTO_REPLENISH);
                }
            
            return SUCCESS;
     
       
    }
    
    
    
    public String savaFindBranchMember(){
        userInfo = getInfo();
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        boolean isChief = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        if (!isSys || !isManager || !isChief)// 总管和系统管理员一般不创建用户
        {
            boolean isBranch = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            if (isBranch) {// 总监进来
                branchStaffExt = branchStaffExtLogic.queryBranchStaffExt(
                        ACCOUNT, userInfo.getAccount());
                commissions = userCommissionLogic.queryCommission(branchStaffExt
                        .getID(),branchStaffExt.getUserType());
                // 初始化占成
                for (int i = 5; i <= 100; i += 5) {
                    rateCount.add(String.valueOf(i) + "%");
                }
            }
        }
        return SUCCESS;
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

    public List<BranchStaffExt> getBranchStaffExts() {
        return branchStaffExts;
    }

    public void setBranchStaffExts(List<BranchStaffExt> branchStaffExts) {
        this.branchStaffExts = branchStaffExts;
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

    public List<String> getCompanyRateCount() {
        return companyRateCount;
    }

    public void setCompanyRateCount(List<String> companyRateCount) {
        this.companyRateCount = companyRateCount;
    }

    public List<UserCommissionDefault> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommissionDefault> commissionsList) {
        this.commissionsList = commissionsList;
    }

    public IUserCommissionDefault getUserCommissionDefaultLogic() {
        return userCommissionDefaultLogic;
    }

    public void setUserCommissionDefaultLogic(
            IUserCommissionDefault userCommissionDefaultLogic) {
        this.userCommissionDefaultLogic = userCommissionDefaultLogic;
    }

    public IUserCommissionLogic getUserCommissionLogic() {
        return userCommissionLogic;
    }

    public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
        this.userCommissionLogic = userCommissionLogic;
    }

    public List<UserCommission> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<UserCommission> commissions) {
        this.commissions = commissions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public static void main(String[] args) {
        /*
         * String beginDate = "02:30:00"; String endDate = "08:30:00"; Date
         * afterDate; Date beforeDate; Date date = new Date(); DateFormat
         * dateformat = new SimpleDateFormat("HH:mm:ss"); try { afterDate =
         * dateformat.parse(beginDate); beforeDate = dateformat.parse(endDate);
         * date = dateformat.parse(dateformat.format(date));
         * System.out.println(date+"adf"); if(date.after(afterDate) &&
         * date.before(beforeDate)){ System.out.println("进来没"); } else {
         * System.out.println("没进来"); } } catch (ParseException e) {
         * e.printStackTrace(); }
         */
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getRateCount() {
        return rateCount;
    }

    public void setRateCount(List<String> rateCount) {
        this.rateCount = rateCount;
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

    /**
     * 这是查询单条记录的信息
     * 
     * @return
     */

    public String findByBranchStaff() {
        String account = getRequest().getParameter(ACCOUNT).trim();
        branchStaffExt = branchStaffExtLogic.queryBranchStaffExt(ACCOUNT,
                account);
        return SUCCESS;
    }
    
    private IReplenishAutoLogic replenishAutoLogic;
    
	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}
	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
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
   
	

}
