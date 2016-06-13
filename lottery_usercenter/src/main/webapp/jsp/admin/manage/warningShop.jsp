<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT LANGUAGE="JavaScript">
<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); %>
<% String info = (String)request.getAttribute("contentInfo");%>
	<%if(ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType())){%>
	<%if(info !=null){%>
		alert('<%=info%>');   
	<%}%>
	// window.location.href="${pageContext.request.contextPath}/user/queryAllMessage.action?type=message";
	window.location.href="${pageContext.request.contextPath}/admin/enterManage.action?type=message";
	<%}else{%>
		<%if(info !=null){%>
		   alert('<%=info%>');
		<% }%>
		//window.location.href="${pageContext.request.contextPath}/user/queryAllMessage.action?type=privateAdmin_SiteMessage";
		window.location.href="${pageContext.request.contextPath}/admin/enterManage.action?type=privateAdmin_SiteMessage";
	<%}
	%>
 </SCRIPT>
<body >
</body>

</html>
