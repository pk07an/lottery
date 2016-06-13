<!--
	登陆信息列表
-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<%@ page import="
    com.npc.lottery.util.Tool,
    com.npc.lottery.common.Constant
"%>  

<html>
<head>
<title>登陆信息列表</title>
<%
    //TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
    String thisPageUrl = request.getContextPath() + "/sysmge/monitor/sessionList.action";;//此列表页面的请求URL
    request.setAttribute("thisPageUrl", thisPageUrl);
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
	//点击查詢按钮操作
	function userSearch(){
	    document.userListForm.submit();
	} 
	
	//点击新增按钮操作
	function createUser(){
	    document.createForm.submit();
	}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
        
    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
           <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	监控信息&nbsp;&gt;&nbsp;登陆信息列表
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->
    
    <!-- 表單信息 -->
    <s:form name="userListForm" action="/sysmge/monitor/sessionList.action"> 
    
    <!-- 查詢信息栏 start -->
    <div id="cnt_search2">
    <div id="cnt_sch2">          
        <ul>
            <li>登录帳號：<s:textfield name="account" cssClass="search_text"/></li>
            <li>用户類型：<s:select list="#request.userTypeList" listKey="key" listValue="name" name="userType" ></s:select></li>
            <li>
                <a href="#" onClick="userSearch()">
                        <img src="${contextPath}/images/bt_search.gif" alt="查詢"
                        height="23" border="0" align="absbottom"
                        onmouseover="changeImg(this,'${contextPath}/images/bt_search_over.gif')"
                        onmouseout="changeImg(this,'${contextPath}/images/bt_search.gif')">
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
             	登陆信息列表
        </div>
    
        <div id="cnt_tblcontent">
        <table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
            
            <tr align='center'>
             	<td width='17%' class='tab-bag' nowrap>
   					 SESSION_ID
                </td>
                <td width='14%' class='tab-bag' nowrap>
					登陆帳號
                </td>
                <td width='20%' class='tab-bag' nowrap>
                   	 中文名
                </td>
                <td width='16%' class='tab-bag' nowrap>
           			安全碼
                </td>
                <td width='16%' class='tab-bag' nowrap>
      				用户類型
                </td>
                <td width='12%' class='tab-bag' nowrap>
                   	 登录IP
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
                <td class='align-l'>
                    <s:property value="sessionID"/>
                </td>
                <td class='align-l'>
                    <s:property value="account" />
                </td>
                <td class='align-l'>
                    <s:property value="userName" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="safeCode" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="userTypeName" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="IP" />
                </td>
                <td class='align-c' nowrap>
                    <s:url action="view.action" id="urlID">
                        <s:param name="ID" value="ID" />
                    </s:url>
                    <s:a href="%{urlID}">
                        <img
                            src="${contextPath}/images/bt_particular.gif"
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

                        <a href="#" onclick="return checkPageGo('userListForm','${thisPageUrl}?action=list')"><img src="${contextPath}/images/page-go.gif" alt="跳转到指定页" border="0" align="absmiddle"/></a><input type="text" name="pageGoNumInput" value="" maxlength="4" size="3" class="goInput" onfocus='this.select()'>&nbsp;
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