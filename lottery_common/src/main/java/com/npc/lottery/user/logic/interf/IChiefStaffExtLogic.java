package com.npc.lottery.user.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.Page;

public interface IChiefStaffExtLogic {

    /**
     * 统计满足指定查询条件的人员信息记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountChiefStaffExtList(ConditionData conditionData);

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息         
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return ChiefStaffExt 类型的 List
     */
    public List<ChiefStaffExt> findChiefStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据ID查询人员信息
     * 
     * @param userID
     *            人员信息ID
     * @return
     */
    public ChiefStaffExt findChiefStaffExtByID(Long userID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveChiefStaffExt(ChiefStaffExt entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateChiefStaffExt(ChiefStaffExt entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delChiefStaffExt(ChiefStaffExt entity);
    
    public ChiefStaffExt queryChiefStaffExt(String propertyName, Object value);
    
    public List<ChiefStaffExt> queryAllChiefStaffExt(String propertyName, Object value);
    //根据商铺号查询总监
    public List<ChiefStaffExt> queryChiefByShops(Object[] values);
    
    public List<ChiefStaffExt> queryAllChiefStaffExt(Criterion... criterions);
    
    public Page<ChiefStaffExt> findPage( Page<ChiefStaffExt> page, Criterion... criterions);
    
    public Page<ChiefStaffExt> findSubPage(Page page, ManagerUser userInfo);
    
    /**
     * 更新用户密码，总管恢复总监密码所用到,密码更新为用户名
     * @param entity
     * @param scheme
     */
    public void updateChiefPassword(ChiefStaffExt entity,String scheme);
    
    /**
     * 根据商铺号查询总监信息  ，使用scheme查询
     * @param shopsCode  商铺号
     * @param scheme
     * @return
     */
    public ChiefStaffExt queryChiefByShopCode(String shopsCode,String scheme);
}
