package com.npc.lottery.user.logic.interf;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.util.Page;

public interface ISubAccountInfoLogic {

    public SubAccountInfo querySubAccountInfo(String propertyName, Object value);
    
    public Page<SubAccountInfo> findPage( Page<SubAccountInfo> page, Criterion... criterions);
    
    public Page<SubAccountInfo> findPage(Page page,ManagerUser userInfo);
    
    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveSubAccountInfo(SubAccountInfo entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateSubAccountInfo(SubAccountInfo entity);
    /**
     * 识别该子帐号的类弄，并把USERINFO的ID和类型换成其所属上级的信息
     */
    public ManagerUser changeSubAccountInfo(ManagerUser userInfo);
    /**
     * 修改主帐号用户信息时，对子帐号进行相应的处理
     * 
     * @param userInfo
     * @return
     */
	public void handleReplenishmentByParent(ManagerUser userInfo);
}
