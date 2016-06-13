<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.entity.ShopsPlayOdds" %>
<%@ page import="com.npc.lottery.common.Constant "%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
Map<String,ShopsPlayOdds> oddMap=(Map<String,ShopsPlayOdds>)request.getAttribute("CQSSCShopOddsMap");  
Map<String,String> moneyMap=(Map<String,String>)request.getAttribute("moneyTotalMap");  
if(moneyMap == null) moneyMap= new HashMap<String,String>();

%>
<script>
function changeOdds(typeCode,jiajian)
{
	var ret=null;
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxCQBalloddsUpdate.action?typeCode=' +typeCode+"&jiajian="+jiajian;		
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({url:queryUrl,async:false,dataType:"json",data:{typeCode:typeCode,jiajian:jiajian},type:"POST",success:successChangeOdd});
	return ret;
	
}
function successChangeOdd(json)
{
//alert(inputName);	
	var  error = json.error;
	if(undefined!=error){alert(error);return null;}
	
var changedValue=json.changedValue;
var inputName=json.typeCode;
$('input[name='+inputName+']').parent().parent().parent().next().html(changedValue);
$('input[name='+inputName+']').val(changedValue);
//alert($('input[name='+inputName+']').parent().parent().parent().next().html());
}
$(document).ready(function() {
	
	
	
	$('#oddTable a').click(function() {
		  
		  if($(this).attr("name")=="jia")
			  {
			  var typeCode=$(this).parent().next().children().attr("name");
			  changeOdds(typeCode,"jia");
			  }
		  else if($(this).attr("name")=="jian")
			  {
			  var typeCode=$(this).parent().prev().children().attr("name");
			  changeOdds(typeCode,"jian");
			  }
	        
		});
	
	$('#s1,#s2').click(function() {
		 var btName=$(this).attr("name");
		 $('#submitType').val(btName);
		 $('#oddsForm').submit();
		  
		});
	
	
	
	 $("#tj input,.king input").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(/[^\d.]/g,""));
		        $(this).val($(this).val().replace(/^\./g,""));  
		       $(this).val($(this).val().replace(/\.{2,}/g,"."));  
		       $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	       }
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();
	        $(this).val(tmptxt.replace(/[^\d.]/g,""));     
	    }).css("ime-mode", "disabled"); 

	 var tt = document.getElementById("periodsState").value;
		if(tt!="")alert("當前盤未開盤，不能修改");
});


</script>
<body>

<% String ballNum="1"; String chinaBall="";
%>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_FIRST==subType'><% ballNum="1";chinaBall="第一球"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_SECOND==subType'><% ballNum="2";chinaBall="第二球"; %></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_THIRD==subType'><% ballNum="3"; chinaBall="第三球";%></s:if>
 <s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_FORTH==subType'><% ballNum="4"; chinaBall="第四球";%></s:if>
<s:if test='@com.npc.lottery.common.Constant@LOTTERY_CQSSC_SUBTYPE_BALL_FIFTH==subType'><% ballNum="5";chinaBall="第五球"; %></s:if>

<div class="content" style="width:98%;">
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
<s:form action="cqsscoddsUpdate" id="oddsForm" namespce="admin" name="cqsscoddsUpdate">
 <s:hidden id="subType" name="subType"></s:hidden>
   <div class="title clearfix">
    <h2 class="fl">重慶時時彩</h2>
<div class="fl ml10" >
      	 <span id="clTime">30秒</span>
		<select id="searchTime" name="searchTime" onchange="changeTime()" >
	        <option value ="30">30秒</option>
	        <option value ="60" <s:if test="searchTime==60"> selected</s:if>>60秒</option>
	      </select>
    </div>
  </div>
  <table width="100%" cellspacing="0" cellpadding="0" border="0" id="oddTable">
  <colgroup>
     <col width="22%">
	 <col width="22%">
	 <col width="22%">
	 <col width="22%">
	 <col width="12%">
  </colgroup>
    <tbody><tr>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="10%">
        <col width="50%">
        <col width="20%">
        <col width="20%">
        </colgroup>
        <tbody><tr>
          <th>號</th>
          <th>賠率</th>
          <th>賠率</th>
          <th>注額</th>
        </tr>
        <tr>
          <td class="qiu" colspan="4"><strong>第一球</strong></td>
        </tr>
        <tr>
          <td class="even"><strong>大</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_1_DA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_1_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_1_DA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_1_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_1_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_1_DA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_1_DA")%></span></td>
        </tr>
         <tr>
          <td class="even"><strong>小</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_1_X").getRealOdds() %>" name="CQSSC_DOUBLESIDE_1_X">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_1_X" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_1_X").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_1_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_1_X")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_1_X")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>單</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_1_DAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_1_DAN">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_1_DAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_1_DAN").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_1_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_1_DAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_1_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>雙</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_1_S").getRealOdds() %>" name="CQSSC_DOUBLESIDE_1_S">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_1_S" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_1_S").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_1_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_1_S")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_1_S")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">0</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_0").getRealOdds() %>" name="CQSSC_BALL_FIRST_0">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_0" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_0").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_0").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_0")==null?0:moneyMap.get("CQSSC_BALL_FIRST_0")%></span></td>
        </tr>
          <tr>
          <td class="even"><strong class="blue">1</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_1").getRealOdds() %>" name="CQSSC_BALL_FIRST_1">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_1" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_1").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_1").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_1")==null?0:moneyMap.get("CQSSC_BALL_FIRST_1")%></span></td>
        </tr>
   <tr>
          <td class="even"><strong class="blue">2</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_2").getRealOdds() %>" name="CQSSC_BALL_FIRST_2">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_2" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_2").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_2").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_2")==null?0:moneyMap.get("CQSSC_BALL_FIRST_2")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">3</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_3").getRealOdds() %>" name="CQSSC_BALL_FIRST_3">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_3" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_3").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_3").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_3")==null?0:moneyMap.get("CQSSC_BALL_FIRST_3")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">4</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_4").getRealOdds() %>" name="CQSSC_BALL_FIRST_4">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_4" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_4").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_4").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_4")==null?0:moneyMap.get("CQSSC_BALL_FIRST_4")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">5</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_5").getRealOdds() %>" name="CQSSC_BALL_FIRST_5">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_5" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_5").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_5").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_5")==null?0:moneyMap.get("CQSSC_BALL_FIRST_5")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">6</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_6").getRealOdds() %>" name="CQSSC_BALL_FIRST_6">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_6" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_6").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_6").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_6")==null?0:moneyMap.get("CQSSC_BALL_FIRST_6")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">7</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_7").getRealOdds() %>" name="CQSSC_BALL_FIRST_7">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_7" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_7").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_7").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_7")==null?0:moneyMap.get("CQSSC_BALL_FIRST_7")%></span></td>
        </tr>
     <tr>
          <td class="even"><strong class="blue">8</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_8").getRealOdds() %>" name="CQSSC_BALL_FIRST_8">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_8" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_8").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_8").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_8")==null?0:moneyMap.get("CQSSC_BALL_FIRST_8")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">9</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIRST_9").getRealOdds() %>" name="CQSSC_BALL_FIRST_9">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIRST_9" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIRST_9").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIRST_9").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIRST_9")==null?0:moneyMap.get("CQSSC_BALL_FIRST_9")%></span></td>
        </tr>
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="10%">
        <col width="50%">
        <col width="20%">
        <col width="20%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第四球</strong></td>
        </tr>
       <tr>
          <td class="even"><strong>大</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_4_DA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_4_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_4_DA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_4_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_4_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_4_DA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_4_DA")%></span></td>
        </tr>
         <tr>
          <td class="even"><strong>小</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_4_X").getRealOdds() %>" name="CQSSC_DOUBLESIDE_4_X">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_4_X" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_4_X").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_4_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_4_X")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_4_X")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>單</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_4_DAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_4_DAN">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_4_DAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_4_DAN").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_4_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_4_DAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_4_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>雙</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_4_S").getRealOdds() %>" name="CQSSC_DOUBLESIDE_4_S">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_4_S" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_4_S").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_4_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_4_S")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_4_S")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">0</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_0").getRealOdds() %>" name="CQSSC_BALL_FORTH_0">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_0" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_0").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_0").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_0")==null?0:moneyMap.get("CQSSC_BALL_FORTH_0")%></span></td>
        </tr>
          <tr>
          <td class="even"><strong class="blue">1</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_1").getRealOdds() %>" name="CQSSC_BALL_FORTH_1">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_1" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_1").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_1").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_1")==null?0:moneyMap.get("CQSSC_BALL_FORTH_1")%></span></td>
        </tr>
   <tr>
          <td class="even"><strong class="blue">2</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_2").getRealOdds() %>" name="CQSSC_BALL_FORTH_2">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_2" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_2").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_2").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_2")==null?0:moneyMap.get("CQSSC_BALL_FORTH_2")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">3</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_3").getRealOdds() %>" name="CQSSC_BALL_FORTH_3">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_3" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_3").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_3").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_3")==null?0:moneyMap.get("CQSSC_BALL_FORTH_3")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">4</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_4").getRealOdds() %>" name="CQSSC_BALL_FORTH_4">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_4" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_4").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_4").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_4")==null?0:moneyMap.get("CQSSC_BALL_FORTH_4")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">5</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_5").getRealOdds() %>" name="CQSSC_BALL_FORTH_5">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_5" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_5").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_5").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_5")==null?0:moneyMap.get("CQSSC_BALL_FORTH_5")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">6</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_6").getRealOdds() %>" name="CQSSC_BALL_FORTH_6">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_6" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_6").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_6").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_6")==null?0:moneyMap.get("CQSSC_BALL_FORTH_6")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">7</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_7").getRealOdds() %>" name="CQSSC_BALL_FORTH_7">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_7" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_7").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_7").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_7")==null?0:moneyMap.get("CQSSC_BALL_FORTH_7")%></span></td>
        </tr>
     <tr>
          <td class="even"><strong class="blue">8</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_8").getRealOdds() %>" name="CQSSC_BALL_FORTH_8">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_8" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_8").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_8").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_8")==null?0:moneyMap.get("CQSSC_BALL_FORTH_8")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">9</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FORTH_9").getRealOdds() %>" name="CQSSC_BALL_FORTH_9">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FORTH_9" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FORTH_9").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FORTH_9").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FORTH_9")==null?0:moneyMap.get("CQSSC_BALL_FORTH_9")%></span></td>
        </tr>
      </tbody></table>
	  </td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="10%">
        <col width="50%">
        <col width="20%">
        <col width="20%">
        </colgroup>
        <tbody><tr>
          <th>號</th>
          <th>賠率</th>
          <th>賠率</th>
          <th>注額</th>
        </tr>
        <tr>
          <td class="qiu" colspan="4"><strong>第二球</strong></td>
        </tr>
         <tr>
          <td class="even"><strong>大</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_2_DA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_2_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_2_DA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_2_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_2_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_2_DA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_2_DA")%></span></td>
        </tr>
         <tr>
          <td class="even"><strong>小</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_2_X").getRealOdds() %>" name="CQSSC_DOUBLESIDE_2_X">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_2_X" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_2_X").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_2_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_2_X")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_2_X")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>單</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_2_DAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_2_DAN">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_2_DAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_2_DAN").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_2_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_2_DAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_2_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>雙</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_2_S").getRealOdds() %>" name="CQSSC_DOUBLESIDE_2_S">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_2_S" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_2_S").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_2_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_2_S")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_2_S")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">0</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_0").getRealOdds() %>" name="CQSSC_BALL_SECOND_0">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_0" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_0").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_0").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_0")==null?0:moneyMap.get("CQSSC_BALL_SECOND_0")%></span></td>
        </tr>
          <tr>
          <td class="even"><strong class="blue">1</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_1").getRealOdds() %>" name="CQSSC_BALL_SECOND_1">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_1" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_1").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_1").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_1")==null?0:moneyMap.get("CQSSC_BALL_SECOND_1")%></span></td>
        </tr>
   <tr>
          <td class="even"><strong class="blue">2</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_2").getRealOdds() %>" name="CQSSC_BALL_SECOND_2">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_2" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_2").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_2").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_2")==null?0:moneyMap.get("CQSSC_BALL_SECOND_2")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">3</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_3").getRealOdds() %>" name="CQSSC_BALL_SECOND_3">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_3" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_3").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_3").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_3")==null?0:moneyMap.get("CQSSC_BALL_SECOND_3")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">4</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_4").getRealOdds() %>" name="CQSSC_BALL_SECOND_4">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_4" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_4").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_4").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_4")==null?0:moneyMap.get("CQSSC_BALL_SECOND_4")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">5</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_5").getRealOdds() %>" name="CQSSC_BALL_SECOND_5">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_5" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_5").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_5").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_5")==null?0:moneyMap.get("CQSSC_BALL_SECOND_5")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">6</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_6").getRealOdds() %>" name="CQSSC_BALL_SECOND_6">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_6" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_6").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_6").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_6")==null?0:moneyMap.get("CQSSC_BALL_SECOND_6")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">7</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_7").getRealOdds() %>" name="CQSSC_BALL_SECOND_7">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_7" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_7").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_7").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_7")==null?0:moneyMap.get("CQSSC_BALL_SECOND_7")%></span></td>
        </tr>
     <tr>
          <td class="even"><strong class="blue">8</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_8").getRealOdds() %>" name="CQSSC_BALL_SECOND_8">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_8" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_8").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_8").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_8")==null?0:moneyMap.get("CQSSC_BALL_SECOND_8")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">9</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_SECOND_9").getRealOdds() %>" name="CQSSC_BALL_SECOND_9">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_SECOND_9" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_SECOND_9").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_SECOND_9").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_SECOND_9")==null?0:moneyMap.get("CQSSC_BALL_SECOND_9")%></span></td>
        </tr>
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="10%">
        <col width="50%">
        <col width="20%">
        <col width="20%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第五球</strong></td>
        </tr>
         <tr>
          <td class="even"><strong>大</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_5_DA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_5_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_5_DA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_5_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_5_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_5_DA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_5_DA")%></span></td>
        </tr>
         <tr>
          <td class="even"><strong>小</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_5_X").getRealOdds() %>" name="CQSSC_DOUBLESIDE_5_X">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_5_X" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_5_X").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_5_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_5_X")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_5_X")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>單</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_5_DAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_5_DAN">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_5_DAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_5_DAN").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_5_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_5_DAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_5_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>雙</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_5_S").getRealOdds() %>" name="CQSSC_DOUBLESIDE_5_S">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_5_S" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_5_S").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_5_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_5_S")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_5_S")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">0</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_0").getRealOdds() %>" name="CQSSC_BALL_FIFTH_0">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_0" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_0").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_0").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_0")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_0")%></span></td>
        </tr>
          <tr>
          <td class="even"><strong class="blue">1</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_1").getRealOdds() %>" name="CQSSC_BALL_FIFTH_1">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_1" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_1").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_1").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_1")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_1")%></span></td>
        </tr>
   <tr>
          <td class="even"><strong class="blue">2</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_2").getRealOdds() %>" name="CQSSC_BALL_FIFTH_2">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_2" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_2").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_2").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_2")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_2")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">3</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_3").getRealOdds() %>" name="CQSSC_BALL_FIFTH_3">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_3" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_3").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_3").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_3")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_3")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">4</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_4").getRealOdds() %>" name="CQSSC_BALL_FIFTH_4">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_4" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_4").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_4").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_4")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_4")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">5</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_5").getRealOdds() %>" name="CQSSC_BALL_FIFTH_5">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_5" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_5").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_5").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_5")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_5")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">6</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_6").getRealOdds() %>" name="CQSSC_BALL_FIFTH_6">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_6" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_6").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_6").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_6")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_6")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">7</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_7").getRealOdds() %>" name="CQSSC_BALL_FIFTH_7">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_7" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_7").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_7").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_7")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_7")%></span></td>
        </tr>
     <tr>
          <td class="even"><strong class="blue">8</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_8").getRealOdds() %>" name="CQSSC_BALL_FIFTH_8">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_8" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_8").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_8").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_8")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_8")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">9</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_FIFTH_9").getRealOdds() %>" name="CQSSC_BALL_FIFTH_9">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_FIFTH_9" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_FIFTH_9").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_FIFTH_9").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_FIFTH_9")==null?0:moneyMap.get("CQSSC_BALL_FIFTH_9")%></span></td>
        </tr>
      </tbody></table>
	  </td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="10%">
        <col width="50%">
        <col width="20%">
        <col width="20%">
        </colgroup>
        <tbody><tr>
          <th>號</th>
          <th>賠率</th>
          <th>賠率</th>
          <th>注額</th>
        </tr>
        <tr>
          <td class="qiu" colspan="4"><strong>第三球</strong></td>
        </tr>
                <tr>
          <td class="even"><strong>大</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_3_DA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_3_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_3_DA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_3_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_3_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_3_DA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_3_DA")%></span></td>
        </tr>
         <tr>
          <td class="even"><strong>小</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_3_X").getRealOdds() %>" name="CQSSC_DOUBLESIDE_3_X">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_3_X" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_3_X").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_3_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_3_X")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_3_X")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>單</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_3_DAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_3_DAN">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_3_DAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_3_DAN").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_3_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_3_DAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_3_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong>雙</strong></td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_3_S").getRealOdds() %>" name="CQSSC_DOUBLESIDE_3_S">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="CQSSC_DOUBLESIDE_3_S" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_3_S").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_DOUBLESIDE_3_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_3_S")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_3_S")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">0</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_0").getRealOdds() %>" name="CQSSC_BALL_THIRD_0">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_0" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_0").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_0").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_0")==null?0:moneyMap.get("CQSSC_BALL_THIRD_0")%></span></td>
        </tr>
          <tr>
          <td class="even"><strong class="blue">1</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_1").getRealOdds() %>" name="CQSSC_BALL_THIRD_1">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_1" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_1").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_1").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_1")==null?0:moneyMap.get("CQSSC_BALL_THIRD_1")%></span></td>
        </tr>
   <tr>
          <td class="even"><strong class="blue">2</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_2").getRealOdds() %>" name="CQSSC_BALL_THIRD_2">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_2" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_2").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_2").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_2")==null?0:moneyMap.get("CQSSC_BALL_THIRD_2")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">3</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_3").getRealOdds() %>" name="CQSSC_BALL_THIRD_3">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_3" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_3").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_3").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_3")==null?0:moneyMap.get("CQSSC_BALL_THIRD_3")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">4</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_4").getRealOdds() %>" name="CQSSC_BALL_THIRD_4">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_4" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_4").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_4").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_4")==null?0:moneyMap.get("CQSSC_BALL_THIRD_4")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">5</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_5").getRealOdds() %>" name="CQSSC_BALL_THIRD_5">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_5" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_5").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_5").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_5")==null?0:moneyMap.get("CQSSC_BALL_THIRD_5")%></span></td>
        </tr>
       <tr>
          <td class="even"><strong class="blue">6</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_6").getRealOdds() %>" name="CQSSC_BALL_THIRD_6">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_6" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_6").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_6").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_6")==null?0:moneyMap.get("CQSSC_BALL_THIRD_6")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">7</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_7").getRealOdds() %>" name="CQSSC_BALL_THIRD_7">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_7" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_7").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_7").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_7")==null?0:moneyMap.get("CQSSC_BALL_THIRD_7")%></span></td>
        </tr>
     <tr>
          <td class="even"><strong class="blue">8</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_8").getRealOdds() %>" name="CQSSC_BALL_THIRD_8">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_8" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_8").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_8").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_8")==null?0:moneyMap.get("CQSSC_BALL_THIRD_8")%></span></td>
        </tr>
        <tr>
          <td class="even"><strong class="blue">9</strong></td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BALL_THIRD_9").getRealOdds() %>" name="CQSSC_BALL_THIRD_9">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="CQSSC_BALL_THIRD_9" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BALL_THIRD_9").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("CQSSC_BALL_THIRD_9").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("CQSSC_BALL_THIRD_9")==null?0:moneyMap.get("CQSSC_BALL_THIRD_9")%></span></td>
        </tr>
      </tbody></table>
	  </td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
  <colgroup>
        <col width="14%">
        <col width="50%">
        <col width="18%">
        <col width="18%">
        </colgroup>
  <tbody><tr>
    <th>號</th>
    <th>賠率</th>
    <th>賠率</th>
    <th>注額</th>
  </tr>
  <tr>
    <td class="even"><strong>總大</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_ZHDA").getRealOdds() %>" name="CQSSC_DOUBLESIDE_ZHDA">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_ZHDA" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_ZHDA").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_ZHDA").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_ZHDA")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_ZHDA")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>總小</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_ZHX").getRealOdds() %>" name="CQSSC_DOUBLESIDE_ZHX">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_ZHX" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_ZHX").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_ZHX").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_ZHX")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_ZHX")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>總單</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_ZHDAN").getRealOdds() %>" name="CQSSC_DOUBLESIDE_ZHDAN">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_ZHDAN" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_ZHDAN").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_ZHDAN").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_ZHDAN")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_ZHDAN")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>總雙</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_ZHS").getRealOdds() %>" name="CQSSC_DOUBLESIDE_ZHS">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_ZHS" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_ZHS").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_ZHS").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_ZHS")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_ZHS")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>龍</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_LONG").getRealOdds() %>" name="CQSSC_DOUBLESIDE_LONG">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_LONG" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_LONG").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_LONG").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_LONG")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_LONG")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>虎</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_HU").getRealOdds() %>" name="CQSSC_DOUBLESIDE_HU">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_HU" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_HU").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_HU").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_HU")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_HU")%></span></td>
  </tr>
<tr>
    <td class="even"><strong>和</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DOUBLESIDE_HE").getRealOdds() %>" name="CQSSC_DOUBLESIDE_HE">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DOUBLESIDE_HE" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DOUBLESIDE_HE").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DOUBLESIDE_HE").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DOUBLESIDE_HE")==null?0:moneyMap.get("CQSSC_DOUBLESIDE_HE")%></span></td>
  </tr>
</tbody></table>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
  <colgroup>
        <col width="14%">
        <col width="50%">
        <col width="18%">
        <col width="18%">
        </colgroup>
  <tbody><tr>
    <td class="qiu" colspan="4"><strong>前三</strong></td>
  </tr>
  <tr>
    <td class="even"><strong>豹子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BZ_FRONT").getRealOdds() %>" name="CQSSC_BZ_FRONT">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BZ_FRONT" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BZ_FRONT").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BZ_FRONT").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BZ_FRONT")==null?0:moneyMap.get("CQSSC_BZ_FRONT")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>順子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_SZ_FRONT").getRealOdds() %>" name="CQSSC_SZ_FRONT">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_SZ_FRONT" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_SZ_FRONT").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_SZ_FRONT").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_SZ_FRONT")==null?0:moneyMap.get("CQSSC_SZ_FRONT")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>對子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DZ_FRONT").getRealOdds() %>" name="CQSSC_DZ_FRONT">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DZ_FRONT" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DZ_FRONT").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DZ_FRONT").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DZ_FRONT")==null?0:moneyMap.get("CQSSC_DZ_FRONT")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>半順</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BS_FRONT").getRealOdds() %>" name="CQSSC_BS_FRONT">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BS_FRONT" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BS_FRONT").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BS_FRONT").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BS_FRONT")==null?0:moneyMap.get("CQSSC_BS_FRONT")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>雜六</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_ZL_FRONT").getRealOdds() %>" name="CQSSC_ZL_FRONT">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_ZL_FRONT" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_ZL_FRONT").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_ZL_FRONT").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_ZL_FRONT")==null?0:moneyMap.get("CQSSC_ZL_FRONT")%></span></td>
  </tr>
</tbody></table>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
  <colgroup>
        <col width="14%">
        <col width="50%">
        <col width="18%">
        <col width="18%">
        </colgroup>
  <tbody><tr>
    <td class="qiu" colspan="4"><strong>中三</strong></td>
  </tr>
   <tr>
    <td class="even"><strong>豹子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BZ_MID").getRealOdds() %>" name="CQSSC_BZ_MID">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BZ_MID" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BZ_MID").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BZ_MID").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BZ_MID")==null?0:moneyMap.get("CQSSC_BZ_MID")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>順子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_SZ_MID").getRealOdds() %>" name="CQSSC_SZ_MID">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_SZ_MID" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_SZ_MID").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_SZ_MID").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_SZ_MID")==null?0:moneyMap.get("CQSSC_SZ_MID")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>對子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DZ_MID").getRealOdds() %>" name="CQSSC_DZ_MID">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DZ_MID" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DZ_MID").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DZ_MID").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DZ_MID")==null?0:moneyMap.get("CQSSC_DZ_MID")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>半順</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BS_MID").getRealOdds() %>" name="CQSSC_BS_MID">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BS_MID" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BS_MID").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BS_MID").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BS_MID")==null?0:moneyMap.get("CQSSC_BS_MID")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>雜六</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_ZL_MID").getRealOdds() %>" name="CQSSC_ZL_MID">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_ZL_MID" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_ZL_MID").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_ZL_MID").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_ZL_MID")==null?0:moneyMap.get("CQSSC_ZL_MID")%></span></td>
  </tr>
</tbody></table>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
  <colgroup>
        <col width="14%">
        <col width="50%">
        <col width="18%">
        <col width="18%">
        </colgroup>
  <tbody><tr>
    <td class="qiu" colspan="4"><strong>后三</strong></td>
  </tr>
  <tr>
    <td class="even"><strong>豹子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BZ_LAST").getRealOdds() %>" name="CQSSC_BZ_LAST">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BZ_LAST" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BZ_LAST").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BZ_LAST").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BZ_LAST")==null?0:moneyMap.get("CQSSC_BZ_LAST")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>順子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_SZ_LAST").getRealOdds() %>" name="CQSSC_SZ_LAST">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_SZ_LAST" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_SZ_LAST").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_SZ_LAST").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_SZ_LAST")==null?0:moneyMap.get("CQSSC_SZ_LAST")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>對子</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_DZ_LAST").getRealOdds() %>" name="CQSSC_DZ_LAST">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_DZ_LAST" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_DZ_LAST").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_DZ_LAST").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_DZ_LAST")==null?0:moneyMap.get("CQSSC_DZ_LAST")%></span></td>
  </tr>
  <tr>
    <td class="even"><strong>半順</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_BS_LAST").getRealOdds() %>" name="CQSSC_BS_LAST">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_BS_LAST" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_BS_LAST").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_BS_LAST").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_BS_LAST")==null?0:moneyMap.get("CQSSC_BS_LAST")%></span></td>
  </tr>
   <tr>
    <td class="even"><strong>雜六</strong></td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("CQSSC_ZL_LAST").getRealOdds() %>" name="CQSSC_ZL_LAST">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="CQSSC_ZL_LAST" name="notPlay" <%if("1".equals(oddMap.get("CQSSC_ZL_LAST").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("CQSSC_ZL_LAST").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("CQSSC_ZL_LAST")==null?0:moneyMap.get("CQSSC_ZL_LAST")%></span></td>
  </tr>
</tbody></table></td>
<td valign="top">
<cache:cache key='cqssctwoside' duration='40s'>  
 <s:action name="twoSideAndDragonRank" executeResult="true" flush="false" namespace="/member">
<s:param name="from">CQSSC</s:param>
</s:action>
</cache:cache>


</td>
    </tr>
  </tbody></table>
  <div id="tj">
    <div class="tj">
    <span>
    	<input type="submit" value="提 交" class="btn" name="">
    </span>
   	<span class="ml10">
    	<input type="reset" value="重 置" class="btn" name="">
   	</span>
    </div>
    <%-- <div class="all clearfix"><span class="red">统一修改：</span><span class="ml6"><input type="radio" value="" name="">單</span><span class="ml6"><input type="radio" value="" name="">雙</span><span class="ml6"><input type="radio" value="" name="">大</span><span class="ml6"><input type="radio" value="" name="">小</span><span class="ml6"><input type="radio" value="" name="">全部</span><span class="red ml6">赔率</span><input class="b_g ml3 no" maxlength="9" size="6" value="" name="Input22"><span class="ml6"><input type="submit" value="统一修改" class="btn_4" name=""></span></div> --%>
  </div>
  <input type="hidden" id="periodsState"  value="<s:property value="#request.periodsState"/>"/>
 </s:form> 
</div>

<script type="text/javascript">
$(document).ready(function(){
	
	GetRTime();
});
var clDate=1000*Math.floor($("#searchTime").val());
function GetRTime(){ 
    clDate=clDate-1000; 
    var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
    if(clDate>= 0){        
         document.getElementById("clTime").innerHTML=clnS+"秒";  
    }  
    else {  
    	clDate=1000*Math.floor($("#searchTime").val());
//     	window.location.reload();
    	changeTime();
    } 
 
    setTimeout("GetRTime()",1000);  
}  
function changeTime()
{
	var searchTime = $("#searchTime").val();
	window.location="${pageContext.request.contextPath}/admin/oddsSet.action?searchTime="+searchTime+"&subType=<%=Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST %>";
	
}
</script>

</body></html>
