<!-- 
    编辑功能信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>编辑功能信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script language="javascript">
    //保存编辑信息
    function resetFunc(){
        modifyForm.reset();
    }
    
    function submitFunc(){
        if(!checkData()){
            return false;
        }
  
        if(confirm("确认修改信息吗？")){
            //控制导航栏区域的功能按钮的显示
		    if(null != window.parent.modifyLocation && null != window.parent.modifyLocation.operateButton && null != window.parent.modifyLocation.backButton){
		        var operateButtonNum = window.parent.modifyLocation.operateButton.length;
		        for(var i = 0; i < operateButtonNum; i ++){
		            window.parent.modifyLocation.operateButton[i].style.display = "none";
		        }
		        window.parent.modifyLocation.backButton.style.display = "none";
		    }
		    //提交数据
            document.modifyForm.submit();
            return true;
        }

        return false;
    }
    
    //校验输入的相关数据信息
    function checkData(){
       return true;
    }
</script>

<style>
    #cnt_list{
        margin-top:0px;
        margin-bottom:3px;
        margin-left:0px;
        margin-right:0px;
        border:1px solid #c3c3c3;
        border-left:0px;
        background-color:#ffffff;
        overflow: auto;
    }
</style>

</head>

<body>

<div id="main">

    <div id="cnt_list">
    <div id="cnt_lt">
        
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
			编辑功能信息
        </div>
        
        <div id="cnt_tblcontent">   
        <s:form name="modifyForm" action="/sysmge/function/functionSave.action">
        <table width="100%"  class="cnt_tblbody">
        <s:iterator value ="#request.function">
        <s:hidden name="ID" value="%{ID}"/>
         <tr>
             <td class="tab-head" width="15%" align="right">
				功能代碼：
             </td>
             <td class="td-input" width="35%">
                 <s:textfield name="funcCode" maxLength="40"/><font color="#FF0000">*</font>
             </td>
             <td class="tab-head" width="15%" align="right">
				功能名稱：
             </td>
             <td class="td-input">
                 <s:textfield name="funcName" /><font color="#FF0000">*</font>
             </td>
         </tr>

         <tr>
             <td class="tab-head" align="right">
				功能狀態：
             </td>
             <td class="td-input">
                 <s:select list="stateList" name="funcState" listKey="key" listValue="name"/>
             </td>
             <td class="tab-head" align="right">
				上级节点：
             </td>
             <td class="td-input" >
                 <label>
                     <s:property value="parentFunc.funcName" />
                 </label>
                 <input type="hidden" name="parentFuncID"
                     value="<s:property value="parentFunc.ID"/>"/>                                                           
             </td>

         </tr>

         <tr>

             <td class="tab-head" align="right">
				功能首页：
             </td>
             <td class="td-input">
                 <s:textfield name="funcUrl" size="30%"/>
             </td>
             <td class="tab-head" align="right">
				图标路径：
             </td>
             <td class="td-input">
                 <s:textfield name="iconPath" size="30%"/>
             </td>
         </tr>

         <tr>
             <td class="tab-head" align="right">
				功能描述：
             </td>
             <td class="td-input" colspan="3">

                 <s:textarea name="funcDesc" cols="28" rows="4">                                                         
                 </s:textarea>
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