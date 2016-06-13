package com.npc.lottery.member.dao.interf;


import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.sysmge.entity.Function;



/**
 * 角色所拥有的功能数据库处理类
 *
 * @author none
 *
 */
public interface IFuncDao extends ILotteryBaseDao<Long, Function>
{
	
	//public Page<Function> findFuncPage(Page<Function> page);
	//public List<Function> findFuncList();

}
