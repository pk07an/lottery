package com.npc.lottery.periods.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.util.Page;

public interface IBJSCPeriodsInfoLogic {

    public List<BJSCPeriodsInfo> queryAllPeriods();
    
    public BJSCPeriodsInfo queryByPeriods(String periodsNum,Object number);
    
    public BJSCPeriodsInfo queryPeriods(Object periodsNum,Object status);

    public void saveBJSCPeriods();
    
    public void saveBJSCPeriods(String startPeriodNum,Date startTime,int periodCnt);
    
    public void save(BJSCPeriodsInfo gdPeriodsInfo);
    
    public List<BJSCPeriodsInfo> queryAllPeriods(Criterion... criterias);
    
    public BJSCPeriodsInfo queryByPeriods(Criterion...criterias);
    
    public BJSCPeriodsInfo queryByPeriodsStatus(String status);
    
    public BJSCPeriodsInfo updateCommon(String commonDate);
    public List<BJSCPeriodsInfo> queryTodayPeriods();
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriods(Page<BJSCPeriodsInfo> page);
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriodsForBoss(Page<BJSCPeriodsInfo> page);
    //保存修改广东快乐十分的开奖结果
    public void updateLotResult(BJSCPeriodsInfo gdPeriodsInfo);
    //封盘
    public BJSCPeriodsInfo updateBJSCStatus();
    public BJSCPeriodsInfo queryLastLotteryPeriods();
    public BJSCPeriodsInfo queryLastNotOpenPeriods();
    public List<BJSCPeriodsInfo> queryLastPeriodsForRefer();
    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status);
    public BJSCPeriodsInfo queryStopPeriods(String status);
    
    public void update(BJSCPeriodsInfo gdPeriodsInfo);
    
    public List<BJSCPeriodsInfo> queryByBJSCPeriods(String periodsNum,String number);
    
    public void deletePeriods(String beginPeriodsNum,String endPeriodsNum);
    
    public List<BJSCPeriodsInfo> queryReportPeriodsNum();
    
    public List<BJSCPeriodsInfo> queryPeriods(String beginPeriodsNum,String endPeriodsNum);

    public List<BJSCPeriodsInfo> queryTodayAllPeriods();

    public Page<BJSCPeriodsInfo> findPage(Page<BJSCPeriodsInfo> page,
			Criterion[] criterions);

    /**
     * 查看当前正在运行的盘期
     * 条件:开盘后,开奖前
     **/
	public BJSCPeriodsInfo queryZhangdanPeriods();

	public List<BJSCPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row);
	
	public List<BJSCPeriodsInfo> queryExceptionPeriods();
	
	/**
	 * 从第三方网站获取广东快乐十分开奖信息
	 * @return
	 */
	/*public Map<String, List<Integer>> getResult();
	public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum);*/
	
	/**
	 * 获取当前封盘的盘期信息
	 * @return
	 */
	public BJSCPeriodsInfo findCurrentStopPeriodInfo();

	public List<BJSCPeriodsInfo> findAllExceptionPeriodInfo();

	public List<BJSCPeriodsInfo> queryTodayPeriodsForEOD();
    
	/**
	 * 每天北京业务结束从投注表里（关联盘期表,获得盘期对象,只获取盘期是4或者7已经开奖或兑奖的数据）获取遗漏兑奖的盘期列表，用作每日结束的重新兑奖
	 * 
	 * @return
	 */
	public Map<String, BJSCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme);

	/**
	 * 根据日期获取当天开奖结果有异常的盘期
	 * @param queryDate
	 * @return
	 */
	public List<BJSCPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate);

	//public BJSCPeriodsInfo updateBJSCStatusWithCheckMiragation();
	
	public BJSCPeriodsInfo findCurrentPeriod();
}
