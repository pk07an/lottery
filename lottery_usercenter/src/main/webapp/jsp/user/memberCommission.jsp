<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	
});
</script>
</head>
<body>
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/updateCommission.action">
		<table id="judge">
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">廣東快樂十分</th>
			</tr>
			<tr>
				<td width="254" height="23" bgcolor="#e7eff8" align="center"><span
					class="STYLE2">類型</span></td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">佣金%A</td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">佣金%B</td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">佣金%C</td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">佣金%D</td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">單注限額</td>
				<td width="254" height="23" bgcolor="#e7eff8" align="center">單项(号)限額</td>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==1">
				<tr>
					<td height="20" align="center" bgcolor="#E7EFF8">${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionD" class="input1" id=""
						value='${commissionD}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionD" id="commissions${sta.index}commissionD" value="${commissionD}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
				</tr>
			</s:if>
				</s:iterator>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">重慶時時彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
			<s:if test="playType==2">
				<tr>
					<td height="20" align="center" bgcolor="#E7EFF8">${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionD" class="input1" id=""
						value='${commissionD}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionD" id="commissions${sta.index}commissionD" value="${commissionD}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
				</tr>
			</s:if>
				</s:iterator>

			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">香港六合彩</th>
			</tr>
			<s:iterator value="commissions" status="sta">
						<s:if test="playType==3">
				<tr>
					<td height="20" align="center" bgcolor="#E7EFF8">${playFinalTypeName}<input
						name="commissions[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"></input>
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionA" class="input1" id=""
						value='${commissionA}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionA" id="commissions${sta.index}commissionA" value="${commissionA}"></input>	
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionB" class="input1" id=""
						value='${commissionB}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionB" id="commissions${sta.index}commissionB" value="${commissionB}"></input>		
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionC" class="input1" id=""
						value='${commissionC}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionC" id="commissions${sta.index}commissionC" value="${commissionC}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].commissionD" class="input1" id=""
						value='${commissionD}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}commissionD" id="commissions${sta.index}commissionD" value="${commissionD}"></input>			
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].bettingQuotas" class="input1" id=""
						value='${bettingQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}bettingQuotas" id="commissions${sta.index}bettingQuotas" value="${bettingQuotas}"></input>				
					</td>
					<td align="center" bgcolor="#F3F8FC"><input
						name="commissions[${sta.index}].itemQuotas" class="input1" id=""
						value='${itemQuotas}' size="10"></input>
					<input type="hidden" name="commissions${sta.index}itemQuotas" id="commissions${sta.index}itemQuotas" value="${itemQuotas}"></input>	
					</td>
					<input type="hidden" name="commissions[${sta.index}].playType" value="${playType }"></input>
				</tr>
			</s:if>
				</s:iterator>

		</table>
		<div class="tj">
			<input type="submit" value="確認" class="btn_4" name="" ></input>
		</div>
	</s:form>
</body>
</html>