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
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
	var args1  = /[\':;*?~`!@#$%^&+=-_{}\[\]\<\>\(\),\.']/; 
	var args2  = /[^\d.]/g; 
	var leftCredit=$('#jq_left_credit').val();
	var maxRate=$('#jq_max_rate').val();
	var owner_cred=$('#jq_owner_credit').val();
	 $("#upperRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
// 	       if(parseInt($(this).val())>parseInt(maxRate)){
// 	    	   alert("總代理占成不可高於"+maxRate+"% 請重新設定");
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
	       
	       if((parseInt($(this).val())-parseInt(owner_cred))>parseInt(leftCredit)){
	    	   if(owner_cred!=0){
		    	   	$(this).val(owner_cred);
		       }else{
		    		   $(this).val("");
		       }
	       }
	       var k = new change($(this).val()); 
	       $("#jq_ch_money").html(k.pri_ary());
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));     
	    }).css("ime-mode", "disabled");
	  
	 var k = new change($('#credLine').val()); 
     $("#jq_ch_money").html(k.pri_ary());  
     
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
	  
	 getMaxRate(${agentStaffExt.ID},6);
});

function checkSubmit() {
	
	var minCredLine=${agentStaffExt.totalCreditLine}-${agentStaffExt.availableCreditLine};
	var leftCredit=$('#jq_left_credit').val();
	var maxRate=$('#jq_max_rate').val();
	var owner_cred=$('#jq_owner_credit').val();	
    var upd_cred=$('#credLine').val();
  	//add by peter add password validation
	var password=$("#userPassword").val();
	if(null !=  password && password != "" && !passwordCheck(password))
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
	if((parseInt(upd_cred)-parseInt(owner_cred))>parseInt(leftCredit))
	{
		alert("設定'信用額度'超過上級'可用餘額'");
		return false;
	}
	else if(parseInt($("#credLine").val())<parseInt(minCredLine))
	{
		alert("信用额度值不能小于已经使用的额度");
		return false;
	}
	if(maxRate<parseInt($('#upperRate').val()))
	{
		alert("總代理占成不可高於"+maxRate+"% 請重新設定");
		$("#upperRate").focus();
		return false;
	}
	 var item = $("input[name='agentStaffExt.rateRestrict']:checked").val();
		
	 var maxRestrictRate=$('#jq_maxRestrictRate').val();
	 var minRestrictRate=$('#jq_minRestrictRate').val();
	 var restrictRate=$('#jq_below_rate_limit').val();
		if(item==1&&restrictRate)
		 {
			if(restrictRate.length==0)			
			{
				alert("限制下級占成數不能為空");
				return false;
			}
			
			<s:if test="#request.agentStaffExt.genAgentStaffExt.rateRestrict==1 || #request.agentStaffExt.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
				//如果上级总代理有设置限制占成，本级代理限制下级占成=上级限制-本级已占
				var tmp = parseInt($('#jq_max_rate').val())-parseInt($('#upperRate').val());
			</s:if>
			<s:else>
				var tmp =	parseInt(maxRestrictRate)-parseInt($('#upperRate').val());
			</s:else>
			if(parseInt(restrictRate)>tmp)
			{
				alert("限制下級占成數不能大于"+tmp);
				return false;
			}
			
			if(parseInt(restrictRate)<parseInt(minRestrictRate))
			{
				alert("限制下級占成數不能小于"+minRestrictRate);
				return false;
			}
			
		 }
		
		//用户密码加密
		if(null !=  password && password != ""){
			//如果用户修改了密码才需要加密
			$("#userPassword").val($.md5(password).toUpperCase());
		}
		$("#sForm").submit();
		$("#userPassword").val("");
	
}

function onChechHasBetSuccess(json) {
	if (null != json && "" != json) {
		var replenishment = ${agentStaffExt.genAgentStaffExt.replenishment};
		var hasBet = json.hasBet;
		if (hasBet != "true") {// 当天没有投注
			//总代理占成
			$("#upperRate").removeAttr("disabled");
			if(replenishment == 0){	//上级没有限制补货功能
				$(":radio[name='agentStaffExt.replenishment']")
							.removeAttr("disabled");
			}
			
			//下线占成上限
			$(":radio[name='agentStaffExt.rateRestrict']").removeAttr("disabled");
			$(":input[name='agentStaffExt.belowRateLimit']").removeAttr("disabled");
		}
	}
}
function onGetMaxRateSuccess(json) {
	if (null != json && "" != json) {
		var maxRate = json.maxRate;
		if (null != maxRate && "" != maxRate) {
			
			var chiefRate = ${agentStaffExt.genAgentStaffExt.stockholderStaffExt.branchStaffExt.chiefRate};
			var branchRate = ${agentStaffExt.genAgentStaffExt.stockholderStaffExt.branchRate};
			var stockRate = ${agentStaffExt.genAgentStaffExt.shareholderRate};
		    var minRestrictRate=eval(100-maxRate);
		    var userMaxRate=eval(maxRate-chiefRate-branchRate-stockRate);
		    var maxRestrictRate = ${maxRestrictRate}
		    if(userMaxRate>maxRestrictRate){
		    	userMaxRate=maxRestrictRate;
		    }
		    
			$("#maxRate").html(userMaxRate);
			var rateRestrict = ${agentStaffExt.genAgentStaffExt.stockholderStaffExt.rateRestrict}
			if(rateRestrict !=1){
				$("#jq_max_rate").val(userMaxRate);
			}
			
			$("#jq_minRestrictRate").val(minRestrictRate);
		}
	}
}
</script>


</head>
<body>
<div id="content">
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/updateAgent.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=%{searchUserStatus}&type=%{type}&searchType=%{searchType}&searchValue=%{searchValue}" method="post" target="_self">
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
                <td width="99%" align="left" class="F_bold">&nbsp;代理 -&gt; 修改</td>
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
    <td class="t_right even4">上級總代理</td>
   
    
     <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74">${agentStaffExt.genAgentStaffExt.account}<input type="hidden" class="b_g" value="${agentStaffExt.genAgentStaffExt.account}" name="agentStaffExt.genAgentStaffExt.account" readonly="readonly" /></td>
        <td><span>名稱：${agentStaffExt.genAgentStaffExt.chsName} 餘額：${agentStaffExt.genAgentStaffExt.availableCreditLine}</span></td>
      </tr>
    </table></td>
 
  </tr>
  
    <tr>
    <td width="18%" class="t_right even4">代理帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> ${agentStaffExt.account}
        <input type="hidden" value="${agentStaffExt.account}" maxlength="20" id="account" name="agentStaffExt.account"></td>
        <td> <td>【
        <s:radio name="agentStaffExt.flag" id="flag" list="#{'0':'启用','2':'冻结','1':'停用'}" label="分公司状态" />】<span class="blue ml10"></span></td></td>
      </tr>
    </table></td>
  </tr>
 
  <tr>
    <td class="t_right even4">登入密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input id="userPassword" type="text" class=""
							value="" name="agentStaffExt.userPwd" maxlength="15"></input></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">代理名稱</td>
    <td class="t_left"><s:textfield name="agentStaffExt.chsName" type="text"  /></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left"><s:textfield  id="credLine" name="agentStaffExt.totalCreditLine"  maxlength="9" /><span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span>『 可  '回收' 馀额  ${agentStaffExt.availableCreditLine} 』 </td>
  </tr>
  <tr>
    <td class="t_right even4">總代理占成</td>
    <td class="t_left"><input type="text" value="${agentStaffExt.genAgentRate}" name="agentStaffExt.genAgentRate"  size="6" maxlength="10" id="upperRate" disabled="true"/>
      % &nbsp;最高可設占成&nbsp;
      <s:if test="#request.agentStaffExt.genAgentStaffExt.rateRestrict==1">
      	<s:property value="#request.agentStaffExt.genAgentStaffExt.belowRateLimit"/>
      </s:if>
      <s:elseif test="#request.agentStaffExt.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
      	<s:property value="#request.agentStaffExt.genAgentStaffExt.stockholderStaffExt.belowRateLimit-#request.agentStaffExt.genAgentStaffExt.shareholderRate"/>
      </s:elseif>
      <s:else>
      	<span id="maxRate"><span>
      </s:else>
      
      %
      </td>
  </tr>
  
  <tr>
    <td class="t_right even4">下線占成上限</td>
    <td class="t_left">
     <s:radio name="agentStaffExt.rateRestrict" id="rateRestrict" list="#{'0':' 占餘成數下線任占','1':' 限制下線可占成數'}" label="下線占成上限" disabled="true"></s:radio> 
    <input type="text" name="agentStaffExt.belowRateLimit" id="jq_below_rate_limit" disabled="disabled" style="width:50px;" value="${agentStaffExt.belowRateLimit}"/>%
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">補貨功能</td>
    <td class="t_left">
    
    <s:radio name="agentStaffExt.replenishment" id="replenishment" list="#{'0':'啟用','1':'禁用'}" label="補貨功能" disabled="true"></s:radio> 
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
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryAgentStaff.action?account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
</s:form>
<s:hidden  id="jq_left_credit" name="agentStaffExt.genAgentStaffExt.availableCreditLine"/>
<%-- <s:hidden  id="jq_left_credit" name="leftcredit" name="agentStaffExt.genAgentStaffExt.availableCreditLine"/> --%>

<s:if test="#request.agentStaffExt.genAgentStaffExt.rateRestrict==1">
	<!-- 如果总代理有设置限制占成，取总代理的 -->
	<input type="hidden" id="jq_max_rate" value="${agentStaffExt.genAgentStaffExt.belowRateLimit}"/>
</s:if>
<s:elseif test="#request.agentStaffExt.genAgentStaffExt.stockholderStaffExt.rateRestrict==1">
	<!-- 否则取上上级股东设置的限制总成-上级总代理已占成数 -->
	<input type="hidden" id="jq_max_rate"  value="${agentStaffExt.genAgentStaffExt.stockholderStaffExt.belowRateLimit-agentStaffExt.genAgentStaffExt.shareholderRate}"/>
</s:elseif>
<s:else>
	<input type="hidden" id="jq_max_rate" value="${maxRate}"/>
</s:else>
<input type="hidden" id="jq_maxRestrictRate" value="${maxRestrictRate}"/>
<input type="hidden" id="jq_minRestrictRate" value="${minRestrictRate}"/>
<%-- <input type="hidden" id="jq_max_rate" name="maxrate" value="<s:property value="agentStaffExt.genAgentStaffExt.shareholderRate+agentStaffExt.genAgentStaffExt.stockholderStaffExt.branchRate+agentStaffExt.genAgentStaffExt.stockholderStaffExt.branchStaffExt.chiefRate" />"/> --%>
<s:hidden id="jq_owner_credit"  name="agentStaffExt.totalCreditLine"/>
<input type="hidden"  id="jq_hasBet" value="${hasBet}"/>
</div>
</body>
</html>
