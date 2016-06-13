<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.periods.entity.CQPeriodsInfo,com.npc.lottery.periods.entity.GDPeriodsInfo" %>


<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" src="${pageContext.request.contextPath}/js/UFO.js"></script>
<%
long lastkpTime=0L;
if(request.getAttribute("LastwkpPeriods") instanceof CQPeriodsInfo)
{
CQPeriodsInfo cqp=(CQPeriodsInfo)request.getAttribute("LastwkpPeriods");
if(cqp!=null)
	lastkpTime=cqp.getOpenQuotTime().getTime()-(new Date().getTime());
}
else if(request.getAttribute("LastwkpPeriods") instanceof GDPeriodsInfo)
{
	GDPeriodsInfo cqp=(GDPeriodsInfo)request.getAttribute("LastwkpPeriods");
	if(cqp!=null)
		lastkpTime=cqp.getOpenQuotTime().getTime()-(new Date().getTime());	

}





%>


<div class="content">

<div class="main">
<div class="ball_scroll">
<script type="text/javascript" language="javascript">



var nMS=<%=lastkpTime%>;
var h=Math.floor(nMS/(1000*60*60)) % 60; 
var m=Math.floor(nMS/(1000*60)) % 60;  
if(m<10) m='0'+m;
var s=Math.floor(nMS/1000) % 60;
if(s<10) s='0'+s;  

//var h = parseInt(time / 60) > 0 ? parseInt(time / 60) : 0;
//var m = time % 60;
//var s = 60;
//document.write('<ul id="CountDown"><li id="f_hh">' + h + '</li><li id="f_mm">' + m + '</li><li id="f_ss">' + s + '</li></ul>');
var timeInterval = setInterval(function () {
if (h == 0 && m == 0 && s == 0) { clearInterval(timeInterval); return; }
if (s == 0) { s = 60; }
if (s == 60) {
m -= 1;
if (m == 0 && h > 0) {
h -= 1;
m = 60;
s = 60;
}
}
s -= 1;
//document.getElementById('f_hh').innerHTML = h;
//document.getElementById('f_mm').innerHTML = m;
//document.getElementById('f_ss').innerHTML = s;

}, 1000);
</script>


    <script type="text/javascript">
        var ballPath = "${pageContext.request.contextPath}/images/nokp.swf";
        /* if("${notKpType}"=="HK"){
        	ballPath = "${pageContext.request.contextPath}/images/ballHK.swf";
        }
        if("${notKpType}"=="GD"){
        	ballPath = "${pageContext.request.contextPath}/images/ballGD.swf";
        }
        if("${notKpType}"=="CQ"){
        	ballPath = "${pageContext.request.contextPath}/images/ballCQ.swf";
        } */
        
		var FO = { movie:ballPath, width:"540", height:"540", majorversion:"8",  build:"0",  music:"false", quality:"best", name:"ball", wmode:"transparent"};
		UFO.create(FO, "balldiv");
	</script><div id="balldiv" style="visibility: visible;" align="center"><embed width="540" height="540" type="application/x-shockwave-flash" src="${pageContext.request.contextPath}/images/nokp.swf" pluginspage="http://www.macromedia.com/go/getflashplayer" name="ball" quality="best" wmode="transparent"></div>
  </div>
</div>
</div>