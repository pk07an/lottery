/**
 * 
 */
package com.npc.lottery.odds.logic.spring;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.odds.dao.interf.IOpenPlayOddsDao;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.logic.interf.IOpenPlayOddsLogic;

/**
 * @author Administrator
 *
 */
public class OpenPlayOddsLogic implements IOpenPlayOddsLogic {

	private  IOpenPlayOddsDao openPlayOddsDao;
	
	@Override
	public OpenPlayOdds findOpenPlayOdds(Criterion...criterions) {
		// TODO Auto-generated method stub
		
		return openPlayOddsDao.findUnique(criterions);
	}

	public IOpenPlayOddsDao getOpenPlayOddsDao() {
		return openPlayOddsDao;
	}

	public void setOpenPlayOddsDao(IOpenPlayOddsDao openPlayOddsDao) {
		this.openPlayOddsDao = openPlayOddsDao;
	}
 
	
}
