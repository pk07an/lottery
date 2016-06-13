package com.npc.lottery.member.logic.interf;

import com.npc.lottery.boss.entity.ShopsInfo;

public interface ICheckLogic {
	public void miragationJSDataToCheck(String periodsNum,String scheme);

	public void miragationNCDataToCheck(String periodsNum,String scheme);

	public void miragationBJDataToCheck(String periodsNum,String scheme);

	public void miragationCQDataToCheckTable(String periodsNum,String scheme);

	public void miragationGDDataToCheck(String periodsNum,String scheme);

	public boolean checkReplenishWithHistory(String periodsNum,String scheme);

	public boolean checkBetWithBackupByPeridosNum(String periodsNum, String lotteryType,String scheme);

}
