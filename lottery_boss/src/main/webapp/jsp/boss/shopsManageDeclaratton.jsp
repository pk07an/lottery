<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="content">
	<s:form name="sForm" action="">
		<div class="main">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">站內消息管理</td>
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
			                   <%-- <%
								ManagerUser userInfo = (ManagerUser) session
											.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
									boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo
											.getUserType());// 總监
									if (isChief) {
						    	%>  --%>
			                     <img src="${pageContext.request.contextPath}/images/admin/newadd.gif" width="14" height="13" /> 
										<a class="btn_4" href="${pageContext.request.contextPath}/boss/saveFindMessage.action?type==message">新增站內消息</a>
			                    <%-- <%
									}
								%> --%>
			            </td>
			            </tr></table></td>
			        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
			      </tr>
			    </table></td>
			<!--控制表格头部结束-->
				
				<tr>
					<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
								<td align="center">
									<!-- 開始  -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
										<tbody>
											<tr>
												<th width="13%">貼出時間</th>
												<th width="64%">消息詳情</th>
												<th width="6%">分級瀏覽</th>
												<th width="6%">登錄彈窗</th>
												<th width="11%">功能</th>
											</tr>
											<s:iterator value="#request.page.result" status="branch">
												<tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
													<td><s:date name="createDate" format="yyyy-MM-dd HH:mm" /></td>
													<td class="t_left"><s:property value="contentInfo" escape="false" /></td>
													<td><s:property value="toWhoChs" escape="false" /></td>
													<td><s:if test="popupMenus==1">
															<img
																src="${pageContext.request.contextPath}/images/admin/popWindows.gif"
																width="12" height="12" />
														</s:if> <s:else>
															<img
																src="${pageContext.request.contextPath}/images/admin/popWindowsNo.gif"
																width="12" height="12" />
														</s:else></td>
													<td>
														<a class="btn_4" href="${pageContext.request.contextPath}/boss/updateFindMessage.action?shopID=<s:property value="ID" escape="false" />"><img  src="${pageContext.request.contextPath}/images/admin/xiugai.gif"/>修改</a> &nbsp;&nbsp;
														<a class="btn_4" href="${pageContext.request.contextPath}/boss/deleteMessage.action?shopID=<s:property value="ID" escape="false" />"><img  src="${pageContext.request.contextPath}/images/admin/delete.gif"/>删除</a>
													</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
								</td>
								<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
							</tr>
						</table></td>
				</tr>
				<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		          <lottery:paginate url="${pageContext.request.contextPath}/boss/queryAllMessage.action" param="" recordType=" 條公告"/>
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
			</table>
		</div>
		<!--中间内容结束-->
	</s:form>

</div>
</html>
