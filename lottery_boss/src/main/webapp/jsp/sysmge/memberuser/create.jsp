<!-- 
    新增会员用户
-->
<%@ page language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-dojo-tags" prefix="sx"%>

<%@ page import="
    com.npc.lottery.common.Constant 
"%> 

<head>
<title>新增会员用户</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<script>
    //重填
    function resetFunc(){
        createForm.reset();
    }
    
    //保存编辑信息
    function submitFunc(){
        
        if(!checkData()){
            return false;
        }
    
        if(confirm("确认新增用户信息吗？")){
            document.createForm.submit();
            return true;
        }
        return false;
    }
    
    //校验输入的相关数据信息

    function checkData(){
        var account = document.createForm.account.value;
        var chsName = document.createForm.chsName.value;
        var userPwd = document.createForm.userPwd.value;
        var userPwdSec = document.createForm.userPwdSec.value;
        
        if(null == account || 0 == account.length){
            alert("请输入登录帳號！");
            document.createForm.account.focus();
            return false;
        }
        if(null == chsName || 0 == chsName.length){
            alert("请输入中文名！");
            document.createForm.chsName.focus();
            return false;
        }
        if(null == userPwd || 0 == userPwd.length){
            alert("请输入用户密碼！");
            document.createForm.userPwd.focus();
            return false;
        }
        if(userPwd != userPwdSec){
            alert("两次输入的密碼不一致，请重新输入！");
            document.createForm.userPwd.focus();
            return false;
        }
        return true;
    }
    
    /**
     * 选择机构（單选），固定写法

     *
     * idObj    机构ID所对应的 htm 对象
     * nameObj  机构名稱所对应的 htm 对象
     */
    function selectOrg(idObj, nameObj){
        //需要打开的机构URL
        var url = "${contextPath}/sysmge/org/selectMain.action";
        //打开模式窗口
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,400,300);
        
        //解析返回值

        if(null != ret && 0 != ret.length){
            var result = ret.split("<%=Constant.SELECT_ORGID_ORGNAME_SPLIT%>");
            
            var ID = result[0];
            var name = result[1];
            
            eval(idObj).value = ID;
            eval(nameObj).value = name;
        }
    }
    
    //弹出窗口
    function openWin(){
        //需要打开的页面URL，设置为实际值

        var url = "${contextPath}/jsp/demo/openWin.jsp";
        //打开模式窗口，固定写法，不要改变
        var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,400,300);
        
        alert("one");
        //解析返回值

        if(null != ret && 0 != ret.length){
            var result = ret.split("&&");
            
            var ID = result[0];
            var name = result[1];
            
            alert("ID = " + ID);
            alert("name = " + name);
        }
    }
</script>

</head>

<body >

<div id="main">

    <!-- 导航及功能按钮栏 start -->
    <div id="position_nav">
        <div id="position">
           <img src="${contextPath}/images/location.gif" width="15" height="15" align="absmiddle"/>
           		会员用户管理&nbsp;&gt;&nbsp;新增会员用户信息
        </div>
        <div id="position_opt" >
            <ul>
                <li>
                <a href="javascript:void history.go(-1)" > 
                    <img src="${contextPath}/images/bt_back.gif" alt="返回" 
                        name="Image3" height="23" border="0"  
                        onmouseover="changeImg(this,'${contextPath}/images/bt_back_over.gif')" 
                        onmouseout="changeImg(this,'${contextPath}/images/bt_back.gif')"/>
                </a>
                </li>
            </ul>
        </div>
    </div>
    <!-- 导航及功能按钮栏 end -->
    
    <div id="cnt_list">
    <div id="cnt_lt">
        <div id="cnt_tblhead">
            <img src="${contextPath}/images/tit_pic.gif" width="8" height="3" align="absmiddle"/>
             	 新增
        </div>
        
        <div id="cnt_tblcontent">
        <s:form name="createForm" action="/sysmge/memberuser/createSubmit.action">
            <table width="100%"  class="cnt_tblbody">
            <s:iterator value="#request.entity">
            <tr>
                <td width="18%" class="cnt_td">
                   	 用户類型：
                </td>
                <td width="32%">
					会员用户
                </td>
                <td width="18%" class="cnt_td">
      				登录帳號：
                </td>
                <td width="32%">
                    <s:textfield name="account" size="22" title="输入登录帳號"></s:textfield>
                    <font color="#FF0000">*</font>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					中文名：
                </td>
                <td>
                    <s:textfield name="chsName" size="22" title="输入中文名"></s:textfield>
                    <font color="#FF0000">*</font>
                </td>
                <td class="cnt_td">
					英文名：
                </td>
                <td>
                    <s:textfield name="engName" size="22" title="输入英文名"></s:textfield>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					密碼：
                </td>
                <td>
                    <s:password name="userPwd" size="22" title="输入用户密碼"></s:password>
                    <font color="#FF0000">*</font>
                </td>
                <td class="cnt_td">
					确认密碼：
                </td>
                <td>
                    <s:password name="userPwdSec" size="22" title="确认用户密碼"></s:password>
                    <font color="#FF0000">*</font>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					 办公室电话：
                </td>
                <td>
                    <s:textfield name="officePhone" size="22" title="输入办公室电话"></s:textfield>
                </td>
                <td class="cnt_td">
					移动电话：
                </td>
                <td>
                    <s:textfield name="mobilePhone" size="22" title="输入移动电话"></s:textfield>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
					传真：
                </td>
                <td>
                    <s:textfield name="fax" size="22" title="输入传真号"></s:textfield>
                </td>
                <td class="cnt_td">
					Email：
                </td>
                <td>
                    <s:textfield name="emailAddr" size="22" title="输入Email"></s:textfield>
                </td>
            </tr>
            
            <tr>
                <td class="cnt_td">
					創建时间：
                </td>
                <td colspan="3">
                    <s:date name="createDate" format="yyyy-MM-dd"/>
                    <s:hidden name="createDate" value="%{createDate}"/>
                </td>
            </tr>

            <tr>
                <td class="cnt_td">
					备注：
                </td>
                <td colspan="3">
                    <s:textarea name="comments" cols="33" rows="4" title="输入备注信息，请控制在250个字符範圍内"></s:textarea>
                </td>
            </tr>
            </s:iterator>
            </table>
            
            <div id="cnt_tblfoot">
                <img onclick="submitFunc()" src="${contextPath}/images/confirm_but.gif" alt="保存输入的数据" 
                    onmouseover="changeImg(this,'${contextPath}/images/confirm_but_over.gif')" 
                    onmouseout="changeImg(this,'${contextPath}/images/confirm_but.gif')" 
                    style="cursor: hand"/>
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