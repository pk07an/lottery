<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.common.Constant "%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<title>《規則及條例》</title>

<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); %>
<head>
<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main_fl">
  <div class="falv">
    <h1>協定和規則聲明</h1>
	<div class="falv_con">
      1、使用本公司網站的客戶，請留意閣下所在的國家或居住地的相關法律規定，如有疑問應就相關問題，尋求當地法律意見。<br />
      2、若發生遭駭客入侵破壞行為或不可抗拒之災害導致網站故障或資料損壞、資料丟失等情況，我們將以本公司之後備資料為最後處理依據；為確保各方利益，請各會員投注後列印資料。本公司不會接受沒有列印資料的投訴。<br />
      3、為避免糾紛，各會員在投注之後，務必進入下注狀況檢查及列印資料。若發現任何異常，請立即與代理商聯繫查證，一切投注將以本公司資料庫的資料為准，不得異議。如出现特殊网络情况或线路不稳定导致不能下注或下注失败。本公司概不负责。<br />
      4、單一注單最高派彩上限為一百萬。<br />
      5、開獎結果以官方公佈的結果為准。<br />
      6、我們將竭力提供準確而可靠的開獎統計等資料，但並不保證資料絕對無誤，統計資料只供參考，並非是對客戶行為的指引，本公司也不接受關於統計數據產生錯誤而引起的相關投訴。<br />
      7、本公司擁有一切判決及註消任何涉嫌以非正常方式下註之權利，在進行更深入調查期間將停止發放與其有關之任何彩金。客戶有責任確保自己的帳戶及密碼保密，如果客戶懷疑自己的資料被盜用，應立即通知本公司，並須更改其個人詳細資料。所有被盜用帳號之損失將由客戶自行負責。在某種特殊情況下，客人之信用額可能會出現透支。<br />
      <b class="t_right"><%=userInfo.getShopsInfo().getShopsName()%> 敬啟</b>

</div>
  </div>
</div>
</body>

</html>




