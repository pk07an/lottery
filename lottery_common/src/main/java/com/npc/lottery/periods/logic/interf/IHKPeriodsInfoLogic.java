package com.npc.lottery.periods.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.entity.HKPeriodsInfo;
import com.npc.lottery.util.Page;
public interface IHKPeriodsInfoLogic {

    public Page<HKPeriodsInfo> findPage(Page<HKPeriodsInfo> page,Criterion... criterions);

    public void save(HKPeriodsInfo hkPeriodsInfo);

    public HKPeriodsInfo queryByPeriods(Long ID);
    
    public void update(HKPeriodsInfo hkPeriodsInfo);
    
    public HKPeriodsInfo queryByHKPeriods(String periodsNum,Object number);
    public HKPeriodsInfo queryByPeriods(Criterion...criterias);

    public List<HKPeriodsInfo> queryLastPeriodsForRefer();
    
    public List<HKPeriodsInfo> queryAllPeriods(Criterion... criterias);
    public HKPeriodsInfo queryByPeriodsStatus(String status);
    
    public HKPeriods queryRunningPeriods(String shopCode);

    public void updatePeriodsStatusByPeriodsNum(String periodsNum, String status);
    
    public List<HKPeriodsInfo> queryReportPeriodsNum();
    
    /**
     * 查看当前正在运行的盘期
     * 条件:开盘后,开奖前
     **/
    public String queryZhangdanPeriods(String shopCode);
}
