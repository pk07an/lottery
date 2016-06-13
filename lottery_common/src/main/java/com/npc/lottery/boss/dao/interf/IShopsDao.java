package com.npc.lottery.boss.dao.interf;


import java.util.List;
import java.util.Map;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.dao.ILotteryBaseDao;

public interface IShopsDao  extends ILotteryBaseDao<Long, ShopsInfo>{
	//根据商铺号查询商铺信息
	public ShopsInfo findShopsCode(String shopsCode);
	//根据商铺名称查询商铺信息
	public ShopsInfo findShopsName(String shopsName);
	//查询所有商铺的shopCode集合
	public List<String> findAllShopsCode();

	/**
	 * 查询所有商铺
	 * @return
	 */
	public List<ShopsInfo> findShopsAll(Map<String,String> schemeMap);
	
	/**
	 * 根据商铺号查询商铺信息 jdbc查询
	 * @param shopsCode
	 * @return
	 */
	public ShopsInfo findShopsInfoByCode(String shopsCode);
	
}
