<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
			alert("请输入日期！");
			
			return false;
		}
		
		return true;
		
	}
</script>

<body>

<!--内容开始-->
<div class="content">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  
  <s:form name="statReportForm" action="/statreport/list.action">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king autoHeight">
    <colgroup>
      <col width="15%" />
      <col width="75%" />
    </colgroup>
    <tr>
      <th colspan="2">報表統計查詢</th>
    </tr>
    
    <tr>
		<td class="t_right even">彩票種類</td>
		<td class="t_left">
		  <select name="lotteryType">
				<option value=" ">--- 所有彩种 ---</option>
				<option value="GDKLSF" selected="selected">廣東快樂十分</option>
				<option value="CQSSC">重慶時時彩</option>
	      		<option value="HKLHC">香港六合彩</option>
			</select>
		</td>
	</tr>
    
    <tr>
		<td class="t_right even">下注類型</td>
		<td class="t_left">
			<%-- <s:select list="#request.playTypeList" name="playType" listKey="commissionType" listValue="commissionTypeName2" headerKey=" " headerValue="--- 所有類型 ---"></s:select> --%>
		</td>
    </tr>
    
    <tr>
		<td class="t_right even">
			<input type="radio" name="searchType" value="radiobutton" style="border:0px;background: transparent;"/>按期数
		</td>
      	<td class="t_left">
      		<select name="periodsNum" id="select3">
        		<option>--- 重庆时时彩   20121245228 期 ---</option>
      		</select>
      	</td>
    </tr>
    
    <tr>
		<td class="t_right even">
			<input type="radio" name="searchType" value="radiobutton" style="border:0px;background: transparent;" checked/>按日期</td>
		<td class="t_left">
			<input name="bettingDateStart" class="b_g" value="" size="8" maxlength="12"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});" readonly/> — <input name="bettingDateEnd" class="b_g mr10" value="" size="8" maxlength="12"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});"/>
      		<input type="button" name="button" id="button" value="今天" class="btn td_btn" onClick="today()"/>
      		<input type="button" name="button" id="button" value="昨天" class="btn td_btn" onClick="yesterday()"/>
      		<input type="button" name="button" id="button" value="本星期" class="btn td_btn" onClick="thisWeek()"/>
      		<input type="button" name="button" id="button" value="上星期" class="btn td_btn" onClick="lastWeek()"/>
      		<input type="button" name="button" id="button" value="本月" class="btn td_btn" onClick="thisMonth()"/>
      		<input type="button" name="button" id="button" value="上月" class="btn td_btn" onClick="lastMonth()"/>
      </td>
    </tr>
    
    <tr>
		<td class="t_right even">下注類型</td>
      	<td class="t_left"><p>
      	  <select name="lotteryType2">
      	    <option value=" ">--- 所有彩种 ---</option>
      	    <option value="GDKLSF" selected="selected">全部</option>
      	    <option value="CQSSC">重慶時時彩</option>
      	    <option value="HKLHC">香港六合彩</option>
    	    </select>
      	</p></td>
    </tr>
    
    <tr>
      	<td class="t_right even">注單號碼</td>
      	<td class="t_left">
      		<p class="green"><textarea name="" value="" rows="2"></textarea></p>
 		</td>
    </tr>
    
	<tr>
		<td class="t_right even">注單種類</td>
      	<td class="t_left"><select name="lotteryType5">
      	  <option value=" ">--- 所有彩种 ---</option>
      	  <option value="GDKLSF" selected="selected">全部</option>
      	  <option value="CQSSC">重慶時時彩</option>
      	  <option value="HKLHC">香港六合彩</option>
   	    </select></td>
    </tr>
    
    <tr>
      <td class="t_right even">狀態</td>
      <td class="t_left"><select name="lotteryType3">
        <option value=" ">--- 所有彩种 ---</option>
        <option value="GDKLSF" selected="selected">全部</option>
        <option value="CQSSC">重慶時時彩</option>
        <option value="HKLHC">香港六合彩</option>
      </select></td>
    </tr>
    <tr>
      <td class="t_right even">分類篩選</td>
      <td class="t_left"><select name="lotteryType4">
        <option value=" ">--- 所有彩种 ---</option>
        <option value="GDKLSF" selected="selected">會員</option>
        <option value="CQSSC">重慶時時彩</option>
        <option value="HKLHC">香港六合彩</option>
      </select></td>
    </tr>
    <tr>
      <td class="t_right even">注額範圍</td>
      <td class="t_left"><input name="" value="" maxlength="9" class="b_g"/>-<input name="" value="" maxlength="9" class="b_g"/></td>
    </tr>
    <tr>
		<td class="t_right even">結果範圍</td>
      	<td class="t_left"><input name="" value="" maxlength="9" class="b_g"/>-<input name="" value="" maxlength="9" class="b_g"/></td>
    </tr>
    
  </table>
  
  <div id="tj">
	<div class="tj"><span><input type="submit" class="btn" onClick=" return checkData()" value="确 定" /></span>&nbsp;&nbsp;<span><input name="" type="reset" class="btn" value="重 置" /></span></div>
  </div>
  </s:form>
</div>
<!--内容结束-->
</body>
</html>
