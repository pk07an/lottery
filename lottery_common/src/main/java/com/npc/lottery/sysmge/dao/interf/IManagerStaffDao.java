package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.user.entity.ChiefStaffExt;

/**
 * 基础管理用户所对应的数据库实现类接口
 * 
 * @author none
 *
 */
public interface IManagerStaffDao {
	
	
	
	public ManagerStaff findManagerStaffByAccount(String account);

    /**
     * 统计满足指定查询条件的人员信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountManagerStaffList(final ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  ManagerStaff 类型的 List
     */
    public List findManagerStaffList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

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
     * 根据总监查询商铺号
     * @param account
     */
    public List<ManagerStaff> findShopsCodeByChief(Long userID);
    /**
     * 通过商铺号查总监
     * @param shopsCode
     */
    public List<ChiefStaffExt> findChiefByShopsCode(String shopsCode);
    
    /**
     * 通过商铺号和scheme查总监  jdbc查询
     * @param shopsCode 商铺号
     * @param scheme 表前缀
     * @return
     */
    public List<ChiefStaffExt> findChiefByShopsCodeByScheme(String shopsCode,String scheme);
 
}
