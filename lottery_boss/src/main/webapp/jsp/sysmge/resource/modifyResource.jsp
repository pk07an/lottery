<!-- 
    编辑资源信息页面
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title> 编辑资源信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetResource(){
        document.modifyResourceForm.reset();
    }
    
    //保存编辑信息
    function submitResource(){
        
        if(!checkData()){
            return false;
        }
    
        if(confirm("确认编辑信息吗？")){
            document.modifyResourceForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
    	var resCode = document.modifyResourceForm.resCode.value;
        var resName = document.modifyResourceForm.resName.value;
        
        if(null == resCode || 0 == resCode.length){
            alert("请输入资源代碼！");
            document.modifyResourceForm.resCode.focus();            
            return false;
        }
        if(null == resName || 0 == resName.length){
            alert("请输入资源名稱！");
            document.modifyResourceForm.resName.focus();
            return false;
        }
        
        return true;
    }
    
    function winClose()
    {
       window.close();
       window.returnValue =1;
       return true;
    }
</script>

<style>
	#position_nav{
	    height:28px;
	    position:relative;
	    margin-top:2px;
	    margin-left:2px;
	    margin-right:2px;
	    border-bottom:0px solid #ccc;
	    border-top:0px solid #ccc;
	    border-right:0px solid #ccc;
	    border-left:0px solid #ccc;
	    background:url(../images/bg_position.jpg) repeat-x;
	}
	#position_opt {
	    position:absolute;
	    top:2px;
	    right:2px;
	    width:460px;
	}
</style>

</head>

<body>

<div id="main">

    <div id="cnt_list">
    <div id="cnt_lt">
    
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
            	资源信息编辑
        </div>
    
        <div id="cnt_tblcontent">
        <s:form name="modifyResourceForm" action="/sysmge/resource/saveModifyResource.action">
            <s:hidden name="ID" value="%{#request.resource.ID}"/>
            <s:iterator value="#request.resource">
   			<table width="100%"  class="cnt_tblbody">
	            <tr>
	                <td class="cnt_tdWth_180">
						资源代碼
	                </td>
	                <td>
	                    <s:textfield name="resCode" size="22" maxLength="40" title="输入资源代碼，不可为空"></s:textfield>
	                    <font color="#FF0000">*</font>              
	                </td>
	            </tr>

	            <tr>
	                <td class="cnt_tdWth_180">
						资源名稱：
	                </td>
	                <td>
	                    <s:textfield name="resName" size="22" maxLength="40" title="输入资源名稱，不可为空"></s:textfield>
	                    <font color="#FF0000">*</font>                  
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="cnt_tdWth_180">
						资源狀態：
	                </td>
	                <td>
	                    <s:select list="#request.resStateMap" name="resState"/>
	                    <font color="#FF0000">*</font>
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="cnt_tdWth_180">
						资源類型：
	                </td>
	                <td>
	                	页面
	                	<s:hidden name="resType" value="0"></s:hidden>
	                </td>
	            </tr>
	            
				<tr>
	                <td class="cnt_tdWth_180">
						资源对应的URL：
	                </td>
	                <td>
	                    <s:textarea name="url" cols="33" rows="6" title="输入资源对应的URL，多个URL之间使用半角的;;分割"></s:textarea>
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="cnt_tdWth_180">
						资源描述：
	                </td>
	                <td>
	                    <s:textarea name="resDesc" cols="33" rows="4" title="输入资源描述信息，请控制在250个字符範圍内"></s:textarea>
	                </td>
	            </tr>
            </table>
            </s:iterator>
            
            <div id="cnt_tblfoot">
                <img onclick="submitResource()" src="${contextPath}/images/confirm_but.gif" alt="保存输入数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand" />
                        &nbsp;&nbsp;
                <img onclick="resetResource()" src="${contextPath}/images/reset_but.gif" alt="重填输入数据" 
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