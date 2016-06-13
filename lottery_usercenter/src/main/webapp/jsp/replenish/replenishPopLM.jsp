<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>補貨</title>
<script type="text/javascript">

//总监补货提交
function replenishSubmitChief(){
	var ret=null;
	var strUrl = '${pageContext.request.contextPath}/replenish/ajaxReplenishSubmit.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=
		$.ajax({url:queryUrl,async:false,
			type : "POST",
			data : {typeCode:$("#playFinalType").val(),plate:$("#rPlate").val(),attribute:$("#attribute").val(),
				replenishUserId:$("#replenishUserId").val(),odds:$("#rOdds").val(),money:$("#money").val()},
			dataType:"json",
			success : showResponseDy
		});
	return ret;
}

//其他补货提交
function replenishSubmitOther(){
	var ret=null;
	var strUrl = '${pageContext.request.contextPath}/replenish/ajaxReplenishSubmit.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=
		$.ajax({url:queryUrl,async:false,
			type : "POST",
			data : {typeCode:$("#playFinalTypeOther").val(),plate:$("#rPlateOther").val(),
				money:$("#moneyOther").val(),attribute:$("#attributeOther").val()},
			dataType:"json",
			success : showResponseDy
		});
	return ret;
}


function replenishShowSubmit(){
	var vl=CheckUtil.Trim($("#money").val());
	if(!CheckUtil.isPlusInteger(vl)){
		alert("請输入數字");
		return false;
	}
	if(vl.length==0)
	{
		alert("请输入投注金額");
		return false;
	}
	if($("#fpTime").html()=="00:03"){
		onChangeSubmit();  
		alert("已经封盘！");
		return false;
	}
	if(window.confirm('是否確定補出?')){
		$("#replenishShow").hide();
		replenishSubmitChief();
     }else{
        return false;
    }
}

function replenishShowSubmitOther(){
		var vl=CheckUtil.Trim($("#moneyOther").val());
		if(!CheckUtil.isPlusInteger(vl)){
			alert("請输入數字");
	        return false;
		}
		if(vl.length==0)
		{
			alert("请输入投注金額");
			return false;
		}else if(vl<10){
			alert("補貨金額不能小于10");
			return false;
		}else if(Math.round(vl) > Math.round($("#maxMoneyOther").val())){
			alert("補貨值不能大于補貨限額！");
			return false;
		}
		if($("#fpTime").html()=="00:03"){
			//window.location.reload();
			$("#rForm").submit();
			alert("已经封盘！");
			return false;
		}
	 if(window.confirm('是否確定補出?')){
		 $("#replenishShowOther").hide();
		 replenishSubmitOther();
     }else{
        return false;
    }
}

</script>
</head>
<body>
    <!-- 补货完成明细START -->
    <div id="finishReplenishShow" style="background-color:#EFF1FF; display:none;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:600px;">
		    <colgroup>
		      <col width="15%" />
		      <col width="45%" />
		      <col width="10%" />
		      <col width="10%" />
		      <col width="20%" />
		    </colgroup>
		    <tr>
		      <th colspan="5">補貨結果明細</th>
		    </tr>
		    <tr>
		      <td class="even"><strong>單碼</strong></td>
		      <td class="even"><strong>明細</strong></td>
		      <td class="even"><strong>金額</strong></td>
		      <td class="even"><strong>可贏</strong></td>
		      <td class="even"><strong>結果</strong></td>
		    </tr>
		    <!-- 交易内容 -->
			<tbody id="infolist"></tbody>
		    <tr>
		      <td colspan="5" class="even"><input id="fBtn" type="button" class="btn td_btn" value="確認" /></td>
		    </tr>
		</table>
    </div>
    <!-- 补货完成明细END -->
    
    
	<!-- 總監补货單开始-->
	<div id="replenishShow"
		style="background-color: #EFF1FF; display: none;">
		<s:form id="replenishForm" action="ajaxReplenishSubmit.action" namespace="replenish" method="post">
			<input name="replenish.typeCode" id="playFinalType" type="hidden" value=""/>
			<input name="replenish.attribute" id="attribute" type="hidden" value=""/>
		    <input name="replenish.plate" id="rPlate" type="hidden" value=""/>
			<table border="0" cellspacing="0" cellpadding="0" class="king"
				style="position: absolute; left: 23px; top: 100px; width: 140px; background: #fff; z-index: 9999;">
				<tr>
					<th colspan="2"><strong style="font-size: 11px;">補貨單</strong></th>
				</tr>
				<tr>
					<td width="38">類型</td>
					<td width="105" class="t_left"><strong id="rFinalType"></strong></td>
				</tr>
				<tr>
					<td>會员</td>
					<td class="t_left">
					<s:select id="replenishUserId" name="replenish.replenishUserId" list="#request.mapC" listKey="key" listValue="value" headerKey="0"/></td>
				</tr>
				<tr>
					<td>號碼</td>
					<td class="t_left"><strong id="rAttribute"></strong></td>
				</tr>
				<tr>
					<td>賠率</td>
					<td class="t_left"><input id="rOdds" class="b_g" name="replenish.odds"
						style="width: 76px;" /></td>
				</tr>
				<tr>
					<td>金額</td>
					<td class="t_left"><input id="money" name="replenish.money" class="b_g"
						style="width: 76px;" onblur="onlyNumber(this);"/></td>
				</tr>
				<tr>
					<td colspan="2" bgcolor="#f1f1f1">
					   <input id="rBtnSubmit" type="button" class="sl btn3" value="補出" onclick="replenishShowSubmit()"/> 
					   <input id="retbtn" type="button" class="sl btn3" value="取消" /></td>
				</tr>
			</table>
			<!-- 存儲 彈出框是香港還是重慶廣東 -->
			<input type="hidden" id="lotteryType" name="lotteryType" />
		</s:form>
	</div>
	<!-- 總監补货單结束-->
	
	
	<!-- 其他补货單开始-->
	<div id="replenishShowOther"
		style="background-color: #EFF1FF; display: none;">
		<s:form id="replenishFormOther" action="ajaxReplenishSubmit.action" namespace="replenish" method="post">
			<input name="replenish.typeCode" id="playFinalTypeOther" type="hidden" value=""/>
		    <input name="replenish.attribute" id="attributeOther" type="hidden" value=""/>
		    <input name="replenish.plate" id="rPlateOther" type="hidden" value=""/>
			<table border="0" cellspacing="0" cellpadding="0" class="king"
				style="position: absolute; left: 23px; top: 100px; width: 140px; background: #fff; z-index: 9999;">
				<tr>
					<th colspan="2"><strong style="font-size: 11px;">補貨單</strong></th>
				</tr>
				<tr>
					<td>號碼</td>
					<td class="t_left"><strong id="rAttributeOther"></strong></td>
				</tr>
				<tr>
					<td width="38">類型</td>
					<td width="105" class="t_left"><strong class="blue" id="rFinalTypeOther"></strong></td>
				</tr>
				<tr>
					<td>賠率</td>
					<td class="t_left"><span id="rOddsOther" class="blue"></span></td>
				</tr>
				<tr>
					<td>金額</td>
					<td class="t_left"><input id="moneyOther" name="replenish.money" class="b_g"
						style="width: 76px;" onblur="onlyNumber(this);"/></td>
				</tr>
				<tr>
					<td>限額</td>
					<td class="t_left">
					<span id="maxMoneyOtherStr"></span>
					<input type="hidden" id="maxMoneyOther"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" bgcolor="#f1f1f1">
					   <input id="rBtnSubmitOther" type="button" class="sl btn3" value="補出" onclick="replenishShowSubmitOther()"/> 
					   <input id="retbtnOther" type="button" class="sl btn3" value="取消" /></td>
				</tr>
			</table>
			<!-- 存儲 彈出框是香港還是重慶廣東 -->
			<input type="hidden" id="lotteryType" name="lotteryType" />
		</s:form>
	</div>
	<!-- 其他补货單结束-->
	
	<!-- 總監赔率修改开始-->
	<!-- 手写设置赔率 -->
<div id="oddsShow" style="background-color:#EFF1FF; display:none;">
   <s:form id="oddForm" action="" namespace="replenish" method="post" onkeydown= "#">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:136px;">
		    <colgroup>
		      <col width="20%" />
		      <col width="80%" />
		    </colgroup>
		    <tr style="height:30px"><th colspan="2" >
		                            設定賠率
		        <input id="oddTypeCode" type="hidden" />
		        </th></tr>
		    <tr style="height:30px">
			    <td>類型</td>
			    <td class="blue" id="oddFinalType">&nbsp;</td>
		    </tr>
		    <tr style="height:30px">
			    <td>賠率</td>
			    <td><input type="text" id="oddValue" maxlength="10" size="6" /></td>
		    </tr>
		    <tr><td colspan="2" class="even"><div class="tj">
		          <span><input id="rBtnSubmit" type="button" class="btn td_btn" onclick="opOdd()" value="確定" /></span>
		          <span class="ml10"><input id="oddBtnCancel" type="button" class="btn td_btn" value="取消" /></span></div>
		      </td>
		    </tr>
     </table>
     <!-- 存儲 彈出框是香港還是重慶廣東 -->
     <input type="hidden" id="lotteryType" name="lotteryType"/>
   </s:form>
</div>
<!-- 總監赔率修改结束-->
</body>
</html>