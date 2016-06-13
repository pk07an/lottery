<!--
	角色授权
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
	com.npc.lottery.sysmge.entity.Function 
"%>  

<html>  
<head>
<title>角色授权</title>
 
<%
    String contextPath = request.getContextPath(); 
	String roleID = request.getParameter("roleID");
	if(null == roleID){
	    roleID = (String)request.getAttribute("roleID");
	}
	if(null == roleID){
	    roleID = "";
	}
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>

<script>
	function authorizRole(){
	
		var selectFunc = document.frames("listFunc").document.all.selectFunc;
		var selectFuncID = "";

		if(null != selectFunc && selectFunc.length > 0){
    		for(var i = 0 ; i < selectFunc.length; i ++){
    			if(selectFunc[i].checked){
    				selectFuncID = selectFuncID + "<%=Function.ID_GROUP_SPLIT%>" + selectFunc[i].value;
    			}
    		}
		}
		
		//设置选择的功能ID集合
		document.authorizRoleForm.selectFuncID.value = selectFuncID;
		
		//alert(document.authorizRoleForm.roleID.value);
		//alert(document.authorizRoleForm.selectFuncID.value);
		
		//提交
        if(confirm("确定提交授权信息吗？")){
        	  document.authorizRoleForm.submit();
        }
	}
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="authorizRoleForm" action="<%=contextPath%>/sysmge/authoriz/authorizRoleSubmit.action">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td  valign="top" class="td-content">
	
	<!-- 超过页面高度出现滚动条 -->
	<div class='outer' style="MARGIN: 0px; WIDTH: 100%; HEIGHT: 90%; overflow:no;">
		<iframe id='listFunc' src='<%=contextPath%>/sysmge/authoriz/selectFunc.action?roleID=<%=roleID%>' frameBorder='0' width='100%' name='content' scrolling='no' height='100%'></iframe>	
	</div>
	
	<div class='outer' align="center">
		<img onClick="authorizRole()" src="<%=contextPath%>/images/bt_affirm.gif" alt="授权" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_affirm_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_affirm.gif')" style="cursor:hand">
		&nbsp;&nbsp;
		<img onClick="javacsript:window.close()" src="<%=contextPath%>/images/bt_close.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_close_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_close.gif')" style="cursor:hand">
	</div>
	
	<s:hidden name="roleID"/>
	<s:hidden name="selectFuncID"/>
    </td>
  	</tr>
</table>
</form>

</body>
</html>