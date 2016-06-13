<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff,com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<head>
<script language="javascript">

	function checkShopsName() {
		var account = $("#account").val().NoSpace();
		if (account != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserName.action?account='
					+ account;
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {},
				dataType : "json",
				success : callBackName
			});
		}
		$("#account").val(account);
	}
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("選擇帳號已被占用，不可用!!!");
			$("#accountAgain").addClass("red");
			$("#verifyName").val("NO");
			$("#account").focus();
		} else {
			$("#accountAgain").html("選擇帳號可用!!!");
			$("#accountAgain").removeClass("red");
			$("#verifyName").val("OK");
		}
	}
	
	function checkSubmit() {
		//add by peter add account validation
		var accountName = $("#account").val();
		var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
		if($.trim(accountName).length==0)
		{
			alert("帳號不能為空！");
			$("#account").focus();
			return false;
		}
		
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			$("#account").focus();
			return false;
		}
		
		//add by peter add password validation
		var password=$("#userPassword").val();
		if(!passwordCheck(password))
		{
			$("#userPassword").focus();
			//alert("false");
			return false;
		}
		
		if ($("#verifyName").val() == "OK" && $("#userPassword").val() !="") {
			//add by peter for 提交时灰化按纽
			$(".btn2").attr("disabled", true);
			//用户密码加密
			$("#userPassword").val($.md5(password).toUpperCase());
			$("#subCreate").submit();
			$("#userPassword").val("");
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			$("#sForm").button();
		}
	}
</script>
<%
String userTypeName = "";
if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){userTypeName="總監";	}
if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType())){userTypeName="分公司";	}
if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType())){userTypeName="股東";	}
if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType())){userTypeName="總代理";	}
if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType())){userTypeName="代理";	}
%>
</head>
<body>
<div id="content">
<s:form id="subCreate" action="%{pageContext.request.contextPath}/user/saveSubAccount.action" method="post"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30%" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="1%"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                <td width="99%" align="left" class="F_bold">&nbsp;<%=userTypeName %>子帳號 -&gt; 新增</td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%">
            </td>
            </tr></table></td>
        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center"><!--修改开始   --><table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt4 king autoHeight">
  <tr>
    <th colspan="2"><strong>基礎資料</strong></th>
  </tr>
  <tr>
    <td width="18%" class="t_right even">子帳號</td>
    <td width="82%" class="t_left">
    <input value="" name="subAccountInfo.account" size="20" maxlength="15" class="b_g" onblur="javascript:checkShopsName();" id="account"/>
    <span id="accountAgain" style="margin-left:5px"></span> <s:hidden id="verifyName" value="" /></td>
  </tr>
  <tr>
    <td class="t_right even">登入密碼</td>
    <td class="t_left"><input value="" type="text" name="subAccountInfo.userPwd" size="20" maxlength="15" class="b_g" id="userPassword"/></td>
  </tr>
  <tr>
    <td class="t_right even">子帳號名稱</td>
    <td class="t_left"><input value="" name="subAccountInfo.chsName" size="20" maxlength="15" class="b_g"/></td>
  </tr>
  
  <%if(isChief==true || isSubChief==true){ %>
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && jszd==null){%>
            <input type="checkbox" name="jszd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="jszd" value="CHIEF_SUB_ROLE_JSZD" id="复选框组1_0" />
        <%}%>
         即時注單
         <%if(isSub==true && odd==null){%>
            <input type="checkbox" name="odd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="odd" value="CHIEF_SUB_ROLE_ODD" id="checkbox9" />
        <%}%>
          操盤權限、輸贏分析 </td>
        <td width="50%">
        <%if(isSub==true && replenishment==null){%>
            <input type="checkbox" name="replenishment" value="" id="checkbox10" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="replenishment" value="CHIEF_SUB_ROLE_REPLENISH" id="checkbox10" />
        <%}%>
          補貨（外補做帳）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && offLineAccount==null){%>
            <input type="checkbox" name="offLineAccount" value="" id="复选框组1_" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="offLineAccount" value="CHIEF_SUB_ROLE_OFFLINE" id="复选框组1_" />
        <%}%>
          下線帳號管理</td>
        <td width="50%">
        <%if(isSub==true && subAccount==null){%>
            <input type="checkbox" name="subAccount" value="" id="复选框组1_2" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="subAccount" value="CHIEF_SUB_ROLE_SUB" id="复选框组1_2" />
        <%}%>
          子帳號管理</td>
      </tr>
      <tr>
        <td>
        <%if(isSub==true && outReplenishManager==null){%>
            <input type="checkbox" name="outReplenishManager" value="" id="checkbox11" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="outReplenishManager" value="CHIEF_SUB_ROLE_OUT_USER_MANAGER" id="checkbox11" />
        <%}%>
          出貨會員管理 </td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td>
        <%if(isSub==true && oddLog==null){%>
            <input type="checkbox" name="oddLog" value="" id="checkbox17" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="oddLog" value="CHIEF_SUB_ROLE_ODD_LOG" id="checkbox17" />
        <%}%>
          每期彩票管理、操盤記錄查詢</td>
        <td>
        <%if(isSub==true && sysInit==null){%>
            <input type="checkbox" name="sysInit" value="" id="checkbox18" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="sysInit" value="CHIEF_SUB_ROLE_SYS_INIT" id="checkbox18" />
        <%}%>
          系統初始設定</td>
      </tr>
      <tr>
        <td>
        <%if(isSub==true && tradingSet==null){%>
            <input type="checkbox" name="tradingSet" value="" id="checkbox19" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="tradingSet" value="CHIEF_SUB_ROLE_TRADING_SET" id="checkbox19" />
        <%}%>
          交易設定、賠率設定 </td>
        <td>
        <%if(isSub==true && message==null){%>
            <input type="checkbox" name="message" value="" id="checkbox20" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="message" value="CHIEF_SUB_ROLE_MESSAGE" id="checkbox20" />
        <%}%>
          站內消息管理</td>
      </tr>
      <tr>
        <td>
        <%if(isSub==true && searchBill==null){%>
            <input type="checkbox" name="searchBill" value="" id="checkbox21" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="searchBill" value="CHIEF_SUB_ROLE_SEARCH_BILL" id="checkbox21" />
        <%}%>
          注單搜索</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50%"><input type="checkbox" name="backsysRole" value="CHIEF_SUB_ROLE_BACKSYS_ROLE" id="checkbox23" disabled="disabled"/>
          系統后臺維護權限</td>
        <td width="50%"><input type="checkbox" name="cancelBill" value="CHIEF_SUB_ROLE_CANCEL_BILL" id="checkbox24" disabled="disabled"/>
          注單取消權限</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && crossReport==null){%>
            <input type="checkbox" name="crossReport" value="" id="复选框组1_3" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="crossReport" value="CHIEF_SUB_ROLE_DELIVERY" id="复选框组1_3" />
        <%}%>
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <%if(isSub==true && classifyReport==null){%>
            <input type="checkbox" name="classifyReport" value="" id="复选框组1_4" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="classifyReport" value="CHIEF_SUB_ROLE_CLASSIFY" id="复选框组1_4" />
        <%}%>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  
  
  <!-- ****************分公司START**************************** -->
  <%}else if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType()) || isSubBranch==true){ %>
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && jszd==null){%>
            <input type="checkbox" name="jszd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="jszd" value="BRANCH_SUB_ROLE_JSZD" id="复选框组1_0" />
        <%}%>
         即時注單 </td>
        <td width="50%">
        	<%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" name="replenishment" value="BRANCH_SUB_ROLE_REPLENISH" id="checkbox10" />
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && offLineAccount==null){%>
            <input type="checkbox" name="offLineAccount" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="offLineAccount" value="BRANCH_SUB_ROLE_OFFLINE" id="复选框组1_" />
        <%}%>	
          下線帳號管理</td>
        <td width="50%">
        <%if(isSub==true && subAccount==null){%>
            <input type="checkbox" name="subAccount" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="subAccount" value="BRANCH_SUB_ROLE_SUB" id="复选框组1_2" />
        <%}%>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && crossReport==null){%>
            <input type="checkbox" name="crossReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="crossReport" value="BRANCH_SUB_ROLE_DELIVERY" id="复选框组1_3" />
        <%}%>	
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <%if(isSub==true && classifyReport==null){%>
            <input type="checkbox" name="classifyReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="classifyReport" value="BRANCH_SUB_ROLE_CLASSIFY" id="复选框组1_4" />
        <%}%>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  <!-- ****************分公司END**************************** -->
  
  
  <%}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType()) || isSubStockholder==true){ %>
  <!-- ****************股東START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && jszd==null){%>
            <input type="checkbox" name="jszd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="jszd" value="STOCKHOLDER_SUB_ROLE_JSZD" id="复选框组1_0" />
        <%}%>
         即時注單 </td>
        <td width="50%">
            <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" name="replenishment" value="STOCKHOLDER_SUB_ROLE_REPLENISH" id="checkbox10" />
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && offLineAccount==null){%>
            <input type="checkbox" name="offLineAccount" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="offLineAccount" value="STOCKHOLDER_SUB_ROLE_OFFLINE" id="复选框组1_" />
        <%}%>
          下線帳號管理</td>
        <td width="50%">
        <%if(isSub==true && subAccount==null){%>
            <input type="checkbox" name="subAccount" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="subAccount" value="STOCKHOLDER_SUB_ROLE_SUB" id="复选框组1_2" />
        <%}%>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && crossReport==null){%>
            <input type="checkbox" name="crossReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="crossReport" value="STOCKHOLDER_SUB_ROLE_DELIVERY" id="复选框组1_3" />
        <%}%>
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <%if(isSub==true && classifyReport==null){%>
            <input type="checkbox" name="classifyReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="classifyReport" value="STOCKHOLDER_SUB_ROLE_CLASSIFY" id="复选框组1_4" />
        <%}%>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  <!-- ****************股東END**************************** -->
  
  
  <%}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType()) || isSubGenAgent==true){ %>
  <!-- ****************總代理START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && jszd==null){%>
            <input type="checkbox" name="jszd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="jszd" value="GEN_AGENT_SUB_ROLE_JSZD" id="复选框组1_0" />
        <%}%>
         即時注單 </td>
         <td width="50%">
         	<%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" name="replenishment" value="GEN_AGENT_SUB_ROLE_REPLENISH" id="checkbox10" />
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && offLineAccount==null){%>
            <input type="checkbox" name="offLineAccount" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="offLineAccount" value="GEN_AGENT_SUB_ROLE_OFFLINE" id="复选框组1_" />
        <%}%>
          下線帳號管理</td>
        <td width="50%">
        <%if(isSub==true && subAccount==null){%>
            <input type="checkbox" name="subAccount" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="subAccount" value="GEN_AGENT_SUB_ROLE_SUB" id="复选框组1_2" />
        <%}%>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && crossReport==null){%>
            <input type="checkbox" name="crossReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="crossReport" value="GEN_AGENT_SUB_ROLE_DELIVERY" id="复选框组1_3" />
        <%}%>
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <%if(isSub==true && classifyReport==null){%>
            <input type="checkbox" name="classifyReport" value="" id="复选框组1_0"  disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="classifyReport" value="GEN_AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_4" />
        <%}%>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  <!-- ****************總代理END**************************** -->
  
  
  <%}else if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType()) || isSubAgent==true){ %>
  <!-- ****************代理START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && jszd==null){%>
            <input type="checkbox" name="jszd" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="jszd" value="AGENT_SUB_ROLE_JSZD" id="复选框组1_0" />
        <%}%>
         即時注單 </td>
        <td width="50%">
       	   <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" name="replenishment" value="AGENT_SUB_ROLE_REPLENISH" id="checkbox10" />
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" name="replenishment" value="" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && offLineAccount==null){%>
            <input type="checkbox" name="offLineAccount" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="offLineAccount" value="AGENT_SUB_ROLE_OFFLINE" id="复选框组1_" />
        <%}%>
          下線帳號管理</td>
        <td width="50%">
        <%if(isSub==true && subAccount==null){%>
            <input type="checkbox" name="subAccount" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="subAccount" value="AGENT_SUB_ROLE_SUB" id="复选框组1_2" />
        <%}%>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <%if(isSub==true && crossReport==null){%>
            <input type="checkbox" name="crossReport" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="crossReport" value="AGENT_SUB_ROLE_DELIVERY" id="复选框组1_3" />
        <%}%>
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <%if(isSub==true && classifyReport==null){%>
            <input type="checkbox" name="classifyReport" value="" id="复选框组1_0" disabled="disabled"/>
        <%}else{ %>
        	<input type="checkbox" name="classifyReport" value="AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_4" />
        <%}%>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  <%} %>
  <!-- ****************代理END**************************** -->
  
  
  <!-- <tr>
    <td class="t_right even">股东占成</td>
    <td class="t_left"><input name="Input3" value="" size="6" maxlength="10" class="b_g"/> 
      % &nbsp;最高可占成&nbsp;0%</td>
  </tr>
  <tr>
    <td class="t_right even">开放盘口</td>
    <td class="t_left">A盘</td>
  </tr> -->
</table>

          <!--  修改结束 --></td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'><input id="ok" type="button" onclick="checkSubmit();" class="btn2" value="确定" />
        <input name="" type="button" class="btn2" value="取消" onclick="javascript:history.go(-1);"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
</s:form>
</div>
</body>
</html>