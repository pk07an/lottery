package com.npc.lottery.statreport.action;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.LotteryComparator;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;


/**
 * 报表统计Action
 * 
 */
public class ClassReportEricAction extends BaseLotteryAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6763143609272139207L;

	private static Logger log = Logger.getLogger(ClassReportEricAction.class);

    private IClassReportEricLogic classReportEricLogic = null;//报表统计
    
    private IReplenishLogic replenishLogic = null;
    private ICommonUserLogic commonUserLogic;
    private IReportStatusLogic reportStatusLogic;

    private String type;
    private String isUp = "false"; //是否是上级往下级查询,默认为否
    private String sitemeshType = "report"; //用于页面菜单显示

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~进入交收报表未结算报表-----------START
    public String classList() throws Exception {
    	String result = "failure";
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    	ReportStatus reportStatus = reportStatusLogic.findReportStatus(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
    	//如果opt为(Y)开启和状态为(Y)成功时，使用新方法（扫VIEW），
    	//如果opt为(N)关闭或状态为(N)不成功时，使用旧方法（扫历史表），
		if("Y".equals(reportStatus.getOpt()) && "Y".equals(reportStatus.getStatus())){
			//如果是凌晨的2:50分至4点都会用老方法计算
			DateFormat dateFormat = new SimpleDateFormat("Hmm");
			java.util.Date nowDay = new java.util.Date();
			String sNowDay = dateFormat.format(nowDay);
			Integer intNowDay = Integer.valueOf(sNowDay);
			if(intNowDay>=250 && intNowDay<=400){
				result = this.classListScanHistory();
			}else{
				result = this.classListScanView();
			}
		}else{
			result = this.classListScanHistory();
		}
			
		return result;
    	
    	
    }
    
    /**
     * 报表列表--老方法--直接扫描历史表
     * 
     * @return
     * @throws Exception
     */
    public String classListScanHistory() throws Exception {

        ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
    	//子帐号处理*********END
    	   
        /*
         * userID为空即用户直接查询，如果当前查询者是子帐号查询的，还要查询子帐号的所属者，把该所属者的userID和userType赋于查询变量
         * userID不为空即从上级往下查下级的，如果当前查询者是子帐号，同上。
         * 
         */
    	if(userID==null){
        	userID = userInfoNew.getID();
  			userType = userInfoNew.getUserType();
  			detailUserAccount = userInfoNew.getAccount();
        	//如果公司的开放报表功能打开，分公司查报表时就等同于总监查询，与总监登录查询一模一样。
        	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
        	    UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
      			if(Constant.OPEN.equals(userVO.getReport())){
      				userID = userVO.getChiefStaffExt().getManagerStaffID();
      				userType = userVO.getChiefStaffExt().getUserType();
      				detailUserAccount = userVO.getChiefStaffExt().getAccount();
      			}
      		}
        }
        
        this.parentUserType = userType;//设置 parentUserType 的属性值，直属会员明细中需要使用
        
        String vPlayType = null;
        //如果没有选择小类就直接查全部，
        if("ALL".indexOf(playType)!=-1){
        	vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
        //把没有选择的一项的参数赋于null
        String periodsNumFrm = ""; 
        String lotteryTypeFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = "";
        	periodsNum = "";
        }
        String[] scanTableList = new String[]{};
        if(lotteryType.indexOf("GD")!=-1 || periodsNum.indexOf("GD")!=-1){
			scanTableList = new String[]{Constant.GDKLSF_HIS_TABLE_NAME};
			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
		}else if(lotteryType.indexOf("CQ")!=-1 || periodsNum.indexOf("CQ")!=-1){
			scanTableList = new String[]{Constant.CQSSC_HIS_TABLE_NAME};
			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
		}else if(lotteryType.indexOf("BJ")!=-1 || periodsNum.indexOf("BJ")!=-1){
			scanTableList = new String[]{Constant.BJSC_HIS_TABLE_NAME};
			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
		}else if(lotteryType.indexOf("K3")!=-1 || periodsNum.indexOf("K3")!=-1){
			scanTableList = new String[]{Constant.K3_HIS_TABLE_NAME};
			lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
		}else if(lotteryType.indexOf("NC")!=-1 || periodsNum.indexOf("NC")!=-1){
			scanTableList = new String[]{Constant.NC_HIS_TABLE_NAME};
			lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
		}else if(lotteryType.indexOf("ALL")!=-1){
			scanTableList = Constant.ALL_HIS_LIST;
			lotteryTypeFrm = "%";
		}
        String replenishTableName = Constant.REPLENISH_TABLE_NAME_HIS;
		String mananerStaffTableName = "tb_frame_manager_staff";
		String memberStaffTableName = "tb_frame_member_staff";
		//String outReplenishStaffExtTabelName = "tb_out_replenish_staff_ext";
			
			
        String winState = "";
        if(getRequest().getParameter("winState")!=null){
        	winState  = getRequest().getParameter("winState");
        }else{
        	winState = "9"; 
        }
        List<ClassReportEric> resultList = classReportEricLogic
                .findClassReport(bettingDateStart, bettingDateEnd, lotteryTypeFrm, vPlayType, periodsNumFrm, userID, userType,
                		scanTableList,winState,"");
        String userTitle = "";
        String adminTitle = "";
        for(ClassReportEric vo:resultList){
        	if(MemberStaff.USER_TYPE_MEMBER.equals(vo.getUserType())){
        		userTitle = ManagerStaff.USER_TYPE_AGENT.equals(userType)?"會員":"直屬會員";
        	}else{
        		adminTitle = ClassReportEric.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        	}
        }
        
//------------处理补出    	START
        //查询补货信息
        List<ClassReportEric> replenishList = classReportEricLogic.queryReplenish(bettingDateStart, bettingDateEnd,userID,userType,vPlayType, 
        		periodsNumFrm,lotteryTypeFrm,winState,replenishTableName,"");
        
        ClassReportEric replenishTotalEntity =  null;
        if (null != replenishList && replenishList.size() > 0) {
        	replenishTotalEntity = new ClassReportEric();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double memberAmount = (double) 0;
        	Double winBackWater = (double) 0;
        	Double backWaterResult = (double) 0;
        	
        	for(ClassReportEric vo : replenishList){
        		turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	memberAmount += vo.getMemberAmount();
            	winBackWater += vo.getWinBackWater();
            	backWaterResult += vo.getBackWaterResult();
        	}
        	replenishTotalEntity.setTurnover(turnover);
        	replenishTotalEntity.setAmount(amount);
        	replenishTotalEntity.setMemberAmount(memberAmount);
        	replenishTotalEntity.setWinBackWater(winBackWater);
        	replenishTotalEntity.setBackWaterResult(backWaterResult);
        }
//------------处理补出            END
        
        //合计数据
        ClassReportEric totalEntity = null;
        if (null != resultList && resultList.size() > 0) {
        	totalEntity = new ClassReportEric();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double memberAmount = (double) 0;
        	Double rateMoney = (double) 0;
        	Double realWin = (double) 0;
        	Double realBackWater = (double) 0;
        	Double commission = (double) 0;
        	Double offerSuperior = (double) 0;
        	Double subordinateAmountWin = (double) 0;/*应收下线输赢*/
        	Double subordinateAmountBackWater = (double) 0;/*应收下线退水*/
        	
            for(ClassReportEric vo : resultList){
            	turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	memberAmount += vo.getMemberAmount();
            	rateMoney += vo.getRateMoney();
            	realWin += vo.getRealWin();
            	realBackWater += vo.getRealBackWater();
            	commission += vo.getCommission();
            	offerSuperior += vo.getOfferSuperior();
            	subordinateAmountWin += vo.getSubordinateAmountWin();
            	subordinateAmountBackWater += vo.getSubordinateAmountBackWater();
            }
            totalEntity.setTurnover(turnover);
            totalEntity.setAmount(amount);
            totalEntity.setMemberAmount(memberAmount);
            totalEntity.setRateMoney(rateMoney);
            totalEntity.setRealWin(realWin);
            totalEntity.setRealBackWater(realBackWater);
            totalEntity.setCommission(commission);
            totalEntity.setOfferSuperior(offerSuperior);
            totalEntity.setSubordinateAmountWin(subordinateAmountWin);
            totalEntity.setSubordinateAmountBackWater(subordinateAmountBackWater);
        }
        
        //计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
        if (null != resultList && null != totalEntity) {
            
            Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
            Double offerSuperior = 0.0;//计算合计数据中的贡献上级
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).calRealResultPer(realResult);//占货比      实占金额/实占金额合计

                offerSuperior += resultList.get(i).getOfferSuperior();
            }

            totalEntity.setOfferSuperior(offerSuperior);//合计数据中赋值实占输赢、实占退水、贡献上级、赚取退水
            totalEntity.setRealResultPer(1.0);//设计合计中的占货比为100%
        }
        
        //抵扣补货及赚水后结果    占成结果+赚取退水-补货报表的退水后结果（总监少了一个赚取退水）
        Double dk = (double) 0;
        Double dBackWaterResult = (double) 0;
        if(replenishTotalEntity!=null){
        	dBackWaterResult = replenishTotalEntity.getBackWaterResult();
        }
        if(totalEntity!=null){
	        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
	            dk = totalEntity.getRealResultNew() - dBackWaterResult;
	        }else{
	        	dk = totalEntity.getRealResultNew() + totalEntity.getCommission() - dBackWaterResult;
	        }
        }
        DecimalFormat df = new DecimalFormat("0.0");
        String dkDis =  df.format(BigDecimal.valueOf(dk).setScale(1,BigDecimal.ROUND_HALF_UP));
        
        //应付上级    应收下线-抵扣补货及赚水后结果（总监没有这项）
        Double yf = (double) 0;
        if(totalEntity!=null){
	        if(!ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
		        yf = totalEntity.getSubordinateAmount() - dk;
	        }
        }
        DecimalFormat dff = new DecimalFormat("0.0");
        String yfDis =  dff.format(BigDecimal.valueOf(yf).setScale(1,BigDecimal.ROUND_HALF_UP));
        
		// add by peter 对resultList进行排序
		Collections.sort(resultList, new Comparator<ClassReportEric>() {
			@Override
			public int compare(ClassReportEric o1, ClassReportEric o2) {
				return new LotteryComparator().compareByCommissionType(o1.getCommissionType(), o2.getCommissionType());
			}
		});
		
		// add by peter 对replenishList进行排序
		Collections.sort(replenishList, new Comparator<ClassReportEric>() {
			@Override
			public int compare(ClassReportEric o1, ClassReportEric o2) {
				return new LotteryComparator().compareByCommissionType(o1.getCommissionType(), o2.getCommissionType());
			}
		});
        getRequest().setAttribute("yfDis", yfDis);
        getRequest().setAttribute("dkDis", dkDis);
        getRequest().setAttribute("resultList", resultList);
        getRequest().setAttribute("totalEntity", totalEntity);
        getRequest().setAttribute("replenishList", replenishList);
        getRequest().setAttribute("replenishTotalEntity", replenishTotalEntity);
        String reportInfo = "";
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + " - 分類報錶";
        }else{
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + "（"
                    + detailUserAccount + "）-分類報錶";
        }
        getRequest().setAttribute("reportInfo",reportInfo);
        
        String searchData = "";
        if("typeTime".equals(selectTypeRadio)){
        	searchData = "按日期查詢：" + bettingDateStart + " — " + bettingDateEnd + "";
        }else{
        	searchData = "按期數查詢：" + periodsNumFrm + " 期" + "";
        }
        getRequest().setAttribute("searchData", searchData);
        String tableTitle  = "";
        if(adminTitle!="" && userTitle==""){
        	tableTitle = adminTitle;
        }else if(adminTitle=="" && userTitle!=""){
        	tableTitle = userTitle;
        }else if(adminTitle!="" && userTitle!=""){
        	tableTitle = adminTitle + "/" + userTitle;
        }else if(adminTitle=="" && userTitle==""){
        	tableTitle = ClassReportEric.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        }
        getRequest().setAttribute("userTypeName", tableTitle);
        getRequest().setAttribute("userType", userType);
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	return "chiefList";
        }else{
        	return "list";
        }
    }

    
    /**
     * 报表列表--新方法--扫描VIEW
     * 
     * @return
     * @throws Exception
     */
    public String classListScanView() throws Exception {
    	java.sql.Date nowDate = new Date(new java.util.Date().getTime()- 60
                * 60 * 1000 * 3);
    	Boolean onlySearchToday = false;
    	if(bettingDateStart.equals(bettingDateEnd) && bettingDateStart.toString().equals(nowDate.toString())){
    		onlySearchToday = true;
    	}
        ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
    	//子帐号处理*********END
    	   
        /*
         * userID为空即用户直接查询，如果当前查询者是子帐号查询的，还要查询子帐号的所属者，把该所属者的userID和userType赋于查询变量
         * userID不为空即从上级往下查下级的，如果当前查询者是子帐号，同上。
         * 
         */
    	if(userID==null){
        	userID = userInfoNew.getID();
  			userType = userInfoNew.getUserType();
  			detailUserAccount = userInfoNew.getAccount();
        	//如果公司的开放报表功能打开，分公司查报表时就等同于总监查询，与总监登录查询一模一样。
        	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
        	    UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
      			if(Constant.OPEN.equals(userVO.getReport())){
      				userID = userVO.getChiefStaffExt().getManagerStaffID();
      				userType = userVO.getChiefStaffExt().getUserType();
      				detailUserAccount = userVO.getChiefStaffExt().getAccount();
      			}
      		}
        }
        
        this.parentUserType = userType;//设置 parentUserType 的属性值，直属会员明细中需要使用
        
        String vPlayType = null;
        //如果没有选择小类就直接查全部，
        if("ALL".indexOf(playType)!=-1){
        	vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
        //把没有选择的一项的参数赋于null
        String periodsNumFrm = ""; 
        String lotteryTypeFrm = ""; 
        String lotteryTypePeriod = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = "";
        	periodsNum = "";
        }
        String[] scanTableList = new String[]{};
    	if(lotteryType.indexOf("GD")!=-1 || periodsNum.indexOf("GD")!=-1){
    		scanTableList = new String[]{Constant.GDKLSF_HIS_TABLE_NAME};
    		lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
    		lotteryTypePeriod = Constant.LOTTERY_TYPE_GDKLSF;
    	}else if(lotteryType.indexOf("CQ")!=-1 || periodsNum.indexOf("CQ")!=-1){
    		scanTableList = new String[]{Constant.CQSSC_HIS_TABLE_NAME};
    		lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
    		lotteryTypePeriod = Constant.LOTTERY_TYPE_CQSSC;
    	}else if(lotteryType.indexOf("BJ")!=-1 || periodsNum.indexOf("BJ")!=-1){
    		scanTableList = new String[]{Constant.BJSC_HIS_TABLE_NAME};
    		lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
    		lotteryTypePeriod = Constant.LOTTERY_TYPE_BJ;
    	}else if(lotteryType.indexOf("K3")!=-1 || periodsNum.indexOf("K3")!=-1){
    		scanTableList = new String[]{Constant.K3_HIS_TABLE_NAME};
    		lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
    		lotteryTypePeriod = Constant.LOTTERY_TYPE_K3;
    	}else if(lotteryType.indexOf("NC")!=-1 || periodsNum.indexOf("NC")!=-1){
    		scanTableList = new String[]{Constant.NC_HIS_TABLE_NAME};
    		lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
    		lotteryTypePeriod = Constant.LOTTERY_TYPE_NC;
    	}else if(lotteryType.indexOf("ALL")!=-1){
			scanTableList = Constant.ALL_HIS_LIST;
			lotteryTypeFrm = "%";
		}
    	
    	String replenishTableName = Constant.REPLENISH_TABLE_NAME_HIS;
		String mananerStaffTableName = "tb_frame_manager_staff";
		String memberStaffTableName = "tb_frame_member_staff";
		//String outReplenishStaffExtTabelName = "tb_out_replenish_staff_ext";
        
        String winState = "";
        if(getRequest().getParameter("winState")!=null){
        	winState  = getRequest().getParameter("winState");
        }else{
        	winState = "9"; 
        }
        
        List<ClassReportEric> resultList = new ArrayList<ClassReportEric>();
        if("typePeriod".equals(selectTypeRadio) || onlySearchToday==true){
        	resultList = classReportEricLogic.queryClassReportPetPeriod(userID, userType,bettingDateStart, bettingDateEnd,periodsNumFrm,lotteryTypePeriod,"");
        }else{
        	if(lotteryType.indexOf("ALL")!=-1 && "ALL".indexOf(playType)!=-1){
        		List<ClassReportPetList> oldPetList = classReportEricLogic.queryClassReportPetList(userID, userType, bettingDateStart, bettingDateEnd);
	        	for(ClassReportPetList vo:oldPetList){
	        		ClassReportEric reportInfo = new ClassReportEric();
	            	reportInfo.setTurnover(vo.getTurnover());
	                reportInfo.setAmount(vo.getAmount());
	                reportInfo.setMoneyRateAgent(vo.getMoneyRateAgent());
	                reportInfo.setMoneyRateGenAgent(vo.getMoneyRateGenAgent());
	                reportInfo.setMoneyRateStockholder(vo.getMoneyRateStockholder());
	                reportInfo.setMoneyRateBranch(vo.getMoneyRateBranch());
	                reportInfo.setMoneyRateChief(vo.getMoneyRateChief());
	                reportInfo.setRateMoney(vo.getRateMoney());
	                
	                reportInfo.setMemberAmount(vo.getMemberAmount());
	                reportInfo.setSubordinateAmountWin(vo.getSubordinateAmountWin());
	                reportInfo.setSubordinateAmountBackWater(vo.getSubordinateAmountBackWater());
	                reportInfo.setRealWin(vo.getRealWin());
	                reportInfo.setRealBackWater(vo.getRealBackWater());
	                reportInfo.setCommission(vo.getCommission());
	                reportInfo.setCommissionType(vo.getCommissionType());
	                reportInfo.setUserType(vo.getUserType());
	        		resultList.add(reportInfo);
	        	}
        	}else if(!"ALL".equals(lotteryType) && "ALL".equals(playType)){
        		//如果是查询某一种大彩种的，而且细类是选择全部查询的， 就只查询盘期统计报表。
        		resultList = classReportEricLogic.queryClassReportPetPeriod(userID, userType,bettingDateStart, bettingDateEnd,periodsNumFrm,lotteryTypePeriod,"");
        	}else if(!"ALL".equals(playType)){
        		//如果是查询细类的，就直接扫描计算历史表，这样速度会较慢。
        		resultList = classReportEricLogic
                        .findClassReport(bettingDateStart, bettingDateEnd, lotteryTypeFrm, vPlayType, periodsNumFrm, userID, userType,
                        		scanTableList,winState,"");
        	}
        }
        
        String userTitle = "";
        String adminTitle = "";
        for(ClassReportEric vo:resultList){
        	if(MemberStaff.USER_TYPE_MEMBER.equals(vo.getUserType())){
        		userTitle = ManagerStaff.USER_TYPE_AGENT.equals(userType)?"會員":"直屬會員";
        	}else{
        		adminTitle = ClassReportEric.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        	}
        }
        
//------------处理补出    	START
        //查询补货信息
        List<ClassReportEric> replenishList = new ArrayList<ClassReportEric>();
        
        if("typePeriod".equals(selectTypeRadio) || onlySearchToday==true){
        	//按盘期查询或只查当天, 就只查询盘期统计报表。
        	replenishList = classReportEricLogic.queryClassReportRPeriod(userID, userType,bettingDateStart, bettingDateEnd,periodsNumFrm,lotteryTypePeriod,"");
        }else{
        	if(lotteryType.indexOf("ALL")!=-1 && "ALL".indexOf(playType)!=-1){
        		List<ClassReportRList> oldRList = classReportEricLogic.queryClassReportRList(userID, userType, bettingDateStart, bettingDateEnd);
	        	for(ClassReportRList vo:oldRList){
	        		ClassReportEric reportInfo = new ClassReportEric();
	        		reportInfo.setUserID(vo.getUserID());
	        		reportInfo.setUserType(vo.getUserType());
	        		reportInfo.setTurnover(vo.getTurnover());
	        		reportInfo.setAmount(vo.getAmount());
	        		reportInfo.setMemberAmount(vo.getMemberAmount());
	                reportInfo.setWinBackWater(vo.getWinBackWater());
	                reportInfo.setBackWaterResult(vo.getBackWaterResult());
	                reportInfo.setCommissionType(vo.getCommissionType());
	                replenishList.add(reportInfo);
	        	}
        	}else if(!"ALL".equals(lotteryType) && "ALL".equals(playType)){
        		//如果是查询某一种大彩种的，而且细类是选择全部查询的， 就只查询盘期统计报表。
        		replenishList = classReportEricLogic.queryClassReportRPeriod(userID, userType,bettingDateStart, bettingDateEnd,periodsNumFrm,lotteryTypePeriod,"");
        	}else if(!"ALL".equals(playType)){
        		//如果是查询细类的，就直接扫描计算历史表，这样速度会较慢。
        		String replenishTableName1 = Constant.REPLENISH_TABLE_NAME_HIS;
        		replenishList = classReportEricLogic.queryReplenish(bettingDateStart, bettingDateEnd,userID,userType,
                		vPlayType, periodsNumFrm,lotteryTypeFrm,winState,replenishTableName1,"");
        	}
        }
        
        ClassReportEric replenishTotalEntity =  null;
        if (null != replenishList && replenishList.size() > 0) {
        	replenishTotalEntity = new ClassReportEric();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double memberAmount = (double) 0;
        	Double winBackWater = (double) 0;
        	Double backWaterResult = (double) 0;
        	
        	for(ClassReportEric vo : replenishList){
        		turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	memberAmount += vo.getMemberAmount();
            	winBackWater += vo.getWinBackWater();
            	backWaterResult += vo.getBackWaterResult();
        	}
        	replenishTotalEntity.setTurnover(turnover);
        	replenishTotalEntity.setAmount(amount);
        	replenishTotalEntity.setMemberAmount(memberAmount);
        	replenishTotalEntity.setWinBackWater(winBackWater);
        	replenishTotalEntity.setBackWaterResult(backWaterResult);
        }
//------------处理补出            END
        
        //合计数据
        ClassReportEric totalEntity = null;
        if (null != resultList && resultList.size() > 0) {
        	totalEntity = new ClassReportEric();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double memberAmount = (double) 0;
        	Double rateMoney = (double) 0;
        	Double realWin = (double) 0;
        	Double realBackWater = (double) 0;
        	Double commission = (double) 0;
        	Double offerSuperior = (double) 0;
        	Double subordinateAmountWin = (double) 0;/*应收下线输赢*/
        	Double subordinateAmountBackWater = (double) 0;/*应收下线退水*/
        	
            for(ClassReportEric vo : resultList){
            	turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	memberAmount += vo.getMemberAmount();
            	rateMoney += vo.getRateMoney();
            	realWin += vo.getRealWin();
            	realBackWater += vo.getRealBackWater();
            	commission += vo.getCommission();
            	offerSuperior += vo.getOfferSuperior();
            	subordinateAmountWin += vo.getSubordinateAmountWin();
            	subordinateAmountBackWater += vo.getSubordinateAmountBackWater();
            }
            totalEntity.setTurnover(turnover);
            totalEntity.setAmount(amount);
            totalEntity.setMemberAmount(memberAmount);
            totalEntity.setRateMoney(rateMoney);
            totalEntity.setRealWin(realWin);
            totalEntity.setRealBackWater(realBackWater);
            totalEntity.setCommission(commission);
            totalEntity.setOfferSuperior(offerSuperior);
            totalEntity.setSubordinateAmountWin(subordinateAmountWin);
            totalEntity.setSubordinateAmountBackWater(subordinateAmountBackWater);
        }
        
        //计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
        if (null != resultList && null != totalEntity) {
            
            Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
            Double offerSuperior = 0.0;//计算合计数据中的贡献上级
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).calRealResultPer(realResult);//占货比      实占金额/实占金额合计

                offerSuperior += resultList.get(i).getOfferSuperior();
            }

            totalEntity.setOfferSuperior(offerSuperior);//合计数据中赋值实占输赢、实占退水、贡献上级、赚取退水
            totalEntity.setRealResultPer(1.0);//设计合计中的占货比为100%
        }
        
        //抵扣补货及赚水后结果    占成结果+赚取退水-补货报表的退水后结果（总监少了一个赚取退水）
        Double dk = (double) 0;
        Double dBackWaterResult = (double) 0;
        if(replenishTotalEntity!=null){
        	dBackWaterResult = replenishTotalEntity.getBackWaterResult();
        }
        if(totalEntity!=null){
	        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
	            dk = totalEntity.getRealResultNew() - dBackWaterResult;
	        }else{
	        	dk = totalEntity.getRealResultNew() + totalEntity.getCommission() - dBackWaterResult;
	        }
        }
        DecimalFormat df = new DecimalFormat("0.0");
        String dkDis =  df.format(BigDecimal.valueOf(dk).setScale(1,BigDecimal.ROUND_HALF_UP));
        
        //应付上级    应收下线-抵扣补货及赚水后结果（总监没有这项）
        Double yf = (double) 0;
        if(totalEntity!=null){
	        if(!ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
		        yf = totalEntity.getSubordinateAmount() - dk;
	        }
        }
        DecimalFormat dff = new DecimalFormat("0.0");
        String yfDis =  dff.format(BigDecimal.valueOf(yf).setScale(1,BigDecimal.ROUND_HALF_UP));
        
		// add by peter 对resultList进行排序
		Collections.sort(resultList, new Comparator<ClassReportEric>() {
			@Override
			public int compare(ClassReportEric o1, ClassReportEric o2) {
				return new LotteryComparator().compareByCommissionType(o1.getCommissionType(), o2.getCommissionType());
			}
		});
		
		// add by peter 对replenishList进行排序
		Collections.sort(replenishList, new Comparator<ClassReportEric>() {
			@Override
			public int compare(ClassReportEric o1, ClassReportEric o2) {
				return new LotteryComparator().compareByCommissionType(o1.getCommissionType(), o2.getCommissionType());
			}
		});
        getRequest().setAttribute("yfDis", yfDis);
        getRequest().setAttribute("dkDis", dkDis);
        getRequest().setAttribute("resultList", resultList);
        getRequest().setAttribute("totalEntity", totalEntity);
        getRequest().setAttribute("replenishList", replenishList);
        getRequest().setAttribute("replenishTotalEntity", replenishTotalEntity);
        String reportInfo = "";
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + " - 分類報錶";
        }else{
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + "（"
                    + detailUserAccount + "）-分類報錶";
        }
        getRequest().setAttribute("reportInfo",reportInfo);
        
        String searchData = "";
        if("typeTime".equals(selectTypeRadio)){
        	searchData = "按日期查詢：" + bettingDateStart + " — " + bettingDateEnd + "";
        }else{
        	searchData = "按期數查詢：" + periodsNumFrm + " 期" + "";
        }
        getRequest().setAttribute("searchData", searchData);
        String tableTitle  = "";
        if(adminTitle!="" && userTitle==""){
        	tableTitle = adminTitle;
        }else if(adminTitle=="" && userTitle!=""){
        	tableTitle = userTitle;
        }else if(adminTitle!="" && userTitle!=""){
        	tableTitle = adminTitle + "/" + userTitle;
        }else if(adminTitle=="" && userTitle==""){
        	tableTitle = ClassReportEric.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        }
        getRequest().setAttribute("userTypeName", tableTitle);
        getRequest().setAttribute("userType", userType);
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	return "chiefList";
        }else{
        	return "list";
        }
    }

    /**
     * 补货会员明细信息
     * 
     * @return
     * @throws Exception
     */
    public String classRelenishDetailed() throws Exception {

    	ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
    	Long currentUserId = userInfoNew.getID();
    	String currentUserType  = userInfoNew.getUserType();
    	
    	//判断分公司的查询报表功能有没有打开
    	String branchReport = Constant.CLOSE;
    	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
    		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
  			if(Constant.OPEN.equals(userVO.getReport())){
  				currentUserId = userVO.getChiefStaffExt().getManagerStaffID();
  		    	currentUserType  = userVO.getChiefStaffExt().getUserType();
  		    	branchReport = userVO.getReport();
  			}
  		}
    	//子帐号处理*********END
    	getRequest().setAttribute("branchReport",branchReport);//用于在页面判断公司是否开放报表
  		Page<DetailVO> page = new Page<DetailVO>(25);
  		int pageNo=1;
  		
  		if(this.getRequest().getParameter("pageNo")!=null)
  			pageNo=this.findParamInt("pageNo");
  		page.setPageNo(pageNo);
  		
  		String commissionTypeCode = "";
		if("ALL".indexOf(playType)!=-1){
			commissionTypeCode = "%";
		}else{
			commissionTypeCode = playType + "%";
		}
		//把没有选择的一项的参数赋于null
		String periodsNumFrm = ""; 
        String lotteryTypeFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = null;
        }
        if("typePeriod".equals(selectTypeRadio)){
        	if(periodsNum.indexOf("GD")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
    		}else if(periodsNum.indexOf("CQ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
    		}else if(periodsNum.indexOf("BJ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
    	    }else if(periodsNum.indexOf("K3")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
    	    }else if(periodsNum.indexOf("NC")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
    	    }
        }else{
        	if(playType.indexOf("GD")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
    		}else if(playType.indexOf("CQ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
    		}else if(playType.indexOf("BJ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
    	    }else if(playType.indexOf("K3")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
    	    }else if(playType.indexOf("NC")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
    	    }else if(playType.indexOf("ALL")!=-1){
            	lotteryTypeFrm = "%";
    	    }
        }

        page=classReportEricLogic.findReplenishDetail(page, bettingDateStart, bettingDateEnd, periodsNumFrm, currentUserId, currentUserType, commissionTypeCode, lotteryTypeFrm);
      //按照盈虧排序亏损高排前
 		 Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
 	           public int compare(DetailVO arg0, DetailVO arg1) {   
 	               return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
 	            }   
 	     });
 		this.getRequest().setAttribute("page", page);
 		this.getRequest().setAttribute("isPet", "false");//用来在明细里控制分页的ACTION
        return "playDetail";
    }
    
    //投注明細
  	public String detail(){
  		ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
    	Long currentUserId = userInfoNew.getID();
    	String currentUserType  = userInfoNew.getUserType();
    	
    	//判断分公司的查询报表功能有没有打开
    	String branchReport = Constant.CLOSE;
    	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
    		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
  			if(Constant.OPEN.equals(userVO.getReport())){
  				currentUserId = userVO.getChiefStaffExt().getManagerStaffID();
  		    	currentUserType  = userVO.getChiefStaffExt().getUserType();
  		    	userInfoNew.setID(currentUserId);
  		    	userInfoNew.setUserType(currentUserType);
  		    	branchReport = userVO.getReport();
  			}
  		}
    	//子帐号处理*********END
    	getRequest().setAttribute("branchReport",branchReport);//用于在页面判断公司是否开放报表
    	
  		Page<DetailVO> page = new Page<DetailVO>(25);
  		Page<DetailVO> pageIn = new Page<DetailVO>(25);
  		int pageNo=1;
  		
  		if(this.getRequest().getParameter("pageNo")!=null)
  			pageNo=this.findParamInt("pageNo");
  		page.setPageNo(pageNo);
  		pageIn.setPageNo(pageNo);
  		
        String vPlayType = "";
		if("ALL".indexOf(playType)!=-1){
			vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
		//把没有选择的一项的参数赋于null
		String periodsNumFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = null;
        }
        
        String[] scanTableList = new String[]{};
        if("typePeriod".equals(selectTypeRadio)){
        	if(periodsNum.indexOf("GD")!=-1){
				scanTableList = new String[]{Constant.GDKLSF_HIS_TABLE_NAME};
			}else if(periodsNum.indexOf("CQ")!=-1){
				scanTableList = new String[]{Constant.CQSSC_HIS_TABLE_NAME};
			}else if(periodsNum.indexOf("BJ")!=-1){
				scanTableList = new String[]{Constant.BJSC_HIS_TABLE_NAME};
			}else if(periodsNum.indexOf("K3")!=-1){
				scanTableList = new String[]{Constant.K3_HIS_TABLE_NAME};
			}else if(periodsNum.indexOf("NC")!=-1){
				scanTableList = new String[]{Constant.NC_HIS_TABLE_NAME};
			}
        }else{
	        if(playType.indexOf("GD")!=-1){
				scanTableList = new String[]{Constant.GDKLSF_HIS_TABLE_NAME};
			}else if(playType.indexOf("CQ")!=-1){
				scanTableList = new String[]{Constant.CQSSC_HIS_TABLE_NAME};
			}else if(playType.indexOf("BJ")!=-1){
				scanTableList = new String[]{Constant.BJSC_HIS_TABLE_NAME};
			}else if(playType.indexOf("K3")!=-1){
				scanTableList = new String[]{Constant.K3_HIS_TABLE_NAME};
			}else if(playType.indexOf("NC")!=-1){
				scanTableList = new String[]{Constant.NC_HIS_TABLE_NAME};
			}else if(playType.indexOf("ALL")!=-1){
				scanTableList = Constant.ALL_HIS_LIST;
			}
        }
			
			
      	page=classReportEricLogic.findDetail(page, bettingDateStart, bettingDateEnd, vPlayType, periodsNumFrm, currentUserId,currentUserType,scanTableList);
      	String tableName = Constant.REPLENISH_TABLE_NAME_HIS;
      	pageIn=replenishLogic.queryReplenishInDetail(pageIn, userInfoNew, null, periodsNumFrm,playType,
      			bettingDateStart, bettingDateEnd,Replenish.WIN_STATE_NOT_PRESENT,"cleared",tableName);
        
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
      	if(pageIn.getResult().size()>0){
			page.getResult().addAll(pageIn.getResult());
		}
  		//按照盈虧排序亏损高排前
  		 Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
  	           public int compare(DetailVO arg0, DetailVO arg1) {   
  	               return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
  	            }   
  	     });
  		this.getRequest().setAttribute("page", page);
  		this.getRequest().setAttribute("isPet", "true");//用来在明细里控制分页的ACTION
  		return "playDetail";
  	}
    
//~~~~~~~~~~~~~~~~~~~~~~~交收报表--未结算报表END

    private Long bettingUserID;//投注会员ID

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息

    private Date bettingDateStart;//开始时间

    private Date bettingDateEnd;//结束时间

    private String reportType;//报表类型
    
    private String parentUserType;//上级用户userID

    private GdklsfHis gdklsfHisEntity;//广东快乐十分历史投注信息
    
    private Long userID;
    
    private String userType;
    
    private String detailUserAccount;
    
    private String selectTypeRadio; //判断是以盘期查询还是以日期查询    typePeriod:盘期    typeTime:日期

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getTypePeriod() {
        return typePeriod;
    }

    public void setTypePeriod(String typePeriod) {
        this.typePeriod = typePeriod;
    }

    public String getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(String typeTime) {
        this.typeTime = typeTime;
    }

    public String getPeriodsNum() {
        return periodsNum;
    }

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	public IClassReportEricLogic getClassReportEricLogic() {
		return classReportEricLogic;
	}

	public void setClassReportEricLogic(
			IClassReportEricLogic classReportEricLogic) {
		this.classReportEricLogic = classReportEricLogic;
	}

	public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}

	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}

	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

	public Date getBettingDateStart() {
        return bettingDateStart;
    }

    public void setBettingDateStart(Date bettingDateStart) {
        this.bettingDateStart = bettingDateStart;
    }

    public Date getBettingDateEnd() {
        return bettingDateEnd;
    }

    public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public void setBettingDateEnd(Date bettingDateEnd) {
        this.bettingDateEnd = bettingDateEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getParentUserType() {
		return parentUserType;
	}

	public void setParentUserType(String parentUserType) {
		this.parentUserType = parentUserType;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GdklsfHis getGdklsfHisEntity() {
        return gdklsfHisEntity;
    }

    public void setGdklsfHisEntity(GdklsfHis gdklsfHisEntity) {
        this.gdklsfHisEntity = gdklsfHisEntity;
    }

    public Long getBettingUserID() {
        return bettingUserID;
    }

    public void setBettingUserID(Long bettingUserID) {
        this.bettingUserID = bettingUserID;
    }

	public Long getUserID() {
		return userID;
	}

	public String getUserType() {
		return userType;
	}

	public String getDetailUserAccount() {
		return detailUserAccount;
	}

	public void setDetailUserAccount(String detailUserAccount) {
		this.detailUserAccount = detailUserAccount;
	}

	public String getSelectTypeRadio() {
		return selectTypeRadio;
	}

	public void setSelectTypeRadio(String selectTypeRadio) {
		this.selectTypeRadio = selectTypeRadio;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
