<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript">
	function checkSubmit() {
			$("#sForm").submit();
	}
	
	function checkChief(){
		var shareholderRates = $("#shareholderRate").val();    //股東占成
		var genAgentRateRates = $("#genAgentRate").val();//總代理占成
		var percentCount = $("#percentCount").val();
		var shareholderRate   = shareholderRates.split("%");
		var shareholder = genAgentRateRates.split("%");
		$("#hshareholderRate").val($("#shareholderRate").val());
		if(shareholderRate[0] != null){
			$("#genAgentRate").empty();
		}
		<%Integer percentCount = (Integer)request.getAttribute("percentCount");%>
		$("#genAgentRate").append("<option value='0'>不占成</option>");
		for(i = 5; i<=eval(<%=percentCount%>)-eval(shareholderRate[0]); i=eval(i)+5){
			$("#genAgentRate").append("<option name='genAgentRate' value="+i+">"+i+"%</option>"); 
		}
	}
	
	function checkChief1(){
		var shareholderRates = $("#shareholderRate1").val();    //股東占成
		var genAgentRateRates = $("#genAgentRate").val();//總代理占成
		var percentCount = $("#percentCount").val();
		var shareholderRate   = shareholderRates.split("%");
		var shareholder = genAgentRateRates.split("%");
		$("#hshareholderRate").val($("#shareholderRate1").val());
		if(shareholderRate[0] != null){
			$("#genAgentRate").empty();
		}
		$("#genAgentRate").append("<option value='0'>不占成</option>");
		for(i = 5; i<=eval(<%=percentCount%>)-eval(shareholderRate[0]); i=eval(i)+5){
			$("#genAgentRate").append("<option name='genAgentRate' value="+i+">"+i+"%</option>"); 
		}
	}
	
	function subRate(){
		$("#hgenAgentRate").val($("#genAgentRate").val());
	}
	
	function checkNum(moneyCount){
		var totalCredit = $("#totalCredit").val();    //總信用額度
		var available = $("#available").val();//可用額度
		<%Integer moneyCredit = (Integer)request.getAttribute("moneyCredit"); %>
		if(eval(totalCredit) > (eval(moneyCount)+eval(<%=moneyCredit%>))){
			alert("總信用額度-不能大于-余額还剩-"+<%=moneyCredit%>);
			$("#totalCredit").val(eval(moneyCount));
		}
	}
	
	function checkNumType(){
		var totalCredit = $("#totalCredit").val();    //總信用額度
		var available = $("#available").val();//可用額度
		var countMoney = $("#countMoney").val();//剩下多少
		if(eval(totalCredit) > eval(countMoney)){
			alert("總信用額度-不能大于-可用信用額度-余額还剩-"+countMoney);
			$("#totalCredit").val("");
		}
	}
	
	function checkPure(radio){
		var pure = radio.value;    //占成狀態
		if(pure == 1){
			var shareholderRates = $("#shareholderRate1").val();    //股東占成
			var genAgentRateRates = $("#genAgentRate").val();//總代理占成
			var percentCount = $("#percentCount").val();
			var shareholderRate   = shareholderRates.split("%");
			var shareholder = genAgentRateRates.split("%");
			$("#hshareholderRate").val($("#shareholderRate1").val());
			if(shareholderRate[0] != null){
				$("#genAgentRate").empty();
			}
			$("#genAgentRate").append("<option value='0'>不占成</option>");
			for(i = 5; i<=eval(<%=percentCount%>); i=eval(i)+5){
				$("#genAgentRate").append("<option name='genAgentRate' value="+i+">"+i+"%</option>"); 
			}
			
			$("#shareholderRate").hide();
			$("#shareholderRate1").show();
			$("#hpureAccounted").val(pure);
		}else{
			var shareholderRates = $("#shareholderRate").val();    //股東占成
			var genAgentRateRates = $("#genAgentRate").val();//總代理占成
			var percentCount = $("#percentCount").val();
			var shareholderRate   = shareholderRates.split("%");
			var shareholder = genAgentRateRates.split("%");
			$("#hshareholderRate").val($("#shareholderRate").val());
			if(shareholderRate[0] != null){
				$("#genAgentRate").empty();
			}
			$("#genAgentRate").append("<option value='0'>不占成</option>");
			for(i = 5; i<=eval(<%=percentCount%>)-eval(shareholderRate[0]); i=eval(i)+5){
				$("#genAgentRate").append("<option name='genAgentRate' value="+i+">"+i+"%</option>"); 
			}
			$("#shareholderRate").show();
			$("#shareholderRate1").hide();
			$("#hpureAccounted").val(pure);
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
	      
		  if(eval($('#'+subName).val())<eval($(this).val())){
			  alert("不能设置大于当前的值--"+$('#'+subName).val());
			  $(this).val($('#'+subName).val());
		  }		      
	      
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
				  alert("不能设置大於当前的值--"+$('#'+subName).val());
				  $(this).val($('#'+subName).val());
			  }
	      }
	      if(nameSpilt[1] =="itemQuotas" || nameSpilt[1] =="bettingQuotas"){
	    	  if(eval($('#'+subName).val())<eval($(this).val())){
				  alert("不能设置超过当前的值--"+$('#'+subName).val());
				  $(this).val($('#'+subName).val());
			  }
	      }
	    });
	 
	 
	 
	 
	 if(eval(<%=percentCount%>)==0){
		 $("#replenishment0").attr("disabled","true");
		 $("#replenishment1").attr("checked",'1');//
		 $("#chiefRate").append("<option value='0'>不占成</option>");
	 }
	 
	 $("#moneyTotalCredit").val(<%=moneyCredit%>); //提示还剩多少信用額度
	 <%Integer rateFlag = (Integer)request.getAttribute("rateFlag");%>
	 if(eval(<%=rateFlag%>) == 0){
		$("#shareholderRate").attr("disabled","true");    //股東占成
		$("#genAgentRate").attr("disabled","true");//總代理占成
		$("#shareholderRate1").attr("disabled","true");    //股東占成
		$("#hshareholderRate").val($("#shareholderRate1").val());
		$("#hshareholderRate").val($("#shareholderRate").val());
		$("#hgenAgentRate").val($("#genAgentRate").val());
		$("#pure1").attr("disabled","true");    //占成狀態
		$("#pure0").attr("disabled","true");    //占成狀態
		
	 }
});
</script>
<html xmlns="http://www.w3.org/1999/xhtml">

<div class="content">
	<s:form id="sForm" action="%{pageContext.request.contextPath}/user/updateGenAgent.action" method="post">
		<div class="main">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="king2 xy pw">
				<tbody>
					<tr>
						<th colspan="2">總代理修改</th>
					</tr>
					<tr>
						<td class="tr">上级用户</td>
						<td class="tl"><input type="text"
							value="${genAgentStaffExt.stockholderStaffExt.account}" name="genAgentStaffExt.stockholderStaffExt.account" readonly="readonly"></input>
							</td>
					</tr>
					<tr>
						<td class="tr" width="50%">用户名</td>
						<td class="tl" width="50%"><input type="text" value="${genAgentStaffExt.account}"
							maxlength="20" id="account" name="genAgentStaffExt.account" readonly="readonly"></input> <span
							id="accountAgain" style="color: #FF0000;"></span> <s:hidden
								id="verifyName" value="" /> <s:hidden
								name="genAgentStaffExt.userType" value="5" />
								<s:hidden name="genAgentStaffExt.managerStaffID" value="%{genAgentStaffExt.ID}"></s:hidden>
								</td>
					</tr>
					<tr>
						<td class="tr">密碼</td>
						<td class="tl"><input id="userPassword" type="password"
							value="" name="genAgentStaffExt.userPwd"></input></td>
					</tr>
					<s:if test="genAgentStaffExt.stockholderStaffExt.replenishment==1">
					<tr>
							<td class="tr">走飞</td>
							<td class="tl">
							<s:radio name="genAgentStaffExt.replenishment" list="#{'0':'允许'}" label="走飞" value="0" disabled="true">
							</s:radio>
							<s:radio name="genAgentStaffExt.replenishment" list="#{'1':'禁止'}" label="走飞" value="1">
							</s:radio>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td class="tr">走飞</td>
							<td class="tl">
							<s:radio name="genAgentStaffExt.replenishment" list="#{'0':'允许','1':'禁止'}" label="走飞">
							</s:radio>
							</td>
						</tr>
					</s:else>
					<s:if test="genAgentStaffExt.stockholderStaffExt.flag==1">
					<tr>
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="genAgentStaffExt.flag" list="#{'0':'有效','2':'冻结'}" label="狀態" disabled="true">
						</s:radio>
						<s:radio name="genAgentStaffExt.flag" list="#{'1':'禁用'}" label="狀態" value="1">
						</s:radio>
						</td>
					</tr>
					</s:if>
					<s:elseif test="genAgentStaffExt.stockholderStaffExt.flag==2">
						<tr>
							<td class="tr">用户狀態</td>
							<td class="tl">
							<s:radio name="genAgentStaffExt.flag" list="#{'0':'有效','1':'禁用'}" label="狀態" disabled="true">
							</s:radio>
							<s:radio name="genAgentStaffExt.flag" list="#{'2':'冻结'}" label="狀態" value="2">
							</s:radio>
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr>
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="genAgentStaffExt.flag" list="#{'0':'有效','1':'禁用','2':'冻结'}" label="狀態">
						</s:radio>
						</td>
					</tr>
					</s:else>
					<tr>
						<td class="tr">占成狀態</td>
						<td class="tl"><s:radio name="genAgentStaffExt.pureAccounted" id="pure" onchange="javascript:checkPure(this);"	
									list="#{'0':'纯占成','1':'含下级占成'}" label="狀態">
								</s:radio>
								<s:hidden value="%{genAgentStaffExt.pureAccounted}" id="hpureAccounted" name="hpureAccounted"></s:hidden>
						</td>
					</tr>
					<tr>
						<td class="tr">股東占成</td>
						<td class="tl">
						<s:if test="genAgentStaffExt.shareholderRate==0">
						<s:if test="genAgentStaffExt.pureAccounted==0">
							<s:select id="shareholderRate"  list="shareholderRateCount"  headerValue="不占成" headerKey="0" onchange="javascript:checkChief();">
							</s:select>
							
							<s:select id="shareholderRate1" cssStyle="display:none"  list="shareholderRateCount"  headerValue="不占成" headerKey="0" onchange="javascript:checkChief1();">
							</s:select>
							<s:hidden id="hshareholderRate" name="hshareholderRate" value="0"></s:hidden>
						
						</s:if>
						<s:else>
								<s:select id="shareholderRate" cssStyle="display:none" list="shareholderRateCount"  headerValue="不占成" headerKey="0" onchange="javascript:checkChief();">
								</s:select>
								
								<s:select id="shareholderRate1" list="shareholderRateCount"  headerValue="不占成" headerKey="0" onchange="javascript:checkChief1();">
								</s:select>
								<s:hidden id="hshareholderRate" name="hshareholderRate" value="0"></s:hidden>
						</s:else>
						</s:if>
						<s:else>
							<s:if test="genAgentStaffExt.pureAccounted==0">
								<s:select id="shareholderRate"  list="shareholderRateCount"  headerValue="%{genAgentStaffExt.shareholderRate}%" headerKey="%{genAgentStaffExt.shareholderRate}%" onchange="javascript:checkChief();">
								</s:select>
								
								<s:select id="shareholderRate1" cssStyle="display:none" list="shareholderRateCount"  headerValue="%{genAgentStaffExt.shareholderRate}%" headerKey="%{genAgentStaffExt.shareholderRate}%" onchange="javascript:checkChief1();">
								</s:select>
								<s:hidden id="hshareholderRate" name="hshareholderRate" value="%{genAgentStaffExt.shareholderRate}%"></s:hidden>
							</s:if>
							<s:else>
								<s:select id="shareholderRate" cssStyle="display:none" list="shareholderRateCount"  headerValue="%{genAgentStaffExt.shareholderRate}%" headerKey="%{genAgentStaffExt.shareholderRate}%" onchange="javascript:checkChief();">
								</s:select>
								
								<s:select id="shareholderRate1"  list="shareholderRateCount"  headerValue="%{genAgentStaffExt.shareholderRate}%" headerKey="%{genAgentStaffExt.shareholderRate}%" onchange="javascript:checkChief1();">
								</s:select>
								<s:hidden id="hshareholderRate" name="hshareholderRate" value="%{genAgentStaffExt.shareholderRate}%"></s:hidden>
							</s:else>
						</s:else>
						<span>修改占成时间是02:30:00至08：30：00</span>
						</td>
					</tr>
					<tr>
						<td class="tr">總代理占成上限</td>
						<td class="tl">
						<s:if test="genAgentStaffExt.genAgentRate==0">
							<s:select list="genAgentRateCount"  id="genAgentRate" headerValue="不占成" headerKey="0" onchange="javascript:subRate();">
							</s:select>
							<s:hidden name="hgenAgentRate" id="hgenAgentRate" value="0"></s:hidden>
						</s:if>
						<s:else>
							<s:select list="genAgentRateCount"  id="genAgentRate" headerValue="%{genAgentStaffExt.genAgentRate}%" headerKey="%{genAgentStaffExt.genAgentRate}%" onchange="javascript:subRate();">
							</s:select>
							<s:hidden name="hgenAgentRate" id="hgenAgentRate" value="%{genAgentStaffExt.genAgentRate}%"></s:hidden>
							</s:else>
						</td>
					</tr>
					<tr>
						<td class="tr">中文名字</td>
						<td class="tl"><input name="genAgentStaffExt.chsName"
							class="w176" type="text" value="${genAgentStaffExt.chsName}" /></input></td>
					</tr>
					<tr>
						<td class="tr">總代理已用信用額度</td>
						<td class="tl">
							<input id="" name="alreadyCreditLine"
								class="w176" type="text" value="<s:property value="alreadyCreditLine"/>" maxlength="9" readonly="readonly" style="border:none"/></input>
						</td>
					</tr>
					<tr>
						<td class="tr">信用額度</td>
						<td class="tl"><input  onblur="javascript:checkNum('${genAgentStaffExt.totalCreditLine}');" id="totalCredit"  onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${genAgentStaffExt.totalCreditLine }" name="genAgentStaffExt.totalCreditLine"
							class="w176" type="text" /></input><span  style="color: #FF0000;">可再增加的額度<input type="text" id="moneyTotalCredit" value="" readonly="readonly" style="border:none"></span></td>
					</tr>
					<tr style="display: none">
						<td class="tr">可用信用額度</td>
						<td class="tl">
							<input id="available" name="genAgentStaffExt.availableCreditLine"
								class="w176" type="text" value="${genAgentStaffExt.availableCreditLine}" readonly="readonly"/></input>
						</td>
					</tr>
					
					<tr>
						<td class="tr">注册时间</td>
						<td class="tl"><s:date name="genAgentStaffExt.createDate"  format="yyyy-MM-dd HH:mm:ss" />
						<s:hidden name="genAgentStaffExt.createDate" value="%{genAgentStaffExt.createDate}"></s:hidden>
						</td>
					</tr>
				</tbody>
			</table>
			<table id="judge" class="king2 xy pw">
			<tr>
				<th align="center" colspan="6">廣東快樂十分</th>
			</tr>
			<tr>
				<td width="254" height="23" align="center"><span
					class="STYLE2">類型</span></td>
				<td width="254" height="23" align="center">佣金%A</td>
				<td width="254" height="23" align="center">佣金%B</td>
				<td width="254" height="23" align="center">佣金%C</td>
				<td width="254" height="23" align="center">單注限額</td>
				<td width="254" height="23" align="center">單项(号)限額</td>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==1">
				<tr>
					<td height="20" align="center" >${playFinalTypeName} <input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					</td>
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					<input type="hidden" name="commissions[${sta.index}].createTime" value="${createTime}"></input>
					<input type="hidden" name="commissions[${sta.index}].ID" value="${ID}"></input>
					<input type="hidden" name="commissions[${sta.index}].userType" value="${userType}"></input>
					<input type="hidden" name="commissions[${sta.index}].userId" value="${userId}"></input>
				</tr>
			</s:if>
				</s:iterator>
			<tr>
				<th align="center" colspan="6">重慶時時彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==2">
				<tr>
					<td height="20" align="center" >${playFinalTypeName} <input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
						<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					<input type="hidden" name="commissions[${sta.index}].createTime" value="${createTime}"></input>
					<input type="hidden" name="commissions[${sta.index}].ID" value="${ID}"></input>
					<input type="hidden" name="commissions[${sta.index}].userType" value="${userType}"></input>
					<input type="hidden" name="commissions[${sta.index}].userId" value="${userId}"></input>
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
					<td height="20" align="center" >${playFinalTypeName} <input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
						<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					<input type="hidden" name="commissions[${sta.index}].createTime" value="${createTime}"></input>
					<input type="hidden" name="commissions[${sta.index}].ID" value="${ID}"></input>
					<input type="hidden" name="commissions[${sta.index}].userType" value="${userType}"></input>
					<input type="hidden" name="commissions[${sta.index}].userId" value="${userId}"></input>
					</td>
				</tr>
			</s:if>
				</s:iterator>

			<tr style="display:none">
				<th align="center" colspan="6">香港六合彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
						<s:if test="playType==3">
				<tr style="display:none">
					<td height="20" align="center" >${playFinalTypeName} <input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					</td>
					<td align="center" ><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
						<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
					<input type="hidden" name="commissions[${sta.index}].createTime" value="${createTime}"></input>
					<input type="hidden" name="commissions[${sta.index}].ID" value="${ID}"></input>
					<input type="hidden" name="commissions[${sta.index}].userType" value="${userType}"></input>
					<input type="hidden" name="commissions[${sta.index}].userId" value="${userId}"></input>
					</td>
					
				</tr>
			</s:if>
				</s:iterator>

				
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==1">
				<tr style="display:none">
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
				</tr>
			</s:if>
				</s:iterator>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==2">
				<tr style="display:none">
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
				</tr>
			</s:if>
				</s:iterator>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==4">
				<tr style="display:none">
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
				</tr>
			</s:if>
				</s:iterator>
			<s:iterator value="commissionsList" status="sta">
						<s:if test="playType==3">
				<tr style="display:none">
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" >
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
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
</div>
</html>
