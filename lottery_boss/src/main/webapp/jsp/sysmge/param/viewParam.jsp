<!-- 
    查看参数类别信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看参数类别信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //修改
    function modifyParamFun(){
        document.modifyForm.submit();
        return false;
        
    }
    
    function openWin(paramValueID)
    {         
        //需要打开的页面URL，设置为实际值
        var url = "${contextPath}/sysmge/param/viewParamValue.action?paramValueID="+paramValueID;
        var controlUrl = "${contextPath}/common/ModalDialogPage.jsp";
        //打开模式窗口，固定写法，不要改变
        var ret = openModalDialog(controlUrl,url,450,330);
        if(ret==1||typeof(ret)=="undefined")
        {           
            window.location.reload(true);
        }
    }
    
    //删除信息
    function delParamFun(){
        if(confirm("确认删除该参数么，其相关联的参数值也将被删除？"))
        {
           document.delForm.submit();
        }
    }
    function addParamValue(paramValueParamID)
    {
      
        //需要打开的页面URL
        var url = "${contextPath}/sysmge/param/addParamValue.action?paramValueParamID="+paramValueParamID;
        //打开模式窗口，固定写法，不要改变
        var ret = openModalDialog("${contextPath}/common/ModalDialogPage.jsp",url,450,333);
        //window.open("${contextPath}/sysmge/param/addParamValue.action?paramID="+paramID);
        if(ret==1)
        {           
            window.location.reload(true);
        }
    }
</script>

<style>
.cnt_tblbody2 {
    line-height:28px;
    font-weight: bold;
}
</style>

</head>

<body>

<div id="main">

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
            	参数维护&nbsp;&gt;&nbsp;查看参数类别信息
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
            
                <li>
                <s:form name="delForm" action="/sysmge/param/delParam.action">
                    <s:hidden name="ID" value="%{#request.param.ID}"/>
                    <a href="#" onclick="delParamFun()" <s:property value="#request.controlShow"/>> <img
                             src="${contextPath}/images/bt_del.gif" alt="删除"
                             name="Image2" height="23" border="0" align="absbottom"
                             onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')"
                             onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')">
                     </a>
                </s:form>
                </li>
                
                <li>
                <s:form name="modifyForm" action="/sysmge/param/modifyParam.action">
                    <s:hidden name="ID" value="%{#request.param.ID}"/>
                    <a href="#" onclick="modifyParamFun()" <s:property value="#request.controlShow"/> > <img
                           src="${contextPath}/images/bt_mod.gif" alt="编辑"
                           name="Image1" height="23" border="0" align="absbottom"
                           onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')"
                           onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
                    </a>
                </s:form>
                </li>
            </ul>
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->

    <div id="cnt_list">
    <div id="cnt_lt">
        
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
            参数类别详细信息
        </div>
        
        <div id="cnt_tblcontent">   
        <form name="viewForm" method="post" action="#">
        <table width="100%"  class="cnt_tblbody">
        <s:iterator value ="#request.param">
            <tr>
                <td class="cnt_tdWth_180">
                    参数代碼：
                </td>
                <td>
                    <s:property value="code" />
                </td>

                <td class="cnt_tdWth_180">
                    参数名稱：
                </td>
                <td>
                    <s:property value="name" />
                </td>
            </tr>
            
            <tr>
                <td class="cnt_tdWth_180">
                    参数類型：
                </td>
                <td>
                    <s:property value="typeName" />
                </td>

                <td class="cnt_tdWth_180">
                    参数狀態：
                </td>
                <td>
                    <s:property value="stateName" escape="false" />
                </td>
            </tr>

            <tr>
                <td class="cnt_tdWth_180">
                    备注：
                </td>
                <td colspan="3">
                    <s:property value="remark" />
                </td>
            </tr>
        </s:iterator>
        </table>
        </form>
        </div>
        
    </div>
    
    <div id="cnt_lt">
    
        <div id="cnt_tblhead">
            <table width="100%" class="cnt_tblbody2">
                <tr>
                    <td align="left" width="50%">
                        <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
                            参数类别值列表
                    </td>
                    <td align="right">
                        <a href="#"
                             onClick="addParamValue('<s:property value="#request.param.ID"/>')">
                             <img src="${contextPath}/images/bt_add.gif"
                                 alt="新增" name="addImage" height="23" border="0"
                                 align="absbottom"
                                 onmouseover="changeImg(this,'${contextPath}/images/bt_add_over.gif')"
                                 onmouseout="changeImg(this,'${contextPath}/images/bt_add.gif')">
                         </a>
                    </td>
                </tr>
            </table>
        </div>
    
        <div id="cnt_tblcontent">
        <table width="100%" border="0" cellpadding="2" cellspacing="1" class="form-table">
            
            <tr align='center'>
                <td width='20%' class='tab-bag' align="center" nowrap>
                    参数代碼
                </td>
                <td width='45%' class='tab-bag' nowrap>
                    参数名稱
                </td>
                <td width='8%' class='tab-bag' nowrap>
                    参数值
                </td>
                <td width='8%' class='tab-bag' nowrap>
                    序号
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
                <td class='align-l' nowrap>
                    <s:property value="code" />
                </td>
                <td class='align-l'>
                    <s:property value="name" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="value" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="sortNum" />
                </td>
                <td class='align-c' nowrap>
                    <a href="#" onClick="openWin('<s:property value="ID"/>')"> <img
                            src="<%=request.getContextPath()%>/images/bt_particular.gif"
                            width="18" height="18" border="0" alt="查看详细信息">
                    </a>
                </td>
            </tr>
            </s:iterator>
            
        </table>
        </div>
    </div>
    </div>
    <!-- 列表参数类别值 end -->
    
</div>
    
</body>
</html>