package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.Roles;

/**
 * 角色逻辑处理类
 * 
 */
public interface IRolesLogic {

    /**
     * 查询满足指定查询条件的角色信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return Roles 类型的 List
     */
    public List findRolesList(ConditionData conditionData, int pageCurrentNo,
            int pageSize);

    /**
     * 统计满足指定查询条件的记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountRecord(ConditionData conditionData);

    /**
     * 查询所有的功能权限类型的角色
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesRoleTypeFunc(
            ConditionData conditionData, int pageCurrentNo, int pageSize);

    /**
     * 统计满足指定查询条件的功能权限类型的角色数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountAllRolesRoleTypeFunc(ConditionData conditionData);

    /**
     * 查询所有的数据权限类型的角色
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesRoleTypeData(
            ConditionData conditionData, int pageCurrentNo, int pageSize);

    /**
     * 统计满足指定查询条件的数据权限类型的角色数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountAllRolesRoleTypeData(ConditionData conditionData);

    /**
     * 查找指定用户的私有功能角色
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @param createNew     此用户所对应的私有权限不存在时是否创建，true创建，false不创建
     * @return  未查询到数据或者失败则返回null
     */
    public Roles findPrivateFuncRole(Long userID, String userType,
            boolean createNew);

    /**
     * 查询所有的角色信息
     * 
     * @return Roles 类型的 List
     */
    public List findAll();

    /**
     * 根据ID查询角色数据
     * 
     * @param ID
     * @return
     */
    public Roles findByID(Long ID);

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(Roles entity);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveRoles(Roles entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Roles entity);

    /**
     * 根据角色编码查询对应的角色
     * 
     * @param roleCode
     * @return
     */
    public Roles findRolesByCode(String roleCode);
}
