<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">

$(document).ready(function() {
	var args1  = /[\':;*?~`!@#$%^&+=-_{}\[\]\<\>\(\),\.']/; 
	var args2  = /[^\d.]/g; 
	var leftCredit=$('#jq_left_credit').val();
	var maxRate=100-$('#jq_max_rate').val();
	
	 $("#upperRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));   
	       }
// 	       if(parseInt($(this).val())>parseInt(maxRate)){
// 	    	   alert("分公司占成不可高於"+maxRate+"% 請重新設定");
// 	    	   $(this).focus();
// 	    	   $(this).val(maxRate);
// 	       }
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));      
	    }).css("ime-mode", "disabled");
	 
	 $("#credLine").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));   
	       }
	       if(parseInt($(this).val())>parseInt(leftCredit))
			   $(this).val(leftCredit);
	       var k = new change($(this).val()); 
	       $("#jq_ch_money").html(k.pri_ary());
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));       
	    }).css("ime-mode", "disabled");
	  
	 //add by peter
	 $("#jq_below_rate_limit").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));   
	       }
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));       
	    }).css("ime-mode", "disabled");
	  
	
});


function enterAgain()
{
  var selAccount=$('#branchAccount').val();
  $('#jq_branch_account').val(selAccount);
  $('#changeForm').submit();	
	
}

	function checkShopsName() {
		var account = $("#account").val().NoSpace();
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
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("  選擇的帳號已被占用，不可用！！！！");
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
		
		<s:if test="#request.isbranchOrbranchSub==false || #request.ischiefOrchiefSub==true">
		var selAccount=$('#branchAccount').val();
		//add by peter
		if($.trim(selAccount).length==0){
			alert("請先選擇或設置上級分公司！")
			return false;
		}
		</s:if>
		if($.trim(accountName).length==0)
		{
			alert("帳號不能為空！");
			$("#account").focus();
			return false;
		}
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			$("#account").focus();
			return false;
		}
		if(!$.trim($('#credLine').val()))
		{
			alert("信用額度 不能为空");
			return false;
		}
		if(!$.isNumeric($('#credLine').val()))
		{
			return false;
		}
		if(parseInt(leftCredit)<parseInt($('#credLine').val()))
		{
			alert("設定'信用額度'超過上級'可用餘額'");
			return false;
		}
		if(100-parseInt(maxRate)<parseInt($('#upperRate').val()))
		{
			alert("分公司占成不可高於"+(100-maxRate)+"% 請重新設定");
			$("#upperRate").focus();
			return false;
			
		}
		var password=$("#userPassword").val();
		if(!passwordCheck(password))
		{
			$("#userPassword").focus();
			return false;
		}

		 var item = $("input[name='stockholderStaffExt.rateRestrict']:checked").val();
	
		if(item==1)
		 {
			
			if($('#jq_below_rate_limit').val().length==0)
			{
			alert("限制下級占成數不能為空");
			return false;
			}
			var maxRc=100-parseInt(maxRate)-parseInt($('#upperRate').val());
			if(parseInt($('#jq_below_rate_limit').val())>maxRc)
			{
				alert("限制下线占成不能大于"+maxRc);
				return false;
			}
		 }
		if ($("#verifyName").val() == "OK" && $("#userPassword").val()!=null && $("#userPassword").val()!="" && $("#userAccount").val() != 0) {
			   //add by peter for 提交时灰化按纽
			   $(".btn2").attr("disabled", true);
			   var account=$("#account").val();
				alert("账户'"+account+"' 已成功新增，请设定退水及投注限额");
				//用户密码加密
				$("#userPassword").val($.md5(password).toUpperCase());
			$("#sForm").submit();
			$("#userPassword").val("");
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}

</script>

<div class="content">
	<s:form id="sForm"
		action="%{pageContext.request.contextPath}/user/saveStock.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=0" method="post">
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
                <td width="99%" align="left" class="F_bold">&nbsp;股東 -&gt; 新增</td>
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
    <td class="t_right even4">上級分公司</td>
    <s:if test="#request.ischiefOrchiefSub==true">
     <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> <s:select id="branchAccount" name="stockholderStaffExt.branchStaffExt.account" list="chiefStaffExt.branchStaffExtSet" listKey="account" listValue="account" onChange="enterAgain();" value="#request.selectBranch.account"></s:select></td>
        <td><span>名稱：<s:property value="#request.selectBranch.chsName"/></span><span class="blue ml10">餘額：<s:property value="#request.selectBranch.availableCreditLine"/></span></td>
      </tr>
    </table></td>
    
    </s:if>
    <s:elseif test="#request.isbranchOrbranchSub==true"> 
    
     <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input type="text" class="b_g" value="<s:property value="#request.selectBranch.account"/>" name="stockholderStaffExt.branchStaffExt.account" readonly="readonly"></input></td>
        <td><span>名稱：<s:property value="#request.selectBranch.chsName"/></span><span class="blue ml10">餘額：<s:property value="#request.selectBranch.availableCreditLine"/></span></td>
      </tr>
    </table></td>
    
    </s:elseif>
    
   
  </tr>
  
  <tr>
    <td width="18%" class="t_right even4">股東帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input type="text" value="" class="" maxlength="20" id="account" name="stockholderStaffExt.account" onblur="javascript:checkShopsName();" maxlength="20"></input></td>
        <td><span id="accountAgain" style="margin-left:5px"></span> <s:hidden id="verifyName" value="" /> <s:hidden name="branchStaffExt.userType" value="3" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">登入密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input id="userPassword" type="text" class=""
							value="" name="stockholderStaffExt.userPwd" maxlength="15"></input></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">股東名稱</td>
    <td class="t_left"><input name="stockholderStaffExt.chsName" type="text" value="" maxlength="20" /></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left"><input type="text"  id="credLine" name="stockholderStaffExt.totalCreditLine" value="0" maxlength="9" /> <span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span></td>
  </tr>
  <tr>
    <td class="t_right even4">分公司占成</td>
    <td class="t_left"><input name="stockholderStaffExt.branchRate" value="0" size="6" maxlength="10" id="upperRate"/>
      % &nbsp;最高可設占成&nbsp;<s:property value="100-#request.selectBranch.chiefRate"/>%</td>
  </tr>
  
  <tr>
    <td class="t_right even4">下線占成上限</td>
    <td class="t_left"><s:radio name="stockholderStaffExt.rateRestrict" id="rateRestrict" list="#{'0':' 占餘成數下線任占','1':' 限制下線可占成數'}" label="下線占成上限" value="0">
						</s:radio><input type="text" name="stockholderStaffExt.belowRateLimit" value="" id="jq_below_rate_limit" style="width:50px;"/>%
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">補貨功能</td>
    <td class="t_left">
     <s:if test="stockholderStaffExt.branchStaffExt.replenishment==1">
    <s:radio name="stockholderStaffExt.replenishment" id="replenishment" list="#{'0':' 啟用','1':' 禁用'}" label="補貨功能" value="1" disabled="true"></s:radio>
    </s:if>
    <s:else>
    <s:radio name="stockholderStaffExt.replenishment" id="replenishment" list="#{'0':' 啟用','1':' 禁用'}" label="補貨功能" value="0"></s:radio>
    </s:else> 
    
    
    
    <%-- <s:radio name="stockholderStaffExt.replenishment" id="replenishment" list="#{'0':' 啟用','1':' 禁用'}" label="補貨功能" value="0"></s:radio>  --%>
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
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryStockholder.action?type=userStockholder&account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&searchType=${searchType}&searchValue=${searchValue}'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
	</s:form>
	<s:form id="changeForm" action="%{pageContext.request.contextPath}/user/saveStockholder.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=%{searchUserStatus}&searchType=%{searchType}&searchValue=%{searchValue}" target="_self">
	<input type="hidden" name="branchStaffExt.account" id="jq_branch_account"/>
	</s:form>
	<input type="hidden" id="jq_left_credit" name="leftcredit" value="${selectBranch.availableCreditLine}"/>
	<input type="hidden" id="jq_max_rate" name="maxrate" value="${selectBranch.chiefRate}"/>
</div>
</html>
