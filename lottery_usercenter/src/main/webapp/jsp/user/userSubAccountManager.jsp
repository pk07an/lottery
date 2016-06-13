<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript">
	function showResponse(jsonData, statusText)  { 
	    self.location="${pageContext.request.contextPath}/user/querySubAccount.action";
	} 
	
	function checkShopsName() {
		var account = $("#account").val();
		if (account != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserName.action?account='
					+ account;
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {},
				dataType : "json",
				success : callBackName
			});
		}
	}
	function callBackName(json) {
		if (json.count > 0) {
			alert("該用户名稱已存在");
			$("#account").val("");
			$("#verifyName").val("NO");
		} else {
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}
	
	function checkSubmit() {
		var accountName = $("#account").val();
		var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			return false;
		}
		if ($("#verifyName").val() == "OK" && $("#userPassword").val() !="") {
			$("#subCreate").submit();
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}
	
	function checkUpdateSubmit() {
			$("#subUpdate").submit();
	}
	
</script>	
<div class="content">
  <s:form id="subUpdate" action="#" method="post"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
	   <!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">子帳號管理</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229">
			                <img src="${pageContext.request.contextPath}/images/admin/newadd.gif" width="14" height="13" /> 
										<a class="btn_4" href="${pageContext.request.contextPath}/user/savefindSubAccount.action">新增子帳號</a>
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
          <colgroup>
<col width="4%" />
<col width="27%" />
<col width="27%" />
<col width="24%" />
<col width="14%" />
<col width="4%" />
</colgroup>
				<tbody>
					<tr>
						<th>在線</th>
						<th>子帳號</th>
						<th>名稱</th>
						<th>新增日期</th>
						<th>功能</th>
						<th>狀態</th>
					</tr>
	 		 <s:iterator value="#request.page.result" status="branch">
				<tr onmouseover="this.style.backgroundColor='#FFFFA2'"
					onmouseout="this.style.backgroundColor=''">
					<td>
						<s:if test="inLine==true">
						<img src="${pageContext.request.contextPath}/images/admin/dengpao.gif"/>
					  </s:if> <s:if test="inLine==false">
					   <img  src="${pageContext.request.contextPath}/images/admin/yuandian.gif"/>
					  </s:if>
					</td>
					<td class="t_center TD_R"><s:property value="account" escape="false" /></td>
					<td class="t_center"><s:property value="chsName" escape="false" /></td>
					<td><s:date name="createDate" format="yyyy-MM-dd"/></td>
					<td><img  src="${pageContext.request.contextPath}/images/admin/xiugai.gif"/>
					<a class="btn_4" href="${pageContext.request.contextPath}/user/updateFindSubAccount.action?qUserID=<s:property value="encodeId" escape="false"/>">修改</a>
					<img  src="${pageContext.request.contextPath}/images/admin/rizhi.gif"/>
     				 <a class="btn_4"  class="btn td_btn" href="${pageContext.request.contextPath}/admin/queryLoginLog.action?qUserID=<s:property value="encodeId" escape="false"/>"/>日志</a></td>
					<td>
					<s:if test="flag==0"> <a href="##" name="userStatus" data="<s:property value="account" escape="false" />|0|7"><input type="button" value="啟用"/> </a></s:if> 
				     	    <s:if test="flag==1">
							<strong class="red"><a href="##" name="userStatus" data="<s:property value="account" escape="false" />|1|7"><input class="red" type="button" value="停用"/></a></strong>
							</s:if> 
							<s:if test="flag==2">
									<strong class="red"><a href="##" name="userStatus" data="<s:property value="account" escape="false" />|2|7"> <input type="button" value="凍結"/></a></strong>
							</s:if>
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
									param=""  recordType="個會員帳號"/>
		        
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
<jsp:include page="/jsp/user/changeSubStatusPop.jsp" />
</html>
