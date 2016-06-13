<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
 <script  src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
 <script  src="/js/admin/user.js" type="text/javascript"></script>
 <script  src="/js/Forbid.js" type="text/javascript"></script>
 <script src="/js/jQuery.md5.js" type="text/javascript"></script>
</head>
<body style="width:95%">
    	<table cellspacing="0" cellpadding="0" border="0" width="804" id="formTable">
		  <tbody><tr>
		    <!-- 中间内容开始 -->
		    <td align="left" width="804" valign="top">
			<div class="main">
			  <table cellspacing="0" cellpadding="0" border="0" width="88%" class="king xy pw" id="betTable">
			  <tbody>
			    <tr>
			      <th colspan="2">變更密碼</th>
			    </tr>
			    <tr bgcolor="#ffffff">
			      <td class="tr even" width="22%">原密碼 </td>
			      <td class="tl" width="78%">
			          <input name="userOldPassword" value="" id="pwd" type="password" onblur="javascript:checkPassword();" style="width:100px"/> 
			          <span id="accountAgain" style="color: #FF0000;"></span> <input  type="hidden" id="verifyName"/>
		          </td>	      
			    </tr>
			    <tr bgcolor="#ffffff">
			      <td class="tr even">新密碼 </td>
			      <td class="tl">
			          <input name="newPassword" id="newPas" value="" type="password" onblur="javascript:checkPasswordFormat()" style="width:100px"/>       
			      </td>
			    </tr>
			    <tr bgcolor="#ffffff">
			      <td class="tr even">確認新密碼 </td>
			      <td class="tl">
			          <input name="newPasswordOne" value="" id="newPasOne" type="password" onblur="javascript:checkNewPassword();" style="width:100px"/>        
			      </td>
			    </tr>
			  </tbody>
			</table>
			<div class="tj">
			     <input type="button" value="確認修改" class="btn2" onclick="checkSubmit();" />
			</div>
			</div>
			</td></tr></tbody></table>
			
			<script type="text/javascript">
				<%if("Y".equals((String)request.getAttribute("isPasswordExpire"))){%>
					$('#formTable').attr("style","margin:0 auto;width:804px");
					alert("抱歉! 您的密碼使用期限超過15天,為安全起見請設定'新密碼'.");
				<%}else if("Y".equals((String)request.getAttribute("isPasswordReset"))){%>
					$('#formTable').attr("style","margin:0 auto;width:804px");
					alert("抱歉! 您的账户為初次登陸 或 密碼由后台重新設定,為安全起見,請設定'新密碼'.");
				<%}%>
				

				function checkPassword() {
					var pwd = $("#pwd").val();
					if (pwd != "") {
						var strUrl = "${pageContext.request.contextPath}/${path}/ajaxQueryQianPassword.xhtml";
						var queryUrl = encodeURI(encodeURI(strUrl));
						$.ajax({
							type : "POST",
							url : queryUrl,
							data : {"pwd": $.md5(pwd).toUpperCase()},
							dataType:"json",
							success : callBackName
						});
					}
				}
				function callBackName(json) {
					if (json.data.count > 0) {
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
							var strUrl="${pageContext.request.contextPath}/${path}/changePassword.xhtml";
							var subUrl = encodeURI(encodeURI(strUrl));
							$.ajax({
								type : "POST",
								url : subUrl,
								data : {"userOldPassword":$("#pwd").val(),"newPassword":$("#newPas").val(),"newPasswordOne":$("#newPasOne").val()},
								dataType:"json",
								success : function(json){
									if(json.errorCode==-1){
										alert(json.errorMsg);
									}else{
										alert("密码修改成功,请重新登录");
										window.parent.location.href="${pageContext.request.contextPath}/${path}/logout.xhtml?shopcode=${shopCode}";
									}
								}
							});
							
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
				
				//add by peter add password format validation
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
</body>
</html>
