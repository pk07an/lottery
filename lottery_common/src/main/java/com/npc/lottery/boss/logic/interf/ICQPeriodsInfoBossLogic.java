package com.npc.lottery.boss.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.util.Page;

public interface ICQPeriodsInfoBossLogic {

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
    public Page<CQPeriodsInfo> queryHistoryPeriodsForBoss(Page<CQPeriodsInfo> page,String state);
    
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
}
