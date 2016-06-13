package com.npc.lottery.periods.logic.interf;

import java.util.List;

import com.npc.lottery.periods.entity.HKPeriods;

public interface IHKPeriodsLogic {

    public void save(HKPeriods hkPeriods);
    
    public void update(HKPeriods hkPeriods);
    
    public HKPeriods queryByPeriods(Object periodsInfoID,Object shopsCode);
    
    public List<HKPeriods> queryAll(Object periodsInfoID);
   // public HKPeriods queryRunningPeriods();
}
