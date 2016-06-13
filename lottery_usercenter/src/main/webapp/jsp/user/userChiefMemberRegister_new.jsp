<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">

function changeUserType()
{
	  //var selAccount=$('#managerAccount').val();
	 // $('#jq_hid_manager_account').val("");
	 var userType=$('input:radio[name="userType"]:checked').val(); 
	  $('#jq_hid_user_type').val(userType);
	  $('#changeForm').submit();
}

function enterAgain()
{
  var selAccount=$('#managerAccount').val();
  $('#jq_hid_manager_account').val(selAccount);
	 var userType=$('input:radio[name="userType"]:checked').val(); 
	  $('#jq_hid_user_type').val(userType);
  $('#changeForm').submit();	
	
}

	function checkShopsName() {
		var account = $("#account").val().NoSpace();
		if (account != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryMemberName.action?account='
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
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("選擇帳號已被占用，不可用!!!");
			$("#accountAgain").addClass("red");
			$("#verifyName").val("NO");
			$("#account").focus();
		} else {
			$("#accountAgain").html("選擇帳號可用!!!");
			$("#accountAgain").removeClass("red");
			$("#verifyName").val("OK");
		}
	}



	
	function checkSubmit() {
		var accountName = $("#account").val();
		var leftCredit=$('#jq_left_credit').val();
		var maxRate=$('#jq_max_rate').val();
		var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
		var selAccount=$('#managerAccount').val();
		//add by peter
		if($.trim(selAccount).length==0){
			alert("請先選擇或設置上級分公司！")
			return false;
		}
		//add by peter add account validation
		if($.trim(accountName).length==0)
		{
			alert("帳號不能為空！");
			$("#account").foucs();
			return false;
		}
		
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			$("#account").focus();
			return false;
		}
		
		//add by peter add password validation
		var password=$("#userPassword").val();
		if(!passwordCheck(password))
		{
			$("#userPassword").focus();
			//alert("false");
			return false;
		}
		
		//alert(leftCredit);
		//alert(maxRate);
		if(parseInt(leftCredit)<parseInt($('#available').val()))
		{
			alert("設定'信用額度'超過上級'可用餘額'");
			return false;
		}
		if(100-parseInt(maxRate)<parseInt($('#branchRate').val()))
		{
			alert("'分公司占成'不可高於"+(100-maxRate)+"% 請重新設定");
		}
			
		if ($("#verifyName").val() == "OK" && $("#userPassword").val()!=null && $("#userPassword").val()!="" && $("#userAccount").val() != 0) {
			//add by peter for 提交时灰化按纽
			$(".btn2").attr("disabled", true);
			$("#sForm").submit();
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}

</script>

<div class="content">
	<s:form id="sForm"
		action="%{pageContext.request.contextPath}/user/savaImmediateMember.action" method="post">
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
                <td width="99%" align="left" class="F_bold">&nbsp;總監     直屬會員 -&gt; 新增</td>
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
 <table width="100%" border="0px" cellspacing="0" cellpadding="0" class="mt4 king higher">
  <tr>
    <th colspan="2"><strong>賬戶资料</strong></th>
  </tr>
  
  <tr>
    <td class="t_right even4">直屬上級</td>
 
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74" colspan="2"><s:radio name="userType" id="jq_userType" list="#request.userTypeMap" label="直屬上級" value="2" onclick="changeUserType()">
						</s:radio> </td>
      
      </tr>
    </table></td>  
  </tr>
  
  
    <tr>
    <td class="t_right even4">直屬總監</td>
   
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> <s:select id="managerAccount" name="memberStaffExt.managerStaff.account" list="#request.selList" listKey="account" listValue="account" onChange="enterAgain();" value="#request.selectManager.account"></s:select></td>
        <td></td>
      </tr>
    </table></td>

    
   
  </tr>
  
  <tr>
    <td width="18%" class="t_right even4">會員帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input type="text" value="" class="" maxlength="20" id="account" name="memberStaffExt.account" onblur="javascript:checkShopsName();" maxlength="20"></input></td>
        <td><span id="accountAgain" style="margin-left:5px"> <s:hidden id="verifyName" value="" /> </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">登入密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input id="userPassword" type="text" class=""
							value="" name="memberStaffExt.userPwd" maxlength="15"></input></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">會員名稱</td>
    <td class="t_left"><input name="memberStaffExt.chsName" type="text" value="" maxlength="20" /></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left"><input type="text"  id="available" name="memberStaffExt.totalCreditLine" value="0" maxlength="9" /> </td>
  </tr>
  <tr>
    <td class="t_right even4">總監占成</td>
    <td class="t_left"><input name="memberStaffExt.rate" value="0" size="6" maxlength="10" id="shareholderRate"/>
  </tr>
  
    <tr>
    <td class="t_right even4">開放盤口</td>
    <td class="t_left"><s:radio name="memberStaffExt.plate" id="plate" list="#{'A':' A 盤','B':' B 盤','C':' C 盤'}" label="開放盤口" value="'A'" />
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">退水設定</td>
    <td class="t_left">
    <s:select list="#{'0':'水全退到底','0.3':' 賺取0.3退水','0.5':' 賺取0.5退水','1.0':' 賺取1.0退水','1.5':' 賺取1.5退水','2.0':' 賺取2.5退水','2.5':' 賺取2.5退水','100':' 賺取所有退水'}"  value='0' name="memberStaffExt.backWater"></s:select>
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
        <td align='center'><input name="" type="button" class="btn2" value="确定" onclick="checkSubmit();"/>
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userStockholder'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
	</s:form>
	<s:form id="changeForm" action="%{pageContext.request.contextPath}/user/savaFindImmediateMember.action" target="_self">
	<input type="hidden" name="memberStaffExt.managerStaff.account" id="jq_hid_manager_account"/>
	<input type="hidden" name="userType" id="jq_hid_user_type"/>
	</s:form>
	<input type="hidden" id="jq_left_credit" name="leftcredit" value="${selectAgent.availableCreditLine}"/>
	<input type="hidden" id="jq_max_rate" name="maxrate" value="${selectAgent.genAgentRate}"/>
</div>
</html>
