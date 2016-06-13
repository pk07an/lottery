<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" />

<marquee scrollamount="5" scrolldelay="120" onMouseOver="this.stop()" onMouseOut="this.start()" width="90%">
 <a target="mainFrame" href="${pageContext.request.contextPath}/user/queryAllMessage.action?type=message">
 <font id="Affiche" class="Font_Count"><s:property value="#request.shopsDeclaratton.contentInfo" escape="false" /></font></a>
 </marquee>