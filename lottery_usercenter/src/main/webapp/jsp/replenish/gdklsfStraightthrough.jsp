<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.action.OddMapProxy,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.periods.entity.GDPeriodsInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<%

GDPeriodsInfo cqp=(GDPeriodsInfo)request.getAttribute("RunningPeriods");
String mReplenishMent = request.getAttribute("mReplenishMent").toString();

long kjTime=1000*90;

long fpTime=0;
if(cqp!=null)
{

	fpTime=Long.valueOf(request.getAttribute("StopTime").toString());
	kjTime=Long.valueOf(request.getAttribute("KjTime").toString());

}

%>
<script type="text/javascript">
var onrefresh = false;
var timer; 

function openHelp(infoLink){
	//alert(infoLink.href);
	//需要打开的页面URL，设置为实际值
	var url = "${pageContext.request.contextPath}/images/replenishHelp.gif";
	//打开模式窗口，固定写法，不要改变
	var ret = openModalDialogReport('${pageContext.request.contextPath}/jsp/statreport/common/ModalDialogPage.jsp',url,1006,800);
	
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
  
    function rOp(vo){
    	if($("#searchType").val()!=2){
	    	$("#moneyOther").val("");
	    	if($("#fpTime").html()=="00:01"){
				$("#rBtnSubmitOther").attr("disabled","disabled");
			}else{
				$("#rBtnSubmitOther").removeAttr("disabled");
			}
	    	if ($("#searchType").val()=="1"){
	    		//alert("溫馨提示:實占下才可以進行補貨操作.");
	    	}else{
	   	        var attribute=$(vo).parent().prev().prev().prev().prev().prev().prev().html();
	   	        var item = $("input[@name='selectType']:checked").val();
	   	        $("#playFinalTypeOther").val(item);
	   	        $("#attributeOther").val(attribute.replace(/\、/g,'|'));
	   	        $("#rAttributeOther").html(attribute);
	   	        $("#rFinalTypeOther").html(getChnName(item));
	   	        $("#rPlateOther").val($("#plate").val());
	   	    	$("#lotteryType").val("GDKLSF");
	   	        $("#rOddsOther").html($("#"+item).next().children().children().html());
	   	        var rmax = $(vo).parent().parent().children().next().next().children().html(); //限额
		        $("#maxMoneyOtherStr").html(rmax);
		        $("#maxMoneyOther").val(rmax);
		        if(rmax<10) {
		     		alert("限額小於10不能補貨.");
		     		return false;
		     	}
		     	var offset = getElementOffsetForReplenish($(vo));
			    var $div = setElementOffset($("#replenishShowOther"), offset);
			    $div.show();
		        $("#moneyOther").focus();
	   	     	getTypeCodeState("GDLM",item,$("#attributeOther").val());
	    	}  
    	}
    }
	
	$(".rOp").live("click", function () {
		$("#moneyOther").val("");
    	<%if(isSub){
    		if(replenishment !=null){%>
    			rOp(this);
    	<%	}
    	%>
        <%}else if(Constant.OPEN.equals(mReplenishMent)){%> 
           rOp(this);
    	<%}%>
    });  

    $(".fOp").live("click", function () {
    	var item = $("input[@name='checkType']:checked").val();
    	submitPageAjaxLM(item,$(this).prev().val());
    });
       
    var options = { 
 	       // target:        '#output2',   // target element(s) to be updated with server response 
 	        beforeSubmit:  validate, // pre-submit callback 
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
    
});


function getChnName(type){
	var chnName ="";
	if(type=="GDKLSF_STRAIGHTTHROUGH_RX2"){
		chnName="任選二";
    }else if(type=="GDKLSF_STRAIGHTTHROUGH_RX3"){
	    $chnName="任選三";
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
    var replenishBtn = "<td><input type=\"button\" class=\"rOp\" value=\"單补\" /></td>";
	<%if(isSub){
		if(replenishment ==null){%>
		  replenishBtn = "<td>&nbsp;</td>";
	<%	}
	%>
    <%}else if(Constant.CLOSE.equals(mReplenishMent)){%> 
          replenishBtn = "<td>&nbsp;</td>";
	<%}%>
	
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
							+ replenishBtn
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
	    var times = $("#times").val(); //記錄補貨的提交的筆數
	    var plate = $("#plate").val();
	    //逐筆AJAX提交進行補貨操作
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
	//補貨成功后再單獨更新補貨狀態，AJAX,quickNo就是提交補貨單的行號
	$("#winStateList"+json[0].quickNo+"").html(json[0].result);
}
//快速补出--END

//处理行变色,因为行是根据三种情况而变色的，所以这里处理鼠标经过时要保存原始的CLASS
function changeColorToYL($this,className){
     oldClass=className;
     $this.className="yl";
     $this.children.className="yl";
}
function changeColorToOld($this){
     $this.className=oldClass;
}

</script> 

<s:form action="replenishSet.action" namespace="replenish" id="rForm" method="post">
 <input type="hidden" name="subType" id="subType" value="<s:property value="#request.subType"/>"/>
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
        <s:if test="#request.openReport==0"><option value = "2" <s:if test="searchType==2">selected</s:if>>公司占</option></s:if>
      </select>
      <select id="plate" name="plate" style="display:none">
        <option value ="A" <s:if test='plate=="A"'> selected</s:if>>A盘</option>
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
	        <option value ="90" <s:if test="#request.refleshTime==90"> selected</s:if>>90秒</option>
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
                </table>
		<table id="Tab_top_center" border="0" cellspacing="0" cellpadding="0">		
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
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX2.fpState=="0" '> class="even"</s:if><s:else>class="even3"</s:else>>任选二</td>
		    <td class="even3">选二连直</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_R2LZ.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>选二连组</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX3.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>任选三</td>
		    <td class="even3">选三前直</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_R3LZ.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>选三前组</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX4.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>任选四</td>
		    <td <s:if test='#request.RunningPeriods.state=="2" && #request.rMap.GDKLSF_STRAIGHTTHROUGH_RX5.fpState=="0"'> class="even"</s:if><s:else>class="even3"</s:else>>任选五</td>
		    </tr>
            <tr>
              <!-- 賠率内容 -->
              <s:iterator value="#request.rMap">
	              <td <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class=""</s:if><s:else> class="even3"</s:else>><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
	                <tr>
			            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <a href="javascript:void(0)" ></a>
			            </td>
			            <td style="text-align:center!important;" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <a href="javascript:void(0)" name="odd"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
			            </td>
			            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>" <s:if test="value.sortNo==5 || value.sortNo==2">class="even3"</s:if>>
			                <a href="javascript:void(0)" ></a>
			            </td>
		            </tr>
	              </table></td>
              </s:iterator>
            </tr>
            <tr>
              <!-- 注額内容 -->
              <s:iterator value="#request.rMap">
	              	<td <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0" && value.sortNo!=5 && value.sortNo!=2 '>class="fOp"</s:if><s:else>class="fOp even3"</s:else>>
                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>&rs=<s:property value="searchType" escape="false"/>" title="查看注單明細">
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
                 </td>
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
		<input id="quickBtn" type="button" class="btn2" value="快速補出" onclick="quickReplenish()" disabled="disabled"/></td>
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
