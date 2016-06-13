package com.npc.lottery.member.dao.interf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BillSearchVo;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.member.entity.PlayWinInfo;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.util.Page;


/**
 * 角色所拥有的功能数据库处理类
 *
 * @author none
 *
 */
public interface IBetDao
{
	/**
	 * 查询商铺下的投注信息，盘期停开时，更新会员可用金额所用
	 * @param periodNum 盘期
	 * @param scheme
	 * @param tables  投注表
	 * @return
	 */
	public List<BaseBet> queryAllMemberBetMoney(String periodNum,String scheme,String[] tables);
	/**
	 * 查询商铺下的会员输赢信息，盘期作废时，更新会员可用金额所用
	 * @param periodNum 盘期
	 * @param scheme
	 * @param tables  投注历史表
	 * @return
	 */
	public List<BaseBet> queryAllMemberWinOrLoseMoney(String periodNum,String scheme,String tableName);
	public int[] batchInsert(List<BaseBet> betList,String TBName,boolean insertAttr);
	public Page queryGDKLSFUserBetDetail(Page page,Long userId);
	public Page queryCQSSCUserBetDetail(Page page,Long userId);
	//合並廣東重慶的投注明細
	public Page querySSCUserBetDetail(Page page, Long userId);
	public Page queryHKLHCUserBetDetail(Page page,Long userId);
	
	public Page queryBJSCUserBetDetail(Page page, Long userId);
    /**
     * 查询PLAY_SUB_TYPE总投注额
     * @author Eric
     * userIdList 格式为id,id,id
     */
	public Integer queryTotalBySubType(String userIdList, String queryTableName, String plate, String typeCode);
	/**
	 * 查詢香港六合彩投注額
	 * @param typeCode 傳入 like字符串 ：'HK_TM\_%' ESCAPE '\'
	 * @return
	 */
	public List queryHKPlayMoneyByTypeCode(String typeCode, String shopCode);
	public List queryBetResult(String TableName,String periodNum,Integer startNum,Integer endNum,String scheme);
	
	public List queryHistoryBetResult(String TableName,String periodNum,String orgTableName ,Integer startNum,Integer endNum);
	
	//public List queryWinBetResult(String TableName,String periodNum,Integer startNum,Integer endNum);
	public void updateFSBetResult(String TableName,Integer recordId,String win,String winAmount,String winCount,String scheme);
	
	public void updateGGBetResult(String TableName,Integer recordID,String win,String winAmount,String winCount,String odds);
	//批量更新未中奖信息
	public void updateNotWinBetResult(String TableName,String periodNum,String scheme);
	//public void updateNotWinReplenshlResult(String periodNum,String type);
	
	public void updateBetResult(String TableName,String winNum,String orderNo,String winAmount);
	
	public void updateCQHistoryBetResult(String winState,String id,String winAmount);
	public void updateGDHistoryBetResult(String winState,String id,String winAmount);
	public void updateHKHistoryBetResult(String winState,String id,String winAmount);
	
	public void updateReplenshBetResult(String winState,String id,String winAmount,String scheme);
	
	//更新打和的记录
	public void updateHKHeResult(String periodsNum,List<Integer> winNums,boolean history);
	
	
	public void updateCQHeResult(String periodsNum,List<String> tableNames,String scheme);
	public void updateGDHeResult(String periodsNum,List<String> tableNames,String scheme);
	public double queryTodayWinMoney(Long userId);
	
	
	
	public void batchUpdateBetResult(String TableName,String periodNum,String scheme);
	
	public void updateUserTotalCreditLine(Integer Winmoney,Long userId);
	//public void updateUserCommison(String TableName,String commission,Long userId,String typeCode);
	public List getUserCommsion(String userId,String playType);
	public List getManagerCommsion(List userIds,String playType);
	public Double queryTotalCommissionMoney(String shopCode,String commissionType);
	public Integer queryTotalOddsMoney(String shopCode,String oddsType);
	//批量更新用户信用额
	public void batchUpdateUserAvailableCredit(String periodsNum,String tableName,String scheme);
	
	//批量恢复用户信用额
    public void batchRestoreUserAvailableCredit(String periodsNum,String tableName,String scheme);
	
	public Integer  queryUseritemQuotasMoney(String playType, String periodNum,String typeCode,String userId);
	public Integer  queryUserLMitemQuotasMoney(String playType, String periodNum,String typeCode,String userId,String attr);
	//public void updatePlayAmount(List<BaseBet> betList);
	public void miragationGDDataToHistory(String periodsNum,String state,String scheme);
	public void miragationCQDataToHistory(String periodsNum,String state,String scheme);
	public void miragationBJDataToHistory(String periodsNum,String state,String scheme);
	public void miragationHKDataToHistory(String periodsNum,String state);
	public List<CqsscHis> queryCQBalanceDao(String beginDate,String endDate);
	public List<BalanceInfo> queryBalanceDao(Long userId,Date beginDate,Date endDate) ;
	public void deletePlayTypeWinInfoByPeriod(String period, String playType);
	public void batchInsertPlayTypeToWin(final List<PlayWinInfo> playwinInfoList, final String periodsNum);
	
	public List<CQandGDReportInfo> queryCQandGDReportDao(Long userId,Date beginDate) ;
	
	public void updateReLotteryBetResult(Integer id,String winState,String winAmount,String orgState,String tableName,String playType);
	
	public List<CQandGDReportInfo> queryHKReportDao(Long userId,Date beginDate) ;
		
	public Map getSXLBetMoney(String periodNum);
	
	public Map getWSLBetMoney(String periodNum);
	
	public Map getWBZBetMoney(String periodNum);
	
	public Map getGGBetMoney(String periodNum);
	
	public List<BaseBet> getReplenishDate(String periodNum,String playType,Integer startNum,Integer endNum,String scheme);
	
	//批量更新补货未中奖信息
	public void updateReplenshNotWinBetResult(String periodNum,String playType,String scheme);
		
	public void updateReplenshWinBetResult(Integer id,String winState,String winAmount);
	public void updateHistoryBetResultToUnLottery(String tableName,String periodNum,String scheme);
	
	/**
	 * 查詢GDKLSF注單信息  add by Aaron
	 * @param page
	 * @param BillSearchVo  查詢條件
	 * @return
	 */
	public Page queryGDKLSFBetByObj(Page page, BillSearchVo entry);
	/**
	 * 查詢CQSSC注單信息  add by Aaron
	 * @param page
	 * @param BillSearchVo  查詢條件
	 * @return
	 */
	public Page queryCQSSCBetByObj(Page page, BillSearchVo entry);
	public Page queryHKLHCBetByObj(Page page, BillSearchVo entry);
	/**
	 * 查询历史表 已结算的
	 * @param page
	 * @param entry
	 * @return
	 */
	public Page queryHKLHCBetByObjHis(Page page, BillSearchVo entry);
	public Page queryGDKLSFBetByObjHis(Page page, BillSearchVo entry);
	public Page queryCQSSCBetByObjHis(Page page, BillSearchVo entry);
	public void updateGDKLSFBetByObj(String orderNum);
	public void updateCQSSCBetByObj(String orderNum);
	public void updateHKLHCBetByObj(String orderNum);

	public void updateHKHisGGResult(String winState,String id,String winAmount,String realOdds);
	
	public void updateBetResultInvalid(String periodNum,String lotteryType,String scheme);
	/*
	 * 盘期停开操作时对补货表里的数据的win_state字段进行赋值
	 * 对于已经结算的补货数据赋于5，未结算的补货数据赋于7
	 */
	public void updateReplenishResultInvalid(String periodNum,String lotteryType,String state,String scheme);
	
	public List<BalanceInfo> queryHKBalanceDao(Long userId,Date beginDate,Date endDate) ;

	/**
	 * 根据条件查询补货-分页
	 * @param page
	 * @param entry
	 * @return
	 */
	public Page queryReplenishByPage(Page page, BillSearchVo entry);

	public void updateReplenshGGResult(String winState,String id,String winAmount,String realOdds);
	//修改補貨表狀態 -
	public void updateReplenishByObj(String orderNum, String state);
	/**
	 * 查詢BJSC注單信息  add by Aaron 
	 * @param page
	 * @param entry
	 * @return
	 */
	public Page queryBJSCBetByObj(Page page, BillSearchVo entry);
	public Page queryBJSCBetByObjHis(Page page, BillSearchVo entry);
	public void updateBJSCBetByObj(String orderNum);
	//public void deleteBJSCBetByObj(String orderNum);
	/**
	 * 删除 廣東快樂十分注單信息  add by Aaron
	 * @param orderNum 注單號
	 */
	//public void deleteGDKLSFBetByObj(String orderNum);
	/**
	 * 删除 重庆时时彩 注單信息  add by Aaron
	 * @param orderNum 注單號
	 */
	//public void deleteCQSSCBetByObj(String orderNum);
	/**
	 * 删除 補貨表注单  add by Aaron20121113
	 * @param orderNum
	 * @return
	 */
	//public void deleteReplenishByOrderNum(String orderNum);
	public Page queryBetByObjAdmin(Page page, BillSearchVo entry);
	public void updateReplenishByOrderNum(Integer money ,String orderNum);
	public List<PeriodsNumVo> queryPeriodsAllOrderTime();
	//查询广东投注的统计信息
	public Map gdBetStatistics(String periodNum) ;
	//查询广东投注的统计信息
		public Map cqBetStatistics(String periodNum) ;
		//查询广东投注的统计信息
	public Map bjBetStatistics(String periodNum);


	public abstract void batchUpdateBetResultForJSSC(String TableName, String periodNum,String scheme);
	public abstract void miragationJSDataToHistory(String periodsNum, String state,String scheme);
	public abstract void updatePlusOddsForJSSB(String typeCode, String periodsNum, int plusOdds,String scheme);
	//public abstract void deleteBetInfoByOrderNum(String orderNo, String tableName,String periodsNum);
	public abstract void updatePlusOddsForJSSBHis(String typeCode, String periodsNum, int plusOdds,String scheme);
	/**
	 * add by peter for 注单注销
	 * @param orderNo
	 * @param tableName
	 * @param periodsNum
	 */
	public abstract void cancelBetInfoByOrderNum(String orderNo, String tableName, String periodsNum);

	//public abstract void deleteHisReplenishByOrderNum(String orderNum);

	public abstract void cancelHisReplenishByOrderNum(String orderNum);

	/**
	 * 迁移补货表已兑奖的记录到历史表
	 * 
	 * @return
	 */
	public abstract void miragationReplenishDataToHistory(String periodsNum, String typeCode,String scheme);
	public abstract void updateReplenshNotWinBetResultHis(String periodNum, String playType,String scheme);
	public abstract void updateReplenshBetResultHis(String winState, String id, String winAmount,String scheme);
	public abstract  List getReplenishDataHis(String periodNum, String playType, Integer startNum, Integer endNum,String scheme);
	public void updateNCHeResult(String periodsNum, List<String> tableNames,String scheme);
	public void miragationNCDataToHistory(String periodsNum, String state,String scheme);
	public List queryBetResultForNC(String tableName, String periodNum, Integer startNum, Integer endNum,String scheme);
	public void batchUpdateBetResultForNC(String TableName, String periodNum,String scheme);
	public void updateNotWinBetResultForNC(String TableName, String periodNum,String scheme);
	public Integer queryUserLMitemQuotasMoneyForNC(String playType, String periodNum, String typeCode, String userId, String attr);
	public Map<String, BigDecimal> queryUseritemQuotasMoneyMap(String playType, String periodNum, Long userId);
	public Map<String, BigDecimal> queryTotalCommissionMoneyMap(String shopCode,String playType);
	public void miragationReplenishDataToHistoryForNotOpen(String periodsNum, String typeCode, String scheme);
}
