<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script language="javascript">

	function checkPassword() {
		var pwd = $("#pwd").val();
		if (pwd != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryPassword.action';
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {"pwd":$.md5(pwd).toUpperCase()},
				dataType : "json",
				success : callBackName
			});
		}
	}
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("原始密碼不對");
			$("#pwd").val("");
			$("#verifyName").val("NO");
			$("#pwd").focus();
		} else {
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}
	
	function checkSubmit() {
		if($.trim($("#pwd").val()) == "")
		{
			alert("請輸入舊密碼");
			$("#pwd").focus();
			return false;
		}
		else if($.trim($("#newPas").val()) == "")
		{
			alert("新密碼不能為空");
			$("#newPas").focus();
			return false;
		}
		else if($.trim($("#newPasOne").val()) == "")
		{
			alert("新密碼不能為空");
			$("#newPasOne").focus();
			return false;
		}
		else if($("#newPas").val() != $("#newPasOne").val())
		{
			alert("兩次輸入的密碼不一致");
			$("#newPas").focus();
			return false;
		}
		//新舊密碼校驗
		if($("#pwd").val() == $("#newPas").val()){
			alert("重置密碼不能與舊密碼一樣,請重新設置");
			$("#newPasOne").val("")
			$("#newPas").val("");
			$("#newPas").focus();
			return false;
		}
		
		if(confirm('是否确认修改密码')){
			if ($("#verifyName").val() == "OK"  && $("#newPas").val() != "" && $("#newPasOne").val() != "") {
				//密码加密
				$("#newPas").val($.md5($("#newPas").val()).toUpperCase());
				$("#newPasOne").val($.md5($("#newPasOne").val()).toUpperCase())
				$("#pwd").val($.md5($("#pwd").val()).toUpperCase())
				$("#sForm").submit();
			} else {
				alert("用户密码数据有误，請修正后再確認");
				//$("#sForm").button();
			}
		}
	}
	
	function checkNewPassword() {
		
		var newPas = $("#newPas").val();
	 	var newPasOne = $("#newPasOne").val();
		
		if (newPas != newPasOne) {
			alert("兩次輸入密碼不對");
			$("#newPas").val("");
			$("#newPasOne").val("");
			$("#newPas").focus();
		}
	}
	
	function checkPasswordFormat(){
		//add by peter add password validation
		var password=$("#newPas").val();
		if(null !=  password && password != "" && !passwordCheck(password))
		{
			$("#newPas").val("");
			$("#newPas").focus();
		}
	}
</script>	
<body>
<div class="content">
    <s:form id="sForm" action="%{pageContext.request.contextPath}/user/updatePassword.action" method="post">
    <table width="100%" border="0" id="formTable">
	  <tr>
	    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
	        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="30%" valign="middle">
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="1%"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
	                <td width="99%" align="left" class="F_bold">
	               &nbsp;變更密碼</td>
	              </tr>
	            </table>
	            </td>
	            <td align="right" width="70%">
	            </td>
	            </tr></table></td>
	        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
	      </tr>
	    </table></td>
	  </tr>
  <tr>
    <td><table width="100%" border="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center"><!--修改开始   -->
 <table width="100%" border="0px" cellspacing="0" cellpadding="0" class="mt4 king higher autoHeight">
  <tr>
    <td width="18%" class="t_right even">原始密碼 </td>
    <td width="82%" class="t_left">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
	      <tr>
		    <td width="74">
		       <input name="userOldPassword" value="" id="pwd" type="password" onblur="javascript:checkPassword();"/> 
	           <span id="accountAgain" style="color: #FF0000;"></span> <s:hidden id="verifyName" value="" />
		    </td>
		  </tr>
		 </table>
	 </td>
  </tr>
  <tr>
    <td class="t_right even">新設密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
    <tr>
        <td width="74"><input name="newPassword" id="newPas" value="" type="password" onblur="javascript:checkPasswordFormat()"/>  </td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even">確認密碼 </td>
    <td class="t_left"><input name="newPasswordOne" value="" id="newPasOne" type="password" onblur="javascript:checkNewPassword();"/>   
     </td>
  </tr>
  
</table>

          <!--  修改结束 --></td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'><input id="btnSubmit" type="button" class="btn2" value="確定修改" onclick="javascript:checkSubmit();"/>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</s:form>
</div>
</body>
<script language="javascript">
$().ready(function(){
	<%if("Y".equals((String)request.getAttribute("isPasswordExpire"))){%>
		$('#formTable').attr("style","margin:0 auto;width:804px");
		alert("抱歉! 您的密碼使用期限超過15天,為安全起見請設定'新密碼'.");
	<%}else if("Y".equals((String)request.getAttribute("isPasswordReset"))){%>
		$('#formTable').attr("style","margin:0 auto;width:804px");
		alert("抱歉! 您的账户為初次登陸 或 密碼由后台重新設定,為安全起見,請設定'新密碼'.");
	<%}%>
});
</script>
</html>
