<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.action.OddMapProxy,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.periods.entity.GDPeriodsInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%

GDPeriodsInfo cqp=(GDPeriodsInfo)request.getAttribute("RunningPeriods");

long kjTime=1000*90;

long fpTime=0;
if(cqp!=null)
{

	fpTime=Long.valueOf(request.getAttribute("StopTime").toString());
	kjTime=Long.valueOf(request.getAttribute("KjTime").toString());

}

%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
var onrefresh = false;
var timer; 

function openHelp(infoLink){
	//alert(infoLink.href);
	//需要打开的页面URL，设置为实际值
	var url = "${pageContext.request.contextPath}/images/replenishHelp.gif";
	//打开模式窗口，固定写法，不要改变
	var ret = openModalDialogReport(url,'',1006,800);
	
	return false;
}

$(document).ready(function() {	
	$("#money").keyup(function(){onlyNumber(this);});
	$("#money").keydown(function(){onlyNumber(this);});

	//获取元素的坐标FOR 赔率设置
    var getElementOffset = function _getElementOffset($this) {
        var offset = $this.position();
        var left = offset.left - 60;
        var top = offset.top + $this.height()+7;  //把text元素本身的高度也算上
        return { left: left, top: top }
    }
	//获取元素的坐标FOR 补货
    var getElementOffsetForReplenish = function _getElementOffset($this) {
        var offset = $this.position();
        var left = offset.left-40;
        var top = offset.top + $this.height()-98;  //把text元素本身的高度也算上
        return { left: left, top: top }
    }
	
  //设置元素的坐标
    var setElementOffset = function _setElementOffset($this, offset) {
        $this.css({ "position": "absolute", "left": offset.left, "top": offset.top });
        return $this;
    }
	
	$(".rOp").live("click", function () {
		<%if(replenishment ==null && isSub){%>
        <%}else{%>
    	$("#money").val("");
    	
    	if($("#fpTime").html()=="00:01"){
			$("#rBtnSubmit").attr("disabled","disabled");
		}else{
			$("#rBtnSubmit").removeAttr("disabled");
		}
    	if ($("#searchType").val()=="1"){
    		//alert("溫馨提示:實占下才可以進行補貨操作.");
    	}else{
   	        var attribute=$(this).parent().prev().prev().prev().prev().prev().prev().html();
   	        var item = $("input[@name='selectType']:checked").val();
   	        
   	        $("#playFinalType").val(item);
   	        $("#attribute").val(attribute.replace(/\、/g,'|'));
   	        $("#rAttribute").html(attribute);
   	        $("#rFinalType").html(getChnName(item));
   	        $("#rPlate").val($("#plate").val());
   	    	$("#lotteryType").val("GDKLSF");
   	        $("#rOdds").val($("#odd"+item).html().trim());
	     	var offset = getElementOffsetForReplenish($(this));
		    var $div = setElementOffset($("#replenishShow"), offset);
		    $div.show();
	        $("#money").focus();
	        getTypeCodeState("GDLM",item,$("#attribute").val());
    	}   
    	<%}%>
    });  

    $(".fOp").live("click", function () {
    	var item = $("input[@name='checkType']:checked").val();
    	submitPageAjaxLM(item,$(this).prev().val());
    });
       
    var options = { 
 	       // target:        '#output2',   // target element(s) to be updated with server response 
 	        beforeSubmit:  validateForChief, // pre-submit callback 
 	        success:       showResponseDy,  // post-submit callback 		 
 	        // other available options: 
 	        url:       'ajaxReplenishSubmit.action',         // override for form's 'action' attribute 
 	        type:      'post',        // 'get' or 'post', override for form's 'method' attribute 
 	        dataType:  'json'       // 'xml', 'script', or 'json' (expected server response type) 		     
 	        //resetForm: true        // reset the form after successful submit 
 	    }; 
    
    $('#replenishForm').submit(function() { 
    	$("#replenishShow").hide();
        $(this).ajaxSubmit(options); 
        return false; 
    }); 
    
    $('#oddTable a').click(function() {
    	<%if(odd ==null  && isSub){%>
       <%}else{%>
		  var typeCode=$(this).parent().attr("id");
		  if(typeCode!="GDKLSF_STRAIGHTTHROUGH_R2LZHI" && typeCode!="GDKLSF_STRAIGHTTHROUGH_R3LZHI"){
			  
			  if($(this).attr("name")=="jia")
				  changeOdds(typeCode,"jia");
			  else if($(this).attr("name")=="jian")
				  changeOdds(typeCode,"jian");
			  else if($(this).attr("name")=="notPlay")
			  {
				  var state = '0';
				  if($(this).parent().attr("class")=="even3"){
			    	  state = '1';
			      }
				  changeNotPlay($(this).attr("id"),state);
			  }
			  else if($(this).attr("name")=="odd"){
				    var offset = getElementOffset($(this));
				    var $div = setElementOffset($("#oddsShow"), offset);
					var typeCodeChnName=$(this).parent().parent().parent().parent().parent().parent().prev().children().html();
		   	        $("#oddFinalType").html(getChnName($(this).parent().prev().attr("id")));
		   	        $("#oddValue").val(parseFloat($(this).children().html()));
		   	        $("#oddTypeCode").val($(this).parent().prev().attr("id"));
				    $div.show();
			  }
          }
		  <%}%>
	});
    
    $('#oddTable a').mousemove(function() {
    	if($(this).attr("name")=="notPlay"){
    		$(this).attr("title","開盤/封盤");
    	}
    		
    });
     
});


//处理实时赔率加减  START
function changeOdds(typeCode,jiajian)
{
	var ret=null;
	var num =$("#changeOddsNum").val().substring(1);
	oddsChangeAddOrSub(typeCode,jiajian,num);
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxgdBalloddsUpdate.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,jiajian:jiajian,odd:num},type:"POST",success:successChangeOdd});
	return ret;

}
function successChangeOdd(json){onChangeSubmit();  }
//处理实时赔率加减  END



//手动修改赔率
function opOdd(){
	var ret = null;
	var odd = $("#oddValue").val();
	var typeCode = $("#oddTypeCode").val();
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxgdBalloddsUpdate.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,odd:odd},type:"POST",success:successChangeOdd});
	//$("#oddForm").reset();
	$("#oddsShow").hide();
	return ret;
}


function getChnName(type){
	var chnName ="";
	if(type=="GDKLSF_STRAIGHTTHROUGH_RX2"){
		chnName="任選二";
    }else if(type=="GDKLSF_STRAIGHTTHROUGH_RX3"){
	    chnName="任選三";
	}else if(type=="GDKLSF_STRAIGHTTHROUGH_RX4"){
		chnName="任選四";
	}else if(type=="GDKLSF_STRAIGHTTHROUGH_RX5"){
		chnName="任選五";
	}else if(type=="GDKLSF_STRAIGHTTHROUGH_R3LZ"){
		chnName="选三连组";
	}else if(type=="GDKLSF_STRAIGHTTHROUGH_R2LZ"){
		chnName="选二连组";
	}
	return chnName;
}

//点击RADIO后--START
function changeSelectType(){
	$("#replenishShow").hide();
    var type =$("input[@name='selectType']:checked").val();
    submitPageAjax_LM(type);
    var chnName = getChnName(type);
    $("#rTitle").html(chnName);
	$("#foot_none").hide();
	$("#foot_r").show();
}

function compute(){
	$("#replenishShow").hide();
    var type =$("input[@name='selectType']:checked").val();
    submitPageAjax_LM(type);
    var chnName = getChnName(type);
    $("#rTitle").html(chnName);
	$("#foot_none").hide();
	$("#foot_r").show();
	$("#quickBtn").removeAttr("disabled");
}

function submitPageAjax_LM(typeCode) {
	var strUrl = 'ajaxReplenishLmMain.action';
	var avLose = $("#avLose").val();
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"avLoseForm":avLose},
		dataType : "json",
		success : getCallback_showLM
	});
}

function getCallback_showLM(json) {
	$("#infolist_lm").empty();
	if (json.length > 0) {
		$("#times").val(json.length);
		var totalAvLose = 0;
		
		for ( var i = 0; i <= json.length - 1; i++) {	
			if($("#avLose").val()!=null && $("#avLose").val()!=""){
				totalAvLose = json[i].avLose - $("#avLose").val();
			}
			var style="";
			var num = i+1;
			//if(json[i].winState==6 || json[i].winState==7  || json[i].winState==4  || json[i].winState==5)
				style="style='text-decoration:line-through;color:red;'";
			$("#infolist_lm").append(
					"" + "<tr>" + "<td>" + num + "</td>"
							+ "<td id=\"attributeList" + i + "\">" + json[i].attribute.replace(/\|/g,"、") + "</td>"
							+ "<td><span class=\"green\"> " + json[i].rateMoney + "</span></td>"
							+ "<td><input type=\"text\" id=\"moneyList" + i + "\" class=\"b_g\" style=\"width:76px;\" value=\"" + totalAvLose + "\"/></td>"
							+ "<td id=\"winStateList" + i + "\">" + json[i].winState + "</td>"
							+ "<td>" + json[i].commissionMoney + "</td>"
							+ "<td><span class=\"red\">" + json[i].winMoney + "</span></td>"
							+ "<td><input type=\"button\" class=\"rOp\" value=\"單补\" /></td>"
					+ "</tr>" + "");
		}
	}else{
		$("#infolist").append("<tr ><td colspan=\"5\" align=\"center\">没有找到信息</td></tr>");
	}
}
//点击RADIO后--END


//快速补出--START
function quickReplenish(){
	if(window.confirm('是否確快速定補出?【注意:在提示"快補完成"時請不要進行任何其它操作!!!】')){
		clDate=1000*Math.floor(300);
		var clnS=Math.floor(clDate/1000);
		if(clnS<10) clnS='0'+clnS; 
	    if(clDate>= 0){        
	        document.getElementById("clTime").innerHTML=clnS+"秒";  
        }  
		$("#replenishShow").hide();
	    var typeCode =$("input[@name='selectType']:checked").val();
	    var times = $("#times").val();
	    var plate = $("#plate").val();
	    for ( var i = 0; i <= times - 1; i++) {	
	    	var attribute = $("#attributeList"+i+"").html().replace(/\、/g,'|');
	    	var money = $("#moneyList"+i+"").val();
	    	submitQucickRelenish(typeCode,attribute,money,plate,i);
	    }
	    if(window.confirm('"快補完成"請詳細覆對補貨結果!【注:3秒后重新分組統計,補貨結果將消失】')){
	    	clDate=1000*Math.floor(3);
			var clnS=Math.floor(clDate/1000);
			if(clnS<10) clnS='0'+clnS; 
		    if(clDate>= 0){        
		        document.getElementById("clTime").innerHTML=clnS+"秒";  
	        }  
	    }
	}else{
        return false;
    }
    
}

 function submitQucickRelenish(typeCode,attribute,money,plate,quickNo) {
	var strUrl = 'ajaxReplenishSubmit.action';
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"typeCode":typeCode,"attribute":attribute,"money":money,"plate":plate,"quickNo":quickNo},
		dataType : "json",
		success : getCallback_quick
	});
}

function getCallback_quick(json) {
	$("#winStateList"+json[0].quickNo+"").html(json[0].result);
}
//快速补出--END

</script> 

<s:form action="replenishSet.action" namespace="replenish" id="rForm" method="post">
 <input type="hidden" id="subType" value="<s:property value="#request.subType"/>"/>
 <input type="hidden" id="lotteryType" value="GDKLSF"/>
 <input type="hidden" id="times" />
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <!--控制表格头部开始-->
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif">
	  <div id="Tab_top">
	    <table id="Tab_top_right" border="0" cellpadding="0" cellspacing="0" id="oddTable">
  <tr>
  <td align="left">
      <select name="searchType" onchange="onChangeSubmit()" id="searchType">
        <option value = "1" <s:if test="searchType==1">selected</s:if>>虛注</option>
        <option value = "0" <s:if test="searchType==0">selected</s:if>>實占</option>
      </select>
      <select id="plate" name="plate" onchange="onChangePlate()">
        <option value ="A" <s:if test='plate=="A"'> selected</s:if>>A盘</option>
        <option value ="B" <s:if test='plate=="B"'> selected</s:if>>B盘</option>
        <option value ="C" <s:if test='plate=="C"'> selected</s:if>>C盘</option>
      </select></td>
    <td width="80" align="right" >
       <input id="refreshBtn" type="button" value="更新" onclick="onChangeSubmit()" style="display:none;"/>
       <span id="refreshTime">更新：<span style="margin-right:2px;" id="clTime">0秒</span></span>
       <span id="onrefresh" style="display:none;" class="blue">載入中...</span>
    </td>
    <td>
      <select name="searchTime" id="searchTime" onchange="changeTime()">
	        <option value ="NO" <s:if test="#request.refleshTime==NO"> selected</s:if>>-NO-</option>
	        <option value ="5" <s:if test="#request.refleshTime==5"> selected</s:if>>5秒</option>
	        <option value ="10" <s:if test="#request.refleshTime==10"> selected</s:if>>10秒</option>
	        <option value ="15" <s:if test="#request.refleshTime==15"> selected</s:if>>15秒</option>
	        <option value ="20" <s:if test="#request.refleshTime==20"> selected</s:if>>20秒</option>
	        <option value ="25" <s:if test="#request.refleshTime==25"> selected</s:if>>25秒</option>
	        <option value ="30" <s:if test="#request.refleshTime==30"> selected</s:if>>30秒</option>
	        <option value ="40" <s:if test="#request.refleshTime==40"> selected</s:if>>40秒</option>
	        <option value ="50" <s:if test="#request.refleshTime==50"> selected</s:if>>50秒</option>
	        <option value ="60" <s:if test="#request.refleshTime==60"> selected</s:if>>60秒</option>
	        <option value ="99" <s:if test="#request.refleshTime==99"> selected</s:if>>99秒</option>
	      </select></td>
	<td width="16" align="right"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
	  </tr>
	    </table>
		<table id="Tab_top_left" border="0" cellspacing="0" cellpadding="0">
                 <tr>
				    <td width="12"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
				    <td width="16" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                    <td style="padding:0 6px;"><strong class="green"><s:property value="#request.periodsNum"/>期</strong></td>
                    <td style="padding:0 6px;"><strong class="blue">連碼</strong></td>
                    <td style="padding:0 6px;">
                        <strong style="font-color:#344B50" id="fpTitle">距離封盤：</strong>
                        <strong class="red" id="kjTitle" style="display:none">距離開獎：</strong>
				        <strong><span style="font-color:#344B50" id="fpTime">00:01</span></strong>
				        <strong><span class="red" id="kjTime" style="display:none">00:01</span></strong>
                    </td>
                    <td style="padding:0 6px;"><strong class="red">今天输赢：<span id="todaywin" class="red"></span></strong></td>
                  </tr>
                  <%@ include  file="/jsp/replenish/oddsSelect.jsp"%><!--加载赔率调节档次的Select标签 -->
                </table>
		<table id="Tab_top_center" class="gd_r" border="0" cellspacing="0" cellpadding="0" style="width:360px;margin:0 auto;">		
		
  <tr>
     <td><strong class="orange"><s:property value="#request.lastLotteryPeriods.PeriondsNum" escape="false"/></strong><strong>期赛果</strong></td>
     <td><s:property value="#request.lastLotteryPeriods.BallNum" escape="false"/></td>
  </tr>
  </table>
	  </div>
	</td>
  </tr>
<!--控制表格头部结束-->
<!--控制中间内容开始-->
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center">
		<!-- 表格内容开始 一行八列-->		<!-- 表格内容结束 一行八列-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt4 king" id="oddTable">
        <colgroup>
          <col width="13%" />
          <col width="13%" />
          <col width="13%" />
          <col width="13%" />
          <col width="12%" />
          <col width="12%" />
          <col width="12%" />
          <col width="12%" />
        </colgroup>
		  <tr>
		    <th colspan="8"><strong>连码</strong></th>
		    </tr>
		  <tr>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX2.fpState=="0" '> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_RX2" href="javascript:void(0)" name="notPlay">任选二</a></td>
		    <td class="even3">选二连直</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_R2LZ.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_R2LZ" href="javascript:void(0)" name="notPlay">选二连组</a></td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX3.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_RX3" href="javascript:void(0)" name="notPlay">任选三</a></td>
		    <td class="even3">选三前直</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_R3LZ.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_R3LZ" href="javascript:void(0)" name="notPlay">选三前组</a></td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX4.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_RX4" href="javascript:void(0)" name="notPlay">任选四</a></td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX5.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>
		    <a id="GDKLSF_STRAIGHTTHROUGH_RX5" href="javascript:void(0)" name="notPlay">任选五</a></td>
		    </tr>
            <tr>
              <!-- 賠率内容 -->
              <s:iterator value="#request.rMap">
	              <td <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class=""</s:if><s:else> class="even3"</s:else>><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
	                <tr>
			            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <%if(odd ==null && isSub){%><%}else{%><a href="javascript:void(0)" name="jia"><img src="${pageContext.request.contextPath}/images/admin/jia.gif" width="19" height="20" /></a><%}%>
			            </td>
			            <td style="text-align:center!important;" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <a href="javascript:void(0)" name="odd">
			                <span class="blue" id="odd<s:property value="value.playFinalType" escape="false"/>">
			                <s:property value="value.realOdds" escape="false"/></span></a>
			            </td>
			            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <%if(odd ==null && isSub){%><%}else{%><a href="javascript:void(0)" name="jian"><img src="${pageContext.request.contextPath}/images/admin/jian.gif" width="19" height="20" /></a><%}%>
			            </td>
		            </tr>
	              </table></td>
              </s:iterator>
            </tr>
            <tr>
              <!-- 注額内容 -->
              <s:iterator value="#request.rMap">
	              	<td <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0" && value.sortNo!=5 && value.sortNo!=2 '>class="fOp"</s:if><s:else>class="fOp even3"</s:else>>
                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>" title="查看注單明細">
					    <s:if test="value.money == 0" >
			            	<span class="green">-</span>
					    </s:if><s:else>
					    	<span class="green"><s:property value="value.money" escape="false"/></span>
					    </s:else>
					    </a>
				    </td>
              </s:iterator>
            </tr>
            <tr>
              <!-- 盈虧内容 -->
              <s:iterator value="#request.rMap">
                 <td <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0" && value.sortNo!=5 && value.sortNo!=2 '>class=""</s:if><s:else>class="even3"</s:else>>
                    <s:if test="value.money == 0 ">
			            -
					</s:if><s:else>
					    <s:property value="value.loseMoney" escape="false"/>
					</s:else>
                 
              </s:iterator>
            </tr>
            <tr>
              <!-- RADIO内容 -->
              <s:iterator value="#request.rMap">
			   		 <td class="even2">
			   		    <input type="radio" name="selectType" value="<s:property value="value.playFinalType" escape="false"/>" 
			   		       <s:if test="value.sortNo==5 || value.sortNo==2">disabled="disabled"</s:if>
			   		       onclick="changeSelectType()" <%if(!cqp.getState().equals("2")){%>disabled="disabled"<%}%>
			   		    />
			   		 </td>
			  </s:iterator>
		    </tr>
		  </table></td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
<!--控制中间内容结束-->

<!--控制底部操作按钮开始-->
  <tr id="foot_none">
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align="center"><input type="button" class="blue_btn btn2" value="補貨說明"  onclick="return openHelp(this)"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
<!--控制底部操作按钮结束-->

<!--控制底部操作补货栏按钮开始-->
<tr id="foot_r" style="display:none">
    <td>
	<table style="width:750px;margin:0 auto;" border="0" cellspacing="0" cellpadding="0" class="king">
		<colgroup>
		  <col width="84" />
		  <col width="156" />
		  <col width="128" />
		  <col width="70" />
		  <col width="66" />
		  <col width="86" />
		  <col width="126" />
		  <col width="34" />
		</colgroup>
	  <tr>
	    <td colspan="8" style="height:30px;">每注保證額度（超出部分補出）：<input type="text" id="avLose" class="b_g" style="width:76px;"/>
		<input type="button" class="btn2 zs" value="計算補貨" onclick="compute()"/>
	    </tr>
	  <tr>
	    <th colspan="8">[ <strong class="red" id="rTitle">任選二</strong> ]按單組統计排行</th>
	    </tr>
	  <tr>
	    <td>排名</td>
	    <td>組合</td>
	    <td>下注額</td>
	    <td>快補金額</td>
	    <td>補貨結果</td>
	    <td>退水</td>
	    <td>派彩額</td>
	    <td>單補</td>
	  </tr>
	  <!-- 交易内容 -->
 	 <tbody id="infolist_lm"></tbody>
	</table>

   </td>
</tr>

<!--控制底部操作补货栏按钮结果-->

</table>
</s:form>
<script type="text/javascript">
var fpDate = <%=fpTime%>;
var kjDate =<%=kjTime%>; 
var clDate=1000*90;
var queryUrl = context+'/replenish/ajaxGetTodayWin.do';
var obj = $.ajax({
	url : queryUrl,
	async : true,
	dataType : "json",
	type : "POST",
	success : function(json) {
		$("#todaywin").html(json);
	}
});
</script>
<script language="javascript" src="${pageContext.request.contextPath}/js/replenishFooter.js"></script>
