<!--
    列表可供选择的角色信息
-->
<%@ page language="java" pageEncoding="UTF-8"%><!-- 页面编碼 UTF-8，所有页面均如此设置 -->
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
<title>列表可供选择的角色信息</title>
<%
    //TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
    String thisPageUrl = request.getContextPath() + "/sysmge/authoriz/userRoleSelectList.action";//此列表页面的请求URL
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
	            <td width='10%' class='tab-bag' nowrap="nowrap">
					选择
	            </td>
	            <td width='20%' class='tab-bag' nowrap="nowrap">
					角色代碼
	            </td>
	            <td width='45%' class='tab-bag' nowrap="nowrap">
					角色名稱
	            </td>
	            <td width='25%' class='tab-bag' nowrap="nowrap">
					角色等级
	            </td>
	        </tr>

            <!-- 判断是否存在对应的数据 -->
            <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
                <tr bgcolor='#FFFFFF' class='list-tr1'>
                    <td class='align-l' colspan='10'>
                        <font color='#FF0000'>未查詢到满足条件的数据！</font>
                    </td>
                </tr>
            </s:if>
            
            <!-- 迭代显示 List 集合中存储的数据记录 -->
            <s:iterator value="#request.resultList" status="st">
            <!-- 行间隔色显示和当前行反白显示，固定写法 -->
            <tr bgcolor='#FFFFFF' class='list-tr1' onmouseover='listSelect(this)' onmouseout='listUnSelect(this)'>
	            <td class='align-c'>
	                <s:if test="isUserAuthoriz">
	                <input type="checkbox" name="roleID" value="<s:property value='ID'/><%=Roles.SELECT_ROLEID_ROLENAME_SPLIT%><s:property value='roleName'/>" style="border: 0" checked="checked"/>
	                </s:if>
	                <s:else>
	                <input type="checkbox" name="roleID" value="<s:property value='ID'/><%=Roles.SELECT_ROLEID_ROLENAME_SPLIT%><s:property value='roleName'/>" style="border: 0"/>
	                </s:else>
	            </td>
	            <td class='align-c' nowrap="nowrap">
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
            
            <!-- 分页标签，固定写法 Begin -->
            <tr align="right" bgcolor="#FFFFFF">
            <td colspan="11" class="tab-head">
                <table width="40%" border="0" cellspacing="3" cellpadding="0">
                <tr>
                    <td align="right" valign="bottom"  nowrap>
                        <page:pages_roller sumPages='${sumPages}'> 
                          <a href="<%=Tool.toServletStr(firsturl,thisPageUrl)%>"><img src="${contextPath}/images/page-first.gif" alt="首页" border="0"/></a>
                          <a href="<%=Tool.toServletStr(prevurl,thisPageUrl)%>"><img src="${contextPath}/images/page-previous.gif" alt="上一页" border="0"/></a>
                          <a href="<%=Tool.toServletStr(nexturl,thisPageUrl)%>"><img src="${contextPath}/images/page-next.gif" alt="下一页" border="0"/></a>
                          <a href="<%=Tool.toServletStr(lasturl,thisPageUrl)%>"><img src="${contextPath}/images/page-last.gif" alt="末页" border="0"/></a>
                        </page:pages_roller>
                    </td> 
                    
                    <td align="right" valign="bottom" nowrap>
                        第 <font color="#003399">${pageCurrentNo}</font> 页 
                        共 <font color="#003399">${sumPages}</font> 页

                        <a href="#" onclick="return checkPageGo('roleListForm','${thisPageUrl}?action=list')"><img src="${contextPath}/images/page-go.gif" alt="跳转到指定页" border="0" align="absmiddle"/></a><input type="text" name="pageGoNumInput" value="" maxlength="4" size="3" class="goInput" onfocus='this.select()'>&nbsp;
                        记录總数：<font color="#003399">${recordAmount}</font>
                        <input type="hidden" name="originSumPages" value="${sumPages}"> 
                    </td>
                </tr>
                </table>
            </td>
            </tr>
            <!-- 分页标签，固定写法 End -->
            
        </table>
        </div>
    </div>
    </div>
    <!-- 列表信息栏 end -->
    
    </s:form>
</div>

</body>
</html>