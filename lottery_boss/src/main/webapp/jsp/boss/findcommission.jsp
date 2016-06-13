<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 38.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric.js"></script>
<script language="JavaScript">  
	$(document).ready(function() {$("#betTable tr td input").keyup(function(){onlyNumber(this);});});
	function onlyNumber($this){$($this).autoNumeric({mDec:'',aSep: ''});}
	function onlyNumberpercent($this){$($this).autoNumeric({mDec:'3',aSep: ''});}
</script>
</head>
<body>
<div class="content">
<s:form id="sForm" action="updateCommissionDefault.action" namespce="boss">
		<table id="betTable" class="king">
			<tr>
				<th align="center" colspan="10">廣東快樂十分<input type="hidden" name="shopsCode" value="<s:property escape="false" value="shopsCode"/>"/></th>
			</tr>
			<tr>						
				<th width="15%">下注類型</th>
			    <th width="15%">公司總受注額(實貨)</th>
			    <th width="5%">單注最低</th>
			    <th width="10%">單注最高</th>
			    <th width="10%">單號限額</th>
			    <th width="15%">最高派彩</th>	    
			    <th width="5%">佣金%A</th>
				<th width="5%">佣金%B</th>
				<th width="5%">佣金%C</th>
				<th width="14%"><span class="blue">負值超額警示</span></th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==1">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			   </s:if>
			</s:iterator>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="10">重慶時時彩</th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==2">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			</s:if>
				</s:iterator>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="10">北京賽車(PK10)</th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==4">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			</s:if>
				</s:iterator>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="10">江苏骰寶(K3)</th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==5">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			</s:if>
				</s:iterator>
            <tr>
				<th align="center" colspan="10">幸运农场</th>
			</tr>
			<tr>						
			    <th width="15%">下注類型</th>
			    <th width="15%">公司總受注額(實貨)</th>
			    <th width="5%">單注最低</th>
			    <th width="10%">單注最高</th>
			    <th width="10%">單號限額</th>
			    <th width="15%">最高派彩</th>	    
			    <th width="5%">佣金%A</th>
				<th width="5%">佣金%B</th>
				<th width="5%">佣金%C</th>
				<th width="14%"><span class="blue">負值超額警示</span></th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
			<s:if test="playType==6">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			   </s:if>
			</s:iterator>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="10">香港六合彩</th>
			</tr>
			<s:iterator value="commissionsList" status="sta">
						<s:if test="playType==3">
				<tr>
					<td height="20" align="center">${playFinalTypeName}
					<input
						name="commissionsList[${sta.index}].playFinalType" type="hidden" id=""
						value="${playFinalType}"/>
					</td>
					<td align="center"><input
						name="commissionsList[${sta.index}].totalQuatas" class="b_g" id=""
						value='${totalQuatas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].lowestQuatas" class="b_g" id=""
						value='${lowestQuatas}' size="5"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].bettingQuotas" class="b_g" id=""
						value='${bettingQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].itemQuotas" class="b_g" id=""
						value='${itemQuotas}' size="10"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].winQuatas" class="b_g" id=""
						value='${winQuatas}' size="10"/></td>	
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionA" class="b_g" id=""
						value='${commissionA}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionB" class="b_g" id=""
						value='${commissionB}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].commissionC" class="b_g" id=""
						value='${commissionC}' size="5" onkeyup="onlyNumberpercent(this);"/></td>
					<td align="center"><input
						name="commissionsList[${sta.index}].loseQuatas" class="b_g" id=""
						value='${loseQuatas}' size="10"/>
					<input type="hidden" name="commissionsList[${sta.index}].playType" value="${playType }"/>
					<input type="hidden" name="commissionsList[${sta.index}].createTime" value="${createTime}"/>
					<input type="hidden" name="commissionsList[${sta.index}].ID" value="${ID}"/></td>
				</tr>
			</s:if>
				</s:iterator>

		</table>
		<div class="tj">
			<input type="submit" value="確認" class="btn_4" name="" />
		</div>
	</s:form>
	</div>
</body>
</html>