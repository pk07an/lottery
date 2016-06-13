<!--
         用户授权查看：主界面
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
    com.npc.lottery.sysmge.entity.Function 
"%>  

<html>  
<head>
<title>用户私有权限授权查看</title>

<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="authorizUserViewForm" action="#">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td  valign="top" class="td-content">
    
    <!-- 超过页面高度出现滚动条 -->
    <div align="center"><b>功能信息</b></div>
    <div class='outer' style="MARGIN: 0px;overflow:no;">
        <iframe id='userViewFunc' src='${contextPath}/sysmge/authoriz/userViewFunc.action?userID=${request.userID}&userType=${request.userType}' frameBorder='0' width='100%' name='content' scrolling='no' height='45%'></iframe>    
    </div>
    <br>
    <div align="center"><b>角色信息</b></div>
    <div class='outer' align="center">
        <iframe id='userViewRole' src='${contextPath}/sysmge/authoriz/userViewRole.action?userID=${request.userID}&userType=${request.userType}' frameBorder='0' width='100%' name='content' scrolling='no' height='45%'></iframe>    
    </div>
    
    <s:hidden name="userID"/>
    </td>
    </tr>
</table>
</form>

</body>
</html>