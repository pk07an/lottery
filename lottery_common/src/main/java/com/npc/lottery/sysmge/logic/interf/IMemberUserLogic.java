package com.npc.lottery.sysmge.logic.interf;

import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;

/**
 * 会员用户信息的逻辑处理类接口
 * 
 * 此类涉及会员用户相关的业务逻辑，包括人员信息（MemberStaff）、人员权限等
 * 
 * @author none
 *
 */
public interface IMemberUserLogic {

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return  数据不存在则返回 null
     */
    public MemberUser findMemberUserByID(Long userID);

    /**
     * 根据用户名查询用户信息
     * 
     * @param userName  用户名
     * @return  数据不存在则返回 null
     */
    public MemberUser findMemberUserByName(String userName);

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息       
     * @return
     */
    public long findAmountMemberUserList(ConditionData conditionData);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveMemberStaff(MemberStaff entity);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息   
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return MemberUser 类型的 List
     */
    public List<MemberUser> findMemberUserList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(MemberStaff entity);
}
