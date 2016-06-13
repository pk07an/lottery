
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
	
	String serverNameOrg = request.getServerName();
	String scheme = request.getScheme();
	int port =  request.getServerPort();
	String  serverNameNew = "";
	String[] serverNameArray = serverNameOrg.split("\\.");
	serverNameNew = serverNameArray[serverNameArray.length - 4] + "." 
					+ serverNameArray[serverNameArray.length - 3] + "."
					+ serverNameArray[serverNameArray.length - 2] + "."
					+ serverNameArray[serverNameArray.length - 1];
	if (port != 80) {
		serverNameNew = serverNameNew + ":" + port;
	}
	serverNameNew = scheme + "://" + serverNameNew;
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>
<Script Language="JavaScript">
<%String type = request.getParameter("type");
	if(!"Y".equals(type)){%>
	alert("您已被強制登出！\n"+
			   "您可能因以下其一原因被登出：\n"+
			   "1.   系統登出(或超时)。\n"+
			   "2.   您的帳戶在多個地方登錄。\n"+
			   "請從新登錄。如有任何疑問，請聯系上線或客服中心。\n");
	
	<%}%>

window.parent.location.href = "<%=serverNameNew%>/managerLogin.jsp";
//window.open("${pageContext.request.contextPath}/managerLogin.jsp",'_self'); 
</Script>
</head> 
</html>
