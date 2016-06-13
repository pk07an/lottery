package com.npc.lottery.user.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.user.logic.spring.MemberStaffExtLogic;
import com.npc.lottery.util.Tools;

public class ManagerCommissionAction extends BaseUserAction{
    /**
     * 
     */
    private static final long serialVersionUID = 5357667482302467290L;
    private static Logger logger = Logger.getLogger(ManagerCommissionAction.class);
    private List<UserCommissionDefault> commissionsList;
    private IUserCommissionDefault userCommissionDefaultLogic;
    private IUserCommissionLogic userCommissionLogic;
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IShopOddsLogic shopOddsLogic;
    private String type="userCommission";
    private String shopsCode;
    private List<UserCommission> commissions=new ArrayList<UserCommission>();
    private ICommonUserLogic commonUserLogic;
    private IMemberStaffExtLogic memberStaffExtLogic;
    
    private IReplenishAutoSetLogLogic replenishAutoSetLogLogic;
    
    private String qUserID;
    
    private Boolean runing;
    
 
    public boolean validateUpdateCommission(String managerId) {
    ManagerUser   user=(ManagerUser) this.getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
    boolean ret=false;
    String account=user.getAccount();
    List<ManagerUser> belowManagers=commonUserLogic.queryBelowManager(account, user.getUserType());
    boolean belowUser=false;
    for (int i = 0; i < belowManagers.size(); i++) {
        ManagerUser mu= belowManagers.get(i);
        if(mu.getID().longValue()==Long.valueOf(managerId).longValue())
        {
            belowUser=true;
            break;
        }
        
    }
    ret=belowUser;
    return ret;
    }
    //跳到分公司默认退水页面
    public String queryBranchCommission(){
    if(qUserID==null)
    	return "fail";
    qUserID=Tools.decodeWithKey(qUserID);
    BranchStaffExt branch= branchStaffExtLogic.findBranchStaffExtByID(new Long(qUserID));
    this.getRequest().setAttribute("CommissionUser", branch);
    initManagerCommission(qUserID,ManagerStaff.USER_TYPE_BRANCH);
    this.getRequest().setAttribute("userType", "branch");
    this.getRequest().setAttribute("searchType",  this.getRequest().getParameter("searchType"));
    this.getRequest().setAttribute("searchValue",  this.getRequest().getParameter("searchValue"));
    this.getRequest().setAttribute("aType",  "userBranch");
    this.getRequest().setAttribute("searchUserStatus", this.getRequest().getParameter("searchUserStatus"));
    	  return SUCCESS;
    }
    
    public String saveBranchCommission(){
    	
    	boolean saveDefault=false;
    	Map<String,String> retMap=new HashMap<String,String>();
    	   if(qUserID==null)
    	    	return "fail";
    	    qUserID=Tools.decodeWithKey(qUserID);
    	long t1=System.currentTimeMillis();
    	boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(qUserID), ManagerUser.USER_TYPE_BRANCH);
    	long t2=System.currentTimeMillis();
        List<UserCommission> needUpdateCommission= getNeedUpdateCommission(ManagerUser.USER_TYPE_BRANCH, new Long(qUserID),hasBet);
        long t3=System.currentTimeMillis();
        String defaultCheck= this.getRequest().getParameter("defaultCommission");
        if(defaultCheck!=null)
        {
        	saveDefault=true;
        	
        }
       
        String shopCode=  this.getCurrentManagerUser().getShopsInfo().getShopsCode();
        long t4=System.currentTimeMillis();
        List<ChiefStaffExt> cList = shopOddsLogic.findChiefByShopsCode(shopCode);
        long t5=System.currentTimeMillis();
		Long chiefID = cList.get(0).getManagerStaffID();
		
        userCommissionLogic.updateBranchBatchCommission(needUpdateCommission,hasBet,saveDefault,chiefID,new Long(qUserID));
        long t6=System.currentTimeMillis();
        System.out.println((t2-t1)/1000);
        System.out.println((t3-t2)/1000);
        System.out.println((t4-t3)/1000);
        System.out.println((t5-t4)/1000);
        System.out.println((t6-t5)/1000);
        //System.out.println((t2-t1)/1000);
       retMap.put("code", "0");
       return  this.ajaxJson(retMap);
    	  //return SUCCESS;
    	
    }
    
    
  //跳到股东退水页面
    public String queryStockCommission(){      
    String stockId=this.getRequest().getParameter("qUserID"); 
    if(stockId==null)
    	return "fail";
    stockId=Tools.decodeWithKey(stockId);
    StockholderStaffExt stock= stockholderStaffExtLogic.findStockholderStaffExtByID(new Long(stockId));
    this.getRequest().setAttribute("CommissionUser", stock);
    //BranchStaffExt branch=stock.getBranchStaffExt();
    //List<UserCommission>    commissions=userCommissionLogic.queryCommission(branch.getID(),ManagerUser.USER_TYPE_BRANCH);
    //Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    //map=convertCommissionListToMap(commissions);
    //String jsonCommission=JSONObject.fromObject(map).toString();
    //this.getRequest().setAttribute("parentCom", jsonCommission);
    
    initManagerCommission(stockId,ManagerStaff.USER_TYPE_STOCKHOLDER);

    //add by peter 
    this.getRequest().setAttribute("searchAccount", this.getRequest().getParameter("searchAccount"));
    this.getRequest().setAttribute("searchUserStatus", this.getRequest().getParameter("searchUserStatus"));
    this.getRequest().setAttribute("parentAccount", this.getRequest().getParameter("parentAccount"));
    this.getRequest().setAttribute("searchType",  this.getRequest().getParameter("searchType"));
    this.getRequest().setAttribute("searchValue",  this.getRequest().getParameter("searchValue"));
    
          return SUCCESS;
    }
    
    public String saveStockCommission(){
        
    	Map<String,String> retMap=new HashMap<String,String>();
    	   if(qUserID==null)
    	    	return "fail";
    	    qUserID=Tools.decodeWithKey(qUserID);
    	 boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(qUserID), ManagerUser.USER_TYPE_STOCKHOLDER);
    	List<UserCommission> needUpdateCommission= getNeedUpdateCommission(ManagerUser.USER_TYPE_STOCKHOLDER, new Long(qUserID),hasBet);
       
        userCommissionLogic.updateUserBatchCommission(needUpdateCommission,hasBet,false);
        retMap.put("code", "0");
        return  this.ajaxJson(retMap);
        //return SUCCESS;
        
    }
    
    
    
    
  ////跳到总代理退水页面
    public String queryGenAgentCommission(){      
    String genAgentId=this.getRequest().getParameter("qUserID"); 
    if(genAgentId==null)
    	return "fail";
    genAgentId=Tools.decodeWithKey(genAgentId);
    GenAgentStaffExt genAgent= genAgentStaffExtLogic.findGenAgentStaffExtByID(new Long(genAgentId));
    this.getRequest().setAttribute("CommissionUser", genAgent);
    initManagerCommission(genAgentId,ManagerStaff.USER_TYPE_GEN_AGENT);

    StockholderStaffExt stock=genAgent.getStockholderStaffExt();
    List<UserCommission>    commissions=userCommissionLogic.queryCommission(stock.getID(),ManagerUser.USER_TYPE_STOCKHOLDER);
    Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    map=convertCommissionListToMap(commissions);
    String jsonCommission=JSONObject.toJSONString(map);
    this.getRequest().setAttribute("parentCom", jsonCommission);
    //add by peter 
    this.getRequest().setAttribute("searchUserStatus", this.getRequest().getParameter("searchUserStatus"));
    this.getRequest().setAttribute("searchAccount", this.getRequest().getParameter("searchAccount"));
    this.getRequest().setAttribute("parentAccount", this.getRequest().getParameter("parentAccount"));
    this.getRequest().setAttribute("aType",  this.getRequest().getParameter("type"));
    this.getRequest().setAttribute("searchType",  this.getRequest().getParameter("searchType"));
    this.getRequest().setAttribute("searchValue",  this.getRequest().getParameter("searchValue"));
         
    return SUCCESS;
    }
    
    public String saveGenAgentCommission(){
    	Map<String,String> retMap=new HashMap<String,String>();
    	   if(qUserID==null)
    	    	return "fail";
    	    qUserID=Tools.decodeWithKey(qUserID);
    	 boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(qUserID), ManagerUser.USER_TYPE_GEN_AGENT);
        List<UserCommission> needUpdateCommission= getNeedUpdateCommission(ManagerUser.USER_TYPE_GEN_AGENT, new Long(qUserID),hasBet);
       
        userCommissionLogic.updateUserBatchCommission(needUpdateCommission,hasBet,false);
        retMap.put("code", "0");
        return  this.ajaxJson(retMap);
        //return SUCCESS;
        
    }
    
    
    
    ////跳到代理退水页面
    public String queryAgentCommission(){      
    String agentId=this.getRequest().getParameter("qUserID");  
    if(agentId==null)
    	return "fail";
    agentId=Tools.decodeWithKey(agentId);
    AgentStaffExt agentStaff= agentStaffExtLogic.findManagerStaffByID(new Long(agentId));
    this.getRequest().setAttribute("CommissionUser", agentStaff);
    initManagerCommission(agentId,ManagerStaff.USER_TYPE_AGENT);
    
    GenAgentStaffExt gen=agentStaff.getGenAgentStaffExt();
    List<UserCommission>    commissions=userCommissionLogic.queryCommission(gen.getID(),ManagerUser.USER_TYPE_GEN_AGENT);
    Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    map=convertCommissionListToMap(commissions);
    String jsonCommission=JSONObject.toJSONString(map);
    this.getRequest().setAttribute("parentCom", jsonCommission);
    
    //add by peter 
    this.getRequest().setAttribute("searchUserStatus", this.getRequest().getParameter("searchUserStatus"));
    this.getRequest().setAttribute("searchAccount", this.getRequest().getParameter("searchAccount"));
    this.getRequest().setAttribute("parentAccount", this.getRequest().getParameter("parentAccount"));
    this.getRequest().setAttribute("aType",  this.getRequest().getParameter("type"));
    this.getRequest().setAttribute("searchType",  this.getRequest().getParameter("searchType"));
    this.getRequest().setAttribute("searchValue",  this.getRequest().getParameter("searchValue"));

          return SUCCESS;
    }
    
    public String saveAgentCommission(){
    	Map<String,String> retMap=new HashMap<String,String>();
    	   if(qUserID==null)
    	    	return "fail";
    	    qUserID=Tools.decodeWithKey(qUserID);
    	  boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(qUserID), ManagerUser.USER_TYPE_AGENT);
        List<UserCommission> needUpdateCommission= getNeedUpdateCommission(ManagerUser.USER_TYPE_AGENT, new Long(qUserID),hasBet);
      
        userCommissionLogic.updateUserBatchCommission(needUpdateCommission,hasBet,false);
        retMap.put("code", "0");
        return  this.ajaxJson(retMap);
        //return SUCCESS;
        
    }
    
    
    
    ////跳到会员退水页面
    public String queryMemberCommission(){      
    String memberId=this.getRequest().getParameter("qUserID"); 
    if(memberId==null)
    	return "fail";
    memberId=Tools.decodeWithKey(memberId);
    MemberStaffExt member= memberStaffExtLogic.findMemberStaffExtByID(new Long(memberId));
   
    if(member==null)
        return "failure";
    
    this.getRequest().setAttribute("CommissionUser", member);
    initManagerCommission(memberId,MemberStaff.USER_TYPE_MEMBER);
    String  userType="0";
    if (member.getManagerStaff() instanceof AgentStaffExt) 
    {
    	userType=ManagerStaff.USER_TYPE_AGENT;
    } 
    else if (member.getManagerStaff() instanceof BranchStaffExt) 
    {
    	userType=ManagerStaff.USER_TYPE_BRANCH;
    } 
    else if (member.getManagerStaff() instanceof StockholderStaffExt) 
    {
    	userType=ManagerStaff.USER_TYPE_STOCKHOLDER;
    }
    else if (member.getManagerStaff() instanceof GenAgentStaffExt) 
    {
    	userType=ManagerStaff.USER_TYPE_GEN_AGENT;
    }
    this.getRequest().setAttribute("plate", member.getPlate());


    List<UserCommission>    commissions=userCommissionLogic.queryCommission(member.getManagerStaff().getID(),userType);
    Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    map=convertCommissionListToMap(commissions);
    String jsonCommission=JSONObject.toJSONString(map);
    this.getRequest().setAttribute("parentCom", jsonCommission);
    
    //add by peter 
    this.getRequest().setAttribute("searchUserStatus", this.getRequest().getParameter("searchUserStatus"));
    this.getRequest().setAttribute("searchAccount", this.getRequest().getParameter("searchAccount"));
    this.getRequest().setAttribute("parentAccount", this.getRequest().getParameter("parentAccount"));
    this.getRequest().setAttribute("aType",  this.getRequest().getParameter("type"));
    this.getRequest().setAttribute("searchType",  this.getRequest().getParameter("searchType"));
    this.getRequest().setAttribute("searchValue",  this.getRequest().getParameter("searchValue"));

          return SUCCESS;
    }
    
    public String saveMemberCommission(){
    	Map<String,String> retMap=new HashMap<String,String>();
    	   if(qUserID==null)
    	    	return "fail";
    	    qUserID=Tools.decodeWithKey(qUserID);
    	boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(qUserID), MemberStaff.USER_TYPE_MEMBER);
        List<UserCommission> needUpdateCommission= getNeedUpdateCommission(MemberStaff.USER_TYPE_MEMBER, new Long(qUserID),hasBet);
        
        MemberStaffExt member= memberStaffExtLogic.findMemberStaffExtByID(new Long(qUserID));
        userCommissionLogic.batchUpdateMemberCommissiono(needUpdateCommission,member.getPlate());
        retMap.put("code", "0");
        return  this.ajaxJson(retMap);
         // return SUCCESS;
        
    }
    
    
    
    
    
    
     //比较哪些数据是真正的变化了
    private List<UserCommission> getNeedUpdateCommission(String userType, Long userId,boolean hasBet)
    {
        List<UserCommission> needUpdateCommissions=new ArrayList<UserCommission>();
        String jsonCommisons=this.getRequest().getParameter("jsonCommission");
        try {
            jsonCommisons=URLDecoder.decode(jsonCommisons, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //add by peter,修改记录列表
        List<ReplenishAutoSetLog> changeLogList = new ArrayList<ReplenishAutoSetLog>();
       // boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(userId), userType);
        JSONObject cacheJson=JSONObject.parseObject(jsonCommisons);
        for (int i = 0; i < commissions.size(); i++) {
            UserCommission userCommission=  commissions.get(i);
            //fixed by peter,如果前台commissions[]下标不连续时候会更新不了数据，有错误
			if (null == userCommission) {
				continue;
			}
            String commisionType=userCommission.getPlayFinalType();
            if("BJ_1-10_DS".equals(commisionType)||"BJ_1-5_LH".equals(commisionType)||"BJ_1-10_DX".equals(commisionType))
            {
            	commisionType=commisionType.replace("-", "_");
            }
            JSONObject cacheCom=(JSONObject)cacheJson.get(commisionType);
            if(cacheCom==null)
            {
                //System.out.println("空。。。。。"+userCommission.getPlayFinalType());
                continue;
            }
            double comA=Double.valueOf((cacheCom.get("commissionA").toString())).doubleValue();
            double comB=Double.valueOf((cacheCom.get("commissionB").toString())).doubleValue();
            double comC=Double.valueOf((cacheCom.get("commissionC").toString())).doubleValue();
            int bq=((Integer)cacheCom.get("bettingQuotas")).intValue();
            int iq=((Integer)cacheCom.get("itemQuotas")).intValue();
            if(hasBet)
            {
            	userCommission.setCommissionA(comA);
            	userCommission.setCommissionB(comB);
            	userCommission.setCommissionC(comC);
            	if(userCommission.getBettingQuotas().intValue()==bq&&userCommission.getItemQuotas().intValue()==iq)
                 {
                     continue;
                 }
            	
				// add by peter for log the change log
				if (userCommission.getBettingQuotas().intValue() != bq) {
					ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_BETTING_QUOTAS, cacheCom.get("playType").toString(),String.valueOf(bq), userCommission.getBettingQuotas().toString(),userType,userId);
            		changeLogList.add(changeLog);
				}

				// add by peter for log the change log
				if (userCommission.getItemQuotas().intValue() != iq) {
					ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_ITEM_QUOTAS,cacheCom.get("playType").toString(), String.valueOf(iq), userCommission.getItemQuotas().toString(),userType,userId);
            		changeLogList.add(changeLog);
				}
            }
            else  
            {    boolean  aNotchanged=true;
                 boolean  bNotchanged=true;
                 boolean  cNotchanged=true;
                 boolean  bqNotchanged=(userCommission.getBettingQuotas().intValue()==bq);
                 boolean  iqNotchange=(userCommission.getItemQuotas().intValue()==iq);
                 
            	if(userCommission.getCommissionA()!=null)
            	{
            		aNotchanged=(userCommission.getCommissionA().doubleValue()==comA);
            		//add by peter for change log
            		if(!aNotchanged){
            			ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONA, cacheCom.get("playType").toString(),String.valueOf(comA), userCommission.getCommissionA().toString(),userType,userId);
            			changeLogList.add(changeLog);
            		}
            	}
            	if(userCommission.getCommissionB()!=null)
            	{
            		bNotchanged=(userCommission.getCommissionB().doubleValue()==comB);
            		//add by peter for change log
            		if(!bNotchanged){
            			ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONB, cacheCom.get("playType").toString(),String.valueOf(comB), userCommission.getCommissionB().toString(),userType,userId);
            			changeLogList.add(changeLog);
            		}
            	}
            	if(userCommission.getCommissionC()!=null)
            	{
            		cNotchanged=(userCommission.getCommissionC().doubleValue()==comC);
            		//add by peter for change log
            		if(!cNotchanged){
            			ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONC,cacheCom.get("playType").toString(), String.valueOf(comC), userCommission.getCommissionC().toString(),userType,userId);
            			changeLogList.add(changeLog);
            		}
            	}
            	//add by peter for change log
            	if(!bqNotchanged){
            		ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_BETTING_QUOTAS, cacheCom.get("playType").toString(),String.valueOf(bq), userCommission.getBettingQuotas().toString(),userType,userId);
            		changeLogList.add(changeLog);
            	}
            	//add by peter for change log
            	if(!iqNotchange){
            		ReplenishAutoSetLog changeLog = this.setChangeLog(userCommission, Constant.CHANGE_LOG_CHANGE_SUB_TYPE_ITEM_QUOTAS,cacheCom.get("playType").toString(), String.valueOf(iq), userCommission.getItemQuotas().toString(),userType,userId);
            		changeLogList.add(changeLog);
            	}
            	
	            if(aNotchanged&&bNotchanged&&cNotchanged&&bqNotchanged&&iqNotchange)
	            {
	                continue;
	            }
            }
            	
            userCommission.setUserId(userId);
            userCommission.setUserType(userType);
            needUpdateCommissions.add(userCommission);
            logger.info("有改變的數據。。。。。。"+userCommission.getPlayFinalType());
        }
		// 插入修改记录到记录表
		if (null != changeLogList && changeLogList.size() > 0) {
			this.getReplenishAutoSetLogLogic().saveUserCommissionLog(changeLogList,this.getReplenishAutoSetLogLogic());
		}
        return needUpdateCommissions;
        
    }
    
	private ReplenishAutoSetLog setChangeLog(UserCommission userCommission, String changeSubType, String playType, String orginalValue,
			String newValue,String userType, Long userId) {
		ReplenishAutoSetLog log = new ReplenishAutoSetLog();
		log.setChangeType(Constant.CHANGE_LOG_CHANGE_TYPE_COMMISSION_UPDATE);
		log.setChangeSubType(changeSubType);
		String type = "";
		switch (Integer.valueOf(playType)) {
			case 1:
				type = "GDKLSF";
				break;
			case 2:
				type = "CQSSC";
				break;
			case 3:
				type = "HKLHC";
				break;
			case 4:
				type = "BJSC";
				break;
			case 5:
				type = "K3";
				break;
			case 6:
				type = "NC";
				break;
			default:
				type = "";
				break;
		}
		log.setType(type);
		ManagerUser user = this.getCurrentManagerUser();
		log.setCreateUserID(userId);
		log.setShopID(user.getShopsInfo().getID());
		log.setCreateTime(new Date());
		log.setNewValue(newValue);
		log.setOrginalValue(orginalValue);
		log.setTypeCode(userCommission.getPlayFinalType());
		log.setCreateUserType(Integer.valueOf(userType));
		log.setIp(user.getLoginIp());

		// 更新的用户信息
		log.setUpdateUserID(user.getID());
		log.setUpdateUserType(Integer.valueOf(user.getUserType()));
		return log;
	}
    
    
    private void initManagerCommission(String managerId,String userType)
    {
        Map<String,UserCommission> map=new HashMap<String,UserCommission>();
        List<UserCommission>    commissions=userCommissionLogic.queryCommission(Long.valueOf(managerId),userType);
        map=convertCommissionListToMap(commissions);
           
        
           String jsonCommission=JSONObject.toJSONString(map);
           try {
               jsonCommission=URLEncoder.encode(jsonCommission,"utf-8");
           } catch (UnsupportedEncodingException e) {
               
               e.printStackTrace();
           }
            //是否投注
           long t1=System.currentTimeMillis();
           boolean hasBet=commonUserLogic.queryUserTreeHasBet(new Long(managerId), userType);
           long t2=System.currentTimeMillis();
           System.out.println("查詢 用戶投注 樹 話費時間："+(t2-t1)/1000);
           this.getRequest().setAttribute("hasBet", hasBet);
            //显示数据
           this.getRequest().setAttribute("commissions", map);
           //缓存数据
           this.getRequest().setAttribute("jsonCommission", jsonCommission); 
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String updateCommission(){
    	try {
    		List<ChiefStaffExt> cList = shopOddsLogic.findChiefByShopsCode(shopsCode);
    		Long chiefID = cList.get(0).getManagerStaffID();
            if(commissionsList != null && commissionsList.size() != 0){
            	UserCommissionDefault commissionDefault = null;
            	for (int i=0; i<commissionsList.size(); i++){	
                	commissionDefault = (UserCommissionDefault)commissionsList.get(i);
                    commissionDefault.setModifyTime(new Date());
                    commissionDefault.setUserId(chiefID);
                    commissionDefault.setCreateUser(chiefID);
                    userCommissionDefaultLogic.updateCommissionDefault(commissionDefault);
                }
            }
            return SUCCESS;
    	} catch (Exception e) {
 			e.printStackTrace();
 			return "failure";
 		}    
       
        
    }
    public IUserCommissionDefault getUserCommissionDefaultLogic() {
        return userCommissionDefaultLogic;
    }

    public void setUserCommissionDefaultLogic(
            IUserCommissionDefault userCommissionDefaultLogic) {
        this.userCommissionDefaultLogic = userCommissionDefaultLogic;
    }

    public List<UserCommissionDefault> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommissionDefault> commissionsList) {
        this.commissionsList = commissionsList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public String getShopsCode() {
		return shopsCode;
	}

	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}
	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}
	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}
	//list s数据 转换为map 方便页面显示
    private Map<String,UserCommission> convertCommissionListToMap(List<UserCommission> commissions)
    {
    	
    	Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    	for (int i = 0; i < commissions.size(); i++) {
    		UserCommission	userCommsiondb=commissions.get(i);
    		UserCommission	userCommsion=new UserCommission();
    		BeanUtils.copyProperties(userCommsiondb, userCommsion); // Copy对像属性
    		Double ca=userCommsion.getCommissionA();
    		Double cb=userCommsion.getCommissionB();
    		Double cc=userCommsion.getCommissionC();
			ca = new BigDecimal("100").subtract(new BigDecimal(ca.toString()).setScale(3, BigDecimal.ROUND_DOWN)).setScale(3, BigDecimal.ROUND_DOWN)
					.doubleValue();
			cb = new BigDecimal("100").subtract(new BigDecimal(cb.toString()).setScale(3, BigDecimal.ROUND_DOWN)).setScale(3, BigDecimal.ROUND_DOWN)
					.doubleValue();
			cc = new BigDecimal("100").subtract(new BigDecimal(cc.toString()).setScale(3, BigDecimal.ROUND_DOWN)).setScale(3, BigDecimal.ROUND_DOWN)
					.doubleValue();
    		userCommsion.setCommissionA(ca);
    		userCommsion.setCommissionB(cb);
    		userCommsion.setCommissionC(cc);
    		String key=userCommsion.getPlayFinalType();
    		//单独处理含有- 的字符的key 页面显示 会报错
    		key=key.replace("-", "_");
    		
    		map.put(key, userCommsion);
		}
    	return map;
    	
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
	public List<UserCommission> getCommissions() {
		return commissions;
	}
	public void setCommissions(List<UserCommission> commissions) {
		this.commissions = commissions;
	}
  
    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }
	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}
	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}
	public String getqUserID() {
		return qUserID;
	}
	public void setqUserID(String qUserID) {
		this.qUserID = qUserID;
	}
    private boolean bjRuning()
    {
        BJSCPeriodsInfo bjp=this.getBJSCRunningPeriods();
   
        if(bjp!=null&&Constant.OPEN_STATUS.equals(bjp.getState()))
        {
        return true;
        }
        return false;
    	
    }
    private boolean cqRuning()
    {
       
        CQPeriodsInfo  cqp=this.getCQRunningPeriods();
        
        if(cqp!=null&&Constant.OPEN_STATUS.equals(cqp.getState()))
        {
            return true;
        }
        return false;
    }
    private boolean gdRuning()
    {
       
        GDPeriodsInfo gdp=this.getGDRunningPeriods();
 
        if(gdp!=null&&Constant.OPEN_STATUS.equals(gdp.getState()))
        {
            return true;
        }
        return false;
    }
	public Boolean getRuning() {
		if(runing==null)
		{
			runing=this.bjRuning()||this.bjRuning()||this.gdRuning();
		}
		
		return runing;
	}
	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}
	public void ajaxParentCommission()
	{
		String userID=this.getRequest().getParameter("userID");
		if(userID==null)
			 this.ajaxJson("{}");
		userID=Tools.decodeWithKey(userID);
		String userType=this.getRequest().getParameter("userType");
		List<UserCommission>    commissions=new ArrayList<UserCommission>();
		Long parentID=0L;
		String parentType="0";
		if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
		{
			 AgentStaffExt agentStaff= agentStaffExtLogic.findManagerStaffByID(new Long(userID));
			commissions=userCommissionLogic.queryCommission(agentStaff.getGenAgentStaffExt().getID(),ManagerStaff.USER_TYPE_GEN_AGENT);
		}
		else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
		{
			GenAgentStaffExt genAgent=genAgentStaffExtLogic.findGenAgentStaffExtByID(new Long(userID));
			commissions=userCommissionLogic.queryCommission(genAgent.getStockholderStaffExt().getID(),ManagerStaff.USER_TYPE_STOCKHOLDER);
		}
		else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
		{
			StockholderStaffExt stock=stockholderStaffExtLogic.findStockholderStaffExtByID(new Long(userID));
			
			
			commissions=userCommissionLogic.queryCommission(stock.getBranchStaffExt().getID(),ManagerStaff.USER_TYPE_BRANCH);
		}
		/*else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
		{
			StockholderStaffExt stock=stockholderStaffExtLogic.findStockholderStaffExtByID(new Long(userID));
			
			
			commissions=userCommissionLogic.queryCommission(stock.getBranchStaffExt().getID(),ManagerStaff.USER_TYPE_BRANCH);
		}*/
		else if(MemberStaff.USER_TYPE_MEMBER.equals(userType))
		{
			 MemberStaffExt member= memberStaffExtLogic.findMemberStaffExtByID(new Long(userID));
			 
			 if (member.getManagerStaff() instanceof AgentStaffExt) 
			    {
				 parentType=ManagerStaff.USER_TYPE_AGENT;
			    } 
			    else if (member.getManagerStaff() instanceof BranchStaffExt) 
			    {
			    	parentType=ManagerStaff.USER_TYPE_BRANCH;
			    } 
			    else if (member.getManagerStaff() instanceof StockholderStaffExt) 
			    {
			    	parentType=ManagerStaff.USER_TYPE_STOCKHOLDER;
			    }
			    else if (member.getManagerStaff() instanceof GenAgentStaffExt) 
			    {
			    	parentType=ManagerStaff.USER_TYPE_GEN_AGENT;
			    }
			 
			 commissions=userCommissionLogic.queryCommission(member.getParentStaff(),parentType);
			 
			 
		}
		else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
		{
			BranchStaffExt branch=branchStaffExtLogic.findBranchStaffExtByID(new Long(userID));
			List<UserCommissionDefault>	defaultCommissions = userCommissionDefaultLogic.queryCommissionDefault(branch.getChiefStaffExt().getID());
			List<UserCommission> changedList=new ArrayList<UserCommission>();
			for (int i = 0; i < defaultCommissions.size(); i++) {
				UserCommissionDefault defualtcommision=defaultCommissions.get(i);
				UserCommission tempCommission=new UserCommission();
				tempCommission.setCommissionA(100d);
				tempCommission.setCommissionB(100d);
				tempCommission.setCommissionC(100d);
				tempCommission.setBettingQuotas(defualtcommision.getBettingQuotas());
				tempCommission.setItemQuotas(defualtcommision.getItemQuotas());
				tempCommission.setPlayFinalType(defualtcommision.getPlayFinalType());
				commissions.add(tempCommission);
				
			}
		}
		
		
	    Map<String,UserCommission> map=new HashMap<String,UserCommission>();
	    map=convertCommissionListToMap(commissions);
	    String jsonCommission=JSONObject.toJSONString(map);
	    this.ajaxJson(jsonCommission);
	}
	public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
		return replenishAutoSetLogLogic;
	}
	public void setReplenishAutoSetLogLogic(IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
	}
	
	
}
