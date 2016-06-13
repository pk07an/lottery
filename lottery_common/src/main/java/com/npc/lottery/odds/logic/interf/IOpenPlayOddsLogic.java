package com.npc.lottery.odds.logic.interf;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.odds.entity.OpenPlayOdds;

public interface IOpenPlayOddsLogic {

	public OpenPlayOdds findOpenPlayOdds(Criterion[] criterions);
	
}
