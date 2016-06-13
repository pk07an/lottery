package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.npc.lottery.util.ListEntity;

/**
 * 记录整个系统中所登录的用户信息
 * 
 * @author none
 *
 */
public class SessionStatInfo implements Serializable {

    public static List<SessionStatInfo> managerSessionList;//管理SESSION列表

    private String sessionID;//sessionID
    private String account;//账号
    private String userName;//用户中文名
    private String safeCode;//安全码
    private String userType;//用户类型
    private String IP;//登陆IP
    private HttpSession session;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSafeCode() {
        return safeCode;
    }

    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserTypeName() {

        String result = "<font color='FF0000'>\u6570\u636e\u9519\u8bef</font>";//数据错误

        try {
            if (ManagerStaff.USER_TYPE_SYS.equalsIgnoreCase(userType)) {
                result = "系统管理员";
            } else if (ManagerStaff.USER_TYPE_MANAGER
                    .equalsIgnoreCase(userType)) {
                result = "总管";
            } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
                result = "总监";
            } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
                result = "分公司";
            } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                    .equalsIgnoreCase(userType)) {
                result = "股东";
            } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                    .equalsIgnoreCase(userType)) {
                result = "总代理";
            } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
                result = "代理";
            } else if (ManagerStaff.USER_TYPE_SUB.equalsIgnoreCase(userType)) {
                result = "子账号";
            } else if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
                result = "会员";
            }
        } catch (Exception ex) {

        }

        return result;
    }

    /**
     * 获取 userType 字段所对应的值 和 中文名称List信息
     * 
     * key = userType 的值
     * name = 对应的中文名称
     * 
     * @param canBlank 是否添加空值
     * 
     * @return
     */
    public static ArrayList<ListEntity> getUserTypeList(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_SYS);
        entity.setName("系统管理员");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_MANAGER);
        entity.setName("总管");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_CHIEF);
        entity.setName("总监");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_BRANCH);
        entity.setName("分公司");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_STOCKHOLDER);
        entity.setName("股东");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_GEN_AGENT);
        entity.setName("总代理");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_AGENT);
        entity.setName("代理");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_SUB);
        entity.setName("子账号");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(MemberStaff.USER_TYPE_MEMBER);
        entity.setName("会员");
        resultList.add(entity);

        return resultList;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    /**
     * 判断用户是否登录
     * 
     * @param account   登陆账号
     * @param userType  用户类型
     * @return  true 用户登陆；false 用户未登陆
     */
    public static boolean isInLine(String account, String userType) {
        boolean result = false;

        if (null != managerSessionList) {
            SessionStatInfo entity;
            for (int i = 0; i < managerSessionList.size(); i++) {
                entity = managerSessionList.get(i);
                if ((entity.getAccount().trim().equalsIgnoreCase(account))
                        && (entity.getUserType().trim()
                                .equalsIgnoreCase(userType))) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 判断对应商铺的在线人数
     * 
     * @param safeCode  商铺编号
     * @return
     */
    public static Long inLineNum(String safeCode) {

        Long result = 0L;

        if (null != managerSessionList) {
            SessionStatInfo entity;
            for (int i = 0; i < managerSessionList.size(); i++) {
                entity = managerSessionList.get(i);
                if(entity.getSafeCode().trim().equalsIgnoreCase(safeCode)){
                    result = result + 1;
                }
            }
        }

        return result;
    }
    /**
     * 统计在线人数   分别统计管理人员和会员的在线人数
     * 
     * @param safeCode  商铺编号
     * @return
     */
    public static Map<String,String> inLineNumByUserType(String safeCode) {
    	Map<String,String> map = new HashMap<String,String>();
    	Integer managerAmount = 0;
        Integer userAmount = 0;
    	
    	if (null != managerSessionList) {
    		SessionStatInfo entity;
    		for (int i = 0; i < managerSessionList.size(); i++) {
    			entity = managerSessionList.get(i);
    			if(entity.getSafeCode().trim().equalsIgnoreCase(safeCode)){
    				if (!(entity.getUserType().trim().equalsIgnoreCase(MemberStaff.USER_TYPE_MEMBER))) {
    	            	managerAmount += 1;
    	            }else{
    	            	userAmount += 1;
    	            }
    			}
    		}
    	}
    	map.put("member", userAmount+"");
        map.put("manager", managerAmount+"");
    	return map;
    }
}
