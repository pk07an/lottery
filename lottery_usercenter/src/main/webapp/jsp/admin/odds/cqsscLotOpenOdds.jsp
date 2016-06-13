<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/index.css">
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/public.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/drag.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/adminTemple.js"></script>
<script language="JavaScript">  
$(document).ready(function() {
	$(".king tr td input").keyup(function(){     
	    var tmptxt=$(this).val();     
	   if($(this).val() != ""){
		   $(this).val(tmptxt.replace(/[^\d.|^-]/g,""));
	   }
	   
	}).bind("paste",function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/[^\d.|^-]/g,""));     
	}).css("ime-mode", "disabled");
	
	$(".king tr td input").blur(function(){ 
	    var tmptxt=$(this).val();     
	   if($(this).val() == ""){
		   $(this).val("0");
	   }
	   });
});
</script>
<body>
<div class="content">
  <s:form action="updateCqOpenOdds.action" namespace="admin">
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
									<td width="81"><strong>賠率設定</strong></td>
								</tr>
							</table>
							<table id="Tab_top_right" class="ssgd_r" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="56"><select name=""
										onChange="forwardToLotteryOpenOdds(this.options[this.options.selectedIndex].value)">
											<option value="GDKLSF"
												<s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if>>廣東快樂十分</option>
											<option value="CQSSC"
												<s:if test="subType.indexOf('CQSSC')>-1">selected</s:if>>重慶時時彩</option>
											<option value="BJSC"
											    <s:if test="subType.indexOf('BJSC')>-1">selected</s:if>>北京賽車(PK10)</option>
											<option value="K3"
											    <s:if test="subType.indexOf('K3')>-1">selected</s:if>>江苏骰寶(快3)</option>
											<option value="NC"
											    <s:if test="subType.indexOf('NC')>-1">selected</s:if>>幸运农场</option>         
									</select></td>
									<td width="16" align="right"><img
										src="${pageContext.request.contextPath}/images/admin/tab_07.gif"
										width="16" height="30" /></td>
								</tr>
							</table>
						</div></td>
				</tr>
     <tr>

		<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
					<td align="center">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" id="betTable">
	  <tr>
	    <th width="5%">下注類型</th>
	    <th width="13%">開盘賠率(A盘)</th>
	    <th width="6%">A盘上限</th>
	    <th width="8%">B盘賠率（降）</th>
	    <th width="8%">C盘賠率（降）</th>
	    <th width="13%"><span class="blue">自動降賠率額度</span><span class="red">（實貨）</span></th>
	    <th width="8%"><span class="blue">每次降賠率</span></th>
	    </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>第一球</td>
	    <td><input name="openOddsList[0].openingOdds" value="<s:property value='openOddsList[0].openingOdds'/>" size="6" maxlength="9" class="b_g " style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[0].bigestOdds" value="<s:property value='openOddsList[0].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[0].cutOddsB" value="<s:property value='openOddsList[0].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[0].cutOddsC" value="<s:property value='openOddsList[0].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[0].autoOddsQuotas" value="<s:property value='openOddsList[0].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g" style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[0].autoOdds" value="<s:property value='openOddsList[0].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>第二球</td>
	    <td><input name="openOddsList[1].openingOdds" value="<s:property value='openOddsList[1].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[1].bigestOdds" value="<s:property value='openOddsList[1].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[1].cutOddsB" value="<s:property value='openOddsList[1].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[1].cutOddsC" value="<s:property value='openOddsList[1].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[1].autoOddsQuotas" value="<s:property value='openOddsList[1].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g" style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[1].autoOdds" value="<s:property value='openOddsList[1].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>第三球</td>
	    <td><input name="openOddsList[2].openingOdds" value="<s:property value='openOddsList[2].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[2].bigestOdds" value="<s:property value='openOddsList[2].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[2].cutOddsB" value="<s:property value='openOddsList[2].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[2].cutOddsC" value="<s:property value='openOddsList[2].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[2].autoOddsQuotas" value="<s:property value='openOddsList[2].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g" style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[2].autoOdds" value="<s:property value='openOddsList[2].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>第四球</td>
	    <td><input name="openOddsList[3].openingOdds" value="<s:property value='openOddsList[3].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[3].bigestOdds" value="<s:property value='openOddsList[3].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[3].cutOddsB" value="<s:property value='openOddsList[3].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[3].cutOddsC" value="<s:property value='openOddsList[3].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[3].autoOddsQuotas" value="<s:property value='openOddsList[3].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g" style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[3].autoOdds" value="<s:property value='openOddsList[3].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>第五球</td>
	    <td><input name="openOddsList[4].openingOdds" value="<s:property value='openOddsList[4].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[4].bigestOdds" value="<s:property value='openOddsList[4].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[4].cutOddsB" value="<s:property value='openOddsList[4].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[4].cutOddsC" value="<s:property value='openOddsList[4].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[4].autoOddsQuotas" value="<s:property value='openOddsList[4].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[4].autoOdds" value="<s:property value='openOddsList[4].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>1-5大小</td>
	    <td><input name="openOddsList[5].openingOdds" value="<s:property value='openOddsList[5].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[5].bigestOdds" value="<s:property value='openOddsList[5].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[5].cutOddsB" value="<s:property value='openOddsList[5].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[5].cutOddsC" value="<s:property value='openOddsList[5].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[5].autoOddsQuotas" value="<s:property value='openOddsList[5].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[5].autoOdds" value="<s:property value='openOddsList[5].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>1-5單雙</td>
	    <td><input name="openOddsList[6].openingOdds" value="<s:property value='openOddsList[6].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[6].bigestOdds" value="<s:property value='openOddsList[6].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[6].cutOddsB" value="<s:property value='openOddsList[6].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[6].cutOddsC" value="<s:property value='openOddsList[6].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[6].autoOddsQuotas" value="<s:property value='openOddsList[6].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[6].autoOdds" value="<s:property value='openOddsList[6].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>總和大小</td>
	    <td><input name="openOddsList[7].openingOdds" value="<s:property value='openOddsList[7].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[7].bigestOdds" value="<s:property value='openOddsList[7].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[7].cutOddsB" value="<s:property value='openOddsList[7].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[7].cutOddsC" value="<s:property value='openOddsList[7].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[7].autoOddsQuotas" value="<s:property value='openOddsList[7].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[7].autoOdds" value="<s:property value='openOddsList[7].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>總和單雙</td>
	    <td><input name="openOddsList[8].openingOdds" value="<s:property value='openOddsList[8].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[8].bigestOdds" value="<s:property value='openOddsList[8].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[8].cutOddsB" value="<s:property value='openOddsList[8].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[8].cutOddsC" value="<s:property value='openOddsList[8].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[8].autoOddsQuotas" value="<s:property value='openOddsList[8].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[8].autoOdds" value="<s:property value='openOddsList[8].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td>龍虎和</td>
	    <td><input name="openOddsList[9].openingOdds" value="<s:property value='openOddsList[9].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      和 
	      <input name="openOddsList[10].openingOdds" value="<s:property value='openOddsList[10].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input></td>
	    <td><input name="openOddsList[9].bigestOdds" value="<s:property value='openOddsList[9].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[9].cutOddsB" value="<s:property value='openOddsList[9].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[9].cutOddsC" value="<s:property value='openOddsList[9].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[9].autoOddsQuotas" value="<s:property value='openOddsList[9].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[9].autoOdds" value="<s:property value='openOddsList[9].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td class="even">前三</td>
	    <td rowspan="3">
	      <p>豹子
	         <input name="openOddsList[11].openingOdds" value="<s:property value='openOddsList[11].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      </p>
	      <p>順子
	        <input name="openOddsList[12].openingOdds" value="<s:property value='openOddsList[12].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      </p>
	      <p>對子
	        <input name="openOddsList[13].openingOdds" value="<s:property value='openOddsList[13].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      </p>
	      <p>半順 
	        <input name="openOddsList[14].openingOdds" value="<s:property value='openOddsList[14].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      </p>
	      <p>雜六
	        <input name="openOddsList[15].openingOdds" value="<s:property value='openOddsList[15].openingOdds'/>" size="6" maxlength="9" class="b_g" style="color:#FF0034"></input>
	      </p></td>
	    <td><input name="openOddsList[11].bigestOdds" value="<s:property value='openOddsList[11].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[11].cutOddsB" value="<s:property value='openOddsList[11].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[11].cutOddsC" value="<s:property value='openOddsList[11].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[11].autoOddsQuotas" value="<s:property value='openOddsList[11].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[11].autoOdds" value="<s:property value='openOddsList[11].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td class="even">中三</td>
	    <td><input name="openOddsList[16].bigestOdds" value="<s:property value='openOddsList[16].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[16].cutOddsB" value="<s:property value='openOddsList[16].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[16].cutOddsC" value="<s:property value='openOddsList[16].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[16].autoOddsQuotas" value="<s:property value='openOddsList[16].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[16].autoOdds" value="<s:property value='openOddsList[16].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
	    <td class="even">后三</td>
	    <td><input name="openOddsList[17].bigestOdds" value="<s:property value='openOddsList[17].bigestOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	    <td><input name="openOddsList[17].cutOddsB" value="<s:property value='openOddsList[17].cutOddsB'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[17].cutOddsC" value="<s:property value='openOddsList[17].cutOddsC'/>" size="6" maxlength="9" class="b_g blue"></input></td>
	    <td><input name="openOddsList[17].autoOddsQuotas" value="<s:property value='openOddsList[17].autoOddsQuotas'/>" size="6" maxlength="9" class="b_g"  style="width:65px;" onKeyUp="onlyNumber(this);"></input></td>
	    <td><input name="openOddsList[17].autoOdds" value="<s:property value='openOddsList[17].autoOdds'/>" size="6" maxlength="9" class="b_g"></input></td>
	  </tr>
	  </table> 
									<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
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
