<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.JSSBPeriodsInfo,com.npc.lottery.rule.BJSCRule" %>


<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
 <script>
 function forwardToLotteryHistory(optValue)
 {
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/enterLotResultHistory.xhtml?subType="+optValue;
 }
 </script>
</head>

<body style="width:95%">
<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 -->
    <td align="left" width="804" valign="top">
<div class="main">
  <div class="top_content"><select class="mr10" name="" onChange="forwardToLotteryHistory(this.options[this.options.selectedIndex].value)">
       	<option value="GDKLSF"   >廣東快樂十分</option>
      	<option value="CQSSC"   >重慶時時彩</option>
		<option value="BJSC"  >北京賽車(PK10)</option>  
		<option value="K3"  selected >江苏骰寶(快3)</option> 
		<option value="NC"   >幸运农场</option>   
    </select></div>
   <table cellspacing="0" cellpadding="0" border="0" width="80%" class="king">
 <tbody>
    <tr>
	    <th width="100">期数</th>
	    <th width="110">开奖时间</th>
	    <th width="84" colspan="3">开出骰子</th>
	    <th width="66" colspan="2">总和</th>
    </tr>
    <tr>
    <%
    Page<JSSBPeriodsInfo> pageResult=(Page)request.getAttribute("page");
    List<JSSBPeriodsInfo> result=pageResult.getResult();
    for(int i=0;i<result.size();i++)
    {
    	List<Integer> ballList=new ArrayList<Integer>();
    	
    	JSSBPeriodsInfo periodsInfo=result.get(i);
    	String periondNum=periodsInfo.getPeriodsNum();
        Integer b1=periodsInfo.getResult1();ballList.add(b1);
        Integer b2=periodsInfo.getResult2();ballList.add(b2);
        Integer b3=periodsInfo.getResult3();ballList.add(b3);
       
        Date lotteryTime=periodsInfo.getLotteryTime();
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm");
        String lotteryStrTime= sm.format(lotteryTime);
        lotteryStrTime= lotteryStrTime.replaceAll("星期", "");
        
        int sum = b1+b2+b3;
        String sumResult="";
        if(b1.equals(b2) && b2.equals(b3)){
        	sumResult = "K3_WS";
        }else if(sum >=4 && sum <=10){
        	sumResult ="K3_X";
        }else if(sum >=11 && sum <=17){
        	sumResult ="K3_DA";
        }
        
    
    %>
   
       
        
      <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
      <td><%=periondNum %></td>
      <td><%=lotteryStrTime %></td>
	<% if("6".equals(periodsInfo.getState().trim()) ){ %><td width="150" colspan="5"><strong class="blue">官方停售，獎期取消</strong></td><%}else{ %>
      <td  width="2%"><% if("6".equals(periodsInfo.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><img src="../images/4_<%=b1 %>.gif"/></span><%} %></td>
      <td  width="2%"><% if("6".equals(periodsInfo.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><img src="../images/4_<%=b2 %>.gif"/></span><%} %></td>
      <td  width="2%"><% if("6".equals(periodsInfo.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><img src="../images/4_<%=b3 %>.gif"/></span><%} %></td>
     
      
      <td  width="10%"><%=sum%></td>
      
      <td  width="10%">
      <%if("K3_X".equals(sumResult)){ %>
      		小 
      	<%} 
      	else if("K3_DA".equals(sumResult)){%>
      		<span class="red">大</span>
      	<%} 
      	else if("K3_WS".equals(sumResult)){%>
      		<span class="green">通吃</span>
      	<%}%>
      </td>
<%} %>
    </tr>
 <%}%>
   
        </table>
  <s:if test="#request.page.totalCount>0">
    <div style="width:80%">    
		<lottery:paginate url="${pageContext.request.contextPath}/${path}/enterLotResultHistory.xhtml" param="subType=K3" recordType=" 期記錄"/>
    </div>
</s:if>
    
  </div>
</td></tr></tbody></table>

</body>
<script language="javascript" src="../js/public.js" type="text/javascript"></script>
<script language="javascript" src="../js/jquery-1.4.2.min.js" type="text/javascript"></script> 
<script language="javascript" src="/js/Forbid.js" type="text/javascript"></script>
</html>
