package com.npc.lottery.member.logic.interf;

import java.util.Date;
import java.util.List;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
public interface ILotteryResultLogic {

	//兑奖接口
	public void updateLotteryGD(String periodNum, List<Integer> winNums, String scheme);
	public void updateLotteryCQ(String periodNum, List<Integer> winNums, String scheme);
	public void updateLotteryBJSC(String periodNum, List<Integer> winNums, String scheme);
	public void updateLotteryJSSB(String periodNum, List<Integer> winNums, String scheme);
	public void updateLotteryNC(String periodNum, List<Integer> winNums, String scheme);

	// 投注+历史重新兌獎接口
	public void updateSecondLotteryGD(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme)
	        throws Exception;
	public void updateSecondLotteryCQ(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme)
	        throws Exception;
	public void updateSecondLotteryBJ(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme)
	        throws Exception;// create by Eric
	public void updateSecondLotteryJS(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme)
	        throws Exception;// add by peter
	public void updateSecondLotteryNC(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme)
	        throws Exception;

	//投注表重新兑奖
	public void updateGDForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme);
	public void updateCQForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme);
	public void updateBJForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme);
	public void updateJSForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme);
	public void updateNCForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime, String scheme);
	public boolean saveReportPeriod(String periodsNum, String lotteryType, String scheme);

	public void updatePlayTypeWin(String periodNum, List<Integer> winNums, String playType);
	public void miragationLotDataToHistory(String periodNum, String playType,String scheme);
}
