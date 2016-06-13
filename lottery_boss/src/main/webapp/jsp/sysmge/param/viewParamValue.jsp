<!-- 
    查看参数類型值
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看参数類型值</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script type="text/javascript">
    
    //编辑
    function modifyParamValueFun()
    {
        document.modifyForm.submit();
    }
    
    //删除
    function delParamValueFun()
    {
        if(confirm("确定删除参数值信息吗？")){
            document.delForm.submit();
        }
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

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position_opt">
            <ul>
                <li>
                    <a href="#" onclick="winClose()"> <img
                           src="${contextPath}/images/bt_back.gif" alt="返回"
                           name="Image3" height="23" border="0" align="absbottom"
                           onmouseover="changeImg(this,'${contextPath}/images/bt_back_over.gif')"
                           onmouseout="changeImg(this,'${contextPath}/images/bt_back.gif')">
                    </a>
                </li>
                
                <li>
                    <s:form name="modifyForm" action="/sysmge/param/modifyParamValue.action">
                    <s:hidden name="paramValueID" value="%{#request.paramValue.ID}"/>
                    <a href="#" onclick="modifyParamValueFun()"> <img
                             src="${contextPath}/images/bt_mod.gif" alt="编辑"
                             name="Image1" height="23" border="0" align="absbottom"
                             onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')"
                             onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
                     </a>
                    </s:form>
                </li>
            
                <li>
                <s:form name="delForm" action="/sysmge/param/delParamValue.action">
                    <s:hidden name="paramValueID" value="%{#request.paramValue.ID}"/>
                    <a href="#" onclick="delParamValueFun()"> <img
                            src="${contextPath}/images/bt_del.gif" alt="删除"
                            name="Image2" height="23" border="0" align="absbottom"
                            onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')"
                            onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')">
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
            参数值详细信息
        </div>
        
        <div id="cnt_tblcontent">   
        <form name="viewForm" method="post" action="#">
        <table width="100%"  class="cnt_tblbody">
        <s:iterator value ="#request.paramValue">
            
            <tr>
                 <td width="18%" class="cnt_td" nowrap>
                     参数值代碼：
                 </td>
                 <td width="32%">
                     <s:property value="code"/>                                                                  
                 </td>
                 
                 <td width="18%" class="cnt_td" nowrap>
                     参数值名稱：
                 </td width="32%">
                 <td>
                     <s:property value="name"/>                                                                  
                 </td>
             </tr>

             <tr>
                 <td class="cnt_td">
                     参数值：
                 </td>
                 <td>
                     <s:property value="value"/> 
                 </td>
                 
                 <td class="cnt_td" nowrap>
                     参数类别名稱:
                 </td>
                 <td>
                     <s:property value="param.name" />
                 </td>
             </tr>
             
             <tr>
                 <td class="cnt_td">
                     序号：
                 </td>
                 <td colspan="3">
                     <s:property value="sortNum"></s:property>
                 </td>
             </tr>
             
             <tr>
                 <td class="cnt_td">
                     备注：
                 </td>
                 <td colspan="3">
                     <s:property value="remark"></s:property>
                 </td>
             </tr>
        </s:iterator>
        </table>
        </form>
        </div>
        
    </div>
    </div>

</div>
    
</body>
</html>