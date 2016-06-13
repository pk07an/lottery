<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT LANGUAGE="JavaScript">
<% String info = (String)request.getAttribute("passwordInfo");
	 if(info !=null){%>
	   alert('<%=info%>');
	   <% 
	   String accountType = (String)request.getParameter("accountType");
	   if("member".equalsIgnoreCase(accountType)){%>
	   		window.location.href="${pageContext.request.contextPath}/user/logoutMember.action";
	   <%}else{%>
	   		window.parent.location.href="${pageContext.request.contextPath}/user/logoutManager.action";
	   <%}%>
	   
	<% }%>
 </SCRIPT>
<body >
</body>

</html>
