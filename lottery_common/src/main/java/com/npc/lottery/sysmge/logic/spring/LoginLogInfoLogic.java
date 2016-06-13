package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.ILoginLogInfoDao;
import com.npc.lottery.sysmge.entity.LoginLogInfo;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.logic.interf.ILoginLogInfoLogic;
import com.npc.lottery.util.Tool;

/**
 * 
 * 登陆日志业务处理类
 */
public class LoginLogInfoLogic implements ILoginLogInfoLogic {

    private ILoginLogInfoDao loginLogInfoDao = null;

    public void setLoginLogInfoDao(ILoginLogInfoDao loginLogInfoDao) {
        this.loginLogInfoDao = loginLogInfoDao;
    }

    public ILoginLogInfoDao getLoginLogInfoDao() {
        return loginLogInfoDao;
    }

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
            ConditionData conditionData, int pageCurrentNo, int pageSize) {
        ArrayList<LoginLogInfo> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        //增加排序条件
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addOrder("loginDate", ConditionData.ORDER_TYPE_DESC);

        // 查询数据
        try {
            resultList = (ArrayList<LoginLogInfo>) loginLogInfoDao
                    .findLoginLogInfoList(conditionData, firstResult,
                            maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

    /**
     * 统计满足指定查询条件的记录数目
     * 
     * @param conditionData
     *            查询条件信息        
     * @return
     */
    public long findAmountLoginLogInfo(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = loginLogInfoDao.findAmountLoginLogInfo(conditionData);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return amount;
    }

    /**
     * 根据ID查询功能数据
     * 
     * @param ID
     * @return
     */
    public LoginLogInfo getLoginLogInfoByID(long ID) {

        LoginLogInfo entity = null;

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findLoginLogInfoList(conditionData, 1, 9999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (LoginLogInfo) resultList.get(0);
        }

        return entity;
    }

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(LoginLogInfo entity) {
        loginLogInfoDao.saveOrUpdate(entity);
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveLoginLogInfo(LoginLogInfo entity) {
        return loginLogInfoDao.saveLoginLogInfo(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(LoginLogInfo entity) {
        loginLogInfoDao.delete(entity);
    }
}
