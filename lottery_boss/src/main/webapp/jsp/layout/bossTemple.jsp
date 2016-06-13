<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/sitemesh-decorator.tld" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <title>總后臺管理 </title>
    <decorator:head></decorator:head>
    <script type="text/javascript">
	    function forwardToLotteryHistory(optValue)
	    {
	    window.location="${pageContext.request.contextPath}/boss/enterLotResultHistoryBoss.action?subType="+$("#optionType").val()+"&state="+$("#state").val();
	    }
	    
	  	//退出
	    function exit(){
	    	if(confirm("确定退出吗？")){
	    		if(parent.document.exitForm.isFrame){
	    			//框架内部的，则跳转到上一级退出
	    			parent.document.exitForm.submit();
	    			return true;
	    		}else{
	    			document.exitForm.submit();
	    			return true;
	    		}
	    	}
	    	
	    	return false;
	    }
	    function loading()
	    {
	    	var code= window.prompt("输入需要恢复商铺的商铺号");
	    	if(code==null) return false;
	    	if(confirm('确定要手动恢复全部会员可用信用额度？一定要慎用')){
	    		//进网页时弹框,更多资料搜索w3c
	    		var href="${pageContext.request.contextPath}/boss/updateMemberTotalCreditLine.action?type=explog&shopCode="+code;
	    		$("#creditLine").attr("href",href);
	    		return true;
	    	}
	    }
    </script>
  </head>
 <body>
<!-- 退出页面 -->
<form name="exitForm" method="post" action="${pageContext.request.contextPath}/boss/logoutBoss.action">
	
</form>
 
<!--头部开始-->
<div class="header">
  <div id="logo"><div class="logo"><img height="32" width="158" src="${pageContext.request.contextPath}/images/admin/logo.png"></div></div>
  <div class="nav">
<%-- 	  <a href="${pageContext.request.contextPath}/boss/shopRegister.action">商鋪注冊</a> --%>
	  <a href="${pageContext.request.contextPath}/boss/shopManager.action">商鋪信息</a>
<%-- 	  <a href="${pageContext.request.contextPath}/boss/getInManageMenu.action?periodsType=getInManager">內部管理</a>  --%>
	  <%-- <a href="${pageContext.request.contextPath}/boss/searchBill.action?periodsType=getInManager" >內部管理</a>  --%>
	  <a href="${pageContext.request.contextPath}/boss/enterLotResultHistoryBoss.action">開獎結果</a>
	  <a href="${pageContext.request.contextPath}/boss/queryGDResultPeriods.action?periodsType=periodsType_gd" <s:if test='periodsType==periodsType_gd'> class="current" </s:if> >盘口管理</a>
	  <a href="${pageContext.request.contextPath}/boss/queryManagerMessage.action?type=privateAdmin">公告管理</a>
	  <%-- <a href="${pageContext.request.contextPath}/boss/queryBossLog.action?type=explog" <s:if test='type==explog'> class="current" </s:if> >異常日誌</a> --%>
	  <a href="#" id="creditLine" onclick="loading()" <s:if test='type==explog'> class="current" </s:if> >信用额度</a>
	  <a href="#" onclick="return exit()">退出</a>
  </div>
  <div class="admin_info">總管登錄</div>
</div>
<!--头部结束-->
<!--菜單开始-->
<%-- <s:if  test='periodsType==null||!(periodsType.indexOf("periodsType")>-1)'> --%>
<s:if  test='periodsType==null'>
<div id="menu">
  <div class="menu_c fl">
  </div>
  <div class="menu fl">
  
  </div>

  <div class="menu_r fr nofont"></div>
  <div class="clear"></div>
</div>
</s:if>

<s:if test='periodsType.indexOf("user")>-1'>
	<div id="menu">
	  <div class="menu_c fl" style="width:10px"></div>
	  <div class="menu fl">
	
          <%-- <a href="${pageContext.request.contextPath}/user/queryChiefStaff.action" style="background:none">總监</a> --%>
   	  	  <a href="${pageContext.request.contextPath}/boss/queryBranchBoss.action?type=userBranch&chief=<s:property value="chief" escape="false"/>"  <s:if test="type=='userBranch'">class="current"</s:if>>分公司</a>
   	  	  <a href="${pageContext.request.contextPath}/boss/queryStockholderBoss.action?type=userStockholder&chief=<s:property value="chief" escape="false"/>" <s:if test="type=='userStockholder'">class="current"</s:if>>股東</a>
   	  	  <a href="${pageContext.request.contextPath}/boss/queryGenAgentStaffBoss.action?type=userGenAgent&chief=<s:property value="chief" escape="false"/>" <s:if test="type=='userGenAgent'">class="current"</s:if>>總代理</a>
   	  	  <a href="${pageContext.request.contextPath}/boss/queryAgentStaffBoss.action?type=userAgent&chief=<s:property value="chief" escape="false"/>" <s:if test="type=='userAgent'">class="current"</s:if>>代理</a>
   	  	  <a href="${pageContext.request.contextPath}/boss/queryMemberStaffBoss.action?type=userMember&chief=<s:property value="chief" escape="false"/>" <s:if test="type=='userMember'">class="current"</s:if>>会员</a>
	</div>
	
	  <div class="menu_r fr nofont"></div>
	  <div class="clear"></div>
	</div>
</s:if>

<s:if  test='periodsType.indexOf("periodsType")>-1'>
 
 <div id="menu">
  <div class="menu fl" style="width:100%">
  	<%-- <a href="${pageContext.request.contextPath}/boss/queryHKResultPeriods.action?periodsType=periodsType_hk" <s:if test='periodsType=="periodsType_hk"'>class="current"</s:if> >香港盘口</a> --%>
	<a href="${pageContext.request.contextPath}/boss/queryCQResultPeriods.action?periodsType=periodsType_cq" <s:if test='periodsType=="periodsType_cq"'>class="current"</s:if> >管理重庆盤期</a>
  	<a href="${pageContext.request.contextPath}/boss/queryGDResultPeriods.action?periodsType=periodsType_gd" <s:if test='periodsType=="periodsType_gd"'>class="current" </s:if> >管理广东盤期</a>
  	<a href="${pageContext.request.contextPath}/boss/queryBJResultPeriods.action?periodsType=periodsType_bj" <s:if test='periodsType=="periodsType_bj"'>class="current" </s:if> >管理北京赛车盤期</a>
  	<a href="${pageContext.request.contextPath}/boss/queryK3ResultPeriods.action?periodsType=periodsType_k3" <s:if test='periodsType=="periodsType_k3"'>class="current" </s:if> >管理快3盤期</a>
  	<a href="${pageContext.request.contextPath}/boss/queryNCResultPeriods.action?periodsType=periodsType_nc" <s:if test='periodsType=="periodsType_nc"'>class="current" </s:if> >管理幸运农场盤期</a>
  </div>
  <div class="menu_r fr nofont"></div>
  <div class="clear"></div>
</div>
</s:if>
<s:if  test='periodsType.indexOf("getInManager")>-1'>
 
 <div id="menu">
  <div class="menu fl" style="width:100%">
  	<a href="${pageContext.request.contextPath}/boss/searchBill.action?periodsType=getInManager" <s:if test="periodsType=='getInManager'">class="current"</s:if>>注單fffffffffff搜索</a>
	 
  </div>
  <div class="menu_r fr nofont"></div>
  <div class="clear"></div>
</div>
</s:if>

<!--菜單结束-->
<!--内容开始-->
<decorator:body ></decorator:body>

</body>
</html>
