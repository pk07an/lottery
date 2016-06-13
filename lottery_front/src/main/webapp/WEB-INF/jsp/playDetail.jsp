<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.PlayTypeUtils"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
 <script>
function forwardToPlayDetail(optValue)
{
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/member/enterPlayDetail.do?subType="+optValue;
}
</script>
 </head>
<body style="width:95%">


<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 -->
    <td align="left" width="804" valign="top">
<div class="main">
  <div class="top_content">
  <%-- <select class="red mr10" name="" onChange="forwardToPlayDetail(this.options[this.options.selectedIndex].value)">
      <!--  <option value="GDKLSF"  <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >“廣東快樂十分”開獎網</option> -->
      <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >  “時時彩”開獎網</option>
      <option value="HKLHC"  <s:if test="subType.indexOf('HKLHC')>-1">selected</s:if> >“香港六合彩”開獎網</option>
       <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >“北京赛车”開獎網</option>
    </select> --%></div>
  <table cellspacing="0" cellpadding="0" border="0" width="88%" class="king" style="line-height:16px;">
  <colgroup>
    <col width="19%" />
    <col width="17%" />
    <col width="30%" />
    <col width="17%" />
    <col width="17%" />
  </colgroup>
  <tbody>
    <tr style="line-height:24px;">
      <th>注單號/時間</th>
      <th>下注類型</th>
      <th>注單明細</th>
      <th>下注金額</th>
      <th>可贏金額</th>    
     
    </tr>
    <%
    Page betPage=(Page)request.getAttribute("page");
    List betList=betPage.getResult();
    String preOrderNo="";
    if(betList.size()>0){
    	double totalMoney = betPage.getTotal1();
		double totalWinMoney = betPage.getTotal2();
        for(int i=0;i<betList.size();i++)
        {
       
        BaseBet betMap=(BaseBet)betList.get(i);
        Date betTime=betMap.getBettingDate();
        String orderNo=betMap.getOrderNo();
        if(preOrderNo.equals(orderNo))
        	continue;
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd HH:mm:ss E");
        String betStrTime= sm.format(betTime);
        betStrTime=betStrTime.replaceAll("星期", "");
        String typeCode=betMap.getTypeCode();
        Integer count=betMap.getCount();
        String subTypeName=PlayTypeUtils.getPlayTypeSubName(typeCode);
        
        BigDecimal odds=(BigDecimal)betMap.getOdds();
        Integer money=betMap.getMoney();
        //NumberFormat nf=NumberFormat.getNumberInstance();
        //nf.setMaximumFractionDigits(1);
        float winMoney=money.floatValue()*(odds.floatValue()-1)*count;
        String attribute=betMap.getAttribute();
        if(attribute==null)attribute="";
        if(attribute!=null&&attribute.length()!=0)
        	attribute=attribute.replace("|", ",");
        %>
        
          <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
          <td><%=betMap.getOrderNo()%>#</br><%=betStrTime %></td>
          <td><%if(typeCode.indexOf("GDKLSF")>-1) {%>廣東快樂十分<%} %>
          <%if(typeCode.indexOf("CQSSC")>-1) {%>重慶時時彩<%} %>
          <%if(typeCode.indexOf("BJ")>-1) {%>北京賽車(PK10)<%} %>
          <%if(typeCode.indexOf("K3")>-1) {%>江苏骰寶(快3)<%} %>
          <%if(typeCode.indexOf("NC")>-1) {%>幸运农场<%} %>
          
          </br><span class="green"><%=betMap.getPeriodsNum() %>&nbsp;期</span></td>
          <td><span class="blue"><%=subTypeName %> 『<%=PlayTypeUtils.getPlayTypeFinalName(typeCode) %>』</span>@<b><span class="red"><%=betMap.getOdds() %></span></b>
         <%if(count>1) {%></br>复式『<%=count%>组』<%} %>
         </br>
         <%=attribute %> 
      
          </td>
         
            <td width="6%" class="t_right">
            <%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %>
           <%=betMap.getMoney()*count %>&nbsp;
          </td>
            <td width="6%" class="t_right">
       <%= new BigDecimal(winMoney).setScale(1, BigDecimal.ROUND_HALF_UP)%>&nbsp;
          </td>
        
        </tr>
    <%
    preOrderNo=orderNo;
        }%>
    <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
	 <td colspan="2" bgcolor="#E8DCCF"><b>合計</b></td>
	 <td bgcolor="#E8DCCF"><b><s:property value="#request.page.totalCount"/>筆</b></td>
	 <td bgcolor="#E8DCCF" class="t_right"><b><%=new BigDecimal(totalMoney).setScale(0, BigDecimal.ROUND_HALF_UP)%></b></td>
	 <td bgcolor="#E8DCCF" class="t_right"><b><%=new BigDecimal(totalWinMoney).setScale(1, BigDecimal.ROUND_HALF_UP)%></b></td>
	 </tr>
   <%}else
   {%>
        <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
	 <td colspan="2" bgcolor="#E8DCCF">合計</td>
	 <td bgcolor="#E8DCCF"><b>0筆</b></td>
	 <td bgcolor="#E8DCCF"><b>0</b></td>
	 <td bgcolor="#E8DCCF"><b>0.0</b></td>
	 </tr>
       <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
	       <td colspan="5" class="dark"><font color="red">當前沒有數據......</font></td>
	     </tr>
  <%  }
   
   %>
   
    
   
        </table>

        <s:if test="#request.page.totalCount>0">
    <div style="width:88%"> 
	<lottery:paginate url="${pageContext.request.contextPath}/member/enterPlayDetail.do" param="subType=${subType}" recordType=" 筆注單"/>
	</div>
	</s:if>  
  </div>
  </td></tr></tbody></table>
</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>
