<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
<div class="content">
	<s:form name="sForm" action="autoReplenishLog">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">資料變更記錄</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229"><a class="btn_4" href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/images/admin/fanhui.gif" />&nbsp;返回</a>
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
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4">
  
				<tbody>
					<tr>
						<th width="3%">ID</th>
						<th width="15%">變更時間 </th>
						<th width="15%">變更類別</th>
						<th width="15%">原始值</th>
						<th width="15%">變更值</th>
						<th width="15%">變更人</th>
						<th width="7%">IP</th>
						<th width="15%">IP歸屬</th>
					</tr>

					<% int count = 1; %>
					<s:iterator value="#request.page.result" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'"
							onmouseout="this.style.backgroundColor=''">
							<td><%=count++ %>
<%-- 							   <img src="${pageContext.request.contextPath}/images/admin/yuandian.gif"/> --%>
							</td>
							
							<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
							<!-- 变更类别 -->
							<td>
								<s:if test="changeType==#request.REPLENISH_AUTO_UPDATE">
									<s:property value="typeName" escape="false" />
									<br/>   
									<s:property value="subTypeName" escape="false" />
									<br/>
									<br/>【自動補貨】
								</s:if>
								<s:elseif test="changeType==#request.COMMISSION_UPDATE">
									<s:property value="typeName" escape="false" />
									<br/>   
									<s:property value="subTypeName" escape="false" />
									<br/>
										<s:if test="changeSubType==#request.COMMISSIONA">
											A盘
										</s:if>
										<s:elseif test="changeSubType==#request.COMMISSIONB">
											B盘
										</s:elseif>
										<s:elseif test="changeSubType==#request.COMMISSIONC">
											C盘
										</s:elseif>
										<s:elseif test="changeSubType==#request.BETTING_QUOTAS">
											單注限額
										</s:elseif>
										<s:elseif test="changeSubType==#request.ITEM_QUOTAS">
											單期限額
										</s:elseif>
									<br/>【用户退水修改】
								</s:elseif>
								<s:elseif test="changeType==#request.USERINFO_UPDATE">
									<br/>
									信用額度
									<br/>   
									<br/>【用户修改】
								</s:elseif>
							</td>
							<!-- 原始值 -->
							<td>
								<s:if test="changeType==#request.REPLENISH_AUTO_UPDATE">
									下注額：<s:property value="moneyOrgin" escape="false" />
									</br>
									<s:if test="stateOrgin==1">【啟用】</s:if>
									<s:else>【禁用】</s:else>
								</s:if>
								<s:else>
									<s:property value="orginalValue" escape="false" />
								</s:else>
							</td>
							<!-- 变更值 -->
							<td>
								<s:if test="changeType==#request.REPLENISH_AUTO_UPDATE">
									下注額：<s:property value="moneyNew" escape="false" />
									</br> 
									<s:if test="stateNew==1">【啟用】</s:if>
									<s:else>【禁用】</s:else>
								</s:if>
								<s:else>
									<s:property value="newValue" escape="false" />
								</s:else>
							</td>
							
							
							<td>
								<s:property value="updateUserName" escape="false" />
							</td>
							<td> ..詢問上級..</td>
							<td>
								<s:property value="ipBelongTo" escape="false" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif">
		     <table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		         <lottery:paginate url="${pageContext.request.contextPath}/replenishauto/autoReplenishLog.action" param="qUserID=${qUserID}"  recordType="條記錄"/>
		        
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table>
		   </td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
	</s:form>
</div>
</html>
