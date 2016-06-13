<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script>
		function showResponse(jsonData, statusText)  { 
			self.location="${pageContext.request.contextPath}/user/queryAgentStaff.action?searchUserStatus=${searchUserStatus}&pageNo=${page.pageNo}&account=${searchAccount}&parentAccount=${parentAccount}&type=${type}&searchType=${searchType}&searchValue=${searchValue}";

		} 
		
		</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<div class="content">
	<s:form name="sForm" action="/user/queryAgentStaff.action?account=%{searchAccount}&parentAccount=%{parentAccount}&type=%{type}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">代理管理</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                <td width="56"><img src="${pageContext.request.contextPath}/images/admin/fanhui.gif" width="13" height="12" /> <a class="red" href="javascript:history.go(-1);"><strong>返回</strong></a></td>
			                <td width="102">筛选： 
			                <s:select name="searchUserStatus" list="#{'0':' 启用','2':' 冻结','1':'停用'}" onchange="this.form.submit();" value="#request.searchUserStatus"></s:select>
			                  <%-- <select name="searchUserStatus" onchange="this.form.submit();">
			                    <option value="0">启用</option>
			                    <option value="2">冻结</option>
			                    <option value="1">停用</option>
			                   </select> --%></td>
			                <td width="120" align="right">搜索：
			                 <s:select name="searchType" list="#{'account':' 账号：','chName':' 名称：'}" value="#request.searchType"></s:select>
			                  <%-- <select name="searchType">
			                    <option value="account">账号：</option>
			                    <option value="chName">名称：</option>
			                  </select> --%></td>
			                <td width="139" align="left"><input name="searchValue" value="" maxlength="16" class="b_g" style="width:92px;"/>
			                  <input type="submit" name="Submit" value="查找" />                 </td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="129"><img src="${pageContext.request.contextPath}/images/admin/newadd.gif" width="14" height="13" /> <a href="${pageContext.request.contextPath}/user/saveAgentStaff.action?searchAccount=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}">新增代理</a></td>
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
       <colgroup>
<col width="3%" />
<col width="8%" />
<col width="4%" />
<col width="8%" />
<col width="5%" />
<col width="8%" />
<col width="3%" />
<col width="9%" />
<col width="10%" />
<col width="10%" />
<col width="19%" />
<col width="3%" />
<col width="3%" />
</colgroup>
				<tbody>
					<tr>
					   <th>在线</th>
						<th>上级總代理</th>
						<th>占成</th>
						<th>代理</th>
						<th>限占</th>
						<th>名称</th>
						<th>会员</th>
						<th>信用额度</th>
						<th>可用余额</th>
						<th>新增日期</th>
						<th>功能</th>
						<th>补货</th>
						<th>状态</th>
					</tr>
					<!--  style="background-color:#f6f0da" -->

					<% int count = 1; %>
					<s:iterator value="#request.page.result" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'"
							onmouseout="this.style.backgroundColor=''">
							<td>
								<s:if test="inLine==true">
								<img src="${pageContext.request.contextPath}/images/admin/dengpao.gif"/>
							  </s:if> <s:if test="inLine==false">
							   <img  src="${pageContext.request.contextPath}/images/admin/yuandian.gif"/>
							  </s:if>
							</td>
							<td><a href="#"><s:property value="genAgentStaffExt.account" escape="false" /></a></td>
							<td><a href="#"><s:property value="genAgentRate" escape="false" />%</a></td>
						
							<td class="TD_R"><a href="#"/><s:property value="account" escape="false" /></a></td>
							
							<td>
								<s:if test="rateRestrict==0">
								...
							  </s:if> <s:if test="rateRestrict==1">
							   <s:property value="belowRateLimit" escape="false" />%
							  </s:if>
							</td>
							
							
							<td><s:property value="chsName" escape="false" /></td>
							
							<s:action name="userTree" executeResult="true"  flush="false">
							   <s:param name="account"><s:property value="account" escape="false" /></s:param>
							   <s:param name="userType">6</s:param>
							</s:action>
							<td class="t_right"><s:property value="totalCreditLine" escape="false" /></td>
							
							<td class="t_right"><s:property value="availableCreditLine" escape="false" /></td>
							<td><s:date name="createDate" format="yyyy-MM-dd"/></td>
							
									<td>
						<img  src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
						  <a class="btn_4" href="${pageContext.request.contextPath}/user/queryAgentcommission.action?qUserID=<s:property value="encodeId" escape="false"/>&searchAccount=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}" target="_self">退水</a>
						 <img  src="${pageContext.request.contextPath}/images/admin/xiugai.gif"/>
							    <a class="btn_4" href="${pageContext.request.contextPath}/user/updateFindByAgent.action?account=<s:property value="account" escape="false"/>&searchAccount=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}">修改</a>
							   <img  src="${pageContext.request.contextPath}/images/admin/rizhi.gif"/>
								<a class="btn_4" href="${pageContext.request.contextPath}/admin/queryLoginLog.action?qUserID=<s:property value="encodeId" escape="false"/>" target="_self">日志</a>
								
								<img  src="${pageContext.request.contextPath}/images/admin/jilu.gif"/>
								<a class="btn_4" href="${pageContext.request.contextPath}/replenishauto/autoReplenishLog.action?qUserID=<s:property value="encodeId" escape="false"/>">記錄</a>
						</td>
							
							
							
							<td>
							<s:if test="replenishment==0"> <img  src="${pageContext.request.contextPath}/images/admin/buhuo.gif"/></s:if> 
							<s:if test="replenishment==1"><img  src="${pageContext.request.contextPath}/images/admin/zongzhang.gif"/></s:if>
							</td>
									<td>
								<s:if test="flag==0"> <a href="##" name="userStatus" data="<s:property value="account" escape="false" />|0|6"><input type="button" value="啟用"/> </a></s:if> 
				     	    <s:if test="flag==1">
							<strong ><a href="##" name="userStatus" data="<s:property value="account" escape="false" />|1|6"><input type="button" value="停用" class="red"/></a></strong>
							</s:if> 
							<s:if test="flag==2">
									<strong ><a href="##" name="userStatus" data="<s:property value="account" escape="false" />|2|6"> <input type="button" value="凍結" class="blue"/></a></strong>
							</s:if>
							</td>
							
						</tr>
					</s:iterator>

				</tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
			<%-- <table border="0" cellspacing="0" cellpadding="0" width="100%"
				class="page">
				<tbody>
					<tr>
						<td><s:if test="#request.page.totalCount>0">
								<lottery:paginate
									url="${pageContext.request.contextPath}/user/queryAgentStaff.action"
									param="" />
							</s:if></td>
					</tr>
				</tbody>
			</table> --%>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		        <lottery:paginate
									url="${pageContext.request.contextPath}/user/queryAgentStaff.action"
									param="searchUserStatus=${searchUserStatus}&account=${searchAccount}&parentAccount=${parentAccount}&type=${type}"  recordType="個代理帳號"/>
		        
		     <%--    <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<colgroup>
		          <col width="40%" />
				  <col width="20%" />
				  <col width="40%" />
		        </colgroup>
		          <tr>
		            <td align="left">共 3445 个分公司账号 </td>
		            <td align="center">共 3 页 </td>
		            <td align="right">前一页 [ <strong class="blue">1</strong> <a href="#">2</a> ] <a href="#">后一页</a> </td>
		          </tr>
		        </table> --%>
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
	</s:form>

<jsp:include page="/jsp/user/changeUserStatusPop.jsp" />
</html>
