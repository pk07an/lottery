package com.npc.lottery.user.dao.interf;



/**
 * 类
 *
 * @author Eric
 *
 */
public interface IUserOutReplenishJdbcDao
{
	public void saveUserCommissionFromDefault(Long chiefId,Long userId,String userType);
	
	 public void updateOutReplenishCommission(Double commissionA,Integer bettingQuotas,Integer itemQuotas,
			 Long userId,String userType,String typeCode);
	 
	//根據用戶Id更新用戶狀態  Create by Eric
	public void updateManagerStatusById(Long userId,String status);
}
