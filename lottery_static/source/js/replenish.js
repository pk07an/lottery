var context="";
function forward(optValue)
{
  if(optValue=='GDKLSF')
      window.location = context+"/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST %>";
  else if(optValue=='CQSSC')
	  window.location = context+"/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST %>";
  else if(optValue=='HKLHC')
	  window.location = context+"/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_HKLHC_SUBTYPE_TE_MA %>";
   
}

//退出
function exit(){
	if(confirm("确定退出吗？")){
		if(parent.document.exitForm.isFrame){
			//框架内部的，则跳转到上一级退出
			parent.document.exitForm.submit();
			return true;
		}else{
			document.exitForm.submit();
			return true;
		}
	}
	
	return false;
}

function validate(formData, jqForm, options)
{	
	if(jqForm[0].moneyOther)
	{		
		var vl=CheckUtil.Trim(jqForm[0].moneyOther.value);
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
		}else if(Math.round(vl) > Math.round(jqForm[0].maxMoneyOther.value)){
			alert("補貨值不能大于補貨限額！");
			return false;
		}
		if($("#fpTime").html()=="00:03"){
			onChangeSubmit();
			alert("已经封盘！");
			return false;
		}
	}
    return true; 
}
function validateForChief(formData, jqForm, options)
{	
	
	if(jqForm[0].money)
	{		
		var vl=CheckUtil.Trim(jqForm[0].money.value);
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
	}
	return true; 
}

function showResponseDy(jsonData, statusText)  { 
	
	$("#infolist").empty();

	if (jsonData.length > 0) {
		var style="";
		if(jsonData[0].winState==6 || jsonData[0].winState==7  || jsonData[0].winState==4  || jsonData[0].winState==5)
			style="style='text-decoration:line-through;color:red;'";
		$("#infolist").append(
				"" + "<tr>" + "<td>" + jsonData[0].orderNo + "</td>"
						+ "<td align=\"center\"> " + jsonData[0].detail + "</td>"
						
						+ "<td align=\"center\""+style+" >" + jsonData[0].money + "</td>"
					
						+ "<td align=\"center\""+style+" >" + jsonData[0].winMoney + "</td>"
						+ "<td align=\"center\"> " + jsonData[0].winStateName + "</td>"
				+ "</tr>" + "");
	} else {
		$("#infolist").append("<tr ><td colspan=\"5\" align=\"center\">没有找到信息</td></tr>");
	}
	$("#finishReplenishShow").show();
    $("#finishReplenishShow").center(); 
} 

function showResponse(jsonData, statusText)  { 
	
	if(jsonData.errorMessage)
	{
		alert(jsonData.errorMessage);
	    return false;
	}	
    form = $("#replenishShow");
    if(jsonData.success)
	{
    	alert(jsonData.success);
	}
    onChangeSubmit();
} 

function submitPageAjax(typeCode) {
	var strUrl = 'ajaxFinishReplenish.action';

	var lotteryType = $("#lotteryType").val();
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"lotteryType":lotteryType},
		dataType : "json",
		success : getPageCallback
	});
}
function submitPageAjaxLM(typeCode,attribute) {
	var strUrl = 'ajaxFinishReplenishLM.action';

	var lotteryType = $("#lotteryType").val();
	var attributeType = $("#attributeType").val();
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"lotteryType":lotteryType,"attribute":attribute,"attributeType":attributeType},
		dataType : "json",
		success : getPageCallback
	});
}
function getPageCallback(json) {
	$("#infolist").empty();

	if (json.length > 0) {
		for ( var i = 0; i <= json.length - 1; i++) {	
			var style="";
			if(json[i].winState==6 || json[i].winState==7  || json[i].winState==4  || json[i].winState==5)
				style="style='text-decoration:line-through;color:red;'";
			$("#infolist").append(
					"" + "<tr>" + "<td>" + json[i].orderNo + "#</td>"
							+ "<td align=\"center\"> " + json[i].detail + "</td>"
							
							+ "<td align=\"center\""+style+" >" + json[i].money + "</td>"
						
							+ "<td align=\"center\""+style+" >" + json[i].winMoney + "</td>"
							+ "<td align=\"center\"> " + json[i].winStateName + "</td>"
					+ "</tr>" + "");
		}
	} else {
		$("#infolist").append("<tr ><td colspan=\"5\" align=\"center\">没有找到信息</td></tr>");
	}
}

//获取当前实时赔率 START
function ajaxGetRealOdd(typeCode,attribute) {
	var strUrl = 'ajaxQueryRealOdds.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"attribute":attribute},
		dataType : "json",
		success : getCallback
	});
}
function getCallback(json) {
	$("#rOdds").html('');
	$("#rOdds").html(json.odd);
	$("#replenishShow").show();
	    $("#replenishShow").center();
	    $("#money").focus();
	
}
//获取当前实时赔率 end

function onlyNumber($this){$($this).autoNumeric({mDec:'',aSep: ''});}
function onlyNumberpercent($this){$($this).autoNumeric({mDec:'3',aSep: ''});}

function getTypeCodeState(panelType,typeCode,attribute) {
	var strUrl = 'getTypeCodeState.action';
	var lotteryType = $("#lotteryType").val();
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"lotteryType":lotteryType,"panelType":panelType,"attribute":attribute},
		dataType : "json",
		success : function(json){
			if(1==json)//1 为封盘状态
			{
				$("#fenghaodiv").show();
				$("#moneydiv").hide();
			}else{
				$("#fenghaodiv").hide();
				$("#moneydiv").show();
			}
		}
	});
}

$(document).ready(function(){
	
	$("#retbtnOther").click(function () {
		   $("#replenishShowOther").hide();
	});
	$("#retbtn").click(function () {
		$("#replenishShow").hide();
	});
	$("#oddBtnCancel").click(function () {
		   $("#oddsShow").hide();
	});
	$("#fBtn").click(function () {
		   onChangeSubmit();
		   $("#finishReplenishShow").hide();
	});
	$("#isCompute").val("f");//默認為不通過“計算補貨”按鈕提交查詢
	
});


jQuery.fn.center = function () {
		    this.css("position","absolute");
		    this.css("top", ( $(window).height() - this.height() ) / 2+$(window).scrollTop() + "px");
		    this.css("left", ( $(window).width() - this.width() ) / 2+$(window).scrollLeft() + "px");
		    return this;
		}

function onChangePlate(){
	$("#rPlate").val($("#plate").val());
	onChangeSubmit();
}
//封號
function changeNotPlay(typeCode,state){
	var strUrl = context+'/admin/ajaxFengHao.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,state:state},type:"POST",success:successFengHao});
}
function successFengHao(json){
	showContent();
}

function onChangeSubmit(){
	if($("#searchType").val()=="1" || $("#searchType").val()=="2"){
		$("#computeBtn").attr("disabled",true);
		$("#avLose").attr("disabled",true);
	}else{
		$("#searchType").removeAttr("disabled");
		$("#avLose").removeAttr("disabled");
	}
	showContent();
}


function showContent() {
	if($("#searchTime").val()=="NO"){
		$("#refreshBtn").hide();
	}else{
		$("#refreshTime").hide();
	}
	$("#onrefresh").show();
	onrefresh = true; 
	var subType = $("#subType").val();
	//var strUrl = context + "/replenish/test.action?searchTime=" + $("#searchTime").val();
	var strUrl = context + "/replenish/replenishSetContent.action";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"subType":subType,"searchArray":$("#searchArray").val(),"subType":$("#subType").val(),
			"loseSort":$("#loseSort").val(),"searchType":$("#searchType").val(),
			"plate":$("#plate").val(),"searchTime":$("#searchTime").val(),"avLose":$("#avLose").val(),
			"avOdd":$("#avOdd").val(),"avCommission":$("#avCommission").val()},
		dataType : "html",
		success : showContentCallback
	});
	//showTwoSideAndDragonRankContent(subType);//显示两面长龙 
}

function showContentCallback(resultHtml) {
	//alert($("#searchType").val());
	if($("#searchType").val()=="1" || $("#searchType").val()=="2"){
		$("#computeBtn").attr("disabled",true);
		$("#avLose").attr("disabled",true);
	}else{
		$("#computeBtn").removeAttr("disabled");
		$("#avLose").removeAttr("disabled");
	}
	var selectOddsNum=$("#changeOddsNum").val();
	$("#content").html(resultHtml);
	$("#changeOddsNum option[value='"+selectOddsNum+"']").attr('selected',true);
	if($("#searchTime").val()=="NO"){
		$("#refreshBtn").show();
	}else{
		$("#refreshTime").show();
	}
	$("#onrefresh").hide();
	onrefresh = false; 
	setCltime();
}

function showTwoSideAndDragonRankContent(subType) {
	var strUrl = context + "/replenish/ajaxtwoSideAndDragonRank.do";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"from":subType},
			dataType : "html",
			success : showTwoSideAndDragonRankContentCallback
	});
}

function showTwoSideAndDragonRankContentCallback(resultHtml) {
	$("#doubleStat").html(resultHtml);
}

function setCltime(){
	$("#onrefresh").hide();
	clDate=1000*Math.floor($("#searchTime").val());
	var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
	document.getElementById("clTime").innerHTML=clnS+"秒";  
}

/**
 *	明细信息列表
 */
 function openDetail(infoLink){
		//alert(infoLink.href);
		//需要打开的页面URL，设置为实际值
		var url = infoLink.href;
		//打开模式窗口，固定写法，不要改变
//		var ret = openModalDialogReport(url,'',1006,800);
		var height=800;
        var width=1006
        //设置窗体居中
        var top = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
        var left = (window.screen.availWidth-10-width)/2; 
        var style="height="+height+", width="+width+", top="+top+",left="+left+", toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no";
        window.open(url,'',style);
		return false;
	}
