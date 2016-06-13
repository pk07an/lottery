<!--
	机构信息查看
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/divpage" prefix="page" %>
<%@ page import="
	com.npc.lottery.util.Tool,
	com.npc.lottery.common.Constant
"%> 
 
<html>  
<head>
<title>机构信息查看</title>
 
<%
    String contextPath = request.getContextPath();
	String sumPages = (String)Tool.getAttributes(request,"sumPages","1");//總页数，第一页是1
	String pageCurrentNo = Tool.getParameter(request, Constant.PAGETAG_CURRENT, "1");//当前页碼
	String recordAmount = (String)Tool.getAttributes(request,"recordAmount","0");//记录總数
	
	String thisPageUrl = contextPath + "/sysmge/org/viewDetail.action";
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>
<script language="javascript">
    function openWin(userId)
    {         
        //需要打开的页面URL，设置为实际值
		var url = "<%=contextPath%>/jsp/demo/openWin.jsp";
		//打开模式窗口，固定写法，不要改变
		var ret = openModalDialog("<%=contextPath%>/sysmge/org/viewUserinfo.action?userID="+userId,url,600,220);
		
    }
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="orgMessage">

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
			<td colspan="4" class="tab-head" align=center>
			  	<b>机构信息</b>
	   		</td>
		</tr>
		<s:iterator value="#request.org">
		<tr>
			<td class="tab-head" width="15%" align="right">
			  	机构名稱：
	   		</td>
	   		<td class="td-input" width="35%">
				<s:property value="orgName"/>
	   		</td>
	   		<td class="tab-head" width="15%" align="right">
			  	父机构：
	   		</td>
	   		<td class="td-input">
				<s:property value="upOrgName"/>
	   		</td>
		</tr>
		
		<tr>
			<td class="tab-head" align="right">
			  	机构類型：
	   		</td>
	   		<td class="td-input">
				<s:property value="orgTypeChName" escape="false"/>
	   		</td>
	   		<td class="tab-head" align="right">
			  	机构区域：
	   		</td>
	   		<td class="td-input">
				<s:property value="orgArea"/>
	   		</td>
		</tr>
		</s:iterator>
		
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
	<div class=outer style="MARGIN: 0px; WIDTH: 100%; HEIGHT: 93%; overflow:auto;">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C3C3C3">
	<tr>
	<td bgcolor="#FFFFFF" class="in6" >
	<table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
	
		<tr>
		<td colspan="9" class="tab-head">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
    		<tr>
				<td colspan="4" class="tab-head" align=center>
				  	<b>用户信息列表</b>
		   		</td>
          	</tr>
   		</table>
   		</td>
		</tr>
				
		<tr align='center'>
			<td width='20%' class='tab-bag' nowrap>员工工号</td>
			<td width='25%' class='tab-bag' nowrap>中文名</td>
			<td width='18%' class='tab-bag' nowrap>eMail</td>
			<td width='12%' class='tab-bag' nowrap>办公室电话</td>
			<td width='5%' class='tab-bag' nowrap>操作</td>
		</tr>
		
		<s:if test="null == request.resultList || 0 == request.resultList.size()">
		<tr bgcolor='#FFFFFF' class='list-tr1'>
			<td class='align-l' colspan='10'>
				<font color='#FF0000'>未查詢到满足条件的数据！</font>
			</td>
		</tr>
		</s:if>
		
		<s:iterator value="#request.resultList" status="st">
		
		<tr bgcolor='#FFFFFF' <s:if test="true == #st.odd">class='list-tr2'</s:if><s:else>class='list-tr1'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)'>
			<td class='align-c' nowrap>
				<s:property value="userID"/>
		  	</td>
		  	<td class='align-l'>
		    	<s:property value="chsName"/>
		  	</td>
		  	<td class='align-l'>
		    	<s:property value="emailAddress"/>
		  	</td>
		  	<td class='align-c' nowrap>
		    	<s:property value="officePhone"/>
		  	</td>
			<td class='align-c' nowrap>				
				<a href="#" onclick="openWin('<s:property value="userID"/>')">
		  			<img src="<%=request.getContextPath()%>/images/bt_particular.gif" width="18" height="18" border="0" alt="查看详细信息">
				<a>
			</td>
		</tr> 
		</s:iterator>       
       
		<s:if test="#request.resultList.size==0">
		<tr bgcolor='#FFFFFF' class='list-tr1'>
			<td class='align-l' colspan='10'>
				<font color='#FF0000'>没有满足条件的数据</font>
			</td>
		</tr>
		</s:if>
		
		
		<tr align="right" bgcolor="#FFFFFF">
	   	<td colspan="6" class="tab-head">
	   	<table width="40%" border="0" cellspacing="3" cellpadding="0">
			<tr>
				<td align="right" nowrap>
		            <page:pages_roller sumPages='<%=sumPages%>'> 
		              <a href="<%=Tool.toServletStr(firsturl,thisPageUrl)%>"><img src="<%=contextPath%>/images/page-first.gif" alt="首页" border="0"></a>
		              <a href="<%=Tool.toServletStr(prevurl,thisPageUrl)%>"><img src="<%=contextPath%>/images/page-previous.gif" alt="上一页" border="0"></a>
		              <a href="<%=Tool.toServletStr(nexturl,thisPageUrl)%>"><img src="<%=contextPath%>/images/page-next.gif" alt="下一页" border="0"></a>
		              <a href="<%=Tool.toServletStr(lasturl,thisPageUrl)%>"><img src="<%=contextPath%>/images/page-last.gif" alt="末页" border="0" align="bottom"></a>
		            </page:pages_roller>
	            </td> 
	            
	            <td align="right" valign="bottom" nowrap>
	           		第 <font color="#003399"><%=pageCurrentNo%></font> 页 
	              	共 <font color="#003399"><%=sumPages%></font> 页
	      		  	<a href="#" onclick="return checkPageGo('disAppBillForm','<%=contextPath%>/bboxmge/disMge/disAppBill.do?actionType=list')"><img src="<%=contextPath%>/images/page-go.gif" alt="跳转到指定页" border="0" align="absmiddle"></a><input type="text" name="pageGoNumInput" value="" maxlength="4" size="3" class="goInput" onfocus='this.select()'>&nbsp;
	              	记录總数：<font color="#003399"><%=recordAmount%> </font>
	              	<input type="hidden" name="originSumPages" value="<%=sumPages%>"> 
	            </td>
	            
			</tr>
		</table>
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
</form>
<!-- /html:form-->

</body>
</html>