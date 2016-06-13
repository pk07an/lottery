package com.npc.lottery.user.logic.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.dao.interf.IChiefStaffExtDao;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.util.Page;

public class ChiefStaffExtLogic implements IChiefStaffExtLogic{

    Logger logger = Logger.getLogger(AgentStaffExtLogic.class);
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
	@Override
    public long findAmountChiefStaffExtList(ConditionData conditionData) {

        return 0;
    }

    @Override
    public List<ChiefStaffExt> findChiefStaffExtList(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public ChiefStaffExt findChiefStaffExtByID(Long userID) {
        return chiefStaffExtDao.get(userID);
    }

    @Override
    public Long saveChiefStaffExt(ChiefStaffExt entity) {
            chiefStaffExtDao.save(entity);
            return (long) 0;
    }

    @Override
    public void updateChiefStaffExt(ChiefStaffExt entity) {
        
    }

    @Override
    public void delChiefStaffExt(ChiefStaffExt entity) {
        
    }

   

    public IChiefStaffExtDao getChiefStaffExtDao() {
        return chiefStaffExtDao;
    }

    public void setChiefStaffExtDao(IChiefStaffExtDao chiefStaffExtDao) {
        this.chiefStaffExtDao = chiefStaffExtDao;
    }

    @Override
    public ChiefStaffExt queryChiefStaffExt(String propertyName, Object value) {
        ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
        chiefStaffExt = chiefStaffExtDao.findUniqueBy(propertyName, value);
//        if(chiefStaffExt !=null){
//            for (BranchStaffExt branchStaffExt : chiefStaffExt.getBranchStaffExtSet()) {
//                for (StockholderStaffExt stockholderStaffExt : branchStaffExt.getStockholderStaffExtSet()) {
//                    for (GenAgentStaffExt genAgentStaffExt : stockholderStaffExt.getGenAgentStaffExtSet()) {
//                        for (AgentStaffExt agentStaffExt : genAgentStaffExt.getAgentStaffExtsSet()) {
//                            Hibernate.initialize(agentStaffExt.getMemberStaffExtSet());
//                        }
//                        Hibernate.initialize(genAgentStaffExt.getMemberStaffExtSet());
//                    }
//                    Hibernate.initialize(stockholderStaffExt.getMemberStaffExtSet());
//                }
//                Hibernate.initialize(branchStaffExt.getMemberStaffExtSet());
//            }
//            Hibernate.initialize(chiefStaffExt.getMemberStaffExtSet());
//        }
        return chiefStaffExt;
    }

    @Override
    public List<ChiefStaffExt> queryAllChiefStaffExt(String propertyName,
            Object value) {
            return chiefStaffExtDao.findBy(propertyName, value);
    }
    //根据商铺号查询总监
	@Override
	public List<ChiefStaffExt> queryChiefByShops(Object[] values) {
		return chiefStaffExtDao.find("from ChiefStaffExt where userType=? and shopsCode=?", values);
	}
	public List<ChiefStaffExt> queryAllChiefStaffExt(Criterion... criterions){
	    return  chiefStaffExtDao.find(criterions);
	}

    @Override
    public Page<ChiefStaffExt> findPage(Page<ChiefStaffExt> page,
            Criterion... criterions) {
        return chiefStaffExtDao.findPage(page, criterions);
    }

    @Override
    public Page<ChiefStaffExt> findSubPage(Page page, ManagerUser userInfo) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean ischief = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_CHIEF);// 总监类型
            boolean ischiefSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 子账号总监类型
           if(ischief || ischiefSub){
               ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
               List<SubAccountInfo> staffExts = new ArrayList<SubAccountInfo>();
               if(ischief){
            	   chiefStaffExt = chiefStaffExtDao.findUniqueBy("managerStaffID",userInfo.getID());
               }else if(ischiefSub){
                   SubAccountInfo subInfo = new SubAccountInfo();
                   subInfo = subAccountActionDao.findUniqueBy("managerStaffID",userInfo.getID());
                   chiefStaffExt = chiefStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
               
               for (SubAccountInfo subAccountInfo : chiefStaffExt.getSubAccountInfoSet()) {
                   if(!subAccountInfo.getFlag().equals(ManagerStaff.FLAG_DELETE)){
                       if(!userInfo.getAccount().equals(subAccountInfo.getAccount())){
                    	   staffExts.add(subAccountInfo);
                       }
                   }
               }
               //add by peter for hide adminsjp sub account
               hideSysSubChiefAccount(staffExts);
               
               int first=page.getFirst()-1;
               int last=first+page.getPageSize();
               if(last>staffExts.size())
                   last=staffExts.size();
               Collections.sort(staffExts);
               page.setTotalCount(staffExts.size());
               page.setResult(staffExts.subList(first, last));
               return page;    
           }
        }    
        return page;
    }
    
    private void hideSysSubChiefAccount(List resultList){
        for(int i=0;i<resultList.size();i++){
       	 SubAccountInfo subAccountInfo = (SubAccountInfo)resultList.get(i);
       	 if("adminSJP".equals(subAccountInfo.getAccount())){
       		 resultList.remove(i);
       		 break;
       	 }
        }
	}

    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }
    
	@Override
	public void updateChiefPassword(ChiefStaffExt entity,String scheme) {
		chiefStaffExtDao.updateChiefPassword(entity, scheme);
	}

	@Override
	public ChiefStaffExt queryChiefByShopCode(String shopsCode, String scheme) {
		return chiefStaffExtDao.queryChiefByShopsCode(shopsCode, scheme);
	}
}
