package com.npc.lottery.replenish.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONArray;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.logic.interf.IZhangdanLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.rule.BJSCRule;
import com.npc.lottery.rule.CQSSCBallRule;
import com.npc.lottery.rule.GDKLSFRule;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.IBossLogLogic;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.user.entity.OutReplenishStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.user.logic.interf.IUserOutReplenishLogic;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;
public class ReplenishAction extends BaseLotteryAction {
	private static Logger log = Logger.getLogger(ReplenishAction.class);
	private static final long serialVersionUID = 1L;
	
    
    private String type = "oddSet";
    private String oddsSubType="GDKLSF";
    private ArrayList<OpenPlayOdds> openOddsList = new ArrayList<OpenPlayOdds>();
    private String playType = "gd";  
    private String plate = "A";
    private String periodsNum = null;
    private String searchType = Constant.TRUE_STATUS;   //虚注还是实占,初始化为实占
    private String searchArray = "lose";  //排序       "ball":按号球       "win":按盈利     "lose":按亏损
    private List<ReplenishVO> replenishList_LM = new ArrayList<ReplenishVO>();
    private SubAccountInfo subAccountInfo;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private ManagerUser userInfo;
    private String subMenu;//二級菜單
    
    private static final BigDecimal DEFAULT_AVLOSE = BigDecimal.valueOf(-100000000); //查询平均盈亏值的默认值
    private static final BigDecimal DEFAULT_AVODD = BigDecimal.valueOf(2); //查询条件--赔率
    private static final BigDecimal DEFAULT_AVCOMMISSION = BigDecimal.valueOf(100); //查询条件--退水
    
    private BigDecimal avLose = DEFAULT_AVLOSE; //平均亏损值查询参数
    private BigDecimal avOdd = DEFAULT_AVODD; //查询条件--赔率
    private BigDecimal avCommission = DEFAULT_AVCOMMISSION; //查询条件--退水
    
    private static final HashMap<String,String>  MA_CODE=new HashMap<String,String>();
	private static final HashMap<String,String>  BET_CODE=new HashMap<String,String>();
	static
	{
		MA_CODE.put("ZM1", "正碼一");
		MA_CODE.put("ZM2", "正碼二");
		MA_CODE.put("ZM3", "正碼三");
		MA_CODE.put("ZM4", "正碼四");
		MA_CODE.put("ZM5", "正碼五");
		MA_CODE.put("ZM6", "正碼六");
		BET_CODE.put("DAN", "單");
		BET_CODE.put("S", "雙");
		BET_CODE.put("DA", "大");
		BET_CODE.put("X", "小");
		BET_CODE.put("RED", "紅波");
		BET_CODE.put("GREEN", "綠波");
		BET_CODE.put("BLUE", "藍波");
		
	}
    
    
	//Constant.TRUE_STATUS   Constant.EMPTY_STATUS
	/*
	 * 	快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球
	 *  时时彩  1-5球
	 */
	private String subType=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST;
	
	private BigDecimal total;
	
	private String refleshTime = "5"; //补货界面的默认刷新时间
	
	private String loseSort;
	
	//广东补货菜单入口
	public String gdReplenishEnter(){
		if(subMenu==null || ("").equals(subMenu)){
			if(subType==null || ("").equals(subType)){
				subMenu = "GDKLSFReplenish";
				this.setSubType(subMenu);
			}else{
				if(subType.indexOf(Constant.LOTTERY_TYPE_GDKLSF)!=-1){
					subMenu = "GDKLSFReplenish";
				}else{
					subMenu = subType;
				}
			}
		}else{
				subMenu = subType;
		}
		return SUCCESS;
	}
	
	//农场补货菜单入口
	public String ncReplenishEnter(){
		if(subMenu==null || ("").equals(subMenu)){
			subMenu = "NCReplenish";
			this.setSubType(subMenu);
		}
		return SUCCESS;
	}
	//重庆补货菜单入口
	public String cqReplenishEnter(){
		if(subMenu==null || ("").equals(subMenu)){
			subMenu = "CQSSCReplenish";
			this.setSubType(subMenu);
		}
		return SUCCESS;
	}
	//北京补货菜单入口
	public String bjReplenishEnter(){
		if(subMenu==null || ("").equals(subMenu)){
			subMenu = "BJSCReplenish";
			this.setSubType(subMenu);
		}
		return SUCCESS;
	}
	//K3补货菜单入口
	public String k3ReplenishEnter(){
		if(subMenu==null || ("").equals(subMenu)){
			subMenu = "K3Replenish";
			this.setSubType(subMenu);
		}
		return SUCCESS;
	}
	
	public String realTimeDetail(){
		String queryTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		this.getRequest().setAttribute("queryTime",queryTime);
		return SUCCESS;
	}
	
	//補貨明細
	public String replenishDetail(){
		ManagerUser currentUserInfo = (ManagerUser) getRequest().getSession().getAttribute(
                Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, currentUserInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		
		//判断是否以公司占查询
		String rs = getRequest().getParameter("rs");  //界面传过来的查询参数，主要是为了判断是不是公司占
		if(Constant.COMPANY_STATUS.equals(rs) && ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
			UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
  			if(Constant.OPEN.equals(userVO.getReport())){
  				userInfoNew.setUserType(ManagerStaff.USER_TYPE_CHIEF);
  				userInfoNew.setID(userVO.getChiefID().longValue());
  			}
		}
		/**
		 * 这里的补货的明细分页要处理一下，因为这里是多表操作的，一个表没到底，另一个表到底了，当前页到了底后。。。
		 */
		Page<DetailVO> page = new Page<DetailVO>(25);
		Page<DetailVO> pageIn = new Page<DetailVO>(25);
		Page<DetailVO> pageOut = new Page<DetailVO>(25);
		int pageNo=1;
		
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		pageIn.setPageNo(pageNo);
		pageOut.setPageNo(pageNo);
		String typeCode = getRequest().getParameter("typeCode");
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        
        GDPeriodsInfo runningPeriodsGD = new GDPeriodsInfo();
        CQPeriodsInfo runningPeriodsCQ = new CQPeriodsInfo();
        NCPeriodsInfo runningPeriodsNC = new NCPeriodsInfo();
        BJSCPeriodsInfo runningPeriodsBJ = new BJSCPeriodsInfo();
        JSSBPeriodsInfo runningPeriodsK3 = new JSSBPeriodsInfo();
        String periodsNum = "";
        if(typeCode.indexOf("GDKLSF")!=-1){
        	runningPeriodsGD = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
        	periodsNum = runningPeriodsGD.getPeriodsNum();
        	String subTypeS = Constant.LOTTERY_TYPE_GDKLSF;
        	if(typeCode.indexOf("GDKLSF_STRAIGHTTHROUGH")!=-1) subTypeS = Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH;
        	
        	page=replenishLogic.queryBetDetail(page, userInfoNew, typeCode, periodsNum,subTypeS);
        	
		}else if(typeCode.indexOf("NC")!=-1){
			runningPeriodsNC = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			periodsNum = runningPeriodsNC.getPeriodsNum();
			String subTypeS = Constant.LOTTERY_TYPE_NC;
			if(typeCode.indexOf("NC_STRAIGHTTHROUGH")!=-1) subTypeS = Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH;
			page=replenishLogic.queryBetDetail(page, userInfoNew, typeCode, periodsNum,subTypeS);
			
		}else if(typeCode.indexOf("CQSSC")!=-1){
			runningPeriodsCQ = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			periodsNum = runningPeriodsCQ.getPeriodsNum();
			page=replenishLogic.queryBetDetail(page, userInfoNew, typeCode, periodsNum,Constant.LOTTERY_TYPE_CQSSC);
			
		}else if(typeCode.indexOf("BJ")!=-1){
			runningPeriodsBJ = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			periodsNum = runningPeriodsBJ.getPeriodsNum();
			page=replenishLogic.queryBetDetail(page, userInfoNew, typeCode, periodsNum,Constant.LOTTERY_TYPE_BJ);
			
		}else if(typeCode.indexOf("K3")!=-1){
			runningPeriodsK3 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			periodsNum = runningPeriodsK3.getPeriodsNum();
			page=replenishLogic.queryBetDetail(page, userInfoNew, typeCode, periodsNum,Constant.LOTTERY_TYPE_K3);
		}
        
        //如果是分公公司、股东、总代理的直属会员，就要这样处理：比如：如果是分公司的直属会员，分公司，股东，总代理的下方是显示总监的退水
      	//										   如查是股东的直属会员，股东，总代理，的下方显示是分公司的退水
      	//                                       如果是总代理的直属会员，总代理的下方是显水股东的退水
        List<DetailVO> retList = page.getResult();
      	for(DetailVO vo:retList){
      		if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getParentUserType())){
      			vo.setBranchCommission(vo.getChiefCommission());
      			vo.setStockCommission(vo.getChiefCommission());
      			vo.setGenAgentCommission(vo.getChiefCommission());
      		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getParentUserType())){
      			vo.setStockCommission(vo.getBranchCommission());
      			vo.setGenAgentCommission(vo.getBranchCommission());
      		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getParentUserType())){
      			vo.setGenAgentCommission(vo.getStockCommission());
      		}
      	}
      	String tableName = Constant.HKLHC_REPLENISH_TABLE_NAME;
		pageIn=replenishLogic.queryReplenishInDetail(pageIn, userInfoNew, typeCode, 
				periodsNum,null,null,null,Replenish.WIN_STATE_NOT_PRESENT,"unclear",tableName);
		pageOut=replenishLogic.queryReplenishOutDetail(pageOut, userInfoNew, typeCode, periodsNum);
		
		if(pageIn.getResult().size()>0){
			page.getResult().addAll(pageIn.getResult());
		}
		if(pageOut.getResult().size()>0){
			page.getResult().addAll(pageOut.getResult());
		}
		//按照盈虧排序亏损高排前
		 Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
	           public int compare(DetailVO arg0, DetailVO arg1) {   
	               return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
	            }   
	     });
		this.getRequest().setAttribute("page", page);
		this.getRequest().setAttribute("typeCode", typeCode);
		this.getRequest().setAttribute("rs", rs);
		return SUCCESS;
	}
	
	
	public String returnFromUserType(String subType,String result){
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}	
		//子帐号处理*********END
		
		if(subType.indexOf(Constant.LOTTERY_TYPE_GDKLSF)!=-1){
			if(Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON.equals(subType)){
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					return "klsfSumDragonTigerChief";
				}else{
					return "klsfSumDragonTiger";
				}
			}else if(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH.equals(subType)){
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					return "klsfStraightthroughChief";
				}else{
					return "klsfStraightthrough";
				}
			}else{
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					if("2columns".equals(result)){
						return "klsfBallChief2";
					}else{
						return "klsfBallChief3";
					}
				}else{
					if("2columns".equals(result)){
						return "klsfBall2";
					}else{
						return "klsfBall3";
					}
				}
			}
		}else
		if(subType.indexOf(Constant.LOTTERY_TYPE_NC)!=-1){
			if(Constant.LOTTERY_NC_SUBTYPE_DRAGON.equals(subType)){
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					return "ncSumDragonTigerChief";
				}else{
					return "ncSumDragonTiger";
				}
			}else if(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH.equals(subType)){
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					return "ncStraightthroughChief";
				}else{
					return "ncStraightthrough";
				}
			}else{
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					if("2columns".equals(result)){
						return "ncBallChief2";
					}else{
						return "ncBallChief3";
					}
				}else{
					if("2columns".equals(result)){
						return "ncBall2";
					}else{
						return "ncBall3";
					}
				}
			}
		}
		return null;
	}
	
	public String enterReplenishSet(){
		
		try {
			//获取请求的查询的类型，再传回Content页面
			String subTypeTmp = getRequest().getParameter("subType");
			if(subTypeTmp!=null){
				subType = subTypeTmp;
				getRequest().setAttribute("subType",subType);
			}
			getRequest().setAttribute("subType",subType);
			
			if(Constant.LOTTERY_CQSSC_SUBTYPE_ZHANGDAN.equals(subType)){
				return "zhangdanCQ";
			}
			if(Constant.LOTTERY_GDKLSF_SUBTYPE_ZHANGDAN.equals(subType)){
				return "zhangdanGD";
			}
			if(Constant.LOTTERY_BJSC_SUBTYPE_ZHANGDAN.equals(subType)){
				return "zhangdanBJ";
			}
			if(Constant.LOTTERY_K3_SUBTYPE_ZHANGDAN.equals(subType)){
				return "zhangdanK3";
			}
			if(Constant.LOTTERY_NC_SUBTYPE_ZHANGDAN.equals(subType)){
				return "zhangdanNC";
			}
			
			userInfo = getInfo();
			getOutReplenishUser();
			if(subType.indexOf("GDKLSF_BALL")!=-1){
				return "klsfBall2";
			}else if(subType.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH)!=-1 || subType.indexOf(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH)!=-1){
					return "klsfStraightthrough";
			}else{
				return "other";
			}
			
		  } catch (Exception e) {
			  log.info("加載補貨信息出錯，請重新登錄后再試。",e);
			  return "failure";
		}
	}
	
	//得到出货会员列表
	public void getOutReplenishUser(){
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		
		java.util.HashMap mapC = new java.util.LinkedHashMap();
		OutReplenishStaffExt outReplenishStaff = new OutReplenishStaffExt();
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("parentStaff", Integer.valueOf(userInfoNew.getID().toString())));
		Page<OutReplenishStaffExt> page = new Page<OutReplenishStaffExt>(1000);
		int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        try {
	        page = userOutReplenishLogic.findUserOutReplenish(page,filters
	                .toArray(new Criterion[filters.size()]));
	        for(int i=0;i<=page.getTotalCount()-1;i++){
		        outReplenishStaff = page.getResult().get(i);
		        mapC.put(outReplenishStaff.getID(),outReplenishStaff.getAccount());
	        }
	        this.getRequest().setAttribute("mapC", mapC);
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：enterReplenishSet总监的出货会员列表-->",e);
        }
	}
	
	
	public String enterReplenishSetContent()
	{
	    userInfo = getInfo();
	    
	    //子帐号处理*********START
  		ManagerUser userInfoNew = new ManagerUser();
  		try {
  			BeanUtils.copyProperties(userInfoNew, userInfo);
  		} catch (Exception e) {
  			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
  		}
  		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
  		//子帐号处理*********END
  		
  		
  		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
  		if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
  			getRequest().setAttribute("openReport",userVO.getReport());
  		}else{
  			getRequest().setAttribute("openReport",Constant.CLOSE);		//是否开放报表 
  		}
  		getRequest().setAttribute("mReplenishMent",userVO.getReplenishMent());	//查询是否允许补货
  		
  		
  		String t = this.getRequest().getParameter("searchTime");
		//控制刷新时间,总监的初始时间为5秒,其他初始时间为20秒
		String replenishTime = ""; 
		if(null != t){
			replenishTime = t;
		}else{
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				replenishTime = "5"; 
			}else{
				replenishTime = "15"; 
			}
		}
		getRequest().setAttribute("refleshTime",replenishTime);
	  		
        getRequest().setAttribute("subType",subType);//主要对应补货的二级菜单
	    
		String tableName = null;
		String ballNum = null;
		String preName = null;
		
		//总监的出货会员列表
	    getOutReplenishUser();
		
		//获得会员的帐户,信用额 可用金额
	  try {					
		//快乐十分 不同玩法
		String shopCode = userInfo.getSafetyCode();
		
		//***************今天输赢处理START
		/*java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		Date nowDate = new Date();
		Integer hours = nowDate.getHours();
		//如果是凌晨0点到3点前查，算前一天
		if(hours>=0 && hours<3){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);    //得到前一天
			Date date = calendar.getTime();
			startDate = new java.sql.Date(date.getTime());
			endDate = new java.sql.Date(date.getTime());
		}else{
			startDate = new java.sql.Date(new java.util.Date().getTime());
			endDate = new java.sql.Date(new java.util.Date().getTime());
		}
        String periodNum = null;
		//Double winData = settledReportEricLogic.queryTodayWinLose(userInfoNew.getID(), userInfoNew.getUserType(), "ALL", "ALL", periodNum,startDate, endDate);
		DecimalFormat df = new DecimalFormat("0.0"); 
		String strWinData = "0.0";*/
		//if(null != winData){
		//	strWinData = df.format(BigDecimal.valueOf(winData).setScale(1,BigDecimal.ROUND_HALF_UP));   //格式化显示
		//}
		//log.info("******当天投注输赢接口结果 = " + strWinData);
		//getRequest().setAttribute("winData", strWinData);
		//***************今天输赢处理END
		
		if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[0];
			ballNum = "1";
			preName="FIRST"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			
			if ("noPeriods".equals(result)){
				return "notKp";
			}
			if ("illegalityData".equals(result)){
				return "illegalityData";
			}
			
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[1];
			ballNum = "2";
			preName="SECOND"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND,result);
		}
	
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[2];
			ballNum = "3";
			preName="THIRD"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[3];
			ballNum = "4";
			preName="FORTH"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[4];
			ballNum = "5";
			preName="FIFTH"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[5];
			ballNum = "6";
			preName="SIXTH"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH,result);
		}
	
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH.equals(subType))
		{
			
			tableName = Constant.GDKLSF_TABLE_LIST[6];
			ballNum = "7";
			preName="SEVENTH"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH.equals(subType))
		{
			tableName = Constant.GDKLSF_TABLE_LIST[7];
			ballNum = "8";
			preName="EIGHTH"; 			
			String result = initGDReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH,result);
		}		
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON.equals(subType))
		{
			tableName = Constant.GDKLSF_DOUBLESIDE_TABLE_NAME;
			String result = initGDReplenishForLh(shopCode,tableName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH.equals(subType))
		{
			tableName = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME;
			String finalPlayType = getRequest().getParameter("checkType");
			String result = initGDReplenishForLM(shopCode,tableName,finalPlayType,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH,result);
		}
		else if(Constant.LOTTERY_GDKLSF_SUBTYPE_ZHANGDAN.equals(subType))
		{
			return "zhangdanGD";
		}
		
		//重庆时时彩
		else if(Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST.equals(subType))
		{
			String result = initCQShopOdds(shopCode,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				return "cqsscBallChief";
			}else{
				return "cqsscBall";
			}
		}else if(Constant.LOTTERY_CQSSC_SUBTYPE_ZHANGDAN.equals(subType))
		{
			return "zhangdanCQ";
		}
		
		//北京賽車
		else if(Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST.equals(subType)){
			String result = initBJShopOdds(shopCode,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			getSideAndDragonRank(Constant.LOTTERY_TYPE_BJSC);//统计两面长龙
			 replenishRightBar(Constant.LOTTERY_TYPE_BJ);//总额和遗漏
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				return "bjscFirstChief";
			}else{
				return "bjscFirst";
			}
		}
		else if(Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD.equals(subType)){
			String result = initBJShopOdds3(shopCode,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			getSideAndDragonRank(Constant.LOTTERY_TYPE_BJSC);//统计两面长龙
			 replenishRightBar(Constant.LOTTERY_TYPE_BJ);//总额和遗漏
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				return "bjscThirdChief";
			}else{
				return "bjscThird";
			}
		}
		else if(Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH.equals(subType)){
			String result = initBJShopOdds7(shopCode,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			getSideAndDragonRank(Constant.LOTTERY_TYPE_BJSC);//统计两面长龙
			 replenishRightBar(Constant.LOTTERY_TYPE_BJ);//总额和遗漏
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				return "bjscSeventhChief";
			}else{
				return "bjscSeventh";
			}
		}//K3补货
		else if(Constant.LOTTERY_K3_SUBTYPE_BALL.equals(subType)){
			String result = initK3ShopOdds(shopCode,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				return "k3Chief";
			}else{
				return "k3";
			}
		}else 
		//农场
		if(Constant.LOTTERY_NC_SUBTYPE_BALL_FIRST.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "1";
			preName="FIRST"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			
			if ("noPeriods".equals(result)){
				return "notKp";
			}
			if ("illegalityData".equals(result)){
				return "illegalityData";
			}
			
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_FIRST,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_SECOND.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "2";
			preName="SECOND"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_SECOND,result);
		}
	
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_THIRD.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "3";
			preName="THIRD"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_THIRD,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_FORTH.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "4";
			preName="FORTH"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_FORTH,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_FIFTH.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "5";
			preName="FIFTH"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_FIFTH,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_SIXTH.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "6";
			preName="SIXTH"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_SIXTH,result);
		}
	
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_SEVENTH.equals(subType))
		{
			
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "7";
			preName="SEVENTH"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_SEVENTH,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_BALL_EIGHTH.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			ballNum = "8";
			preName="EIGHTH"; 			
			String result = initNCReplenish(shopCode,tableName,ballNum,preName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_BALL_EIGHTH,result);
		}		
		else if(Constant.LOTTERY_NC_SUBTYPE_DRAGON.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			String result = initNCReplenishForLh(shopCode,tableName,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_DRAGON,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH.equals(subType))
		{
			tableName = Constant.NC_TABLE_NAME;
			String finalPlayType = getRequest().getParameter("checkType");
			String result = initNCReplenishForLM(shopCode,tableName,finalPlayType,userInfoNew);
			if (result.equals("noPeriods")){
				return "notKp";
			}
			if (result.equals("illegalityData")){
				return "illegalityData";
			}
			return returnFromUserType(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH,result);
		}
		else if(Constant.LOTTERY_NC_SUBTYPE_ZHANGDAN.equals(subType))
		{
			return "zhangdanGD";
		}
		
		
	   this.getRequest().setAttribute("subType", subType);
		return "memberCenter";
	  } catch (Exception e) {
		  log.info("加載補貨信息出錯，請重新登錄后再試。",e);
		  e.printStackTrace();
		  return "failure";
		}
	}
 	// 當前盤期
	public GDPeriodsInfo getGDRunningPeriods(String shopCode)
	{
		GDPeriodsInfo runningPeriods=periodsInfoLogic.findCurrentPeriod();
		Date now=new Date();
		if(now.after(runningPeriods.getStopQuotTime())){
			//当前时间在封盘时间之后,状态为封盘
			runningPeriods.setState("3");
		}else{
			//当前时间在封盘时间之前,状态为开盘
			runningPeriods.setState("2");
		}
		this.getRequest().setAttribute("RunningPeriods", runningPeriods);
		return runningPeriods;
	}
	
	//北京冠亚军和指定
	public String initBJShopOdds(String shopCode,ManagerUser userInfoNew){
		Map<String,ReplenishVO> groupMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> doubleMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> firstMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> secondMap=new HashMap<String,ReplenishVO>();
		
		Integer ballPetTotal_1 = 0;    //号球总投注额
		Integer ballPetTotal_2 = 0;
		Integer ballPetTotal_group = 0; //冠、亞軍和 指定
		BigDecimal topLose = BigDecimal.ZERO; //最高亏損
		BigDecimal topWin = BigDecimal.ZERO; //最高盈利
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		//查询系统初始设定里K3是否为封盘状态
		String pState = systemLogic.findPeriodState(Constant.LOTTERY_TYPE_BJ, userInfoNew.getSafetyCode());
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			if(Constant.CLOSE.equals(pState)) runningPeriods.setState(Constant.STOP_STATUS);
			if(Constant.OPEN.equals(pState)) runningPeriods.setState(Constant.OPEN_STATUS);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("BJ_GROUP%");//和組合
			list.add("BJ_DOUBLESIDE_D%");//大和單
			list.add("BJ_DOUBLESIDE_X");
			list.add("BJ_DOUBLESIDE_S");
			list.add("BJ_BALL_FIRST%");
			list.add("BJ_DOUBLESIDE_1_D%");
			list.add("BJ_DOUBLESIDE_1_S");
			list.add("BJ_DOUBLESIDE_1_X");
			list.add("BJ_DOUBLESIDE_1_LONG");
			list.add("BJ_DOUBLESIDE_1_HU");
			list.add("BJ_BALL_SECOND%");
			list.add("BJ_DOUBLESIDE_2_D%");
			list.add("BJ_DOUBLESIDE_2_S");
			list.add("BJ_DOUBLESIDE_2_X");
			list.add("BJ_DOUBLESIDE_2_LONG");
			list.add("BJ_DOUBLESIDE_2_HU");
			List<ShopsPlayOdds> oddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_BJ(orgUserInfo, plate, periodsNum,searchType);
			if(oddList != null && oddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : oddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = shopodds.getPlayTypeCode();
					rVO.setFpState(shopodds.getState());
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							BigDecimal tmpLoseMoney = BigDecimal.ZERO; //臨時對比用
							BigDecimal tmpWinMoney = BigDecimal.ZERO; //臨時對比用
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							tmpLoseMoney = v.getLoseMoney();
							tmpWinMoney = v.getLoseMoney();
							//統計總額和最高盈虧
							if(typeCode.indexOf("BJ_GROUP")!=-1){
								//返回值    -1 小于   0 等于    1 大于
								if (tmpLoseMoney.compareTo(topLose)==-1){ topLose = tmpLoseMoney; }
								if (tmpWinMoney.compareTo(topWin)==1){ topWin = tmpWinMoney; }
								ballPetTotal_group += v.getMoney();
							}else if(typeCode.indexOf("BJ_DOUBLESIDE_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_X")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_S")!=-1){
								doubleMap.put(shopodds.getPlayTypeCode(), rVO);
							}else if(typeCode.indexOf("BJ_BALL_FIRST")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_1_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_HU")!=-1){
								
								if(typeCode.indexOf("BJ_BALL_FIRST")!=-1){
									ballPetTotal_1 += v.getMoney();
								}
								firstMap.put(shopodds.getPlayTypeCode(), rVO);
							}else if(typeCode.indexOf("BJ_BALL_SECOND")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_2_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_HU")!=-1){
								if(typeCode.indexOf("BJ_BALL_SECOND")!=-1){
									ballPetTotal_2 += v.getMoney();
								}
								//secondMap.put(shopodds.getPlayTypeCode(), rVO);
							}
							
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					if(typeCode.indexOf("BJ_GROUP")!=-1){
						groupMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_DOUBLESIDE_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_X")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_S")!=-1){
						doubleMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_FIRST")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_1_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_1_HU")!=-1){
						
						firstMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_SECOND")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_2_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_2_HU")!=-1){
						
						secondMap.put(shopodds.getPlayTypeCode(), rVO);
					}
				}
			}
		}
		
		groupMap = this.sortByBallInt(groupMap);
		doubleMap = this.sortBySortNo(doubleMap);
		firstMap = this.sortBySortNo(firstMap);
		secondMap = this.sortBySortNo(secondMap);
		this.getRequest().setAttribute("groupMap", groupMap);
		this.getRequest().setAttribute("doubleMap", doubleMap);
		this.getRequest().setAttribute("firstMap", firstMap);
		this.getRequest().setAttribute("secondMap", secondMap);
		this.getRequest().setAttribute("ballPetTotal_1", ballPetTotal_1);
		this.getRequest().setAttribute("ballPetTotal_2", ballPetTotal_2);
		this.getRequest().setAttribute("ballPetTotal_group", ballPetTotal_group);
		this.getRequest().setAttribute("topLose", topLose.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("topWin", topWin.setScale(0,BigDecimal.ROUND_HALF_UP));
		return "success";
	}
	
	//北京第三至六名
	public String initBJShopOdds3(String shopCode,ManagerUser userInfoNew){
		Map<String,ReplenishVO> firstMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> secondMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> thirdMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> forthMap=new HashMap<String,ReplenishVO>();
		
		Integer ballPetTotal_1 = 0;//号球总投注额
		Integer ballPetTotal_2 = 0;
		Integer ballPetTotal_3 = 0;
		Integer ballPetTotal_4 = 0;
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		//查询系统初始设定里K3是否为封盘状态
		String pState = systemLogic.findPeriodState(Constant.LOTTERY_TYPE_BJ, userInfoNew.getSafetyCode());
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			if(Constant.CLOSE.equals(pState)) runningPeriods.setState(Constant.STOP_STATUS);
			if(Constant.OPEN.equals(pState)) runningPeriods.setState(Constant.OPEN_STATUS);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("BJ_BALL_THIRD%");
			list.add("BJ_DOUBLESIDE_3_%");
			list.add("BJ_BALL_FORTH%");
			list.add("BJ_DOUBLESIDE_4_%");
			list.add("BJ_BALL_FIFTH%");
			list.add("BJ_DOUBLESIDE_5_%");
			list.add("BJ_BALL_SIXTH%");
			list.add("BJ_DOUBLESIDE_6_%");
			List<ShopsPlayOdds> oddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_BJ_Other(orgUserInfo, plate, periodsNum,searchType,"secondForm");
			if(oddList != null && oddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : oddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = shopodds.getPlayTypeCode();
					rVO.setFpState(shopodds.getState());
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							//統計總額和最高盈虧
							if(typeCode.indexOf("BJ_BALL_THIRD")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_3_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_HU")!=-1){
								
								        if(typeCode.indexOf("BJ_BALL_THIRD")!=-1){
								        	ballPetTotal_1 += v.getMoney();
								        }
										//firstMap.put(shopodds.getPlayTypeCode(), rVO);
							}else if(typeCode.indexOf("BJ_BALL_FORTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_4_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_HU")!=-1){
										if(typeCode.indexOf("BJ_BALL_FORTH")!=-1){
								        	ballPetTotal_2 += v.getMoney();
								        }
							}else if(typeCode.indexOf("BJ_BALL_FIFTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_5_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_HU")!=-1){
								if(typeCode.indexOf("BJ_BALL_FIFTH")!=-1){
									ballPetTotal_3 += v.getMoney();
								}
							}else if(typeCode.indexOf("BJ_BALL_SIXTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_6_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_6_X")!=-1 
								|| typeCode.indexOf("BJ_DOUBLESIDE_6_S")!=-1){
								if(typeCode.indexOf("BJ_BALL_SIXTH")!=-1){
									ballPetTotal_4 += v.getMoney();
								}
							}
							
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					if(typeCode.indexOf("BJ_BALL_THIRD")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_3_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_3_HU")!=-1){
						
								firstMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_FORTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_4_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_4_HU")!=-1){
						
								secondMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_FIFTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_5_S")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_LONG")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_5_HU")!=-1){
						
						thirdMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_SIXTH")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_6_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_6_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_6_S")!=-1){
						
						forthMap.put(shopodds.getPlayTypeCode(), rVO);
					}
				}
			}
		}
		
		firstMap = this.sortBySortNo(firstMap);
		secondMap = this.sortBySortNo(secondMap);
		thirdMap = this.sortBySortNo(thirdMap);
		forthMap = this.sortBySortNo(forthMap);
		this.getRequest().setAttribute("firstMap", firstMap);
		this.getRequest().setAttribute("secondMap", secondMap);
		this.getRequest().setAttribute("thirdMap", thirdMap);
		this.getRequest().setAttribute("forthMap", forthMap);
		this.getRequest().setAttribute("ballPetTotal_1", ballPetTotal_1);
		this.getRequest().setAttribute("ballPetTotal_2", ballPetTotal_2);
		this.getRequest().setAttribute("ballPetTotal_3", ballPetTotal_3);
		this.getRequest().setAttribute("ballPetTotal_4", ballPetTotal_4);
		return "success";
	}
	
	//北京第七至十名
	public String initBJShopOdds7(String shopCode,ManagerUser userInfoNew){
		Map<String,ReplenishVO> firstMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> secondMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> thirdMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> forthMap=new HashMap<String,ReplenishVO>();
		
		Integer ballPetTotal_1 = 0;//号球总投注额
		Integer ballPetTotal_2 = 0;
		Integer ballPetTotal_3 = 0;
		Integer ballPetTotal_4 = 0;
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		//查询系统初始设定里K3是否为封盘状态
		String pState = systemLogic.findPeriodState(Constant.LOTTERY_TYPE_BJ, userInfoNew.getSafetyCode());
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			if(Constant.CLOSE.equals(pState)) runningPeriods.setState(Constant.STOP_STATUS);
			if(Constant.OPEN.equals(pState)) runningPeriods.setState(Constant.OPEN_STATUS);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("BJ_BALL_SEVENTH_%");
			list.add("BJ_DOUBLESIDE_7_%");
			list.add("BJ_BALL_EIGHTH_%");
			list.add("BJ_DOUBLESIDE_8_%");
			list.add("BJ_BALL_NINTH_%");
			list.add("BJ_DOUBLESIDE_9_%");
			list.add("BJ_BALL_TENTH_%");
			list.add("BJ_DOUBLESIDE_10_%");
			List<ShopsPlayOdds> oddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_BJ_Other(orgUserInfo, plate, periodsNum,searchType,"thirdForm");
			if(oddList != null && oddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : oddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = shopodds.getPlayTypeCode();
					rVO.setFpState(shopodds.getState());
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							//統計總額和最高盈虧
							if(typeCode.indexOf("BJ_BALL_SEVENTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_7_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_7_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_7_S")!=-1){
								
								if(typeCode.indexOf("BJ_BALL_SEVENTH_")!=-1){
									ballPetTotal_1 += v.getMoney();
								}
								//firstMap.put(shopodds.getPlayTypeCode(), rVO);
							}else if(typeCode.indexOf("BJ_BALL_EIGHTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_8_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_8_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_8_S")!=-1){
								if(typeCode.indexOf("BJ_BALL_EIGHTH_")!=-1){
									ballPetTotal_2 += v.getMoney();
								}
							}else if(typeCode.indexOf("BJ_BALL_NINTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_9_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_9_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_9_S")!=-1){
								if(typeCode.indexOf("BJ_BALL_NINTH_")!=-1){
									ballPetTotal_3 += v.getMoney();
								}
							}else if(typeCode.indexOf("BJ_BALL_TENTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_10_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_10_X")!=-1 
									|| typeCode.indexOf("BJ_DOUBLESIDE_10_S")!=-1){
								if(typeCode.indexOf("BJ_BALL_TENTH_")!=-1){
									ballPetTotal_4 += v.getMoney();
								}
							}
							
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					if(typeCode.indexOf("BJ_BALL_SEVENTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_7_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_7_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_7_S")!=-1){
						
						firstMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_EIGHTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_8_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_8_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_8_S")!=-1){
						
						secondMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_NINTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_9_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_9_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_9_S")!=-1){
						
						thirdMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("BJ_BALL_TENTH_")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_10_D")!=-1 || typeCode.indexOf("BJ_DOUBLESIDE_10_X")!=-1 
							|| typeCode.indexOf("BJ_DOUBLESIDE_10_S")!=-1){
						
						forthMap.put(shopodds.getPlayTypeCode(), rVO);
					}
				}
			}
		}
		
		firstMap = this.sortBySortNo(firstMap);
		secondMap = this.sortBySortNo(secondMap);
		thirdMap = this.sortBySortNo(thirdMap);
		forthMap = this.sortBySortNo(forthMap);
		this.getRequest().setAttribute("firstMap", firstMap);
		this.getRequest().setAttribute("secondMap", secondMap);
		this.getRequest().setAttribute("thirdMap", thirdMap);
		this.getRequest().setAttribute("forthMap", forthMap);
		this.getRequest().setAttribute("ballPetTotal_1", ballPetTotal_1);
		this.getRequest().setAttribute("ballPetTotal_2", ballPetTotal_2);
		this.getRequest().setAttribute("ballPetTotal_3", ballPetTotal_3);
		this.getRequest().setAttribute("ballPetTotal_4", ballPetTotal_4);
		return "success";
	}
	
	//K3
	public String initK3ShopOdds(String shopCode,ManagerUser userInfoNew){
		Map<String,ReplenishVO> sjMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> dxMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> qsMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> wsMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> dsMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> cpMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> dpMap=new HashMap<String,ReplenishVO>();
		
		/*Integer ballPetTotal_sj = 0;
		Integer ballPetTotal_dx = 0;
		Integer ballPetTotal_qs = 0;
		Integer ballPetTotal_ws = 0;
		Integer ballPetTotal_ds = 0;
		Integer ballPetTotal_cp = 0;
		Integer ballPetTotal_dp = 0;*/
		
		BigDecimal ballPetTotal_sj_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_dx_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_qs_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_ws_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_ds_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_temp = BigDecimal.ZERO;
		BigDecimal ballPetTotal_dp_temp = BigDecimal.ZERO;
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		JSSBPeriodsInfo runningPeriods=jssbPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		
		//查询系统初始设定里K3是否为封盘状态
		String pState = systemLogic.findPeriodState(Constant.LOTTERY_TYPE_K3, userInfoNew.getSafetyCode());
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			if(Constant.CLOSE.equals(pState)) runningPeriods.setState(Constant.STOP_STATUS);
			if(Constant.OPEN.equals(pState)) runningPeriods.setState(Constant.OPEN_STATUS);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("K3_SJ_%");
			list.add("K3_DA%");
			list.add("K3_X%");
			list.add("K3_QS");
			list.add("K3_WS_%");
			list.add("K3_DS_%");
			list.add("K3_CP_%");
			list.add("K3_DP_%");
			List<ShopsPlayOdds> oddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
				if(Constant.OPEN.equals(userVO.getReport())){
					orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
					orgUserInfo.setID(userVO.getChiefID().longValue());
				}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_K3(orgUserInfo, plate, periodsNum,searchType,"thirdForm");
			if(oddList != null && oddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : oddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = shopodds.getPlayTypeCode();
					rVO.setFpState(shopodds.getState());
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							//因为三军二骰三骰的盈亏计算是在页面用一骰换算的，所以要保留小数点。
							if(typeCode.indexOf("K3_SJ_")!=-1){
								rVO.setLoseMoney(v.getLoseMoney()); 
							}else{
								rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							}
							rVO.setSortNo(v.getSortNo());
							//这里主要用于K3三军计算,因为从数据库计算的commissionMoney是注额*拥金率，但三军补货的合计里需要单独减拥金，所以这里要再用注额-commssionMoney=拥金
							//System.out.println("~~~~~~~~~~~~v.getMoney(): "+v.getMoney());
							//System.out.println("~~~~~~~~~~~~v.getTotalCommissionMoney(): "+v.getTotalCommissionMoney());
							rVO.setCommissionMoney(BigDecimal.valueOf(v.getMoney()).subtract(v.getTotalCommissionMoney()));
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							//統計總額
							if(typeCode.indexOf("K3_SJ_")!=-1){
								//System.out.println("~~~~~~~~~~~~ballPetTotal_sj_temp before: "+ballPetTotal_sj_temp);
								//System.out.println("~~~~~~~~~~~~v.getRMoney(): "+v.getRMoney());
								//System.out.println("~~~~~~~~~~~~v.getTotalCommissionMoney()(): "+v.getTotalCommissionMoney());
								ballPetTotal_sj_temp = ballPetTotal_sj_temp.add(v.getTotalCommissionMoney());
//								ballPetTotal_sj_temp = ballPetTotal_sj_temp.add(BigDecimal.valueOf(v.getRMoney())).subtract(v.getCommissionMoney());
								//System.out.println("~~~~~~~~~~~~ballPetTotal_sj_temp after: "+ballPetTotal_sj_temp);
							}else if("K3_DA".equals(typeCode) || "K3_X".equals(typeCode)){
									ballPetTotal_dx_temp = ballPetTotal_dx_temp.add(v.getTotalCommissionMoney());
							}else if("K3_QS".equals(typeCode)){
									ballPetTotal_qs_temp = ballPetTotal_qs_temp.add(v.getTotalCommissionMoney());
							}else if(typeCode.indexOf("K3_WS_")!=-1){
									ballPetTotal_ws_temp = ballPetTotal_ws_temp.add(v.getTotalCommissionMoney());
							}else if(typeCode.indexOf("K3_DS_")!=-1){
								ballPetTotal_ds_temp = ballPetTotal_ds_temp.add(v.getTotalCommissionMoney());
							}else if(typeCode.indexOf("K3_CP_")!=-1){
								ballPetTotal_cp_temp = ballPetTotal_cp_temp.add(v.getTotalCommissionMoney());
							}else if(typeCode.indexOf("K3_DP_")!=-1){
								ballPetTotal_dp_temp = ballPetTotal_dp_temp.add(v.getTotalCommissionMoney());
							}
							
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					if(typeCode.indexOf("K3_SJ_")!=-1){
						sjMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if("K3_DA".equals(typeCode) || "K3_X".equals(typeCode)){
						dxMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if("K3_QS".equals(typeCode)){
						qsMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("K3_WS_")!=-1){
						wsMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("K3_DS_")!=-1){
						dsMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("K3_CP_")!=-1){
						cpMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("K3_DP_")!=-1){
						dpMap.put(shopodds.getPlayTypeCode(), rVO);
					}
				}
			}
		}
		
		Map<String,String>  lastLotteryPeriods = getK3LastLotteryPeriods();//获取上期开奖结果
		
		sjMap = this.sortBySortNo(sjMap);
		dxMap = this.sortBySortNo(dxMap);
		qsMap = this.sortBySortNo(qsMap);
		wsMap = this.sortBySortNo(wsMap);
		dsMap = this.sortBySortNo(dsMap);
		cpMap = this.sortBySortNo(cpMap);
		dpMap = this.sortBySortNo(dpMap);
		this.getRequest().setAttribute("sjMap", sjMap);
		this.getRequest().setAttribute("dxMap", dxMap);
		this.getRequest().setAttribute("qsMap", qsMap);
		this.getRequest().setAttribute("wsMap", wsMap);
		this.getRequest().setAttribute("dsMap", dsMap);
		this.getRequest().setAttribute("cpMap", cpMap);
		this.getRequest().setAttribute("dpMap", dpMap);
		this.getRequest().setAttribute("ballPetTotal_sj", ballPetTotal_sj_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_dx", ballPetTotal_dx_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_qs", ballPetTotal_qs_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_ws", ballPetTotal_ws_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_ds", ballPetTotal_ds_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_cp", ballPetTotal_cp_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("ballPetTotal_dp", ballPetTotal_dp_temp.setScale(0,BigDecimal.ROUND_HALF_UP));
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		return "success";
	}
	
	//重庆
	public String initCQShopOdds(String shopCode,ManagerUser userInfoNew)
	{
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> ballMap1=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> ballMap2=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> ballMap3=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> ballMap4=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> ballMap5=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> zhMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> frontMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> midMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> lastMap=new HashMap<String,ReplenishVO>();
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			
			periodsNum = runningPeriods.getPeriodsNum();
			
			//查询该用户当期的各种玩法的赔额合计、注额合计、占成、拥金
			List<ShopsPlayOdds> cqoddList=shopOddsLogic.queryCQSSCRealOdds(shopCode,"CQSSC");
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_CQ(orgUserInfo, plate, periodsNum,searchType);
			
			if(cqoddList != null && cqoddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : cqoddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = shopodds.getPlayTypeCode();
					rVO.setFpState(shopodds.getState());
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					if(typeCode.indexOf("CQSSC_BALL_FIRST")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_1")!=-1){
						ballMap1.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("CQSSC_BALL_SECOND")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_2")!=-1){
						ballMap2.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("CQSSC_BALL_THIRD")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_3")!=-1){
						ballMap3.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("CQSSC_BALL_FORTH")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_4")!=-1){
						ballMap4.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("CQSSC_BALL_FIFTH")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_5")!=-1){
						ballMap5.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("CQSSC_DOUBLESIDE_ZH")!=-1 || typeCode.indexOf("CQSSC_DOUBLESIDE_LONG")!=-1  || typeCode.indexOf("CQSSC_DOUBLESIDE_H")!=-1){
						zhMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("FRONT")!=-1){
						frontMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("MID")!=-1){
						midMap.put(shopodds.getPlayTypeCode(), rVO);
					}else if(typeCode.indexOf("LAST")!=-1){
						lastMap.put(shopodds.getPlayTypeCode(), rVO);
					}
					//rMap.put(shopodds.getPlayTypeCode(), rVO);
				}
			}else{
				List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		    	filtersPeriodInfo.add(Restrictions.eq("playType","CQSSC"));
				List<PlayType> ptList = playTypeLogic.findPlayType(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
				for (int i = 0; i < ptList.size(); i++) {
					rMap.put(ptList.get(i).getTypeCode(), new ReplenishVO());
				}
			}
		}
		
		ballMap1 = this.sortBySortNo(ballMap1);
		ballMap2 = this.sortBySortNo(ballMap2);
		ballMap3 = this.sortBySortNo(ballMap3);
		ballMap4 = this.sortBySortNo(ballMap4);
		ballMap5 = this.sortBySortNo(ballMap5);
		zhMap = this.sortBySortNo(zhMap);
		frontMap = this.sortBySortNo(frontMap);
		midMap = this.sortBySortNo(midMap);
		lastMap = this.sortBySortNo(lastMap);
		
		Map<String,String>  lastLotteryPeriods = getCQLastLotteryPeriods();//获取上期开奖结果
		
		getSideAndDragonRank(Constant.LOTTERY_TYPE_CQSSC);//统计两面长龙
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		this.getRequest().setAttribute("ballMap1", ballMap1);
		this.getRequest().setAttribute("ballMap2", ballMap2);
		this.getRequest().setAttribute("ballMap3", ballMap3);
		this.getRequest().setAttribute("ballMap4", ballMap4);
		this.getRequest().setAttribute("ballMap5", ballMap5);
		this.getRequest().setAttribute("zhMap", zhMap);
		this.getRequest().setAttribute("frontMap", frontMap);
		this.getRequest().setAttribute("midMap", midMap);
		this.getRequest().setAttribute("lastMap", lastMap);
		return "success";
	}
	
	
	public String ajaxReplenishLM(){
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		String shopCode = userInfo.getSafetyCode();
		String tableName = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME;
		String finalPlayType = this.getRequest().getParameter("checkType");
		if(finalPlayType==null) finalPlayType="GDKLSF_STRAIGHTTHROUGH_RX2";
		searchType = getRequest().getParameter("seachType");
		String result = initGDReplenishForLM(shopCode,tableName,finalPlayType,userInfoNew);
		if (result.equals("noPeriods")){
			return "noPeriods";
		}		
		
		return  this.ajaxText(JSONArray.toJSONString(replenishList_LM));
	}
	
	public String ajaxReplenishLmAttribute(){
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		
		String shopCode = userInfo.getSafetyCode();
		//String tableName = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME;
		String finalPlayType = this.getRequest().getParameter("typeCode");
		String avLose = this.getRequest().getParameter("avLoseForm");
		if(finalPlayType==null) finalPlayType="GDKLSF_STRAIGHTTHROUGH_RX2";
		String result = initGDReplenishForLM_Attribute(shopCode,finalPlayType,avLose,userInfoNew);
		/*if(result!=null){
			if (result.equals("noPeriods")){
				return "noPeriods";
			}		
		}*/
		
		//JSONArray jsonArrays = JSONArray.fromObject(replenishList_LM);
		//return  this.ajaxText(jsonArrays.toString());
		return  this.ajaxText(result);
		
	}
	
	public String ajaxReplenishNCLmAttribute(){
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfo);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		
		String shopCode = userInfo.getSafetyCode();
		//String tableName = Constant.NC_TABLE_NAME;
		String finalPlayType = this.getRequest().getParameter("typeCode");
		String avLose = this.getRequest().getParameter("avLoseForm");
		if(finalPlayType==null) finalPlayType="NC_STRAIGHTTHROUGH_RX2";
		String result = initNCReplenishForLM_Attribute(shopCode,finalPlayType,avLose,userInfoNew);
		return  this.ajaxText(result);
		
	}
	
	//广东连码
	public String initGDReplenishForLM(String shopCode,String tableName,String finalPlayType,ManagerUser userInfoNew){
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		//Map<String,String> fpStateMap = new HashMap<String,String>();//记录玩法的有没有封号
		//获取实时赔率信息
		List<String> list = new ArrayList<String>();
		list.add("GDKLSF_STRAIGHTTHROUGH%");
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        GDPeriodsInfo runningPeriods = new GDPeriodsInfo();
        runningPeriods = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			
			if(finalPlayType==null) finalPlayType="GDKLSF_STRAIGHTTHROUGH_RX2";
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.queryReplenish_LM(tableName, orgUserInfo, plate, periodsNum, searchType, finalPlayType);
			if(gdoddList != null && gdoddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : gdoddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = rVO.getPlayFinalType();
					
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							/*try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								//BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								//BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								//BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}*/
							
						}				
					}
					//排序
					
					if("GDKLSF_STRAIGHTTHROUGH_RX2".equals(typeCode)){
						rVO.setSortNo(1);
						//fpStateMap.put("RX2", shopodds.getState());
					}else if("GDKLSF_STRAIGHTTHROUGH_R2LZ".equals(typeCode)){
						rVO.setSortNo(3);
						//fpStateMap.put("R2LZ", shopodds.getState());
					}else if("GDKLSF_STRAIGHTTHROUGH_RX3".equals(typeCode)){
						rVO.setSortNo(4);
						//fpStateMap.put("RX3", shopodds.getState());
					}else if("GDKLSF_STRAIGHTTHROUGH_R3LZ".equals(typeCode)){
						rVO.setSortNo(6);
						//fpStateMap.put("R3LZ", shopodds.getState());
					}else if("GDKLSF_STRAIGHTTHROUGH_RX4".equals(typeCode)){
						rVO.setSortNo(7);
						//fpStateMap.put("RX4", shopodds.getState());
					}else if("GDKLSF_STRAIGHTTHROUGH_RX5".equals(typeCode)){
						rVO.setSortNo(8);
						//fpStateMap.put("RX5", shopodds.getState());
					}
					rVO.setFpState(shopodds.getState());
					rMap.put(shopodds.getPlayTypeCode(), rVO);
				}
				//选二连直和选三连直，这个是不能投注的。TB_PLAY_TYPE表里没有的。
				ReplenishVO rVO2 = new ReplenishVO();
				rVO2.setPlayTypeName("選二連直");
				rVO2.setSortNo(2);
				rVO2.setRealOdds(BigDecimal.ZERO);
				rVO2.setPlayFinalType("GDKLSF_STRAIGHTTHROUGH_R2LZHI");
				rMap.put("GDKLSF_STRAIGHTTHROUGH_R2LZHI", rVO2);
				
				ReplenishVO rVO3 = new ReplenishVO();
				rVO3.setPlayTypeName("選三連直");
				rVO3.setSortNo(5);
				rVO3.setRealOdds(BigDecimal.ZERO);
				rVO3.setPlayFinalType("GDKLSF_STRAIGHTTHROUGH_R3LZHI");
				rMap.put("GDKLSF_STRAIGHTTHROUGH_R3LZHI", rVO3);
			}
			
		}
		Map<String,String>  lastLotteryPeriods = getGDLastLotteryPeriods();//获取上期开奖结果
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		rMap = this.sortBySortNo_LM(rMap);
		this.getRequest().setAttribute("rMap", rMap);	
		//this.getRequest().setAttribute("fpStateMap", fpStateMap);	
		this.getRequest().setAttribute("radioState", finalPlayType);
		return "success";
	}
	
	//农场连码
	public String initNCReplenishForLM(String shopCode,String tableName,String finalPlayType,ManagerUser userInfoNew){
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		//Map<String,String> fpStateMap = new HashMap<String,String>();//记录玩法的有没有封号
		//获取实时赔率信息
		List<String> list = new ArrayList<String>();
		list.add("NC_STRAIGHTTHROUGH%");
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        NCPeriodsInfo runningPeriods = new NCPeriodsInfo();
        runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			
			periodsNum = runningPeriods.getPeriodsNum();
			
			if(finalPlayType==null) finalPlayType="NC_STRAIGHTTHROUGH_RX2";
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.queryReplenish_LM(tableName, orgUserInfo, plate, periodsNum, searchType, finalPlayType);
			if(gdoddList != null && gdoddList.size()>0)
			{
				for (ShopsPlayOdds shopodds : gdoddList) {
					ReplenishVO rVO = new ReplenishVO();          
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					String typeCode = rVO.getPlayFinalType();
					
					for (ReplenishVO v : petlist) {
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());	
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							rVO.setSortNo(v.getSortNo());
							
						}				
					}
					//排序
					
					if("NC_STRAIGHTTHROUGH_RX2".equals(typeCode)){
						rVO.setSortNo(1);
					}else if("NC_STRAIGHTTHROUGH_R2LZ".equals(typeCode)){
						rVO.setSortNo(3);
					}else if("NC_STRAIGHTTHROUGH_RX3".equals(typeCode)){
						rVO.setSortNo(4);
					}else if("NC_STRAIGHTTHROUGH_R3LZ".equals(typeCode)){
						rVO.setSortNo(6);
					}else if("NC_STRAIGHTTHROUGH_RX4".equals(typeCode)){
						rVO.setSortNo(7);
					}else if("NC_STRAIGHTTHROUGH_RX5".equals(typeCode)){
						rVO.setSortNo(8);
					}
					rVO.setFpState(shopodds.getState());
					rMap.put(shopodds.getPlayTypeCode(), rVO);
				}
				//选二连直和选三连直，这个是不能投注的。TB_PLAY_TYPE表里没有的。
				ReplenishVO rVO2 = new ReplenishVO();
				rVO2.setPlayTypeName("選二連直");
				rVO2.setSortNo(2);
				rVO2.setRealOdds(BigDecimal.ZERO);
				rVO2.setPlayFinalType("NC_STRAIGHTTHROUGH_R2LZHI");
				rMap.put("NC_STRAIGHTTHROUGH_R2LZHI", rVO2);
				
				ReplenishVO rVO3 = new ReplenishVO();
				rVO3.setPlayTypeName("選三連直");
				rVO3.setSortNo(5);
				rVO3.setRealOdds(BigDecimal.ZERO);
				rVO3.setPlayFinalType("NC_STRAIGHTTHROUGH_R3LZHI");
				rMap.put("NC_STRAIGHTTHROUGH_R3LZHI", rVO3);
			}
			
		}
		Map<String,String>  lastLotteryPeriods = getNCLastLotteryPeriods();//获取上期开奖结果
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		rMap = this.sortBySortNo_LM(rMap);
		this.getRequest().setAttribute("rMap", rMap);	
		//this.getRequest().setAttribute("fpStateMap", fpStateMap);	
		this.getRequest().setAttribute("radioState", finalPlayType);
		return "success";
	}
	
	//广东连码--按类型显示ATTRIBUTE的应补货额
	public String initGDReplenishForLM_Attribute(String shopCode,String finalPlayType,String tAvLose,ManagerUser userInfoNew){
		List<ReplenishVO> jsonArray = new ArrayList<ReplenishVO>();  //补货明细
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
		filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
		GDPeriodsInfo runningPeriods = new GDPeriodsInfo();
		runningPeriods = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		Integer avLose = 0;
		
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			if(finalPlayType==null) finalPlayType="GDKLSF_STRAIGHTTHROUGH_RX2";
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			List<ReplenishVO> petlist = replenishLogic.queryReplenish_LM_Attribute(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, orgUserInfo, plate, periodsNum, searchType, finalPlayType);
			
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add(finalPlayType);
			List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);//此方法只会搜出指定的一个或多个类型的赔率
			if(gdoddList != null && gdoddList.size()>0)
			{
					for (ReplenishVO v : petlist) {
						ReplenishVO rVO = new ReplenishVO();  
						ShopsPlayOdds shopodds = gdoddList.get(0);
						
						rVO.setPlayFinalType(shopodds.getPlayTypeCode());
						rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
						rVO.setAttribute(v.getAttribute());
						rVO.setCommissionMoney(v.getCommissionMoney().setScale(1,BigDecimal.ROUND_HALF_UP));
						rVO.setRateMoney(v.getRateMoney());
						
						BigDecimal odd = BigDecimal.ZERO;
						if(Constant.A.equals(plate)){
							odd =  shopodds.getRealOdds();
						}else if(Constant.B.equals(plate)){
							odd =  shopodds.getRealOddsB();
						}else{
							odd =  shopodds.getRealOddsC();
						}
						//計算可贏金額
						rVO.setWinMoney(v.getRateMoney().multiply((odd.subtract(BigDecimal.ONE))).setScale(2,BigDecimal.ROUND_HALF_UP));
						
						try {
							/*BigDecimal nLose = BigDecimal.ZERO;
							if(v.getMoney() ==0){
								nLose = BigDecimal.ZERO;
							}else{
								nLose = BigDecimal.valueOf(avLose).subtract(rVO.getLoseMoney());
							}
							Double commission = (double) 0;
							UserCommission userCommission = new UserCommission();
							UserCommissionDefault userCommissionDefault = new UserCommissionDefault();
							if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoSys.getUserType())){
								userCommissionDefault = userCommissionDefaultLogic.queryUserPlayTypeCommission(userInfoSys.getID(), userInfoSys.getUserType(), finalPlayType);
								if(plate.equals("A")){
									commission = userCommissionDefault.getCommissionA();
								}else if(plate.equals("B")){
									commission = userCommissionDefault.getCommissionB();
								}else{
									commission = userCommissionDefault.getCommissionC();
								}
							}else{
								userCommission = userCommissionLogic.queryUserPlayTypeCommission(userInfoSys.getID(), userInfoSys.getUserType(), finalPlayType);
								if(plate.equals("A")){
									commission = userCommission.getCommissionA();
								}else if(plate.equals("B")){
									commission = userCommission.getCommissionB();
								}else{
									commission = userCommission.getCommissionC();
								}
							}
							BigDecimal nOdd = odd.subtract(BigDecimal.ONE);
							BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(commission))).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);*/
							if(null!=tAvLose && ""!=tAvLose){
								//avLose = 1000000000;
								rVO.setAvLose((v.getRateMoney().subtract(BigDecimal.valueOf(avLose))).intValue());
							}
							/*else{
								avLose = Integer.valueOf(tAvLose);
							}*/
						} catch (Exception e) {
							log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
						}
						jsonArray.add(rVO);	
					}
			}
			
		}
		return  JSONArray.toJSONString(jsonArray);
		//return  this.ajaxText(jsonArrays.toString());
	}
	
	//农场连码--按类型显示ATTRIBUTE的应补货额
	public String initNCReplenishForLM_Attribute(String shopCode,String finalPlayType,String tAvLose,ManagerUser userInfoNew){
		List<ReplenishVO> jsonArray = new ArrayList<ReplenishVO>();  //补货明细
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
		filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
		NCPeriodsInfo runningPeriods = new NCPeriodsInfo();
		runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		Integer avLose = 0;
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
			if(finalPlayType==null) finalPlayType="NC_STRAIGHTTHROUGH_RX2";
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			List<ReplenishVO> petlist = replenishLogic.queryReplenish_LM_Attribute(Constant.NC_TABLE_NAME, orgUserInfo, plate, periodsNum, searchType, finalPlayType);
			
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add(finalPlayType);
			List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);//此方法只会搜出指定的一个或多个类型的赔率
			if(gdoddList != null && gdoddList.size()>0)
			{
				for (ReplenishVO v : petlist) {
					ReplenishVO rVO = new ReplenishVO();  
					ShopsPlayOdds shopodds = gdoddList.get(0);
					
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
					rVO.setAttribute(v.getAttribute());
					rVO.setCommissionMoney(v.getCommissionMoney().setScale(1,BigDecimal.ROUND_HALF_UP));
					rVO.setRateMoney(v.getRateMoney());
					
					BigDecimal odd = BigDecimal.ZERO;
					if(Constant.A.equals(plate)){
						odd =  shopodds.getRealOdds();
					}else if(Constant.B.equals(plate)){
						odd =  shopodds.getRealOddsB();
					}else{
						odd =  shopodds.getRealOddsC();
					}
					//計算可贏金額
					rVO.setWinMoney(v.getRateMoney().multiply((odd.subtract(BigDecimal.ONE))).setScale(2,BigDecimal.ROUND_HALF_UP));
					
					try {
						if(null!=tAvLose && ""!=tAvLose){
							rVO.setAvLose((v.getRateMoney().subtract(BigDecimal.valueOf(avLose))).intValue());
						}
					} catch (Exception e) {
						log.info("计算农场补货时转换BigDecimal错误:"+e.getMessage());
					}
					jsonArray.add(rVO);	
				}
			}
			
		}
		return  JSONArray.toJSONString(jsonArray);
		//return  this.ajaxText(jsonArrays.toString());
	}
	
	
	//香港过关
	/*public String initHK_GG(String shopCode,String tableName){
		List<ReplenishVO> replenishList = new ArrayList<ReplenishVO>();
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryHKRealOdds(shopCode,"HK");
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		String finalPlayType = "HK_GG";
		ShopsPlayOdds shopodds = null;
		for (int i = 0; i < gdoddList.size(); i++) {
			shopodds = gdoddList.get(i);
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		this.getRequest().setAttribute("map", shopOddMap);		
		
		HKPeriods runningPeriods = skperiodsInfoLogic.queryRunningPeriods(shopCode);
		
		periodsNum = runningPeriods.getPeriodsNum();
		ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		ManagerUser orgUserInfo = new ManagerUser();
		try {
			BeanUtils.copyProperties(orgUserInfo, userInfoSys);
		} catch (Exception e) {
			log.info("出错"+ e.getMessage());
		}
		replenishList = replenishLogic.queryReplenish_LM(tableName, orgUserInfo, plate, periodsNum, searchType, finalPlayType);
		String attChinaName = "";
		ReplenishVO vo = null;
		for (int j = 0; j < replenishList.size(); j++) {
			vo = replenishList.get(j);
			BigDecimal dMoney = BigDecimal.ZERO;
			dMoney = BigDecimal.valueOf(vo.getMoney());	
			vo.setMoney(dMoney.intValue());
			String[] ss = vo.getAttribute().split("\\|");
			for(int i = 0; i < ss.length; i++){
				String[] sub = ss[i].split("_");
				String maCode = MA_CODE.get(sub[0]);
				String betCode =BET_CODE.get(sub[1]);					
				String tempChinaName = "(" + maCode + "_" + betCode + ")";
				if (i==0){
				   attChinaName = tempChinaName;
				}else{
				   attChinaName = attChinaName + tempChinaName;
				}
				vo.setAttributeChinaName(attChinaName);
			}
		}
		
		this.getRequest().setAttribute("list", replenishList);	
		this.getRequest().setAttribute("periodsNum", periodsNum);
		return "success";
	}*/
	
	//广东总和龙虎
	public String initGDReplenishForLh(String shopCode,String tableName,ManagerUser userInfoNew){
		
		Integer dxPetTotal = 0;    //大小总投注额
		Integer dsPetTotal = 0;    //单双总投注额
		Integer wdxPetTotal = 0;    //尾大小总投注额
		Integer lhPetTotal = 0;    //中发白总投注额
		
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
        GDPeriodsInfo runningPeriods = new GDPeriodsInfo();
        runningPeriods = periodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			
			periodsNum = runningPeriods.getPeriodsNum();
			
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("GDKLSF_DOUBLESIDE_ZH%");
			list.add("GDKLSF_DOUBLESIDE_LONG%");
			list.add("GDKLSF_DOUBLESIDE_HU%");
			List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//查询该用户当期的各种玩法的赔额合计、注额合计、占成、拥金
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetListForLh(tableName, orgUserInfo, plate, periodsNum,searchType);
			
			if(gdoddList !=null && gdoddList.size()>0)
			{
			for (int i = 0; i < gdoddList.size(); i++) {
				ShopsPlayOdds shopodds=gdoddList.get(i);
				ReplenishVO rVO = new ReplenishVO();     
				rVO.setPlayFinalType(shopodds.getPlayTypeCode());
				rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));	
				
				rVO.setFpState(shopodds.getState());
				for(int ii=0; ii<petlist.size(); ii++){
					ReplenishVO v = petlist.get(ii);
					String petPlayFinalType = v.getPlayFinalType();
					if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
						v.setRealOdds(this.getRealOddsFromPlate(shopodds));
						BigDecimal dMoney = BigDecimal.ZERO;
						dMoney = BigDecimal.valueOf(v.getMoney());
						if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
							if(dMoney.compareTo(BigDecimal.ZERO) ==-1)
							{
								//BossLog  log =  new BossLog();
								//log.setLogMessage("注額異常：注額為 "+dMoney+", 當前shopCode:"+shopCode+" ,playType: "+v.getPlayFinalType());
								//this.bossLogLogic.saveLog(log);
								
								String title = "<--注额异常-注额为负: shopCode:"+shopCode+" userType:"+userInfo.getUserType()+" userID:"+userInfo.getID()+" 盘期："+periodsNum+" typeCode："+v.getPlayFinalType()+" 总额："+dMoney+"-->";
								throw new IllegalArgumentException(title);
							}
						}
						if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHX")){
							//返回值    -1 小于   0 等于    1 大于
							if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDA")){
								rVO.setSortNo(1);
							}else{
								rVO.setSortNo(2);
							}
							dxPetTotal += v.getMoney();	
						}else
							if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHS")){
								if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDAN")){
									rVO.setSortNo(3);
								}else{
									rVO.setSortNo(4);
								}
								dsPetTotal += v.getMoney();		
							}else
								if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHWD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHWX")){	
									if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHWD")){
										rVO.setSortNo(5);
									}else{
										rVO.setSortNo(6);
									}
									wdxPetTotal += v.getMoney();
								}else
									if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_LONG") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_HU")){	
										if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_LONG")){
											rVO.setSortNo(7);
										}else{
											rVO.setSortNo(8);
										}
										lhPetTotal += v.getMoney();
									}
						
						rVO.setMoney(dMoney.intValue());	
						rVO.setRMoney(v.getRMoney());
						rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
						
						//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
						if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
							rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
						}
						
						//紫色字公式【 平均亏损值-亏盈值】/【（赔率-1）+(100-退水）%】  ，結果為四舍五入
						try {
							BigDecimal nLose = BigDecimal.ZERO;
							if(null==avLose){
								avLose = DEFAULT_AVLOSE;
							}
							if(rVO.getMoney() ==0){
								nLose = BigDecimal.ZERO;
							}else{
								nLose = avLose.subtract(rVO.getLoseMoney());
							}
							BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
							BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
							rVO.setAvLose(nTotal.intValue());
						} catch (Exception e) {
							log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
						}
					}				
				}
				rMap.put(shopodds.getPlayTypeCode(), rVO);
			}
			}else{
				List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		    	filtersPeriodInfo.add(Restrictions.eq("playType","GDKLSF"));
				List<PlayType> ptList = playTypeLogic.findPlayType(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
				for (int i = 0; i < ptList.size(); i++) {
					rMap.put(ptList.get(i).getTypeCode(), new ReplenishVO());
				}
			}
			
		}
		
		 rMap = this.sortBySortNo(rMap);
		
		 Map<String,String>  lastLotteryPeriods = getGDLastLotteryPeriods();//获取上期开奖结果
		 
		 getSideAndDragonRank(Constant.LOTTERY_TYPE_GDKLSF);//统计两面长龙
		 try {
			replenishRightBar(Constant.LOTTERY_TYPE_GDKLSF);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//总额和遗漏
			
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		this.getRequest().setAttribute("rMap", rMap);	
		this.getRequest().setAttribute("periodsNum", periodsNum);
		this.getRequest().setAttribute("avLose", avLose);
		this.getRequest().setAttribute("avOdd", avOdd);
		this.getRequest().setAttribute("avCommission", avCommission);
		this.getRequest().setAttribute("RunningPeriods", runningPeriods);
		
		return "success";
	}
	
	//农场总和龙虎
	public String initNCReplenishForLh(String shopCode,String tableName,ManagerUser userInfoNew){
		
		Integer dxPetTotal = 0;    //大小总投注额
		Integer dsPetTotal = 0;    //单双总投注额
		Integer wdxPetTotal = 0;    //尾大小总投注额
		Integer lhPetTotal = 0;    //中发白总投注额
		
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		NCPeriodsInfo runningPeriods = new NCPeriodsInfo();
		runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime));
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			
			periodsNum = runningPeriods.getPeriodsNum();
			
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
				if(Constant.OPEN.equals(userVO.getReport())){
					orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
					orgUserInfo.setID(userVO.getChiefID().longValue());
				}
			}
			
			//获取实时赔率信息
			List<String> list = new ArrayList<String>();
			list.add("NC_DOUBLESIDE_ZH%");
			list.add("NC_DOUBLESIDE_LONG%");
			list.add("NC_DOUBLESIDE_HU%");
			List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryShopRealOddsByLoop(shopCode,list);
			//查询该用户当期的各种玩法的赔额合计、注额合计、占成、拥金
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetListForLh_NC(tableName, orgUserInfo, plate, periodsNum,searchType);
			
			if(gdoddList !=null && gdoddList.size()>0)
			{
				for (int i = 0; i < gdoddList.size(); i++) {
					ShopsPlayOdds shopodds=gdoddList.get(i);
					ReplenishVO rVO = new ReplenishVO();     
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));	
					
					rVO.setFpState(shopodds.getState());
					for(int ii=0; ii<petlist.size(); ii++){
						ReplenishVO v = petlist.get(ii);
						String petPlayFinalType = v.getPlayFinalType();
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());
							if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
								if(dMoney.compareTo(BigDecimal.ZERO) ==-1)
								{
									//BossLog  log =  new BossLog();
									//log.setLogMessage("注額異常：注額為 "+dMoney+", 當前shopCode:"+shopCode+" ,playType: "+v.getPlayFinalType());
									//this.bossLogLogic.saveLog(log);
									
									String title = "<--注额异常-注额为负: shopCode:"+shopCode+" userType:"+userInfo.getUserType()+" userID:"+userInfo.getID()+" 盘期："+periodsNum+" typeCode："+v.getPlayFinalType()+" 总额："+dMoney+"-->";
									throw new IllegalArgumentException(title);
								}
							}
							if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHX")){
								//返回值    -1 小于   0 等于    1 大于
								if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDA")){
									rVO.setSortNo(1);
								}else{
									rVO.setSortNo(2);
								}
								dxPetTotal += v.getMoney();	
							}else
								if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHS")){
									if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDAN")){
										rVO.setSortNo(3);
									}else{
										rVO.setSortNo(4);
									}
									dsPetTotal += v.getMoney();		
								}else
									if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHWD") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHWX")){	
										if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHWD")){
											rVO.setSortNo(5);
										}else{
											rVO.setSortNo(6);
										}
										wdxPetTotal += v.getMoney();
									}else
										if(petPlayFinalType.equals("NC_DOUBLESIDE_LONG") || petPlayFinalType.equals("NC_DOUBLESIDE_HU")){	
											if(petPlayFinalType.equals("NC_DOUBLESIDE_LONG")){
												rVO.setSortNo(7);
											}else{
												rVO.setSortNo(8);
											}
											lhPetTotal += v.getMoney();
										}
							
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							
							//紫色字公式【 平均亏损值-亏盈值】/【（赔率-1）+(100-退水）%】  ，結果為四舍五入
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
						}				
					}
					rMap.put(shopodds.getPlayTypeCode(), rVO);
				}
			}else{
				List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
				filtersPeriodInfo.add(Restrictions.eq("playType","NC"));
				List<PlayType> ptList = playTypeLogic.findPlayType(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
				for (int i = 0; i < ptList.size(); i++) {
					rMap.put(ptList.get(i).getTypeCode(), new ReplenishVO());
				}
			}
			
		}
		
		rMap = this.sortBySortNo(rMap);
		
		Map<String,String>  lastLotteryPeriods = getNCLastLotteryPeriods();//获取上期开奖结果
		
		getSideAndDragonRank(Constant.LOTTERY_TYPE_NC);//统计两面长龙
		try {
			replenishRightBar(Constant.LOTTERY_TYPE_NC);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//总额和遗漏
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		this.getRequest().setAttribute("rMap", rMap);	
		this.getRequest().setAttribute("periodsNum", periodsNum);
		this.getRequest().setAttribute("avLose", avLose);
		this.getRequest().setAttribute("avOdd", avOdd);
		this.getRequest().setAttribute("avCommission", avCommission);
		this.getRequest().setAttribute("RunningPeriods", runningPeriods);
		
		return "success";
	}
	
	//广东
	public String initGDReplenish(String shopCode,String tableName,String ballNum,String preName,ManagerUser userInfoNew)
	{		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal topLoseMoney = BigDecimal.ZERO;
		BigDecimal topWinMoney = BigDecimal.ZERO;
		//最高亏损
		BigDecimal ballTopLostMoney = BigDecimal.ZERO;
		BigDecimal dxTopLostMoney = BigDecimal.ZERO;
		BigDecimal dsTopLostMoney = BigDecimal.ZERO;
		BigDecimal wTopLostMoney = BigDecimal.ZERO;
		BigDecimal hsTopLostMoney = BigDecimal.ZERO;
		BigDecimal fwTopLostMoney = BigDecimal.ZERO;
		BigDecimal zfbTopLostMoney = BigDecimal.ZERO;
		//最高盈利
		BigDecimal ballTopWinMoney = BigDecimal.ZERO;
		BigDecimal dxTopWinMoney = BigDecimal.ZERO;
		BigDecimal dsTopWinMoney = BigDecimal.ZERO;
		BigDecimal wTopWinMoney = BigDecimal.ZERO;
		BigDecimal hsTopWinMoney = BigDecimal.ZERO;
		BigDecimal fwTopWinMoney = BigDecimal.ZERO;
		BigDecimal zfbTopWinMoney = BigDecimal.ZERO;
		
		Integer ballPetTotal = 0;    //号球总投注额
		Integer dxPetTotal = 0;    //大小总投注额
		Integer dsPetTotal = 0;    //单双总投注额
		Integer wdxPetTotal = 0;    //尾大小总投注额
		Integer hsdsPetTotal = 0;    //合数单双总投注额
		Integer fwPetTotal = 0;    //方位总投注额
		Integer zfbPetTotal = 0;    //中发白总投注额
		
		
		
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> rBallMap=new HashMap<String,ReplenishVO>();
		
		BigDecimal tmpWinMoney = BigDecimal.ZERO;
		BigDecimal tmpMoney = BigDecimal.ZERO;
		
		//获取实时赔率信息		
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryGDKLSFRealOddsGroupByBall(shopCode,"GDKLSF_BALL_" + preName,"GDKLSF_DOUBLESIDE_"+ballNum);
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        GDPeriodsInfo runningPeriods = new GDPeriodsInfo();
        runningPeriods = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime) );
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			periodsNum = runningPeriods.getPeriodsNum();
	
			//ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			
			//查询该用户当期的各种玩法的赔额合计、注额合计、占成、拥金
			ManagerUser orgUserInfo = new ManagerUser();
			try {
				BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
	  			if(Constant.OPEN.equals(userVO.getReport())){
	  				orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
	  				orgUserInfo.setID(userVO.getChiefID().longValue());
	  			}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList(tableName, orgUserInfo, plate, periodsNum,searchType,ballNum,preName);
			if(gdoddList!=null && gdoddList.size()>0){
				for(ShopsPlayOdds shopodds: gdoddList){
					ReplenishVO rVO = new ReplenishVO();
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					
					rVO.setFpState(shopodds.getState());
					for(ReplenishVO v : petlist){
						String petPlayFinalType = v.getPlayFinalType();
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							BigDecimal tMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());
							tMoney = v.getRateMoney();
							total = total.add(dMoney);
							tmpWinMoney = v.getLoseMoney();
							
							//.intValue()
							tmpMoney = v.getLoseMoney();
							if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_X")){
								if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA")){
									rVO.setSortNo(1);
								}else{
									rVO.setSortNo(2);
								}
								//返回值    -1 小于   0 等于    1 大于
								if (tmpMoney.compareTo(dxTopLostMoney)==-1){ dxTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(dxTopWinMoney)==1){ dxTopWinMoney = tmpWinMoney; }
								dxPetTotal += v.getMoney();	
							}else if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_S")){
									if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN")){
										rVO.setSortNo(3);
									}else{
										rVO.setSortNo(4);
									}
									if (tmpMoney.compareTo(dsTopLostMoney)==-1){ dsTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(dsTopWinMoney)==1){ dsTopWinMoney = tmpWinMoney; }
									dsPetTotal += v.getMoney();		
							}else if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WX")){	
									if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD")){
										rVO.setSortNo(5);
									}else{
										rVO.setSortNo(6);
									}
									if (tmpMoney.compareTo(wTopLostMoney)==-1){ wTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(wTopWinMoney)==1){ wTopWinMoney = tmpWinMoney; }
									wdxPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSS")){
									if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD")){
										rVO.setSortNo(7);
									}else{
										rVO.setSortNo(8);
									}
									if (tmpMoney.compareTo(hsTopLostMoney)==-1){ hsTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(hsTopWinMoney)==1){ hsTopWinMoney = tmpWinMoney; }
									hsdsPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_DONG") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_NAN") ||
													petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_XI") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_BEI")){	
									
								    if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DONG")){
										rVO.setSortNo(9);
									}else if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_NAN")){
										rVO.setSortNo(10);
									}else if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_XI")){
										rVO.setSortNo(11);
									}else{
										rVO.setSortNo(12);
									}
									if (tmpMoney.compareTo(fwTopLostMoney)==-1){ fwTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(fwTopWinMoney)==1){ fwTopWinMoney = tmpWinMoney; }
									fwPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_Z") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_F") ||
														petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_B")){
								
									if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_Z")){
										rVO.setSortNo(13);
									}else if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_F")){
										rVO.setSortNo(14);
									}else{
										rVO.setSortNo(15);
									}
									if (tmpMoney.compareTo(zfbTopLostMoney)==-1){ zfbTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(zfbTopWinMoney)==1){ zfbTopWinMoney = tmpWinMoney; }											
									zfbPetTotal += v.getMoney();
							}else{
									if (tmpMoney.compareTo(ballTopLostMoney)==-1){ ballTopLostMoney = tmpMoney; }
									if (tmpWinMoney.compareTo(ballTopWinMoney)==1){ ballTopWinMoney = tmpWinMoney; }
									ballPetTotal += v.getMoney();
							}
							
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							//紫色字公式【 平均亏损值-亏盈值】/【（赔率-1）+(100-退水）%】  ，結果為四舍五入
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
							
						}				
					}
					if(!rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_Z") && !rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_F") &&
							!rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_B") &&
							!rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_DONG") && !rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_NAN") &&
							!rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_XI") && !rVO.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_BEI") &&
							rVO.getPlayFinalType().indexOf("GDKLSF_DOUBLESIDE_")==-1)
					{	
						rBallMap.put(shopodds.getPlayTypeCode(), rVO);
					}else{
						rMap.put(shopodds.getPlayTypeCode(), rVO);
					}
				    
			  }
			
			//由于封盘之后依然要显示赔率，所以先把这段注释
			/*}
			else{
				List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		    	filtersPeriodInfo.add(Restrictions.eq("playType","GDKLSF"));
				List<PlayType> ptList = playTypeLogic.findPlayType(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
				for (int i = 0; i < ptList.size(); i++) {
					rMap.put(ptList.get(i).getTypeCode(), new ReplenishVO());
				}*/
			}
			//计算最高盈利
			
			topWinMoney = ballTopWinMoney.add(zfbTopWinMoney).add(fwTopWinMoney).add(hsTopWinMoney).add(wTopWinMoney).add(dsTopWinMoney).add(dxTopWinMoney);
			//计算最高亏损
			topLoseMoney = (ballTopLostMoney.add(zfbTopLostMoney).add(fwTopLostMoney).add(hsTopLostMoney).add(wTopLostMoney).add(dsTopLostMoney).add(dxTopLostMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
			
		}
		if("lose".equals(searchArray)){
        	rBallMap = this.sortByLose(this.sortByBall(rBallMap)); 
        }
		Map<String,ReplenishVO> sorMap=new HashMap<String,ReplenishVO>();
        if("ball".equals(searchArray)){
        	rBallMap = this.sortByBall(rBallMap); 
        	if("lose".equals(loseSort)){
        		sorMap = this.sortByLose(rBallMap); 
        	}else{
        		sorMap = this.sortByWin(rBallMap); 
        	}
        }
        
        rMap = this.sortBySortNo(rMap);//右边栏排序
        
        getSideAndDragonRank(Constant.LOTTERY_TYPE_GDKLSF);//统计两面长龙
		try {
			//总额和遗漏
			replenishRightBar(Constant.LOTTERY_TYPE_GDKLSF);
		} catch (IllegalAccessException e) {
			log.info("计算补货信息异常"+e.getMessage());
		} catch (InvocationTargetException e) {
			log.info("计算补货信息异常"+e.getMessage());
		}
		
		
		Map<String,String>  lastLotteryPeriods = getGDLastLotteryPeriods();//获取上期开奖结果
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		this.getRequest().setAttribute("GDShopOddsMap", rMap);	
		this.getRequest().setAttribute("sorMap", sorMap);	//按号球排序时用
		this.getRequest().setAttribute("BallMap", rBallMap);	
		this.getRequest().setAttribute("total", total);	
		this.getRequest().setAttribute("topLoseMoney", topLoseMoney.setScale(0,BigDecimal.ROUND_HALF_UP));	
		this.getRequest().setAttribute("topWinMoney", topWinMoney.setScale(0,BigDecimal.ROUND_HALF_UP));	
		this.getRequest().setAttribute("periodsNum", periodsNum);
		this.getRequest().setAttribute("avLose", avLose);
		this.getRequest().setAttribute("avOdd", avOdd);
		this.getRequest().setAttribute("avCommission", avCommission);
		
		if("lose".equals(searchArray)){
			return "2columns";
		}else{
			return "3columns";
		}
	}
	
	//农场
	public String initNCReplenish(String shopCode,String tableName,String ballNum,String preName,ManagerUser userInfoNew) throws IllegalAccessException, InvocationTargetException
	{		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal topLoseMoney = BigDecimal.ZERO;
		BigDecimal topWinMoney = BigDecimal.ZERO;
		//最高亏损
		BigDecimal ballTopLostMoney = BigDecimal.ZERO;
		BigDecimal dxTopLostMoney = BigDecimal.ZERO;
		BigDecimal dsTopLostMoney = BigDecimal.ZERO;
		BigDecimal wTopLostMoney = BigDecimal.ZERO;
		BigDecimal hsTopLostMoney = BigDecimal.ZERO;
		BigDecimal fwTopLostMoney = BigDecimal.ZERO;
		BigDecimal zfbTopLostMoney = BigDecimal.ZERO;
		//最高盈利
		BigDecimal ballTopWinMoney = BigDecimal.ZERO;
		BigDecimal dxTopWinMoney = BigDecimal.ZERO;
		BigDecimal dsTopWinMoney = BigDecimal.ZERO;
		BigDecimal wTopWinMoney = BigDecimal.ZERO;
		BigDecimal hsTopWinMoney = BigDecimal.ZERO;
		BigDecimal fwTopWinMoney = BigDecimal.ZERO;
		BigDecimal zfbTopWinMoney = BigDecimal.ZERO;
		
		Integer ballPetTotal = 0;    //号球总投注额
		Integer dxPetTotal = 0;    //大小总投注额
		Integer dsPetTotal = 0;    //单双总投注额
		Integer wdxPetTotal = 0;    //尾大小总投注额
		Integer hsdsPetTotal = 0;    //合数单双总投注额
		Integer fwPetTotal = 0;    //方位总投注额
		Integer zfbPetTotal = 0;    //中发白总投注额
		
		
		
		Map<String,ReplenishVO> rMap=new HashMap<String,ReplenishVO>();
		Map<String,ReplenishVO> rBallMap=new HashMap<String,ReplenishVO>();
		
		BigDecimal tmpWinMoney = BigDecimal.ZERO;
		BigDecimal tmpMoney = BigDecimal.ZERO;
		
		//获取实时赔率信息
		//这里之所以要固定取出某，是因为对于没有实货的类型也要显示赔率。
		List<ShopsPlayOdds> oddList=shopOddsLogic.queryGDKLSFRealOddsGroupByBall(shopCode,"NC_BALL_" + preName,"NC_DOUBLESIDE_"+ballNum);
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
		filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
		NCPeriodsInfo runningPeriods = new NCPeriodsInfo();
		runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriods==null){
			return "noPeriods";
		}else{
			Date now=new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
			long lotteryTime=runningPeriods.getLotteryTime().getTime()-new Date().getTime();
			long stopTime=runningPeriods.getStopQuotTime().getTime()-new Date().getTime();
			this.getRequest().setAttribute("KjTime", Long.valueOf(lotteryTime) );
			this.getRequest().setAttribute("StopTime", Long.valueOf(stopTime));
			
			String pState = systemLogic.findPeriodState(Constant.LOTTERY_TYPE_NC, userInfoNew.getSafetyCode());
			if(Constant.CLOSE.equals(pState)) runningPeriods.setState(Constant.STOP_STATUS);
			this.getRequest().setAttribute("RunningPeriods", runningPeriods);
			
			periodsNum = runningPeriods.getPeriodsNum();
			
			//查询该用户当期的各种玩法的赔额合计、注额合计、占成、拥金
			ManagerUser orgUserInfo = new ManagerUser();
			BeanUtils.copyProperties(orgUserInfo, userInfoNew);
			
			//判断是否以公司占查询
			if(Constant.COMPANY_STATUS.equals(searchType) && ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
				UserVO userVO =  commonUserLogic.getUserVo(orgUserInfo);
				if(Constant.OPEN.equals(userVO.getReport())){
					orgUserInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
					orgUserInfo.setID(userVO.getChiefID().longValue());
				}
			}
			
			List<ReplenishVO> petlist = replenishLogic.findReplenishPetList_NC(tableName, orgUserInfo, plate, periodsNum,searchType,ballNum,preName);
			if(oddList!=null && oddList.size()>0){
				for(ShopsPlayOdds shopodds: oddList){
					ReplenishVO rVO = new ReplenishVO();
					rVO.setPlayFinalType(shopodds.getPlayTypeCode());
					rVO.setRealOdds(this.getRealOddsFromPlate(shopodds));
					
					rVO.setFpState(shopodds.getState());
					for(ReplenishVO v : petlist){
						String petPlayFinalType = v.getPlayFinalType();
						if (shopodds.getPlayTypeCode().equals(v.getPlayFinalType())){	    //找到与赔率表匹配的记录
							v.setRealOdds(this.getRealOddsFromPlate(shopodds));
							BigDecimal dMoney = BigDecimal.ZERO;
							BigDecimal tMoney = BigDecimal.ZERO;
							dMoney = BigDecimal.valueOf(v.getMoney());
							tMoney = v.getRateMoney();
							total = total.add(dMoney);
							tmpWinMoney = v.getLoseMoney();
							
							//.intValue()
							tmpMoney = v.getLoseMoney();
							if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_X")){
								if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DA")){
									rVO.setSortNo(1);
								}else{
									rVO.setSortNo(2);
								}
								//返回值    -1 小于   0 等于    1 大于
								if (tmpMoney.compareTo(dxTopLostMoney)==-1){ dxTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(dxTopWinMoney)==1){ dxTopWinMoney = tmpWinMoney; }
								dxPetTotal += v.getMoney();	
							}else if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_S")){
								if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DAN")){
									rVO.setSortNo(3);
								}else{
									rVO.setSortNo(4);
								}
								if (tmpMoney.compareTo(dsTopLostMoney)==-1){ dsTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(dsTopWinMoney)==1){ dsTopWinMoney = tmpWinMoney; }
								dsPetTotal += v.getMoney();		
							}else if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_WD") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_WX")){	
								if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_WD")){
									rVO.setSortNo(5);
								}else{
									rVO.setSortNo(6);
								}
								if (tmpMoney.compareTo(wTopLostMoney)==-1){ wTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(wTopWinMoney)==1){ wTopWinMoney = tmpWinMoney; }
								wdxPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_HSD") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_HSS")){
								if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_HSD")){
									rVO.setSortNo(7);
								}else{
									rVO.setSortNo(8);
								}
								if (tmpMoney.compareTo(hsTopLostMoney)==-1){ hsTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(hsTopWinMoney)==1){ hsTopWinMoney = tmpWinMoney; }
								hsdsPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("NC_BALL_" +preName+"_DONG") || petPlayFinalType.equals("NC_BALL_" +preName+"_NAN") ||
									petPlayFinalType.equals("NC_BALL_" +preName+"_XI") || petPlayFinalType.equals("NC_BALL_" +preName+"_BEI")){	
								
								if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DONG")){
									rVO.setSortNo(9);
								}else if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_NAN")){
									rVO.setSortNo(10);
								}else if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_XI")){
									rVO.setSortNo(11);
								}else{
									rVO.setSortNo(12);
								}
								if (tmpMoney.compareTo(fwTopLostMoney)==-1){ fwTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(fwTopWinMoney)==1){ fwTopWinMoney = tmpWinMoney; }
								fwPetTotal += v.getMoney();
							}else if(petPlayFinalType.equals("NC_BALL_" +preName+"_Z") || petPlayFinalType.equals("NC_BALL_" +preName+"_F") ||
									petPlayFinalType.equals("NC_BALL_" +preName+"_B")){
								
								if(petPlayFinalType.equals("NC_BALL_" +preName+"_Z")){
									rVO.setSortNo(13);
								}else if(petPlayFinalType.equals("NC_BALL_" +preName+"_F")){
									rVO.setSortNo(14);
								}else{
									rVO.setSortNo(15);
								}
								if (tmpMoney.compareTo(zfbTopLostMoney)==-1){ zfbTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(zfbTopWinMoney)==1){ zfbTopWinMoney = tmpWinMoney; }											
								zfbPetTotal += v.getMoney();
							}else{
								if (tmpMoney.compareTo(ballTopLostMoney)==-1){ ballTopLostMoney = tmpMoney; }
								if (tmpWinMoney.compareTo(ballTopWinMoney)==1){ ballTopWinMoney = tmpWinMoney; }
								ballPetTotal += v.getMoney();
							}
							
							rVO.setMoney(dMoney.intValue());	
							rVO.setRMoney(v.getRMoney());
							rVO.setLoseMoney(v.getLoseMoney().setScale(0,BigDecimal.ROUND_HALF_UP)); //DECIMAL保留兩位小數四舍五入
							
							//把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
							if(!Constant.COMPANY_STATUS.equals(searchType) && !ManagerStaff.USER_TYPE_BRANCH.equals(orgUserInfo.getUserType())){
								rVO.setOverLoseQuatas(this.valiteOverLoseQuatas(rVO.getLoseMoney(), v.getPlayFinalType(), shopCode));
							}
							//紫色字公式【 平均亏损值-亏盈值】/【（赔率-1）+(100-退水）%】  ，結果為四舍五入
							try {
								BigDecimal nLose = BigDecimal.ZERO;
								if(null==avLose){
									avLose = DEFAULT_AVLOSE;
								}
								if(rVO.getMoney() ==0){
									nLose = BigDecimal.ZERO;
								}else{
									nLose = avLose.subtract(rVO.getLoseMoney());
								}
								BigDecimal nOdd = avOdd.subtract(BigDecimal.ONE);
								BigDecimal nCommission = (BigDecimal.valueOf(100).subtract(avCommission)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
								BigDecimal nTotal = nLose.divide(nOdd.add(nCommission), 0, BigDecimal.ROUND_HALF_UP);
								rVO.setAvLose(nTotal.intValue());
							} catch (Exception e) {
								log.info("计算广东补货时转换BigDecimal错误:"+e.getMessage());
							}
							
						}				
					}
					if(!rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_Z") && !rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_F") &&
							!rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_B") &&
							!rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_DONG") && !rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_NAN") &&
							!rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_XI") && !rVO.getPlayFinalType().equals("NC_BALL_" +preName+"_BEI") &&
							rVO.getPlayFinalType().indexOf("NC_DOUBLESIDE_")==-1)
					{	
						rBallMap.put(shopodds.getPlayTypeCode(), rVO);
					}else{
						rMap.put(shopodds.getPlayTypeCode(), rVO);
					}
					
				}
				
				//由于封盘之后依然要显示赔率，所以先把这段注释
				/*}
			else{
				List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		    	filtersPeriodInfo.add(Restrictions.eq("playType","NC"));
				List<PlayType> ptList = playTypeLogic.findPlayType(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
				for (int i = 0; i < ptList.size(); i++) {
					rMap.put(ptList.get(i).getTypeCode(), new ReplenishVO());
				}*/
			}
			//计算最高盈利
			
			topWinMoney = ballTopWinMoney.add(zfbTopWinMoney).add(fwTopWinMoney).add(hsTopWinMoney).add(wTopWinMoney).add(dsTopWinMoney).add(dxTopWinMoney);
			//计算最高亏损
			topLoseMoney = (ballTopLostMoney.add(zfbTopLostMoney).add(fwTopLostMoney).add(hsTopLostMoney).add(wTopLostMoney).add(dsTopLostMoney).add(dxTopLostMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
			
		}
		if("lose".equals(searchArray)){
			rBallMap = this.sortByLose(this.sortByBall(rBallMap)); 
		}
		Map<String,ReplenishVO> sorMap=new HashMap<String,ReplenishVO>();
		if("ball".equals(searchArray)){
			rBallMap = this.sortByBall(rBallMap); 
			if("lose".equals(loseSort)){
				sorMap = this.sortByLose(rBallMap); 
			}else{
				sorMap = this.sortByWin(rBallMap); 
			}
		}
		
		rMap = this.sortBySortNo(rMap);//右边栏排序
		
		getSideAndDragonRank(Constant.LOTTERY_TYPE_NC);//统计两面长龙
		replenishRightBar(Constant.LOTTERY_TYPE_NC);//总额和遗漏
		
		Map<String,String>  lastLotteryPeriods = getNCLastLotteryPeriods();//获取上期开奖结果
		
		this.getRequest().setAttribute("lastLotteryPeriods", lastLotteryPeriods);	
		this.getRequest().setAttribute("NCShopOddsMap", rMap);	
		this.getRequest().setAttribute("sorMap", sorMap);	//按号球排序时用
		this.getRequest().setAttribute("BallMap", rBallMap);	
		this.getRequest().setAttribute("total", total);	
		this.getRequest().setAttribute("topLoseMoney", topLoseMoney.setScale(0,BigDecimal.ROUND_HALF_UP));	
		this.getRequest().setAttribute("topWinMoney", topWinMoney.setScale(0,BigDecimal.ROUND_HALF_UP));	
		this.getRequest().setAttribute("periodsNum", periodsNum);
		this.getRequest().setAttribute("avLose", avLose);
		this.getRequest().setAttribute("avOdd", avOdd);
		this.getRequest().setAttribute("avCommission", avCommission);
		
		if("lose".equals(searchArray)){
			return "2columns";
		}else{
			return "3columns";
		}
	}

	
	public Map<String,String>  getGDLastLotteryPeriods() 
	{
		
		GDPeriodsInfo lastcqp=periodsInfoLogic.queryLastLotteryPeriods_noTime();
		if(lastcqp!=null)
		{
			Map<String,String> retMap=new HashMap<String,String>();
			StringBuffer sbBallNum=new StringBuffer();
			retMap.put("PeriondsNum", lastcqp.getPeriodsNum());
			
			String result1,result2,result3,result4,result5,result6,result7,result8 = "";
			if(lastcqp.getResult1()>=0 && lastcqp.getResult1()<=9){result1 = "0"+String.valueOf(lastcqp.getResult1());}else{result1 = String.valueOf(lastcqp.getResult1());}
			if(lastcqp.getResult2()>=0 && lastcqp.getResult2()<=9){result2 = "0"+String.valueOf(lastcqp.getResult2());}else{result2 = String.valueOf(lastcqp.getResult2());}
			if(lastcqp.getResult3()>=0 && lastcqp.getResult3()<=9){result3 = "0"+String.valueOf(lastcqp.getResult3());}else{result3 = String.valueOf(lastcqp.getResult3());}
			if(lastcqp.getResult4()>=0 && lastcqp.getResult4()<=9){result4 = "0"+String.valueOf(lastcqp.getResult4());}else{result4 = String.valueOf(lastcqp.getResult4());}
			if(lastcqp.getResult5()>=0 && lastcqp.getResult5()<=9){result5 = "0"+String.valueOf(lastcqp.getResult5());}else{result5 = String.valueOf(lastcqp.getResult5());}
			if(lastcqp.getResult6()>=0 && lastcqp.getResult6()<=9){result6 = "0"+String.valueOf(lastcqp.getResult6());}else{result6 = String.valueOf(lastcqp.getResult6());}
			if(lastcqp.getResult7()>=0 && lastcqp.getResult7()<=9){result7 = "0"+String.valueOf(lastcqp.getResult7());}else{result7 = String.valueOf(lastcqp.getResult7());}
			if(lastcqp.getResult8()>=0 && lastcqp.getResult8()<=9){result8 = "0"+String.valueOf(lastcqp.getResult8());}else{result8 = String.valueOf(lastcqp.getResult8());}
			sbBallNum.append("<span class='No_").append(result1).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result2).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result3).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result4).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result5).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result6).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result7).append("'>").append("</span>");
			sbBallNum.append("<span class='No_").append(result8).append("'>").append("</span>");
			retMap.put("BallNum", sbBallNum.toString());
		    return retMap;	
		}
		else 
			return null;
		
	}
	
	public Map<String,String>  getNCLastLotteryPeriods() 
	{
		
		NCPeriodsInfo lastcqp=ncPeriodsInfoLogic.queryLastLotteryPeriods_noTime();
		if(lastcqp!=null)
		{
			Map<String,String> retMap=new HashMap<String,String>();
			StringBuffer sbBallNum=new StringBuffer();
			retMap.put("PeriondsNum", lastcqp.getPeriodsNum());
			
			String result1,result2,result3,result4,result5,result6,result7,result8 = "";
			if(lastcqp.getResult1()>=0 && lastcqp.getResult1()<=9){result1 = "0"+String.valueOf(lastcqp.getResult1());}else{result1 = String.valueOf(lastcqp.getResult1());}
			if(lastcqp.getResult2()>=0 && lastcqp.getResult2()<=9){result2 = "0"+String.valueOf(lastcqp.getResult2());}else{result2 = String.valueOf(lastcqp.getResult2());}
			if(lastcqp.getResult3()>=0 && lastcqp.getResult3()<=9){result3 = "0"+String.valueOf(lastcqp.getResult3());}else{result3 = String.valueOf(lastcqp.getResult3());}
			if(lastcqp.getResult4()>=0 && lastcqp.getResult4()<=9){result4 = "0"+String.valueOf(lastcqp.getResult4());}else{result4 = String.valueOf(lastcqp.getResult4());}
			if(lastcqp.getResult5()>=0 && lastcqp.getResult5()<=9){result5 = "0"+String.valueOf(lastcqp.getResult5());}else{result5 = String.valueOf(lastcqp.getResult5());}
			if(lastcqp.getResult6()>=0 && lastcqp.getResult6()<=9){result6 = "0"+String.valueOf(lastcqp.getResult6());}else{result6 = String.valueOf(lastcqp.getResult6());}
			if(lastcqp.getResult7()>=0 && lastcqp.getResult7()<=9){result7 = "0"+String.valueOf(lastcqp.getResult7());}else{result7 = String.valueOf(lastcqp.getResult7());}
			if(lastcqp.getResult8()>=0 && lastcqp.getResult8()<=9){result8 = "0"+String.valueOf(lastcqp.getResult8());}else{result8 = String.valueOf(lastcqp.getResult8());}
			sbBallNum.append("<span class='NC_").append(result1).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result2).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result3).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result4).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result5).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result6).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result7).append("'>").append("</span>");
			sbBallNum.append("<span class='NC_").append(result8).append("'>").append("</span>");
			retMap.put("BallNum", sbBallNum.toString());
			return retMap;	
		}
		else 
			return null;
		
	}
	
	public Map<String,String>  getCQLastLotteryPeriods() 
	{
		CQPeriodsInfo lastcqp=icqPeriodsInfoLogic.queryLastLotteryPeriods();
		if(lastcqp!=null)
		{
			Map<String,String> retMap=new HashMap<String,String>();
			StringBuffer sbBallNum=new StringBuffer();
			retMap.put("PeriondsNum", lastcqp.getPeriodsNum());
			sbBallNum.append("<span class='No_00").append(lastcqp.getResult1()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_00").append(lastcqp.getResult2()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_00").append(lastcqp.getResult3()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_00").append(lastcqp.getResult4()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_00").append(lastcqp.getResult5()).append("'>").append("</span>");
			retMap.put("BallNum", sbBallNum.toString());
		    return retMap;	
		}
		else 
			return null;
		
	}
	
	public Map<String,String>  getK3LastLotteryPeriods() 
	{
		JSSBPeriodsInfo lastcqp=jssbPeriodsInfoLogic.queryLastLotteryPeriods();
		if(lastcqp!=null)
		{
			Map<String,String> retMap=new HashMap<String,String>();
			StringBuffer sbBallNum=new StringBuffer();
			retMap.put("PeriondsNum", lastcqp.getPeriodsNum());
			sbBallNum.append("<span class='No_0000").append(lastcqp.getResult1()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_0000").append(lastcqp.getResult2()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_0000").append(lastcqp.getResult3()).append("'>").append("</span>");
			retMap.put("BallNum", sbBallNum.toString());
			return retMap;	
		}
		else 
			return null;
		
	}
	
	public Map<String,String>  getLastLotteryPeriods() 
	{
		
		BJSCPeriodsInfo lastcqp=bjscPeriodsInfoLogic.queryLastLotteryPeriods();
		if(lastcqp!=null)
		{
			Map<String,String> retMap=new HashMap<String,String>();
			StringBuffer sbBallNum=new StringBuffer();
			retMap.put("PeriondsNum", lastcqp.getPeriodsNum());
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult1()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult2()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult3()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult4()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult5()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult6()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult7()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult8()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult9()).append("'>").append("</span>");
			sbBallNum.append("<span class='No_000").append(lastcqp.getResult10()).append("'>").append("</span>");
			retMap.put("BallNum", sbBallNum.toString());
		    return retMap;	
		}
		else 
			return null;
		
	}
	
	//****************两面长龙START****************************
	public String getSideAndDragonRank(String from)
	{ 
		List<PlayType> playType=new ArrayList<PlayType>();
		try
		{
		if("GDKLSF".equals(from))
		{
			playType=this.getGDKLSFResult();
		}
		else if("CQSSC".equals(from))
		{
			playType=this.getCQSSCResult();
		}
		else if("BJSC".equals(from))
		{
			playType=this.getBJSCResult();
		}
		else if("NC".equals(from))
		{
			playType=this.getNCResult();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.getRequest().setAttribute("twoSide", playType);
		return "rankResult";
	}
	
	private List<PlayType> getGDKLSFResult()
	{
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("playType","GDKLSF"));
		filtersPlayType.add(Restrictions.ilike("typeCode", "DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playType=playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<GDPeriodsInfo> periodInfoList=periodsInfoLogic.queryLastPeriodsForRefer();
		//Collections.reverse(periodInfoList);
		//System.out.println(periodInfoList.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map<PlayType,String> map=new HashMap<PlayType,String>();
		for (int i = 0;  i< periodInfoList.size(); i++) {
			GDPeriodsInfo periondInfo=periodInfoList.get(i);
			Integer ball1=periondInfo.getResult1();
			Integer ball2=periondInfo.getResult2();
			Integer ball3=periondInfo.getResult3();
			Integer ball4=periondInfo.getResult4();
			Integer ball5=periondInfo.getResult5();
			Integer ball6=periondInfo.getResult6();
			Integer ball7=periondInfo.getResult7();
			Integer ball8=periondInfo.getResult8();
			List<Integer> winNums=new ArrayList<Integer>(); 
			winNums.add(ball1);
			winNums.add(ball2);
			winNums.add(ball3);
			winNums.add(ball4);
			winNums.add(ball5);
			winNums.add(ball6);
			winNums.add(ball7);
			winNums.add(ball8);
			//String winNum=String.valueOf(ball1)+String.valueOf(ball2)+String.valueOf(ball3)+String.valueOf(ball4)+String.valueOf(ball5)+String.valueOf(ball6)+String.valueOf(ball7)+String.valueOf(ball8);
			
			Iterator<PlayType> it=playType.iterator();
			
			while(it.hasNext())
			{
				 PlayType type=	it.next();
				 
				
				 String typeWinStr=map.get(type);
				 String winBooleanStr="0";
				 //long start=System.currentTimeMillis();
				 boolean win=GDKLSFRule.getBetResult(type, winNums);
				// long end=System.currentTimeMillis();
				 
					// System.out.println("開銷"+type.getSubTypeName()+">>"+type.getFinalTypeName()+">>"+(end-start));
				
					
				 if(win) winBooleanStr="1";
				 if(typeWinStr==null)
				 {
					 typeWinStr=winBooleanStr;
			 
				 }
				 else
					 typeWinStr=typeWinStr+winBooleanStr;
				 
				 map.put(type, typeWinStr);
				
			}
			
		}
		
		Set keySet=map.keySet();
		Iterator<PlayType> it=keySet.iterator();
		List<PlayType> playTypes=new ArrayList();
		while(it.hasNext())
		{
			PlayType	pt=it.next();
			String winStr=map.get(pt);

			pt.setCount(this.getMaxLength(winStr));
			playTypes.add(pt);
			
		}
		
		Collections.sort(playTypes);
		
		 return playTypes;
		
	}
	
	private List<PlayType> getNCResult()
	{
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("playType","NC"));
		filtersPlayType.add(Restrictions.ilike("typeCode", "DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playType=playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<NCPeriodsInfo> periodInfoList=ncPeriodsInfoLogic.queryLastPeriodsForRefer();
		
		Map<PlayType,String> map=new HashMap<PlayType,String>();
		for (int i = 0;  i< periodInfoList.size(); i++) {
			NCPeriodsInfo periondInfo=periodInfoList.get(i);
			Integer ball1=periondInfo.getResult1();
			Integer ball2=periondInfo.getResult2();
			Integer ball3=periondInfo.getResult3();
			Integer ball4=periondInfo.getResult4();
			Integer ball5=periondInfo.getResult5();
			Integer ball6=periondInfo.getResult6();
			Integer ball7=periondInfo.getResult7();
			Integer ball8=periondInfo.getResult8();
			List<Integer> winNums=new ArrayList<Integer>(); 
			winNums.add(ball1);
			winNums.add(ball2);
			winNums.add(ball3);
			winNums.add(ball4);
			winNums.add(ball5);
			winNums.add(ball6);
			winNums.add(ball7);
			winNums.add(ball8);
			//String winNum=String.valueOf(ball1)+String.valueOf(ball2)+String.valueOf(ball3)+String.valueOf(ball4)+String.valueOf(ball5)+String.valueOf(ball6)+String.valueOf(ball7)+String.valueOf(ball8);
			
			Iterator<PlayType> it=playType.iterator();
			
			while(it.hasNext())
			{
				PlayType type=	it.next();
				
				
				String typeWinStr=map.get(type);
				String winBooleanStr="0";
				//long start=System.currentTimeMillis();
				boolean win=GDKLSFRule.getBetResult(type, winNums);
				// long end=System.currentTimeMillis();
				
				// System.out.println("開銷"+type.getSubTypeName()+">>"+type.getFinalTypeName()+">>"+(end-start));
				
				
				if(win) winBooleanStr="1";
				if(typeWinStr==null)
				{
					typeWinStr=winBooleanStr;
					
				}
				else
					typeWinStr=typeWinStr+winBooleanStr;
				
				map.put(type, typeWinStr);
				
			}
			
		}
		
		Set keySet=map.keySet();
		Iterator<PlayType> it=keySet.iterator();
		List<PlayType> playTypes=new ArrayList();
		while(it.hasNext())
		{
			PlayType	pt=it.next();
			String winStr=map.get(pt);
			
			pt.setCount(this.getMaxLength(winStr));
			playTypes.add(pt);
			
		}
		
		Collections.sort(playTypes);
		
		return playTypes;
		
	}
	
	
	private List<PlayType> getCQSSCResult()
	{
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("playType","CQSSC"));
		filtersPlayType.add(Restrictions.ilike("typeCode", "DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playType=playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		
		
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
		//java.util.Date now=new java.util.Date();
		//String nowStr=sdf.format(now);
		//String before=nowStr+" 00:00:00";
		//String after=nowStr+" 23:59:59";
		//List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		//filtersPeriodInfo.add(Restrictions.ge("lotteryTime",java.sql.Timestamp.valueOf(before)));
		//filtersPeriodInfo.add(Restrictions.le("lotteryTime",java.sql.Timestamp.valueOf(after)));
		//filtersPeriodInfo.add(Restrictions.eq("state", "4"));
		//List<CQPeriodsInfo> periodInfoList=icqPeriodsInfoLogic.queryAllPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		List<CQPeriodsInfo> periodInfoList=icqPeriodsInfoLogic.queryLastPeriodsForRefer();
		Collections.reverse(periodInfoList);
		//System.out.println(periodInfoList.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map<PlayType,String> map=new HashMap<PlayType,String>();
		
		for (int i = 0;  i< periodInfoList.size(); i++) {
			CQPeriodsInfo periondInfo=periodInfoList.get(i);
			Integer ball1=periondInfo.getResult1();
			Integer ball2=periondInfo.getResult2();
			Integer ball3=periondInfo.getResult3();
			Integer ball4=periondInfo.getResult4();
			Integer ball5=periondInfo.getResult5();
            List<Integer> ballList=new ArrayList<Integer>();
            ballList.add(ball1);
            ballList.add(ball2);
            ballList.add(ball3);
            ballList.add(ball4);
            ballList.add(ball5);
          
			Iterator<PlayType> it=playType.iterator();
			
			while(it.hasNext())
			{
				 PlayType type=	it.next();
  			     String typeWinStr=map.get(type);
				 String winBooleanStr="0";
				 boolean win=CQSSCBallRule.getBallBetResult(type, ballList);
				 if(win) winBooleanStr="1";
				 if(typeWinStr==null)
				 {
					 typeWinStr=winBooleanStr;
			 
				 }
				 else
					 typeWinStr=typeWinStr+winBooleanStr;
				 
				 map.put(type, typeWinStr);
				 
				 }
			
			
			
		}
		Set keySet=map.keySet();
		Iterator<PlayType> it=keySet.iterator();
		List<PlayType> playTypes=new ArrayList();
		while(it.hasNext())
		{
			PlayType	pt=it.next();
			String winStr=map.get(pt);
			pt.setCount(this.getMaxLength(StringUtils.reverse(winStr)));
			playTypes.add(pt);
			
			
		}
		 Collections.sort(playTypes);
		
		 return playTypes;
		
	}
	
	
	
	private List<PlayType> getBJSCResult()
	{
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("playType","BJ"));
		filtersPlayType.add(Restrictions.ilike("typeCode", "DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playType=playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));	
		List<BJSCPeriodsInfo> periodInfoList=bjscPeriodsInfoLogic.queryLastPeriodsForRefer();
		Collections.reverse(periodInfoList);
		Map<PlayType,String> map=new HashMap<PlayType,String>();
		
		for (int i = 0;  i< periodInfoList.size(); i++) {
			BJSCPeriodsInfo periondInfo=periodInfoList.get(i);
			Integer ball1=periondInfo.getResult1();
			Integer ball2=periondInfo.getResult2();
			Integer ball3=periondInfo.getResult3();
			Integer ball4=periondInfo.getResult4();
			Integer ball5=periondInfo.getResult5();
			Integer ball6=periondInfo.getResult6();
			Integer ball7=periondInfo.getResult7();
			Integer ball8=periondInfo.getResult8();
			Integer ball9=periondInfo.getResult9();
			Integer ball10=periondInfo.getResult10();
            List<Integer> ballList=new ArrayList<Integer>();
            ballList.add(ball1);
            ballList.add(ball2);
            ballList.add(ball3);
            ballList.add(ball4);
            ballList.add(ball5);
            ballList.add(ball6);
            ballList.add(ball7);
            ballList.add(ball8);
            ballList.add(ball9);
            ballList.add(ball10);
          
			Iterator<PlayType> it=playType.iterator();
			
			while(it.hasNext())
			{
				 PlayType type=	it.next();
  			     String typeWinStr=map.get(type);
				 String winBooleanStr="0";
				 boolean win=BJSCRule.getBetResult(type, ballList);
				 if(win) winBooleanStr="1";
				 if(typeWinStr==null)
				 {
					 typeWinStr=winBooleanStr;
			 
				 }
				 else
					 typeWinStr=typeWinStr+winBooleanStr;
				 
				 map.put(type, typeWinStr);
				 
				 }
			
			
			
		}
		Set keySet=map.keySet();
		Iterator<PlayType> it=keySet.iterator();
		List<PlayType> playTypes=new ArrayList();
		while(it.hasNext())
		{
			PlayType	pt=it.next();
			String winStr=map.get(pt);
			pt.setCount(this.getMaxLength(StringUtils.reverse(winStr)));
			playTypes.add(pt);
			
			
		}
		 Collections.sort(playTypes);
		
		 return playTypes;
		
	}
	
	public static int getMaxLength(final String str){
		String first=str.substring(0,1);
		if("1".equals(first))
		{
			int k=str.indexOf("0");
			if(k==-1)
				k=str.lastIndexOf("1")+1;
			return k;
		}
		else 
			return 0;
		
		

	
	}
	
	//****************两面长龙END****************************
	
	
	//****************总额和遗漏START****************************
    /**
     * 补货界面的右边栏，总额和遗漏，是根据条件返回单独的总额还是遗漏
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void replenishRightBar(String subType) throws IllegalAccessException, InvocationTargetException{
    	if(subType==null){
    		subType = Constant.LOTTERY_TYPE_GDKLSF;
    	}
    	String searchType = getRequest().getParameter("searchType");//是实占还是虚占
    	if(searchType==null){
    		searchType  = Constant.TRUE_STATUS;
    	}
    	
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        
        GDPeriodsInfo runningPeriodsGD = new GDPeriodsInfo();
        BJSCPeriodsInfo runningPeriodsBJ = new BJSCPeriodsInfo();
        NCPeriodsInfo runningPeriodsNC = new NCPeriodsInfo();
        String periodsNum = "";
        if(subType.indexOf("GDKLSF")!=-1){
        	runningPeriodsGD = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
        	if(runningPeriodsGD!=null){
        		periodsNum = runningPeriodsGD.getPeriodsNum();
        	}
		}else if(subType.indexOf("BJ")!=-1){
			runningPeriodsBJ = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsBJ!=null){
				periodsNum = runningPeriodsBJ.getPeriodsNum();
			}
	    }else if(subType.indexOf("NC")!=-1){
	    	runningPeriodsNC = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
	    	if(runningPeriodsNC!=null){
	    		periodsNum = runningPeriodsNC.getPeriodsNum();
	    	}
	    }
    	List<ZhanDangVO> result = new ArrayList<ZhanDangVO>();
    	
    	//子帐号处理*********START
    	ManagerUser userInfo = this.getCurrentManagerUser();
    	ManagerUser userInfoNew = new ManagerUser();
		BeanUtils.copyProperties(userInfoNew, userInfo);
	    if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
		   userInfoNew = getSubAccountParent(userInfoNew);	
	    }	
    	//子帐号处理*********END
	    
    	UserVO userVO = replenishLogic.getUserType(userInfo);
        //这里取的userVO.getUserType()里面是存字段名
		result = zhangdanLogic.queryRightTotal(subType, periodsNum, userInfoNew.getID(), userInfoNew.getUserType(), userVO.getUserType(), userVO.getRateUser(), searchType);
    	
		Map<String,ZhanDangVO> map = new HashMap<String,ZhanDangVO>();
		Map<String, String> mapType = new LinkedHashMap<String,String>();
		Map<String, String> temp = new LinkedHashMap<String,String>();
		if(subType.indexOf(Constant.LOTTERY_TYPE_GDKLSF)!=-1){
				temp = Constant.GDKLSF_RIGHT_TYPE;
				mapType=new HashMap(temp);
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_NC)!=-1){
				temp = Constant.NC_RIGHT_TYPE;
				mapType=new HashMap(temp);
		}else{
				temp = Constant.BJSC_RIGHT_TYPE;
				mapType=new HashMap(temp);
		}
		BigDecimal total = BigDecimal.ZERO;//总额
		for(ZhanDangVO v:result){
			if(mapType.get(v.getCommissionType())!=null){
				map.put(v.getCommissionType(), v);
				//System.out.println("~~~~~~~~~~~~~~~" + v.getCommissionType() + ":"+map.get(v.getCommissionType()).getRightBarMoney());
				mapType.remove(v.getCommissionType());
			}
			total = total.add(v.getTotalMoney());
		}
		for(Map.Entry<String, String> entry : mapType.entrySet()){
			map.put(entry.getKey(), new ZhanDangVO());
			//System.out.println("~~~~~~~~~~~~~~~"  + entry.getKey() + ":"+ map.get(entry.getKey()).getRightBarMoney());
		}
		// 如果是统计广东的，还要加上遗漏
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(subType)) {
			map = replenishLogic.notAppearCnt(map);// 广东遗漏
		} else if (Constant.LOTTERY_TYPE_NC.equals(subType)) {
			map = replenishLogic.notAppearCntForNc(map);// 农场遗漏
		}
		
		getRequest().setAttribute("result", map);
		getRequest().setAttribute("totalMoney", total.setScale(0,BigDecimal.ROUND_HALF_UP));
		
    }
    
	//****************总额和遗漏END****************************
	
	
	
	//根椐盘口选择赔率
	private BigDecimal getRealOddsFromPlate(ShopsPlayOdds shopodds){
        BigDecimal odd = BigDecimal.ZERO;	    
		if("A".equals(plate)){
			odd = shopodds.getRealOdds();
		}else if("B".equals(plate)){
			odd = shopodds.getRealOddsB();
		}else if("C".equals(plate)){
			odd = shopodds.getRealOddsC();
		}
		return odd;
	}

	public String shopLogin(){
	    
		return SUCCESS;
	}
	
	public String chooseLogin(){
		return SUCCESS;
	}
	
	public String userLogin(){
		return SUCCESS;
	}

	
	
    private ManagerUser getInfo() {
        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        return userInfo;
    }
    
    //把盈亏数对比交易设定里的负值超额警示值，如果小于警示值就赋于1,否则赋于0
    private String valiteOverLoseQuatas(BigDecimal loseMoney,String finalType,String shopCode){
    	PlayType playType = PlayTypeUtils.getPlayType(finalType);
		UserCommissionDefault userCommsionDefault=userCommissionDefaultLogic.queryPlayTypeCommission(playType.getCommissionType(),shopCode);
		Integer loseQuatas = userCommsionDefault.getLoseQuatas();
		if(loseMoney.compareTo(BigDecimal.valueOf(-loseQuatas))==-1 && loseMoney.compareTo(BigDecimal.ZERO)==-1){
			return "1";
		}else{
			return "0";
		}
    }
    
    /*public UserVO getUserVo(ManagerUser userInfoNew){
    	UserVO userVO =  new UserVO();
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
            userVO.setReplenishMent(branchStaffExt.getReplenishment());
        }else if(isStockholder){
            StockholderStaffExt  stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(stockholderStaffExt.getReplenishment());
        }else if(isGenAgent){
            GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(genAgentStaffExt.getReplenishment());
        }else if(isAgent){
            AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(agentStaffExt.getReplenishment());
        }
    	
		return userVO;
    	
    }*/
    
    private IPlayTypeLogic playTypeLogic;
    private IShopOddsLogic shopOddsLogic;
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic	= null;
	private IStatReportLogic statReportLogic;
	private ISettledReportEricLogic settledReportEricLogic;
	private IReplenishLogic replenishLogic;
	private IAuthorizLogic authorizLogic;
	//private ISubAccountInfoLogic subAccountInfoLogic;
	private IShopsLogic shopsLogic = null;
	//private IHKPeriodsInfoLogic skperiodsInfoLogic;	
	private IUserCommissionLogic userCommissionLogic;
	private IUserCommissionDefault userCommissionDefaultLogic;
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private IUserOutReplenishLogic userOutReplenishLogic;
	private IZhangdanLogic zhangdanLogic;
	
	private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    
    private ICommonUserLogic commonUserLogic;
    private ISystemLogic systemLogic;
	
	
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}
	
	public IStatReportLogic getStatReportLogic() {
		return statReportLogic;
	}

	public void setStatReportLogic(IStatReportLogic statReportLogic) {
		this.statReportLogic = statReportLogic;
	}

	public ISettledReportEricLogic getSettledReportEricLogic() {
		return settledReportEricLogic;
	}
	public void setSettledReportEricLogic(
			ISettledReportEricLogic settledReportEricLogic) {
		this.settledReportEricLogic = settledReportEricLogic;
	}

	private IBossLogLogic bossLogLogic; // 記錄系統日誌接口
	
	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}
	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
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

	public String getSearchType() {
		return searchType;
	}

	public String getSearchArray() {
		return searchArray;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchArray(String searchArray) {
		this.searchArray = searchArray;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public String getPlate() {
		return plate;
	}

	public String getPeriodsNum() {
		return periodsNum;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}
	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}
	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}
	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}
	public String getOddsSubType() {
		return oddsSubType;
	}

	public void setOddsSubType(String oddsSubType) {
		this.oddsSubType = oddsSubType;
	}


	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public ArrayList<OpenPlayOdds> getOpenOddsList() {
		return openOddsList;
	}

	public void setOpenOddsList(ArrayList<OpenPlayOdds> openOddsList) {
		this.openOddsList = openOddsList;
	}

	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}

	public List<ReplenishVO> getReplenishList_LM() {
		return replenishList_LM;
	}

	public void setReplenishList_LM(List<ReplenishVO> replenishList_LM) {
		this.replenishList_LM = replenishList_LM;
	}

	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}

	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}
	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}
	public void setShopsPlayOddsLogLogic(
			IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}
	public IUserOutReplenishLogic getUserOutReplenishLogic() {
		return userOutReplenishLogic;
	}
	public void setUserOutReplenishLogic(
			IUserOutReplenishLogic userOutReplenishLogic) {
		this.userOutReplenishLogic = userOutReplenishLogic;
	}
	public IZhangdanLogic getZhangdanLogic() {
		return zhangdanLogic;
	}
	public void setZhangdanLogic(IZhangdanLogic zhangdanLogic) {
		this.zhangdanLogic = zhangdanLogic;
	}
	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}
	public IBranchStaffExtLogic getBranchStaffExtLogic() {
		return branchStaffExtLogic;
	}
	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}
	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}
	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}
	public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
		this.branchStaffExtLogic = branchStaffExtLogic;
	}
	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}
	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}
	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}
	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}
	public void setUserCommissionDefaultLogic(
			IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
	}
	public IBossLogLogic getBossLogLogic() {
		return bossLogLogic;
	}

	public void setBossLogLogic(IBossLogLogic bossLogLogic) {
		this.bossLogLogic = bossLogLogic;
	}

    public ISystemLogic getSystemLogic() {
		return systemLogic;
	}
	public void setSystemLogic(ISystemLogic systemLogic) {
		this.systemLogic = systemLogic;
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

    /*public ISubAccountInfoLogic getSubAccountInfoLogic() {
        return subAccountInfoLogic;
    }

    public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
        this.subAccountInfoLogic = subAccountInfoLogic;
    }*/
	
    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }


	public String getRefleshTime() {
		return refleshTime;
	}

	public void setRefleshTime(String refleshTime) {
		this.refleshTime = refleshTime;
	}
	public String getLoseSort() {
		return loseSort;
	}
	public void setLoseSort(String loseSort) {
		this.loseSort = loseSort;
	}
	public BigDecimal getAvLose() {
		return avLose;
	}
	public BigDecimal getAvOdd() {
		return avOdd;
	}
	public BigDecimal getAvCommission() {
		return avCommission;
	}
	public void setAvLose(BigDecimal avLose) {
		this.avLose = avLose;
	}
	public void setAvOdd(BigDecimal avOdd) {
		this.avOdd = avOdd;
	}
	public void setAvCommission(BigDecimal avCommission) {
		this.avCommission = avCommission;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}
}

