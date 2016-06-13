<%@ page contentType="text/html; charset=UTF-8" %>
<html>

<head>
<title>提示信息</title>

<Script Language="JavaScript">
<% String shopCode = (String)request.getAttribute("shopCode");
if( null != shopCode && !"".equals(shopCode)){%>
window.parent.location.href = "${pageContext.request.contextPath}/memberLogin.xhtml?shopcode=${shopCode}";
<%}else {%>
	window.parent.location.href = "/";
<%}%>
</Script>
</head> 
</html>
