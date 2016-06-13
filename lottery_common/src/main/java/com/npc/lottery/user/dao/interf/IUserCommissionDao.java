package com.npc.lottery.user.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.vo.ShopCommissionVO;

public interface IUserCommissionDao extends ILotteryBaseDao<Long, UserCommission>{
	
    public void updateCommission(Long userID,String userType);

    public List<ShopCommissionVO> queryShopCommission(Long userID, String typeCode);
    //Create by Eric
    public void deleteUserCommission(Long userId);

    public List<UserCommission> getUserCommissionMapByPlayFinalType(String playFinalType, Long userId);
    
    public UserCommission queryUserPlayTypeCommission(Object[] values,String scheme);
    
}
