<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
	补货明细信息页面
-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>

<style>
	.alink{
   		color: #333333;
   		text-decoration:underline
   	}
</style>

</head>

<body>

<div class="content">
	<s:form name="sForm" action="#">
		<div class="main">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king2">
				<tbody>
					<tr>
						<th colspan="14" >
							<s:property value="#request.reportInfo"/>
						</th>
					</tr>
					<tr>
						<th width="21%" nowrap>注單號碼/時間</th>
						<th width="21%" nowrap>下注類型</th>
						<th width="8%" nowrap>盤面</th>
						<th width="21%" nowrap>下注明細</th>
						<th width="7%" nowrap>補貨金額</th>
						<th width="7%" nowrap>補貨輸贏</th>
						<th width="7%" nowrap>退水</th>
						<th width="8%" nowrap>退水後結果</th>
					</tr>
					<!--  style="background-color:#f6f0da" -->

					<!-- 判断是否存在对应的数据 -->
		            <s:if test="null == #request.replenishList || 0 == #request.replenishList.size()">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="left" colspan='15'>
		                        <font color='#FF0000'>未查詢到满足条件的数据！</font>
		                    </td>
		                </tr>
		            </s:if>
		            
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.replenishList" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
						<td align="center">
							<s:property value="orderNo"/> #<br/>
							<s:date name="bettingDate" format="MM-dd HH:mm E"/>
						</td>
						<td align="center" nowrap>
							<s:property value="playTypeName"/> <br/>
							<span class="green"><s:property value="periodsNum"/>期</span>
						</td>
						<td align="center" nowrap>
							<s:property value="plate" />盤
						</td>
						<td align="center">
							<s:property value="typeCodeNameOdd" escape="false"/>
						</td>
						<td align="center" nowrap>
							<s:property value="money" escape="false"/>
						</td>
						<td align="center" nowrap>
							<s:property value="winAmountResult" />
						</td>
						<td align="center" nowrap>
							<s:property value="backWater" />
						</td>
						<td align="center">
							<s:property value="backWaterResult" />
						</td>
					</tr>
					</s:iterator>
					
					<!-- 合计数据 -->
		            <s:if test="null != #request.totalEntity">
		            	<s:iterator value ="#request.totalEntity">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="right" colspan="2">
								<s:property value="userName" />
							</td>
							<td align="center" nowrap>
								<s:property value="turnover" escape="false"/>
							</td>
							<td align="center" nowrap>
								<s:property value="amount" />
							</td>
							<td align="center">
								<s:property value="validAmount" />
							</td>
							<td align="center" nowrap>
								<s:property value="memberAmount" escape="false"/>
							</td>
							<td align="center" nowrap>
								<s:property value="memberBackWater" />
							</td>
							<td align="center" nowrap>
								<s:property value="memberBackWaterResult" />
							</td>
							<td align="center">
								<s:property value="subordinateAmount" />
							</td>
							<td align="center" nowrap>
								<s:property value="realResult" />
							</td>
							<td align="center" nowrap>
								<s:property value="winBackWater" escape="false"/>
							</td>
							<td align="center">
								<s:property value="winBackWaterResult" />
							</td>
							<td align="center" nowrap>
								<s:property value="paySuperior" escape="false"/>
							</td>
		                </tr>
		                </s:iterator>
		            </s:if>

				</tbody>
			</table>
		</div>
	</s:form>
	</div>
	</body>
</html>
