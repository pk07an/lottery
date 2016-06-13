package com.npc.lottery.manage.logic.spring;

import java.util.Date;
import java.util.List;

import com.npc.lottery.manage.dao.interf.IShopsDeclarattonDao;
import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.Page;

public class ShopsDeclarattonLogic implements IShopsDeclarattonLogic{

    private IShopsDeclarattonDao shopsDeclarattonDao;
    @Override
    public void saveShopsDeclare(ShopsDeclaratton shopsDeclaratton) {
        shopsDeclarattonDao.save(shopsDeclaratton);
    }
    public IShopsDeclarattonDao getShopsDeclarattonDao() {
        return shopsDeclarattonDao;
    }
    public void setShopsDeclarattonDao(IShopsDeclarattonDao shopsDeclarattonDao) {
        this.shopsDeclarattonDao = shopsDeclarattonDao;
    }
    @Override
    public List<ShopsDeclaratton> queryAll(String propertyName, Object value) {
        return shopsDeclarattonDao.findBy(propertyName, value);
    }
    @Override
    public ShopsDeclaratton queryByShopsDeclaratton(String propertyName,
            Object value) {
        return shopsDeclarattonDao.findUniqueBy(propertyName, value);
    }
    @Override
    public void update(ShopsDeclaratton shopsDeclaratton) {
        shopsDeclarattonDao.update(shopsDeclaratton);
    }
    @Override
    public Page<ShopsDeclaratton> findShopsPage(Page<ShopsDeclaratton> page) {
        return shopsDeclarattonDao.findPage(page, "from ShopsDeclaratton where type=? order by createDate desc", "0");
    }
    @Override
    public void delete(ShopsDeclaratton shopsDeclaratton) {
        shopsDeclarattonDao.delete(shopsDeclaratton);
    }
    @Override
    public ShopsDeclaratton queryByShopsDeclaratton(Date date,ManagerUser userStaff) {
        ShopsDeclaratton declaratton = new ShopsDeclaratton();
        Object[] parameter = new Object[] {date,userStaff.getSafetyCode(),userStaff.getUserType()};
        List<ShopsDeclaratton> declarattonList = shopsDeclarattonDao.find("from ShopsDeclaratton where createDate <? and (shopsCode=? or shopsCode is null) and (managerMessageStatus=? or managerMessageStatus=0  or managerMessageStatus=2) order by createDate desc", parameter);
        if(declarattonList!=null&&declarattonList.size()>0)
            declaratton=declarattonList.get(0);
         return declaratton;
    }
    @Override
    public Page<ShopsDeclaratton> findShopsChiefPage(Page<ShopsDeclaratton> page,ManagerUser userStaff) {
        Object[] parameter = new Object[] {};
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userStaff.getUserType())){
        	parameter = new Object[] {userStaff.getSafetyCode()};
        	return shopsDeclarattonDao.findPage(page, "from ShopsDeclaratton where (shopsCode=? or shopsCode is null) order by createDate desc",parameter);
        }else{
        	parameter = new Object[] {userStaff.getSafetyCode(),userStaff.getUserType()};
        	return shopsDeclarattonDao.findPage(page, "from ShopsDeclaratton where (shopsCode=? or shopsCode is null)  and ( managerMessageStatus=? or managerMessageStatus=0  or managerMessageStatus=2) order by createDate desc",parameter);
        }
    }
    @Override
    public ShopsDeclaratton queryByMemberShopsDeclaratton(Date date,MemberUser userStaff) {
        ShopsDeclaratton declaratton = new ShopsDeclaratton();
        Object[] parameter = new Object[] {date,userStaff.getSafetyCode()};
        List<ShopsDeclaratton> declarattonList = shopsDeclarattonDao.find("from ShopsDeclaratton where createDate <? and (shopsCode=? or shopsCode is null) and (managerMessageStatus=9 or managerMessageStatus=0 ) order by createDate desc", parameter);
        if(declarattonList!=null&&declarattonList.size()>0)
            declaratton=declarattonList.get(0);
         return declaratton;
    }
    
    @Override
    public ShopsDeclaratton queryByMemberPopupMenusDeclaratton(Date date,MemberUser userStaff) {
        ShopsDeclaratton declaratton = new ShopsDeclaratton();
        Object[] parameter = new Object[] {date,userStaff.getSafetyCode()};
        List<ShopsDeclaratton> declarattonList = shopsDeclarattonDao.find("from ShopsDeclaratton where createDate <? and (shopsCode=? or shopsCode is null) and popupMenus=1 order by createDate desc", parameter);
        if(declarattonList!=null&&declarattonList.size()>0)
            declaratton=declarattonList.get(0);
         return declaratton;
    }
    @Override
    public ShopsDeclaratton queryByPopupMenusManagerDeclaratton(Date date,
            ManagerUser userStaff) {
        ShopsDeclaratton declaratton = new ShopsDeclaratton();
        Object[] parameter = new Object[] {date,userStaff.getSafetyCode(),userStaff.getUserType()};
        List<ShopsDeclaratton> declarattonList = shopsDeclarattonDao.find("from ShopsDeclaratton where createDate <? and (shopsCode=? or shopsCode is null)" +
        		" and popupMenus=1 and ( managerMessageStatus=? or managerMessageStatus=0  or managerMessageStatus=2) order by createDate desc", parameter);
        if(declarattonList!=null&&declarattonList.size()>0)
            declaratton=declarattonList.get(0);
         return declaratton;
    }
    
}
