package com.npc.lottery.sysmge.logic.spring;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.dao.interf.IBossLogDao;
import com.npc.lottery.sysmge.entity.BossLog;
import com.npc.lottery.sysmge.logic.interf.IBossLogLogic;
import com.npc.lottery.util.Page;

public class BossLogLogic implements IBossLogLogic {

	private IBossLogDao bossLogDao;
	
	private String type = "explog";
	
	@Override
	public void saveLog(BossLog log) {
		// TODO Auto-generated method stub
		bossLogDao.save(log);
	}

	@Override
	public Page<BossLog> queryLogByPage(Page<BossLog> page,
			Criterion... criterions) {
		// TODO Auto-generated method stub
		return bossLogDao.findPage(page, criterions);
	}

	public IBossLogDao getBossLogDao() {
		return bossLogDao;
	}

	public void setBossLogDao(IBossLogDao bossLogDao) {
		this.bossLogDao = bossLogDao;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 
}
