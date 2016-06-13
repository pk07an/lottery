<!-- 
    编辑参数类别信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>编辑参数类别信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetParam(){
        document.modifyParamForm.reset();
    }
    
    //保存编辑信息
    function submitParam(){
        
        if(!checkData()){
            return false;
        }
    
        if(confirm("确认提交信息吗？")){
            document.modifyParamForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
        var name = document.modifyParamForm.name.value;
        var code = document.modifyParamForm.code.value;
        
        if(null == code || 0 == code.length){
            alert("请输入角色编碼！");
            document.modifyParamForm.code.focus();
            return false;
        }
        if(null == name || 0 == name.length){
            alert("请输入角色名稱！");
            document.modifyParamForm.name.focus();
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
            系统管理 &gt; 系统设置 &gt; 参数维护
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
            编辑参数类别信息
        </div>
    
        <div id="cnt_tblcontent">
        <s:form name="modifyParamForm" action="/sysmge/param/saveParam.action">
            <s:hidden name="ID" value="%{#request.param.ID}"/>
                <table width="100%" class="cnt_tblbody">
                <s:iterator value="#request.param">
                
                <tr>
                    <td class="cnt_tdWth_180">
                        参数代碼：
                    </td>
                    <td>
                        <input name="code" value="<s:property value="code"/>"
                            size="22" title="输入长度40以内的参数类别代碼，不可为空，参数类别代碼不可重复" )" maxlength="40"/>
                        <font color="#FF0000">*</font>
                    </td>
                </tr>

                <tr>
                    <td class="cnt_tdWth_180">
                        参数名稱：
                    </td>
                    <td>
                        <input name="name" value="<s:property value="name"/>"
                            size="22" title="输入参数类别名稱，不可为空" maxlength="40"/>
                        <font color="#FF0000">*</font>
                    </td>
                </tr>

                <tr>
                    <td class="cnt_tdWth_180">
                        参数類型：
                    </td>
                    <td>
                        <s:label value="普通参数" name="type" />
                    </td>
                </tr>
                <tr>
                    <td class="cnt_tdWth_180">
                        参数狀態：
                    </td>
                    <td>
                        <s:select list="stateMap" name="state" />
                    </td>
                </tr>

                <tr>
                    <td class="cnt_tdWth_180">
                        备注：
                    </td>
                    <td>
                        <s:textarea name="remark" cols="33" rows="4"
                            title="输入备注信息，请控制在250个字符範圍内"></s:textarea>
                    </td>
                </tr>
                </s:iterator>
            </table>
            
            <div id="cnt_tblfoot">
                <img onclick="submitParam()" src="${contextPath}/images/confirm_but.gif" alt="保存输入数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand" />
                        &nbsp;&nbsp;
                <img onclick="resetParam()" src="${contextPath}/images/reset_but.gif" alt="重填输入数据" 
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