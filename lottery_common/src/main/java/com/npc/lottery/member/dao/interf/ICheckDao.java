package com.npc.lottery.member.dao.interf;

import java.util.List;

import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BetCheckVo;
import com.npc.lottery.replenish.entity.Replenish;

public interface ICheckDao {

	public void miragationJSDataToCheck(String periodsNum,String scheme);

	public void miragationNCDataToCheck(String periodsNum,String scheme);

	public void miragationBJDataToCheck(String periodsNum,String scheme);

	public void miragationCQDataToCheckTable(String periodsNum,String scheme);

	public void miragationGDDataToCheck(String periodsNum,String scheme);

	public int[] batchInsertToCheck(List<BaseBet> tempbetList, String TBName, boolean insertAttr);

	public List<BetCheckVo> getCheckVoByPeriodsNumInHisTable(String periodsNum, String lotteryType,String scheme);

	public List<BetCheckVo> getCheckVoByPeriodsNumInCheckTable(String periodsNum, String lotteryType,String scheme);

	public int getMatchCheckCountByPeriodsNum(String periodsNum, String lotteryType);

	public int getHisCountByPeriodsNum(String periodsNum, String lotteryType);

	public void insertReplenishCheck(Replenish replenish);

}
