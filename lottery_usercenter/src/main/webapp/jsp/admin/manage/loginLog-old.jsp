<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Tool,com.npc.lottery.common.Constant"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="page" uri="/divpage-list-tag"%><!-- 分页标签 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/index.css">
	<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>

<script>
	
	//格式化日期：yyyy-MM-dd      
	function formatDate(date) {       
	    var myyear = date.getFullYear();      
	    var mymonth = date.getMonth()+1;      
	    var myweekday = date.getDate();       
	          
	    if(mymonth < 10){      
	        mymonth = "0" + mymonth;      
	    }       
	    if(myweekday < 10){      
	        myweekday = "0" + myweekday;      
	    }      
	    return (myyear+"-"+mymonth + "-" + myweekday);       
	}     
	
	//获得某月的天数      
	function getMonthDays(myYear, myMonth){
	    var monthStartDate = new Date(myYear, myMonth, 1);       
	    var monthEndDate = new Date(myYear, myMonth + 1, 1);       
	    var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);       
	    return days;       
	}    
	
	//当天
	function today(){
		var now=new Date();
		now = formatDate(now); 
		
		document.sform.dateStart.value = now;
		document.sform.dateEnd.value = now;
	}
	
	//昨天
	function yesterday(){
		var yest = new Date();
		yest.setDate(yest.getDate()-1);
		
		yest = formatDate(yest);
		
		document.sform.dateStart.value = yest;
		document.sform.dateEnd.value = yest;
	}
	
	//本周
	function thisWeek(){
		//var now = new Date();                    //当前日期      
		var now = new Date();
		now.setDate(now.getDate()-1);
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);       
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek +1));       
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.sform.dateStart.value = weekStartDate;
	    document.sform.dateEnd.value = weekEndDate;
	}
	
	//上周
	function lastWeek(){
		var now = new Date();                    //当前日期   
		now.setDate(now.getDate()-1);
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;      
		
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 6);       
		weekStartDate = formatDate(weekStartDate);      
	    
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 6));       
	    weekEndDate = formatDate(weekEndDate);   
	    
	    document.sform.dateStart.value = weekStartDate;
	    document.sform.dateEnd.value = weekEndDate;
	}
	
	//本月
	function thisMonth(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;

		var monthStartDate = new Date(nowYear, nowMonth, 1);       
		monthStartDate = formatDate(monthStartDate);
		
	    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear, nowMonth));       
	    monthEndDate = formatDate(monthEndDate); 
	    
	    document.sform.dateStart.value = monthStartDate;
	    document.sform.dateEnd.value = monthEndDate;
	}
	
	//上月
	function lastMonth(){
		var now = new Date();                    //当前日期      
		var nowDayOfWeek = now.getDay();         //今天本周的第几天      
		var nowDay = now.getDate();              //当前日      
		var nowMonth = now.getMonth();           //当前月      
		var nowYear = now.getYear();             //当前年      
		nowYear += (nowYear < 2000) ? 1900 : 0;

		var monthStartDate = new Date(nowYear, nowMonth - 1, 1);       
		monthStartDate = formatDate(monthStartDate);
		
	    var monthEndDate = new Date(nowYear, nowMonth - 1, getMonthDays(nowYear, nowMonth - 1));       
	    monthEndDate = formatDate(monthEndDate); 
	    
	    document.sform.dateStart.value = monthStartDate;
	    document.sform.dateEnd.value = monthEndDate;
	}
	
	
</script>

<div class="content">
<%
	//TODO 修改为从request中直接获取（struts2可能存在多次转发，目前request中只能获得jsp页面的请求URL，以后修改）
	String thisPageUrl = request.getContextPath() + "/admin/queryLoginLog.action";//此列表页面的请求URL
	request.setAttribute("thisPageUrl", thisPageUrl);
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<div class="main">
<s:form id="sform" method="post" action="queryLoginLog.action" name="sform" namespace="admin">
  <div class="top_content">
      <input type="hidden" name="qUserID" value="<s:property value="qUserID"/>"/>
	  <table width="100%" border="0">
		   <colgroup>
		     <col width="10%"></col>
			 <col width="85%"></col>
			 <col width="5%"></col>
		  </colgroup>
		  <tr>
			<td class="t_left even">
				<input type="radio" name="selectTypeRadio" value="typeTime" style="border:0px;background: transparent;" <s:if test="(#parameters.selectType!= null&&#parameters.selectType[0]=='typeTime')||#parameters.selectType== null">checked</s:if>/>按日期</td>
			<td class="t_left">
				<input name="dateStart" class="b_g" value='<s:property  value="%{#parameters.dateStart[0]}" />' size="10" maxlength="12" style="background: transparent;border:1px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});" readonly/> — <input name="dateEnd" class="b_g mr10" value='<s:property  value="%{#parameters.dateEnd[0]}" />' size="10" maxlength="12" style="background: transparent;border:1px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()}});" readonly/>
	      		<input type="button" name="button" id="button" value="今天" class="btn td_btn" onclick="today()"/>
	      		<input type="button" name="button" id="button" value="昨天" class="btn td_btn" onclick="yesterday()"/>
	      		<input type="button" name="button" id="button" value="本星期" class="btn td_btn" onclick="thisWeek()"/>
	      		<input type="button" name="button" id="button" value="上星期" class="btn td_btn" onclick="lastWeek()"/>
	      		<input type="button" name="button" id="button" value="本月" class="btn td_btn" onclick="thisMonth()"/>
	      		<input type="button" name="button" id="button" value="上月" class="btn td_btn" onclick="lastMonth()"/>
	       </td>
	       <td class="t_right"><input type="submit" name="button" id="button" value="查詢" class="btn td_btn" /></td>
	      </tr>
	  </table>
  </div>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
	  <tbody>
	    <tr>
	  	  <th width="5%">序號</th>
	  	  <th width="10%">登錄時間</th>
	      <th width="10%">IP</th>
	      <th width="10%">IP歸屬</th>
	    </tr>
	 	<!-- 判断是否存在对应的数据 -->
	    <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
	        <tr bgcolor='#FFFFFF' class='list-tr1'>
	            <td class='align-l' colspan='10'>
	                <font color='#FF0000'>未查詢到满足条件的数据！</font>
	            </td>
	        </tr>
	    </s:if>
		         
		<!-- 迭代显示 List 集合中存储的数据记录 -->
		<s:iterator value="#request.resultList" status="st">
		<!-- 行间隔色显示和当前行反白显示，固定写法 -->
		<tr <s:if test="true == #st.odd">class='list-tr2'</s:if><s:else>class='list-tr1'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
			<td class='align-l' nowrap>
				<s:property value="#st.count" />
			</td>
			<td class='align-l'>
				<s:date name="loginDate" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class='align-c' nowrap>
				<s:property value="loginIp" escape="false"/>
			</td>
			<td class='align-c' nowrap>
				<s:property value="ipBelongTo" escape="false"/>
			</td>
		</tr>
		</s:iterator>
		
		<s:if test="0 != #request.resultList.size()">		
			<!-- 分页标签，固定写法 Begin -->
				<tr>
					<td align="right" valign="bottom" nowrap colspan="3">
			            <page:pages_roller sumPages='${sumPages}'> 
			              <a href="<%=Tool.toServletStr(firsturl,thisPageUrl)%>"><img src="${pageContext.request.contextPath}/images/page-first.gif" alt="首页" border="0"/></a>
			              <a href="<%=Tool.toServletStr(prevurl,thisPageUrl)%>"><img src="${pageContext.request.contextPath}/images/page-previous.gif" alt="上一页" border="0"/></a>
			              <a href="<%=Tool.toServletStr(nexturl,thisPageUrl)%>"><img src="${pageContext.request.contextPath}/images/page-next.gif" alt="下一页" border="0"/></a>
			              <a href="<%=Tool.toServletStr(lasturl,thisPageUrl)%>"><img src="${pageContext.request.contextPath}/images/page-last.gif" alt="末页" border="0"/></a>
			            </page:pages_roller>
		            </td> 
		            
		            <td align="right" valign="bottom" nowrap>
		           		第 <font color="#003399">${pageCurrentNo}</font> 页 
		              	共 <font color="#003399">${sumPages}</font> 页
		
		              	<input type="hidden" name="originSumPages" value="${sumPages}"> 
		            </td>
				</tr>
			<!-- 分页标签，固定写法 End -->
		</s:if>
			 
    </table>
    
</s:form>
<script>
	//默认选中当天
	  var startDate=document.sform.dateStart.value ;
	    var endDate=document.sform.dateEnd.value ;
	    if(startDate==''&&endDate=='')
	    	{today();}
	    
	
</script>
  </div>

</div>

