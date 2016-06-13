<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.periods.entity.CQPeriodsInfo,com.npc.lottery.periods.entity.GDPeriodsInfo" %>


<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" src="${pageContext.request.contextPath}/js/UFO.js"></script>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>

<div class="content">

<div class="main">
<div class="ball_scroll">

    <script type="text/javascript">
        var ballPath = "${pageContext.request.contextPath}/images/bak.swf";
		var FO = { movie:ballPath, width:"540", height:"540", majorversion:"8",  build:"0",  music:"false", quality:"best", name:"ball", wmode:"transparent"};
		UFO.create(FO, "balldiv");
	</script><div id="balldiv" style="visibility: visible;" align="center"><embed width="540" height="540" type="application/x-shockwave-flash" src="${pageContext.request.contextPath}/images/bak.swf" pluginspage="http://www.macromedia.com/go/getflashplayer" name="ball" quality="best" wmode="transparent"></div>
  </div>
</div>
</div>