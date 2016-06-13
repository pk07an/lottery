<!-- 
    编辑用户
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@ page import="
    com.npc.lottery.common.Constant 
"%> 

<head>
<title>编辑用户</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetFunc(){
        modifyForm.reset();
    }
    
    //保存编辑信息
    function submitFunc(){
        
        if(!checkData()){
            return false;
        }
    
        if(confirm("确认新增信息吗？")){
            document.modifyForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
        var orgID = document.modifyForm.orgID.value;
        var chsName = document.modifyForm.chsName.value;
        
        if(null == orgID || 0 == orgID.length){
            alert("请选择所属机构！");
            document.modifyForm.orgID.focus();
            return false;
        }
        if(null == chsName || 0 == chsName.length){
            alert("请输入中文名！");
            document.modifyForm.chsName.focus();
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
              系统管理 &gt; 系统设置 &gt; 编辑用户
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
               编辑用户信息
        </div>
    
        <div id="cnt_tblcontent">
        <s:form name="modifyForm" action="/sysmge/user/modifySubmit.action">
        <table width="100%" class="cnt_tblbody">
            <s:iterator value="#request.user">
            <s:hidden name="ID"/><!-- 用户ID -->
            <tr>
                <td width="18%" class="cnt_td">
                    所属机构：
                </td>
                <td width="32%">
                    <s:textfield name="orgName" size="22" title="选择机构" readonly="true" value="测试机构"></s:textfield>
                    <font color="#FF0000">*</font>
                    <s:hidden name="orgID" value="10001"/>
                    <img onClick="selectOrg('document.createForm.orgID','document.createForm.orgName')" src="${contextPath}/images/bt_find.gif" alt="选择机构信息" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_find_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_find.gif')" style="cursor:hand">
                </td>
                <td width="18%" class="cnt_td">
                    登录帳號：
                </td>
                <td width="32%">
                    <s:property value ="account"/>
                    <s:hidden name="account"/>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
                    中文名：
                </td>
                <td>
                    <s:textfield name="chsName" size="22" title="输入中文名"></s:textfield>
                    <font color="#FF0000">*</font>
                </td>
                <td class="cnt_td">
                    英文名：
                </td>
                <td>
                    <s:textfield name="engName" size="22" title="输入英文名"></s:textfield>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
                    办公室电话：
                </td>
                <td>
                    <s:textfield name="officePhone" size="22" title="输入办公室电话"></s:textfield>
                </td>
                <td class="cnt_td">
                    移动电话：
                </td>
                <td>
                    <s:textfield name="mobilePhone" size="22" title="输入移动电话"></s:textfield>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
                    传真：
                </td>
                <td>
                    <s:textfield name="fax" size="22" title="输入传真号"></s:textfield>
                </td>
                <td class="cnt_td">
                 Email：
                </td>
                <td>
                    <s:textfield name="emailAddr" size="22" title="输入Email"></s:textfield>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
                    創建时间：
                </td>
                <td colspan="3">
                    <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
                    备注：
                </td>
                <td colspan="3">
                    <s:textarea name="comments" cols="33" rows="4" title="输入备注信息，请控制在250个字符範圍内"></s:textarea>
                </td>
            </tr>
            </s:iterator>
            </table>
            
            <div id="cnt_tblfoot">
                <img onclick="submitFunc()" src="${contextPath}/images/confirm_but.gif" alt="保存输入数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand" />
                        &nbsp;&nbsp;
                <img onclick="resetFunc()" src="${contextPath}/images/reset_but.gif" alt="重填输入数据" 
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