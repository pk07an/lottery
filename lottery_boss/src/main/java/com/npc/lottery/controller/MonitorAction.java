package com.npc.lottery.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.SessionStatInfo;
import com.npc.lottery.sysmge.logic.interf.IMonitorLogic;
import com.npc.lottery.util.Tool;

/**
 * 系统监控
 * 
 * @author none
 *
 */
public class MonitorAction extends BaseAction {

    private static Logger log = Logger.getLogger(MonitorAction.class);

    private IMonitorLogic monitorLogic;

    public void setMonitorLogic(IMonitorLogic monitorLogic) {
        this.monitorLogic = monitorLogic;
    }

    //sessionList
    /**
     * 列表
     * 
     * @return
     * @throws Exception
     */
    public String sessionList() throws Exception {

        log.info("sessionList");

        HttpServletRequest request = ServletActionContext.getRequest();
        
        //获取请求的页码
        String pageCurrentNoStr = Tool.getParameter(request,
                Constant.PAGETAG_CURRENT, "1");
        int pageCurrentNo = 1;
        try {
            pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
        } catch (Exception ex) {

        }
        boolean accountFlag = false;
        boolean userTypeFlag = false;
        if (null != account && 0 != account.trim().length()) {
            accountFlag = true;
        }
        if (null != userType && 0 != userType.trim().length()) {
            userTypeFlag = true;
        }

        //获取记录总数
        List<SessionStatInfo> managerSessionList = null;
        long recordAmount = 0;
        if (accountFlag && userTypeFlag) {
            //查询登陆账号及用户类型
            recordAmount = monitorLogic
                    .findAmountSessionList(account, userType);
            managerSessionList = monitorLogic.findSessionList(account,
                    userType, pageCurrentNo, Constant.LIST_PAGE_SIZE);
        } else if (accountFlag) {
            //查询登陆账号
            recordAmount = monitorLogic.findAmountSessionListByAccount(account);
            managerSessionList = monitorLogic.findSessionListByAccount(account,
                    pageCurrentNo, Constant.LIST_PAGE_SIZE);
        } else if (userTypeFlag) {
            //查询用户类型
            recordAmount = monitorLogic
                    .findAmountSessionListByUserType(userType);
            managerSessionList = monitorLogic.findSessionListByUserType(
                    userType, pageCurrentNo, Constant.LIST_PAGE_SIZE);
        } else {
            //查询所有
            recordAmount = monitorLogic.findAmountSessionList();
            managerSessionList = monitorLogic.findSessionList(pageCurrentNo,
                    Constant.LIST_PAGE_SIZE);
        }

        log.info("当前登录用户信息列表：" + managerSessionList);

        //获取记录总数
        log.info("记录总数：" + recordAmount);
        long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

        //保存返回的数据记录集
        request.setAttribute("sumPages", sumPages + "");
        request.setAttribute("recordAmount", recordAmount + "");
        request.setAttribute("pageCurrentNo", pageCurrentNo + "");
        request.setAttribute("resultList", managerSessionList);
        request.setAttribute("userTypeList",
                SessionStatInfo.getUserTypeList(true));//用户类型的页面下拉列表数据

        return "sessionList";
    }

    private String account;//账号
    private String userName;//用户中文名
    private String safeCode;//安全码
    private String userType;//用户类型

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSafeCode() {
        return safeCode;
    }

    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
