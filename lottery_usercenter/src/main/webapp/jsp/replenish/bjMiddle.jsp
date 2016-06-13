<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.npc.lottery.periods.entity.BJSCPeriodsInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
BJSCPeriodsInfo cqp=(BJSCPeriodsInfo)request.getAttribute("RunningPeriods");%>
   
        <tr <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class="noneTr"</s:if><s:else>class="even3Tr"</s:else> onmouseover="changeColorToYL(this,this.className)" onmouseout="changeColorToOld(this)">
            <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
               <a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay">
	               <strong><s:property value="value.finalPlayTypeName" escape="false"/></strong></a>
            </td>
            <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" class="none_border" >
		          <tr>
		            <td width="19" align="left" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		            <td style="text-align:center!important;">
		                <a href="javascript:void(0)"><span class="blue"><s:property value="value.realOdds" escape="false"/></span></a>
		            </td>
		            <td width="21" style="text-align:right!important;" id="<s:property value="value.playFinalType" escape="false"/>">
		                <a href="javascript:void(0)"></a>
		            </td>
		           
		            </tr>
		          </table></td>
                    <td class="fOp" >
                        <a onclick="return openDetail(this)" href="${pageContext.request.contextPath}/replenish/replenishDetail.action?typeCode=<s:property value="value.playFinalType" escape="false"/>&rs=<s:property value="searchType" escape="false"/>" title="查看注單明細">
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
				    <td class="rOp"><a href="javascript:void(0)" title="補單">
					    <s:if test="value.loseMoney==0">
					        </a><span>-</span>
					    </s:if><s:elseif test="value.loseMoney>0">
					        <span ><s:property value="value.loseMoney" escape="false"/></span>
					    </s:elseif><s:else>
					        <span class="red"><s:property value="value.loseMoney" escape="false"/></span>
					    </s:else></a>
				    </td>
           </tr>