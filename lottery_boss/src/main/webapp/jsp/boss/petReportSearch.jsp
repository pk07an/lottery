<%@ page language="java" import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page"  pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.PlayTypeUtils"%>
<%@page import="com.npc.lottery.member.entity.BillSearchVo"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->


<head>
<title>查詢設定</title>
<link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" type="text/css" />
</head>

<script>
	
	//格式化日期：yyyy-MM-dd      
	function formatDate(date) {       
	    var myyear = date.getFullYear();      
	    var mymonth = date.getMonth()+1;      
	    var myweekday = date.getDate();       
	          
	    if(mymonth < 10){      
	        mymonth = "0" + mymonth;      
	    }       
	    if(myweekday < 10){      
	        myweekday = "0" + myweekday;      
	    }      
	    return (myyear+"-"+mymonth + "-" + myweekday);       
	}     
	
	//获得某月的天数      
	function getMonthDays(myYear, myMonth){
	    var monthStartDate = new Date(myYear, myMonth, 1);       
	    var monthEndDate = new Date(myYear, myMonth + 1, 1);       
	    var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);       
	    return days;       
	}    
	
	//当天
	function today(){
		var now=new Date();
		now = formatDate(now); 
		
		document.statReportForm.bettingDateStart.value = now;
		document.statReportForm.bettingDateEnd.value = now;
	}
	
	//昨天
	function yesterday(){
		var yest = new Date();
		yest.setDate(yest.getDate()-1);
		
		yest = formatDate(yest);
		
		document.statReportForm.bettingDateStart.value = yest;
		document.statReportForm.bettingDateEnd.value = yest;
	}
	
	//本周
	function thisWeek(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);       
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek +1));       
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.statReportForm.bettingDateStart.value = weekStartDate;
	    document.statReportForm.bettingDateEnd.value = weekEndDate;
	}
	
	//上周
	function lastWeek(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 6);       
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 6));       
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.statReportForm.bettingDateStart.value = weekStartDate;
	    document.statReportForm.bettingDateEnd.value = weekEndDate;
	}
	
	//本月
	function thisMonth(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;

		var monthStartDate = new Date(nowYear, nowMonth, 1);       
		monthStartDate = formatDate(monthStartDate);
		
	    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear, nowMonth));       
	    monthEndDate = formatDate(monthEndDate); 
	    
	    document.statReportForm.bettingDateStart.value = monthStartDate;
	    document.statReportForm.bettingDateEnd.value = monthEndDate;
	}
	
	//上月
	function lastMonth(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;

		var monthStartDate = new Date(nowYear, nowMonth - 1, 1);       
		monthStartDate = formatDate(monthStartDate);
		
	    var monthEndDate = new Date(nowYear, nowMonth - 1, getMonthDays(nowYear, nowMonth - 1));       
	    monthEndDate = formatDate(monthEndDate); 
	    
	    document.statReportForm.bettingDateStart.value = monthStartDate;
	    document.statReportForm.bettingDateEnd.value = monthEndDate;
	}
	
	function checkData(){
		var bettingDateStart = document.statReportForm.bettingDateStart.value;
		var bettingDateEnd = document.statReportForm.bettingDateEnd.value;
	    
		if(null == bettingDateStart || null == bettingDateEnd || bettingDateStart.length == 0 || bettingDateEnd.length == 0){
			alert("请輸入日期！");
			
			return false;
		}
		
		return true;
		
	}
	// 文本框只能輸入小數 、整數
	 $("#vo.eduMin,#vo.eduMin").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(/[^\d.]/g,""));
		        $(this).val($(this).val().replace(/^\./g,""));  
		       $(this).val($(this).val().replace(/\.{2,}/g,"."));  
		       $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	       }
	 });
	 function validate()
		{
			var bettingDateStart = document.getElementById("bettingDateStart").value;
			var bettingDateEnd = document.getElementById("bettingDateEnd").value;
			var eduMin = document.getElementsByName("vo.eduMin")[0].value;
			var eduMax = document.getElementsByName("vo.eduMax")[0].value;
			var resMin = document.getElementsByName("vo.resMin")[0].value;
			var resMax = document.getElementsByName("vo.resMax")[0].value;
// 			alert(eduMin+eduMax);

			if(bettingDateStart=="" || bettingDateEnd=="" )
			{
				alert("時間不能為空 .");
				return false;
			}
			if(eduMin>0 && eduMax>0 || (""==eduMax && eduMin==""))
			{
			}else{
				alert("注額範圍輸入錯誤.");
				return false;
			}
			
			if(resMin>0 && resMax>0 || (""==resMin && resMax==""))
			{
			}else{
				alert("結果範圍輸入錯誤.");
				return false;
			}
			
			document.getElementById("statReportForm").submit();
			
		}
	 function setTagDisabled(obj)
		{
			
// 			alert(billType);
			if(obj=='0'){
				document.getElementsByName("vo.lotteryType")[0].disabled=true;
				document.getElementsByName("vo.lotteryType")[1].disabled=true;
				document.getElementsByName("vo.memberID")[0].disabled=true;
			}else{
				document.getElementsByName("vo.lotteryType")[0].disabled=false;
				document.getElementsByName("vo.lotteryType")[1].disabled=false;
				document.getElementsByName("vo.memberID")[0].disabled=false;
			}
		}
	
	 function setPlayTypeList()
		{
			var type = $("#subType").val();
			if(type=='ALL'){
				
			}else if(type=='GDKLSF'){
				$("#playTypeGD").show();
				$("#playTypeGD").attr("name","vo.playType");
				$("#playTypeCQ").hide();
				$("#playTypeBJ").hide();
				$("#playTypeCQ").attr("name","");
				$("#playTypeBJ").attr("name","");
			}else if(type=='CQSSC'){
				$("#playTypeGD").hide();
				$("#playTypeCQ").show();
				$("#playTypeBJ").hide();
				$("#playTypeCQ").attr("name","vo.playType");
				$("#playTypeGD").attr("name","");
				$("#playTypeBJ").attr("name","");
			}else if(type=='BJSC'){
				$("#playTypeGD").hide();
				$("#playTypeCQ").hide();
				$("#playTypeBJ").show();
				$("#playTypeGD").attr("name","");
				$("#playTypeCQ").attr("name","");
				$("#playTypeBJ").attr("name","vo.playType");
			} 
		}
 $(document).ready(function(){
		 
	 if($("#bettingDateStart").val()=="") //  第一次 進入時為  空  今天時間 
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  
  <s:form id="statReportForm" name="statReportForm" method="post" action="queryBill">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
    <colgroup>
      <col width="15%" />
      <col width="75%" />
    </colgroup>
    <tr>
      <th colspan="2">注單搜索查詢</th>
    </tr>
    
    <tr>
		<td class="t_right even">彩票種類</td>
		<td class="t_left">
			<s:select onchange="setPlayTypeList()" name="vo.subType" id="subType" list="#{'GDKLSF':'廣東快樂十分','CQSSC':'重慶時時彩','BJSC':'北京赛车'}" theme="simple"></s:select>
<%-- 			<s:select onchange="setPlayTypeList()" name="vo.subType" id="subType" list="#{'HKLHC':'香港六合彩'}" theme="simple"></s:select> --%>
		</td>
	</tr>
    
    <tr>
		<td class="t_right even" >下注類型</td>
		<td class="t_left">

			<s:select id="playTypeGD" value="vo.playType" style="width:135px" list="#request.playTypeMapGD" listKey="key" listValue="value" headerKey="" headerValue="--- 所有類型 ---"></s:select>
			<s:select id="playTypeCQ"  value="vo.playType" style="display:none;width:135px" list="#request.playTypeMapCQ" listKey="key" listValue="value" headerKey="" headerValue="--- 所有類型 ---"></s:select>
			<s:select id="playTypeBJ"  value="vo.playType" style="display:none;width:135px" list="#request.playTypeMapBJ" listKey="key" listValue="value" headerKey="" headerValue="--- 所有類型 ---"></s:select>
<%-- 			<s:select id="playTypeHK"  value="vo.playType" style="display:none;width:135px" list="#request.playTypeMapHK" listKey="key" listValue="value" headerKey="" headerValue="--- 所有類型 ---"></s:select> --%>
<%-- 			<s:select id="playTypeAll" list="#request.playTypeList" listKey="commissionType" listValue="commissionTypeName2" headerKey="" headerValue="--- 所有類型 ---"></s:select> --%>
		</td>
    </tr>
    
    <tr>
		<td class="t_right even">
			按日期</td>
		<td class="t_left">
			<s:textfield name="vo.bettingDateStart" id="bettingDateStart" class="b_g" size="8" maxlength="12" style="background: transparent;border:1px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});" readonly="true"></s:textfield>
      		- <s:textfield name="vo.bettingDateEnd" id="bettingDateEnd" class="b_g mr10"   size="8" maxlength="12" style="background: transparent;border:1px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});" ></s:textfield>
      		<input type="button" name="button" id="button" value="今天" class="btn td_btn" onClick="today()"/>
      		<input type="button" name="button" id="button" value="昨天" class="btn td_btn" onClick="yesterday()"/>
      		<input type="button" name="button" id="button" value="本星期" class="btn td_btn" onClick="thisWeek()"/>
      		<input type="button" name="button" id="button" value="上星期" class="btn td_btn" onClick="lastWeek()"/>
      		<input type="button" name="button" id="button" value="本月" class="btn td_btn" onClick="thisMonth()"/>
      		<input type="button" name="button" id="button" value="上月" class="btn td_btn" onClick="lastMonth()"/>
      </td>
    </tr>
    <tr>
      	<td class="t_right even">注單號碼</td>
      	<td class="t_left">
      		<p class="green"> <s:textarea name="vo.orderNum" rows="2" cssStyle="width:250px" ></s:textarea>唯一條件(多個以","分開)</p>
 		</td>
    </tr>
    
    <tr>
      <td class="t_right even">狀態</td>
      <td class="t_left">
      <input type="radio" name="vo.lotteryType" value="1"  checked="checked" />未結算&nbsp;&nbsp;&nbsp;
      <input type="radio" name="vo.lotteryType" value="0" <s:if test="vo.lotteryType==0">checked="checked"</s:if> />已結算
      
      </td>
    </tr>
    <tr>
      <td class="t_right even">注單類型</td>
      <td class="t_left">
      <input type="radio" name="vo.billType"   value="1"  checked="checked" />會員注單
      <input type="radio" name="vo.billType"  value="0" <s:if test="vo.billType==0">checked="checked"</s:if> />補貨注單
      
      </td>
    </tr>
    <tr>
      <td class="t_right even">會員篩選</td>
      <td class="t_left"> 
      <s:textfield name="vo.memberID"   size="15" maxlength="10" />會員號
       </td>
    </tr>
    <tr>
      <td class="t_right even">注額範圍</td>
      <td class="t_left"><s:textfield name="vo.eduMin"  maxlength="9" class="b_g"/>-<s:textfield name="vo.eduMax"  maxlength="9" class="b_g"/></td>
    </tr>
    <tr>
		<td class="t_right even">結果範圍</td>
      	<td class="t_left"><s:textfield name="vo.resMin" maxlength="9" class="b_g"/>-<s:textfield name="vo.resMax"  maxlength="9" class="b_g"/></td>
    </tr>
    
  </table>
  
  <div id="tj">
	<div class="tj"><span><input type="button" onClick="validate();" class="btn" value="确 定" /></span>&nbsp;&nbsp;<span><input name="" type="reset" class="btn" value="重 置" /></span></div>
  </div>
  
   <input type="hidden" id="pageNoCancel" value="<s:property value="#request.page.pageNo"/>"/>
   
</s:form>
<%--   <s:if test="vo.subType.indexOf('HKLHC')>-1"> --%>
<%-- 	 <jsp:include page="/jsp/member/hkPlayDetailBill.jsp" /> --%>
<%--   </s:if> --%>
<%--   <s:else> --%>
 	<jsp:include  page="/jsp/boss/playDetailBill.jsp" />
<%--   </s:else> --%>
</div>
<!--内容結束-->
<script type="text/javascript">


	
	
	
</script>
</body>
