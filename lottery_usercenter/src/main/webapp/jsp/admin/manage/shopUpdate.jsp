<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	//查询商铺号
function checkSubmit(){	
		
		if($("#textInfo").val() !=""){
			$("#sForm").submit();
	 	}else{
			alert("请填写公告内容");
		}
		
	}
</script>
<body>
<div class="content">
    <s:form id="sForm" action="/boss/updateMessage.action" method="post">
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="60%" class="king2 xy pw">
	  <tbody>
	     <tr>
	      <th colspan="2">修改公告 </th>
	    </tr>
	     <tr>
	      <td  colspan="2">下注端
	      <s:radio name="shopsDeclaratton.memberMessageStatus" list="#{'1':'顯示','0':'隱藏'}" label="狀態" >
		  </s:radio>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="2">經銷端
	      <s:radio name="shopsDeclaratton.managerMessageStatus" list="#{'1':'顯示','0':'隱藏'}" label="狀態">
		  </s:radio>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="2">弹出&nbsp;&nbsp;
	      <s:radio name="shopsDeclaratton.popupMenus" list="#{'1':'是','0':'否'}" label="狀態">
		  </s:radio>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">公告詳情 </td>
	      <td class="tl t_left" width="50%">
	      <s:textarea rows="5" id="textInfo" cols="106" name="shopsDeclaratton.contentInfo" value="%{shopsDeclaratton.contentInfo}"></s:textarea>
          <s:hidden value="%{shopsDeclaratton.ID}" name="shopsDeclaratton.ID"></s:hidden> 
           </td>	      
	    </tr>
	  </tbody>
	</table>
	<div class="tj"><input type="button" value="確認" class="btn_4" name="" onclick="checkSubmit();"></input></div>
	</div>
  </s:form>
</div>
</body>

</html>
