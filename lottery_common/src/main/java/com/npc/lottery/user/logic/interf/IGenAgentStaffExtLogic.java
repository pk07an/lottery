package com.npc.lottery.user.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.util.Page;

public interface IGenAgentStaffExtLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountGenAgentStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return GenAgentStaffExt 类型的 List
     */
    public List<GenAgentStaffExt> findGenAgentStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public GenAgentStaffExt findGenAgentStaffExtByID(Long userID);
    
    /**
     * 根据ID查询人员信息,使用scheme进行jdbc查询
     * 
     * @param userID
     *            人员信息ID
     * @param scheme            
     * @return
     */
    public GenAgentStaffExt findById(Long userID,String scheme);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveGenAgentStaffExt(GenAgentStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateGenAgentStaffExt(GenAgentStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delGenAgentStaffExt(GenAgentStaffExt entity);
    
    public GenAgentStaffExt queryGenAgentStaffExt(String propertyName, Object value);
    
    Page<GenAgentStaffExt>  findPage(Page<GenAgentStaffExt> page,Criterion... criterions);
    
    public Page<GenAgentStaffExt> findPage(Page page,ManagerStaff userInfo, String userStatus, String account, String chName);
    
    public Page<GenAgentStaffExt> findSubPage(Page page, ManagerUser userInfo); 
    
    /*
     * 保存總代 提取相關邏輯到事物
     */
    public Long saveUserGenAgentStaff(GenAgentStaffExt entity,Long createUserId);
    
    /*
     * 修改總代 提取相關邏輯到事物
     */
    public void updateUserGenAgentStaff(GenAgentStaffExt entity,String currentUserType);
    
}
