<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.BJSCPeriodsInfo,com.npc.lottery.rule.BJSCRule" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
  <%-- <script>
function forwardToLotteryHistory(optValue)
 {
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/member/enterLotResultHistory.do?subType="+optValue;
 }
 </script> --%>
 <script language="javascript">
function stopPriod(priodNum,type){
// 	alert(priodNum);
	if(confirm("確認是否停盤期操作."))
	{
		var strUrl = '${pageContext.request.contextPath}/boss/ajaxDelPriod.action';
		var queryUrl = encodeURI(encodeURI(strUrl));
		$.ajax({
			type : "POST",
			url : queryUrl,
			data : {"priodNum":priodNum,"subType":$("#optionType").val(),"type":type},
			dataType : "json",
			success : callBack
		});
	}
}
function callBack(json) {
	if(json.errorMessage)
	{
		alert(json.errorMessage);
	    return false;
	}
	if(json.success){
		alert(json.success);
		location.reload();
	}	
}
</script>
</head>

<body>
<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 -->
    <td align="left" width="804" valign="top">
<div class="main">
  <div class="top_content"><select class="red mr10" name="" id="optionType" onChange="forwardToLotteryHistory(this.options[this.options.selectedIndex].value)">
       <option value="GDKLSF"  <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >“廣東快樂十分”開獎網</option>
      <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >“重慶時時彩”開獎網</option>
	  <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >“北京賽車”開獎網</option>    
      <option value="K3"  <s:if test="subType.indexOf('K3')>-1">selected</s:if> >江苏骰寶(快3)</option>
      <option value="NC"  <s:if test="subType.indexOf('NC')>-1">selected</s:if> >“幸运农场”開獎網</option>
    </select>&nbsp;&nbsp;盘期状态：<select class="red mr10" id="state" name="" onChange="forwardToLotteryHistory()">
       <option value="7"  <s:if test="state.indexOf('7')>-1">selected</s:if> >已开奖|停开</option>
      <option value="all"  <s:if test="state.indexOf('all')>-1">selected</s:if> >全部</option>
      <option value="5"  <s:if test="state.indexOf('5')>-1">selected</s:if> >未取到结果</option>  
    </select></div>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <tbody>
    <tr>
      <th width="10%">期數</th>
      <th width="20%">開獎時間</th>
      <th colspan="10">開出號碼</th>
      <th colspan="3">冠亚军和</th>
      <th colspan="5">1-5龙虎</th>
      <th width="6%">停盤操作</th>
    </tr>
    <%
    Page<BJSCPeriodsInfo> pageResult=(Page)request.getAttribute("page");
    List<BJSCPeriodsInfo> result=pageResult.getResult();
    for(int i=0;i<result.size();i++)
    {
    	List<Integer> ballList=new ArrayList<Integer>();
    	
    	BJSCPeriodsInfo periond=result.get(i);
    	String periondNum=periond.getPeriodsNum();
        Integer b1=periond.getResult1();ballList.add(b1);
        Integer b2=periond.getResult2();ballList.add(b2);
        Integer b3=periond.getResult3();ballList.add(b3);
        Integer b4=periond.getResult4();ballList.add(b4);
        Integer b5=periond.getResult5();ballList.add(b5);
        Integer b6=periond.getResult6();ballList.add(b6);
        Integer b7=periond.getResult7();ballList.add(b7);
        Integer b8=periond.getResult8();ballList.add(b8);
        Integer b9=periond.getResult9();ballList.add(b9);
        Integer b10=periond.getResult10();ballList.add(b10);
        List<Integer> fballList=new ArrayList<Integer>();
        List<Integer> mballList=new ArrayList<Integer>();
        List<Integer> lballList=new ArrayList<Integer>();
        fballList.add(b1); fballList.add(b2); fballList.add(b3);
        mballList.add(b2); mballList.add(b3); mballList.add(b4);
        lballList.add(b3); lballList.add(b4); lballList.add(b5);
        Date lotteryTime=periond.getLotteryTime();
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm");
        String lotteryStrTime= sm.format(lotteryTime);
        
    
    %>
   
       
        
      <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
      <%if("5".equals(periond.getState().trim())){ %>
          <td bgcolor="#FF0000"><a href="${pageContext.request.contextPath}/boss/queryBJSCLotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}else{ %>
          <td><a href="${pageContext.request.contextPath}/boss/queryBJSCLotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}%>
      <td><%=lotteryStrTime %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b1 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b2 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b3%></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b4 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b5 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b6 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b7 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b8 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b9 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span class="ball-bg"><%=b10 %></span><%} %></td>
	  <% if("6".equals(periond.getState().trim()) ){ %><td width="40%" colspan="8"><strong class="blue">官方停售，獎期取消</strong></td><%}else{ %>
		      <td width="5%"><%=b1+b2 %></td>
		      <td width="5%">
		      <%if(BJSCRule.GYX(b1+b2)){ %>小 <%} else {%><span class="red">大</span><%} %>
		      </td>
		      
		      <td width="5%">
		    <%if(BJSCRule.GYDAN(b1+b2)){ %>單 <%} else{%><span class="red">雙</span><%} %>
		      </td>
		 
		        <td width="5%">
		   <%if(BJSCRule.LONG(b1,b10)){ %><span class="blue"> 龍</span><%} else {%> 虎 <%}%>
		      </td>
		      
		       <td width="5%">
		   <%if(BJSCRule.LONG(b2,b9)){ %> <span class="blue"> 龍</span><%} else {%> 虎 <%}%>
		      </td>
		        <td width="5%">
		   <%if(BJSCRule.LONG(b3,b8)){ %> <span class="blue"> 龍</span><%} else {%> 虎 <%}%>
		      </td>
		        <td width="5%">
		  <%if(BJSCRule.LONG(b4,b7)){ %> <span class="blue"> 龍</span><%} else {%> 虎 <%}%>
		      </td>
		       <td width="5%">
		  <%if(BJSCRule.LONG(b5,b6)){ %> <span class="blue"> 龍</span><%} else {%> 虎 <%}%>
		      </td>
		<%} %>
      <td> 
      
     <% if(!"6".equals(periond.getState().trim())&& !"7".equals(periond.getState().trim())&&periond.getLotteryTime().compareTo(new Date())<0 ){ %> 
    <input type="button" name="stopPriod" id="button" value="該期停開" class="btn td_btn" onclick="stopPriod(<%=periondNum %>,'stop')" title="慎用：點擊停開后，當期所有設注作廢。"/>
    <%} %>
    <% if("6".equals(periond.getState().trim()) ){ %> 该期已作废<%} %>
    <% if("7".equals(periond.getState().trim()) ){ %> <input type="button" name="stopPriod" id="button" value="該期作废" class="btn td_btn" onclick="stopPriod(<%=periondNum %>,'Invalid')" title="慎用：點擊停開后，當期所有設注作廢。"/><%} %>
      
      </td>
    </tr>
 <%
    
    }
    %>
   
        </table>
  <s:if test="#request.page.totalCount>0">
      
	<lottery:paginate url="${pageContext.request.contextPath}/boss/enterLotResultHistoryBoss.action" param="subType=BJSC&state=${state}"/>
</s:if>
    
  </div>
</td></tr></tbody></table>

</body>
<script language="javascript" src="../js/public.js" type="text/javascript"></script>
<script language="javascript" src="../js/jquery-1.4.2.min.js" type="text/javascript"></script> 
<script>
$(document).ready(function() {
	
    $(".king td").find('span').each(function(i){
    	
	    var num=$(this).text();
	    if(CheckUtil.IsNumber(num))
    	{
	    var cls="No_000"+num;
	    $(this).text("");
		 $(this).removeClass().addClass(cls);
    	}
	 
   });


});

</script>
</html>
