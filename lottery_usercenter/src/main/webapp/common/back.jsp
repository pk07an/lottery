<!-- 
	提示信息页面
	参数：


		Constant.INFO_PAGE_MESSAGE				提示信息
		Constant.INFO_PAGE_MESSAGE_KEY			提示信息所对应的资源文件key
													HashMap類型，key = file，对应资源文件的名稱
																key = id，对应资源文件中的ID
		Constant.INFO_PAGE_TYPE_CLOSE			true 显示关闭按钮
		Constant.INFO_PAGE_TYPE_RETURN			true 显示返回按钮，返回到指定的url
		Constant.INFO_PAGE_RETURN_URL			返回的url
		Constant.INFO_PAGE_TYPE_RETURN_SIMPLE 	true 显示返回按钮，返回到history.back()
				
-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="
		com.npc.lottery.common.Constant,
		java.util.HashMap
"%>

<html>

<head>
<title>提示信息</title>

<%
    //工程上下文，涉及路径的资源，在写访问路径时一般均要加上，如引用一个图片

    request.setAttribute("contextPath",request.getContextPath());
%>

<link href="${contextPath}/css/css.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${contextPath}/js/public.js"></script>

<style>
    .cnt_info {
	    text-align:center;
	    background-color:#e7f0ff;
	}
	
	.cnt_infobody {
	    border:1px solid #ccc;
        padding:15px;
	}
</style>
<script type="text/javascript">
	function refreshTree(url,treeUrl){
		window.parent.modifyTreeList.location.href = treeUrl;
		location.href = url;
	}
</script>
</head> 

<%
try{
	String info = (String)request.getAttribute(Constant.INFO_PAGE_MESSAGE);
	
	HashMap infoID = (HashMap)request.getAttribute(Constant.INFO_PAGE_MESSAGE_KEY);
	 
	if(null == info && null == infoID){
		//infoID = new HashMap();
		//infoID.put(Constant.INFO_PAGE_MESSAGE_KEY_FILE,"sysbase");
		//infoID.put(Constant.INFO_PAGE_MESSAGE_KEY_ID,"label.infopage.unknowErr");
		info = "<font color='" + Constant.COLOR_RED + "'>未知错误！</font>";
	}
	String typeClose = (String)request.getAttribute(Constant.INFO_PAGE_TYPE_CLOSE);//显示关闭按钮
	String typeReturn = (String)request.getAttribute(Constant.INFO_PAGE_TYPE_RETURN);//显示返回按钮
	String typeReturnSimple = (String)request.getAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE);//显示返回按钮
%>

<body>

<div id="main">

    <div id="cnt_list">
    <div id="cnt_lt">
    
        <div id="cnt_tblcontent">   
        <table width="100%" class="cnt_infobody">
        <tr> 
            <td align="center">&nbsp;</td>
            </tr>
          
            <tr> 
                <td align="center">
                <% 
                    if(null != info){
                        out.println(info);
                    }else if(null != infoID){
                %>
                        <bean:message key='<%=(String)infoID.get(Constant.INFO_PAGE_MESSAGE_KEY_ID)%>' bundle='<%=(String)infoID.get(Constant.INFO_PAGE_MESSAGE_KEY_FILE)%>'/>
                <%
                    }
                %>
                </td>
            </tr>
          
            <tr> 
                <td align="center">&nbsp;</td>
            </tr>
                
            <tr> 
                <td align="center">
                <%
                    if(null != typeClose){
                        out.println("<a href='#' onClick='window.close()'>【关闭】</a>");
                    }else if(null != typeReturnSimple){                         
                        out.println("<a href='#' onClick='refreshTree();'>【返回】</a>");
                    }else if(null != typeReturn){
                        out.print("<a href='javascript:refreshTree(\""+request.getContextPath() + (String)request.getAttribute(Constant.INFO_PAGE_RETURN_URL)+"\",\""
                        +request.getContextPath() + (String)request.getAttribute(Constant.BACK_TREE_PAGE_RETURN)+"\");'>【返回】</a>");
                    }
                %>
                </td>
            </tr>
        </table>
        </div>
        
    </div>
    </div>

</div>
    
</body>

<%
}catch(Exception ex){
    ex.printStackTrace();
}
%>  
</html>
