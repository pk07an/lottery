<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
function setupPriods() {
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/ncPeriodsInfoEnter.action");
	$("#sForm").submit();
}
function delPriods() {
	window.location.href="${pageContext.request.contextPath}/boss/delNCPeriodsInfo.do";
}
</script>
<body>
<div class="content">
<s:form name="sForm" id="sForm">
	<div class="main">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
	  <tbody>
 		    <tr>
				<input type="button" name="btn1" id="button3" value="手动生成农场盤期" onclick="setupPriods()" />
				&nbsp;&nbsp;&nbsp;<input type="button" value="删除农场盤期" onclick="delPriods()" />
			</tr>
	    <tr>
	      <th width="5%">序號</th>
	      <th width="15%">期數</th>
	      <th width="25%">開盤時間</th>
	      <th width="25%">開獎時間</th>
	      <th width="20%">封盤時間</th>
	      <th width="10%">狀態</th>
	    </tr>
	   	 <% int count = 1; %>
	 		 <s:iterator value="#request.page.result" status="branch">
				<tr onmouseover="this.style.backgroundColor='#FFFFA2'"
					onmouseout="this.style.backgroundColor=''">
					<td><%=count++%></td>
					<td><s:property value="periodsNum" escape="false" /></td>
					<td><s:date name="openQuotTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:date name="lotteryTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:date name="stopQuotTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="stateName" escape="false"/></td>
				</tr>
				
			</s:iterator>
	  </tbody>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" class="page">
	        <tbody>
	          <tr>
	            <td>
		            <s:if test="#request.page.totalCount>0">			      
					      <lottery:paginate url="${pageContext.request.contextPath}/boss/queryNCResultPeriods.action" param="periodsType=periodsType"/>
					</s:if>
				</td>
	          </tr>
	        </tbody>
	      </table>
	  </div>
<!--中间内容结束-->
</s:form>
</div>
</body>

</html>
