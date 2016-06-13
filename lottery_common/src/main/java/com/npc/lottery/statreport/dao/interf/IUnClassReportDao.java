package com.npc.lottery.statreport.dao.interf;

import java.sql.Date;
import java.util.List;

import com.npc.lottery.statreport.entity.UnClassReport;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类接口
 * @typeCode 用来过滤玩法类型，模糊要加%
 */
public interface IUnClassReportDao {

	public List<UnClassReport> queryUnClassReport(Date startDate, Date endDate,
			String lotteryType, String playType, String periodNum, Long userid,String userType,
			String userColumn,String rateUser,String commissionUser,String nextColumn,String[] scanTableList);
    
	//计算该用户的补出的货的合计
	public List<UnClassReport> queryReplenish(Date startDate, Date endDate, Long userID,
			String typeCode, String periodsNum,String rateUser,String lotteryType,String userType);

	public Page queryBetDetail(Page page, Long userId,Date startDate, Date endDate, String periodNum,
			String playType,String[] scanTableList,String myColumn);

	public Page queryReplenishOutDetail(Page page, Long userId, String commissionTypeCode,
			String periodsNum, String userType, Date startDate, Date endDate,String lotteryType);
    

}
