<!-- 
	参考页面：查看用户信息
-->
<%@ page language="java" pageEncoding="UTF-8" %><!-- 页面编碼 UTF-8，所有页面均如此设置 -->
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	//工程上下文，涉及路径的资源，在写访问路径时一般均要加上，如引用一个图片
	String contextPath = request.getContextPath();
%>
<html>
<head>
<title>用户详细信息</title>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>

<script>
<!-- 
   function	back()
   {
      window.close();
   }
	
-->
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">


<form name="viewForm" method="post" action="#">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
<td  valign="top" class="td-content">
	      
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td class="h-line"></td>
	</tr>
	</table>	

	<div class=outer style="MARGIN: 0px; WIDTH: 100%; HEIGHT: 93%; overflow:auto;">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C3C3C3">
	<tr> 
		<td bgcolor="#FFFFFF" class="in6" >
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#919191">
		
			<tr> 
			<td colspan="7" class="tab-head">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td width="3%" align="center"><img src="<%=contextPath%>/images/tit_pic.gif" width="8" height="3"></td>
	           		<td width="64%" class="text-title">详细信息</td>
	            	<td width="33%">&nbsp;</td>
	      		</tr>
	      		</table>
	    	</td>
			</tr>
			
            <tr align="right" valign="top"> 
  			<td colspan="7" align="right" valign="top" class="tab-bag">
	   			<table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
	      		<s:iterator value ="#request.user">
	      		
	         	<tr class="list-tr1"> 
	        		<td width="21%" class="td-label">员工工号：</td>
	        		<td class="td-input">
						<s:property value="userID"/>
					</td>
					
					<td width="21%" class="td-label">所属机构：</td>
	        		<td class="td-input">
	        			<s:property value="userOrgName"/>
	        		</td>
	     		</tr>	        		
	        		
	     		<tr class="list-tr1"> 
	        		<td width="21%" class="td-label">中文名：</td>
	        		<td class="td-input">
	        			<s:property value="chsName" />
	        		</td>
	        		
	        		<td width="21%" class="td-label">eMail：</td>
	       			<td class="td-input">
	        			<s:property value="emailAddress"/>
	        		</td>
	       		</tr>	        		
	     		        		
	     		<tr class="list-tr1"> 
	       			<td width="21%" class="td-label">办公室电话：</td>
	        		<td class="td-input">
	        			<s:property value ="officePhone"/>
	        		</td>
	        		
	        		<td width="21%" class="td-label">移动电话：</td>
	        		<td class="td-input">
	        			<s:property value ="mobilePhone"/>
	        		</td>
	      		</tr>
	      	    
	      	    <tr class="list-tr1"> 
	       			<td width="21%" class="td-label">PHSPHONE：</td>
	        		<td class="td-input">
	        			<s:property value ="PHSPHONE"/>
	        		</td>
	        		
	        		<td width="21%" class="td-label">FAX：</td>
	        		<td class="td-input">
	        			<s:property value ="FAX"/>
	        		</td>
	      		</tr>
	      	
	      		<tr class="list-tr1"> 
	       			<td width="21%" class="td-label">創建日期：</td>
	        		<td class="td-input" colspan="3">
	        			<s:date name ="createDate" format="yyyy-MM-dd"/>
	        		</td>	        		
	      		</tr>
	      		
	        	</s:iterator>
		  		</table>
      		</td>
            </tr>
            
            <tr> 
			<td colspan="7" class="tab-head">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td width="50%" id="backButton" align="right" style="display:block"> 
				<a href="#" onclick="back()">
			  		<img src="<%=contextPath%>/images/bt_back.gif" alt="返回" name="Image3" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_back_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_back.gif')">
				</a>
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
</body>
</html>