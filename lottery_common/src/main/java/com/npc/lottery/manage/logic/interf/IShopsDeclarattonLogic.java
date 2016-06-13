package com.npc.lottery.manage.logic.interf;

import java.util.Date;
import java.util.List;

import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.Page;

public interface IShopsDeclarattonLogic {

    public void saveShopsDeclare(ShopsDeclaratton shopsDeclaratton);
    
    public List<ShopsDeclaratton> queryAll(String propertyName, Object value);
    
    public ShopsDeclaratton queryByShopsDeclaratton(String propertyName, Object value);
    
    public void update(ShopsDeclaratton shopsDeclaratton);
    
    public Page<ShopsDeclaratton> findShopsPage(Page<ShopsDeclaratton> page);
    
    public Page<ShopsDeclaratton> findShopsChiefPage(Page<ShopsDeclaratton> page,ManagerUser userStaff);
    
    public void delete(ShopsDeclaratton shopsDeclaratton);
    
    public ShopsDeclaratton queryByShopsDeclaratton(Date date,ManagerUser userStaff);
    
    public ShopsDeclaratton queryByMemberShopsDeclaratton(Date date,MemberUser userStaff);
    
    public ShopsDeclaratton queryByMemberPopupMenusDeclaratton(Date date,MemberUser userStaff);
    
    public ShopsDeclaratton queryByPopupMenusManagerDeclaratton(Date date,ManagerUser userStaff);
    
}
