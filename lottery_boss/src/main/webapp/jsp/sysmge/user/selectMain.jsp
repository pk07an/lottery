<!--
	用户信息选择（單选）：主界面
	
	此模式窗口返回值为 [userID]&&[userName]
	注意返回的页面上解析时，&& 使用常量 Constant.SELECT_USERID_USERNAME_SPLIT
-->

<%@ page language="java" pageEncoding="UTF-8" %>

<%
	String contextPath = request.getContextPath();
%>

<html>
	<head>
		<title> .:: 用户信息选择 ::. </title>
	</head>
	
	<frameset border='0' frameSpacing='0' rows='*' frameBorder='no'>
		<frameset id='frmWorkSpace' border='0' frameSpacing='0' frameBorder='no' cols='200,*'>
			<frame name="selectTreeList" src="<%=contextPath%>/sysmge/user/selectTreeList.action"  scrolling='no' border='0'>
			<frame name="selectUserList" src="<%=contextPath%>/sysmge/user/selectUserList.action?userOrgID=<%=com.npc.lottery.common.ConstantBusiness.ORG_ROOT_DATA_ID%>"  scrolling='no' border='0'>
		</frameset>
	</frameset>
	
</html>