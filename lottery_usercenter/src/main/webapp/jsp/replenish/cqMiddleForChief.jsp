<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.npc.lottery.periods.entity.CQPeriodsInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<%CQPeriodsInfo cqp=(CQPeriodsInfo)request.getAttribute("RunningPeriods");%>
   
   <tr <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class="noneTr"</s:if><s:else>class="even3Tr"</s:else> onmouseover="changeColorToYL(this,this.className)" onmouseout="changeColorToOld(this)">
       <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
          <b><a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay"
          <s:if test="value.playTypeName.indexOf('0')!=-1 || value.playTypeName.indexOf('1')!=-1  || value.playTypeName.indexOf('2')!=-1
           || value.playTypeName.indexOf('3')!=-1 || value.playTypeName.indexOf('4')!=-1 || value.playTypeName.indexOf('5')!=-1
            || value.playTypeName.indexOf('6')!=-1 || value.playTypeName.indexOf('7')!=-1 || value.playTypeName.indexOf('8')!=-1
             || value.playTypeName.indexOf('9')!=-1">class="blue"</s:if>>
       <s:property value="value.playTypeName" escape="false"/></a></b>
       </td>
       <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
       <tr>
         <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>">
             <%if(odd ==null && isSub){%><%}else{%><a href="javascript:void(0)" name="jia"><img src="${pageContext.request.contextPath}/images/admin/jia.gif" width="19" height="20" /></a><%}%>
         </td>
         <td style="text-align:center!important;">
             <a href="javascript:void(0)" name="odd"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
         </td>
         <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>">
             <%if(odd ==null && isSub){%><%}else{%><a href="javascript:void(0)" name="jian"><img src="${pageContext.request.contextPath}/images/admin/jian.gif" width="19" height="20" /></a><%}%>
         </td>
        
         </tr>
       </table></td>
               <td class="fOp" >
                   <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>" title="查看注單明細">
    <s:if test="value.avLose == 0" >
          	<span class="green">-</span>
    </s:if>
          <s:if test="value.avLose < 0 ">
          	<span class="green"><s:property value="value.money" escape="false"/></span>
    </s:if>
    <s:if test="value.avLose > 0">
         <span class="green"><s:property value="value.money" escape="false"/></span>&nbsp;
         <strong style="color:#7300AA"><s:property value="value.avLose" escape="false"/></strong>
    </s:if></a>
   </td>
   <s:if test="value.overLoseQuatas==1">
    <td class="rOp overMoney"><a href="javascript:void(0)" title="補單">
	    <s:if test="value.loseMoney==0">
	        </a><span>-</span>
	    </s:if><s:elseif test="value.loseMoney>0">
	        <span ><s:property value="value.loseMoney" escape="false"/></span>
	    </s:elseif><s:else>
	        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
	    </s:else></a>
    </td>
   </s:if><s:else>
   	<td class="rOp"><a href="javascript:void(0)" title="補單">
	    <s:if test="value.loseMoney==0">
	        </a><span>-</span>
	    </s:if><s:elseif test="value.loseMoney>0">
	        <span ><s:property value="value.loseMoney" escape="false"/></span>
	    </s:elseif><s:else>
	        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
	    </s:else></a>
    </td>
   </s:else>
      </tr>
