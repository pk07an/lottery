<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
function checkPet(shopCode) {
	window.open("${pageContext.request.contextPath}/boss/queryCheckPetEntry.action?code="+shopCode,'newwindow');
}
function searchSettleReport(account,type) {
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/statReportBoss.action?detailUserID=" + account + "&detailUserType=" + type);
	$("#sForm").submit();
}
function searchShop(shopCode) {
	window.open("${pageContext.request.contextPath}/boss/queryShop.action?code=" + shopCode,'newwindow');
}
function searchTrade(shopCode) {
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/queryCommissionDefault.action?code=" + shopCode);
	$("#sForm").submit();
}

function resetPwd(shopsCode){
	var strUrl = '${pageContext.request.contextPath}/boss/ajaxUpdatePwdBoss.action';
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"shopsCode":shopsCode},
		dataType : "json",
		success : callBack
	});
}
function callBack(json) {
	if(json.errorMessage)
	{
		alert(json.errorMessage);
	    return false;
	}
	if(json.success){
		alert(json.success);
	}	
}
</script>
<body>
<div class="content">
<s:form id="sForm" action="" namespace="boss">
	<div class="main">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
	  <tbody>
	    <tr>
	      <th width="3%">序號</th>
	      <th width="5%">商鋪號</th>
	      <th width="7%">商鋪名稱</th>
	      <th width="8%">總监帐號</th>
	      <th width="4%">狀態</th>
	      <th width="5%">樣式</th>
	      <th width="6%">備注</th>
	      <th width="10%">創建時間</th>
	      <th width="10%">租約有效期</th>
	      <th width="5%">在線人數</th>
	      <th width="10%">初始化</th>
	      <th width="15%">操作</th>
	    </tr>
	    <s:iterator value="#request.page.result" status="st">
	        <tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''"
	            <s:if test="#st.odd"></s:if>>
			      <td><s:property value="#st.count" escape="false"/></td>
			      <td><s:property value="shopsCode" escape="false"/></td>
			      <td><s:property value="shopsName" escape="false"/></td>
			      <td>
			         <s:if test="inLine==true">
						<img src="${pageContext.request.contextPath}/images/online.gif"/>
							  </s:if> <s:if test="inLine==false">
						<img  src="${pageContext.request.contextPath}/images/offline.gif"/>
					 </s:if>
			         <s:property value="chiefStaffExt.managerStaff.account" escape="false"/>
			      </td>
			      <td>
			          <s:if test="state==0" >
			                                                 開放
			          </s:if>
			          <s:if test="state==1" >
			              <strong class="blue">凍結</strong>
			          </s:if>
			          <s:if test="state==2" >
			              <strong class="red">關閉</strong>
			          </s:if>
			     </td>
			      <td>
			          <s:if test="css==1" >
			                                                 春
			          </s:if>
			          <s:if test="css==2" >
			                                                 夏
			          </s:if>			  
			      </td>
			      <td><s:property value="remark" escape="false"/></td>
			      <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			      <td><s:date name="expityTime" format="yyyy-MM-dd"/></td>
			      <td><s:property value="inLineNum" escape="false"/></td>
			      <td><input type="submit" name="btn3" id="button1" value="交易設定" class="btn td_btn" onclick="searchTrade('<s:property value="shopsCode" escape="false"/>')" /></td>
			      <td >
			          <input type="button" name="btn1" id="button3" value="校验数据" class="btn td_btn" onclick="checkPet('<s:property value="shopsCode" escape="false"/>')"/><br />
			          <input type="button" name="btn2" id="button4" value="商鋪管理" class="btn td_btn" onclick="searchShop('<s:property value="shopsCode" escape="false"/>')"/>
			          <input type="button" name="restPwd" id="button5" value="恢復密碼" class="btn td_btn" onclick="resetPwd('<s:property value="shopsCode" escape="false"/>')" title="還原總监密碼为總监的登錄帳号"/>			         
			      </td>
			</tr>
	    </s:iterator>
	  </tbody>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" class="page">
	        <tbody>
	          <tr>
	            <td>
		            <s:if test="#request.page.totalCount>0">			      
					      <lottery:paginate url="${pageContext.request.contextPath}/boss/shopManager.action" param=""/>
					</s:if>
				</td>
	          </tr>
	        </tbody>
	      </table>
	  </div>
<!--中间内容结束-->
</s:form>
</div>
</body>

</html>
