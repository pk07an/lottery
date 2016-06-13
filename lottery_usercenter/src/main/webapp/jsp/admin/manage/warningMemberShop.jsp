<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT LANGUAGE="JavaScript">
<% String info = (String)request.getAttribute("contentInfo");
	 if(info !=null){%>
	   alert('<%=info%>');
	   window.location.href="${pageContext.request.contextPath}/member/enterBet.do";
	<% }else{%>
		 window.location.href="${pageContext.request.contextPath}/member/enterBet.do";
	 <%}%>
   
 </SCRIPT>
<body >
</body>

</html>
