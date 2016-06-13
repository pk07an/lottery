package com.npc.lottery.sysmge.dao.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.MemberStaff;

/**
 * 基础会员用户所对应的数据库实现类接口
 * 
 * @author none
 *
 */
public interface IMemberStaffDao {

    /**
     * 统计满足指定查询条件的人员信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountMemberStaffList(final ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  MemberStaff 类型的 List
     */
    public List findMemberStaffList(final ConditionData conditionData,
            final int firstResult, final int maxResults);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveMemberStaff(MemberStaff entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateMemberStaff(MemberStaff entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delMemberStaff(MemberStaff entity);

}
