package com.npc.lottery.odds.logic.interf;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.Page;
public interface IShopOddsLogic {

   
    
    public List<ShopsPlayOdds> queryCQSSCRealOdds(String shopCode,String like);
    
    public List<ShopsPlayOdds> queryBJSCRealOdds(String shopCode,String like);
    
    public List<ShopsPlayOdds> queryGDKLSFRealOdds(String shopCode,String like);
    /**
	 * 此接口可以把要查詢的typeCode放進list里，循環查詢
	 * @param shopCode
	 * @param list<String>
	 * @return
	 */
    public List<ShopsPlayOdds> queryShopRealOddsByLoop(String shopCode,List<String> list);
    
    public List<ShopsPlayOdds> queryGDKLSFRealOddsGroupByBall(String shopCode,String playType,String playType2);
    
    public List<ShopsPlayOdds> queryHKRealOdds(String shopCode,String like);
    
    public List<ShopsPlayOdds> queryCQSSCOdds(String shopCode,String likeTypeCode);
    
    public List<ShopsPlayOdds> queryGDKLSFOdds(String shopCode,String likeTypeCode);
    public List<ShopsPlayOdds> queryHKOdds(String shopCode,String likeTypeCode);
    
    //public List<ShopsPlayOdds> queryHKLMOdds(String shopCode);
    
    public List<ShopsPlayOdds> queryOddsByTypeCode(String shopCode,String typeCode);
    
    /**
     * 修改shopsOdds根據playCode查找
     * @param shopOdds
     */
    public void updateShopOdds(List<ShopsPlayOdds> shopOdds);

    public  ShopsPlayOdds queryShopPlayOdds(String shopsCode,String playTypeCode);
    
    public  ShopsPlayOdds queryPlateRealShopPlayOdds(String shopsCode,String playTypeCode,String plate);
    
    public void updateShopOdds(ShopsPlayOdds shopOdds);
    
    public List<OpenPlayOdds> queryCQSSCOpenOdds(String shopsCode);
    
    public List<OpenPlayOdds> queryBJSCOpenOdds(String shopsCode);
    
    public List<OpenPlayOdds> queryK3OpenOdds(String shopsCode);
    
    public List<OpenPlayOdds> queryGDKLSFOpenOdds(String shopsCode);
    
    public List<OpenPlayOdds> queryNCOpenOdds(String shopsCode);
    
    public List<OpenPlayOdds> queryHKOpenOdds(String shopsCode);
    
    public OpenPlayOdds queryOpenPlayOdds(String oddType,String shopCode);
   
    
    public void updateGdOpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode);
    
    public void updateBjOpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode);
    
    public void updateK3OpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode);
    
    public void updateCqOpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode);
    //获取当前登录总监的商铺号
    public String getCurrentShopsCode();
    //通过商铺号查总监
    public List<ChiefStaffExt> findChiefByShopsCode(String shopsCode);
    
    /**
     * 通过商铺号和scheme查总监
     * @param shopsCode
     * @param scheme
     * @return
     */
    public List<ChiefStaffExt> findChiefByShopsCodeByScheme(String shopsCode,String scheme);

    /**
     * 修改香港六合彩 特码 封号
     * @param state
     * @param shopsCode
     */
    public void updateShopOddsState(String state, String shopsCode, String oddsType);
    /**
     * 修改香港六合彩 正碼 封号
     * @param state
     * @param shopsCode
     */
    public void updateShopOddsStateZMA(String state, String shopsCode);
    /**
     *  修改香港六合彩 正特碼 封号
     * @param state
     * @param shopsCode
     * @param ballNum
     */
    public void updateShopOddsStateZT(String state, String shopsCode, String ballNum);
    /**
     * 封号
     * @param state   1:已封号  0:未封号
     * @param typeCode
     * @param shopsCode
     */
    public void updateFengHao(String state,String typeCode,String shopsCode);
    /**
     * 根據 oddsTypeX 字段查詢
     * @param shopsCode
     * @param playTypeCode
     * @return
     */
    public ShopsPlayOdds queryShopPlayOddsByTypeX(String shopsCode, String playTypeCode);
    /**
     * 修改shopsOdds根據typeX查找
     * @param shopOdds
     */
    public void updateShopOddsByTypeX(List<ShopsPlayOdds> shopOdds);

    public ShopsPlayOdds queryShopPlayOddsByTypeCodeTypeX(String shopsCode,
			String playTypeCode, String typeX);
    /**
     * 修改shopsOdds根據playTypeCode typeX查找
     * @param shopOdds
     */
    public void updateShopOddsByTypeCodeTypeX(List<ShopsPlayOdds> shopOdds);
    /**
     * playTypeCode查找 所以商鋪odds
     * @param shopOdds
     */
    public List<ShopsPlayOdds> queryShopPlayOddsListByTypeCode(String playTypeCode,String shopCode);

    public List<ShopsPlayOdds> queryShopPlayOddsListByLike(String playTypeCode);

	public  void updateShopOddsRealOddsByPlayTypeCode(String playTypeCode,
			BigDecimal realOdds,String shopsCode);

	public void setRunningPeriodsNum(String runningPeriodsNum);
	/**
	 * 把开盘赔率更新实时赔率(广东和重庆)
	 * @param type 如广东GD,重庆QC
	 */
	public void updateRealOddsFromOpenOddsForGD(String type);
	//把开盘赔率更新实时赔率(香港)
	public void updateRealOddsFromOpenOddsForHK(String shopCode);

	public void updateShopOddsByObj(ShopsPlayOdds shopOdds);
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
	 */
	public List<ShopsPlayOdds> getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(String shopCode,String playTypePerfix);

	public Map<String, ShopsPlayOdds> getCurrentShopRealOddsAndId(String shopCode,String playType);

	public void updateRealOddsBatchById(List<ShopsPlayOdds> shopsPlayOddsList);

	public Map<String, OpenPlayOdds> queryOpenPlayOddsMapByShopCode(String shopCode);

	public Map<String, BigDecimal> queryPlateRealOddsMap(String shopsCode, String playTypeCode, String plate);
}
