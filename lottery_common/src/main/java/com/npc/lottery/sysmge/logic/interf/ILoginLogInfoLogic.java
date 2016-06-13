package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.LoginLogInfo;

/**
 * 登陆日志业务处理类接口
 * 
 */
public interface ILoginLogInfoLogic {

    /**
     * 查询满足指定查询条件的功能信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  LoginLogInfo 类型的 List
     */
    public ArrayList<LoginLogInfo> findLoginLogInfoList(
            ConditionData conditionData, int pageCurrentNo, int pageSize);

    /**
     * 统计满足指定查询条件的记录数目
     * 
     * @param conditionData
     *            查询条件信息        
     * @return
     */
    public long findAmountLoginLogInfo(ConditionData conditionData);

    /**
     * 根据ID查询功能数据
     * 
     * @param ID
     * @return
     */
    public LoginLogInfo getLoginLogInfoByID(long ID);

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(LoginLogInfo entity);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveLoginLogInfo(LoginLogInfo entity);

    /**
     * 删除对象
     * @param entity
     */
    public void delete(LoginLogInfo entity);
}
