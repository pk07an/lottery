<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.npc.lottery.common.Constant"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="javax.swing.text.StyleContext.SmallAttributeSet"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page" %>
<%@page import="com.npc.lottery.member.entity.CQandGDReportInfo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.npc.lottery.util.PlayTypeUtils"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
</head>
<body oncontextnenu="return false" onselectstart="return false" style="width:95%">
<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 -->
    <td align="left" width="804" valign="top">


<div class="main">
  <div class="top_content"></div>
  <table cellspacing="0" cellpadding="0" border="0" width="88%" class="king" style="line-height:16px;">
  <tbody>
    <tr style="line-height:24px;">
      <th width="19%">注單號/時間</th>
      <th width="17%">下注類型</th>
      <th width="30%">注單明細</th>
      <th width="17%">下注金額</th>
      <th width="17%">退水后结果</th>
    </tr>
    	<%
   
    		List<CQandGDReportInfo> cQandGDReportInfoList = new ArrayList<CQandGDReportInfo>(); 
    		Page betPage=(Page)request.getAttribute("page");
    		if(betPage!=null) cQandGDReportInfoList = (List)betPage.getResult();
    		 double money = betPage.getTotal1();
    		 double commissionResult = betPage.getTotal2();//今天一共退了多少钱
    		for(int i = 0; i<cQandGDReportInfoList.size();i++)
    		{    
    		    SimpleDateFormat sm=new SimpleDateFormat("MM-dd HH:mm:ss E");
    		    /* if(!"9".equals(cQandGDReportInfoList.get(i).getWinState()) && !"4".equals(cQandGDReportInfoList.get(i).getWinState()) && !"5".equals(cQandGDReportInfoList.get(i).getWinState())){
    		        commissionResult +=cQandGDReportInfoList.get(i).getRecessionResults();
    		    }
    		    if(!"4".equals(cQandGDReportInfoList.get(i).getWinState()) && !"5".equals(cQandGDReportInfoList.get(i).getWinState())){
    		        money +=cQandGDReportInfoList.get(i).getMoney();
    		    } */
    		    String attribute=cQandGDReportInfoList.get(i).getAttribute();
    		    Date bettingDate = cQandGDReportInfoList.get(i).getBettingDate();
    		    Integer count=cQandGDReportInfoList.get(i).getCount();
    	        if(attribute==null)attribute="";
    	        if(attribute!=null&&attribute.length()!=0)
    	        	attribute=attribute.replace("|", ",");
    	        
         	%>
		    	<tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
		 	    	<td><%=cQandGDReportInfoList.get(i).getOrderNo()%>#</br><%=cQandGDReportInfoList.get(i).getBettingDateStr()%></td>
		 	    	<td>
		 	    	<%if(cQandGDReportInfoList.get(i).getTypeCode().indexOf("GDKLSF")>-1) {%>廣東快樂十分<%} %>
         			 <%if(cQandGDReportInfoList.get(i).getTypeCode().indexOf("CQSSC")>-1) {%>重慶時時彩<%} %>
         			 <%if(cQandGDReportInfoList.get(i).getTypeCode().indexOf("BJ")>-1) {%>北京賽車(PK10)<%} %>
         			 <%if(cQandGDReportInfoList.get(i).getTypeCode().indexOf("K3")>-1) {%>江苏骰寶(快3)<%} %>
         			 <%if(cQandGDReportInfoList.get(i).getTypeCode().indexOf("NC")>-1) {%>幸运农场<%} %>
		 	    	</br><span class="green"><%=cQandGDReportInfoList.get(i).getPeriodsNum()%>&nbsp;期</span>
		 	    	</td>
		 	    	<td><span class="blue"><%=PlayTypeUtils.getPlayTypeSubName(cQandGDReportInfoList.get(i).getTypeCode())%>『<%=PlayTypeUtils.getPlayTypeFinalName(cQandGDReportInfoList.get(i).getTypeCode()) %>』</span>@<span class="red"><strong><%=cQandGDReportInfoList.get(i).getOdds() %></strong></span>
		 	    	<%if(count>1) {%></br>复式『<%=count%>组』<%} %>
		 	    	</br>
	    			<%=attribute %> 
		 	    	</td>
		 	    	<%if("4".equals(cQandGDReportInfoList.get(i).getWinState())){%>
		 	    	   <td class="t_right" style="text-decoration:line-through;color:red;"> 
		 	    	    <%if(count>1) {%> <%=cQandGDReportInfoList.get(i).getMoney()/count%>X<%=count%>组</br><%} %>
          			 		<%=cQandGDReportInfoList.get(i).getMoney()%>&nbsp;
		 	    	  </td>
		 	    	<%}else if("5".equals(cQandGDReportInfoList.get(i).getWinState())){%>
		 	    	   <td class="t_right" style="text-decoration:line-through;color:red;"> 
		 	    	    <%if(count>1) {%> <%=cQandGDReportInfoList.get(i).getMoney()/count%>X<%=count%>组</br><%} %>
         			 		<%=cQandGDReportInfoList.get(i).getMoney()%>&nbsp;
		 	    	  </td>
		 	    	<%}else{%>
		 	    	    <td class="t_right"> 
		 	    	    <%if(count>1) {%> <%=cQandGDReportInfoList.get(i).getMoney()/count%>X<%=count%>组</br><%} %>
          			 		<%=cQandGDReportInfoList.get(i).getMoney()%>&nbsp;
		 	    	  </td>
		 	    	<%}%>
		     		<%if("9".equals(cQandGDReportInfoList.get(i).getWinState())) {%>
		     		<td class="t_right">
		     		0&nbsp;
		     		</td class="t_right">
		     		<%} 
		     		else if("5".equals(cQandGDReportInfoList.get(i).getWinState())){%>
		     		<td style="color:red;" class="t_right">
		     		   	注銷&nbsp;
		     		 </td>
		     		<%}else if("4".equals(cQandGDReportInfoList.get(i).getWinState())){%>
		     		<td style="color:red;" class="t_right">
	     		   	注銷&nbsp;
	     		   </td>
	     			<%}else{%>
		     		<td class="t_right">
		     			<%=new BigDecimal(cQandGDReportInfoList.get(i).getRecessionResults()).setScale(1, BigDecimal.ROUND_DOWN).toString()%>&nbsp;
		     		</td>	
		     		<%}%>
		     	 </tr>
     		<%}%>	
     
	 <%if(cQandGDReportInfoList.size()==0){ %>
		  <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
		 <td colspan="2" bgcolor="#E8DCCF">合計</td>
		 <td bgcolor="#E8DCCF"><b>0筆</b></td>
		 <td bgcolor="#E8DCCF"><b>0</b></td>
		 <td bgcolor="#E8DCCF"><b>0.0</b></td>
		 </tr>
	       <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
		       <td colspan="5" class="dark"><font color="red">當前沒有數據......</font></td>
		     </tr>
	 <%} else{%>
	
	
		     	 <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
	 <td colspan="2" bgcolor="#E8DCCF"><b>閤計</b></td>
	 <td bgcolor="#E8DCCF">
	 <b>${page.totalCount}筆</b><!-- cQandGDReportInfoList.size() -->
	 </td>
	 <td class="t_right" bgcolor="#E8DCCF"><b><%=new BigDecimal(money).setScale(1, BigDecimal.ROUND_DOWN).toString()%></b></td>
	 <td class="t_right" bgcolor="#E8DCCF"><b><%=new BigDecimal(commissionResult).setScale(1, BigDecimal.ROUND_DOWN).toString()%></b></td>
	 </tr>
	 <%
	}%>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="88%"
				class="page">
				<tbody>
					<tr>
						<td>
							<%
							if(betPage.getTotalCount()>0){%>
								<lottery:paginate
										url="${pageContext.request.contextPath}/${path}/queryCurrentReport.xhtml"
										param="dateTime=${dateTime}" recordType=" 筆注單"/>
							<%}%>
						</td>
					</tr>
				</tbody>
			</table>
  </div>
  </td></tr></tbody></table>
</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>
