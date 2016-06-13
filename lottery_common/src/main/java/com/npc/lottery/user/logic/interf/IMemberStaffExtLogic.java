package com.npc.lottery.user.logic.interf;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.Page;

public interface IMemberStaffExtLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountMemberStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return MemberStaffExt 类型的 List
     */
    public List<MemberStaffExt> findMemberStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public MemberStaffExt findMemberStaffExtByID(Long userID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveMemberStaffExt(MemberStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateMemberStaffExt(MemberStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delMemberStaffExt(MemberStaffExt entity);
    
    public MemberStaffExt queryMemberStaffExt(String propertyName, Object value);
    
    public Page<MemberStaffExt> findPage( Page<MemberStaffExt> page, Criterion... criterions);
    
    public Page<MemberStaffExt> findPage(Page page,ManagerStaff userInfo, String userStatus, String userManager, String account, String chName);
    
    public Map<String,Integer> findRate(MemberStaffExt entity);
    
    public List<MemberStaffExt> findAllMemeber();
    
    public Map<String,Integer> findManagerRate(ManagerUser userInfo);
    
    /*
     * 保存 會員 提取相關邏輯到事物
     */
    public Long saveUserMemberStaff(MemberStaffExt entity,Long createUserId);
    
    /*
     * 修改會員 提取相關邏輯到事物
     */
    public void updateUserMemberStaff(MemberStaffExt entity);

	public void updateUserMemberStaff(MemberStaffExt memberStaffExt, ManagerUser currentUser);

	/**
	 * 获取会员的状态
	 * @param id
	 * @return
	 */
	public abstract String getMemberFlagById(long id);
	
	/**
	 * 恢复会员信用额度
	 * @param scheme
	 * @return
	 */
	public int updateMemberAvailableCreditToTotal(String scheme);
    
}
