<!-- 
	鉴权不通过返回页面
-->
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<%
    //工程上下文，涉及路径的资源，在写访问路径时一般均要加上，如引用一个图片

    request.setAttribute("contextPath",request.getContextPath());
%>

<head>
<title>警告信息</title>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>
<script type="text/javascript">
     //弹出窗口
	 function openWin(openURL){
		//需要打开的页面URL，设置为实际值
		var url ="${contextPath}"+openURL;
		//打开模式窗口，固定写法，不要改变
	  	var ret = openModalDialog('${contextPath}/common/ModalDialogPage.jsp',url,550,350);
	}
</script>
<style>
    .cnt_info {
	    text-align:center;
	    background-color:#e7f0ff;
	}
	
	.cnt_infobody {
	    border:1px solid #ccc;
        padding:15px;
	}
	
	.cnt_tblcontent2 {
		background-color:#e7f0ff;
		border-top:0px solid #c3c3c3;
		border-left:0px solid #c3c3c3;
		border-right:0px solid #c3c3c3;
		border-bottom:0px solid #c3c3c3;
		padding-left:0px;
		padding-top:0px;
	}
</style>

</head> 

<body>

<div id="main">

    <div id="cnt_list">
    <div id="cnt_lt">
    
        <div id="cnt_tblcontent2">   
        <table width="100%" class="cnt_infobody">
        <tr> 
            <td align="center">&nbsp;</td>
            </tr>
          
            <tr> 
                <td align="center">
                	<font color="#FF0000">您无权访问此资源，请与系统管理员联系！</font>
                </td>
            </tr>
          
            <tr> 
                <td align="center">&nbsp;</td>
            </tr>
                
            <tr> 
                <td align="center">
                	<a href='#' onClick='history.go(-1)'>【返回】</a>
                </td>
            </tr>
        </table>
        </div>
        
    </div>
    </div>

</div>
    
</body>

</html>
