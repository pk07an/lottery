package com.npc.lottery.member.dao.interf;


import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.member.entity.PlayAmount;



/**
 * 类
 *
 * @author none
 *
 */
public interface IPlayAmountDao extends ILotteryBaseDao<Long, PlayAmount>
{
	public PlayAmount queryUniqueByTypeCode(String typeCode,String shopCode,String scheme);
}
