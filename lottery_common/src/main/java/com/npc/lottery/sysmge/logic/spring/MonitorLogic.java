package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;

import com.npc.lottery.sysmge.entity.SessionStatInfo;
import com.npc.lottery.sysmge.logic.interf.IMonitorLogic;

/**
 * 系统监控逻辑处理类
 * 
 * @author none
 *
 */
public class MonitorLogic implements IMonitorLogic {

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
            int pageSize) {
        ArrayList<SessionStatInfo> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        int endResult = firstResult + pageSize;
        if (endResult > managerSessionList.size()) {
            endResult = managerSessionList.size();
        }

        resultList = new ArrayList<SessionStatInfo>(managerSessionList.subList(
                firstResult, endResult));

        return resultList;
    }

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @return
     */
    public long findAmountSessionList() {
        long amount = 0;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        amount = managerSessionList.size();

        return amount;
    }

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
            int pageCurrentNo, int pageSize) {
        ArrayList<SessionStatInfo> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (entity.getAccount().indexOf(account) < 0) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        int endResult = firstResult + pageSize;
        if (endResult > managerSessionList.size()) {
            endResult = managerSessionList.size();
        }

        resultList = new ArrayList<SessionStatInfo>(managerSessionList.subList(
                firstResult, endResult));

        return resultList;
    }

    /**
     * 根据登陆账号查询数据（模糊查询）
     * 
     * @param   account     登陆账号（模糊查询）
     * 
     * @return
     */
    public long findAmountSessionListByAccount(String account) {
        long amount = 0;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (entity.getAccount().indexOf(account) < 0) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        amount = managerSessionList.size();

        return amount;
    }

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
            String userType, int pageCurrentNo, int pageSize) {
        ArrayList<SessionStatInfo> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (!entity.getUserType().trim().equalsIgnoreCase(userType)) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        int endResult = firstResult + pageSize;
        if (endResult > managerSessionList.size()) {
            endResult = managerSessionList.size();
        }

        resultList = new ArrayList<SessionStatInfo>(managerSessionList.subList(
                firstResult, endResult));

        return resultList;
    }

    /**
     * 根据用户类型查询数据
     * 
     * @param userType
     *            用户类型
     * 
     * @return
     */
    public long findAmountSessionListByUserType(String userType) {
        long amount = 0;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (!entity.getUserType().trim().equalsIgnoreCase(userType)) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        amount = managerSessionList.size();

        return amount;
    }

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
            String userType, int pageCurrentNo, int pageSize) {
        ArrayList<SessionStatInfo> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (!(entity.getUserType().trim().equalsIgnoreCase(userType))
                    || entity.getAccount().indexOf(account) < 0) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        int endResult = firstResult + pageSize;
        if (endResult > managerSessionList.size()) {
            endResult = managerSessionList.size();
        }

        resultList = new ArrayList<SessionStatInfo>(managerSessionList.subList(
                firstResult, endResult));

        return resultList;
    }

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
    public long findAmountSessionList(String account, String userType) {
        long amount = 0;

        ArrayList<SessionStatInfo> managerSessionList = new ArrayList<SessionStatInfo>(
                SessionStatInfo.managerSessionList);

        SessionStatInfo entity;
        for (int i = 0; i < managerSessionList.size(); i++) {
            entity = managerSessionList.get(i);
            if (!(entity.getUserType().trim().equalsIgnoreCase(userType))
                    || entity.getAccount().indexOf(account) < 0) {
                //不匹配则移除
                managerSessionList.remove(i);
                i--;
            }
        }

        amount = managerSessionList.size();

        return amount;
    }

}
