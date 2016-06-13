<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.BJSCPeriodsInfo,com.npc.lottery.rule.BJSCRule" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" />
 <script>
 function forwardToLotteryHistory(optValue)
 {
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/admin/enterLotResultHistoryAdmin.action?subType="+optValue;
 }
 </script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">歷史開獎結果</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right" width="229">
			                 <select style="width:120px" class="mr10" name="" onChange="forwardToLotteryHistory(this.options[this.options.selectedIndex].value)">
						       <option value="GDKLSF"  <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >廣東快樂十分</option>
						      <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >重慶時時彩</option>
						      <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >北京賽車(PK10)</option>
						      <option value="K3"  <s:if test="subType.indexOf('K3')>-1">selected</s:if> >江苏骰寶(快3)</option>
						      <option value="NC"  <s:if test="subType.indexOf('NC')>-1">selected</s:if> >幸运农场</option>  
						    </select>
			            </td>
			            </tr></table></td>
			        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
			      </tr>
			    </table></td>
			<!--控制表格头部结束-->
			 <tr>
			    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
			        <td align="center" valign="top">
					<!-- 表格内容开始 一行六列-->
					<!-- 表格内容结束 一行六列-->
		<table width="700" border="0" cellspacing="0" cellpadding="0" class="king mt4" style="width:700px;margin:0 auto;">
  
				<tbody>
					<tr>
				      <th width="10%">期數</th>
				      <th width="20%">開獎時間</th>
				      <th colspan="10">開出號碼</th>
				      <th colspan="3"><b>冠亚军和</b></th>
				      <th colspan="5"><b>1-5龙虎</b></th>
				      
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
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm:ss");
        String lotteryStrTime= sm.format(lotteryTime);
        lotteryStrTime= lotteryStrTime.replaceAll("星期", "");
        
    
    %>
   
       
        
      <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" >
      <td><%=periondNum %></td>
      <td><%=lotteryStrTime %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b1 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b2 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b3%></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b4 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b5 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b6 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b7 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b8 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b9 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span ><%=b10 %></span><%} %></td>
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
    </tr>
 <%
    
    }
    %>
    </tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		            <lottery:paginate url="${pageContext.request.contextPath}/admin/enterLotResultHistoryAdmin.action" param="subType=BJSC" recordType=" 期記錄"/>
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
  </div>
</td></tr></tbody></table>

</body>
<script language="javascript" src="../js/public.js" type="text/javascript"></script>
<script language="javascript" src="../js/jquery-1.7.2.min.js" type="text/javascript"></script> 
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
