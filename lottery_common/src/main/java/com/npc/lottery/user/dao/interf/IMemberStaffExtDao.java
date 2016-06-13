package com.npc.lottery.user.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.MemberStaffExt;

public interface IMemberStaffExtDao extends ILotteryBaseDao<Long, MemberStaffExt> {

	public abstract String getMemberFlagById(long id);

	public abstract void updateMemberAvailableCreditLineById(double availableCreditLine, long id);

	public int updateMemberAvailableCreditToTotal(String scheme);

	/** 用于总管盘期停开或作废，更新会员可用额度，在原有基础上 加上
	 * @param availableCreditLine
	 * @param id
	 * @param scheme
	 * @return
	 */
	public void updateMemberAvailableCreditLineByAdd(double availableCreditLine,long id,String scheme);
}
