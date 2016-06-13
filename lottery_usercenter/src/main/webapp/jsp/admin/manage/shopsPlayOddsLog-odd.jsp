<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.common.Constant"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/index.css" />
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				// 文本框只能輸入小數 、整數
				$("#periodsNum").keyup(
						function() {
							var tmptxt = $(this).val();
							if ($(this).val() != "") {
								$(this).val(tmptxt.replace(/[^\d.]/g, ""));
								$(this).val($(this).val().replace(/^\./g, ""));
								$(this).val(
										$(this).val().replace(/\.{2,}/g, "."));
								$(this).val(
										$(this).val().replace(".", "$#$")
												.replace(/\./g, "").replace(
														"$#$", "."));
							}
						});

			});
</script>
<div class="content">
		<s:form id="sform" method="post" action="playOddsLogAction">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!--控制表格头部开始-->
				<tr>
					<td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif">
							<table border="0" cellpadding="0" cellspacing="0" class="ssgd_l" id="Tab_top_left">
								<tr>
									<td width="14" height="30"><img
										src="${pageContext.request.contextPath}/images/admin/tab_03.gif"
										width="12" height="30" /></td>
									<td width="24" align="left"><img
										src="${pageContext.request.contextPath}/images/admin/tb.gif"
										width="16" height="16" /></td>
									<td width="81"><strong>操盤日誌</strong></td>
								</tr>
							</table>
							<table id="Tab_top_right" class="ssgd_r" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="56">
										<select id="areaLottery" name="areaLottery"
											onchange="changeAreaLottery()">
												<option value="GDKLSF"
													<s:if test="#request.areaLottery.indexOf('GDKLSF')>-1">selected</s:if>>廣東快樂十分</option>
												<option value="CQSSC"
													<s:if test="#request.areaLottery.indexOf('CQSSC')>-1">selected</s:if>>重慶時時彩</option>
												<option value="BJ"
													<s:if test="#request.areaLottery.indexOf('BJ')>-1">selected</s:if>>北京賽車(PK10)</option>
										</select>
										&nbsp;&nbsp;&nbsp;
									<td width="56">
										<s:select id="periodsNum" name="periodsNum" onchange="$('#sform').submit();"
													 style="width:135px"
													list="#request.periodsNumList" listKey="key"
													listValue="value"></s:select>
									</td>
									&nbsp;&nbsp;&nbsp;
									<td width="106">
										分類
										<s:select id="playType" name="playType" onchange="$('#sform').submit();"
													 style="width:135px"
													list="#request.playTypeMap" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
									</td>	
									<td width="16" align="right"><img
										src="${pageContext.request.contextPath}/images/admin/tab_07.gif"
										width="16" height="30" /></td>
								</tr>
							</table>
						</td>
				</tr>
				<tr>

					<td><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
								<td align="center">
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="king">
										<tbody>
											<tr>
												<th width="5%">類型</th>
												<th width="10%">操盤明細</th>
												<th width="15%">操盤手</th>
												<th width="10%">操盤時間</th>
												<th width="10%">IP</th>
												<th width="10%">IP歸屬</th>
											</tr>
											<%
												int count = 1;
											%>
											<s:iterator value="#request.page.result" status="branch">
												<tr onmouseover="this.style.backgroundColor='#FFFFA2'"
													onmouseout="this.style.backgroundColor=''">
													<td>
														<s:property value="oddsTypeNameLog" escape="false" />
													</td>
													<td><s:property value="playType.finalTypeName" escape="false" /> @ <span class="red"><s:property value="realOddsNew" escape="false" /></span>
													</td>
													<td><s:property value="realUpdateUserNewAccount.account" escape="false" /> 
													</td>
													<td><s:date name="realUpdateDateNew" format="yy-MM-dd HH:mm:ss" /></td>
													<s:if test="type==1">
													<td colspan="2" bgcolor="black">
														 額度到賠率自動降
													</td>
													</s:if>
													<s:else>
														<td><s:property value="ip" escape="false" /></td>
													<td>
														<s:property value="ip" escape="false" />
													</td>
													</s:else>
												</tr>
											</s:iterator>
									</table> <td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
							</tr>
						</table></td>

				</tr>
				<tr>
					<td height="35"
						background="${pageContext.request.contextPath}/images/admin/tab_19.gif">
							</td>
				</tr>
			</table>
  <s:if test="#request.page.totalCount>0">
	<s:if test="#request.page.totalCount>0">			      
		      <lottery:paginate url="${pageContext.request.contextPath}/admin/playOddsLogAction.action" param="periodsNum=${periodsNum}&areaLottery=${areaLottery}" />
		</s:if>
</s:if> 
</s:form>
<script type="text/javascript">
$(document).ready(function(){
	
// 	var playType = document.getElementById("playType");
// 	var playTypeHidden = $("#playTypeHidden").val();
// 	for(var i=0;i<playType.length;i++)
// 	{
		
// 		if(playType[i].)
// 	}
	
});

function changeAreaLottery()
{
	$("#playType").val("");
	$("#periodsNum").attr("value",'');
	$('#sform').submit();
}
</script>
</div>

