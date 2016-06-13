<!--
	功能信息查看：查看功能详细信息
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
	com.npc.lottery.util.Tool
"%> 
 
<html>  
<head>
<title>查看功能信息</title>
 
<%
    String contextPath = request.getContextPath();
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-- html:form action="/sysmge/user/list.action"-->
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td  valign="top" class="td-content">
	
	<!-- 超过页面高度出现滚动条 -->
	<div class=outer style="MARGIN: 0px; WIDTH: 100%; overflow:auto;">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C3C3C3">
	<tr>
	<td bgcolor="#FFFFFF" class="in6" >
	<table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
	
		<tr>
			<td colspan="4" class="tab-head" align="center">
			  	<b>功能信息</b>
	   		</td>
		</tr>
		
		<tr>
			<td class="tab-head" width="15%" align="right">
			  	功能代碼：
	   		</td>
	   		<td class="td-input">
				<s:property value="#request.function.funcCode"/>
	   		</td>
	   		<td class="tab-head" width="15%" align="right">
			  	功能名稱：
	   		</td>
	   		<td class="td-input">
				<s:property value="#request.function.funcName"/>
	   		</td>
		</tr>
		
		<tr>
			<td class="tab-head" width="15%" align="right">
			  	功能狀態：
	   		</td>
	   		<td class="td-input">
				<s:property value="#request.function.funcState"/>
	   		</td>
	   		<td class="tab-head" width="15%" align="right">
			  	功能首页：
	   		</td>
	   		<td class="td-input">
				<s:property value="#request.function.funcUrl"/>
	   		</td>
		</tr>
		
		<tr>
			<td class="tab-head" width="15%" align="right">
			  	上级节点：
	   		</td>
	   		<td class="td-input"  colspan="3">
				<s:property value="#request.function.sortNum"/>
	   		</td>
		</tr>
		
		<tr>
			<td class="tab-head" width="15%" align="right">
			  	功能描述：
	   		</td>
	   		<td class="td-input"  colspan="3">
				<s:property value="#request.function.funcDesc"/>
	   		</td>
		</tr>
		 
	</table>
	</td>
	</tr>
	</table>
	</div>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td class="h-line"></td>
    	</tr>
  	</table>

	<!-- 超过页面高度出现滚动条 -->
	<div class=outer style="MARGIN: 0px; WIDTH: 100%; overflow:auto;">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C3C3C3">
	<tr>
	<td bgcolor="#FFFFFF" class="in6" >
	<table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
	
	
		<tr>
			<td colspan="4" class="tab-head" align="center">
			  	<b>功能所对应的资源</b>
	   		</td>
		</tr>
		
		 
	</table>
	</td>
	</tr>
	</table>
	</div>

    </td>
  	</tr>
</table>

<!-- /html:form-->

</body>
</html>