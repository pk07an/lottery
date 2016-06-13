<!-- 
    新增参数值信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<%@ page import="
    com.npc.lottery.common.Constant 
"%> 

<head>
<title>新增参数值信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetParamValue(){
        document.addParamValueForm.reset();
    }
    
    //保存编辑信息
    function submitParamValue(){
        
        if(!checkData()){
            return false;
        }      
        if(confirm("确认新增信息吗？")){
            document.addParamValueForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
        var name = document.addParamValueForm.name.value;
        var code = document.addParamValueForm.code.value;
        var parameValue = document.addParamValueForm.value.value;
        
        if(null == code || 0 == code.length){
            alert("请输入参数值代碼！");
            document.addParamValueForm.code.focus();            
            return false;
        }
        if(null == name || 0 == name.length){
            alert("请输入参数值名稱！");
            document.addParamValueForm.name.focus();
            return false;
        }
        if(null == parameValue || 0 == parameValue.length){
            alert("请输入参数值！");
            document.addParamValueForm.value.focus();
            return false;
        }        
        
        return true;
    }
    
    function winClose(){
       window.close();
       window.returnValue =1;
       return true;
    }
    
</script>

</head>

<body >

<div id="main">
    
    <div id="cnt_list">
    <div id="cnt_lt">
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
            新增参数值信息
        </div>
        
        <div id="cnt_tblcontent">
        <s:form name="addParamValueForm" action="/sysmge/param/saveParamValue.action">
            <table width="100%"  class="cnt_tblbody">
            <s:hidden name="paramValueParamID" value="%{#request.paramValueParamID}"/>
                <s:iterator value="#request.paramValue">
		            <tr>
		                <td class="cnt_tdWth_180">
		                    参数值代碼：
		                </td>
		                <td>
		                    <input name="code" size="22" title="输入参数类别代碼，不可为空" )"/>
		                    <font color="#FF0000">*</font>              
		                </td>
		            </tr>

		            <tr>
		                <td class="cnt_tdWth_180">
		                    参数值名稱：
		                </td>
		                <td>
		                    <input name="name" size="22" title="输入参数类别名稱，不可为空"/>
		                    <font color="#FF0000">*</font>                  
		                </td>
		            </tr>
		            
		            <tr>
		                <td class="cnt_tdWth_180">
		                    参数值：
		                </td>
		                <td>
		                    <input name="value" value="<s:property value="value"/>" size="22" title="输入参数值，不可为空"/>
		                    <font color="#FF0000">*</font>
		                </td>
		            </tr>
		            
		            <tr>
		                <td class="cnt_tdWth_180">
		                    序号：
		                </td>
		                <td>
		                <input name="sortNum" size="22" onkeyup="value=value.replace(/[^\d]/g,'')">
		                </td>
		            </tr>
		            
		            <tr>
		                <td class="cnt_tdWth_180">
		                    参数类别名稱:
		                </td>
		                <td>
		                    <s:property value="#request.param.name"/>
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
                <img onclick="submitParamValue()" src="${contextPath}/images/confirm_but.gif" alt="保存输入的数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand"/>
                    &nbsp;&nbsp;
                <img onclick="resetParamValue()" src="${contextPath}/images/reset_but.gif" alt="重填输入数据" 
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