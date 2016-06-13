package com.npc.lottery.odds.dao.interf;

import java.util.Date;
import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.util.Page;

public interface IShopOddsDao extends ILotteryBaseDao<Long, ShopsPlayOdds>{
	public List<ShopsPlayOdds> queryShopRealOdds(String shopCode,String playType);
	public List<ShopsPlayOdds> queryOddsByTypeCode(String shopCode,String typeCode);
	/*
	 * 新建商铺时补始化商铺赔率表
	 */
	public void saveShopsPlayOddsForAddShop(String shopCode,Long userId);
    //根据第几球统计
	public List<ShopsPlayOdds> queryShopRealOddsGroupByBall(String shopCode,String playType,String playType2);
	/**
	 * 此接口可以把要查詢的typeCode放進list里，循環查詢
	 * @param shopCode
	 * @param list<String>
	 * @return
	 */
	public List<ShopsPlayOdds> queryShopRealOddsByLoop(String shopCode,List<String> list);
	/**
	 * 查询实时赔率操盘日志
	 * @param shopCode
	 * @param periodsNum
	 * @param typeCode
	 * @param prevSearchTime
	 * @param opType 操作类型  1、 --自动Constant.ODD_LOG_AUTO 2、--手动Constant.ODD_LOG_MENU 
	 * @return
	 */
	public Page queryShopsPlayOddsLog(Page page, String shopCode,String periodsNum,String typeCode,Date prevSearchTime,String opType);
	
	/**
	 * 根据shopCode查询出已经停盘的球号
	 * @param shopCode
	 * @param playTypePerfix
	 * @return
	 */
	public List<ShopsPlayOdds> getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(String shopCode,String playTypePerfix);
	public void updateRealOddsBatchById(List<ShopsPlayOdds> shopsPlayOddsList);
	public List<ShopsPlayOdds> getCurrentShopRealOddsAndId(String shopCode, String playType);
}
