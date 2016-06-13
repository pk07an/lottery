<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" />
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	//查询商铺号
function checkSubmit(){	
		
		if($("#textInfo").val() !=""){
			$("#sForm").submit();
	 	}else{
			alert("请填写公告内容");
		}
		
	}
</script>
<body>
<div id="menu">
<div class="menu_c fl" style="width:10px"></div>
  <div class="menu fl">
  </div>
</div>
<div class="content">
    <s:form id="sForm" action="/boss/saveMessage.action" method="post">
	<div class="main">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">站內消息管理</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229">&nbsp;</td>
			            </tr></table></td>
			        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
			      </tr>
			    </table></td>
			<!--控制表格头部结束-->
				<tr>
			    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
			        <td align="center" valign="top">
					<!-- 表格内容开始 一行六列-->
					<!-- 表格内容结束 一行六列-->
		           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4">
  
				<tbody>
					<tr>
				      <th colspan="2"><strong>站內消息 </strong></th>
				    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr t_right" width="18%" bgcolor="#EBF4FF">消息詳情: </td>
	      <td class="tl t_left" width="82%" style="padding:4px">
	      <s:textarea rows="10" id="textInfo" cols="86" name="shopsDeclaratton.contentInfo" ></s:textarea>
           </td>	      
	    </tr>
	     <tr>
	      <td bgcolor="#EBF4FF" class="t_right">分級瀏覽:</td>
	      <td class="t_left" style="padding:4px">
			  <s:select name="shopsDeclaratton.managerMessageStatus" list="#{0:'所有人',2:'管理層',3:'分公司',4:'股東',5:'總代理',6:'代理',9:'會員'}" listKey="key" listValue="value"/>
		      &nbsp;&nbsp;&nbsp;&nbsp;<s:checkboxlist name="shopsDeclaratton.popupMenus" list="#{'1':'彈出窗口(登錄時)'}" value="1"/>
		  </td>
	    </tr>
	  </tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
			<td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
						<td align='center'>
						    <input name="" type="button" onClick="checkSubmit();" class="btn2" value="保存" /> 
						    <input name="" type="reset" class="btn2" value="取消" />
						</td>
						<td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
					</tr>
				</table></td>
		</tr>
	</table>
	</div>
  </s:form>
</div>
</body>

</html>
