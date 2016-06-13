<!--
	用户的角色授权：主界面
-->

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String contextPath = request.getContextPath();
%>

<html>
<head>
	<title> .:: 用户的角色授权 ::. </title>
</head>

<form name="authorizUserRoleForm" action="<%=contextPath%>/sysmge/authoriz/authorizUserRoleSubmit.action">
	<s:hidden name="userID"/>
	<s:hidden name="rolesList"/>
	<s:hidden name="userType"/>
</form>
	
<frameset border='0' frameSpacing='0' rows='*' frameBorder='no'>
	<frameset id='frmWorkSpace' border='0' frameSpacing='0' frameBorder='no' rows='45,*'>
        <frame name="userRoleData" src="<%=contextPath%>/sysmge/authoriz/userRoleData.action?userID=<%=request.getParameter("userID")%>&userType=<%=request.getParameter("userType")%>"  scrolling='no' border='0'>
        <frame name="userRoleSelectList" src="<%=contextPath%>/sysmge/authoriz/userRoleSelectList.action?userID=<%=request.getParameter("userID")%>&userType=<%=request.getParameter("userType")%>"  scrolling='no' border='0'>
    </frameset>
</frameset>
	
</html>