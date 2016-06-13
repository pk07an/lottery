package com.npc.lottery.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.statreport.entity.DeliveryUnReport;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.logic.interf.IUnsettledReportLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 报表统计Action
 * 
 */
public class BossUSRAction extends BaseAction {

    private static Logger log = Logger.getLogger(BossUSRAction.class);

    private IUnsettledReportLogic unsettledReportLogic = null;//报表统计
    
    private IReplenishLogic replenishLogic = null;
    private ISubAccountInfoLogic subAccountInfoLogic;//子账号

    private String type;
    private String isUp = "false"; //是否是上级往下级查询,默认为否
    private String sitemeshType = "report"; //用于页面菜单显示

    //进入交收报表未结算报表-----------START
    /**
     * 报表列表
     * 
     * @return
     * @throws Exception
     */
    public String unsettledList() throws Exception {

        /*
         * userID为空即用户直接查询，如果当前查询者是子帐号查询的，还要查询子帐号的所属者，把该所属者的userID和userType赋于查询变量
         * userID不为空即从上级往下查下级的，如果当前查询者是子帐号，同上。
         * 
         */
        String periodsNum = null;
        String vPlayType = null;
        //如果没有选择小类就直接查全部，
        if(("ALL").equals(playType) || ("").equals(playType)  || playType==null){
        	if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){playType = "GD";}
        	if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){playType = "CQ";}
        	if(Constant.LOTTERY_TYPE_HKLHC.equals(lotteryType)){playType = "HK";}
        	vPlayType = playType + "%";
        }
        if ("HK".equals(playType) ||"GD".equals(playType) || "CQ".equals(playType)){
			vPlayType = playType + "%";
		}
        if("ALL".indexOf(playType)!=-1){
        	vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
        String[] scanTableList = new String[]{};
		if(playType.indexOf("GD")!=-1){
			scanTableList = Constant.GDKLSF_TABLE_LIST;
		}else if(playType.indexOf("CQ")!=-1){
			scanTableList = Constant.CQSSC_TABLE_LIST;
		}else if(playType.indexOf("BJ")!=-1){
			scanTableList = Constant.BJSC_TABLE_LIST;
		}else if(playType.indexOf("ALL")!=-1){
			scanTableList = Constant.ALL_TABLE_LIST;
		}
        List<DeliveryUnReport> resultList = unsettledReportLogic
                .findUnSettledReport(bettingDateStart, bettingDateEnd, lotteryType, vPlayType, periodsNum, detailUserID, detailUserType,scanTableList);

        //查询补货信息
        DeliveryUnReport replenishEntity =  null;
        periodsNum = null;
        List<DeliveryUnReport> list = unsettledReportLogic.queryReplenish(bettingDateStart, bettingDateEnd,detailUserID,detailUserType,vPlayType, periodsNum,lotteryType);
        
        if(!list.isEmpty()){
        	replenishEntity= new DeliveryUnReport();
        	replenishEntity = (DeliveryUnReport) list.get(0);
        	replenishEntity.setSubordinate("补货");
        	replenishEntity.setUserName("补货");
        }
        
        DeliveryUnReport totalEntity = null;
        if (null != resultList && resultList.size() > 0) {
        	totalEntity = new DeliveryUnReport();
        	Long turnover = 0L;
        	Double amount = (double) 0;
            for(DeliveryUnReport vo : resultList){
            	turnover += vo.getTurnover();
            	amount += vo.getAmount();
            }
            if(isUp.equals("true")){
	            if(!list.isEmpty()){
	            	turnover = turnover + replenishEntity.getTurnover();
	            	amount = amount + replenishEntity.getAmount();
	            }
            }
            totalEntity.setTurnover(turnover);
            totalEntity.setAmount(amount);
        }
        
        request.setAttribute("resultList", resultList);
        request.setAttribute("totalEntity", totalEntity);
        request.setAttribute("replenishEntity", replenishEntity);

        return "list";
    }

    /**
     * 补货会员明细信息
     * 
     * @return
     * @throws Exception
     */
    public String unsettledRelenishDetailed() throws Exception {

        log.info("unsettledRelenishDetailed");
        
        //转换为凌晨2点半的问题
    	Date sqlDateStart =new Date(bettingDateStart.getTime() + 60 * 60 * 1000 * 2);
    	Date sqlDateEnd = new Date(bettingDateEnd.getTime() + 60 * 60 * 1000 * 26);
        
        Long replenishUserId = Long.valueOf(getRequest().getParameter("detailUserID"));
        
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("replenishUserId",replenishUserId));
		filtersPlayType.add(Restrictions.like("commissionType",playType));
		filtersPlayType.add(Restrictions.ne("winState",Replenish.WIN_STATE_WIN));
		filtersPlayType.add(Restrictions.ne("winState",Replenish.WIN_STATE_NOT_WIN));
		filtersPlayType.add(Restrictions.ne("winState",Replenish.WIN_STATE_PRIZE));
		filtersPlayType.add(Restrictions.between("bettingDate", sqlDateStart, sqlDateEnd));
		List<Replenish> ptList = replenishLogic.findReplenish(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		request.setAttribute("gdklsfHisList", ptList);
		Collections.sort(ptList);
        return "detailedList";
    }
    
    //交收报表--未结算报表END

    private Long bettingUserID;//投注会员ID

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息

    private Date bettingDateStart;//开始时间

    private Date bettingDateEnd;//结束时间

    private String reportType;//报表类型

    private GdklsfHis gdklsfHisEntity;//广东快乐十分历史投注信息
    
    private String detailUserType;//明细用户类型

    private Long detailUserID;//明细页面对应的用户ID
    

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

    public void setUnsettledReportLogic(IUnsettledReportLogic unsettledReportLogic) {
		this.unsettledReportLogic = unsettledReportLogic;
	}

	public IUnsettledReportLogic getUnsettledReportLogic() {
		return unsettledReportLogic;
	}

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }

    public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	}

	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
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

	public String getDetailUserType() {
		return detailUserType;
	}

	public Long getDetailUserID() {
		return detailUserID;
	}

	public void setDetailUserType(String detailUserType) {
		this.detailUserType = detailUserType;
	}

	public void setDetailUserID(Long detailUserID) {
		this.detailUserID = detailUserID;
	}

}
