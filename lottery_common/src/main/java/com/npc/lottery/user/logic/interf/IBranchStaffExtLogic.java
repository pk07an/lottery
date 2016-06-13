package com.npc.lottery.user.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.util.Page;

public interface IBranchStaffExtLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountBranchStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return BranchStaffExt 类型的 List
     */
    public List findBranchStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public BranchStaffExt findBranchStaffExtByID(Long userID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveBranchStaffExt(BranchStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateBranchStaffExt(BranchStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delBranchStaffExt(BranchStaffExt entity);
    
    public BranchStaffExt queryBranchStaffExt(String propertyName, Object value);
    
    public List<BranchStaffExt> queryAllBranchStaffExt(String propertyName, Object value);
    
    public Page<BranchStaffExt> findPage( Page<BranchStaffExt> page, Criterion... criterions);
    
    public Page<BranchStaffExt> findPage(Page page,ManagerUser userInfo, String userStatus, String account, String chName);
    
    public Page<BranchStaffExt> findSubPage(Page page,ManagerUser userInfo);
    
    /*
     * 保存分公司 提取相關邏輯到事物
     */
    public Long saveUserBranchStaff(BranchStaffExt entity,Long chiefId,Long createUserId);
    
    /*
     * 修改分公司 提取相關邏輯到事物
     */
    public void updateUserBranchStaff(BranchStaffExt entity,String currentUserType);
    
    public BranchStaffExt findById(long id,String scheme);
}
