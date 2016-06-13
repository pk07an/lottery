package com.npc.lottery.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.GetURL;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action基类
 * 
 * @author none
 *
 */
public class BaseAction extends ActionSupport implements ServletResponseAware,
		ServletRequestAware {

	protected HttpServletResponse response;
	protected HttpServletRequest request;

	public void setServletResponse(HttpServletResponse response) {

		//工程上下文，jsp页面上涉及路径的资源，在写访问路径时一般均要加上${contextPath}引用（如引用一个图片）
		request.setAttribute("contextPath", request.getContextPath());

		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public String getSafetyCodeFromUrl(){
		String full = request.getServerName();
		String safetyCode = GetURL.getSafetyCodeFromUrl(full);
		return safetyCode;
	}
	
	public MemberUser getCurrentUser() {

		return (MemberUser) this.getRequest().getSession(true)
				.getAttribute(Constant.MEMBER_LOGIN_INFO_IN_SESSION);

	}

}
