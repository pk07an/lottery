<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT LANGUAGE="JavaScript">
<% String info = (String)request.getAttribute("contentInfo");
	 if(info !=null){%>
	   alert('<%=info%>');
	   window.location.href="${pageContext.request.contextPath}/${path}/enterBet.xhtml";
	<% }else{%>
		 window.location.href="${pageContext.request.contextPath}/${path}/enterBet.xhtml";
	 <%}%>
   
 </SCRIPT>
<body >
</body>

</html>
