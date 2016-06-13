<!-- 
    编辑用户密碼
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@ page import="
    com.npc.lottery.common.Constant 
"%> 

<head>
<title>编辑用户密碼</title>

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
    
        if(confirm("确认修改密碼吗？")){
            document.modifyForm.submit();
            return true;
        }
        return false;
    }

    //校验输入的相关数据信息
    function checkData(){
        var userPwdOrign = document.modifyForm.userPwdOrign.value;
        var userPwd = document.modifyForm.userPwd.value;
        var userPwdSec = document.modifyForm.userPwdSec.value;
        
        if(null == userPwdOrign || 0 == userPwdOrign.length){
            alert("请输入旧密碼！");
            document.modifyForm.userPwdOrign.focus();
            return false;
        }
        if(null == userPwd || 0 == userPwd.length){
            alert("请输入新密碼！");
            document.modifyForm.userPwd.focus();
            return false;
        }
        if(null == userPwdSec || 0 == userPwdSec.length){
            alert("请确认新密碼！");
            document.modifyForm.userPwdSec.focus();
            return false;
        }
        if(userPwd != userPwdSec){
            alert("两次输入的新密碼不一致，请重新输入！");
            document.modifyForm.userPwd.select();
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
            系统管理 &gt; 系统设置 &gt; 修改密碼
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
              修改密碼
        </div>
    
        <div id="cnt_tblcontent">
        <s:form name="modifyForm" action="/sysmge/user/modifyPasswordSubmit.action">
            
                <table width="100%" class="cnt_tblbody">
                <s:iterator value="#request.user">
	                <s:hidden name="ID" value="%{ID}"/>
	                <tr>
		                <td width="21%" class="cnt_td">
		                    旧密碼：
		                </td>
		                <td>
		                    <s:password name="userPwdOrign" size="22" title="请输入旧密碼"></s:password>
		                    <font color="#FF0000">*</font>
		                </td>
		            </tr>
		            
		            <tr>
		                <td width="21%" class="cnt_td">
		                    新密碼：
		                </td>
		                <td>
		                    <s:password name="userPwd" size="22" title="请输入新密碼"></s:password>
		                    <font color="#FF0000">*</font>
		                </td>
		            </tr>
		            
		            <tr>
		                <td width="21%" class="cnt_td">
		                    确认新密碼：
		                </td>
		                <td>
		                    <s:password name="userPwdSec" size="22" title="请确认密碼"></s:password>
		                    <font color="#FF0000">*</font>
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