<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<div class="content">
	<s:form name="sForm" action="/boss/shopRegister.action">
		<div class="main">
			<!-- <div class="top_content"><select name="" class="red mr10">
	      <option>廣東快樂十分</option>
	      <option>重慶時時彩</option>
	    </select></div> -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king">
				<tbody>
					<tr>
					    <th>登录狀態</th>
						<th width="5%">序號</th>
						<th width="8%">帳號</th>
						<th width="5%">用戶名</th>
						<th width="5%">分公司</th>
						<th width="15%">信用額度/可用信用額度</th>
						<th width="8%">分公司占成</th>
						<th width="8%">盘口</th>
						<th width="8%">狀態</th>
						<th width="10%">注册时间</th>
						<th width="15%">操作</th>
					</tr>
					<!--  style="background-color:#f6f0da" -->

					<% int count = 1; %>
					<s:iterator value="#request.page.result" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'"
							onmouseout="this.style.backgroundColor=''">
							<td>
								<s:if test="inLine==true">
								<img src="${pageContext.request.contextPath}/images/online.gif"/>
							  </s:if> <s:if test="inLine==false">
							   <img  src="${pageContext.request.contextPath}/images/offline.gif"/>
							  </s:if>
							</td>
							<td><%=count++%></td>
							<td><a href="#"/><s:property value="account" escape="false" /></a></td>
							<td><s:property value="chsName" escape="false" /></td>
							<td><a href="#"><s:property value="managerStaff.account" escape="false" /></a></td>
							<td><s:property value="totalCreditLine" escape="false" />/<s:property
									value="availableCreditLine" escape="false" /></td>
							<td><s:property value="rate" escape="false" />%</td>
							<td><s:property value="plate" escape="false" /></td>
							<td><s:if test="flag==0">
					     	 	有效
					      	</s:if> 
					      	<s:if test="flag==1">
									<strong class="blue">禁用</strong>
								</s:if> 
								<s:if test="flag==2">
									<strong class="red"> 冻结</strong>
								</s:if></td>
							<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td><a class="btn_4"
								href="${pageContext.request.contextPath}/user/updateFindByMember.action?account=<s:property value="account" escape="false"/>">修改</a></td>
						</tr>
					</s:iterator>

				</tbody>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				class="page">
				<tbody>
					<tr>
						<td><s:if test="#request.page.totalCount>0">
								<lottery:paginate
									url="${pageContext.request.contextPath}/user/queryBranchMember.action"
									param="" />
							</s:if></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--中间内容结束-->
	</s:form>


</html>
