<!--
	机构信息查看：导航信息页面
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<html>  
<head>
<title>机构信息导航栏</title>
 
<%
    String contextPath = request.getContextPath();
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- html:form action="/sysmge/user/list.action"-->
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td  valign="top" class="td-content">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="location">
		<tr>
			<td width="4%" align="center"><img src="<%=contextPath%>/images/location.gif" width="15" height="15"></td>
            <td width="46%" nowrap>系统管理 &gt; 信息管理 &gt; 机构信息</td>
          	<td width="50%" align="right" > 
	            &nbsp;
          	</td>
		</tr>
  	</table>
      	
  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td class="h-line"></td>
    	</tr>
  	</table>
    </td>
  	</tr>
</table>

<!-- /html:form-->

</body>
</html>