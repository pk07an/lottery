<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
<script language="javascript">

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
	
	function checkDelSubmit(account) {
		if(confirm('是否确认要删除？删除之后不能再恢复')){
			$("#subUpdate").attr("action","${pageContext.request.contextPath}/user/delSubAccount.action?account="+account);
			$("#subUpdate").submit();
		}
	}
</script>	
<div class="content">
	 <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  <s:form id="subUpdate" action="%{pageContext.request.contextPath}/user/delSubAccount.action" method="post"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
		<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); 
           boolean isSub = ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType());   	
              if(!isSub)
                {%>
			<a class="btn_4" href="${pageContext.request.contextPath}/user/savefindSubAccount.action">創建子帳號</a>
                 <%}    
		%>	
	
    <colgroup>
      <col width="10%" />
      <col width="10%" />
      <col width="22%" />
      <col width="22%" />
      <col width="20%" />
      <col width="16%" />
    </colgroup>
    <tr>
    <th>狀態</th>
      <th>編號</th>
      <th>帳號</th>
      <th>上級所属</th>
      <th>加入时间</th>
      <th>操作</th>
    </tr>
    	<% int count = 1; %>
	 		 <s:iterator value="#request.page.result" status="branch">
				<tr onmouseover="this.style.backgroundColor='#FFFFA2'"
					onmouseout="this.style.backgroundColor=''">
					<td>
						<s:if test="inLine==true">
						<img src="${pageContext.request.contextPath}/images/online.gif"/>
					  </s:if> <s:if test="inLine==false">
					   <img  src="${pageContext.request.contextPath}/images/offline.gif"/>
					  </s:if>
					</td>
					<td><%=count++%></td>
					<td><s:property value="account" escape="false" /></td>
					<td><s:property value="managerStaff.account" escape="false" /></td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td><a class="btn_4" href="${pageContext.request.contextPath}/user/updateFindSubAccount.action?account=<s:property value="account" escape="false"/>">修改</a>
     				 <a class="btn_4"  class="btn td_btn" onclick="checkDelSubmit('<s:property value="account" escape="false" />');"/>删除</a></td>
				</tr>
			</s:iterator>
  </table>
</s:form>
</div>

</html>
