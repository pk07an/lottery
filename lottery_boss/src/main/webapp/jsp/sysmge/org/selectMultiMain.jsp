<!--
    选择机构信息（多选）
    
    此模式窗口返回值为 orgID1&&orgName1!!orgID2&&orgName2
    注意返回的页面上解析时，&& 使用常量 Constant.SELECT_ORGID_ORGNAME_SPLIT
                         !! 使用常量Constant.SELECT_MULTI_ORG_SPLIT
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

    //选择机构信息
    function selectMultiOrg(){

        var result = "";
        var selectOrg = document.frames("selectTreeList").document.all.selectOrg;

        //TODO 处理机构列表不存在或者只有一个机构的情况
        for(var i = 0; i < selectOrg.length; i++){
            if(selectOrg[i].checked){
                //构造返回值
                result = result + '<%=Constant.SELECT_MULTI_ORG_SPLIT%>' + selectOrg[i].value;
            }
        }

        if(result.length > 0){
            //截取掉最开始的分割符号
            result = result.substring('<%=Constant.SELECT_MULTI_ORG_SPLIT%>'.length);
        }

        if(0 == result.length){
            alert("请选择机构信息！");
            return false;
        }
        window.close();
        window.returnValue = result;
        
        return true;
    }
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="main"> 
        
    <!-- 表單信息 -->
    <s:form name="selectForm"> 
    
    <div id="cnt_search">
    <div id="cnt_sch">          
        <ul>
            <li>
                已选机构数：<input type="text" name="orgNum" readonly="readonly" size="3">
            </li>
            <li>
              <img onClick="selectMultiOrg()" src="${contextPath}/images/bt_select.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_select_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_select.gif')" style="cursor:hand">
              <img onClick="javacsript:window.close()" src="${contextPath}/images/bt_close.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_close_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_close.gif')" style="cursor:hand">
            </li>
        </ul>           
    </div>
    </div>
   
    <div class="outer" style="MARGIN: 2px; WIDTH: 100%; HEIGHT: 80%; overflow:auto;">
        <iframe id='selectTreeList' src='${contextPath}/sysmge/org/selectMultiTreeList.action' frameBorder='0' width='100%' name='content' scrolling='no' height='100%'></iframe> 
    </div>
        
    </s:form>
</div>

</body>
</html>