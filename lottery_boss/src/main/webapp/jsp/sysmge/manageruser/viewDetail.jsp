<!-- 
    查看用户信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看用户信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //查看授权
    function authorizView(){
        var userID = document.userInfoForm.ID.value;
        var userType = document.userInfoForm.userType.value;
        var url = "${contextPath}/sysmge/authoriz/authorizUserViewMain.action?userID=" + userID + "&userType=" + userType;
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,600,500);
    }

    //私有授权
    function authorizPrivate(){
        var userID = document.userInfoForm.ID.value;
        var userType = document.userInfoForm.userType.value;
        var url = "${contextPath}/sysmge/authoriz/authorizUserPrivate.action?userID=" + userID + "&userType=" + userType;
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,400,300);
    }

    //角色授权
    function authorizRole(){
        var userID = document.userInfoForm.ID.value;
        var userType = document.userInfoForm.userType.value;
        var url = "${contextPath}/sysmge/authoriz/authorizUserRoleMain.action?userID=" + userID + "&userType=" + userType;
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,600,500);
    }
    
    //编辑用户信息
    function modifyInfo(){
        document.modifyForm.submit();
    }
    
    //删除用户信息
    function deleteUserInfo(){
        if(confirm("确认删除改用户吗？")){
           document.deleteForm.submit();
        }
        return false;
    }
    
    //修改用户密碼
    function modifyPwd(){
        document.modifyPasswordForm.submit();
    }
</script>
<style>
<!-- 本页面上的功能按钮较多，故使用下面两个單独的样式覆盖公共样式 -->
#position{
    color:#000000;
    height:28px;
    line-height:28px;
    width:300px;
    padding-left:4px;
    padding-top:4px;
    padding-bottom:4px;
}
#position_opt {
    position:absolute;
    top:2px;
    right:2px;
    /*border:1px solid #0ff;*/
    width:540px;

}
</style>
</head>

<body>

<s:form name="userInfoForm" action="#">
    <s:hidden name="ID" value="%{#request.user.ID}"/>
    <s:hidden name="userType" value="%{#request.user.userType}"/>
</s:form>
<div id="main">

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	基础用户管理&nbsp;&gt;&nbsp;查看基础用户信息
        </div>
        <div id="position_opt">
            <ul>
                <li>
	                <a href="#" onclick="history.go(-1)">
	                    <img src="${contextPath}/images/bt_back.gif" alt="返回" name="Image3" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_back_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_back.gif')">
	                </a>      
                </li>
            
                <!-- 登录者编辑自己的信息时，不允许删除操作 -->
                <s:if test="false == #request.isSelf">
                <li>
	                <s:form name="deleteForm" action="/sysmge/manageruser/deleteInfo.action">
	                    <s:hidden name="ID" value="%{#request.user.ID}"/>
	                    <a href="#" onclick="deleteUserInfo()">
	                        <img src="${contextPath}/images/bt_del.gif" alt="删除用户信息" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')">
	                    </a>
	                </s:form>
                </li>
                </s:if>
                
                <li>
                    <s:form name="modifyForm" action="/sysmge/manageruser/modify.action">
					    <s:hidden name="ID" value="%{#request.user.ID}"/>
                        <a href="#" onclick="modifyInfo()">
		                    <img src="${contextPath}/images/bt_mod.gif" alt="编辑用户信息" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
		                </a>
                    </s:form>
                </li>
                <li>
                    <s:form name="modifyPasswordForm" action="/sysmge/manageruser/modifyPassword.action">
					    <s:hidden name="ID" value="%{#request.user.ID}"/>
                        <a href="#" onclick="modifyPwd()">
		                    <img src="${contextPath}/images/modify_password.gif" alt="编辑用户密碼" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/modify_password_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/modify_password.gif')">
		                </a>
                    </s:form>
                </li>
                <li>
                    <a href="#" onclick="authorizRole()">
	                   <img src="${contextPath}/images/bt_authoriz_role.gif" alt="角色授权" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_authoriz_role_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_authoriz_role.gif')">
	                </a>
                </li>
                <li>
                    <a href="#" onclick="authorizPrivate()">
                        <img src="${contextPath}/images/bt_authoriz_private.gif" alt="私有授权" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_authoriz_private_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_authoriz_private.gif')">
                    </a>
                </li>
                <li>
                    <a href="#" onclick="authorizView()">
                        <img src="${contextPath}/images/bt_authoriz_view.gif" alt="查看授权" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_authoriz_view_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_authoriz_view.gif')">
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->

    <div id="cnt_list">
    <div id="cnt_lt">
        
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
            	详细信息
        </div>
        
        <div id="cnt_tblcontent">   
        <form name="viewForm" method="post" action="#">
        <table width="100%"  class="cnt_tblbody">
            <s:iterator value="#request.user">
            <s:hidden name="ID"/><!-- 用户ID -->
            
            <tr>
                <td width="18%" class="cnt_td">
         			用户類型：
                </td>
                <td width="32%">
                    <s:property value ="userTypeName"/>
                </td>
                <td width="18%" class="cnt_td">
					登录帳號：
                </td>
                <td width="32%">
                    <s:property value ="account"/>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					中文名：
                </td>
                <td>
                    <s:property value ="chsName"/>
                </td>
                <td class="cnt_td">
					 英文名：
                </td>
                <td>
                    <s:property value ="engName"/>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					办公室电话：
                </td>
                <td>
                    <s:property value ="officePhone"/>
                </td>
                <td class="cnt_td">
					移动电话：
                </td>
                <td>
                    <s:property value ="mobilePhone"/>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
					传真：
                </td>
                <td>
                    <s:property value ="fax"/>
                </td>
                <td class="cnt_td">
					Email：
                </td>
                <td>
                    <s:property value ="emailAddr"/>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					狀態：
                </td>
                <td>
                    <s:property value ="flagName" escape="false"/>
                </td>
                <td class="cnt_td">
					創建时间：
                </td>
                <td>
                    <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					最后登录时间：
                </td>
                <td>
                    <s:date name="loginDate" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="cnt_td">
			                    最后登录IP：
                </td>
                <td>
                    <s:property value ="loginIp"/>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
					备注：
                </td>
                <td colspan="3">
                    <s:property value ="comments"/>
                </td>
            </tr>
            </s:iterator>
        </table>
        </form>
        </div>
        
    </div>
    </div>

</div>
    
</body>
</html>