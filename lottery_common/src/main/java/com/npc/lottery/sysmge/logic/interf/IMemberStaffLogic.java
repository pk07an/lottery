package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.MemberStaff;

/**
 * 基础会员用户所对应的逻辑实现类接口
 * 
 * @author none
 *
 */
public interface IMemberStaffLogic {


    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountMemberStaffList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return MemberStaff 类型的 List
     */
    public List findMemberStaffList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public MemberStaff findMemberStaffByID(Long userID);

    /**
     * 根据登陆账号查询人员信息
     * 
     * @param account
     *            登陆账号
     * @return
     */
    public MemberStaff findMemberStaffByAccount(String account);

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
