<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/public.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/adminTemple.js"></script>
<script language="JavaScript">  
$(document).ready(function() {
	$("#betTable tr td input").keyup(function(){     
	    var tmptxt=$(this).val();     
	   if($(this).val() != ""){
		   $(this).val(tmptxt.replace(/[^\d]/g,""));
	   }
	   
	}).bind("paste",function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/[^\d]/g,""));     
	}).css("ime-mode", "disabled");
	
	$("#betTable tr td input").blur(function(){ 
	    var tmptxt=$(this).val();     
	   if($(this).val() == ""){
		   $(this).val("0");
	   }
	   });
	
	});
</script>
<body>
<div class="content">
  <s:form action="updateBjTradingSet.action" namespace="admin">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!--控制表格头部开始-->
				<tr>
					<td height="30"
						background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><div
							id="Tab_top">
							<table border="0" cellpadding="0" cellspacing="0" class="ssgd_l"
								id="Tab_top_left">
								<tr>
									<td width="14" height="30"><img
										src="${pageContext.request.contextPath}/images/admin/tab_03.gif"
										width="12" height="30" /></td>
									<td width="24" align="left"><img
										src="${pageContext.request.contextPath}/images/admin/tb.gif"
										width="16" height="16" /></td>
									<td width="81"><strong>交易設定</strong></td>
								</tr>
							</table>
							<table id="Tab_top_right" class="ssgd_r" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="56">
										  <select name="" class="fr" onChange="forwardToTradingSet(this.options[this.options.selectedIndex].value)">
										      <option value="GDKLSF" <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >廣東快樂十分</option>
									          <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >重慶時時彩</option>
									          <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >北京赛车(PK10)</option>
									          <option value="K3"  <s:if test="subType.indexOf('K3')>-1">selected</s:if> >江苏骰寶(快3)</option>
									          <option value="NC"  <s:if test="subType.indexOf('NC')>-1">selected</s:if> >幸运农场</option>
									      </select>
		     						 </td>
									<td width="16" align="right"><img
										src="${pageContext.request.contextPath}/images/admin/tab_07.gif"
										width="16" height="30" /></td>
								</tr>
							</table>
						</div></td>
				</tr>
				
  <tr>

	<td><table width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="8"
					background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
				<td align="center">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="king" id="betTable">
	  <tr>
	    <th width="9%">下注類型</th>
	    <th width="11%">公司總受注額<strong class="red">(實貨)</strong></th>
	    <th width="9%">單注最低</th>
	    <th width="9%">單注最高</th>
	    <th width="9%">單號限額</th>
	    <th width="9%">最高派彩</th>	    
		<th width="9%"><span class="blue">負值超額警示</span></th>
	  </tr>
    
      <s:iterator value="showList" status="st">
            <tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''"><!-- 共26项 -->	
	        <s:if test="playFinalType.trim()=='BJ_BALL_FIRST'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_SECOND'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_THIRD'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_FORTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_FIFTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_SIXTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_SEVENTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_EIGHTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_NINTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_BALL_TENTH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_1-10_DX'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_1-10_DS'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_1-5_LH'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_DOUBLSIDE_DX'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_DOUBLSIDE_DS'"><td>${playFinalTypeName}</td></s:if>
		    <s:if test="playFinalType.trim()=='BJ_GROUP'"><td>${playFinalTypeName}</td></s:if>
		    
	        <td><input name="showList[${st.index}].totalQuatas" value="${totalQuatas}" size="10" maxlength="9" class="b_g"></input></td>
		    <td><input name="showList[${st.index}].lowestQuatas" value="${lowestQuatas}" size="6" maxlength="9" class="b_g"></input></td>
		    <td><input name="showList[${st.index}].bettingQuotas" value="${bettingQuotas}" size="10" maxlength="9" class="b_g"></input></td>
		    <td><input name="showList[${st.index}].itemQuotas" value="${itemQuotas}" size="10" maxlength="9" class="b_g"></input></td>
		    <td><input name="showList[${st.index}].winQuatas" value="${winQuatas}" size="10" maxlength="9" class="b_g"></input></td>
		    <input name="showList[${st.index}].commissionA" value="${commissionA}" type="hidden"/>
		    <input name="showList[${st.index}].commissionB" value="${commissionB}" type="hidden"/>
		    <input name="showList[${st.index}].commissionC" value="${commissionC}" type="hidden"/>
		    <td>
		        -<input name="showList[${st.index}].loseQuatas" value="${loseQuatas}" size="10" maxlength="9" class="b_g" style="color:#FF0034"></input>
		        <input name="showList[${st.index}].userId" value="${userId}" type="hidden"></input>
		        <input name="showList[${st.index}].userType" value="${userType}" type="hidden"></input>
		        <input name="showList[${st.index}].playType" value="${playType}" type="hidden"></input>
		        <input name="showList[${st.index}].playFinalType" value="${playFinalType}" type="hidden"></input>
		        <input name="showList[${st.index}].createUser" value="${createUser}" type="hidden"></input>
		        <input name="showList[${st.index}].createTime" value="${createTime}" type="hidden"></input>
		        <input name="showList[${st.index}].ID" value="${ID}" type="hidden"></input>
		    </td>
		    </tr>
      </s:iterator>
  </table>
  <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
							</tr>
						</table></td>

				</tr>
				<tr>
					<td height="35"
						background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table
							width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12" height="35"><img
									src="${pageContext.request.contextPath}/images/admin/tab_18.gif"
									width="12" height="35" /></td>
								<td align='center'><input name="" type="submit"
									class="btn2" value="保存" onclick="return confirm('确定保存修改?')"/> <input name="" type="reset"
									class="btn2" value="取消" /></td>
								<td width="16"><img
									src="${pageContext.request.contextPath}/images/admin/tab_20.gif"
									width="16" height="35" /></td>
							</tr>
						</table></td>
				</tr>
			</table>
  </s:form>
</div>
</body>
</html>
