package com.npc.lottery.statreport.logic.interf;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.service.IBaseLogic;
import com.npc.lottery.statreport.dao.interf.IClassReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRPeriodDao;
import com.npc.lottery.statreport.dao.interf.IReportStatusDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRPeriodDao;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRList;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;
import com.npc.lottery.statreport.vo.TopRightVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类接口
 * 
 */
public interface ISettledReportEricLogic extends IBaseLogic{

    /**
     * 查询未结算
     * @param lotteryType 如广东，重庆，香港
     * @param playType  玩法类型，这里取拥金类型
     * @param userType  用户类型，要查询的用户类型，如股东登录，查到总代理时，这个字段就是总代理，而currentUserType就是股东
     * @param currentUserType 当前登录用户的用户类型
     * @return
     */
    public List<DeliveryReportEric> findSettledReport(Date startDate,Date endDate,String lotteryType,
    		String playType,String periodNum,Long userid,String userType,String[] scanTableList,String winState,
    		String schema);

	public List<DeliveryReportEric> queryReplenish(Date startDate, Date endDate, Long userID,String userType,
			String typeCode, String periodsNum,String lotteryType,String winState,String tableName,String outReplenishStaffExtTabelName);
	
	public Page findDetail(Page page, Date startDate, Date endDate, String playType,
			String periodNum, Long userId,String userType,String[] scanTableList,Long currentUserId);

	public Page findReplenishDetail(Page page, Date startDate, Date endDate,String periodNum, Long userId, String userType,String currentUserType,String commissionTypeCode,String lotteryType,Long currentUserId);
	/**
     * 当天输赢
     * 查询当天投注结果数据 对应报表汇总部分的抵扣补货及赚水后结果
     * 
     * @param userID
     *            用户ID
     * @param userType
     *            用户类型
     * @param lotteryType
     *            彩票类型：ALL-所有；GDKLSF-广东快乐十分；CQSSC-重庆时时彩；HKLHC-香港六合彩
     * @param playType
     *            下注类型，不过滤则传入 null
     * @param periodsNum
     *            盘期，不过滤则传入null
     * @return 返回投注统计结果数据，异常则返回null
     */
    public Double queryTodayWinLose(Long userID, String userType,
            String lotteryType, String playType, String periodsNum,Date startDate,Date endDate,String schema);

	public List<TopRightVO> findDetailGroupByDate(Date startDate, Date endDate,String playType,String periodNum, Long userId, String[] scanTableList);

	public List<TopRightVO> findDetailGroupByDateForReplenish(Date startDate,
			Date endDate, String playType, String periodNum, Long userId,String userType);

	/*public void saveReportPetList(DeliveryReportPetList entity);

	public void saveReportRList(DeliveryReportRList entity);*/

	public void updateReportPetList(DeliveryReportPetList entity);

	public void updateReportRList(DeliveryReportRList entity);

	public List<DeliveryReportPetList> queryDeliveryReportPetList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<DeliveryReportRList> queryDeliveryReportRList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<DeliveryReportPetList> queryReportPetList(Criterion[] criterias);

	public List<DeliveryReportRList> queryReportRList(Criterion[] criterias);

	public List<DeliveryReportEric> findSettledReportMerge(Date startDate,Date endDate, Date nowDate, Long userid, String userType);

	public List<DeliveryReportEric> queryReplenishMerge(Date startDate, Date endDate,Date nowDate, Long userID,String userType);
    /**
     * 生成报表
     * @param rStart
     * @param rEnd
     * @param schema 
     */
	public void saveReportList(String rStart, String rEnd,String schema)
			throws ParseException;
    /**
     * 异步执行重新计算报表
     * @param rStart
     * @param rEnd
     * @param isJOB
     * @throws ParseException
     */
	public void saveReportListForReCompute(final String periodNum,
			final String lotteryType, final String dateStr,final Boolean isToday,final ISettledReportEricLogic settledReportEricLogic,
			final IClassReportEricLogic classReportEricLogic,final IReportStatusLogic reportStatusLogic,String schema)
			throws ParseException;
    /**
     * 根据条件查询出有实货的管理人员
     * 如果periodNum为Null,就以日期查询
     * @param scanTableList
     * @param startDate
     * @param endDate
     * @param periodNum
     * @return
     */
	public List<ManagerStaff> queryAllManagerUser(String[] scanTableList,
			Date startDate, Date endDate,String periodNum,String schema);


	public void deleteReportPetList(String rStart, String rEnd,String schema) throws ParseException;

	public void deleteReportRList(String rStart, String rEnd,String schema) throws ParseException;
	
	public void deleteReportPetPeriod(String periodsNum,String lotteryType,String schema) throws ParseException;

	public void deleteReportRPeriod(String periodsNum,String lotteryType,String schema) throws ParseException;
    //重新计算整天的报表
	public void saveReportFullDayList(String rStart, String rEnd, Boolean isJOB, String schema)
			throws ParseException;

	public List<ManagerStaff> queryAllManagerUserInReportPeriod(
			String[] scanTableList, Date startDate, Date endDate,
			String periodNum, String schema);
    /**
     * 
     * @param userID
     * @param userType
     * @param startDate 可选性，传入NULL就忽略条件
     * @param endDate 可选性，传入NULL就忽略条件
     * @param periodNum 可选性，传入NULL就忽略条件
     * @param lotteryType 大类，用常量，可选性，传入NULL就忽略条件
     * @return
     */
	public List<DeliveryReportEric> queryDeliveryReportPetPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String lotteryType,String schema);

	public List<DeliveryReportEric> queryDeliveryReportRPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String lotteryType,String schema);

	/*public void saveReportListForReComputeMenu(String periodNum, String lotteryType,
			String dateStr, ISettledReportEricLogic settledReportEricLogic,
			final IClassReportEricLogic classReportEricLogic,final IReportStatusLogic reportStatusLogic,final String schema)
			throws ParseException;*/

	public void saveReportListForReComputeMenuForBoss(String periodNum,
			String lotteryType, String dateStr, String schema);

	public void saveReportPeriod(String periodsNum, String lotteryType, String schema, String culDate)
			throws ParseException;

	public void saveReportPeriodForBoss(String periodsNum, String lotteryType,
			String schema) throws ParseException;

}
