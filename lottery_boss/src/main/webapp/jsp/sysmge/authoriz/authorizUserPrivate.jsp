<!--
         用户私有权限授权
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
    com.npc.lottery.sysmge.entity.Function 
"%>  

<html>  
<head>
<title>用户私有权限授权</title>

<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    function authorizRole(){
    
        var selectFunc = document.frames("userPrivateSelectFunc").document.all.selectFunc;
        var selectFuncID = "";//选中的功能数据集合
        var unSelectFuncID = "";//未选中的功能数据集合

        if(null != selectFunc){
            if(selectFunc.length > 0){
	            for(var i = 0 ; i < selectFunc.length; i ++){
	                if(selectFunc[i].disabled){
	                    //禁用的checkbox则不处理
	                    continue;
	                }else{
	                    if(selectFunc[i].checked){
	                        selectFuncID = selectFuncID + "<%=Function.ID_GROUP_SPLIT%>" + selectFunc[i].value;
	                    }else{
	                    	unSelectFuncID = unSelectFuncID + "<%=Function.ID_GROUP_SPLIT%>" + selectFunc[i].value;
	                    }
	                }
	            }
            }else{
                if(selectFunc.checked){
                     selectFuncID = selectFuncID + "<%=Function.ID_GROUP_SPLIT%>" + selectFunc.value;
                }else{
                     unSelectFuncID = unSelectFuncID + "<%=Function.ID_GROUP_SPLIT%>" + selectFunc.value;
                }
            }
        }
        
        //设置选择的功能ID集合
        document.authorizUserPrivateForm.selectFuncID.value = selectFuncID;
        document.authorizUserPrivateForm.unSelectFuncID.value = unSelectFuncID;
        
        //提交
        if(confirm("确定提交授权信息吗？")){
              document.authorizUserPrivateForm.submit();
        }
    }
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="authorizUserPrivateForm" action="${contextPath}/sysmge/authoriz/authorizUserPrivateSubmit.action">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td  valign="top" class="td-content">
    
    <!-- 超过页面高度出现滚动条 -->
    <div class='outer' style="MARGIN: 0px; WIDTH: 100%; HEIGHT: 90%; overflow:no;">
        <iframe id='userPrivateSelectFunc' src='${contextPath}/sysmge/authoriz/userPrivateSelectFunc.action?userID=${request.userID}&userType=${request.userType}' frameBorder='0' width='100%' name='content' scrolling='no' height='100%'></iframe>    
    </div>
    
    <div class='outer' align="center">
        <img onClick="authorizRole()" src="${contextPath}/images/bt_affirm.gif" alt="授权" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_affirm_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_affirm.gif')" style="cursor:hand">
        &nbsp;&nbsp;
        <img onClick="javacsript:window.close()" src="${contextPath}/images/bt_close.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_close_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_close.gif')" style="cursor:hand">
    </div>
    
    <s:hidden name="selectFuncID"/>
    <s:hidden name="unSelectFuncID"/>
    <s:hidden name="userID"/>
    <s:hidden name="userType"/>
    </td>
    </tr>
</table>
</form>

</body>
</html>