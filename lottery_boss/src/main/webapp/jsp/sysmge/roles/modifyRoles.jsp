<!-- 
    编辑角色信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>编辑角色信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetRoles(){
        document.modifyRolesForm.reset();
    }
    
    //保存编辑信息
    function submitRoles(){
        
        if(!checkData()){
            return false;
        }
    
        if(confirm("确认修改信息吗？")){
            document.modifyRolesForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
        var name = document.modifyRolesForm.roleName.value;
        var code = document.modifyRolesForm.roleCode.value;
        
        if(null == code || 0 == code.length){
            alert("请输入角色编碼！");
            document.modifyRolesForm.roleCode.focus();
            return false;
        }
        if(null == name || 0 == name.length){
            alert("请输入角色名稱！");
            document.modifyRolesForm.roleName.focus();
            return false;
        }
        
        return true;
    }
</script>

</head>

<body>

<div id="main">

     <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	角色管理&nbsp;&gt;&nbsp;编辑角色信息
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
            </ul>
        </div>
    </div><!-- end position_nav -->


    <div id="cnt_list">
    <div id="cnt_lt">
    
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
			编辑角色信息
        </div>
    
        <div id="cnt_tblcontent">
        <s:form name="modifyRolesForm" action="/sysmge/roles/submitModifyRoles.action">
            <s:hidden name="ID" value="%{#request.roles.ID}"/>
                <table width="100%" class="cnt_tblbody">
                <s:iterator value="#request.roles">
                
                <tr>
	                <td class="cnt_tdWth_180">
						角色代碼：
	                </td>
	                <td>
	                    <input name="roleCode" value="<s:property value="roleCode"/>" onKeyDown="isOver('roleCode',40)" size="22" title="输入角色代碼，不可为空" )"/>
	                    <font color="#FF0000">*</font>              
	                </td>
	            </tr>
	
	            <tr>
	                <td class="cnt_tdWth_180">
						角色名稱：
	                </td>
	                <td>
	                    <input name="roleName" value="<s:property value="roleName"/>" onKeyDown="isOver('roleName',40)" size="22" title="输入角色名稱，不可为空"/>
	                    <font color="#FF0000">*</font>                  
	                </td>
	            </tr>
	
	            <tr>
	                <td class="cnt_tdWth_180">
						角色等级：
	                </td>
	                <td>
	                    <s:select list="roleLevelList" name="roleLevel" listKey="key" listValue="name"></s:select>
	                </td>
	            </tr>
	
	            <tr>
	                <td class="cnt_tdWth_180">
						角色類型：
	                </td>
	                <td>
	                    <s:select list="roleTypeList" name="roleType" listKey="key" listValue="name"></s:select>
	                </td>
	            </tr>
	
	            <tr>
	                <td class="cnt_tdWth_180">
						备注：
	                </td>
	                <td>
	                    <s:textarea name="remark" cols="33" rows="4" title="输入备注信息，请控制在250个字符範圍内"></s:textarea>
	                </td>
	            </tr>
                </s:iterator>
            </table>
            
            <div id="cnt_tblfoot">
                <img onclick="submitRoles()" src="${contextPath}/images/confirm_but.gif" alt="保存输入数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand" />
                        &nbsp;&nbsp;
                <img onclick="resetRoles()" src="${contextPath}/images/reset_but.gif" alt="重填输入数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/reset_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/reset_but.gif')" 
                    style="cursor: hand" />
            </div>
        </s:form>
        </div>
        
    </div>
    </div>

</div>

</body>
</html>