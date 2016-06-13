<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
    function noCheckSubmit(){   
        window.location.href="${pageContext.request.contextPath}/${path}/logout.xhtml?shopcode=${shopCode}";       
    }
    function checkSubmit(){ 
        window.location.href="${pageContext.request.contextPath}/${path}/queryMemberPopupMenus.xhtml";      
    }
</script>
<head>
<link href="/css/login.css" rel="stylesheet" type="text/css" />
</head>
<style type="text/css">

body {
    background-color: #110502;
    background-image: url("${pageContext.request.contextPath}/images/Warn_XY_Top.jpg");
    background-repeat: repeat-x;
/*    overflow: hidden;*/
	font-size: 12px; 
    list-style-type: none;
    margin: 0;
    padding: 0;
	color:#fff;
	text-align:left;
}
body, div, span, p, b, textarea, input, select, form, table, tr, td {
    font-size: 12px;
    margin: 0;
    padding: 0;
}
td {
    cursor: default;
    line-height: 18px;
	text-align:left;
}
.clearfix:after{height:0;line-height:0;display:block;clear:both;content:"";}
.clearfix{*zoom:1;}
.btn {
    background-position: 0 0;
}
.btn, .btn_m {
    background-color: #FFFFFF;
    background-image: url("${pageContext.request.contextPath}/images/Login_but.jpg");
    border: 0 solid #FF9224;
    height: 87px;
    width: 104px;
}
</style>

<body>
<table width="980" cellspacing="0" cellpadding="0" border="0" background="${pageContext.request.contextPath}/images/Warn_L1.jpg" style="margin:0 auto;">
  <tbody><tr>
    <td height="103">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top" height="531" align="center"><table width="100%" cellspacing="0" cellpadding="0" border="0">
      <tbody><tr>
        <td width="21%" height="531">&nbsp;</td>
        <td width="58%">
          <p style="padding-top:100px;">1、使用本公司網站的客戶，請留意閣下所在的國家或居住地的相關法律規定，如有疑問應就相關問題，尋求當地法律意見。<br />
      2、若發生遭駭客入侵破壞行為或不可抗拒之災害導致網站故障或資料損壞、資料丟失等情況，我們將以本公司之後備資料為最後處理依據；為確保各方利益，請各會員投注後列印資料。本公司不會接受沒有列印資料的投訴。<br />
      3、為避免糾紛，各會員在投注之後，務必進入下注狀況檢查及列印資料。若發現任何異常，請立即與代理商聯繫查證，一切投注將以本公司資料庫的資料為准，不得異議。如出现特殊网络情况或线路不稳定导致不能下注或下注失败。本公司概不负责。<br />
      4、單一注單最高派彩上限為一百萬。<br />
      5、開獎結果以官方公佈的結果為准。<br />
      6、我們將竭力提供準確而可靠的開獎統計等資料，但並不保證資料絕對無誤，統計資料只供參考，並非是對客戶行為的指引，本公司也不接受關於統計數據產生錯誤而引起的相關投訴。<br />
      7、本公司擁有一切判決及註消任何涉嫌以非正常方式下註之權利，在進行更深入調查期間將停止發放與其有關之任何彩金。客戶有責任確保自己的帳戶及密碼保密，如果客戶懷疑自己的資料被盜用，應立即通知本公司，並須更改其個人詳細資料。所有被盜用帳號之損失將由客戶自行負責。在某種特殊情況下，客人之信用額可能會出現透支。</p>
      <p style="text-align:right;padding-right:50px;margin-top:30px;">“${managerName }”管理層 敬啟</p>
          <p style="text-align:center;padding-top:40px;">我瞭解以及同意下註列明的協定和規則。 </p>
      
          </td>
        <td width="21%">&nbsp;</td>
      </tr>
    </tbody></table></td>
  </tr>
  <tr>
    <td height="161"><img src="${pageContext.request.contextPath}/images/Warn_L2.jpg" width="980" height="161" border="0" usemap="#Map"></td>
  </tr>
</tbody></table>

<map name="Map" id="Map">
  <area shape="rect" coords="353,17,466,57" href="javascript:noCheckSubmit()"/>
  <area shape="rect" coords="512,15,625,57" href="javascript:checkSubmit()"/>
</map>
</body>
</html>
