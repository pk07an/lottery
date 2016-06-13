<!--
    列表用户所授予的角色信息
-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<%@ page import="
    com.npc.lottery.util.Tool,
    com.npc.lottery.sysmge.entity.Roles,
    com.npc.lottery.common.Constant
"%>

<html>
<head>
<title>列表用户所授予的角色信息</title>
<%
    //TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
    String thisPageUrl = request.getContextPath() + "/sysmge/roles/list.action";//此列表页面的请求URL
    request.setAttribute("thisPageUrl", thisPageUrl);
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
     
    <!-- 表單信息 -->
    <s:form name="roleListForm" action="#">
    
    <!-- 列表信息栏 start -->
    <div id="cnt_list">
    <div id="cnt_lt">
    
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
            角色信息列表
        </div>
    
        <div id="cnt_tblcontent">
        <table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
	        <tr align='center'>
	            <td width='25%' class='tab-bag' nowrap="nowrap">角色代碼</td>
	            <td width='50%' class='tab-bag' nowrap="nowrap">角色名稱</td>
	            <td width='25%' class='tab-bag' nowrap="nowrap">角色等级</td>
	        </tr>
	        
	
	        <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
	        <tr bgcolor='#FFFFFF' class='list-tr1'>
	            <td class='align-l' colspan='10'>
	                <font color='#FF0000'>该用户目前尚无相关的角色授权信息！</font>
	            </td>
	        </tr>
	        </s:if>
	        
	        <s:iterator value="#request.resultList">
	        <tr bgcolor='#FFFFFF' class='list-tr1' onmouseover='listSelect(this)' onmouseout='listUnSelect(this)'>
	            <td class='align-l' nowrap="nowrap">
	                <s:property value="roleCode" />
	            </td>
	            <td class='align-l'>
	                <s:property value="roleName" />
	            </td>
	            <td class='align-c' nowrap>
                    <s:property value="roleLevelName"  />
                </td>
	        </tr>
	        </s:iterator>
        </table>
        </div>
    </div>
    </div>
    <!-- 列表信息栏 end -->
    
    </s:form>
</div>

</body>
</html>