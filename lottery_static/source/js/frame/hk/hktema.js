

function loadDynicDate(type,LT)
{
form = $("#ajaxform",frameDocument('leftFrame'));
form.html("");
 var json=getHKAJAXRealodd(type,2);
 var realOdd=json.realOdd;
 var runningPeriods=json.runningPeriods;
 if(runningPeriods=="no")
 {
	 window.parent.mainFrame.location=context+"/html/hknotkp.html"; 	
	 return false;
 }
 var fpTime=runningPeriods.FPTime;
 hkfpDate=fpTime;

 var runningPeriodNum=runningPeriods.PeriondsNum;
 period=runningPeriodNum;
 $("#runningperiods").html(runningPeriodNum);
 $.each(realOdd,function(name,value){		
			$("#"+name).text(value);	
		}) ;

 
var json2=getHKAJAXOtherAssist(type,1,0);	
 
 $("#todayWin").html("今天輸贏："+json2.todayWin);
 $("#marq",frameDocument("DownFrame")).html(json2.marquee);

}


$(document).ready(function() {
		
	$("#quicksubmit").click(function() {	  
		form = $("#quickAjaxForm");
		form.html("");
		tab=$("#TAB").val();
		form.attr('action',"ajaxhkTMSub.do");
		form.attr('method','post'); 
		abType = $('<input type=hidden name="TAB" value='+tab+' />');
		form.append(abType);
	   var fillValue=$("#qsrmb").val();
	  if(!CheckUtil.isInteger(fillValue))
		{
		   alert("請輸入正確的投注金額");
		   return false;
		} 
	 
		var td=$("#qsnum").find("tr td");
		var selsize=0;
		$.each(td,function(){			
			if($(this).hasClass('selectnumber'))
	         {		
				selsize++;
				var ballNum=eval($(this).html());
				var name="HK_TM_"+ballNum+"_"+tab;
				selectBall = $("<input type=hidden  name="+name+" value="+fillValue+" />");
				form.append(selectBall);

			 }  			 	
		}
		
		);
		
		if(selsize==0)
		{
			alert("請選擇投注號碼！");
			return false;
		}
		form.submit();	
		$.unblockUI();
	});
	
	
	 $("#tmSubmit").click(function() {
			//alert("test");
			var betPrices=$("#betBallTable input[type='text'][value!=''] ,#betDoubleTable input[type='text'][value!='']");
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
			if(sum>accountRemainPrice)
			{	alert("投注超过账户总額");return false;}
			var json=getShopPlayOdds(parameter);
			if(json==null){alert("系统错误 请联系管理员");return false;}
			else if(json.errorMessage)
			{alert(json.errorMessage);return false;}	
			else if(json.message) {
			   if(confirm(json.message))
				{
						$("#betform").submit();
				}
			   else{return false;}
			}
		});
	  

 
	 $("#betBallTable a,#betDoubleTable a").click(function() {
		 
			var tab=$('#TAB').val();
			var playName="特碼A";
			if(tab=='B')
				playName="特碼B";
		    var odd=$(this).text();
			var name=$(this).parent().prev().text();
			if(CheckUtil.IsNumber(name))
			{	if(eval(name)<10)
				name="0"+name;
			name=playName+" 『 "+name+" 』";
			}

			var inputTd=$(this).parent().next();
			var inputName=$(inputTd.html()).attr("name");
		
			createSubmitForm(inputName,name,odd); 
			
	
		});
	 
	  parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        url:       context+'/member/ajaxhkTMSub.do',          
		        type:      'post',       
		        dataType:  'json'       		     
		    }; 
	 	 	
	 var options = { 		      
		        beforeSubmit:  validate,
		        success:       showResponse, 	 
		        url:        context+'/member/ajaxhkTMSub.do',     
		        type:      'post',        
		        dataType:  'json'       
		    }; 

		    $('#betform').submit(function() { 
		        $(this).ajaxSubmit(options); 
		        return false; 
		    }); 
		    $('#quickAjaxForm').submit(function() { 
		    	
		        $(this).ajaxSubmit(options); 
		        $("#substraight").attr("disabled",true);
		        return false; 
		    }); 
		    
	
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
	
	var queryString = $.param(formData); 
	 
	     return true; 
	
}
function showRequest(formData, jqForm, options) { 
    
    var queryString = $.param(formData);  
     return true; 
} 
 
// post-submit callback 
function showResponse(jsonData, statusText)  { 
  
	$('#betform')[0].reset();
	if(jsonData.errorMessage)
	{
		
	alert(jsonData.errorMessage);
	
	if(("#substraight",frameDocument('leftFrame')))
	{
		$("#substraight",frameDocument('leftFrame')).attr("disabled",false);
	}
	else
		$("#substraight").attr("disabled",false);
    return false;
	}
	
    form = $("#ajaxform",frameDocument('leftFrame'));
    form.html(jsonData.success);
    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.remainPrice);
    
} 

function createSubmitForm(inputName,playType,odds)
{
form = $("#ajaxform",frameDocument('leftFrame'));
form.html("");
form.attr('action',"ajaxhkTMSub.do");
form.attr('method','post');
hidOdds = '<input type="hidden" id="odds" name="odds" value="'+odds+'" />';
form.append(hidOdds);
inputBetType = '<input type="hidden" name="checkType" value="'+inputName+'" id="checkType" />';
//inputBetType.attr('value',inputName);
form.append(inputBetType);

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
    GetHKRTime();  
} ;
function changePrice(price)
{
	  var tmptxt=price.val();
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