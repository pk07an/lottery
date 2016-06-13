package com.npc.lottery.user.logic.interf;

import java.util.List;

import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.vo.UserInfoVO;


public interface IUserCommissionLogic {

    public void saveCommission(UserCommission entity);

    public List<UserCommission> queryCommission(Object value,Object userType);

    public void updateCommission(UserCommission entity);

    public List<UserCommission> queryAll(String orderBy,boolean isAsc);
    
    public UserCommission queryUserPlayTypeCommission(Long userId,String userType,String typeCode);
    
    /**
     * 传入scheme jdbc查询
     * @param userId
     * @param userType
     * @param typeCode
     * @param scheme
     * @return
     */
    public UserCommission queryUserPlayTypeCommissionByScheme(Long userId,String userType,String typeCode,String scheme);
    
    /**
     * 传入用户对象，会自动根据用户类型返回自己需要的数据
     * @param userInfo
     * @return  UserInfoVO 可以在这里放进自己需要的项目
     */
    public UserInfoVO queryUserInfo(ManagerUser userInfo); 
    
    /**
     * 传入用户对象，会自动根据用户类型返回自己需要的数据,使用scheme查询
     * @param userInfo
     * @param scheme
     * @return
     */
    public UserInfoVO queryUserInfoByScheme(ManagerUser userInfo,String scheme); 
    
    public List<UserCommission> queryCommissionByType(Object value, Object value2,Object value3);
    
    public void updateCommission(Long userId,String userType);
    
    public void updateUserBatchCommission(List<UserCommission> userCommissions,boolean run,boolean saveDefault);
    
    public void saveUserBatchCommission(List<UserCommission> userCommissions);
    
    public void updateBranchBatchCommission(List<UserCommission> userCommissions,boolean run,boolean saveDefault,Long chiefId,Long branchId);
    public void batchUpdateUserTreeCommissiono(final List<UserCommission> commissions);
    public void batchUpdateMemberCommissiono(final List<UserCommission> commissions,final String plate);
    public void batchUpdateChiefUsereCommission(final List<UserCommissionDefault> commissions);
    
    
}
