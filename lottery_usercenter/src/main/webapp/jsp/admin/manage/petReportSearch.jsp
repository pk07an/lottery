<%@ page language="java"
	import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page"
	pageEncoding="UTF-8"%>
<%@page
	import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.PlayTypeUtils"%>
<%@page import="com.npc.lottery.member.entity.BillSearchVo"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->


<head>
<title>查詢設定</title>
<link href="${pageContext.request.contextPath}/css/admin/index.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	 <script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
</head>

<script>
	//格式化日期：yyyy-MM-dd      
	function formatDate(date) {
		var myyear = date.getFullYear();
		var mymonth = date.getMonth() + 1;
		var myweekday = date.getDate();

		if (mymonth < 10) {
			mymonth = "0" + mymonth;
		}
		if (myweekday < 10) {
			myweekday = "0" + myweekday;
		}
		return (myyear + "-" + mymonth + "-" + myweekday);
	}

	//获得某月的天数      
	function getMonthDays(myYear, myMonth) {
		var monthStartDate = new Date(myYear, myMonth, 1);
		var monthEndDate = new Date(myYear, myMonth + 1, 1);
		var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
		return days;
	}

	//当天
	function today() {
		var now = new Date();
		now = formatDate(now);

		document.statReportForm.createTime.value = now;
	}

	function checkData() {
		var bettingDateStart = document.statReportForm.bettingDateStart.value;
		var bettingDateEnd = document.statReportForm.bettingDateEnd.value;

		if (null == bettingDateStart || null == bettingDateEnd
				|| bettingDateStart.length == 0 || bettingDateEnd.length == 0) {
			alert("请輸入日期！");

			return false;
		}

		return true;

	}
	// 文本框只能輸入小數 、整數
	$("#vo.eduMin,#vo.eduMin").keyup(
			function() {
				var tmptxt = $(this).val();
				if ($(this).val() != "") {
					$(this).val(tmptxt.replace(/[^\d.]/g, ""));
					$(this).val($(this).val().replace(/^\./g, ""));
					$(this).val($(this).val().replace(/\.{2,}/g, "."));
					$(this).val(
							$(this).val().replace(".", "$#$")
									.replace(/\./g, "").replace("$#$", "."));
				}
			});
	function validate() {
		var createTime = document.getElementById("createTime").value;
// 		var bettingDateEnd = document.getElementById("bettingDateEnd").value;
		var eduMin = document.getElementsByName("vo.eduMin")[0].value;
		var eduMax = document.getElementsByName("vo.eduMax")[0].value;
		var resMin = document.getElementsByName("vo.resMin")[0].value;
		var resMax = document.getElementsByName("vo.resMax")[0].value;
		// 			alert(eduMin+eduMax);

		if (createTime == "") {
			alert("時間不能為空 .");
			return false;
		}

		document.getElementById("statReportForm").submit();

	}
	function setTagDisabled(obj) {

		// 			alert(billType);
		if (obj == '0') {
			document.getElementsByName("vo.lotteryType")[0].disabled = true;
			document.getElementsByName("vo.lotteryType")[1].disabled = true;
			document.getElementsByName("vo.memberID")[0].disabled = true;
		} else {
			document.getElementsByName("vo.lotteryType")[0].disabled = false;
			document.getElementsByName("vo.lotteryType")[1].disabled = false;
			document.getElementsByName("vo.memberID")[0].disabled = false;
		}
	}

	function setPlayTypeList() {
		var type = "";
		if(document.getElementById("periodsNum").innerHTML!=""){
			type = $("#periodsNum").val().split("_")[0];
		}
		var radioBox = document.getElementsByName("vo.radioBox");
		var rad = 1;
		for(var i=0;i<radioBox.length;i++)
		{
			if(radioBox[i].checked){
				rad = radioBox[i].value;
				break;
			}
		}
		document.getElementById("playTypeAll").style.display="none";
		if (rad == 0) {
			$("#playTypeGD").hide();
			$("#playTypeCQ").hide()
			$("#playTypeBJ").hide();
// 			document.getElementById("playTypeAll").style.display="block";
			$("#playTypeJS").hide();
			$("#playTypeNC").hide();
			$("#playTypeAll").show();
			$("#playTypeAll").attr("name", "vo.playType");
			$("#playTypeGD").attr("name", "");
			$("#playTypeCQ").attr("name", "");
			$("#playTypeBJ").attr("name", "");
			$("#playTypeJS").attr("name", "");
			$("#playTypeNC").attr("name", "");
		} else if (type == 'GD') {
			$("#playTypeGD").show();
			$("#playTypeGD").attr("name", "vo.playType");
			$("#playTypeCQ").hide();
			$("#playTypeBJ").hide();
			$("#playTypeJS").hide();
			$("#playTypeNC").hide();
			$("#playTypeCQ").attr("name", "");
			$("#playTypeBJ").attr("name", "");
			$("#playTypeAll").attr("name", "");
			$("#playTypeJS").attr("name", "");
			$("#playTypeNC").attr("name", "");
		} else if (type == 'CQ') {
			$("#playTypeALL").hide();
			$("#playTypeGD").hide();
			$("#playTypeCQ").show();
			$("#playTypeBJ").hide();
			$("#playTypeJS").hide();
			$("#playTypeNC").hide();
			$("#playTypeCQ").attr("name", "vo.playType");
			$("#playTypeGD").attr("name", "");
			$("#playTypeBJ").attr("name", "");
			$("#playTypeAll").attr("name", "");
			$("#playTypeJS").attr("name", "");
			$("#playTypeNC").attr("name", "");
		} else if (type == 'BJ') {
			$("#playTypeALL").hide();
			$("#playTypeGD").hide();
			$("#playTypeCQ").hide();
			$("#playTypeJS").hide();
			$("#playTypeNC").hide();
			$("#playTypeBJ").show();
			$("#playTypeGD").attr("name", "");
			$("#playTypeCQ").attr("name", "");
			$("#playTypeBJ").attr("name", "vo.playType");
			$("#playTypeAll").attr("name", "");
			$("#playTypeJS").attr("name", "");
			$("#playTypeNC").attr("name", "");
		}else if (type == 'K3') {
			$("#playTypeALL").hide();
			$("#playTypeGD").hide();
			$("#playTypeCQ").hide();
			$("#playTypeBJ").hide();
			$("#playTypeNC").hide();
			$("#playTypeJS").show();
			$("#playTypeGD").attr("name", "");
			$("#playTypeCQ").attr("name", "");
			$("#playTypeBJ").attr("name", "");
			$("#playTypeAll").attr("name", "");
			$("#playTypeJS").attr("name", "vo.playType");
			$("#playTypeNC").attr("name", "");
		}else if (type == 'NC') {
			$("#playTypeALL").hide();
			$("#playTypeGD").hide();
			$("#playTypeCQ").hide();
			$("#playTypeBJ").hide();
			$("#playTypeJS").hide();
			$("#playTypeNC").show();
			$("#playTypeGD").attr("name", "");
			$("#playTypeCQ").attr("name", "");
			$("#playTypeBJ").attr("name", "");
			$("#playTypeAll").attr("name", "");
			$("#playTypeJS").attr("name", "");
			$("#playTypeNC").attr("name", "vo.playType");
		}
	}
	$(document).ready(function() {

		if ($("#createTime").val() == "") //  第一次 進入時為  空  今天時間 
		{
			today();
		}
		setPlayTypeList();
		//補貨注單 搜索 時 會員篩選、狀態 disabled
		// 		 var billType = document.getElementsByName("vo.billType")[1];
		// 			alert(billType);
		// 		if(billType.checked==true)
		// 		{
		// 			setTagDisabled(0);
		// 		}else{
		// 			setTagDisabled(1);
		// 		} 	 
	});
</script>
<body>

	<!--内容开始-->
	<div class="content">

		<s:form id="statReportForm" name="statReportForm" method="post" action="queryBillAdmin" theme="simple" >
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
									<td width="81"><strong>注單搜索</strong></td>
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
									<table width="100%" border="0" cellpadding="0" cellspacing="0"class="king autoHeight" id="betTable">
										<colgroup>
											<col width="15%" />
											<col width="75%" />
										</colgroup>
										<tr>
											<th colspan="2"> <b>搜索條件</b></th>
										</tr>

										<tr>
											<td class="t_right even"> 
												<input type="radio" onClick="setPlayTypeList()"  name="vo.radioBox" value="1" checked="checked" /> 按期數
											</td>
											<td class="t_left">
												<s:select id="periodsNum"  name="vo.periodsNum" list="#request.listPeriods" listKey="key" listValue="value"
												 onchange="setPlayTypeList()"  ></s:select>
											</td>
										</tr>

										<tr>
											<td class="t_right even">
												<input type="radio" onclick="setPlayTypeList()" name="vo.radioBox" value="0" <s:if test="vo.radioBox==0">checked="checked"</s:if> /> 
												按日期
											</td>
											<td class="t_left"><s:textfield
													name="vo.createTime" id="createTime"
													class="b_g" size="12" maxlength="12"													
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});"
													readonly="true"></s:textfield> 
												</td>
										</tr>
										<tr>
											<td class="t_right even">下注類型</td>
											<td class="t_left">
												<s:select id="playTypeGD"
													value="vo.playType" style="width:135px"
													list="#request.playTypeMapGD" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
												<s:select id="playTypeCQ" value="vo.playType"
													style="display:none;width:135px"
													list="#request.playTypeMapCQ" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
												<s:select id="playTypeBJ" value="vo.playType"
													style="display:none;width:135px"
													list="#request.playTypeMapBJ" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
													<!-- add by peter for K3 -->
												<s:select id="playTypeJS" value="vo.playType"
													style="display:none;width:135px"
													list="#request.playTypeMapJS" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
													<!-- add by peter for NC -->
												<s:select id="playTypeNC" value="vo.playType"
													style="display:none;width:135px"
													list="#request.playTypeMapNC" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
												<s:select id="playTypeAll" value="vo.playType"
													style="display:none;width:135px"
													list="#request.playTypeMapAll" listKey="key"
													listValue="value" headerKey="" headerValue="--- 全部 ---"></s:select>
											</td>
										</tr>
										<tr>
											<td class="t_right even">注單號碼</td>
											<td class="t_left">
												<p class="red">
													<s:textarea name="vo.orderNum" rows="4"
														cssStyle="width:280px"></s:textarea>
													唯一條件(多個以","分開)
												</p>
											</td>
										</tr>

										<tr>
											<td class="t_right even">注單種類</td>
											<td class="t_left">
												<s:select 
												 	name="vo.billType"
													list="#{'':'--全部--','1':'會員注單','0':'補貨注單'}"
													theme="simple"></s:select>
											</td>
<!-- 											<td class="t_left"><input type="radio" -->
<!-- 												name="vo.billType" value="1" checked="checked" />會員注單 <input -->
<!-- 												type="radio" name="vo.billType" value="0" -->
<!-- 												<s:if test="vo.billType==0">checked="checked"</s:if> />補貨注單</td> -->
										</tr>
										<tr>
											<td class="t_right even">狀態</td>
											<td class="t_left">
												<s:select 
												 	name="vo.winState"
													list="#{'':'--全部--','0':'正常','4':'註銷'}"
													theme="simple"></s:select>
											</td>
<!-- 											<td class="t_left"><input type="radio" -->
<!-- 												name="vo.lotteryType" value="1" checked="checked" />未結算&nbsp;&nbsp;&nbsp; -->
<!-- 												<input type="radio" name="vo.lotteryType" value="0" -->
<!-- 												<s:if test="vo.lotteryType==0">checked="checked"</s:if> />已結算</td> -->
										</tr>
										<tr>
											<td class="t_right even">分級篩選</td>
											<td class="t_left">
											<s:select name="vo.userType"
													list="#{'1':'會員','6':'代理','5':'總代理','4':'股東','3':'分公司'}"
													theme="simple"></s:select>
											<s:textfield name="vo.memberName"
													size="15" maxlength="10" /></td>
										</tr>
										<tr>
											<td class="t_right even">注額範圍</td>
											<td class="t_left"><s:textfield name="vo.eduMin"
													maxlength="9" class="b_g" />-<s:textfield name="vo.eduMax"
													maxlength="9" class="b_g" /></td>
										</tr>
										<tr>
											<td class="t_right even">結果範圍</td>
											<td class="t_left"><s:textfield name="vo.resMin"
													maxlength="9" class="b_g" />-<s:textfield name="vo.resMax"
													maxlength="9" class="b_g" /></td>
										</tr>

									</table>
								<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
							</tr>
						</table></td>

				</tr>


				<%-- <input type="hidden" id="pageNoCancel"
					value="<s:property value="#request.page.pageNo"/>" />
				 --%>
				<tr>
					<td height="35"
						background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table
							width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12" height="35"><img
									src="${pageContext.request.contextPath}/images/admin/tab_18.gif"
									width="12" height="35" /></td>
								<td align='center'><input type="button"
									onClick="validate();" class="btn2" value="确 定" /></td>
								<td width="16"><img
									src="${pageContext.request.contextPath}/images/admin/tab_20.gif"
									width="16" height="35" /></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</s:form>
<%-- 			<jsp:include page="/jsp/member/playDetailBill.jsp" /> --%>
	</div>
	<!--内容結束-->
	<script type="text/javascript">
		
	</script>
</body>
