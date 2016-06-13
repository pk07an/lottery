<!-- 
    查看角色信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看权限信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
<!-- 
    //修改
    function modifyRolesFunc(){
        document.modifyForm.submit();
    }
    
    //删除
    function delRole(){
    	if(confirm("确认删除该角色吗？"))
        {
    		document.delForm.submit();
        }
    }
    
    //授权
    function authoriz(){
        var url = "${contextPath}/sysmge/authoriz/authorizRoleMain.action?roleID=<%=request.getParameter("ID")%>";
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,400,300);
        //alert(ret);
    }
    
    //查看授权
    function authorizView(){
        var url = "${contextPath}/sysmge/authoriz/authorizRoleView.action?roleID=<%=request.getParameter("ID")%>";
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,400,300);
    }
-->
</script>

</head>

<body>

<div id="main">

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	角色管理&nbsp;&gt;&nbsp;查看角色信息
        </div>
        <div id="position_opt">
            <ul>
                <li>
                <a href="javascript:void history.go(-1)">
                    <img src="${contextPath}/images/bt_back.gif" alt="返回" 
                        name="Image3" height="23" border="0" 
                        onmouseover="changeImg(this,'${contextPath}/images/bt_back_over.gif')" 
                        onmouseout="changeImg(this,'${contextPath}/images/bt_back.gif')" />
                </a> 
                </li>
                
                <s:if test="#request.roles.delFlag">
                <li>
                <s:form name="delForm" action="/sysmge/roles/del.action">
                    <s:hidden name="ID" value="%{#request.roles.ID}"/>
                    <a href="#" onclick="delRole()">
                        <img
                             src="${contextPath}/images/bt_del.gif" alt="删除"
                             name="Image2" height="23" border="0" align="absbottom"
                             onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')"
                             onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')">
                    </a>
                </s:form>
                </li>
                
                <li>
                <s:form name="modifyForm" action="/sysmge/roles/modifyRoles.action">
                    <s:hidden name="ID" value="%{#request.roles.ID}"/>
                    <a href="#" onclick="modifyRolesFunc()">
                        <img src="${contextPath}/images/bt_mod.gif" alt="编辑" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
                    </a>
                </s:form>
                </li>
                </s:if>
            
                <li>
                    <a href="#" onclick="authoriz()">
                        <img src="${contextPath}/images/bt_authoriz.gif" alt="授权" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_authoriz_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_authoriz.gif')">
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
        <s:iterator value ="#request.roles">
            
            <tr> 
                <td class="cnt_tdWth_180">角色代碼：</td>
                <td>
                    <s:property value="roleCode"/>
                </td>
            </tr>
                
            <tr> 
                <td class="cnt_tdWth_180">角色名稱：</td>
                <td>
                    <s:property value="roleName"/>
                </td>
            </tr>
                
            <tr> 
                <td class="cnt_tdWth_180">角色等级：</td>
                <td>
                    <s:property value="roleLevelName"/>
                </td>
            </tr>
                
            <tr>
                <td class="cnt_tdWth_180">角色類型：</td>
                <td>
                    <s:property value="roleTypeName" escape="false"/>
                </td>
            </tr>
                
            <tr> 
                <td class="cnt_tdWth_180">备注：</td>
                <td>
                    <s:property value ="remark"/>
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