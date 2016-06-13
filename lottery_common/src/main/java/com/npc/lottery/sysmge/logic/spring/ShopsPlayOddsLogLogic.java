package com.npc.lottery.sysmge.logic.spring;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.dao.interf.IShopsPlayOddsLogDao;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.util.Page;

public class ShopsPlayOddsLogLogic implements IShopsPlayOddsLogLogic {

	private IShopsPlayOddsLogDao shopsPlayOddsLogDao;

	@Override
	public void saveLog(ShopsPlayOddsLog log) {
		// TODO Auto-generated method stub
		shopsPlayOddsLogDao.save(log);
	}

	@Override
	public Page<ShopsPlayOddsLog> queryLogByPage(Page<ShopsPlayOddsLog> page,Criterion... criterions) {
		// TODO Auto-generated method stub
		
		return shopsPlayOddsLogDao.findPage(page, criterions);
	}

	public IShopsPlayOddsLogDao getShopsPlayOddsLogDao() {
		return shopsPlayOddsLogDao;
	}

	public void setShopsPlayOddsLogDao(IShopsPlayOddsLogDao shopsPlayOddsLogDao) {
		this.shopsPlayOddsLogDao = shopsPlayOddsLogDao;
	}

	@Override
	public void saveLogByScheme(ShopsPlayOddsLog log, String scheme) {
		shopsPlayOddsLogDao.saveLogByScheme(log,scheme);
	}

}
