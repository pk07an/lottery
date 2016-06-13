<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
 
<div class="content">
<div class="main"> 
  <s:form id="sform" method="post" action="queryBossLog">
  <div class="top_content">
	<table width="100%" border="0">
	   <colgroup>
	     <col width="40%"></col>
	     <col width="60%"></col>
	  </colgroup>
	  <tr>
	  	<td> <strong><font class="black">異常日誌</font></strong></td>
	    <td class="t_center">
		  	日誌信息： <s:textfield id="logMessage" name="logMessage"> </s:textfield>&nbsp; &nbsp; &nbsp;<input type="submit" value="提 交"/>
		 </td>
	  </tr>
	</table>

   </div>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <tbody>
    <tr>
      <th width="10%">ID</th> 
      <th width="25%">日誌信息</th>
      <th width="25">狀態</th>
      <th width="20">級別</th>
      <th width="10">創建時間</th>
      <th width="10">操作</th>
    </tr>
  
   	<% int count = 1; %>
		 <s:iterator value="#request.page.result" status="branch">
			<tr onmouseover="this.style.backgroundColor='#FFFFA2'"
			onmouseout="this.style.backgroundColor=''">
				<td><%=count++%></td>
				<td><s:property value="logMessage" escape="false" /></td>
				<td><s:property value="logState" escape="false" /></td>
				<td><s:property value="logLevel" escape="false" /></td>
				<td><s:property value="createDate" escape="false" /></td>
				<td><a href="#">OK.</a></td>
			</tr>
		</s:iterator>
   	</tbody>
    </table>
    
	  <s:if test="#request.page.totalCount>0">
		<s:if test="#request.page.totalCount>0">			      
			       <lottery:paginate url="${pageContext.request.contextPath}/boss/queryBossLog.action" param="logMessage=${logMessage}"/>
			</s:if>
	</s:if>
	</s:form>
</div>

</div>
