package com.npc.lottery.replenish.dao.interf;

import java.util.Date;
import java.util.List;

import com.npc.lottery.replenish.vo.ReplenishHKVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.util.Page;

public interface IReplenishDao {
	/**
	 * 根据play_type分组统计出各种玩法的赔额合计和注额合计
	 * 
	 * @param tableName 表名
	 * @param userType  查询的用户角色
	 * @param userId    该用户角色的ID
	 * @param plate     盘口
	 * @param periodsNum 期数
	 * @param searchType        虚注还是实占
	 * @param rateUser    占成用戶类型字段名
	 * @param commissionUser   拥金用户类型字段名
	 * @param typeCode
	 * @return
	 */
	public List<ReplenishVO> queryTotal(String tableName,String userType,Long userID,String plate,String periodsNum,String rateUser,String commissionUser,String typeCode);
	/**
	 * 根据play_type分组统计出各种玩法的赔额合计和注额合计(两面表)
	 * 
	 * @param tableName  表名
	 * @param userType   查询的用户角色
	 * @param userId     该用户角色的ID
	 * @param plate      盘口
	 * @param periodsNum 期数
	 * @param searchType  虚注还是实占
	 * @param rateUser    占成用戶类型字段名
	 * @param commissionUser   拥金用户类型字段名
	 * @param typeCode
	 * @return
	 */
	public List<ReplenishVO> queryDoubleTotal(String userType,Long userID,String plate,String periodsNum,String rateUser,String commissionUser,String typeCode);
	//补货--补货人
	//public List<ReplenishVO> queryReplenish(String userType, Long userID,String typeCode, String periodsNum, String plate,String rCommissionUser);	
	//补货--接受补货人
    //public List<ReplenishVO> queryReplenishForAcc(String userType, Long userID,String typeCode, String periodsNum, String plate,String rateUser,String rCommissionUser);    
    //补出
  	public List<ReplenishVO> queryReplenishForOut(String userColumn, Long userID,String typeCode, String periodsNum, String plate,String rCommissionUser,String userType);
	
	/**
	 * 查询该用户下的FINAL_TYPE玩法和拥金的对应列表
	 * 
	 * @param userId    该用户角色的ID
	 * @param playSubType     如GD,HK,CQ
	 * @return
	 */
	/*public List queryCommissionPlayTypeList(Long userID,String playSubType);*/

	/**
	 * 查詢投注總額
	 * @param periodsNum
	 * @param state 实占还是虚注
	 * @return
	 */
	public List<ReplenishHKVO> queryDeliveryDetail(String tableName, String periodsNum,String userType,Long userID,String rateUser,String commissionUser,String state);
	/**
	 * 查詢投注總額 六肖
	 * @param periodsNum
	 * @param state 实占还是虚注
	 * @return
	 */
	public List<ReplenishHKVO> queryDeliveryLXDetail(String periodsNum,String userType,Long userID,String rateUser,String commissionUser,String state);
	/**
	 * 广东快乐十分 龙虎分类统计（用于补货）
	 * @param userType
	 * @param userID
	 * @param plate
	 * @param periodsNum
	 * @param searchType
	 * @param rateUser
	 * @param commissionUser
	 * @return
	 */
	public List<ReplenishVO> queryDoubleTotalForLh(String userType, Long userID,
			String plate, String periodsNum,
			String rateUser, String commissionUser);	
	//重庆补货操作
	public List<ReplenishVO> queryTotal_CQ(String userType, Long userID,
			String plate, String periodsNum, 
			String rateUser, String commissionUser,String typeCode);
	/**
	 * 该方法可通用查询广东连码，香港的连码，过关，五不中，尾数连，生肖连
	 * @param tableName  传进表名，通用其他连码或复式的补货
	 * @param userType
	 * @param userID
	 * @param plate
	 * @param periodsNum
	 * @param searchType
	 * @param rateUser
	 * @param commissionUser
	 * @return
	 */
	public List<ReplenishVO> queryTotal_LM(String tableName, String userType,
			Long userID, String plate, String periodsNum, 
			String rateUser, String commissionUser,String typeCode);
	/**
	 * 统计补货表--用于连码或复式
	 * @param userType
	 * @param userID
	 * @param typeCode
	 * @param periodsNum
	 * @param plate
	 * @return
	 */
	public List<ReplenishVO> queryReplenish_LM(String userType, Long userID,
			String typeCode, String periodsNum, String plate,String rCommissionUser);
	/**
	 * 生肖連
	 * @param tableName
	 * @param periodsNum
	 * @param userType
	 * @param userID
	 * @param rateUser
	 * @param commissionUser
	 * @return
	 */
	public List<ReplenishHKVO> queryDeliveryDetailByAttr(String tableName,
			String periodsNum, String userType, Long userID, String rateUser,
			String commissionUser,String state);
	
	public List<ReplenishVO> queryReplenish_LM_Acc(String userType, Long userID,
			String typeCode, String periodsNum, String plate, String rateUser,
			String rCommissionUser);
	
	public List<ReplenishVO> queryTotal_GD(String tableName, String userType,
			Long userID, String plate, String periodsNum,
			String rateUser, String commissionUser,String typeCode);
	
	public List<ReplenishVO> queryReplenishForIn(String userType, Long userID,
			String typeCode, String periodsNum, String plate, String rateUser,
			String rCommissionUser);
	/**
	 * 補出 列表
	 * @param userType
	 * @param userID
	 * @param typeCode
	 * @param periodsNum
	 * @param plate
	 * @param rCommissionUser
	 * @return
	 */
	public List<ReplenishVO> queryReplenish(String userType, Long userID,
			String typeCode, String periodsNum, String plate,
			String rCommissionUser);
	/**
	 * 補入列表
	 * @param userType
	 * @param userID
	 * @param typeCode
	 * @param periodsNum
	 * @param plate
	 * @param rateUser
	 * @param rCommissionUser
	 * @return
	 */
	public List<ReplenishVO> queryReplenishForAcc(String userType, Long userID,
			String typeCode, String periodsNum, String plate, String rateUser,
			String rCommissionUser);
	
	//用于投注时的数据校验
	public List<ReplenishVO> queryTotal_GD_valiate(String tableName, String userType,
			Long userID, String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode);
	
	//用于连码朴货时的数据校验
	List<ReplenishVO> queryTotal_LM_valite(String tableName, String userType,
			Long userID, String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode, String attribute);
	//用于连码朴货时的数据校验
	List<ReplenishVO> queryReplenish_LM_valiate(String userColumn, Long userID,
			String typeCode, String periodsNum, String plate,
			String rCommissionUser, String attribute,String userType);
	//用于连码朴货时的数据校验
	List<ReplenishVO> queryReplenish_LM_Acc_valiate(String userType,
			Long userID, String typeCode, String periodsNum, String plate,
			String rateUser, String rCommissionUser, String attribute);
	
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
	public Page queryBetDetail(Page page, Long userId, String typeCode,
			String periodsNum, String rateUser,String userType,String subType);
	
	/**
	 * 查询补货明细(下级补入)
	 * @param page
	 * @param userId
	 * @param typeCode  typeCode为null，就可以查询广东全部投注类型
	 * @param periodsNum
	 * @param rateUser  占成者的字段名
	 * @param userType
	 * @param commissionType 传入null,此条件就不执行
	 * @param startDate,endDate 传入null,此条件就不执行
	 * @param winState 补货单的状态
	 * @return
	 */
	public Page queryReplenishInDetail(Page page, Long userId, String typeCode,String periodsNum, String rateUser, 
			String userType,String commissionType,Date startDate,Date endDate,String winState,
			String opType,String rateColumn,String commissionColumn,String tableName);
	/**
	 * 查询补货明细(自已补出)
	 * @param page
	 * @param userId
	 * @param typeCode  typeCode为null，就可以查询广东全部投注类型
	 * @param periodsNum
	 * @param rateUser  占成者的字段名
	 * @param userType
	 * @return
	 */
	public Page queryReplenishOutDetail(Page page, Long userId, String typeCode,
			String periodsNum, String rateUser, String userType);
	
	public List<ReplenishVO> queryTotal_LM_Main(String tableName, String userType,
			Long userID, String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode);
	
	public List<ReplenishVO> queryReplenish_LM_Main(String userColumn, Long userID,
			String typeCode, String periodsNum, String plate,
			String rCommissionUser,String userType);
	
	public List<ReplenishVO> queryReplenish_LM_Acc_Main(String userType, Long userID,
			String typeCode, String periodsNum, String plate, String rateUser,
			String rCommissionUser);
	
	public List<ReplenishVO> queryTotal_BJ(String userType, Long userID, String plate,
			String periodsNum, String rateUser, String commissionUser);
	
	public List<ReplenishVO> queryTotal_BJ_valiate(String userType, Long userID,
			String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode);
	
	public Page queryBetDetail_RealTime(Page page, Long userId, String typeCode,
			String periodsNum, String rateUser, String userType, String subType,Date prevSearchTime,Integer money);
	
	public Page queryReplenishInDetail_RealTime(Page page, Long userId,
			String typeCode, String periodsNum, String rateUser, String userType,Date prevSearchTime,Integer money);
	
	public Page queryReplenishOutDetail_RealTime(Page page, Long userId,
			String typeCode, String periodsNum, String rateUser, String userType,Date prevSearchTime,Integer money);
	
	public Page queryBetDetail_Backup(Page page, Long userId, String typeCode,
			String periodsNum, String userType, String subType);
	
	public Page queryReplenishInDetail_Backup(Page page, Long userId, String typeCode,
			String periodsNum, String rateUser, String userType);
	
	public Page queryReplenishOutDetail_Backup(Page page, Long userId,
			String typeCode, String periodsNum, String rateUser, String userType);
	
	//统计单个管理者的下级补入额
	public List<ReplenishVO> queryReplenishInForBetCheck(String userColumn,
			Long userID, String typeCode, String periodsNum, String rateUser);
	
	/**
	 * 查询实时滚单 补入、补出数据
	 * @param page
	 * @param userId
	 * @param typeCode
	 * @param periodsNum
	 * @param rateUser
	 * @param userType
	 * @param prevSearchTime
	 * @param money
	 * @return
	 */
	public Page queryReplenishInAndOutDetail_RealTime(Page page, Long userId,
			String typeCode, String periodsNum, String rateUser, String userType,Date prevSearchTime,Integer money);
	
	/**
	 * 统计单个管理者的下级补入额，新方法，使用scheme
	 * @param userColumn
	 * @param userID
	 * @param typeCode
	 * @param periodsNum
	 * @param rateUser
	 * @return
	 */
	public List<ReplenishVO> queryReplenishInForBetCheckByScheme(String userColumn,
			Long userID, String typeCode, String periodsNum, String rateUser,String scheme);
	
	public List<ReplenishVO> queryTotal_K3(String userType, Long userID, String plate,
			String periodsNum, String rateUser, String commissionUser);
	
	public List<ReplenishVO> queryTotal_K3_valiate(String userType, Long userID,
			String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode);
	public List<ReplenishVO> queryTotal_NC(String tableName, String userType,
			Long userID, String plate, String periodsNum, String rateUser,
			String commissionUser, String typeCode);
	public List<ReplenishVO> queryDoubleTotalForLh_NC(String userType, Long userID,
			String plate, String periodsNum, String rateUser,
			String commissionUser);
	

}
