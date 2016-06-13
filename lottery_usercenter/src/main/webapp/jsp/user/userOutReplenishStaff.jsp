<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
<script language="javascript">
function deleteUser(id) {
	if(window.confirm('是否確定刪除該出貨會員?(注:該帳戶如存在帳務將無法刪除)')){
		var strUrl = "${pageContext.request.contextPath}/replenish/ajaxFindReplenishPet.action";
		var queryUrl = encodeURI(encodeURI(strUrl));
		$.ajax({
			type : "POST",
			url : queryUrl,
			data : {"id":id},
			dataType : "json",
			success : getCallback
		});
	}
}

function getCallback(json) {
	if(json.status!="failure"){
		 window.location.href="${pageContext.request.contextPath}/user/deleteUserOutReplenish.action?id="+json.stauts;
	}else{
		alert("該用戶存在帳務無法刪除");
	}
}


</script>


<div class="content">
	<s:form name="sForm" action="/user/queryUserOutReplenish.action?type=userOutReplenish">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">出貨會員管理</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229"><img src="${pageContext.request.contextPath}/images/admin/newadd.gif" width="14" height="13" /> 
										<a class="btn_4" href="${pageContext.request.contextPath}/user/registerUserOutReplenish.action">新增出貨會員</a>
			            </td>
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
						<th width="3%">在線</th>
						<th width="10%">帳號</th>
						<th width="30%">名稱</th>
						<th width="8%">盤口</th>
						<th width="33%">開戶日期</th>
						<th width="16%">功能</th>
					</tr>

					<% int count = 1; %>
					<s:iterator value="#request.page.result" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'"
							onmouseout="this.style.backgroundColor=''">
							<td>
							   <img src="${pageContext.request.contextPath}/images/admin/yuandian.gif"/>
							</td>
							
							<td class="t_center"><s:property value="account" escape="false" /></td>
							<td class="t_center"><s:property value="chsName" escape="false" /></td>
							<td><s:property value="plate" escape="false" />盤</td>
							<td><s:date name="createDate" format="yyyy-MM-dd"/></td>
							
							<td>
						  <a class="btn_4" href="${pageContext.request.contextPath}/user/findUserOutCommission.action?ID=<s:property value="ID" escape="false"/>" target="_self"><img  src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>退水</a>
						 
					      <a class="btn_4" href="${pageContext.request.contextPath}/user/findUserOutReplenish.action?ID=<s:property value="ID" escape="false"/>" target="_self"><img  src="${pageContext.request.contextPath}/images/admin/xiugai.gif"/>修改</a>
					   
						  <a class="btn_4" onclick="deleteUser(<s:property value="managerStaffID" escape="false"/>)" 
									href="javascript:void(0)" target="_self"><img  src="${pageContext.request.contextPath}/images/admin/delete.gif"/>刪除</a>
								
						</td>
						</tr>
					</s:iterator>
				</tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		         <lottery:paginate
									url="${pageContext.request.contextPath}/user/queryUserOutReplenish.action"
									param=""  recordType=" 個帳號"/>
		        
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
	</s:form>
</div>
</html>
