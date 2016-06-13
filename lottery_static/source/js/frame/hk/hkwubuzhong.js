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
	        		 $('#betSum').html(eval($(this).val())*eval(comSize));
	        		 }
		}); */
	
	
       $("#betTable input[type='checkbox']").click(function() {
    	   
    	   var checkSize=$("#betTable input[type='checkbox']:checked").size();
    	   if(checkSize==8)
    			$("#betform input[type='checkbox']:[checked=false]").attr('disabled',true); 
    	   else if(checkSize<8)
    		   $("#betform input[type='checkbox']:[checked=false]").attr('disabled',false); 
		
	
		
		
	
	
		
	});  
	
	 $("#res").click(function() {
		 $("#betform input[type='checkbox']").attr('disabled',false); 
	});
	
	
	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 		 
		        url:      context+'/member/ajaxhkWBZSub.do',
		        type:      'post',        
		        dataType:  'json'         
		    }; 	
	var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 	 
		        url:      context+'/member/ajaxhkWBZSub.do', 
		        type:      'post',
		        dataType:  'json'	     
		    }; 
		 
		/*    // bind to the form's submit event 
		    $('#ajaxform').submit(function() {
		    	
		    	var value=$('#price').val();
		    	if(!CheckUtil.isPlusInteger(value))
		    	{
		    		alert("请输入正确的金额");
		    		return false;
		    	}
		    	var parameter="";
				$("#ajaxform input[name=BALL]").each(function() {
					
					parameter=parameter+"@"+$(this).val();
		

				});
				var combinSize=$('#combinSize').val();
				parameter=parameter+"&type=HK_WBZ&combinSize="+combinSize+"&price="+value;;
				
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
		        //$(this).ajaxSubmit(options); 
		        return false; 
		    });*/  

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
 
// post-submit callback 
function showResponse(jsonData, statusText)  { 
  
	$(":input[type=checkbox]").attr("disabled",false);
	$(":input[type=checkbox]").attr("checked",false);
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

	var selecedBall=$(":input[type=checkbox]:checked");
	var selecedSize=selecedBall.size();
	var type="五不中";
	var number=5;

	if(selecedSize<number)
   {alert('"五不中"最少選擇五個號碼');return false;}
	var balls=new   Array();
	for(var i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
	}

	
	createFormForSubmit(balls,type,number);

}

function createFormForSubmit(balls,playType,number) 
{
	
	
	var resultArr=C(balls,number);
	 var notDanBall=new Array();
	
	var betSubTypeName="複式";
	
	
	var selecedSize=$(":input[type=checkbox]:checked").size();
	 comSize=resultArr.length;	
	
	var betNum="<p>";

	var jsonOdds=getHKShopPlayOdds("HK_WBZ");
	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxhkWBZSub.do");
	    form.attr('method','post');
	   var tempball=new   Array();
	    for(var i=0;i<balls.length;i++)
	    {
	    	 var odds=$(":input[type=checkbox][value="+balls[i]+"]").parent().parent().prev().children().text();	    	 
	    	 tempball.push(odds);
	    	 ballName=balls[i];
	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"@<font color='red'>"+jsonOdds[eval(balls[i])]+"</font></span>、";
	    	 ballValue = ("<input type='hidden' name='BALL'  value='"+balls[i]+"'/>");
	    	 //ballValue.attr('value',balls[i]);
	    	 form.append(ballValue);
	    }  
	    tempball.sort(function(x,y){return (x - y);});
	    var odds=tempball[0];
	    betNum=betNum+"</p>";
	    hidOdds = ('<input type=hidden id="odds" name="odds" value='+odds+' />');
	    form.append(hidOdds);
	    var comb = ("<input type='hidden' name='combinSize' id='combinSize' value='"+comSize+"'/>");
	    //comb.attr('value',comSize);
	    form.append(comb);
	    inputBetType = ("<input type='hidden' name='checkType' id='checkType' value='HK_WBZ'/>");
	    form.append(inputBetType);
	    //var json=ajaxGetTopWinPrice("HK_WBZ");
	    canBetPrice=Math.floor(jsonOdds.betQuotas/comSize);
	    var winQuto=jsonOdds.winQuatas;
	var div=("<div class='p'>");
	div=div+(""
    +"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
    +" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
    +"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+playType+"</span> </p>"
    +"<p class=\"xzmz blue\">下注号碼明细</p>"
    +betNum
    +"<p>您共选择了<span class=\"red\">"+selecedSize+"</span>个号碼</p>"
    +" <p>“"+betSubTypeName+"” 共分为<span class=\"red\">"+comSize+"</span>组</p>"
    +"<p>每组最高可下注金額<span class=\"red\">"+canBetPrice+"</span>元</p>"
    +"</td></tr>"
    +"<tr><td class=\"l_color\">每注金額</td><td>"
    +"<input type='text' size=\"8\" maxlength=\"9\" class=\"xz\" name='price' id='price'    ondragenter='return false' />"    
    +"</td></tr><tr><td width=\"34%\" class=\"l_color\">下注合计</td><td width=\"66%\"><span class=\"red\" id=\"betSum\">0</span></td></tr>"
    +"<tr><td class=\"l_color\">最高派彩</td><td>"+winQuto+"</td></tr>"
	+"<tr><td colspan=\"2\" class=\"l_color\"><div class=\"tj\" style=\"margin:8px auto;\"><span><input type=\"button\" name=\"\" class=\"btn2\" value=\"取 消\" id=\"retbtn\"></span><span class=\"ml10\">"	
	+"<input type='submit' class=\"btn2\" value=\"下 注\" name='substraight' id='substraight'/>"	  
	+"</span></div></td></tr></tbody></table>");	     		    
    form.append(div); 
    $("#price",frameDocument('leftFrame')).focus();
		      

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
    GetHKRTime();  
}; 
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
        		 $('#betSum',frameDocument('leftFrame')).html(eval(price.val())*eval(comSize));
        		 }
}