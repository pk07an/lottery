<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>parameter error page</title>
  
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <div class="content">
    非法数据错误，请联系系统管理员 <br>
    
    <s:if test="hasFieldErrors()">      
      
    <s:iterator value="fieldErrors">      
     <s:iterator value="value">      
         <p><s:property escape="false"/> </p>              
     </s:iterator>        
    </s:iterator> 
    </s:if> 
    </div>    
  </body>
</html>
