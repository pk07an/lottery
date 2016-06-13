	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">

	function checkShopsName() {
		var account = $("#account").val();
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
	}
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("該用户名稱已存在");
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
			 $("#findFrom").attr("action","${pageContext.request.contextPath}/user/saveFindMember.action?account="+account);
			$("#findFrom").submit();
		 }
	}
	
	
	
	function checkSubmit() {
		if ($("#verifyName").val() == "OK"  && $("#userAccount").val() != 0) {
			$("#sForm").submit();
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}
	
	function checkChief(){
		
	}
	
	
	function checkNum(){
		var totalCredit = $("#totalCredit").val();    //總信用額度
		$("#available").val(eval($("#totalCredit").val()));//可用額度
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
 
 $("#tuishui").change(function(){   
     var tmptxt=$(this).val();   
	     $("#judge input[type!=hidden]").each(function() {
				var newName =  $(this).attr("name");
			      var subName = newName.replace(/[\[|\]|\.]/g,"");
			      var nameSpilt = newName.split(".");
			      if(nameSpilt[1] =="commissionA" || nameSpilt[1] =="commissionB"  || nameSpilt[1] =="commissionC" ){
			    	  var temp = $(this).val();
		    		  var tempCommission = eval(eval($('#'+subName).val())) - eval(tmptxt);
		    		  if(tempCommission>0){
		    			  if(tempCommission>eval($('#'+subName).val())){
			    			  $(this).val(eval($('#'+subName).val()));
			    		  }else{
			    			  $(this).val(tempCommission); 
			    		  }
		    		  }else{
		    			  $(this).val(eval($('#'+subName).val()));
		    		  }
			      }
		});
     
 });
 
 

});
</script>
<div class="content">
	<s:form id="sForm" action="%{pageContext.request.contextPath}/user/savaImmediateMember.action" method="post">
		<div class="main">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="king2 xy pw">
				<tbody>
					<tr>
						<th colspan="2">總监直属会员注冊</th>
					</tr>
						<tr bgcolor="#ffffff">
						<td class="tr">直属上级帳號</td>
						<td class="tl"><input type="text"
							value="${chiefStaffExt.account}" name="memberStaffExt.managerStaff.account" readonly="readonly"></input></td>
						</tr>
					<tr bgcolor="#ffffff">
						<td class="tr" width="50%">帳號</td>
						<td class="tl" width="50%"><input type="text" value=""
							maxlength="20" id="account" name="memberStaffExt.account"
							onblur="javascript:checkShopsName();"></input> <span
							id="accountAgain" style="color: #FF0000;"></span> <s:hidden
								id="verifyName" value="" /> <s:hidden
								name="memberStaffExt.userType" value="9" /></td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">密碼</td>
						<td class="tl"><input id="userPassword" type="password" maxlength="16"
							value="" name="memberStaffExt.userPwd"></input></td>
					</tr>
					<s:if test="chiefStaffExt.flag==1">
					<tr bgcolor="#ffffff">
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="memberStaffExt.flag" list="#{'0':'有效','2':'冻结'}" label="狀態" disabled="true">
						</s:radio>
						<s:radio name="memberStaffExt.flag" list="#{'1':'禁用'}" label="狀態" value="1">
						</s:radio>
						</td>
					</tr>
					</s:if>
					<s:elseif test="chiefStaffExt.flag==2">
						<tr bgcolor="#ffffff">
							<td class="tr">用户狀態</td>
							<td class="tl">
							<s:radio name="memberStaffExt.flag" list="#{'0':'有效','1':'禁用'}" label="狀態" disabled="true">
							</s:radio>
							<s:radio name="memberStaffExt.flag" list="#{'2':'冻结'}" label="狀態" value="2">
							</s:radio>
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr bgcolor="#ffffff">
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="memberStaffExt.flag" list="#{'0':'有效','1':'禁用','2':'冻结'}" label="狀態" value="0">
						</s:radio>
						</td>
					</tr>
					</s:else>
					<tr bgcolor="#ffffff" style="display: none">
						<td class="tr">總监占成</td>
						<td class="tl">
						<input id="rate" name="rate" class="w176" type="text" value="100%" readonly="readonly"/></input>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">中文名字</td>
						<td class="tl"><input name="memberStaffExt.chsName"  maxlength="20"
							class="w176" type="text" value="" /></input></td>
					</tr>
						<tr bgcolor="#ffffff">
						<td class="tr">信用額度</td>
						<td class="tl"><input  onblur="javascript:checkNum();" id="totalCredit"  onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" value="0" name="memberStaffExt.totalCreditLine"
							class="w176" type="text" maxlength="9"/></input></td>
						</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">盘口</td>
						<td class="tl">
						<s:radio name="memberStaffExt.plate" list="#{'A':'A盘','B':'B盘','C':'C盘'}" label="盘口" value="'A'">
						</s:radio>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td class="tr">退水</td>
						<td class="tl">
							<select name="memberStaffExt.backWater" onchange="" id="tuishui"> 
							<option value="0" selected="selected">水全退到底</option> 
							<option value="0.3">赚取 0.3 退水</option>
							<option value="0.5">赚取 0.5 退水</option> 
							<option value="1.0">赚取 1.0 退水</option> 
							<option value="1.5">赚取 1.5 退水</option> 
							<option value="2.0">赚取 2.0 退水</option> 
							<option value="2.0">赚取所有退水</option> 
							</select> 
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
			<s:iterator value="commissionDefault" status="sta">
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
				<th align="center" colspan="6">重慶時時彩</th>
			</tr>
			<s:iterator value="commissionDefault" status="sta">
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
			<s:iterator value="commissionDefault" status="sta">
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
				<th align="center" colspan="6">香港六合彩</th>
			</tr>
			<s:iterator value="commissionDefault" status="sta">
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
