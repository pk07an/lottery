<!--
	选择机构信息（單选）：列表功能
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
	com.npc.lottery.util.Tool,
	java.util.ArrayList,
	com.npc.lottery.sysmge.entity.Org 
"%> 
 
<html>  
<head>
<title>机构信息列表</title>
 
<%
    String contextPath = request.getContextPath();
    
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>
<script language="JavaScript" src="<%=contextPath%>/js/treeView.js"></script>

<script>
	//点击机构树上节点操作
	function selectOrg(id,name){ 
		parent.document.selectForm.orgID.value = id;
	 	parent.document.selectForm.orgName.value = name;	
		return false;
	}
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td  valign="top" class="td-content">
      
	<!-- 超过页面高度出现滚动条 -->
	<div class='outer' style="MARGIN: 0px; WIDTH: 100%; HEIGHT: 100%; overflow:auto;">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C3C3C3">
	<tr valign="top">
	<td bgcolor="#FFFFFF" class="in6" >
	
	<script language="JavaScript">
	
  		var tree = new MzTreeView("tree");
  		tree.icons["depJigou"] = "dep2.gif";
  		tree.icons["depBumen"] = "depJigou.gif";
  		tree.setIconPath("<%=contextPath%>/images/tree/");//可用相对路径
  		tree.nodes['999999999_999999999'] = 'text:999999';
 
	 <%  
		long selectId = 1;
		ArrayList list=(ArrayList)(request.getAttribute("resultList"));
		
		//构造树，注意需要有个根节点（所谓根节点指的是其父节点部分的ID为 0		
		Org entity = null;
		
		for (int i = 0; i < list.size(); i++){
		    entity = (Org)list.get(i);
			
			StringBuffer str = new StringBuffer("");
			
			str.append("tree.nodes['");
			str.append(entity.getUporgID2());//构造nodes标志的父节点部分
			str.append("_");
			str.append(entity.getOrgID());//构造nodes标志的自身节点部分
			str.append("']").append( " = 'text:");
			str.append(entity.getOrgName()+";");//树上显示的名稱
			str.append("icon:");
			str.append("depJigou");//图片
			str.append(";");
			str.append("url:;");
			str.append("method:");
			str.append("selectOrg(\"" + entity.getOrgID() + "\", \"" + entity.getOrgName() + "\")");
			str.append("\'");
			
			out.println(str.toString());	
		  };        
	  %>  
		tree.setURL("#");
		tree.setTarget("Frame");
		tree.focus(<%=selectId%>);
		document.write(tree.toString());    
	</script> 
	
	</td>
	</tr>
	</table>
	</div>

    </td>
  	</tr>
</table>

</body>
</html>