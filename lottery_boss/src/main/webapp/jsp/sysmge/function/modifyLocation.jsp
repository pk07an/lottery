<!-- 
    功能信息编辑：导航信息页面
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<head>
<title>功能信息导航页面</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //调用详细信息页面的js
    function modify(){
        window.parent.modifyDetail.modify();
        hiddenOperate();//隐藏功能按钮
        return false;
    }
    function del(){
        window.parent.modifyDetail.del();
        //hiddenOperate();//隐藏功能按钮
        return false;
    }
    function addSame(){
        window.parent.modifyDetail.addSame();
        hiddenOperate();//隐藏功能按钮
        return false;
    }
    function addNext(){
        window.parent.modifyDetail.addNext();
        hiddenOperate();//隐藏功能按钮
        return false;
    }
    function back(){
        window.parent.modifyDetail.history.go(-1);
        showOperate();//显示功能按钮
    }
    //隐藏功能按钮
    function hiddenOperate(){
	    var operateButtonNum = document.all.operateButton.length;
	    for(var i = 0; i < operateButtonNum; i ++){
	        document.all.operateButton[i].style.display = "none";
	    }
	    
        document.all.backButton.style.display = "block";
    }
    //显示功能按钮
    function showOperate(){
        var operateButtonNum = document.all.operateButton.length;
        for(var i = 0; i < operateButtonNum; i ++){
            document.all.operateButton[i].style.display = "none";
        }
        
        document.all.backButton.style.display = "none";
    }
    
</script>
</head>

<body>

<div id="main">

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
            <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
           	 功能信息&nbsp;&gt;&nbsp;功能信息管理
        </div>
        <div id="position_opt">
            <ul>
                <li id="backButton" style="display:none">
	                <a href="javascript:void history.go(-1)">
	                    <img src="${contextPath}/images/bt_back.gif" alt="返回" 
	                        name="Image3" height="23" border="0" 
	                        onmouseover="changeImg(this,'${contextPath}/images/bt_back_over.gif')" 
	                        onmouseout="changeImg(this,'${contextPath}/images/bt_back.gif')" />
	                </a> 
                </li>
                
                <li id="operateButton" style="display:block">
                    <a href="#" onclick="addNext()">
                        <img src="${contextPath}/images/bt_add_next.gif" alt="增加下级" name="Image4" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_add_next_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_add_next.gif')">
                    </a> 
                </li>
                
                <li id="operateButton" style="display:block">
                    <a href="#" onclick="addSame()">
                        <img src="${contextPath}/images/bt_add_same.gif" alt="增加同级" name="Image3" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_add_same_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_add_same.gif')">
                    </a> 
                </li>
                
                <li id="operateButton" style="display:block">
                    <a href="#" onclick="del()">
                        <img src="${contextPath}/images/bt_del.gif" alt="删除" name="Image2" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')">
                    </a> 
                </li>
                
                <li id="operateButton" style="display:block">
                    <a href="#" onclick="modify()">
                        <img src="${contextPath}/images/bt_mod.gif" alt="修改" name="Image1" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_mod_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_mod.gif')">
                    </a> 
                </li>
            </ul>
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->

</div>
    
</body>
</html>