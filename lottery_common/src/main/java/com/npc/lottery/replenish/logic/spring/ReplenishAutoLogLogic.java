package com.npc.lottery.replenish.logic.spring;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.replenish.dao.interf.IReplenishAutoLog;
import com.npc.lottery.replenish.entity.ReplenishAutoLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogLogic;
import com.npc.lottery.util.Page;

public class ReplenishAutoLogLogic implements IReplenishAutoLogLogic {

	 private IReplenishAutoLog replenishAutoLogDao;
	
	 
	 private static final Logger LOG = Logger.getLogger(ReplenishAutoLogLogic.class);

	@Override
	public Page<ReplenishAutoLog> queryLogByPage(Page<ReplenishAutoLog> page,Criterion... criterions) {
		
		return replenishAutoLogDao.findPage(page, criterions);
	}
	 
	@Override
	public List<ReplenishAutoLog> queryReplenishLogList(Criterion... criterions) {
		return replenishAutoLogDao.find(criterions);
	}

	@Override
	public void updateReplenishAuto(ReplenishAutoLog entity) {

		
	}

	@Override
	public void saveReplenishLog(ReplenishAutoLog entity) {

		replenishAutoLogDao.save(entity);
	}
	
	@Override
	public void saveReplenishLogByScheme(ReplenishAutoLog entity,String scheme) {

		replenishAutoLogDao.saveReplenishLogByScheme(entity,scheme);
	}

	public IReplenishAutoLog getReplenishAutoLogDao() {
		return replenishAutoLogDao;
	}

	public void setReplenishAutoLogDao(IReplenishAutoLog replenishAutoLogDao) {
		this.replenishAutoLogDao = replenishAutoLogDao;
	}

}
