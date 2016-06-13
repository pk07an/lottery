package com.npc.lottery.user.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.StockholderStaffExt;

public interface IStockholderStaffExtDao extends ILotteryBaseDao<Long, StockholderStaffExt>{
	public StockholderStaffExt findById(long id, String scheme);
}
