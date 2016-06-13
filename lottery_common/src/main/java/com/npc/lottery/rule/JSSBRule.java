/**
 * 
 */
package com.npc.lottery.rule;

import java.util.List;

import com.npc.lottery.member.entity.PlayType;

/**
 * @author peteran
 * 
 */
public class JSSBRule {

	/**
	 * 首先判断是都围骰，如果是的话返回围骰的点数，否则返回0
	 * 
	 * @param winNums
	 * @return
	 */
	public static int getWSNum(List<Integer> winNums) {
		int wsNum = 0;
		if (JSSBRule.isWS(winNums)) {
			wsNum = winNums.get(0);
		}
		return wsNum;
	}

	public static boolean isWS(List<Integer> winNums) {
		boolean flag = false;
		int num1 = winNums.get(0);
		int num2 = winNums.get(1);
		int num3 = winNums.get(2);
		if (num1 == num2 && num2 == num3) {
			flag = true;
		}
		return flag;
	}

	public static int getSJRealOdd(PlayType type, List<Integer> winNums) {

		int returnOdd = 0;
		String playTypeCode = type.getTypeCode();
		if (playTypeCode.startsWith("K3_SJ")) {
			int sjNum = Integer.valueOf(playTypeCode.split("_")[2]);
			if (JSSBRule.isSJplayTypeWin(sjNum, winNums)) {
				int count = 0;
				for (int openNum : winNums) {
					if (sjNum == openNum) {
						count++;
					}
				}
				returnOdd = count;
			}
		}

		return returnOdd;
	}

	public static boolean isSJplayTypeWin(int sjNum, List<Integer> winNums) {
		boolean flag = false;
		if (winNums.contains(sjNum)) {
			flag = true;
		}
		return flag;
	}

	public static int getWinNumsSum(List<Integer> winNums) {
		int sum = 0;
		for (int winNum : winNums) {
			sum = sum + winNum;
		}
		return sum;
	}

	public static boolean isWinNumsSumEqualDA(int sum) {
		boolean flag = false;
		if (sum >= 11 && sum <= 17) {
			flag = true;
		}
		return flag;
	}

	public static boolean isWinNumsSumEqualX(int sum) {
		boolean flag = false;
		if (sum >= 4 && sum <= 10) {
			flag = true;
		}
		return flag;
	}

	public static boolean isCPNumWin(int cpNum1, int cpNum2, List<Integer> winNums) {
		boolean flag = false;
		if (winNums.contains(cpNum1) && winNums.contains(cpNum2) && cpNum1 != cpNum2) {
			flag = true;
		}
		return flag;
	}

	public static int winNumRepeatTimes(int num, List<Integer> winNums) {
		int count = 0;
		for (int numTmp : winNums) {
			if (num == numTmp) {
				count++;
			}
		}
		return count;
	}

	public static boolean getBetResult(PlayType type, List<Integer> winNums) {
		String playTypeCode = type.getTypeCode();
		boolean betResult = false;
		// 如果开奖时围骰的话，只需要和三军/围骰/全骰/短牌/点数兑奖
		if (JSSBRule.isWS(winNums)) {
			if (playTypeCode.startsWith("K3_SJ")) {// 三军兑奖
				int sjNum = Integer.valueOf(playTypeCode.split("_")[2]);
				betResult = JSSBRule.isSJplayTypeWin(sjNum, winNums);
			} else if (playTypeCode.startsWith("K3_WS")) {// 围骰
				int wsNum = Integer.valueOf(playTypeCode.split("_")[2]);
				if (JSSBRule.getWSNum(winNums) == wsNum) {
					betResult = true;

				}
			} else if (playTypeCode.startsWith("K3_QS")) {// 全骰
				betResult = true;
			} else if (playTypeCode.startsWith("K3_DP")) {// 短牌
				int dpNum = Integer.valueOf(playTypeCode.split("_")[2]);
				if (JSSBRule.winNumRepeatTimes(dpNum, winNums) == 3) {
					betResult = true;
				}
			} else if (playTypeCode.startsWith("K3_DS")) {// 点数
				int sum = JSSBRule.getWinNumsSum(winNums);// 点数总和
				int dsNum = Integer.valueOf(playTypeCode.split("_")[2]);
				if (dsNum == sum) {
					betResult = true;
				}
			}

		} else {// 如果不是围骰
			int sum = JSSBRule.getWinNumsSum(winNums);// 点数总和
			if (playTypeCode.startsWith("K3_SJ")) {// 三军兑奖
				int sjNum = Integer.valueOf(playTypeCode.split("_")[2]);
				betResult = JSSBRule.isSJplayTypeWin(sjNum, winNums);
			} else if (playTypeCode.startsWith("K3_DS")) {// 点数
				int dsNum = Integer.valueOf(playTypeCode.split("_")[2]);
				if (dsNum == sum) {
					betResult = true;
				}
			} else if (playTypeCode.startsWith("K3_CP")) {// 长牌
				int cpNum1 = Integer.valueOf(playTypeCode.split("_")[2]);
				int cpNum2 = Integer.valueOf(playTypeCode.split("_")[3]);
				if (JSSBRule.isCPNumWin(cpNum1, cpNum2, winNums)) {
					betResult = true;
				}
			} else if (playTypeCode.startsWith("K3_DP")) {// 短牌
				int dpNum = Integer.valueOf(playTypeCode.split("_")[2]);
				if (JSSBRule.winNumRepeatTimes(dpNum, winNums) == 2) {
					betResult = true;
				}
			} else if (playTypeCode.startsWith("K3_DA")) {
				if (JSSBRule.isWinNumsSumEqualDA(sum)) {
					betResult = true;
				}
			} else if (playTypeCode.startsWith("K3_X")) {
				if (JSSBRule.isWinNumsSumEqualX(sum)) {
					betResult = true;
				}
			}
		}
		return betResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
