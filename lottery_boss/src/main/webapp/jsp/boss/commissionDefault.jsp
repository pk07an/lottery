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
<s:form id="sForm" action="saveCommissionDefault.action" namespce="boss">
		<table id="betTable" class="king" >
			<tr>
				<th align="center" colspan="100">廣東快樂十分</th>
				<input type="hidden" name="shopsCode" value="<s:property escape="false" value="shopsCode"/>"/>
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
			<tr>
				<td height="20" align="center" >第一球 <input name="commissionsList[0].playFinalType" type="hidden" id="" value="GD_ONE_BALL"/></td>
				<td align="center"><input name="commissionsList[0].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[0].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[0].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[0].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[0].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[0].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[0].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[0].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[0].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[0].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第二球 <input
					name="commissionsList[1].playFinalType" type="hidden" id=""
					value="GD_TWO_BALL"/></td>
				<td align="center"><input name="commissionsList[1].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[1].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[1].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[1].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[1].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[1].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[1].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[1].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[1].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[1].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第三球 <input
					name="commissionsList[2].playFinalType" type="hidden" id=""
					value="GD_THREE_BALL"/></td>
				<td align="center"><input name="commissionsList[2].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[2].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[2].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[2].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[2].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[2].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[2].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[2].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[2].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[2].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第四球 <input
					name="commissionsList[3].playFinalType" type="hidden" id=""
					value="GD_FOUR_BALL"/></td>
				<td align="center"><input name="commissionsList[3].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[3].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[3].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[3].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[3].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[3].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[3].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[3].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[3].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[3].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第五球 <input
					name="commissionsList[4].playFinalType" type="hidden" id=""
					value="GD_FIVE_BALL"/></td>
				<td align="center"><input name="commissionsList[4].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[4].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[4].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[4].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[4].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[4].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[4].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[4].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[4].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[4].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第六球 <input
					name="commissionsList[5].playFinalType" type="hidden" id=""
					value="GD_SIX_BALL"/></td>
				<td align="center"><input name="commissionsList[5].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[5].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[5].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[5].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[5].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[5].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[5].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[5].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[5].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[5].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第七球 <input
					name="commissionsList[6].playFinalType" type="hidden" id=""
					value="GD_SEVEN_BALL"/></td>
				<td align="center"><input name="commissionsList[6].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[6].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[6].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[6].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[6].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[6].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[6].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[6].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[6].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[6].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第八球 <input
					name="commissionsList[7].playFinalType" type="hidden" id=""
					value="GD_EIGHT_BALL"/></td>
				<td align="center"><input name="commissionsList[7].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[7].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[7].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[7].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[7].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[7].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[7].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[7].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[7].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[7].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8大小 <input
					name="commissionsList[8].playFinalType" type="hidden" id=""
					value="GD_OEDX_BALL"/></td>
				<td align="center"><input name="commissionsList[8].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[8].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[8].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[8].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[8].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[8].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[8].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[8].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[8].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[8].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8單雙 <input
					name="commissionsList[9].playFinalType" type="hidden" id=""
					value="GD_OEDS_BALL"/></td>
				<td align="center"><input name="commissionsList[9].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[9].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[9].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[9].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[9].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[9].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[9].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[9].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[9].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[9].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8尾數大小 <input
					name="commissionsList[10].playFinalType" type="hidden" id=""
					value="GD_OEWSDX_BALL"/></td>
				<td align="center"><input name="commissionsList[10].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[10].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[10].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[10].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[10].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[10].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[10].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[10].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[10].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[10].playType" value="1"/></td>
			</tr>

			<tr>
				<td height="20" align="center" >第1-8合數單雙 <input
					name="commissionsList[11].playFinalType" type="hidden" id=""
					value="GD_HSDS_BALL"/></td>
				<td align="center"><input name="commissionsList[11].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[11].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[11].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[11].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[11].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[11].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[11].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[11].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[11].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[11].playType" value="1"/></td>
			</tr>

			<tr>
				<td height="20" align="center" >第1-8方位 <input
					name="commissionsList[12].playFinalType" type="hidden" id=""
					value="GD_FW_BALL"/></td>
				<td align="center"><input name="commissionsList[12].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[12].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[12].bettingQuotas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[12].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[12].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[12].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[12].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[12].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[12].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[12].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8中發白 <input
					name="commissionsList[13].playFinalType" type="hidden" id=""
					value="GD_ZF_BALL"/></td>
				<td align="center"><input name="commissionsList[13].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[13].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[13].bettingQuotas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[13].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[13].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[13].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[13].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[13].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[13].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[13].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和大小 <input
					name="commissionsList[14].playFinalType" type="hidden" id=""
					value="GD_ZHDX_BALL"/></td>
				<td align="center"><input name="commissionsList[14].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[14].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[14].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[14].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[14].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[14].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[14].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[14].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[14].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[14].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和單雙 <input
					name="commissionsList[15].playFinalType" type="hidden" id=""
					value="GD_ZHDS_BALL"/></td>
				<td align="center"><input name="commissionsList[15].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[15].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[15].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[15].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[15].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[15].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[15].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[15].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[15].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[15].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和尾數大小 <input
					name="commissionsList[16].playFinalType" type="hidden" id=""
					value="GD_ZHWSDX_BALL"/></td>
				<td align="center"><input name="commissionsList[16].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[16].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[16].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[16].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[16].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[16].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[16].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[16].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[16].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[16].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >龍虎 <input
					name="commissionsList[17].playFinalType" type="hidden" id=""
					value="GD_LH_BALL"/></td>
				<td align="center"><input name="commissionsList[17].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[17].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[17].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[17].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[17].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[17].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[17].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[17].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[17].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[17].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選二 <input
					name="commissionsList[18].playFinalType" type="hidden" id=""
					value="GD_RXH_BALL"/></td>
				<td align="center"><input name="commissionsList[18].totalQuatas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[18].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[18].bettingQuotas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[18].itemQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[18].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[18].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[18].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[18].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[18].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[18].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任二連直 <input
					name="commissionsList[19].playFinalType" type="hidden" id=""
					value="GD_RELZ_BALL"/></td>
				<td align="center"><input name="commissionsList[19].totalQuatas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[19].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[19].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[19].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[19].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[19].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[19].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[19].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[19].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[19].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任二連組 <input
					name="commissionsList[20].playFinalType" type="hidden" id=""
					value="GD_RTLZ_BALL"/></td>
				<td align="center"><input name="commissionsList[20].totalQuatas" class="b_g" id="" value="5000" size="10"/></td>
				<td align="center"><input name="commissionsList[20].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[20].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[20].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[20].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[20].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[20].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[20].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[20].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[20].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選三 <input
					name="commissionsList[21].playFinalType" type="hidden" id=""
					value="GD_RXS_BALL"/></td>
				<td align="center"><input name="commissionsList[21].totalQuatas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[21].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[21].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[21].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[21].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[21].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[21].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[21].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[21].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[21].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >選三前直 <input
					name="commissionsList[22].playFinalType" type="hidden" id=""
					value="GD_XSQZ_BALL"/></td>
				<td align="center"><input name="commissionsList[22].totalQuatas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[22].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[22].bettingQuotas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[22].itemQuotas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[22].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[22].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[22].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[22].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[22].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[22].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >選三前組 <input
					name="commissionsList[23].playFinalType" type="hidden" id=""
					value="GD_XTQZ_BALL"/></td>
				<td align="center"><input name="commissionsList[23].totalQuatas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[23].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[23].bettingQuotas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[23].itemQuotas" class="b_g" id="" value="2000" size="10"/></td>
				<td align="center"><input name="commissionsList[23].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[23].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[23].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[23].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[23].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[23].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選四 <input
					name="commissionsList[24].playFinalType" type="hidden" id=""
					value="GD_RXF_BALL"/></td>
				<td align="center"><input name="commissionsList[24].totalQuatas" class="b_g" id="" value="5000" size="10"/></td>
				<td align="center"><input name="commissionsList[24].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[24].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[24].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[24].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[24].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[24].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[24].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[24].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[24].playType" value="1"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選五 <input
					name="commissionsList[25].playFinalType" type="hidden" id=""
					value="GD_RXW_BALL"/></td>
				<td align="center"><input name="commissionsList[25].totalQuatas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[25].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[25].bettingQuotas" class="b_g" id="" value="2000" size="10"/></td>
				<td align="center"><input name="commissionsList[25].itemQuotas" class="b_g" id="" value="4000" size="10"/></td>
				<td align="center"><input name="commissionsList[25].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[25].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[25].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[25].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[25].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[25].playType" value="1"/></td>
			</tr>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">重慶時時彩</th>
			</tr>
			<tr>
				<td height="20" align="center" >第一球 <input
					name="commissionsList[26].playFinalType" type="hidden" id=""
					value="CQ_ONE_BALL"/></td>
				<td align="center"><input name="commissionsList[26].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[26].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[26].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[26].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[26].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[26].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[26].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[26].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[26].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[26].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第二球 <input
					name="commissionsList[27].playFinalType" type="hidden" id=""
					value="CQ_TWO_BALL"/></td>
				<td align="center"><input name="commissionsList[27].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[27].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[27].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[27].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[27].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[27].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[27].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[27].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[27].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[27].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第三球 <input
					name="commissionsList[28].playFinalType" type="hidden" id=""
					value="CQ_THREE_BALL"/></td>
				<td align="center"><input name="commissionsList[28].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[28].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[28].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[28].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[28].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[28].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[28].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[28].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[28].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[28].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第四球 <input
					name="commissionsList[29].playFinalType" type="hidden" id=""
					value="CQ_FOUR_BALL"/></td>
				<td align="center"><input name="commissionsList[29].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[29].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[29].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[29].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[29].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[29].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[29].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[29].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[29].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[29].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第五球 <input
					name="commissionsList[30].playFinalType" type="hidden" id=""
					value="CQ_FIVE_BALL"/></td>
				<td align="center"><input name="commissionsList[30].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[30].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[30].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[30].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[30].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[30].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[30].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[30].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[30].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[30].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-5大小 <input
					name="commissionsList[31].playFinalType" type="hidden" id=""
					value="CQ_OFDX_BALL"/></td>
				<td align="center"><input name="commissionsList[31].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[31].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[31].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[31].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[31].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[31].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[31].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[31].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[31].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[31].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-5單雙 <input
					name="commissionsList[32].playFinalType" type="hidden" id=""
					value="CQ_OFDS_BALL"/></td>
				<td align="center"><input name="commissionsList[32].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[32].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[32].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[32].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[32].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[32].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[32].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[32].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[32].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[32].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總合大小 <input
					name="commissionsList[33].playFinalType" type="hidden" id=""
					value="CQ_ZHDX_BALL"/></td>
				<td align="center"><input name="commissionsList[33].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[33].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[33].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[33].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[33].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[33].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[33].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[33].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[33].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[33].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總合單雙 <input
					name="commissionsList[34].playFinalType" type="hidden" id=""
					value="CQ_ZHDS_BALL"/></td>
				<td align="center"><input name="commissionsList[34].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[34].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[34].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[34].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[34].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[34].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[34].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[34].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[34].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[34].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >龍虎和 <input
					name="commissionsList[35].playFinalType" type="hidden" id=""
					value="CQ_LH_BALL"/></td>
				<td align="center"><input name="commissionsList[35].totalQuatas" class="b_g" id="" value="500000" size="10"/></td>
				<td align="center"><input name="commissionsList[35].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[35].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[35].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[35].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[35].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[35].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[35].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[35].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[35].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >前三 <input
					name="commissionsList[36].playFinalType" type="hidden" id=""
					value="CQ_QS_BALL"/></td>
				<td align="center"><input name="commissionsList[36].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[36].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[36].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[36].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[36].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[36].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[36].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[36].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[36].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[36].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >中三 <input
					name="commissionsList[37].playFinalType" type="hidden" id=""
					value="CQ_ZS_BALL"/></td>
				<td align="center"><input name="commissionsList[37].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[37].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[37].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[37].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[37].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[37].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[37].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[37].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[37].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[37].playType" value="2"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >後三 <input
					name="commissionsList[38].playFinalType" type="hidden" id=""
					value="CQ_HS_BALL"/></td>
				<td align="center"><input name="commissionsList[38].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[38].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[38].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[38].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[38].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[38].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[38].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[38].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[38].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[38].playType" value="2"/></td>
			</tr>
			
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">北京賽車(PK10)</th>
			</tr>
			<tr>
				<td height="20" align="center" >冠軍<input
					name="commissionsList[39].playFinalType" type="hidden" id=""
					value="BJ_BALL_FIRST"/></td>
				<td align="center"><input name="commissionsList[39].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[39].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[39].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[39].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[39].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[39].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[39].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[39].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[39].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[39].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >亞軍<input
					name="commissionsList[40].playFinalType" type="hidden" id=""
					value="BJ_BALL_SECOND"/></td>
				<td align="center"><input name="commissionsList[40].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[40].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[40].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[40].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[40].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[40].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[40].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[40].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[40].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[40].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第三名 <input
					name="commissionsList[41].playFinalType" type="hidden" id=""
					value="BJ_BALL_THIRD"/></td>
				<td align="center"><input name="commissionsList[41].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[41].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[41].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[41].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[41].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[41].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[41].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[41].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[41].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[41].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第四名 <input
					name="commissionsList[42].playFinalType" type="hidden" id=""
					value="BJ_BALL_FORTH"/></td>
				<td align="center"><input name="commissionsList[42].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[42].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[42].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[42].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[42].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[42].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[42].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[42].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[42].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[42].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第五名 <input
					name="commissionsList[43].playFinalType" type="hidden" id=""
					value="BJ_BALL_FIFTH"/></td>
				<td align="center"><input name="commissionsList[43].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[43].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[43].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[43].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[43].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[43].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[43].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[43].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[43].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[43].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第六名<input
					name="commissionsList[44].playFinalType" type="hidden" id=""
					value="BJ_BALL_SIXTH"/></td>
				<td align="center"><input name="commissionsList[44].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[44].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[44].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[44].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[44].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[44].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[44].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[44].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[44].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[44].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第七名 <input
					name="commissionsList[45].playFinalType" type="hidden" id=""
					value="BJ_BALL_SEVENTH"/></td>
				<td align="center"><input name="commissionsList[45].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[45].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[45].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[45].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[45].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[45].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[45].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[45].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[45].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[45].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第八名<input
					name="commissionsList[46].playFinalType" type="hidden" id=""
					value="BJ_BALL_EIGHTH"/></td>
				<td align="center"><input name="commissionsList[46].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[46].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[46].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[46].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[46].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[46].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[46].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[46].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[46].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[46].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第九名 <input
					name="commissionsList[47].playFinalType" type="hidden" id=""
					value="BJ_BALL_NINTH"/></td>
				<td align="center"><input name="commissionsList[47].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[47].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[47].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[47].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[47].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[47].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[47].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[47].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[47].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[47].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第十名<input
					name="commissionsList[48].playFinalType" type="hidden" id=""
					value="BJ_BALL_TENTH"/></td>
				<td align="center"><input name="commissionsList[48].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[48].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[48].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[48].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[48].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[48].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[48].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[48].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[48].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[48].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >1-10大小<input
					name="commissionsList[49].playFinalType" type="hidden" id=""
					value="BJ_1-10_DX"/></td>
				<td align="center"><input name="commissionsList[49].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[49].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[49].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[49].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[49].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[49].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[49].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[49].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[49].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[49].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >1-10單雙<input
					name="commissionsList[50].playFinalType" type="hidden" id=""
					value="BJ_1-10_DS"/></td>
				<td align="center"><input name="commissionsList[50].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[50].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[50].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[50].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[50].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[50].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[50].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[50].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[50].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[50].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >1-5龍虎<input
					name="commissionsList[51].playFinalType" type="hidden" id=""
					value="BJ_1-5_LH"/></td>
				<td align="center"><input name="commissionsList[51].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[51].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[51].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[51].itemQuotas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[51].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[51].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[51].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[51].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[51].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[51].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >冠亞軍和大小<input
					name="commissionsList[52].playFinalType" type="hidden" id=""
					value="BJ_DOUBLSIDE_DX"/></td>
				<td align="center"><input name="commissionsList[52].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[52].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[52].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[52].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[52].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[52].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[52].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[52].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[52].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[52].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >冠亞軍和單雙<input
					name="commissionsList[53].playFinalType" type="hidden" id=""
					value="BJ_DOUBLSIDE_DS"/></td>
				<td align="center"><input name="commissionsList[53].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[53].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[53].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[53].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[53].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[53].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[53].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[53].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[53].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[53].playType" value="4"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >冠亞軍和<input
					name="commissionsList[54].playFinalType" type="hidden" id=""
					value="BJ_GROUP"/></td>
				<td align="center"><input name="commissionsList[54].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[54].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[54].bettingQuotas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[54].itemQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[54].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[54].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[54].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[54].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[54].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[54].playType" value="4"/></td>
			</tr>

			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">香港六合彩</th>
			</tr>
			<tr>
				<td height="20" align="center" >特A <input
					name="commissionsList[55].playFinalType" type="hidden" id=""
					value="HK_TA"/></td>
				<td align="center"><input name="commissionsList[55].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[55].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[55].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[55].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[55].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[55].commissionA" class="b_g" id="" value='13' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[55].commissionB" class="b_g" id="" value='14' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[55].commissionC" class="b_g" id="" value='15' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[55].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[55].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特B <input
					name="commissionsList[56].playFinalType" type="hidden" id=""
					value="HK_TB"/></td>
				<td align="center"><input name="commissionsList[56].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[56].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[56].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[56].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[56].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[56].commissionA" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[56].commissionB" class="b_g" id="" value='4' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[56].commissionC" class="b_g" id="" value='5' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[56].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[56].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼單雙<input
					name="commissionsList[57].playFinalType" type="hidden" id=""
					value="HK_TM_DS"/></td>
				<td align="center"><input name="commissionsList[57].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[57].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[57].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[57].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[57].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[57].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[57].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[57].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[57].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[57].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼大小<input
					name="commissionsList[58].playFinalType" type="hidden" id=""
					value="HK_TM_DX"/></td>
				<td align="center"><input name="commissionsList[58].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[58].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[58].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[58].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[58].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[58].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[58].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[58].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[58].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[58].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼合數單雙 <input
					name="commissionsList[59].playFinalType" type="hidden" id=""
					value="HK_TMHS_DS"/></td>
				<td align="center"><input name="commissionsList[59].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[59].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[59].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[59].itemQuotas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[59].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[59].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[59].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[59].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[59].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[59].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼尾數大小 <input
					name="commissionsList[60].playFinalType" type="hidden" id=""
					value="HK_TMWS_DX"/></td>
				<td align="center"><input name="commissionsList[60].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[60].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[60].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[60].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[60].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[60].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[60].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[60].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[60].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[60].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼色波<input
					name="commissionsList[61].playFinalType" type="hidden" id=""
					value="HK_TM_SB"/></td>
				<td align="center"><input name="commissionsList[61].totalQuatas" class="b_g" id="" value="500000" size="10"/></td>
				<td align="center"><input name="commissionsList[61].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[61].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[61].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[61].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[61].commissionA" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[61].commissionB" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[61].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[61].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[61].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正碼<input
					name="commissionsList[62].playFinalType" type="hidden" id=""
					value="HK_ZM"/></td>
				<td align="center"><input name="commissionsList[62].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[62].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[62].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[62].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[62].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[62].commissionA" class="b_g" id="" value='11' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[62].commissionB" class="b_g" id="" value='12' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[62].commissionC" class="b_g" id="" value='13' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[62].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[62].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正特<input
					name="commissionsList[63].playFinalType" type="hidden" id=""
					value="HK_ZT"/></td>
				<td align="center"><input name="commissionsList[63].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[63].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[63].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[63].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[63].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[63].commissionA" class="b_g" id="" value='13' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[63].commissionB" class="b_g" id="" value='14' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[63].commissionC" class="b_g" id="" value='15' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[63].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[63].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正碼1-6單雙<input
					name="commissionsList[64].playFinalType" type="hidden" id=""
					value="HK_ZM1TO6_DS"/></td>
				<td align="center"><input name="commissionsList[64].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[64].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[64].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[64].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[64].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[64].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[64].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[64].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[64].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[64].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正碼1-6大小<input
					name="commissionsList[65].playFinalType" type="hidden" id=""
					value="HK_ZM1TO6_DX"/></td>
				<td align="center"><input name="commissionsList[65].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[65].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[65].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[65].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[65].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[65].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[65].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[65].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[65].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[65].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正碼1-6合數單雙<input
					name="commissionsList[66].playFinalType" type="hidden" id=""
					value="HK_ZM1TO6_HSDS"/></td>
				<td align="center"><input name="commissionsList[66].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[66].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[66].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[66].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[66].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[66].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[66].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[66].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[66].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[66].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >正碼1-6色波<input
					name="commissionsList[67].playFinalType" type="hidden" id=""
					value="HK_ZM1TO6_SB"/></td>
				<td align="center"><input name="commissionsList[67].totalQuatas" class="b_g" id="" value="500000" size="10"/></td>
				<td align="center"><input name="commissionsList[67].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[67].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[67].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[67].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[67].commissionA" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[67].commissionB" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[67].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[67].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[67].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >連碼 <input
					name="commissionsList[68].playFinalType" type="hidden" id=""
					value="HK_LM"/></td>
				<td align="center"><input name="commissionsList[68].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[68].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[68].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[68].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[68].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[68].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[68].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[68].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[68].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[68].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >特碼生肖 <input
					name="commissionsList[69].playFinalType" type="hidden" id=""
					value="HK_TM_SX"/></td>
				<td align="center"><input name="commissionsList[69].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[69].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[69].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[69].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[69].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[69].commissionA" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[69].commissionB" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[69].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[69].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[69].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >生肖尾數(生肖)<input
					name="commissionsList[70].playFinalType" type="hidden" id=""
					value="HK_SXWS_SX"/></td>
				<td align="center"><input name="commissionsList[70].totalQuatas" class="b_g" id="" value="2000000" size="10"/></td>
				<td align="center"><input name="commissionsList[70].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[70].bettingQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[70].itemQuotas" class="b_g" id="" value="80000" size="10"/></td>
				<td align="center"><input name="commissionsList[70].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[70].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[70].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[70].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[70].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[70].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >生肖尾數(尾數)<input
					name="commissionsList[71].playFinalType" type="hidden" id=""
					value="HK_SXWS_WS"/></td>
				<td align="center"><input name="commissionsList[71].totalQuatas" class="b_g" id="" value="2000000" size="10"/></td>
				<td align="center"><input name="commissionsList[71].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[71].bettingQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[71].itemQuotas" class="b_g" id="" value="80000" size="10"/></td>
				<td align="center"><input name="commissionsList[71].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[71].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[71].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[71].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[71].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[71].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >半波 <input
					name="commissionsList[72].playFinalType" type="hidden" id=""
					value="HK_BB"/></td>
				<td align="center"><input name="commissionsList[72].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[72].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[72].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[72].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[72].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[72].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[72].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[72].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[72].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[72].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >六肖 <input
					name="commissionsList[73].playFinalType" type="hidden" id=""
					value="HK_LX"/></td>
				<td align="center"><input name="commissionsList[73].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[73].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[73].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[73].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[73].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[73].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[73].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[73].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[73].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[73].playType" value="3"/></td>
			</tr>

			<tr>
				<td height="20" align="center" >生肖連<input
					name="commissionsList[74].playFinalType" type="hidden" id=""
					value="HK_SXL"/></td>
				<td align="center"><input name="commissionsList[74].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[74].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[74].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[74].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[74].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[74].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[74].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[74].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[74].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[74].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >尾數連 <input
					name="commissionsList[75].playFinalType" type="hidden" id=""
					value="HK_WSL"/></td>
				<td align="center"><input name="commissionsList[75].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[75].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[75].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[75].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[75].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[75].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[75].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[75].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[75].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[75].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >五不中 <input
					name="commissionsList[76].playFinalType" type="hidden" id=""
					value="HK_WBZ"/></td>
				<td align="center"><input name="commissionsList[76].totalQuatas" class="b_g" id="" value="500000" size="10"/></td>
				<td align="center"><input name="commissionsList[76].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[76].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[76].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[76].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[76].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[76].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[76].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[76].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[76].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >過關<input
					name="commissionsList[77].playFinalType" type="hidden" id=""
					value="HK_GG"/></td>
				<td align="center"><input name="commissionsList[77].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[77].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[77].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[77].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[77].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[77].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[77].commissionB" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[77].commissionC" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[77].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[77].playType" value="3"/></td>
			</tr>
            <tr>
				<td height="20" align="center" >總和單雙<input
					name="commissionsList[78].playFinalType" type="hidden" id=""
					value="HK_ZHDS"/></td>
				<td align="center"><input name="commissionsList[78].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[78].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[78].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[78].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[78].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[78].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[78].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[78].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[78].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[78].playType" value="3"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和大小<input
					name="commissionsList[79].playFinalType" type="hidden" id=""
					value="HK_ZHDX"/></td>
				<td align="center"><input name="commissionsList[79].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[79].lowestQuatas" class="b_g" id="" value="5" size="5"/></td>
				<td align="center"><input name="commissionsList[79].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[79].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[79].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[79].commissionA" class="b_g" id="" value='1' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[79].commissionB" class="b_g" id="" value='2' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[79].commissionC" class="b_g" id="" value='3' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[79].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[79].playType" value="3"/></td>
			</tr>
			<tr>
				<th align="center" bgcolor="#e7eff8" colspan="100">江苏骰寶(K3)</th>
			</tr>
			<tr>
				<td height="20" align="center" >大小<input
					name="commissionsList[80].playFinalType" type="hidden" id=""
					value="K3_DX"/></td>
				<td align="center"><input name="commissionsList[80].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[80].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[80].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[80].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[80].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[80].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[80].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[80].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[80].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[80].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >三軍<input
					name="commissionsList[81].playFinalType" type="hidden" id=""
					value="K3_SJ"/></td>
				<td align="center"><input name="commissionsList[81].totalQuatas" class="b_g" id="" value="500000" size="10"/></td>
				<td align="center"><input name="commissionsList[81].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[81].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[81].itemQuotas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[81].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[81].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[81].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[81].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[81].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[81].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >圍骰<input
					name="commissionsList[82].playFinalType" type="hidden" id=""
					value="K3_WS"/></td>
				<td align="center"><input name="commissionsList[82].totalQuatas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[82].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[82].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[82].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[82].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[82].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[82].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[82].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[82].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[82].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >全骰<input
					name="commissionsList[83].playFinalType" type="hidden" id=""
					value="K3_QS"/></td>
				<td align="center"><input name="commissionsList[83].totalQuatas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[83].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[83].bettingQuotas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[83].itemQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[83].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[83].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[83].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[83].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[83].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[83].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >點數<input
					name="commissionsList[84].playFinalType" type="hidden" id=""
					value="K3_DS"/></td>
				<td align="center"><input name="commissionsList[84].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[84].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[84].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[84].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[84].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[84].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[84].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[84].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[84].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[84].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >長牌<input
					name="commissionsList[85].playFinalType" type="hidden" id=""
					value="K3_CP"/></td>
				<td align="center"><input name="commissionsList[85].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[85].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[85].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[85].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[85].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[85].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[85].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[85].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[85].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[85].playType" value="5"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >短牌<input
					name="commissionsList[86].playFinalType" type="hidden" id=""
					value="K3_DP"/></td>
				<td align="center"><input name="commissionsList[86].totalQuatas" class="b_g" id="" value="200000" size="10"/></td>
				<td align="center"><input name="commissionsList[86].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[86].bettingQuotas" class="b_g" id="" value="50000" size="10"/></td>
				<td align="center"><input name="commissionsList[86].itemQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[86].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[86].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[86].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[86].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[86].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[86].playType" value="5"/></td>
			</tr>
			<tr>
				<th align="center" colspan="100">幸运农场</th>
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
			<tr>
				<td height="20" align="center" >第一球 <input name="commissionsList[87].playFinalType" type="hidden" id="" value="NC_ONE_BALL"/></td>
				<td align="center"><input name="commissionsList[87].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[87].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[87].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[87].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[87].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[87].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[87].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[87].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[87].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[87].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第二球 <input
					name="commissionsList[88].playFinalType" type="hidden" id=""
					value="NC_TWO_BALL"/></td>
				<td align="center"><input name="commissionsList[88].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[88].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[88].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[88].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[88].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[88].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[88].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[88].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[88].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[88].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第三球 <input
					name="commissionsList[89].playFinalType" type="hidden" id=""
					value="NC_THREE_BALL"/></td>
				<td align="center"><input name="commissionsList[89].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[89].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[89].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[89].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[89].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[89].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[89].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[89].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[89].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[89].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第四球 <input
					name="commissionsList[90].playFinalType" type="hidden" id=""
					value="NC_FOUR_BALL"/></td>
				<td align="center"><input name="commissionsList[90].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[90].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[90].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[90].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[90].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[90].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[90].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[90].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[90].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[90].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第五球 <input
					name="commissionsList[91].playFinalType" type="hidden" id=""
					value="NC_FIVE_BALL"/></td>
				<td align="center"><input name="commissionsList[91].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[91].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[91].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[91].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[91].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[91].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[91].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[91].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[91].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[91].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第六球 <input
					name="commissionsList[92].playFinalType" type="hidden" id=""
					value="NC_SIX_BALL"/></td>
				<td align="center"><input name="commissionsList[92].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[92].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[92].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[92].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[92].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[92].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[92].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[92].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[92].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[92].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第七球 <input
					name="commissionsList[93].playFinalType" type="hidden" id=""
					value="NC_SEVEN_BALL"/></td>
				<td align="center"><input name="commissionsList[93].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[93].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[93].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[93].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[93].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[93].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[93].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[93].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[93].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[93].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第八球 <input
					name="commissionsList[94].playFinalType" type="hidden" id=""
					value="NC_EIGHT_BALL"/></td>
				<td align="center"><input name="commissionsList[94].totalQuatas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[94].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[94].bettingQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[94].itemQuotas" class="b_g" id="" value="40000" size="10"/></td>
				<td align="center"><input name="commissionsList[94].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[94].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[94].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[94].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[94].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[94].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8大小 <input
					name="commissionsList[95].playFinalType" type="hidden" id=""
					value="NC_OEDX_BALL"/></td>
				<td align="center"><input name="commissionsList[95].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[95].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[95].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[95].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[95].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[95].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[95].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[95].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[95].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[95].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8單雙 <input
					name="commissionsList[96].playFinalType" type="hidden" id=""
					value="NC_OEDS_BALL"/></td>
				<td align="center"><input name="commissionsList[96].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[96].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[96].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[96].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[96].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[96].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[96].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[96].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[96].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[96].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8尾數大小 <input
					name="commissionsList[97].playFinalType" type="hidden" id=""
					value="NC_OEWSDX_BALL"/></td>
				<td align="center"><input name="commissionsList[97].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[97].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[97].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[97].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[97].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[97].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[97].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[97].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[97].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[97].playType" value="6"/></td>
			</tr>

			<tr>
				<td height="20" align="center" >第1-8合數單雙 <input
					name="commissionsList[98].playFinalType" type="hidden" id=""
					value="NC_HSDS_BALL"/></td>
				<td align="center"><input name="commissionsList[98].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[98].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[98].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[98].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[98].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[98].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[98].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[98].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[98].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[98].playType" value="6"/></td>
			</tr>

			<tr>
				<td height="20" align="center" >第1-8方位 <input
					name="commissionsList[99].playFinalType" type="hidden" id=""
					value="NC_FW_BALL"/></td>
				<td align="center"><input name="commissionsList[99].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[99].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[99].bettingQuotas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[99].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[99].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[99].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[99].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[99].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[99].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[99].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >第1-8中發白 <input
					name="commissionsList[100].playFinalType" type="hidden" id=""
					value="NC_ZF_BALL"/></td>
				<td align="center"><input name="commissionsList[100].totalQuatas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[100].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[100].bettingQuotas" class="b_g" id="" value="30000" size="10"/></td>
				<td align="center"><input name="commissionsList[100].itemQuotas" class="b_g" id="" value="60000" size="10"/></td>
				<td align="center"><input name="commissionsList[100].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[100].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[100].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[100].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[100].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[100].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和大小 <input
					name="commissionsList[101].playFinalType" type="hidden" id=""
					value="NC_ZHDX_BALL"/></td>
				<td align="center"><input name="commissionsList[101].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[101].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[101].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[101].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[101].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[101].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[101].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[101].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[101].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[101].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和單雙 <input
					name="commissionsList[102].playFinalType" type="hidden" id=""
					value="NC_ZHDS_BALL"/></td>
				<td align="center"><input name="commissionsList[102].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[102].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[102].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[102].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[102].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[102].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[102].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[102].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[102].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[102].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >總和尾數大小 <input
					name="commissionsList[103].playFinalType" type="hidden" id=""
					value="NC_ZHWSDX_BALL"/></td>
				<td align="center"><input name="commissionsList[103].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[103].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[103].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[103].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[103].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[103].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[103].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[103].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[103].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[103].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >龍虎 <input
					name="commissionsList[104].playFinalType" type="hidden" id=""
					value="NC_LH_BALL"/></td>
				<td align="center"><input name="commissionsList[104].totalQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[104].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[104].bettingQuotas" class="b_g" id="" value="100000" size="10"/></td>
				<td align="center"><input name="commissionsList[104].itemQuotas" class="b_g" id="" value="300000" size="10"/></td>
				<td align="center"><input name="commissionsList[104].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[104].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[104].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[104].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[104].loseQuatas" class="b_g" id="" value='500000' size="10"/>					
				<input type="hidden" name="commissionsList[104].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選二 <input
					name="commissionsList[105].playFinalType" type="hidden" id=""
					value="NC_RXH_BALL"/></td>
				<td align="center"><input name="commissionsList[105].totalQuatas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[105].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[105].bettingQuotas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[105].itemQuotas" class="b_g" id="" value="20000" size="10"/></td>
				<td align="center"><input name="commissionsList[105].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[105].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[105].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[105].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[105].loseQuatas" class="b_g" id="" value='100000' size="10"/>					
				<input type="hidden" name="commissionsList[105].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任二連直 <input
					name="commissionsList[106].playFinalType" type="hidden" id=""
					value="NC_RELZ_BALL"/></td>
				<td align="center"><input name="commissionsList[106].totalQuatas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[106].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[106].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[106].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[106].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[106].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[106].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[106].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[106].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[106].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任二連組 <input
					name="commissionsList[107].playFinalType" type="hidden" id=""
					value="NC_RTLZ_BALL"/></td>
				<td align="center"><input name="commissionsList[107].totalQuatas" class="b_g" id="" value="5000" size="10"/></td>
				<td align="center"><input name="commissionsList[107].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[107].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[107].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[107].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[107].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[107].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[107].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[107].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[107].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選三 <input
					name="commissionsList[108].playFinalType" type="hidden" id=""
					value="NC_RXS_BALL"/></td>
				<td align="center"><input name="commissionsList[108].totalQuatas" class="b_g" id="" value="10000" size="10"/></td>
				<td align="center"><input name="commissionsList[108].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[108].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[108].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[108].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[108].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[108].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[108].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[108].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[108].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >選三前直 <input
					name="commissionsList[109].playFinalType" type="hidden" id=""
					value="NC_XSQZ_BALL"/></td>
				<td align="center"><input name="commissionsList[109].totalQuatas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[109].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[109].bettingQuotas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[109].itemQuotas" class="b_g" id="" value="0" size="10"/></td>
				<td align="center"><input name="commissionsList[109].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[109].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[109].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[109].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[109].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[109].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >選三前組 <input
					name="commissionsList[110].playFinalType" type="hidden" id=""
					value="NC_XTQZ_BALL"/></td>
				<td align="center"><input name="commissionsList[110].totalQuatas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[110].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[110].bettingQuotas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[110].itemQuotas" class="b_g" id="" value="2000" size="10"/></td>
				<td align="center"><input name="commissionsList[110].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[110].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[110].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[110].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[110].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[110].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選四 <input
					name="commissionsList[111].playFinalType" type="hidden" id=""
					value="NC_RXF_BALL"/></td>
				<td align="center"><input name="commissionsList[111].totalQuatas" class="b_g" id="" value="5000" size="10"/></td>
				<td align="center"><input name="commissionsList[111].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[111].bettingQuotas" class="b_g" id="" value="3000" size="10"/></td>
				<td align="center"><input name="commissionsList[111].itemQuotas" class="b_g" id="" value="6000" size="10"/></td>
				<td align="center"><input name="commissionsList[111].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[111].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[111].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[111].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[111].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[111].playType" value="6"/></td>
			</tr>
			<tr>
				<td height="20" align="center" >任選五 <input
					name="commissionsList[112].playFinalType" type="hidden" id=""
					value="NC_RXW_BALL"/></td>
				<td align="center"><input name="commissionsList[112].totalQuatas" class="b_g" id="" value="1000" size="10"/></td>
				<td align="center"><input name="commissionsList[112].lowestQuatas" class="b_g" id="" value="2" size="5"/></td>
				<td align="center"><input name="commissionsList[112].bettingQuotas" class="b_g" id="" value="2000" size="10"/></td>
				<td align="center"><input name="commissionsList[112].itemQuotas" class="b_g" id="" value="4000" size="10"/></td>
				<td align="center"><input name="commissionsList[112].winQuatas" class="b_g" id="" value="1000000" size="10"/></td>
				<td align="center"><input name="commissionsList[112].commissionA" class="b_g" id="" value='0.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[112].commissionB" class="b_g" id="" value='1.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>
				<td align="center"><input name="commissionsList[112].commissionC" class="b_g" id="" value='2.6' size="5" onkeyup="onlyNumberpercent(this);"/></td>	
				<td align="center"><input name="commissionsList[112].loseQuatas" class="b_g" id="" value='1000000' size="10"/>					
				<input type="hidden" name="commissionsList[112].playType" value="6"/></td>
			</tr>
		</table>
		<div class="tj">
			<input type="submit" value="確認" class="btn_4" name="" />
		</div>
	</s:form>
</div>
</body>
</html>