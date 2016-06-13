package com.npc.lottery.replenish.logic.interf;

import java.util.List;
import java.util.Map;

import com.npc.lottery.replenish.entity.Zhangdan;
import com.npc.lottery.replenish.vo.ZhanDangLMDetailVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;

/**
 * 报表统计相关的逻辑处理类接口
 * 
 */
public interface IZhangdanLogic {

    /**
     * 查询未结算
     * @param lotteryType 如广东，重庆，香港
     * @param playType  玩法类型，这里取拥金类型
     * @param userType  用户类型，要查询的用户类型，如股东登录，查到总代理时，这个字段就是总代理，而currentUserType就是股东
     * @param currentUserType 当前登录用户的用户类型
     * @return
     */
    public List<Zhangdan> findUnSettledReport(String lotteryType,
    		String playType,String periodNum,Long userid,String userType,String currentUserType);

    //帳單
	public List<ZhanDangVO> queryZhangdan(String lotteryType,String periodNum, Long userid, String userType,
			String currentUserType,String tableName);

	//查询帐单(处理补货，补进和补出相加，笔数相加，其他相减)
	public List<ZhanDangVO> queryZhangdanForReplenish(String lotteryType, String periodNum,
			Long userid, String userType, String currentUserType);
	
	/**
	 * 查询补货界面的总额，里面直接把投注、本级补出和下级补入的在数据库进行计算，投注+下级补入-本级补出
	 * @param lotteryType
	 * @param periodNum
	 * @param userid
	 * @param userType
	 * @param myColumn
	 * @param rateUser  占成的字段
	 * @param status  实占还是虚占
	 * @return
	 */
	public List<ZhanDangVO> queryRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,String status);

	public Map<String,Object> queryZhangdanLM(String lotteryType,
			String periodNum, Long userid, String userType,
			String currentUserType);

	public Map<String, Object> queryZhangdanLM_NC(String lotteryType,
			String periodNum, Long userId, String userType,
			String currentUserType);

}
