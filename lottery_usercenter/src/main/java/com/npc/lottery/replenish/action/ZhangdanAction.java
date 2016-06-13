package com.npc.lottery.replenish.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.PlayTypeSort;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.entity.Zhangdan;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.logic.interf.IZhangdanLogic;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.PlayTypeUtils;

/**
 * 报表统计Action
 * 
 */
public class ZhangdanAction extends BaseLotteryAction {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ZhangdanAction.class);

    private String type;
    private String isUp = "false"; //是否是上级往下级查询,默认为否
    private String sitemeshType = "report"; //用于页面菜单显示
    
    java.math.BigDecimal totalCommissionMoney = BigDecimal.ZERO;
    java.math.BigDecimal totalMoney = BigDecimal.ZERO;//注额*占成=实占金额
    Integer totalTurnover = 0; //笔数合計  

    /**
     * 报表列表
     * 
     * @return
     * @throws Exception
     */
    public String queryZhangdan() throws Exception {
    	
    	String periodsNum = "";

        ManagerUser currentUserInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        String currentUserType = null;

        ManagerUser userInfoSub = new ManagerUser(); //存子帐号信息
    	boolean isSubAccount = currentUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//子帐号
    	//子帐号处理
        if(isSubAccount){
			userInfoSub = subAccountInfoLogic.changeSubAccountInfo(currentUserInfo);
        }
        //子帐号处理
        if(isSubAccount){
			userID = userInfoSub.getID();
			userType = userInfoSub.getUserType();
		}else{
			userID = currentUserInfo.getID();
            userType = currentUserInfo.getUserType();
		}
        if(isSubAccount){
        	currentUserType = userInfoSub.getUserType();
        	
        }else{
        	currentUserType = currentUserInfo.getUserType();
        	
        }
        
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
    		GDPeriodsInfo gdPeriodsInfo=periodsInfoLogic.queryZhangdanPeriods();
    		if(gdPeriodsInfo==null){
    			return "notKp";
    		}else{
    			periodsNum = gdPeriodsInfo.getPeriodsNum();
    		}
    	}
    	if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
    		CQPeriodsInfo cqPeriodsInfo=icqPeriodsInfoLogic.queryZhangdanPeriods();
    		if(cqPeriodsInfo==null){
    			return "notKp";
    		}else{
    			periodsNum = cqPeriodsInfo.getPeriodsNum();
    		}
    	}
		
        List<Zhangdan> resultList = zhangdanLogic.findUnSettledReport(lotteryType, playSubType, periodsNum, userID, userType,currentUserType);

        getRequest().setAttribute("resultList", resultList);
        getRequest().setAttribute("periodsNum", periodsNum);
        return "list";
    }

    //投注减去补货(在LOGIC里已经用补进减去了补出)
    public Map<String,ZhanDangVO> handelReplenish(Map<String,ZhanDangVO> petMap,List<ZhanDangVO> rList){
		for(ZhanDangVO r : rList){
			if(petMap.get(r.getTypeCode())!=null){
				ZhanDangVO v = petMap.get(r.getTypeCode());
				v.setTotalMoney(v.getTotalMoney().add(r.getTotalMoney()));//占成是相减的
				v.setCommissionMoney(v.getCommissionMoney().add(r.getCommissionMoney()));//退水的相减的
				totalMoney = totalMoney.add(r.getTotalMoney());
				totalCommissionMoney = totalCommissionMoney.add(r.getCommissionMoney());
			}
			/*else if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				petMap.put(r.getTypeCode(),r);
			}*/
		}
		return petMap;
    }
    
    
    //帳單
 public String queryZhangdanReplenish() throws Exception {
    	
    	String periodsNum = "";
    	

        ManagerUser currentUserInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        String currentUserType = null;

        ManagerUser userInfoSub = new ManagerUser(); //存子帐号信息
    	boolean isSubAccount = currentUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//子帐号
    	//子帐号处理
        if(isSubAccount){
			userInfoSub = subAccountInfoLogic.changeSubAccountInfo(currentUserInfo);
        }
        //子帐号处理
        if(isSubAccount){
			userID = userInfoSub.getID();
			userType = userInfoSub.getUserType();
		}else{
			userID = currentUserInfo.getID();
            userType = currentUserInfo.getUserType();
		}
        if(isSubAccount){
        	currentUserType = userInfoSub.getUserType();
        	
        }else{
        	currentUserType = currentUserInfo.getUserType();
        	
        }
        
      //获取请求的查询的类型，再传回Content页面
		String subTypeTmp = getRequest().getParameter("subType");
		if(subTypeTmp!=null){
			lotteryType = subTypeTmp;
			getRequest().setAttribute("subType",lotteryType);
		}
		getRequest().setAttribute("subType",lotteryType);
		
		
		
//**************************START****处理广东*****************************************
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
    		//************广东****START*****************
    		Map<String,ZhanDangVO> gdList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8DXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8DSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8WSDXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8HSDSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8FWList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gd1To8ZFBList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> gdZHDXList = new HashMap<String,ZhanDangVO>();//总和大小
    		Map<String,ZhanDangVO> gdZHDSList = new HashMap<String,ZhanDangVO>();//总和单双
    		Map<String,ZhanDangVO> gdZHWSDXList = new HashMap<String,ZhanDangVO>();//总和尾数大小
    		Map<String,ZhanDangVO> gdLHList = new HashMap<String,ZhanDangVO>();
    		//************广东****START*****************
    		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
            filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
//    		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
    		
            filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
            GDPeriodsInfo runningPeriods = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    			List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
    	        filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
    	        filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
    	        GDPeriodsInfo runningPeriods2 = new GDPeriodsInfo();
    	        runningPeriods2 = periodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    	        if(runningPeriods2==null){
    	        	return "notKp";
    	        }
    	        return "noZhanDang";
    		}
    		periodsNum = runningPeriods.getPeriodsNum();
    		
    		//处理本级补出的，在之前的数减去本级补出的START
    		List<ZhanDangVO> resultListR = new ArrayList<ZhanDangVO>();
    		resultListR = zhangdanLogic.queryZhangdanForReplenish(lotteryType,periodsNum, userID, userType,currentUserType);
    		//处理本级补出的，在之前的数减去本级补出的END
    		
    		//************************号球处理start*******************************
    		for(int i=1;i<=8;i++){
    			gdList = new HashMap<String,ZhanDangVO>();
    			String tableName = "";
    			String chsName = "";
    			if(i==1){chsName = "FIRST";tableName = "TB_GDKLSF_BALL_FIRST";
    			}else if(i==2){chsName = "SECOND";tableName = "TB_GDKLSF_BALL_SECOND";
    			}else if(i==3){chsName = "THIRD";tableName = "TB_GDKLSF_BALL_THIRD";
    			}else if(i==4){chsName = "FORTH";tableName = "TB_GDKLSF_BALL_FORTH";
    			}else if(i==5){chsName = "FIFTH";tableName = "TB_GDKLSF_BALL_FIFTH";
    			}else if(i==6){chsName = "SIXTH";tableName = "TB_GDKLSF_BALL_SIXTH";
    			}else if(i==7){chsName = "SEVENTH";tableName = "TB_GDKLSF_BALL_SEVENTH";
    			}else if(i==8){chsName = "EIGHTH";tableName = "TB_GDKLSF_BALL_EIGHTH";}
    		
    			List<ZhanDangVO> resultList = new ArrayList<ZhanDangVO>();
	    		resultList = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,tableName);
	    		//循环结果，分配到各list里
	    		for(ZhanDangVO v : resultList){
	    			if(v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_")!=-1){
	    				if(v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_DONG")!=-1 || v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_NAN")!=-1
	    					|| v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_XI")!=-1 || v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_BEI")!=-1
	    					|| v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_Z")!=-1 || v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_F")!=-1
	    					|| v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_B")!=-1 ){
	    				
	    					if(v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_DONG")!=-1 || v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_NAN")!=-1
	    	    					|| v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_XI")!=-1 || v.getTypeCode().indexOf("GDKLSF_BALL_" + chsName + "_BEI")!=-1){
	    						
	    						gd1To8FWList.put(v.getTypeCode(), v);
	    					}else{
	    						gd1To8ZFBList.put(v.getTypeCode(), v);
	    					}
	    				}else{
	    					gdList.put(v.getTypeCode(), v);
	    				}
	    			}else if(v.getTypeCode().indexOf("GDKLSF_DOUBLESIDE_" + i + "_")!=-1){
			    				if(("GDKLSF_DOUBLESIDE_"+i+"_DAN").equals(v.getTypeCode())||("GDKLSF_DOUBLESIDE_"+i+"_S").equals(v.getTypeCode())){
			    					gd1To8DSList.put(v.getTypeCode(), v);
			    				}else if(("GDKLSF_DOUBLESIDE_"+i+"_WD").equals(v.getTypeCode())||("GDKLSF_DOUBLESIDE_"+i+"_WX").equals(v.getTypeCode())){
			    					gd1To8WSDXList.put(v.getTypeCode(), v);
			    				}else if(("GDKLSF_DOUBLESIDE_"+i+"_HSD").equals(v.getTypeCode())||("GDKLSF_DOUBLESIDE_"+i+"_HSS").equals(v.getTypeCode())){
			    					gd1To8HSDSList.put(v.getTypeCode(), v);
			    				}else{//最后只剩大小了
			    					gd1To8DXList.put(v.getTypeCode(), v);
			    				}
	    			}
	    			totalTurnover += v.getTurnover();
	    			totalMoney = totalMoney.add(v.getTotalMoney());
	    			totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
	    		}
	    		//投注减去补货(在LOGIC里已经用补进减去了补出)后START
	    		if(resultListR!=null){
	    			this.handelReplenish(gdList, resultListR);
	    		}
	    		//处理补货END
	    		
	    		getRequest().setAttribute("gdList" + i, PlayTypeSort.mapSortByKeyPlayType(gdList));
    		}
    		//************************号球处理start*******************************

    		//************************DOUBLESIDE其他处理START****************************
    		List<ZhanDangVO> resultListDoubleLH = new ArrayList<ZhanDangVO>();
    		String tableName = "TB_GDKLSF_DOUBLE_SIDE";
    		resultListDoubleLH = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,tableName);
    		//循环结果，分配到各list里
    		for(ZhanDangVO v : resultListDoubleLH){
    			if("GDKLSF_DOUBLESIDE_LONG".equals(v.getTypeCode()) || "GDKLSF_DOUBLESIDE_HU".equals(v.getTypeCode())){
    				gdLHList.put(v.getTypeCode(), v);
    			}else if("GDKLSF_DOUBLESIDE_ZHDAN".equals(v.getTypeCode()) || "GDKLSF_DOUBLESIDE_ZHS".equals(v.getTypeCode())){
    				gdZHDSList.put(v.getTypeCode(), v);
				}else if("GDKLSF_DOUBLESIDE_ZHWD".equals(v.getTypeCode()) || "GDKLSF_DOUBLESIDE_ZHWX".equals(v.getTypeCode())){
					gdZHWSDXList.put(v.getTypeCode(), v);
				}else if("GDKLSF_DOUBLESIDE_ZHDA".equals(v.getTypeCode()) || "GDKLSF_DOUBLESIDE_ZHX".equals(v.getTypeCode())){
					gdZHDXList.put(v.getTypeCode(), v);
				}
    			totalTurnover += v.getTurnover();
    			totalMoney = totalMoney.add(v.getTotalMoney());
    			totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
    		}
    		if(resultListR!=null){
    			//this.handelReplenish(gdList, resultListR);
    			this.handelReplenish(gdLHList, resultListR);
    			this.handelReplenish(gdZHDSList, resultListR);
    			this.handelReplenish(gdZHDXList, resultListR);
    			this.handelReplenish(gdZHWSDXList, resultListR);
    		}
			getRequest().setAttribute("gdLHList", PlayTypeSort.mapSortByKeyPlayType(gdLHList));
			getRequest().setAttribute("gdZHDSList", PlayTypeSort.mapSortByKeyPlayType(gdZHDSList));
			getRequest().setAttribute("gdZHDXList", PlayTypeSort.mapSortByKeyPlayType((gdZHDXList)));
			getRequest().setAttribute("gdZHWSDXList", PlayTypeSort.mapSortByKeyPlayType(gdZHWSDXList));
    		//************************DOUBLESIDE其他处理END****************************
    		
    		//************************连码START****************************
    		Map<String,Object> mapLM = zhangdanLogic.queryZhangdanLM(lotteryType,periodsNum, userID, userType,currentUserType);
    		//************************连码END****************************
    		
    		if(resultListR!=null){
    			//this.handelReplenish(gdLMList, resultListR);
    			this.handelReplenish(gd1To8FWList, resultListR);
    			this.handelReplenish(gd1To8ZFBList, resultListR);
    			
    			this.handelReplenish(gd1To8DSList, resultListR);
    			this.handelReplenish(gd1To8WSDXList, resultListR);
    			this.handelReplenish(gd1To8HSDSList, resultListR);
    			this.handelReplenish(gd1To8DXList, resultListR);
    		}
    		String hasLM = "true";
    		if(mapLM.isEmpty()){
    			hasLM = "false";
    		}
    		
    		DecimalFormat df = new DecimalFormat("0.0");
    		String totalMoneyStr =  df.format(totalMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			String totalCommissionMoneyStr =  df.format(totalCommissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			
    		getRequest().setAttribute("lm", hasLM);
    		getRequest().setAttribute("gdLMListRX2", mapLM.get("RX2"));
    		getRequest().setAttribute("gdLMListR2LZ", mapLM.get("R2LZ"));
    		getRequest().setAttribute("gdLMListRX3", mapLM.get("RX3"));
    		getRequest().setAttribute("gdLMListR3LZ", mapLM.get("R3LZ"));
    		getRequest().setAttribute("gdLMListRX4", mapLM.get("RX4"));
    		getRequest().setAttribute("gdLMListRX5", mapLM.get("RX5"));
    		getRequest().setAttribute("totalTunoverLM", mapLM.get("totalTunover"));
    		getRequest().setAttribute("totalMoneyLM", mapLM.get("totalMoney"));
    		getRequest().setAttribute("totalCommissionMoneyLM", mapLM.get("totalCommissionMoney"));
    		
			getRequest().setAttribute("gd1To8FWList", PlayTypeSort.mapSortByKeyPlayType(gd1To8FWList));
			getRequest().setAttribute("gd1To8ZFBList", PlayTypeSort.mapSortByKeyPlayType(gd1To8ZFBList));
			getRequest().setAttribute("gd1To8DSList", PlayTypeSort.mapSortByKeyPlayType(gd1To8DSList));
			getRequest().setAttribute("gd1To8WSDXList", PlayTypeSort.mapSortByKeyPlayType(gd1To8WSDXList));
			getRequest().setAttribute("gd1To8HSDSList", PlayTypeSort.mapSortByKeyPlayType(gd1To8HSDSList));
			getRequest().setAttribute("gd1To8DXList", PlayTypeSort.mapSortByKeyPlayType(gd1To8DXList));
    		getRequest().setAttribute("periodsNum", periodsNum);
    		getRequest().setAttribute("totalTurnover", totalTurnover);
    		getRequest().setAttribute("totalMoney", totalMoneyStr);
    		getRequest().setAttribute("totalCommissionMoney", totalCommissionMoneyStr);
    		return "zhangdanGD";
    	}
//**************************END****处理广东*****************************************

//**************************START****处理农场*****************************************
    	if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
    		//************农场****START*****************
    		Map<String,ZhanDangVO> ncList1 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList2 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList3 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList4 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList5 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList6 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList7 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncList8 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8DXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8DSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8WSDXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8HSDSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8FWList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> nc1To8ZFBList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> ncZHDXList = new HashMap<String,ZhanDangVO>();//总和大小
    		Map<String,ZhanDangVO> ncZHDSList = new HashMap<String,ZhanDangVO>();//总和单双
    		Map<String,ZhanDangVO> ncZHWSDXList = new HashMap<String,ZhanDangVO>();//总和尾数大小
    		Map<String,ZhanDangVO> ncLHList = new HashMap<String,ZhanDangVO>();
    		//************农场****START*****************
    		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
            filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
//    		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
    		
            filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
            NCPeriodsInfo runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    			List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
    	        filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
    	        filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
    	        NCPeriodsInfo runningPeriods2 = new NCPeriodsInfo();
    	        runningPeriods2 = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    	        if(runningPeriods2==null){
    	        	return "notKp";
    	        }
    	        return "noZhanDang";
    		}
    		periodsNum = runningPeriods.getPeriodsNum();
    		
    		//处理本级补出的，在之前的数减去本级补出的START
    		List<ZhanDangVO> resultListR = new ArrayList<ZhanDangVO>();
    		resultListR = zhangdanLogic.queryZhangdanForReplenish(lotteryType,periodsNum, userID, userType,currentUserType);
    		//处理本级补出的，在之前的数减去本级补出的END
    		String tableName = "TB_NC";
    		List<ZhanDangVO> resultList = new ArrayList<ZhanDangVO>();
    		resultList = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,tableName);
    		
    		//************************号球处理start*******************************
    		
	    		//循环结果，分配到各list里
	    		for(ZhanDangVO v : resultList){
	    			for(int i=1;i<=8;i++){
	    				String chsName = "";
	    				if(i==1){chsName = "FIRST";
	    				}else if(i==2){chsName = "SECOND";
	    				}else if(i==3){chsName = "THIRD";
	    				}else if(i==4){chsName = "FORTH";
	    				}else if(i==5){chsName = "FIFTH";
	    				}else if(i==6){chsName = "SIXTH";
	    				}else if(i==7){chsName = "SEVENTH";
	    				}else if(i==8){chsName = "EIGHTH";}
		    			if(v.getTypeCode().indexOf("NC_BALL_" + chsName + "_")!=-1){
		    				if(v.getTypeCode().indexOf("NC_BALL_" + chsName + "_DONG")!=-1 || v.getTypeCode().indexOf("NC_BALL_" + chsName + "_NAN")!=-1
		    					|| v.getTypeCode().indexOf("NC_BALL_" + chsName + "_XI")!=-1 || v.getTypeCode().indexOf("NC_BALL_" + chsName + "_BEI")!=-1
		    					|| v.getTypeCode().indexOf("NC_BALL_" + chsName + "_Z")!=-1 || v.getTypeCode().indexOf("NC_BALL_" + chsName + "_F")!=-1
		    					|| v.getTypeCode().indexOf("NC_BALL_" + chsName + "_B")!=-1 ){
		    				
		    					if(v.getTypeCode().indexOf("NC_BALL_" + chsName + "_DONG")!=-1 || v.getTypeCode().indexOf("NC_BALL_" + chsName + "_NAN")!=-1
		    	    					|| v.getTypeCode().indexOf("NC_BALL_" + chsName + "_XI")!=-1 || v.getTypeCode().indexOf("NC_BALL_" + chsName + "_BEI")!=-1){
		    						
		    						nc1To8FWList.put(v.getTypeCode(), v);
		    						totalTurnover += v.getTurnover();
		    						totalMoney = totalMoney.add(v.getTotalMoney());
		    						totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
		    					}else{
		    						nc1To8ZFBList.put(v.getTypeCode(), v);
		    						totalTurnover += v.getTurnover();
		    						totalMoney = totalMoney.add(v.getTotalMoney());
		    						totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
		    					}
		    				}else{
		    					if(i==1){ncList1.put(v.getTypeCode(), v);
			    				}else if(i==2){ncList2.put(v.getTypeCode(), v);
			    				}else if(i==3){ncList3.put(v.getTypeCode(), v);
			    				}else if(i==4){ncList4.put(v.getTypeCode(), v);
			    				}else if(i==5){ncList5.put(v.getTypeCode(), v);
			    				}else if(i==6){ncList6.put(v.getTypeCode(), v);
			    				}else if(i==7){ncList7.put(v.getTypeCode(), v);
			    				}else if(i==8){ncList8.put(v.getTypeCode(), v);}
		    					totalTurnover += v.getTurnover();
		    					totalMoney = totalMoney.add(v.getTotalMoney());
		    					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
		    				}
		    				
		    			}else if(v.getTypeCode().indexOf("NC_DOUBLESIDE_" + i + "_")!=-1){
				    				if(("NC_DOUBLESIDE_"+i+"_DAN").equals(v.getTypeCode())||("NC_DOUBLESIDE_"+i+"_S").equals(v.getTypeCode())){
				    					nc1To8DSList.put(v.getTypeCode(), v);
				    					totalTurnover += v.getTurnover();
				    					totalMoney = totalMoney.add(v.getTotalMoney());
				    					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				    				}else if(("NC_DOUBLESIDE_"+i+"_WD").equals(v.getTypeCode())||("NC_DOUBLESIDE_"+i+"_WX").equals(v.getTypeCode())){
				    					nc1To8WSDXList.put(v.getTypeCode(), v);
				    					totalTurnover += v.getTurnover();
				    					totalMoney = totalMoney.add(v.getTotalMoney());
				    					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				    				}else if(("NC_DOUBLESIDE_"+i+"_HSD").equals(v.getTypeCode())||("NC_DOUBLESIDE_"+i+"_HSS").equals(v.getTypeCode())){
				    					nc1To8HSDSList.put(v.getTypeCode(), v);
				    					totalTurnover += v.getTurnover();
				    					totalMoney = totalMoney.add(v.getTotalMoney());
				    					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				    				}else{//最后只剩大小了
				    					nc1To8DXList.put(v.getTypeCode(), v);
				    					totalTurnover += v.getTurnover();
				    					totalMoney = totalMoney.add(v.getTotalMoney());
				    					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				    				}
		    			}
	    			}
	    		}
	    		//处理补货END
    			//投注减去补货(在LOGIC里已经用补进减去了补出)后START
				if(resultListR!=null){
					this.handelReplenish(ncList1, resultListR);
					this.handelReplenish(ncList2, resultListR);
					this.handelReplenish(ncList3, resultListR);
					this.handelReplenish(ncList4, resultListR);
					this.handelReplenish(ncList5, resultListR);
					this.handelReplenish(ncList6, resultListR);
					this.handelReplenish(ncList7, resultListR);
					this.handelReplenish(ncList8, resultListR);
				}
    			getRequest().setAttribute("ncList1", PlayTypeSort.mapSortByKeyPlayType(ncList1));
    			getRequest().setAttribute("ncList2", PlayTypeSort.mapSortByKeyPlayType(ncList2));
    			getRequest().setAttribute("ncList3", PlayTypeSort.mapSortByKeyPlayType(ncList3));
    			getRequest().setAttribute("ncList4", PlayTypeSort.mapSortByKeyPlayType(ncList4));
    			getRequest().setAttribute("ncList5", PlayTypeSort.mapSortByKeyPlayType(ncList5));
    			getRequest().setAttribute("ncList6", PlayTypeSort.mapSortByKeyPlayType(ncList6));
    			getRequest().setAttribute("ncList7", PlayTypeSort.mapSortByKeyPlayType(ncList7));
    			getRequest().setAttribute("ncList8", PlayTypeSort.mapSortByKeyPlayType(ncList8));
    		//}
	    	
    		//************************号球处理start*******************************

    		//************************DOUBLESIDE其他处理START****************************
    		List<ZhanDangVO> resultListDoubleLH = new ArrayList<ZhanDangVO>();
    		//String tableName = "TB_NC";
    		resultListDoubleLH = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,tableName);
    		//循环结果，分配到各list里
    		for(ZhanDangVO v : resultListDoubleLH){
    			if("NC_DOUBLESIDE_LONG".equals(v.getTypeCode()) || "NC_DOUBLESIDE_HU".equals(v.getTypeCode())){
    				ncLHList.put(v.getTypeCode(), v);
    				totalTurnover += v.getTurnover();
    				totalMoney = totalMoney.add(v.getTotalMoney());
    				totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
    			}else if("NC_DOUBLESIDE_ZHDAN".equals(v.getTypeCode()) || "NC_DOUBLESIDE_ZHS".equals(v.getTypeCode())){
    				ncZHDSList.put(v.getTypeCode(), v);
    				totalTurnover += v.getTurnover();
    				totalMoney = totalMoney.add(v.getTotalMoney());
    				totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				}else if("NC_DOUBLESIDE_ZHWD".equals(v.getTypeCode()) || "NC_DOUBLESIDE_ZHWX".equals(v.getTypeCode())){
					ncZHWSDXList.put(v.getTypeCode(), v);
					totalTurnover += v.getTurnover();
					totalMoney = totalMoney.add(v.getTotalMoney());
					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				}else if("NC_DOUBLESIDE_ZHDA".equals(v.getTypeCode()) || "NC_DOUBLESIDE_ZHX".equals(v.getTypeCode())){
					ncZHDXList.put(v.getTypeCode(), v);
					totalTurnover += v.getTurnover();
					totalMoney = totalMoney.add(v.getTotalMoney());
					totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
				}
    		}
    		if(resultListR!=null){
    			//this.handelReplenish(ncList, resultListR);
    			this.handelReplenish(ncLHList, resultListR);
    			this.handelReplenish(ncZHDSList, resultListR);
    			this.handelReplenish(ncZHDXList, resultListR);
    			this.handelReplenish(ncZHWSDXList, resultListR);
    		}
			getRequest().setAttribute("ncLHList", PlayTypeSort.mapSortByKeyPlayType(ncLHList));
			getRequest().setAttribute("ncZHDSList", PlayTypeSort.mapSortByKeyPlayType(ncZHDSList));
			getRequest().setAttribute("ncZHDXList", PlayTypeSort.mapSortByKeyPlayType((ncZHDXList)));
			getRequest().setAttribute("ncZHWSDXList", PlayTypeSort.mapSortByKeyPlayType(ncZHWSDXList));
    		//************************DOUBLESIDE其他处理END****************************
    		
    		//************************连码START****************************
    		Map<String,Object> mapLM = zhangdanLogic.queryZhangdanLM_NC(lotteryType,periodsNum, userID, userType,currentUserType);
    		//************************连码END****************************
    		
    		if(resultListR!=null){
    			//this.handelReplenish(ncLMList, resultListR);
    			this.handelReplenish(nc1To8FWList, resultListR);
    			this.handelReplenish(nc1To8ZFBList, resultListR);
    			
    			this.handelReplenish(nc1To8DSList, resultListR);
    			this.handelReplenish(nc1To8WSDXList, resultListR);
    			this.handelReplenish(nc1To8HSDSList, resultListR);
    			this.handelReplenish(nc1To8DXList, resultListR);
    		}
    		String hasLM = "true";
    		if(mapLM.isEmpty()){
    			hasLM = "false";
    		}
    		
    		DecimalFormat df = new DecimalFormat("0.0");
    		String totalMoneyStr =  df.format(totalMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			String totalCommissionMoneyStr =  df.format(totalCommissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			
    		getRequest().setAttribute("lm", hasLM);
    		getRequest().setAttribute("ncLMListRX2", mapLM.get("RX2"));
    		getRequest().setAttribute("ncLMListR2LZ", mapLM.get("R2LZ"));
    		getRequest().setAttribute("ncLMListRX3", mapLM.get("RX3"));
    		getRequest().setAttribute("ncLMListR3LZ", mapLM.get("R3LZ"));
    		getRequest().setAttribute("ncLMListRX4", mapLM.get("RX4"));
    		getRequest().setAttribute("ncLMListRX5", mapLM.get("RX5"));
    		getRequest().setAttribute("totalTunoverLM", mapLM.get("totalTunover"));
    		getRequest().setAttribute("totalMoneyLM", mapLM.get("totalMoney"));
    		getRequest().setAttribute("totalCommissionMoneyLM", mapLM.get("totalCommissionMoney"));
    		
			getRequest().setAttribute("nc1To8FWList", PlayTypeSort.mapSortByKeyPlayType(nc1To8FWList));
			getRequest().setAttribute("nc1To8ZFBList", PlayTypeSort.mapSortByKeyPlayType(nc1To8ZFBList));
			getRequest().setAttribute("nc1To8DSList", PlayTypeSort.mapSortByKeyPlayType(nc1To8DSList));
			getRequest().setAttribute("nc1To8WSDXList", PlayTypeSort.mapSortByKeyPlayType(nc1To8WSDXList));
			getRequest().setAttribute("nc1To8HSDSList", PlayTypeSort.mapSortByKeyPlayType(nc1To8HSDSList));
			getRequest().setAttribute("nc1To8DXList", PlayTypeSort.mapSortByKeyPlayType(nc1To8DXList));
    		getRequest().setAttribute("periodsNum", periodsNum);
    		getRequest().setAttribute("totalTurnover", totalTurnover);
    		getRequest().setAttribute("totalMoney", totalMoneyStr);
    		getRequest().setAttribute("totalCommissionMoney", totalCommissionMoneyStr);
    		return "zhangdanNC";
    	}
//**************************END****处理农场*****************************************    	
    	
//**************************START****处理重庆*****************************************
    	if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
    		Map<String,ZhanDangVO> cqList1 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqList2 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqList3 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqList4 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqList5 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cq1To5DXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cq1To5DSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqZHDXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqZHDSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqLHList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqFrontList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqMidList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> cqLastList = new HashMap<String,ZhanDangVO>();
    		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    		
            filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
//    		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
    		
            filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
            CQPeriodsInfo runningPeriods = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    			List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
    	        filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
    	        filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
    	        CQPeriodsInfo runningPeriods2 = new CQPeriodsInfo();
    	        runningPeriods2 = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    	        if(runningPeriods2==null){
    	        	return "notKp";
    	        }
    	        return "noZhanDang";
    		}
    		periodsNum = runningPeriods.getPeriodsNum();
    		
    		//处理本级补出的，在之前的数减去本级补出的START
    		List<ZhanDangVO> resultListRCQBall = new ArrayList<ZhanDangVO>();
    		resultListRCQBall = zhangdanLogic.queryZhangdanForReplenish(lotteryType,periodsNum, userID, userType,currentUserType);
    		//处理本级补出的，在之前的数减去本级补出的END
    		
    		
    		List<ZhanDangVO> resultListCQBall = new ArrayList<ZhanDangVO>();
    		resultListCQBall = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,"");
    		//循环结果，分配到各list里
    		for(ZhanDangVO v : resultListCQBall){
    			if(v.getTypeCode().indexOf("CQSSC_BALL_FIRST")!=-1){
    				cqList1.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("CQSSC_BALL_SECOND")!=-1){
    				cqList2.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("CQSSC_BALL_THIRD")!=-1){
					cqList3.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("CQSSC_BALL_FORTH")!=-1){
					cqList4.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("CQSSC_BALL_FIFTH")!=-1){
					cqList5.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("1")!=-1 || v.getTypeCode().indexOf("2")!=-1 ||v.getTypeCode().indexOf("3")!=-1
						||v.getTypeCode().indexOf("4")!=-1 ||v.getTypeCode().indexOf("5")!=-1){
					    
					    if(v.getTypeCode().indexOf("DAN")!=-1){
					    	cq1To5DSList.put(v.getTypeCode(), v);
					    }else if(v.getTypeCode().indexOf("DA")!=-1){ //大小
					    	cq1To5DXList.put(v.getTypeCode(), v);
					    }else if(v.getTypeCode().indexOf("X")!=-1){ //大小
					    	cq1To5DXList.put(v.getTypeCode(), v);
					    }else{
					    	cq1To5DSList.put(v.getTypeCode(), v);
					    }
				}else if(v.getTypeCode().indexOf("CQSSC_DOUBLESIDE_ZH")!=-1){
						if("CQSSC_DOUBLESIDE_ZHDAN".equals(v.getTypeCode()) || "CQSSC_DOUBLESIDE_ZHS".equals(v.getTypeCode())){
							cqZHDSList.put(v.getTypeCode(), v);
						}else{//只有大小了
							cqZHDXList.put(v.getTypeCode(), v);
						}
				}else if(v.getTypeCode().indexOf("CQSSC_DOUBLESIDE_LONG")!=-1 || v.getTypeCode().indexOf("CQSSC_DOUBLESIDE_HU")!=-1 || v.getTypeCode().indexOf("CQSSC_DOUBLESIDE_HE")!=-1){
					cqLHList.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("FRONT")!=-1){
					cqFrontList.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("MID")!=-1){
					cqMidList.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("LAST")!=-1){
					cqLastList.put(v.getTypeCode(), v);
				}
    			totalTurnover += v.getTurnover();
    			totalMoney = totalMoney.add(v.getTotalMoney());
    			totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
    		}
    		if(resultListRCQBall!=null){
    			this.handelReplenish(cqList1, resultListRCQBall);
    			this.handelReplenish(cqList2, resultListRCQBall);
    			this.handelReplenish(cqList3, resultListRCQBall);
    			this.handelReplenish(cqList4, resultListRCQBall);
    			this.handelReplenish(cqList5, resultListRCQBall);
    			this.handelReplenish(cq1To5DSList, resultListRCQBall);
    			this.handelReplenish(cq1To5DXList, resultListRCQBall);
    			this.handelReplenish(cqZHDSList, resultListRCQBall);
    			this.handelReplenish(cqZHDXList, resultListRCQBall);
    			this.handelReplenish(cqLHList, resultListRCQBall);
    			this.handelReplenish(cqFrontList, resultListRCQBall);
    			this.handelReplenish(cqMidList, resultListRCQBall);
    			this.handelReplenish(cqLastList, resultListRCQBall);
    		}
    		getRequest().setAttribute("cqList1", PlayTypeSort.mapSortByKeyPlayType(cqList1));
    		getRequest().setAttribute("cqList2", PlayTypeSort.mapSortByKeyPlayType(cqList2));
    		getRequest().setAttribute("cqList3", PlayTypeSort.mapSortByKeyPlayType(cqList3));
    		getRequest().setAttribute("cqList4", PlayTypeSort.mapSortByKeyPlayType(cqList4));
    		getRequest().setAttribute("cqList5", PlayTypeSort.mapSortByKeyPlayType(cqList5));
    		getRequest().setAttribute("cq1To5DSList", PlayTypeSort.mapSortByKeyPlayType(cq1To5DSList));
    		getRequest().setAttribute("cq1To5DXList", PlayTypeSort.mapSortByKeyPlayType(cq1To5DXList));
    		getRequest().setAttribute("cqZHDSList", PlayTypeSort.mapSortByKeyPlayType(cqZHDSList));
    		getRequest().setAttribute("cqZHDXList", PlayTypeSort.mapSortByKeyPlayType(cqZHDXList));
    		getRequest().setAttribute("cqLHList", PlayTypeSort.mapSortByKeyPlayType(cqLHList));
    		getRequest().setAttribute("cqFrontList", PlayTypeSort.mapSortByKeyPlayType(cqFrontList));
    		getRequest().setAttribute("cqMidList", PlayTypeSort.mapSortByKeyPlayType(cqMidList));
    		getRequest().setAttribute("cqLastList", PlayTypeSort.mapSortByKeyPlayType(cqLastList));
    		getRequest().setAttribute("periodsNum", periodsNum);
    		getRequest().setAttribute("totalTurnover", totalTurnover);
    		getRequest().setAttribute("totalMoney", totalMoney);
    		getRequest().setAttribute("totalCommissionMoney", totalCommissionMoney);
    		return "zhangdanCQ";
    	}
//**************************END****处理重庆*****************************************
    	
    	
//**************************START****处理北京*****************************************
    	if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
    		
    		Map<String,ZhanDangVO> bjList1 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList2 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList3 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList4 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList5 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList6 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList7 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList8 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList9 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjList10 = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bj1To10DXList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bj1To10DSList = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> bjHDXList = new HashMap<String,ZhanDangVO>();  //冠亞軍和大小
    		Map<String,ZhanDangVO> bjHDSList = new HashMap<String,ZhanDangVO>();  //冠亞軍和單雙
    		Map<String,ZhanDangVO> gyGroupList = new HashMap<String,ZhanDangVO>();//冠亞軍和
    		Map<String,ZhanDangVO> bjLHList = new HashMap<String,ZhanDangVO>();//龍虎
    		
    		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
            filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
//    		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
    		
            filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
            BJSCPeriodsInfo runningPeriods = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    			List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
    	        filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
    	        filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
    	        BJSCPeriodsInfo runningPeriods2 = new BJSCPeriodsInfo();
    	        runningPeriods2 = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    	        if(runningPeriods2==null){
    	        	return "notKp";
    	        }
    	        return "noZhanDang";
    		}
    		periodsNum = runningPeriods.getPeriodsNum();
    		
    		
    		//处理本级补出的，在之前的数减去本级补出的START
    		List<ZhanDangVO> resultListRBJ = new ArrayList<ZhanDangVO>();
    		resultListRBJ = zhangdanLogic.queryZhangdanForReplenish(lotteryType,periodsNum, userID, userType,currentUserType);
    		//处理本级补出的，在之前的数减去本级补出的END
    		
    		List<ZhanDangVO> resultListBJ = new ArrayList<ZhanDangVO>();
    		resultListBJ = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,"");
    		//循环结果，分配到各list里
    		for(ZhanDangVO v : resultListBJ){
    			if(v.getTypeCode().indexOf("BJ_BALL_FIRST")!=-1){
    				bjList1.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("BJ_BALL_SECOND")!=-1){bjList2.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_THIRD")!=-1){bjList3.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_FORTH")!=-1){bjList4.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_FIFTH")!=-1){bjList5.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_SIXTH")!=-1){bjList6.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_SEVENTH")!=-1){bjList7.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_EIGHTH")!=-1){bjList8.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_NINTH")!=-1){bjList9.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("BJ_BALL_TENTH")!=-1){bjList10.put(v.getTypeCode(), v);
				}else if("BJ_DOUBLESIDE_DAN".equals(v.getTypeCode()) || "BJ_DOUBLESIDE_S".equals(v.getTypeCode()) 
						||"BJ_DOUBLESIDE_DA".equals(v.getTypeCode()) || "BJ_DOUBLESIDE_X".equals(v.getTypeCode())){
					    
					    if(("BJ_DOUBLESIDE_DAN").equals(v.getTypeCode()) || ("BJ_DOUBLESIDE_S").equals(v.getTypeCode())){
					    	bjHDSList.put(v.getTypeCode(), v);
					    }else if(("BJ_DOUBLESIDE_DA").equals(v.getTypeCode()) || ("BJ_DOUBLESIDE_X").equals(v.getTypeCode())){//只有大小了
					    	bjHDXList.put(v.getTypeCode(), v);
					    }
				}else if(v.getTypeCode().indexOf("BJ_GROUP")!=-1){
							gyGroupList.put(v.getTypeCode(), v);
				}else if(v.getTypeCode().indexOf("LONG")!=-1 || v.getTypeCode().indexOf("HU")!=-1){
					     bjLHList.put(v.getTypeCode(), v);
				}else{//剩下就是1-10的大小单双了。
					 if("BJ_1-10_DS".equals(PlayTypeUtils.getPlayType(v.getTypeCode()).getOddsType())){
						 bj1To10DSList.put(v.getTypeCode(), v);
					    }else if("BJ_1-10_DX".equals(PlayTypeUtils.getPlayType(v.getTypeCode()).getOddsType())){//只有大小了
					    	bj1To10DXList.put(v.getTypeCode(), v);
					    }
				}
    			totalTurnover += v.getTurnover();
    			totalMoney = totalMoney.add(v.getTotalMoney());
    			totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
    		}
    		
    		if(resultListRBJ!=null){
    			this.handelReplenish(bjList1, resultListRBJ);
    			this.handelReplenish(bjList2, resultListRBJ);
    			this.handelReplenish(bjList3, resultListRBJ);
    			this.handelReplenish(bjList4, resultListRBJ);
    			this.handelReplenish(bjList5, resultListRBJ);
    			this.handelReplenish(bjList6, resultListRBJ);
    			this.handelReplenish(bjList7, resultListRBJ);
    			this.handelReplenish(bjList8, resultListRBJ);
    			this.handelReplenish(bjList9, resultListRBJ);
    			this.handelReplenish(bjList10, resultListRBJ);
    			this.handelReplenish(bjHDSList, resultListRBJ);
    			this.handelReplenish(bjHDXList, resultListRBJ);
    			this.handelReplenish(gyGroupList, resultListRBJ);
    			this.handelReplenish(bj1To10DSList, resultListRBJ);
    			this.handelReplenish(bj1To10DXList, resultListRBJ);
    			this.handelReplenish(bjLHList, resultListRBJ);
    		}
			getRequest().setAttribute("bjList1", PlayTypeSort.mapSortByKeyPlayType(bjList1));
			getRequest().setAttribute("bjList2", PlayTypeSort.mapSortByKeyPlayType(bjList2));
			getRequest().setAttribute("bjList3", PlayTypeSort.mapSortByKeyPlayType(bjList3));
			getRequest().setAttribute("bjList4", PlayTypeSort.mapSortByKeyPlayType(bjList4));
			getRequest().setAttribute("bjList5", PlayTypeSort.mapSortByKeyPlayType(bjList5));
			getRequest().setAttribute("bjList6", PlayTypeSort.mapSortByKeyPlayType(bjList6));
			getRequest().setAttribute("bjList7", PlayTypeSort.mapSortByKeyPlayType(bjList7));
			getRequest().setAttribute("bjList8", PlayTypeSort.mapSortByKeyPlayType(bjList8));
			getRequest().setAttribute("bjList9", PlayTypeSort.mapSortByKeyPlayType(bjList9));
			getRequest().setAttribute("bjList10", PlayTypeSort.mapSortByKeyPlayType(bjList10));
			getRequest().setAttribute("bjHDSList", PlayTypeSort.mapSortByKeyPlayType(bjHDSList));
			getRequest().setAttribute("bjHDXList", PlayTypeSort.mapSortByKeyPlayType(bjHDXList));
			getRequest().setAttribute("gyGroupList", PlayTypeSort.mapSortByKeyPlayType(gyGroupList));
			getRequest().setAttribute("bj1To10DSList", PlayTypeSort.mapSortByKeyPlayType(bj1To10DSList));
			getRequest().setAttribute("bj1To10DXList", PlayTypeSort.mapSortByKeyPlayType(bj1To10DXList));
			getRequest().setAttribute("bjLHList", PlayTypeSort.mapSortByKeyPlayType(bjLHList));
    		getRequest().setAttribute("periodsNum", periodsNum);
    		getRequest().setAttribute("totalTurnover", totalTurnover);
    		getRequest().setAttribute("totalMoney", totalMoney);
    		getRequest().setAttribute("totalCommissionMoney", totalCommissionMoney);
    		return "zhangdanBJ";
    	}
//**************************END****帐单处理北京*****************************************
    	
//**************************START****处理K3*****************************************
    	if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
    		
    		Map<String,ZhanDangVO> mapSj = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapDx = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapQs = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapWs = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapDs = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapCp = new HashMap<String,ZhanDangVO>();
    		Map<String,ZhanDangVO> mapDp = new HashMap<String,ZhanDangVO>();
    		
    		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    		filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
//    		filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
    		
    		filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
    		JSSBPeriodsInfo runningPeriods = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    			List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
    			filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
    			filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
    			JSSBPeriodsInfo runningPeriods2 = new JSSBPeriodsInfo();
    			runningPeriods2 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    			if(runningPeriods2==null){
    				return "notKp";
    			}
    			return "noZhanDang";
    		}
    		periodsNum = runningPeriods.getPeriodsNum();
    		
    		
    		//处理本级补出的，在之前的数减去本级补出的START
    		List<ZhanDangVO> resultListRK3 = new ArrayList<ZhanDangVO>();
    		resultListRK3 = zhangdanLogic.queryZhangdanForReplenish(lotteryType,periodsNum, userID, userType,currentUserType);
    		//处理本级补出的，在之前的数减去本级补出的END
    		
    		List<ZhanDangVO> resultListK3 = new ArrayList<ZhanDangVO>();
    		resultListK3 = zhangdanLogic.queryZhangdan(lotteryType,periodsNum, userID, userType,currentUserType,"");
    		//循环结果，分配到各list里
    		for(ZhanDangVO v : resultListK3){
    			if(v.getTypeCode().indexOf("K3_SJ")!=-1){
    				mapSj.put(v.getTypeCode(), v);
    			}else if("K3_DA".equals(v.getTypeCode()) || "K3_X".equals(v.getTypeCode()) ){mapDx.put(v.getTypeCode(), v);
    			}else if("K3_QS".equals(v.getTypeCode())){mapQs.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("K3_WS")!=-1){mapWs.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("K3_DS")!=-1){mapDs.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("K3_CP")!=-1){mapCp.put(v.getTypeCode(), v);
    			}else if(v.getTypeCode().indexOf("K3_DP")!=-1){mapDp.put(v.getTypeCode(), v);}
    			totalTurnover += v.getTurnover();
    			totalMoney = totalMoney.add(v.getTotalMoney());
    			totalCommissionMoney = totalCommissionMoney.add(v.getCommissionMoney());
    		}
    		
    		if(resultListRK3!=null){
    			this.handelReplenish(mapSj, resultListRK3);
    			this.handelReplenish(mapDx, resultListRK3);
    			this.handelReplenish(mapQs, resultListRK3);
    			this.handelReplenish(mapWs, resultListRK3);
    			this.handelReplenish(mapDs, resultListRK3);
    			this.handelReplenish(mapCp, resultListRK3);
    			this.handelReplenish(mapDp, resultListRK3);
    		}
    		getRequest().setAttribute("mapSj", PlayTypeSort.mapSortByKeyPlayType(mapSj));
    		getRequest().setAttribute("mapDx", PlayTypeSort.mapSortByKeyPlayType(mapDx));
    		getRequest().setAttribute("mapQs", PlayTypeSort.mapSortByKeyPlayType(mapQs));
    		getRequest().setAttribute("mapWs", PlayTypeSort.mapSortByKeyPlayType(mapWs));
    		getRequest().setAttribute("mapDs", PlayTypeSort.mapSortByKeyPlayType(mapDs));
    		getRequest().setAttribute("mapCp", PlayTypeSort.mapSortByKeyPlayType(mapCp));
    		getRequest().setAttribute("mapDp", PlayTypeSort.mapSortByKeyPlayType(mapDp));
    		getRequest().setAttribute("periodsNum", periodsNum);
    		getRequest().setAttribute("totalTurnover", totalTurnover);
    		getRequest().setAttribute("totalMoney", totalMoney);
    		getRequest().setAttribute("totalCommissionMoney", totalCommissionMoney);
    		return "zhangdanK3";
    	}
//**************************END****帐单处理K3*****************************************
        return "noZhanDang";
    }
 
 

 
 
    /**
     * 补货界面的右边栏，总额和遗漏，是根据条件返回单独的总额还是遗漏
     * @return
     */
    public String replenishRightBar(){
    	String subType = getRequest().getParameter("subType");
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
		}
        //periodsNum = "1111";
    	List<ZhanDangVO> result = new ArrayList<ZhanDangVO>();
    	//子帐号处理*********START
    	ManagerUser userInfo = this.getCurrentManagerUser();
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("replenishRightBar returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
    	}
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
			try {
				temp = Constant.GDKLSF_RIGHT_TYPE;
				mapType=new HashMap(temp);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
		}else{
			try {
				temp = Constant.BJSC_RIGHT_TYPE;
				mapType=new HashMap(temp);
			} catch (Exception e) {
				log.info("出错"+ e.getMessage());
			}
		}
		BigDecimal total = BigDecimal.ZERO;//总额
		for(ZhanDangVO v:result){
			if(mapType.get(v.getCommissionType())!=null){
				map.put(v.getCommissionType(), v);
				mapType.remove(v.getCommissionType());
			}
			total = total.add(v.getTotalMoney());
		}
		for(Map.Entry<String, String> entry : mapType.entrySet()){
			map.put(entry.getKey(), new ZhanDangVO());
		}
		//如果是统计广东的，还要加上遗漏
		Map<String,Integer> notAppearRate= new HashMap<String,Integer>();
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(subType)){
			List<GDPeriodsInfo> periodInfoList=periodsInfoLogic.queryTodayPeriods();
			List<List<String>> listBallAll=new ArrayList<List<String>>();
			for (int i = 0;  i< periodInfoList.size(); i++) {
				GDPeriodsInfo periondInfo=periodInfoList.get(i);
				String b1=periondInfo.getResult1()+"";
				String b2=periondInfo.getResult2()+"";
				String b3=periondInfo.getResult3()+"";
				String b4=periondInfo.getResult4()+"";
				String b5=periondInfo.getResult5()+"";
				String b6=periondInfo.getResult6()+"";
				String b7=periondInfo.getResult7()+"";
				String b8=periondInfo.getResult8()+"";
							
				List<String> listBallForOnePer=new ArrayList<String>();
				listBallForOnePer.add(b1);
				listBallForOnePer.add(b2);
				listBallForOnePer.add(b3);
				listBallForOnePer.add(b4);
				listBallForOnePer.add(b5);
				listBallForOnePer.add(b6);
				listBallForOnePer.add(b7);
				listBallForOnePer.add(b8);
				listBallAll.add(listBallForOnePer);
			}
			map = notAppearCnt(listBallAll,map);
			
		}
		getRequest().setAttribute("result", PlayTypeSort.mapSortByKeyPlayType(map));
		getRequest().setAttribute("totalMoney", total.setScale(0,BigDecimal.ROUND_HALF_UP));
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(subType)){
			return "gd";
		}else{
			return "bj";
			
		}
    }
    
    //广东遗漏
    private  Map<String,ZhanDangVO> notAppearCnt(List<List<String>> listNum,Map<String,ZhanDangVO> map)
	{
		for (int i = 1; i <= 20; i++) {
			String key=Integer.valueOf(i).toString();
			Integer cnt=new Integer(0);
			ZhanDangVO vo = new ZhanDangVO();
			for (int j = 0; j < listNum.size(); j++) {
				List<String> onePeriondNum=listNum.get(j);
				if(onePeriondNum.indexOf(key)!=-1)
				{	
					vo.setYLsum(cnt);
					map.put(key, vo);
					break;
				}
				else
				{
					cnt++;
				}
				
			}
			vo.setYLsum(cnt);
			map.put(key, vo);
			
		}
		return map;
	}
    
    public String backupDetailCheck(){
    	String subTypeTmp = getRequest().getParameter("subType");
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
//    	filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));//---------临时调试打开，正试时要注释
        filtersPeriodInfo.add(Restrictions.le("stopQuotTime",new Date()));//---------临时调试注释
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        
        List<Criterion> filtersPeriodInfo2 = new ArrayList<Criterion>();
        filtersPeriodInfo2.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo2.add(Restrictions.ge("lotteryTime",new Date()));
        
        if(Constant.LOTTERY_TYPE_GDKLSF.equals(subTypeTmp)){
	        GDPeriodsInfo runningPeriods = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			if(runningPeriods==null){
		        GDPeriodsInfo runningPeriods2 = new GDPeriodsInfo();
		        runningPeriods2 = periodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
		        if(runningPeriods2==null){
		        	return "notKp";
		        }
		        return "noZhanDang";
			}else{
				return "GDKLSF";
			}
        }else if(Constant.LOTTERY_TYPE_CQSSC.equals(subTypeTmp)){
        	 CQPeriodsInfo runningPeriods = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
     		if(runningPeriods==null){
     	        CQPeriodsInfo runningPeriods2 = new CQPeriodsInfo();
     	        runningPeriods2 = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
     	        if(runningPeriods2==null){
     	        	return "notKp";
     	        }
     	        return "noZhanDang";
     		}else{
				return "CQSSC";
			}
        }else if(Constant.LOTTERY_TYPE_BJ.equals(subTypeTmp)){
        	BJSCPeriodsInfo runningPeriods = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    		if(runningPeriods==null){
    	        BJSCPeriodsInfo runningPeriods2 = new BJSCPeriodsInfo();
    	        runningPeriods2 = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
    	        if(runningPeriods2==null){
    	        	return "notKp";
    	        }
    	        return "noZhanDang";
    		}else{
				return "BJ";
			}
	    }else if(Constant.LOTTERY_TYPE_K3.equals(subTypeTmp)){
	    	JSSBPeriodsInfo runningPeriods = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
	    	if(runningPeriods==null){
	    		JSSBPeriodsInfo runningPeriods2 = new JSSBPeriodsInfo();
	    		runningPeriods2 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
	    		if(runningPeriods2==null){
	    			return "notKp";
	    		}
	    		return "noZhanDang";
	    	}else{
	    		return "K3";
	    	}
	    }else if(Constant.LOTTERY_TYPE_NC.equals(subTypeTmp)){
	    	NCPeriodsInfo runningPeriods = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
	    	if(runningPeriods==null){
	    		NCPeriodsInfo runningPeriods2 = new NCPeriodsInfo();
	    		runningPeriods2 = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo2.toArray(new Criterion[filtersPeriodInfo2.size()]));
	    		if(runningPeriods2==null){
	    			return "notKp";
	    		}
	    		return "noZhanDang";
	    	}else{
	    		return "NC";
	    	}
	    }
		return INPUT;
    	
    }
 
 
    private String lotteryType=Constant.LOTTERY_TYPE_GDKLSF;//彩票种类

    private String playSubType;//下注类型

    private Long userID;
    
    private String userType;
    
    private IZhangdanLogic zhangdanLogic = null;//报表统计
    
    private IReplenishLogic replenishLogic = null;
    private ISubAccountInfoLogic subAccountInfoLogic;//子账号
    private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic	= null;
    private INCPeriodsInfoLogic ncPeriodsInfoLogic;

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

    public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	}

	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
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

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

    public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public Long getUserID() {
		return userID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPlaySubType() {
		return playSubType;
	}

	public void setPlaySubType(String playSubType) {
		this.playSubType = playSubType;
	}

	public IZhangdanLogic getZhangdanLogic() {
		return zhangdanLogic;
	}

	public void setZhangdanLogic(IZhangdanLogic zhangdanLogic) {
		this.zhangdanLogic = zhangdanLogic;
	}

}
