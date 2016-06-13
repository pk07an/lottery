package com.npc.lottery.replenish.dao.interf;

import java.util.List;
import java.util.Map;

import com.npc.lottery.replenish.vo.AutoReplenishSetVO;

public interface IReplenishAutoSetJDBCDao {

	public List<AutoReplenishSetVO> queryLowestQuotasByAutoType(Long userid);

	public void updateReplenishAutoSetByUser(String shopsID, Long userID,String userType, String state);

	public List<AutoReplenishSetVO> queryReplenishAutoSet(List<Long> userList,
			String shopsID, String typeCode);
	public List<AutoReplenishSetVO> queryReplenishAutoSet(List<Long> userList,String shopsID,String typeCode,String scheme);
	/**
	 * 可根据scheme查询，传入scheme空值则不根据
	 * @param periodNum
	 * @param userid
	 * @param userType
	 * @param myColumn
	 * @param rateUser
	 * @param typeCode
	 * @param tableList
	 * @param attribute 这个值只会在连码操作时才生效
	 * @param scheme  不为空则根据scheme查询操作
	 * @return
	 */
	public List<AutoReplenishSetVO> queryTrueMoneyForSignal(String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String typeCode, String[] tableList,String attribute,String scheme);

	public List<AutoReplenishSetVO> queryTotalTrueMoney(String periodNum, Long userid,
			String userType, String myColumn, String rateUser, String playType);

	public void updateReplenishAutoSetForClose(String shopsID, String userIDList);
    /**
     * 查询本级的下级所有管理ID列表。
     * @param userID
     * @param userType
     * @return
     */
	public List<Long> queryReplenishAutoSetUserList(Long userID, String userType);

	public List<AutoReplenishSetVO> queryTotalTrueMoney_LM(String periodNum,
			Long userid, String userType, String myColumn, String rateUser,
			String playType);

}
