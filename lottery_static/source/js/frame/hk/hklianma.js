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
var WS = {"W1": "1尾", "W2": "2尾","W3": "3尾","W4": "4尾","W5": "5尾","W6": "6尾","W7": "7尾","W8": "8尾","W9": "9尾","W0": "0尾"};
/*function fp()
{
  	//alert("封盘时间已经到了");	
    $(":input[name='checkType']").attr("disabled", true);
    //$(":input[name='checkType']").hide();
    $("#subBet").attr("disabled", true);
    $("#res").attr("disabled", true);
	$(":input[name='BALL']").hide();
	$("#HK_STRAIGHTTHROUGH_3QZ").html("-");
	$("#HK_LM_3Z2").html("-");
	$("#HK_LM_Z3").html("-");
	$("#HK_STRAIGHTTHROUGH_2QZ").html("-");
	$("#HK_LM_2ZT").html("-");
	$("#HK_LM_Z2").html("-");
	$("#HK_STRAIGHTTHROUGH_TC").html("-");
	
}*/
function kp()
{
	//alert("开奖时间已经到了");  
	window.location.reload();
	$("#betform").reset();
}
function refresh()
{
}
function displayBetInfoOnLeft()
{
	
	var radioType=$("#checkTable :input[name='checkType']:checked").val();
	var radioType2=$("#checkTable :input[name='checkType2']:checked").val();
	var selecedBall=$(":input[name='BALL']:checked");
	var selecedSize=selecedBall.size();
	var type="";
	var number=0;
	var odds=0;
	var title="";
	if(radioType==null)
	{
		alert("請選擇投注類型");
		return false;
	}
	if(radioType2==null)
	{
		alert("請選擇投注類型");
		return false;
	}
	if(radioType=='HK_STRAIGHTTHROUGH_3QZ')
	{
		odds=$("#HK_STRAIGHTTHROUGH_3QZ").text();
		number=3;
		type="叁全中";
		var jsonOdds=getHKShopPlayOdds(radioType);
		odds=jsonOdds.HK_STRAIGHTTHROUGH_3QZ;
		title=type+"@<strong class=\"red\">"+odds+"</strong>";
		if(selecedSize<3)
	   {alert('"叁全中"最少必須選擇叁個號碼');return false;}
	}
	else if(radioType=='HK_STRAIGHTTHROUGH_3Z2')
	{
		number=3;
		var z2odds=$("#HK_LM_3Z2").text();
		var z3odds=$("#HK_LM_Z3").text();
		var jsonOdds=getHKShopPlayOdds(radioType);
		z2odds=jsonOdds.HK_LM_3Z2;
		z3odds=jsonOdds.HK_LM_Z3;
		type="叁中二";
		title=type+"@<strong class=\"red\">"+z2odds+"</strong><br>中三@<strong class=\"red\">"+z3odds+"</strong>";
		if(selecedSize<3)
			{
		alert('"叁中二"最少必須選擇叁個號碼');
		return false;
			}
	}
	else if(radioType=='HK_STRAIGHTTHROUGH_2QZ')
	{
		number=2;
		odds=$("#HK_STRAIGHTTHROUGH_2QZ").text();
		type="二全中";
		var jsonOdds=getHKShopPlayOdds(radioType);
		odds=jsonOdds.HK_STRAIGHTTHROUGH_2QZ;
		title=type+"@<strong class=\"red\">"+odds+"</strong>";;
		if(selecedSize<2)
			{
		alert('"二全中"最少必須選擇两個號碼');
		return false;}
	}
	else if(radioType=='HK_STRAIGHTTHROUGH_2ZT')
	{
		number=2;
		var zztodds=$("#HK_LM_2ZT").text();
		var z2odds=$("#HK_LM_Z2").text();
		type="二中特";
		var jsonOdds=getHKShopPlayOdds(radioType);
		zztodds=jsonOdds.HK_LM_2ZT;
		z2odds=jsonOdds.HK_LM_Z2;
		title=type+"@<strong class=\"red\">"+zztodds+"</strong><br>中二@<strong class=\"red\">"+z2odds+"</strong>";
		if(selecedSize<2)
			{
		alert('"二中特"最少必須選擇两個號碼');
		return false;}
	}
	else if(radioType=='HK_STRAIGHTTHROUGH_TC')
	{
		number=2;
		type="特串";
		odds=$("#HK_STRAIGHTTHROUGH_TC").text();
		var jsonOdds=getHKShopPlayOdds(radioType);
		odds=jsonOdds.HK_STRAIGHTTHROUGH_TC;
		title=type+"@<strong class=\"red\">"+odds+"</strong>";
		if(selecedSize<2)
			{
		alert('"特串"最少必須選擇两個號碼');
		return false;}
	}
	var i;
	var num=0;
	
	
	
	var balls=new   Array();
	for(i=0;i<selecedSize;i++)
	{
		balls[i]=selecedBall[i].value;
		//alert("ball is : " + balls[i]);
	}
	if(radioType2!='SXDP'&&radioType2!='WSDP'){balls.sort(function(x,y){return (x - y);});}
	createFormForSubmit(balls,title,number);

}

function createFormForSubmit(balls,title,number) 
{
	
	
	var radioType=$("#checkTable input[name='checkType']:checked").val();
	var radioType2=$("#checkTable :input[name='checkType2']:checked").val();
	var resultArr=C(balls,number);
	 var notDanBall=new Array();
	 var dan=new Array();
	if(radioType2=="T1D"||radioType2=="T2D")
	{
	 
	   $(":input[name='BALL']:checked").each(function(i){
		  if(!$(this).parent().hasClass("dan"))
			  notDanBall.push($(this).val());
		  else
			  dan.push($(this).val());
	   });

	   var dispalySize=$(".dan").size();
	   //alert(number+">>>>"+dispalySize);
	   var comsize=eval(number)-eval(dispalySize);
	  // alert(comsize);
	   resultArr=C(notDanBall,comsize);
	  
	}
	comSize=resultArr.length;
	var betSubTypeName="複式";
	if(radioType2=='SXDP')
	{
		betSubTypeName="生肖對碰";
		comSize=SXCode[balls[0]].length*SXCode[balls[1]].length;
		
	}
	else if(radioType2=='WSDP')
	{
			betSubTypeName="尾數對碰";
			comSize=WSCode[balls[0]].length*WSCode[balls[1]].length;
	}
	else if(radioType2=='T1D'||radioType2=='T2D')betSubTypeName="拖膽";
	else if(comSize==1)
	{
		
		betSubTypeName="單註";
	}
	var selecedSize=$(":input[name='BALL']:checked").size();
		
    
	
	
	
	
	var betNum="<p>";

	
	 form = $("#ajaxform",frameDocument('leftFrame'));
	    form.html("");
	    form.attr('action',"ajaxhkLMSub.action");
	    form.attr('method','post');
	    var inputBetType = ("<input type='hidden' name='checkType' value='"+radioType+"' id='checkType'/>");
	    var comb = ("<input type='hidden' name='combinSize' value='"+comSize+"' id='combinSize'/>");
	    //comb.val(comSize);
	    //comb.attr('value',comSize);
	
	    form.append(comb);
	    //inputBetType.attr('value',radioType);
	    form.append(inputBetType);
	   
	    var subTypeValue="";
	    if(radioType2=='T1D'||radioType2=='T2D')
	    {
	    	 //subType.attr('value',radioType2);
	    	subTypeValue=radioType2;
	    }
	    else if(radioType2=='DZ')
	    {
	    	//subType.attr('value',"DZ");
	    	subTypeValue="DZ";
        }
	    else if(radioType2=='SXDP')
	    {
	    	//subType.attr('value',"SXDP");
	    	subTypeValue="DZ";
	    }
	    	
	    else if(radioType2=='WSDP')
	    {
	    	//subType.attr('value',"WSDP");
	    	subTypeValue="WSDP";
	    }
	    	
	    var subType = ("<input type='hidden' name='checkSubType' value='"+subTypeValue+"' />");
	    form.append(subType);
	    if(radioType2=='T1D'||radioType2=='T2D')
	    {
	    	
	    	
 	    	
	    	 for(var i=0;i<dan.length;i++)
	 	    {
	 	    	var ballName=dan[i];
      	 
	 	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"</span>、";
	 	    	 ballValue = ("<input type='hidden' name='DAN' value='"+dan[i]+"'  />");
	 	    	 //ballValue.attr('value',dan[i]);
	 	    	 form.append(ballValue);
	 	    }
	    	 betNum=betNum+"</br>拖</br>";
	    	 for(var j=0;j<notDanBall.length;j++)
	 	    {
	 	    	var ballName=notDanBall[j];
	 	    	
	 	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"</span>、";
	 	    	 ballValue = ("<input type='hidden' name='BALL' value='"+notDanBall[j]+"'  />");
	 	    	 //ballValue.attr('value',notDanBall[j]);
	 	    	 form.append(ballValue);
	 	    }
	    	
	    	
	    	 
	    }
	     else
	    {
	    for(var i=0;i<balls.length;i++)
	    {
	    	var ballName=balls[i];
	    	if(radioType2=='SXDP') 
	    		ballName=SX[balls[i]];
	    	else if(radioType2=='WSDP')
	    		ballName=WS[balls[i]];
	    	
	       	 
	    	 betNum=betNum+"<span class=\"l_blue\">"+ballName+"</span>、";
	    	 ballValue = ("<input type='hidden' name='BALL' value='"+balls[i]+"' />");
	    	 //ballValue.attr('value',balls[i]);
	    	 form.append(ballValue);
	    }}	   
	    betNum=betNum+"</p>";
	   
	
	var json=ajaxGetTopWinPrice(radioType);
	
	var winQuto=json.winQuatas;
	canBetPrice=Math.floor(json.betQuotas/comSize);
	var div=("<div class='p'>");
	div=div+(""
    +"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231px\" class=\"print_l\"><tbody>"
    +" <tr><th colspan=\"2\" align=\"center\"><strong class=\"green\">"+period+"期</strong></th> </tr>"
    +"<tr> <td colspan=\"2\" class=\"xz_detail\"><p><span class=\"blue\">"+title+"</span></p>"
    +"<p class=\"xzmz blue\">下注号碼明细</p>"
    +betNum
    +"<p>您共选择了<span class=\"red\">"+selecedSize+"</span>个号碼</p>"
    +" <p>“"+betSubTypeName+"” 共分为<span class=\"red\">"+comSize+"</span>组</p>"
    +"<p>每组最高可下注金額<span class=\"red\">"+canBetPrice+"</span>元</p>"
    +"</td></tr>"
    +"<tr><td class=\"l_color\">每注金額</td><td>"
    +"<input type='text' size=\"8\" maxlength=\"9\" class=\"xz\" name='price' id='price'   ondragenter='return false' />"
     
    +"</td></tr><tr><td width=\"34%\" class=\"l_color\">下注合计</td><td width=\"66%\"><span class=\"red\" id=\"betSum\">0</span></td></tr>"
    +"<tr><td class=\"l_color\">最高派彩</td><td>"+winQuto+"</td></tr>"
	+"<tr><td colspan=\"2\" class=\"l_color\"><div class=\"tj\" style=\"margin:8px auto;\"><span><input type=\"button\" name=\"\" class=\"btn2\" value=\"取 消\" id=\"retbtn\"></span><span class=\"ml10\">"
	
	+"<input type='submit' class=\"btn2\" value=\"下 注\" name='substraight' id='substraight'/>"
	  
	+"</span></div></td></tr></tbody></table>");	     
		   
   
      form.append(div);   
      $("#price",frameDocument('leftFrame')).focus();
		      

}
		    
$(document).ready(function() {
	
	
	
	initPageBall();
	$("#checkTable :input[name='checkType2']").click(function() {
		var type=$(this).val();
		$(":input[name='BALL']").attr("disabled",false);
		$(":input[name='BALL']").attr("checked",false);
		$(".dan").removeClass("dan");
		if(type=="DZ"||type=="T1D"||type=="T2D")
		{
			$("#betTable").show();
			$("#wsdp").hide();
			$("#sxdp").hide();
		}
		else if(type=="SXDP")
		{
			$("#betTable").hide();
			$("#wsdp").hide();
			$("#sxdp").show();
		}
		else if (type=="WSDP")
		{
			$("#betTable").hide();
			$("#wsdp").show();
			$("#sxdp").hide();
		}
		
	}); 
	
	
	
/*	$('#price').live('keyup', function() {
		  //alert("Live handler called."); 
		  var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(/\D|^0/g,'')); 
	        if(tmptxt>canBetPrice) 
	        {
	        	$(this).val(canBetPrice);
	        	
	        }
	        if($(this).val().length==0)
	        	 $('#betSum').html(0)
	        	 else
	         $('#betSum').html(eval($(this).val())*eval(comSize));
	        
		});*/
	
	
	
	$("#subBet").click(function() {
		displayBetInfoOnLeft();
		
	}); 
	$("#checkTable input[name='checkType']").click(function() {
		 $("#subBet").attr("disabled", false);
		 $("#betTable").show();
			$("#wsdp").hide();
			$("#sxdp").hide();
			$("#checkTable input[name='checkType2']:[checked=true]").attr('checked',false);
			
		 $("#betform input[type='checkbox']").attr("checked",false); 
		 $("#betform input[type='checkbox']").attr("disabled",false); 
		 $(".dan").removeClass("dan");
		$("input[name='BALL']").show();	
		if ($("#checkTable input[name='checkType']:checked").val()=="HK_STRAIGHTTHROUGH_3QZ" || $("#checkTable input[name='checkType']:checked").val()=="HK_STRAIGHTTHROUGH_3Z2"){	
			$("#checkTable input[name='checkType2']:[value='SXDP']").attr('disabled',true);
			$("#checkTable input[name='checkType2']:[value='WSDP']").attr('disabled',true);		
		}
		if ($("#checkTable input[name='checkType']:checked").val()=="HK_STRAIGHTTHROUGH_2QZ" || $("#checkTable input[name='checkType']:checked").val()=="HK_STRAIGHTTHROUGH_2ZT" || $("#checkTable input[name='checkType']:checked").val()=="HK_STRAIGHTTHROUGH_TC"){
			$("#checkTable input[name='checkType2']:[value='SXDP']").attr('disabled',false);
			$("#checkTable input[name='checkType2']:[value='WSDP']").attr('disabled',false);		
		}
		
	}); 
	
       $(":input[name='BALL']").click(function() {
    	   
    	  
    	   
    	   
    	   
		var  radioType=$("#checkTable :input[name='checkType2']:checked").val();
		var  radioType1=$("#checkTable :input[name='checkType']:checked").val();
		var selecedBall=$(":input[name='BALL']:checked");
		if((radioType=="SXDP"||radioType=="WSDP"))
			{
			if(selecedBall.size()==2)
			$(":input[name='BALL']:[checked=false]").attr("disabled",true);
			else
				$("#betform input[name='BALL']:[checked=false]").attr('disabled',false);
			}
		else
		{
			
			
		   		
		   		if($("#betform input[name='BALL']:checked").size()==10)
		   			$("#betform input[name='BALL']:[checked=false]").attr('disabled',true);
		   		else if($("#betform input[name='BALL']:checked").size()<10)
		   			$("#betform input[name='BALL']:[checked=false]").attr('disabled',false);
			
			
			}
		
		var dispalySize=$(".dan").size();	
		
		var danSize=0;
		if(radioType=="T2D") danSize=2;
		if(radioType=="T1D")	danSize=1;
	    if(radioType1=='HK_STRAIGHTTHROUGH_2QZ'||radioType1=='HK_STRAIGHTTHROUGH_2ZT'||radioType1=='HK_STRAIGHTTHROUGH_TC')
		{
			danSize=1;
		}
        
		if(radioType=='T1D'||radioType=='T2D')
		{
		    if($(this).attr("checked")==true&&dispalySize<danSize)
		    	{
		    $(this).parent().addClass("dan");
		    	}
		    else
		    	$(this).parent().removeClass("dan");	

			
		}

		
	});  
	
	 $("#res").click(function() {
		//alert("ddddd");
			$("#checkTable,input[name='BALL']:[checked=true]").attr('checked',false);
			$("#checkTable, input[name='BALL']:[checked=false]").attr('disabled',false);
			$(".dan").removeClass("dan");
	});
	
	 parent.topFrame.options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse, 		 
		        url:       context+'/member/ajaxhkLMSub.do',
		        type:      'post',        
		        dataType:  'json'         
		    }; 	
	
	var options = { 

		        beforeSubmit:  validate, 
		        success:       showResponse,  		 
		        // other available options: 
		        url:       context+'/member/ajaxhkLMSub.do',
		        type:      'post', 
		        dataType:  'json' 	     
		    }; 

	/*	    $('#ajaxform').submit(function() {		 	  
		    	var typeCode=$('#checkType').val();		    	
		    	var value=$('#price').val();
		    	if(!CheckUtil.isPlusInteger(value))
		    	{
		    		alert("请输入正确的金额");
		    		return false;
		    	}
		    	
		    	var dan="";
				$("#ajaxform input[name=DAN]").each(function() {					
					dan=dan+"&dan="+$(this).val();		
				});
				var combinSize=$('#combinSize').val();
				var parameter=typeCode+"@"+value+"&type=HK_LM"+dan+"&combinSize="+combinSize;
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

}); 

function validate(formData, jqForm, options)
{
	var price=jqForm[0].price.value;
	
	//alert(price);
	
	var queryString = $.param(formData); 
	    //alert(queryString); 
	     return true; 
	//alert("check submit");	
}
function showRequest(formData, jqForm, options) { 
    
    var queryString = $.param(formData); 
   // alert('About to submit: \n\n' + queryString); 
     return true; 
} 
 
// post-submit callback 
function showResponse(jsonData, statusText)  { 
  
	$('#betform')[0].reset();
	$(".dan").removeClass("dan");
	$(":input[name='BALL']").attr("checked",false);
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
	 price.val(tmptxt.replace(/\D|^0/g,'')); 
     if(tmptxt>canBetPrice) 
     {
    	 price.val(canBetPrice);
     	
     }
     if(price.val().length==0)
     	 $('#betSum',frameDocument('leftFrame')).html(0);
     	 else
      $('#betSum',frameDocument('leftFrame')).html(eval(price.val())*eval(comSize));
	
	
/*	var tmptxt=price.val();
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
      }*/
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
	
	$("#w1").html(changeNumToBall(w1));
	$("#w2").html(changeNumToBall(w2));
	$("#w3").html(changeNumToBall(w3));
	$("#w4").html(changeNumToBall(w4));
	$("#w5").html(changeNumToBall(w5));
	$("#w6").html(changeNumToBall(w6));
	$("#w7").html(changeNumToBall(w7));
	$("#w8").html(changeNumToBall(w8));
	$("#w9").html(changeNumToBall(w9));
	$("#w0").html(changeNumToBall(w0));



}