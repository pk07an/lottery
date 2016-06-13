package com.npc.lottery.boss.logic.hibernate;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.service.IBaseLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.Page;

/**
 * 功能逻辑处理类
 * 
 * @author Eric
 * 
 */
public interface IShopsLogic extends IBaseLogic{
/**
 * 保存商铺注册信息,注冊商铺时同时新建一个总监用户
 * @return  此信息记录所对应的ID，Long类型
 */
public void saveShopsRegister(ShopsInfo shopsInfo,ChiefStaffExt entity);
//分页查询所有商铺信息
public Page<ShopsInfo> findShopsPage(Page<ShopsInfo> page);
//根据商铺号查询商铺信息
public ShopsInfo findShopsCode(String shopsCode);
//根据商铺名称查询商铺信息
public ShopsInfo findShopsName(String shopsName);
/**
 * 更新信息
 * 
 * @param entity 待更新的信息
 */
public void update(ShopsInfo entity);


public String verifyShopState(String shopsCode);
/**
 * 盤起停開 廣東
 * @param periodsNum
 */
public void updateGDStopStart(String periodsNum);
/**
 * 盤起停開 香港
 * @param periodsNum
 */
public void updateHKStopStart(String periodsNum);
/**
 * 盤起停開 重慶
 * @param periodsNum
 */
public void updateCQStopStart(String periodsNum);
public List<ShopsInfo> findShopsList(Criterion[] criterions);

public void updateBJStopStart(String periodsNum);





}
