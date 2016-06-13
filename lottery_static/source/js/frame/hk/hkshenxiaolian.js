var realOdd;
var SX = {"SHU": "鼠", "HU": "虎","LONG": "龍","MA": "马","HOU": "猴","GOU": "狗","NIU": "牛","TU": "兔","SHE": "蛇","YANG": "羊","JI": "雞","ZHU": "豬"};

function loadDynicDate(type,LT)
{
form = $("#ajaxform",frameDocument('leftFrame'));
form.html("");
 var json=getHKAJAXRealodd(type,2);
realOdd=json.realOdd;
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
 
 /*$.each(realOdd,function(name,value){
			$("#"+name).text(value);	
		}) */
 changeOdd("2XL");
var json2=getHKAJAXOtherAssist(type,1,0);	
 
 $("#todayWin").html("今天輸贏："+json2.todayWin);
 $("#marq",frameDocument("DownFrame")).html(json2.marquee);

}


function changeOdd(type)
{
	$.each(realOdd,function(name,value){
		if(name.indexOf(type)!=-1)
		{
		name=name.substring(4,name.length).toLowerCase();
		$("#"+name+"_odd").text(value);
		}
		
	});

}


$(document).ready(function() {
	
	 initPageBall();
	
	$("#subBet").click(function() {
		displayBetInfoOnLeft();
		
	});
	$("#checkTable input[name='TT']").click(function() {
		
		 if($(this).attr("checked")==true)
			 $('#T1').attr("checked",true);
		 else
			 {
			 $('#T1').attr("checked",false);
			 $('#T2').attr("checked",false);
			 $('#T3').attr("checked",false);
			 $("#betform input[type='text']").val(""); 
			 
			 }		
		
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
	
	
	$("#checkTable input[name='betSubType']").click(function() {
		
		 $("#subBet").attr("disabled", "");
		
		 $("#checkTable input[type='checkbox']").attr("checked",false);
		 $("#checkTable input[type='checkbox']").val(""); 
		 $("#betform input").attr("disabled",false);
		 $("#betform input[type='text']").val(""); 
		
		 if($(this).val()=="2XL")changeOdd("2XL");
		 else if($(this).val()=="3XL")changeOdd("3XL");
		 else if($(this).val()=="4XL")changeOdd("4XL");
		 
		 
		 
		 
		
		
	}); 
	
       $(":input[name='SX']").click(function() {
    	   
    	   
    	   var checkSize=$(":input[name='SX']:checked").size();
    	  
    	   if(checkSize==8)
    		   {
    			$("#betform input[name='SX']:[checked=false]").attr('disabled',true);
    			
    		   }
    	   if(checkSize>8)
    		   return false;
    	   else if(checkSize<8)
    		   $("#betform input[name='SX']:[checked=false]").attr('disabled',false); 
		
		var  radioType=$("#checkTable :input[name='betSubType']:checked").val();
		var checked=$(this).attr("checked");
		if($(this).attr("checked")==false)
		{
			var uncheckVal=$(this).val();
			if( $('#T1').val()==uncheckVal)
			{$('#T1').attr("checked",false);$('#T1V,#T1').val("");$('#T2').attr("checked",false);$('#T2V,#T2').val("");$('#T3').attr("checked",false);$('#T3V,#T3').val("");}
			else if( $('#T2').val()==uncheckVal)
			{$('#T2').attr("checked",false);$('#T2V,#T2').val("");$('#T3').attr("checked",false);$('#T3V,#T3').val("");}
			else if( $('#T3').val()==uncheckVal)
			{$('#T3').attr("checked",false);$('#T3V,#T3').val("");}
			
		}
		
		
		if(radioType=='2XL')
		{
			 if($('#T1').attr("checked")==true&&$('#T1V').val()==""&&checked)
			 {	
				
				 $('#T1').val($(this).val());
				 $('#T1V').val($(this).parent().prev().prev().prev().text());
				 $('#T2,#T2V').attr("disabled",true);
				 $('#T3,#T3V').attr("disabled",true);
			 }
		}
		
		if(radioType=='3XL')
		{
			 if($('#T1').attr("checked")==true&&$('#T1V').val()==""&&checked)
			 {	
				 $('#T1').val($(this).val());
				 $('#T1V').val($(this).parent().prev().prev().prev().text());
			 }
			 else if($('#T2').attr("checked")==true&&$('#T2V').val()==""&&checked)
			 {	
				 $('#T2').val($(this).val());
				 $('#T2V').val($(this).parent().prev().prev().prev().text());
				 $('#T3,#T3V').attr("disabled",true);
			 }
		}
		if(radioType=='4XL')
		{
			 if($('#T1').attr("checked")==true&&$('#T1V').val()==""&&checked)
			 {	
				 $('#T1').val($(this).val());
				 $('#T1V').val($(this).parent().prev().prev().prev().text());
			 }
			 else if($('#T2').attr("checked")==true&&$('#T2V').val()==""&&checked)
			 {	
				 $('#T2').val($(this).val());
				 $('#T2V').val($(this).parent().prev().prev().prev().text());
			 }
			 else if($('#T3').attr("checked")==true&&$('#T3V').val()==""&&checked)
			 {	
				 $('#T3').val($(this).val());
				 $('#T3V').val($(this).parent().prev().prev().prev().text());
			 }
		}
	
		
	});  
	
	 $("#res").click(function() {
		 $("#betform input[name='SX']").attr('disabled',false); 
	});
	
	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 		 
		        url:      context+'/member/ajaxhkSXLSub.do',
		        type:      'post',        
		        dataType:  'json'         
		    }; 		 
	
	var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,   	
		        url:      context+'/member/ajaxhkSXLSub.do',
		        type:      'post',
		        dataType:  'json' 		     

		    }; 
		 
	/*	    // bind to the form's submit event 
		    $('#ajaxform').submit(function() { 
		    	
		    	//var typeCode=$('#price').attr("name");
		    	var value=$('#price').val();
		    	if(!CheckUtil.isPlusInteger(value))
		    	{
		    		alert("请输入正确的金额");
		    		return false;
		    	}
		    	var parameter="";
				$("#ajaxform input[name=SX]").each(function() {
					
					parameter=parameter+"@"+$(this).val();
		

				});
				var sxl=$('#checkType').val();
				var parameter=parameter+"&type=HK_SXL_"+sxl;
				var dan="";
				$("#ajaxform input[name=DAN]").each(function() {
					
					dan=dan+"&dan="+$(this).val();
		

				});
				var combinSize=$('#combinSize').val();
				var parameter=parameter+"&type=HK_SXL_"+sxl+dan+"&combinSize="+combinSize+"&price="+value;
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
		    }); */ 

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
  
	$('#betform')[0].reset();
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
	
	var radioType=$("#checkTable :input[name='betSubType']:checked").val();
	//alert(radioType);
	var radioType2=$("#checkTable :input[name='checkType2']:checked").val();
	var selecedBall=$(":input[name='SX']:checked");
	var selecedSize=selecedBall.size();
	var type="";
	var number=0;
	if(radioType==null)
		{
		alert("請選擇投注類型");
		return false;
		}
	
	if(radioType=='2XL')
	{
		number=2;
		type="二肖連中";
		if(selecedSize<2)
	   {alert('"二肖連中"最少必須選擇兩個號碼');return false;}
	}
	if(radioType=='3XL')
	{
		number=3;
		type="三肖連中";
		if(selecedSize<3)
	   {alert('"三肖連中"最少必須選擇三個號碼');return false;}
	}
	
if(radioType=='4XL')
{
	number=4;
	type="四肖連中";
	if(selecedSize<4)
   {alert('"四肖連中"最少必須選擇四個號碼');return false;}
}

	var i;
	//var num=0;
	
	
	
	var balls=new   Array();
	for(i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
	}

	
	createFormForSubmit(balls,type,number);

}

function createFormForSubmit(balls,playType,number) 
{
	
	var T=$("#TT").attr("checked")==true;
	var resultArr=C(balls,number);
	 var notDanBall=new Array();
	 var dan=new Array();
	if(T==true)
	{
		if($("#T1").attr("checked")==true&&$("#T1").val()!="")
			dan.push($("#T1").val());
		if($("#T2").attr("checked")==true&&$("#T2").val()!="")
			dan.push($("#T2").val());
		if($("#T3").attr("checked")==true&&$("#T3").val()!="")
			dan.push($("#T3").val());
	   $(":input[name='SX']:checked").each(function(i){
		  if($(this).val()!=$("#T1").val()&&$(this).val()!=$("#T2").val()&&$(this).val()!=$("#T3").val())
			  notDanBall.push($(this).val());
		  
	   });
	   
	 
	   var comsize=eval(number)-eval(dan.length);
	  // alert(comsize);
	   resultArr=C(notDanBall,comsize);
	  
	}

	var radioType=$("#checkTable input[name='betSubType']:checked").val();
	
	var betSubTypeName="複式";
	
	var selecedSize=$(":input[name='SX']:checked").size();
	 comSize=resultArr.length;	

	
	
	
	var ballOdds=new   Array();
	var betNum="<p>";

	var jsonOdds=getHKShopPlayOdds("HK_SXL_"+radioType);
	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxhkSXLSub.do");
	    form.attr('method','post');
	    var comb = ("<input type='hidden' name='combinSize' id='combinSize' value='"+comSize+"' />");
	    //comb.attr('value',comSize);
	    form.append(comb);
	    inputBetType = ("<input type='hidden' name='checkType' id='checkType' value='"+radioType+"' />");
	    
	    //inputBetType.attr('value',radioType);
	    form.append(inputBetType);
	   // var displayOdds="";
	    if(T==true)
	    {//alert("if");
			betSubTypeName="拖頭"; 
			for(var i=0;i<dan.length;i++)
		 	    {
				     var odds=$(":input[type=checkbox][value="+dan[i]+"]").parent().prev().children().text();
				     ballOdds.push(odds);
				     var ballName=SX[dan[i]];	      	 
		 	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"@<font color='red'>"+jsonOdds[dan[i]]+"</font></span>、";
		 	    	 ballValue = ("<input type='hidden' name='DAN' value='"+dan[i]+"' />");
		 	    	 //ballValue.attr('value',dan[i]);
		 	    	 form.append(ballValue);
		 	    }
		    	 betNum=betNum+"</br>拖</br>";
		    	 for(var j=0;j<notDanBall.length;j++)
		 	     {
		    		 
		 	    	var ballName=SX[notDanBall[j]];
		 	    	var odds=$(":input[type=checkbox][value="+notDanBall[j]+"]").parent().prev().children().text();
		 	    	ballOdds.push(odds);
		 	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"@<font color='red'>"+jsonOdds[notDanBall[j]]+"</font></span>、";
		 	    	 ballValue = ("<input type='hidden' name='SX' value='"+notDanBall[j]+"' />");
		 	    	 //ballValue.attr('value',notDanBall[j]);
		 	    	 form.append(ballValue);
		 	    	
		 	    }
	    }
	    else
	    {
	    for(i=0;i<balls.length;i++)
	    {
	    	 var odds=$(":input[type=checkbox][value="+balls[i]+"]").parent().prev().children().text();
	    	 ballOdds.push(odds);
	    	 ballName=SX[balls[i]];
	    	 
	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"@<font color='red'>"+jsonOdds[balls[i]]+"</font></span>、";
	    	 ballValue = ("<input type='hidden' name='SX' value='"+balls[i]+"' />");
	    	 //var ball=balls[i];
	    	
	    	// ballValue.attr('value',balls[i]);
	    	 form.append(ballValue);
	    }  
	    }
	    ballOdds.sort(function(x,y){return (x - y);});
	    var odds=ballOdds[0];
	    var lp=$("#long_odd").text();
	    betNum=betNum+"</p>";
	    hidOdds = ('<input type=hidden id="odds" name="odds" value='+odds+' />');
	    form.append(hidOdds);
	  
	   // var json=ajaxGetTopWinPrice("HK_SXL_"+radioType);
	
		
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
	$("#shu").html(changeNumToBall(shu));
	$("#niu").html(changeNumToBall(niu));
	$("#hu").html(changeNumToBall(hu));
	$("#tu").html(changeNumToBall(tu));
	$("#long").html(changeNumToBall(lon));
	$("#se").html(changeNumToBall(se));
	$("#ma").html(changeNumToBall(ma));
	$("#yang").html(changeNumToBall(yang));
	$("#hou").html(changeNumToBall(hou));
	$("#ji").html(changeNumToBall(ji));
	$("#gou").html(changeNumToBall(gou));
	$("#zhu").html(changeNumToBall(zhu));
	




}


