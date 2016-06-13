<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.odds.action.OddMapProxy" %>
<%@ page import="com.npc.lottery.common.Constant "%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<script>
function changeOdds(typeCode,jiajian)
{
	var ret=null;
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxgdBalloddsUpdate.action?typeCode=' +typeCode+"&jiajian="+jiajian;		
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
	
	
	
	 $("#tj input,#oddTable input").keyup(function(){     
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
<%
Map<String,ShopsPlayOdds> oddMap=(Map<String,ShopsPlayOdds>)request.getAttribute("GDShopOddsMap");  
Map<String,String> moneyMap=(Map<String,String>)request.getAttribute("moneyTotalMap");  
if(moneyMap == null) moneyMap= new HashMap<String,String>();

%>

<div class="content">
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  <div class="title clearfix">
    <h2 class="fl">連碼</h2>
<div class="fl ml10" >
      <span id="clTime">30秒</span>
		<select name="searchTime" id="searchTime" onChange="changeTime()">
	        <option value ="30">30秒</option>
	        <option value ="60" <s:if test="searchTime==60"> selected</s:if>>60秒</option>
	      </select>
    </div>
  </div>
 <s:form action="lmOddsUpdate" id="oddsForm" namespce="admin" name="lmoddsUpdate">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king" id="oddTable">
  <colgroup>
    <col width="15%">
    <col width="15%">
    <col width="30%">
    <col width="40%">
    </colgroup>
  <tbody><tr>
    <th>類型</th>
    <th>賠率/封號</th>
    <th>當前賠率</th>
    <th>下注總額</th>
    </tr>
  <tr>
    <td class="even">任選二</td>
    <td>
      <ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX2").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_RX2">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_RX2" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX2").getState())) out.println("checked"); %>>
              </li>
          </ul>    </td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX2").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX2")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX2")%></span></td>
    </tr>
     <tr>
    <td class="even">選二連組</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_R2LZ">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_R2LZ" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_R2LZ")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_R2LZ")%></span></td>
  </tr>
   <tr>
    <td class="even">任選三</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX3").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_RX3">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_RX3" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX3").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX3").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX3")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX3")%></span></td>
  </tr>
  <tr>
    <td class="even">選三前組</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_R3LZ">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_R3LZ" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_R3LZ")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_R3LZ")%></span></td>
  </tr>
 <tr>
    <td class="even">任選四</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX4").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_RX4">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_RX4" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX4").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX4").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX4")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX4")%></span></td>
  </tr>
  <tr>
    <td class="even">任選五</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX5").getRealOdds() %>" name="GDKLSF_STRAIGHTTHROUGH_RX5">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_STRAIGHTTHROUGH_RX5" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX5").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_STRAIGHTTHROUGH_RX5").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX5")==null?0:moneyMap.get("GDKLSF_STRAIGHTTHROUGH_RX5")%></span></td>
  
</tbody></table>
  <div id="tj">
    <div class="tj">
    <span>
    	<s:hidden name="subType" value="%{#parameters.subType[0]}"/> 
    	<input type="submit" value="提 交" class="btn" name=""/>
    </span>
    <span class="ml10">
    	<input type="reset" value="重 置" class="btn" name=""/>
    </span>
   </div>
    <%-- <div class="all clearfix"><span class="red">统一修改：</span><span class="ml6"><input type="radio" value="" name="">單</span><span class="ml6"><input type="radio" value="" name="">雙</span><span class="ml6"><input type="radio" value="" name="">大</span><span class="ml6"><input type="radio" value="" name="">小</span><span class="ml6"><input type="radio" value="" name="">全部</span><span class="red ml6">赔率</span><input class="b_g ml3 no" maxlength="9" size="6" value="" name="Input22"><span class="ml6"><input type="submit" value="统一修改" class="btn_4" name=""></span></div> --%>
  </div>
  <s:hidden id="periodsState" name="periodsState"></s:hidden>
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
	window.location="${pageContext.request.contextPath}/admin/oddsSet.action?searchTime="+searchTime+"&subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>";
	
}
</script>
</body></html>
