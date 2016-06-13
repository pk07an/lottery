<!--
    用户信息选择（單选）：用户列表
-->
<%@ page language="java" pageEncoding="UTF-8"%><!-- 页面编碼 UTF-8，所有页面均如此设置 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<%@ page import="
    com.npc.lottery.util.Tool,
    com.npc.lottery.common.Constant
"%>  

<html>
<head>
<title>用户信息列表</title>
<%
    //TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
    String thisPageUrl = request.getContextPath() + "/sysmge/user/selectUserList.action";//此列表页面的请求URL
    request.setAttribute("thisPageUrl", thisPageUrl);
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //选择用户
    function selectUser(){
        var result = "";
        
        /*
        var radioList = document.userListForm.userID;
        if(null != radioList){
            if(null != radioList.length){
                alert("存在多个radio");
                for(var i = 0; i < radioList.length; i++){
                    if(radioList[i].checked){
                        result = radioList[i].value;
                        break;
                    }
                }
            }else{
                alert("只有一个radio");
                if(radioList.checked){
                    result = radioList.value;
                }
            }       
        }else{
            alert("不存在radio");
        }
        */
        var userID = document.userListForm.userID.value;
        var userName = document.userListForm.userName.value;
        
        if(null == userID || 0 == userID.length){
            alert("请选择用户！");
            return false;
        }
        
        result = userID + "<%=Constant.SELECT_USERID_USERNAME_SPLIT%>" + userName;
        
        //alert(result);
        window.close();
        window.returnValue = result;
        
        return true;
    }
    
    //点击radio的操作
    function radioClick(obj){
        var result = obj.value;
        if(null == result){
            return false;
        }
        
        var results = result.split("<%=Constant.SELECT_USERID_USERNAME_SPLIT%>");
        document.userListForm.userID.value = results[0];//用户ID
        document.userListForm.userName.value = results[1];//用户名稱
    }
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
     
    <!-- 表單信息 -->
    <s:form name="userListForm" action=""> 
    <s:hidden name="userOrgID"/>
    
    <!-- 查詢信息栏 start -->
    <div id="cnt_search2">
    <div id="cnt_sch2">          
        <ul>
            <li>用户中文名：<input type="text" name="userName" readonly="readonly">
                        <input type="hidden" name="userID"></li>
            <li>
                <img onClick="selectUser()" src="${contextPath}/images/bt_select.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_select_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_select.gif')" style="cursor:hand">
                <img onClick="javacsript:window.close()" src="${contextPath}/images/bt_close.gif" alt="关闭" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_close_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_close.gif')" style="cursor:hand">
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
            用户信息列表
        </div>
    
        <div id="cnt_tblcontent">
        <table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
            
            <tr align='center'>
                <td width='5%' class='tab-bag' nowrap="nowrap">选择</td>
	            <td width='15%' class='tab-bag' nowrap="nowrap">员工工号</td>
	            <td width='25%' class='tab-bag' nowrap="nowrap">中文名</td>
	            <td width='20%' class='tab-bag' nowrap="nowrap">电话</td>
	            <td width='35%' class='tab-bag' nowrap="nowrap">eMail</td>
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
                <td class='align-c'>
	                <input type="radio" name="userID" value="<s:property value='ID'/>&&<s:property value='chsName'/>" onClick="radioClick(this)" style="border: 0"/>
	            </td>
	            <td class='align-c' nowrap="nowrap">
	                <s:property value="userID" />
	            </td>
	            <td class='align-l'>
	                <s:property value="chsName" />
	            </td>
	            <td class='align-c' nowrap="nowrap">
	                <s:property value="officePhone" />
	            </td>
	            <td class='align-l'>
	                <s:property value="emailAddress" />
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