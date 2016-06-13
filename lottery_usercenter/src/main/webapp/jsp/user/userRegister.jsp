<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="/js/formvalidator.js" type="text/javascript"></script>
<script src="/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="/js/register.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	function checkShopsName(){
		var	account = $("#account").val();
	   	if (account != ""){	
	   		var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserName.action?account=' + account;		   							
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
		if (json.count>0){
			$("#accountAgain").html("該用户名稱已存在");
			$("#verifyName").val("NO");
		}else{
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}

	function checkSubmit(){
		if($("#verifyName").val()=="OK"){
			$("#sForm").submit();
	 	}else{
			alert("户名稱數據有誤，請修正后再確認");
		}
	}
	
</script>
<body>
	<s:form id="sForm" action="/user/chiefStaffRegister.action">
		<div class="main">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="king2 xy pw">
				<tbody>
					<tr>
						<th colspan="2">總监注冊</th>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr" width="50%">用户名</td>
						<td class="tl" width="50%"><input type="text" value=""
							maxlength="20" id="account" name="chiefStaffExt.account"
							onblur="javascript:checkShopsName();"></input> 
							<span id="accountAgain" style="color: #FF0000;"></span> 
							<s:hidden id="verifyName" value="" /> 
							<s:hidden name="chiefStaffExt.userType" value="2" /></td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">密碼</td>
						<td class="tl"><input id="userPassword" type="password" value=""  
							name="chiefStaffExt.userPwd"></input></td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">中文名字</td>
						<td class="tl"><input name="chiefStaffExt.chsName"
							class="w176" type="text" value="" /></input></td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">狀態</td>
						<td class="tl"><input type="text" value="0" maxlength="20" name="chiefStaffExt.flag" ></input></td>
					</tr>
				</tbody>
			</table>
			<div class="tj">
				<input type="button" value="確認" class="btn_4" name="" onclick="checkSubmit();"></input>
			</div>
		</div>
	</s:form>

</body>

</html>
