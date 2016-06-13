package com.npc.lottery.periods.logic.spring;

import java.util.List;

import com.npc.lottery.periods.dao.interf.IHKPeriodsDao;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.logic.interf.IHKPeriodsLogic;

public class HKPeriodsLogic implements IHKPeriodsLogic{

    private IHKPeriodsDao periodsDao;
    
    @Override
    public void save(HKPeriods hkPeriods) {
        periodsDao.save(hkPeriods);
    }

    @Override
    public void update(HKPeriods hkPeriods) {
        periodsDao.update(hkPeriods);
    }

    public IHKPeriodsDao getPeriodsDao() {
        return periodsDao;
    }

    public void setPeriodsDao(IHKPeriodsDao periodsDao) {
        this.periodsDao = periodsDao;
    }

    @Override
    public HKPeriods queryByPeriods(Object periodsInfoID,Object shopsCode) {
        return periodsDao.findUnique("from HKPeriods where periodsInfoID = ? and shopsCode = ?", periodsInfoID,shopsCode);
    }

    @Override
    public List<HKPeriods> queryAll(Object periodsInfoID) {
        return  periodsDao.find("from HKPeriods where periodsInfoID = ?", periodsInfoID);
    }
    
   
    
}
