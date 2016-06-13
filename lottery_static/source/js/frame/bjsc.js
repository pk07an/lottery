function loadDynicDate(type, LT, showSound) {
	form = $("#ajaxform", frameDocument('leftFrame'));
	//form.html("");
	beforePageLoadSuccess();
	// 获取盘期信息
	getBJPeriodsInfoAsync(type, 0, true, showSound);
	// 获取赔率
	getBJOddsAsync(type);

	updateBJButtomStat(LT,type);
	updateBJRightStat();

	initPageCallBack();
	// add by peter
	lotType = "BJ";
	getBJAJAXOtherAssist(type, 1, 0);

}

function updateBJButtomStat(LT,type)
{
	getButtomStat('bjsc',1);
}
function updateBJRightStat(type,LT)
{
	RightStat('bjsc');
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
	    var cls="No_000"+num;
	    $(this).text("");
		 $(this).addClass(cls);
	 
   });	


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
				
				if(CheckUtil.isPlusInteger(vl)&&vl.length!=0)
				   sum=eval(sum)+eval(vl);
				else
				{
					alert("输入非法字符");return false;
				}
				parameter=parameter+"*"+ betPrices[i].name+"@"+betPrices[i].value;
			}
			//alert(sum);
			/*if(sum>accountRemainPrice)
				{alert("投注超过账户总額"); return false;}*/
			var json=getShopPlayOdds(parameter,"BJ");
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
						        url:       context+'/bjsc/submitBet.json',
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
	  
	 
	 
	 $("table[name='betTable'] a").click(function() {
		
		    
		 if($(this).text()=='-')
		    	return false;
			var odd=$(this).text();
			var name=$(this).parent().prev().text();
			 quickBetCallBack();
			if(ballNum=="3456"||ballNum=="78910"||ballNum=="gyzh")
			{
			 name=$(this).parent().prev().html();
			
			 if(name.indexOf("00010")!=-1) name="10";
			 else if(name.indexOf("0001")!=-1) name="1";
			 else if(name.indexOf("0002")!=-1) name="2";
			 else if(name.indexOf("0003")!=-1) name="3";
			 else if(name.indexOf("0004")!=-1) name="4";
			 else if(name.indexOf("0005")!=-1) name="5";
			 else if(name.indexOf("0006")!=-1) name="6";
			 else if(name.indexOf("0007")!=-1) name="7";
			 else if(name.indexOf("0008")!=-1) name="8";
			 else if(name.indexOf("0009")!=-1) name="9";
				
			}
			
			
			var inputTd=$(this).parent().next();
			var inputName=$(inputTd.html()).attr("name");
			
			if(inputName.indexOf("BALL_FIRST")!=-1||inputName.indexOf("DOUBLESIDE_1_")!=-1 )	
				name="冠軍"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_SECOND")!=-1||inputName.indexOf("DOUBLESIDE_2")!=-1)
				name="亞軍"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_THIRD")!=-1||inputName.indexOf("DOUBLESIDE_3")!=-1)
				name="第三名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_FORTH")!=-1||inputName.indexOf("DOUBLESIDE_4")!=-1)
				name="第四名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_FIFTH")!=-1||inputName.indexOf("DOUBLESIDE_5")!=-1)
				name="第五名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_SIXTH")!=-1||inputName.indexOf("DOUBLESIDE_6")!=-1)
				name="第六名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_SEVENTH")!=-1||inputName.indexOf("DOUBLESIDE_7")!=-1)
				name="第七名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_EIGHTH")!=-1||inputName.indexOf("DOUBLESIDE_8")!=-1)
				name="第八名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_NINTH")!=-1||inputName.indexOf("DOUBLESIDE_9")!=-1)
				name="第九名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BALL_TENTH")!=-1||inputName.indexOf("DOUBLESIDE_10")!=-1)
				name="第十名"+" 『 "+name+" 』";
			else if(inputName.indexOf("BJ_GROUP")!=-1)
				name="冠亞和"+" 『 "+name+" 』";
			
			createSubmitForm(inputName,name,odd);
	
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
	 
	 	  
	      parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        url:       context+'/bjsc/submitBet.json',          
		        type:      'post',       
		        dataType:  'json'       		     
		    }; 
	 		
	 		/*
	       var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,	 
		        url:       context+'/member/ajaxBJSCSub.do',
		        type:      'post',
		        dataType:  'json'		     
		    }; 
		 
	       
		    $('#betform').submit(function() { 
		        $(this).ajaxSubmit(options); 
		        return false; 
		    }); 
	 		*/
		    
		    //setStopLottery('BJ');
		
	 
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
	getBJPeriodsInfoAsync(type, 0, false, true);
}
function refreshExceptBetTable(){
	loadDynicDateExceptBetTable(subType,lottterType);
}

function updateBJButtomStatByJson(gydx, gyds, gyh) {
	if ($("#ballStat").length > 0) {
		var html = [], h = -1;
		html[++h] = '<table cellspacing="0" cellpadding="0" border="0" width="100%" class="ball t_line" id="tab">';
		html[++h] = '<tbody><tr>';
		html[++h] = '<th width="33%"><a href="javascript:void(0)" >冠、亚军和</a></th>';
		html[++h] = '<th width="33%"><a href="javascript:void(0)" >冠、亚军和 大小</a></th>';
		html[++h] = '<th width="34%"><a href="javascript:void(0)" >冠、亚军和 單雙</a></th>';

		html[++h] = '</tr>';
		html[++h] = '<tr><td colspan="6">';

		html[++h] = drawBottomPanelTable(gyh, "tab3");
		html[++h] = drawBottomPanelTable(gydx, "tab4");
		html[++h] = drawBottomPanelTable(gyds, "tab5");
		html[++h] = '</td></tr>';
		html[++h] = '</tbody></table>';

		$("#ballStat").html(html.join(''));
		bottomStateHandle();

	}
}

function drawBottomPanelTable(json, tab) {
	var html = [], h = -1;
	html[++h] = '<table cellspacing="0" cellpadding="0" border="0" width="100%" id="'+ tab + '" >';
	html[++h] = '<tbody>';
	html[++h] = '<tr dir="rtl">';
	html[++h] = '<td width="4%">';
	var count = 1;
	var j = 1;
	var perData = 0;
	$.each(json, function(i, n) {
		if (j == 1) {
			perData = n;
		}
		if (count == 25) {
			return false;
		}
		if (perData == n) {
			html[++h] = n + "<br/>";
		} else {
			count++;
			html[++h] = "</td>";
			if (count % 2 == 0) {
				html[++h] = "<td class='even'  width='4%'>";
			} else {
				html[++h] = "<td width='4%'>";
			}
			html[++h] = n;
			html[++h] = "</br>";
		}
		perData = n;
		j++;
	});
	html[++h] = "</td>";
	html[++h] = "</tr>";
	html[++h] = "</tbody>";
	html[++h] = "</table>";
	return html.join('');
}

function bottomStateHandle() {
	$("#tab th:first").addClass("current");
	$("#tab table:gt(0)").hide();
	$("#tab th").live(
			'click',
			function() {
				$(this).addClass("current").siblings("th").removeClass();
				$("#tab table:eq(" + $(this).index() + ")").show().siblings("table").hide();
			});
	$("#tab table>tbody>tr").each(function(index) {

		var tdLen = 25 - $(this).find("td").length;
		for ( var i = 0; i < tdLen; i++){
			$(this).append("<td width=\"4%\">&nbsp;</td>");
		}
		reverseTR($(this), true);
	});
}

function drawRightPanelTable(json){
	var html = [], h = -1;
	html[++h] = '<table cellspacing="0" cellpadding="0" border="0" width="100%" class="long" >';
	html[++h] = '<tbody>';
	html[++h] = '<tr>';
	html[++h] = '<td colspan="2">兩面長龍排行</td>';
	html[++h] = '</tr>';
	// 动态生成右边长龙统计框
	$.each(json, function(i, n) {
		var subTypeName = n.subTypeName;
		var finalTypeName = n.finalTypeName;
		var typeCode = n.typeCode;
		var playSubType = n.playSubType;
		var playFinalType = n.playFinalType;
		var typeCount = n.count;
		if (typeCount >= 2) {
			// alert(subTypeName+"||"+finalTypeName+"||"+typeCode+"||"+playSubType+"||"+playFinalType+"||"+typeCount);
			if (typeCode.indexOf("BJ") != -1) {
				if ("GROUP" == playSubType) {
					if ("DA" == playFinalType || "X" == playFinalType
							|| "DAN" == playFinalType
							|| "S" == playFinalType)
						subTypeName = "";
				}
			}
			if (subTypeName != null) {
				if (subTypeName.indexOf("一") != -1) {
					subTypeName = subTypeName.replace("一", "1");
				} else if (subTypeName.indexOf("二") != -1) {
					subTypeName = subTypeName.replace("二", "2");
				} else if (subTypeName.indexOf("三") != -1) {
					subTypeName = subTypeName.replace("三", "3");
				} else if (subTypeName.indexOf("四") != -1) {
					subTypeName = subTypeName.replace("四", "4");
				} else if (subTypeName.indexOf("五") != -1) {
					subTypeName = subTypeName.replace("五", "5");
				} else if (subTypeName.indexOf("六") != -1) {
					subTypeName = subTypeName.replace("六", "6");
				} else if (subTypeName.indexOf("七") != -1) {
					subTypeName = subTypeName.replace("七", "7");
				} else if (subTypeName.indexOf("八") != -1) {
					subTypeName = subTypeName.replace("八", "8");
				} else if (subTypeName.indexOf("九") != -1) {
					subTypeName = subTypeName.replace("九", "9");
				} else if (subTypeName.indexOf("十") != -1) {
					subTypeName = subTypeName.replace("十", "10");
				}
			}

			html[++h] = '<tr>';
			html[++h] = '<td width="50%" class="lf">';
			if (subTypeName != null && subTypeName.length != 0) {
				html[++h] = subTypeName + "-";
			}
			html[++h] = finalTypeName;
			html[++h] = '</td>';
			html[++h] = '<td width="20%" class="rg">' + typeCount
					+ ' 期</td>'
			html[++h] = '</tr>';
		}

	});

	html[++h] = '</tbody>';
	html[++h] = '</table>';

	$("#bjright").html(html.join(''));
}

function getBJAJAXOtherAssist(type, LT, onlyM) {
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
