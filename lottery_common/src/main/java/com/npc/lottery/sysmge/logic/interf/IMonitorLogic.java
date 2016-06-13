package com.npc.lottery.sysmge.logic.interf;

import java.util.ArrayList;

import com.npc.lottery.sysmge.entity.SessionStatInfo;

/**
 * 系统监控逻辑处理类接口
 * 
 * @author none
 *
 */
public interface IMonitorLogic {

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  SessionStatInfo 类型的 ArrayList
     */
    public ArrayList<SessionStatInfo> findSessionList(int pageCurrentNo,
            int pageSize);

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @return
     */
    public long findAmountSessionList();

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @param   account     登陆账号（模糊查询）
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  SessionStatInfo 类型的 ArrayList
     */
    public ArrayList<SessionStatInfo> findSessionListByAccount(String account,
            int pageCurrentNo, int pageSize);

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @param   account     登陆账号（模糊查询）
     * 
     * @return
     */
    public long findAmountSessionListByAccount(String account);

    /**
     * 根据用户类型查询数据
     * 
     * @param userType
     *            用户类型
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  SessionStatInfo 类型的 ArrayList
     */
    public ArrayList<SessionStatInfo> findSessionListByUserType(
            String userType, int pageCurrentNo, int pageSize);

    /**
     * 根据用户类型查询数据
     * 
     * @param userType
     *            用户类型
     * 
     * @return
     */
    public long findAmountSessionListByUserType(String userType);

    /**
     * 根据登陆账号及用户类型查询数据
     * 
     * @param account
     *            用户账号（模糊查询）
     * @param userType
     *            用户类型
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  SessionStatInfo 类型的 ArrayList
     */
    public ArrayList<SessionStatInfo> findSessionList(String account,
            String userType, int pageCurrentNo, int pageSize);

    /**
     * 根据用户类型查询数据
     * 
     * @param account
     *            登陆账号（模糊查询）
     * @param userType
     *            用户类型
     * 
     * @return
     */
    public long findAmountSessionList(String account, String userType);

}
