package com.npc.lottery.statreport.dao.interf;

import java.sql.Date;
import java.util.List;

import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类接口
 * @typeCode 用来过滤玩法类型，模糊要加%
 */
public interface IClassReportEricDao {

	public List<ClassReportEric> queryClassReport(Date startDate, Date endDate,
			String lotteryType, String playType, String periodNum, Long userid,String userType,
			String userColumn,String rateUser,String nextRateColumn,String commissionUser,String nextColumn,String[] scanTableList,
			String underLineRateColumn,String nextCommissionColumn,String winState,String schema);
    
	//计算该用户的补出的货的合计
	public List<ClassReportEric> queryReplenish(Date startDate, Date endDate, Long userID,String typeCode, String periodsNum,
			String rateUser,String lotteryType,String userType,String commissionUser,String winState,String tableName,String schema);
    //myCommissionColumn是为了处理直属会员的，如果是直属会员的，就要取自己的拥金，否则取下级拥金
	public Page queryBetDetail(Page page, Long userId,Date startDate, Date endDate, String periodNum,
			String playType,String[] scanTableList,String rateColumn,String commissionColumn,String myCommissionColumn,String myColumn);

	public Page queryReplenishOutDetail(Page page, Long userId, String commissionTypeCode,
			String periodsNum, String userType, Date startDate, Date endDate,String lotteryType,String rateColumn,String commissionColumn);

	public List<ClassReportPetList> queryClassReportPetList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<ClassReportRList> queryClassReportRList(Long userID, String userType,
			Date startDate, Date endDate);

	public List<ClassReportEric> queryClassReportPetPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String lotteryType,String schema);

	public List<ClassReportEric> queryClassReportRPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String lotteryType,String schema);
    

}
