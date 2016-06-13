
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.collections.MapUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.npc.lottery.common.Constant,com.npc.lottery.sysmge.entity.SessionStatInfo,com.npc.lottery.util.SpringUtil,com.npc.lottery.service.OnlineMemberService"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%ManagerUser userInfoT = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		Map<String,String> map = SessionStatInfo.inLineNumByUserType(userInfoT.getSafetyCode());
		
		OnlineMemberService onlineMemberService = (OnlineMemberService)SpringUtil.getBean("onlineMemberService");
		Map<String, Date> onlineMemberMap = onlineMemberService.getOnlineMember(userInfoT.getShopsInfo().getShopsCode());
		int onlineMemberSize = MapUtils.isEmpty(onlineMemberMap)?0:onlineMemberMap.size();
	   request.setAttribute("onlineMemberSize", onlineMemberSize);
		%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base target="content">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/top.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" />
<script language="javascript" src="${pageContext.request.contextPath}/js/UFO.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
	/**
	 *	明细信息列表
	 */
	function openRealTimeDetail(infoLink){
		//alert(infoLink.href);
		//需要打开的页面URL，设置为实际值
		var url = infoLink.href;
		var height=800;
        var width=1006
        //设置窗体居中
        var top = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
        var left = (window.screen.availWidth-10-width)/2; 
        var style="height="+height+", width="+width+", top="+top+",left="+left+", toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no";
        window.open(url,'',style);
		//打开模式窗口，固定写法，不要改变
// 		var ret = openModalDialogReport(url,'',1006,800);
		
		return false;
	}
	
</script>

<%@ include  file="topSub.jsp"%><!--加载判断用户信息，包括子帐号的信息判断  -->

</head>
<body oncopy="return false" oncut="return false" onselectstart="return false">
<!-- 退出页面 -->
<form name="exitForm" method="post" action="${pageContext.request.contextPath}/user/logoutManager.do" target="_parent">
	
</form>
<div id="container">
<table width="100%" height="67" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="219"><img src="${pageContext.request.contextPath}/images/admin/shoplogo/<%=userInfo.getShopsInfo().getShopsCode()%>.jpg" width="219" height="67"></td>
    <td>
        <div id="scrollnote">
          <div id="scrollleft">
          <%if(isChief==true || isSubChief==true){ %>
          <strong>线上：<%=map.get("manager") %>/${onlineMemberSize}</strong>
          <%} %>
          &nbsp;</div>
          <div id="scrollright"><strong><%=usreType%>：<%=userInfo.getAccount() %></strong></div>
          <div id="scrollcenter">
             <s:action name="marquee" executeResult="true" flush="false" namespace="/admin"/>
          </div>
        </div>
        <div class="menu">
       
		     <%
		     if(isFreeze==false){
		     if(isSub){
		        if(jszd !=null){%>
		           <a href="${pageContext.request.contextPath}/replenish/gdReplenishEnter.action?subType=<s:property value="subType"/>" id="replensh" target="topframe">即時注單</a>  
		       <% } 
		     }else{%>
		         <a href="${pageContext.request.contextPath}/replenish/gdReplenishEnter.action?subType=<s:property value="subType"/>" id="replensh" target="topframe">即時注單</a>
		    <% }}
		     if(isFreeze==false){
		      if(isSub){
		        if(offLineAccount !=null){%>
		 	    <a href="${pageContext.request.contextPath}/admin/getUserMenu.action?subType=<s:property value="subType"/>" id="userManagement" target="topframe">用户管理</a>   
		 		<% } 
		     }else{%>
		 	    <a href="${pageContext.request.contextPath}/admin/getUserMenu.action?subType=<s:property value="subType"/>" id="userManagement" target="topframe">用户管理</a>   
		 	 <%}}%>
		      
		       <a href="${pageContext.request.contextPath}/admin/getPersonalAdminMenu.action?subType=<s:property value="subType"/>" id="personManagement" target="topframe">個人管理</a>
		      
		      <%if(isFreeze==false){
			      if(isSys || isManager || isChief || oddLog!=null || searchBill!=null || sysInit!=null || tradingSet!=null || message!=null)
				  	 {%>
				  	  
				  	  <a href="${pageContext.request.contextPath}/admin/getInManageMenu.action?subType=<s:property value="subType"/>" id="innerManagement" target="topframe">內部管理</a>  	  
				  <% }}
		  	       if(crossReport !=null || classifyReport!=null  || isChief || isBranch || isStockholder || isGenAgent || isAgent){%>
		 	    <a href="${pageContext.request.contextPath}/statreport/statReportSearch.action?subType=<s:property value="subType"/>" id="report" target="mainFrame">報表查询</a>   
		 		<%}%>
	  	 
		     
		      <a href="${pageContext.request.contextPath}/admin/enterLotResultHistoryAdmin.action?subType=<s:property value="subType"/>" id="historyLottery" target="mainFrame">歷史開獎</a>
		      <a href="${pageContext.request.contextPath}/user/queryAllMessage.action?type=message?subType=<s:property value="subType"/>" id="siteMessage" target="mainFrame">站內消息</a>
		      <a href="#" onClick="exit();return false;">退出</a>
      
  		</div>
     </td>
  </tr>
</table>
<div class="head_col">
<div class="Tab_col">
	<%if(isFreeze==false){
     if(isSub){
        if(jszd !=null){%>
			<a target="topframe" href="${pageContext.request.contextPath}/replenish/gdReplenishEnter.action" <s:if test="subType.indexOf('GDKLSF')!=-1 || subType=='' || subType==null">class="tab_1_s"</s:if><s:else>class="tab_1"</s:else>>广东快乐十分</a><a target="topframe" href="${pageContext.request.contextPath}/replenish/cqReplenishEnter.action" <s:if test="subType.indexOf('CQSSC')!=-1">class="tab_2_s"</s:if><s:else>class="tab_2"</s:else>>重庆时时彩</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/bjReplenishEnter.action" <s:if test="subType.indexOf('BJ')!=-1">class="tab_3_s"</s:if><s:else>class="tab_3"</s:else>>北京赛车(PK10)</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/k3ReplenishEnter.action" <s:if test="subType.indexOf('K3')!=-1">class="tab_4_s"</s:if><s:else>class="tab_4"</s:else>>江苏骰寶(快3)</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/ncReplenishEnter.action" <s:if test="subType.indexOf('NC')!=-1">class="tab_5_s"</s:if><s:else>class="tab_5"</s:else>>幸运农场</a></div>
       <% } 
     }else{%>
			<a target="topframe" href="${pageContext.request.contextPath}/replenish/gdReplenishEnter.action" <s:if test="subType.indexOf('GDKLSF')!=-1 || subType=='' || subType==null">class="tab_1_s"</s:if><s:else>class="tab_1"</s:else>>广东快乐十分</a><a target="topframe" href="${pageContext.request.contextPath}/replenish/cqReplenishEnter.action" <s:if test="subType.indexOf('CQSSC')!=-1">class="tab_2_s"</s:if><s:else>class="tab_2"</s:else>>重庆时时彩</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/bjReplenishEnter.action" <s:if test="subType.indexOf('BJ')!=-1">class="tab_3_s"</s:if><s:else>class="tab_3"</s:else>>北京赛车(PK10)</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/k3ReplenishEnter.action" <s:if test="subType.indexOf('K3')!=-1">class="tab_4_s"</s:if><s:else>class="tab_4"</s:else>>江苏骰寶(快3)</a><a  target="topframe" href="${pageContext.request.contextPath}/replenish/ncReplenishEnter.action" <s:if test="subType.indexOf('NC')!=-1">class="tab_5_s"</s:if><s:else>class="tab_5"</s:else>>幸运农场</a></div>
    <% }}%>
<!-- 內部管理 -->
<s:if test="subMenu=='inManage'">
	  <div class="col_link">
	  <%if(oddLog!=null || isChief){%>
	  <a href="${pageContext.request.contextPath}/admin/playOddsLogAction.action?type=privateAdmin_OddLog"  target="mainFrame"<s:if test="type=='privateAdmin_OddLog'">class="current"</s:if>>操盤記錄</a><span style="color:#F8FF2D">|</span>
	  <%} %>
	  <%if(searchBill!=null || isChief){%>
	  <a href="${pageContext.request.contextPath}/admin/searchBill.action?type=privateAdmin_BetSearch"  target="mainFrame"<s:if test="type=='privateAdmin_BetSearch'">class="current"</s:if>>注單搜索</a><span style="color:#F8FF2D">|</span>
	  <%} %>
	  <%if(sysInit!=null || isChief){%>
	  <a href="${pageContext.request.contextPath}/admin/systemConfig.action?type=privateAdmin_SystemSet"  target="mainFrame"<s:if test="type=='privateAdmin_SystemSet'">class="current"</s:if> >系統初始設定</a><span style="color:#F8FF2D">|</span>
	  <%} %>
	  <%if(tradingSet!=null || isChief){%>
	  <a href="${pageContext.request.contextPath}/admin/enterTradingSet.action?type=privateAdmin_TradingSet&&subType=<s:property value="subType"/>"  target="mainFrame"<s:if test="type=='privateAdmin_TradingSet'">class="current"</s:if> >交易設定</a><span style="color:#F8FF2D">|</span>
	  <a href="${pageContext.request.contextPath}/admin/enterOpenOdds.action?type=privateAdmin_OddSet&&subType=<s:property value="subType"/>"  target="mainFrame"<s:if test="type=='privateAdmin_OddSet'">class="current"</s:if> >賠率設定</a><span style="color:#F8FF2D">|</span>
	  <%} %>
	  <%if(message!=null || isChief){%>
	  <a href="${pageContext.request.contextPath}/admin/queryAllMessage.action?type=privateAdmin_SiteMessage"  target="mainFrame"<s:if test="type=='privateAdmin_SiteMessage'">class="current"</s:if> >站內消息管理</a>
	  <%} %>
	  </div>
</s:if>
<!-- 用戶管理 -->
<s:if test="subMenu=='user'">
	  <div class="col_link">
	  <% 
       if(isSys || isManager)
       {%>
           
            <a href="${pageContext.request.contextPath}/user/queryChiefStaff.action" style="background:none">總监</a>
   	  	  <a href="${pageContext.request.contextPath}/user/queryBranchStaff.action?type=userBranch"  target="mainFrame"<s:if test="type=='userBranch'">class="current"</s:if>>分公司</a><span style="color:#F8FF2D">|</span>
   	  	  <a href="${pageContext.request.contextPath}/user/queryStockholder.action?type=userStockholder"  target="mainFrame"<s:if test="type=='userStockholder'">class="current"</s:if>>股東</a><span style="color:#F8FF2D">|</span>
   	  	  <a href="${pageContext.request.contextPath}/user/queryGenAgentStaff.action?type=userGenAgent"  target="mainFrame"<s:if test="type=='userGenAgent'">class="current"</s:if>>總代理</a><span style="color:#F8FF2D">|</span>
   	  	  <a href="${pageContext.request.contextPath}/user/queryAgentStaff.action?type=userAgent"  target="mainFrame"<s:if test="type=='userAgent'">class="current"</s:if>>代理</a><span style="color:#F8FF2D">|</span>
   	  	  <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
   	  	  <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
      <% }    
     else if(isSys || isManager || isChief || isSubChief)
    {
    	if(isSys || isManager || isChief || offLineAccount != null){%>
    	     <a href="${pageContext.request.contextPath}/user/queryBranchStaff.action?type=userBranch"  target="mainFrame"<s:if test="type=='userBranch'">class="current"</s:if>>分公司</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryStockholder.action?type=userStockholder"  target="mainFrame"<s:if test="type=='userStockholder'">class="current"</s:if>>股東</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryGenAgentStaff.action?type=userGenAgent"  target="mainFrame"<s:if test="type=='userGenAgent'">class="current"</s:if>>總代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryAgentStaff.action?type=userAgent"  target="mainFrame"<s:if test="type=='userAgent'">class="current"</s:if>>代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
	  	 <% if(subAccount!=null || isChief){%>
	  	     <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a><span style="color:#F8FF2D">|</span>
	  	<%}%>
    	<%}else{%>
    	    <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
    	<%}
    	if(outReplenishManager!=null || isChief){%>
	  	     <a href="${pageContext.request.contextPath}/user/queryUserOutReplenish.action?type=userOutReplenish"  target="mainFrame">出貨會員</a>
    	<%}
	  }else if(isBranch || isSubBranch)
    {
        if(isBranch || offLineAccount != null){%>
         <a href="${pageContext.request.contextPath}/user/queryStockholder.action?type=userStockholder"  target="mainFrame"<s:if test="type=='userStockholder'">class="current"</s:if>>股東</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryGenAgentStaff.action?type=userGenAgent"  target="mainFrame"<s:if test="type=='userGenAgent'">class="current"</s:if>>總代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryAgentStaff.action?type=userAgent"  target="mainFrame"<s:if test="type=='userAgent'">class="current"</s:if>>代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
	  	  <% if(subAccount!=null || isBranch){%>
	  	     <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
	  	<%}%>
       <% }else{%>
	    <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
		<%}
    %>
    <%}
    else if(isStockholder || isSubStockholder)
    {
        if(isStockholder || offLineAccount != null){%>
        	<a href="${pageContext.request.contextPath}/user/queryGenAgentStaff.action?type=userGenAgent"  target="mainFrame"<s:if test="type=='userGenAgent'">class="current"</s:if>>總代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryAgentStaff.action?type=userAgent"  target="mainFrame"<s:if test="type=='userAgent'">class="current"</s:if>>代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
	  	  <% if(subAccount!=null || isStockholder){%>
	  	     <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
	  	<%}%>
        <%}else if(subAccount !=null){%>
	    <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
		<%}
    %>
   <%}
    else if(isGenAgent || isSubGenAgent)
    {
        if(isGenAgent || offLineAccount !=null){%>
         <a href="${pageContext.request.contextPath}/user/queryAgentStaff.action?type=userAgent"  target="mainFrame"<s:if test="type=='userAgent'">class="current"</s:if>>代理</a><span style="color:#F8FF2D">|</span>
	  	  <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
	  	  <% if(subAccount!=null || isGenAgent){%>
	  	     <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
	  	<%}%>
        <%}else if(subAccount !=null){%>
		    <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
		<%}
    %>
   <%} 
    else if(isAgent || isSubAgent)
    {
        if(isAgent || offLineAccount != null){%>
        	 <a href="${pageContext.request.contextPath}/user/queryMemberStaff.action?type=userMember"  target="mainFrame"<s:if test="type=='userMember'">class="current"</s:if>>會員</a><span style="color:#F8FF2D">|</span>
		  	 <% if(subAccount!=null || isAgent){%>
		  	     <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
		  	<%}%>
        <%}else{%>
	    <a href="${pageContext.request.contextPath}/user/querySubAccount.action?type=userSubMember"  target="mainFrame"<s:if test="type=='userSubMember'">class="current"</s:if>>子帳號</a>
		<%}
    %>
   <%}                       
    
    %>
	  </div>
</s:if>
<s:if test="subMenu=='personalAdmin'">
	  <div class="col_link">
	  <%if(isSys || isManager || isChief)
      {%>
	  <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a><span style="color:#F8FF2D">|</span>
	  <a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else if(isSubChief){%>
	    <%-- <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a> --%>
	    <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
    	<a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else if(isSubBranch){%>
	    <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a>
	    <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
	    <a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else if(isSubStockholder){%>
	    <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a>
	    <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
    	<a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else if(isSubGenAgent){%>
	    <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a>
	    <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
		<a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else if(isSubAgent){%>
	    <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a>
	    <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
		<a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
	<%}else{%>  
          <a href="${pageContext.request.contextPath}/user/queryCreditInformation.action?type=personalAdmin_Credit"  target="mainFrame"<s:if test="type=='personalAdmin_Credit'">class="current"</s:if> >信用資料</a>
		  <a href="${pageContext.request.contextPath}/admin/loginLog.action?type=personalAdmin_loginLog"  target="mainFrame"<s:if test="type=='personalAdmin_loginLog'">class="current"</s:if> >登錄日誌</a>
		  <a href="${pageContext.request.contextPath}/user/updateFindPassword.action?type=personalAdmin_PasswordChage"  target="mainFrame"<s:if test="type=='personalAdmin_PasswordChage'">class="current"</s:if> >變更密碼</a> 
      <%}%>
      <%if(isFreeze==false){
	      if(isSub){
	    	  if(replenishment !=null && !isSubChief && !isChief){
	      %>
			  <a href="${pageContext.request.contextPath}/replenishauto/autoReplenish.action?type=personalAdmin_autoReplenish"  target="mainFrame"<s:if test="type=='personalAdmin_autoReplenish'">class="current"</s:if> >自動補貨設定</a>
			  <a href="${pageContext.request.contextPath}/replenishauto/autoReplenishLog.action?type=personalAdmin_autoReplenishLog"  target="mainFrame"<s:if test="type=='personalAdmin_autoReplenishLog'">class="current"</s:if> >自動補貨變更記錄</a>
	      <%  }
	        }else if(!isSubChief && !isChief){
	        	    String m = request.getAttribute("mReplenishMent").toString();
	                if(m!=null && Constant.OPEN.equals(m)){%>
			  <a href="${pageContext.request.contextPath}/replenishauto/autoReplenish.action?type=personalAdmin_autoReplenish"  target="mainFrame"<s:if test="type=='personalAdmin_autoReplenish'">class="current"</s:if> >自動補貨設定</a>
			  <a href="${pageContext.request.contextPath}/replenishauto/autoReplenishLog.action?type=personalAdmin_autoReplenishLog"  target="mainFrame"<s:if test="type=='personalAdmin_autoReplenishLog'">class="current"</s:if> >自動補貨變更記錄</a>
	      <%        }
	        }}%>  
	  </div>
</s:if>

<!-- 补货 -->
<s:if test="subMenu=='GDKLSFReplenish' || subMenu==null">
	  <div class="col_link">
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST==subType'>class="current"</s:if>>第一球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND==subType'>class="current"</s:if>>第二球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD==subType'>class="current"</s:if>>第三球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH==subType'>class="current"</s:if>>第四球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH==subType'>class="current"</s:if>>第五球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH==subType'>class="current"</s:if>>第六球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH==subType'>class="current"</s:if>>第七球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH==subType'>class="current"</s:if>>第八球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_DRAGON==subType'>class="current"</s:if>>總和、龍虎</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>&&finalPLayType=GDKLSF_STRAIGHTTHROUGH_RX2" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH==subType'>class="current"</s:if>>連碼</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/queryZhangdanReplenish.action?subType=<%=Constant.LOTTERY_TYPE_GDKLSF%>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_GDKLSF_SUBTYPE_ZHANGDAN==subType'>class="current"</s:if>class="special">帳單</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/backupDetailCheck.action?subType=<%=Constant.LOTTERY_TYPE_GDKLSF%>" class="special">備份</a><span style="color:#f8ff2d">|</span>
		<%if(isSys || isManager || isChief || isSubChief){%>
	  		  <a onclick="return openRealTimeDetail(this)" href="${pageContext.request.contextPath}/replenish/realTimeDetail.action?subType=<%=Constant.LOTTERY_TYPE_GDKLSF%>" title="系統實時滾單">實時滾單</a>
	    <%}%>
	  </div>
</s:if>
<s:if test="subMenu=='CQSSCReplenish'">
	  <div class="col_link">
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_FIRST==subType'>class="current"</s:if>>總項盤口</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/queryZhangdanReplenish.action?subType=<%=Constant.LOTTERY_TYPE_CQSSC%>" class="special">帳單</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/backupDetailCheck.action?subType=<%=Constant.LOTTERY_TYPE_CQSSC%>" class="special">備份</a><span style="color:#f8ff2d">|</span>
	    <%if(isSys || isManager || isChief || isSubChief){%>
	    <a onclick="return openRealTimeDetail(this)" href="${pageContext.request.contextPath}/replenish/realTimeDetail.action?subType=<%=Constant.LOTTERY_TYPE_CQSSC%>" title="系統實時滾單">實時滾單</a>
	    <%}%>
	  </div>
</s:if>
<s:if test="subMenu=='BJSCReplenish'">
	  <div class="col_link">
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>" >冠、亞軍組合</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD%>" >三、四、伍、六名</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH%>" >七、八、九、十名</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/queryZhangdanReplenish.action?subType=<%=Constant.LOTTERY_TYPE_BJ%>" class="special">帳單</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/backupDetailCheck.action?subType=<%=Constant.LOTTERY_TYPE_BJ%>" class="special">備份</a><span style="color:#f8ff2d">|</span>
	    <%if(isSys || isManager || isChief || isSubChief){%>
	    <a onclick="return openRealTimeDetail(this)" href="${pageContext.request.contextPath}/replenish/realTimeDetail.action?subType=<%=Constant.LOTTERY_TYPE_BJ%>" title="系統實時滾單">實時滾單</a>
	    <%}%>
	  </div>
</s:if>
<s:if test="subMenu=='K3Replenish'">
	  <div class="col_link">
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_K3_SUBTYPE_BALL %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_K3_SUBTYPE_BALL==subType'>class="current"</s:if>>總項盤口</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/queryZhangdanReplenish.action?subType=<%=Constant.LOTTERY_TYPE_K3%>" class="special">帳單</a><span style="color:#f8ff2d">|</span>
	    <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/backupDetailCheck.action?subType=<%=Constant.LOTTERY_TYPE_K3%>" class="special">備份</a><span style="color:#f8ff2d">|</span>
	    <%if(isSys || isManager || isChief || isSubChief){%>
	    <a onclick="return openRealTimeDetail(this)" href="${pageContext.request.contextPath}/replenish/realTimeDetail.action?subType=<%=Constant.LOTTERY_TYPE_K3%>" title="系統實時滾單">實時滾單</a>
	    <%}%>
	  </div>
</s:if>
<s:if test="subMenu=='NCReplenish'">
	  <div class="col_link">
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_FIRST %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_FIRST==subType'>class="current"</s:if>>第一球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_SECOND %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_SECOND==subType'>class="current"</s:if>>第二球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_THIRD %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_THIRD==subType'>class="current"</s:if>>第三球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_FORTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_FORTH==subType'>class="current"</s:if>>第四球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_FIFTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_FIFTH==subType'>class="current"</s:if>>第五球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_SIXTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_SIXTH==subType'>class="current"</s:if>>第六球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_SEVENTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_SEVENTH==subType'>class="current"</s:if>>第七球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_BALL_EIGHTH %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_BALL_EIGHTH==subType'>class="current"</s:if>>第八球</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_DRAGON %>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_DRAGON==subType'>class="current"</s:if>>總和、龍虎</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH %>&&finalPLayType=NC_STRAIGHTTHROUGH_RX2" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH==subType'>class="current"</s:if>>連碼</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/queryZhangdanReplenish.action?subType=<%=Constant.LOTTERY_TYPE_NC%>" <s:if test='@com.npc.lottery.common.Constant@LOTTERY_NC_SUBTYPE_ZHANGDAN==subType'>class="current"</s:if>class="special">帳單</a><span style="color:#f8ff2d">|</span>
		<a target="mainFrame" href="${pageContext.request.contextPath}/replenish/backupDetailCheck.action?subType=<%=Constant.LOTTERY_TYPE_NC%>" class="special">備份</a><span style="color:#f8ff2d">|</span>
		<%if(isSys || isManager || isChief || isSubChief){%>
	  		  <a onclick="return openRealTimeDetail(this)" href="${pageContext.request.contextPath}/replenish/realTimeDetail.action?subType=<%=Constant.LOTTERY_TYPE_NC%>" title="系統實時滾單">實時滾單</a>
	    <%}%>
	  </div>
</s:if>
</div>
<script>

	//退出
	function exit(){
		if(confirm("确定退出吗？")){
		
				document.exitForm.submit();			
		}
	}
</script>
</body>
</html>