<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.JSSBPeriodsInfo,com.npc.lottery.rule.BJSCRule" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" />
 <script>
 /* function forwardToLotteryHistory(optValue)
 {
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/admin/enterLotResultHistoryAdmin.action?subType="+optValue;
 } */
 
 function stopPriod(priodNum,type){
//	 	alert(priodNum);
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">歷史開獎結果</td>
						<td width="935" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="2">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right" width="329">
			                 <select style="width:120px" class="mr10" id="optionType"  onChange="forwardToLotteryHistory(this.options[this.options.selectedIndex].value)">
						       <option value="GDKLSF"  <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >廣東快樂十分</option>
						      <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >重慶時時彩</option>
						      <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >北京賽車(PK10)</option>
						      <option value="K3"  <s:if test="subType.indexOf('K3')>-1">selected</s:if> >江苏骰寶(快3)</option> 
						    </select>&nbsp;&nbsp;盘期状态：<select class="red mr10" id="state" name="" onChange="forwardToLotteryHistory()">
       <option value="7"  <s:if test="state.indexOf('7')>-1">selected</s:if> >已开奖|停开</option>
      <option value="all"  <s:if test="state.indexOf('all')>-1">selected</s:if> >全部</option>
      <option value="5"  <s:if test="state.indexOf('5')>-1">selected</s:if> >未取到结果</option>  
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
		<table width="576" border="0" cellspacing="0" cellpadding="0" class="king mt4" style="width:576px;margin:0 auto;">
 <tbody>
    <tr>
	    <th width="10%">期数</th>
	    <th width="20%">开奖时间</th>
	    <th width="40%" colspan="3">开出骰子</th>
	    <th width="20%" colspan="2">总和</th>
	    <th width="10%">停盤操作</th>
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
      <%if("5".equals(periodsInfo.getState().trim())){ %>
          <td bgcolor="#FF0000"><a href="${pageContext.request.contextPath}/boss/queryK3LotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}else{ %>
          <td><a href="${pageContext.request.contextPath}/boss/queryK3LotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}%>
      <td><%=lotteryStrTime %></td>
	  <% if("6".equals(periodsInfo.getState().trim()) ){ %><td width="60%" colspan="5"><strong class="blue">官方停售，獎期取消</strong></td><%}else{ %>
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
	<td>
     <% if(!"6".equals(periond.getState().trim())&& !"7".equals(periond.getState().trim())&&periond.getLotteryTime().compareTo(new Date())<0 ){ %> 
    <input type="button" name="stopPriod" id="button" value="該期停開" class="btn td_btn" onclick="stopPriod(<%=periondNum %>,'stop')" title="慎用：點擊停開后，當期所有設注作廢。"/>
    <%} %>
    <% if("6".equals(periodsInfo.getState().trim()) ){ %> 该期已作废<%} %>
    <% if("7".equals(periodsInfo.getState().trim()) ){ %> <input type="button" name="stopPriod" id="button" value="該期作废" class="btn td_btn" onclick="stopPriod(<%=periondNum %>)" title="慎用：點擊停開后，當期所有設注作廢。"/><%} %>
    </td>
    </tr>
 <%}%>
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
		            <lottery:paginate url="${pageContext.request.contextPath}/boss/enterLotResultHistoryBoss.action" param="subType=K3&state=${state}" recordType=" 期記錄"/>
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
<script language="javascript" src="../js/jquery-1.4.2.min.js" type="text/javascript"></script> 
</html>
