<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">

<div class="content">
<s:form name="sForm" action="">
	<div class="main">
	  <!-- <div class="top_content"><select name="" class="red mr10">
	      <option>廣東快樂十分</option>
	      <option>重慶時時彩</option>
	    </select></div> -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
	  <tbody>
		  <tr>
		  	 <%-- <a class="btn_4" href="${pageContext.request.contextPath}/user/userChiefStaffRegister.action" >創建總监</a> --%>
		  </tr>
	    <tr>
	      <th width="5%">序號</th>
	      <th width="8%">用户名</th>
	      <th width="10%">姓名</th>
	      <th width="5%">狀態</th>
	      <th width="15%">創建時間</th>
	      <th width="15%">操作</th>
	    </tr>
	    <!--  style="background-color:#f6f0da" -->
	   
	     <% int count = 1; %>
	    <s:iterator value="#request.listChiefSE">
	        <tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
			      <td><%=count++%></td>
			      <td><s:property value="account" escape="false"/></td>
			      <td><s:property value="chsName" escape="false"/></td>
			      <td>
			          <s:if test="flag==0">
			                                                       有效
			          </s:if>
			          <s:if test="flag==1">
			                                                      禁用
			          </s:if>
			          <s:if test="flag==2">
			                                                      删除
			          </s:if>			         
			      </td>
			      <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			      <td><a class="btn_4" href="">修改</a></td>
			</tr>
	    </s:iterator>
	    
	  </tbody>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" class="page">
	        <tbody>
	          <tr>
	            <td>
		            <s:if test="#request.page.totalCount>0">			      
					      <lottery:paginate url="${pageContext.request.contextPath}/user/userManager.action" param=""/>
					</s:if>
				</td>
	          </tr>
	        </tbody>
	      </table>
	  </div>
<!--中间内容结束-->
</s:form>

</div>

</html>
