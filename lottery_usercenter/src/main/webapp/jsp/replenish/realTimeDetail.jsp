<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系統實時滾單</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var searchTime = 2;
var onrefresh = false;
var timer;
//发出声音
function sound(){
	swfobject.embedSWF("../images/ClewSound.swf", "soundContent", "5", "5", "9.0.0", "../images/ClewSound.swf");
}
//保持页面只有49条数据
function all(listTimes){
	var trslength= $("#infolist").find("tr").length;
	for(var i=trslength;i>=listTimes;i--) //保留最前面两行！
	{
		$("#infolist").find("tr").eq(i).remove();
	}
}
function setCltime(){
	clDate=1000*Math.floor($("#searchTime").val());
	var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
	document.getElementById("clTime").innerHTML=clnS+"秒";  
}

function changeTime()
{
	submitAjax();
	onrefresh = false; 
	setCltime();
	if($("#searchTime").val()=="NO"){
		$("#refreshTime").hide();
		$("#refreshBtn").show();
	}else{
		if(onrefresh == false){
			$("#refreshTime").show();
		}else{
			$("#refreshTime").hide();
		}
		$("#refreshBtn").hide(); 
		GetRTime();
	}
} 

function submitAjax() {
	if($("#searchTime").val()=="NO"){
		$("#refreshBtn").hide();
	}else{
		$("#refreshTime").hide();
	}
	$("#onrefresh").show();
	onrefresh = true; 
	var strUrl = "${pageContext.request.contextPath}/replenish/ajaxRealTimeDetail.action";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"queryTime":$("#queryTime").val(),"subType":$("#subType").val(),"lowestMoney":$("#lowestMoney").val()},
		dataType : "json",
		success : getCallback
	});
}

function getCallback(json) {
	 $("#infolist").html("");
	 $("#infolistOdd").html("");
	 //处理投注单和朴货单数据
	 if (json.detail.length > 0) {
		for ( var i = 0; i <= json.detail.length - 1; i++) {
			var obj=document.getElementsByName('sound');
			if(obj[0].checked){
				 sound();
			}
			
			$("#infolist").prepend(
					"" + "<tr onMouseOver=\"this.style.backgroundColor='#FFFFA2'\" onmouseout=\"this.style.backgroundColor=''\">"
		                + "<td>" + json.detail[i].orderNo + "#<br />"
	                    + json.detail[i].bettingDateStr + "</td>"
	              + "<td>"+	json.detail[i].userName+"<br /><b>"+ json.detail[i].whoReplenish+ "</b></td>"
	              + "<td>"+json.detail[i].agent+"<br />"
			      + "<span class=\"g666\">" + json.detail[i].agentRate+"%</span></td>"
		   		  + "<td>" + json.detail[i].genAgent + "<br />"
			      + "<span class=\"g666\">" + json.detail[i].genAgentRate + "%</span></td>"
			      + "<td>" + json.detail[i].stock + "<br />"
			      + "<span class=\"g666\">" + json.detail[i].stockRate + "%</span></td>"
			      + "<td>" + json.detail[i].branch + "<br />"
			      + "<span class=\"g666\">" + json.detail[i].branchRate + "%</span></td>"
			      + "<td>" + json.detail[i].chiefRate + "%<br />"
			      + "<span class=\"g666\">" + json.detail[i].chiefCommission + "</span></td>"
			      + "<td><span class=\"blue\">" + json.detail[i].typeCodeNameOdd + "</span></td>"
			      + "<td class=\"t_right\">" + json.detail[i].money + "<br />"
	              + "<span class=\"red\">" + json.detail[i].rateMoney + "</span></td>"
	              + "</tr>" + "");
		}
 		all(50); //保留49条数据
	} 
	//处理自动降赔数据
	if (json.detailOdd.length > 0) {
		if($("#infoListOdd_title").html()==""){
			$("#infoListOdd_title").html("<tr><th colspan=\"2\"><strong class=\"blue\">設定額度到“賠率自動降”提醒</strong></th></tr>");
		}
		for ( var i = 0; i <= json.detailOdd.length - 1; i++) {
			var obj=document.getElementsByName('sound');
			if(obj[0].checked){sound();} 
			
			if(i != json.detailOdd.length - 1){
 				$("#infolistOdd").prepend(
						"" + "<tr onMouseOver=\"this.style.backgroundColor='#FFFFA2'\" onmouseout=\"this.style.backgroundColor=''\">"
						+"<td class=\"even\">" + json.detailOdd[i].oddsTypeName + "</td>"
					    +"<td class=\"even t_left\" >" + json.detailOdd[i].playTypeCodeName + "</td>"
						+ "</tr>" + ""); 
			}
		    else{
				//处理最后一行。
				$("#infolistOdd").prepend(
						"" + "<tr onMouseOver=\"this.style.backgroundColor='#FFFFA2'\" onmouseout=\"this.style.backgroundColor=''\">"
						+"<td width=\"21%\" class=\"even\">" + json.detailOdd[i].oddsTypeName + "</td>"
					    +"<td width=\"79%\" class=\"even t_left\">" + json.detailOdd[i].playTypeCodeName + "</td>"
						+ "</tr>" + "");
			} 
		}
 		all(5); //保留5条数据
	} 
	if($("#searchTime").val()=="NO"){
		$("#refreshBtn").show();
	}else{
		$("#refreshTime").show();
	}
	$("#onrefresh").hide();
	onrefresh = false; 
	setCltime();
}
</script>
</head>
<body>
<input value="${queryTime}" id="queryTime" type="hidden"/>
<div id="content">
<div id="soundContent"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!--控制表格头部开始-->
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif">
	  <div id="Tab_top">
	    <table border="0" cellpadding="0" cellspacing="0" class="ssgd_l" id="Tab_top_left">
                  <tr>
				    <td width="14" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
				    <td width="24" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                    <td width="81"><strong>实时滚單</strong></td>
                  </tr>
          </table>
		<table id="Tab_top_right" class="ssgd_r" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="94" align="right">
        <input type="hidden" id="subType" value="<s:property value='#request.subType' escape='false'/>"/>
    	<input id="refreshBtn" type="button" value="更新" onclick="submitAjax()" style="display:none;"/>
        <span id="refreshTime">更新：<span style="margin-right:2px;" id="clTime">0秒</span></span>
        <span id="onrefresh" style="display:none;">載入中...</span>
    </td>
    <td width="56">
      <select name="searchTime" id="searchTime" onchange="changeTime()">
	        <option value ="NO" <s:if test="searchTime==NO"> selected</s:if>>-NO-</option>
	        <option value ="2" <s:if test="searchTime==2"> selected</s:if>>2秒</option>
	        <option value ="5" <s:if test="searchTime==5"> selected</s:if>>5秒</option>
	        <option value ="10" <s:if test="searchTime==10"> selected</s:if>>10秒</option>
	        <option value ="15" <s:if test="searchTime==15"> selected</s:if>>15秒</option>
	        <option value ="20" <s:if test="searchTime==20"> selected</s:if>>20秒</option>
	        <option value ="25" <s:if test="searchTime==25"> selected</s:if>>25秒</option>
	        <option value ="30" <s:if test="searchTime==30"> selected</s:if>>30秒</option>
	        <option value ="40" <s:if test="searchTime==40"> selected</s:if>>40秒</option>
	        <option value ="50" <s:if test="searchTime==50"> selected</s:if>>50秒</option>
	        <option value ="60" <s:if test="searchTime==60"> selected</s:if>>60秒</option>
	        <option value ="99" <s:if test="searchTime==99"> selected</s:if>>99秒</option>
	      </select></td>
	<td width="16" align="right"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
	  </tr>
	    </table>
		<table border="0" cellpadding="0" cellspacing="0" class="ssgd_m" id="Tab_top_center_ssgd" name="Tab_top_center">
  <tr>
     <td width="275">注额低于
       <input id="lowestMoney" value='0' size="10" maxlength="10" class="b_g" />
       的注單不滚入本列表</td>
     <td width="77">
       <input type="checkbox" name="sound" checked="checked"/>
       入單音</td>
  </tr>
</table>
	  </div>
	</td>
  </tr>
<!--控制表格头部结束-->

<!--控制中间内容开始-->
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4 ssgd_padding">
		<colgroup>
        <col width="126" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="60" />
		<col width="200" />
		<col width="100" />
        </colgroup>
        <tbody id="infoListOdd_title"></tbody>
        <tbody id="infolistOdd"></tbody>
        
		<!-- 表格内容结束 一行六列-->
		<table width="900" border="0" cellspacing="0" cellpadding="0" class="king mt4 ssgd_padding">
		<colgroup>
        <col width="126" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="72" />
		<col width="60" />
		<col width="200" />
		<col width="100" />
        </colgroup>
		  <tr>
		    <th><strong>注單号/时间</strong></th>
            <th><strong>会员</strong></th>
            <th><strong>代理</strong></th>
            <th><strong>總代理</strong></th>
            <th><strong>股东</strong></th>
            <th><strong>分公司</strong></th>
            <th><strong>總监</strong></th>
            <th><strong>下注明细</strong></th>
            <th><strong>下注金额/<span class="red">实占额</span></strong></th>
		  </tr>
		  <tbody id="infolist"></tbody>
        </table></td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
<!--控制中间内容结束-->

<!--控制底部操作按钮开始-->
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align="center">&nbsp;</td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
<!--控制底部操作按钮结束-->
</table>
</div>
<script type="text/javascript">
var clDate=1000*Math.floor($("#searchTime").val());
var change = 0;
function GetRTime(){
	if($("#searchTime").val()!="NO" && onrefresh==false){
        clDate=clDate-1000;
    }
    var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
    if(clDate> 0){        
    	document.getElementById("clTime").innerHTML=clnS+"秒";  
       
        }  
    else {  
    	clDate=1000*Math.floor($("#searchTime").val());
    	submitAjax();
    } 
    
    timer=setTimeout("GetRTime()",1000);  
} 
	
function changeTime()
{
	clearTimeout(timer);
	submitAjax();
	onrefresh = false; 
	setCltime();
	if($("#searchTime").val()=="NO"){
		$("#refreshTime").hide();
		$("#refreshBtn").show();
	}else{
		if(onrefresh == false){
			$("#refreshTime").show();
		}else{
			$("#refreshTime").hide();
		}
		$("#refreshBtn").hide(); 
		GetRTime();
	}
}

$(document).ready(function(){
	$("#searchTime").val(2);
	$("#refreshTime").show();
	$("#refreshBtn").hide();
	changeTime();
});
</script>
</body>
</html>