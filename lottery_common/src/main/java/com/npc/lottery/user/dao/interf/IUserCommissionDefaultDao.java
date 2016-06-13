package com.npc.lottery.user.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.UserCommissionDefault;

public interface IUserCommissionDefaultDao extends ILotteryBaseDao<Long, UserCommissionDefault> {

	public List<UserCommissionDefault> getDefaultCommissionMapByPlayFinalType(String playFinalType, Long chiefId);

	public List<UserCommissionDefault> queryCommissionDefaultById(long userId,String scheme);
}
