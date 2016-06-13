package com.npc.lottery.member.logic.spring;

import java.util.List;

import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.npc.lottery.member.dao.interf.IFuncDao;
import com.npc.lottery.member.logic.interf.IFuncLogic;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.util.Page;


/**
 * 功能逻辑处理类
 *
 * @author none
 *
 */
public class FuncLogic implements IFuncLogic {

	private IFuncDao funcDao = null;

	public void setFuncDao(IFuncDao funcDao) {
		this.funcDao = funcDao;
	}

	public IFuncDao getFuncDao() {
		return funcDao;
	}

	
	@Cacheable(modelId="testCaching")
	public Page<Function> findFuncPage(Page<Function> page) {
		
		System.out.println("get func from db>>....................................");
		
		return funcDao.getAll(page);
	}
	@CacheFlush(modelId="testFlushing")
	public List<Function> findFuncList()
	{
		
		System.out.println("flush cache>>>>>>>>>>>>>>>>>>>>>>.......................");
		return null;
	}

}
