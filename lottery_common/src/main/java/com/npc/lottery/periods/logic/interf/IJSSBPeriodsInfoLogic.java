/**
 * 
 */
package com.npc.lottery.periods.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.util.Page;

/**
 * @author peteran
 * 
 */
public interface IJSSBPeriodsInfoLogic {

	public abstract JSSBPeriodsInfo queryLastLotteryPeriods();

	public abstract JSSBPeriodsInfo queryStopPeriods(String status);

	public abstract JSSBPeriodsInfo queryByPeriodsStatus(String status);

	public abstract void deletePeriods(String beginPeriodsNum, String endPeriodsNum);

	public abstract void saveJSPeriods();

	public abstract void updateCommon(String commonDate);

	public abstract JSSBPeriodsInfo updateJSSBStatus();

	public abstract List<JSSBPeriodsInfo> queryAllPeriods(Criterion[] criterias);

	public abstract void save(JSSBPeriodsInfo jssbPeriodsInfo);

	/**
	 * 获取K3当天历史开奖信息， 用于更新会员页面右边的table
	 * 
	 * @return
	 */
	public abstract List<JSSBPeriodsInfo> getCurrentDayHisLotteryPeridosInfoList();

	public abstract Page<JSSBPeriodsInfo> findPage(Page<JSSBPeriodsInfo> page, Criterion[] criterions);

	public abstract List<JSSBPeriodsInfo> queryPeriods(String beginPeriodsNum, String endPeriodsNum);

	public abstract JSSBPeriodsInfo queryByPeriods(Criterion[] criterias);

	public abstract Page<JSSBPeriodsInfo> queryHistoryPeriods(Page<JSSBPeriodsInfo> page);

	public abstract JSSBPeriodsInfo findCurrentStopPeriodInfo();

	public abstract void update(JSSBPeriodsInfo jssbPeriodsInfo);

	/*public abstract Map<String, List<Integer>> getResult();
	public abstract Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum);*/
	
	public abstract List<JSSBPeriodsInfo> findAllExceptionPeriodInfo();

	/**
	 * 更新江苏盘期的状态
	 * @param periodsNum
	 * @param status
	 */
	public abstract void updatePeriodsStatusByPeriodsNum(String periodsNum, String status);

	public Page<JSSBPeriodsInfo> queryHistoryPeriodsForBoss(Page<JSSBPeriodsInfo> page,String state);

	public void updateK3LotResult(JSSBPeriodsInfo jssbPeriodsInfo);

	public List<JSSBPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row);

	public abstract List<JSSBPeriodsInfo> queryTodayPeriods();
	
	/**
	 * 每天江苏业务结束从投注表里（关联盘期表,获得盘期对象,只获取盘期是4或者7已经开奖或兑奖的数据）获取遗漏兑奖的盘期列表，用作每日结束的重新兑奖
	 * 
	 * @return
	 */
	public Map<String, JSSBPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme);

	public JSSBPeriodsInfo queryByPeriods(String periodsNum, Object number);

	/**
	 * 根据日期获取当天开奖结果有异常的盘期
	 * @param queryDate
	 * @return
	 */
	public abstract List<JSSBPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate);

	//public JSSBPeriodsInfo updateJSSBStatusWithCheckMiragation();
	
	public JSSBPeriodsInfo findCurrentPeriod();

	public List<JSSBPeriodsInfo> queryTodayAllPeriods();
}
