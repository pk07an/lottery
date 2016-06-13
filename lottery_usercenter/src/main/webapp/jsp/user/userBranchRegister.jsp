<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ page import="com.npc.lottery.common.Constant "%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" ></link>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
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
			$("#accountAgain").html("該用户名稱已存在");
			$("#verifyName").val("NO");
		} else {
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}

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
	 
	 
	 <%Integer percentCount = (Integer)request.getAttribute("percentCount");%>
	 if(eval(<%=percentCount%>)==0){
		 $("#replenishment0").attr("disabled","true");
		 $("#replenishment1").attr("checked",'1');//
		 $("#chiefRate").append("<option value='0'>不占成</option>");
	 }
	 
	
});
</script>
<div class="content">
	<s:form id="sForm" action="%{pageContext.request.contextPath}/user/saveBranch.action" method="post">
		<div class="main">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="king xy pw">
				<tbody>
					<tr>
						<th colspan="2">分公司注冊</th>
					</tr>
					<tr>
						<td class="tr">上级帳號</td>
						<td class="tl"><input type="text" class="b_g"
							value="${chiefStaffExt.account}" name="chiefStaffExt.account" readonly="readonly"></input></td>
					</tr>
					<tr>
						<td class="tr" width="50%">帳號</td>
						<td class="tl" width="50%"><input type="text" value="" class="b_g"
							maxlength="20" id="account" name="branchStaffExt.account"
							onblur="javascript:checkShopsName();" maxlength="20"></input> <span
							id="accountAgain" style="color: #FF0000;"></span> <s:hidden
								id="verifyName" value="" /> <s:hidden
								name="branchStaffExt.userType" value="3" /></td>
					</tr>
					<tr>
						<td class="tr">密碼</td>
						<td class="tl"><input id="userPassword" type="password" class="b_g"
							value="" name="branchStaffExt.userPwd" maxlength="15"></input></td>
					</tr>
					<tr>
						<td class="tr">走飞</td>
						<td class="tl">
						<s:radio name="branchStaffExt.replenishment" id="replenishment" list="#{'0':'允许','1':'禁止'}" label="走飞" value="0">
						</s:radio>
						</td>
					</tr>
					<tr>
						<td class="tr">用户狀態</td>
						<td class="tl">
						<s:radio name="branchStaffExt.flag" list="#{'0':'有效','1':'禁用','2':'冻结'}" label="狀態" value="0">
						</s:radio>
						</td>
					</tr>
					<tr>
						<td class="tr">中文名字</td>
						<td class="tl"><input name="branchStaffExt.chsName"
							class="w176 b_g" type="text" value="" maxlength="20"/></input></td>
					</tr>
					<tr>
						<td class="tr">信用額度</td>
						<td class="tl">
							<input id="available" name="branchStaffExt.availableCreditLine"
								class="w176 b_g" type="text" value="0" maxlength="9"/></input>
						</td>
					</tr>
					<tr>
						<td class="tr">分公司占成上限</td>
						<td class="tl">
						<s:select list="companyRateCount" name="company" id="companyRate" headerValue="不占成" headerKey="0">
						</s:select>
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
			<s:iterator value="commissionsList" status="sta">
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
			<s:iterator value="commissionsList" status="sta">
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
			<s:iterator value="commissionsList" status="sta">
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
			<s:iterator value="commissionsList" status="sta">
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
</div>
</html>
