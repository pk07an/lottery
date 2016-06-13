<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.odds.action.OddMapProxy" %>
<%@ page import="com.npc.lottery.common.Constant "%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<script>
function changeOdds(typeCode,jiajian)
{
	var ret=null;
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxgdBalloddsUpdate.action?typeCode=' +typeCode+"&jiajian="+jiajian;	
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,jiajian:jiajian},type:"POST",success:successChangeOdd});
	return ret;
	
}
function successChangeOdd(json)
{
//alert(inputName);	
	var  error = json.error;
	if(undefined!=error){alert(error);return null;}
	
var changedValue=json.changedValue;
var inputName=json.typeCode;
$('input[name='+inputName+']').parent().parent().parent().next().html(changedValue);
$('input[name='+inputName+']').val(changedValue);
//alert($('input[name='+inputName+']').parent().parent().parent().next().html());
}
$(document).ready(function() {
	
	
	
	$('#oddTable a').click(function() {
		  var typeCode=$(this).parent().prev().children().attr("name");
		  if($(this).attr("name")=="jia")
			  changeOdds(typeCode,"jia");
		  else if($(this).attr("name")=="jian")
			  changeOdds(typeCode,"jian");
	        
		});
	
	$('#s1,#s2').click(function() {
		 var btName=$(this).attr("name");
		 $('#submitType').val(btName);
		 $('#oddsForm').submit();
		  
		});
	
	
	
	 $("#tj input,.king input").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(/[^\d.]/g,""));
		        $(this).val($(this).val().replace(/^\./g,""));  
		       $(this).val($(this).val().replace(/\.{2,}/g,"."));  
		       $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	       }
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();
	        $(this).val(tmptxt.replace(/[^\d.]/g,""));     
	    }).css("ime-mode", "disabled"); 
	 var tt = document.getElementById("periodsState").value;
	 if(tt!="")alert("當前盤未開盤，不能修改");

});

window.onload=function(){  
    GetRTime();  
} 
</script>
<body>

<% String ballNum="1"; String chinaBall="";String preName="FIRST";%>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST==subType'><%ballNum="1";chinaBall="第一球";preName="FIRST"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND==subType'><%ballNum="2";chinaBall="第二球";preName="SECOND"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD==subType'><%ballNum="3"; chinaBall="第三球";preName="THIRD";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH==subType'><%ballNum="4";chinaBall="第四球"; preName="FORTH";%></s:if>
<s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH==subType'><%ballNum="5";chinaBall="第五球"; preName="FIFTH";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH==subType'><%ballNum="6";chinaBall="第六球"; preName="SIXTH";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH==subType'><%ballNum="7";chinaBall="第七球";preName="SEVENTH"; %></s:if>
<s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH==subType'><%ballNum="8";chinaBall="第八球"; preName="EIGHTH";%></s:if> 


<%
Map<String,ShopsPlayOdds> oddMap=(Map<String,ShopsPlayOdds>)request.getAttribute("GDShopOddsMap");  
Map<String,String> moneyMap=(Map<String,String>)request.getAttribute("moneyTotalMap");  
if(moneyMap == null) moneyMap= new HashMap<String,String>();

%>

<div class="content" style="width:98%;">
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  <div class="title clearfix">
    <div class="fl"><%=chinaBall %>賠率設置</div>
    <div class="fl ml10" >
     
        	 <span id="clTime">30秒</span>
		<select id="searchTime" name="searchTime" onchange="changeTime()" >
	        <option value ="30">30秒</option>
	        <option value ="60" <s:if test="searchTime==60"> selected</s:if>>60秒</option>
	      </select>
    </div>
  </div>
  <s:form action="balloddsUpdate" id="oddsForm" namespce="admin" name="balloddsUpdate">
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king" id="oddTable">
  <tbody><tr>
    <th width="4%">號碼</th>
    <th width="8%">賠率/封號</th>
    <th width="6%">賠率</th>
    <th width="7%">下注總額</th>
    <th width="4%">號碼</th>
    <th width="8%">賠率/封號</th>
    <th width="6%">賠率</th>
    <th width="7%">下注總額</th>
    <th width="4%">號碼</th>
    <th width="8%">賠率/封號</th>
    <th width="6%">賠率</th>
    <th width="7%">下注總額</th>
    <th width="4%">號碼</th>
    <th width="8%">賠率/封號</th>
    <th width="6%">賠率</th>
    <th width="7%">下注總額</th>
    </tr>
  <tr>
    <td><span class="ball-bg ball-blue">1</span></td>
    <td>
      <ul class="jj clearfix">
        <li class="in"><input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_1").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_1"></li>
        <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
        <li class="cb"><input type="checkbox" value="GDKLSF_BALL_<%=preName%>_1" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_1").getState())) out.println("checked"); %>></li>
      </ul>
    </td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_1").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_1")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_1")%></span></td>
    <td><span class="ball-bg ball-blue">6</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_6").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_6">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_6" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_6").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_6").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_6")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_6")%></span></td>
    <td><span class="ball-bg ball-blue">11</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_11").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_11">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_11" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_11").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_11").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_11")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_11")%></span></td>
    <td><span class="ball-bg ball-blue">16</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_16").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_16">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_16" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_16").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_16").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_16")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_16")%></span></td>
  </tr>
  <tr>
    <td><span class="ball-bg ball-blue">2</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_2").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_2">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_2" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_2").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_2").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_2")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_2")%></span></td>
    <td><span class="ball-bg ball-blue">7</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_7").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_7">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_7" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_7").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_7").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_7")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_7")%></span></td>
    <td><span class="ball-bg ball-blue">12</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_12").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_12">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_12" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_12").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_12").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_12")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_12")%></span></td>
    <td><span class="ball-bg ball-blue">17</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_17").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_17">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_17" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_17").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_17").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_17")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_17")%></span></td>
  </tr>
  <tr>
    <td><span class="ball-bg ball-blue">3</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_3").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_3">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_3" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_3").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_3").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_3")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_3")%></span></td>
    <td><span class="ball-bg ball-blue">8</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_8").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_8">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_8" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_8").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_8").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_8")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_8")%></span></td>
    <td><span class="ball-bg ball-blue">13</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_13").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_13">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_13" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_13").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_13").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_13")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_13")%></span></td>
    <td><span class="ball-bg ball-blue">18</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_18").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_18">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_18" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_18").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_18").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_18")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_18")%></span></td>
  </tr>
  <tr>
    <td><span class="ball-bg ball-blue">4</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_4").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_4">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_4" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_4").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_4").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_4")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_4")%></span></td>
    <td><span class="ball-bg ball-blue">9</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_9").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_9">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_9" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_9").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_9").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_9")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_9")%></span></td>
    <td><span class="ball-bg ball-blue">14</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_14").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_14">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_14" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_14").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_14").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_14")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_14")%></span></td>
    <td><span class="ball-bg ball-red">19</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_19").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_19">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_19" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_19").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_19").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_19")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_19")%></span></td>
  </tr>
  <tr>
    <td><span class="ball-bg ball-blue">5</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_5").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_5">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_5" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_5").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_5").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_5")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_5")%></span></td>
    <td><span class="ball-bg ball-blue">10</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_10").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_10">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_10" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_10").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_10").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_10")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_10")%></span></td>
    <td><span class="ball-bg ball-blue">15</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_15").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_15">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_15" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_15").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_15").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_15")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_15")%></span></td>
    <td><span class="ball-bg ball-red">20</span></td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_20").getRealOdds() %>" name="GDKLSF_BALL_<%=preName%>_20">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_20" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_20").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_20").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_20")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_20")%></span></td>
  </tr>
  <tr>
    <td class="even">大</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_DA">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DA").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DA").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DA")%></span></td>
    <td class="even">單</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_DAN">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DAN").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DAN").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_DAN")%></span></td>
    <td class="even">尾大</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_WD">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WD").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WD").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WD")%></span></td>
    <td class="even">合數單</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_HSD">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSD").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSD").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSD")%></span></td>
    </tr>
  <tr>
    <td class="even">小</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_X">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_X").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_X").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_X")%></span></td>
    <td class="even">雙</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_S">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_S").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_S").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_S")%></span></td>
    <td class="even">尾小</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_WX">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WX").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WX").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_WX")%></span></td>
    <td class="even">合數雙</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_<%=ballNum %>_HSS">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_<%=ballNum %>_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSS").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSS").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_"+ballNum+"_HSS")%></span></td>
    </tr>
  <tr>
    <td class="even">東</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_DONG").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_DONG">
        </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_DONG" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_DONG").getState())) out.println("checked"); %>>
        </li>
      </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_DONG").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_DONG")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_DONG")%></span></td>
    <td class="even">南</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_NAN").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_NAN">
        </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_NAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_NAN").getState())) out.println("checked"); %>>
        </li>
      </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_NAN").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_DONG")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_DONG")%></span></td>
    <td class="even">西</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_XI").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_XI">
        </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_XI" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_XI").getState())) out.println("checked"); %>>
        </li>
      </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_XI").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_XI")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_XI")%></span></td>
    <td class="even">北</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_BEI").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_BEI">
        </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_BEI" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_BEI").getState())) out.println("checked"); %>>
        </li>
      </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_BEI").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_BEI")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_BEI")%></span></td>
  </tr>
  <tr>
    <td class="even">中</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_Z").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_Z">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_Z" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_Z").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_Z").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_Z")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_Z")%></span></td>
    <td class="even">發</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_F").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_F">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_F" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_F").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_F").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_F")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_F")%></span></td>
    <td class="even">白</td>
    <td><ul class="jj clearfix">
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_BALL_"+preName+"_B").getRealOdds() %>" name="GDKLSF_BALL_<%=preName %>_B">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jia"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a><a href="javascript:void(0)" name="jian"><img height="16" width="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_BALL_<%=preName%>_B" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_BALL_"+preName+"_B").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_BALL_"+preName+"_B").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_BALL_"+preName+"_B")==null?0:moneyMap.get("GDKLSF_BALL_"+preName+"_B")%></span></td>
    <td class="even">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</tbody></table>
  <div id="tj">
    <div class="tj">
    <span>
    <s:hidden name="subType" value="%{#parameters.subType[0]}"/>
    	 <input type="hidden" name="submitType" value="Single" id="submitType"/>
    	 <input type="button" value="提 交" class="btn" name="" id="s1">
     </span>
     <span class="ml10">
     	<input type="reset" value="重 置" class="btn" name="">
     </span>
     </div>
    <div class="all clearfix"><span class="red">统一修改：</span><span class="ml6"><input type="radio" value="dan" name="integrate" checked>單</span><span class="ml6"><input type="radio" value="shuang" name="integrate">雙</span><span class="ml6"><input type="radio" value="da" name="integrate">大</span><span class="ml6"><input type="radio" value="xiao" name="integrate">小</span><span class="ml6"><input type="radio" value="all" name="integrate">全部</span><span class="red ml6">赔率</span><input class="b_g ml3 no" maxlength="9" size="6" value="" name="odds"><span class="ml6"><input type="button" value="统一修改" class="btn_4" name="integrate" id="s2"></span></div>
  </div>
  <s:hidden id="periodsState" name="periodsState"></s:hidden>
  </s:form>
</div>
<script type="text/javascript">
var clDate=1000*Math.floor($("#searchTime").val());
function GetRTime(){ 
    clDate=clDate-1000; 
    var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
    if(clDate>= 0){        
         document.getElementById("clTime").innerHTML=clnS+"秒";  
    }  
    else {  
    	clDate=1000*Math.floor($("#searchTime").val());
//     	window.location.reload();
    	changeTime();
    } 
 
    setTimeout("GetRTime()",1000);  
}  
function changeTime()
{
	var searchTime = $("#searchTime").val();
	var type = null;
	if(<%=ballNum%>=="1")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST %>';
	}else if(<%=ballNum%>=="2")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND %>';
	}else if(<%=ballNum%>=="3")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD %>';
	}else if(<%=ballNum%>=="4")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH %>';
	}else if(<%=ballNum%>=="5")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH %>';
	}else if(<%=ballNum%>=="6")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH %>';
	}else if(<%=ballNum%>=="7")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH %>';
	}else if(<%=ballNum%>=="8")
	{
		type = '<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH %>';
	}

	window.location="${pageContext.request.contextPath}/admin/oddsSet.action?searchTime="+searchTime+"&subType="+type+"";
	
}
</script>
</body></html>

