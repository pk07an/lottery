<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link type="text/css" rel="stylesheet" href="/css/left.css" />
</head>

<body>

<!-- 左边内容开始 -->
<table width="231" cellspacing="1" cellpadding="0" border="0" class="t_list">
  	<tbody>
	  	<tr><td colspan="2" class="t_list_caption">請覈對您的帳戶</td></tr>
	  	<tr><td width="65" class="t_td_caption_1">會員帳戶</td><td width="166" class="t_td_text">${account}（${plate }盤）<span id="status" style="color: red">${status==2?"冻结":"" }</span></td></tr>
		<tr><td class="t_td_caption_1">信用額度</td><td class="t_td_text" id="totalCredit">${ totalCredit}</td></tr>
		<tr><td class="t_td_caption_1">可用金額</td><td class="t_td_text" id="remainMoney">${avalilableCredit }</td></tr>
		<tr id="jq_gdgf"><td colspan="2" class="t_list_caption"><a onclick="window.open('http://www.gdfc.org.cn/play_list_game_9.html','廣東快樂十分','width=687,height=464,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,toolbar=no');" href="javascript:void(0);"> “廣東快樂十分”開獎网</a>&nbsp;</td></tr>
		<tr id="jq_cqgf"><td colspan="2" class="t_list_caption"><a onclick="window.open('http://video.shishicai.cn/cqssc/','重慶時時彩','width=687,height=464,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,toolbar=no');" href="javascript:void(0);"> “重慶時時彩”開獎网</a>&nbsp;&nbsp;</td></tr>
		<tr id="jq_bjgf"><td colspan="2" class="t_list_caption"><a onclick="window.open('http://www.lecai.com/lottery/draw/list/557','北京賽車','width=687,height=464,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,toolbar=no');" href="javascript:void(0);"> “北京賽車(PK10)”官方网</a>&nbsp;&nbsp;</td></tr>
		<tr id="jq_jsgf"><td colspan="2" class="t_list_caption"><a onclick="window.open('http://www.jslottery.com/Lottery/LotteryInfo_Fast3?PlayType=7','江苏骰寶','width=687,height=464,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,toolbar=no');" href="javascript:void(0);"> “江苏骰寶(快3)”官方网</a>&nbsp;&nbsp;</td></tr>
		<tr id="jq_ncgf"><td colspan="2" class="t_list_caption"><a onclick="window.open('http://www.cqcp.net/game/xync/','幸运农场','width=687,height=464,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,toolbar=no');" href="javascript:void(0);"> “重庆幸运农场”官方网</a>&nbsp;&nbsp;</td></tr>
	</tbody>
</table>
<form id='ajaxform'></form>
<form id='ajaxhiddenform'></form>
<script language="javascript" src="/js/jquery-1.4.2.min.js"></script>
  <script language="javascript" src="/js/jquery.form.js"></script>
  <script language="javascript" src="/js/public.js"></script>
  <script language="javascript" src="${pageContext.request.contextPath}/${path }/js/lottery.js"></script>
 <script type="text/javascript">
$(document).ready(function() {
	$('#retbtn,#cancelButton').live('click', function() {	 
	         $('.p').hide();	
	         $('#jq_gdgf').show();
	     	$('#jq_cqgf').show();
	     	$('#jq_bjgf').show();
	     	$('#jq_jsgf').show();
	     	$('#jq_ncgf').show();
		});	
	
	 $('#ajaxhiddenform').submit(function() { 
	        $(this).ajaxSubmit(parent.topFrame.options); 
	        return false; 
	    }); 

	
    $('#ajaxform').submit(function() { 
    	
    	
    	var typeCode=$('#checkType').val();
    	var opt=parent.topFrame.options;
    	var value=$('#price').val();
    	if(!CheckUtil.isPlusInteger(value))
    	{
    		alert("请输入正确的金额");
    		return false;
    	}
    	var parameter=typeCode+"@"+value;
    	if(typeCode.indexOf("HK_STRAIGHTTHROUGH")!=-1)
    	{
    	var dan="";
		$("#ajaxform input[name=DAN]").each(function() {					
			dan=dan+"&dan="+$(this).val();		
		});
		var combinSize=$('#combinSize',frameDocument('leftFrame')).val();
		parameter=typeCode+"@"+value+"&type=HK_LM"+dan+"&combinSize="+combinSize;
    	}
    	else if(typeCode=="2XL"||typeCode=="3XL"||typeCode=="4XL")
    	{
    	parameter="";
    	$("#ajaxform input[name=SX]").each(function() {
			
			parameter=parameter+"@"+$(this).val();
		});
    	
		var sxl=typeCode;
		parameter=parameter+"&type=HK_SXL_"+sxl;
		var dan="";
		$("#ajaxform input[name=DAN]").each(function() {
			
			dan=dan+"&dan="+$(this).val();


		});
		
		var combinSize=$('#combinSize').val();
		parameter=parameter+"&type=HK_SXL_"+sxl+dan+"&combinSize="+combinSize+"&price="+value;
		
    	}
    	
    	else if(typeCode=="2WL"||typeCode=="3WL"||typeCode=="4WL")
       {
    		parameter="";
			$("#ajaxform input[name=WS]").each(function() {
				
				parameter=parameter+"@"+$(this).val();
	

			});
			var wsl=$('#checkType').val();
			
			var dan="";
			$("#ajaxform input[name=DAN]").each(function() {
				
				dan=dan+"&dan="+$(this).val();
	

			});
			var combinSize=$('#combinSize').val();
			parameter=parameter+"&type=HK_WSL_"+wsl+dan+"&combinSize="+combinSize+"&price="+value;
    		
       }
    	else if(typeCode=="HK_WBZ")
    	{
    		parameter="";
			$("#ajaxform input[name=BALL]").each(function() {
				
				parameter=parameter+"@"+$(this).val();
	

			});
			var combinSize=$('#combinSize').val();
			parameter=parameter+"&type=HK_WBZ&combinSize="+combinSize+"&price="+value;;
    		
    		
    		}
    	else if(typeCode=="HK_GG")
    		{
    		 parameter="";
			$("#ajaxform input[name=BALL]",frameDocument('leftFrame')).each(function() {
				
				parameter=parameter+"@"+$(this).val();
	

			});
			var combinSize=$('#combinSize').val();
			parameter=parameter+"&type=HK_GG&combinSize="+combinSize+"&price="+value;
    		
    		
    		}
    	
    	var playType = "";
    	 if(typeCode.indexOf("GDKLSF") != -1){
    		 playType="GDKLSF";
    	 }
    	 else if(typeCode.indexOf("CQSSC") != -1){
    		 playType="CQSSC";
    	 }
		else if(typeCode.indexOf("BJ") != -1){
			 playType="BJ"; 
		}
		else if(typeCode.indexOf("NC") != -1){
			 playType="NC";
		}
		else if(typeCode.indexOf("K3") != -1){
			 playType="K3";
		}
		 var json=getShopPlayOdds(parameter,playType);
			if(json==null){alert("系统错误 请联系管理员");return false;}
			else if(json.errorMessage){alert(json.errorMessage);return false;}	
			else if(json.message) {
				if(confirm(json.message))
				{
				  $(this).ajaxSubmit(opt); 
				  $("#substraight").attr("disabled",true);
				}
			}      
        return false; 
    }); 
    
    $('#price').live('keyup', function() {
		 
    	parent.mainFrame.changePrice($(this));
    	/*   */
		}); 
    
    $('#print').live('click',function (){window.print();});
    
	
});
</script>
</body>

</html>

