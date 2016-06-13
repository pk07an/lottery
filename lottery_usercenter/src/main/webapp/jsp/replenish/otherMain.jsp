<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.replenish.vo.ReplenishVO,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.periods.entity.CQPeriodsInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/replenish.js"></script>
<script type="text/javascript">
var timer;
$(document).ready(function() {	
	
	showContentMain();
});

function showContentMain() {
	var strUrl = context + "/replenish/replenishSetContent.action";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"subType":$("#subType").val(),"searchArray":"lose",
			"searchType":"0","plate":"A"},
		dataType : "html",
		success : showContentCallbackMain
	});
}

function showContentCallbackMain(resultHtml) {
	$("#content").html(resultHtml);
	if($("#searchTime").val()=="NO"){
		$("#refreshBtn").show();
	}else{
		$("#refreshTime").show();
	}
	$("#onrefresh").hide();
	onrefresh = false; 
	setCltime();
}
</script>
<body>

<!--内容开始-->
<div id="content"><input type="hidden"  name="subType" id="subType" value="<s:property value="subType"/>"/></div>
<!--内容结束-->

<jsp:include page="/jsp/replenish/replenishPop.jsp" />
</body>
</html>
