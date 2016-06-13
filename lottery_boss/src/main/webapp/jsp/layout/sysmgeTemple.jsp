<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser" %>
<%@ page import="com.npc.lottery.common.Constant" %>
<%@ taglib uri="/WEB-INF/tag/sitemesh-decorator.tld" prefix="decorator" %>

<script>
//退出
function exit(){
	if(confirm("确定退出吗？")){
		if(parent.document.exitForm.isFrame){
			//框架内部的，则跳转到上一级退出
			parent.document.exitForm.submit();
			return true;
		}else{
			document.exitForm.submit();
			return true;
		}
	}
	
	return false;
}
</script>

<html>
  <head>
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/layout_sysmge.css">
  <script language="javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <title>系统管理后台 — <decorator:title default="Welcome! 装饰器页面。。。" /></title>
    <decorator:head></decorator:head>
  </head>
 <body>
<%
	String contextPath = request.getContextPath();
	//读取登陆用户信息
	ManagerUser user = (ManagerUser)session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
	String userName = "<font color='#FF0000'>未登陆</font>";
	if(null != user){
	    userName = user.getChsName();
	}
%>
<!-- 退出 -->
<form name="exitForm" method="post" action="${pageContext.request.contextPath}/sysmge/logoutManager.action">
	
</form>
<!--头部开始-->
<div class="header m2">
  <div class="logo"><img src="${pageContext.request.contextPath}/images/images_sysmge/logo.gif" width="240" height="80" /></div>
  <div class="nav"><a href="#">帮助</a>|<a href="#" onClick="return exit()">退出</a></div>
  <div class="top_info"><%=userName%></div>
</div>
<!--头部结束-->
<!--分割线开始-->
<div class="fenge m2 nofont"></div>
<!--分割线结束-->
<!--菜單开始-->
<div id="menu" class="m2">
	<a href="${pageContext.request.contextPath}/sysmge/manageruser/list.action?menuNum=1" target="_top" <s:if test="menuNum==1">class="current"</s:if>>基础用户管理</a>
	<a href="${pageContext.request.contextPath}/sysmge/memberuser/list.action" target="_top">会员用户管理</a>
	<a href="${pageContext.request.contextPath}/sysmge/roles/list.action?menuNum=2" target="_top">角色管理</a>
	<a href="${pageContext.request.contextPath}/sysmge/param/list.action" target="_top">参数维护</a>
	<a href="${pageContext.request.contextPath}/sysmge/function/modifyMain.action" target="_top">功能信息</a>
	<a href="${pageContext.request.contextPath}/sysmge/monitor/sessionList.action" target="_top">监控信息</a>
	<a href="${pageContext.request.contextPath}/demo/list.action" target="_top">Demo</a>
</div>
<!--菜單结束-->
<!--内容开始-->
<decorator:body ></decorator:body>
<!--内容结束-->
</body>
</html>
