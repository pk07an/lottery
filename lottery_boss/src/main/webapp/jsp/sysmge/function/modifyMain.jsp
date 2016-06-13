<!--
	功能信息编辑：主界面
-->

<%@ page language="java" pageEncoding="UTF-8" %>

<%
	String contextPath = request.getContextPath();
%>

<html>
	<head>
		<title> .:: 功能信息 ::. </title>
	</head>
	
	<!-- 退出页面 -->
	<form name="exitForm" method="post" action="<%=contextPath%>/sysmge/logoutManager.action">
		<input type="hidden" name="isFrame" value="true"/>
	</form>
	
	<frameset border=0 frameSpacing=0 rows=157,* frameBorder=no>
		<frame name="modifyLocation" src="<%=contextPath%>/sysmge/function/modifyLocation.action" noResize scrolling=no>
		<frameset id=frmWorkSpace border=0 frameSpacing=0 frameBorder=no cols=200,*>
			<frame name="modifyTreeList" src="<%=contextPath%>/sysmge/function/modifyTreeList.action"  scrolling=no border=0>
			<frame name="modifyDetail" src="<%=contextPath%>/sysmge/function/modifyDetail.action?ID=1"  scrolling=auto border=0>
		</frameset>
	</frameset>
	
</html>