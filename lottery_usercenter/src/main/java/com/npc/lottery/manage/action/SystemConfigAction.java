package com.npc.lottery.manage.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Lists;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.manage.entity.PeriodAutoOdds;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.rule.CQSSCBallRule;
import com.npc.lottery.rule.GDKLSFRule;
import com.npc.lottery.sysmge.entity.LoginLogInfo;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.ILoginLogInfoLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
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
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Tool;
import com.npc.lottery.util.Tools;
public class SystemConfigAction extends BaseLotteryAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SystemConfigAction.class);
    private IPlayTypeLogic playTypeLogic;
    private IShopOddsLogic shopOddsLogic;
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private IUserCommissionDefault userCommissionDefaultLogic;
	private ILoginLogInfoLogic loginLogInfoLogic;
    private String type="SystemManage";
    private String oddsSubType =  Constant.LOTTERY_TYPE_GDKLSF;
    private String subType =  Constant.LOTTERY_TYPE_GDKLSF;
    private ArrayList<UserCommissionDefault> showList = new ArrayList<UserCommissionDefault>();
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IMemberStaffExtLogic memberStaffExtLogic; //會員
    private IUserCommissionLogic userCommissionLogic;
    private IChiefStaffExtLogic chiefStaffExtLogic = null;// 总监
    private ICommonUserLogic commonUserLogic;
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private String qUserID = null; //页面传过来的userId
   
    private String sitemeshType = ""; //用于页面菜单显示
    private String subMenu;//二級菜單
	/*
	 * 	快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球
	 *  时时彩  1-5球
	 */
	
	private IShopsLogic shopsLogic = null;
	
	private ISystemLogic systemLogic;
	
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	
	private String autoOddsType;
	
	private String periodsType;
	
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}
	
	public String getInManageMenuBoss() // 总管内部管理
	{
		periodsType = this.getRequest().getParameter("periodsType"); //內部管理
		return "success";	
	}
	public String getInManageMenu()
	{
		subMenu = "inManage"; //內部管理
		return "success";	
	}
	public String getPersonalAdminMenu()
	{
		subMenu = "personalAdmin"; //個人管理
		ManagerUser user = this.getCurrentManagerUser();
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, user);
		} catch (Exception e) {
		  log.info("getPersonalAdminMenu里出错"+ e.getMessage());
		}
		   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		   }	
		//子帐号处理*********END
		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);  
		getRequest().setAttribute("mReplenishMent",userVO.getReplenishMent());
		return "success";	
	}
	public String getUserMenu()   //用戶管理
	{
		subMenu = "user"; 
		return "success";	
	}
	public String getTopMenu()
	{
		
	   return "topMenu";	
	}
	public String enterManage()
	{
		
		return "enterManage";	
	}
	
	//登錄日志入口
	public String loginLog(){
		
		type = "personalAdmin_loginLog";
		this.queryLoginLog();
		return "success";
	}
	
	//查询登录日志
    public String queryLoginLog(){
		
		type = "personalAdmin_loginLog";
		int pageSize = 49;
		HttpServletRequest request = ServletActionContext.getRequest();

    	//dateStart = new Date();
    	//dateEnd = new Date();
    	Calendar caStart = Calendar.getInstance();
    	caStart.add(Calendar.DATE, -7);  //統計7天以內
    	dateStart = caStart.getTime();
//    	
//    	Calendar ca = Calendar.getInstance();
//    	ca.add(Calendar.DATE, 7);  //統計7天以內
    	dateEnd = new Date();  //查询到当前时间
    	
        //转换为凌晨2点半的问题
    	Date sqlDateStart =new Date(dateStart.getTime() + 60 * 60 * 1000 * 2);
    	Date sqlDateEnd = new Date(dateEnd.getTime() + 60 * 60 * 1000 * 26);
        
        String pageCurrentNoStr = Tool.getParameter(request,
                Constant.PAGETAG_CURRENT, "1");
        int pageCurrentNo = 1;
        try {
            pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
        } catch (Exception ex) {

        }

        //构造查询条件
        ConditionData conditionData = new ConditionData();
        //判断是否需要增加状态查询条件

        if (null != qUserID && 0 != qUserID.trim().length()) {
        	qUserID=Tools.decodeWithKey(qUserID);
        	Long userId = Long.valueOf(qUserID);
        	
            conditionData.addEqual("userId", userId);//设置状态的查询条件
        }else{
        	ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        	conditionData.addEqual("userId", userInfo.getID());
        	qUserID = String.valueOf(userInfo.getID());
        }
        //设置时间的查询条件

        conditionData.addBetween("loginDate", sqlDateStart, true,sqlDateEnd, true);

        //查询记录列表
        ArrayList<LoginLogInfo> loginLogList = loginLogInfoLogic.findLoginLogInfoList(conditionData, pageCurrentNo, pageSize);
        
      //获取记录总数
        long recordAmount = loginLogInfoLogic.findAmountLoginLogInfo(conditionData);
        log.info("记录总数：" + recordAmount);
        long sumPages = (recordAmount - 1) / pageSize + 1;


        //保存返回的数据记录集
        request.setAttribute("sumPages", sumPages + "");
        //保存返回的数据记录集
        request.setAttribute("pageCurrentNo", pageCurrentNo + "");
        request.setAttribute("resultList", loginLogList);
		
		return "list";
	}
	
    
	public String enter()
	{
	    ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));
	    
        if(subType.indexOf("CQSSC")!=-1)
		{
			initCQSSCLotHistoryData();
			return "cqlotResultHistory";
		}else if(subType.indexOf("NC")!=-1){
		    initNCLotHistoryData();
		    return "nclotResultHistory";
		}
		else if(subType.indexOf("GDKLSF")!=-1)
		{
			initGDKLSFLotHistoryData();
			return "gdlotResultHistory";
		}
		else if(subType.indexOf("BJSC")!=-1)
		{
			initBJSCLotHistoryData();
			return "bjlotResultHistory";
		}
        else
        {
        	initJSSBLotHistoryData();
        	return "jslotResultHistory";
        }
		
	}
	
	private void initJSSBLotHistoryData()
	{
		Page<JSSBPeriodsInfo> page=new Page<JSSBPeriodsInfo>();
		page.setPageSize(15);
		int pageNo=1;
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page=jssbPeriodsInfoLogic.queryHistoryPeriods(page);
		this.getRequest().setAttribute("page", page);
		
	}
	
	private void initBJSCLotHistoryData()
	{
		Page<BJSCPeriodsInfo> page=new Page<BJSCPeriodsInfo>();
		page.setPageSize(15);
		int pageNo=1;
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page=bjscPeriodsInfoLogic.queryHistoryPeriods(page);
		this.getRequest().setAttribute("page", page);
		
	}
	
	private void initCQSSCLotHistoryData()
	{
		Page<CQPeriodsInfo> page=new Page<CQPeriodsInfo>();
		page.setPageSize(15);
		int pageNo=1;
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page=icqPeriodsInfoLogic.queryHistoryPeriods(page);
		this.getRequest().setAttribute("page", page);
		
	}
	
	private void initGDKLSFLotHistoryData()
	{
		Page<GDPeriodsInfo> page=new Page<GDPeriodsInfo>(15);
		page.setPageSize(15);
		int pageNo=1;
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page=periodsInfoLogic.queryHistoryPeriods(page);
		this.getRequest().setAttribute("page", page);
	}
	
	private void initNCLotHistoryData()
	{
		Page<NCPeriodsInfo> page=new Page<NCPeriodsInfo>(15);
		page.setPageSize(15);
		int pageNo=1;
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page=ncPeriodsInfoLogic.queryHistoryPeriods(page);
		this.getRequest().setAttribute("page", page);
	}
	
	/**
	 * 查询当前店铺下所有 将陪设置 ，放入map中
	 * @param autoOddsType
	 * @return
	 */
	public Map<String,String> initAutoOddd(String autoOddsType)
	{
		ManagerUser	manager = ((ManagerUser) this.getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION));
		String shopsCode = manager.getSafetyCode();
		
		List<PeriodAutoOdds> oddsList = systemLogic.findAutoOddsByShopCode(shopsCode);
		Map<String,String> autoOddsMap=new HashMap<String,String>();
		for (int i = 0; i < oddsList.size(); i++) {
			PeriodAutoOdds ent =oddsList.get(i);
			autoOddsMap.put(ent.getType()+"/"+ent.getName(), ent.getAutoOdds().toString());
		}
		return autoOddsMap;
	}
	/**
	 * 系統初始設定-加載頁面 add byAaron 20121201
	 * @return
	 */
	public String enterSystemConfig()
	{
		try{
//			ManagerUser	manager = ((ManagerUser) this.getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION));
			
			Map<String,String> AutoOddsMapAll = null;
			String autoOddsType = this.getRequest().getParameter("autoOddsType");
			this.autoOddsType=(autoOddsType==null?"GDKLSF":autoOddsType);
		
			AutoOddsMapAll = initAutoOddd("");
		
			this.getRequest().setAttribute("AutoOddsMap", AutoOddsMapAll);
		    
			 
			
		}catch(Exception  e){
			
			log.error("<--訪問系統初始化設定 查詢異常：SystemConfigAction.enterSystemConfig-->",e);
			return "exception";
		}
		return SUCCESS;
		
	}
	/**
	 * 是否是連續 性 數字 
	 * @param seriesArray
	 * @return
	 */
	public boolean isSeriesArray(List<Integer> seriesArray) {
		if(seriesArray==null || seriesArray.size()==0) return false;
		 int a [] = new int[seriesArray.size()];
    	 for(int i=0;i<seriesArray.size();i++)
    	 {
    		 a[i]=seriesArray.get(i);
    	 }
    	 Arrays.sort(a);
	     return a[a.length-1] - a[0] == a.length - 1 ? true : false;
	}
	/**
	 * 重慶 兩面盤自動降賠 設置
	 */
	public void  CQDSAutoOdds()
	{
		
		List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.CQSSC_DOUBLESIDE, "STATE");
		if(pOList ==null || pOList.size()==0) return;
		
		// 不針對 某個店鋪
		List<CQPeriodsInfo> cqPeriodList = icqPeriodsInfoLogic.queryLastPeriodsForRefer();
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "CQSSC_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<Integer>  winNums = null; 
		
		Map<String,String> map=new HashMap<String,String>();
		for(PlayType playTypeInfo:playTypeList)
		{
			CQPeriodsInfo  gdInfo =null;
			for(int i=0;i<cqPeriodList.size();i++)
			{
				gdInfo = cqPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());
				
				String typeWinStr=map.get(playTypeInfo.getTypeCode());
				boolean  betResult = CQSSCBallRule.getBallBetResult(playTypeInfo,winNums);
				String winBooleanStr="0";
				if(betResult) winBooleanStr="1"; //連出
				if(typeWinStr==null)
				{
					typeWinStr=winBooleanStr;
				}
				else{
					typeWinStr=typeWinStr+winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);
				
			}
		}
		try{
			for(PeriodAutoOdds pO:pOList){
//				if(Integer.valueOf(pO.getAutoOdds().toString())!=0)//0為啟用 1 為禁用
//					continue ;
				updateOddsDOUBLESIDE(pO,map,Constant.CQSSC_DOUBLESIDE);
			}
		}catch(Exception e)
		{
			log.error("<--重慶 兩面盤自動降賠 設置異常：SystemConfigAction.CQDSAutoOdds-->",e);
		}
	}
	/**
	 * 廣東、重慶  根據type 類型更新雙面盤賠率
	 * @param pO查詢出開關 狀態
	 * @param map key:TypeCode value:0101010011存儲開獎結果（中，不中）
	 * @param option 操作類型   
	 * @throws Exception
	 */
	public void updateOddsDOUBLESIDE(PeriodAutoOdds pO,Map<String,String> map,String option) throws Exception
	{
//		long currtentMillisStart = System.currentTimeMillis();
		List<PeriodAutoOdds> autoOddsList = systemLogic.findAutoOddsByType(option,pO.getShopCode());
		Map<String,PeriodAutoOdds> autoOddsMap =  new HashMap<String,PeriodAutoOdds>();
		Map<String,BigDecimal> playOddsMap =  new HashMap<String,BigDecimal>(); // 降同路，如果玩法降了，同路則將；如果玩法沒將同路不將
		for(PeriodAutoOdds o:autoOddsList)
		{
			autoOddsMap.put(o.getName(), o);
		}
		PeriodAutoOdds pL = null;
		PeriodAutoOdds pN = null;
		if(null != pO && Integer.valueOf(pO.getAutoOdds().toString())==0)//0為啟用 1 為禁用
		{
			for(Map.Entry<String, String> entry: map.entrySet()) {
				
				int countL = getMaxLengthL(entry.getValue());
				int countN = getMaxLengthN(entry.getValue());
				// 連出 沒出 只能有一個 
				if(countN>25) countN = 25; //超過25期按25期降 
				if(countL>25) countL = 25; //超過25期按25期降 
				pN =  autoOddsMap.get("N_"+countN);
				pL =  autoOddsMap.get("L_"+countL);
				//p==null ==0說明 沒有設置自動將賠 不需要修改賠率
				if((null == pN || pN.getAutoOdds().compareTo(BigDecimal.ZERO)==0) && (null == pL || pL.getAutoOdds().compareTo(BigDecimal.ZERO)==0))  
					continue;
				
				ShopsPlayOdds  odds =  null ;// shopOddsLogic.queryShopPlayOdds(shopsCode,entry.getKey());
				List<ShopsPlayOdds> oddsList = shopOddsLogic.queryShopPlayOddsListByTypeCode(entry.getKey(),pO.getShopCode());//針對所有商鋪
				for(ShopsPlayOdds ent:oddsList)
				{
					odds = ent;
					odds.setRealUpdateDate(new Date());
					if(null != pN && pN.getAutoOdds().compareTo(BigDecimal.ZERO)==1) //p==null 說明 沒有設置自動將賠 不需要修改賠率 ,==1 為>0
					{
						odds.setRealOdds(odds.getRealOdds().subtract(pN.getAutoOdds()));
						shopOddsLogic.updateShopOdds(odds);
						playOddsMap.put(odds.getPlayTypeCode(), pN.getAutoOdds());
					}
					if(null != pL && pL.getAutoOdds().compareTo(BigDecimal.ZERO)==1) //p==null 說明 沒有設置自動將賠 不需要修改賠率 
					{
						odds.setRealOdds(odds.getRealOdds().subtract(pL.getAutoOdds()));
						shopOddsLogic.updateShopOdds(odds);
						playOddsMap.put(odds.getPlayTypeCode(), pL.getAutoOdds());
					} 
				}
			}
			
			// 設置同路
			updateOddsDOUBLESIDETL(playOddsMap,option,pO.getShopCode());
			
		}
	}
	//廣東 重慶、 雙面盤的同路
	public void updateOddsDOUBLESIDETL(Map<String,BigDecimal> playOddsMap,String option,String shopCode) throws Exception
	{
		PeriodAutoOdds peroddsInfo = systemLogic.queryAutoOddsByTypeName(option, "ODDSTL_DS",shopCode);
		// 同路
		if(null != peroddsInfo && peroddsInfo.getAutoOdds().compareTo(BigDecimal.ZERO)==1)
		{
			for(Map.Entry<String, BigDecimal> entryInfo: playOddsMap.entrySet()) {
			
				// 主要用 sub_type,final_type
				PlayType playType = playTypeLogic.getPlayTypeByTypeCode(entryInfo.getKey());
				// 取出對應規則對應的好球  同路   下降的賠率=(同路值%*當前賠率=0.04),
				List<Integer> list = null;
				if(Constant.GDKLSF_DOUBLESIDE.equals(option))
				{
					list = GDKLSFRule.getMatchList(playType.getPlayFinalType());
				}else if(Constant.CQSSC_DOUBLESIDE.equals(option))
				{
					list = CQSSCBallRule.getMatchList(playType.getPlayFinalType());
				}
				BigDecimal realOdds = peroddsInfo.getAutoOdds().divide(new BigDecimal(100)).multiply(entryInfo.getValue());
				for(Integer i:list)
				{
					if(null !=  playType.getPlaySubType())//重慶 虎、龍..對應為空
					{
						shopOddsLogic.updateShopOddsRealOddsByPlayTypeCode(playType.getPlayType()+"_"+playType.getPlaySubType()+"_"+i, realOdds,shopCode);
					}
				}
		    }
		}
	}

	/**
	 * 廣東 兩面盤自動降賠 設置
	 */
	public void  GDDSAutoOdds()
	{
		
		// 快樂十分 兩面盤
		List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.GDKLSF_DOUBLESIDE, "STATE");
		if(pOList ==null || pOList.size()==0) return;
		
		// 不針對 某個店鋪
		List<GDPeriodsInfo> gdPeriodList = periodsInfoLogic.queryLastPeriodsForRefer();
		
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "GDKLSF_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<Integer>  winNums = null; 
		
		// type:0101010111
		Map<String,String> map=new HashMap<String,String>();
		for(PlayType playTypeInfo:playTypeList)
		{
			GDPeriodsInfo  gdInfo =null;
			for(int i=0;i<gdPeriodList.size();i++)
			{
				gdInfo = gdPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());
				winNums.add(gdInfo.getResult6());
				winNums.add(gdInfo.getResult7());
				winNums.add(gdInfo.getResult8());
				
				String typeWinStr=map.get(playTypeInfo.getTypeCode());
				boolean  betResult = GDKLSFRule.getBetResult(playTypeInfo, winNums);
				String winBooleanStr="0";
				if(betResult) winBooleanStr="1"; //連出
				if(typeWinStr==null)
				{
					typeWinStr=winBooleanStr;
				}
				else{
					typeWinStr=typeWinStr+winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);
				
			}
		}
		try{
			for(PeriodAutoOdds pO : pOList)
			{
//				if(Integer.valueOf(pO.getAutoOdds().toString())!=0)//0為啟用 1 為禁用
//					continue ;
				updateOddsDOUBLESIDE(pO,map,Constant.GDKLSF_DOUBLESIDE);
			}
			
		}catch(Exception e)
		{
			log.error("<--廣東 兩面盤自動降賠 設置異常：SystemConfigAction.GDDSAutoOdds-->",e);
		}
		
	}
	/**
	 * 重慶  遺漏自動降賠 設置
	 */
	public void  CQYLAutoOdds()
	{
		
		List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.CQSSC_YILOU, "STATE");
		if(pOList == null || pOList.size()==0) return;
		
		List<CQPeriodsInfo> cqPeriodList = icqPeriodsInfoLogic.queryLastPeriodsForRefer();
		
		List<Integer>  winNums = null; 
		Map<String,String> map=new HashMap<String,String>();
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.and(Restrictions.eq("playType","CQSSC"),Restrictions.sqlRestriction("type_code not  like 'CQSSC_DOUBLESIDE%'")));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		
		// type:0101010111
		for(PlayType playTypeInfo : playTypeList)
		{
			CQPeriodsInfo  gdInfo = null;
			for(int i=0;i<cqPeriodList.size();i++)
			{
				gdInfo = cqPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());
				
				String winBooleanStr="0";
				String typeWinStr=map.get(playTypeInfo.getTypeCode());
				boolean betResult =  CQSSCBallRule.getBallBetResult(playTypeInfo,winNums);
				if(betResult) winBooleanStr="1"; 
				if(typeWinStr==null)
				{
					typeWinStr=winBooleanStr;
				}else{
					typeWinStr=typeWinStr+winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);
			}
		}
		
		try{
			for(PeriodAutoOdds pO : pOList)
			{
				updateOddsYILOU(pO,map,Constant.CQSSC_YILOU);
			}
		}catch(Exception e){
			log.error("<--重慶遺漏自動降賠 設置異常：SystemConfigAction.CQYLAutoOdds-->",e);
		}
	}
	/**
	 *  根據type 類型更新賠率
	 * @param pO查詢出開關 狀態
	 * @param map key:TypeCode value:0101010011存儲開獎結果（中，不中）
	 * @param option 操作類型   
	 * @throws Exception
	 */
	public void updateOddsYILOU(PeriodAutoOdds pO,Map<String,String> map,String option) throws Exception
	{
		
		List<PeriodAutoOdds> autoOddsList = systemLogic.findAutoOddsByType(option,pO.getShopCode());
		Map<String,PeriodAutoOdds> autoOddsMap =  new HashMap<String,PeriodAutoOdds>();
		for(PeriodAutoOdds o:autoOddsList)
		{
			autoOddsMap.put(o.getName(), o);
		}
		
		PeriodAutoOdds pL = null;
		PeriodAutoOdds pN = null;
		
		if(null != pO && Integer.valueOf(pO.getAutoOdds().toString())==0)//0為啟用 1 為禁用
		{
			for(Map.Entry<String, String> entry: map.entrySet()) {
				
				int countL = getMaxLengthL(entry.getValue());
				int countN = getMaxLengthN(entry.getValue());
				if(countN>25) countN = 25; //超過25期按25期降 
				if(countL>25) countL = 25; //超過25期按25期降 
				pN =  autoOddsMap.get("N_"+countN);
				pL =  autoOddsMap.get("L_"+countL);
				//p==null ==0說明 沒有設置自動將賠 不需要修改賠率
				if((null == pN || pN.getAutoOdds().compareTo(BigDecimal.ZERO)==0) && (null == pL || pL.getAutoOdds().compareTo(BigDecimal.ZERO)==0))  
					continue;
				
				List<ShopsPlayOdds> oddsList = shopOddsLogic.queryShopPlayOddsListByTypeCode(entry.getKey(),pO.getShopCode());
				for(ShopsPlayOdds odds:oddsList)
				{
					odds.setRealUpdateDate(new Date());
					if(null != pN && pN.getAutoOdds().compareTo(BigDecimal.ZERO)==1) //p==null 說明 沒有設置自動將賠 不需要修改賠率 
					{
						odds.setRealOdds(odds.getRealOdds().subtract(pN.getAutoOdds()));
						shopOddsLogic.updateShopOdds(odds);
					} 
					if(null != pL && pL.getAutoOdds().compareTo(BigDecimal.ZERO)==1) //p==null 說明 沒有設置自動將賠 不需要修改賠率  ,=1 為>0
					{
						odds.setRealOdds(odds.getRealOdds().subtract(pL.getAutoOdds()));
						shopOddsLogic.updateShopOdds(odds);
					}
				}
			}
		}
	}
	/**
	 * 廣東 遺漏自動降賠 設置
	 */
	public void  GDYLAutoOdds()
	{
		
			List<PeriodAutoOdds> pOList = systemLogic.queryAutoOddsByTypeName(Constant.GDKLSF_YILOU, "STATE");
			if(pOList ==null || pOList.size()==0) return ;
			 
			
		   List<GDPeriodsInfo> gdPeriodList = periodsInfoLogic.queryLastPeriodsForRefer();

		   List<Integer>  winNums = null; 
		   Map<String,String> map=new HashMap<String,String>();
		   List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		   filtersPlayType.add(Restrictions.and(Restrictions.eq("playType","GDKLSF"),Restrictions.sqlRestriction("type_code not  like 'GDKLSF_DOUBLESIDE%'")));
		   List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		   
		   // type:0101010111
		   for(PlayType playTypeInfo : playTypeList)
		   {
			   GDPeriodsInfo  gdInfo = null;
			   for(int i=0;i<gdPeriodList.size();i++)
			   {
				   gdInfo = gdPeriodList.get(i);
				   winNums = new ArrayList<Integer>();
				   winNums.add(gdInfo.getResult1());
				   winNums.add(gdInfo.getResult2());
				   winNums.add(gdInfo.getResult3());
				   winNums.add(gdInfo.getResult4());
				   winNums.add(gdInfo.getResult5());
				   winNums.add(gdInfo.getResult6());
				   winNums.add(gdInfo.getResult7());
				   winNums.add(gdInfo.getResult8());
				   String winBooleanStr="0";
				   String typeWinStr=map.get(playTypeInfo.getTypeCode());
				   boolean betResult = GDKLSFRule.getBetResult(playTypeInfo, winNums);
				   if(betResult) winBooleanStr="1"; 
				   if(typeWinStr==null)
				   {
					   typeWinStr=winBooleanStr;
				   }else{
					   typeWinStr=typeWinStr+winBooleanStr;
				   }
				   map.put(playTypeInfo.getTypeCode(), typeWinStr);
			   }
		   }
			   
		   try {
			   for(PeriodAutoOdds pO : pOList)
			   {
				   
				   updateOddsYILOU(pO, map, Constant.GDKLSF_YILOU);
			   }
			
		} catch (Exception e) {
			log.error("<--廣東 遺漏自動降賠 設置異常:SystemConfigAction.GDYLAutoOdds-->",e);
		}
			
			
	}
	/**
	 * 連續中的 
	 * @param str
	 * @return
	 */
	public static int getMaxLengthL(final String str){
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
	/**
	 * 連續沒出的 
	 * @param str
	 * @return
	 */
	public static int getMaxLengthN(final String str){
		String first=str.substring(0,1);
		if("0".equals(first))
		{
			int k=str.indexOf("1");
			if(k==-1)
				k=str.lastIndexOf("0")+1;
			return k;
		}
		else 
			return 0;
	}
	/**
	 *  系統初始化 修改將陪 add by Aaron20121201
	 * @return
	 */
	public String updateAutoOddsDoubleSide()
	{
		String userId=null;
//		String type  = null;
//		String radiobutton="";
		try{
			ManagerUser	manager = ((ManagerUser) this.getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION));
			String shopsCode = manager.getShopsInfo().getShopsCode();
			userId = manager.getID().toString();
			
//			formType =  this.getRequest().getParameter("formType");
			PeriodAutoOdds p = null;
//			radiobutton =  this.getRequest().getParameter("radiobutton");//0:啟用，1：禁用
//			String	oddsTL =  this.getRequest().getParameter("oddsTL");//同路
			 Enumeration names = this.getRequest().getParameterNames();
			 while(names.hasMoreElements())
			 {
				 String eleName = (String) names.nextElement();
				 String odds = this.getRequest().getParameter(eleName);
				 if (GenericValidator.isBlankOrNull(odds))
						continue;
				 if (eleName.indexOf("GDKLSF_") != -1 || eleName.indexOf("CQSSC_") != -1|| eleName.indexOf("BJSC_") != -1
						  || eleName.indexOf("K3_") != -1 || eleName.indexOf("NC_") != -1 || eleName.indexOf("SYS_") != -1) {
					 
					 String type = eleName.split("\\$")[0]; 
					 String name = eleName.split("\\$")[1]; 
					p = systemLogic.queryAutoOddsByTypeName(type,name,shopsCode);
					if(null==p) p = new PeriodAutoOdds();
					p.setAutoOdds(BigDecimal.valueOf(Double.valueOf(odds)));
					p.setCreateDate(new Date());
					p.setCreateUserID(Integer.valueOf(userId));
					p.setName(name);
					p.setShopCode(shopsCode);
					p.setType(type);
					systemLogic.saveAutoOdds(p);
					 
				 }
				 
				 
			 }
			
			
		
		}catch(Exception e)
		{
		  log.error("<--系統初始設定異常.updateAutoOddsDoubleSide ",e);
		  return "exception";
		}
		
		return SUCCESS;
	}
	public String getqUserID() {
		return qUserID;
	}

	public void setqUserID(String qUserID) {
		this.qUserID = qUserID;
	}

	public void initCQShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> cqoddList=shopOddsLogic.queryCQSSCRealOdds(shopCode,"CQSSC");
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds=cqoddList.get(i);
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		this.getRequest().setAttribute("CQSSCShopOddsMap", shopOddMap);
		
		
	}
	
	//进入交易设定界面
	public String enterTradingSet(){
		if(subType.indexOf(Constant.LOTTERY_TYPE_GDKLSF)!=-1){
			initGdTradingSetData();
			type="privateAdmin";
			return "gd";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_CQSSC)!=-1){
			initCqTradingSetData();
			type="privateAdmin";
			return "cq";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_BJSC)!=-1){
			initBJTradingSetData();
			return "bjsc";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_K3)!=-1){
			initK3TradingSetData();
			return "K3";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_NC)!=-1){
			initNCTradingSetData();
			return "NC";
		}
		return "input";
	}
	

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	private void initGdTradingSetData() {
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
		Long userId = userInfoNew.getID();
		showList = new ArrayList<UserCommissionDefault>();
		List<UserCommissionDefault> list=userCommissionDefaultLogic.queryCommissionDefaultByType(userId,"GD%");
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			showList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			UserCommissionDefault obj = list.get(j);
			if(obj.getPlayFinalType().trim().equals("GD_ONE_BALL")){showList.set(0, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_TWO_BALL")){showList.set(1, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_THREE_BALL")){showList.set(2, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_FOUR_BALL")){showList.set(3, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_FIVE_BALL")){showList.set(4, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_SIX_BALL")){showList.set(5, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_SEVEN_BALL")){showList.set(6, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_EIGHT_BALL")){showList.set(7, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_OEDX_BALL")){showList.set(8, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_OEDS_BALL")){showList.set(9, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_OEWSDX_BALL")){showList.set(10, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_HSDS_BALL")){showList.set(11, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_FW_BALL")){showList.set(12, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_ZF_BALL")){showList.set(13, obj);}
			//if(obj.getPlayFinalType().trim().equals("GD_B_BALL")){showList.set(14, obj);}大運版把中發和白合并為中發白
			if(obj.getPlayFinalType().trim().equals("GD_ZHDX_BALL")){showList.set(14, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_ZHDS_BALL")){showList.set(15, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_ZHWSDX_BALL")){showList.set(16, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_LH_BALL")){showList.set(17, obj);}
			if(obj.getPlayFinalType().trim().equals("GD_RXH_BALL")){showList.set(18, obj);}  //任選二
			if(obj.getPlayFinalType().trim().equals("GD_RELZ_BALL")){showList.set(19, obj);}  //任二連直
			if(obj.getPlayFinalType().trim().equals("GD_RTLZ_BALL")){showList.set(20, obj);}  //任二連組
			if(obj.getPlayFinalType().trim().equals("GD_RXS_BALL")){showList.set(21, obj);}//任選三
			if(obj.getPlayFinalType().trim().equals("GD_XSQZ_BALL")){showList.set(22, obj);}//選三前直
			if(obj.getPlayFinalType().trim().equals("GD_XTQZ_BALL")){showList.set(23, obj);}//選三前組
			if(obj.getPlayFinalType().trim().equals("GD_RXF_BALL")){showList.set(24, obj);}//任選四
			if(obj.getPlayFinalType().trim().equals("GD_RXW_BALL")){showList.set(25, obj);}//任選五
		}
	}
	
	private void initNCTradingSetData() {
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
		Long userId = userInfoNew.getID();
		showList = new ArrayList<UserCommissionDefault>();
		List<UserCommissionDefault> list=userCommissionDefaultLogic.queryCommissionDefaultByType(userId,"NC%");
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			showList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			UserCommissionDefault obj = list.get(j);
			if(obj.getPlayFinalType().trim().equals("NC_ONE_BALL")){showList.set(0, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_TWO_BALL")){showList.set(1, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_THREE_BALL")){showList.set(2, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_FOUR_BALL")){showList.set(3, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_FIVE_BALL")){showList.set(4, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_SIX_BALL")){showList.set(5, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_SEVEN_BALL")){showList.set(6, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_EIGHT_BALL")){showList.set(7, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_OEDX_BALL")){showList.set(8, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_OEDS_BALL")){showList.set(9, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_OEWSDX_BALL")){showList.set(10, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_HSDS_BALL")){showList.set(11, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_FW_BALL")){showList.set(12, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_ZF_BALL")){showList.set(13, obj);}
			//if(obj.getPlayFinalType().trim().equals("NC_B_BALL")){showList.set(14, obj);}大運版把中發和白合并為中發白
			if(obj.getPlayFinalType().trim().equals("NC_ZHDX_BALL")){showList.set(14, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_ZHDS_BALL")){showList.set(15, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_ZHWSDX_BALL")){showList.set(16, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_LH_BALL")){showList.set(17, obj);}
			if(obj.getPlayFinalType().trim().equals("NC_RXH_BALL")){showList.set(18, obj);}  //任選二
			if(obj.getPlayFinalType().trim().equals("NC_RELZ_BALL")){showList.set(19, obj);}  //任二連直
			if(obj.getPlayFinalType().trim().equals("NC_RTLZ_BALL")){showList.set(20, obj);}  //任二連組
			if(obj.getPlayFinalType().trim().equals("NC_RXS_BALL")){showList.set(21, obj);}//任選三
			if(obj.getPlayFinalType().trim().equals("NC_XSQZ_BALL")){showList.set(22, obj);}//選三前直
			if(obj.getPlayFinalType().trim().equals("NC_XTQZ_BALL")){showList.set(23, obj);}//選三前組
			if(obj.getPlayFinalType().trim().equals("NC_RXF_BALL")){showList.set(24, obj);}//任選四
			if(obj.getPlayFinalType().trim().equals("NC_RXW_BALL")){showList.set(25, obj);}//任選五
		}
	}
	
	private void initBJTradingSetData() {
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
		Long userId = userInfoNew.getID();
		showList = new ArrayList<UserCommissionDefault>();
		List<UserCommissionDefault> list=userCommissionDefaultLogic.queryCommissionDefaultByType(userId,"BJ%");
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			showList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			UserCommissionDefault obj = list.get(j);
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_FIRST")){showList.set(0, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_SECOND")){showList.set(1, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_THIRD")){showList.set(2, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_FORTH")){showList.set(3, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_FIFTH")){showList.set(4, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_SIXTH")){showList.set(5, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_SEVENTH")){showList.set(6, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_EIGHTH")){showList.set(7, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_NINTH")){showList.set(8, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_BALL_TENTH")){showList.set(9, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_1-10_DX")){showList.set(10, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_1-10_DS")){showList.set(11, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_1-5_LH")){showList.set(12, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_DOUBLSIDE_DX")){showList.set(13, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_DOUBLSIDE_DS")){showList.set(14, obj);}
			if(obj.getPlayFinalType().trim().equals("BJ_GROUP")){showList.set(15, obj);}
		}
	}
	
	private void initK3TradingSetData() {
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
		Long userId = userInfoNew.getID();
		showList = new ArrayList<UserCommissionDefault>();
		List<UserCommissionDefault> list=userCommissionDefaultLogic.queryCommissionDefaultByType(userId,"K3%");
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			showList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			UserCommissionDefault obj = list.get(j);
			if(obj.getPlayFinalType().trim().equals("K3_DX")){showList.set(0, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_SJ")){showList.set(1, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_WS")){showList.set(2, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_QS")){showList.set(3, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_DS")){showList.set(4, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_CP")){showList.set(5, obj);}
			if(obj.getPlayFinalType().trim().equals("K3_DP")){showList.set(6, obj);}
		}
	}

	private void initCqTradingSetData() {
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
		Long userId = userInfoNew.getID();
		showList = new ArrayList<UserCommissionDefault>();
		List<UserCommissionDefault> list=userCommissionDefaultLogic.queryCommissionDefaultByType(userId,"CQ%");
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			showList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			UserCommissionDefault obj = list.get(j);
			if(obj.getPlayFinalType().trim().equals("CQ_ONE_BALL")){showList.set(0, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_TWO_BALL")){showList.set(1, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_THREE_BALL")){showList.set(2, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_FOUR_BALL")){showList.set(3, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_FIVE_BALL")){showList.set(4, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_OFDX_BALL")){showList.set(5, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_OFDS_BALL")){showList.set(6, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_ZHDX_BALL")){showList.set(7, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_ZHDS_BALL")){showList.set(8, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_LH_BALL")){showList.set(9, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_QS_BALL")){showList.set(10, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_ZS_BALL")){showList.set(11, obj);}
			if(obj.getPlayFinalType().trim().equals("CQ_HS_BALL")){showList.set(12, obj);}
		}
		
	}
	

	public String updateGdTradingSet(){
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>更新总监下面所有用户拥金开始");
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
			Long userId = userInfoNew.getID();
			long start=System.currentTimeMillis();
			updateUserCommission("GD%");
			long updateTreeTime=System.currentTimeMillis();
			userCommissionDefaultLogic.updateTradingSet(showList,userId);
			long updateDefualtTime=System.currentTimeMillis();
			System.out.println((updateTreeTime-start)/1000+">>>>>>>>>>更新总监下面所有用户佣金时间");
			System.out.println((updateDefualtTime-updateTreeTime)/1000+">>>>>>>>>>更新总监佣金时间");
			oddsSubType="GDKLSF";
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}		
	}
	
	public String updateNCTradingSet(){
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>更新总监下面所有用户拥金开始");
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
			Long userId = userInfoNew.getID();
			long start=System.currentTimeMillis();
			updateUserCommission("NC%");
			long updateTreeTime=System.currentTimeMillis();
			userCommissionDefaultLogic.updateTradingSet(showList,userId);
			long updateDefualtTime=System.currentTimeMillis();
			System.out.println((updateTreeTime-start)/1000+">>>>>>>>>>更新总监下面所有用户佣金时间");
			System.out.println((updateDefualtTime-updateTreeTime)/1000+">>>>>>>>>>更新总监佣金时间");
			oddsSubType="NC";
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}		
	}
	
	public String updateCqTradingSet(){
		try {
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
			Long userId = userInfoNew.getID();
			updateUserCommission("CQ%");
			userCommissionDefaultLogic.updateTradingSet(showList,userId);
			oddsSubType="CQSSC";
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}		
	}
	public String updateBjTradingSet(){
		try {
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
			Long userId = userInfoNew.getID();
			updateUserCommission("BJ%");
			userCommissionDefaultLogic.updateTradingSet(showList,userId);
			oddsSubType="BJSC";
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}		
	}
	
	public String updateK3TradingSet(){
		try {
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
			Long userId = userInfoNew.getID();
			updateUserCommission("K3%");
			userCommissionDefaultLogic.updateTradingSet(showList,userId);
			oddsSubType="K3";
			return SUCCESS;
		} catch (Exception e) {
			return INPUT;
		}		
	}
	
	public void updateUserCommission(String type){
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
	    ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
        chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("managerStaffID",userInfoNew.getID());
        if(showList!=null&&showList.size()!=0)
        userCommissionLogic.batchUpdateChiefUsereCommission(showList);
	}
	
	public List<UserCommission> getUserCommission(List<UserCommission> list,String type){
	    List<UserCommission> testList =Lists.newArrayList(list);
	    if("GD%".equals(type)){
	        for(int j=0; j<list.size(); j++){
	            UserCommission obj = list.get(j);
	            if(obj.getPlayFinalType().trim().equals("GD_ONE_BALL")){testList.set(0, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_TWO_BALL")){testList.set(1, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_THREE_BALL")){testList.set(2, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_FOUR_BALL")){testList.set(3, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_FIVE_BALL")){testList.set(4, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_SIX_BALL")){testList.set(5, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_SEVEN_BALL")){testList.set(6, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_EIGHT_BALL")){testList.set(7, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_OEDX_BALL")){testList.set(8, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_OEDS_BALL")){testList.set(9, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_OEWSDX_BALL")){testList.set(10, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_HSDS_BALL")){testList.set(11, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_FW_BALL")){testList.set(12, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_ZF_BALL")){testList.set(13, obj);}
	            //if(obj.getPlayFinalType().trim().equals("GD_B_BALL")){testList.set(14, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_ZHDX_BALL")){testList.set(14, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_ZHDS_BALL")){testList.set(15, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_ZHWSDX_BALL")){testList.set(16, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_LH_BALL")){testList.set(17, obj);}
	            if(obj.getPlayFinalType().trim().equals("GD_RXH_BALL")){testList.set(18, obj);}  //任選二
	            if(obj.getPlayFinalType().trim().equals("GD_RELZ_BALL")){testList.set(19, obj);}  //任二連直
	            if(obj.getPlayFinalType().trim().equals("GD_RTLZ_BALL")){testList.set(20, obj);}  //任二連組
	            if(obj.getPlayFinalType().trim().equals("GD_RXS_BALL")){testList.set(21, obj);}//任選三
	            if(obj.getPlayFinalType().trim().equals("GD_XSQZ_BALL")){testList.set(22, obj);}//選三前直
	            if(obj.getPlayFinalType().trim().equals("GD_XTQZ_BALL")){testList.set(23, obj);}//選三前組
	            if(obj.getPlayFinalType().trim().equals("GD_RXF_BALL")){testList.set(24, obj);}//任選四
	            if(obj.getPlayFinalType().trim().equals("GD_RXW_BALL")){testList.set(25, obj);}//任選五
	        }
	        return testList;
	    }else if("CQ%".equals(type)){
	        for(int j=0; j<list.size(); j++){
	            UserCommission obj = list.get(j);
	            if(obj.getPlayFinalType().trim().equals("CQ_ONE_BALL")){testList.set(0, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_TWO_BALL")){testList.set(1, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_THREE_BALL")){testList.set(2, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_FOUR_BALL")){testList.set(3, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_FIVE_BALL")){testList.set(4, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_OFDX_BALL")){testList.set(5, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_OFDS_BALL")){testList.set(6, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_ZHDX_BALL")){testList.set(7, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_ZHDS_BALL")){testList.set(8, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_LH_BALL")){testList.set(9, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_QS_BALL")){testList.set(10, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_ZS_BALL")){testList.set(11, obj);}
	            if(obj.getPlayFinalType().trim().equals("CQ_HS_BALL")){testList.set(12, obj);}
	        }
	        return testList;
	    }else if("BJ%".equals(type)){
	        for(int j=0; j<list.size(); j++){
	            UserCommission obj = list.get(j);
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_FIRST")){testList.set(0, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_SECOND")){testList.set(1, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_THIRD")){testList.set(2, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_FORTH")){testList.set(3, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_FIFTH")){testList.set(4, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_SIXTH")){testList.set(5, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_SEVENTH")){testList.set(6, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_EIGHTH")){testList.set(7, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_NINTH")){testList.set(8, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_BALL_TENTH")){testList.set(9, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_1-10_DX")){testList.set(10, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_1-10_DS")){testList.set(11, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_1-5_LH")){testList.set(12, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_DOUBLSIDE_DX")){testList.set(13, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_DOUBLSIDE_DS")){testList.set(14, obj);}
	            if(obj.getPlayFinalType().trim().equals("BJ_GROUP")){testList.set(15, obj);}
	        }
	        return testList;    
	    }
	    return testList;
	}
	
	public String shopLogin(){
	    
		return SUCCESS;
	}
	
	public String chooseLogin(){
		return SUCCESS;
	}
	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String userLogin(){
		return SUCCESS;
	}

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

	public String getOddsSubType() {
		return oddsSubType;
	}

	public void setOddsSubType(String oddsSubType) {
		this.oddsSubType = oddsSubType;
	}

	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}

	public void setUserCommissionDefaultLogic(IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
	}

	public ArrayList<UserCommissionDefault> getShowList() {
		return showList;
	}

	public void setShowList(ArrayList<UserCommissionDefault> showList) {
		this.showList = showList;
	}

	public ISystemLogic getSystemLogic() {
		return systemLogic;
	}

	public void setSystemLogic(ISystemLogic systemLogic) {
		this.systemLogic = systemLogic;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public String getAutoOddsType() {
		return autoOddsType;
	}

	public void setAutoOddsType(String autoOddsType) {
		this.autoOddsType = autoOddsType;
	}

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
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

    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
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
    
    private Date dateStart;
    private Date dateEnd;
    
	public Date getDateStart() {
		return dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	public ILoginLogInfoLogic getLoginLogInfoLogic() {
		return loginLogInfoLogic;
	}

	public void setLoginLogInfoLogic(ILoginLogInfoLogic loginLogInfoLogic) {
		this.loginLogInfoLogic = loginLogInfoLogic;
	}

	public String getPeriodsType() {
		return periodsType;
	}

	public void setPeriodsType(String periodsType) {
		this.periodsType = periodsType;
	}

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}

}

