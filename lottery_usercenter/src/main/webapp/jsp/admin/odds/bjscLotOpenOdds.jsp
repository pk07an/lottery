<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	function changeValue(index){
		if(index==13 || index==15){
			$("input[name='openOddsList["+(index+1)+"].bigestOdds']").val($("input[name='openOddsList["+index+"].bigestOdds']").val());
			$("input[name='openOddsList["+(index+1)+"].cutOddsB']").val($("input[name='openOddsList["+index+"].cutOddsB']").val());
			$("input[name='openOddsList["+(index+1)+"].cutOddsC']").val($("input[name='openOddsList["+index+"].cutOddsC']").val());
			$("input[name='openOddsList["+(index+1)+"].autoOddsQuotas']").val($("input[name='openOddsList["+index+"].autoOddsQuotas']").val());
			$("input[name='openOddsList["+(index+1)+"].autoOdds']").val($("input[name='openOddsList["+index+"].autoOdds']").val());
		}else if(index==17){
			for(var i=1;i<=4;i++){
				$("input[name='openOddsList["+(index+i)+"].bigestOdds']").val($("input[name='openOddsList["+index+"].bigestOdds']").val());
				$("input[name='openOddsList["+(index+i)+"].cutOddsB']").val($("input[name='openOddsList["+index+"].cutOddsB']").val());
				$("input[name='openOddsList["+(index+i)+"].cutOddsC']").val($("input[name='openOddsList["+index+"].cutOddsC']").val());
				$("input[name='openOddsList["+(index+i)+"].autoOddsQuotas']").val($("input[name='openOddsList["+index+"].autoOddsQuotas']").val());
				$("input[name='openOddsList["+(index+i)+"].autoOdds']").val($("input[name='openOddsList["+index+"].autoOdds']").val());
			}
		}
	}
	
	function changeValueOnSubmit(){
		changeValue(13);
		changeValue(15);
		changeValue(17);
	}
</script>
<body>
	<div id="content">
		<s:form action="updateBjOpenOdds.action" namespace="admin" onsubmit="changeValueOnSubmit();">
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

					<td><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
								<td align="center">
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="king" id="betTable">
										<tr>
											<th width="8%">下注類型</th>
											<th width="10%">開盘賠率(A盘)</th>
											<th width="6%">A盘上限</th>
											<th width="8%">B盘賠率（降）</th>
											<th width="8%">C盘賠率（降）</th>
											<th width="13%"><span class="blue">自動降賠率額度</span><span
												class="red">（實貨）</span></th>
											<th width="8%"><span class="blue">每次降賠率</span></th>
										</tr>

										<s:iterator value="openOddsList" status="st">
											<tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
												<s:if test="oddsType.trim()=='BJ_BALL_FIRST'">
													<td>冠軍</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_SECOND'">
													<td>亞軍</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_THIRD'">
													<td>第三名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_FORTH'">
													<td>第四名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_FIFTH'">
													<td>第五名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_SIXTH'">
													<td>第六名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_SEVENTH'">
													<td>第七名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_EIGHTH'">
													<td>第八名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_NINTH'">
													<td>第九名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_BALL_TENTH'">
													<td>第十名</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_1-10_DX'">
													<td>1-10大小</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_1-10_DS'">
													<td>1-10單雙</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_1-5_LH'">
													<td>1-5龍虎</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_DOUBLESIDE_DA'">
													<td>冠亞軍和大小</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_DOUBLESIDE_DAN'">
													<td>冠亞軍和單雙</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_GROUP_ONE'">
													<td>冠亞軍和</td>
												</s:if>
												<s:if test="oddsType.trim()=='BJ_DOUBLESIDE_DA'">
													<td>大<input name="openOddsList[${st.index}].openingOdds"
														value="${openingOdds}" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>【44.444%中獎率】
														<br/>小<input name="openOddsList[${(st.index)+1}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+1].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>【55.556%中獎率】
													</td>
												</s:if>
												<s:elseif test="oddsType.trim()=='BJ_DOUBLESIDE_DAN'">
												    <td>
												    	單<input name="openOddsList[${st.index}].openingOdds" value="${openingOdds}" size="6" maxlength="9" class="b_g" style="color:#FF0034"/>【55.556%中獎率】
												    	<br/>雙<input name="openOddsList[${(st.index)+1}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+1].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>【44.444%中獎率】
												    </td>
												</s:elseif>
												<s:elseif test="oddsType.trim()=='BJ_GROUP_ONE'">
												    <td>
												    	3、4、18、19<input name="openOddsList[${st.index}].openingOdds" value="${openingOdds}" size="6" maxlength="9" class="b_g" style="color:#FF0034"/>
												    	<br/>
												    	5、6、16、17<input name="openOddsList[${(st.index)+1}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+1].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>
												    	<br/>
												    	7、8、14、15<input name="openOddsList[${(st.index)+2}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+2].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>
												    	<br/>
												    	9、10、12、13<input name="openOddsList[${(st.index)+3}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+3].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>
														<br/>
												    	11<input name="openOddsList[${(st.index)+4}].openingOdds"
														value="<s:property value="%{openOddsList[#st.index+4].openingOdds}"/>" size="6" maxlength="9"
														class="b_g" style="color:#FF0034"/>
												    </td>
												</s:elseif><s:else>
												    <s:if test="oddsType.trim()!='BJ_DOUBLESIDE_X' && oddsType.trim()!='BJ_DOUBLESIDE_S' && oddsType.trim()!='BJ_GROUP_ONE'
												     			&& oddsType.trim()!='BJ_GROUP_TWO' && oddsType.trim()!='BJ_GROUP_THREE' 
												     			&& oddsType.trim()!='BJ_GROUP_FOUR' && oddsType.trim()!='BJ_GROUP_FIVE'">
													    <td><input name="openOddsList[${st.index}].openingOdds"
															value="${openingOdds}" size="6" maxlength="9"
															class="b_g" style="color:#FF0034"/>
														</td>
													</s:if>
												</s:else>
												
												<s:if test="oddsType.trim()!='BJ_DOUBLESIDE_X' && oddsType.trim()!='BJ_DOUBLESIDE_S' 
												           && oddsType.trim()!='BJ_GROUP_TWO' && oddsType.trim()!='BJ_GROUP_THREE'
												           && oddsType.trim()!='BJ_GROUP_FOUR' && oddsType.trim()!='BJ_GROUP_FIVE'">
													<td><input name="openOddsList[${st.index}].bigestOdds" value="${bigestOdds}" size="6" maxlength="9" class="b_g" onchange="changeValue(${st.index})"/></td>
													<td><input name="openOddsList[${st.index}].cutOddsB" value="${cutOddsB}" size="6" maxlength="9" class="b_g blue" onchange="changeValue(${st.index})"/></td>
													<td><input name="openOddsList[${st.index}].cutOddsC" value="${cutOddsC}" size="6" maxlength="9" class="b_g blue" onchange="changeValue(${st.index})"/></td>
													<td><input name="openOddsList[${st.index}].autoOddsQuotas" value="${autoOddsQuotas}" size="6" maxlength="9"  style="width:65px;" class="b_g" onkeyup="onlyNumber(this);" onchange="changeValue(${st.index})"/></td>
													<td><input name="openOddsList[${st.index}].autoOdds" value="${autoOdds}" size="6" maxlength="9" class="b_g" onchange="changeValue(${st.index})"/>
														<input name="openOddsList[${st.index}].oddsType" value="${oddsType}" type="hidden"/> 
														<input name="openOddsList[${st.index}].shopsCode" value="${shopsCode}" type="hidden"/> 
														<input name="openOddsList[${st.index}].openingUpdateDate" value="${openingUpdateDate}" type="hidden"/>
													    <input name="openOddsList[${st.index}].openingUpdateUser" value="${openingUpdateUser}" type="hidden"/> 
													    <input name="openOddsList[${st.index}].createUser" value="${createUser}" type="hidden"/> 
													    <input name="openOddsList[${st.index}].createTime" value="${createTime}" type="hidden"/> 
													    <input name="openOddsList[${st.index}].ID" value="${ID}" type="hidden"/>
													</td>
												</s:if><s:else>
													<input name="openOddsList[${st.index}].bigestOdds" value="${bigestOdds}" type="hidden"/>
													<input name="openOddsList[${st.index}].cutOddsB" value="${cutOddsB}" type="hidden"/>
													<input name="openOddsList[${st.index}].cutOddsC" value="${cutOddsC}" type="hidden"/>
													<input name="openOddsList[${st.index}].autoOddsQuotas" value="${autoOddsQuotas}" type="hidden"/>
													<input name="openOddsList[${st.index}].autoOdds" value="${autoOdds}" type="hidden"/>
													<input name="openOddsList[${st.index}].oddsType" value="${oddsType}" type="hidden"/> 
													<input name="openOddsList[${st.index}].shopsCode" value="${shopsCode}" type="hidden"/> 
													<input name="openOddsList[${st.index}].openingUpdateDate" value="${openingUpdateDate}" type="hidden"/> 
													<input name="openOddsList[${st.index}].openingUpdateUser" value="${openingUpdateUser}" type="hidden"/> 
													<input name="openOddsList[${st.index}].createUser" value="${createUser}" type="hidden"/> 
													<input name="openOddsList[${st.index}].createTime" value="${createTime}" type="hidden"/> 
													<input name="openOddsList[${st.index}].ID" value="${ID}" type="hidden"/>
												</s:else>
											</tr>
										</s:iterator>
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
