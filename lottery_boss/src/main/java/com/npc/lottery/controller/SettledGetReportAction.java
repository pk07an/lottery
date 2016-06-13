package com.npc.lottery.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.logic.interf.ILotteryResultLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;

/**
 * 报表统计Action
 * 
 */
public class SettledGetReportAction extends BaseLotteryAction {

	private static final long serialVersionUID = -6763143609272139207L;

	private static Logger log = Logger.getLogger(SettledGetReportAction.class);

    private ISettledReportEricLogic settledReportEricLogic = null;//报表统计
    private IClassReportEricLogic classReportEricLogic = null;//报表统计
    private IReportStatusLogic reportStatusLogic;
    private ShopSchemeService shopSchemeService;
    /**
     * 手动生成报表列表
     * @return
     * @throws Exception
     */
    public String queryReport() {
    	String today = new Date(new java.util.Date().getTime()).toString();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	String dateStrBefore = "";
    	String dateStrAfter = "";
    	String rStart = getRequest().getParameter("reportStartDate");
    	String rEnd = getRequest().getParameter("reportEndDate");
    	LOG.info("<--手动生成统计报表   start-->操作日期为从：" + rStart +" 至 " + rEnd);
    	String shopCode=getRequest().getParameter("shopCode");
    	String scheme=shopSchemeService.getSchemeByShopCode(shopCode);
    	//计算前先把状态改为N,是为了不影响客户查询
    	String status = "N";
    	reportStatusLogic.updateReportStatus(status, scheme);
    	
    	try {
	    	String[] strStart = rStart.split("-");
	        String[] strEnd = rEnd.split("-");
	        String head = strStart[0]+'-'+strStart[1];
	        Integer startDate = Integer.valueOf(strStart[2]);
	        Integer endDate = Integer.valueOf(strEnd[2]);
	        //1、循环日期
	        for(int i=startDate;i<=endDate;i++){
	        	dateStrBefore = head + "-" + i;
	        	dateStrAfter = head + "-" + (i+1);
	        	java.util.Date ddBefore =  sdf.parse(dateStrBefore);//统一日期格式
	        	java.util.Date ddAfter =  sdf.parse(dateStrAfter);//统一日期格式
	        	dateStrBefore = sdf.format(ddBefore);
	        	dateStrAfter = sdf.format(ddAfter);
	        	//判断要处理的日期是不是今天
	    		Boolean isToday=false;
	    		if(today.equals(dateStrBefore)){isToday=true;}
	        	//取出5个玩法的所有盘期，放到5个LIST里，循环.
	        	//处理广东
	    		String before=dateStrBefore + " 02:30:00";
	    		String after=dateStrAfter + " 02:30:00";
	    		List<Criterion> filtersPeriodInfoP = new ArrayList<Criterion>();
	    		filtersPeriodInfoP.add(Restrictions.ge("lotteryTime",java.sql.Timestamp.valueOf(before)));
	    		filtersPeriodInfoP.add(Restrictions.le("lotteryTime",java.sql.Timestamp.valueOf(after)));
	    		filtersPeriodInfoP.add(Restrictions.not(Restrictions.eq("state", "0")));
	    		List<GDPeriodsInfo> periodInfoList=periodsInfoLogic.queryAllPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
	        	
	    		for(GDPeriodsInfo info:periodInfoList){
			    	this.updateReport(info.getPeriodsNum(), Constant.LOTTERY_TYPE_GDKLSF, dateStrBefore, isToday, scheme);
	    		}
	    		//处理重庆
	    		List<CQPeriodsInfo> periodInfoListCQ=icqPeriodsInfoLogic.queryAllPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
	    		
	    		for(CQPeriodsInfo info:periodInfoListCQ){
	    			this.updateReport(info.getPeriodsNum(), Constant.LOTTERY_TYPE_CQSSC, dateStrBefore, isToday, scheme);
	    		}
	    		//处理北京
	    		List<BJSCPeriodsInfo> periodInfoListBJ=bjscPeriodsInfoLogic.queryAllPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
	    		
	    		for(BJSCPeriodsInfo info:periodInfoListBJ){
	    			this.updateReport(info.getPeriodsNum(), Constant.LOTTERY_TYPE_BJ, dateStrBefore, isToday, scheme);
	    		}
	    		//处理K3
	    		List<JSSBPeriodsInfo> periodInfoListK3=jssbPeriodsInfoLogic.queryAllPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
	    		
	    		for(JSSBPeriodsInfo info:periodInfoListK3){
	    			this.updateReport(info.getPeriodsNum(), Constant.LOTTERY_TYPE_K3, dateStrBefore, isToday, scheme);
	    		}
	    		//处理农场
	    		List<NCPeriodsInfo> periodInfoListNC=ncPeriodsInfoLogic.queryAllPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
	    		
	    		for(NCPeriodsInfo info:periodInfoListNC){
	    			this.updateReport(info.getPeriodsNum(), Constant.LOTTERY_TYPE_NC, dateStrBefore, isToday, scheme);
	    		}
	        }
	    	//生成历史报表
			//2、以userid和bettingDate来求和报表的期数统计表再写入报表统计历史表里，如果是当天的就不写入报表统计历史表里。
			settledReportEricLogic.deleteReportPetList(rStart, rEnd,scheme);
			settledReportEricLogic.deleteReportRList(rStart, rEnd,scheme);
			classReportEricLogic.deleteReportPetList(rStart, rEnd,scheme);
			classReportEricLogic.deleteReportRList(rStart, rEnd,scheme);
			
			settledReportEricLogic.saveReportList(rStart,rEnd,scheme);
	        
		} catch (ParseException e) {
			status = "N";
	    	reportStatusLogic.updateReportStatus(status,scheme);
	    	log.info("手动生成统计报表数据 异常，提示错误："+e.getMessage());
		}
        return SUCCESS;
    }
    
    private void updateReport(String periodNum,String lotteryType, String dateStr, Boolean isToday,String schema) throws ParseException{
    	//先把要该日期的报表统计数据删除
    	/*settledReportEricLogic.saveReportListForReComputeMenu(periodNum, lotteryType, dateStr,
    			this.getSettledReportEricLogic(),this.getClassReportEricLogic(),this.getReportStatusLogic(),schema);*/
    	settledReportEricLogic.saveReportListForReComputeMenuForBoss(periodNum, lotteryType, dateStr,schema);
    }
    
    /*public void queryReport() {
    	lotteryResultLogic.saveReportPeriod("2015092008", Constant.LOTTERY_TYPE_NC, "LOT8866");// 统计报表
    }*/
    
    private IGDPeriodsInfoLogic periodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	private ILotteryResultLogic lotteryResultLogic;
    
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

	public ILotteryResultLogic getLotteryResultLogic() {
		return lotteryResultLogic;
	}

	public void setLotteryResultLogic(ILotteryResultLogic lotteryResultLogic) {
		this.lotteryResultLogic = lotteryResultLogic;
	}

	public ISettledReportEricLogic getSettledReportEricLogic() {
		return settledReportEricLogic;
	}

	public void setSettledReportEricLogic(
			ISettledReportEricLogic settledReportEricLogic) {
		this.settledReportEricLogic = settledReportEricLogic;
	}

	public IClassReportEricLogic getClassReportEricLogic() {
		return classReportEricLogic;
	}

	public void setClassReportEricLogic(IClassReportEricLogic classReportEricLogic) {
		this.classReportEricLogic = classReportEricLogic;
	}

	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}

	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}

	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}

	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}

}
