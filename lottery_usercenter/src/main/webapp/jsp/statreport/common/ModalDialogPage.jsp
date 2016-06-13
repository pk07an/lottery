<!-- 
	打开模式窗口的过渡页面
	如果不用此页面过渡，则打开的模式页面中会有很多使用限制，比如取参数等。
	
-->
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title></title>

<script>
 
	var contentUrl = window.dialogArguments;
	
	document.write("<frameset id=frmWorkSpace border=0 frameSpacing=0 frameBorder=no rows=0,* cols=*>");
	document.write("<frame name=nav src='/common/blank.jsp' scrolling=no>");
	document.write("<frame name=nav src='" + contentUrl + "' scrolling=auto>");
	document.write("</frameset>");
</script>

</head>
</html>

