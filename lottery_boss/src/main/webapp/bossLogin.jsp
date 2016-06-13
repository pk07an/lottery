<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>OA系统管理登录入口</title>
<link href="${pageContext.request.contextPath}/css/loginManager.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script>
    var checkCodeResult = false;//验证码校验结果

    //校验用户的输入
    function checkForm(){
        
        var userName = document.LoginForm.userName.value;
        var userPassword = document.LoginForm.userPwd.value;
        var code = document.LoginForm.code.value;
        
        if(null == userName || 0 == userName.length){
            alert("请输入用户名！");
            document.LoginForm.userName.focus();
            return false;
        }
        if(null == userPassword || 0 == userPassword.length){
            alert("请输入登陆密碼！");
            document.LoginForm.userPwd.focus();
            return false;
        }
        if(null == code || 0 == code.length){
            alert("请输入驗證码！");
            document.LoginForm.code.focus();
            return false;
        }
        
        //校验验证码
        checkCode();
        
        return checkCodeResult;
        
        //return true;
    }
    
    //改变验证码
    function changeCode(){
        document.getElementById("codeImage").src="${pageContext.request.contextPath}/checkCodeBoss.jsp?"+Math.random()*1000;
    }
    
    //校验验证码
    function checkCode(){
        var code = document.LoginForm.code.value;
        if (code != ""){    
            var strUrl = '${pageContext.request.contextPath}/boss/ajaxCheckCode.action?code=' + code;
            var queryUrl = encodeURI(encodeURI(strUrl));
            $.ajax({
                type : "POST",
                url : queryUrl,
                async:false,
                data : {},
                dataType : "json",
                complete : callBackCode
            }); 
        }       
    }
    //校验验证码回调函数
    function callBackCode(json) {
        //alert(json.responseText);
        //if (json.checkResult){
        //TODO 无法按json格式取值，后续查找原因
        if("{\"checkResult\":\"true\"}" == json.responseText){
            checkCodeResult = true;
        }else{
            alert("验证码错误！");
            document.LoginForm.code.select();
            checkCodeResult = false;
        }
    }
        
    function submitForm(){
    	if(checkForm())	{
    	var webAppUrl = "/boss/loginBoss.action";
		$("#LoginForm").attr("action", webAppUrl);
		//alert($("#LoginForm").attr("action"));
		//$("#LoginForm").submit();
		var userPwd = $(":input[name='userPwd']").val();
		$(":input[name='userPwd']").val($.md5(userPwd).toUpperCase());
		return true;
	}else{
		changeCode();
		return false;
	}
}
</script>

</head>
 
<body>
<!--
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="0" height="0" id="Secrecy" align="middle">
<param name="allowScriptAccess" value="sameDomain" />
<param name="movie" value="./Secrecy.swf" /><param name="quality" value="high" /><param name="bgcolor" value="#ffffff" /><embed src="./Secrecy.swf" quality="high" bgcolor="#ffffff" width="0" height="0" name="Secrecy" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
</object>
-->
<s:form name="LoginForm" method="post" action="#" id="LoginForm" onsubmit="return submitForm()">
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
			            <td align="left"><input type="text" class="ip" value="" name="userName" id="userName" tabindex="1"/></td>
			          </tr>
			          <tr>
			            <td height="50" align="center">密&nbsp;&nbsp;&nbsp;码</td>
			            <td align="left"><input type="password" class="ip"  value="" name="userPwd" tabindex="2"/></td>
			          </tr>
			          <tr>
			            <td height="50" align="center">验证码</td>
			            <td align="left"><input type="text" name="code" tabindex="3" maxlength="4" class="ip_code" style="float: left"/>
			              <img src="${pageContext.request.contextPath}/checkCodeBoss.jsp" width="88" height="32" style="vertical-align:middle;position:relative;margin-left:4px;border-radius:2px 2px 2px 2px;float: left;" id="codeImage" onClick="changeCode();"/>
			            </td>
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
</s:form>
</body>
</html>