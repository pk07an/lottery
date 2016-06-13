package com.npc.lottery.user.logic.interf;

import java.util.List;

import com.npc.lottery.user.entity.UserCommissionDefault;

public interface IUserCommissionDefault {

    public void saveCommission(UserCommissionDefault entity);
    
    public List<UserCommissionDefault> queryCommissionDefault(Object  value);
    
    public List<UserCommissionDefault> queryCommissionDefaultByType(Object value,Object value2);
    
    public void updateCommissionDefault(UserCommissionDefault entity);
    
    public List<UserCommissionDefault> queryAll(String orderBy,boolean isAsc);
    
    public List<UserCommissionDefault> queryCommissionDefault(Long userId);
    
    public void updateTradingSet(List<UserCommissionDefault> list, Long userId);
    public UserCommissionDefault queryPlayTypeCommission(String commissionType,String shopCode);

    /**
     * 查找当前typeCode 的退水
     * PlayType 表与退水表联合查询 条件：PlayType.typeCode 
     * @param userId
     * @param userType
     * @param typeCode
     * @return
     */
    public UserCommissionDefault queryUserPlayTypeCommission(Long userId,
			String userType, String typeCode);

}
