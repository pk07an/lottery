
function loadDynicDate(type, LT, showSound) {
	form = $("#ajaxform", frameDocument('leftFrame'));
	//form.html("");
	beforePageLoadSuccess();
	// 获取盘期信息
	getNCPeriodsInfoAsync(type, 0, true, showSound);
	// 获取赔率
	getNCOddsAsync(type);

	updateNCButtomStat(LT,type);
	updateNCRightStat(LT,type);
	initPageCallBack();
	//$('#betform')[0].reset();

	// add by peter
	lotType = "NC";
	getNCAJAXOtherAssist(type, 1, 0);
}

function updateNCButtomStat(LT,type)
{
getButtomStat('xync',type);
}
function updateNCRightStat(LT,type)
{
	RightStat('xync');
}

function fp()
{
  	//alert("封盘时间已经到了");	
    $(":input[name='checkType']").attr("disabled", true);
    $("#subBet").attr("disabled", true);
    $("#res").attr("disabled", true);
	$(":input[name='BALL']").hide();
	$("#NC_STRAIGHTTHROUGH_RX2").html("-");
	$("#NC_STRAIGHTTHROUGH_RX3").html("-");
	$("#NC_STRAIGHTTHROUGH_RX4").html("-");
	$("#NC_STRAIGHTTHROUGH_RX5").html("-");
	$("#NC_STRAIGHTTHROUGH_R2LZ").html("-");
	$("#NC_STRAIGHTTHROUGH_R3LZ").html("-");
    
	
}
function kp()
{
	//alert("开奖时间已经到了");  
	window.location.reload();
	//$("#betform")[0].reset();
}
function refresh()
{
	 loadDynicDate(subType,lottterType,true);
}
function setBallStyle()
{
    $("#prekj").find('span').each(function(i){
    	var num=$(this).text();
	 if(num<10)
		 num="0"+num;
	    var cls="NC_"+num;
	    $(this).text("");
		 $(this).addClass(cls);});
}


function displayBetInfoOnLeft()
{
	
	var  radioType=$("#checkTable :input[name='checkType']:checked").val();
	var selecedBall=$("#betTable :input[name='BALL']:checked");
	var selecedSize=selecedBall.size();
	var type="";
	var number=0;
	var odds=0;
	if(radioType==null)
		{
		alert("請選擇投注類型");
		return false;
		}
	if(radioType=='NC_STRAIGHTTHROUGH_RX2')
	{
		odds=$("#NC_STRAIGHTTHROUGH_RX2").text();
		number=2;
		type="任選二";
		if(selecedSize<2)
	   {alert('"任選二"最少必須選擇兩個號碼');return false;}
	}
	if(radioType=='NC_STRAIGHTTHROUGH_R2LZ')
	{
		odds=$("#NC_STRAIGHTTHROUGH_R2LZ").text();
		number=2;
		type="選二連組";
		if(selecedSize<2)
			{
		alert('"選二連組"最少必須選擇兩個號碼');
		return false;
			}
	}
	if(radioType=='NC_STRAIGHTTHROUGH_RX3')
	{
		odds=$("#NC_STRAIGHTTHROUGH_RX3").text();
		number=3;
		type="任選三";
		if(selecedSize<3)
			{
		alert('"任選三"最少必須選擇三個號碼');
		return false;}
	}
	if(radioType=='NC_STRAIGHTTHROUGH_R3LZ')
	{
		odds=$("#NC_STRAIGHTTHROUGH_R3LZ").text();
		number=3;
		type="選三前組";
		if(selecedSize<3)
			{
		alert('"選三前組"最少必須選擇三個號碼');
		return false;}
	}
	if(radioType=='NC_STRAIGHTTHROUGH_RX4')
	{
		odds=$("#NC_STRAIGHTTHROUGH_RX4").text();
		number=4;
		type="任選四";
		if(selecedSize<4)
			{
		alert('"任選四"最少必須選擇四個號碼');
		return false;}
	}
	if(radioType=='NC_STRAIGHTTHROUGH_RX5')
	{
		odds=$("#NC_STRAIGHTTHROUGH_RX5").text();
		number=5;
		type="任選五";
		if(selecedSize<5)
			{
		alert('"任選五"最少必須選擇五個號碼');
	    return false;}
	}
	
	var i;
	var num=0;
	
	var resultArr=C(selecedBall,number);
	
	
	var balls=new   Array();
	for(i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
	}
	var json=ajaxGetTopWinPrice(radioType);
	var winQuto=json.winQuatas;
	
	balls.sort(function(x,y){return (x - y);});
	createFormForSubmit(resultArr,balls,type,odds,winQuto,json.betQuotas);

}

function createFormForSubmit(resultArr,balls,playType,odds,winQuto,betQuotas) 
{
	 $("#ajaxhiddenform",frameDocument('leftFrame')).html("");
	 
	var  radioType=$("#checkTable input[name='checkType']:checked").val();
	var selecedBall=$("#betTable input[name='BALL']:checked");
	var selecedSize=selecedBall.size();
	 comSize=resultArr.length;	

	canBetPrice=Math.floor(betQuotas/comSize);
	//var odds=odds;
	var betNum="<p>";

	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxncStraightthroughSub.do");
	    form.attr('method','post');
	    inputBetType = "<input type='hidden' name='checkType' id='checkType' value='"+radioType+"' />";
	    cacheOdds = ("<input type='hidden' name='cachedOdd' value='{"+radioType+":"+odds+"} '/>");
	    form.append(cacheOdds);
	    form.append(inputBetType);
	    //add by peter
	    var tokenHtml = getTokenHtmlByAjax();
	    form.append(tokenHtml);
	    //add by peter
	    var subType=$("#subType",frameDocument('mainFrame')).val();
	    subType = ('<input type="hidden" name="subType" value="'+subType+'"/>');
	    form.append(subType);
	    for(var i=0;i<balls.length;i++)
	    {
	    	 betNum=betNum+"<span class=\"l_blue\">"+balls[i]+"</span>、";
	    	 ballValue = "<input type='hidden' name='BALL' value='"+balls[i]+"' />";
	    	 form.append(ballValue);
	    }	   
	    betNum=betNum+"</p>";
	
	var div="<div class='p'>";
	div=div+""
	+"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
    +" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
    +"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+playType+"</span> @ <strong class=\"red\">"+odds+"</strong></p>"
    +"<p class=\"xzmz blue\">下注号碼明细</p>"
    +betNum
    +"<p>您共选择了<span class=\"red\">"+selecedSize+"</span>个号碼</p>"
    +" <p>“复式”共分为<span class=\"red\">"+comSize+"</span>组</p>"
    +"<p>每组最高可下注金額<span class=\"red\">"+canBetPrice+"</span>元</p>"
    +"</td></tr>"
    +"<tr><td class=\"l_color\">每注金額</td><td>"
    +"<input type='text' size=\"8\" maxlength=\"9\" class=\"xz\" name='price' id='price'   onpaste=\"var s=clipboardData.getData('text'); if(!/\D/.test(s)) value=s.replace(/^0*/,''); return false;\" ondragenter='return false' />"
     
    +"</td></tr><tr><td width=\"34%\" class=\"l_color\">下注合计</td><td width=\"66%\"><span class=\"red\" id=\"betSum\">0</span></td></tr>"
    +"<tr><td class=\"l_color\">最高派彩</td><td>"+winQuto+"</td></tr>"
	+"<tr><td colspan=\"2\" class=\"l_color\"><div class=\"tj\" style=\"margin:8px auto;\"><span><input type=\"button\" name=\"\" class=\"btn2\" value=\"取 消\" id=\"retbtn\"></span><span class=\"ml10\">"
	
	+"<input type='submit' class=\"btn2\" value=\"下 注\" name='substraight' id='substraight'/>"
	  
	+"</span></div></td></tr></tbody></table>";	     
		   
   
      form.append(div);  
      $("#ajaxform",frameDocument('leftFrame')).show();
      $("#price",frameDocument('leftFrame')).focus();
		      

}

		    
$(document).ready(function() {
		
	
	 $("#subBet").click(function() {
		displayBetInfoOnLeft();
		
	});
	$("#checkTable input[name='checkType']").click(function() {
		$("#subBet").attr("disabled", "");
		
		 $("#betform input[type='checkbox']").attr("checked",false); 
		 $("#betform input[type='checkbox']").attr("disabled",false); 
		$("input[name='BALL']").show();		
		
	}); 
	
	$("#betTable input[name='BALL']").click(function() {
		var  radioType=$("#checkTable :input[name='checkType']:checked").val();
		var size=10;
		if(radioType=='RX4'||radioType=='RX5')
			size=8;
		if($("#betform input[name='BALL']:checked").size()==size)
			$("#betform input[name='BALL']:[checked=false]").attr('disabled',true);
		else if($("#betform input[name='BALL']:checked").size()<size)
			$("#betform input[name='BALL']:[checked=false]").attr('disabled',false);
		
	}); 
	
	$("#res").click(function() {
			$("#checkTable,input[name='BALL']:[checked=true]").attr('checked',false);
			$("#checkTable, input[name='BALL']:[checked=false]").attr('disabled',false);
	}); 
	
	
	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        url:       context+ '/nc/submitBetLM.json',          
		        type:      'post',       
		        dataType:  'json'       		     
		    }; 
	
	 var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  	 
		        url:      context+ '/nc/submitBetLM.json', 
		        type:      'post',        
		        dataType:  'json'       		     
		        
		    }; 
		 
		
		    
		    $("#prekj").find('span').each(function(i){ var num=$(this).text();
			 if(num<10)
				 num="0"+num;
			    var cls="NC_"+num;
			    $(this).text("");
				 $(this).addClass(cls);});

});

function validate(formData, jqForm, options)
{
	var price=jqForm[0].price.value;
	     return true; 
}
 
function showResponse(jsonData, statusText)  { 
  
	$('#betform input[type=text]').each(function(){$(this).val("");});  
	$(":input[name='BALL']").hide();
	if(jsonData.errorCode != 0 )
	{
	alert(jsonData.errorMsg);
	 $("#substraight").attr("disabled",false);
    //return false;
	}
	else if(jsonData.data.success)
	{
	showReponseCallBack();
    form = $("#ajaxhiddenform",frameDocument('leftFrame'));
    form.html(jsonData.data.success);
    //add by peter
    var tokenHtml = getTokenHtmlByAjax();
    form.append(tokenHtml);
    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.data.remainPrice);
	}
	$('#betform')[0].reset();
} 

function C(arr, num){
    var r=[];
    (function f(t,a,n){
        if (n==0) return r.push(t);
        for (var i=0,l=a.length; i<=l-n; i++){
            f(t.concat(a[i]), a.slice(i+1), n-1);
        }
    })([],arr,num);
    return r;
}
window.onload=function(){  
    GetRTime();  
} ;
function changePrice(price)
{
	  var tmptxt=price.val();     
	  price.val(tmptxt.replace(/\D|^0/g,'')); 
       if(tmptxt>canBetPrice) 
       {
    	   price.val(canBetPrice);
       	
       }
       if(price.val().length==0)
       	 $('#betSum',frameDocument('leftFrame')).html(0);
       	 else
        $('#betSum',frameDocument('leftFrame')).html(eval(price.val())*eval(comSize));
}

function loadDynicDateExceptBetTable(type, LT) {
	// 获取盘期信息
	getGDPeriodsInfoAsync(type, 0, false, true);
}

function refreshExceptBetTable() {
	loadDynicDateExceptBetTable(subType, lottterType);
}

function getNCAJAXOtherAssist(type, LT, onlyM) {
	var ret = null;
	var strUrl = context + '/getSomeOtherAssist.json?type=' + type
			+ "&LT=" + LT + "&onlyMarquee=" + onlyM + "&time="
			+ new Date().getTime();
	;
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({
		url : queryUrl,
		async : true,
		dataType : "json",
		type : "POST",
		success : function(json) {
			if(json){
				ret = json.data;
				onOtherAssistSuccess(json.data);
			}
		}
	});
	return ret;

} 

function onOtherAssistSuccess(json) {
	if (null != json) {
		if (null != json.todayWin && json.todayWin != undefined) {

			$("#todayWin").html("今天輸贏：" + json.todayWin);
		} else {
			$("#todayWin").html("今天輸贏：");
		}

		if (json.availabalCred) {
			$("#remainMoney", frameDocument('leftFrame')).text(
					json.availabalCred);
		}
		// add by peter
		if (json.totalCredit) {
			$("#totalCredit", frameDocument('leftFrame'))
					.text(json.totalCredit);
		}
		$("#marq", frameDocument("DownFrame")).html(json.marquee);
	}
}
