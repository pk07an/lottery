package com.npc.lottery.replenish.logic.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.entity.ReplenishCheck;
import com.npc.lottery.replenish.entity.ReplenishHis;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.util.Page;

public interface IReplenishLogic {
	
	//根据用户信息得到用于计算补货的相关信息
	public UserVO getUserType(ManagerUser userInfo);

    /**
     * 查询该大类玩法的FINALTYPE的投注统计
     * @param averageLoseMoney  平均亏损值 
     * @param tableName         要查询的表名
     * @param userInfo          用户信息PO
     * @param plate             盘口
     * @param periodsNum        期数
     * @param searchType        虚注还是实占
     * @param ballNum           如1、2、3
     * @param preName           如FIRST、SECOND
     * @return PlayTypeTotalVO 类型的 List
     * 
     */
    public List<ReplenishVO> findReplenishPetList(String tableName,
    		                 ManagerUser userInfo,String plate,String periodsNum,String searchType,String ballNum,String preName);
 
    public void saveReplenishSubmit(Replenish replenish);
    
    /**
     * 补货信息保存，使用scheme操作
     * @param replenish
     * @param scheme
     */
    public void saveReplenishSubmitByScheme(Replenish replenish,String scheme);
    
	public List<Replenish> queryFinishReplenish(String userType,Long replenishUserId,String typeCode,String periodsNum,String plate);
    //合数龙虎
	public List<ReplenishVO> findReplenishPetListForLh(String tableName, ManagerUser userInfo, String plate,
			String periodsNum, String searchType);
    //连码
	public List<ReplenishVO> queryReplenish_LM(String tableName,ManagerUser userInfo, String plate, String periodsNum,String searchType, String typeCode);
	
	public List<ReplenishVO> findReplenishPetList_CQ(ManagerUser userInfo, String plate, String periodsNum,String searchType);

	public List<Replenish> queryFinishReplenish_LM(String userType,
			Long replenishUserId, String typeCode, String periodsNum,
			String plate, String attribute);
	
	public Boolean varifyData(Integer rMoney, ManagerUser userInfo,
			String plate, String periodsNum, String typeCode,String attribute);

	public List<Replenish> findReplenish(Criterion...criterions);
	
	public List<ReplenishCheck> findReplenishCheck(Criterion...criterions);

	public Map<String, Replenish> saveReplenish(Replenish replenish, ManagerUser userInfo, String periodsNum,String str
			,String isQuick,String isAutoReplenish);
	
	/**
	 * 新方法，使用scheme操作
	 * @param replenish
	 * @param userInfo
	 * @param periodsNum
	 * @param str
	 * @param isQuick
	 * @param isAutoReplenish
	 * @param scheme
	 * @return
	 */
	public Map<String, Replenish> saveReplenishByScheme(Replenish replenish, ManagerUser userInfo, String periodsNum,String str
			,String isQuick,String isAutoReplenish,String scheme);
	/**
	 * 查询投注明细
	 * @param page
	 * @param userId
	 * @param typeCode  typeCode为null，就可以查询广东全部投注类型
	 * @param periodsNum
	 * @param rateUser  占成者的字段名
	 * @param userType
	 * @param subType   大类，用常量，如Constant.LOTTERY_TYPE_GDKLSF
	 * @return
	 */
	public Page queryBetDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum,String subType);
	
	/**
	 * 查询补货明细(下级补入)
	 * @param page
	 * @param userId
	 * @param typeCode  typeCode为null，此条件就不执行,就可以查询广东全部投注类型
	 * @param periodsNum
	 * @param rateUser  占成者的字段名
	 * @param userType
	 * @param commissionType 传入null,此条件就不执行
	 * @param startDate,endDate 传入null,此条件就不执行
	 * @param winState 补货单的状态
	 * @param opType 判断查询的对象是已结算琮是未结算  已结算:cleared 未结算:unclear
	 * @return
	 */
	public Page queryReplenishInDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum,
			String commissionType,Date startDate,Date endDate,String winState,String opType,String tableName);
	
	public Page queryReplenishOutDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum);

	public List<ReplenishVO> queryReplenish_LM_Attribute(String tableName,
			ManagerUser userInfo, String plate, String periodsNum,
			String searchType, String typeCode);

	public List<ReplenishVO> findReplenishPetList_BJ(ManagerUser userInfo,
			String plate, String periodsNum, String searchType);

	public List<ReplenishVO> findReplenishPetList_BJ_Other(ManagerUser userInfo,
			String plate, String periodsNum, String searchType, String type);
    /**
     * 查询实时滚单投注明细
     * @param page
     * @param currentUserInfo
     * @param typeCode   如果为NULL,即查询某个彩种的所有类型的投注明细
     * @param periodsNum
     * @param subType    彩种
     * @param prevSearchTime  上次查询的时间，为null时即查全部
     * @param money  下注金額
     * @return
     */
	public Page<DetailVO> queryBetDetail_RealTime(Page<DetailVO> page,
			ManagerUser currentUserInfo, String typeCode, String periodsNum,
			String subType,Date prevSearchTime,Integer money);

	public Page<DetailVO> queryReplenishInDetail_RealTime(
			Page<DetailVO> pageIn, ManagerUser currentUserInfo,
			String typeCode, String periodsNum,Date prevSearchTime,Integer money);

	public Page<DetailVO> queryReplenishOutDetail_RealTime(
			Page<DetailVO> pageOut, ManagerUser currentUserInfo,
			String typeCode, String periodsNum,Date prevSearchTime,Integer money);
	
	/**
	 * 新方法，将上面两方法合并一起查询
	 * @param pageOut
	 * @param currentUserInfo
	 * @param typeCode
	 * @param periodsNum
	 * @param prevSearchTime
	 * @param money
	 * @return
	 */
	public Page<DetailVO> queryReplenishInAndOutDetail_RealTime(
			Page<DetailVO> pageOut, ManagerUser currentUserInfo,
			String typeCode, String periodsNum,Date prevSearchTime,Integer money);
	
	public Page<DetailVO> queryBetDetail_Backup(Page<DetailVO> page,
			ManagerUser currentUserInfo, String typeCode, String periodsNum,
			String subType);
	
	public Page<DetailVO> queryReplenishInDetail_Backup(
			Page<DetailVO> pageIn, ManagerUser currentUserInfo,
			String typeCode, String periodsNum);
	
	public Page<DetailVO> queryReplenishOutDetail_Backup(
			Page<DetailVO> pageOut, ManagerUser currentUserInfo,
			String typeCode, String periodsNum);
	
	public Page<Replenish> findReplenishPet(Page<Replenish> page,Criterion... criterions);
	//此方法是为补货准备上级的占成
	public Replenish readyReplenishDataForRate(Replenish replenish,ManagerUser userInfo);
	//此方法是为补货准备上级的拥金
	public Replenish readyReplenishDataForCommission(Replenish replenish,ManagerUser userInfo);
	//此方法是为补货准备上级的拥金,jdbc操作
	public Replenish readyReplenishDataForCommission(Replenish replenish,ManagerUser userInfo,String scheme);
	//此方法是为补货准备上级的userID
	public Replenish readyReplenishDataForUserId(Replenish replenish,ManagerUser userInfo);
	//根据号码统计遗漏
	public Map<String,ZhanDangVO> notAppearCnt(Map<String,ZhanDangVO> map);
	/**
	 * 查询单个管理用户，某种玩法的下级补入总额
	 * @param userID     用户ID
	 * @param userType   用户类型
	 * @param typeCode   玩法类型 
	 * @param periodsNum 盘期
	 * @return
	 */
	public Integer queryReplenishForBetCheck(Long userID,String userType,String typeCode,String periodsNum);
	
	/**
	 * 查询单个管理用户，某种玩法的下级补入总额,新方法,使用scheme查询
	 * @param userID     用户ID
	 * @param userType   用户类型
	 * @param typeCode   玩法类型 
	 * @param periodsNum 盘期
	 * @param scheme
	 * @return
	 */
	public Integer queryReplenishForBetCheckByScheme(Long userID,String userType,String typeCode,String periodsNum,String scheme);

	public List<ReplenishVO> findReplenishPetList_K3(ManagerUser userInfo,
			String plate, String periodsNum, String searchType, String type);
	/**
	 * 补货触发自动降赔
	 * @param shopCode
	 * @param money
	 * @param userID
	 * @param typeCode
	 * @param chiefRate
	 * @param periodsNum
	 */
	public void updateShopRealOddsForReplenish(String shopCode,Integer money,Long userID,String typeCode,String periodsNum);

	/**
	 * 补货触发自动降赔,新方法,使用scheme操作
	 * @param shopCode
	 * @param money
	 * @param userID
	 * @param typeCode
	 * @param periodsNum
	 * @param scheme
	 */
	public void updateShopRealOddsForReplenishByScheme(String shopCode,Integer money,Long userID,String typeCode,String periodsNum,String scheme);
	
	/**
	 * 农场遗漏
	 * @param map
	 * @return
	 */
	public Map<String, ZhanDangVO> notAppearCntForNc(Map<String, ZhanDangVO> map);

	public List<ReplenishVO> findReplenishPetList_NC(String tableName,
			ManagerUser userInfo, String plate, String periodsNum,
			String searchType, String ballNum, String preName);

	public List<ReplenishHis> findReplenishHis(Criterion[] criterions);

	public List<ReplenishVO> findReplenishPetListForLh_NC(String tableName,
			ManagerUser userInfo, String plate, String periodsNum,
			String searchType);

}
