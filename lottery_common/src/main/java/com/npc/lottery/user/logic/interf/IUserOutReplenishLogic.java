package com.npc.lottery.user.logic.interf;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.service.IBaseLogic;
import com.npc.lottery.user.entity.OutReplenishStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.util.Page;

public interface IUserOutReplenishLogic extends IBaseLogic{

	
	public void saveUserOutReplenish(OutReplenishStaffExt outReplenishStaff);

	public Page<OutReplenishStaffExt> findUserOutReplenish(Page<OutReplenishStaffExt> page,Criterion... criterions);
	
	public void updateOutReplenishCommission(OutReplenishStaffExt outReplenishStaff,UserCommission userCommission,String typeCode );
	
	//删除出货会员
    public void deleteUserOutReplenish(Long userId);

	public void updateUserOutReplenish(OutReplenishStaffExt outReplenishStaff);
}
