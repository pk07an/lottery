<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.common.Constant" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {	
	var date = new Date(); //日期对象
	var now = "";
	now = date.getFullYear()+"-"; //读英文就行了
	now = now + (date.getMonth()+1)+"-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	now = now + date.getDate()+" ";
	now = now + date.getHours()+":";
	now = now + date.getMinutes()+":";
	now = now + date.getSeconds()+"";
	document.getElementById("nowDiv").innerHTML = now; //div的html是now这个字符串
});


function saveHTMLFile(strFileName)
{
	document.execCommand("saveas","true","a.htm");
}

function show(){
	var date = new Date(); //日期对象
	var now = "";
	now = date.getFullYear()+"-"; //读英文就行了
	now = now + (date.getMonth()+1)+"-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	now = now + date.getDate()+" ";
	now = now + date.getHours()+":";
	now = now + date.getMinutes()+":";
	now = now + date.getSeconds()+"";
	document.getElementById("nowDiv").innerHTML = now; //div的html是now这个字符串
}

</script>
<div class="content">
<div class="main">
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <tbody>
     <tr>
	      <th colspan="6">盘期 &nbsp;&nbsp;&nbsp;&nbsp; <s:property value="#request.periodsNum"/>
	      <!-- <input onClick="SaveAs();" type=button value=另存为>    -->
	      </th>
	 </tr>
    <tr>
      <th width="14%">類型</th>
      <th width="10%">筆</th>
      <th width="19%">實占金額</th>
      <th width="23%">實占輸贏</th>
      <th width="15%">退水</th>
      <th width="19%">退水后結果</th>
      </tr>
      
      <!-- 判断是否存在对应的数据 -->
      <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
          <tr bgcolor='#FFFFFF' class=''>
              <td align="left" colspan='15'>
                  <font color='#FF0000'>没有投注的数据</font>
              </td>
          </tr>
      </s:if>
		            
      <!-- 迭代显示 List 集合中存储的数据记录 -->
	<s:iterator value="#request.resultList" status="st">
		<!-- 行间隔色显示和当前行反白显示，固定写法 -->
		<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
	        <td><s:property value="typeCodeName"/></td>
	        <td><s:property value="turnover"/></td>
	        <td><s:property value="amount"/></td>
	        <td>--</td>
	        <td>--</td>
	        <td>--</td>
	    </tr>
     </s:iterator>
     <tr>
	      <th colspan="6" ><font style="color:#00f">報表生成時間:<font id="nowDiv"></font></font></th>
	 </tr>
     </tbody>
     
      </table>
      
     </div>      
</div>