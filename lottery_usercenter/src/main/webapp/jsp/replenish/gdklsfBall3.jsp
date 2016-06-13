<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.replenish.vo.ReplenishVO,com.npc.lottery.odds.action.OddMapProxy,
com.npc.lottery.common.Constant,com.npc.lottery.periods.entity.GDPeriodsInfo,
com.npc.lottery.member.entity.PlayType,com.npc.lottery.replenish.vo.ZhanDangVO" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<%Map<String,ZhanDangVO> map = (Map<String,ZhanDangVO>)request.getAttribute("result");%>
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
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
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
        var left = offset.left-20;
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
	    	$("#money").val("");
	    	if($("#fpTime").html()=="00:01"){
				$("#rBtnSubmitOther").attr("disabled","disabled");
			}else{
				$("#rBtnSubmitOther").removeAttr("disabled");
			}
	    	if($("#kjTime").html()=="00:00"){
				onChangeSubmit();  
	    	}
	    	if ($("#searchType").val()=="1"){
	    		//alert("溫馨提示:實占下才可以進行補貨操作.");
	    	}else{
	   	        var typeCode=$(vo).prev().prev().children().children().children().children().attr("id");
	   	        var typeCodeChnName=$(vo).prev().prev().prev().children().html();
	   	        $("#playFinalTypeOther").val(typeCode);
	   	        $("#rFinalTypeOther").html(typeCodeChnName);
	   	        var odd=$(vo).prev().prev().children().children().children().children().next().children().children().html();
	   	        $("#rOddsOther").html(odd);
	   	        var max = $(vo).prev().children().children().html();
	   	        $("#maxMoneyOther").html(max);
	   	        var loseM = 1;
	   	        if($(vo).children().children().html()!=""){
	   	        	loseM = $(vo).children().children().html();
	   	        }
	   	        if(loseM >= 0) {
		     		alert("盈虧為虧時才能補貨.");
		     		return false;
		     	}
	   	        if(max=="-"){
	   	        	alert("限額小於10不能補貨.");
		     		return false;
	   	        }
		        if($("#maxMoneyOther").val()<10) {
		     		alert("限額小於10不能補貨.");
		     		return false;
		     	}
	   	     	$("#lotteryType").val("GDKLSF");
		        $("#rPlateOther").val($("#plate").val());
		        var offset = getElementOffsetForReplenish($(vo));
			    var $div = setElementOffset($("#replenishShowOther"), offset);
			    $div.show();
		   	    $("#rOddsOther").focus();
		   	    getTypeCodeState("GDBALL",typeCode,'');
	    	}  
    	}
    }
	
    $(".rOp").on("click", function () {
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
    
    $('#replenishFormOther').submit(function() { 
    	$("#replenishShowOther").hide();
        $(this).ajaxSubmit(options); 
        return false; 
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
function successChangeOdd(json){window.location.reload();}
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

function changeLoseSort(){
	if($("#loseSort").val()=="lose"){
		$("#loseSort").val("win");
	}else{
		$("#loseSort").val("lose");
	}
	onChangeSubmit();
}

//处理行变色,因为行是根据三种情况而变色的，所以这里处理鼠标经过时要保存原始的CLASS
function changeColorToYL_Middle($this,className,fpState){
	oldClass=className;
	if(fpState=='1'){
	     $this.className="even3Tr";
	     $this.children.className="even3Tr";
	}else{
		$this.className="noneTr";
	     $this.children.className="noneTr";
	}
}
function changeColorToOld_Middle($this){
	$this.className="yl";
    $this.children.className="yl";
}
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
<% String ballNum="1"; String chinaBall="";String preName="FIRST";%>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST==subType'><%ballNum="1";chinaBall="第一球";preName="FIRST"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND==subType'><%ballNum="2";chinaBall="第二球";preName="SECOND"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD==subType'><%ballNum="3"; chinaBall="第三球";preName="THIRD";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH==subType'><%ballNum="4";chinaBall="第四球"; preName="FORTH";%></s:if>
<s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH==subType'><%ballNum="5";chinaBall="第五球"; preName="FIFTH";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH==subType'><%ballNum="6";chinaBall="第六球"; preName="SIXTH";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH==subType'><%ballNum="7";chinaBall="第七球";preName="SEVENTH"; %></s:if>
<s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH==subType'><%ballNum="8";chinaBall="第八球"; preName="EIGHTH";%></s:if> 

<!--内容开始-->
<div class="content">
<s:form action="replenishSet.action" id="rForm" namespace="replenish" method="post">
  <input type="hidden" name="subType" id="subType" value="<s:property value="#request.subType"/>"/>
  <input type="hidden" id="loseSort" value="<s:property value="#request.loseSort"/>"/><!-- 用于盈亏排序 -->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <!--控制表格头部开始-->
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif">
	  <div id="Tab_top">
	    <table id="Tab_top_right" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td align="left">
      <select class="red" name="searchArray" id="searchArray" onchange="onChangeSubmit()">
        <option value ="lose" <s:if test='searchArray=="lose"'> selected</s:if>>按虧盈排列</option>
        <option value ="ball" <s:if test='searchArray=="ball"'> selected</s:if>>按號球排列</option>
      </select> 
      <select name="searchType" onchange="onChangeSubmit()" id="searchType">
        <option value = "1" <s:if test="searchType==1">selected</s:if>>虛注</option>
        <option value = "0" <s:if test="searchType==0">selected</s:if>>實占</option>
        <s:if test="#request.openReport==0"><option value = "2" <s:if test="searchType==2">selected</s:if>>公司占</option></s:if>
      </select>
      <select id="plate" name="plate" style="display:none">
        <option value ="A" <s:if test='plate=="A"'> selected</s:if>>A盘</option>
      </select></td>
    <td width="80" align="right">
       <input id="refreshBtn" type="button" value="更新" onclick="onChangeSubmit()" style="display:none;"/>
       <span id="refreshTime">更新：<span style="margin-right:2px;" id="clTime">0秒</span></span>
       <span id="onrefresh" style="display:none;" class="blue">載入中...</span>
    </td>
    <td>
      <select name="searchTime" id="searchTime" onchange="changeTime()">
	        <option value ="NO">-NO-</option>
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
                <td style="padding:0 6px;"><strong class="blue"><%=chinaBall %></strong></td>
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
			<table id="Tab_top_center" border="0" cellspacing="0" cellpadding="0" style="width:360px;margin:0 auto;">
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
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="oddTable">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center">
		<!-- 表格内容开始 一行五列-->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="mt4">
		  <tr>
		    <td valign="top" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
		      <colgroup>
		        <col width="10%" />
		        <col width="34%" />
		        <col width="28%" />
		        <col width="28%" />
		        </colgroup>
		      <tr>
		        <th>號</th>
		        <th>賠率</th>
		        <th>注額</th>
		        <th>虧盈</th>
		        </tr>
        <s:iterator value="#request.BallMap">
        <tr <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class="noneTr"</s:if><s:else>class="even3Tr"</s:else> onmouseover="changeColorToYL(this,this.className)" onmouseout="changeColorToOld(this)">
            <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
               <a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay">
               <s:if test="value.playTypeName=='19' || value.playTypeName=='20'">
	               <strong class="red">
               </s:if><s:else>
	               <strong class="blue">
               </s:else>
               <s:property value="value.playTypeName" escape="false"/></strong></a>
            </td>
            <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
		          <tr>
		            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		            <td style="text-align:center!important;">
		                <a href="javascript:void(0)"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
		            </td>
		            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		           
		            </tr>
		          </table></td>
                    <td class="fOp" >
                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>&rs=<s:property value="searchType" escape="false"/>" title="查看注單明細">
					    <s:if test="value.avLose == 0" >
			            	<span class="green">-</span>
					    </s:if>
			            <s:if test="value.avLose < 0 ">
			            	<span class="green"><s:property value="value.money" escape="false"/></span>
					    </s:if>
					    <s:if test="value.avLose > 0">
					         <span class="green"><s:property value="value.money" escape="false"/></span>&nbsp;
					         <strong style="color:#7300AA"><s:property value="value.avLose" escape="false"/></strong>
					    </s:if></a>
				    </td>
				    <td class="rOp"><a href="javascript:void(0)" title="補單">
					    <s:if test="value.loseMoney==0">
					        </a><span>-</span>
					    </s:if><s:elseif test="value.loseMoney>0">
					        <span ><s:property value="value.loseMoney" escape="false"/></span>
					    </s:elseif><s:else>
					        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
					    </s:else></a>
				    </td>
           </tr>
        </s:iterator> 
      </table></td>
      <td valign="top" align="left" style="padding-right:1px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
        <colgroup>
        <col width="10%" />
        <col width="34%" />
        <col width="28%" />
        <col width="28%" />
        </colgroup>
        <tr>
          <th colspan="4">
              <s:if test="#request.loseSort=='lose'">
                  <a href="javascript:changeLoseSort()"><strong class="red">按“虧損”额排列</strong></a>
              </s:if><s:else>
                  <a href="javascript:changeLoseSort()"><strong >按“盈利”額排列</strong></a>
              </s:else>
          </th>
        </tr>
        <s:iterator value="#request.sorMap">
	        <tr class="yl" onmouseover="changeColorToYL_Middle(this,this.className,<s:property value='value.fpState'/>)" onmouseout="changeColorToOld_Middle(this)">
	            <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
	               <a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay">
		           <s:if test="value.playTypeName=='19' || value.playTypeName=='20'">
		               <strong class="red">
	               </s:if><s:else>
		               <strong class="blue">
	               </s:else>
	               <s:property value="value.playTypeName" escape="false"/></strong></a>
	            </td>
	            <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
			          <tr>
			            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>" class="yl">
			                <a href="javascript:void(0)"></a>
			            </td>
			            <td style="text-align:center!important;" class="yl">
			                <a href="javascript:void(0)"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
			            </td>
			            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>" class="yl">
			                <a href="javascript:void(0)"></a>
			            </td>
			           
			            </tr>
			          </table></td>
	                    <td class="fOp yl" >
	                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>&rs=<s:property value="searchType" escape="false"/>"  title="查看注單明細">
						    <s:if test="value.avLose == 0" >
				            	<span class="green">-</span>
						    </s:if>
				            <s:if test="value.avLose < 0 ">
				            	<span class="green"><s:property value="value.money" escape="false"/></span>
						    </s:if>
						    <s:if test="value.avLose > 0">
						         <span class="green"><s:property value="value.money" escape="false"/></span>&nbsp;
						         <strong style="color:#7300AA"><s:property value="value.avLose" escape="false"/></strong>
						    </s:if></a>
					    </td>
					    <td class="rOp yl"><a href="javascript:void(0)" title="補單">
						    <s:if test="value.loseMoney==0">
						        </a><span>-</span>
						    </s:if><s:elseif test="value.loseMoney>0">
						        <span ><s:property value="value.loseMoney" escape="false"/></span>
						    </s:elseif><s:else>
						        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
						    </s:else></a>
					    </td>
	           </tr>
        </s:iterator> 
      </table></td>    
      <td valign="top" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
		  <colgroup>
		    <col width="10%" />
		    <col width="34%" />
		    <col width="28%" />
		    <col width="28%" />
		    </colgroup>
		  <tr>
		    <th>號</th>
		    <th>賠率</th>
		    <th>注額</th>
		    <th>虧盈</th>
		    </tr>
		    <s:iterator value="#request.GDShopOddsMap">
        <tr <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class="noneTr"</s:if><s:else>class="even3Tr"</s:else> onmouseover="changeColorToYL(this,this.className)" onmouseout="changeColorToOld(this)">
            <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
               <a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay">
	           <s:if test='value.playFinalType.indexOf("HSD")!=-1'>
		               <strong >合單</strong></a>
		       </s:if>
               <s:elseif test='value.playFinalType.indexOf("HSS")!=-1'>
		               <strong >合雙</strong></a>
		       </s:elseif>
		       <s:else>
	           		   <strong ><s:property value="value.playTypeName" escape="false"/></strong></a>
	           </s:else>
            </td>
            <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
		          <tr>
		            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		            <td style="text-align:center!important;">
		                <a href="javascript:void(0)"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
		            </td>
		            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		           
		            </tr>
		          </table></td>
                    <td class="fOp" >
                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>" title="查看注單明細">
					    <s:if test="value.avLose == 0" >
			            	<span class="green">-</span>
					    </s:if>
			            <s:if test="value.avLose < 0 ">
			            	<span class="green"><s:property value="value.money" escape="false"/></span>
					    </s:if>
					    <s:if test="value.avLose > 0">
					         <span class="green"><s:property value="value.money" escape="false"/></span>&nbsp;
					         <strong style="color:#7300AA"><s:property value="value.avLose" escape="false"/></strong>
					    </s:if></a>
				    </td>
				    <td class="rOp"><a href="javascript:void(0)" title="補單">
					    <s:if test="value.loseMoney==0">
					        </a><span>-</span>
					    </s:if><s:elseif test="value.loseMoney>0">
					        <span ><s:property value="value.loseMoney" escape="false"/></span>
					    </s:elseif><s:else>
					        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
					    </s:else></a>
				    </td>
           </tr>
        </s:iterator> 

      </table>
       <div style="margin-top:24px;margin-left:10px;text-align:left;">
			    <p class="mt15">總投注額：<strong class="green"><s:property value="#request.total"/></strong></p>
				<p class="mt15">最高虧損：<strong class="red"><s:property value="#request.topLoseMoney"/></strong></p>
				<p class="mt15">最高盈利：<strong class="green"><s:property value="#request.topWinMoney"/></strong></p>
	   </div> 
	   </td>
      <td width="160" valign="top" align="left"><!-- 右边统计开始--><jsp:include page="/jsp/replenish/gdklsfRightBar.jsp" /></td>
		  </tr>
		</table>
        <!-- 表格内容结束 一行五列-->
		</td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
<!--控制中间内容结束-->
  
  <!--控制底部操作按钮开始-->
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align="center"><span >平均虧損：</span><input id="avLose" name="avLose" value="<s:property value="#request.avLose"/>" size="16" maxlength="16" class="b_g"/>
				<span >赔率：</span><input id="avOdd" name="avOdd" value="<s:property value="#request.avOdd"/>" size="5" maxlength="16" class="b_g"/>
	  <span >退水：</span><input id="avCommission" name="avCommission" value="<s:property value="#request.avCommission"/>" size="5" maxlength="16" class="b_g"/>
	  <input name="computeBtn" type="button" class="ml10 zs btn2" value="計算補貨" onclick="return onChangeSubmit()"/>
	  <input name="quickBtn" type="button" class="ml10 sl btn2" value="快速補單" />
	  <input name="helpBtn" type="button" class="ml10 blue_btn btn2" value="補貨說明"  onclick="return openHelp(this)"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
<!--控制底部操作按钮结束-->
</table>
</s:form>
  
<!--内容结束-->

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
