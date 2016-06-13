package com.npc.lottery.replenish.dao.interf;

import java.util.List;

import com.npc.lottery.replenish.entity.Zhangdan;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.ZhanDangLMDetailVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;

/**
 * 报表统计相关的数据库处理类接口
 * @typeCode 用来过滤玩法类型，模糊要加%
 */
public interface IZhangdanDao {

	public List<Zhangdan> queryUnSettledReport(String lotteryType, String playType,String periodNum, Long userid,String userType,
			String userColumn,String rateUser,String commissionUser,String nextColumn,String tableName);

	//查询帐单
	public List<ZhanDangVO> queryZhangDang(String lotteryType,String periodNum, Long userid, String userType, String myColumn,
			String rateUser, String commissionUser, String nextColumn,String tableName);
	
	//查询帐单(本级补出的)
	public List<ZhanDangVO> queryZhangDangForOut(String lotteryType, String periodNum,
			Long userid, String userType, String rateUser, String commissionUser);

	//查询帐单(下级补进的)
	public List<ZhanDangVO> queryZhangDangForIn(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String commissionUser, String outCommissionUser,String nextColumn);

//***************总额统计************START***********
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
	public List<ZhanDangVO> queryBallRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String status);
    //统计广东的总和大小 和总和单双
	public List<ZhanDangVO> queryGdZHRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String status);
    
	//统计广东连码
	public List<ZhanDangVO> queryGDLMRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String status);

	public List<ZhanDangVO> queryBJDoubleRightTotal(String lotteryType,
			String periodNum, Long userid, String userType, String myColumn,
			String rateUser, String status);
    //查询连码帐单
	public List<ZhanDangLMDetailVO> queryZhangDangLM(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String commissionUser, String nextColumn);
	//查询帐单(双面的总数龙虎的)
	public List<ZhanDangVO> queryZhangDangDoubleLH(String lotteryType,
			String periodNum, Long userid, String userType, String myColumn,
			String rateUser, String commissionUser, String nextColumn);

	public List<ZhanDangLMDetailVO> queryReplenishOutDetailLM(Long userId,
			String periodsNum, String userType, String commissionUser);


	public List<ZhanDangLMDetailVO> queryReplenishInDetailLM(Long userId,
			String periodsNum, String rateUser, String userType,
			String commissionUser);

	public List<ZhanDangVO> queryNCZHRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String status);

	public List<ZhanDangVO> queryNCLMRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String status);

	public List<ZhanDangLMDetailVO> queryZhangDangLM_NC(String lotteryType,
			String periodNum, Long userId, String userType, String myColumn,
			String rateUser, String commissionUser, String nextColumn);

	public List<ZhanDangLMDetailVO> queryReplenishOutDetailLM_NC(Long userId,
			String periodsNum, String userType, String commissionUser);

	public List<ZhanDangLMDetailVO> queryReplenishInDetailLM_NC(Long userId,
			String periodsNum, String rateUser, String userType,
			String commissionUser);
	
	
//***************总额统计************END***********
    
}
