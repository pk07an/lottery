<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<%


%>

<s:if test="from.indexOf('GDKLSF')>-1">
<cache:cache key='gdklsftwoside' duration='2s'>  
<s:action name="twoSideAndDragonRank" executeResult="true" flush="false">
<s:param name="from">GDKLSF</s:param>
</s:action>
</cache:cache>  

</s:if>
<s:elseif test="from.indexOf('CQSSC')>-1">
<cache:cache key='cqssctwoside' duration='2s'>  
<s:action name="twoSideAndDragonRank" executeResult="true" flush="false">
<s:param name="from">CQSSC</s:param>
</s:action>
</cache:cache>
</s:elseif>

<s:elseif test="from.indexOf('BJSC')>-1">
<cache:cache key='bjsctwoside' duration='2s'>  
<s:action name="twoSideAndDragonRank" executeResult="true" flush="false">
<s:param name="from">BJSC</s:param>
</s:action>
</cache:cache>
</s:elseif>

