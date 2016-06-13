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

var SX = {"SHU": "鼠", "HU": "虎","LONG": "龍","MA": "马","HOU": "猴","GOU": "狗","NIU": "牛","TU": "兔","SHE": "蛇","YANG": "羊","JI": "雞","ZHU": "豬"};
$(document).ready(function() {

$("#qsjq").click(function() {
	$("#zhu,#gou,#ji,#yang,#ma,#niu").attr("disabled",false);
	$("#zhu,#gou,#ji,#yang,#ma,#niu").attr("checked",true);
	$("#she,#long,#tu,#hu,#shu,#hou").attr("disabled",true).attr("checked",false);
	
});
$("#qsys").click(function() {
	$("#she,#long,#tu,#hu,#shu,#hou").attr("disabled",false);	
	$("#she,#long,#tu,#hu,#shu,#hou").attr("checked",true);	
	$("#zhu,#gou,#ji,#yang,#ma,#niu").attr("disabled",true).attr("checked",false);;
});

$("#reset").click(function() {
	$("#zhu,#gou,#ji,#yang,#ma,#niu").attr("disabled",false);
	
	$("#she,#long,#tu,#hu,#shu,#hou").attr("disabled",false);
	$("#zhu,#gou,#ji,#yang,#ma,#niu").attr("checked",false);
	$("#she,#long,#tu,#hu,#shu,#hou").attr("checked",false);
	
});


});


$(document).ready(function() {	
	initPageBall();
	$("#subBet").click(function() {		
		displayBetInfoOnLeft();		
	});

	  $("#betTable input[type='checkbox']").click(function() {  	   
   	   var checkSize=$("#betTable input[type='checkbox']:checked").size();
   	   if(checkSize==6)
   			$("#betform input[type='checkbox']:[checked=false]").attr('disabled',true); 
   	   else if(checkSize<6)
   		   $("#betform input[type='checkbox']:[checked=false]").attr('disabled',false); 	
	  });  
	
	
	
/*	 $('#price').live('keyup', function() {
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
		}); */
	
	 $("#res").click(function() {
		 $("#betform input[type='checkbox']").attr('disabled',false); 
	});

	 
	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 		 
		        url:      context+'/member/ajaxhkLXSub.do',
		        type:      'post',        
		        dataType:  'json'         
		    }; 		 
	var options = { 
		        beforeSubmit:  validate,
		        success:       showResponse,  	 
		        url:      context+'/member/ajaxhkLXSub.do',
		        type:      'post',
		        dataType:  'json'  		     
		        
		    }; 
		 
		   /* // bind to the form's submit event 
		    $('#ajaxform').submit(function() { 
		    	
		    	var value=$('#price').val();
		    	
				 var json=getShopPlayOdds("HK_LX"+"@"+value);
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
  
	 $("#betform input[type='checkbox']").attr("checked",false); 
	 $("#betform input[type='checkbox']").attr("disabled",false); 
		if(jsonData.errorMessage)
		{
		alert(jsonData.errorMessage);
		 $("#substraight").attr("disabled",false);
	    return false;
		}
		
	    form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html(jsonData.success);
	    $("#remainMoney",frameDocument('leftFrame')).text(jsonData.remainPrice);
} 

function displayBetInfoOnLeft()
{

	var selecedBall=$(":input[type=checkbox]:checked");
	var selecedSize=selecedBall.size();
	var type="六肖";
	var number=6;

	if(selecedSize<number)
   {alert('六肖需要選擇六個號碼');return false;}
	var balls=new   Array();
	for(var i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
	}
	odds=$("#HK_LX").html();
	createFormForSubmit(balls,type,number,odds);

	

}

function createFormForSubmit(balls,playType,number,odds) 
{
    var selecedSize=$(":input[type=checkbox]:checked").size();
	var betNum="<p>";
	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxhkLXSub.do");
	    form.attr('method','post');
	    hidOdds = ('<input type=hidden id="odds" name="odds" value='+odds+' />');
	    form.append(hidOdds);
	    inputBetType = ("<input type='hidden' name='checkType' id='checkType' value='HK_LX' />");
	    form.append(inputBetType);
	    for(var i=0;i<balls.length;i++)
	    {
	    	 ballName=balls[i];	    	
	    	 betNum=betNum+"<span class=\"l_blue\">"+SX[ballName]+"</span>、";
	    	 ballValue = ("<input type='hidden' name='BALL' value='"+balls[i]+"' />");
	    	 //ballValue.attr('value',balls[i]);
	    	 form.append(ballValue);
	    }  

	    betNum=betNum+"</p>";
	
	    var json=ajaxGetTopWinPrice("HK_LX");

		var winQuto=json.winQuatas;    
	var div=("<div class='p'>");
	div=div+(""
    +"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
    +" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
    +"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+playType+"</span> @ <strong class=\"red\">"+odds+"</strong></p>"
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
} ;
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

function initPageBall()
{
	$("#shuball").html(changeNumToBall(shu));
	$("#niuball").html(changeNumToBall(niu));
	$("#huball").html(changeNumToBall(hu));
	$("#tuball").html(changeNumToBall(tu));
	$("#longball").html(changeNumToBall(lon));
	$("#seball").html(changeNumToBall(se));
	$("#maball").html(changeNumToBall(ma));
	$("#yangball").html(changeNumToBall(yang));
	$("#houball").html(changeNumToBall(hou));
	$("#jiball").html(changeNumToBall(ji));
	$("#gouball").html(changeNumToBall(gou));
	$("#zhuball").html(changeNumToBall(zhu));
	

}
