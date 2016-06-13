<!--
	用户的角色授权：记录所选择的角色数据页面
-->

<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="
    com.npc.lottery.common.Constant,
    com.npc.lottery.sysmge.entity.Roles
"%> 

<html>  
<head>
<title>用户的角色授权</title>
 
<%
    String contextPath = request.getContextPath();
%>

<link href="<%=contextPath%>/css/style.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=contextPath%>/js/public.js"></script>

<script>
	
	//将角色添加到列表中
	function addSelect(){
	    var checkList = window.parent.userRoleSelectList.document.roleListForm.roleID;
	    
	    if(null != checkList){
	        //判断数据是否已经存在于下拉列表中
	        var existData = "";
	        var obj=document.getElementById("selectRoleList");
	        for(i=0;i<obj.length;i++){
	           existData = existData + "<%=Roles.SELECT_MULTI_ROLES_SPLIT%>" + obj[i].value + "<%=Roles.SELECT_ROLEID_ROLENAME_SPLIT%>" + obj[i].innerText;               
	        }
	        //alert(existData);
	        
	        if(null != checkList.length){
	            //存在多个checkbox
	            for(var i = 0; i < checkList.length; i++){
	                if(checkList[i].checked){
	                    //alert(checkList[i].value);
	                    //判断数据是否已经存在于下拉列表中
	                    if(existData.indexOf(checkList[i].value) < 0){
	                        //添加数据到下拉列表
	                        addSelectRoleList(checkList[i].value);
	                    }
	                    //取消选中
	                    //checkList[i].checked = false;
	                }else{
		                //未选中则删除
	                	removeSelectRoleList(checkList[i].value);
	                }
	            }
	        }else{
	            //只有一个checkbox
	            if(checkList.checked){
	                //alert(checkList.value);
	                //判断数据是否已经存在于下拉列表中
	                if(existData.indexOf(checkList.value) < 0){
	                    //添加数据到下拉列表
	                    addSelectRoleList(checkList.value);
	                    //取消选中
	                    //checkList[i].checked = false;
	                }
	            }else{
	            	//未选中则删除
                    removeSelectRoleList(checkList.value);
	            }
	        }

	        alert("已将角色信息添加至已选角色列表！");
	    }else{
	        //不存在radio
	    }
	}
	
	//将角色从列表中删除
	function delSelect(){
	    var obj=document.getElementById("selectRoleList");
	    for(i=0;i<obj.length;i++){
	        if(obj[i].selected){
	            obj.options.remove(i);
	            //break;
	            //alert("已将角色信息从已选角色列表中移除！");
	        }
	    }
	    
	}
	
	//增加 selectRoleList 下拉列表的值
	//optionValue值类似于 1100002852&&角色名稱
	function addSelectRoleList(optionValue){
	    
	    if(null == optionValue || 0 == optionValue){
	        return false;
	    }
	
	    //解析数据
	    var roleID;
	    var roleName;
	    var results = optionValue.split("<%=Roles.SELECT_ROLEID_ROLENAME_SPLIT%>");
	    roleID = results[0];
	    roleName = results[1];
	
	    //将数据增加到下拉列表中
	    var oOption = document.createElement("OPTION");  
	    oOption.value = roleID;  
	    oOption.text = roleName;  
	    document.all.selectRoleList.options.add(oOption);
	}

	//删除 selectRoleList 下拉列表的值
    //optionValue值类似于 1100002852&&角色名稱
	function removeSelectRoleList(optionValue){
        var obj=document.getElementById("selectRoleList");
        var indx = -1;
        for(i=0;i<obj.length;i++){
           if(optionValue == (obj[i].value + "<%=Roles.SELECT_ROLEID_ROLENAME_SPLIT%>" + obj[i].innerText)){
        	    indx = i;
        	    break;
           }              
        }

        if(indx > -1){
            //删除
        	document.getElementById("selectRoleList").options.remove(indx); 
        }
	}
	
	//选择角色
	function authorizUserRoleSubmit(){
	    var result = "";
	
	    //根据下拉列表中的数据，构造返回信息
	    var obj=document.getElementById("selectRoleList");
	    for(i=0;i<obj.length;i++){
	        result = result + "<%=Roles.SELECT_MULTI_ROLES_SPLIT%>" + obj[i].value;               
	    }
	    
	    if(0 == result.length){
	        if(confirm("确定取消该用户的所有角色吗？")){
	        
	        }else{
	        	return false;
	        }
	    }
	
	    if(result.length > 0){
	        //截取掉最开始的分割符号
	        result = result.substring('<%=Roles.SELECT_MULTI_ROLES_SPLIT%>'.length);
	    }
	    
	    parent.document.authorizUserRoleForm.rolesList.value = result;
	    
	    //提交
	    parent.document.authorizUserRoleForm.submit();
	    
	    return true;
	}
	
	//初始化下拉列表
	function initSelectRoleList(){
		var rolesString = "<%=(String)request.getAttribute("rolesGroup")%>";
		var rolesGroup = rolesString.split('<%=Roles.SELECT_MULTI_ROLES_SPLIT%>');
		if(null != rolesGroup && rolesGroup.length > 0){
		    //填充下拉列表
		    for(var i = 0; i < rolesGroup.length; i++){
		    	  addSelectRoleList(rolesGroup[i]);
		    }
		}
	}
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="cnt_search">
<div id="cnt_sch">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td  valign="top" class="td-content">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="90%">
                <img onClick="addSelect()" src="<%=contextPath%>/images/bt_add_select.gif" alt="添加所选角色到下拉列表中" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_add_select_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_add_select.gif')" style="cursor:hand">
                &nbsp;
                                                        已选角色：
                <select name="selectRoleList" style="width:120">
                </select>
                <img onClick="delSelect()" src="<%=contextPath%>/images/bt_del.gif" alt="删除下拉列表当前所选" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_del_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_del.gif')" style="cursor:hand">
            </td>
            <td align="right" width="10%" nowrap="nowrap">
                <img onClick="authorizUserRoleSubmit()" src="<%=contextPath%>/images/bt_affirm.gif" alt="确定" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_affirm_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_affirm.gif')" style="cursor:hand">
                <img onClick="javacsript:window.close()" src="<%=contextPath%>/images/bt_close.gif" alt="关闭" height="23" border="0" align="absbottom" onmouseover="changeImg(this,'<%=contextPath%>/images/bt_close_over.gif')" onmouseout="changeImg(this,'<%=contextPath%>/images/bt_close.gif')" style="cursor:hand">
            </td>
        </tr>
    </table>
    </td>
    </tr>
</table>
</div>
</div>

</body>
<script>
	//自动调用初始化下拉列表方法
	initSelectRoleList();
</script>
</html>