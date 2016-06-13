﻿﻿
function loadDynicDate(type,LT,showSound)
{
	form = $("#ajaxform",frameDocument('leftFrame'));
 	//form.html("");
 	beforePageLoadSuccess();
	// 获取盘期信息
	getGDPeriodsInfoAsync(type, 0, true, showSound);
	// 获取赔率
	getGDOddsAsync(type);
 
	updateGDButtomStat(LT,type);
	updateGDRightStat(LT,type);
	initPageCallBack();
	 
	// add by peter
	lotType="GD";
	getGDAJAXOtherAssist(type,1,0);
}

function updateGDButtomStat(LT,type)
{
	getButtomStat('gdklsf',type);
}
function updateGDRightStat(LT,type)
{
	RightStat('gdklsf',type);
}

function fp()
{
	  $("#betform .red").html("-");
	    $("td[name='inputPrice']").html("封盤中");
	    $("#betSubmit").attr("disabled", true);
	    $("#reset").attr("disabled", true);	
	    // add by peter for batch bet fp reset
	    batchBetFPEvent();
}
function kp()
{
	window.parent.mainFrame.location.reload();
	//alert("开奖时间已经到了");  
	//window.location;
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
	    var cls="No_"+num;
	    $(this).text("");
		 $(this).addClass(cls);});

}

$(document).ready(function() {
	
	 $("#betSubmit").click(function() {
		 	//add by peter for batchBet start
		 	var batchBetSubmitFlag = batchBetSubmit();
		 	if(!batchBetSubmitFlag){
		 		return false;
		 	}
		 	//add by peter for batchBet end
			var betPrices=$("table td[name='inputPrice'] :input[type='text'][value!='']");
			var parameter="";
			if(betPrices.size()==0)
			{
				alert("请输入投注金額");
				return false;
			}
			var sum=0;
			
			for(var i=0;i<betPrices.size();i++)
			{
				var vl=CheckUtil.Trim(betPrices[i].value);
								
				if(CheckUtil.isPlusInteger(vl)&&vl.length!=0)
				   sum=eval(sum)+eval(vl);
				else
				{
					alert("输入非法字符");return false;
				}
				parameter=parameter+"*"+ betPrices[i].name+"@"+betPrices[i].value;

			}
			/*if(sum>accountRemainPrice)
			{	
				alert("投注超过账户总額"); return false;
			}*/
			 var json=getShopPlayOdds(parameter,"GDKLSF");
			if(json==null){alert("系统错误 请联系管理员");return false;}
			else if(json.errorMessage){alert(json.errorMessage);return false;}	
			else if(json.message) {
				if(confirm(json.message))
				{
					//$("#betform").submit();
					 var options = { 
						        beforeSubmit:  validate, 
						        success:       showResponse,  		 
						        url:       context+'/gdklsf/submitBet.json',
						        type:      'post', 
						        dataType:  'json'   		     
						    }; 
					 var tokenHtml = getTokenHtmlByAjax();
					 $("#betform").append(tokenHtml);
					 $("#betform").ajaxSubmit(options); 
					afterBetSubmit();
				}
			} 
		});
	  
	 
	 $(".king tr td input").keyup(function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(/\D|^0/g,''));     
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(/\D|^0/g,''));     
	    }).blur(function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(/\D|^0/g,''));     
	    }).css("ime-mode", "disabled");    
	 
	 $("table[name='betTable'] a").click(function() {
		 if($(this).text()=='-')
		    	return false;
		 quickBetCallBack();
			var odd=$(this).text();
			var name=$(this).parent().prev().text();
			
			if(name == ""){
				name=$(this).parent().prev().children().attr("class");
			}
			var nameArray = name.split("_");
			if(nameArray.length>=2){
				name = nameArray[1];
			}
			/*
			if(CheckUtil.IsNumber(name)&&eval(name)<10){
				name="0"+name;
			}
			*/
			name=chinaBall+" 『 "+name+" 』";	
			var inputTd=$(this).parent().next();
			var inputName=$(inputTd.html()).attr("name");
		
			createSubmitForm(inputName,name,odd); 
			
	
		});
	 
	 	
	 
	      parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        url:       context+'/gdklsf/submitBet.json',          
		        type:      'post',       
		        dataType:  'json'       		     
		    }; 
	      /*
	        var options = { 
			        beforeSubmit:  validate, 
			        success:       showResponse,  		 
			        url:       context+'/member/ajaxklsfBallSub.do',
			        type:      'post', 
			        dataType:  'json'   		     
			    }; 
	       
	    
		    // bind to the form's submit event 
		    $('#betform').submit(function() { 
		        $(this).ajaxSubmit(options); 
		        return false; 
		    }); 
	 */
		    
		    //setStopLottery('GDKLSF');
	      	batchBetInit();
		    initBatchBetTips();
		  
	
});
function validate(formData, jqForm, options)
{	
	if(jqForm[0].price)
	{		
		if(jqForm[0].price.value.length==0)
		{
			alert("请输入投注金額");
			return false;
		}				
	}	
	     return true; 
	
}

function showResponse(jsonData, statusText)  { 
  
	
	$('#betform input[type=text]').each(function(){$(this).val("");});  
	if(jsonData.errorCode != 0 )
	{		
	 alert(jsonData.errorMsg);	 
	 $("#substraight",frameDocument('leftFrame')).attr("disabled",false);
     // return false;
	}
	else if(jsonData.data.success)
	   {
	   showReponseCallBack();
    form =$("#ajaxhiddenform",frameDocument('leftFrame'));
    form.html(jsonData.data.success);
    //add by peter
    var tokenHtml = getTokenHtmlByAjax();
    form.append(tokenHtml);
    //add by peter
    var subType=$("#subType",frameDocument('mainFrame')).val();
    subType = ('<input type="hidden" name="subType" value="'+subType+'"/>');
    form.append(subType);
    
    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.data.remainPrice);
	}
   afterBetSuc();
} 


function createSubmitForm(inputName,playType,odds)
{
form = $("#ajaxform",frameDocument('leftFrame'));
$("#ajaxform",frameDocument('leftFrame')).clearForm();  
var subType=$("#subType",frameDocument('mainFrame')).val();
form.html("");
form.attr('action',"ajaxklsfBallSub.do");
form.attr('method','post');
subType = ('<input type="hidden" name="subType" value="'+subType+'"/>');
hidOdds = ('<input type=hidden id="odds" name="odds" value='+odds+' />');
inputBetType = ("<input type='hidden' name='checkType' value='"+inputName+"' id='checkType' />");
cacheOdds = ("<input type='hidden' name='cachedOdd' value='{"+inputName+":"+odds+"} '/>");
form.append(cacheOdds);
form.append(inputBetType);
form.append(hidOdds);
form.append(subType);
//add by peter
var tokenHtml = getTokenHtmlByAjax();
form.append(tokenHtml);
var json=ajaxGetTopWinPrice(inputName);
var winQuto=json.winQuatas;
var div="<div class='p'>"
+"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
+" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
+"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+playType+"</span> @ <strong class=\"red\">"+odds+"</strong></p>"
+"</td></tr>"
+"<tr><td class=\"l_color\">每注金額</td><td>"
+"<input type='text' size=\"8\" maxlength=\"9\" class=\"xz\" name='"+inputName+"' id='price'   onpaste=\"var s=clipboardData.getData('text'); if(!/\D/.test(s)) value=s.replace(/^0*/,''); return false;\" ondragenter='return false' />"
+"</td></tr><tr><td width=\"34%\" class=\"l_color\">可赢金額</td><td width=\"66%\"><span class=\"red\" id=\"betSum\">0</span></td></tr>"
+"<tr><td class=\"l_color\">最高派彩</td><td>"+winQuto+"</td></tr>"
+"<tr><td colspan=\"2\" class=\"l_color\"><div class=\"tj\" style=\"margin:8px auto;\"><span><input type=\"button\" name=\"\" class=\"btn2\" value=\"取 消\" id=\"retbtn\"></span><span class=\"ml10\">"
+"<input type='submit' class=\"btn2\" value=\"下 注\" name='substraight' id='substraight'/>"
+"</span></div></td></tr></tbody></table>";	       
form.append(div); 
$("#price",frameDocument('leftFrame')).focus();
}
window.onload=function(){  
    GetRTime();  
};
function changePrice(price)
{
	//alert(price.val());
	var tmptxt=price.val();
	 // alert(tmptxt);
	  var odd=$("#odds",frameDocument('leftFrame')).val();
	  price.val(tmptxt.replace(/\D|^0/g,'')); 
      if(tmptxt>accountRemainPrice) 
      {
    	  price.val(accountRemainPrice);
      	
      }
      if(price.val().length==0)
      	 $('#betSum',frameDocument('leftFrame')).html(0);
      else
      {
      		 var mm=eval(price.val())*eval(odd)-eval(price.val());   		 
             $('#betSum',frameDocument('leftFrame')).html(Math.round(mm*10)/10);
      }
}



function loadDynicDateExceptBetTable(type,LT)
   {
	// 获取盘期信息
	getGDPeriodsInfoAsync(type, 0, false, true);
}

function refreshExceptBetTable(){
	loadDynicDateExceptBetTable(subType,lottterType);
}

function getGDAJAXOtherAssist(type, LT, onlyM) {
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