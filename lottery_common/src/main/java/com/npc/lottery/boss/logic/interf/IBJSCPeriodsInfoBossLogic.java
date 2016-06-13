package com.npc.lottery.boss.logic.interf;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.util.Page;

public interface IBJSCPeriodsInfoBossLogic {

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
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriodsForBoss(Page<BJSCPeriodsInfo> page,String state);
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
    
}
