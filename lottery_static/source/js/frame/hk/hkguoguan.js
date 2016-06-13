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
		}); 
var json2=getHKAJAXOtherAssist(type,1,0);	
 
 $("#todayWin").html("今天輸贏："+json2.todayWin);
 $("#marq",frameDocument("DownFrame")).html(json2.marquee);

}
$(document).ready(function() {
	
	$("#subBet").click(function() {
		displayBetInfoOnLeft();
		
	});

       $("#betTable input[type='radio']").click(function() {
    	   
    	   var checkSize=$("#betTable input[type='radio']:checked").size();

	});  
	
	 $("#res").click(function() {
		 $("#betform input[type='radio']").attr('disabled',false); 
	});

	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 		 
		        url:      context+'/member/ajaxhkGGSub.do',
		        type:      'post',        
		        dataType:  'json'         
		    }; 	
	var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,  
		        url:      context+'/member/ajaxhkGGSub.do', 
		        type:      'post', 
		        dataType:  'json' 		     
		    }; 
		 
		    // bind to the form's submit event 
		   /* $('#ajaxform',frameDocument('leftFrame')).submit(function() { 
		    	var value=$('#price',frameDocument('leftFrame')).val();
		    	if(!CheckUtil.isPlusInteger(value))
		    	{
		    		alert("请输入正确的金额");
		    		return false;
		    	}
		    	var parameter="";
				$("#ajaxform input[name=BALL]",frameDocument('leftFrame')).each(function() {
					
					parameter=parameter+"@"+$(this).val();
		

				});
				var combinSize=$('#combinSize').val();
				parameter=parameter+"&type=HK_GG&combinSize="+combinSize+"&price="+value;
				//alert(parameter);
				var json=getShopPlayOdds(parameter);
				if(json==null){alert("系统错误 请联系管理员");return false;}
				else if(json.errorMessage){alert(json.errorMessage);return false;}	
				else if(json.message) {
					if(confirm(json.message))
					{
						   $(this).ajaxSubmit(options); 
					        $("#substraight").attr("disabled",true);
					}
				} 
		        return false; 
		    });  */
		    
		    
		   /* $('#price').live('keyup', function() {
				  //alert("Live handler called."); 
				  var tmptxt=$(this).val(); 
				  var odd=$("#odds").val();
			        $(this).val(tmptxt.replace(/\D|^0/g,'')); 
			        if(tmptxt>accountRemainPrice) 
			        {
			        	$(this).val(accountRemainPrice);
			        	
			        }
			        if($(this).val().length==0)
			        	 $('#betSum').html(0)
			        	 else
			        	{
			        	var mm=eval($(this).val())*eval(odd)-eval($(this).val());
			        		 
			         $('#betSum').html(Math.round(mm*10)/10);
			        }
				}); 
		    */

}); 

function validate(formData, jqForm, options)
{
	var price=jqForm[0].price.value;
	var queryString = $.param(formData); 
	     return true; 
}
function showRequest(formData, jqForm, options) { 
    
    var queryString = $.param(formData); 
     return true; 
} 

function showResponse(jsonData, statusText)  { 
  
	$('#betform')[0].reset();
	if(jsonData.errorMessage)
	{
	alert(jsonData.errorMessage);
    return false;
	}
	
    form = $("#ajaxform",frameDocument('leftFrame'));
    form.html(jsonData.success);
    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.remainPrice);
} 

function displayBetInfoOnLeft()
{

	var selecedBall=$(":input[type=radio]:checked");
	var selecedSize=selecedBall.size();
	var type="過關";
	var number=2;

	if(selecedSize<number)
   {alert('最少選擇两個號碼"过关"');return false;}
	var balls=new   Array();
	for(var i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
	}

	createFormForSubmit(balls,type,number);

}

function createFormForSubmit(balls,playType,number) 
{
    var selecedSize=$(":input[type=radio]:checked").size();
	var betNum="<p>";
	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxhkGGSub.do");
	    form.attr('method','post');
	    var finalOdd=1;
	    for(var i=0;i<balls.length;i++)
	    {
	    	 ballName=balls[i];
	    	 odd=$(":input[value="+balls[i]+"]").next().text();
	    	 var maCode="";
	    	 var betCode="";
	    	 if(ballName.indexOf("ZM1")!=-1)
	    		 maCode="正碼一";
	    	 else if(ballName.indexOf("ZM2")!=-1)
	    		 maCode="正碼二";
	    	 else if(ballName.indexOf("ZM3")!=-1)
	    		 maCode="正碼三";
	    	 else if(ballName.indexOf("ZM4")!=-1)
	    		 maCode="正碼四";
	    	 else if(ballName.indexOf("ZM5")!=-1)
	    		 maCode="正碼五";
	    	 else if(ballName.indexOf("ZM6")!=-1)
	    		 maCode="正碼六";
	    	 if(ballName.indexOf("DAN")!=-1)
	    		 betCode="單";
	    	 else if(ballName.indexOf("DA")!=-1)
	    			 betCode="大";
	    	 else  if(ballName.indexOf("S")!=-1)
	    				 betCode="雙" ;
	    	 else if(ballName.indexOf("X")!=-1)
	    				 betCode="小";
	    	 else if(ballName.indexOf("RED")!=-1)
	    				 betCode="紅";
	    	 else if(ballName.indexOf("GREEN")!=-1)
	    				 betCode="綠";
	    	 else if(ballName.indexOf("BLUE")!=-1)
	    				 betCode="藍";
	    	 ballName=	maCode+"-"+betCode+"@ <strong class=\"red\">"+odd+"</strong>";
	    	 finalOdd=eval(finalOdd)*eval(odd);
	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"</span>、<br>";
	    	 ballValue = ("<input type='hidden' name='BALL' value='"+balls[i]+"' />");
	    	 //ballValue.attr('value',balls[i]);
	    	 form.append(ballValue);
	    }  
	    hidOdds = ('<input type=hidden id="odds" name="odds" value='+finalOdd+' />');
	    form.append(hidOdds);
	    inputBetType = ("<input type='hidden' name='checkType' id='checkType' value='HK_GG'/>");
	    form.append(inputBetType);
	    betNum=betNum+"</p>";
	    var json=ajaxGetTopWinPrice("HK_GG");

	    var winQuto=json.winQuatas;
	   
	var div=("<div class='p'>");
	div=div+(""
    +"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
    +" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
    +"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+playType+"</span></p>"
    +"<p class=\"xzmz blue\">下注号碼明细</p>"
    +betNum
    +"<p>您共选择了<span class=\"red\">"+selecedSize+"</span>个号碼</p>"
    +"</td></tr>"
    +"<tr><td class=\"l_color\">每注金額</td><td>"
    +"<input type='text' size=\"8\" maxlength=\"9\" class=\"xz\" name='price' id='price'   onpaste=\"var s=clipboardData.getData('text'); if(!/\D/.test(s)) value=s.replace(/^0*/,''); return false;\" ondragenter='return false' />"    
    +"</td></tr>"
    +"<tr><td width=\"34%\" class=\"l_color\">可赢金額</td><td width=\"66%\"><span class=\"red\" id=\"betSum\">0</span></td></tr>"
    +"<tr><td class=\"l_color\">最高派彩</td><td>"+winQuto+"</td></tr>"
	+"<tr><td colspan=\"2\" class=\"l_color\"><div class=\"tj\" style=\"margin:8px auto;\"><span><input type=\"button\" name=\"\" class=\"btn2\" value=\"取 消\" id=\"retbtn\"></span><span class=\"ml10\">"
	
	+"<input type='submit' class=\"btn2\" value=\"下 注\" name='substraight' id='substraight'/>"
	  
	+"</span></div></td></tr></tbody></table>");	     
		   
   
      form.append(div);  
      $("#price",frameDocument('leftFrame')).focus();
		      

}
window.onload=function(){  
    GetHKRTime();  
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