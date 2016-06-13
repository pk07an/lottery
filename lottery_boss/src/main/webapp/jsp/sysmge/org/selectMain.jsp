<!--
    选择机构信息（單选）
    
    此模式窗口返回值为 [orgID]&&[orgName]
    注意返回的页面上解析时，&& 使用常量 Constant.SELECT_ORGID_ORGNAME_SPLIT
-->
<%@ page language="java" pageEncoding="UTF-8"%><!-- 页面编碼 UTF-8，所有页面均如此设置 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page import="
    com.npc.lottery.common.Constant
"%>  

<html>
<head>
<title>选择机构信息（單选）</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    function selectOrg(){
        var orgID = document.selectForm.orgID.value;
        var orgName = document.selectForm.orgName.value;
        
        if(null == orgID || 0 == orgID.length){
            //alert("请选择机构信息！");
            //return false;
            window.close();
            window.returnValue = "";
            return true;
        }   
        
        window.close();
        window.returnValue = orgID + "<%=Constant.SELECT_ORGID_ORGNAME_SPLIT%>" + orgName;
        
        return true;
    }
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
        
    <!-- 表單信息 -->
    <s:form name="selectForm"> 
    
    <div id="cnt_search2">
    <div id="cnt_sch2">          
        <ul>
            <li>
                机构名稱：<input type="text" name="orgName" readonly="readonly">
                      <input type="hidden" name="orgID">
            </li>
            <li>
                <img onClick="selectOrg()" src="${contextPath}/images/bt_select.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_select_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_select.gif')" style="cursor:hand">
                <img onClick="javacsript:window.close()" src="${contextPath}/images/bt_close.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_close_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_close.gif')" style="cursor:hand">
            </li>
        </ul>           
    </div>
    </div>
   
    <div class="outer" style="MARGIN: 2px; WIDTH: 100%; HEIGHT: 80%; overflow:auto;">
        <iframe id='selectTreeList' src='${contextPath}/sysmge/org/selectTreeList.action' frameBorder='0' width='100%' name='content' scrolling='no' height='100%'></iframe>  
    </div>
        
    </s:form>
</div>

</body>
</html>