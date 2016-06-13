package com.npc.lottery.periods.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.util.Page;

public interface ICQPeriodsInfoLogic {

    public void saveCQAfterPeriods();; //  这是每隔5分钟开一次97-120期的时时彩 
  
    public void saveCQPeriods(); //  这是每隔10分钟开一次的时时彩 
    
    public CQPeriodsInfo queryByPeriods(Criterion...criterias);
    
    public CQPeriodsInfo queryByPeriodsStatus(String status);
    
    public void updateCommon(String commonDate);
    
    public CQPeriodsInfo queryByPeriods(String periodsNum,String number);
    
    public void save(CQPeriodsInfo caPeriodsInfo);
    
    public List<CQPeriodsInfo> queryAllPeriods(Criterion... criterias);
    
    
    public void saveCQInterimPeriods();//  这是每隔5分钟开一次1-23期的时时彩 
	
    public List<CQPeriodsInfo> queryTodayPeriods();
	
    public Page<CQPeriodsInfo> queryHistoryPeriods(Page<CQPeriodsInfo> page);
    
    //总管开奖结果显示，区别于上一个接品，把拿不到开奖结果也显示出来，以便手动输入开奖结果
    public Page<CQPeriodsInfo> queryHistoryPeriodsForBoss(Page<CQPeriodsInfo> page);
    
    //保存修改重庆时时彩的开奖结果
    public void updateLotResult(CQPeriodsInfo caPeriodsInfo);
  //修改重庆时时彩的封盘状态
    public void updateCQStatus();
    
    public CQPeriodsInfo queryLastLotteryPeriods();
    public CQPeriodsInfo queryLastNotOpenPeriods();
    
    public List<CQPeriodsInfo> queryLastPeriodsForRefer();
    
    public List<CQPeriodsInfo> queryReportPeriodsNum();
    
    public List<CQPeriodsInfo> queryAllPeriods(String periodsNum,String number);
    
    public void update(CQPeriodsInfo cqPeriodsInfo);
    
    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status);
    public CQPeriodsInfo queryStopPeriods(String status);
    
    public void deletePeriods(String beginPeriodsNum,String endPeriodsNum);
    
    public List<CQPeriodsInfo> queryPeriods(String beginPeriodsNum,String endPeriodsNum);
    
    public void saveManualCQInterimPeriods();
    
    public void saveManualCQPeriods();
    
    public void saveManualCQAfterPeriods();

    public Page<CQPeriodsInfo> findPage(Page<CQPeriodsInfo> page,
			Criterion[] criterions);

    /**
     * 查看当前正在运行的盘期
     * 条件:开盘后,开奖前
     **/
	public CQPeriodsInfo queryZhangdanPeriods();

	public List<CQPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row);
	
	/*public Map<String, List<Integer>> getResult();
	public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum);*/
	
	/**
	 * 获取当前封盘的盘期信息
	 * @return
	 */
	public CQPeriodsInfo findCurrentStopPeriodInfo();

	public List<CQPeriodsInfo> findAllExceptionPeriodInfo();

	/**
	 * add by peter 给重庆业务结束对投注表遗漏重新兑奖使用，获取前一天00:00:00到当天02:00:00的盘期数据
	 * @return
	 */
	public abstract List<CQPeriodsInfo> queryTodayPeriodsForEOD();

	/**
	 * 每天重庆时时彩业务结束从投注表里（关联盘期表,获得盘期对象,只获取盘期是4或者7已经开奖或兑奖的数据）获取遗漏兑奖的盘期列表，用作每日结束的重新兑奖
	 * 
	 * @return
	 */
	public abstract Map<String, CQPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme);

	/**
	 * 根据日期获取当天开奖结果有异常的盘期
	 * 
	 * @param queryDate
	 * @return
	 */
	public abstract List<CQPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate);

	//public void updateCQStatusWithCheckMiragation();
	
	public CQPeriodsInfo findCurrentPeriod();

	public List<CQPeriodsInfo> queryTodayAllPeriods();
}
