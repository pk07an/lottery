package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.logic.interf.IMemberStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IMemberUserLogic;

/**
 * 会员用户信息的逻辑处理类
 * 
 * 此类涉及会员用户相关的业务逻辑，包括人员信息（MemberStaff）、人员权限等
 * 
 * @author none
 *
 */
public class MemberUserLogic implements IMemberUserLogic {


    private IMemberStaffLogic MemberStaffLogic;//人员信息逻辑处理类

    public void setMemberStaffLogic(IMemberStaffLogic MemberStaffLogic) {
        this.MemberStaffLogic = MemberStaffLogic;
    }

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return  数据不存在则返回 null
     */
    public MemberUser findMemberUserByID(Long userID) {
        MemberStaff entity = MemberStaffLogic.findMemberStaffByID(userID);//查询 staff

        if (null == entity) {
            return null;
        }

        MemberUser user = new MemberUser(entity);//根据 MemberStaff 构造 user

        return user;
    }

    /**
     * 根据用户名查询用户信息
     * 
     * @param userName  用户名
     * @return  数据不存在则返回 null
     */
    public MemberUser findMemberUserByName(String userName) {

        //判断查询条件是否为空
        if (null == userName) {
            return null;
        }

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("account", userName);

        List<MemberUser> userList = this.findMemberUserList(conditionData, 1,
                10);
        if (null == userList || 0 == userList.size()) {
            return null;
        }

        return userList.get(0);
    }

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息       
     * @return
     */
    public long findAmountMemberUserList(ConditionData conditionData) {
        return MemberStaffLogic.findAmountMemberStaffList(conditionData);
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveMemberStaff(MemberStaff entity) {
        return MemberStaffLogic.saveMemberStaff(entity);
    }

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
            int pageCurrentNo, int pageSize) {
        List<MemberUser> resultList = null;

        //增加人员显示顺序的排序条件
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addOrder("ID", ConditionData.ORDER_TYPE_ASC);
        //调用staffLogic方法
        List staffList = MemberStaffLogic.findMemberStaffList(conditionData,
                pageCurrentNo, pageSize);

        if (null != staffList) {
            resultList = new ArrayList<MemberUser>(staffList.size());
            //将 staffList 中的数据填充到结果信息列表中
            for (int i = 0; i < staffList.size(); i++) {
                MemberStaff staff = (MemberStaff) staffList.get(i);
                String userOrgName = null;
                resultList.add(new MemberUser(staff));
            }
        }

        return resultList;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(MemberStaff entity) {
        MemberStaffLogic.updateMemberStaff(entity);
    }
}
