<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
<script>

function checkUserName() {
	if($("#account").val().length<2){
		$("#accountAgain").html("必須兩位起");
		$("#accountAgain").addClass("red");
		$("#verifyName").val("NO");
		$("#btnSubmit").attr("disabled","disabled");
	}else{
		$("#btnSubmit").removeAttr("disabled");
		var account = $("#account").val().replace(/\s+/g, "");
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
		$("#account").val(account);
	}
}
function callBackName(json) {
	if (json.count > 0) {
		$("#accountAgain").html("選擇帳號已被占用，不可用!!!");
		$("#accountAgain").addClass("red");
		$("#verifyName").val("NO");
		$("#btnSubmit").attr("disabled","disabled");
	} else {
		$("#btnSubmit").removeAttr("disabled");
		$("#accountAgain").html("選擇帳號可用!!!");
		$("#accountAgain").removeClass("red");
		$("#verifyName").val("OK");
	}
}
function checkSubmit() {
	var accountName = $("#account").val();
	var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
	if($.trim(accountName).length==0)
	{
		alert("帳號不能為空！");
		$("#account").focus();
		return false;
	}
	if(args.test(accountName)) 
	{
		alert("帳號不能包含特殊字符:~@#$%^&*<>()");
		return false;
	}
	if ($("#verifyName").val() == "OK"  && $("#userAccount").val() != 0) {
		//add by peter for 提交时灰化按纽
		$(".btn2").attr("disabled", true);
		$("#sForm").submit();
	} else {
		alert("户名稱數據有誤，請修正后再確認");
		return false;
	}
}

</script>


</head>
<body>
<div id="content">
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/saveUserOutReplenish.action" method="post" target="_self">
<table width="100%" border="0" >
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
               出貨會員-&gt; 新增</td>
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
    <th colspan="2"><strong>賬戶资料</strong></th>
  </tr>
  
  <tr>
    <td width="18%" class="t_right even">帳號</td>
    <td width="82%" class="t_left">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
	      <tr>
		    <td width="74"><input id="account" type="text" class="b_g" value="" name="outReplenishStaff.account" onblur="javascript:checkUserName();" maxlength="20"/></td>
		    <td><span id="accountAgain" style="margin-left:5px"></span> <s:hidden id="verifyName" value="" /></td>
		  </tr>
		 </table>
	 </td>
  </tr>
  <tr>
    <td class="t_right even">名稱</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
    <tr>
        <td width="74"><s:textfield name="outReplenishStaff.chsName" type="text"  /></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even">開放盤口</td>
    <td class="t_left"><s:radio name="outReplenishStaff.plate" id="plate" list="#{'A':' A 盤','B':' B 盤','C':' C 盤'}" label="開放盤口" value="'A'"/>
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
        <td align='center'><input id="btnSubmit" type="button" class="btn2" value="确定" onclick="checkSubmit();"disabled='disabled'/>
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryUserOutReplenish.action'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
</s:form>
</div>
</body>
</html>
