package com.npc.lottery.user.logic.interf;

import java.util.List;
import java.util.Map;

import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.sysmge.entity.ManagerUser;


public interface ICommonUserLogic {

    /*
     * 更新管理用户下所有人的可用余额为 跟信用额度0
     */
    public void clearBelowAvailableCreditLine(String managerAccount,String managerType);
    
    public Long queryBelowCreditLine(Long id,String managerType);
    
    /*
     * 更新管理用户下所有人的占成为0
     */
    public void clearBelowRate(String managerAccount,String managerType);
    
    /*
     * 更新管理用户下所有人走飞为禁止
     */
    public void updateBelowReplenishment(String managerAccount,String managerType);
    
    /*
     * 更新管理用户下所有人为禁止状态
     */
    public void updateBelowForbid(String managerAccount,String managerType,String currentUserType);
    
    /*
     * 更新管理用户下所有人为冻结状态
     */
    public void updateBelowFreeze(String managerAccount,String managerType,String currentUserType);
    
    
    /*
     * 更新管理用户下所有人为启动状态
     */
    public void updateBelowUsed(String managerAccount,String managerType,String currentUserType);
    
    public List<ManagerUser> queryBelowManager(String  account,String type);
    
    public List<ManagerUser> queryBelowManagerByUserId(String  userId,String type);
    
    public  Map queryManagerUserTree(String  account,String type,String searchUserStatus);
    public boolean queryUserTreeHasBet(Long userId,String type);
    public int queryBelowMaxRate(Long managerId,String type);
    
    public UserVO getUserVo(ManagerUser userInfoNew);
 
}
