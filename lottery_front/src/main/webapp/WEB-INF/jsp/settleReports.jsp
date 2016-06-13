<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.DatetimeUtil"%>
<%@page import="com.npc.lottery.member.entity.BalanceInfo"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
</head>

<body style="width:95%">

<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 you on -->
    <td align="left" width="804" valign="top">
<div class="main">

<div class="top_content"/>
<div class="top_content">
  <table cellspacing="0" cellpadding="0" border="0" width="87%" class="king">
  <colgroup>
          <col width="17%" />
          <col width="12%" />
          <col width="19%" />
          <col width="17%" />
          <col width="13%" />
          <col width="22%" />
          </colgroup>
  <tbody>
    <tr>
      <th>交易日期</th>
      <th>注單筆數</th>
      <th>下注金額</th>
      <th>輸贏結果</th>
      <th>退水</th>
      <th>退水結果</th>
    </tr>
    <%
    Map<String,BalanceInfo> balanceInfoMap = (Map<String,BalanceInfo>)request.getAttribute("BalanceInfoList"); 
    SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd EEEE",Locale.CHINA);
    int sumCount = 0;
 	double sumMoney = 0;
 	double sumBunkoResults = 0;
 	double sumCommisionResults = 0;
 	double sumRecessionResults = 0;
 	

     for (int i = 7; i >0; i--) {
 		Calendar	firstDayOfWeek=DatetimeUtil.getFirstDayOfWeekByOffset(-2,-30);
 		 firstDayOfWeek.add(Calendar.DATE, -i);
 		String key=sm.format(firstDayOfWeek.getTime());
 		String displayKey=sdf.format(firstDayOfWeek.getTime());
 		BalanceInfo blanceInfo=balanceInfoMap.get(key);
 		int count = 0;
     	double money = 0;
     	double bunkoResults = 0;
     	double commisionResults = 0;
     	double recessionResults = 0;
 		
 		if(blanceInfo!=null)
 		{
 			count = blanceInfo.getWagersOn();
 	     	money = blanceInfo.getWagering();
 	     	bunkoResults =blanceInfo.getBunkoResults();
 	     	commisionResults = blanceInfo.getRecession();
 	     	recessionResults =  new BigDecimal( bunkoResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP)).doubleValue();
 		}
     
     
     
     	
 		sumCount = sumCount+count;
 		sumMoney = sumMoney+money;
 		sumBunkoResults = new BigDecimal( sumBunkoResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(bunkoResults).setScale(1, BigDecimal.ROUND_DOWN)).doubleValue();
	 	sumCommisionResults = new BigDecimal( sumCommisionResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP)).doubleValue();
	    sumRecessionResults = new BigDecimal( sumRecessionResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(recessionResults).setScale(1, BigDecimal.ROUND_DOWN)).doubleValue();
    	%>
	      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" 
	      <%
	      if(sdf.format(new Date()).equals(displayKey ) && count>0){
           out.print("class='blue'");
	      }else{}%>
	      >
	      <td><%=displayKey%></td>
	      <td><%=count%></td>
	      <td class="t_right"><%=new BigDecimal(money).setScale(0, BigDecimal.ROUND_DOWN).toString()%></td>
	      <td class="t_right"><%=new BigDecimal(bunkoResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	      <td class="t_right"><%=new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP).toString()%></td>
	      <td class="t_right"><strong><a class="red" href="${pageContext.request.contextPath}/${path}/queryCurrentReport.xhtml?dateTime=<%=sm.format(firstDayOfWeek.getTime())%>"><%=new BigDecimal(recessionResults).setScale(1, BigDecimal.ROUND_HALF_UP).toString()%></a></strong></td>
	    </tr>
    	<%}
%>
	 <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="">
	 <td class="t_list_caption">上周</td>
	 <td class="t_list_caption"><%=sumCount%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumMoney).setScale(0, BigDecimal.ROUND_DOWN).toString()%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumBunkoResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumCommisionResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	 <td class="t_list_caption t_right"><b><%=new BigDecimal(sumRecessionResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></b></td>
	 </tr>
	 
  </table>
       <table cellspacing="0" cellpadding="0" border="0" width="87%" class="king mt10">
<colgroup>
          <col width="17%" />
          <col width="12%" />
          <col width="19%" />
          <col width="17%" />
          <col width="13%" />
          <col width="22%" />
          </colgroup>
  <tbody>
    <tr>
      <th>交易日期</th>
      <th>注單筆數</th>
      <th>下注金額</th>
      <th>輸贏結果</th>
      <th>退水</th>
      <th>退水結果</th>
    </tr>
    <% 
     sumCount = 0;
 	 sumMoney = 0;
 	 sumBunkoResults = 0;
 	 sumCommisionResults = 0;
 	 sumRecessionResults = 0;
 	for (int i = 0; i <=6; i++) {
 	 		Calendar	firstDayOfWeek=DatetimeUtil.getFirstDayOfWeekByOffset(-2,-30);
 	 		 firstDayOfWeek.add(Calendar.DATE, i);
 	 		String key=sm.format(firstDayOfWeek.getTime());
 	 		String displayKey=sdf.format(firstDayOfWeek.getTime());
 	 		BalanceInfo blanceInfo=balanceInfoMap.get(key);
 	 		int count = 0;
 	     	double money = 0;
 	     	double bunkoResults = 0;
 	     	double commisionResults = 0;
 	     	double recessionResults = 0;
 	 		
 	 		if(blanceInfo!=null)
 	 		{
 	 			
 	 			count = blanceInfo.getWagersOn();
 	 	     	money = blanceInfo.getWagering();
 	 	     	bunkoResults =blanceInfo.getBunkoResults();
 	 	     	commisionResults = blanceInfo.getRecession();
 	 	     	recessionResults =  new BigDecimal( bunkoResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP)).doubleValue();
 	 			
 	 		}
 	     
 	     
 	     
 	     	
 	    	
 	 		sumCount = sumCount+count;
 	 		sumMoney = sumMoney+money;
 	 		sumBunkoResults = new BigDecimal( sumBunkoResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(bunkoResults).setScale(1, BigDecimal.ROUND_DOWN)).doubleValue();
 	 		sumCommisionResults = new BigDecimal( sumCommisionResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP)).doubleValue();
 	    	sumRecessionResults = new BigDecimal( sumRecessionResults).setScale(1, BigDecimal.ROUND_DOWN).add(new BigDecimal(recessionResults).setScale(1, BigDecimal.ROUND_DOWN)).doubleValue();
 	    	%>
	      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'">
	       
	      <%if(sdf.format(new Date()).equals(displayKey ) && count>0){%>
             <td class="blue"><b><%=displayKey%></b></td> 
	      <%}else{%>
	    	  <td><%=displayKey%></td> 
	      <%}%>
	      
	      <td><%=count%></td>
	      <td class="t_right"><%=new BigDecimal(money).setScale(0, BigDecimal.ROUND_DOWN).toString()%></td>
	      <td class="t_right"><%=new BigDecimal(bunkoResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	      <td class="t_right"><%=new BigDecimal(commisionResults).setScale(1, BigDecimal.ROUND_HALF_UP).toString()%></td>
	      <td class="t_right"><strong><a class="red" href="${pageContext.request.contextPath}/${path}/queryCurrentReport.xhtml?dateTime=<%=sm.format(firstDayOfWeek.getTime())%>"><%=new BigDecimal(recessionResults).setScale(1, BigDecimal.ROUND_HALF_UP).toString()%></a></strong></td>
	    </tr>
	<%}
   %>
    <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="">
	 <td class="t_list_caption">本周</td>
	 <td class="t_list_caption"><%=sumCount%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumMoney).setScale(0, BigDecimal.ROUND_DOWN)%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumBunkoResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	 <td class="t_list_caption t_right"><%=new BigDecimal(sumCommisionResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></td>
	 <td class="t_list_caption t_right"><b><%=new BigDecimal(sumRecessionResults).setScale(1, BigDecimal.ROUND_DOWN).toString()%></b></td>
	 </tr>
  </table>   
</div>
</div>
</td></tr></tbody></table>
</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>
