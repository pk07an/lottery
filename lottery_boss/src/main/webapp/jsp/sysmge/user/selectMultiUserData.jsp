<!--
    用户信息选择（多选）：记录所选择的人员数据页面
-->
<%@ page language="java" pageEncoding="UTF-8"%><!-- 页面编碼 UTF-8，所有页面均如此设置 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<%@ page import="
    com.npc.lottery.common.Constant
"%>  

<html>
<head>
<title>记录所选择的人员数据页面</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    
    //将用户添加到列表中
    function addSelect(){
    
        var checkList = window.parent.selectMultiUserList.document.userListForm.userID;
        
        if(null != checkList){
            //判断数据是否已经存在于下拉列表中
            var existData = "";
            var obj=document.getElementById("selectUserList");
            for(i=0;i<obj.length;i++){
               existData = existData + "<%=Constant.SELECT_MULTI_USER_SPLIT%>" + obj[i].value + "<%=Constant.SELECT_USERID_USERNAME_SPLIT%>" + obj[i].innerText;               
            }
            //alert(existData);
            
            var existCheck = false;//标志是否选择了记录
            if(null != checkList.length){
                //存在多个radio
                for(var i = 0; i < checkList.length; i++){
                    if(checkList[i].checked){
                        existCheck = true;
                        //alert(checkList[i].value);
                        //判断数据是否已经存在于下拉列表中
                        if(existData.indexOf(checkList[i].value) < 0){
                            //添加数据到下拉列表
                            addSelectUserList(checkList[i].value);
                        }
                        //取消选中
                        checkList[i].checked = false;
                    }
                }
            }else{
                //只有一个radio
                if(checkList.checked){
                    existCheck = true;
                    //alert(checkList.value);
                    //判断数据是否已经存在于下拉列表中
                    if(existData.indexOf(checkList.value) < 0){
                        //添加数据到下拉列表
                        addSelectUserList(checkList.value);
                        //取消选中
                        checkList.checked = false;
                    }
                }
            }
    
            if(!existCheck){
                alert("请从用户列表中选择数据！");
            }else{
                //alert("已将用户信息添加至已选用户列表！");
            }       
        }else{
            //不存在radio
        }
    }
    
    //将用户从列表中删除
    function delSelect(){
        //document.getElementById("ddlResourceType").options.remove(indx);  
        var obj=document.getElementById("selectUserList");
        for(i=0;i<obj.length;i++){
            if(obj[i].selected){
                obj.options.remove(i);
                //break;
                //alert("已将用户信息从已选用户列表中移除！");
            }
        }
        
    }
    
    //增加 selectUserList 下拉列表的值
    //optionValue值类似于 1100002852&&张三
    function addSelectUserList(optionValue){
        
        if(null == optionValue || 0 == optionValue){
            return false;
        }
    
        //解析数据
        var userID;
        var userName;
        var results = optionValue.split("<%=Constant.SELECT_USERID_USERNAME_SPLIT %>");
        userID = results[0];
        userName = results[1];
    
        //将数据增加到下拉列表中
        var oOption = document.createElement("OPTION");  
        oOption.value = userID;  
        oOption.text = userName;  
        document.all.selectUserList.options.add(oOption);
    }
    
    //选择用户
    function selectUser(){
        var result = "";
    
        //根据下拉列表中的数据，构造返回信息
        var obj=document.getElementById("selectUserList");
        for(i=0;i<obj.length;i++){
            result = result + "<%=Constant.SELECT_MULTI_USER_SPLIT%>" + obj[i].value + "<%=Constant.SELECT_USERID_USERNAME_SPLIT%>" + obj[i].innerText;               
        }
        
        if(0 == result.length){
            alert("请选择用户！");
            return false;
        }
    
        if(result.length > 0){
            //截取掉最开始的分割符号
            result = result.substring('<%=Constant.SELECT_MULTI_USER_SPLIT%>'.length);
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
    <s:form name="userListForm" action=""> 
    
    <div id="cnt_search2">
    <div id="cnt_sch2">          
        <ul>
            <li>
                <img onClick="addSelect()" src="${contextPath}/images/bt_add_select.gif" alt="添加所选用户到下拉列表中" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_add_select_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_add_select.gif')" style="cursor:hand">
                &nbsp;
                                                        已选用户：
                <select name="selectUserList" style="width:120">
                </select>
                <img onClick="delSelect()" src="${contextPath}/images/bt_del.gif" alt="删除下拉列表当前所选" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_del_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_del.gif')" style="cursor:hand">
            </li>
            
            <li>
                <img onClick="selectUser()" src="${contextPath}/images/bt_select.gif" alt="选择" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_select_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_select.gif')" style="cursor:hand">
                <img onClick="javacsript:window.close()" src="${contextPath}/images/bt_close.gif" alt="关闭" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'${contextPath}/images/bt_close_over.gif')" onmouseout="changeImg(this,'${contextPath}/images/bt_close.gif')" style="cursor:hand">
            </li>
        </ul>           
    </div>
    </div>
    
    </s:form>
</div>

</body>
</html>