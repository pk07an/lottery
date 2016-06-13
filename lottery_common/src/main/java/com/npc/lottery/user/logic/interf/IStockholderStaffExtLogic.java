package com.npc.lottery.user.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.util.Page;

public interface IStockholderStaffExtLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountStockholderStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return StockholderStaffExt 类型的 List
     */
    public List<StockholderStaffExt> findStockholderStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public StockholderStaffExt findStockholderStaffExtByID(Long userID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveStockholderStaffExt(StockholderStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateStockholderStaffExt(StockholderStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delStockholderStaffExt(StockholderStaffExt entity);
    
    public StockholderStaffExt queryStockholderStaffExt(String propertyName, Object value);
    
    /**
     * 根据id查询，scheme进行jdbc查询
     * @param id
     * @param scheme
     * @return
     */
    public StockholderStaffExt findById(long id, String scheme);
    
    public Page<StockholderStaffExt> findPage( Page<StockholderStaffExt> page, Criterion... criterions);
    
    public Page<StockholderStaffExt> findPage(Page page,ManagerStaff userInfo, String userStatus, String account, String chName);
    
    public Page<StockholderStaffExt> findSubPage(Page page, ManagerUser userInfo); 
    
    /*
     * 保存股東 提取相關邏輯到事物
     */
    public Long saveUserStockholderStaff(StockholderStaffExt entity,Long createUserId);
    
    /*
     * 修改股東 提取相關邏輯到事物
     */
    public void updateUserStockholderStaff(StockholderStaffExt entity,String currentUserType);
    
}
