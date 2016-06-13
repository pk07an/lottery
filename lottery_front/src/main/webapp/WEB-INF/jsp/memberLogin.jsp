<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OA系统会员登录入口</title>
<link href="/css/loginMember.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="/js/jQuery.md5.js" type="text/javascript"></script>
<script>

    //校验用户的输入
    function checkForm(){
        
        var userName = document.LoginForm.userName.value;
        var userPwdInput = document.LoginForm.userPwdInput.value;
        var code = document.LoginForm.code.value;
        
        if(null == userName || 0 == userName.length){
            alert("請填寫“會員帳號”！！！");
            document.LoginForm.userName.focus();
            return false;
        }
        if(null == userPwdInput || 0 == userPwdInput.length){
            alert("請填寫“會員密碼”！！！");
            document.LoginForm.userPwdInput.focus();
            return false;
        }
        
        if(null == code || 0 == code.length){
            alert("请输入驗證码！！！");
            document.LoginForm.code.focus();
            return false;
        }
        return true;
    }
    
    //改变验证码
    function changeCode(){
        document.getElementById("codeImage").src="${pageContext.request.contextPath}/checkCodePage.xhtml?"+Math.random()*1000;
    }
    
        function submitForm(){
        	
        	if(checkForm())	{
        		var account = $("#userName").val();
        		var webAppUrl = "${pageContext.request.contextPath}/login.xhtml?shopcode=${shopCode}";
				$("#LoginForm").attr("action", webAppUrl);
				var userPwdInput = $("#textfield2").val();
				$("#hiddenfield2").val($.md5(userPwdInput).toUpperCase());
				$("#button").attr("disabled",true);
				return true;
		} else {
			changeCode();
			$("#button").attr("disabled",false);
			return false;
		}
	}
</script>

</head>
<body>

<form name="LoginForm" method="post" action="#" id="LoginForm" onsubmit="return submitForm()">


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="middle">
      <table width="974" height="787" border="0" cellspacing="0" cellpadding="0" class="login_bg">
      <tr>
        <td height="311">&nbsp;</td>
      </tr>
      <tr>
        <td height="354" align="center" valign="top"><table width="320" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="50" align="center">用户名</td>
            <td align="left"><input type="text" class="ip" name="userName" value="" id="userName"  tabindex="1"/></td>
          </tr>
          <tr>
            <td height="50" align="center">密&nbsp;&nbsp;&nbsp;码</td>
            <td align="left"><input type="password" id="textfield2" class="ip"  name="userPwdInput" tabindex="2" value=""/></td>
            <input type="hidden" id="hiddenfield2" name="userPwd" value=""/>
          </tr>
          <tr>
            <td height="50" align="center">验证码</td>
            <td align="left"><input type="text" id="textfield3" class="ip_code" name="code" tabindex="3" value="" maxlength="4" style="float: left"/>
              <img src="${pageContext.request.contextPath}/checkCodePage.xhtml" width="88" height="32" style="vertical-align:middle;position:relative;margin-left:4px;border-radius:2px 2px 2px 2px;float: left;" id="codeImage" onClick="changeCode();"/></td>
          </tr>
          <tr>
            <td align="center" colspan="2"><input type="submit" name="button" id="button" value="" class="btn" tabindex="4"/></td>
          </tr>
        </table></td>
      </tr>
      <tr>
    <td height="107" align="center" valign="top"><p class="copyright">版权所有： Microsoft 2013 All Rights Reserved.</p></td>
  </tr>
</table>
    </td>
  </tr>
</table>
<form>

</body>
</html>
