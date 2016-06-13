<!--
	用户信息选择（多选）：主界面
	
	此模式窗口返回值为 userID1&&userName1!!userID2&&userName2
	注意返回的页面上解析时，&& 使用常量 Constant.SELECT_USERID_USERNAME_SPLIT
	                     !! 使用常量Constant.SELECT_MULTI_USER_SPLIT
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
			<frame name="selectMultiTreeList" src="<%=contextPath%>/sysmge/user/selectMultiTreeList.action"  scrolling='no' border='0'>
			<frameset id='frmWorkSpace02' border='0' frameSpacing='0' frameBorder='no' rows='46,*'>
	            <frame name="selectMultiUserData" src="<%=contextPath%>/sysmge/user/selectMultiUserData.action?userOrgID=<%=com.npc.lottery.common.ConstantBusiness.ORG_ROOT_DATA_ID%>"  scrolling='no' border='0'>
	            <frame name="selectMultiUserList" src="<%=contextPath%>/sysmge/user/selectMultiUserList.action?userOrgID=<%=com.npc.lottery.common.ConstantBusiness.ORG_ROOT_DATA_ID%>"  scrolling='no' border='0'>
	        </frameset>
		</frameset>
	</frameset>
	
</html>