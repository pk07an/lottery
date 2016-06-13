function loadDynicDate(type, LT, showSound) {

	form = $("#ajaxform", frameDocument('leftFrame'));
	//form.html("");
	beforePageLoadSuccess();
	// 获取盘期信息
	getJSPeriodsInfoAsync(type, 0, true, showSound);
	// 获取赔率
	getJSOddsAsync(type);
	updatejsRightStat();
	initPageCallBack();
	lotType = "K3";
	getJSAJAXOtherAssist(type, 1, 0);
}

/*
 * 更行右边当天历史开奖信息
 */
function updatejsRightStat(LT,type)
{
	
	RightStat('jsks');
}

function drawRightPanelTable(queryList){
 	var html = [], h = -1;
	html[++h] ='<table cellspacing="0" cellpadding="0" border="0" width="100%" class="long jslong" >';
	html[++h] = '<tbody>';
	html[++h] ='<tr><td colspan="6" class="tt">近期开奖结果</td></tr>';
		if ( queryList ) {
			$.each( queryList, function(i, n) {
					html[++h] = '<tr>'
					html[++h] = '<td width="33" class="even">' + n.periodsNum + '期</td>';
					var numList = n.lotteryNums.split(',');
					
					if(numList && numList.length==3){
						html[++h] = '<td width="27"><img src="../images/4_'+numList[0]+'.gif"/></td>';
						html[++h] = '<td width="27"><img src="../images/4_'+numList[1]+'.gif"/></td>';
						html[++h] = '<td width="27"><img src="../images/4_'+numList[2]+'.gif"/></td>';
					}
					
					
					var numListSum = eval(numList[0])+eval(numList[1])+eval(numList[2]);
					html[++h] = '<td width="23">'+numListSum+'</td>';
					//如果围骰
					if(numList[0] == numList[1] && numList[1] == numList[2]){
						html[++h] = '<td width="29" class="green">通吃</td>';
					}else if(numListSum>=4 && numListSum<=10 ){//小
						html[++h] = '<td width="29">小</td>';
					}else if(numListSum>=11 && numListSum<=17){//大
						html[++h] ='<td width="29" class="big">大</td>';
					}
					html[++h] = '</tr>';

			});
		}
	html[++h] = '</tbody>';
	html[++h] = '</table>';
	$('#jsright')[0].innerHTML = html.join('');
}



function fp()
{
    $("#betform .red").html("-");
    $("td[name='inputPrice']").html("封盤中");
    $("#betSubmit").attr("disabled", true);
    $("#reset").attr("disabled", true);	
    
    //add by peter for batch bet fp reset
    batchBetFPEvent();
}
function kp()
{

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
	    var cls="No_0000"+num;
	    $(this).text("");
		 $(this).addClass(cls);
	 
   });	


}

$(document).ready(function() {
	
		
	$("#prekj").find('span').each(function(i){
		 if($(this).text()==19||$(this).text()==20) 
			 $(this).addClass("ball-red ball-bg");
		 else 
			 $(this).addClass("ball-blue ball-bg");
		 
	   });
	
	 $("#betSubmit").click(function() {
		 	//add by peter for batchBet start
		 	var batchBetSubmitFlag = batchBetSubmit();
		 	if(!batchBetSubmitFlag){
		 		return false;
		 	}
		 	//add by peter for batchBet end
		 	
			var betPrices=$("table td[name='inputPrice'] :input[type='text'][value!='']");
			if(betPrices.size()==0)
			{
				alert("请输入投注金額");
				return false;
			}
			var sum=0;
			var parameter="";
			for(var i=0;i<betPrices.size();i++)
			{
				var vl=CheckUtil.Trim(betPrices[i].value);
				
				if(CheckUtil.isPlusInteger(vl)&&vl.length!=0){
				   sum=eval(sum)+eval(vl);
				}
				else
				{
					alert("输入非法字符");return false;
				}
				parameter=parameter+"*"+ betPrices[i].name+"@"+betPrices[i].value;
			}
			//alert(sum);
			/*if(sum>accountRemainPrice)
				{alert("投注超过账户总額"); return false;}*/
			var json=getShopPlayOdds(parameter,"K3");
			if(json==null){alert("系统错误 请联系管理员");return false;}
			else if(json.errorMessage){alert(json.errorMessage);return false;}	
			else if(json.message)
			{
				if(confirm(json.message))
				{
					//$("#betform").submit();
					  var options = { 
						        beforeSubmit:  validate, 
						        success:       showResponse,	 
						        url:       context+'/jssb/submitBet.json',
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
	  
	 
	 
	 //peter
	 bindBetTableAEvent();

	 
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
	 
	 
	      parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        url:       context+'/jssb/submitBet.json',          
		        type:      'post',       
		        dataType:  'json'       		     
		    }; 
	 
	      /*
	       var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,	 
		        url:       context+'/member/ajaxJSSBSub.do',
		        type:      'post',
		        dataType:  'json'		     
		    }; 
		 
		    $('#betform').submit(function() { 
		        $(this).ajaxSubmit(options); 
		        return false; 
		    }); 
	 */
		    
		    //setStopLottery('K3');
		
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

// post-submit callback 
function showResponse(jsonData, statusText)  {  
  
	$('#betform input[type=text]').each(function(){$(this).val("");});  
	if(jsonData.errorCode != 0 )
	{
	alert(jsonData.errorMsg);
	 $("#substraight",frameDocument('leftFrame')).attr("disabled",false);
    //return false;
	}else if(jsonData.data.success)
	{
		showReponseCallBack();
    form = $("#ajaxhiddenform",frameDocument('leftFrame'));
    form.html(jsonData.data.success);
    //add by peter
    var tokenHtml = getTokenHtmlByAjax();
    form.append(tokenHtml);
    //add by peter
    var subType=$("#subType",frameDocument('mainFrame')).val();
    subType = ('<input type="hidden" name="subType" value="'+subType+'"/>');
    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.data.remainPrice);
	}
	afterBetSuc();
} 



function createSubmitForm(inputName,playType,odds)
{

form = $("#ajaxform",frameDocument('leftFrame'));
form.html("");
form.attr('action',"ajaxbjscBallSub.do");
form.attr('method','post');
var subType=$("#subType",frameDocument('mainFrame')).val();
subType = ('<input type="hidden" name="subType" value="'+subType+'"/>');
hidOdds = ('<input type=hidden id="odds" name="odds" value='+odds+' />');
inputBetType = ("<input type='hidden' name='checkType' id='checkType' value='"+inputName+"' />");
cacheOdds = ("<input type='hidden' name='cachedOdd' value='{"+inputName+":"+odds+"} '/>");
form.append(cacheOdds);
form.append(inputBetType);
form.append(hidOdds);
form.append(subType);
//add by peter
var tokenHtml = getTokenHtmlByAjax();
form.append(tokenHtml);
var json=ajaxGetTopWinPrice(inputName);
var winQuto="";
if(json&&json.winQuatas)
{
winQuto=json.winQuatas;
}
var div=("<div class='p'>");
div=div+(""
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
+"</span></div></td></tr></tbody></table>");

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

function loadDynicDateExceptBetTable(type, LT) {

	// 获取盘期信息
	getJSPeriodsInfoAsync(type, 0, false, true);
}
function refreshExceptBetTable(){
	loadDynicDateExceptBetTable(subType,lottterType);
}

function bindBetTableAEvent(){
	$("table[name='betTable'] a").click(function() {
		
	    
		 if($(this).text()=='-'){
		    	return false;
		 }
			var odd=$(this).text();
			var name='';
			
			 
			quickBetCallBack();
			
			
			var inputTd=$(this).parent().next();
			var inputName=$(inputTd.html()).attr("name");
			var nameArray = inputName.split('_');
			
			//alert(inputName);
			//alert(nameArray[nameArray.length-1]);
			
			if(inputName.indexOf("K3_SJ")!=-1){	
				name="三军"+" 『 "+nameArray[nameArray.length-1]+" 』";
			}
			else if(inputName.indexOf("K3_WS")!=-1){
				name="围骰"+" 『 "+nameArray[nameArray.length-1]+" 』";
			}
			else if(inputName.indexOf("K3_QS")!=-1){
				name="全骰"+" 『 全骰 』";
			}
			else if(inputName.indexOf("K3_DS")!=-1){
				name="点数"+" 『 "+nameArray[nameArray.length-1]+"点 』";
			}
			else if(inputName.indexOf("K3_CP")!=-1){
				name="长牌"+" 『 "+nameArray[nameArray.length-2]+"_"+nameArray[nameArray.length-1]+" 』";
			}
			else if(inputName.indexOf("K3_DP")!=-1){
				name="短牌"+" 『 "+nameArray[nameArray.length-2]+"_"+nameArray[nameArray.length-1]+" 』";
			}else if(inputName.indexOf("K3_DA")!=-1){
				name="三军"+" 『 大 』";
			}else if(inputName.indexOf("K3_X")!=-1){
				name="三军"+" 『 小 』";
			}
			
			
			createSubmitForm(inputName,name,odd);
	
		});
}

//异步获取其他信息
function getJSAJAXOtherAssist(type, LT, onlyM) {
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