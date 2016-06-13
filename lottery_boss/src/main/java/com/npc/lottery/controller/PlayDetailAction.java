package com.npc.lottery.controller;


import java.sql.Date;

import com.npc.lottery.common.action.BetAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.statreport.logic.interf.IUnsettledReportLogic;
import com.npc.lottery.sysmge.entity.ManagerUser;

public class PlayDetailAction extends BetAction {
	
	private static final long serialVersionUID = 1L;
	private String type="PlayDetail";
	private String subType="GDKLSF";
	private IUnsettledReportLogic unsettledReportLogic;
	private String sitemeshType = "report"; //用于页面菜单显示
	 
	public String enter()
	{
		ManagerUser currentUserInfo = (ManagerUser) getRequest().getSession().getAttribute(
                Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        
		Page<BaseBet> page = new Page<BaseBet>(15);
		int pageNo=1;
		
		if(this.getRequest().getParameter("pageNo")!=null)
			pageNo=this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		String vPlayType = null;
		
		if(lotteryType.indexOf("GDKLSF")!=-1)
		{
			if ("GD".equals(playType)){
				vPlayType = playType + "%";
			}
			page=unsettledReportLogic.queryGDKLSFUserBet(page, bettingDateStart, bettingDateEnd, detailUserID, detailUserType, vPlayType, periodsNum);
			this.getRequest().setAttribute("page", page);
			return "playDetail";
			
		}
		if(lotteryType.indexOf("CQSSC")!=-1)
		{
			if ("CQ".equals(playType)){
				vPlayType = playType + "%";
			}
			page=unsettledReportLogic.queryCQSSCUserBet(page, bettingDateStart, bettingDateEnd, detailUserID, detailUserType, vPlayType, periodsNum);
			this.getRequest().setAttribute("page", page);
			return "playDetail";
	    }
		
		return "playDetail";
	}
	
    private Long bettingUserID;//投注会员ID

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息

    private Date bettingDateStart;//开始时间

    private Date bettingDateEnd;//结束时间

    private String reportType;//报表类型

    private Long detailUserID;
    
    private String detailUserType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public IUnsettledReportLogic getUnsettledReportLogic() {
		return unsettledReportLogic;
	}

	public void setUnsettledReportLogic(IUnsettledReportLogic unsettledReportLogic) {
		this.unsettledReportLogic = unsettledReportLogic;
	}

	public Long getBettingUserID() {
		return bettingUserID;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public String getPlayType() {
		return playType;
	}

	public String getTypePeriod() {
		return typePeriod;
	}

	public String getTypeTime() {
		return typeTime;
	}

	public String getPeriodsNum() {
		return periodsNum;
	}

	public Date getBettingDateStart() {
		return bettingDateStart;
	}

	public Date getBettingDateEnd() {
		return bettingDateEnd;
	}

	public String getReportType() {
		return reportType;
	}

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	public void setBettingUserID(Long bettingUserID) {
		this.bettingUserID = bettingUserID;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public void setTypePeriod(String typePeriod) {
		this.typePeriod = typePeriod;
	}

	public void setTypeTime(String typeTime) {
		this.typeTime = typeTime;
	}

	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}

	public void setBettingDateStart(Date bettingDateStart) {
		this.bettingDateStart = bettingDateStart;
	}

	public void setBettingDateEnd(Date bettingDateEnd) {
		this.bettingDateEnd = bettingDateEnd;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Long getDetailUserID() {
		return detailUserID;
	}

	public String getDetailUserType() {
		return detailUserType;
	}

	public void setDetailUserID(Long detailUserID) {
		this.detailUserID = detailUserID;
	}

	public void setDetailUserType(String detailUserType) {
		this.detailUserType = detailUserType;
	}

}
