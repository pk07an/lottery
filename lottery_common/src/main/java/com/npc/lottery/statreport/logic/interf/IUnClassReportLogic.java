package com.npc.lottery.statreport.logic.interf;

import java.sql.Date;
import java.util.List;

import com.npc.lottery.statreport.entity.UnClassReport;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类接口
 * 
 */
public interface IUnClassReportLogic {

    /**
     * 查询未结算
     * @param lotteryType 如广东，重庆，香港
     * @param playType  玩法类型，这里取拥金类型
     * @param userType  用户类型，要查询的用户类型，如股东登录，查到总代理时，这个字段就是总代理，而currentUserType就是股东
     * @param currentUserType 当前登录用户的用户类型
     * @return
     */
    public List<UnClassReport> findUnClassReport(Date startDate,Date endDate,String lotteryType,
    		String playType,String periodNum,Long userid,String userType,String[] scanTableList);

	public List<UnClassReport> queryReplenish(Date startDate, Date endDate, Long userID,String userType,
			String typeCode, String periodsNum,String lotteryType);
	
	public Page findDetail(Page page, Date startDate, Date endDate, String playType,
			String periodNum, Long userId,String userType,String[] scanTableList);

	public Page findReplenishDetail(Page page, Date startDate, Date endDate,String periodNum, Long userId, String userType,String commissionTypeCode,String lotteryType);
    
}
