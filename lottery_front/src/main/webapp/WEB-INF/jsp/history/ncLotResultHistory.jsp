<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.NCPeriodsInfo,com.npc.lottery.rule.NCRule" %>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
      	<option value="GDKLSF"  >廣東快樂十分</option>
      	<option value="CQSSC"   >重慶時時彩</option>
    	<option value="BJSC"  >北京賽車(PK10)</option>
    	<option value="K3"   >江苏骰寶(快3)</option> 
    	<option value="NC"  selected>幸运农场</option> 
    	</select></div>
  <table cellspacing="0" cellpadding="0" border="0" width="88%" class="king">
  <tbody>
    <tr>
      <th width="14%">期數</th>
      <th width="16%">開獎時間</th>
      <th colspan="8">開出號碼</th>
      <th colspan="4">總和</th>
      <th width="6%">龍虎</th>
    </tr>
    <%
    Page<NCPeriodsInfo> pageResult=(Page)request.getAttribute("page");
    List<NCPeriodsInfo> result=pageResult.getResult();
    for(int i=0;i<result.size();i++)
    {
    	List<Integer> ballList=new ArrayList<Integer>();
    	NCPeriodsInfo periond=result.get(i);
    	String periondNum=periond.getPeriodsNum();
        Integer b1=periond.getResult1();ballList.add(b1);
        Integer b2=periond.getResult2();ballList.add(b2);
        Integer b3=periond.getResult3();ballList.add(b3);
        Integer b4=periond.getResult4();ballList.add(b4);
        Integer b5=periond.getResult5();ballList.add(b5);
        Integer b6=periond.getResult6();ballList.add(b6);
        Integer b7=periond.getResult7();ballList.add(b7);
        Integer b8=periond.getResult8();ballList.add(b8);
        Date lotteryTime=periond.getLotteryTime();
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm");
        String lotteryStrTime= sm.format(lotteryTime);
        lotteryStrTime=lotteryStrTime.replaceAll("星期", "");
        
        
    
    %>
   
       
        
      <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style=""<%if(i%2!=0)out.print("class=\"even\""); %>>
      <td><%=periondNum %></td>
      <td><%=lotteryStrTime %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b1 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b2 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b3%></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b4 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b5 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b6 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b7 %></span><%} %></td>
      <td width="5%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b8 %></span><%} %></td>
      <% if("6".equals(periond.getState().trim()) ){ %><td width="22%" colspan="5"><strong class="blue">官方停售，獎期取消</strong></td><%}else{ %>
	      <td width="6%"><%=b1+b2+b3+b4+b5+b6+b7+b8 %></td>
	      <td width="5%">
	      <%if(NCRule.ZHX(ballList)){ %>小 <%} else if(NCRule.ZHDA(ballList)){%><span class="red">大</span><%} else{%><font color="green">和</font><%} %>
	      </td>
	         
	       <td width="5%">
	        <%if(NCRule.ZHDAN(ballList)){ %>單 <%} else{%><span class="red">雙</span><%} %>
	    
	      </td> 
	      <td width="6%">
	  	 <%if(NCRule.ZHWX(ballList)){ %>尾小 <%} else{%><span class="red">尾大</span><%} %>
	      </td>
	      <td>
	      <%if(NCRule.HU(ballList)){ %>虎 <%} else{%><span class="red">龍</span><%} %>
	      </td>
	  <%} %>
    </tr>
 <%
    
    }
    %>
   
        </table>
  <s:if test="#request.page.totalCount>0">
    <div style="width:88%">  
	<lottery:paginate url="${pageContext.request.contextPath}/${path}/enterLotResultHistory.xhtml" param="subType=NC" recordType=" 期記錄"/>
	</div>
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
		 if(num<10)
			 num="0"+num;
		    var cls="NC_"+num;
		    $(this).text("");
			 $(this).removeClass().addClass(cls);
    	}
	 
   });


});

</script>
<script language="javascript" src="/js/Forbid.js" type="text/javascript"></script>
</html>
