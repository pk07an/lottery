
function getCook(name) {
		var cookval = '';
		var arrCookie=document.cookie.split("; ");
		for(var i=0;i<arrCookie.length;i++){
			var arr=arrCookie[i].split("=");
			if(arr[0] == name) cookval = unescape(arr[1]);
		}
		return cookval;
	}

var pathname = window.location.pathname;
//var context= "/"+pathname.split("/")[1]+ '/'+getCook("SESSION_PATH");
var context = '/'+getCook("SESSION_PATH");
if(pathname.split("/")[1] != getCook("SESSION_PATH")){
	context= "/"+pathname.split("/")[1]+ '/'+getCook("SESSION_PATH");
}
var frontDomain ='/'

var accountRemainPrice=100000;
var period="";
//var kjDate =1000*90; 
var kjDate =0; 
var fpDate = kjDate-1000*90;
var clDate=1000*20;
var clHiddernDate= 0;
var kpsoundtimes=false;
var fpsoundtimes=false;
var timer;
var lotType;
var initSuccess=false;
function GetRTime() {
	if (initSuccess) {
		var kpflag = false;
		var fpflag = false;
		fpDate = fpDate - 1000;
		var nMS = fpDate;
		var nM = Math.floor(nMS / (1000 * 60)) % 60;
		if (nM < 10)
			nM = '0' + nM;
		var nS = Math.floor(nMS / 1000) % 60;
		if (nS < 10)
			nS = '0' + nS;
		if (fpDate >= 0) {
			document.getElementById("fpTime").innerHTML = nM + ":" + nS;

		} else {
			fpflag = true;
			if (initSuccess) {
				fp();
			}

		}
		kjDate = kjDate - 1000;
		var kjMS = kjDate;
		var kjnM = Math.floor(kjMS / (1000 * 60)) % 60;

		if (kjnM < 10)
			kjnM = '0' + kjnM;

		var kjnS = Math.floor(kjMS / 1000) % 60;
		if (kjnS < 10)
			kjnS = '0' + kjnS;
		if (kjDate >= 0) {
			document.getElementById("kjTime").innerHTML = kjnM + ":" + kjnS;

			var runningperiods = $("#runningperiods").text();
			var regExp = /\d+/;
			var preperiod = regExp.exec($("#preperiod").text());
			// alert(eval(runningperiods - preperiod));
			if (eval(runningperiods - preperiod) > 1) {

				if (null != lotType && lotType != undefined) {
					if (lotType == "GD") {
						if (kjnM == '07' || kjnM == '06') {
							clHiddernDate = clHiddernDate + 1000;
							if (clHiddernDate >= 1000 * 3) {
								clHiddernDate = 0;
							}
						} else {
							clHiddernDate = 0;
						}
					} else if (lotType == "CQ") {
						if ((kjnM == '09' && kjnS <= 30)
								|| (kjnM == '04' && kjnS <= 30)) {
							clHiddernDate = clHiddernDate + 1000;
							if (clHiddernDate >= 1000 * 3) {
								clHiddernDate = 0;
							}
						} else {
							clHiddernDate = 0;
						}
					} else if (lotType == "BJ") {
						if (kjnM == '04' && kjnS <= 30) {
							clHiddernDate = clHiddernDate + 1000;
							if (clHiddernDate >= 1000 * 3) {
								clHiddernDate = 0;
							}
						} else {
							clHiddernDate = 0;
						}
					} else if (lotType == "K3") {
						if (kjnM == '08') {
							clHiddernDate = clHiddernDate + 1000;
							if (clHiddernDate >= 1000 * 3) {
								clHiddernDate = 0;
							}
						} else {
							clHiddernDate = 0;
						}
					}
				}
			}
		} else {
			if (clDate > 1000 * 10)
				clDate = 1000 * 10;
			if (clDate == 0) {
				refreshExceptBetTable();
				// setTimeout(refreshExceptBetTable(),2000);
				kp();
				kpflag = true;

			}

		}
		clDate = clDate - 1000;

		var clnS = Math.floor(clDate / 1000);
		if (clnS < 10)
			clnS = '0' + clnS;
		if (clDate >= 0) {
			document.getElementById("clTime").innerHTML = clnS + "秒";
			if (clHiddernDate == 1000 * 2) {
				clHiddernDate = 0;
				if (!kpflag && !fpflag) {
					refreshExceptBetTable();

				}
			}
		} else {
			// alert("开奖时间已经到了");
			clDate = 1000 * 20;
			if (!kpflag && !fpflag)
				refresh();

		}
	}else{
		//页面加载中
		$("#clTime").html("<span class='blue'>加載中</span>");
	}

	timer = setTimeout("GetRTime()", 1000);
}  
var hkfpDate=1000*90;
function GetHKRTime(){      
	hkfpDate=hkfpDate-1000;
    var nMS=hkfpDate;
    var nH=Math.floor(nMS/(1000*60*60)) % 60; 
    var nM=Math.floor(nMS/(1000*60)) % 60;  
    if(nH<10)nH='0'+nH;
    if(nM<10) nM='0'+nM;
    var nS=Math.floor(nMS/1000) % 60;
    if(nS<10) nS='0'+nS;  
    if(hkfpDate>= 0){document.getElementById("fpTime").innerHTML=nH+":"+nM+":"+nS;}  
    else {}
    clDate=clDate-1000;
    var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
    if(clDate>= 0){        document.getElementById("clTime").innerHTML=clnS+"秒";}  
    else {  
    	//alert("开奖时间已经到了");  
    	clDate=1000*90;
    	//refresh();
    } 
    setTimeout("GetHKRTime()",1000);  
}

function frameDocument(id)
{
  if (document.frames) {
	  //for ie
	   var d = parent.document.frames[id];
	   if(d){
		   return d.document;
	   }
	} else {
		//for w3c
		var d3c = parent.document.getElementById(id);
		if(d3c){
			return d3c.contentDocument;
		}
	}	
}



function getPlayType(typeCode)
{       
	    var ret=null;
		var strUrl = context+'/member/ajaxGetPlayTypeInfo.do?typeCode=' +typeCode;		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ ret=json;}});
		return ret;
	
}                                          
function callBackName(json)
{
 //alert(json.finalTypeName);
}
function getShopPlayOdds(typeCodes,playType)
{       
	var ret=null;
	var strUrl = context+'/getCurrentRealOdd.json?typeCodes=' +typeCodes+"&playType="+playType;		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(html){ ret=html.data;}});	
	return ret;
	
}           
   

/**
 * 最高派彩
 * @param typeCode  下注码
 * @returns
 */
function ajaxGetTopWinPrice(typeCode)
{
	var ret=null;
	var strUrl =context+ '/getTopWinPrice.json?typeCode=' +typeCode;		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(html){ ret=html.data;}});
	
	return ret;
}
function getAJAXRealodd(type,LT)
{       
	    var ret=null;
		var strUrl = context+'/getRealOdd.json?playType=GDKLSF&type=' +type+"&LT="+LT;		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
		return ret;
	
} 


function getCQAJAXRealodd(type,LT)
{       
	    var ret=null;
		var strUrl = context+'/getRealOdd.json?playType=CQSSC&type=' +type+"&LT="+LT;		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
		return ret;
	
} 


function getBJAJAXRealodd(type,LT)
{      
	   
	    var ret=null;
		var strUrl = context+'/getRealOdd.json?playType=BJSC&type=' +type+"&LT="+LT;		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
		
		return ret;
	
} 
//北京盘期信息
function getBJPeriodsInfo(type,LT)
{      
	
	var ret=null;
	var strUrl = context+'/getPeriodsInfo.json?playType=BJSC';		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
	
	return ret;
	
} 
//江苏盘期信息
function getJSPeriodsInfo(type,LT)
{      
	
	var ret=null;
	var strUrl = context+'/getPeriodsInfo.json?playType=K3';		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
	
	return ret;
	
} 
//广东盘期信息
function getGDPeriodsInfo(type,LT)
{      
	
	var ret=null;
	var strUrl = context+'/getPeriodsInfo.json?playType=GDKLSF';		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
	
	return ret;
	
} 
//重庆盘期信息
function getCQPeriodsInfo(type,LT)
{      
	
	var ret=null;
	var strUrl = context+'/getPeriodsInfo.json?playType=CQSSC';		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
	
	return ret;
	
} 
//北京异步获取赔率
function getBJOddsAsync(type) {
	var ret = null;
	var strUrl = context + '/getRealOdd.json?playType=BJSC&type=' +type+'&time='
			+ new Date().getTime();
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onGetOddSuccess(json.data);
				setStopLottery('BJ');
			}
		}
	});
	return ret;
}
//江苏异步获取赔率
function getJSOddsAsync(type) {
	var ret = null;
	var strUrl = context + '/getRealOdd.json?playType=K3&type=' + type + '&time='
			+ new Date().getTime();
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onGetOddSuccess(json.data);
				setStopLottery('K3');
			}
		}
	});
	return ret;
}
//广东异步获取赔率
function getGDOddsAsync(type) {
	var ret = null;
	var strUrl = context + '/getRealOdd.json?playType=GDKLSF&type=' + type + '&time='
	+ new Date().getTime();
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onGetOddSuccess(json.data);
				setStopLottery('GDKLSF');
			}
		}
	});
	return ret;
}

//重庆异步获取赔率
function getCQOddsAsync(type) {
	var ret = null;
	var strUrl = context + '/getRealOdd.json?playType=CQSSC&type=' + type + '&time='
	+ new Date().getTime();
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onGetOddSuccess(json.data);
				setStopLottery('CQSSC');
			}
		}
	});
	return ret;
}

//农场异步获取赔率
function getNCOddsAsync(type) {
	var ret = null;
	var strUrl = context + '/getRealOdd.json?playType=NC&type=' + type + '&time='
	+ new Date().getTime();
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onGetOddSuccess(json.data);
				setStopLottery('NC');
			}
		}
	});
	return ret;
}

function onGetOddSuccess(json) {
	if (null != json && json != undefined) {
		var state = json.state;
		var realOdd = json.realOdd;
		if (state == '3') {
			fp();
		} else {
			var cacheValue = [];
			cacheValue.push("{");
			$.each(realOdd, function(name, value) {

				$("#" + name).text(value);
				var odd = name + ":" + value + ",";
				cacheValue.push(odd);
			});
			cacheValue.push("}");
			$("#jq_cachedOdd").val(cacheValue.join(""));
		}
	}
}

function preCall(json)
{
	if(json.error != undefined)
	{
		var error=json.error;
		if(error.errorInfo=="notLogin")
		{
			 top.location= context+"/logout.xhtml"; 
			 return false;
		}else if(error.errorInfo=="kickout"){
				top.location= context+"/kickout.xhtml";
			 return false;
		}
		
	}
	if(json.userInfo != undefined){
		var userInfo=json.userInfo;
		if(userInfo.userStatus)
		{
			 $("#status",frameDocument('leftFrame')).text("");
			 if(userInfo.userStatus==1)//禁用
			 {
				 //alert("你的帳戶帳戶已經停用，請聯繫上級");
				 top.location=context+"/logout.xhtml"; 
				 return false;
			 }
			 else if(userInfo.userStatus==2)//凍結
			 {
				 //alert("你的帳戶已經凍結，請聯繫上級");
				 $("#status",frameDocument('leftFrame')).text("冻结 ");
				 self.location=context+"/enterSettleReport.xhtml"; 
				
				 return false;
				 
			 }
			 
			 
		    }
	}

return true;
}



function  initPageCallBack()
{

	//$('#ajaxform',frameDocument('leftFrame')).html("");
	if($('#ajaxform',frameDocument('leftFrame')).html()!=''){
		hiddenOfficeLink();
	}else{
		showOfficeLink();
	}
	$('#ajaxform',frameDocument('leftFrame')).show();
	$('#ajaxhiddenform',frameDocument('leftFrame')).html("");
}
function quickBetCallBack()
{
	$('#ajaxform',frameDocument('leftFrame')).html("");
	$('#ajaxform',frameDocument('leftFrame')).show();
	$('#ajaxhiddenform',frameDocument('leftFrame')).html("");
	hiddenOfficeLink();
	
}
function afterBetSubmit()
{
	$('#jq_title').html("<font color='red'>投注中，請稍候......</font>");
	$('#jq_tz').hide();
	$('#jq_sub').hide();
	$('#jq_batch').hide();
	
	//add by peter
	 $('input[name=token]').replaceWith("");
}
function afterBetSuc()
{
	$('#jq_title').html("");
	$('#jq_tz').show();
	$('#jq_sub').show();
	//add by peter for batch bet
	$('#jq_batch').show();
	afterBatchBetSubmit();
}

function hiddenOfficeLink()
{
	$('#jq_gdgf,#jq_cqgf,#jq_bjgf,#jq_jsgf,#jq_ncgf',frameDocument('leftFrame')).hide();
	//$('#jq_cqgf',frameDocument('leftFrame')).hide();
//	$('#jq_bjgf',frameDocument('leftFrame')).hide();
	//$('#jq_jsgf',frameDocument('leftFrame')).hide();
	//$('#jq_ncgf',frameDocument('leftFrame')).hide();
}
function showOfficeLink()
{
	$('#jq_gdgf,#jq_cqgf,#jq_bjgf,#jq_jsgf,#jq_ncgf',frameDocument('leftFrame')).show();
	//$('#jq_cqgf',frameDocument('leftFrame')).show();
	//$('#jq_bjgf',frameDocument('leftFrame')).show();
	//$('#jq_jsgf',frameDocument('leftFrame')).show();
	//$('#jq_ncgf',frameDocument('leftFrame')).show();
}
function showReponseCallBack()
{
	hiddenOfficeLink();
	$('#ajaxform',frameDocument('leftFrame')).html("");
	$('#ajaxform',frameDocument('leftFrame')).hide();
	
}



var shu=new Array("5","17","29","41");
var niu=new Array("4","16","28","40");
var hu=new Array("3","15","27","39");
var tu=new Array("2","14","26","38");
var lon=new Array("1","13","25","37","49");
var se=new Array("12","24","36","48");
var ma=new Array("11","23","35","47");
var yang=new Array("10","22","34","46");
var hou=new Array("9","21","33","45");
var ji=new Array("8","20","32","44");
var gou=new Array("7","19","31","43");
var zhu=new Array("6","18","30","42");

var lxlong=new Array("1","13","25","37");


var SXCode={"SHU":["5","17","29","41"],
		"NIU":["4","16","28","40"],
		"HU":["3","15","27","39"],
		"TU":["2","14","26","38"],
		"LONG":["1","13","25","37","49"],
		"SHE":["2","14","26","38"],
		"MA":["11","23","35","47"],
		"YANG":["10","22","34","46"],
		"HOU":["9","21","33","45"],
		"JI":["8","20","32","44"],
		"GOU":["7","19","31","43"],
		"ZHU":["6","18","30","42"]
		};







//家禽 野兽
var jq=zhu.concat(gou).concat(ji).concat(yang).concat(ma).concat(niu);
var ys=hou.concat(se).concat(lon).concat(tu).concat(hu).concat(shu);
//0-9尾
var w0=new Array("10","20","30","40");
var w1=new Array("1","11","21","31","41");
var w2=new Array("2","12","22","32","42");
var w3=new Array("3","13","23","33","43");
var w4=new Array("4","14","24","34","44");
var w5=new Array("5","15","25","35","45");
var w6=new Array("6","16","26","36","46");
var w7=new Array("7","17","27","37","47");
var w8=new Array("8","18","28","38","48");
var w9=new Array("9","19","29","39","49");


var WSCode={"W0":["10","20","30","40"],
		"W1":["1","11","21","31","41"],
		"W2":["2","12","22","32","42"],
		"W3":["3","13","23","33","43"],
		"W4":["4","14","24","34","44"],
		"W5":["5","15","25","35","45"],
		"W6":["6","16","26","36","46"],
		"W7":["7","17","27","37","47"],
		"W8":["8","18","28","38","48"],
		"W9":["9","19","29","39","49"]	
		};


//單雙
var dan=new Array("1","3","5","7","9","11","13","15","17","19","21","23","25","27","29","31","33","35","37","39","41","43","45","47","49");
var shuang=new Array("2","4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40","42","44","46","48");
//大小
var da=new Array("25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49");
var xiao=new Array("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
//合單雙
var hedan=new Array("1","3","5","7","9","10","12","14","16","18","21","23","25","27","29","30","32","34","36","38","41","43","45","47","49");
var heshuang=new Array("2","4","6","8","11","13","15","17","19","20","22","24","26","28","31","33","35","37","39","40","42","44","46","48");


//尾大小
var wx=new Array("1","2","3","4","10","11","12","13","14","20","21","22","23","24","30","31","32","33","34","40","41","42","43","44");
var wd=new Array("5","6","7","8","9","15","16","17","18","19","25","26","27","28","29","35","36","37","38","39","45","46","47","48","49");

//大單雙
var dadan=new Array("25","27","29","31","33","35","37","39","41","43","45","47");
var dashuang=new Array("26","28","30","32","34","36","38","40","42","44","46","48");
//小單雙
var xiaodan=new Array("1","3","5","7","9","11","13","15","17","19","21","23");
var xiaoshuang=new Array("2","4","6","8","10","12","14","16","18","20","22","24");

//波色
var red=new Array("1","2","7","8","12","13","18","19","23","24","29","30","34","35","40","45","46");
      var reddan=new Array("1","7","13","19","23","29","35","45");
      var redshuang=new Array("2","8","12","18","24","30","34","40","46");
   	   var redda=new Array("29","30","34","35","40","45","46");
   	   var redxiao=new Array("1","2","7","8","12","13","18","19","23","24");


var blue=new Array("3","4","9","10","14","15","20","25","26","31","36","37","41","42","47","48");
         var bluedan=new Array("3","9","15","25","31","37","41","47");
       	  var blueshuang=new Array("4","10","14","20","26","36","42","48");
       		  var blueda=new Array("25","26","31","36","37","41","42","47","48");
       			  var bluexiao=new Array("3","4","9","10","14","15","20");
      
var green=new Array("5","6","11","16","17","21","22","27","28","32","33","38","39","43","44","49");
           var greendan=  new Array("5","11","17","21","27","33","39","43","49"); 
           var greenshuang=  new Array("6","16","22","28","32","38","44");
           var greenda=  new Array("27","28","32","33","38","39","43","44","49");
           var greenxiao=  new Array("5","6","11","16","17","21","22");




$(document).ready(function() {

	
	
	
	
	
	
	
/*$(".king tr td input,#txtrmb,#qsrmb").keyup(function(){     
    var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).bind("paste",function(){     
    var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).css("ime-mode", "disabled");  */


$('#quickMode').click(function() { 
    $.blockUI({ 
    	message: $('#test'), 
    	css: { 
            width: '250px', 
            heighth: '400px',
            top: '5px', 
            left: '', 
            right: '550px', 
            border: 'none', 
            padding: '5px', 
            cursor: 'auto'
        } 
    
    }); 
	$('.close').click($.unblockUI,$("#qsnum tr td").removeClass("selectnumber"),$("#qs input[type=radio][@checked],#sxs input[type=radio][@checked]").attr("checked",false),$("#qsrmb").val(""));

}); 


});

function changeNumToBall(numBerBall)
{
	
	var newBall=$("<div>");

	$.each(numBerBall,function(name,value){
		
			if($.inArray(value,red)!=-1)
				{
				
				newBall.append("<span class=\"ball-bg ball-red\">"+value+"</span>&nbsp;");
				
				}
			else if($.inArray(value,blue)!=-1)
	{
				newBall.append("<span class=\"ball-bg ball-blue\">"+value+"</span>&nbsp;");
				
	}
			else if($.inArray(value,green)!=-1)
				{
					
					newBall.append("<span class=\"ball-bg ball-green\">"+value+"</span>&nbsp;");
					
				}
		});
		return newBall.html();
		
}
function changeZT(val)
{	
	
	$('#ZT').val(val);
	if(val=="Z1")
		{
		 loadDynicDate(3,2);
		 //window.parent.mainFrame.location=context+"/html/hk/zhengmate.html"; 
		}
	else if(val=="Z2")
		{
		//window.parent.mainFrame.location=context+"/html/hk/zhengmate2.html";
		 loadDynicDate(4,2);
		}
	else if(val=="Z3")
		{
		//window.parent.mainFrame.location=context+"/html/hk/zhengmate3.html";
		loadDynicDate(5,2);
		}
	else if(val=="Z4")
	{	
		//window.parent.mainFrame.location=context+"/html/hk/zhengmate4.html";
		loadDynicDate(6,2);
		}  
	else if(val=="Z5")
		{
		//window.parent.mainFrame.location=context+"/html/hk/zhengmate5.html";
		loadDynicDate(7,2);
		}
	else if(val=="Z6")
		{
		loadDynicDate(8,2);
		//window.parent.mainFrame.location=context+"/html/hk/zhengmate6.html";
		
		}
	
}
function changeTM(val)
{
	$('#TAB').val(val);	
	if(val=="A")
		 window.parent.mainFrame.location=context+"/html/hk/tema.html"; 
	else 
		 window.parent.mainFrame.location=context+"/html/hk/temaB.html"; 
}

/*if (window.event) document.captureEvents(event.MOUSEUP);
 function nocontextmenu(){
	event.cancelBubble = true
	event.returnValue = false;
	return false;
} 
function norightclick(event){
	
	
	if (window.event){
		if (event.which == 2 || event.which == 3)
		return false;
	} else if (event.button == 2 || event.button == 3){
		event.cancelBubble = true
		event.returnValue = false;
		return false;
	}
} 

document.oncontextmenu = nocontextmenu; 
document.onmousedown = norightclick; 
function forbid_key(){ 
    if(event.keyCode==116){
        event.keyCode=0;
        event.returnValue=false;
    }
    
    if(event.shiftKey){
        event.returnValue=false;
    }
    if(event.altKey){
        event.returnValue=false;
    }  
    if(event.ctrlKey){
        event.returnValue=false;
    }

    return true;
}
document.onkeydown=forbid_key;*/

/*
 * add by peter for 解决统计渐变的问题,如果传入isClass是true的话，td元素style渐变
 */
function reverseTR(objid,isClass)
{
	var tds=[];
	var trInnerHtml=[];
	objid.children().each(function(index) {
		   
		tds[index]=$(this);
	});
	 for(var j=tds.length-1;j>=0;j--)
	 {
		 if(isClass != undefined && isClass){
			 if(j%2==0){
				 trInnerHtml.push("<td width=\"4%\">"+tds[j].html()+"</td>"); 
			 }else{
				 trInnerHtml.push("<td class=\"even\" width=\"4%\">"+tds[j].html()+"</td>"); 
			 }
		 }else{
			 trInnerHtml.push("<td width=\"4%\">"+tds[j].html()+"</td>"); 
		 }
	 }
	 objid.html(trInnerHtml.join(""));
}
function playOpenSound(){

	//var src="ALARM.WAV";
	 //$("#snd",frameDocument('mainFrame')).attr("src",src);
	 //alert( $("#snd",frameDocument('mainFrame')).attr("src"));
	}
function playStopSound(){
	//var src="ALARM.WAV";
	//$("#snd",frameDocument('mainFrame')).attr("src",src);
}
/*	SWFObject v2.2 <http://code.google.com/p/swfobject/> 
is released under the MIT License <http://www.opensource.org/licenses/mit-license.php> 
*/
var swfobject=function(){var D="undefined",r="object",S="Shockwave Flash",W="ShockwaveFlash.ShockwaveFlash",q="application/x-shockwave-flash",R="SWFObjectExprInst",x="onreadystatechange",O=window,j=document,t=navigator,T=false,U=[h],o=[],N=[],I=[],l,Q,E,B,J=false,a=false,n,G,m=true,M=function(){var aa=typeof j.getElementById!=D&&typeof j.getElementsByTagName!=D&&typeof j.createElement!=D,ah=t.userAgent.toLowerCase(),Y=t.platform.toLowerCase(),ae=Y?/win/.test(Y):/win/.test(ah),ac=Y?/mac/.test(Y):/mac/.test(ah),af=/webkit/.test(ah)?parseFloat(ah.replace(/^.*webkit\/(\d+(\.\d+)?).*$/,"$1")):false,X=!+"\v1",ag=[0,0,0],ab=null;if(typeof t.plugins!=D&&typeof t.plugins[S]==r){ab=t.plugins[S].description;if(ab&&!(typeof t.mimeTypes!=D&&t.mimeTypes[q]&&!t.mimeTypes[q].enabledPlugin)){T=true;X=false;ab=ab.replace(/^.*\s+(\S+\s+\S+$)/,"$1");ag[0]=parseInt(ab.replace(/^(.*)\..*$/,"$1"),10);ag[1]=parseInt(ab.replace(/^.*\.(.*)\s.*$/,"$1"),10);ag[2]=/[a-zA-Z]/.test(ab)?parseInt(ab.replace(/^.*[a-zA-Z]+(.*)$/,"$1"),10):0}}else{if(typeof O.ActiveXObject!=D){try{var ad=new ActiveXObject(W);if(ad){ab=ad.GetVariable("$version");if(ab){X=true;ab=ab.split(" ")[1].split(",");ag=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}}catch(Z){}}}return{w3:aa,pv:ag,wk:af,ie:X,win:ae,mac:ac}}(),k=function(){if(!M.w3){return}if((typeof j.readyState!=D&&j.readyState=="complete")||(typeof j.readyState==D&&(j.getElementsByTagName("body")[0]||j.body))){f()}if(!J){if(typeof j.addEventListener!=D){j.addEventListener("DOMContentLoaded",f,false)}if(M.ie&&M.win){j.attachEvent(x,function(){if(j.readyState=="complete"){j.detachEvent(x,arguments.callee);f()}});if(O==top){(function(){if(J){return}try{j.documentElement.doScroll("left")}catch(X){setTimeout(arguments.callee,0);return}f()})()}}if(M.wk){(function(){if(J){return}if(!/loaded|complete/.test(j.readyState)){setTimeout(arguments.callee,0);return}f()})()}s(f)}}();function f(){if(J){return}try{var Z=j.getElementsByTagName("body")[0].appendChild(C("span"));Z.parentNode.removeChild(Z)}catch(aa){return}J=true;var X=U.length;for(var Y=0;Y<X;Y++){U[Y]()}}function K(X){if(J){X()}else{U[U.length]=X}}function s(Y){if(typeof O.addEventListener!=D){O.addEventListener("load",Y,false)}else{if(typeof j.addEventListener!=D){j.addEventListener("load",Y,false)}else{if(typeof O.attachEvent!=D){i(O,"onload",Y)}else{if(typeof O.onload=="function"){var X=O.onload;O.onload=function(){X();Y()}}else{O.onload=Y}}}}}function h(){if(T){V()}else{H()}}function V(){var X=j.getElementsByTagName("body")[0];var aa=C(r);aa.setAttribute("type",q);var Z=X.appendChild(aa);if(Z){var Y=0;(function(){if(typeof Z.GetVariable!=D){var ab=Z.GetVariable("$version");if(ab){ab=ab.split(" ")[1].split(",");M.pv=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}else{if(Y<10){Y++;setTimeout(arguments.callee,10);return}}X.removeChild(aa);Z=null;H()})()}else{H()}}function H(){var ag=o.length;if(ag>0){for(var af=0;af<ag;af++){var Y=o[af].id;var ab=o[af].callbackFn;var aa={success:false,id:Y};if(M.pv[0]>0){var ae=c(Y);if(ae){if(F(o[af].swfVersion)&&!(M.wk&&M.wk<312)){w(Y,true);if(ab){aa.success=true;aa.ref=z(Y);ab(aa)}}else{if(o[af].expressInstall&&A()){var ai={};ai.data=o[af].expressInstall;ai.width=ae.getAttribute("width")||"0";ai.height=ae.getAttribute("height")||"0";if(ae.getAttribute("class")){ai.styleclass=ae.getAttribute("class")}if(ae.getAttribute("align")){ai.align=ae.getAttribute("align")}var ah={};var X=ae.getElementsByTagName("param");var ac=X.length;for(var ad=0;ad<ac;ad++){if(X[ad].getAttribute("name").toLowerCase()!="movie"){ah[X[ad].getAttribute("name")]=X[ad].getAttribute("value")}}P(ai,ah,Y,ab)}else{p(ae);if(ab){ab(aa)}}}}}else{w(Y,true);if(ab){var Z=z(Y);if(Z&&typeof Z.SetVariable!=D){aa.success=true;aa.ref=Z}ab(aa)}}}}}function z(aa){var X=null;var Y=c(aa);if(Y&&Y.nodeName=="OBJECT"){if(typeof Y.SetVariable!=D){X=Y}else{var Z=Y.getElementsByTagName(r)[0];if(Z){X=Z}}}return X}function A(){return !a&&F("6.0.65")&&(M.win||M.mac)&&!(M.wk&&M.wk<312)}function P(aa,ab,X,Z){a=true;E=Z||null;B={success:false,id:X};var ae=c(X);if(ae){if(ae.nodeName=="OBJECT"){l=g(ae);Q=null}else{l=ae;Q=X}aa.id=R;if(typeof aa.width==D||(!/%$/.test(aa.width)&&parseInt(aa.width,10)<310)){aa.width="310"}if(typeof aa.height==D||(!/%$/.test(aa.height)&&parseInt(aa.height,10)<137)){aa.height="137"}j.title=j.title.slice(0,47)+" - Flash Player Installation";var ad=M.ie&&M.win?"ActiveX":"PlugIn",ac="MMredirectURL="+O.location.toString().replace(/&/g,"%26")+"&MMplayerType="+ad+"&MMdoctitle="+j.title;if(typeof ab.flashvars!=D){ab.flashvars+="&"+ac}else{ab.flashvars=ac}if(M.ie&&M.win&&ae.readyState!=4){var Y=C("div");X+="SWFObjectNew";Y.setAttribute("id",X);ae.parentNode.insertBefore(Y,ae);ae.style.display="none";(function(){if(ae.readyState==4){ae.parentNode.removeChild(ae)}else{setTimeout(arguments.callee,10)}})()}u(aa,ab,X)}}function p(Y){if(M.ie&&M.win&&Y.readyState!=4){var X=C("div");Y.parentNode.insertBefore(X,Y);X.parentNode.replaceChild(g(Y),X);Y.style.display="none";(function(){if(Y.readyState==4){Y.parentNode.removeChild(Y)}else{setTimeout(arguments.callee,10)}})()}else{Y.parentNode.replaceChild(g(Y),Y)}}function g(ab){var aa=C("div");if(M.win&&M.ie){aa.innerHTML=ab.innerHTML}else{var Y=ab.getElementsByTagName(r)[0];if(Y){var ad=Y.childNodes;if(ad){var X=ad.length;for(var Z=0;Z<X;Z++){if(!(ad[Z].nodeType==1&&ad[Z].nodeName=="PARAM")&&!(ad[Z].nodeType==8)){aa.appendChild(ad[Z].cloneNode(true))}}}}}return aa}function u(ai,ag,Y){var X,aa=c(Y);if(M.wk&&M.wk<312){return X}if(aa){if(typeof ai.id==D){ai.id=Y}if(M.ie&&M.win){var ah="";for(var ae in ai){if(ai[ae]!=Object.prototype[ae]){if(ae.toLowerCase()=="data"){ag.movie=ai[ae]}else{if(ae.toLowerCase()=="styleclass"){ah+=' class="'+ai[ae]+'"'}else{if(ae.toLowerCase()!="classid"){ah+=" "+ae+'="'+ai[ae]+'"'}}}}}var af="";for(var ad in ag){if(ag[ad]!=Object.prototype[ad]){af+='<param name="'+ad+'" value="'+ag[ad]+'" />'}}aa.outerHTML='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+ah+">"+af+"</object>";N[N.length]=ai.id;X=c(ai.id)}else{var Z=C(r);Z.setAttribute("type",q);for(var ac in ai){if(ai[ac]!=Object.prototype[ac]){if(ac.toLowerCase()=="styleclass"){Z.setAttribute("class",ai[ac])}else{if(ac.toLowerCase()!="classid"){Z.setAttribute(ac,ai[ac])}}}}for(var ab in ag){if(ag[ab]!=Object.prototype[ab]&&ab.toLowerCase()!="movie"){e(Z,ab,ag[ab])}}aa.parentNode.replaceChild(Z,aa);X=Z}}return X}function e(Z,X,Y){var aa=C("param");aa.setAttribute("name",X);aa.setAttribute("value",Y);Z.appendChild(aa)}function y(Y){var X=c(Y);if(X&&X.nodeName=="OBJECT"){if(M.ie&&M.win){X.style.display="none";(function(){if(X.readyState==4){b(Y)}else{setTimeout(arguments.callee,10)}})()}else{X.parentNode.removeChild(X)}}}function b(Z){var Y=c(Z);if(Y){for(var X in Y){if(typeof Y[X]=="function"){Y[X]=null}}Y.parentNode.removeChild(Y)}}function c(Z){var X=null;try{X=j.getElementById(Z)}catch(Y){}return X}function C(X){return j.createElement(X)}function i(Z,X,Y){Z.attachEvent(X,Y);I[I.length]=[Z,X,Y]}function F(Z){var Y=M.pv,X=Z.split(".");X[0]=parseInt(X[0],10);X[1]=parseInt(X[1],10)||0;X[2]=parseInt(X[2],10)||0;return(Y[0]>X[0]||(Y[0]==X[0]&&Y[1]>X[1])||(Y[0]==X[0]&&Y[1]==X[1]&&Y[2]>=X[2]))?true:false}function v(ac,Y,ad,ab){if(M.ie&&M.mac){return}var aa=j.getElementsByTagName("head")[0];if(!aa){return}var X=(ad&&typeof ad=="string")?ad:"screen";if(ab){n=null;G=null}if(!n||G!=X){var Z=C("style");Z.setAttribute("type","text/css");Z.setAttribute("media",X);n=aa.appendChild(Z);if(M.ie&&M.win&&typeof j.styleSheets!=D&&j.styleSheets.length>0){n=j.styleSheets[j.styleSheets.length-1]}G=X}if(M.ie&&M.win){if(n&&typeof n.addRule==r){n.addRule(ac,Y)}}else{if(n&&typeof j.createTextNode!=D){n.appendChild(j.createTextNode(ac+" {"+Y+"}"))}}}function w(Z,X){if(!m){return}var Y=X?"visible":"hidden";if(J&&c(Z)){c(Z).style.visibility=Y}else{v("#"+Z,"visibility:"+Y)}}function L(Y){var Z=/[\\\"<>\.;]/;var X=Z.exec(Y)!=null;return X&&typeof encodeURIComponent!=D?encodeURIComponent(Y):Y}var d=function(){if(M.ie&&M.win){window.attachEvent("onunload",function(){var ac=I.length;for(var ab=0;ab<ac;ab++){I[ab][0].detachEvent(I[ab][1],I[ab][2])}var Z=N.length;for(var aa=0;aa<Z;aa++){y(N[aa])}for(var Y in M){M[Y]=null}M=null;for(var X in swfobject){swfobject[X]=null}swfobject=null})}}();return{registerObject:function(ab,X,aa,Z){if(M.w3&&ab&&X){var Y={};Y.id=ab;Y.swfVersion=X;Y.expressInstall=aa;Y.callbackFn=Z;o[o.length]=Y;w(ab,false)}else{if(Z){Z({success:false,id:ab})}}},getObjectById:function(X){if(M.w3){return z(X)}},embedSWF:function(ab,ah,ae,ag,Y,aa,Z,ad,af,ac){var X={success:false,id:ah};if(M.w3&&!(M.wk&&M.wk<312)&&ab&&ah&&ae&&ag&&Y){w(ah,false);K(function(){ae+="";ag+="";var aj={};if(af&&typeof af===r){for(var al in af){aj[al]=af[al]}}aj.data=ab;aj.width=ae;aj.height=ag;var am={};if(ad&&typeof ad===r){for(var ak in ad){am[ak]=ad[ak]}}if(Z&&typeof Z===r){for(var ai in Z){if(typeof am.flashvars!=D){am.flashvars+="&"+ai+"="+Z[ai]}else{am.flashvars=ai+"="+Z[ai]}}}if(F(Y)){var an=u(aj,am,ah);if(aj.id==ah){w(ah,true)}X.success=true;X.ref=an}else{if(aa&&A()){aj.data=aa;P(aj,am,ah,ac);return}else{w(ah,true)}}if(ac){ac(X)}})}else{if(ac){ac(X)}}},switchOffAutoHideShow:function(){m=false},ua:M,getFlashPlayerVersion:function(){return{major:M.pv[0],minor:M.pv[1],release:M.pv[2]}},hasFlashPlayerVersion:F,createSWF:function(Z,Y,X){if(M.w3){return u(Z,Y,X)}else{return undefined}},showExpressInstall:function(Z,aa,X,Y){if(M.w3&&A()){P(Z,aa,X,Y)}},removeSWF:function(X){if(M.w3){y(X)}},createCSS:function(aa,Z,Y,X){if(M.w3){v(aa,Z,Y,X)}},addDomLoadEvent:K,addLoadEvent:s,getQueryParamValue:function(aa){var Z=j.location.search||j.location.hash;if(Z){if(/\?/.test(Z)){Z=Z.split("?")[1]}if(aa==null){return L(Z)}var Y=Z.split("&");for(var X=0;X<Y.length;X++){if(Y[X].substring(0,Y[X].indexOf("="))==aa){return L(Y[X].substring((Y[X].indexOf("=")+1)))}}}return""},expressInstallCallback:function(){if(a){var X=c(R);if(X&&l){X.parentNode.replaceChild(l,X);if(Q){w(Q,true);if(M.ie&&M.win){l.style.display="block"}}if(E){E(B)}}a=false}}}}();
function sound(){
	swfobject.embedSWF("clarion.swf", "soundContent", "5", "5", "9.0.0", "clarion.swf");
}

/**
 * 获取停盘的期号
 * @param playTypePerfix
 * @returns
 */
function setStopLottery(playTypePerfix) {
	var ret = null;
	var strUrl = context
			+ '/getStopLotteryInfo.json?playTypePerfix='
			+ playTypePerfix;
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				for ( var result in json.data) {
						var key = json.data[result]['key'];
						var value = json.data[result]['value'];
						if (value = 'Y') {
							var inputElements = $('input[name=' + key + ']');
							inputElements.each(function() {
								$(this).parent().html("封盤中");
							});
	
							var radio = $('input[value=' + key + ']');
							radio.each(function() {
								$(this).attr("disabled", true);
							});
	
							$('#' + key + '').html('-');
	
						}
				}
			}
		}
	});
	return ret;

}

/*
 * add by peter for k3 jssb start
 */
function getJSAJAXRealodd(type,LT)
{      
	   
	    var ret=null;
		var strUrl = context+'/getRealOdd.json?playType=K3&type=' +type+"&LT="+LT;		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"json",type:"POST",success:function(json){ if(json){ret=json.data;}}});
		
		return ret;
	
} 

/*
 * add by peter for k3 jssb end
 */

function getTokenHtmlByAjax()
{      
	    var ret=null;
		var strUrl = context+'/getToken.xhtml?'+new Date();		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({url:queryUrl,async:false,dataType:"text/html",type:"POST",success:function(html){ ret=html;}});
		return ret;
} 
  

//add by peter
function setStopLotteryByJsonInfo(json) {
	for ( var result in json) {
		var key = json[result]['key'];
		var value = json[result]['value'];
		if (value = 'Y') {
			var inputElements = $('input[name=' + key + ']');
			inputElements.each(function() {
				$(this).parent().html("封盤中");
			});
			var radio = $('input[value=' + key + ']');
			radio.each(function() {
				$(this).attr("disabled", true);
			});

			$('#' + key + '').html('-');

		}
	}
}

// add by peter for batch bet start
// 输入快捷金额时，同步赋值给选中的项目
function setBetValToBatchBetVal(batchBetVal) {
	var batchBetTR = $("tr .darkyellow");
	batchBetTR.each(function() {
		if ($(this).hasClass("darkyellow")) {
			$(this).children("td[name='inputPrice']").children(
					":input[type='text']").val(batchBetVal);
		}
	});
}
function batchBetInit() {
	bindBatchBetEvent();
	$(":input[name='batchBetAmount']").keyup(function() {
		var tmptxt = $(this).val();
		$(this).val(tmptxt.replace(/\D|^0/g, ''));

		// 设置快捷金额到选中项目
		var batchBetVal = $(this).val();
		setBetValToBatchBetVal(batchBetVal);
	}).bind("paste", function() {
		var tmptxt = $(this).val();
		$(this).val(tmptxt.replace(/\D|^0/g, ''));
		// 设置快捷金额到选中项目
		var batchBetVal = $(this).val();
		setBetValToBatchBetVal(batchBetVal);
	}).blur(function() {
		var tmptxt = $(this).val();
		$(this).val(tmptxt.replace(/\D|^0/g, ''));
		// 设置快捷金额到选中项目
		var batchBetVal = $(this).val();
		setBetValToBatchBetVal(batchBetVal);
	}).css("ime-mode", "disabled");
}

function bindBatchBetEvent() {
	var inputEleList = $("table td[name='inputPrice'] :input[type='text']");
	var parentTrEleList = inputEleList.parent().parent("tr")
	parentTrEleList.each(function() {
		var firstChildTdEle = $(this).children(":first");
		var parentTrEle = firstChildTdEle.parent("tr");

		// 鼠标移入操作
		firstChildTdEle.bind("mouseover", function() {
			var inputEle = parentTrEle.children("td[name='inputPrice']")
					.children(":input[type='text']");
			if (inputEle.length != 0) {
				parentTrEle.addClass("lightyellow");
				parentTrEle.children(".even").addClass("lightyellow");
			}
		});

		// 鼠标移出操作
		firstChildTdEle.bind("mouseout", function() {
			parentTrEle.removeClass("lightyellow");
			parentTrEle.children(".even").removeClass("lightyellow");
		});

		// 选中操作
		firstChildTdEle.bind("click", function() {
			var inputEle = parentTrEle.children("td[name='inputPrice']")
					.children(":input[type='text']");
			// 去除选中
			if (parentTrEle.hasClass("darkyellow")) {

				parentTrEle.children(".even").removeClass("darkyellow");
				parentTrEle.removeClass("darkyellow");
				if (inputEle.length != 0) {
					inputEle.val("");
					inputEle.removeAttr("readonly");
					inputEle.removeAttr("style");
				}
			}
			// 选中
			else {
				if (inputEle.length != 0) {
					parentTrEle.children(".even").addClass("darkyellow");
					parentTrEle.addClass("darkyellow");
					inputEle.val("");
					inputEle.attr("readonly", "readonly");
					inputEle.attr("style", "background:#ccc");

					var batchBetValue = $(":input[name='batchBetAmount']")
							.val();
					if (null != batchBetValue && batchBetValue != "") {
						parentTrEle.children("td[name='inputPrice']").children(
								":input[type='text']").val(batchBetValue);
					}
				}
			}
		});
	});
}

function batchBetSubmit() {

	var batchBetTR = $("tr .darkyellow");
	// 如果有选择快捷的话，校验是否有输入金额
	if (batchBetTR.size() != 0) {
		var batchBetValue = $(":input[name='batchBetAmount']").val();
		if (null == batchBetValue || batchBetValue == "") {
			alert("請輸入快捷下注的金額");
			return false;
		}
		// 把快捷金额赋值给选择的项目
		batchBetTR.each(function() {
			$(this).children("td[name='inputPrice']").children(
					":input[type='text']").val(batchBetValue);
			//清除输入框背景颜色
			$(this).children("td[name='inputPrice']").children(":input[type='text']").removeAttr("style");
		});
	}

	return true;
}

function afterBatchBetSubmit() {
	// 清空之前的快捷金额
	$(":input[name='batchBetAmount']").val("");
	var batchBetTR = $("tr .darkyellow");
	// 把选中的项目清除掉
	batchBetTR.each(function() {
		if ($(this).hasClass("darkyellow")) {
			$(this).children(".even").removeClass("darkyellow");
			$(this).removeClass("darkyellow");
			$(this).children("td[name='inputPrice']").children(
					":input[type='text']").removeAttr("readonly");
		}
	});
}

function batchBetFPEvent() {

	// 清空快捷栏数据
	$(":input[name='batchBetAmount']").val("");
	$(":input[name='batchBetAmount']").attr("disabled",true);
	unBindBatchBetEvent();
}

// 封盘时候操作
function unBindBatchBetEvent() {
	var inputEleList = $("td[name='inputPrice']");
	var parentTrEleList = inputEleList.parent("tr")
	parentTrEleList.each(function() {
		var firstChildTdEle = $(this).children(":first");
		var parentTrEle = firstChildTdEle.parent("tr");

		// 解除事件绑定
		firstChildTdEle.unbind("mouseover");
		firstChildTdEle.unbind("mouseout");
		firstChildTdEle.unbind("click");

		// 去除选中class
		if (parentTrEle.hasClass("darkyellow")) {
			firstChildTdEle.removeClass("darkyellow");
			parentTrEle.removeClass("darkyellow");
		}
		// 去除mouseover变化的class
		if (parentTrEle.hasClass("lightyellow")) {
			firstChildTdEle.removeClass("lightyellow");
			parentTrEle.removeClass("lightyellow");
		}
	});
}
// add by peter for batch bet end
// 北京盘期信息(异步)
function getBJPeriodsInfoAsync(type, LT, isInit, showSound) {
	var ret = null;
	var strUrl = context + '/getPeriodsInfo.json?playType=BJSC';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if (json && null != json.data && json.data != undefined) {
				// 處理用戶未登錄 凍結 禁用
				var betOk = preCall(json.data);
				if (!betOk){
					return;
				}
				if(json){
					ret = json.data;
					if (isInit) {
						onGetPeriodsInfoSuccess(json.data, showSound, "BJ");
					} else {
						onLoadBJInfoExceptBetTable(json.data, showSound, type, LT);
					}
				}
			}
		}
	});
	getLastBall('bjsc',showSound,type);
	return ret;

}

// 江苏盘期信息(异步)
function getJSPeriodsInfoAsync(type, LT, isInit, showSound) {
	var ret = null;
	var strUrl = context + '/getPeriodsInfo.json?playType=K3';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
		
			if (json && null != json.data && json.data != undefined) {
				// 處理用戶未登錄 凍結 禁用
				var betOk = preCall(json.data);
				if (!betOk){
					return;
				}
				if(json){
				ret = json.data;
					if (isInit) {
						onGetPeriodsInfoSuccess(json.data, showSound, "JS");
					} else {
						onLoadJSInfoExceptBetTable(json.data, showSound, type, LT);
					}
				}
			}
		}
	});
	getLastBall('jsks',showSound,type);
	return ret;

}
// 广东盘期信息(异步)
function getGDPeriodsInfoAsync(type, LT, isInit, showSound) {
	var ret = null;
	var strUrl = context + '/getPeriodsInfo.json?playType=GDKLSF';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if (json && null != json.data && json.data != undefined) {
				// 處理用戶未登錄 凍結 禁用
				var betOk = preCall(json.data);
				if (!betOk){
					return;
				}
				if(json){
				ret = json.data;
					if (isInit) {
						onGetPeriodsInfoSuccess(json.data, showSound,"GD");
					} else {
						onLoadGDInfoExceptBetTable(json.data, showSound, type, LT);
					}
				}
			}
		}
	});
	getLastBall('gdklsf',showSound,type);
	return ret;

}
// 重庆盘期信息(异步)
function getCQPeriodsInfoAsync(type, LT, isInit, showSound) {

	var ret = null;
	var strUrl = context + '/getPeriodsInfo.json?playType=CQSSC';
			+ LT;
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if (json && null != json.data && json.data != undefined) {
				// 處理用戶未登錄 凍結 禁用
				var betOk = preCall(json.data);
				if (!betOk){
					return;
				}
				if(json){
					ret = json.data;
					if (isInit) {
						onGetPeriodsInfoSuccess(json.data, showSound,"CQ");
					} else {
						onLoadCQInfoExceptBetTable(json.data, showSound, type, LT);
					}
				}
			}
		}
	});
	getLastBall('cqssc',showSound,type);
	return ret;

}

//幸运农场盘期信息(异步)
function getNCPeriodsInfoAsync(type, LT, isInit, showSound) {
	var ret = null;
	var strUrl = context + '/getPeriodsInfo.json?playType=NC';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if (json && null != json.data && json.data != undefined) {
				// 處理用戶未登錄 凍結 禁用
				var betOk = preCall(json.data);
				if (!betOk){
					return;
				}
				if(json){
					ret = json.data;
					if (isInit) {
						onGetPeriodsInfoSuccess(json.data, showSound,"NC");
					} else {
						onLoadNCInfoExceptBetTable(json.data, showSound, type, LT);
					}
				}
			}
		}
	});
	getLastBall('xync',showSound,type);
	return ret;

}

// 页面加载时调用
function onGetPeriodsInfoSuccess(json, showSound, lotteryType) {
	var runningPeriods = json.runningPeriods;
	if (runningPeriods && runningPeriods == "no") {
		window.parent.mainFrame.location = context + "/html/cqnotkp.html";
		return false;

	}
	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;
	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	period = runningPeriodNum;
	$("#runningperiods").html(runningPeriodNum);
	if (state == '3') {
		fp();
	} else {
		afterPageLoadSuccess();
	}
	/*
	var lastLotteryPeriods = json.lastLotteryPeriods;
	if (lastLotteryPeriods) {

		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		var lastBallNum = lastLotteryPeriods.BallNum;

		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html(lastBallNum);

		var topPeriodsNum = "";
		if ("GD" == lotteryType) {
			topPeriodsNum = parent.topFrame.gdprePeriodsNum;
		} else if ("CQ" == lotteryType) {
			topPeriodsNum = parent.topFrame.cqprePeriodsNum;
		} else if ("BJ" == lotteryType) {
			topPeriodsNum = parent.topFrame.bjprePeriodsNum;
		} else if ("JS" == lotteryType) {
			topPeriodsNum = parent.topFrame.jsprePeriodsNum;
		}else if ("NC" == lotteryType) {
			topPeriodsNum = parent.topFrame.ncprePeriodsNum;
		}
		if (null != lastLotteryPeriods.PeriondsNum
				&& lastLotteryPeriods.PeriondsNum != topPeriodsNum) {
			// alert("sound");
			if (topPeriodsNum) {
				if (showSound != null && showSound != undefined
						&& showSound == true) {
					sound();
				}

			}
			
			if ("GD" == lotteryType) {
				parent.topFrame.gdprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			} else if ("CQ" == lotteryType) {
				parent.topFrame.cqprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			} else if ("BJ" == lotteryType) {
				parent.topFrame.bjprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			} else if ("JS" == lotteryType) {
				parent.topFrame.jsprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			}else if ("NC" == lotteryType) {
				parent.topFrame.ncprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			}
		}
	}
	setBallStyle();*/
	initSuccess = true;
}

// 开奖刷新时调用(北京)
function onLoadBJInfoExceptBetTable(json, showSound, type, LT) {
	var runningPeriods = json.runningPeriods;

	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;

	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	if(state == '3'){
		fp();
	}else {
		afterPageLoadSuccess();
	}
	period = runningPeriodNum;
/*
	var lastLotteryPeriods = json.lastLotteryPeriods;
	if (lastLotteryPeriods) {

		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		var lastBallNum = lastLotteryPeriods.BallNum;

		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html(lastBallNum);

		if (null != lastLotteryPeriods.PeriondsNum
				&& lastLotteryPeriods.PeriondsNum != parent.topFrame.bjprePeriodsNum) {
			if (parent.topFrame.bjprePeriodsNum) {
				sound();
			}
			parent.topFrame.bjprePeriodsNum = lastLotteryPeriods.PeriondsNum;

			updateBJButtomStat(type, LT);
			updateBJRightStat();
			//获取其他信息
			getBJAJAXOtherAssist(type, 1, 1);

		}
	}
	setBallStyle();*/
}
// 开奖刷新时调用(重庆)
function onLoadCQInfoExceptBetTable(json, showSound, type, LT) {
	var runningPeriods = json.runningPeriods;

	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;

	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	period = runningPeriodNum;

	if(state == '3'){
		fp();
	}else {
		afterPageLoadSuccess();
	}
/*
	var lastLotteryPeriods = json.lastLotteryPeriods;

	var lastperiodNum = "";
	var lastBallNum = "";
	if (lastLotteryPeriods) {
		lastperiodNum = lastLotteryPeriods.PeriondsNum;
		lastBallNum = lastLotteryPeriods.BallNum;
		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html(lastBallNum);

		if (null != lastperiodNum && lastperiodNum != parent.topFrame.cqprePeriodsNum) {
			if (parent.topFrame.cqprePeriodsNum) {
				sound();
			}
			parent.topFrame.cqprePeriodsNum = lastperiodNum;

			updateCQButtomStat(type, LT);
			updateCQRightStat(type, LT);
			//获取其他信息
			getCQAJAXOtherAssist(type, 1, 1);
		}
	}
	setBallStyle();*/
}
// 开奖刷新时调用(江苏)
function onLoadJSInfoExceptBetTable(json, showSound, type, LT) {
	var runningPeriods = json.runningPeriods;

	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;

	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	period = runningPeriodNum;

	if(state == '3'){
		fp();
	}else {
		afterPageLoadSuccess();
	}
	/*
	var lastLotteryPeriods = json.lastLotteryPeriods;
	if (lastLotteryPeriods) {

		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		var lastBallNum = lastLotteryPeriods.BallNum;

		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html(lastBallNum);

		if (null != lastLotteryPeriods.PeriondsNum
				&& lastLotteryPeriods.PeriondsNum != parent.topFrame.jsprePeriodsNum) {
			if (parent.topFrame.jsprePeriodsNum) {
				sound();
			}

			parent.topFrame.jsprePeriodsNum = lastLotteryPeriods.PeriondsNum;
			updatejsRightStat();
			// 获取其他信息
			getJSAJAXOtherAssist(type, 1, 1);

		}
	}
	setBallStyle();*/
}
// 开奖刷新时调用(广东)
function onLoadGDInfoExceptBetTable(json, showSound, type, LT) {
	var runningPeriods = json.runningPeriods;

	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;

	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	period = runningPeriodNum;

	if(state == '3'){
		fp();
	}else {
		afterPageLoadSuccess();
	}
	/*
	var lastLotteryPeriods = json.lastLotteryPeriods;
	if (lastLotteryPeriods) {
		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		if (null != lastperiodNum
				&& lastperiodNum != parent.topFrame.gdprePeriodsNum) {
			if (parent.topFrame.gdprePeriodsNum) {
				sound();
			}
			parent.topFrame.gdprePeriodsNum = lastperiodNum;

			updateGDButtomStat(type, LT);
			updateGDRightStat(type, LT);
			getGDAJAXOtherAssist(type, 1, 1);
		}

		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		var lastBallNum = lastLotteryPeriods.BallNum;

		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html("<a class=\"\" href=\"#\" >" + lastBallNum + "</a>");
	}
	setBallStyle();*/
}
// 开奖刷新时调用(农场)
function onLoadNCInfoExceptBetTable(json, showSound, type, LT) {
	var runningPeriods = json.runningPeriods;
	
	var kjTime = runningPeriods.KjTime;
	var stopTime = runningPeriods.StopTime;
	kjDate = kjTime;
	fpDate = stopTime;
	
	var runningPeriodNum = runningPeriods.PeriondsNum;
	var state = runningPeriods.State;
	period = runningPeriodNum;
	
	if(state == '3'){
		fp();
	}else {
		afterPageLoadSuccess();
	}
	/*
	var lastLotteryPeriods = json.lastLotteryPeriods;
	if (lastLotteryPeriods) {
		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		if (null != lastperiodNum
				&& lastperiodNum != parent.topFrame.gdprePeriodsNum) {
			if (parent.topFrame.gdprePeriodsNum) {
				sound();
			}
			parent.topFrame.gdprePeriodsNum = lastperiodNum;
			
			updateNCButtomStat(type, LT);
			updateNCRightStat(type, LT);
			getNCAJAXOtherAssist(type, 1, 1);
		}
		
		var lastperiodNum = lastLotteryPeriods.PeriondsNum;
		var lastBallNum = lastLotteryPeriods.BallNum;
		
		$("#preperiod").html(lastperiodNum + "期賽果");
		$("#prekj").html("<a class=\"\" href=\"#\" >" + lastBallNum + "</a>");
	}
	setBallStyle();*/
}

function beforePageLoadSuccess() {
	$("#betform .red").html("");
	var inputEles = $("td[name='inputPrice']").children(":input[type='text']");
	inputEles.hide();
//	$("td[name='inputPrice']").each(function() {
//		if ($(this).children("p[name='before']").length == 0) {
//			$(this).append("<p name='before'>封盤中</p>");
//		}
//	});
	if ($("#ballSubmit").length != 0) {
		$("#ballSubmit").attr("disabled", true);
	}
	if ($("#reset").length != 0) {
		$("#reset").attr("disabled", true);
	}
	if ($("#betSubmit").length != 0) {
		$("#betSubmit").attr("disabled", true);
	}

}

function afterPageLoadSuccess() {
	var inputEles = $("td[name='inputPrice']").children(":input[type='text']");
	inputEles.show();
//	var stopEles = $("td[name='inputPrice']").children("p[name='before']");
//	stopEles.html("");
//	stopEles.remove();
	if ($("#ballSubmit").length != 0) {
		$("#ballSubmit").attr("disabled", false);
	}
	if ($("#reset").length != 0) {
		$("#reset").attr("disabled", false);
	}
	if ($("#betSubmit").length != 0) {
		$("#betSubmit").attr("disabled", false);
	}
}

function initBatchBetTips(){
	$("a[name='batchTips']").bind("click",function(){
		alert("點擊項目第一格選中需要快捷投注的項目，輸入金額按下注按紐即可以完成投注操作");
	});
}






///////////开奖统计函数start--arno/////////////			
              function getButtomStat(type,cNum)
                {                   
                        var url=frontDomain+'index.php?s=/home/result/getSumList/';
						var currNum=cNum>0&&cNum<=8?cNum-1:0;
                        $.post(url,{t:type},function(result){                           
                                if(result.status==1){
                                    var Table1_head='';
                                    var Table1_Body="";
                                    var currTab='';
                                    var num1=0;
                                    //开奖球数标题
                                    for(var i in result.data.ballsum){
                                         var display1="none";
                                         var current1='';
                                         var Table1_Body_Table1="";
                                         var Table1_Body_Table2="";
                                         var Table1_Body_Table3="";
                                        if(num1==currNum){
                                             current1='current';
                                             display1="table";
                                             currTab=i;
                                        }
                                        Table1_head+='<th class="'+current1+'"  id="Table1_'+num1+'" onclick="showTab(this,true);"><a  href="javascript:void(0)" >'+i+'</a></th>';
                                        Table1_Body+='<table class="sumTable" cellspacing="0" cellpadding="0" border="0" width="100%" id="Body_Table1_'+num1+'" style="display:'+display1+'">';
                                        Table1_Body_Table1='<tr class="ball_num">';                                       
                                        Table1_Body_Table2+='<tr>';
                                        
                                        if(type!='bjsc'){
                                            Table1_Body_Table1+='<td style="width:30px;*width:36px;">号码</td>';
                                            Table1_Body_Table2+='<td style="width:30px;*width:36px;">未出率</td>';
                                            Table1_Body_Table3+='<tr>';
                                            Table1_Body_Table3+='<td style="width:30px;*width:36px;">出球率</td>';
                                        }
                                        
                                            //统计号码出现次数
                                            for(var j in result.data.ballsum[i]){
                                                Table1_Body_Table1+='<td>'+j+'</td>';   
                                                if(type!='bjsc'){
                                                    Table1_Body_Table2+='<td>'+result.data.notOpen[i][j]+'</td>';  
                                                 }  
                                                      Table1_Body_Table3+='<td>'+result.data.ballsum[i][j]+'</td>';
                                                                                                                                                 
                                            }
                                         Table1_Body_Table1+="</tr>";
                                         Table1_Body_Table2+="</tr>";
                                         Table1_Body_Table3+="</tr>";
                                         if(type=='xync'){
                                             var Table1_Body_Table4='<tr class="ball_num"><td width="50">类型</td>'; 
                                              for(var j in result.data.numName){
                                                  Table1_Body_Table4+='<td>'+result.data.numName[j]+'</td>';  
                                              }
                                              Table1_Body_Table4+="</tr>";
                                              Table1_Body_Table1=Table1_Body_Table4+Table1_Body_Table1;
                                        }
                                         Table1_Body+=Table1_Body_Table1+Table1_Body_Table2+Table1_Body_Table3+"</table>";
                                         num1++;
                                    }
                                    var Table1='<table width="100%" cellspacing="0" cellpadding="0" border="0" class="t_line ball"><tbody><tr class="yellow"> Table1_Head</tr><tr><td colspan="'+num1+'"> Table1_Body</td></tr></tbody></table>';
                                   
                                    var Table2_head='';
                                    var Table2_Body="";
                                    var num2=0;
                                    //开奖号码标题
                                    for(var i in result.data.ballnum){
                                        var display2="none";
                                        var current2='';
                                       if(currTab==i){
                                           current2='current';
                                           display2="";                                             
                                        }
                                        num2++;
                                        Table2_head+='<th  class="'+current2+' b_t t_'+i+'" style="display:'+display2+'" id="Table2_'+num2+'" onclick="showTab(this);"><a  href="javascript:void(0)" >'+i+'</a></th>';                                         
                                        Table2_Body+='<table cellspacing="0" cellpadding="0" border="0" width="100%" id="Body_Table2_'+num2+'" style="display:'+display2+'" class="b_t t_'+i+'">';
                                       if(result.data.ballnum[i].length<30){
                                           for(var jj=0;jj<30-result.data.ballnum[i].length;jj++){
                                                Table2_Body+='<td>&nbsp;<br /></td>';
                                            }
											var diff=0;
                                       }else{
                                            var diff=result.data.ballnum[i].length-30;
                                       }              
                                        //每个球的详细开奖号码
                                        for(var k in result.data.ballnum[i]){ 
											if(k>=diff){
                                                Table2_Body+='<td>'+result.data.ballnum[i][k]+'</td>';
                                            }
                                        }
                                        Table2_Body+="</table>";
                                        //循环每个球单双大小标题
                                        for(var j in result.data.ballresult[i]){  
                                                num2++;
                                              Table2_head+='<th class="b_t t_'+i+'"  style="display:'+display2+'"  id="Table2_'+num2+'" onclick="showTab(this);"><a  href="javascript:void(0)" >'+j+'</a></th>'; 
                                              Table2_Body+='<table cellspacing="0" cellpadding="0" border="0" width="100%" id="Body_Table2_'+num2+'" style="display:none" class="b_t t_'+i+'">';
                                                if(result.data.ballresult[i][j].length<30){
                                                     for(var jj=0;jj<30-result.data.ballresult[i][j].length;jj++){
                                                        Table2_Body+='<td>&nbsp;<br /></td>';                                                    
                                                     }                                                      
													diff=0;
                                                }else{
                                                      diff=result.data.ballresult[i][j].length-30;
                                                }        
                                                //循环每个球开奖号码的单双大小
                                                for(var l in result.data.ballresult[i][j]){
													if(l>=diff){
                                                        Table2_Body+='<td>'+result.data.ballresult[i][j][l]+'</td>';
                                                    }
                                               }
                                               Table2_Body+="</table>";
                                         }
                                         
                                    }
                                    //总和标题
                                    for(var i in result.data.sum){
                                          num2++;
                                         Table2_head+='<th   id="Table2_'+num2+'" onclick="showTab(this);" ><a  href="javascript:void(0)" >'+i+'</a></th>'; 
                                           Table2_Body+='<table cellspacing="0" cellpadding="0" border="0" width="100%" id="Body_Table2_'+num2+'" style="display:none" class="b_t">';
                                             if(result.data.sum[i].length<30){
                                                     for(var jj=0;jj<30-result.data.sum[i].length;jj++){
                                                        Table2_Body+='<td>&nbsp;<br /></td>';                                                    
                                                     }
                                                
													diff=0;
                                             }else{
                                                      diff=result.data.sum[i].length-30;
                                            } 
                                            //总和结果
                                            for(var j in result.data.sum[i]){  
												if(j>=diff){
													Table2_Body+='<td>'+result.data.sum[i][j]+'</td>';
                                                }
                                           }
                                           Table2_Body+="</table>";
                                         
                                    }
                                    var Table2='<table width="100%" cellspacing="0" cellpadding="0" border="0" class="t_line ball"><tbody><tr class="yellow"> Table2_Head</tr><tr><td colspan="'+num2+'"> Table2_Body</td></tr></tbody></table>';
                                    
                                }else{
                                     var Table1='<table width="100%" cellspacing="0" cellpadding="0" border="0" class="t_line ball"><tbody><tr><td > Table1_Body</td></tr></tbody></table>';
                                     var Table1_head='';
                                     var Table1_Body=result.info;                                    
                                }
                                 // $('#ballStat').html(Table1.replace('Table1_Head',Table1_head).replace('Table1_Body',Table1_Body));
                                if(Table1){
                                	Table1=Table1.replace('Table1_Head',Table1_head).replace('Table1_Body',Table1_Body);
                                }
                                if(Table2){
                                	Table2=Table2.replace('Table2_Head',Table2_head).replace('Table2_Body',Table2_Body);
                                }
                                
                                 var Table_All=Table1+Table2;
                                  $('#ballStat').html(Table_All.replace(/\|/g,'<br />'));
                               
                        });
                        
                      //  return str;
                      
                }
            /**获取两面长龙数据
            * @param type 彩种 取值： cqssc、bjsc、jsks、gdklsf、xync
            * 调用如：RightStat('cqssc'); 
            */            
            function RightStat(type)   
            {
                var url=frontDomain+'index.php?s=/home/result/getLMCLList/';
                $.post(url,{t:type},function(result){
                        if(type=='jsks'){
                            var str='<table cellspacing="0" cellpadding="0" border="0" width="100%" class="long jslong" ><tr><td colspan="6" class="tt">近期开奖结果</td></tr>';
                        }else{
                            var str='<table cellspacing="0" cellpadding="0" border="0" width="100%" class="long" ><tr><td colspan="2">两面长龙排行</td></tr>';
                        }
                        if(result.status==1){
                            if(type=='jsks'){
                                var imgurl="../images/";
                                for(var i in result.data){                                          
                                    str+="<tr><td width='33'>"+i+"期</td>";
                                    for(var b in result.data[i].ball){       
                                        str+="<td width='27'><img src="+imgurl+'4_'+result.data[i].ball[b]+".gif /></td>";
                                    }                                    
                                    str+="<td width='23'>"+result.data[i].sum+"</td><td width='29'";
                                    if(result.data[i].daxiao=='大') {
										str+="class='big'";
									}else if(result.data[i].daxiao=='通吃'){
										str+="class='green'";
									}
                                    str+=">"+result.data[i].daxiao+"</td></tr>";                                            
                                }
                            }else{  
                                for(var i in result.data){                                          
                                    str+="<tr><td width='50%' class='lf'>"+i+"</td><td class='rg'>"+result.data[i]+"期</td></tr>";                                            
                                }
                            }

                        }else{
                            str+="<tr><td colspan='2'>"+result.info+"</td></tr>";                                      
                        }
                        str+="</table>";                        
						$('.gdright,.jsright').html(str);
                });
            }

			  /**获取开奖数据
            * @param lotteryType 彩种 取值： cqssc、bjsc、jsks、gdklsf、xync
            * 调用如：getLastBall('cqssc'); 
            */            
            function getLastBall(lotteryType,showSound,cNum)   
            {
                var url=frontDomain+'index.php?s=/home/result/getLastBall/';
          
				$.post(url,{t:lotteryType},function(result){ 					 
                		var lastLottery  =[];                       
                        if(result.status==1){                          	  
                        	var lastBallNum=''; 
                        	for(var b in result.data.ball){       
                               lastBallNum+="<span>"+result.data.ball[b]+"</span>";
								
                            }                     
                        		var LastQishu=result.data.qishu;
							
								$("#preperiod").html(LastQishu + "期賽果");
								$("#prekj").html(lastBallNum);

								var topPeriodsNum = "";
								if ("gdklsf" == lotteryType) {
									topPeriodsNum = parent.topFrame.gdprePeriodsNum;
								} else if ("cqssc" == lotteryType) {
									topPeriodsNum = parent.topFrame.cqprePeriodsNum;
								} else if ("bjsc" == lotteryType) {
									topPeriodsNum = parent.topFrame.bjprePeriodsNum;
								} else if ("jsks" == lotteryType) {
									topPeriodsNum = parent.topFrame.jsprePeriodsNum;
								}else if ("xync" == lotteryType) {
									topPeriodsNum = parent.topFrame.ncprePeriodsNum;
								}
								if (null != LastQishu	&& LastQishu != topPeriodsNum) {
									// alert("sound");
									if (topPeriodsNum) {
										if (showSound != null && showSound != undefined
												&& showSound == true) {
											sound();
										}

									}
									
									if ("gdklsf" == lotteryType) {
										parent.topFrame.gdprePeriodsNum = LastQishu;
									} else if ("cqssc" == lotteryType) {
										parent.topFrame.cqprePeriodsNum = LastQishu;
									} else if ("bjsc" == lotteryType) {
										parent.topFrame.bjprePeriodsNum = LastQishu;
									} else if ("jsks" == lotteryType) {
										parent.topFrame.jsprePeriodsNum = LastQishu;
									}else if ("xync" == lotteryType) {
										parent.topFrame.ncprePeriodsNum = LastQishu;
									}
								}
							}
							if ("gdklsf" == lotteryType) {
								updateGDButtomStat(lotteryType,cNum);
								updateGDRightStat(lotteryType, true);
								getGDAJAXOtherAssist(lotteryType, 1, 1);
							} else if ("cqssc" == lotteryType) {
								updateCQButtomStat(lotteryType,cNum);
								updateCQRightStat(lotteryType, true);								
								getCQAJAXOtherAssist(lotteryType, 1, 1);
							} else if ("bjsc" == lotteryType) {
								updateBJButtomStat(lotteryType, cNum);
								updateBJRightStat();
								//获取其他信息
								getBJAJAXOtherAssist(lotteryType, 1, 1);
							} else if ("jsks" == lotteryType) {
								updatejsRightStat();			
								getJSAJAXOtherAssist(lotteryType, 1, 1);
							}else if ("xync" == lotteryType) {
								updateNCButtomStat(lotteryType, cNum);
								updateNCRightStat(lotteryType, true);
								getNCAJAXOtherAssist(lotteryType, 1, 1);
							}
							setBallStyle();
                     
                });
            }
            function showTab(t,f){
                var $this=$(t);
                var id=$this.attr('id');  
                $this.addClass('current').siblings('th').removeClass('current');
                $("#Body_"+id).show().siblings("table").hide();
                if(f){
                    var tit=$this.text();
                    //alert(tit);
                    $('.b_t').hide().removeClass('current');
                    $('th.t_'+tit).show().eq(0).addClass('current').siblings('th').removeClass('current');
                     $('table.t_'+tit).eq(0).show();
                }
            }
///////////开奖统计函数end--arno/////////////
            document.write('<script language="javascript" src="/js/Forbid.js" type="text/javascript"></script>');
            
            $(document).ready(function() {
				$('#ajaxform',frameDocument('leftFrame')).html("");
				showOfficeLink();
				 $('body').attr('keydown_status',1);
            	 $(document).bind('keydown', function (event) {
            		 var key = event.keyCode;
					 var keydown_status=$('body').attr('keydown_status')*1;					 
            		 if(key == 13&&keydown_status){	
							 $("#betSubmit").click();
							 $('body').attr('keydown_status',0);
							 setTimeout(function(){
								$('body').attr('keydown_status',1);
							 },3000);
            		 }
            		 
            	 });
            });