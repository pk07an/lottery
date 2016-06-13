package com.npc.lottery.util;

import java.util.Comparator;

import com.npc.lottery.member.entity.PlayType;

public class LotteryComparator implements Comparator<String> {

	@Override
	public int compare(String playTypeCode1, String playTypeCode2) {
		PlayType playType1 = PlayTypeUtils.getPlayType(playTypeCode1);
		PlayType playType2 = PlayTypeUtils.getPlayType(playTypeCode2);
		int displayOrder1 = 0;
		int displayOrder2 = 0;
		if (null != playType1) {
			displayOrder1 = playType1.getDisplayOrder() != null ? playType1.getDisplayOrder() : 0;
		}
		if (null != playType2) {
			displayOrder2 = playType2.getDisplayOrder() != null ? playType2.getDisplayOrder() : 0;
		}
		return displayOrder1 - displayOrder2;
	}
	
	public int compareByCommissionType(String commissionType1, String commissionType2) {
		if (commissionType1.startsWith("GD_") && !commissionType2.startsWith("GD_")) {
			return -1;
		}
		else if (commissionType1.startsWith("CQ_") && commissionType2.startsWith("BJ_")) {
			return -1;
		}
		else if (commissionType1.startsWith("CQ_") && commissionType2.startsWith("K3_")) {
			return -1;
		}
		else if (commissionType1.startsWith("CQ_") && commissionType2.startsWith("NC_")) {
			return -1;
		}
		else if (commissionType1.startsWith("CQ_") && commissionType2.startsWith("GD_")) {
			return 1;
		}
		/*else if (commissionType1.startsWith("BJ_") && !commissionType2.startsWith("BJ_")) {
			return 1;
		}*/
		else if (commissionType1.startsWith("BJ_") && commissionType2.startsWith("K3_")) {
			return -1;
		}
		else if (commissionType1.startsWith("BJ_") && commissionType2.startsWith("NC_")) {
			return -1;
		}
		else if (commissionType1.startsWith("BJ_") && (commissionType2.startsWith("GD_")||commissionType2.startsWith("CQ_"))) {
			return 1;
		}
		else if (commissionType1.startsWith("K3_") && commissionType2.startsWith("NC_")) {
			return -1;
		}
		else if (commissionType1.startsWith("K3_") && !commissionType2.startsWith("K3_")) {
			return 1;
		}
		else if (commissionType1.startsWith("NC_") && !commissionType2.startsWith("NC_")) {
			return 1;
		}
		return PlayTypeUtils.getCommissionTypeDisplayOrder(commissionType1) - PlayTypeUtils.getCommissionTypeDisplayOrder(commissionType2);
	}

}
