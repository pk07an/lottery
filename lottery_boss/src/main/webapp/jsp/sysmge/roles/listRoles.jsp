<!--
    角色信息列表
-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<%@ page import="
    com.npc.lottery.util.Tool
"%>  

<html>
<head>
<title>角色信息列表</title>
<%
    //TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
    String thisPageUrl = request.getContextPath() + "/sysmge/roles/list.action";//此列表页面的请求URL
    request.setAttribute("thisPageUrl", thisPageUrl);
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //调用详细信息页面的js
    function modify(){     
        document.rolesForm.action="${contextPath}/sysmge/roles/modifyRoles.action";
        document.rolesForm.submit;
    }
    function del(){
        document.rolesForm.action="${contextPath}/sysmge/roles/delRoles.action";
        document.rolesForm.submit;
    }
    function add(){
        document.rolesForm.action="${contextPath}/sysmge/roles/addRoles.action";
        document.rolesForm.submit();
    }
    function searchRoles(){
        document.rolesForm.action="${contextPath}/sysmge/roles/searchRoles.action";
        document.rolesForm.submit();
    }   
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
        
    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	角色管理&nbsp;&gt;&nbsp;角色信息列表
        </div>
        <div id="position_opt">
            <ul>
                <li>
                <s:form name="createForm" action="/demo/create.action">
                    <a href="#" onClick="add()"> <img
                       src="${contextPath}/images/bt_add.gif" alt="新增"
                       name="addImage" height="23" border="0" align="absbottom"
                       onmouseover="changeImg(this,'${contextPath}/images/bt_add_over.gif')"
                       onmouseout="changeImg(this,'${contextPath}/images/bt_add.gif')">
                    </a>
                </s:form>
                </li>
            </ul>
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->
    
    <!-- 表單信息 -->
    <s:form name="rolesForm" action="/sysmge/roles/list.action">
    
    <!-- 查詢信息栏 start -->
    <div id="cnt_search2">
    <div id="cnt_sch2">          
        <ul>
        <li>
			角色代碼：<s:textfield name="roleCode" size="10"/>
			角色名稱：<s:textfield name="roleName" size="10"/>
			角色等级：<s:select list="#request.roleLevelList" name="roleLevel" listKey="key" listValue="name"></s:select>
			角色類型：<s:select list="#request.roleTypeList" name="roleType" listKey="key" listValue="name"></s:select>
            
                <a href="#" onClick="searchRoles()">
                    <img src="${contextPath}/images/bt_search.gif" alt="查詢" 
                        height="23" border="0" 
                        onmouseover="changeImg(this,'${contextPath}/images/bt_search_over.gif')" 
                        onmouseout="changeImg(this,'${contextPath}/images/bt_search.gif')" align="absmiddle"/>
                </a>
         </li>
        </ul>           
    </div>
    </div>
    <!-- 查詢信息栏 end -->
   
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
                <td width='20%' class='tab-bag' align="center" nowrap>
                    角色代碼
                </td>
                <td width='45%' class='tab-bag' nowrap>
                    角色名稱
                </td>
                <td width='15%' class='tab-bag' nowrap>
                    角色等级
                </td>
                <td width='15%' class='tab-bag' nowrap>
                    角色類型
                </td>
                <td width='5%' class='tab-bag' nowrap>
                    操作
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
            <tr <s:if test="true == #st.odd">class='list-tr2'</s:if><s:else>class='list-tr1'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
                <td class='align-l' nowrap>
                    <s:property value="roleCode" />
                </td>
                <td class='align-l'>
                    <s:property value="roleName" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="roleLevelName"  />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="roleTypeName" escape="false" />
                </td>
                <td class='align-c' nowrap>
                    <s:url action="viewRoles.action" id="viewUrl">
                        <s:param name="ID" value="ID" />
                    </s:url>
                    <s:a href="%{viewUrl}">
                        <img
                            src="<%=request.getContextPath()%>/images/bt_particular.gif"
                            width="18" height="18" border="0" alt="查看详细信息">
                    </s:a>
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

                        <a href="#" onclick="return checkPageGo('rolesForm','${thisPageUrl}?action=list')"><img src="${contextPath}/images/page-go.gif" alt="跳转到指定页" border="0" align="absmiddle"/></a><input type="text" name="pageGoNumInput" value="" maxlength="4" size="3" class="goInput" onfocus='this.select()'>&nbsp;
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