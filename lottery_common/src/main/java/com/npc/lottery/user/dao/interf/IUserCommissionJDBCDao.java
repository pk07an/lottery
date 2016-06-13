package com.npc.lottery.user.dao.interf;

import java.util.List;

import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;


public interface IUserCommissionJDBCDao {
	
    public void updateCommission(Long userID,String userType);
    
    public int[] batchUpdateUserCommission(List<UserCommission> commissions);
    
    public int[] batchUpdateUserCommissionRun(List<UserCommission> commissions);
    
    public int[] batchInsertUserCommission(List<UserCommission> commissions);
    
 
    public void  updateBranchforDefualtUserCommission(Long chiefId,Long branchId);
    
    public int[] batchUpdateUserTreeCommission(final List<UserCommission> commissions);
    
    public int[]  updateMemberUserCommission(List<UserCommission> commissions,String plate);
    public int[] batchUpdateChiefUsereCommission(final List<UserCommissionDefault> commissions);
    
}
