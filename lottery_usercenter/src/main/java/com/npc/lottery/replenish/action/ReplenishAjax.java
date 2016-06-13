package com.npc.lottery.replenish.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.odds.vo.OddDetail;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.dao.interf.IReplenishDao;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.AutoReplenishSetVO;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.FinishReplenishVO;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.LotteryComparator;
import com.npc.lottery.util.Page;

@SuppressWarnings("serial")
public class ReplenishAjax extends BaseLotteryAction{
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private IShopOddsLogic shopOddsLogic;
	private IUserCommissionLogic userCommissionLogic;
	private IUserCommissionDefault userCommissionDefaultLogic;
	private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    protected IMemberStaffExtLogic memberStaffExtLogic;
    protected IPlayTypeLogic playTypeLogic;
	private IReplenishLogic replenishLogic;
	private IReplenishDao replenishDao = null;
	private static Logger log = Logger.getLogger(ReplenishAjax.class);
    private Integer money;
    private Replenish replenish;
    private List jsonArray;
    
    private IHKPeriodsInfoLogic skperiodsInfoLogic;	
    private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
    private String lotteryType;
	private String shopCode;
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
	
	public String test(){
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		//CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		//BJSCPeriodsInfo runningPeriods = bjscPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		GDPeriodsInfo runningPeriods = periodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
		/*if(runningPeriods!=null){
			String typeCode  = "CQSSC_BALL_FIRST_0";
			ManagerUser userInfoFrom = this.getCurrentManagerUser();
			replenishAutoLogic.updatReplenishAutoCQ(runningPeriods.getPeriodsNum(), typeCode, userInfoFrom,"A");
			
		}*/
		/*if(runningPeriods!=null){
			String typeCode  = "GDKLSF_BALL_FIRST_1";
			ManagerUser userInfoFrom = this.getCurrentManagerUser();
			replenishAutoLogic.updateReplenishAutoGDMenu(runningPeriods.getPeriodsNum(), typeCode, userInfoFrom,"A");
			
		}*/
/*		if(runningPeriods!=null){
			String typeCode  = "BJ_BALL_FIRST_1";
			ManagerUser userInfoFrom = this.getCurrentManagerUser();
			replenishAutoLogic.updateReplenishAutoBJ(runningPeriods.getPeriodsNum(), typeCode, userInfoFrom,"A");
			
		}
*/		return SUCCESS;
	}
	
	public String findReplenishPet(){
		String userId = getRequest().getParameter("id");
		Map<String, String> messageMap = new HashMap<String, String>();
		Long id = (long)0;
		try {
			if(userId!=null){
				id = Long.valueOf(userId);
			}
		} catch (Exception e) {
			log.error("<--數據轉換出錯string換轉成Long：findReplenishPet-->",e);
            return "failure";
		}
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("replenishUserId", id));
		Page<Replenish> page = new Page<Replenish>(1);
		int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        try {
	        page = replenishLogic.findReplenishPet(page,filters
	                .toArray(new Criterion[filters.size()]));
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：findReplenishPet-->",e);
            return "failure";
        }
        if(page.getTotalCount()>0){
        	messageMap.put("stauts","failure");
        }else{
        	messageMap.put("stauts",id.toString());
        }
		return this.ajaxJson(messageMap);
	}
	
	@SuppressWarnings("unused")
	public String replenishSubmit(){
		log.info("进行补货操作");
		ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfoSys);
		} catch (Exception e) {
			log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}
		//子帐号处理*********END
		
		ManagerUser userInfo = new ManagerUser();				
		Long currentUserId = null;
		String userChsName = "";
		Date currentDate = new Date();
		String shopCode = null;
		UserCommission uCommission = new UserCommission();
		Map<String, Replenish> messageMap = new HashMap<String, Replenish>();
//		new ArrayList<BaseBet>();
		String quickNo = getRequest().getParameter("quickNo");
		String typeCode = getRequest().getParameter("typeCode");
		String attribute = getRequest().getParameter("attribute");
		String money = getRequest().getParameter("money");
		String plate = getRequest().getParameter("plate");
		String replenishUserId = getRequest().getParameter("replenishUserId");
		String odds = getRequest().getParameter("odds");
		//String lotteryType = getRequest().getParameter("lotteryType");
		
		String isQuick = "no";
		if(quickNo!=null){
			isQuick="yes";
		}	
		try {
			replenish = new Replenish();
			replenish.setTypeCode(typeCode);
			replenish.setAttribute(attribute);
			replenish.setPlate(plate);
			replenish.setMoney(Integer.valueOf(money));
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
				replenish.setReplenishUserId(Integer.valueOf(replenishUserId));
			    replenish.setOdds(BigDecimal.valueOf(Double.valueOf(odds)));
			}
		} catch (Exception e) {
			replenish =  new Replenish();
			log.info("补货时数据转换错误!"+e.getMessage());
		}
		String periodsNum =  null;
		try {
			periodsNum = getPeriodsNum();  //期数
		} catch (Exception e) {
			log.info("获取盘期错误，手动补货异常！");
			return this.ajaxText("<strong>補貨失敗</strong>");
		}
		if (userInfoSys!=null){
			
			if (periodsNum!=null){
				
				try {
					BeanUtils.copyProperties(userInfo, userInfoNew);
				} catch (Exception e) {
					log.info("replenishAjax replenishSubmit方法出错，转换userInfoSys里出错"+ e.getMessage());
				}
				//子帐号处理
				if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB)){
					ManagerUser userInfoSub = new ManagerUser();
					//获取子帐户的上级
					userInfoSub = subAccountInfoLogic.changeSubAccountInfo(userInfo);
					userInfo.setID(userInfoSub.getID());
					userInfo.setUserType(userInfoSub.getUserType());
					
				}
				List<ShopsPlayOdds> oddsList = null;					
				
				oddsList = shopOddsLogic.queryOddsByTypeCode(userInfoSys.getSafetyCode(),replenish.getTypeCode());  
				ShopsPlayOdds shopsPlayOdds = new ShopsPlayOdds();
				if(oddsList!=null && oddsList.size()>0){
					shopsPlayOdds=oddsList.get(0);
					
				}else{
					log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId);
				}
				try {
					if(Constant.A.equals(replenish.getPlate())){
						replenish.setOdds(shopsPlayOdds.getRealOdds());
					}else if(Constant.B.equals(replenish.getPlate())){
						replenish.setOdds(shopsPlayOdds.getRealOddsB());
					}else{
						replenish.setOdds(shopsPlayOdds.getRealOddsC());
					}
					replenish = replenishLogic.readyReplenishDataForUserId(replenish, userInfo);//获取上级的UserID
					replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo);//获取上级的拥金
					replenish = replenishLogic.readyReplenishDataForRate(replenish, userInfo);//获取上级的占成
					messageMap = replenishLogic.saveReplenish(replenish,userInfo, periodsNum,"",isQuick,Constant.MENU_REPLENISH);
					/*//补货触发的自动降赔
					Integer chiefPet = (BigDecimal.valueOf(replenish.getMoney()).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)))).intValue();
					replenishLogic.updateShopRealOddsForReplenish(userInfo.getSafetyCode(), chiefPet, userInfo.getID(), typeCode, periodsNum);*/
				} catch (Exception e) {
					log.info("手动补货异常！" + e.getMessage());
				}
				try {
					if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
						AutoReplenishSetVO userReplenish = replenishAutoLogic.queryReplenishAutoSetForUser(userInfoNew.getID(), userInfoNew.getShopsInfo().getID().toString(), typeCode);
						if(userReplenish!=null){
							log.info("<--手动补货触发自動補貨   start-->");
							long startTime = System.currentTimeMillis();
							
							BaseBet betOrder = new BaseBet();
							betOrder.setOdds(replenish.getOdds());
							betOrder.setPeriodsNum(replenish.getPeriodsNum());
							betOrder.setPlayType(replenish.getTypeCode());
							betOrder.setPlate(replenish.getPlate());
							
							if(replenish.getChiefStaff()!=null){betOrder.setChiefStaff(replenish.getChiefStaff());}
							if(replenish.getBranchStaff()!=null){betOrder.setBranchStaff(replenish.getBranchStaff());}
							if(replenish.getStockHolderStaff()!=null){betOrder.setStockholderStaff(replenish.getStockHolderStaff());}
							if(replenish.getGenAgenStaff()!=null){betOrder.setGenAgenStaff(replenish.getGenAgenStaff());}
							if(replenish.getAgentStaff()!=null){betOrder.setAgentStaff(replenish.getAgentStaff());}
							
							betOrder.setShopInfo(userInfo.getShopsInfo());
							betOrder.setShopCode(userInfo.getShopsInfo().getShopsCode());
							
							betOrder.setChiefRate(replenish.getRateChief().intValue());
							betOrder.setBranchRate(replenish.getRateBranch().intValue());
							betOrder.setStockHolderRate(replenish.getRateStockHolder().intValue());
							betOrder.setGenAgenRate(replenish.getRateGenAgent().intValue());
							betOrder.setAgentStaffRate(replenish.getRateAgent().intValue());
							
							if(replenish.getCommissionChief()!=null){betOrder.setBranchCommission(replenish.getCommissionChief().doubleValue());}
							if(replenish.getCommissionBranch()!=null){betOrder.setStockHolderCommission(replenish.getCommissionBranch().doubleValue());}
							if(replenish.getCommissionStockHolder()!=null){betOrder.setGenAgenCommission(replenish.getCommissionStockHolder().doubleValue());}
							if(replenish.getCommissionGenAgent()!=null){betOrder.setAgentStaffCommission(replenish.getCommissionGenAgent().doubleValue());}
							if(replenish.getCommissionAgent()!=null){betOrder.setMemberCommission(replenish.getCommissionAgent().doubleValue());}
							
							betOrder.setSplitAttribute(replenish.getAttribute());
							
							replenishAutoLogic.updateReplenishAutoForMenu(betOrder,userInfoNew);
							
							long end = System.currentTimeMillis();
							log.info("<--手动补货触发自動補貨 结束  所用时间："+(end-startTime)/1000+"秒 -->");
						}
					}
				} catch (Exception e) {
					log.info("手动补货时自动补货异常！" + e.getMessage());
				}
				
				
				//整理补货明细start
				List<FinishReplenishVO> jsonArray = new ArrayList<FinishReplenishVO>();  //补货明细
				FinishReplenishVO fVo = new FinishReplenishVO();
				Replenish vo = messageMap.get("success");
				fVo.setOrderNo(vo.getOrderNo());
	
				fVo.setDetail(vo.getTypeCodeNameOdd());
				fVo.setMoney(vo.getMoney());
				BigDecimal vMoney = BigDecimal.valueOf(vo.getMoney()).multiply(vo.getOdds()).subtract(BigDecimal.valueOf(vo.getMoney()));
				fVo.setWinMoney(vMoney.setScale(1, BigDecimal.ROUND_HALF_UP));
				
				fVo.setResult("<strong>成功補出</strong>");
				fVo.setQuickNo(quickNo);//记住快速补货的序列号
				fVo.setWinState(vo.getWinState());	
				jsonArray.add(fVo);	
				return  this.ajaxText(JSONArray.toJSONString(jsonArray));
		        //整理补货明细END
			}else{
				log.info("该用户没有权限补货" + ",商鋪號為:" + shopCode + ",用戶：" + userChsName);
				//messageMap.put("errorMessage", "该用户没有权限补货");
				//return this.ajaxText("该用户没有权限补货");
				return this.ajaxText("<strong>補貨失敗</strong>");
			}						
		} 
		
		//messageMap.put("success","補貨成功");
		return this.ajaxText("補貨成功");
		
	}
	

	//获取当前赔率
	public String queryRealOdds(){
		Map<String, String> messageMap = new HashMap<String, String>();
		List<Replenish> jsonArray = new ArrayList<Replenish>();  //补货明细
		List<ShopsPlayOdds> oddsList = null;
		replenish = new Replenish();
		String typeCode = getRequest().getParameter("typeCode");
		String attribute = getRequest().getParameter("attribute");
		replenish.setTypeCode(typeCode);
		replenish.setAttribute(attribute);
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		shopCode = userInfo.getSafetyCode();
		
		if(replenish.getTypeCode().indexOf("HK_SXL")!=-1 || replenish.getTypeCode().indexOf("HK_WSL")!=-1
				 || replenish.getTypeCode().indexOf("HK_WBZ")!=-1 || replenish.getTypeCode().indexOf("HK_GG")!=-1){   //处理生肖连赔率，取组合中最小的赔率
			
			if(replenish.getTypeCode().indexOf("HK_SXL")!=-1 || replenish.getTypeCode().indexOf("HK_WSL")!=-1 || replenish.getTypeCode().indexOf("HK_WBZ")!=-1){
					Map<String,ShopsPlayOdds> shopMap=null;
					if(replenish.getTypeCode().indexOf("HK_SXL")!=-1){
						shopMap=this.initLMShopOdds(shopCode, replenish.getTypeCode());
					}
					if(replenish.getTypeCode().indexOf("HK_WSL")!=-1){
						shopMap=this.initLMShopOdds(shopCode, replenish.getTypeCode());
					}
					if(replenish.getTypeCode().indexOf("HK_WBZ")!=-1){
						shopMap=this.initLMShopOdds(shopCode, "HK_WBZ");
					}						
					BigDecimal odds=new BigDecimal(0f);
    				List<BigDecimal> oddList=new ArrayList<BigDecimal>();	     				
    				String[] splitAttArray=StringUtils.split(replenish.getAttribute(),"|");
    				
    				List<String> selecedOdds=Lists.newArrayList(); //用来存五不中、过关、生肖连和尾数连的每个选择球的赔率
    				
    				for (int j = 0; j < splitAttArray.length; j++) {
    					String checkBall=splitAttArray[j];	
    					ShopsPlayOdds shopOdds= new ShopsPlayOdds();
    					if(replenish.getTypeCode().indexOf("HK_WBZ")!=-1){
    						shopOdds=shopMap.get(Integer.valueOf(checkBall).toString());
    					}else{
    						shopOdds=shopMap.get(checkBall);
    					}
						
    					BigDecimal	shopOdd=shopOdds.getRealOdds();
    					oddList.add(shopOdd);
    				}
    				Collections.sort(oddList);
    				odds=oddList.get(0);
    				replenish.setOdds(odds);
    				replenish.setStringOdd(odds.toString());
			}
			List<String> selecedOdds=Lists.newArrayList(); 
			
			if(replenish.getTypeCode().indexOf("HK_GG")!=-1){
				BigDecimal odds=new BigDecimal(1f);
				Map<String,ShopsPlayOdds> shopMap=initLMShopOdds(shopCode,"HK_GG");
				//设置过关赔率
				String[] splitAttArray=StringUtils.split(replenish.getAttribute(),"|");
				for (int i = 0; i < splitAttArray.length; i++) {
					String checkBall=splitAttArray[i];
				    
				    ShopsPlayOdds shopOdds=shopMap.get(checkBall);
	                BigDecimal	shopOdd=shopOdds.getRealOdds();
				    odds=odds.multiply(shopOdd);
				}
				odds=odds.setScale(2, BigDecimal.ROUND_HALF_UP);				
				replenish.setOdds(odds);	
				replenish.setStringOdd(odds.toString());
			}
		}else{
			oddsList = shopOddsLogic.queryOddsByTypeCode(shopCode,replenish.getTypeCode());   
			replenish.setOdds(oddsList.get(0).getRealOdds());  //玩法的当前赔率,而不是投注页面的赔率
			replenish.setStringOdd(replenish.getOdds().toString());
		}
		
		
		//处理香港三中二和二中特赔率，这两种情况要插入ODDS字段，以便对补货的记录进行兑奖处理
		if(replenish.getTypeCode().equals("HK_STRAIGHTTHROUGH_3Z2")){
			if(oddsList.get(0).getOddsType().equals("HK_LM_3Z2")){
				replenish.setOdds(oddsList.get(0).getRealOdds());
				replenish.setOdds2(oddsList.get(1).getRealOdds());
				replenish.setStringOdd("中二@" + replenish.getOdds() + " 中三@" + replenish.getOdds2());
			}else{
				replenish.setOdds(oddsList.get(1).getRealOdds());
				replenish.setOdds2(oddsList.get(0).getRealOdds());
				replenish.setStringOdd("中二@" + replenish.getOdds() + " 中三@" + replenish.getOdds2());
			}
			if(oddsList.get(1).getOddsType().equals("HK_LM_Z3")){
				replenish.setOdds(oddsList.get(0).getRealOdds());
				replenish.setOdds2(oddsList.get(1).getRealOdds());
				replenish.setStringOdd("中二@" + replenish.getOdds() + " 中三@" + replenish.getOdds2());
			}else{
				replenish.setOdds(oddsList.get(1).getRealOdds());
				replenish.setOdds2(oddsList.get(0).getRealOdds());
				replenish.setStringOdd("中二@" + replenish.getOdds() + " 中三@" + replenish.getOdds2());
			}						
		}
		//处理香港二中特赔率
		if(replenish.getTypeCode().equals("HK_STRAIGHTTHROUGH_2ZT")){
			if(oddsList.get(0).getOddsType().equals("HK_LM_2ZT")){
				replenish.setOdds(oddsList.get(0).getRealOdds());
				replenish.setOdds2(oddsList.get(1).getRealOdds());
				replenish.setStringOdd("中特@" + replenish.getOdds() + " 中二@" + replenish.getOdds2());
			}else{
				replenish.setOdds(oddsList.get(1).getRealOdds());
				replenish.setOdds2(oddsList.get(0).getRealOdds());
				replenish.setStringOdd("中特@" + replenish.getOdds() + " 中二@" + replenish.getOdds2());
			}
			if(oddsList.get(1).getOddsType().equals("HK_LM_Z2")){
				replenish.setOdds(oddsList.get(0).getRealOdds());
				replenish.setOdds2(oddsList.get(1).getRealOdds());
				replenish.setStringOdd("中特@" + replenish.getOdds() + " 中二@" + replenish.getOdds2());
			}else{
				replenish.setOdds(oddsList.get(1).getRealOdds());
				replenish.setOdds2(oddsList.get(0).getRealOdds());
				replenish.setStringOdd("中特@" + replenish.getOdds() + " 中二@" + replenish.getOdds2());
			}	
		}
		messageMap.put("odd",replenish.getStringOdd());
		return this.ajaxJson(messageMap);
	}
	
	
	//获取盘期
	public String getPeriodsNum(){
		
		ManagerUser userInfo =(ManagerUser) this.getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		String periodsNum = null;
		shopCode =userInfo.getSafetyCode();
		if(this.replenish.getTypeCode().indexOf("HKLHC")!=-1)
		{
			HKPeriods runningPeriods = skperiodsInfoLogic.queryRunningPeriods(shopCode);
			periodsNum = runningPeriods.getPeriodsNum();
		}else if(this.replenish.getTypeCode().indexOf("CQSSC")!=-1){
			CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.findCurrentPeriod();
			periodsNum = runningPeriods.getPeriodsNum();
		}else if(this.replenish.getTypeCode().indexOf("GDKLSF")!=-1){
			GDPeriodsInfo runningPeriods=periodsInfoLogic.findCurrentPeriod();
			periodsNum = runningPeriods.getPeriodsNum();
		}else if(this.replenish.getTypeCode().indexOf("BJ")!=-1){
			BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.findCurrentPeriod();
			periodsNum = runningPeriods.getPeriodsNum();
		}else if(this.replenish.getTypeCode().indexOf("K3")!=-1){
			JSSBPeriodsInfo runningPeriods=jssbPeriodsInfoLogic.findCurrentPeriod();
			periodsNum = runningPeriods.getPeriodsNum();
		}else if(this.replenish.getTypeCode().indexOf("NC")!=-1){
			NCPeriodsInfo runningPeriods=ncPeriodsInfoLogic.findCurrentPeriod();
			periodsNum = runningPeriods.getPeriodsNum();
		}else{
			periodsNum = null;
			
		}
		return periodsNum;
		
	}
	
	private String ajaxSubmitResult(String period, Replenish replenish) {

		StringBuffer html = new StringBuffer();
		html.append("<div id=\"fr\" class=\"p\">");

		html.append("<div class=\"print_btn\"><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"10px\" class=\"print_l\">");
		html.append("<tbody><tr><th colspan=\"2\">" + period
				+ "期</th></tr><tr><td colspan=\"2\"><ul>");
		
		Integer price = replenish.getMoney();
		BigDecimal odds = replenish.getOdds();
		String typeCode = replenish.getTypeCode();
		PlayType playType = playTypeLogic.getPlayTypeByTypeCode(typeCode);
		//String subType = playType.getSubTypeName();
		String typeName = playType.getFinalTypeName();
		String orderNo = replenish.getOrderNo();
		float winMoney = odds.floatValue() * price;
		float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

		html.append("<li><p>補貨單号：" + orderNo
				+ "#</p><p class=\"t_center\"><span class=\"blue\">"
				+ " 『 " + typeName
				+ " 』</span>@ <strong style=\"color:red\">" + odds
				+ "</strong></p><p>下注额：" + price + "</p><p>可赢额："
				+ roundMoney + "</p></li>");


		html.append("</ul></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">補貨笔数</td><td width=\"66%\">"
				+ 1 + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">補貨合计</td><td>￥" + price
				+ "</td></tr>");
		html.append("</tbody></table></div>");

		return html.toString();

	}
	
	public String queryFinishReplenish(){
		List<Replenish> replenishList = null;  //补货人_补货球表
		List<FinishReplenishVO> jsonArray = new ArrayList<FinishReplenishVO>();  //补货明细
		String typeCode = getRequest().getParameter("typeCode");
		lotteryType = getRequest().getParameter("lotteryType");
		
		ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfoSys);
		} catch (Exception e) {
		  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		   }	
		//子帐号处理*********END
		   
		ManagerUser userInfo =new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfo, userInfoNew);
		} catch (Exception e) {
			log.info("replenishAjax replenishSubmit方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		//子帐号处理
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB)){
			ManagerUser userInfoSub = new ManagerUser();
			userInfoSub = subAccountInfoLogic.changeSubAccountInfo(userInfo);
			userInfo.setID(userInfoSub.getID());
			userInfo.setUserType(userInfoSub.getUserType());
		}
		String periodsNum = getPeriodsNum();
		Long userId = null;
		String plate = Constant.DEFUALT_PLATE; 		
		
		if (userInfo!=null && periodsNum!=null){
			userId = userInfo.getID();				
			replenishList = replenishLogic.queryFinishReplenish("replenish_user_id", userId, typeCode, periodsNum, plate);		
			for(int i=0; i<replenishList.size(); i++){
				FinishReplenishVO fVo = new FinishReplenishVO();
				Replenish vo = replenishList.get(i);
				fVo.setOrderNo(vo.getOrderNo());
	
				fVo.setDetail(vo.getTypeCodeNameOdd());
				fVo.setMoney(vo.getMoney());
				//派彩额(可赢金额)=投注额*(赔率-1)
				BigDecimal vMoney = BigDecimal.valueOf(vo.getMoney()).multiply(vo.getOdds()).subtract(BigDecimal.valueOf(vo.getMoney()));
				fVo.setWinMoney(vMoney.setScale(1, BigDecimal.ROUND_HALF_UP));
				fVo.setResult("成功補出");	
				fVo.setWinState(vo.getWinState());	
				jsonArray.add(fVo);	
			}
	    }
		return  this.ajaxText(JSONArray.toJSONString(jsonArray));
	}
	
	//查询连码或复式补货的结果
	public String queryFinishReplenish_LM(){
		List<Replenish> replenishList = null;  //补货人_补货球表
		List<FinishReplenishVO> jsonArray = new ArrayList<FinishReplenishVO>();  //补货明细
		String typeCode = getRequest().getParameter("typeCode");
		lotteryType = getRequest().getParameter("lotteryType");
		String attribute = getRequest().getParameter("attribute");
		
		ManagerUser userInfoSys = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfoSys);
		} catch (Exception e) {
		  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			  userInfoNew = getSubAccountParent(userInfoNew);	
		   }	
		//子帐号处理*********END
		
		ManagerUser userInfo =new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfo, userInfoNew);
		} catch (Exception e) {
			log.info("replenishAjax replenishSubmit方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		String periodsNum = getPeriodsNum();
		Long userId = null;
		String plate = Constant.DEFUALT_PLATE; 		
		
		if (userInfo!=null && periodsNum!=null){
			userId = userInfo.getID();				
			replenishList = replenishLogic.queryFinishReplenish_LM("replenish_user_id", userId, typeCode, periodsNum, plate, attribute);		
			
			for(int i=0; i<replenishList.size(); i++){
				FinishReplenishVO fVo = new FinishReplenishVO();
				Replenish vo = replenishList.get(i);
				fVo.setOrderNo(vo.getOrderNo());
				fVo.setDetail(vo.getTypeCodeNameOdd());
				fVo.setMoney(vo.getMoney());
				fVo.setWinMoney(BigDecimal.valueOf(vo.getMoney()).multiply(vo.getOdds()).subtract(BigDecimal.valueOf(vo.getMoney())));
				fVo.setResult("成功補出");	
				fVo.setWinState(vo.getWinState());	
				jsonArray.add(fVo);	
			}
	    }
		return  this.ajaxText(JSONArray.toJSONString(jsonArray));
	}
	
	//实时滚单
	public String getRealTimeDetail(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ManagerUser currentUserInfo = (ManagerUser) getRequest().getSession().getAttribute(
				Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		List<DetailVO> detailList=new ArrayList<DetailVO>();
		try {
			BeanUtils.copyProperties(userInfoNew, currentUserInfo);
		} catch (Exception e) {
		  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
	   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
		userInfoNew = getSubAccountParent(userInfoNew);	
	   }	
		//子帐号处理*********END
		//获取查询时间
		String queryTime=this.getRequest().getParameter("queryTime"); 
		if(null == queryTime){
			queryTime=df.format(new Date());
		}
		Page<DetailVO> page = new Page<DetailVO>(50);
		Page<DetailVO> pageInAndOut = new Page<DetailVO>(50);
		String typeCode = null;
		String subType = getRequest().getParameter("subType");
		String lowestMoney = getRequest().getParameter("lowestMoney");
		Integer money = 0;
		if(lowestMoney!=null){
			try {
				money = Integer.valueOf(lowestMoney);
			} catch (Exception e) {
				money = 0;
			}
		}
		
		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
		filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
		GDPeriodsInfo runningPeriodsGD = new GDPeriodsInfo();
		CQPeriodsInfo runningPeriodsCQ = new CQPeriodsInfo();
		BJSCPeriodsInfo runningPeriodsBJ = new BJSCPeriodsInfo();
		JSSBPeriodsInfo runningPeriodsK3 = new JSSBPeriodsInfo();
		NCPeriodsInfo runningPeriodsNC = new NCPeriodsInfo();
		//根据玩法查询最新盘期
		String periodsNum = "";
		if(subType.indexOf("GDKLSF")!=-1){
			runningPeriodsGD = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsGD!=null) 	periodsNum = runningPeriodsGD.getPeriodsNum();
		}else if(subType.indexOf("CQSSC")!=-1){
			runningPeriodsCQ = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsCQ!=null) 	periodsNum = runningPeriodsCQ.getPeriodsNum();
		}else if(subType.indexOf("BJ")!=-1){
			runningPeriodsBJ = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsBJ!=null) 	periodsNum = runningPeriodsBJ.getPeriodsNum();
		}else if(subType.indexOf("K3")!=-1){
			runningPeriodsK3 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsK3!=null) 	periodsNum = runningPeriodsK3.getPeriodsNum();
		}else if(subType.indexOf("NC")!=-1){
			runningPeriodsNC = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsNC!=null) 	periodsNum = runningPeriodsNC.getPeriodsNum();
		}
			    	
        Timestamp searchTime = Timestamp.valueOf(queryTime);
        
        page=replenishLogic.queryBetDetail_RealTime(page, userInfoNew, typeCode, periodsNum,subType,searchTime,money);
		if(page.getResult().size()>0){
			detailList.addAll(page.getResult());
		}
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(subType)){
			typeCode = "GDKLSF%";
		}else if(Constant.LOTTERY_TYPE_NC.equals(subType)){
				typeCode = "NC%";
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(subType)){
			typeCode = "CQSSC%";
		}else if(Constant.LOTTERY_TYPE_BJ.equals(subType)){
			typeCode = "BJ%";
		}else{
			typeCode = "K3%";
		}
		pageInAndOut=replenishLogic.queryReplenishInAndOutDetail_RealTime(pageInAndOut, currentUserInfo, typeCode, periodsNum, searchTime, money);
		//合并再排序
		if(pageInAndOut.getResult().size()>0){
			detailList.addAll(pageInAndOut.getResult());
		}
		
		//按投注時間排序，最新的排前面
		Collections.sort(detailList,new Comparator<DetailVO>(){   
			public int compare(DetailVO arg0, DetailVO arg1) {   
				return arg0.getBettingDate().compareTo(arg1.getBettingDate());   
			}   
		});
		//按注单好排序，最大的排前面
		Collections.sort(detailList,new Comparator<DetailVO>(){   
			public int compare(DetailVO arg0, DetailVO arg1) { 
				return arg0.getOrderNo().compareTo(arg1.getOrderNo());   
			}   
		});
		Map<String, Object> messageMap = new HashMap<String, Object>();

		messageMap.put("detail", detailList);
		
		//查询自动降赔的记录START
		String shopCode= userInfoNew.getSafetyCode();
		
		Page<OddDetail> pageOdd = new Page<OddDetail>(5);
        try {
        	pageOdd = shopOddsLogic.queryShopsPlayOddsLog(pageOdd, shopCode, periodsNum, typeCode, searchTime,Constant.ODD_LOG_MENU);
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：getRealTimeDetail-->",e);
            return "exception";
        }
        messageMap.put("detailOdd", pageOdd.getResult());	        
         //查询自动降赔的记录END
        return this.ajaxObjectJson(messageMap);
	}
	
	//备份
	public String backupDetail(){
		String periodsNum = "";
		Page<DetailVO> page = new Page<DetailVO>(9999999);
		Page<DetailVO> pageIn = new Page<DetailVO>(9999999);
		Page<DetailVO> pageOut = new Page<DetailVO>(9999999);
		int pageNo=1;
		page.setPageNo(pageNo);
		pageIn.setPageNo(pageNo);
		pageOut.setPageNo(pageNo);
		if(getRequest().getParameter("periodNum")!=null){
			periodsNum = getRequest().getParameter("periodNum");
		}
		String subType = getRequest().getParameter("subType");
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
		   
	   List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
       //filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
	   filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
	
       filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
		
		GDPeriodsInfo runningPeriodsGD = new GDPeriodsInfo();
		CQPeriodsInfo runningPeriodsCQ = new CQPeriodsInfo();
		BJSCPeriodsInfo runningPeriodsBJ = new BJSCPeriodsInfo();
		JSSBPeriodsInfo runningPeriodsK3 = new JSSBPeriodsInfo();
		NCPeriodsInfo runningPeriodsNC = new NCPeriodsInfo();
		String typeCode = "";
		String fileType = "";//导出文件的时候用于区别文件名
		
		if(subType.indexOf("GDKLSF")!=-1){
			typeCode = Constant.LOTTERY_TYPE_GDKLSF + "%";
			fileType = "1";
			runningPeriodsGD = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsGD!=null) 	periodsNum = runningPeriodsGD.getPeriodsNum();
		}else if(subType.indexOf("CQSSC")!=-1){
			fileType = "2";
			typeCode = Constant.LOTTERY_TYPE_CQSSC + "%";
			runningPeriodsCQ = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsCQ!=null) 	periodsNum = runningPeriodsCQ.getPeriodsNum();
		}else if(subType.indexOf("BJ")!=-1){
			fileType = "3";
			typeCode = Constant.LOTTERY_TYPE_BJ + "%";
			runningPeriodsBJ = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsBJ!=null) 	periodsNum = runningPeriodsBJ.getPeriodsNum();
		}else if(subType.indexOf("K3")!=-1){
			fileType = "4";
			typeCode = Constant.LOTTERY_TYPE_K3 + "%";
			runningPeriodsK3 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsK3!=null) 	periodsNum = runningPeriodsK3.getPeriodsNum();
		}else if(subType.indexOf("NC")!=-1){
			fileType = "5";
			typeCode = Constant.LOTTERY_TYPE_NC + "%";
			runningPeriodsNC = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriodsNC!=null) 	periodsNum = runningPeriodsNC.getPeriodsNum();
		}
		page=replenishLogic.queryBetDetail_Backup(page, userInfoNew, typeCode, periodsNum,subType);   
		pageIn=replenishLogic.queryReplenishInDetail_Backup(pageIn, userInfoNew, typeCode, periodsNum);
		pageOut=replenishLogic.queryReplenishOutDetail_Backup(pageOut, userInfoNew, typeCode, periodsNum);   
		
		//合并再排序
		if(pageIn.getResult().size()>0){
			page.getResult().addAll(pageIn.getResult());
		}
		if(pageOut.getResult().size()>0){
			page.getResult().addAll(pageOut.getResult());
		}
		
		
		//按号码排序
		Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
			public int compare(DetailVO arg0, DetailVO arg1) {   
				return new LotteryComparator().compare(arg0.getTypeCode(), arg1.getTypeCode());   
			}   
		});
		//按投注時間排序，最新的排前面
		Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
			public int compare(DetailVO arg0, DetailVO arg1) {   
				return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
			}   
		});
		//***********开始导出数据到CSV*****START*****
		

      
		 
        try {

        	this.getResponse().setContentType("application/csv;charset=UTF-8"); 
        	this.getResponse().setHeader("Content-disposition",   
        			"attachment;filename=" +   
        					URLEncoder.encode(fileType+"["+periodsNum+"].csv", "UTF-8"));   
        	  OutputStream fos =  this.getResponse().getOutputStream();   
              BufferedOutputStream bos  = new BufferedOutputStream(fos);   
              //这个就就是弹出下载对话框的关键代码   
            StringBuffer finalSql=new StringBuffer();
            if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
            	bos.write("註單號碼,下注時間,下注類型,會員,註單明細,下注金額,代理%,總代理%,股東%,分公司%,總監%\n".getBytes());
            }else if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
            	bos.write("註單號碼,下注時間,下注類型,會員,註單明細,下注金額,代理%,總代理%,股東%,分公司%\n".getBytes());
			}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoNew.getUserType())){
				bos.write("註單號碼,下注時間,下注類型,會員,註單明細,下注金額,代理%,總代理%,股東%\n".getBytes());
			}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoNew.getUserType())){
				bos.write("註單號碼,下注時間,下注類型,會員,註單明細,下注金額,代理%,總代理%\n".getBytes());
			}else if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoNew.getUserType())){
				bos.write("註單號碼,下注時間,下注類型,會員,註單明細,下注金額,代理%\n".getBytes());
			}
        	java.text.DateFormat format1 = new java.text.SimpleDateFormat("hh:mm:ss");
			for(DetailVO v:page.getResult()){
				String orderNo=v.getOrderNo();
				String bettingDate=format1.format(v.getBettingDate());
				String playTypeName=v.getPlayTypeName();
				String userName=v.getUserName();
				String codeName=v.getTypeCodeNameOddForFile();
				//处理连码的显示
				String moneyDis = null;
				if(v.getAttribute()!=null && v.getCount()>1){
					moneyDis = v.getMoney()/v.getCount() + "x" + v.getCount() + "組 " + v.getMoney();
				}else{
					moneyDis = "" + v.getMoney();
				}
				String money = moneyDis;
				String agentRate=v.getAgentRate().setScale(1,BigDecimal.ROUND_HALF_UP).toString();
				String genAgentRate=v.getGenAgentRate().setScale(1,BigDecimal.ROUND_HALF_UP).toString();
				String stockRate=v.getStockRate().setScale(1,BigDecimal.ROUND_HALF_UP).toString();
				String branchRate=v.getBranchRate().setScale(1,BigDecimal.ROUND_HALF_UP).toString();
				String chiefRate=v.getChiefRate().setScale(1,BigDecimal.ROUND_HALF_UP).toString();
				String body="";
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfoNew.getUserType())){
					body=orderNo+"#,"+bettingDate+","+playTypeName+","+userName+","+codeName+","+money+","+agentRate+","+genAgentRate+","+stockRate+","+branchRate+","+chiefRate+"\n";
	            }else if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
	            	body=orderNo+"#,"+bettingDate+","+playTypeName+","+userName+","+codeName+","+money+","+agentRate+","+genAgentRate+","+stockRate+","+branchRate+"\n";
				}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoNew.getUserType())){
					body=orderNo+"#,"+bettingDate+","+playTypeName+","+userName+","+codeName+","+money+","+agentRate+","+genAgentRate+","+stockRate+"\n";
				}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoNew.getUserType())){
					body=orderNo+"#,"+bettingDate+","+playTypeName+","+userName+","+codeName+","+money+","+agentRate+","+genAgentRate+"\n";
				}else if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoNew.getUserType())){
					body=orderNo+"#,"+bettingDate+","+playTypeName+","+userName+","+codeName+","+money+","+agentRate+"\n";
				}
				bos.write(body.getBytes());
			}
			 bos.flush();   
             fos.close();   
             bos.close();   
		} catch (IOException e) {
			e.printStackTrace();
		}  
 

		return null;
	}

	public String convertENToCN(String[] attribute,String attributeType)
	{
		if(null == attribute || attribute.length==0) return "";
		String attrCNName = "";
		if("SXL".equals(attributeType))
		{
			for(int i=0;i<attribute.length;i++)
			{
				attrCNName += Constant.HK_SX_MAP.get(attribute[i])+" ";
			}	
		}else if("WSL".equals(attributeType))
		{
			for(int i=0;i<attribute.length;i++)
			{
				attrCNName += Constant.HK_WS_MAP.get(attribute[i])+" ";
			}	
		}else{

			for(int k = 0; k< attribute.length; k++){
				String[] sub = attribute[k].split("_");
				String maCode = MA_CODE.get(sub[0]);
				String betCode =BET_CODE.get(sub[1]);					
				String tempChinaName = "(" + maCode + "_" + betCode + ")";
				attrCNName = attrCNName + tempChinaName;
			}

		}
		
		return attrCNName;
	}
	/**
	 * 根据typeCode 返回当前是否封号
	 * @return
	 */
	public String getTypeCodeState()
	{
		String json = "";
		ManagerUser userInfo =(ManagerUser) this.getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		String shopCode = userInfo.getSafetyCode();
		String typeCode = this.getRequest().getParameter("typeCode");
		String panelType = this.getRequest().getParameter("panelType");
		String attribute = this.getRequest().getParameter("attribute");
		if(panelType ==null) json = "";
		
		ShopsPlayOdds shopodd = null;  
		if("GDBALL".equals(panelType)  || "GDZHLH".equals(panelType) || "GDLM".equals(panelType) || "BJ".equals(panelType) || "K3".equals(panelType)
				|| "NCBALL".equals(panelType)  || "NCZHLH".equals(panelType) || "NCLM".equals(panelType) || "CQBH".equals(panelType))
		{
			shopodd = shopOddsLogic.queryShopPlayOdds(shopCode,typeCode);
			if(shopodd ==null) shopodd = new ShopsPlayOdds();
			json = 	shopodd.getState();
		}else if("LM".equals(panelType))
		{
			List<ShopsPlayOdds>  list = shopOddsLogic.queryHKOdds(shopCode,typeCode);
			String tmp= "";
			if(list != null) 
			{
				for(ShopsPlayOdds ent : list)
				{
					if("1".equals(ent.getState())) //==1 被 封號
					{
						tmp = ent.getState();
					}
				}
			}
			json = 	tmp;
			
		}else if("SXL".equals(panelType) || "WSL".equals(panelType) || "WBZ".equals(panelType) || "GG".equals(panelType))
		{
			String[] shuxing = attribute.split("\\|");
			for(int i=0;i<shuxing.length;i++)
			{
				shopodd = shopOddsLogic.queryShopPlayOddsByTypeCodeTypeX(shopCode,typeCode,shuxing[i]);
				if("1".equals(shopodd.getState())) //==1 被 封號
				{
					json = shopodd.getState();
				}
			}
		}
		
		return this.ajaxText(json);
	}
	
	public Map<String,ShopsPlayOdds> initLMShopOdds(String shopCode,String playType)
	{
		List<ShopsPlayOdds> hkoddList=shopOddsLogic.queryOddsByTypeCode(shopCode,playType);
		Map<String,ShopsPlayOdds> oddMap=Maps.newHashMap();
		for (int i = 0; i < hkoddList.size(); i++) {
			ShopsPlayOdds shopodds=	hkoddList.get(i);
			oddMap.put(shopodds.getOddsTypeX(), shopodds);
			
		}
		return oddMap;
		
	}
	
	public String getTodayWin()
	{
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);;
	    
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
  	    //***************今天输赢处理START
		java.sql.Date startDate = null;
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
		Double winData = settledReportEricLogic.queryTodayWinLose(userInfoNew.getID(), userInfoNew.getUserType(), "ALL", "ALL", periodNum,startDate, endDate,"");
		DecimalFormat df = new DecimalFormat("0.0"); 
		String json = "0.0";
		if(null != winData){
			json = df.format(BigDecimal.valueOf(winData).setScale(1,BigDecimal.ROUND_HALF_UP));   //格式化显示
		}
		Map<String,String> todayWinMap = new HashMap<String,String>();
		todayWinMap.put("winData", json);
		return this.ajaxText(json);
		
	}
	
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic	= null;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private IReplenishAutoLogic replenishAutoLogic;
	private ISettledReportEricLogic settledReportEricLogic;
	
	public Replenish getReplenish() {
		return replenish;
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

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}

	public void setReplenish(Replenish replenish) {
		this.replenish = replenish;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}

	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
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

	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
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

	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}

	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}

	public void setUserCommissionDefaultLogic(
			IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public ISettledReportEricLogic getSettledReportEricLogic() {
		return settledReportEricLogic;
	}

	public void setSettledReportEricLogic(
			ISettledReportEricLogic settledReportEricLogic) {
		this.settledReportEricLogic = settledReportEricLogic;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public IReplenishDao getReplenishDao() {
		return replenishDao;
	}

	public void setReplenishDao(IReplenishDao replenishDao) {
		this.replenishDao = replenishDao;
	}

	public List getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(List jsonArray) {
		this.jsonArray = jsonArray;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}


	public void setShopsPlayOddsLogLogic(
			IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}


	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}

	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
		return skperiodsInfoLogic;
	}

	public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
		this.skperiodsInfoLogic = skperiodsInfoLogic;
	}

}
