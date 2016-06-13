package com.npc.lottery.member.logic.interf;


import java.util.List;

import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.util.Page;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
public interface IFuncLogic {

	

	public Page<Function> findFuncPage(Page<Function> page);
	public List<Function> findFuncList();




}
