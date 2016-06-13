<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric.js"></script> --%>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
.notok{border:1px #de0000 solid}
.ok{border:1px #FF0 solid}

</style>
</head>
<body>
<div id="content">
<%
//针对会员页面显示用
String plate=(String)request.getAttribute("plate");
boolean a=true;
boolean b=true;
boolean c=true;
if(plate!=null)
{
  if("A".equals(plate))
  {
	b=false;c=false;  
  }
  else if("B".equals(plate))
  {
	  a=false;c=false;  
  }
  else if("C".equals(plate))
  {
	  a=false;b=false;  
  }
}
%>
<s:if test="#request.CommissionUser.userType==9">
<s:set var="userTypeName" value="会员" />
<form id="sForm" action="${pageContext.request.contextPath}/user/saveMembercommission.action" method="post" target="_self">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryMemberStaff.action" />
<input type="hidden" name="plate" id="jq_plate" value="${CommissionUser.plate}" />
</s:if>

<s:elseif test="#request.CommissionUser.userType==3">
<s:set var="userTypeName" value="分公司" />
<form id="sForm" action="${pageContext.request.contextPath}/user/saveBranchcommission.action" method="post" target="_self">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryBranchStaff.action" />
</s:elseif>
<s:elseif test="#request.CommissionUser.userType==4">
<s:set var="userTypeName" value="股东" />
<form id="sForm" action="${pageContext.request.contextPath}/user/saveStockcommission.action" method="post" target="_self">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryStockholder.action" />
</s:elseif>
<s:elseif test="#request.CommissionUser.userType==5">
<s:set var="userTypeName" value="總代理" />
<form id="sForm" action="${pageContext.request.contextPath}/user/saveGenAgentcommission.action" method="post" target="_self">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryGenAgentStaff.action" />
</s:elseif>
<s:elseif test="#request.CommissionUser.userType==6">
<s:set var="userTypeName" value="代理" />
<form id="sForm" action="${pageContext.request.contextPath}/user/saveAgentcommission.action" method="post" target="_self">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryAgentStaff.action" />
</s:elseif>
<input type="hidden" value="<s:property value="#parameters['qUserID']"/>" name="qUserID" id="jq_userID"/>
<input type="hidden" value="${CommissionUser.userType}" id="jq_userType"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30%" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="1%"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                <td width="99%" align="left" class="F_bold">&nbsp;退水设定 -&gt; 
               
<s:if test="#request.CommissionUser.userType==9">会员</s:if>
<s:elseif test="#request.CommissionUser.userType==3">分公司</s:elseif>
<s:elseif test="#request.CommissionUser.userType==4">股东</s:elseif>
<s:elseif test="#request.CommissionUser.userType==5">總代理</s:elseif>
<s:elseif test="#request.CommissionUser.userType==6">代理</s:elseif>         
（${CommissionUser.account} ）</td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%">
<s:if test="#request.CommissionUser.userType==9">会员</s:if>
<s:elseif test="#request.CommissionUser.userType==3">分公司</s:elseif>
<s:elseif test="#request.CommissionUser.userType==4">股东</s:elseif>
<s:elseif test="#request.CommissionUser.userType==5">總代理</s:elseif>
<s:elseif test="#request.CommissionUser.userType==6">代理</s:elseif> 名称：${CommissionUser.chsName}
            </td>
            </tr></table></td>
        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
    <!--广东-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center">
        <div class="greenbg tt">大項快速設置【注意：設置高於上級最高限制時按最高可調】</div>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="longse" name="batch">
  <tbody>
  <tr class="greenbg">
    <th width="30%">调整项目</th>
    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
    <th width="10%">A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
    <th width="13%">B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th width="13%">C盘</th>
    </s:elseif>
    <s:else>
    <th width="10%">A盘</th>
    <th width="13%">B盘</th>
    <th width="13%">C盘</th>
    </s:else>
 
    <th width="13%">单注限额</th>
    <th width="15%">单期限额</th>
    <th width="6%">...</th>
  </tr>
  <tr>
    <td height="22" class="ligreen">特码类（第一球、第二球、冠军...）</td>
    
     <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
    <td align="center" class="lanse2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionA}" ></td>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
   <td align="center" class="lanse2"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionB}" ></td>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <td align="center" class="lanse2"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionC}" ></td>
    </s:elseif>
    <s:else>
   <td align="center" class="lanse2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionA}" ></td>
    <td align="center"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionB}" ></td>
    <td align="center"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_ONE_BALL.commissionC}" ></td>
    </s:else>
    
    
    
    
    
    <td align="center"><input type="text" name="BQ" style="width:100px" class="b_g" value="${commissions.GD_ONE_BALL.bettingQuotas}" ></td>
    <td align="center"><input  type="text" name="IQ" style="width:100px" class="b_g" value="${commissions.GD_ONE_BALL.itemQuotas}" ></td>
    <td align="center" class="lanse2"><input type="button" value="修改" class="btn2" name="input12" id="jq_tm"></td>
  </tr>
  <tr>
    <td height="22" class="ligreen">兩面類（單雙、大小、龍虎...）</td>
    
    
         <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <td align="center" class="zise2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionA}" ></td>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
   <td align="center" class="zise2"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionB}" ></td>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <td align="center" class="zise2"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionC}" ></td>
    </s:elseif>
    <s:else>
  <td align="center" class="zise2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionA}" ></td>
    <td align="center"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionB}" ></td>
    <td align="center"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_OEDX_BALL.commissionC}" ></td>
    </s:else>
    
    
    
    
    
    <td align="center"><input type="text" name="BQ" style="width:100px" class="b_g" value="${commissions.GD_OEDX_BALL.bettingQuotas}" ></td>
    <td align="center"><input type="text" name="IQ" style="width:100px" class="b_g" value="${commissions.GD_OEDX_BALL.itemQuotas}" ></td>
    <td align="center" class="zise2"><input type="button" value="修改" class="btn2" name="input12" id="jq_double"></td>
  </tr>
  <tr>
    <td height="22" class="ligreen">連碼類（任選二、任選三...）</td>
    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <td align="center" class="lvse2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionA}" ></td>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <td align="center" class="lvse2"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionB}" ></td>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <td align="center" class="lvse2"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionC}" ></td>
    </s:elseif>
    <s:else>
 <td align="center" class="lvse2"><input type="text" name="CA" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionA}" ></td>
    <td align="center"><input type="text" name="CB" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionB}" ></td>
    <td align="center"><input type="text" name="CC" style="width:82px" class="b_g" value="${commissions.GD_RXH_BALL.commissionC}" ></td>
    </s:else>
    
    
    <td align="center"><input type="text" name="BQ" style="width:100px" class="b_g" value="${commissions.GD_RXH_BALL.bettingQuotas}" ></td>
    <td align="center"><input type="text"  name="IQ" style="width:100px" class="b_g" value="${commissions.GD_RXH_BALL.itemQuotas}" ></td>
    <td align="center" class="lvse2"><input type="button" value="修改" class="btn2" name="input12" id="jq_lm"></td>
  </tr>
</tbody></table>
        
        
        
        <!--广东开始-->
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="" name="comm" >
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>广东快乐十分钟</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
           <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        
  
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="tema">
        <td class="even2">第一球 <input type="hidden" name="commissions[0].playFinalType" value="GD_ONE_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lanse2"><input type="text" name="commissions[0].commissionA" value="${commissions.GD_ONE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%> ><input type="text" name="commissions[0].commissionB" value="${commissions.GD_ONE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[0].commissionC" value="${commissions.GD_ONE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[0].bettingQuotas" value="${commissions.GD_ONE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[0].itemQuotas" value="${commissions.GD_ONE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
      </tr>
      <tr name="tema">
        <td class="even2">第二球<input type="hidden" name="commissions[1].playFinalType" value="GD_TWO_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lanse2"><input type="text" name="commissions[1].commissionA" value="${commissions.GD_TWO_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[1].commissionB" value="${commissions.GD_TWO_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[1].commissionC" value="${commissions.GD_TWO_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[1].bettingQuotas" value="${commissions.GD_TWO_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[1].itemQuotas" value="${commissions.GD_TWO_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第三球<input type="hidden" name="commissions[2].playFinalType" value="GD_THREE_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lanse2"><input type="text" name="commissions[2].commissionA" value="${commissions.GD_THREE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[2].commissionB" value="${commissions.GD_THREE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[2].commissionC" value="${commissions.GD_THREE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[2].bettingQuotas" value="${commissions.GD_THREE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[2].itemQuotas" value="${commissions.GD_THREE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第四球<input type="hidden" name="commissions[3].playFinalType" value="GD_FOUR_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lanse2"><input type="text" name="commissions[3].commissionA" value="${commissions.GD_FOUR_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[3].commissionB" value="${commissions.GD_FOUR_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[3].commissionC" value="${commissions.GD_FOUR_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[3].bettingQuotas" value="${commissions.GD_FOUR_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[3].itemQuotas" value="${commissions.GD_FOUR_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第五球<input type="hidden" name="commissions[4].playFinalType" value="GD_FIVE_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lanse2"><input type="text" name="commissions[4].commissionA" value="${commissions.GD_FIVE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[4].commissionB" value="${commissions.GD_FIVE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[4].commissionC" value="${commissions.GD_FIVE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[4].bettingQuotas" value="${commissions.GD_FIVE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[4].itemQuotas" value="${commissions.GD_FIVE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第六球<input type="hidden" name="commissions[5].playFinalType" value="GD_SIX_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lanse2"><input type="text" name="commissions[5].commissionA" value="${commissions.GD_SIX_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
       <%if(b){ %> <td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[5].commissionB" value="${commissions.GD_SIX_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[5].commissionC" value="${commissions.GD_SIX_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[5].bettingQuotas" value="${commissions.GD_SIX_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[5].itemQuotas" value="${commissions.GD_SIX_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第七球<input type="hidden" name="commissions[6].playFinalType" value="GD_SEVEN_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lanse2"><input type="text" name="commissions[6].commissionA" value="${commissions.GD_SEVEN_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[6].commissionB" value="${commissions.GD_SEVEN_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[6].commissionC" value="${commissions.GD_SEVEN_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[6].bettingQuotas" value="${commissions.GD_SEVEN_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[6].itemQuotas" value="${commissions.GD_SEVEN_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第八球<input type="hidden" name="commissions[7].playFinalType" value="GD_EIGHT_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lanse2"><input type="text" name="commissions[7].commissionA" value="${commissions.GD_EIGHT_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
       <%if(b){ %> <td name="gd_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[7].commissionB" value="${commissions.GD_EIGHT_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[7].commissionC" value="${commissions.GD_EIGHT_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[7].bettingQuotas" value="${commissions.GD_EIGHT_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[7].itemQuotas" value="${commissions.GD_EIGHT_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8大小<input type="hidden" name="commissions[8].playFinalType" value="GD_OEDX_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="zise2"><input type="text" name="commissions[8].commissionA" value="${commissions.GD_OEDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[8].commissionB" value="${commissions.GD_OEDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[8].commissionC" value="${commissions.GD_OEDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[8].bettingQuotas" value="${commissions.GD_OEDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[8].itemQuotas" value="${commissions.GD_OEDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8單雙<input type="hidden" name="commissions[9].playFinalType" value="GD_OEDS_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="zise2"><input type="text" name="commissions[9].commissionA" value="${commissions.GD_OEDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[9].commissionB" value="${commissions.GD_OEDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[9].commissionC" value="${commissions.GD_OEDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[9].bettingQuotas" value="${commissions.GD_OEDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[9].itemQuotas" value="${commissions.GD_OEDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8尾數大小<input type="hidden" name="commissions[10].playFinalType" value="GD_OEWSDX_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="zise2"><input type="text" name="commissions[10].commissionA" value="${commissions.GD_OEWSDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[10].commissionB" value="${commissions.GD_OEWSDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[10].commissionC" value="${commissions.GD_OEWSDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[10].bettingQuotas" value="${commissions.GD_OEWSDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[10].itemQuotas" value="${commissions.GD_OEWSDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8合數單雙<input type="hidden" name="commissions[11].playFinalType" value="GD_HSDS_BALL" /></td>
       <%if(a){ %><td name="gd_commission" class="zise2"><input type="text" name="commissions[11].commissionA" value="${commissions.GD_HSDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="gd_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[11].commissionB" value="${commissions.GD_HSDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[11].commissionC" value="${commissions.GD_HSDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[11].bettingQuotas" value="${commissions.GD_HSDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[11].itemQuotas" value="${commissions.GD_HSDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="other">
        <td class="even2">1-8方位<input type="hidden" name="commissions[12].playFinalType" value="GD_FW_BALL" /></td>
        <%if(a){ %> <td name="gd_commission" class="hongse"><input type="text" name="commissions[12].commissionA" value="${commissions.GD_FW_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="gd_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[12].commissionB" value="${commissions.GD_FW_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[12].commissionC" value="${commissions.GD_FW_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[12].bettingQuotas" value="${commissions.GD_FW_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[12].itemQuotas" value="${commissions.GD_FW_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="other">
        <td class="even2">1-8中发白<input type="hidden" name="commissions[13].playFinalType" value="GD_ZF_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="hongse"><input type="text" name="commissions[13].commissionA" value="${commissions.GD_ZF_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[13].commissionB" value="${commissions.GD_ZF_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[13].commissionC" value="${commissions.GD_ZF_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[13].bettingQuotas" value="${commissions.GD_ZF_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[13].itemQuotas" value="${commissions.GD_ZF_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
         <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
           <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="double">
        <td class="even2">總和大小<input type="hidden" name="commissions[14].playFinalType" value="GD_ZHDX_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="zise2"><input type="text" name="commissions[14].commissionA" value="${commissions.GD_ZHDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[14].commissionB" value="${commissions.GD_ZHDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[14].commissionC" value="${commissions.GD_ZHDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[14].bettingQuotas" value="${commissions.GD_ZHDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[14].itemQuotas" value="${commissions.GD_ZHDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">總和單雙<input type="hidden" name="commissions[15].playFinalType" value="GD_ZHDS_BALL" /></td>
        <%if(a){ %> <td name="gd_commission" class="zise2"><input type="text" name="commissions[15].commissionA" value="${commissions.GD_ZHDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[15].commissionB" value="${commissions.GD_ZHDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[15].commissionC" value="${commissions.GD_ZHDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[15].bettingQuotas" value="${commissions.GD_ZHDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[15].itemQuotas" value="${commissions.GD_ZHDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">總和尾數大小<input type="hidden" name="commissions[16].playFinalType" value="GD_ZHWSDX_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="zise2"><input type="text" name="commissions[16].commissionA" value="${commissions.GD_ZHWSDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[16].commissionB" value="${commissions.GD_ZHWSDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[16].commissionC" value="${commissions.GD_ZHWSDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[16].bettingQuotas" value="${commissions.GD_ZHWSDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[16].itemQuotas" value="${commissions.GD_ZHWSDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    
      <tr name="double">
        <td class="even2">龍虎<input type="hidden" name="commissions[17].playFinalType" value="GD_LH_BALL" /></td>
         <%if(a){ %> <td name="gd_commission" class="zise2"><input type="text" name="commissions[17].commissionA" value="${commissions.GD_LH_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[17].commissionB" value="${commissions.GD_LH_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[17].commissionC" value="${commissions.GD_LH_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[17].bettingQuotas" value="${commissions.GD_LH_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[17].itemQuotas" value="${commissions.GD_LH_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選二<input type="hidden" name="commissions[18].playFinalType" value="GD_RXH_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lvse2"><input type="text" name="commissions[18].commissionA" value="${commissions.GD_RXH_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission"  <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[18].commissionB" value="${commissions.GD_RXH_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission"  <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[18].commissionC" value="${commissions.GD_RXH_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[18].bettingQuotas" value="${commissions.GD_RXH_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[18].itemQuotas" value="${commissions.GD_RXH_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選二連直<input type="hidden" name="commissions[19].playFinalType" value="GD_RELZ_BALL" /></td>
        <%if(a){ %> <td name="gd_commission" class="lvse2"><input type="text" name="commissions[19].commissionA" value="${commissions.GD_RELZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[19].commissionB" value="${commissions.GD_RELZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[19].commissionC" value="${commissions.GD_RELZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[19].bettingQuotas" value="${commissions.GD_RELZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[19].itemQuotas" value="${commissions.GD_RELZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選二連組<input type="hidden" name="commissions[20].playFinalType" value="GD_RTLZ_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lvse2"><input type="text" name="commissions[20].commissionA" value="${commissions.GD_RTLZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[20].commissionB" value="${commissions.GD_RTLZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[20].commissionC" value="${commissions.GD_RTLZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[20].bettingQuotas" value="${commissions.GD_RTLZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[20].itemQuotas" value="${commissions.GD_RTLZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選三<input type="hidden" name="commissions[21].playFinalType" value="GD_RXS_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lvse2"><input type="text" name="commissions[21].commissionA" value="${commissions.GD_RXS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[21].commissionB" value="${commissions.GD_RXS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[21].commissionC" value="${commissions.GD_RXS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[21].bettingQuotas" value="${commissions.GD_RXS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[21].itemQuotas" value="${commissions.GD_RXS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選三前直<input type="hidden" name="commissions[22].playFinalType" value="GD_XSQZ_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lvse2"><input type="text" name="commissions[22].commissionA" value="${commissions.GD_XSQZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[22].commissionB" value="${commissions.GD_XSQZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[22].commissionC" value="${commissions.GD_XSQZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[22].bettingQuotas" value="${commissions.GD_XSQZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[22].itemQuotas" value="${commissions.GD_XSQZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選三前組<input type="hidden" name="commissions[23].playFinalType" value="GD_XTQZ_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lvse2"><input type="text" name="commissions[23].commissionA" value="${commissions.GD_XTQZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[23].commissionB" value="${commissions.GD_XTQZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[23].commissionC" value="${commissions.GD_XTQZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[23].bettingQuotas" value="${commissions.GD_XTQZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[23].itemQuotas" value="${commissions.GD_XTQZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選四<input type="hidden" name="commissions[24].playFinalType" value="GD_RXF_BALL" /></td>
        <%if(a){ %><td name="gd_commission" class="lvse2"><input type="text" name="commissions[24].commissionA" value="${commissions.GD_RXF_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[24].commissionB" value="${commissions.GD_RXF_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[24].commissionC" value="${commissions.GD_RXF_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[24].bettingQuotas" value="${commissions.GD_RXF_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[24].itemQuotas" value="${commissions.GD_RXF_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選五<input type="hidden" name="commissions[25].playFinalType" value="GD_RXW_BALL" /></td>
       <%if(a){ %> <td name="gd_commission" class="lvse2"><input type="text" name="commissions[25].commissionA" value="${commissions.GD_RXW_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="gd_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[25].commissionB" value="${commissions.GD_RXW_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="gd_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[25].commissionC" value="${commissions.GD_RXW_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[25].bettingQuotas" value="${commissions.GD_RXW_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[25].itemQuotas" value="${commissions.GD_RXW_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table>
    
    </td>
  </tr>
</table>
          <!-- 广东结束 -->
          <!--重庆开始-->
          <table width="100%" border="0" cellspacing="0" cellpadding="0" name="comm">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>重庆时时彩</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
             <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
          
          
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
          <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="tema">
        <td class="even2">第一球<input type="hidden" name="commissions[26].playFinalType" value="CQ_ONE_BALL" /></td>
      <%if(a){ %><td name="cq_commission" class="lanse2"><input type="text" name="commissions[26].commissionA" value="${commissions.CQ_ONE_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="cq_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[26].commissionB" value="${commissions.CQ_ONE_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[26].commissionC" value="${commissions.CQ_ONE_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[26].bettingQuotas" value="${commissions.CQ_ONE_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[26].itemQuotas" value="${commissions.CQ_ONE_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="tema">
        <td class="even2">第二球<input type="hidden" name="commissions[27].playFinalType" value="CQ_TWO_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="lanse2"><input type="text" name="commissions[27].commissionA" value="${commissions.CQ_TWO_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[27].commissionB" value="${commissions.CQ_TWO_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="cq_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[27].commissionC" value="${commissions.CQ_TWO_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[27].bettingQuotas" value="${commissions.CQ_TWO_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[27].itemQuotas" value="${commissions.CQ_TWO_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第三球<input type="hidden" name="commissions[28].playFinalType" value="CQ_THREE_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="lanse2"><input type="text" name="commissions[28].commissionA" value="${commissions.CQ_THREE_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[28].commissionB" value="${commissions.CQ_THREE_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[28].commissionC" value="${commissions.CQ_THREE_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[28].bettingQuotas" value="${commissions.CQ_THREE_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[28].itemQuotas" value="${commissions.CQ_THREE_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第四球<input type="hidden" name="commissions[29].playFinalType" value="CQ_FOUR_BALL" /></td>
         <%if(a){ %><td name="cq_commission" class="lanse2"><input type="text" name="commissions[29].commissionA" value="${commissions.CQ_FOUR_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[29].commissionB" value="${commissions.CQ_FOUR_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[29].commissionC" value="${commissions.CQ_FOUR_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[29].bettingQuotas" value="${commissions.CQ_FOUR_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[29].itemQuotas" value="${commissions.CQ_FOUR_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第五球<input type="hidden" name="commissions[30].playFinalType" value="CQ_FIVE_BALL" /></td>
        <%if(a){ %> <td name="cq_commission" class="lanse2"><input type="text" name="commissions[30].commissionA" value="${commissions.CQ_FIVE_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[30].commissionB" value="${commissions.CQ_FIVE_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="cq_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[30].commissionC" value="${commissions.CQ_FIVE_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[30].bettingQuotas" value="${commissions.CQ_FIVE_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[30].itemQuotas" value="${commissions.CQ_FIVE_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-5大小<input type="hidden" name="commissions[31].playFinalType" value="CQ_OFDX_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="zise2"><input type="text" name="commissions[31].commissionA" value="${commissions.CQ_OFDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[31].commissionB" value="${commissions.CQ_OFDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[31].commissionC" value="${commissions.CQ_OFDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[31].bettingQuotas" value="${commissions.CQ_OFDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[31].itemQuotas" value="${commissions.CQ_OFDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double"> 
        <td class="even2">1-5單雙<input type="hidden" name="commissions[32].playFinalType" value="CQ_OFDS_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="zise2"><input type="text" name="commissions[32].commissionA" value="${commissions.CQ_OFDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[32].commissionB" value="${commissions.CQ_OFDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[32].commissionC" value="${commissions.CQ_OFDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[32].bettingQuotas" value="${commissions.CQ_OFDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[32].itemQuotas" value="${commissions.CQ_OFDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
          <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="double">
        <td class="even2">總和大小<input type="hidden" name="commissions[33].playFinalType" value="CQ_ZHDX_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="zise2"><input type="text" name="commissions[33].commissionA" value="${commissions.CQ_ZHDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[33].commissionB" value="${commissions.CQ_ZHDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[33].commissionC" value="${commissions.CQ_ZHDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[33].bettingQuotas" value="${commissions.CQ_ZHDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[33].itemQuotas" value="${commissions.CQ_ZHDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">總和單雙<input type="hidden" name="commissions[34].playFinalType" value="CQ_ZHDS_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="zise2"><input type="text" name="commissions[34].commissionA" value="${commissions.CQ_ZHDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="cq_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[34].commissionB" value="${commissions.CQ_ZHDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="cq_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[34].commissionC" value="${commissions.CQ_ZHDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[34].bettingQuotas" value="${commissions.CQ_ZHDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[34].itemQuotas" value="${commissions.CQ_ZHDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">龍虎和<input type="hidden" name="commissions[35].playFinalType" value="CQ_LH_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="zise2"><input type="text" name="commissions[35].commissionA" value="${commissions.CQ_LH_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[35].commissionB" value="${commissions.CQ_LH_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[35].commissionC" value="${commissions.CQ_LH_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[35].bettingQuotas" value="${commissions.CQ_LH_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[35].itemQuotas" value="${commissions.CQ_LH_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="other">
        <td class="even2">前三<input type="hidden" name="commissions[36].playFinalType" value="CQ_QS_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="hongse"><input type="text" name="commissions[36].commissionA" value="${commissions.CQ_QS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[36].commissionB" value="${commissions.CQ_QS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[36].commissionC" value="${commissions.CQ_QS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[36].bettingQuotas" value="${commissions.CQ_QS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[36].itemQuotas" value="${commissions.CQ_QS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="other">
        <td class="even2">中三<input type="hidden" name="commissions[37].playFinalType" value="CQ_ZS_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="hongse"><input type="text" name="commissions[37].commissionA" value="${commissions.CQ_ZS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[37].commissionB" value="${commissions.CQ_ZS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[37].commissionC" value="${commissions.CQ_ZS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[37].bettingQuotas" value="${commissions.CQ_ZS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[37].itemQuotas" value="${commissions.CQ_ZS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="other">
        <td class="even2">后三<input type="hidden" name="commissions[38].playFinalType" value="CQ_HS_BALL" /></td>
        <%if(a){ %><td name="cq_commission" class="hongse"><input type="text" name="commissions[38].commissionA" value="${commissions.CQ_HS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="cq_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[38].commissionB" value="${commissions.CQ_HS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="cq_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[38].commissionC" value="${commissions.CQ_HS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[38].bettingQuotas" value="${commissions.CQ_HS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[38].itemQuotas" value="${commissions.CQ_HS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table></td>
  </tr>
</table>
          <!--重庆结束-->
          <!--北京开始-->
          <table width="100%" border="0" cellspacing="0" cellpadding="0" name="comm">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>北京赛车</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
           <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
          <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="tema">
        <td class="even2">冠军<input type="hidden" name="commissions[39].playFinalType" value="BJ_BALL_FIRST" /></td>
        <%if(a){ %><td name="bj_commission" class="lanse2"><input type="text" name="commissions[39].commissionA" value="${commissions.BJ_BALL_FIRST.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[39].commissionB" value="${commissions.BJ_BALL_FIRST.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[39].commissionC" value="${commissions.BJ_BALL_FIRST.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[39].bettingQuotas" value="${commissions.BJ_BALL_FIRST.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[39].itemQuotas" value="${commissions.BJ_BALL_FIRST.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="tema">
        <td class="even2">亚军<input type="hidden" name="commissions[40].playFinalType" value="BJ_BALL_SECOND" /></td>
    <%if(a){ %> <td name="bj_commission" class="lanse2"><input type="text" name="commissions[40].commissionA" value="${commissions.BJ_BALL_SECOND.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[40].commissionB" value="${commissions.BJ_BALL_SECOND.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[40].commissionC" value="${commissions.BJ_BALL_SECOND.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[40].bettingQuotas" value="${commissions.BJ_BALL_SECOND.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[40].itemQuotas" value="${commissions.BJ_BALL_SECOND.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第三名<input type="hidden" name="commissions[41].playFinalType" value="BJ_BALL_THIRD" /></td>
       <%if(a){ %> <td name="bj_commission" class="lanse2"><input type="text" name="commissions[41].commissionA" value="${commissions.BJ_BALL_THIRD.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[41].commissionB" value="${commissions.BJ_BALL_THIRD.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[41].commissionC" value="${commissions.BJ_BALL_THIRD.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[41].bettingQuotas" value="${commissions.BJ_BALL_THIRD.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[41].itemQuotas" value="${commissions.BJ_BALL_THIRD.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第四名<input type="hidden" name="commissions[42].playFinalType" value="BJ_BALL_FORTH" /></td>
       <%if(a){ %><td name="bj_commission" class="lanse2"><input type="text" name="commissions[42].commissionA" value="${commissions.BJ_BALL_FORTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[42].commissionB" value="${commissions.BJ_BALL_FORTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[42].commissionC" value="${commissions.BJ_BALL_FORTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[42].bettingQuotas" value="${commissions.BJ_BALL_FORTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[42].itemQuotas" value="${commissions.BJ_BALL_FORTH.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第五名<input type="hidden" name="commissions[43].playFinalType" value="BJ_BALL_FIFTH" /></td>
    <%if(a){ %><td name="bj_commission" class="lanse2"><input type="text" name="commissions[43].commissionA" value="${commissions.BJ_BALL_FIFTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[43].commissionB" value="${commissions.BJ_BALL_FIFTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[43].commissionC" value="${commissions.BJ_BALL_FIFTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[43].bettingQuotas" value="${commissions.BJ_BALL_FIFTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[43].itemQuotas" value="${commissions.BJ_BALL_FIFTH.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第六名<input type="hidden" name="commissions[44].playFinalType" value="BJ_BALL_SIXTH" /></td>
        <%if(a){ %><td name="bj_commission" class="lanse2"><input type="text" name="commissions[44].commissionA" value="${commissions.BJ_BALL_SIXTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[44].commissionB" value="${commissions.BJ_BALL_SIXTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[44].commissionC" value="${commissions.BJ_BALL_SIXTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[44].bettingQuotas" value="${commissions.BJ_BALL_SIXTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[44].itemQuotas" value="${commissions.BJ_BALL_SIXTH.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第七名<input type="hidden" name="commissions[45].playFinalType" value="BJ_BALL_SEVENTH" /></td>
     <%if(a){ %> <td name="bj_commission" class="lanse2"><input type="text" name="commissions[45].commissionA" value="${commissions.BJ_BALL_SEVENTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[45].commissionB" value="${commissions.BJ_BALL_SEVENTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[45].commissionC" value="${commissions.BJ_BALL_SEVENTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[45].bettingQuotas" value="${commissions.BJ_BALL_SEVENTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[45].itemQuotas" value="${commissions.BJ_BALL_SEVENTH.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第八名<input type="hidden" name="commissions[46].playFinalType" value="BJ_BALL_EIGHTH" /></td>
    <%if(a){ %> <td name="bj_commission" class="lanse2"><input type="text" name="commissions[46].commissionA" value="${commissions.BJ_BALL_EIGHTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
      <%if(b){ %>  <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[46].commissionB" value="${commissions.BJ_BALL_EIGHTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[46].commissionC" value="${commissions.BJ_BALL_EIGHTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[46].bettingQuotas" value="${commissions.BJ_BALL_EIGHTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[46].itemQuotas" value="${commissions.BJ_BALL_EIGHTH.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
         <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
          <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="tema">
        <td class="even2">第九名<input type="hidden" name="commissions[47].playFinalType" value="BJ_BALL_NINTH" /></td>
     <%if(a){ %><td name="bj_commission" class="lanse2"><input type="text" name="commissions[47].commissionA" value="${commissions.BJ_BALL_NINTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
      <%if(b){ %>  <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[47].commissionB" value="${commissions.BJ_BALL_NINTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
      <%if(c){ %>  <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[47].commissionC" value="${commissions.BJ_BALL_NINTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[47].bettingQuotas" value="${commissions.BJ_BALL_NINTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[47].itemQuotas" value="${commissions.BJ_BALL_NINTH.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="tema">
        <td class="even2">第十名<input type="hidden" name="commissions[48].playFinalType" value="BJ_BALL_TENTH" /></td>
      <%if(a){ %>  <td name="bj_commission" class="lanse2"><input type="text" name="commissions[48].commissionA" value="${commissions.BJ_BALL_TENTH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[48].commissionB" value="${commissions.BJ_BALL_TENTH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[48].commissionC" value="${commissions.BJ_BALL_TENTH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[48].bettingQuotas" value="${commissions.BJ_BALL_TENTH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[48].itemQuotas" value="${commissions.BJ_BALL_TENTH.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">1-10大小<input type="hidden" name="commissions[49].playFinalType" value="BJ_1-10_DX" /></td>
       <%if(a){ %> <td name="bj_commission" class="zise2"><input type="text" name="commissions[49].commissionA" value="${commissions.BJ_1_10_DX.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[49].commissionB" value="${commissions.BJ_1_10_DX.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[49].commissionC" value="${commissions.BJ_1_10_DX.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[49].bettingQuotas" value="${commissions.BJ_1_10_DX.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[49].itemQuotas" value="${commissions.BJ_1_10_DX.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">1-10單雙<input type="hidden" name="commissions[50].playFinalType" value="BJ_1-10_DS" /></td>
        <%if(a){ %><td name="bj_commission" class="zise2"><input type="text" name="commissions[50].commissionA" value="${commissions.BJ_1_10_DS.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[50].commissionB" value="${commissions.BJ_1_10_DS.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[50].commissionC" value="${commissions.BJ_1_10_DS.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[50].bettingQuotas" value="${commissions.BJ_1_10_DS.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[50].itemQuotas" value="${commissions.BJ_1_10_DS.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">1-5龍虎<input type="hidden" name="commissions[51].playFinalType" value="BJ_1-5_LH" /></td>
        <%if(a){ %><td name="bj_commission" class="zise2"><input type="text" name="commissions[51].commissionA" value="${commissions.BJ_1_5_LH.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="bj_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[51].commissionB" value="${commissions.BJ_1_5_LH.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[51].commissionC" value="${commissions.BJ_1_5_LH.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[51].bettingQuotas" value="${commissions.BJ_1_5_LH.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[51].itemQuotas" value="${commissions.BJ_1_5_LH.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">冠亞軍和大小<input type="hidden" name="commissions[52].playFinalType" value="BJ_DOUBLSIDE_DX" /></td>
        <%if(a){ %><td name="bj_commission" class="zise2"><input type="text" name="commissions[52].commissionA" value="${commissions.BJ_DOUBLSIDE_DX.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[52].commissionB" value="${commissions.BJ_DOUBLSIDE_DX.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[52].commissionC" value="${commissions.BJ_DOUBLSIDE_DX.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[52].bettingQuotas" value="${commissions.BJ_DOUBLSIDE_DX.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[52].itemQuotas" value="${commissions.BJ_DOUBLSIDE_DX.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">冠亞军和單雙<input type="hidden" name="commissions[53].playFinalType" value="BJ_DOUBLSIDE_DS" /></td>
       <%if(a){ %>  <td name="bj_commission" class="zise2"><input type="text" name="commissions[53].commissionA" value="${commissions.BJ_DOUBLSIDE_DS.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[53].commissionB" value="${commissions.BJ_DOUBLSIDE_DS.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="bj_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[53].commissionC" value="${commissions.BJ_DOUBLSIDE_DS.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[53].bettingQuotas" value="${commissions.BJ_DOUBLSIDE_DS.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[53].itemQuotas" value="${commissions.BJ_DOUBLSIDE_DS.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="other">
        <td class="even2">冠亞军和<input type="hidden" name="commissions[54].playFinalType" value="BJ_GROUP" /></td>
       <%if(a){ %> <td name="bj_commission" class="hongse"><input type="text" name="commissions[54].commissionA" value="${commissions.BJ_GROUP.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="bj_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[54].commissionB" value="${commissions.BJ_GROUP.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="bj_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[54].commissionC" value="${commissions.BJ_GROUP.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[54].bettingQuotas" value="${commissions.BJ_GROUP.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[54].itemQuotas" value="${commissions.BJ_GROUP.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table></td>
  </tr>
</table>
          <!--北京结束-->    
          
<!--江苏开始-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" name="comm">
  <colgroup>
	  <col width="50%" />
	  <col width="50%" />
  </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>江苏骰宝</strong></th>
  </tr>
  <tr>
    <td width="50%" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
	    <colgroup>
	    	<col width="15%" />
		    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
		      <col width="15%" />
		    </s:if>
	    	<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
	     		<col width="15%" />
	    	</s:elseif>
	    	<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
	     		<col width="15%" />
	    	</s:elseif>
	    	<s:else>
	 			<col width="15%" />
	          	<col width="15%" />
	          	<col width="15%" />
	    	</s:else>
	          <col width="20%" />
	          <col width="20%" />
	        </colgroup>
	      <tr>
	        <th>交易类型</th>
	        	<s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
	     			<th>A盘</th>
	    		</s:if>
	    		<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
	     			<th>B盘</th>
	    		</s:elseif>
	    		<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
	    			<th>C盘</th>
	    		</s:elseif>
	    		<s:else>
		 			<th>A盘</th>
		        	<th>B盘</th>
		        	<th>C盘</th>
	    		</s:else>
	        <th>單注限额</th>
	        <th>單期限额</th>
	      </tr>
	      <tr name="double">
	        <td class="even2">大小<input type="hidden" name="commissions[80].playFinalType" value="K3_DX" /></td>
	        <%if(a){ %><td name="js_commission" class="zise2"><input type="text" name="commissions[80].commissionA" value="${commissions.K3_DX.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[80].commissionB" value="${commissions.K3_DX.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[80].commissionC" value="${commissions.K3_DX.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[80].bettingQuotas" value="${commissions.K3_DX.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[80].itemQuotas" value="${commissions.K3_DX.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
	      <tr name="double">
	        <td class="even2">三军<input type="hidden" name="commissions[81].playFinalType" value="K3_SJ" /></td>
	        <%if(a){ %><td name="js_commission" class="zise2"><input type="text" name="commissions[81].commissionA" value="${commissions.K3_SJ.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[81].commissionB" value="${commissions.K3_SJ.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[81].commissionC" value="${commissions.K3_SJ.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[81].bettingQuotas" value="${commissions.K3_SJ.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[81].itemQuotas" value="${commissions.K3_SJ.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
	      <tr name="lm">
	        <td class="even2">圍骰<input type="hidden" name="commissions[82].playFinalType" value="K3_WS" /></td>
	        <%if(a){ %><td name="js_commission" class="lvse2"><input type="text" name="commissions[82].commissionA" value="${commissions.K3_WS.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[82].commissionB" value="${commissions.K3_WS.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[82].commissionC" value="${commissions.K3_WS.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[82].bettingQuotas" value="${commissions.K3_WS.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[82].itemQuotas" value="${commissions.K3_WS.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
	      <tr name="lm">
	        <td class="even2">全骰<input type="hidden" name="commissions[83].playFinalType" value="K3_QS" /></td>
	        <%if(a){ %><td name="js_commission" class="lvse2"><input type="text" name="commissions[83].commissionA" value="${commissions.K3_QS.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[83].commissionB" value="${commissions.K3_QS.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[83].commissionC" value="${commissions.K3_QS.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[83].bettingQuotas" value="${commissions.K3_QS.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[83].itemQuotas" value="${commissions.K3_QS.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
    </table>
   </td>
   <td width="50%" valign="top">
   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
	    <colgroup>
	    	<col width="15%" />
		    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
		      <col width="15%" />
		    </s:if>
	    	<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
	     		<col width="15%" />
	    	</s:elseif>
	    	<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
	     		<col width="15%" />
	    	</s:elseif>
	    	<s:else>
	 			<col width="15%" />
	          	<col width="15%" />
	          	<col width="15%" />
	    	</s:else>
	          <col width="20%" />
	          <col width="20%" />
	        </colgroup>
	      <tr>
	        <th>交易类型</th>
	        	<s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
	     			<th>A盘</th>
	    		</s:if>
	    		<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
	     			<th>B盘</th>
	    		</s:elseif>
	    		<s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
	    			<th>C盘</th>
	    		</s:elseif>
	    		<s:else>
		 			<th>A盘</th>
		        	<th>B盘</th>
		        	<th>C盘</th>
	    		</s:else>
	        <th>單注限额</th>
	        <th>單期限额</th>
	      </tr>
	      <tr name="other">
	        <td class="even2">點數<input type="hidden" name="commissions[84].playFinalType" value="K3_DS" /></td>
	        <%if(a){ %><td name="js_commission" class="hongse"><input type="text" name="commissions[84].commissionA" value="${commissions.K3_DS.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[84].commissionB" value="${commissions.K3_DS.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[84].commissionC" value="${commissions.K3_DS.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[84].bettingQuotas" value="${commissions.K3_DS.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[84].itemQuotas" value="${commissions.K3_DS.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
	      <tr name="lm">
	        <td class="even2">長牌<input type="hidden" name="commissions[85].playFinalType" value="K3_CP" /></td>
	        <%if(a){ %><td name="js_commission" class="lvse2"><input type="text" name="commissions[85].commissionA" value="${commissions.K3_CP.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission"  <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[85].commissionB" value="${commissions.K3_CP.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission"  <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[85].commissionC" value="${commissions.K3_CP.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[85].bettingQuotas" value="${commissions.K3_CP.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[85].itemQuotas" value="${commissions.K3_CP.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
	      <tr name="lm">
	        <td class="even2">短牌<input type="hidden" name="commissions[86].playFinalType" value="K3_DP" /></td>
	        <%if(a){ %><td name="js_commission" class="lvse2"><input type="text" name="commissions[86].commissionA" value="${commissions.K3_DP.commissionA}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(b){ %><td name="js_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[86].commissionB" value="${commissions.K3_DP.commissionB}" class="b_g" style="width:58px"/></td><%} %>
	        <%if(c){ %><td name="js_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[86].commissionC" value="${commissions.K3_DP.commissionC}" class="b_g" style="width:58px"/></td><%} %>
	        <td><input type="text" name="commissions[86].bettingQuotas" value="${commissions.K3_DP.bettingQuotas}" class="b_g" style="width:82px"/></td>
	        <td><input type="text" name="commissions[86].itemQuotas" value="${commissions.K3_DP.itemQuotas}" class="b_g" style="width:82px"/></td>
	      </tr>
      
    </table>
   </td>
   </tr>
</table>     
   <!--幸运农场开始-->
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="" name="comm" >
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>幸运农场</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
           <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
    <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        
  
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="tema">
        <td class="even2">第一球 <input type="hidden" name="commissions[87].playFinalType" value="NC_ONE_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lanse2"><input type="text" name="commissions[87].commissionA" value="${commissions.NC_ONE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%> ><input type="text" name="commissions[87].commissionB" value="${commissions.NC_ONE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[87].commissionC" value="${commissions.NC_ONE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[87].bettingQuotas" value="${commissions.NC_ONE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[87].itemQuotas" value="${commissions.NC_ONE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
      </tr>
      <tr name="tema">
        <td class="even2">第二球<input type="hidden" name="commissions[88].playFinalType" value="NC_TWO_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lanse2"><input type="text" name="commissions[88].commissionA" value="${commissions.NC_TWO_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[88].commissionB" value="${commissions.NC_TWO_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[88].commissionC" value="${commissions.NC_TWO_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[88].bettingQuotas" value="${commissions.NC_TWO_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[88].itemQuotas" value="${commissions.NC_TWO_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第三球<input type="hidden" name="commissions[89].playFinalType" value="NC_THREE_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lanse2"><input type="text" name="commissions[89].commissionA" value="${commissions.NC_THREE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[89].commissionB" value="${commissions.NC_THREE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[89].commissionC" value="${commissions.NC_THREE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[89].bettingQuotas" value="${commissions.NC_THREE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[89].itemQuotas" value="${commissions.NC_THREE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第四球<input type="hidden" name="commissions[90].playFinalType" value="NC_FOUR_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lanse2"><input type="text" name="commissions[90].commissionA" value="${commissions.NC_FOUR_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[90].commissionB" value="${commissions.NC_FOUR_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[90].commissionC" value="${commissions.NC_FOUR_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[90].bettingQuotas" value="${commissions.NC_FOUR_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[90].itemQuotas" value="${commissions.NC_FOUR_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第五球<input type="hidden" name="commissions[91].playFinalType" value="NC_FIVE_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lanse2"><input type="text" name="commissions[91].commissionA" value="${commissions.NC_FIVE_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[91].commissionB" value="${commissions.NC_FIVE_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[91].commissionC" value="${commissions.NC_FIVE_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[91].bettingQuotas" value="${commissions.NC_FIVE_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[91].itemQuotas" value="${commissions.NC_FIVE_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第六球<input type="hidden" name="commissions[92].playFinalType" value="NC_SIX_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lanse2"><input type="text" name="commissions[92].commissionA" value="${commissions.NC_SIX_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
       <%if(b){ %> <td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[92].commissionB" value="${commissions.NC_SIX_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[92].commissionC" value="${commissions.NC_SIX_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[92].bettingQuotas" value="${commissions.NC_SIX_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[92].itemQuotas" value="${commissions.NC_SIX_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第七球<input type="hidden" name="commissions[93].playFinalType" value="NC_SEVEN_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lanse2"><input type="text" name="commissions[93].commissionA" value="${commissions.NC_SEVEN_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[93].commissionB" value="${commissions.NC_SEVEN_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[93].commissionC" value="${commissions.NC_SEVEN_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[93].bettingQuotas" value="${commissions.NC_SEVEN_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[93].itemQuotas" value="${commissions.NC_SEVEN_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="tema">
        <td class="even2">第八球<input type="hidden" name="commissions[94].playFinalType" value="NC_EIGHT_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lanse2"><input type="text" name="commissions[94].commissionA" value="${commissions.NC_EIGHT_BALL.commissionA}" class="b_g" style="width:58px" dataType="gd18a"/></td><%} %>
       <%if(b){ %> <td name="NC_commission" <%if(!a&!c){ %>class="lanse2"<%}%>><input type="text" name="commissions[94].commissionB" value="${commissions.NC_EIGHT_BALL.commissionB}" class="b_g" style="width:58px" dataType="gd18b"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lanse2"<%}%>><input type="text" name="commissions[94].commissionC" value="${commissions.NC_EIGHT_BALL.commissionC}" class="b_g" style="width:58px" dataType="gd18c"/></td><%} %>
        <td><input type="text" name="commissions[94].bettingQuotas" value="${commissions.NC_EIGHT_BALL.bettingQuotas}" class="b_g" style="width:82px" dataType="gd18bq"/></td>
        <td><input type="text" name="commissions[94].itemQuotas" value="${commissions.NC_EIGHT_BALL.itemQuotas}" class="b_g" style="width:82px" dataType="gd18iq"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8大小<input type="hidden" name="commissions[95].playFinalType" value="NC_OEDX_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="zise2"><input type="text" name="commissions[95].commissionA" value="${commissions.NC_OEDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[95].commissionB" value="${commissions.NC_OEDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[95].commissionC" value="${commissions.NC_OEDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[95].bettingQuotas" value="${commissions.NC_OEDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[95].itemQuotas" value="${commissions.NC_OEDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8單雙<input type="hidden" name="commissions[96].playFinalType" value="NC_OEDS_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="zise2"><input type="text" name="commissions[96].commissionA" value="${commissions.NC_OEDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[96].commissionB" value="${commissions.NC_OEDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[96].commissionC" value="${commissions.NC_OEDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[96].bettingQuotas" value="${commissions.NC_OEDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[96].itemQuotas" value="${commissions.NC_OEDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8尾數大小<input type="hidden" name="commissions[97].playFinalType" value="NC_OEWSDX_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="zise2"><input type="text" name="commissions[97].commissionA" value="${commissions.NC_OEWSDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[97].commissionB" value="${commissions.NC_OEWSDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[97].commissionC" value="${commissions.NC_OEWSDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[97].bettingQuotas" value="${commissions.NC_OEWSDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[97].itemQuotas" value="${commissions.NC_OEWSDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="double">
        <td class="even2">1-8合數單雙<input type="hidden" name="commissions[98].playFinalType" value="NC_HSDS_BALL" /></td>
       <%if(a){ %><td name="NC_commission" class="zise2"><input type="text" name="commissions[98].commissionA" value="${commissions.NC_HSDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="NC_commission" <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[98].commissionB" value="${commissions.NC_HSDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[98].commissionC" value="${commissions.NC_HSDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[98].bettingQuotas" value="${commissions.NC_HSDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[98].itemQuotas" value="${commissions.NC_HSDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="other">
        <td class="even2">1-8方位<input type="hidden" name="commissions[99].playFinalType" value="NC_FW_BALL" /></td>
        <%if(a){ %> <td name="NC_commission" class="hongse"><input type="text" name="commissions[99].commissionA" value="${commissions.NC_FW_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="NC_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[99].commissionB" value="${commissions.NC_FW_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[99].commissionC" value="${commissions.NC_FW_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[99].bettingQuotas" value="${commissions.NC_FW_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[99].itemQuotas" value="${commissions.NC_FW_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
        </tr>
      <tr name="other">
        <td class="even2">1-8中发白<input type="hidden" name="commissions[100].playFinalType" value="NC_ZF_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="hongse"><input type="text" name="commissions[100].commissionA" value="${commissions.NC_ZF_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="hongse"<%}%>><input type="text" name="commissions[100].commissionB" value="${commissions.NC_ZF_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="hongse"<%}%>><input type="text" name="commissions[100].commissionC" value="${commissions.NC_ZF_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[100].bettingQuotas" value="${commissions.NC_ZF_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[100].itemQuotas" value="${commissions.NC_ZF_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
         <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
      <col width="15%" />
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <col width="15%" />
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
     <col width="15%" />
    </s:elseif>
    <s:else>
 <col width="15%" />
          <col width="15%" />
          <col width="15%" />
    </s:else>
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th>交易类型</th>
           <s:if test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("A")'>
     <th>A盘</th>
    </s:if>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("B")'>
     <th>B盘</th>
    </s:elseif>
    <s:elseif test='#request.CommissionUser.userType==9&&#request.CommissionUser.plate.equals("C")'>
    <th>C盘</th>
    </s:elseif>
    <s:else>
 <th>A盘</th>
        <th>B盘</th>
        <th>C盘</th>
    </s:else>
        <th>單注限额</th>
        <th>單期限额</th>
      </tr>
      <tr name="double">
        <td class="even2">總和大小<input type="hidden" name="commissions[101].playFinalType" value="NC_ZHDX_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="zise2"><input type="text" name="commissions[101].commissionA" value="${commissions.NC_ZHDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[101].commissionB" value="${commissions.NC_ZHDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[101].commissionC" value="${commissions.NC_ZHDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[101].bettingQuotas" value="${commissions.NC_ZHDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[101].itemQuotas" value="${commissions.NC_ZHDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">總和單雙<input type="hidden" name="commissions[102].playFinalType" value="NC_ZHDS_BALL" /></td>
        <%if(a){ %> <td name="NC_commission" class="zise2"><input type="text" name="commissions[102].commissionA" value="${commissions.NC_ZHDS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[102].commissionB" value="${commissions.NC_ZHDS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[102].commissionC" value="${commissions.NC_ZHDS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[102].bettingQuotas" value="${commissions.NC_ZHDS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[102].itemQuotas" value="${commissions.NC_ZHDS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="double">
        <td class="even2">總和尾數大小<input type="hidden" name="commissions[103].playFinalType" value="NC_ZHWSDX_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="zise2"><input type="text" name="commissions[103].commissionA" value="${commissions.NC_ZHWSDX_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[103].commissionB" value="${commissions.NC_ZHWSDX_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[103].commissionC" value="${commissions.NC_ZHWSDX_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[103].bettingQuotas" value="${commissions.NC_ZHWSDX_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[103].itemQuotas" value="${commissions.NC_ZHWSDX_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    
      <tr name="double">
        <td class="even2">龍虎<input type="hidden" name="commissions[104].playFinalType" value="NC_LH_BALL" /></td>
         <%if(a){ %> <td name="NC_commission" class="zise2"><input type="text" name="commissions[104].commissionA" value="${commissions.NC_LH_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission"  <%if(!a&!c){ %>class="zise2"<%}%>><input type="text" name="commissions[104].commissionB" value="${commissions.NC_LH_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission"  <%if(!a&!b){ %>class="zise2"<%}%>><input type="text" name="commissions[104].commissionC" value="${commissions.NC_LH_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[104].bettingQuotas" value="${commissions.NC_LH_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[104].itemQuotas" value="${commissions.NC_LH_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選二<input type="hidden" name="commissions[105].playFinalType" value="NC_RXH_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lvse2"><input type="text" name="commissions[105].commissionA" value="${commissions.NC_RXH_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission"  <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[105].commissionB" value="${commissions.NC_RXH_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission"  <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[105].commissionC" value="${commissions.NC_RXH_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[105].bettingQuotas" value="${commissions.NC_RXH_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[105].itemQuotas" value="${commissions.NC_RXH_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選二連直<input type="hidden" name="commissions[106].playFinalType" value="NC_RELZ_BALL" /></td>
        <%if(a){ %> <td name="NC_commission" class="lvse2"><input type="text" name="commissions[106].commissionA" value="${commissions.NC_RELZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[106].commissionB" value="${commissions.NC_RELZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[106].commissionC" value="${commissions.NC_RELZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[106].bettingQuotas" value="${commissions.NC_RELZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[106].itemQuotas" value="${commissions.NC_RELZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選二連組<input type="hidden" name="commissions[107].playFinalType" value="NC_RTLZ_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lvse2"><input type="text" name="commissions[107].commissionA" value="${commissions.NC_RTLZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[107].commissionB" value="${commissions.NC_RTLZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[107].commissionC" value="${commissions.NC_RTLZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[107].bettingQuotas" value="${commissions.NC_RTLZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[107].itemQuotas" value="${commissions.NC_RTLZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選三<input type="hidden" name="commissions[108].playFinalType" value="NC_RXS_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lvse2"><input type="text" name="commissions[108].commissionA" value="${commissions.NC_RXS_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[108].commissionB" value="${commissions.NC_RXS_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[108].commissionC" value="${commissions.NC_RXS_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[108].bettingQuotas" value="${commissions.NC_RXS_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[108].itemQuotas" value="${commissions.NC_RXS_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選三前直<input type="hidden" name="commissions[109].playFinalType" value="NC_XSQZ_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lvse2"><input type="text" name="commissions[109].commissionA" value="${commissions.NC_XSQZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
       <%if(b){ %> <td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[109].commissionB" value="${commissions.NC_XSQZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
       <%if(c){ %> <td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[109].commissionC" value="${commissions.NC_XSQZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[109].bettingQuotas" value="${commissions.NC_XSQZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[109].itemQuotas" value="${commissions.NC_XSQZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">選三前組<input type="hidden" name="commissions[110].playFinalType" value="NC_XTQZ_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lvse2"><input type="text" name="commissions[110].commissionA" value="${commissions.NC_XTQZ_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[110].commissionB" value="${commissions.NC_XTQZ_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[110].commissionC" value="${commissions.NC_XTQZ_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[110].bettingQuotas" value="${commissions.NC_XTQZ_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[110].itemQuotas" value="${commissions.NC_XTQZ_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選四<input type="hidden" name="commissions[111].playFinalType" value="NC_RXF_BALL" /></td>
        <%if(a){ %><td name="NC_commission" class="lvse2"><input type="text" name="commissions[111].commissionA" value="${commissions.NC_RXF_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[111].commissionB" value="${commissions.NC_RXF_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[111].commissionC" value="${commissions.NC_RXF_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[111].bettingQuotas" value="${commissions.NC_RXF_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[111].itemQuotas" value="${commissions.NC_RXF_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
      <tr name="lm">
        <td class="even2">任選五<input type="hidden" name="commissions[112].playFinalType" value="NC_RXW_BALL" /></td>
       <%if(a){ %> <td name="NC_commission" class="lvse2"><input type="text" name="commissions[112].commissionA" value="${commissions.NC_RXW_BALL.commissionA}" class="b_g" style="width:58px"/></td><%} %>
        <%if(b){ %><td name="NC_commission" <%if(!a&!c){ %>class="lvse2"<%}%>><input type="text" name="commissions[112].commissionB" value="${commissions.NC_RXW_BALL.commissionB}" class="b_g" style="width:58px"/></td><%} %>
        <%if(c){ %><td name="NC_commission" <%if(!a&!b){ %>class="lvse2"<%}%>><input type="text" name="commissions[112].commissionC" value="${commissions.NC_RXW_BALL.commissionC}" class="b_g" style="width:58px"/></td><%} %>
        <td><input type="text" name="commissions[112].bettingQuotas" value="${commissions.NC_RXW_BALL.bettingQuotas}" class="b_g" style="width:82px"/></td>
        <td><input type="text" name="commissions[112].itemQuotas" value="${commissions.NC_RXW_BALL.itemQuotas}" class="b_g" style="width:82px"/></td>
      </tr>
    </table>
    
    </td>
  </tr>
</table>
          <!-- 幸运农场结束 -->
 </td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;
        
        
        
        </td>
      </tr>
    </table>
     <s:if test="#request.hasBet==true">
    <p style="text-align:center;padding:10px 0 5px;" class="red">抱歉！開盤中暫時停止修改退水（各項限額依然可以調整），請在本期結算後再調整退水</p>
    </s:if>
    
    </td>
  </tr>
 
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif">
    <input type="hidden" value='${parentCom}' id="jq_parent_com" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'><input name="" type="button" class="btn2" value="保存" onclick="checkSubmit();" id="jq_save"/>
        <input name="" type="button" class="btn2 ml10" value="取消" id="jq_cancel" onclick="cancle();"/>
        
        <s:if test="#request.userType=='branch'">
        <input name="defaultCommission" type="checkbox"  <s:if test="#request.CommissionUser.defaultCommission==1">checked="checked"</s:if> style="margin-left:20px;" />
         <strong class="blue">存为“新開户”默认</strong>
        </s:if>
       </td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<input type="hidden" value="${jsonCommission}" name="jsonCommission"/>
</form>
</div>
<script>


var suc=false;
var jsonpc=null;
$(document).ready(function() {
	
	
	jsonpc=getparentCom();
	$("#jq_lm,#jq_tm,#jq_double").click(function() { 
		
       if($(this).attr("id")=="jq_lm")
    	{
    	   var eachObj=$('table[name=comm] tr[name="lm"] td');
    	   batchInput($(this),eachObj);
    	}
       else if($(this).attr("id")=="jq_tm")
    	   {
    	   var eachObj=$('table[name=comm] tr[name="tema"] td');
    	   batchInput($(this),eachObj);
    	   }
       else if($(this).attr("id")=="jq_double")
    	   {
    	   var eachObj=$('table[name=comm] tr[name="double"] td')
    	   batchInput($(this),eachObj);
    	   }
		  
	});
	
	/*
	$("table[name='comm'] input[type=text]").focusin(function() { 
		      if(suc==false)
		    	  return;
		      var thv=$(this).attr("dataType");
		       var values=[];
		       var finalVal=null;
		       $("input[dataType="+thv+"]").each(function(i){
		    	   values.push($(this).val());
		  	    });	
		        values.sort();
		        if(values[0]==values[1])
		    	   finalVal=values[values.length-1];
		       else 
		    	   finalVal=values[0];
		       $("input[dataType="+thv+"]").each(function(i){
		    	   $(this).removeClass("notok");
		    	   $(this).val(finalVal);
		  	    }); 	 
			});
	*/
	$("table[name='comm'] input[type=text]").change(function() {   
	    
		return (checkInput($(this)));
		
		  
		
	});
 



var hasBet='${hasBet}';

if(hasBet&&hasBet=='true')
{
	
	$('.king td[name="gd_commission"]').each(function(i){
		
		 var commissionInput=$(this).children();
		 $(this).html(commissionInput.val());
	
	});	
	
}

if(hasBet&&hasBet=='true')
{
	
	$('.king td[name="cq_commission"]').each(function(i){
		
		 var commissionInput=$(this).children();
		 $(this).html(commissionInput.val());
	
	});	
	
}

if(hasBet&&hasBet=='true')
{
	
	$('.king td[name="bj_commission"]').each(function(i){
		
		 var commissionInput=$(this).children();
		 $(this).html(commissionInput.val());
	
	});	
	
}
//add by peter for K3
if(hasBet&&hasBet=='true')
{
	
	$('.king td[name="js_commission"]').each(function(i){
		
		 var commissionInput=$(this).children();
		 $(this).html(commissionInput.val());
	
	});	
	
}

//add by peter for NC
if(hasBet&&hasBet=='true')
{
	
	$('.king td[name="NC_commission"]').each(function(i){
		
		 var commissionInput=$(this).children();
		 $(this).html(commissionInput.val());
	
	});	
	
}

});

function checkInput(inputObj)
{
	
	  var value=inputObj.val();
	  var key=inputObj.parent().parent().children(".even2").children("input").val();
	  key=key.replace("-","_");//特殊處理北京 1-10 1-5
	  var inputName=inputObj.attr("name");
	  suc=false;
	  if(jsonpc)//修改 非非公司的管理级别 提水
	  {
		  if(!$.isNumeric(value))
		  {
			  alert("請輸入正確的數字");
			   window.setTimeout(function () { inputObj.focus(); }, 0);
			  return false; 
		  }

		 if(inputName.indexOf('commissionA')!=-1)
		  {
			  var pa=jsonpc[key].commissionA;
		
			  if(parseFloat(value)<parseFloat(pa))
			  {
				  alert("'A'盘 不能低于'"+pa+"',请修改"); 
				  
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  return false;
				 
			  }
			  else if(parseFloat(value)>100)
			  {
				  alert("'A'盘 不能高于'"+100+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false;
			  }
			  $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('commissionB')!=-1)
		  {
			  var pb=jsonpc[key].commissionB; 
		      if(parseFloat(value)<parseFloat(pb))
			  {
				  alert("'B'盘 不能低于'"+pb+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this)[0].focus();
				  return false;
			  }
		      else if(parseFloat(value)>100)
			  {
				  alert("'B'盘 不能高于'"+100+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false;
			  }
		      $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('commissionC')!=-1)
		  {
			  var pc=jsonpc[key].commissionC;
	          if(parseFloat(value)<parseFloat(pc))
			  {
				  alert("'C'盘 不能低于'"+pc+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this)[0].focus();
				  return false;
			  }
	          else if(parseFloat(value)>100)
			  {
				  alert("'C'盘 不能高于'"+100+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false;
			  }
	          $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('bettingQuotas')!=-1)
		  {
			  var pbq=jsonpc[key].bettingQuotas; 
			  if(Math.floor(value)>Math.floor(pbq))
			  {
				  alert("单注限额不能高于'"+pbq+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this)[0].focus();
				  return false;
			  }
			  $(this).removeClass("notok");
			  
		  }
		  else if(inputName.indexOf('itemQuotas')!=-1)
		  {
			  var piq=jsonpc[key].itemQuotas; 
			  if(Math.floor(value)>Math.floor(piq))
			  {
				  alert("单项限额不能高于'"+piq+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				 // $(this)[0].focus();
				  return false;
			  }
			  $(this).removeClass("notok");
		  }
	  }
	  else//修改分公司级别提水
	  {
		  if(!$.isNumeric(value))
		  {
			  alert("請輸入正確的數字");
			   window.setTimeout(function () { inputObj.focus(); }, 0);
			  //$(this).focus();
			  return false; 
		  }
		  if(inputName.indexOf('commissionA')!=-1)
		  {
			  if(parseFloat(value)<0)
			 {
				 
				   alert("'A'盘 不能低于'0',请修改");
				   window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false; 
		     }
			  else if(parseFloat(value)>100)
			  {
				  alert("'A'盘 不能高于'"+100+"',请修改"); 
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false;
			  }
			  $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('commissionB')!=-1)
		  {
			  if(parseFloat(value)<0)
			{
				  
				  alert("'B'盘 不能低于'0',请修改");
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				 // $(this)[0].focus();
				  return false;
		       }
			  else if(parseFloat(value)>100)
			  {
				  alert("'A'盘 不能高于'"+100+"',请修改");
				  window.setTimeout(function () { inputObj.focus(); }, 0);
				 // $(this).focus();
				  return false;
			  }
			  $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('commissionC')!=-1)
		  {
			  if(parseFloat(value)<0)
				  {
				  alert("'C'盘 不能低于'0',请修改");
				  window.setTimeout(function () {inputObj.focus(); }, 0);
				 // $(this)[0].focus();
				  return false;
		        }
			  else if(parseFloat(value)>100)
			  {
				  alert("'A'盘 不能高于'"+100+"',请修改");
				  window.setTimeout(function () {inputObj.focus(); }, 0);
				  //$(this).focus();
				  return false;
			  }
			  $(this).removeClass("notok");
		  }
		  else if(inputName.indexOf('bettingQuotas')!=-1)
		  {
			  
		  }
		  else if(inputName.indexOf('itemQuotas')!=-1)
		  {
			  
		  }
		  
		  
	  }	
	
	  suc=true;
	  return suc;
	
}






function checkSubmit()
{
var error=false;
$("table[name='comm'] input[type=text]").each(function() {   
    
	if(!checkInput($(this)))
		{
		error=true;
	return false;
		}
	
});
	if(error)
		return false;
	       var options = { 
		        success:showResponse,	 
		        type:      'post',
		        dataType:  'json',
		        error:function(){
		        	$(".btn2").attr("disabled", false);
					alert("程序異常,重試或聯繫管理員.");
					
				}
		    }; 
	       if(confirm("是否確定 寫入該賬戶的‘退水設置’嗎？")){
	       //add by peter for 提交时灰化按纽
		   $(".btn2").attr("disabled", true);
	       $("#sForm").ajaxSubmit(options);	
	       }
}
function showResponse(jsonData)
{
	alert("退水設置成功保存");
	var forward="${pageContext.request.contextPath}"+$("#jq_forward").val()+"?account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${aType}&searchType=${searchType}&searchValue=${searchValue}";	
	self.location=forward;
}
function batchInput(button,eachObj)
{
	 var CA=button.parent().parent().children("td").children("input[name=CA]").val();
	 var CB=button.parent().parent().children("td").children("input[name=CB]").val();
	 var CC=button.parent().parent().children("td").children("input[name=CC]").val();
	 var BQ=button.parent().parent().children("td").children("input[name=BQ]").val();
	 var IQ=button.parent().parent().children("td").children("input[name=IQ]").val();
	 var canSubmit=true;
	
	  eachObj.each(function(i){
		
		var key=$(this).parent().children(".even2").children("input").val();
		key=key.replace("-","_");//特殊處理北京 1-10 1-5
		var input =$(this).children('input');
		var inputName=input.attr("name");
		
			 
		 if(inputName&&inputName.indexOf('commissionA')!=-1&&CA)
		  {
			 if(jsonpc)
             {
				 
				 var pa=jsonpc[key].commissionA;
				 if(parseFloat(CA)<pa)
				 {
					 input.removeClass("ok");
					 input.addClass("notok");
					 input.val(pa); 
				 }
				 else
				 {
					 input.removeClass("notok");
					 input.addClass("ok");
					 input.val(CA); 
				
				 }
				
             }
			 else
			 {
				 if(parseFloat(CA)<0)
				 {
					 input.removeClass("ok");
					 input.addClass("notok");
					 input.val(0); 
				 }
				 else
				 {
					 input.removeClass("notok");
					 input.addClass("ok");
					 input.val(CA); 
					
				 } 
				 
			 }
			 
		  }
		  else if(inputName&&inputName.indexOf('commissionB')!=-1&&CB)
		  {
			  if(jsonpc)
	             {
					 var pb=jsonpc[key].commissionB;
					 if(parseFloat(CB)<pb)
					 {
						 input.removeClass("ok");
						 input.addClass("notok");
						 input.val(pb); 
					 }
					 else
					 {
						 input.removeClass("notok");
						 input.addClass("ok");
						 input.val(CB); 
						
					 }
					
	             }
				 else
				 {
					 if(parseFloat(CB)<0)
					 {
						 input.removeClass("ok");
						 input.addClass("notok");
						 input.val(0); 
					 }
					 else
					 {
						 input.removeClass("notok");
						 input.addClass("ok");
						 input.val(CB);
						 
					 } 
					  
				 }
		  }
		  else if(inputName&&inputName.indexOf('commissionC')!=-1&&CC)
		  {
			  if(jsonpc)
	             {
					 var pc=jsonpc[key].commissionC;
					 if(parseFloat(CC)<pc)
					 {
						 input.removeClass("ok");
						 input.addClass("notok");
						 input.val(pc); 
					 }
					 else
					 {
						 input.removeClass("notok");
						 input.addClass("ok");
						 input.val(CC); 
						
					 }
					 
	             }
				 else
				 {
					 if(parseFloat(CC)<0)
					 {
						 input.removeClass("ok");
						 input.addClass("notok");
						 input.val(0); 
					 }
					 else
					 {input.removeClass("notok");
						 input.addClass("ok");
						 input.val(CC); 
						
					 } 
					
				 }
		  }
		  
		  else if(inputName&&inputName.indexOf('bettingQuotas')!=-1)
		  {
			  if(jsonpc)
	             {
					 var bq=jsonpc[key].bettingQuotas;
					 if(Math.floor(BQ)>bq)
					 {
						 input.removeClass("ok");
						 input.addClass("notok");
						 input.val(bq); 
					 }
					 else
					 {
						 input.removeClass("notok");
						 input.addClass("ok");
						 input.val(BQ); 
					 }
					
	             }
				 else
				 {
					 input.removeClass("notok");
					 input.addClass("ok");
					 input.val(BQ); 
				 }
		  }
		  else if(inputName&&inputName.indexOf('itemQuotas')!=-1)
		  {
			  if(jsonpc)
	             {
					 var iq=jsonpc[key].itemQuotas;
					 if(Math.floor(IQ)>iq)
					 {
						 input.removeClass("ok");
						 input.addClass("notok"); 
						 input.val(iq); 
					 }
					 else
					 {
						 input.removeClass("notok");
						 input.addClass("ok");
						 input.val(IQ); 
						
					 }
					
	             }
				 else
				 {
					 input.removeClass("notok");
					 input.addClass("ok");
					 input.val(IQ); 
				 }
		  }
	
	});	
	 
}
function hiddenOtherPlate()
{
	var plate=$('#jq_plate').val();
	if(plate)
	{
		if(plate=='A')
		{
		
			$('table[name=comm] tr td:nth-child(3)').hide(); 
			$('table[name=comm] tr td:nth-child(4)').hide(); 
		}
		else if(plate=='B')
		{
			$('table[name=comm] tr td:nth-child(2)').hide(); 
			$('table[name=comm] tr td:nth-child(4)').hide(); 
		}
		else if(plate=='C')
		{
			$('table[name=comm] tr td:nth-child(2)').hide(); 
			$('table[name=comm] tr td:nth-child(3)').hide(); 
		}
		
	}
	
	
}
function getparentCom()
{
var json=null;
var userID=$("#jq_userID").val();
var userType=$("#jq_userType").val();
var ball = '${pageContext.request.contextPath}/user/ajaxparentCommission.do?userID=' +userID+"&userType="+userType;
$.ajax({
	url:ball,
	dataType:"json",
	type:"POST",
	async:false,
	success:function(data)
	{ 
		json=data;
	}
});
	
return 	json;
}

function cancle()
{
	var forward="${pageContext.request.contextPath}"+$("#jq_forward").val()+"?account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${aType}&searchType=${searchType}&searchValue=${searchValue}";	
	self.location=forward;	
}

function myfocus(obj)
{
setTimeout(function(){obj.focus();},0);
}
</script>
</body>
</html>