package com.npc.lottery.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriodsInfo;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.statreport.entity.DetailedReport;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.statreport.entity.ReplenishReport;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;

/**
 * 报表统计Action
 * 
 */
public class BossSRAction extends BaseAction {

    private static Logger log = Logger.getLogger(BossSRAction.class);

    private IHklhcHisLogic hklhcHisLogic = null;//投注历史（香港六合彩）逻辑处理类

    private IGdklsfHisLogic gdklsfHisLogic = null;//投注历史（广东快乐十分）

    private ICqsscHisLogic cqsscHisLogic = null;//投注历史（重庆时时彩）

    private IStatReportLogic statReportLogic = null;//报表统计

    private ISubAccountInfoLogic subAccountInfoLogic;
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
    private IGDPeriodsInfoLogic periodsInfoLogic;
    private IHKPeriodsInfoLogic skperiodsInfoLogic;

    private String sitemeshType = "report"; //用于页面菜单显示

    public void setHklhcHisLogic(IHklhcHisLogic hklhcHisLogic) {
        this.hklhcHisLogic = hklhcHisLogic;
    }

    public void setGdklsfHisLogic(IGdklsfHisLogic gdklsfHisLogic) {
        this.gdklsfHisLogic = gdklsfHisLogic;
    }

    public void setCqsscHisLogic(ICqsscHisLogic cqsscHisLogic) {
        this.cqsscHisLogic = cqsscHisLogic;
    }

    public void setStatReportLogic(IStatReportLogic statReportLogic) {
        this.statReportLogic = statReportLogic;
    }

    /**
     * 输入报表查询条件
     * 
     * @return
     * @throws Exception
     */
    public String statReportSearch() throws Exception {

        log.info("statReportSearch");

        //TODO 待优化，传回3个列表，根据彩票种类改变下注类型的的可选值
        //下注类型列表
        ArrayList<PlayType> playTypeList = statReportLogic
                .findCommissionTypeList();

        request.setAttribute("playTypeList", playTypeList);
        request.setAttribute("playTypeMapGD", Constant.GDKLSF_COMMISSION_TYPE);
        request.setAttribute("playTypeMapCQ", Constant.CQSSC_COMMISSION_TYPE);
        request.setAttribute("playTypeMapHK", Constant.HKLHC_COMMISSION_TYPE);

        //查询彩种期数
        String lotteryType = getRequest().getParameter("lotteryType");
        this.getRequest().setAttribute("selectType", lotteryType);
        if ("HKLHC".equalsIgnoreCase(lotteryType)) {
            List<HKPeriodsInfo> periodsList = skperiodsInfoLogic
                    .queryReportPeriodsNum();
            getRequest().setAttribute("hkPeriodsList", periodsList);
        } else if ("CQSSC".equalsIgnoreCase(lotteryType)) {
            List<CQPeriodsInfo> periodsList = icqPeriodsInfoLogic
                    .queryReportPeriodsNum();
            getRequest().setAttribute("cqPeriodsList", periodsList);
        } else if ("GDKLSF".equalsIgnoreCase(lotteryType)) {
            List<GDPeriodsInfo> periodsList = periodsInfoLogic
                    .queryReportPeriodsNum();
            getRequest().setAttribute("gdPeriodsList", periodsList);
        }
        return "statReportSearch";
    }

    /**
     * 报表列表
     * 
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        //TODO 增加期数的查询条件
        log.info("list");


        //处理时间：凌晨2:00算前一天
        Date bettingDateStartNew = new Date(bettingDateStart.getTime() + 60
                * 60 * 1000 * 2);
        Date bettingDateEndNew = new Date(bettingDateEnd.getTime() + 60 * 60
                * 1000 * 26);

        //根据投注类型解析查询所需要的typeCode
        ArrayList<PlayType> typeCodeList = statReportLogic
                .findPlayTypeByCommission(playType);
        //构造typeCode查询条件('a','b')类型的查询条件
        StringBuffer typeCode = new StringBuffer();
        if (null != typeCodeList && typeCodeList.size() > 0) {
            for (int i = 0; i < typeCodeList.size(); i++) {
                if (0 != i) {
                    typeCode.append(", ");
                }
                typeCode.append("'" + typeCodeList.get(i).getTypeCode() + "'");
            }
        }

        ArrayList<DeliveryReport> resultList = statReportLogic
                .findDeliveryReportList(detailUserID, lotteryType,
                        typeCode.toString(), periodsNum, bettingDateStartNew,
                        bettingDateEndNew, detailUserType);

        DeliveryReport totalEntity = null;
        DeliveryReport totalEntitySec = null;//统计值（含补货）
        ReplenishReport replenishEntity = null;
        DeliveryReport deliveryReport = null;
        if (null != resultList && resultList.size() > 0) {

            //取出补货数据，代理的补货数据对应的用户类型为 g，由于存储过程中补货数据放置在最后，故从最后数据开始循环效率更高
            //取出最后的合计数据，代理的合计数据对应的用户类型为G
            //c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
            for (int i = resultList.size() - 1; i > -1; i--) {
                if ("g".equals(resultList.get(i).getUserType())
                        || "f".equals(resultList.get(i).getUserType())
                        || "e".equals(resultList.get(i).getUserType())
                        || "d".equals(resultList.get(i).getUserType())
                        || "c".equals(resultList.get(i).getUserType())) {
                    deliveryReport = resultList.remove(i);

                    //构造补货数据对象
                    replenishEntity = new ReplenishReport();
                    replenishEntity.setReplenishUserId(deliveryReport
                            .getUserID());//补货人
                    replenishEntity.setTurnover(deliveryReport.getTurnover());//笔数
                    replenishEntity.setReplenishAmount(deliveryReport
                            .getAmount());//补货金额
                    replenishEntity.setReplenishValidAmount(deliveryReport
                            .getValidAmount());//有效金额
                    replenishEntity.setReplenishWin(deliveryReport
                            .getMemberAmount());//补货输赢
                    replenishEntity.setBackWater(deliveryReport
                            .getMemberBackWater());//退水
                    replenishEntity.setBackWaterResult(deliveryReport
                            .getMemberBackWaterResult());//退水后结果
                    //设置补货对象的用户类型
                    replenishEntity.setUserType(detailUserType);

                    continue;
                }
                //C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
                if ("G".equals(resultList.get(i).getUserType())
                        || "F".equals(resultList.get(i).getUserType())
                        || "E".equals(resultList.get(i).getUserType())
                        || "D".equals(resultList.get(i).getUserType())
                        || "C".equals(resultList.get(i).getUserType())) {
                    //合计记录数据
                    totalEntity = resultList.remove(i);
                    continue;
                }
            }
        }

        //如果补货对象不为空，则合计数据里面去除补货数据
        //填充含补货的合计数据
        if (null != replenishEntity && null != totalEntity) {
            //成交笔数
            totalEntity.setTurnover(totalEntity.getTurnover()
                    - replenishEntity.getTurnover());
            //投注总额
            totalEntity.setAmount(totalEntity.getAmount()
                    - replenishEntity.getReplenishAmount());
            //有效金额
            totalEntity.setValidAmount(totalEntity.getValidAmount()
                    - replenishEntity.getReplenishValidAmount());
            //会员输赢
            totalEntity.setMemberAmount(totalEntity.getMemberAmount()
                    - replenishEntity.getReplenishWin());
            //会员退水
            totalEntity.setMemberBackWater(totalEntity.getMemberBackWater()
                    - replenishEntity.getBackWater());
            //赚水后结果
            totalEntity.setWinBackWaterResult(totalEntity
                    .getWinBackWaterResult()
                    - replenishEntity.getBackWaterResult());

            //含补货的合计数据，其中，实占结果=合计实占结果，赚取退水=合计赚取退水
            totalEntitySec = totalEntity.clone();
            //抵扣补货及赚水后结果 = 合计赚取退水 - 补货退水后结果
            totalEntitySec.setWinBackWaterReple(totalEntity
                    .getWinBackWaterResult()
                    - replenishEntity.getBackWaterResult());
            //应付上级 = 合计上级应付 + 补货退水后结果
            totalEntitySec.setPaySuperior(totalEntity.getPaySuperior()
                    + replenishEntity.getBackWaterResult());
        }

        //查询补货信息
        //ReplenishReport replenishEntity = statReportLogic.findReplenish(user
        //        .getID());

        request.setAttribute("resultList", resultList);
        request.setAttribute("totalEntity", totalEntity);
        request.setAttribute("totalEntitySec", totalEntitySec);
        request.setAttribute("replenishEntity", replenishEntity);
        request.setAttribute(
                "reportInfo",
                DeliveryReport.getUserTypeName(detailUserType) + "（"
                        + detailUserID + "）交收报表");

        //判断是否是总监
        if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(detailUserType)) {
            return "chiefList";
        } else {
            return "list";
        }
    }

    /**
     * 明细报表信息
     * 
     * @return
     * @throws Exception
     */
    public String detailedReport() throws Exception {

        log.info("detailedReport");

        //处理时间：凌晨2:00算前一天
        Date bettingDateStartNew = new Date(bettingDateStart.getTime() + 60
                * 60 * 1000 * 2);
        Date bettingDateEndNew = new Date(bettingDateEnd.getTime() + 60 * 60
                * 1000 * 26);

        //根据投注类型解析查询所需要的typeCode
        ArrayList<PlayType> typeCodeList = statReportLogic
                .findPlayTypeByCommission(playType);
        //构造typeCode查询条件('a','b')类型的查询条件
        StringBuffer typeCode = new StringBuffer();
        if (null != typeCodeList && typeCodeList.size() > 0) {
            for (int i = 0; i < typeCodeList.size(); i++) {
                if (0 != i) {
                    typeCode.append(", ");
                }
                typeCode.append("'" + typeCodeList.get(i).getTypeCode() + "'");
            }
        }

        ArrayList<DeliveryReport> resultList = statReportLogic
                .findDeliveryReportList(detailUserID, lotteryType,
                        typeCode.toString(), periodsNum, bettingDateStartNew,
                        bettingDateEndNew, detailUserType);

        DeliveryReport totalEntity = null;
        DeliveryReport totalEntitySec = null;//统计值（含补货）
        ReplenishReport replenishEntity = null;
        DeliveryReport deliveryReport = null;
        if (null != resultList && resultList.size() > 0) {

            //取出补货数据，代理的补货数据对应的用户类型为 g，由于存储过程中补货数据放置在最后，故从最后数据开始循环效率更高
            //取出最后的合计数据，代理的合计数据对应的用户类型为G
            //c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
            for (int i = resultList.size() - 1; i > -1; i--) {
                if ("g".equals(resultList.get(i).getUserType())
                        || "f".equals(resultList.get(i).getUserType())
                        || "e".equals(resultList.get(i).getUserType())
                        || "d".equals(resultList.get(i).getUserType())
                        || "c".equals(resultList.get(i).getUserType())) {
                    deliveryReport = resultList.remove(i);

                    //构造补货数据对象
                    replenishEntity = new ReplenishReport();
                    replenishEntity.setReplenishUserId(deliveryReport
                            .getUserID());//补货人
                    replenishEntity.setTurnover(deliveryReport.getTurnover());//笔数
                    replenishEntity.setReplenishAmount(deliveryReport
                            .getAmount());//补货金额
                    replenishEntity.setReplenishValidAmount(deliveryReport
                            .getValidAmount());//有效金额
                    replenishEntity.setReplenishWin(deliveryReport
                            .getMemberAmount());//补货输赢
                    replenishEntity.setBackWater(deliveryReport
                            .getMemberBackWater());//退水
                    replenishEntity.setBackWaterResult(deliveryReport
                            .getMemberBackWaterResult());//退水后结果
                    //设置补货对象的用户类型
                    replenishEntity.setUserType(detailUserType);
                    continue;
                }
                //C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
                if ("G".equals(resultList.get(i).getUserType())
                        || "F".equals(resultList.get(i).getUserType())
                        || "E".equals(resultList.get(i).getUserType())
                        || "D".equals(resultList.get(i).getUserType())
                        || "C".equals(resultList.get(i).getUserType())) {
                    //合计记录数据
                    totalEntity = resultList.remove(i);
                    continue;
                }
            }
        }

        //如果补货对象不为空，则合计数据里面去除补货数据
        if (null != replenishEntity) {
            //成交笔数
            totalEntity.setTurnover(totalEntity.getTurnover()
                    - replenishEntity.getTurnover());
            //投注总额
            totalEntity.setAmount(totalEntity.getAmount()
                    - replenishEntity.getReplenishAmount());
            //有效金额
            totalEntity.setValidAmount(totalEntity.getValidAmount()
                    - replenishEntity.getReplenishValidAmount());
            //会员输赢
            totalEntity.setMemberAmount(totalEntity.getMemberAmount()
                    - replenishEntity.getReplenishWin());
            //会员退水
            totalEntity.setMemberBackWater(totalEntity.getMemberBackWater()
                    - replenishEntity.getBackWater());
            //赚水后结果
            totalEntity.setWinBackWaterResult(totalEntity
                    .getWinBackWaterResult()
                    - replenishEntity.getBackWaterResult());

            //含补货的合计数据，其中，实占结果=合计实占结果，赚取退水=合计赚取退水
            totalEntitySec = totalEntity.clone();
            //抵扣补货及赚水后结果 = 合计赚取退水 - 补货退水后结果
            totalEntitySec.setWinBackWaterReple(totalEntity
                    .getWinBackWaterResult()
                    - replenishEntity.getBackWaterResult());
            //应付上级 = 合计上级应付 + 补货退水后结果
            totalEntitySec.setPaySuperior(totalEntity.getPaySuperior()
                    + replenishEntity.getBackWaterResult());
        }

        request.setAttribute("resultList", resultList);
        request.setAttribute("totalEntity", totalEntity);
        request.setAttribute("totalEntitySec", totalEntitySec);
        request.setAttribute("replenishEntity", replenishEntity);
        request.setAttribute("reportInfo",
                DeliveryReport.getUserTypeName(detailUserType) + "（"
                        + detailUserAccount + "）交收报表");

        return "detailedReport";
    }

    /**
     * 投注会员明细信息
     * 
     * @return
     * @throws Exception
     */
    public String detailedList() throws Exception {

        log.info("detailedList");

        //处理时间：凌晨2:00算前一天
        Date bettingDateStartNew = new Date(bettingDateStart.getTime() + 60
                * 60 * 1000 * 2);
        Date bettingDateEndNew = new Date(bettingDateEnd.getTime() + 60 * 60
                * 1000 * 26);
        //格式化需要的时间
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.MEDIUM, Locale.CHINA);
        String startDateStr = df.format(bettingDateStartNew);
        String endDateStr = df.format(bettingDateEndNew);

        //构造sql查询语句
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("betting_user_id = " + bettingUserID);
        sqlStr.append(" AND BETTING_DATE BETWEEN to_date('" + startDateStr
                + "','yyyy-mm-dd hh24:mi:ss') AND to_date('" + endDateStr
                + "','yyyy-mm-dd hh24:mi:ss')");
        //根据投注类型解析查询所需要的typeCode
        ArrayList<PlayType> typeCodeList = statReportLogic
                .findPlayTypeByCommission(playType);
        //构造typeCode查询条件('a','b')类型的查询条件
        StringBuffer typeCode = new StringBuffer();
        if (null != typeCodeList && typeCodeList.size() > 0) {
            for (int i = 0; i < typeCodeList.size(); i++) {
                if (0 != i) {
                    typeCode.append(", ");
                }
                typeCode.append("'" + typeCodeList.get(i).getTypeCode() + "'");
            }
        }
        if (typeCode.length() > 0) {
            sqlStr.append(" AND TYPE_CODE IN (" + typeCode.toString() + ")");
        }

        ArrayList<DetailedReport> resultList = new ArrayList<DetailedReport>();

        DetailedReport detailedReportEntity = null;
        if ("ALL".equalsIgnoreCase(lotteryType)
                || "GDKLSF".equalsIgnoreCase(lotteryType)) {
            //广东快乐十分
            ArrayList<GdklsfHis> gdklsfHisList = gdklsfHisLogic
                    .findGdklsfHis(sqlStr.toString());
            if (null != gdklsfHisList && gdklsfHisList.size() > 0) {
                GdklsfHis gdklsfHisEntity;
                for (int i = 0; i < gdklsfHisList.size(); i++) {
                    gdklsfHisEntity = gdklsfHisList.get(i);//广东快乐十分历史数据对象
                    detailedReportEntity = new DetailedReport(gdklsfHisEntity);//报表明细对象
                    //保存数据
                    resultList.add(detailedReportEntity);
                }
            }

        }

        if ("ALL".equalsIgnoreCase(lotteryType)
                || "CQSSC".equalsIgnoreCase(lotteryType)) {
            //重庆时时彩
            ArrayList<CqsscHis> cqsscHisList = cqsscHisLogic
                    .findCqsscHis(sqlStr.toString());
            if (null != cqsscHisList && cqsscHisList.size() > 0) {
                CqsscHis cqsscHisEntity;
                for (int i = 0; i < cqsscHisList.size(); i++) {
                    cqsscHisEntity = cqsscHisList.get(i);//重庆时时彩历史数据对象
                    detailedReportEntity = new DetailedReport(cqsscHisEntity);//报表明细对象
                    //保存数据
                    resultList.add(detailedReportEntity);
                }
            }
        }

        if ("ALL".equalsIgnoreCase(lotteryType)
                || "HKLHC".equalsIgnoreCase(lotteryType)) {
            //香港六合彩
            ArrayList<HklhcHis> hklhcHisList = hklhcHisLogic
                    .findHklhcHis(sqlStr.toString());
            if (null != hklhcHisList && hklhcHisList.size() > 0) {
                HklhcHis hklhcHisEntity;
                for (int i = 0; i < hklhcHisList.size(); i++) {
                    hklhcHisEntity = hklhcHisList.get(i);//香港六合彩历史数据对象
                    detailedReportEntity = new DetailedReport(hklhcHisEntity);//报表明细对象
                    //保存数据
                    resultList.add(detailedReportEntity);
                }
            }
        }

        request.setAttribute("resultList", resultList);
        request.setAttribute("reportInfo", "会员（" + detailUserAccount
                + "）交收报表的明细信息");

        return "detailedList";
    }

    /**
     * 补货明细信息
     * 
     * @return
     * @throws Exception
     */
    public String replenishDetailedList() throws Exception {

        log.info("replenishDetailedList");
        
        //处理时间：凌晨2:00算前一天
        Date bettingDateStartNew = new Date(bettingDateStart.getTime() + 60
                * 60 * 1000 * 2);
        Date bettingDateEndNew = new Date(bettingDateEnd.getTime() + 60 * 60
                * 1000 * 26);

        //构造查询条件
        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("replenishUserId", replenishUserId);//补货人ID
        conditionData.addBetween("bettingDate", bettingDateStartNew, true, bettingDateEndNew, true);
        //状态查询条件
        conditionData.addIn("winState", Replenish.WIN_STATE_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_NOT_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_PRIZE);
        
        ArrayList<Replenish> replenishList = statReportLogic
                .findReplenishList(conditionData);

        //设置补货对象的用户类型
        if (null != replenishList && replenishList.size() > 0) {
            for (int i = 0; i < replenishList.size(); i++) {
                replenishList.get(i).setReplenishUserType(detailUserType);
            }
        }

        request.setAttribute("replenishList", replenishList);
        request.setAttribute("reportInfo", "补货明细信息");

        return "replenishDetailedList";
    }

    public String queryPetReport() {

        request.setAttribute("type", "privateAdmin");
        return "success";
    }

    private Long bettingUserID;//投注会员ID

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息
    
    private String timePeriod;//期数、时间

    private Date bettingDateStart;//开始时间

    private Date bettingDateEnd;//结束时间

    private String reportType;//报表类型

    private GdklsfHis gdklsfHisEntity;//广东快乐十分历史投注信息

    private Long replenishUserId;//补货人ID

    private String detailUserType;//明细用户类型

    private Long detailUserID;//明细页面对应的用户ID

    private String detailUserAccount;//明细页面对应的用户账号
    
    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

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

    public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
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

    public void setBettingDateEnd(Date bettingDateEnd) {
        this.bettingDateEnd = bettingDateEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
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

    public Long getReplenishUserId() {
        return replenishUserId;
    }

    public void setReplenishUserId(Long replenishUserId) {
        this.replenishUserId = replenishUserId;
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

    public IStatReportLogic getStatReportLogic() {
        return statReportLogic;
    }

    public IAuthorizLogic getAuthorizLogic() {
        return authorizLogic;
    }

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public String getClassifyReport() {
        return classifyReport;
    }

    public void setClassifyReport(String classifyReport) {
        this.classifyReport = classifyReport;
    }

    public Map<String, String> getAutoSub(SubAccountInfo subAccountInfo) {
        Map<String, String> autoSubMap = new HashMap<String, String>();
        if (subAccountInfo != null) {
            List<String> authoriz = new ArrayList<String>();
            authoriz = authorizLogic.findSubRole(subAccountInfo.getID(),
                    subAccountInfo.getUserType());
            if (ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo
                    .getParentUserType())) {
                for (String authority : authoriz) {
                    if (ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(authority)) {
                        replenishment = authority;
                        autoSubMap.put("replenishment", replenishment);
                    } else {
                        autoSubMap.put("replenishment", replenishment);
                    }
                    if (ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(authority)) {
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount", offLineAccount);
                    } else {
                        autoSubMap.put("offLineAccount", offLineAccount);
                    }
                    if (ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(authority)) {
                        subAccount = authority;
                        autoSubMap.put("subAccount", subAccount);
                    } else {
                        autoSubMap.put("subAccount", subAccount);
                    }
                    if (ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(authority)) {
                        crossReport = authority;
                        autoSubMap.put("crossReport", crossReport);
                    } else {
                        autoSubMap.put("crossReport", crossReport);
                    }
                    if (ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(authority)) {
                        classifyReport = authority;
                        autoSubMap.put("classifyReport", classifyReport);
                    } else {
                        autoSubMap.put("classifyReport", classifyReport);
                    }
                }
            } else if (ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo
                    .getParentUserType())) {
                for (String authority : authoriz) {
                    if (ManagerStaff.BRANCH_SUB_ROLE_REPLENISH
                            .equals(authority)) {
                        replenishment = authority;
                        autoSubMap.put("replenishment", replenishment);
                    }
                    if (ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(authority)) {
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount", offLineAccount);
                    }
                    if (ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(authority)) {
                        subAccount = authority;
                        autoSubMap.put("subAccount", subAccount);
                    }
                    if (ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(authority)) {
                        crossReport = authority;
                        autoSubMap.put("crossReport", crossReport);
                    }
                    if (ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(authority)) {
                        classifyReport = authority;
                        autoSubMap.put("classifyReport", classifyReport);
                    }
                }

            } else if (ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo
                    .getParentUserType())) {
                for (String authority : authoriz) {
                    if (ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH
                            .equals(authority)) {
                        replenishment = authority;
                        autoSubMap.put("replenishment", replenishment);
                    } else {
                        autoSubMap.put("replenishment", replenishment);
                    }
                    if (ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE
                            .equals(authority)) {
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount", offLineAccount);
                    } else {
                        autoSubMap.put("offLineAccount", offLineAccount);
                    }
                    if (ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(authority)) {
                        subAccount = authority;
                        autoSubMap.put("subAccount", subAccount);
                    } else {
                        autoSubMap.put("subAccount", subAccount);
                    }
                    if (ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY
                            .equals(authority)) {
                        crossReport = authority;
                        autoSubMap.put("crossReport", crossReport);
                    } else {
                        autoSubMap.put("crossReport", crossReport);
                    }
                    if (ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY
                            .equals(authority)) {
                        classifyReport = authority;
                        autoSubMap.put("classifyReport", classifyReport);
                    } else {
                        autoSubMap.put("classifyReport", classifyReport);
                    }
                }

            } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo
                    .getParentUserType())) {
                for (String authority : authoriz) {
                    if (ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH
                            .equals(authority)) {
                        replenishment = authority;
                        autoSubMap.put("replenishment", replenishment);
                    } else {
                        autoSubMap.put("replenishment", replenishment);
                    }
                    if (ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE
                            .equals(authority)) {
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount", offLineAccount);
                    } else {
                        autoSubMap.put("offLineAccount", offLineAccount);
                    }
                    if (ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(authority)) {
                        subAccount = authority;
                        autoSubMap.put("subAccount", subAccount);
                    } else {
                        autoSubMap.put("subAccount", subAccount);
                    }
                    if (ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY
                            .equals(authority)) {
                        crossReport = authority;
                        autoSubMap.put("crossReport", crossReport);
                    } else {
                        autoSubMap.put("crossReport", crossReport);
                    }
                    if (ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY
                            .equals(authority)) {
                        classifyReport = authority;
                        autoSubMap.put("classifyReport", classifyReport);
                    } else {
                        autoSubMap.put("classifyReport", classifyReport);
                    }
                }

            } else if (ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo
                    .getParentUserType())) {
                for (String authority : authoriz) {
                    if (ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(authority)) {
                        replenishment = authority;
                        autoSubMap.put("replenishment", replenishment);
                    } else {
                        autoSubMap.put("replenishment", replenishment);
                    }
                    if (ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(authority)) {
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount", offLineAccount);
                    } else {
                        autoSubMap.put("offLineAccount", offLineAccount);
                    }
                    if (ManagerStaff.AGENT_SUB_ROLE_SUB.equals(authority)) {
                        subAccount = authority;
                        autoSubMap.put("subAccount", subAccount);
                    } else {
                        autoSubMap.put("subAccount", subAccount);
                    }
                    if (ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(authority)) {
                        crossReport = authority;
                        autoSubMap.put("crossReport", crossReport);
                    } else {
                        autoSubMap.put("crossReport", crossReport);
                    }
                    if (ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(authority)) {
                        classifyReport = authority;
                        autoSubMap.put("classifyReport", classifyReport);
                    } else {
                        autoSubMap.put("classifyReport", classifyReport);
                    }
                }
            }
        } else {
            autoSubMap.put("replenishment", replenishment);
            autoSubMap.put("offLineAccount", offLineAccount);
            autoSubMap.put("subAccount", subAccount);
            autoSubMap.put("crossReport", crossReport);
            autoSubMap.put("classifyReport", classifyReport);
        }
        return autoSubMap;
    }

    public String getSitemeshType() {
        return sitemeshType;
    }

    public void setSitemeshType(String sitemeshType) {
        this.sitemeshType = sitemeshType;
    }

    public String getDetailUserType() {
        return detailUserType;
    }

    public void setDetailUserType(String detailUserType) {
        this.detailUserType = detailUserType;
    }

    public Long getDetailUserID() {
        return detailUserID;
    }

    public void setDetailUserID(Long detailUserID) {
        this.detailUserID = detailUserID;
    }

    public String getDetailUserAccount() {
        return detailUserAccount;
    }

    public void setDetailUserAccount(String detailUserAccount) {
        this.detailUserAccount = detailUserAccount;
    }

    public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
        return icqPeriodsInfoLogic;
    }

    public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
        this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
    }

    public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
        return periodsInfoLogic;
    }

    public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
        this.periodsInfoLogic = periodsInfoLogic;
    }

    public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
        return skperiodsInfoLogic;
    }

    public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
        this.skperiodsInfoLogic = skperiodsInfoLogic;
    }
}
