<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
	      <th width="5%">序號</th>
	      <th width="8%">帳號</th>
	      <th width="5%">用戶名</th>
	      <th width="5%">總监</th>
	      <th width="15%">信用額度/分配余額</th>
	      <th width="8%">分公司</th>
	      <th width="8%">總监</th>
	      <th width="8%">走飞</th>
	      <th width="8%">狀態</th>
	      <th width="10%">注册时间</th>
	      <th width="27%">操作</th>
	    </tr>
	    <!--  style="background-color:#f6f0da" -->
	   
	     <% int count = 1; %>
	        <tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
			      <td><%=count++%></td>
			      <td><s:property value="branchStaffExt.account" escape="false"/></td>
			      <td><s:property value="branchStaffExt.chsName" escape="false"/></td>
			      <td><a  href="#"><s:property value="branchStaffExt.chiefStaffExt.account" escape="false"/></a></td>
			      <td><s:property value="branchStaffExt.totalCreditLine" escape="false"/>/<s:property value="branchStaffExt.availableCreditLine" escape="false"/></td>
			      <td><s:property value="branchStaffExt.companyRate" escape="false"/>%</td>
			      <td><s:property value="branchStaffExt.chiefRate" escape="false"/>%</td>
			      <td>
				      <s:if test="branchStaffExt.replenishment==0">
					     	 允许走飞
					  </s:if>
					   <s:if test="branchStaffExt.replenishment==1">
					     	 禁止走飞
					  </s:if>
				      </td>
			      <td>
				      <s:if test="branchStaffExt.flag==0">
				     	 有效
				      </s:if>
				      <s:if test="branchStaffExt.flag==1" >
			              <strong class="blue">禁用</strong>
			          </s:if>
			          <s:if test="branchStaffExt.flag==2" >
			              <strong class="red"> 已删除</strong>
			          </s:if>
			      </td>
			      <td><s:date name="branchStaffExt.createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			      <td><a class="btn_4" href="">修改</a></td>
			</tr>
	    
	  </tbody>
	</table>
	  </div>
<!--中间内容结束-->
</s:form>
</div>

</html>
