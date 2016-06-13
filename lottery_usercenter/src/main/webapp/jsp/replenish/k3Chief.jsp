<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.replenish.vo.ReplenishVO,com.npc.lottery.odds.entity.ShopsPlayOdds,
com.npc.lottery.periods.entity.JSSBPeriodsInfo,com.npc.lottery.member.entity.PlayType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/jssbAdmin.js"></script>

<%
JSSBPeriodsInfo cqp=(JSSBPeriodsInfo)request.getAttribute("RunningPeriods");
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

    $(".rOp").on("click", function () {
    	<%if(replenishment ==null && isSub){%>
        //alert("該功能已被禁用,請聯系上級");
        <%}else{%>
    	$("#money").val("");
    	if($("#fpTime").html()=="00:01"){
			$("#rBtnSubmit").attr("disabled","disabled");
		}else{
			$("#rBtnSubmit").removeAttr("disabled");
		}
    	if($("#kjTime").html()=="00:00"){
			onChangeSubmit();  
    	}
    	if ($("#searchType").val()=="1"){
    		//alert("溫馨提示:實占下才可以進行補貨操作.");
    	}else{
   	        var typeCode=$(this).prev().prev().children().children().children().children().attr("id");
   	        var typeCodeChnName=$(this).prev().prev().prev().children().html();
   	        $("#playFinalType").val(typeCode);
   	        $("#rFinalType").html(typeCodeChnName);
   	        var odd=$(this).prev().prev().children().children().children().children().next().children().children().html();
   	        $("#rOdds").val(odd);
   	     	$("#lotteryType").val("K3");
	        $("#rPlate").val($("#plate").val());
	        var offset = getElementOffsetForReplenish($(this));
		    var $div = setElementOffset($("#replenishShow"), offset);
		    $div.show();
	   	    $("#rOdds").focus();
   	        getTypeCodeState("K3",typeCode,"");
    	}      
    	<%}%>
    }); 
    
    $(".rOpS").on("click", function () {
    	//alert($(this).parent().parent().parent().parent().html());
    	<%if(replenishment ==null && isSub){%>
        //alert("該功能已被禁用,請聯系上級");
        <%}else{%>
    	$("#money").val("");
    	if($("#fpTime").html()=="00:01"){
			$("#rBtnSubmit").attr("disabled","disabled");
		}else{
			$("#rBtnSubmit").removeAttr("disabled");
		}
    	if($("#kjTime").html()=="00:00"){
			onChangeSubmit();  
    	}
    	if ($("#searchType").val()=="1"){
    		//alert("溫馨提示:實占下才可以進行補貨操作.");
    	}else{
    		var typeCode=$(this).parent().parent().parent().parent().prev().prev().children().children().children().children().attr("id");
   	        var typeCodeChnName=$(this).parent().parent().parent().parent().prev().prev().prev().children().children().html();
   	        $("#playFinalType").val(typeCode);
   	        $("#rFinalType").html(typeCodeChnName);
   	        var odd=$(this).parent().parent().parent().parent().prev().prev().children().children().children().children().next().children().children().html();
   	        $("#rOdds").val(odd);
   	     	$("#lotteryType").val("K3");
	        $("#rPlate").val($("#plate").val());
	        var offset = getElementOffsetForReplenish($(this));
		    var $div = setElementOffset($("#replenishShow"), offset);
		    $div.show();
	   	    $("#rOdds").focus();
   	        getTypeCodeState("K3",typeCode,"");
    	}      
    	<%}%>
    });  

    var options = { 
    		beforeSubmit:  validateForChief,
    		success:       showResponseDy,	 
 	        url:'ajaxReplenishSubmit.action',
 	        type:'post',
 	        dataType:'json'	     
 	    }; 
    
    
    $('#oddTable a').click(function() {
    	<%if(odd ==null && isSub){%>
         //alert("該功能已被禁用,請聯系上級");
        <%}else{%>
		  var typeCode=$(this).parent().attr("id");
		  if($(this).attr("name")=="jia")
			  changeOdds(typeCode,"jia");
		  else if($(this).attr("name")=="jian")
			  changeOdds(typeCode,"jian");
		  else if($(this).attr("name")=="notPlay")
		  {
			  var state = '0';
			  if($(this).parent().parent().attr("class")=="even3"){
		    	  state = '1';
		      }
			  changeNotPlay($(this).attr("id"),state);
		  }
		  else if($(this).attr("name")=="odd"){
			    var offset = getElementOffset($(this));
			    var $div = setElementOffset($("#oddsShow"), offset);
				var typeCodeChnName=$(this).parent().parent().parent().parent().parent().prev().children().html();
	   	        $("#oddFinalType").html(typeCodeChnName);
	   	        $("#oddValue").val($(this).children().html());
	   	        $("#oddTypeCode").val($(this).parent().prev().attr("id"));
			    $div.show();
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
	var num=$("#changeOddsNum").val().substring(1);
	oddsChangeAddOrSub(typeCode,jiajian,num);
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxK3BalloddsUpdate.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,jiajian:jiajian,odd:num},type:"POST",success:successChangeOdd});
	return ret;
	
}
function successChangeOdd(json){onChangeSubmit();}
//处理实时赔率加减  END



//手动修改赔率
function opOdd(){
	var ret = null;
	var odd = $("#oddValue").val();
	var typeCode = $("#oddTypeCode").val();
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxK3BalloddsUpdate.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj = $.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,odd:odd},type:"POST",success:successChangeOdd});
	//$("#oddForm").reset();
	$("#oddsShow").hide();
	return ret;
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

<!--内容开始-->
<s:form action="replenishSet.action" id="rForm" namespace="replenish" method="post">
<input type="hidden"  name="subType" id="subType" value="<s:property value="#request.subType"/>"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!--控制表格头部开始-->
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif">
	  <div id="Tab_top">
        <table id="Tab_top_right" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left"><select name="searchType" onchange="onChangeSubmit()" id="searchType">
        <option value = "1" <s:if test="searchType==1">selected</s:if>>虛注</option>
        <option value = "0" <s:if test="searchType==0">selected</s:if>>實占</option>
      </select><select id="plate" name="plate" onchange="onChangePlate()">
        <option value ="A" <s:if test='plate=="A"'> selected</s:if>>A盘</option>
        <option value ="B" <s:if test='plate=="B"'> selected</s:if>>B盘</option>
        <option value ="C" <s:if test='plate=="C"'> selected</s:if>>C盘</option>
      </select></td>
    <td width="80" align="right">
       <input id="refreshBtn" type="button" value="更新" onclick="onChangeSubmit()" style="display:none;"/>
       <span id="refreshTime">更新：<span style="margin-right:2px;" id="clTime">0秒</span></span>
       <span id="onrefresh" style="display:none;" class="blue">載入中...</span>
    </td>
    <td><select name="searchTime" id="searchTime" onchange="changeTime()">
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
	    <table border="0" cellpadding="0" cellspacing="0" id="Tab_top_left">
                  <tr>
				    <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
				    <td width="16" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                    <td style="padding:0 6px;"><strong class="green"><s:property value="#request.periodsNum"/>期</strong></td>
                    <td style="padding:0 6px;"><strong class="blue">總項盤口</strong></td>
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
		<table id="Tab_top_center" border="0" cellspacing="0" cellpadding="0" style="width:220px;margin:0 auto;">
  <tr>
     <td><strong class="orange"><s:property value="#request.lastLotteryPeriods.PeriondsNum" escape="false"/></strong><strong>期開獎</strong></td>
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
		<!-- 表格内容开始 一行六列-->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="mt4">
		  <tr>
		    <td valign="top" align="left"><table width="99%" border="0" cellpadding="0" cellspacing="0" class="king">
  <colgroup>
  <col width="13%" />
  <col width="33%" />
  <col width="27%" />
  <col width="27%" />
  </colgroup>
  <tr>
    <th>號</th>
    <th>賠率</th>
    <th>注额</th>
    <th>亏盈</th>
  </tr>
  <tr class="sub_th">
    <td colspan="4">三军</td>
  </tr>
  <!-- 内容 -->
  <s:iterator value="#request.sjMap">
      <jsp:include page="/jsp/replenish/k3MiddleForChiefSj.jsp" />
  </s:iterator>  
  <tr>
    <td colspan="4">退水后总注额：<strong><s:property value="#request.ballPetTotal_sj" escape="false"/></strong></strong></td>
  </tr>
  <tr class="sub_th">
    <td colspan="4">大小</td>
  </tr>
  <!-- 内容 -->
  <s:iterator value="#request.dxMap">
      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
  </s:iterator>  
  <tr>
    <td colspan="4">全骰盈利：<strong><s:property value="#request.ballPetTotal_dx" escape="false"/></strong></td>
  </tr>
  <tr class="sub_th">
    <td colspan="4">全骰</td>
  </tr>
  <!-- 内容 -->
  <s:iterator value="#request.qsMap">
      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
  </s:iterator> 
  <tr>
    <td colspan="4">混骰盈利：<strong><s:property value="#request.ballPetTotal_qs" escape="false"/></strong></td>
  </tr>
  </table>
</td>
		    <td valign="top" align="left"><table width="99%" border="0" cellpadding="0" cellspacing="0" class="king">
		      <colgroup>
		        <col width="13%" />
		        <col width="33%" />
		        <col width="27%" />
		        <col width="27%" />
		        </colgroup>
		      <tr>
		        <th>號</th>
		        <th>賠率</th>
		        <th>注额</th>
		        <th>亏盈</th>
		        </tr>
		      <tr class="sub_th">
		        <td colspan="4">围骰</td>
		        </tr>
		      <!-- 内容 -->
			  <s:iterator value="#request.wsMap">
			      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
			  </s:iterator> 
		      <tr>
		        <td colspan="4">混骰盈利：<strong><s:property value="#request.ballPetTotal_ws" escape="false"/></strong></td>
		        </tr>
		      <tr class="sub_th">
		        <td colspan="4">点数</td>
		        </tr>
		      <!-- 内容 -->
			  <s:iterator value="#request.dsMap">
			      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
			  </s:iterator> 
		      <tr>
		        <td colspan="4">3点/18点盈利：<strong><s:property value="#request.ballPetTotal_ds" escape="false"/></strong></td>
		        </tr>
		      </table></td>
			<td valign="top" align="left"><table width="99%" border="0" cellpadding="0" cellspacing="0" class="king">
			  <colgroup>
			    <col width="13%" />
			    <col width="33%" />
			    <col width="27%" />
			    <col width="27%" />
			    </colgroup>
			  <tr>
			    <th>號</th>
			    <th>賠率</th>
			    <th>注额</th>
			    <th>亏盈</th>
			    </tr>
			  <tr class="sub_th">
			    <td colspan="4">长牌</td>
			    </tr>
			  <!-- 内容 -->
			  <s:iterator value="#request.cpMap">
			      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
			  </s:iterator> 
			  <tr>
			    <td colspan="4">退水后总注额：<strong><s:property value="#request.ballPetTotal_cp" escape="false"/></strong></td>
			    </tr>
			  <tr class="sub_th">
			    <td colspan="4">短牌</td>
			    </tr>
			  <!-- 内容 -->
			  <s:iterator value="#request.dpMap">
			      <jsp:include page="/jsp/replenish/k3MiddleForChief.jsp" />
			  </s:iterator> 
			  <tr>
			    <td colspan="4">混骰盈利：<strong><s:property value="#request.ballPetTotal_dp" escape="false"/></strong></td>
			    </tr>
			  </table></td>
			<td width="174" valign="top" align="left">
			<div class="jsright" id='jsright'></div>
			</td>
			</tr>
		</table>
        <!-- 表格内容结束 一行六列-->
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
	  <input name="computeBtn" id="computeBtn" type="button" class="ml10 zs btn2" value="計算補貨" onclick="return onChangeSubmit()"/>
	  <!-- <input name="quickBtn" id="quickBtn" type="button" class="ml10 sl btn2" value="快速補單" disabled="disabled"/> -->
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
updatejsRightStat();
</script>
<script language="javascript" src="${pageContext.request.contextPath}/js/replenishFooter.js"></script>