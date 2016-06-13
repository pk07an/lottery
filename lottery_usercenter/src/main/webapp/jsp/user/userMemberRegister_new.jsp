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
	<s:if test="#request.selectAgent.rateRestrict==1 || #request.selectAgent.genAgentStaffExt.rateRestrict==1 || #request.selectAgent.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
	var maxRate = $('#jq_below_max_rate').val()
	</s:if>
	<s:else>
	var maxRate = 100-$('#jq_max_rate').val();
	</s:else>
	
	 $("#upperRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
// 	       if(parseInt($(this).val())>parseInt(maxRate)){
// 	    	   alert("代理占成不可高於"+maxRate+"% 請重新設定");
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
	  
	  
	
});


function enterAgain()
{
  var selAccount=$('#agentAccount').val();
  $('#jq_Agent_account').val(selAccount);
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
		
		<s:if test="#request.isAgentOrSub==false">
		var selAccount=$('#agentAccount').val();
		//add by peter
		if($.trim(selAccount).length==0){
			alert("請先選擇或設置上級代理！")
			return false;
		}
		</s:if>
		//add by peter add account validation
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
		

		//add by peter add password validation
		var password=$("#userPassword").val();
		if(!passwordCheck(password))
		{
			//alert("false");
			$("#userPassword").focus();
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
		<s:if test="#request.selectAgent.rateRestrict==1">
			var tmp = 	parseInt($('#jq_below_max_rate').val())
		</s:if>
		<s:elseif test="#request.selectAgent.genAgentStaffExt.rateRestrict==1">
			//如果上级代理没有设置限制占成，上上级总代理限制了,占成=上上级总代理限制占成-上级代理已占
			var tmp = parseInt($('#jq_below_max_rate').val()) - parseInt(${selectAgent.genAgentRate})
			if(tmp<0){
				tmp=0;
			}
		</s:elseif>
		<s:elseif test="#request.selectAgent.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
			//如果上上级总代理和上级代理没有设置限制占成，上上上级股东限制了,占成=上上上级股东限制占成-上上级总代理已占-上级代理已占
			var tmp = parseInt($('#jq_below_max_rate').val()) - parseInt(${selectAgent.genAgentStaffExt.shareholderRate}) - parseInt(${selectAgent.genAgentRate})
			if(tmp<0){
				tmp=0;
			}
		</s:elseif>
		<s:else>
			var tmp = 100-parseInt(maxRate)
		</s:else>
		if(tmp <parseInt($('#upperRate').val()))
		{
			alert("代理占成不可高於"+tmp+"% 請重新設定");
			$("#upperRate").focus();
			return false;
		}
		
		if ($("#verifyName").val() == "OK" && $("#userPassword").val()!=null && $("#userPassword").val()!="" && $("#userAccount").val() != 0) {
			 var account=$("#account").val();
				//add by peter for 提交时灰化按纽
				$(".btn2").attr("disabled", true);
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
		action="%{pageContext.request.contextPath}/user/saveMember.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=0&type=%{type}" method="post">
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
                <td width="99%" align="left" class="F_bold">&nbsp;會員 -&gt; 新增</td>
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
    <td class="t_right even4">上級代理</td>
    <s:if test="#request.isAgentOrSub==true">
      <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input type="text" class="b_g" value="<s:property value="#request.selectAgent.account"/>" name="memberStaffExt.managerStaff.account" readonly="readonly"></input></td>
        <td><span>名稱：<s:property value="#request.selectAgent.chsName"/></span><span class="blue ml10">餘額：<s:property value="#request.selectAgent.availableCreditLine"/></span></td>
      </tr>
    </table></td>
    
    </s:if>
    <s:else> 
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> <s:select id="agentAccount" name="memberStaffExt.managerStaff.account" list="#request.selList" listKey="account" listValue="account" onChange="enterAgain();" value="#request.selectAgent.account"></s:select></td>
        <td><span>名稱：<s:property value="#request.selectAgent.chsName"/></span><span class="blue ml10">餘額：<s:property value="#request.selectAgent.availableCreditLine"/></span></td>
      </tr>
    </table></td>
    
    
    
    
    </s:else>
    
   
  </tr>
  
  <tr>
    <td width="18%" class="t_right even4">會員帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input type="text" value="" class="" maxlength="20" id="account" name="memberStaffExt.account" onblur="javascript:checkShopsName();" maxlength="20"></input></td>
        <td><span id="accountAgain" style="margin-left:5px"></span> <s:hidden id="verifyName" value="" /> </td>
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
    <td class="t_left"><input type="text"  id="credLine" name="memberStaffExt.totalCreditLine" value="0" maxlength="9" /> <span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span></td>
  </tr>
  <tr>
    <td class="t_right even4">代理占成</td>
    <td class="t_left"><input name="memberStaffExt.rate" value="0" size="6" maxlength="10" id="upperRate"/>
      % &nbsp;最高可設占成&nbsp;
       <s:if test="#request.selectAgent.rateRestrict==1">
       <!-- 上级代理限制了占成 -->
      	<s:property value="#request.selectAgent.belowRateLimit"/>
      </s:if>
       <s:elseif test="#request.selectAgent.genAgentStaffExt.rateRestrict==1">
       	<!--如果上级代理没有设置限制占成，上上级总代理限制了,占成=上上级总代理限制占成-上级代理已占-->
       	<s:if test="(#request.selectAgent.genAgentStaffExt.belowRateLimit - #request.selectAgent.genAgentRate)<0">
       		0
       	</s:if>
       	<s:else>
       		<s:property value="#request.selectAgent.genAgentStaffExt.belowRateLimit - #request.selectAgent.genAgentRate"/>
       	</s:else>
      	
      </s:elseif>
       <s:elseif test="#request.selectAgent.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
       <!-- 如果上上级总代理和上级代理没有设置限制占成，上上上级股东限制了,占成=上上上级股东限制占成-上上级总代理已占-上级代理已占 -->
       	<s:if test="(#request.selectAgent.genAgentStaffExt.stockholderStaffExt.belowRateLimit - #request.selectAgent.genAgentRate - #request.selectAgent.genAgentStaffExt.shareholderRate)<0">
       		0
       	</s:if>
       	<s:else>
      		<s:property value="#request.selectAgent.genAgentStaffExt.stockholderStaffExt.belowRateLimit - #request.selectAgent.genAgentRate - #request.selectAgent.genAgentStaffExt.shareholderRate"/>
      	</s:else>
      </s:elseif>
      <s:else>
      	<s:property value="100-#request.selectAgent.genAgentRate-#request.selectAgent.genAgentStaffExt.shareholderRate-#request.selectAgent.genAgentStaffExt.stockholderStaffExt.branchRate-#request.selectAgent.genAgentStaffExt.stockholderStaffExt.branchStaffExt.chiefRate"/>
      </s:else>
      %</td>
  </tr>
  
  <tr>
    <td class="t_right even4">開放盤口</td>
    <td class="t_left"><s:radio name="memberStaffExt.plate" id="plate" list="#{'A':' A 盤','B':' B 盤','C':' C 盤'}" label="開放盤口" value="'A'" />
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">退水設定</td>
    <td class="t_left">
    <s:select list="#{'0':'水全退到底','0.3':' 賺取0.3退水','0.5':' 賺取0.5退水','1.0':' 賺取1.0退水','1.5':' 賺取1.5退水','2.0':' 賺取2.0退水','2.5':' 賺取2.5退水','100':' 賺取所有退水'}"  value='0' name="memberStaffExt.backWater"></s:select>
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
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryMemberStaff.action?account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
	</s:form>
	<s:form id="changeForm" action="%{pageContext.request.contextPath}/user/saveInitMember.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=%{searchUserStatus}&type=%{type}&searchType=%{searchType}&searchValue=%{searchValue}" target="_self">
	<input type="hidden" name="agentStaffExt.account" id="jq_Agent_account"/>
	</s:form>
	<input type="hidden" id="jq_left_credit" name="leftcredit" value="${selectAgent.availableCreditLine}"/>
	
	<input type="hidden" id="jq_max_rate" name="maxrate" value="<s:property value="#request.selectAgent.genAgentRate+#request.selectAgent.genAgentStaffExt.shareholderRate+#request.selectAgent.genAgentStaffExt.stockholderStaffExt.branchRate+#request.selectAgent.genAgentStaffExt.stockholderStaffExt.branchStaffExt.chiefRate"/>"/>
	<s:if test="#request.selectAgent.rateRestrict==1">
		<!-- 检查上级代理有没有设置占成限制 -->
		<input type="hidden" id="jq_below_max_rate" name="belowmaxrate" value="${selectAgent.belowRateLimit}"/>
	</s:if>
	<s:elseif test="#request.selectAgent.genAgentStaffExt.rateRestrict==1">
		<!-- 检查上上级总代理有没有设置占成限制 -->
		<input type="hidden" id="jq_below_max_rate" name="belowmaxrate" value="${selectAgent.genAgentStaffExt.belowRateLimit}"/>
	</s:elseif>
	<s:elseif test="#request.selectAgent.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
		<!-- 检查上上级股东有没有设置占成限制 -->
		<input type="hidden" id="jq_below_max_rate" name="belowmaxrate" value="${selectAgent.genAgentStaffExt.stockholderStaffExt.belowRateLimit}"/>
	</s:elseif>
	<s:else>
		<input type="hidden" id="jq_below_max_rate" name="belowmaxrate" value=""/>
	</s:else>
</div>
</html>
