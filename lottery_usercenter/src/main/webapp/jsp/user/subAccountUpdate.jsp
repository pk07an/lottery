<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff,com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ include  file="/jsp/admin/frame/topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<head>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script language="javascript">

	function checkShopsName() {
		var account = $("#account").val();
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
	}
	function callBackName(json) {
		if (json.count > 0) {
			alert("該用户名稱已存在");
			$("#account").val("");
			$("#verifyName").val("NO");
		} else {
			$("#accountAgain").html("");
			$("#verifyName").val("OK");
		}
	}
	
	function checkSubmit() {
		//add by peter add password validation
		var password=$("#userPassword").val();
		if(null !=  password && password != "" && !passwordCheck(password))
		{
			$("#userPassword").focus();
			//alert("false");
			return false;
		}
		//用户密码加密
		if(null !=  password && password != ""){
			//如果用户修改了密码才需要加密
			$("#userPassword").val($.md5(password).toUpperCase());
		}
		$("#subCreate").submit();
		$("#userPassword").val("");
	}
	
	function opValue(vo,obj){
	    if($(vo).attr("checked")=="checked"){
	    	$("input[name='"+obj+"']").val($(vo).val());
		}else{
			$("input[name='"+obj+"']").val("");
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
<s:form id="subCreate" action="%{pageContext.request.contextPath}/user/updateSubAccount.action" method="post"> 
<s:hidden name="subAccountInfo.parentStaffQry"/>
<s:hidden name="subAccountInfo.parentStaffTypeQry"/>
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
                <td width="99%" align="left" class="F_bold">&nbsp;<%=userTypeName %>子帳號 -&gt; 修改（${subAccountInfo.account}）</td>
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
    <td width="82%" class="t_left"><input value="${subAccountInfo.account}" name="subAccountInfo.account" size="20" maxlength="15" class="b_g"  readonly="readonly" id="account"/></td>
    <s:hidden id="verifyName" value="" />
  </tr>
  <tr>
    <td class="t_right even">登入密碼</td>
    <td class="t_left"><input id="userPassword" value="" type="text" name="subAccountInfo.userPwd" size="20" maxlength="15" class="b_g"/></td>
  </tr>
  <tr>
    <td class="t_right even">子帳號名稱<s:property value="userInfo.userType"/></td>
    <td class="t_left"><input value="${subAccountInfo.chsName}" name="subAccountInfo.chsName"  size="20" maxlength="15" class="b_g"/></td>
  </tr>
  
  <s:if test="subAccountInfo.parentUserType==2">  
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="jszd" />
        <s:if test="jszd != null">
            <%if(isSub==true && jszd==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_JSZD" id="复选框组1_0" checked="checked" onchange="opValue(this,'jszd')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && jszd==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_JSZD" id="复选框组1_0" onchange="opValue(this,'jszd')"/>
	         <%}%>
        </s:else>      
         即時注單
        <s:hidden name="odd" />
        <s:if test="odd != null">
            <%if(isSub==true && odd==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_ODD" id="复选框组1_0" checked="checked" onchange="opValue(this,'odd')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && odd==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_ODD" id="复选框组1_0" onchange="opValue(this,'odd')"/>
	         <%}%>
        </s:else> 
          操盤權限、輸贏分析 </td>
        <td width="50%">
        <s:hidden name="replenishment" />
        <s:if test="replenishment != null">
            <%if(isSub==true && replenishment==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_REPLENISH" id="复选框组1_0" checked="checked" onchange="opValue(this,'replenishment')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && replenishment==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_REPLENISH" id="复选框组1_0" onchange="opValue(this,'replenishment')"/>
	         <%}%>
        </s:else> 
          補貨（外補做帳）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="offLineAccount" />
        <s:if test="offLineAccount != null">
            <%if(isSub==true && offLineAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_OFFLINE" id="复选框组1_0" checked="checked" onchange="opValue(this,'offLineAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && offLineAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_OFFLINE" id="复选框组1_0" onchange="opValue(this,'offLineAccount')"/>
	         <%}%>
        </s:else> 
          下線帳號管理</td>
        <td width="50%">
        <s:hidden name="subAccount" />
        <s:if test="subAccount != null">
            <%if(isSub==true && subAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_SUB" id="复选框组1_0" checked="checked" onchange="opValue(this,'subAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && subAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_SUB" id="复选框组1_0" onchange="opValue(this,'subAccount')"/>
	         <%}%>
        </s:else>
          子帳號管理</td>
      </tr>
      <tr>
        <td>
        <s:hidden name="outReplenishManager" />
        <s:if test="outReplenishManager != null">
            <%if(isSub==true && outReplenishManager==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_OUT_USER_MANAGER" id="复选框组1_0" checked="checked" onchange="opValue(this,'outReplenishManager')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && outReplenishManager==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_OUT_USER_MANAGER" id="复选框组1_0" onchange="opValue(this,'outReplenishManager')"/>
	         <%}%>
        </s:else>
          出貨會員管理 </td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td>
        <s:hidden name="oddLog" />
        <s:if test="oddLog != null">
            <%if(isSub==true && oddLog==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_ODD_LOG" id="复选框组1_0" checked="checked" onchange="opValue(this,'oddLog')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && oddLog==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_ODD_LOG" id="复选框组1_0" onchange="opValue(this,'oddLog')"/>
	         <%}%>
        </s:else>
          每期彩票管理、操盤記錄查詢</td>
        <td>
        <s:hidden name="sysInit" />
        <s:if test="sysInit != null">
            <%if(isSub==true && sysInit==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_SYS_INIT" id="复选框组1_0" checked="checked" onchange="opValue(this,'sysInit')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && sysInit==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_SYS_INIT" id="复选框组1_0" onchange="opValue(this,'sysInit')"/>
	         <%}%>
        </s:else>
          系統初始設定</td>
      </tr>
      <tr>
        <td>
        <s:hidden name="tradingSet" />
        <s:if test="tradingSet != null">
            <%if(isSub==true && tradingSet==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_TRADING_SET" id="复选框组1_0" checked="checked" onchange="opValue(this,'tradingSet')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && tradingSet==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_TRADING_SET" id="复选框组1_0" onchange="opValue(this,'tradingSet')"/>
	         <%}%>
        </s:else>
          交易設定、賠率設定 </td>
        <td>
        <s:hidden name="message" />
        <s:if test="message != null">
            <%if(isSub==true && message==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_MESSAGE" id="复选框组1_0" checked="checked" onchange="opValue(this,'message')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && message==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_MESSAGE" id="复选框组1_0" onchange="opValue(this,'message')"/>
	         <%}%>
        </s:else>
          站內消息管理</td>
      </tr>
      <tr>
        <td>
        <s:hidden name="searchBill" />
        <s:if test="searchBill != null">
            <%if(isSub==true && searchBill==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_SEARCH_BILL" id="复选框组1_0" checked="checked" onchange="opValue(this,'searchBill')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && searchBill==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_SEARCH_BILL" id="复选框组1_0" onchange="opValue(this,'searchBill')"/>
	         <%}%>
        </s:else>
          注單搜索</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50%">
        <input type="checkbox" name="backsysRole" value="CHIEF_SUB_ROLE_BACKSYS_ROLE" id="checkbox23" disabled="disabled"/>
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
        <s:hidden name="crossReport" />
        <s:if test="crossReport != null">
            <%if(isSub==true && crossReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_DELIVERY" id="复选框组1_0" checked="checked" onchange="opValue(this,'crossReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && crossReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_DELIVERY" id="复选框组1_0" onchange="opValue(this,'crossReport')"/>
	         <%}%>
        </s:else>
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <s:hidden name="classifyReport" />
        <s:if test="classifyReport != null">
            <%if(isSub==true && classifyReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="CHIEF_SUB_ROLE_CLASSIFY" id="复选框组1_0" checked="checked" onchange="opValue(this,'classifyReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && classifyReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="CHIEF_SUB_ROLE_CLASSIFY" id="复选框组1_0" onchange="opValue(this,'classifyReport')"/>
	         <%}%>
        </s:else>
         <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
   </s:if>  
  
  <!-- ****************分公司START**************************** -->
  <s:if test="subAccountInfo.parentUserType==3">
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="jszd" />
        <s:if test="jszd != null">
            <%if(isSub==true && jszd==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_JSZD" id="复选框组1_0" checked="checked" onchange="opValue(this,'jszd')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && jszd==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="BRANCH_SUB_ROLE_JSZD" id="复选框组1_0" onchange="opValue(this,'jszd')"/>
	         <%}%>
        </s:else>      
         即時注單 </td>
        <td width="50%">
        <s:hidden name="replenishment" />
        <s:if test="replenishment != null">
            <%if(isSub==true && replenishment==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_REPLENISH" id="复选框组1_0" checked="checked" onchange="opValue(this,'replenishment')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" value="BRANCH_SUB_ROLE_REPLENISH" id="复选框组1_0" onchange="opValue(this,'replenishment')"/>
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
        </s:else> 
         手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="offLineAccount" />
        <s:if test="offLineAccount != null">
            <%if(isSub==true && offLineAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_OFFLINE" id="复选框组1_0" checked="checked" onchange="opValue(this,'offLineAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && offLineAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="BRANCH_SUB_ROLE_OFFLINE" id="复选框组1_0" onchange="opValue(this,'offLineAccount')"/>
	         <%}%>
        </s:else>
          下線帳號管理</td>
        <td width="50%">
        <s:hidden name="subAccount" />
        <s:if test="subAccount != null">
            <%if(isSub==true && subAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_SUB" id="复选框组1_0" checked="checked" onchange="opValue(this,'subAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && subAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="BRANCH_SUB_ROLE_SUB" id="复选框组1_0" onchange="opValue(this,'subAccount')"/>
	         <%}%>
        </s:else>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="crossReport" />
        <s:if test="crossReport != null">
            <%if(isSub==true && crossReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_DELIVERY" id="复选框组1_0" checked="checked" onchange="opValue(this,'crossReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && crossReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="BRANCH_SUB_ROLE_DELIVERY" id="复选框组1_0" onchange="opValue(this,'crossReport')"/>
	         <%}%>
        </s:else>        
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <s:hidden name="classifyReport" />
        <s:if test="classifyReport != null">
            <%if(isSub==true && classifyReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="BRANCH_SUB_ROLE_CLASSIFY" id="复选框组1_0" checked="checked" onchange="opValue(this,'classifyReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && classifyReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="BRANCH_SUB_ROLE_CLASSIFY" id="复选框组1_0" onchange="opValue(this,'classifyReport')"/>
	         <%}%>
        </s:else>
         <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  </s:if> 
  <!-- ****************分公司END**************************** -->
  
  
  <s:if test="subAccountInfo.parentUserType==4">  
  <!-- ****************股東START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
            <s:hidden name="jszd" />
	        <s:if test="jszd != null">
	            <%if(isSub==true && jszd==null){%>
	                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
	            <%}else{ %>
	            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_JSZD" id="复选框组1_0" checked="checked" onchange="opValue(this,'jszd')"/>
	            <%}%>	
	        </s:if>
	        <s:else>
		        <%if(isSub==true && jszd==null){%>
		           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_JSZD" id="复选框组1_0" onchange="opValue(this,'jszd')"/>
		         <%}%>
	        </s:else>
         即時注單 </td>
        <td width="50%">
			<s:hidden name="replenishment" />
	        <s:if test="replenishment != null">
	            <%if(isSub==true && replenishment==null){%>
	                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
	            <%}else{ %>
	            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_REPLENISH" id="复选框组1_0" checked="checked" onchange="opValue(this,'replenishment')"/>
	            <%}%>	
	        </s:if>
	        <s:else>
		        <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
		          if(Constant.OPEN.equals(replenishMentMain)){%>
		             <%if(isSub==true && replenishment==null){%>
			           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
			         <%}else{ %>
			           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_REPLENISH" id="复选框组1_0" onchange="opValue(this,'replenishment')"/>
			         <%}%>
		        <%}else{%>
		             <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
		        <%} %>
	        </s:else>        
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="offLineAccount" />
        <s:if test="offLineAccount != null">
            <%if(isSub==true && offLineAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_OFFLINE" id="复选框组1_0" checked="checked" onchange="opValue(this,'offLineAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && offLineAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_OFFLINE" id="复选框组1_0" onchange="opValue(this,'offLineAccount')"/>
	         <%}%>
        </s:else>
          下線帳號管理</td>
        <td width="50%">
        <s:hidden name="subAccount" />
        <s:if test="subAccount != null">
            <%if(isSub==true && subAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_SUB" id="复选框组1_0" checked="checked" onchange="opValue(this,'subAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && subAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_SUB" id="复选框组1_0" onchange="opValue(this,'subAccount')"/>
	         <%}%>
        </s:else>
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="crossReport" />
        <s:if test="crossReport != null">
            <%if(isSub==true && crossReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_DELIVERY" id="复选框组1_0" checked="checked" onchange="opValue(this,'crossReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && crossReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_DELIVERY" id="复选框组1_0" onchange="opValue(this,'crossReport')"/>
	         <%}%>
        </s:else>        
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <s:hidden name="classifyReport" />
        <s:if test="classifyReport != null">
            <%if(isSub==true && classifyReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="STOCKHOLDER_SUB_ROLE_CLASSIFY" id="复选框组1_0" checked="checked" onchange="opValue(this,'classifyReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && classifyReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="STOCKHOLDER_SUB_ROLE_CLASSIFY" id="复选框组1_0" onchange="opValue(this,'classifyReport')"/>
	         <%}%>
        </s:else>
         <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  </s:if> 
  <!-- ****************股東END**************************** -->
  
  
  <s:if test="subAccountInfo.parentUserType==5">  
  <!-- ****************總代理START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="jszd" />
        <s:if test="jszd != null">
            <%if(isSub==true && jszd==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_JSZD" id="复选框组1_0" checked="checked" onchange="opValue(this,'jszd')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && jszd==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_JSZD" id="复选框组1_0" onchange="opValue(this,'jszd')"/>
	         <%}%>
        </s:else>
         即時注單 </td>
        <td width="50%">
        <s:hidden name="replenishment" />
        <s:if test="replenishment != null">
            <%if(isSub==true && replenishment==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_REPLENISH" id="复选框组1_0" checked="checked" onchange="opValue(this,'replenishment')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_REPLENISH" id="复选框组1_0" onchange="opValue(this,'replenishment')"/>
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
        </s:else>     
          手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
		<s:hidden name="offLineAccount" />
        <s:if test="offLineAccount != null">
            <%if(isSub==true && offLineAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_OFFLINE" id="复选框组1_0" checked="checked" onchange="opValue(this,'offLineAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && offLineAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_OFFLINE" id="复选框组1_0" onchange="opValue(this,'offLineAccount')"/>
	         <%}%>
        </s:else>        
          下線帳號管理</td>
        <td width="50%">
		<s:hidden name="subAccount" />
        <s:if test="subAccount != null">
            <%if(isSub==true && subAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_SUB" id="复选框组1_0" checked="checked" onchange="opValue(this,'subAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && subAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_SUB" id="复选框组1_0" onchange="opValue(this,'subAccount')"/>
	         <%}%>
        </s:else>        
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
		<s:hidden name="crossReport" />
        <s:if test="crossReport != null">
            <%if(isSub==true && crossReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_DELIVERY" id="复选框组1_0" checked="checked" onchange="opValue(this,'crossReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && crossReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_DELIVERY" id="复选框组1_0" onchange="opValue(this,'crossReport')"/>
	         <%}%>
        </s:else>                
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <s:hidden name="classifyReport" />
        <s:if test="classifyReport != null">
            <%if(isSub==true && classifyReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="GEN_AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_0" checked="checked" onchange="opValue(this,'classifyReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && classifyReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="GEN_AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_0" onchange="opValue(this,'classifyReport')"/>
	         <%}%>
        </s:else>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  </s:if> 
  <!-- ****************總代理END**************************** -->
  
  
  <s:if test="subAccountInfo.parentUserType==6">  
  <!-- ****************代理START**************************** -->
  <tr>
    <td rowspan="4" class="t_right even">各權限設定</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="jszd" />
        <s:if test="jszd != null">
            <%if(isSub==true && jszd==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_JSZD" id="复选框组1_0" checked="checked" onchange="opValue(this,'jszd')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && jszd==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="AGENT_SUB_ROLE_JSZD" id="复选框组1_0" onchange="opValue(this,'jszd')"/>
	         <%}%>
        </s:else>
         即時注單 </td>
        <td width="50%">
        <s:hidden name="replenishment" />
        <s:if test="replenishment != null">
            <%if(isSub==true && replenishment==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_REPLENISH" id="复选框组1_0" checked="checked" onchange="opValue(this,'replenishment')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%String replenishMentMain = request.getAttribute("replenishMent").toString(); 
	          if(Constant.OPEN.equals(replenishMentMain)){%>
	             <%if(isSub==true && replenishment==null){%>
		           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
		         <%}else{ %>
		           <input type="checkbox" value="AGENT_SUB_ROLE_REPLENISH" id="复选框组1_0" onchange="opValue(this,'replenishment')"/>
		         <%}%>
	        <%}else{%>
	             <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	        <%} %>
        </s:else>     
         手工補貨、自動補貨設定（及變更記錄）</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="offLineAccount" />
        <s:if test="offLineAccount != null">
            <%if(isSub==true && offLineAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_OFFLINE" id="复选框组1_0" checked="checked" onchange="opValue(this,'offLineAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && offLineAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="AGENT_SUB_ROLE_OFFLINE" id="复选框组1_0" onchange="opValue(this,'offLineAccount')"/>
	         <%}%>
        </s:else>    
          下線帳號管理</td>
        <td width="50%">
        <s:hidden name="subAccount" />
        <s:if test="subAccount != null">
            <%if(isSub==true && subAccount==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_SUB" id="复选框组1_0" checked="checked" onchange="opValue(this,'subAccount')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && subAccount==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="AGENT_SUB_ROLE_SUB" id="复选框组1_0" onchange="opValue(this,'subAccount')"/>
	         <%}%>
        </s:else>  
          子帳號管理</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_left"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="none_border">
      <tr>
        <td width="50%">
        <s:hidden name="crossReport" />
        <s:if test="crossReport != null">
            <%if(isSub==true && crossReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_DELIVERY" id="复选框组1_0" checked="checked" onchange="opValue(this,'crossReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && crossReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="AGENT_SUB_ROLE_DELIVERY" id="复选框组1_0" onchange="opValue(this,'crossReport')"/>
	         <%}%>
        </s:else>   
          <%=userTypeName %>交收報表</td>
        <td width="50%">
        <s:hidden name="classifyReport" />
        <s:if test="classifyReport != null">
            <%if(isSub==true && classifyReport==null){%>
                <input type="checkbox" id="复选框组1_0" checked="checked" disabled="disabled"/>
            <%}else{ %>
            	<input type="checkbox" value="AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_0" checked="checked" onchange="opValue(this,'classifyReport')"/>
            <%}%>	
        </s:if>
        <s:else>
	        <%if(isSub==true && classifyReport==null){%>
	           <input type="checkbox" id="复选框组1_0" disabled="disabled"/>
	         <%}else{ %>
	           <input type="checkbox" value="AGENT_SUB_ROLE_CLASSIFY" id="复选框组1_0" onchange="opValue(this,'classifyReport')"/>
	         <%}%>
        </s:else>
          <%=userTypeName %>分類報表</td>
      </tr>
    </table></td>
  </tr>
  </s:if>
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
        <td align='center'><input name="" type="button" onclick="checkSubmit();" class="btn2" value="确定" />
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