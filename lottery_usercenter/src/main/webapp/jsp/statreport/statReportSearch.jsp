<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查詢設定</title>

<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->

</head>

<style>
    .td_btn{background:url(../images/bt_bg.gif) no-repeat center top!important;color:#036!important;}
    .btn{background:url(../images/bt_bg.gif) no-repeat center top;width:62px;height:19px;cursor:pointer;border:none;font-size:12px;}
    .btn:hover{background-position:center bottom;}
</style>

<script>
	var nowDate =  new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));
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
		$("#btnyesterday").attr("class","");
		$("#btntoday").attr("class","blue_btn");
		$("#btnthisWeek").attr("class","");
		$("#btnlastWeek").attr("class","");
		$("#btnthisMonth").attr("class","");
		$("#btnlastMonth").attr("class","");
		var now = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));;
		//2点之前算前一天
		now = now - (1000 * 3 * 60 * 60);
		now = new Date(now);
		now = formatDate(now); 
		
		document.statReportForm.bettingDateStart.value = now;
		document.statReportForm.bettingDateEnd.value = now;
	}
	
	//昨天
	function yesterday(){
		$("#btnyesterday").attr("class","blue_btn");
		$("#btntoday").attr("class","");
		$("#btnthisWeek").attr("class","");
		$("#btnlastWeek").attr("class","");
		$("#btnthisMonth").attr("class","");
		$("#btnlastMonth").attr("class","");
		var yest = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));;
		yest.setDate(yest.getDate()-1);
		//2点之前算前一天
		yest = yest - (1000 * 3 * 60 * 60);
		yest = new Date(yest);
		
		yest = formatDate(yest);
		
		document.statReportForm.bettingDateStart.value = yest;
		document.statReportForm.bettingDateEnd.value = yest;
	}
	
	//本周
	function thisWeek(){
		$("#btnyesterday").attr("class","");
		$("#btntoday").attr("class","");
		$("#btnthisWeek").attr("class","blue_btn");
		$("#btnlastWeek").attr("class","");
		$("#btnthisMonth").attr("class","");
		$("#btnlastMonth").attr("class","");
		var now = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));
		now = now - (1000 * 3 * 60 * 60);
		now = new Date(now);
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		if(nowDayOfWeek==1){  //如果是周一查询的，就等于是上周一查询
			now.setDate(now.getDate()-7);
		}else if(nowDayOfWeek==0){
			nowDayOfWeek=7;
		}
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年 
		
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);   
		//2点之前算前一天
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek +1));  
	    //2点之前算前一天
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.statReportForm.bettingDateStart.value = weekStartDate;
	    document.statReportForm.bettingDateEnd.value = weekEndDate;
	}
	
	//上周
	function lastWeek(){
		$("#btnyesterday").attr("class","");
		$("#btntoday").attr("class","");
		$("#btnthisWeek").attr("class","");
		$("#btnlastWeek").attr("class","blue_btn");
		$("#btnthisMonth").attr("class","");
		$("#btnlastMonth").attr("class","");
		var now = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));                    //当前日期   
		now = now - (1000 * 3 * 60 * 60);
		now = new Date(now);
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		if(nowDayOfWeek==1){  //如果是周一查询的，就等于是上上周一查询
			now.setDate(now.getDate()-7);
		}else if(nowDayOfWeek==0){
			nowDayOfWeek=7;
		}
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 6);  
		
	    //2点之前算前一天
		
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 6)); 
	    
	    //2点之前算前一天
	    
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.statReportForm.bettingDateStart.value = weekStartDate;
	    document.statReportForm.bettingDateEnd.value = weekEndDate;
	}
	
	//本月
	function thisMonth(){
		$("#btnyesterday").attr("class","");
		$("#btntoday").attr("class","");
		$("#btnthisWeek").attr("class","");
		$("#btnlastWeek").attr("class","");
		$("#btnthisMonth").attr("class","blue_btn");
		$("#btnlastMonth").attr("class","");
		var now = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));                    //当前日期      
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
		$("#btnyesterday").attr("class","");
		$("#btntoday").attr("class","");
		$("#btnthisWeek").attr("class","");
		$("#btnlastWeek").attr("class","");
		$("#btnthisMonth").attr("class","");
		$("#btnlastMonth").attr("class","blue_btn");
		var now = new Date(Date.parse('<s:property value="#request.nowDate"/>'.replace(/-/g,   "/")));                    //当前日期      
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
	
	//校验输入的解雇
	function checkData(){
		//判断选中的值
		var selectTypeRadio;
		if(document.statReportForm.selectTypeRadio[0].checked){
			selectTypeRadio = document.statReportForm.selectTypeRadio[0].value;
		}else if(document.statReportForm.selectTypeRadio[1].checked){
			selectTypeRadio = document.statReportForm.selectTypeRadio[1].value;
		}
		
        //判断选中的报表类型
        var reportType;
        if(document.statReportForm.reportTypeRadio[0].checked){
            reportType = document.statReportForm.reportTypeRadio[0].value;
        }else if(document.statReportForm.reportTypeRadio[1].checked){
            reportType = document.statReportForm.reportTypeRadio[1].value;
        }
		
        //判断报表的结算状态
        var balanceTypeValue;
        if(document.statReportForm.balanceType[0].checked){
        	balanceTypeValue = document.statReportForm.balanceType[0].value;
        }else if(document.statReportForm.balanceType[1].checked){
        	balanceTypeValue = document.statReportForm.balanceType[1].value;
        }
        
		if('typeTime' == selectTypeRadio){
			//日期
			var bettingDateStart = document.statReportForm.bettingDateStart.value;
			var bettingDateEnd = document.statReportForm.bettingDateEnd.value;
		    
			if(null == bettingDateStart || null == bettingDateEnd || bettingDateStart.length == 0 || bettingDateEnd.length == 0){
				alert("请输入日期！");
				
				return false;
			}
			
	        //如果是分类报表，则只允许统计一天的数据
	        if("classification" == reportType){
	            var dateStart = new Date(bettingDateStart.replace(/-/g, "/"));
	            var dateEnd = new Date(bettingDateEnd.replace(/-/g, "/"));
	   
	            var sepDays = parseInt((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
	    
	            if(sepDays != 0){
	                alert("由于数据库系统升级，暂时分类报表只能‘独日查询’！");
	                return false;
	            }
	        }
	        
	        //设置期数的特殊值，不计入查询条件
	        document.statReportForm.periodsNum.value = "--- 所有期 ---";
		}else if('typePeriod' == selectTypeRadio){
			//期数
			var periodsNum  = document.statReportForm.periodsNum.value;
			
			if(null == periodsNum || periodsNum.length == 0){
				alert("请选择期数！");
				
				return false;
			}
		}
		
		//保存选中的值
		document.statReportForm.timePeriod.value = selectTypeRadio;
		
		if('delivery'== reportType && 'cleared' == balanceTypeValue){
			//已结算交收报表
			document.statReportForm.action = "${pageContext.request.contextPath}/statreport/settledListEric.action";
		}else if('classification'== reportType && 'cleared' == balanceTypeValue){
			//已结算分类报表
			document.statReportForm.action = "${pageContext.request.contextPath}/classreport/classListEric.action";
		}else if('delivery'== reportType && 'unclear' == balanceTypeValue){
            //未结算交收报表
            document.statReportForm.action = "${pageContext.request.contextPath}/statreport/unsettledList.action?isUp='false'";
        }else if('classification'== reportType && 'unclear' == balanceTypeValue){
            //未结算分类报表
            document.statReportForm.action = "${pageContext.request.contextPath}/classreport/unClassList.action";
        }
		$("#statReportForm").submit();
		$("#btnSubmit").attr("disabled",true);
		return true;
	}
	
	function unsettled(){
		$("form[name='statReportForm']").attr("action","${pageContext.request.contextPath}/statreport/unsettledList.action?isUp='false'");
	}
	
	/* function settled(){
		$("#selTypePeriod").attr("disabled",false);
		$("select[name=periodsNum]").attr("disabled",false);
		$("form[name='statReportForm']").attr("action","${pageContext.request.contextPath}/statreport/list.action");
		
	} */
	
	$(document).ready(function(){
		setPlayTypeList();
	});
	
	function checkPeriod(){
		$("#selTypePeriod").attr("checked","checked");
	}
	function checkTime(){
		$("#selTypeTime").attr("checked","checked");
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
  
  <s:form name="statReportForm" action="/statreport/list.action" id="statReportForm">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king autoHeight account">
    <colgroup>
      <col width="15%" />
      <col width="75%" />
    </colgroup>
    <tr>
      <th colspan="2">報表統計查詢</th>
    </tr>
    
    <tr>
		<td height="36" class="t_right even">彩票種類</td>
		<td class="t_left">
		  <select name="lotteryType" id="lotteryType" class="red">
				<s:if test="#request.selectType=='ALL'">
			  		<option value="ALL" selected="selected">--- 所有彩种 ---</option>
			  		<option value="GDKLSF">廣東快樂十分</option>
					<option value="CQSSC">重慶時時彩</option>
		      		<option value="BJ">北京赛车</option>
		      		<option value="K3">江苏骰寶(K3)</option>
		      		<option value="NC">幸运农场</option>
				</s:if>
				<s:elseif test="#request.selectType=='GDKLSF'">
					<option value="ALL">--- 所有彩种 ---</option>
				   <option value="GDKLSF"  selected="selected">廣東快樂十分</option>
				   <option value="CQSSC">重慶時時彩</option>
		      		<option value="BJ">北京赛车</option>
		      		<option value="K3">江苏骰寶(K3)</option>
		      		<option value="NC">幸运农场</option>
				</s:elseif>
				<s:elseif test="#request.selectType=='CQSSC'">
					<option value="ALL">--- 所有彩种 ---</option>
					<option value="GDKLSF">廣東快樂十分</option>
				   <option value="CQSSC"  selected="selected">重慶時時彩</option>
				   <option value="BJ">北京赛车</option>
				   <option value="K3">江苏骰寶(K3)</option>
				   <option value="NC">幸运农场</option>
				</s:elseif>
				<s:elseif test="#request.selectType=='BJ'">
                    <option value="ALL">--- 所有彩种 ---</option>
                   <option value="GDKLSF">廣東快樂十分</option>
                   <option value="CQSSC">重慶時時彩</option>
                   <option value="BJ" selected="selected">北京赛车</option>
                   <option value="K3">江苏骰寶(K3)</option>
                   <option value="NC">幸运农场</option>
                </s:elseif>
				<s:elseif test="#request.selectType=='K3'">
                    <option value="ALL">--- 所有彩种 ---</option>
                   <option value="GDKLSF">廣東快樂十分</option>
                   <option value="CQSSC">重慶時時彩</option>
                   <option value="BJ">北京赛车</option>
                   <option value="K3" selected="selected">江苏骰寶(K3)</option>
                   <option value="NC">幸运农场</option>
                </s:elseif>
				<s:elseif test="#request.selectType=='NC'">
					<option value="ALL">--- 所有彩种 ---</option>
					<option value="GDKLSF">廣東快樂十分</option>
					<option value="CQSSC">重慶時時彩</option>
		      		<option value="BJ">北京赛车</option>
		      		<option value="K3">江苏骰寶(K3)</option>
		      		<option value="NC" selected="selected">幸运农场</option>
				</s:elseif>
				<s:else>
					<option value="ALL" selected="selected">--- 所有彩种 ---</option>
					<option value="GDKLSF">廣東快樂十分</option>
					<option value="CQSSC">重慶時時彩</option>
		      		<option value="BJ">北京赛车</option>
		      		<option value="K3">江苏骰寶(K3)</option>
		      		<option value="NC">幸运农场</option>
				</s:else>
				
			</select>
			<span id="reportAgain" style="color: #FF0000;"></span>
		</td>
	</tr>
    
    <tr>
		<td height="36" class="t_right even">下注類型</td>
		<td class="t_left">
		<s:select  id="playTypeTem" list="#request.playTypeList" name="playType" listKey="commissionType" listValue="commissionTypeName2" headerKey="ALL" headerValue="--- 所有類型 ---"></s:select>
		</td>
    </tr>
    
    <tr>
    	<input type="hidden" name="timePeriod"/>
		<td height="36" class="t_right even">
			<input id="selTypePeriod" type="radio" name="selectTypeRadio" value="typePeriod" style="border:0px;background: transparent;" <s:if test="#parameters.selectType!= null&&#parameters.selectType[0]=='typePeriod'">checked</s:if> />按期数
		</td>
      	<td class="t_left" >
				<s:select id="periodsNum"  name="periodsNum" list="#request.allPeriodsList" listKey="key" listValue="value" onclick="checkPeriod()" >
				</s:select>
      	</td>
    </tr>
    
    <tr>
		<td class="t_right even">
			<input type="radio" name="selectTypeRadio" value="typeTime" id="selTypeTime" style="border:0px;background: transparent;" <s:if test="(#parameters.selectType!= null&&#parameters.selectType[0]=='typeTime')||#parameters.selectType== null">checked</s:if>/>按日期</td>
		<td class="t_left">
			<input name="bettingDateStart" class="" value='<s:property  value="%{#parameters.bettingDateStart[0]}" />' size="10" maxlength="12" style="border:1px solid #7F9DB9" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});checkTime();" readonly/> — <input name="bettingDateEnd" class="b_g mr10" value='<s:property  value="%{#parameters.bettingDateEnd[0]}" />' size="10" maxlength="12" style="border:1px solid #7F9DB9" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});checkTime();" readonly/>
      		<input type="button" name="button" id="btntoday" value="今天" class="blue_btn" onClick="today();checkTime();"/>
      		<input type="button" name="button" id="btnyesterday" value="昨天" onClick="yesterday();checkTime();"/>
      		<input type="button" name="button" id="btnthisWeek" value="本星期" onClick="thisWeek();checkTime();"/>
      		<input type="button" name="button" id="btnlastWeek" value="上星期" onClick="lastWeek();checkTime();"/>
      		<input type="button" name="button" id="btnthisMonth" value="本月" onClick="thisMonth();checkTime();"/>
      		<input type="button" name="button" id="btnlastMonth" value="上月" onClick="lastMonth();checkTime();"/>
      </td>
    </tr>
    
    <tr>
		<td height="36" class="t_right even">历史報表範圍</td>
      	<td class="t_left"><p id="hs"><s:property value="#request.reportScopeStr"/></p></td>
    </tr>
     
    <tr>
      	<td height="36" class="t_right even">账务说明</td>
      	<td class="t_left">
      		<p class="green" style="line-height:24px;">“当天報表”已与“历史報表”合并<br />
      		“重庆时时彩”凌晨两点前注單算当天帐</p>
 		</td>
    </tr>
    
	<tr>
		<td height="36" class="t_right even">報表類型</td>
      	<td class="t_left">
      	    <!-- 如果是子帐号登录，没有对应的报表的类型的查询权限的，就把就中一项DISABLED掉，把另一项默认为选择-->
      	    <%if(isSub){ 
      	        if(crossReport !=null){%>
      			   <input type="radio" name="reportTypeRadio" value="delivery" style="border:0px;background: transparent;" checked/>交收報表 
      		    <%}else{ %>
      			   <input type="radio" name="reportTypeRadio" value="delivery" style="border:0px;background: transparent;" disabled="disabled" title="該功能已被禁用,請聯系上級"/>交收報表 
      		    <%} %>
<%}else { %>
      			<input type="radio" name="reportTypeRadio" value="delivery" style="border:0px;background: transparent;" checked/>交收報表 
      		<%}%>
      		
      		<%if(isSub){ 
      	        if(classifyReport !=null){
      	            if(crossReport !=null){%>
      		           <input type="radio" name="reportTypeRadio" value="classification" style="border:0px;background: transparent;" />分类報表         	
      		        <%}else{ %>
      		           <input type="radio" name="reportTypeRadio" value="classification" style="border:0px;background: transparent;" checked/>分类報表         	
<%} %>
      		   <%}else{ %>
      		       <input type="radio" name="reportTypeRadio" value="classification" style="border:0px;background: transparent;" disabled="disabled" title="該功能已被禁用,請聯系上級"/>分类報表         	
      		   <%} %>
<%}else { %>
      			  <input type="radio" name="reportTypeRadio" value="classification" style="border:0px;background: transparent;"/>分类報表         
      		<%}%>
        </td>
    </tr>
    
    <tr>
		<td height="36" class="t_right even">结算狀態</td>
      	<td class="t_left">
      	    <!-- TODO 下面两个单击事件可以删除 -->
      		<input type="radio" name="balanceType" value="cleared" style="border:0px;background: transparent;" <s:if test="#parameters.balanceType== null||#parameters.balanceType[0]=='cleared'">checked</s:if> />已结算
        	<input type="radio" name="balanceType" value="unclear" style="border:0px;background: transparent;" <s:if test="#parameters.balanceType[0]=='unclear'">checked</s:if> /><span class="blue">未 结 算</span>
        </td>
    </tr>
    
  </table>
  <div id="tj">
	<div class="tj"><span><input id="btnSubmit" type="button" class="btn" onClick=" return checkData()" value="确 定" style="margin-right:400px;"/></span></div>
  </div>

  </s:form>
</div>
<!--内容结束-->
<script>
	//默认选中当天
	  var startDate=document.statReportForm.bettingDateStart.value ;
	    var endDate=document.statReportForm.bettingDateEnd.value ;
	    var hs=$("#hs").html() ;
	    if(startDate==''&&endDate=='')
	    	{today();}
	     /* alert(hs.indexOf('null'));
	     if(hs.indexOf('null')==0)
	    	{
	    	alert(document.statReportForm.bettingDateStart.value);
	    	$("#hs").html(document.statReportForm.bettingDateStart.value+"-"
			document.statReportForm.bettingDateEnd.value);
	    	} */ 
	    	
	    <s:if test="#parameters.balanceType[0]=='unclear'">
	    unsettled();
	    
	    </s:if>
	    
	
</script>
</body>
</html>
