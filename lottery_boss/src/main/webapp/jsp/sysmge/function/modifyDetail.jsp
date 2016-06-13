<!-- 
    查看功能信息
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>查看功能信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //控制导航栏区域的功能按钮的显示
    if(null != window.parent.modifyLocation && null != window.parent.modifyLocation.operateButton && null != window.parent.modifyLocation.backButton){
	    var operateButtonNum = window.parent.modifyLocation.operateButton.length;
	    for(var i = 0; i < operateButtonNum; i ++){
	        window.parent.modifyLocation.operateButton[i].style.display = "block";
	    }
	    window.parent.modifyLocation.backButton.style.display = "none";
    }
    
    //编辑
    function modify(){
        document.operateForm.action += "/modifyInfo.action";
        document.operateForm.submit();
        //alert("sdgfsdf");
    }
    //删除
    function del(){
        if(!confirm("确定删除此功能吗？")){
            return false;
        }
        
        document.operateForm.action += "/del.action";
        document.operateForm.submit();
        //控制导航栏区域的功能按钮的显示
        if(null != window.parent.modifyLocation && null != window.parent.modifyLocation.operateButton && null != window.parent.modifyLocation.backButton){
	        var operateButtonNum = window.parent.modifyLocation.operateButton.length;
	        for(var i = 0; i < operateButtonNum; i ++){
	            window.parent.modifyLocation.operateButton[i].style.display = "none";
	        }
	        window.parent.modifyLocation.backButton.style.display = "none";
        }
    }
    //增加同级
    function addSame(){
        document.operateForm.action += "/addSame.action";
        document.operateForm.submit();
    }
    //增加下级
    function addNext(){
        document.operateForm.action += "/addNext.action";
        document.operateForm.submit();
    }
    
    //增加资源
    function addResource(functionID)
    {
      
        //需要打开的页面URL
        var url = "${contextPath}/sysmge/resource/addResource.action?functionID="+functionID;
        //打开模式窗口，固定写法，不要改变
        var ret = openModalDialog("${contextPath}/common/ModalDialogPage.jsp",url,450,383);
        if(ret==1)
        {
            window.location.reload(true);
        }
    }
    
    //查看资源信息
    function openWin(resourceID)
    {         
        //需要打开的页面URL
        var url = "${contextPath}/sysmge/resource/viewResource.action?ID="+resourceID;
        var controlUrl = "${contextPath}/common/ModalDialogPage.jsp";
        //打开模式窗口，固定写法，不要改变
        var ret = openModalDialog(controlUrl,url,450,360);
        if(ret==1||typeof(ret)=="undefined")
        {           
            window.location.reload(true);
        }
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
            功能信息
        </div>
        
        <div id="cnt_tblcontent">   
        <form name="operateForm" method="post" action="${contextPath}/sysmge/function">
        <table width="100%"  class="cnt_tblbody">
        <s:iterator value ="#request.function">
        <s:hidden name="ID" value="%{ID}"/>
         <tr>
             <td class="tab-head" width="15%" align="right">
                 功能代碼：
             </td>
             <td class="td-input" width="35%">
                 <s:property value="funcCode" />
             </td>
             <td class="tab-head" width="15%" align="right">
                 功能名稱：
             </td>
             <td class="td-input">
                 <s:property value="funcName" />
             </td>
         </tr>

         <tr>
             <td class="tab-head" align="right">
                 功能狀態：
             </td>
             <td class="td-input">
                 <s:property value="stateName"escape="false" />
             </td>
             <td class="tab-head" align="right">
                 上级节点：
             </td>
             <td class="td-input" >
                 <s:property value="parentFunc.funcName" />
             </td>
         </tr>

         <tr>
             <td class="tab-head" align="right">
                 功能首页：
             </td>
             <td class="td-input">
                 <s:property value="funcUrl" />
             </td>
             <td class="tab-head" align="right">
                 图标路径：
             </td>
             <td class="td-input" >
                 <s:property value="iconPath" />
             </td>
         </tr>

         <tr>
             <td class="tab-head" align="right">
                 功能描述：
             </td>
             <td class="td-input" colspan="3">
                 <s:property value="funcDesc" />
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
            				资源信息列表
                    </td>
                    <td align="right">
                        <a href="#"
                             onClick="addResource('<s:property value="#request.function.ID"/>')">
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
                <td width='10%' class='tab-bag' align="center" nowrap>
          			资源代碼
                </td>
                <td width='15%' class='tab-bag' nowrap>
					资源名稱
                </td>
                <td width='8%' class='tab-bag' nowrap>
					资源狀態
                </td>
                <td width='8%' class='tab-bag' nowrap>
               		资源類型
                </td>
                <td width='40%' class='tab-bag' nowrap>
               		资源信息
                </td>
                <td width='5%' class='tab-bag' nowrap>
					操作
                </td>
            </tr>

            <!-- 判断是否存在对应的数据 -->
            <s:if test="null == #request.function.resources || 0 == #request.function.resources.size()">
                <tr bgcolor='#FFFFFF' class='list-tr1'>
                    <td class='align-l' colspan='10'>
                        <font color='#FF0000'>未查詢到满足条件的数据！</font>
                    </td>
                </tr>
            </s:if>
            
            <!-- 迭代显示 List 集合中存储的数据记录 -->
            <s:iterator value="#request.function.resources" status="st">
            <!-- 行间隔色显示和当前行反白显示，固定写法 -->
            <tr <s:if test="true == #st.odd">class='list-tr2'</s:if><s:else>class='list-tr1'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
                <td class='align-l' nowrap>
                    <s:property value="resCode" />
                </td>
                <td class='align-l'>
                    <s:property value="resName" />
                </td>
                <td class='align-c' nowrap>
                    <s:property value="resStateName" escape="false"/>
                </td>
                <td class='align-c' nowrap>
                    <s:property value="resTypeName" />
                </td>
                <td class='align-l' style="word-break:break-all">
                    <s:property value="url" />
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

</div>
    
</body>
</html>