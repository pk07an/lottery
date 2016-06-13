<!--
	功能信息查看：主界面
-->

<%@ page language="java" pageEncoding="UTF-8" %>

<%
	String contextPath = request.getContextPath();
%>

<html>
	<head>
		<title> .:: 功能信息 ::. </title>
	</head>
	
	<frameset border=0 frameSpacing=0 rows=85,* frameBorder=no>
		<frame name="viewLocation" src="<%=contextPath%>/sysmge/function/viewLocation.action" noResize scrolling=no>
		<frameset id=frmWorkSpace border=0 frameSpacing=0 frameBorder=no cols=200,*>
			<frame name="viewTreeList" src="<%=contextPath%>/sysmge/function/viewTreeList.action"  scrolling=no border=0>
			<frame name="viewDetail" src="<%=contextPath%>/sysmge/function/viewDetail.action?ID=1"  scrolling=no border=0>
		</frameset> 
	</frameset>
	
</html>