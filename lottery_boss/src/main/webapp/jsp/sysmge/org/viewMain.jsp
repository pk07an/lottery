<!--
	机构信息查看：主界面
-->

<%@ page language="java" pageEncoding="UTF-8" %>

<%
	String contextPath = request.getContextPath();
%>

<html>
	<head>
		<title> .:: 机构信息 ::. </title>
	</head>
	
	<frameset border=0 frameSpacing=0 rows=30,* frameBorder=no>
		<frame name="viewLocation" src="<%=contextPath%>/sysmge/org/viewLocation.action" noResize scrolling=no>
		<frameset id=frmWorkSpace border=0 frameSpacing=0 frameBorder=no cols=200,*>
			<frame name="viewTreeList" src="<%=contextPath%>/sysmge/org/viewTreeList.action"  scrolling=no border=0>
			<frame name="viewDetail" src="<%=contextPath%>/sysmge/org/viewDetail.action?ID=<%=com.npc.lottery.common.ConstantBusiness.ORG_ROOT_DATA_ID%>"  scrolling=no border=0>
		</frameset>
	</frameset>
	
</html>