<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="
		com.npc.lottery.common.Constant,
		java.util.HashMap
"%>
<html>

<head>
<title>提示信息</title>

<%
    //工程上下文，涉及路径的资源，在写访问路径时一般均要加上，如引用一个图片
    request.setAttribute("contextPath",request.getContextPath());
	String info = (String)request.getAttribute(Constant.INFO_PAGE_MESSAGE);
	String userState = (String)request.getAttribute("userState");
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>
<Script Language="JavaScript">
alert("<%=info%>");
<%if(userState!=null && "userFreeze".equals(userState)){%>
window.location.href="${pageContext.request.contextPath}/user/queryPopupMenus.action?type=message"; 
<%}else{%>
window.location.href="${pageContext.request.contextPath}/managerLogin.jsp"; 
<%}%>
</Script>
</head> 
</html>
