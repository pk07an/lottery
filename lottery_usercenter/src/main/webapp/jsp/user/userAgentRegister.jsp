<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript">

	function checkShopsName() {
		var account = $("#account").val();
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
	}
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("該帳號已存在");
			$("#verifyName").val("NO");
		} else {
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}

	function checkUserName() {
		 var account = $("#userAccount").val();
		 if(account ==0){
			 $("#userAccountAgain").html("获取数据出错必须选一个上级用户");
		 }else{
			 $("#findFrom").attr("action","${pageContext.request.contextPath}/user/saveFindAgent.action?account="+account);
			$("#findFrom").submit();
		 }
			
		/* var account = $("#userAccount").val();
		if (account != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryStockholerName.action?account='
					+ account;
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {},
				dataType : "json",
				success : callBackAccount
			});
		} */
	}
	
	/* function callBackAccount(json) {
		if (json.moneyCount == null) {
			$("#accountAgain1").html("获取数据出错必须选一个上级用户");
			$("#available").val(null);
		} else {
			$("#accountAgain1").html("");
			$("#available").val(json.moneyCount);
			$("#countMoney").val(json.countMoney);
			$("#percentCount").val(json.percentCount);
		}
	} */
	
	function checkSubmit() {
		var accountName = $("#account").val();
		var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			return false;
		}
		if ($("#verifyName").val() == "OK"  && $("#userAccount").val() != 0) {
			$("#sForm").submit();
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}
	
	function checkChief(){
		var genAgentRates = $("#genAgentRate").val();    //總代理占成
		var agentRates = $("#agentRate").val();//代理占成
		var percentCount = $("#percentCount").val();
		var genAgentRate   = genAgentRates.split("%");
		var agentRate = agentRates.split("%");
		if(genAgentRate[0] != null){
			$("#agentRate").empty();
		}
		<%Integer percentCount = (Integer)request.getAttribute("percentCount");%>
		$("#agentRate").append("<option value='0'>不占成</option>");
		for(i = 5; i<=eval(<%=percentCount%>)-eval(genAgentRate[0]); i=eval(i)+5){
			$("#agentRate").append("<option name='agentRate' value="+i+">"+i+"%</option>"); 
		}
	}
	
	function checkChief1(){
		var genAgentRates = $("#genAgentRate1").val();    //總代理占成
		var agentRates = $("#agentRate").val();//代理占成
		var percentCount = $("#percentCount").val();
		var genAgentRate   = genAgentRates.split("%");
		var agentRate = agentRates.split("%");
		if(genAgentRate[0] != null){
			$("#agentRate").empty();
		}
		$("#agentRate").append("<option value='0'>不占成</option>");
		for(i = 5; i<=eval(genAgentRate[0]); i=eval(i)+5){
			$("#agentRate").append("<option name='agentRate' value="+i+">"+i+"%</option>"); 
		}
	}
	
	
	function checkNum(){
		var totalCredit = $("#totalCredit").val();    //總信用額度
		var available = $("#available").val();//可用額度
		<%Integer moneyCredit = (Integer)request.getAttribute("moneyCredit"); %>
		if(eval(totalCredit) > eval(<%=moneyCredit%>)){
			alert("總信用額度-不能大于-可用信用額度还剩-"+<%=moneyCredit%>);
			$("#totalCredit").val("");
		}
	}
	
	function checkNumType(){
		var totalCredit = $("#totalCredit").val();    //總信用額度
		var available = $("#available").val();//可用額度
		var countMoney = $("#countMoney").val();//剩下多少
		if(eval(totalCredit) > eval(countMoney)){
			alert("總信用額度-不能大于-可用信用額度-可用額还剩-"+countMoney);
			$("#totalCredit").val("");
		}
	}
	
	function checkPure(radio){
		var pure = radio.value;    //占成狀態
		if(pure == 1){
			$("#genAgentRate").hide();
			$("#genAgentRate1").show();
		}else{
			$("#genAgentRate").show();
			$("#genAgentRate1").hide();
		}
	}
</script>

<script language="javascript">
$(document).ready(function() {
	 $("#judge input[type!=hidden]").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(/[^\d.]/g,""));
		        $(this).val($(this).val().replace(/^\./g,""));  
		       $(this).val($(this).val().replace(/\.{2,}/g,"."));  
		       $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	       }
	      var newName =  $(this).attr("name");
	      var subName = newName.replace(/[\[|\]|\.]/g,"");
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(/[^\d.]/g,""));     
	    }).css("ime-mode", "disabled"); 
	 
	 $("#judge input[type!=hidden]").blur(function(){     
	        var tmptxt=$(this).val();     
	      var newName =  $(this).attr("name");
	      var subName = newName.replace(/[\[|\]|\.]/g,"");
	      var nameSpilt = newName.split(".");
	      if(nameSpilt[1] =="commissionA" || nameSpilt[1] =="commissionB"  || nameSpilt[1] =="commissionC" ){
	    	  if(eval($('#'+subName).val())<eval($(this).val())){
				  alert("不能設置大於当前的值--"+$('#'+subName).val());
				  $(this).val($('#'+subName).val());
			  }
	      }
	      if(nameSpilt[1] =="itemQuotas" || nameSpilt[1] =="bettingQuotas"){
	    	  if(eval($('#'+subName).val())<eval($(this).val())){
				  alert("不能設置超过当前的值--"+$('#'+subName).val());
				  $(this).val($('#'+subName).val());
			  }
	      }
	    });
	 
	 
	 <%Integer remainingMoery = (Integer)request.getAttribute("remainingMoery");%>//信用額度还剩多少
	 <%Integer availableMoney = (Integer)request.getAttribute("availableMoney");%>// 可用信用額度
	 <%String typeUser = (String) request.getAttribute("typeUser");%>// 标记
	 if(<%=typeUser%> != null){//上级进来的数据
		 $("#available").val(<%=availableMoney%>);
	 }
	 $("#totalCreditMoney").val(<%=remainingMoery%>);//
	 	$("#countMoney").val(<%=remainingMoery%>);
	 $("#moneyTotalCredit").val(<%=moneyCredit%>);//分公司进来的用户
	 if(eval(<%=percentCount%>)==0){
		 $("#replenishment0").attr("disabled","true");
		 $("#replenishment1").attr("checked",'1');//
		 $("#genAgentRate").append("<option value='0'>不占成</option>");
	 }
});
</script>
<div class="content">
	<s:form id="sForm" action="%{pageContext.request.contextPath}/user/saveAgent.action" method="post">
		<div class="main">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="king2 xy pw">
				<tbody>
					<tr>
						<th colspan="2">代理注冊</th>
					</tr>
					<s:if test="userInfo.userType==2 || subAccountInfo.parentUserType==2">
						<tr >
							<td class="tr">上级帳號</td>
							<td class="tl">
							<select id="userAccount" name="agentStaffExt.genAgentStaffExt.account" onchange="javascript:checkUserName();">
								 <option value="0">----</option>
								 <s:iterator value="chiefStaffExt.branchStaffExtSet" var="st">
								 <s:iterator value="stockholderStaffExtSet" var="s">
								 <s:iterator value="genAgentStaffExtSet" var="g">
								 <s:if test="#request.selaccount==account">
								  	<option value="${account}"   selected="selected" >${account}</option>
								  </s:if>
								  <s:else>
								  	<option value="${account}" >${account}</option>
								  </s:else>
								  </s:iterator>
								  </s:iterator>
								 </s:iterator>
							</select>
								<span id="userAccountAgain" style="color: #FF0000;"></span>
							</td>
						</tr>
					</s:if>
					<s:elseif test="userInfo.userType==3 || subAccountInfo.parentUserType==3">
						<tr >
							<td class="tr">上级帳號</td>
							<td class="tl">
							<select id="userAccount" name="agentStaffExt.genAgentStaffExt.account" onchange="javascript:checkUserName();">
								 <option value="0">----</option>
								 <s:iterator value="branchStaffExt.stockholderStaffExtSet" var="st">
								 <s:iterator value="genAgentStaffExtSet" var="s">
								 <s:if test="#request.selaccount==account">
								  	<option value="${account}"   selected="selected" >${account}</option>
								  </s:if>
								  <s:else>
								  	<option value="${account}" >${account}</option>
								  </s:else>
								  </s:iterator>
								 </s:iterator>
							</select>
								<span id="userAccountAgain" style="color: #FF0000;"></span>
							</td>
						</tr>
					</s:elseif>
					<s:elseif test="userInfo.userType==4 || subAccountInfo.parentUserType==4">
						<tr >
							<td class="tr">上级帳號</td>
							<td class="tl">
							<select id="userAccount" name="agentStaffExt.genAgentStaffExt.account" onchange="javascript:checkUserName();">
								 <option value="0">----</option>
								 <s:iterator value="stockholderStaffExt.genAgentStaffExtSet" var="st">
								 <s:if test="#request.selaccount==account">
								  	<option value="${account}"   selected="selected" >${account}</option>
								  </s:if>
								  <s:else>
								  	<option value="${account}" >${account}</option>
								  </s:else>
								 </s:iterator>
							</select>
								<span id="userAccountAgain" style="color: #FF0000;"></span>
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr >
						<td class="tr">上级帳號</td>
						<td class="tl"><input type="text"
							value="${genAgentStaffExt.account}" name="agentStaffExt.genAgentStaffExt.account" readonly="readonly"></input></td>
						</tr>
					</s:else>
					<tr >
						<td class="tr" width="50%">帳號</td>
						<td class="tl" width="50%"><input type="text" value=""
							maxlength="20" id="account" name="agentStaffExt.account"
							onblur="javascript:checkShopsName();"></input> <span
							id="accountAgain" style="color: #FF0000;"></span> <s:hidden
								id="verifyName" value="" /> <s:hidden
								name="agentStaffExt.userType" value="6" /></td>
					</tr>
					<tr >
						<td class="tr">密碼</td>
						<td class="tl"><input id="userPassword" maxlength="16"  type="password"
							value="" name="agentStaffExt.userPwd"></input></td>
					</tr>
					<s:if test="genAgentStaffExt.replenishment==1">
						<tr >
							<td class="tr">走飞</td>
							<td class="tl">
							<s:radio name="agentStaffExt.replenishment" list="#{'0':'允许'}" label="走飞" value="0" disabled="true">
							</s:radio>
							<s:radio name="agentStaffExt.replenishment" list="#{'1':'禁止'}" label="走飞" value="1">
							</s:radio>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr >
							<td class="tr">走飞</td>
							<td class="tl">
							<s:radio name="agentStaffExt.replenishment" list="#{'0':'允许','1':'禁止'}" label="走飞" value="0">
							</s:radio>
							</td>
						</tr>
					</s:else>
					<s:if test="genAgentStaffExt.flag==1">
					<tr >
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="agentStaffExt.flag" list="#{'0':'有效','2':'凍結'}" label="狀態" disabled="true">
						</s:radio>
						<s:radio name="agentStaffExt.flag" list="#{'1':'禁用'}" label="狀態" value="1">
						</s:radio>
						</td>
					</tr>
					</s:if>
					<s:elseif test="genAgentStaffExt.flag==2">
						<tr >
							<td class="tr">用户狀態</td>
							<td class="tl">
							<s:radio name="agentStaffExt.flag" list="#{'0':'有效','1':'禁用'}" label="狀態" disabled="true">
							</s:radio>
							<s:radio name="agentStaffExt.flag" list="#{'2':'凍結'}" label="狀態" value="2">
							</s:radio>
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr >
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="agentStaffExt.flag" list="#{'0':'有效','1':'禁用','2':'凍結'}" label="狀態" value="0">
						</s:radio>
						</td>
					</tr>
					</s:else>
					<tr >
						<td class="tr">占成狀態</td>
						<td class="tl"><s:radio name="agentStaffExt.pureAccounted" id="pure" onclick="javascript:checkPure(this);"	
									list="#{'0':'纯占成','1':'含下级占成'}" label="狀態" value="0">
								</s:radio>
						</td>
					</tr>
					
					<tr >
						<td class="tr">總代理占成</td>
						<td class="tl">
						<s:select list="genAgentRateCount" name="genAgentRate" id="genAgentRate" headerValue="不占成" headerKey="0" onchange="javascript:checkChief();">
						</s:select>
						
						<s:select list="genAgentRateCount" cssStyle="display:none" name="genAgentRate1" id="genAgentRate1" headerValue="不占成" headerKey="0" onchange="javascript:checkChief1();">
						</s:select>
						<s:hidden name="percentCount" id="percentCount" value=""></s:hidden>
						</td>
					</tr>
					<tr >
						<td class="tr">代理占成上限</td>
						<td class="tl">
						<s:select list="agentRateCount" name="agentRate" id="agentRate" headerValue="不占成" headerKey="0">
						</s:select>
						</td>
					</tr>
					<tr >
						<td class="tr">中文名字</td>
						<td class="tl"><input maxlength="20" name="agentStaffExt.chsName"
							class="w176" type="text" value="" /></input></td>
					</tr>
					<s:if test="(userInfo.userType!=5 && userInfo.userType!=7) || subAccountInfo.parentUserType!=5">
					<tr >
						<td class="tr">信用額度</td>
						<td class="tl"><input  onblur="javascript:checkNumType();"  maxlength="9" id="totalCredit"  onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" value="" name="agentStaffExt.totalCreditLine"
							class="w176" type="text" /></input><span  style="color: #FF0000;">可开信用額度还剩<input type="text" id="totalCreditMoney" value="0" readonly="readonly" style="border:none"></span></td>
					</tr>
					<tr  style="display:none">
						<td class="tr">可用信用額度</td>
						<td class="tl">
							<input id="available" name="agentStaffExt.availableCreditLine"
								class="w176" type="text" value="" readonly="readonly"/><span
							id="accountAgain1" ></span></input>
							<s:hidden id ="countMoney" value=""></s:hidden>
						</td>
					</tr>
					</s:if>
					<s:else>
						<tr >
						<td class="tr">總信用額度</td>
						<td class="tl"><input  onblur="javascript:checkNum();" maxlength="9" id="totalCredit"  onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" value="" name="agentStaffExt.totalCreditLine"
							class="w176" type="text" /></input><span>信用額度还剩<input type="text" id="moneyTotalCredit" value="0" readonly="readonly"></span></td>
						</tr>
						<tr  style="display:none"> 
						<td class="tr">可用信用額度</td>
						<td class="tl">
							<input id="available" name="agentStaffExt.availableCreditLine" class="w176" type="text" value="${genAgentStaffExt.totalCreditLine}" readonly="readonly"/></input>
						</td>
					</tr>
					</s:else>
				</tbody>
			</table>
			
			<table id="judge" class="king2 xy pw">
			<tr>
				<th align="center"  colspan="6">廣東快樂十分</th>
			</tr>
			<tr>
				<td width="254" height="23"  align="center"><span
					class="STYLE2">類型</span></td>
				<td width="254" height="23"  align="center">佣金%A</td>
				<td width="254" height="23"  align="center">佣金%B</td>
				<td width="254" height="23"  align="center">佣金%C</td>
				<td width="254" height="23"  align="center">單注限額</td>
				<td width="254" height="23"  align="center">單项(号)限額</td>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==1">
				<tr>
					<td height="20" align="center" >${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					</td>
					
				</tr>
			</s:if>
				</s:iterator>
			<tr>
				<th align="center"  colspan="6">重慶時時彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==2">
				<tr>
					<td height="20" align="center" >${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					</td>
				</tr>
			</s:if>
				</s:iterator>
				
			<tr>
				<th align="center" colspan="6">北京賽車(PK10)</th>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==4">
				<tr>
					<td height="20" align="center" >${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					</td>
					
				</tr>
			</s:if>
				</s:iterator>

			<tr style="display:none">
				<th align="center"  colspan="6">香港六合彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
						<s:if test="playType==3">
				<tr style="display:none">
					<td height="20" align="center" >${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					</td>
				</tr>
			</s:if>
				</s:iterator>

			</table>    
			<div class="tj">
				<input type="button" value="確認" class="btn_4" name=""
					onclick="checkSubmit();"></input>
			</div>
		</div>
	</s:form>
	<s:form id="findFrom" action="">
	</s:form>
</div>
</html>
