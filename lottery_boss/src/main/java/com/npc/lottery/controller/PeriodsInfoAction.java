package com.npc.lottery.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.entity.HKPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.util.Page;
import com.opensymphony.oscache.util.StringUtil;

public class PeriodsInfoAction extends BaseLotteryAction {

    /**
     * 
     */
    private static final long serialVersionUID = -9047557086466189626L;

    private static Logger logger = Logger.getLogger(PeriodsInfoAction.class);
    private IHKPeriodsInfoLogic skperiodsInfoLogic = null;
    private ISystemLogic systemLogic;
    private String periodsType="periodsType";
    private HKPeriodsInfo hkperiodsInfos = null;
    private String lotteryTime; //选择日期
    private String numPeriods;
    private ManagerUser userInfo;
    private IHKPeriodsLogic hkperiodsLogic;
    private HKPeriods hkPeriods;
    private String subType="HKLHC";
    private IShopOddsLogic shopOddsLogic;
    private IPlayTypeLogic playTypeLogic;
    private String openDate;
    private IGDPeriodsInfoLogic periodsInfoLogic = null;
    private ICQPeriodsInfoLogic icqPeriodsInfoLogic = null;
    private IMemberStaffExtLogic memberStaffExtLogic;
    private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic = null;
    private IJSSBPeriodsInfoLogic	jssbPeriodsInfoLogic	= null; //快3
    private INCPeriodsInfoLogic	ncPeriodsInfoLogic	= null; 
    private ShopSchemeService shopSchemeService;
    private IShopsLogic shopsLogic;
    
    

	//总管查看广东盘期
    public String queryGDResultPeriods() {
    	logger.info("queryGDResultPeriods");
        userInfo = getInfo();
        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date();
        String startStr = sdf.format(now) + " 00:00:00";
        String endStr = sdf.format(now) + " 23:59:59";
        Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
        Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
        sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 2);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
        Page<GDPeriodsInfo> page = new Page<GDPeriodsInfo>(20);
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("ID");
        page.setOrder("asc");
        try {
            page = periodsInfoLogic.findPage(page,filtersPeriodInfo
                    .toArray(new Criterion[filtersPeriodInfo.size()]));
            this.getRequest().setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法queryGDResultPeriods时出现错误 "
                    + e.getMessage());
        }
    	 return SUCCESS;
    }
    
    /**
     * 总管查看农场盘期
     * 农场第01期开盘是23:53:00，第97期开奖时间是23:53:00
     * @return
     */
    public String queryNCResultPeriods() {
    	logger.info("queryNCResultPeriods");
    	userInfo = getInfo();
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date now = new java.util.Date();
    	String startStr = sdf.format(now) + " 00:00:00";
    	String endStr = sdf.format(now) + " 23:59:59";
    	Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
    	Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
    	sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 7 * 1000 * -1);//农场第01期开盘是23:53:00,所以要提前一小时，再减去7分钟
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
    	Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>(20);
    	int pageNo = 1;
    	if (this.getRequest().getParameter("pageNo") != null)
    		pageNo = this.findParamInt("pageNo");
    	page.setPageNo(pageNo);
    	page.setOrderBy("ID");
    	page.setOrder("asc");
    	try {
    		page = ncPeriodsInfoLogic.findPage(page,filtersPeriodInfo
    				.toArray(new Criterion[filtersPeriodInfo.size()]));
    		this.getRequest().setAttribute("page", page);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法queryNCResultPeriods时出现错误 "
    				+ e.getMessage());
    	}
    	return SUCCESS;
    }
    /**
     * 总管查看重庆盘期
     * @return
     */
    public String queryCQResultPeriods() {
    	logger.info("queryCQResultPeriods");
        userInfo = getInfo();
        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date();
        String startStr = sdf.format(now) + " 00:00:00";
        String endStr = sdf.format(now) + " 23:59:59";
        Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
        Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
        sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 0);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
        Page<CQPeriodsInfo> page = new Page<CQPeriodsInfo>(20);
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("ID");
        page.setOrder("asc");
        try {
            page = icqPeriodsInfoLogic.findPage(page,filtersPeriodInfo
                    .toArray(new Criterion[filtersPeriodInfo.size()]));
            this.getRequest().setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法queryCQResultPeriods时出现错误 "
                    + e.getMessage());
        }
    	 return SUCCESS;
    }
    //总管查看北京赛车盘期
    public String queryBJResultPeriods() {
    	logger.info("queryBJResultPeriods");
    	userInfo = getInfo();
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date now = new java.util.Date();
    	String startStr = sdf.format(now) + " 00:00:00";
    	String endStr = sdf.format(now) + " 23:59:59";
    	Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
    	Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
    	sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 0);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
    	Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>(20);
    	int pageNo = 1;
    	if (this.getRequest().getParameter("pageNo") != null)
    		pageNo = this.findParamInt("pageNo");
    	page.setPageNo(pageNo);
    	page.setOrderBy("ID");
    	page.setOrder("asc");
    	try {
    		page = bjscPeriodsInfoLogic.findPage(page,filtersPeriodInfo
    				.toArray(new Criterion[filtersPeriodInfo.size()]));
    		this.getRequest().setAttribute("page", page);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法queryCQResultPeriods时出现错误 "
    				+ e.getMessage());
    	}
    	return SUCCESS;
    }
    
    //总管查看K3盘期
    public String queryK3ResultPeriods() {
    	userInfo = getInfo();
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date now = new java.util.Date();
    	String startStr = sdf.format(now) + " 00:00:00";
    	String endStr = sdf.format(now) + " 23:59:59";
    	Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
    	Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
    	sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 0);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
    	Page<JSSBPeriodsInfo> page = new Page<JSSBPeriodsInfo>(20);
    	int pageNo = 1;
    	if (this.getRequest().getParameter("pageNo") != null)
    		pageNo = this.findParamInt("pageNo");
    	page.setPageNo(pageNo);
    	page.setOrderBy("ID");
    	page.setOrder("asc");
    	try {
    		page = jssbPeriodsInfoLogic.findPage(page,filtersPeriodInfo
    				.toArray(new Criterion[filtersPeriodInfo.size()]));
    		this.getRequest().setAttribute("page", page);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法queryK3ResultPeriods时出现错误 "
    				+ e.getMessage());
    	}
    	return SUCCESS;
    }
    
    //总管查看重庆盘期
    public String queryBJSCResultPeriods() {
    	logger.info("queryBJResultPeriods");
        userInfo = getInfo();
        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date();
        String startStr = sdf.format(now) + " 00:00:00";
        String endStr = sdf.format(now) + " 23:59:59";
        Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
        Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
        sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 0);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	filtersPeriodInfo.add(Restrictions.between("openQuotTime", sqlDateStart, sqlDateEnd));
        Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>(20);
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("ID");
        page.setOrder("asc");
        try {
            page = bjscPeriodsInfoLogic.findPage(page,filtersPeriodInfo
                    .toArray(new Criterion[filtersPeriodInfo.size()]));
            this.getRequest().setAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法queryBJResultPeriods时出现错误 "
                    + e.getMessage());
        }
    	 return SUCCESS;
    }
    
    
    
    //总管设定期期
    public String saveNewPeriods() {
        userInfo = getInfo();
        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.eq("state","1"));
        HKPeriodsInfo periodsInfo = new HKPeriodsInfo();
        periodsInfo = skperiodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
        if(periodsInfo ==null){
            return SUCCESS;
        }
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>已经存在一期数没有开奖，不能开设下一期</font>");
        return "failure";
    }
    
    /*
    //跳到修改盘期
    public String updateFindPeriods(){
        String ID = getRequest().getParameter("ID");
        try {
            List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
            filtersPeriodInfo.add(Restrictions.lt("state",Constant.LOTTERY_STATUS));
            hkperiodsInfos = skperiodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
            if(hkperiodsInfos !=null){
                SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String lotteryDate = openFormat.format(hkperiodsInfos.getLotteryTime());
                String lotteryTimes[]  = lotteryDate.split(" ");
                lotteryTime = lotteryTimes[0];
                userInfo = getInfo();
                hkPeriods = hkperiodsLogic.queryByPeriods(hkperiodsInfos.getID(),userInfo.getSafetyCode());
            }else{
                getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                        + Constant.COLOR_RED + "'>还没到开奖日</font>");
                return "failure";
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateFindPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
    }
    //总监盘期修改
    public String updatePeriods(){
        String flag = getRequest().getParameter("flag");
        userInfo = getInfo();
        SimpleDateFormat openFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        HKPeriods hkPeriodInfo = new HKPeriods();
        hkPeriodInfo = hkperiodsLogic.queryByPeriods(hkperiodsInfos.getID(),userInfo.getSafetyCode());
        boolean isChief = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_CHIEF); // 总监类型
        Calendar resultCalender = Calendar.getInstance();
        resultCalender.setTime(hkperiodsInfos.getLotteryTime());  //时间相关系
        resultCalender.add(Calendar.MINUTE,-hkperiodsInfos.getAutoStopOuot());
        Date openPeriodsDate = null;
        if("zidong".equals(flag)){
            //選擇自動開盤時間
            if(!"".equals(openDate) && !StringUtil.isEmpty(openDate)){
                String openDateTime = openFormat.format(new Date()) +" "+openDate;
                try {
                    SimpleDateFormat openFmt = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm");
                    openPeriodsDate = openFmt.parse(openDateTime);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }  
                if(openPeriodsDate.getTime()>resultCalender.getTime().getTime()){
                    getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                            + Constant.COLOR_RED + "'>開盤時間不能超過封盤時間</font>");
                    return "failure";
                }
            }
        }else{
            if("open".equals(flag)){
                //手動開盤的時候
                  if(new Date().getTime()>resultCalender.getTime().getTime()){
                      getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                      getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                              + Constant.COLOR_RED + "'>開盤時間不能超過封盤時間</font>");
                      return "failure";
                  }
              }
          }
        HKPeriods hkPeriod = new HKPeriods();
       String periodsInfo =null;
        if(hkPeriodInfo == null){
            if(isChief){
                hkPeriod.setStopQuotTime(resultCalender.getTime());
                hkPeriod.setModifyTime(new Date());
                hkPeriod.setModifyUser(userInfo.getID());
                hkPeriod.setShopsCode(userInfo.getSafetyCode());
                hkPeriod.setAutoStopOuot(hkperiodsInfos.getAutoStopOuot());    
                hkPeriod.setPeriodsInfoID(hkperiodsInfos.getID());
                if("open".equals(flag)){
                    hkPeriod.setPeriodsState(Constant.HK_OPEN_STATUS);
                    hkPeriod.setOpenQuotTime(new Date());
                }else if("stop".equals(flag)){
                    hkPeriod.setPeriodsState(Constant.HK_STOP_STATUS);
                }else if("zidong".equals(flag)){
                    hkPeriod.setPeriodsState(Constant.HK_ZIDONG_OPEN_STATUS);
                    hkPeriod.setOpenQuotTime(openPeriodsDate);
                }
                try {
                    hkperiodsLogic.save(hkPeriod);
                    this.systemLogic.updateHKAutoOdds();
                    shopOddsLogic.updateRealOddsFromOpenOddsForHK(userInfo.getSafetyCode());
                    playTypeLogic.updatePlayTypeAmountZero("HKLHC");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法updatePeriods时出现错误 "
                            + e.getMessage());
                }
                if("open".equals(flag)){
                        periodsInfo = "开盘成功";
                        getRequest().setAttribute("periodsInfo",periodsInfo);
                        return SUCCESS;
                }else if("stop".equals(flag)){
                    periodsInfo = "封盘成功";
                    getRequest().setAttribute("periodsInfo",periodsInfo);
                        return SUCCESS;
                }else if("zidong".equals(flag)){
                    periodsInfo = "手动开盘成功";
                    getRequest().setAttribute("periodsInfo",periodsInfo);
                        return SUCCESS;
                }
            }
         }else{
             if(isChief){
                 if("open".equals(flag)){
                     hkPeriod.setPeriodsState(Constant.HK_OPEN_STATUS);
                     hkPeriod.setOpenQuotTime(new Date());
                     this.systemLogic.updateHKAutoOdds();
                     shopOddsLogic.updateRealOddsFromOpenOddsForHK(userInfo.getSafetyCode());
                     playTypeLogic.updatePlayTypeAmountZero("HKLHC");
                 }else if("stop".equals(flag)){
                     hkPeriod.setPeriodsState(Constant.HK_STOP_STATUS);
                 }else if("zidong".equals(flag)){
                     hkPeriod.setPeriodsState(Constant.HK_ZIDONG_OPEN_STATUS);
                     hkPeriod.setOpenQuotTime(openPeriodsDate);
                 }
                 hkPeriod.setStopQuotTime(resultCalender.getTime());
                 hkPeriod.setModifyTime(hkPeriodInfo.getModifyTime());
                 hkPeriod.setID(hkPeriodInfo.getID());
                 hkPeriod.setModifyUser(userInfo.getID());
                 hkPeriod.setShopsCode(hkPeriodInfo.getShopsCode());
                 hkPeriod.setAutoStopOuot(hkperiodsInfos.getAutoStopOuot());
                 hkPeriod.setPeriodsInfoID(hkPeriodInfo.getPeriodsInfoID());
                 try {
                     hkperiodsLogic.update(hkPeriod);
                 } catch (Exception e) {
                     e.printStackTrace();
                     logger.error("执行" + this.getClass().getSimpleName()
                             + "中的方法updatePeriods时出现错误 "
                             + e.getMessage());
                 }
                 
                 if("open".equals(flag)){
                     periodsInfo = "开盘成功";
                     getRequest().setAttribute("periodsInfo",periodsInfo);
                         return SUCCESS;
                 }else if("stop".equals(flag)){
                     periodsInfo = "封盘成功";
                     getRequest().setAttribute("periodsInfo",periodsInfo); 
                         return SUCCESS;
                 }else if("zidong".equals(flag)){
                     periodsInfo = "手动开盘成功";
                     getRequest().setAttribute("periodsInfo",periodsInfo);
                         return SUCCESS;
                 }
                 
             }
         }
        if("open".equals(flag)){
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>开盘不成功</font>");
        }else if("stop".equals(flag)){
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>封盘不成功</font>");
        }else if("zidong".equals(flag)){
            getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>自动开盘不成功</font>");
        }
        
        return "failure";
    }*/
    
    public ISystemLogic getSystemLogic() {
		return systemLogic;
	}

	public void setSystemLogic(ISystemLogic systemLogic) {
		this.systemLogic = systemLogic;
	}

	/*
     * 總管操作修改
     */
    public String updateFindResultPeriods(){
        String ID = getRequest().getParameter("ID");
        try {
            long periodsId = Long.parseLong(ID);
            hkperiodsInfos = skperiodsInfoLogic.queryByHKPeriods("ID",periodsId);
            SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lotteryDate = openFormat.format(hkperiodsInfos.getLotteryTime());
            String lotteryTimes[]  = lotteryDate.split(" ");
            lotteryTime = lotteryTimes[0];
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateFindResultPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
    }
    
    /*
     * 總管操作修改
     */
    public String updateFindHistoryResultPeriods(){
        String ID = getRequest().getParameter("ID");
        try {
            long periodsId = Long.parseLong(ID);
            hkperiodsInfos = skperiodsInfoLogic.queryByHKPeriods("ID",periodsId);
            SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lotteryDate = openFormat.format(hkperiodsInfos.getLotteryTime());
            String lotteryTimes[]  = lotteryDate.split(" ");
            lotteryTime = lotteryTimes[0];
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateFindResultPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
    }
    
   /* *//**
     * 總管手動提交開獎數據
     * @return
     *//*
    public String updateResultPeriods(){
        HKPeriodsInfo hkInfo = new HKPeriodsInfo();
        try {
            hkInfo = skperiodsInfoLogic.queryByHKPeriods("ID",hkperiodsInfos.getID());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行时间异常" + this.getClass().getSimpleName()
                    + "中的方法updateResultPeriods时出现错误 "
                    + e.getMessage());
        }
        hkInfo.setResult1(hkperiodsInfos.getResult1());
        hkInfo.setResult2(hkperiodsInfos.getResult2());
        hkInfo.setResult3(hkperiodsInfos.getResult3());
        hkInfo.setResult4(hkperiodsInfos.getResult4());
        hkInfo.setResult5(hkperiodsInfos.getResult5());
        hkInfo.setResult6(hkperiodsInfos.getResult6());
        hkInfo.setResult7(hkperiodsInfos.getResult7());
        hkInfo.setState(Constant.LOTTERY_STATUS);
        List<HKPeriods> periodList = new ArrayList<HKPeriods>();
        try {
            logger.info("查询所有总监状态");
            periodList = hkperiodsLogic.queryAll(hkInfo.getID());
            skperiodsInfoLogic.update(hkInfo);
            for (HKPeriods period : periodList) {
                logger.info("总监状态修改为开奖");
                period.setPeriodsState(Constant.LOTTERY_STATUS);
                hkperiodsLogic.update(period);
            }
            List<Integer> winNums = new ArrayList<Integer>();
            winNums.add(hkInfo.getResult1());
            winNums.add(hkInfo.getResult2());
            winNums.add(hkInfo.getResult3());
            winNums.add(hkInfo.getResult4());
            winNums.add(hkInfo.getResult5());
            winNums.add(hkInfo.getResult6());
            winNums.add(hkInfo.getResult7());
            lotteryResultLogic.updateLotteryHK(hkInfo.getPeriodsNum(),winNums);
        } catch (Exception e) {
            logger.info("执行香港开奖数据出错");
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateResultPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
     }*/
    
    /**
     * 總管手動提交開獎數據
     * @return
     */
    public String updateMangerPeriods(){
        String flag = getRequest().getParameter("flag");
        String lotteryTimes[] = lotteryTime.split("-");
        SimpleDateFormat openFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        Date openDate = null;
        Date  stopDate =null;
        try {
                String LotteryDate = lotteryTime+" "+hkperiodsInfos.getLotteryTimeInfo();
                openDate = openFormat.parse(LotteryDate);
                stopDate = openFormat.parse(LotteryDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行时间异常" + this.getClass().getSimpleName()
                    + "中的方法savePeriods时出现错误 "
                    + e.getMessage());
        }
        Calendar resultCalender = Calendar.getInstance();
        resultCalender.setTime(stopDate);  //时间相关系
        resultCalender.add(Calendar.MINUTE,-hkperiodsInfos.getAutoStopOuot());
        Date resultDate = (Date) resultCalender.getTime(); 
        logger.info(resultDate);   //相减想加得出值
        if(Integer.valueOf(hkperiodsInfos.getPeriodsInfo())>100){
            String periodsNum = lotteryTimes[0]+lotteryTimes[1]+lotteryTimes[2]+hkperiodsInfos.getPeriodsInfo();
            hkperiodsInfos.setPeriodsNum(periodsNum);
        }else if(Integer.valueOf(hkperiodsInfos.getPeriodsInfo())>10){
            String periodsNum = lotteryTimes[0]+lotteryTimes[1]+lotteryTimes[2]+"0"+hkperiodsInfos.getPeriodsInfo();
            hkperiodsInfos.setPeriodsNum(periodsNum);
        }else if(Integer.valueOf(hkperiodsInfos.getPeriodsInfo())<10){
            String periodsNum = lotteryTimes[0]+lotteryTimes[1]+lotteryTimes[2]+"00"+hkperiodsInfos.getPeriodsInfo();
            hkperiodsInfos.setPeriodsNum(periodsNum);
        }
        hkperiodsInfos.setState(Constant.NOT_STATUS);
        hkperiodsInfos.setLotteryTime(openDate);
        hkperiodsInfos.setStopQuotTime(resultDate);
        try {
            skperiodsInfoLogic.update(hkperiodsInfos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateResultPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
     }
    
  /*  *//**
     * 總管手動修改提交历史開獎數據
     * @return
     *//*
    public String updateHistoryResultPeriods(){
        HKPeriodsInfo hkInfo = new HKPeriodsInfo();
        try {
            hkInfo = skperiodsInfoLogic.queryByHKPeriods("ID",hkperiodsInfos.getID());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行时间异常" + this.getClass().getSimpleName()
                    + "中的方法updateHistoryResultPeriods时出现错误 "
                    + e.getMessage());
        }
        hkInfo.setResult1(hkperiodsInfos.getResult1());
        hkInfo.setResult2(hkperiodsInfos.getResult2());
        hkInfo.setResult3(hkperiodsInfos.getResult3());
        hkInfo.setResult4(hkperiodsInfos.getResult4());
        hkInfo.setResult5(hkperiodsInfos.getResult5());
        hkInfo.setResult6(hkperiodsInfos.getResult6());
        hkInfo.setResult7(hkperiodsInfos.getResult7());
        try {
            skperiodsInfoLogic.update(hkInfo);
            lotteryResultLogic.updateSecondLotteryHK(hkInfo.getPeriodsNum(), Lists.newArrayList(hkInfo.getResult1(),hkInfo.getResult2(),hkInfo.getResult3(),hkInfo.getResult4(),hkInfo.getResult5(),hkInfo.getResult6(),hkInfo.getResult7()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateHistoryResultPeriods时出现错误 "
                    + e.getMessage());
        }
        return SUCCESS;
     }*/
    
    
    public String gdPeriodsInfo(){
        SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
        String beginNumber = ageFormat.format(new Date())+"01";
        String endNumber = ageFormat.format(new Date())+"84";
        try {
            logger.info("手动广东查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
            List<GDPeriodsInfo> gdPeriodsInfos = periodsInfoLogic.queryPeriods(beginNumber,endNumber);
            logger.info("手动查询广东  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
            if(gdPeriodsInfos.size() ==0){
                logger.info("手动生成广东盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
                periodsInfoLogic.saveGDPeriods();
                logger.info("手动生成广东 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
                getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                        + Constant.COLOR_RED + "'>已经生成当天广东所有期数</font>");
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.info("手动广东快乐十分 盘期");
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法executeInternal时出现错误 "
                    + e.getMessage());
        }
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>当天盘期已经存在，不能重复生成</font>");
        return "failure";
        
    }
    
    public String ncPeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	String beginNumber = ageFormat.format(new Date())+"01";
    	String endNumber = ageFormat.format(new Date())+"97";
    	String startDate = getRequest().getParameter("startDate");
    	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");            
        Date date = null;
    	try {
    		logger.info("手动农场查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    		List<NCPeriodsInfo> ncPeriodsInfos = ncPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询农场  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(ncPeriodsInfos.size() ==0){
    			logger.info("手动生成农场盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			if(startDate==""){
    				date = new Date();
    			}else{
    				date = fmt.parse(startDate);
    			}
    			ncPeriodsInfoLogic.saveNCPeriods(date);
    			logger.info("手动生成农场 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经生成当天农场所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动农场 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期已经存在，不能重复生成</font>");
    	return "failure";
    	
    }
    
    public String k3PeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	String beginNumber = ageFormat.format(new Date())+"001";
    	String endNumber = ageFormat.format(new Date())+"082";
    	try {
    		logger.info("手动快3查询盘期开始>>>>>>>>>>>>>>>"+beginNumber+"至"+endNumber);
    		List<JSSBPeriodsInfo> periodsInfos = jssbPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询快3  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() ==0){
    			logger.info("手动生成快3盘期开始>>>>>>>>>>>");
    			jssbPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			jssbPeriodsInfoLogic.saveJSPeriods();
    			logger.info("手动生成快3 盘期结束>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经生成当天快3所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动生成快3 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>谨慎操作！！！当天盘期已经存在，请先删除当天盘期，再手动生成盘期。</font>");
    	return "failure";
    	
    }
    
    public String delK3PeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	String beginNumber = ageFormat.format(new Date())+"001";
    	String endNumber = ageFormat.format(new Date())+"082";
    	try {
    		logger.info("手动删除快3查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    		List<JSSBPeriodsInfo> periodsInfos = jssbPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询快3  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() > 0){
    			logger.info("手动删除快3盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			jssbPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			logger.info("手动删除快3 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经删除当天快3所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动删除快3 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期不存在，不能重复生成</font>");
    	return "failure";
    	
    }
    
    public String delGDPeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	 String beginNumber = ageFormat.format(new Date())+"01";
         String endNumber = ageFormat.format(new Date())+"84";
    	try {
    		logger.info("手动删除广东快乐十分查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    		List<GDPeriodsInfo> periodsInfos = periodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询广东快乐十分  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() > 0){
    			logger.info("手动删除广东快乐十分盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			periodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			logger.info("手动删除农场 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经删除当天广东快乐十分所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动删除农场 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期不存在，不能重复生成</font>");
    	return "failure";
    	
    }
    
    public String delNCPeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	String beginNumber = ageFormat.format(new Date())+"01";
    	String endNumber = ageFormat.format(new Date())+"97";
    	try {
    		logger.info("手动删除农场查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    		List<NCPeriodsInfo> periodsInfos = ncPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询农场  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() > 0){
    			logger.info("手动删除农场盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			ncPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			logger.info("手动删除农场 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经删除当天农场所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动删除农场 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期不存在</font>");
    	return "failure";
    	
    }
    
    public String delCQPeriodsInfo(){
    	SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
    	String beginNumber = ageFormat.format(new Date())+"001";
        String endNumber = ageFormat.format(new Date())+"120";
    	try {
    		logger.info("手动删除重庆时时彩查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    		List<CQPeriodsInfo> periodsInfos = icqPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询重庆时时彩  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() > 0){
    			logger.info("手动删除重庆时时彩盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			icqPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			logger.info("手动删除重庆时时彩 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经删除当天重庆时时彩所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动删除农场 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期不存在，不能重复生成</font>");
    	return "failure";
    	
    }
    
    public String delBjPeriodsEnter(){
    	
    	return SUCCESS;
    }

    
    public String delBJPeriodsInfo(){
    	String beginNumber = getRequest().getParameter("beginNumber");
    	String endNumber = getRequest().getParameter("endNumber");
    	try {
    		logger.info("手动删除北京查询盘期开始>>>>>>>>>>>>从："+beginNumber+"至"+endNumber);
    		List<BJSCPeriodsInfo> periodsInfos = bjscPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
    		logger.info("手动查询北京  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    		if(periodsInfos.size() > 0){
    			logger.info("手动删除北京盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
    			bjscPeriodsInfoLogic.deletePeriods(beginNumber, endNumber);
    			logger.info("手动删除北京 盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
    			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    					+ Constant.COLOR_RED + "'>已经删除当天北京所有期数</font>");
    			return SUCCESS;
    		}
    	} catch (Exception e) {
    		logger.info("手动删除农场 盘期");
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法executeInternal时出现错误 "
    				+ e.getMessage());
    	}
    	getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
    	getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
    			+ Constant.COLOR_RED + "'>当天盘期不存在</font>");
    	return "failure";
    	
    }
    
    public String cqPeriodsInfo(){
        SimpleDateFormat ageFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String beginNumber = ageFormat.format(calendar.getTime())+"001";
        String endNumber = ageFormat.format(calendar.getTime())+"120";
        try {
            
            logger.info("手动重庆查询盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
            List<CQPeriodsInfo> cqPeriodsInfos = icqPeriodsInfoLogic.queryPeriods(beginNumber,endNumber);
            logger.info("手动查询重庆  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
            if(cqPeriodsInfos.size()==0){
                logger.info("手动生成重庆盘期开始>>>>>>>>>>>>>>>>>>>>>>>>");
                icqPeriodsInfoLogic.saveManualCQInterimPeriods();
                icqPeriodsInfoLogic.saveManualCQPeriods();
                icqPeriodsInfoLogic.saveManualCQAfterPeriods();
                logger.info("手动生成重庆  盘期结束>>>>>>>>>>>>>>>>>>>>>>>>");
                getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                        + Constant.COLOR_RED + "'>已经生成当天重庆所有期数</font>");
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.info("手动重庆 盘期");
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法executeInternal时出现错误 "
                    + e.getMessage());
        }
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>当天盘期已经存在，不能重复生成</font>");
        return "failure";
    }
    
    public String bjPeriodsInfoEnter(){
    	
    	return SUCCESS;
    }
    public String ncPeriodsInfoEnter(){
    	
    	return SUCCESS;
    }
    
    public String bjPeriodsInfo(){
    	String startPeriodNum = getRequest().getParameter("period");
    	String startDate = getRequest().getParameter("startDate");
    	String count = getRequest().getParameter("count");
    	if(count==null || count=="")
    		count="179";
    	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");            
        Date date = null;
        int periodCnt = 0;
		try {
			date = fmt.parse(startDate);
			periodCnt = Integer.valueOf(count);
		} catch (ParseException e) {
			date = new Date();
			periodCnt = 179;
			logger.info("错误!因用户手动生成盘期时的数据有误，未生成盘期!");
		}
		try {
			bjscPeriodsInfoLogic.saveBJSCPeriods(startPeriodNum,date,periodCnt);
			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>已经生成当天北京所有期数</font>");			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return SUCCESS;
    }
    
    
    public String updateMemberTotalCreditLine(){
//        List<MemberStaffExt> memberStaffExtList = new ArrayList<MemberStaffExt>();
        String shopCode=this.getRequest().getParameter("shopCode");
        ShopsInfo shopInfo=shopsLogic.findShopsInfoByCode(shopCode);
    	if(null == shopInfo){
    		getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>商铺不存在！</font>");
            return "failure";
    	}
        try {
        	String scheme = shopSchemeService.getSchemeByShopCode(shopCode);
            memberStaffExtLogic.updateMemberAvailableCreditToTotal(scheme);
		} catch (Exception e) {
			logger.error("恢复全部会员信用额度失败>>>"+e);
			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
		    getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
		                + Constant.COLOR_RED + "'>恢复全部会员信用额度失败</font>");
		}
        
//        memberStaffExtList = memberStaffExtLogic.findAllMemeber();
//            for (MemberStaffExt memberStaffExt : memberStaffExtList) {
//                logger.info("改之后的信用额度"+memberStaffExt.getTotalCreditLine()+"可用信用额度"+memberStaffExt.getAvailableCreditLine());
//                memberStaffExt.setAvailableCreditLine(memberStaffExt.getTotalCreditLine());
//                memberStaffExtLogic.updateMemberStaffExt(memberStaffExt);
//                logger.info("改之后的信用额度"+memberStaffExt.getTotalCreditLine()+"可用信用额度"+memberStaffExt.getAvailableCreditLine());
//            } 
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>已经恢复全部会员信用额度了</font>");
        return SUCCESS;
    }
    
    private ManagerUser getInfo() {
        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        return userInfo;
    }
    
    public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
        return skperiodsInfoLogic;
    }

    public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
        this.skperiodsInfoLogic = skperiodsInfoLogic;
    }

    public HKPeriodsInfo getHkperiodsInfos() {
        return hkperiodsInfos;
    }

    public void setHkperiodsInfos(HKPeriodsInfo hkperiodsInfos) {
        this.hkperiodsInfos = hkperiodsInfos;
    }

    public String getPeriodsType() {
        return periodsType;
    }

    public void setPeriodsType(String periodsType) {
        this.periodsType = periodsType;
    }


    public String getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(String lotteryTime) {
        this.lotteryTime = lotteryTime;
    }


    public String getNumPeriods() {
        return numPeriods;
    }

    public void setNumPeriods(String numPeriods) {
        this.numPeriods = numPeriods;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }

    public IHKPeriodsLogic getHkperiodsLogic() {
        return hkperiodsLogic;
    }

    public void setHkperiodsLogic(IHKPeriodsLogic hkperiodsLogic) {
        this.hkperiodsLogic = hkperiodsLogic;
    }

    public HKPeriods getHkPeriods() {
        return hkPeriods;
    }

    public void setHkPeriods(HKPeriods hkPeriods) {
        this.hkPeriods = hkPeriods;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public IShopOddsLogic getShopOddsLogic() {
        return shopOddsLogic;
    }

    public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
        this.shopOddsLogic = shopOddsLogic;
    }


    public IPlayTypeLogic getPlayTypeLogic() {
        return playTypeLogic;
    }

    public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
        this.playTypeLogic = playTypeLogic;
    }
    public String getOpenDate() {
        return openDate;
    }
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }
    public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
        return periodsInfoLogic;
    }
    public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
        this.periodsInfoLogic = periodsInfoLogic;
    }
    public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
        return icqPeriodsInfoLogic;
    }
    public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
        this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
    }
    public IMemberStaffExtLogic getMemberStaffExtLogic() {
        return memberStaffExtLogic;
    }
    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
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
	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}

	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}

	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}
	
	
}
