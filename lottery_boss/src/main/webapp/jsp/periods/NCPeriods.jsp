<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	
</script>
<body>
<div class="content">
    <s:form id="sForm" action="%{pageContext.request.contextPath}/boss/ncPeriodsInfo.action" >
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king2 xy pw">
	  <tbody>
	    <tr>
	      <th colspan="2">幸运农场期数设置</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">起始時間</td>
	      <td class="tl">
	          <input type="text" maxlength="20" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });"  name="startDate" />
	          <span>例如：2012-10-27</span>
	      </td>
	    </tr>
	    
	  </tbody>
	</table>
	<div class="tj">
	     <input type="submit" value="確認" class="btn_4"/>
	</div>
	</div>
  </s:form>
</div>
</body>

</html>
