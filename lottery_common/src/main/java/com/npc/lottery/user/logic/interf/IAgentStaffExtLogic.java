package com.npc.lottery.user.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.util.Page;

public interface IAgentStaffExtLogic{

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountAgentStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return AgentStaffExt 类型的 List
     */
    public List findAgentStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public AgentStaffExt findManagerStaffByID(Long userID);
    
    /**
     * 根据ID查询人员信息,使用scheme进行jdbc查询
     * 
     * @param userID
     *            人员信息ID
     * @param scheme
     * @return
     */
    public AgentStaffExt findByID(Long userID,String scheme);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveAgentStaffExt(AgentStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateAgentStaffExt(AgentStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delAgentStaffExt(AgentStaffExt entity);
    
    public AgentStaffExt queryAgenStaffExt(String propertyName,Object value);
    
    public List<AgentStaffExt> queryAllAgentStaffExt(String propertyName, Object value);
    
    public Page<AgentStaffExt> findPage( Page<AgentStaffExt> page, Criterion... criterions);
    
    public Page<AgentStaffExt> findPage(Page page,ManagerStaff userInfo, String userStatus, String account, String chName);
    
    public Page<AgentStaffExt> findSubPage(Page page, ManagerUser userInfo);
    
    /*
     * 保存代 提取相關邏輯到事物
     */
    public Long saveUserAgentStaff(AgentStaffExt entity,Long createUserId);
    
    /*
     * 修改代 提取相關邏輯到事物
     */
    public void updateUserAgentStaff(AgentStaffExt entity,String currentUserType);

    public void updateUserAgentStaff(AgentStaffExt agentStaffExt, String currentUserType, ManagerUser currentUser);
    
    
    
}
