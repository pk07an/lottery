package com.npc.lottery.common.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAdminLotteryAction extends ActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ManagerUser getCurrentUser()
	{
		
		return  (ManagerUser) this.getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

		
	}
	
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	
	public String ajaxJson(Map<String, String> jsonMap) {
		return ajax(JSONObject.toJSONString(jsonMap), "text/html");
	}
	
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}
	
	protected String findParameter(String name) {
		return getRequest().getParameter(name);
	}

	protected float findParamFloat(String param) {
		return Float.parseFloat(findParameter(param));
	}

	protected short findParamShort(String param) {
		return Short.parseShort(findParameter(param));
	}

	protected int findParamInt(String param) {
		return Integer.parseInt(findParameter(param));
	}

	protected long findParamLong(String param) {
		return Long.parseLong(findParameter(param));
	}
	
}
