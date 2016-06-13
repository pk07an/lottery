<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.npc.lottery.periods.entity.JSSBPeriodsInfo" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
   
        <tr <s:if test='#request.RunningPeriods.state=="2" && value.fpState=="0"'> class="noneTr"</s:if><s:else>class="even3Tr"</s:else> onmouseover="changeColorToYL(this,this.className)" onmouseout="changeColorToOld(this)">
            <td <s:if test='value.fpState=="1"'> class="even3"</s:if><s:else>class="even"</s:else>>
               <b><a id="<s:property value="value.playFinalType" escape="false"/>" href="javascript:void(0)" name="notPlay"><s:property value="value.playTypeName" escape="false"/></a></b>
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
				    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="ott">
				    <s:set value="value.loseMoney" var="loseMoney" scope="request"/>
				    <s:set value="value.commissionMoney" var="commissionMoney" scope="request"/>
					    <%
					    for(int i=1;i<=3;i++){ 
					         BigDecimal loseMoneyFrm  = (BigDecimal)request.getAttribute("loseMoney");
					         BigDecimal commissionMoney  = (BigDecimal)request.getAttribute("commissionMoney");
					         if(loseMoneyFrm.compareTo(BigDecimal.ZERO)==-1){
					        	 loseMoneyFrm = loseMoneyFrm.multiply(BigDecimal.valueOf(i));
					        	 if(i==2){
					        		 loseMoneyFrm = loseMoneyFrm.add(commissionMoney);
					        	 }else if(i==3){
					        		 loseMoneyFrm = loseMoneyFrm.add(commissionMoney.multiply(BigDecimal.valueOf(2)));
					        	 }
					             	 
					         }
					         request.setAttribute("loseMoneyFrm", loseMoneyFrm.setScale(0,BigDecimal.ROUND_HALF_UP));
					         %>
						    <tr>
		       				 <td width="23%" height="16"><img src="${pageContext.request.contextPath}/images/admin/<%=i%>T.jpg" width="29" height="16" /></td>
							    <s:if test="value.overLoseQuatas==1">
								    <td class="rOpS overMoney t_c"><a href="javascript:void(0)" title="補單">
									    <s:if test="value.loseMoney==0">
									        </a><span>-</span>
									    </s:if><s:elseif test="value.loseMoney>0">
									        <span >${loseMoneyFrm}</span>
									    </s:elseif><s:else>
									        <span class="red">${loseMoneyFrm}</span>
									    </s:else></a>
								    </td>
							    </s:if><s:else>
							    	<td class="rOpS t_c"><a href="javascript:void(0)" title="補單">
									    <s:if test="value.loseMoney==0">
									        </a><span>-</span>
									    </s:if><s:elseif test="value.loseMoney>0">
									        <span >${loseMoneyFrm}</span>
									    </s:elseif><s:else>
									        <span class="red">${loseMoneyFrm}</span>
									    </s:else></a>
								    </td>
							    </s:else>
							 </tr>
						 <%} %>
				    </table></td>
           </tr>
