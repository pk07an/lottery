package com.npc.lottery.statreport.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.entity.BjscHis;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.statreport.entity.DetailedReport;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.statreport.entity.ReplenishReport;
import com.npc.lottery.statreport.logic.interf.IBjscHisLogic;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.SelectBox;

/**
 * 报表统计Action
 * 
 */
public class StatReportAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(StatReportAction.class);

    private IStatReportLogic statReportLogic = null;//报表统计

    private IBetLogic betLogic;//查询期数

    private ISubAccountInfoLogic subAccountInfoLogic;
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表

    private String sitemeshType = "report"; //用于页面菜单显示

    public void setStatReportLogic(IStatReportLogic statReportLogic) {
        this.statReportLogic = statReportLogic;
    }

    public void setBetLogic(IBetLogic betLogic) {
        this.betLogic = betLogic;
    }

    /**
     * 输入报表查询条件
     * 
     * @return
     * @throws Exception
     */
    public String statReportSearch() throws Exception {

        log.info("statReportSearch");
        ManagerUser userInfo = (ManagerUser) request.getSession().getAttribute(
                Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",
                userInfo.getAccount());
        Map<String, String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo", subAccountInfo);
        getRequest().setAttribute("replenishment",
                autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",
                autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount", autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport", autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",
                autoSubMap.get("classifyReport"));

        //TODO 待优化，传回3个列表，根据彩票种类改变下注类型的的可选值
        //下注类型列表
        ArrayList<PlayType> playTypeList = statReportLogic
                .findCommissionTypeList();

        request.setAttribute("playTypeList", playTypeList);

        //查询彩种期数
        String lotteryType = getRequest().getParameter("lotteryType");
        this.getRequest().setAttribute("selectType", lotteryType);
        List<SelectBox> allPeriodsList = null;
        allPeriodsList = new ArrayList<SelectBox>();
        //TODO 取所有玩法的盘期，可能有效率问题，暂时注释掉，后续测试
        allPeriodsList = this.getPeriodsNumList();
        request.setAttribute("allPeriodsList", allPeriodsList);

        //查询报表历史范围
        Map<String, Date> reportScope = statReportLogic.parseReportScope();
        String reportScopeStr = "";
        if (null != reportScope) {
        	if(null!=reportScope.get("start") && null!=reportScope.get("end")){
	            reportScopeStr = reportScope.get("start") + " — "
	                    + reportScope.get("end");
        	}else{
        		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        		reportScopeStr = 
        				df.format(System.currentTimeMillis()) + " — "
	                    + df.format(System.currentTimeMillis());
        	}
        }
        //String dd = "2013-08-16 08:00";
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //java.util.Date datedd=dateformat.parse(dd);
        String date=dateformat.format(new java.util.Date());
        //String date=dateformat.format(datedd);
        request.setAttribute("nowDate", date);
        request.setAttribute("reportScopeStr", reportScopeStr);

        return "statReportSearch";
    }

    public List<SelectBox> getPeriodsNumList() throws IllegalArgumentException {
        List<PeriodsNumVo> list = betLogic.queryPeriodsAllOrderTime();
        List<SelectBox> listPeriods = new ArrayList<SelectBox>();
        Integer count = 10; //下拉列表顯示多少行
        for (PeriodsNumVo ent : list) {
            listPeriods.add(new SelectBox(ent.getPeriodsNum1(), ent
                    .getPeriodsNum2()));
        }
        return listPeriods;
    }

    public String queryPetReport() {

        request.setAttribute("type", "privateAdmin");
        return "success";
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

}
