package com.npc.lottery.user.logic.spring;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.service.BaseLogic;
import com.npc.lottery.user.dao.interf.ICommonUserDao;
import com.npc.lottery.user.dao.interf.IUserCommissionDao;
import com.npc.lottery.user.dao.interf.IUserOutReplenishDao;
import com.npc.lottery.user.dao.interf.IUserOutReplenishJdbcDao;
import com.npc.lottery.user.entity.OutReplenishStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IUserOutReplenishLogic;
import com.npc.lottery.util.Page;

public class UserOutReplenishLogic extends BaseLogic implements IUserOutReplenishLogic{

	@Override
	public void saveUserOutReplenish(OutReplenishStaffExt outReplenishStaff) {
	    userOutReplenishDao.save(outReplenishStaff);
	    Long id = outReplenishStaff.getID();
		userOutReplenishJdbcDao.saveUserCommissionFromDefault(Long.valueOf(outReplenishStaff.getParentStaff()), id, outReplenishStaff.getUserType());
		
	}
	
	@Override
	public void updateUserOutReplenish(OutReplenishStaffExt outReplenishStaff) {
		userOutReplenishDao.save(outReplenishStaff);
	}
	
	public void updateOutReplenishCommission(OutReplenishStaffExt outReplenishStaff,UserCommission userCommission,String typeCode ) {
		Long id = outReplenishStaff.getID();
		userOutReplenishJdbcDao.updateOutReplenishCommission(userCommission.getCommissionA(), userCommission.getBettingQuotas(), 
				userCommission.getItemQuotas(), outReplenishStaff.getID(), outReplenishStaff.getUserType(), typeCode);
		
	}
	
	public Page<OutReplenishStaffExt> findUserOutReplenish(Page<OutReplenishStaffExt> page,Criterion... criterions) {
		return userOutReplenishDao.findPage(page, criterions);
		
	}
	
	public void deleteUserOutReplenish(Long userId){
		commonUserDao.deleteMangerStaff(userId);
		commonUserDao.deleteUserOutReplenishStaff(userId);
		userCommissionDao.deleteUserCommission(userId);
    }
	
	private IUserOutReplenishDao userOutReplenishDao;
	private IUserOutReplenishJdbcDao userOutReplenishJdbcDao;
	private ICommonUserDao commonUserDao;
	private IUserCommissionDao userCommissionDao;

	public IUserOutReplenishDao getUserOutReplenishDao() {
		return userOutReplenishDao;
	}

	public void setUserOutReplenishDao(IUserOutReplenishDao userOutReplenishDao) {
		this.userOutReplenishDao = userOutReplenishDao;
	}

	public IUserOutReplenishJdbcDao getUserOutReplenishJdbcDao() {
		return userOutReplenishJdbcDao;
	}

	public void setUserOutReplenishJdbcDao(
			IUserOutReplenishJdbcDao userOutReplenishJdbcDao) {
		this.userOutReplenishJdbcDao = userOutReplenishJdbcDao;
	}

	public ICommonUserDao getCommonUserDao() {
		return commonUserDao;
	}

	public IUserCommissionDao getUserCommissionDao() {
		return userCommissionDao;
	}

	public void setCommonUserDao(ICommonUserDao commonUserDao) {
		this.commonUserDao = commonUserDao;
	}

	public void setUserCommissionDao(IUserCommissionDao userCommissionDao) {
		this.userCommissionDao = userCommissionDao;
	}

}
