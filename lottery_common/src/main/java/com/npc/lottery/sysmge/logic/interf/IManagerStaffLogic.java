package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;

/**
 * 基础管理用户所对应的逻辑实现类接口
 * 
 * @author none
 *
 */
public interface IManagerStaffLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountManagerStaffList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return ManagerStaff 类型的 List
     */
    public List findManagerStaffList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public ManagerStaff findManagerStaffByID(Long userID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveManagerStaff(ManagerStaff entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateManagerStaff(ManagerStaff entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delManagerStaff(ManagerStaff entity);
    
    /**
     * 根据登陆账号查询人员信息 hibernate查询
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public ManagerStaff findManagerStaffByAccount(String account);
    
    /**
     * 根据登陆账号查询人员信息 jdbc查询
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public ManagerStaff queryManagerStaffByAccount(String account);
    /**
     * 根据中文名查询人员信息
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public ManagerStaff findManagerStaffByChsName(String account);
}
