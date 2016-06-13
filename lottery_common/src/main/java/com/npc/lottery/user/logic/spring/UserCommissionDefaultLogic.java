package com.npc.lottery.user.logic.spring;

import java.util.Date;
import java.util.List;

import com.npc.lottery.user.dao.interf.IUserCommissionDefaultDao;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;

public class UserCommissionDefaultLogic implements IUserCommissionDefault{

    private IUserCommissionDefaultDao userCommissionDefaultDao;


    @Override
    public List<UserCommissionDefault> queryCommissionDefault(Object value) {
        return userCommissionDefaultDao.find("from UserCommissionDefault where userId=? order by ID asc", value);
    }
    
    @Override
    public List<UserCommissionDefault> queryCommissionDefault(Long userId) {
        return userCommissionDefaultDao.find("from UserCommissionDefault where userId=? order by ID asc", userId);
    }
    
    @Override
    public void updateCommissionDefault(UserCommissionDefault entity) {
        userCommissionDefaultDao.update(entity);
    }
    public void saveCommission(UserCommissionDefault entity){
        userCommissionDefaultDao.save(entity);
    }
    public IUserCommissionDefaultDao getUserCommissionDefaultDao() {
        return userCommissionDefaultDao;
    }
    public void setUserCommissionDefaultDao(
            IUserCommissionDefaultDao userCommissionDefaultDao) {
        this.userCommissionDefaultDao = userCommissionDefaultDao;
    }
    @Override
    public List<UserCommissionDefault> queryAll(String orderBy,boolean isAsc) {
        return userCommissionDefaultDao.getAll(orderBy, isAsc);
    }
	@Override
	public List<UserCommissionDefault> queryCommissionDefaultByType(Object value, Object value2) {
		return userCommissionDefaultDao.find("from UserCommissionDefault where userId=? and play_final_type like ? order by createTime asc", value,value2);
	}
	@Override
	public void updateTradingSet(List<UserCommissionDefault> list,Long userId) {
		Date nowDate = new Date();
		for(int i=0; i<list.size(); i++){
			UserCommissionDefault obj = list.get(i);
			obj.setModifyUser(userId);
			obj.setModifyTime(nowDate);
			userCommissionDefaultDao.save(obj);
		}				
	}
	
	 public UserCommissionDefault queryPlayTypeCommission(String commissionType,String shopCode)
	 {
	    	Object[] values={commissionType,shopCode};
	    	String hql="select userCommissionDefault from UserCommissionDefault userCommissionDefault,ChiefStaffExt chiefStaffExt where  userCommissionDefault.playFinalType=? and chiefStaffExt.shopsCode=? and chiefStaffExt.managerStaffID=userCommissionDefault.userId ";
	    	return userCommissionDefaultDao.findUnique(hql, values);	    	
	 }
	@Override
	 public UserCommissionDefault queryUserPlayTypeCommission(Long userId,String userType,String typeCode)
    {
		 // 总监无userType
    	Object[] values={userId,typeCode};
    	String hql="select userCommission from UserCommissionDefault userCommission,PlayType playType where userCommission.userId=?  and playType.typeCode=? and userCommission.playFinalType=playType.commissionType ";
    	//System.out.println(hql);
    	//System.out.println(userId+"--"+userType+"--"+typeCode);
    	return userCommissionDefaultDao.findUnique(hql, values);    	
    	
    }
}
