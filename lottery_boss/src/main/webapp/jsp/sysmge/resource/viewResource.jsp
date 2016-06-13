<!-- 
    查看资源信息值
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看资源信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script type="text/javascript">
    
    //编辑
    function modifyResourceFun()
    {
        document.modifyForm.submit();
    }
    
    //删除
    function delResourceFun()
    {
        if(confirm("确定删除资源信息吗？")){
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
                    <s:form name="modifyForm" action="/sysmge/resource/modifyResource.action">
                    <s:hidden name="ID" value="%{#request.resource.ID}"/>
                    <a href="#" onclick="modifyResourceFun()"> <img
                             src="${contextPath}/images/bt_mod.gif" alt="编辑"
                             name="Image1" height="23" border="0" align="absbottom"
                             onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')"
                             onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
                     </a>
                    </s:form>
                </li>
            
                <li>
                <s:form name="delForm" action="/sysmge/resource/delResource.action">
                    <s:hidden name="ID" value="%{#request.resource.ID}"/>
                    <a href="#" onclick="delResourceFun()"> <img
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
            资源详细信息
        </div>
        
        <div id="cnt_tblcontent">   
        <form name="viewForm" method="post" action="#">
        <table width="100%"  class="cnt_tblbody">
        <s:iterator value ="#request.resource">
            
            <tr>
                 <td width="18%" class="cnt_td" nowrap>
					资源代碼：
                 </td>
                 <td width="32%">
                     <s:property value="resCode"/>                                                                  
                 </td>
                 
                 <td width="18%" class="cnt_td" nowrap>
					资源名稱：
                 </td width="32%">
                 <td>
                     <s:property value="resName"/>                                                                  
                 </td>
             </tr>

             <tr>
                 <td class="cnt_td">
					资源狀態：
                 </td>
                 <td>
                     <s:property value="resStateName" escape="false"/> 
                 </td>
                 
                 <td class="cnt_td" nowrap>
					资源類型:
                 </td>
                 <td>
                     <s:property value="resTypeName" />
                 </td>
             </tr>
             
             <tr>
                 <td class="cnt_td">
					URL：
                 </td>
                 <td colspan="3" style="word-break:break-all">
                     <s:property value="url"></s:property>
                 </td>
             </tr>
             
             <tr>
                 <td class="cnt_td">
					资源描述：
                 </td>
                 <td colspan="3">
                     <s:property value="resDesc"></s:property>
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