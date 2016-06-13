<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
	股东明细
-->

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>

<style>
	.alink{
   		color: #333333;
   		text-decoration:underline
   	}
</style>
</head>

<body style="padding-left: 5px;padding-right: 5px">

<div class="content" style="overflow-y: yes">
	<s:form name="sForm" action="#">
		<div class="main">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king mt4">
				<tbody>
					<tr>
						<th colspan="14" >
							<s:property value="#request.reportInfo" escape="false"/>
						</th>
					</tr>
					<tr>
						<th width="18%" nowrap>注單號碼/時間</th>
						<th width="17%" nowrap>下注類型</th>
						<th width="7%" nowrap>会员</th>
						<th width="18%" nowrap>下注明細</th>
						<th width="6%" nowrap>會員下注</th>
						<th width="6%" nowrap>會員輸贏</th>
						<th width="7%" nowrap>代理</th>
						<th width="7%" nowrap>总代理</th>
						<th width="7%" nowrap>股东</th>
						<th width="7%" nowrap><font color='#FF0000'>您的結果</font></th>
					</tr>
					
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.page.result" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
						<td align="center">
							<s:property value="orderNo"/> #<br/>
							<s:date name="bettingDate" format="MM-dd HH:mm EE"/>
						</td>
						<td align="center" nowrap>
							<s:property value="playTypeName"/> <br/>
							<span class="green"><s:property value="periodsNum"/>期</span>
						</td>
						<td align="center" nowrap>
						    <s:property value="bettingUserID" />
						    <br/>
							<s:property value="plate" />盤
						</td>
						<td align="center">
							<s:property value="typeCodeNameOdd" escape="false"/>
						</td>
						<td align="center" nowrap>
							<s:property value="moneyTotalDis" escape="false"/>
						</td>
						<td align="center" nowrap>
							<s:property value="winAmountTotalDis" />
						</td>
						<td align="center" nowrap>
							<s:property value="rateAgentName" />
							<br/>
							<!-- 代理下级的退水 -->
							<s:property value="commissionMemberValue2" />
						</td>
						<td align="center" nowrap>
                            <s:property value="rateGenAgentName" />
                            <br/>
                            <!-- 总代理下级的退水 -->
                            <s:property value="commissionAgentValue2" />
                        </td>
                        <td align="center" nowrap>
                            <s:property value="rateStockholderName" />
                            <br/>
                            <!-- 股东下级的退水 -->
                            <s:property value="commissionGenAgentValue2" />
                        </td>
						<td align="center">
                            <s:property value="resultStockholder" />
                            <br/>
                            <s:property value="backWaterResultStockholder" />
                        </td>
					</tr>
					</s:iterator>
					
				</tbody>
			</table>
			<!-- 分页 -->
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
                class="page">
                <tbody>
                    <tr>
                        <td>
                            <s:if test="#request.page.totalCount>0">
                                <lottery:paginate
                                    url="${pageContext.request.contextPath}/statreport/detailedList.action"
                                    param="lotteryType=${lotteryType}&playType=${playType}&bettingUserID=${bettingUserID}&bettingDateStart=${bettingDateStart}&bettingDateEnd=${bettingDateEnd}&detailUserAccount=${detailUserAccount}&parentUserType=${parentUserType}" />
                            </s:if>
                         </td>
                    </tr>
                </tbody>
            </table>
		</div>
	</s:form>
	</div>
	</body>
</html>
