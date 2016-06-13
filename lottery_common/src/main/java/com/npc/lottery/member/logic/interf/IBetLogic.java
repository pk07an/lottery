package com.npc.lottery.member.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BillSearchVo;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.Page;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
public interface IBetLogic {

	public void saveGDBet(List<BaseBet> BallList, MemberStaffExt memberStaff, String shopCode);

	public double queryTodayWinMoney(Long userId);

	public void saveCQBet(List<BaseBet> BallList, MemberStaffExt memberStaff, String shopCode);

	public void saveBJSCBet(List<BaseBet> BallList, MemberStaffExt memberStaff, String shopCode);

	public Page queryGDKLSFUserBet(Page page, Long userId);

	public Page queryCQSSCUserBet(Page page, Long userId);

	public Page querySSCUserBet(Page page, Long userId);

	public Page queryBJSCUserBet(Page page, Long userId);

	public Map<String, Double> getUserTreeCommission(List<Long> userTree, String userId, String playType, String plate);

	/**
	 * 根據 tableName 查詢投注額
	 * 
	 * @param tableName
	 * @return
	 */

	public Double queryTotalCommissionMoney(String shopCode, String commissionType);

	public Integer queryUseritemQuotasMoney(String playType, String periodNum, String typeCode, String userId);

	public Integer queryUserLMitemQuotasMoney(String playType, String periodNum, String typeCode, String userId, String attr);

	public Integer queryTotalOddsMoney(String shopCode, String oddsType);

	public List<BalanceInfo> queryCQBalance(List<String> list, Long userMemberID);

	public List<BalanceInfo> queryGDBalance(List<String> list, Long userMemberID);

	public Map<String, BalanceInfo> queryBalance(Long userMemberID, Date startDate, Date endDate);

	public Page<CQandGDReportInfo> queryCQandGDReport(Page page, Long userMemberID, Date startDate);

	/**
	 * 查詢CQSSC注單信息 add by Aaron
	 * 
	 * @param page
	 * @param BillSearchVo
	 *            查詢條件
	 * @return
	 */
	public Page queryGDKLSFBetByObj(Page page, BillSearchVo entry);

	public Page queryCQSSCBetByObj(Page page, BillSearchVo entry);

	/**
	 * update CQSSC注單信息 - 狀態 add by Aaron
	 */
	public void updateGDKLSFBet(String orderNum);

	public void updateCQSSCBet(String orderNum);

	public Page queryReplishPage(Page page, BillSearchVo entry);

	public void updateReplenishStateByOrderNum(String orderNum, String state);

	/**
	 * 查詢 北京赛车注單信息 add by Aaron 20121110
	 * 
	 * @param page
	 * @param vo
	 * @return
	 */
	public Page<BaseBet> queryBJSCBetByObj(Page<BaseBet> page, BillSearchVo vo);

	public void updateBJSCBet(String orderNum);

	/**
	 * 总监 注单搜索
	 * 
	 * @param page
	 * @param entry
	 * @return
	 */
	public Page queryBillSerachPageAdmin(Page page, BillSearchVo entry);

	public void updateReplenishByOrderNum(Integer montey, String orderNum);

	public List<PeriodsNumVo> queryPeriodsAllOrderTime();

	public Map gdBetStatistics(String periodNum);

	// 查询广东投注的统计信息
	public Map cqBetStatistics(String periodNum);

	// 查询广东投注的统计信息
	public Map bjBetStatistics(String periodNum);

	public void autoReplenish(List<BaseBet> ballList, MemberStaffExt memberStaff);

	
	public void autoReplenish(List<BaseBet> ballList,MemberStaffExt memberStaff,String scheme);
	/**
	 * 保存会员提交的投注信息到投注表
	 * 
	 * @param ballList
	 * @param memberStaff
	 */
	public abstract void saveJSSBBet(List<BaseBet> ballList, MemberStaffExt memberStaff, String shopCode);

	/**
	 * 总监注单注销
	 * 
	 * @param playType
	 * @param lotteryType
	 * @param orderNo
	 * @param periodsNum
	 * @param typeCode
	 * @param billType
	 */
	public abstract void cancelBet(String playType, String lotteryType, String orderNo, String periodsNum, String typeCode, String billType, String ip);

	/**
	 * 注销 历史 補貨表注单 add by Aaron20121113
	 * 
	 * @param orderNum
	 * @return
	 */
	public abstract void cancelHisReplenishByOrderNum(String typeCode, String orderNo, String periodsNum, String billType, String ip);

	public abstract void updateMemberAvailableCreditLineAsyn(MemberStaffExt memberStaff);

	public void saveNCBet(List<BaseBet> ballList, MemberStaffExt memberStaff, boolean insertAttr, String shopCode);

	public Integer queryUserLMitemQuotasMoneyForNC(String playType, String periodNum, String typeCode, String userId, String attr);

	public double getMemberAvailableCreditLineRealTime(MemberStaffExt memberStaff);

	public void updateMemberAvailableCreditLineById(double availableCreditLine, long id);

	public CancelPet queryCancelPet(Criterion[] criterions);

	public void recoveryPet(String orderNo, String typeCode, String periodsNum, String billType, String ip);

	public List<CancelPetLog> queryCancelPetLogList(String orderNo, String typeCode, String periodsNum, String billType);

}
