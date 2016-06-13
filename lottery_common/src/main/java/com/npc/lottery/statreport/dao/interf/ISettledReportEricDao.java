package com.npc.lottery.statreport.dao.interf;

import java.sql.Date;
import java.util.List;

import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRList;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;
import com.npc.lottery.statreport.vo.TopRightVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类接口
 * @typeCode 用来过滤玩法类型，模糊要加%
 */
public interface ISettledReportEricDao {

	public List<DeliveryReportEric> querySettledReport(Date startDate, Date endDate,
			String lotteryType, String playType, String periodNum, Long userid,String userType,
			String userColumn,String rateUser,String nextRateColumn,String commissionUser,String nextColumn,String[] scanTableList,
			String underLineRateColumn,String nextCommissionColumn,String winState,String schema);
    
	//计算该用户的补出的货的合计
	public List<DeliveryReportEric> queryReplenish(Date startDate, Date endDate, Long userID,
			String typeCode, String periodsNum,String rateUser,String lotteryType,String userType,String commissionUser,
			String winState,String tableName,String schema);

	public Page queryBetDetail(Page page, Long userId,Date startDate, Date endDate, String periodNum,
			String playType,String[] scanTableList,String rateColumn,String commissionColumn,String myCommissionColumn);

	public Page queryReplenishOutDetail(Page page, Long userId, String commissionTypeCode,
			String periodsNum, String userType,String currentUserType, Date startDate, Date endDate,String lotteryType,String rateColumn,String commissionColumn);

	public List<TopRightVO> queryBetDetailGroupByDate(Long userId, Date startDate,Date endDate,
			String periodNum, String playType, String[] scanTableList);

	public List<TopRightVO> queryBetDetailGroupByDateForReplnish(Long userId,
			Date startDate, Date endDate, String periodNum, String playType,String userType);

	public List<DeliveryReportPetList> queryDeliveryReportPetList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<DeliveryReportRList> queryDeliveryReportRList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<DeliveryReportEric> querySettledReportMerge(Date startDate,Date endDate,Date nowDate,Long userid,String userType);

	public List<DeliveryReportEric> queryReplenishMerge(Date startDate,Date endDate,Date nowDate,Long userID,String userType);

	public List<ManagerStaff> queryAllManagerUser(String[] scanTableList, Date startDate, Date endDate,String periodNum,String schema);

	public List<ManagerStaff> queryAllManagerUserInReportPeriod(
			String[] scanTableList, Date startDate, Date endDate,
			String periodNum,String schema);
    /**
     * @param userID
     * @param userType
     * @param startDate 传入NULL就会省去该条件
     * @param endDate 传入NULL就会省去该条件
     * @param periodNum  传入NULL就会省去该条件
     * @return
     */
	public List<DeliveryReportEric> queryDeliveryReportPetPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String playType,String schema);

	public List<DeliveryReportEric> queryDeliveryReportRPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String playType,String schema);

}
