package com.npc.lottery.periods.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.util.Page;

public interface INCPeriodsInfoLogic {

	public List<NCPeriodsInfo> queryAllPeriods();

	public NCPeriodsInfo queryByPeriods(String periodsNum, Object number);

	public NCPeriodsInfo queryPeriods(Object periodsNum, Object status);


	public void save(NCPeriodsInfo NCPeriodsInfo);

	public List<NCPeriodsInfo> queryAllPeriods(Criterion... criterias);

	public NCPeriodsInfo queryByPeriods(Criterion... criterias);

	public NCPeriodsInfo queryByPeriodsStatus(String status);

	public void updateCommon(String commonDate);

	public List<NCPeriodsInfo> queryTodayPeriods();

	public Page<NCPeriodsInfo> queryHistoryPeriods(Page<NCPeriodsInfo> page);

	public Page<NCPeriodsInfo> queryHistoryPeriodsForBoss(Page<NCPeriodsInfo> page);

	// 保存修改广东快乐十分的开奖结果
	public void updateLotResult(NCPeriodsInfo NCPeriodsInfo);

	// 封盘
	public void updateNCStatus();

	public NCPeriodsInfo queryLastLotteryPeriods();

	public NCPeriodsInfo queryLastLotteryPeriods_noTime();

	public NCPeriodsInfo queryLastNotOpenPeriods();

	public List<NCPeriodsInfo> queryLastPeriodsForRefer();

	public void updatePeriodsStatusByPeriodsNum(String periodsNum, String status);

	public NCPeriodsInfo queryStopPeriods(String status);

	public void update(NCPeriodsInfo NCPeriodsInfo);

	public List<NCPeriodsInfo> queryByNCPeriods(String periodsNum, String number);

	public void deletePeriods(String beginPeriodsNum, String endPeriodsNum);

	public List<NCPeriodsInfo> queryReportPeriodsNum();

	public List<NCPeriodsInfo> queryPeriods(String beginPeriodsNum, String endPeriodsNum);

	public List<NCPeriodsInfo> queryTodayAllPeriods();

	public Page<NCPeriodsInfo> findPage(Page<NCPeriodsInfo> page, Criterion[] criterions);

	/**
	 * 查看当前正在运行的盘期 条件:开盘后,开奖前
	 **/
	public NCPeriodsInfo queryZhangdanPeriods();

	public List<NCPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row);

	/**
	 * 从第三方网站获取广东快乐十分开奖信息
	 * 
	 * @return
	 */
	/*public Map<String, List<Integer>> getResult();

	public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum);*/

	/**
	 * 获取当前封盘的盘期信息
	 * 
	 * @return
	 */
	public NCPeriodsInfo findCurrentStopPeriodInfo();

	public List<NCPeriodsInfo> findAllExceptionPeriodInfo();

	/**
	 * 每天广东快乐十分业务结束从投注表里（关联盘期表,获得盘期对象,只获取盘期是4或者7已经开奖或兑奖的数据）获取遗漏兑奖的盘期列表，用作每日结束的重新兑奖
	 * 
	 * @return
	 */
	public Map<String, NCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme);

	/**
	 * 根据日期获取当天开奖结果有异常的盘期
	 * 
	 * @param queryDate
	 * @return
	 */
	public List<NCPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate);

	public void saveNCPeriods(Date peridosDate);

	//public void updateNCStatusWithCheckMiragation();
	
	public NCPeriodsInfo findCurrentPeriod(); 

}
