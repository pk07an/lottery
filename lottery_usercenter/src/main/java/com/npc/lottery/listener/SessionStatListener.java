package com.npc.lottery.listener;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.SessionStatInfo;

/**
 * 系统session监听
 * 
 * @author none
 * 
 */
public class SessionStatListener implements HttpSessionAttributeListener, HttpSessionListener {
	private static Logger log = Logger.getLogger(SessionStatListener.class);

	static {
		if (SessionStatInfo.managerSessionList == null) {
			SessionStatInfo.managerSessionList = Collections.synchronizedList(new ArrayList<SessionStatInfo>());
		}
	}

	public void attributeAdded(HttpSessionBindingEvent e) {
		HttpSession session = e.getSession();

		log.error("系统增加 SESSION");

		String attrName = e.getName();

		if (attrName.equals(Constant.MANAGER_LOGIN_INFO_IN_SESSION)) {
			// 管理登录
			ManagerUser nowUser = (ManagerUser) e.getValue();
			// 遍历所有session
			for (int i = SessionStatInfo.managerSessionList.size() - 1; i >= 0; i--) {
				SessionStatInfo entity = SessionStatInfo.managerSessionList.get(i);
				if (entity.getAccount().equals(nowUser.getAccount())) {
					try {
						entity.getSession().removeAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
					} catch (Exception ex) {
						log.error(ex);
						SessionStatInfo.managerSessionList.remove(i);
					}
					break;
				}
			}

			SessionStatInfo entityNow = new SessionStatInfo();
			entityNow.setAccount(nowUser.getAccount());
			entityNow.setUserName(nowUser.getChsName());
			entityNow.setUserType(nowUser.getUserType());
			entityNow.setSession(session);
			entityNow.setSessionID(session.getId());
			entityNow.setSafeCode(nowUser.getSafetyCode());
			entityNow.setIP(nowUser.getLoginIp());

			SessionStatInfo.managerSessionList.add(entityNow);
		}

	}

	public void attributeRemoved(HttpSessionBindingEvent e) {
		log.error("系统删除 SESSION");

		String attrName = e.getName();
		// 登录
		if (attrName.equals(Constant.MANAGER_LOGIN_INFO_IN_SESSION)) {
			// 管理登录
			ManagerUser nowUser = (ManagerUser) e.getValue();
			// 遍历所有session
			for (int i = SessionStatInfo.managerSessionList.size() - 1; i >= 0; i--) {
				SessionStatInfo entity = SessionStatInfo.managerSessionList.get(i);
				if (entity.getAccount().equals(nowUser.getAccount())) {
					SessionStatInfo.managerSessionList.remove(i);

					break;
				}
			}
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent e) {
		HttpSession session = e.getSession();

		log.error("系统替换 SESSION");

		String attrName = e.getName();
		int delS = -1;
		// 登录
		if (attrName.equals(Constant.MANAGER_LOGIN_INFO_IN_SESSION)) {
			// 管理登录
			// User nowUser = (User) e.getValue();//old value
			ManagerUser nowUser = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);// 当前session中的user
			// 遍历所有session
			for (int i = SessionStatInfo.managerSessionList.size() - 1; i >= 0; i--) {
				SessionStatInfo entity = SessionStatInfo.managerSessionList.get(i);
				if (entity.getAccount().equals(nowUser.getAccount()) && !entity.getSessionID().equals(session.getId())) {
					delS = i;
				} else if (entity.getSessionID().equals(session.getId())) {
					entity.setAccount(nowUser.getAccount());
				}
			}

			if (delS != -1) {
				// SessionStatInfo.managerSessionList.get(delS).getSession()
				// .invalidate();//失效时自动调用了remove方法。也就会把它从SessionStatInfo.managerSessionList中移除了
				SessionStatInfo.managerSessionList.get(delS).getSession()
				        .removeAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			}
		}
	}

	public void sessionCreated(HttpSessionEvent e) {
	}

	public void sessionDestroyed(HttpSessionEvent e) {
	}

}
