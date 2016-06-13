package com.npc.lottery.odds.dao.interf;

public interface IOpenPlayOddsJdbcDao {
	
	/**
	 * 把开盘赔率更新实时赔率
	 * 把TB_OPEN_PLAY_ODDS。OPENING_ODDS更新TB_SHOPS_PLAY_ODDS.REAL_ODDS
	 * 
	 * @param shopCode
	 */
	public void updateRealOddsFromOpenOdds(String shopCode,String type);
	/**
	 * 新建商铺时初始化开盘赔率表
	 * @param shopCode
	 * @param userId
	 */
	public void saveOpenPlayOddsForAddShop(String shopCode,Long userId) ;
}
