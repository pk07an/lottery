package com.npc.lottery.periods.logic.spring;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.periods.dao.interf.IHKPeriodsDao;
import com.npc.lottery.periods.dao.interf.IHKPeriodsInfoDao;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.entity.HKPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.util.Page;

public class HKPeriodsInfoLogic implements IHKPeriodsInfoLogic {

    private IHKPeriodsInfoDao hkperiodsInfoDao;
    private IHKPeriodsDao periodsDao;
    
    
    public IHKPeriodsInfoDao getHkperiodsInfoDao() {
        return hkperiodsInfoDao;
    }

    public void setHkperiodsInfoDao(IHKPeriodsInfoDao hkperiodsInfoDao) {
        this.hkperiodsInfoDao = hkperiodsInfoDao;
    }

    @Override
    public Page<HKPeriodsInfo> findPage(Page<HKPeriodsInfo> page,Criterion... criterions) {
        return hkperiodsInfoDao.findPage(page, criterions);
    }

    @Override
    public void save(HKPeriodsInfo hkPeriodsInfo) {
        hkperiodsInfoDao.save(hkPeriodsInfo);
    }

    @Override
    public HKPeriodsInfo queryByPeriods(Long ID) {
        return hkperiodsInfoDao.get(ID);
    }

    @Override
    public void update(HKPeriodsInfo hkPeriodsInfo) {
        hkperiodsInfoDao.update(hkPeriodsInfo);
    }

    @Override
    public HKPeriodsInfo queryByHKPeriods(String periodsNum, Object number) {
        return hkperiodsInfoDao.findUniqueBy(periodsNum, number);
    }
    
    @Override
    public HKPeriodsInfo queryByPeriods(Criterion... criterias) {
        return hkperiodsInfoDao.findUnique(criterias);
    }
    @Override
    public List<HKPeriodsInfo> queryLastPeriodsForRefer() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date();
        String nowStr = sdf.format(now);
        
        String after = nowStr + " 23:59:59";
      
        Date af = java.sql.Timestamp.valueOf(after);

        String hql = "from HKPeriodsInfo where  lotteryTime<=? and state=4 order by lotteryTime desc";
        Object[] parameter = new Object[] {  af };
        return hkperiodsInfoDao.find(hql, parameter);
    }
    @Override
    public List<HKPeriodsInfo> queryAllPeriods(Criterion...criterias) {
        return hkperiodsInfoDao.find(criterias);
    }
    
    
    public HKPeriodsInfo queryByPeriodsStatus(String status)
    {
    	HKPeriodsInfo cqp=null;
    	String hql="from HKPeriodsInfo where  state=? order by openQuotTime desc ";
    	List<HKPeriodsInfo> periodsList=hkperiodsInfoDao.find(hql, status);
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    	
    }
    public HKPeriods queryRunningPeriods(String shopsCode)
    {
    	HKPeriods runningPeriods=	periodsDao.queryShopRunningPeriods(shopsCode);
    	Date now=new Date();
		if(runningPeriods!=null)
		{			
			if(runningPeriods.getStopQuotTime().getTime()<now.getTime())
			  runningPeriods=null;
			else if(runningPeriods.getOpenQuotTime().getTime()>now.getTime()) 
				runningPeriods=null;
		}
  	      return runningPeriods;
    }
    
    @Override
    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status){

	    String hql="update HKPeriodsInfo set state=? where periodsNum=? ";
	    hkperiodsInfoDao.batchExecute(hql, status,periodsNum);
	 
    }

	public IHKPeriodsDao getPeriodsDao() {
		return periodsDao;
	}

	public void setPeriodsDao(IHKPeriodsDao periodsDao) {
		this.periodsDao = periodsDao;
	}

    @Override
    public List<HKPeriodsInfo> queryReportPeriodsNum() {
        String hql = "from HKPeriodsInfo where  state=? order by createTime desc";       
        Object[] parameter = new Object[] {"4"};
        Page<HKPeriodsInfo> page = new Page<HKPeriodsInfo>();
        page.setPageSize(30);
        page.setPageNo(1);
        page = hkperiodsInfoDao.findPage(page, hql, parameter);
        return  page.getResult();
    }
    @Override
    public String queryZhangdanPeriods(String shopCode) {
    	String periodsNum = null;
    	String hql="SELECT a.periodsNum FROM HKPeriodsInfo a,HKPeriods b " +
    			      "WHERE a.ID=b.periodsInfoID AND b.shopsCode=? " +
    			      "AND a.state!=4 ORDER BY b.modifyTime DESC";
    	List<String> list=hkperiodsInfoDao.find(hql, shopCode);
    	periodsNum = list.get(0);
        return periodsNum;
    }

}
