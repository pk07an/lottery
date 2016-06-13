package com.npc.lottery.periods.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.periods.entity.CQPeriodsInfo;

public interface ICQPeriodsInfoDao  extends ILotteryBaseDao<Long, CQPeriodsInfo>{
	/**
	 * 每天重庆时时彩业务结束从投注表里（关联盘期表,获得盘期对象,只获取盘期是4或者7已经开奖或兑奖的数据）获取遗漏兑奖的盘期列表，用作每日结束的重新兑奖
	 * @return
	 */
	public abstract List<CQPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme);
}
