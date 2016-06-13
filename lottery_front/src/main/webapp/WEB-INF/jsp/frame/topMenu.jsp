<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link type="text/css" rel="stylesheet" href="/css/top.css" />
 <script language="javascript" src="/js/UFO.js"></script>
 <script language="javascript" src="/js/Forbid.js" type="text/javascript"></script>
</head>
<body>
<!-- 退出页面 -->
<form name="exitForm" method="post" action="${pageContext.request.contextPath}/${path}/logout.xhtml?shopcode=${shopCode}" target="_top">
	
</form>

<table width="100%" height="108" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
    <td width="10%" valign="top">
<table width="100%" border="0">
  <tbody><tr>
    <td background="${pageContext.request.contextPath}/images/shoplogo/${shopCode}.jpg">
<object width="231" height="79" id="top_c" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,22,0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"><param value="transparent" name="wmode"><param value="${pageContext.request.contextPath}/images/lx.swf" name="movie"><param value="pageID=0" name="FlashVars"><param value="high" name="quality"><param value="false" name="menu"><embed width="231" height="79" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?p1_prod_version=shockwaveflash" type="application/x-shockwave-flash" wmode="transparent" quality="high" name="top_c" src="${pageContext.request.contextPath}/images/lx.swf"></object>
	</td>
   </tr>
   <tr>
    <td><img width="231" height="29" src="${pageContext.request.contextPath}/images/TopMenu_Top2.jpg"></td>
  </tr>
</tbody></table>
	</td>
    <td width="90%" background="${pageContext.request.contextPath}/images/TopMenu_Top.jpg">
		<table width="100%" height="108" cellspacing="0" cellpadding="0" border="0">
		  <tbody><tr>
			<td height="43">
				<table width="720" cellspacing="0" cellpadding="0" border="0">
				  <tbody><tr>
					<td align="right">				  				
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/enterCreditInfo.xhtml')" href="javascript:void(0);" class="T_a" >信用資料</a> | 
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/enterChangePassword.xhtml')"  class="T_a"  href="javascript:void(0);">修改密碼</a> | 
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/html/playDetail.html')"  href="javascript:void(0);" class="T_a" >下註明細</a> | 
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/enterSettleReport.xhtml')" href="javascript:void(0);" class="T_a"  >結算報表</a> | 
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/enterLotResultHistory.xhtml')" href="javascript:void(0);"  class="T_a" >歷史開獎</a> | 
					<a onClick="TO_URL('${pageContext.request.contextPath}/${path}/html/playRule.html')" href="javascript:void(0);" class="T_a">規則</a> | 
					<a href="javascript:void(0);" onClick="exit();" style="color:#baff00">退出</a>
					</td>
				  </tr>
				</tbody></table>
			</td>
		  </tr>
		  <tr>
			<td height="36">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
    <td width="1%" height="36"><img width="19" height="36" src="${pageContext.request.contextPath}/images/TopMenu_2Left.jpg"></td>
    <td  style="vertical-align: middle">
    <!--<input type="button" value="廣東快樂十分" style="cursor: hand;" onClick="forward('GDKLSF');" name="bST_1" class="bST_1_s" id="gdklsf_tab"><input type="button" value="重慶時時彩" style="cursor: hand;" onClick="forward('CQSSC');" name="bST_2" class="bST_1" id="cqssc_tab"><input type="button" value="北京賽車(PK10)" style="cursor: hand;" onClick="forward('BJSC');" name="bST_3" class="bST_1" id="bjsc_tab"><input type="button" value="香港六盒彩" style="cursor: hand;" onClick="forward('HKLHC');" name="bST_4" class="bST_1" id="hklhc_tab">-->
    <a onClick="forward('GDKLSF');" name="bST_1" class="bST_1_s" id="gdklsf_tab">廣東快樂十分</a>
    <a onClick="forward('CQSSC');" name="bST_2" class="bST_1" id="cqssc_tab">重慶時時彩</a>
    <a onClick="forward('BJSC');" name="bST_3" class="bST_1" id="bjsc_tab">北京賽車(PK10)</a>
    <a onClick="forward('K3');" name="bST_4" class="bST_1" id="jssb_tab">江苏骰寶(快3)</a>
    <a onClick="forward('NC');" name="bST_5" class="bST_1" id="nc_tab">幸运农场</a>
    
   </td>
    <td width="1%" align="right"></td>
  </tr>
</tbody></table>
			</td>
		  </tr>
		  <tr>
			<td height="29" id="gdmenu"><span style="position: relative; top: 0px; left: 2px;" >
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfDoubleSide.html"   target="mainFrame" class="Font_R" id="gddefault">兩面盤</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB1.html"   target="mainFrame">第一球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB2.html"   target="mainFrame">第二球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB3.html"    target="mainFrame">第三球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB4.html"    target="mainFrame">第四球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB5.html"    target="mainFrame">第五球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB6.html"    target="mainFrame">第六球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB7.html"  target="mainFrame">第七球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfB8.html"   target="mainFrame">第八球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfSumDragonTiger.html" target="mainFrame">總和、龍虎</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/gdklsfStraightthrough.html"  target="mainFrame">連碼</a>
			</span></td>
			
			
			
		<td height="29" id="cqmenu" style="display:none"><span style="position: relative; top: 0px; left:2px;"  >
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscDoubleSide.html"   target="mainFrame" class="Font_R" id="cqdefault">兩面盤</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscB1.html"   target="mainFrame" >第一球</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscB2.html"   target="mainFrame">第二球</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscB3.html"   target="mainFrame">第三球</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscB4.html"   target="mainFrame">第四球</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/cqsscB5.html"   target="mainFrame">第五球</a>
			</span></td>
			
			<td height="29" id="bjmenu" style="display:none"><span style="position: relative; top: 0px; left:2px;"  >
		<a  href="${pageContext.request.contextPath}/${path}/html/bjscDoubleSide.html"   target="mainFrame" class="Font_R" >兩面盤</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/bjscgyzh.html"   target="mainFrame" id="bjscdefault">冠、亞軍 組合</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/bjsc3456.html"   target="mainFrame">三、四、五、六名</a>|
		<a  href="${pageContext.request.contextPath}/${path}/html/bjsc78910.html"   target="mainFrame">七、八、九、十名</a>

			</span></td>
		<td height="29" id="jssbmenu" style="display:none">
			<span style="position: relative; top: 0px; left:2px;"  >
				<a  href="${pageContext.request.contextPath}//${path}/html/jssb.html"   target="mainFrame" class="Font_R"  id='jssbdefault'>大小骰寶</a>|
			</span>
		</td>
			
			<!-- 快乐农场 -->
			<td height="29" id="ncmenu" style="display:none"><span style="position: relative; top: 0px; left: 2px;" >
			<a href="${pageContext.request.contextPath}/${path}/html/ncDoubleSide.html"   target="mainFrame" class="Font_R" id="ncdefault">兩面盤</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB1.html"   target="mainFrame">第一球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB2.html"   target="mainFrame">第二球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB3.html"    target="mainFrame">第三球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB4.html"    target="mainFrame">第四球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB5.html"    target="mainFrame">第五球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB6.html"    target="mainFrame">第六球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB7.html"  target="mainFrame">第七球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncB8.html"   target="mainFrame">第八球</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncSumDragonTiger.html" target="mainFrame">總和、龍虎</a>|
			<a href="${pageContext.request.contextPath}/${path}/html/ncStraightthrough.html"  target="mainFrame">連碼</a>
			</span></td>
		  </tr>
		</tbody></table>
	</td>
  </tr>
</tbody></table>




 <script language="javascript" src="/js/jquery-1.4.2.min.js"></script>
 <script language="javascript" src="/js/top.js"></script>
  <script language="javascript" src="/js/public.js"></script>
<script>
function forward(optValue)
{
	
	
   if(optValue=='GDKLSF')
    {
	   
	   $("#gdmenu").show();
	   $("#cqmenu").hide();
	   $("#hkmenu").hide();
	   $("#bjmenu").hide();
	   //add by peter
	   $("#jssbmenu").hide();
	   $("#ncmenu").hide();
	   window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/gdklsfDoubleSide.html";
	   window.parent.topFrame.currentPlayType="GDKLSF";
	   $("#gdklsf_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
	   $("#gddefault").addClass("Font_R").siblings("a").removeClass();
	  	   
	 }
  else if(optValue=='CQSSC')
	  {
	 
	   $("#gdmenu").hide();
	   $("#bjmenu").hide();
	   $("#cqmenu").show();
	   $("#hkmenu").hide();
	   //add by peter
	   $("#jssbmenu").hide();
	   $("#ncmenu").hide();
	 window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/cqsscDoubleSide.html";
	 window.parent.topFrame.currentPlayType="CQSSC";
	 $("#cqssc_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
	 $("#cqdefault").addClass("Font_R").siblings("a").removeClass();
	  }
  else if(optValue=='HKLHC')
	  {
	   $("#gdmenu").hide();
	   $("#cqmenu").hide();
	   $("#bjmenu").hide();
	   $("#hkmenu").show();
	   //add by peter
	   $("#jssbmenu").hide();
	   window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/hk/tema.html";
	   window.parent.topFrame.currentPlayType="HKLHC";
	   $("#hklhc_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
	  $("#hkdefault").addClass("Font_R").siblings("a").removeClass();
	  } 
  else if(optValue=='BJSC')
  {
   $("#gdmenu").hide();
   $("#cqmenu").hide();
   $("#hkmenu").hide();
   $("#bjmenu").show();
   //add by peter
   $("#jssbmenu").hide();
   $("#ncmenu").hide();
   window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/bjscgyzh.html";
   window.parent.topFrame.currentPlayType="BJSC";
   $("#bjsc_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
  $("#bjscdefault").addClass("Font_R").siblings("a").removeClass();
  }  
   
  else if(optValue=='K3')
  {
   $("#gdmenu").hide();
   $("#cqmenu").hide();
   $("#hkmenu").hide();
   $("#bjmenu").hide();
   $("#jssbmenu").show();
   $("#ncmenu").hide();
   window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/jssb.html";
   window.parent.topFrame.currentPlayType="K3";
   $("#jssb_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
  $("#jssbdefault").addClass("Font_R").siblings("a").removeClass();
  }  
   
  else if(optValue=='NC')
  {
   $("#gdmenu").hide();
   $("#cqmenu").hide();
   $("#hkmenu").hide();
   $("#bjmenu").hide();
   $("#jssbmenu").hide();
   $("#ncmenu").show();
   window.parent.mainFrame.location.href="${pageContext.request.contextPath}/${path}/html/ncDoubleSide.html";
   window.parent.topFrame.currentPlayType="NC";
   $("#nc_tab").addClass("bST_1_s").siblings("a").attr("className","bST_1");
  $("#ncdefault").addClass("Font_R").siblings("a").removeClass();
  }  
}

//退出
function exit(){
	if(confirm("确定退出吗？")){
	
			document.exitForm.submit();
	}

}
$(document).ready(function() {
	
	 $("#gdmenu span a,#cqmenu span a,#hkmenu span a,,#bjmenu span a,#jssbmenu span a,#ncmenu span a").click(function() {
		
	 $(this).addClass("Font_R").siblings("a").removeClass(); 	 
	 });
});

function redirct(url)
{
	window.parent.mainFrame.location.href=url;
}
function TO_URL(url)
{
	 window.parent.mainFrame.location.href=url+"?subType="+currentPlayType;	
}
</script>
</body>
</html>

