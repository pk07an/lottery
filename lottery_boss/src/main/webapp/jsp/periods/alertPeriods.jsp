<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT LANGUAGE="JavaScript">
<% String periodsInfo = (String)request.getAttribute("periodsInfo");
	 if(periodsInfo !=null){%>
	   alert('<%=periodsInfo%>');
	   window.location.href="${pageContext.request.contextPath}/periods/updateFindPeriods.action?periodsType=periodsType";
	<% }else{%>
		 window.location.href="${pageContext.request.contextPath}/periods/updateFindPeriods.action?periodsType=periodsType";
	 <%}%>
   
 </SCRIPT>
<body >
</body>

</html>
