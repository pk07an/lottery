<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.entity.ShopsPlayOdds,com.npc.lottery.odds.action.OddMapProxy" %>
<%@ page import="com.npc.lottery.common.Constant "%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
Map<String,ShopsPlayOdds> oddMap=(Map<String,ShopsPlayOdds>)request.getAttribute("GDShopOddsMap");  
Map<String,String> moneyMap=(Map<String,String>)request.getAttribute("moneyTotalMap");  
if(moneyMap == null) moneyMap= new HashMap<String,String>();

%>

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


<div class="content" style="width:98%;">
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
  <div class="title clearfix">
    <h2 class="fl">雙面賠率設置 &nbsp;&nbsp;&nbsp;</h2>
      <h2 class="t_left">
	 <span id="clTime">0秒</span>
		<select name="searchTime" id="searchTime" onChange="changeTime()">
	        <option value ="30">30秒</option>
	        <option value ="60" <s:if test="searchTime==60"> selected</s:if>>60秒</option>
	      </select>
	</h2>
      
  </div>
    <s:form action="doubleSideOddsUpdate" id="oddsForm" namespce="admin" name="balloddsUpdate">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" id="oddTable">
  <colgroup>
     <col width="22%">
	 <col width="22%">
	 <col width="22%">
	 <col width="22%">
	 <col width="12%">
  </colgroup>
    <tbody><tr>
      <td valign="top">
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        <tbody><tr>
          <th>號</th>
          <th>賠率</th>
          <th>賠率</th>
          <th>注額</th>
        </tr>
        <tr>
          <td class="qiu" colspan="4"><strong><strong>第一球</strong></td>
        </tr>
        <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_1_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_1_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_1_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_1_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_1_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_1_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_1_HSS")%></span></td>
        </tr>
        
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第四球</strong></td>
        </tr>
        <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_4_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_4_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_4_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_4_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_4_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_4_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_4_HSS")%></span></td>
        </tr>
        
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第七球</strong></td>
        </tr>
        <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_7_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_7_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_7_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_7_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_7_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_7_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_7_HSS")%></span></td>
        </tr>
       
      </tbody></table></td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
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
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_X" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_2_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_2_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_2_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_2_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_2_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_2_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_2_HSS")%></span></td>
        </tr>
        
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第五球</strong></td>
        </tr>
       <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_DA" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_5_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_5_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_5_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_5_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_5_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_5_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_5_HSS")%></span></td>
        </tr>
        
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第八球</strong></td>
        </tr>
         <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_X" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_8_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_8_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_8_HSS" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_8_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_8_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_8_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_8_HSS")%></span></td>
        </tr>
       
      </tbody></table></td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
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
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_DA" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_DAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_S" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_WD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_3_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_3_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_3_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_3_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_3_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_3_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_3_HSS")%></span></td>
        </tr>
       
      </tbody></table>
	  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
        <colgroup>
        <col width="18%">
        <col width="38%">
        <col width="22%">
        <col width="22%">
        </colgroup>
        
        <tbody><tr>
          <td class="qiu" colspan="4"><strong>第六球</strong></td>
        </tr>
         <tr>
          <td class="even">大</td>
          <td><ul class="jj clearfix">
              <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
              <li class="in">
                <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_DA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_DA">
              </li>
              <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
              <li class="cb">
                <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_DA" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_DA").getState())) out.println("checked"); %>>
              </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_DA").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_DA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_DA")%></span></td>
        </tr>
        <tr>
          <td class="even">小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_X").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_X">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_X" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_X").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_X").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_X")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_X")%></span></td>
        </tr>
        <tr>
          <td class="even">單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_DAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_DAN">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_DAN" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_DAN").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_DAN").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_DAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_DAN")%></span></td>
        </tr>
        <tr>
          <td class="even">雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_S").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_S">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_S" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_S").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_S").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_S")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_S")%></span></td>
        </tr>
        <tr>
          <td class="even">尾大</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_WD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_WD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_WD" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_WD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_WD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_WD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_WD")%></span></td>
        </tr>
        <tr>
          <td class="even">尾小</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_WX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_WX">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_WX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_WX").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_WX").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_WX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_WX")%></span></td>
        </tr>
        <tr>
          <td class="even">合單</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_HSD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_HSD">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_HSD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_HSD").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_HSD").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_HSD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_HSD")%></span></td>
        </tr>
        <tr>
          <td class="even">合雙</td>
          <td><ul class="jj clearfix">
            <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
            <li class="in">
              <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_6_HSS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_6_HSS">
            </li>
            <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
            <li class="cb">
              <input type="checkbox" value="GDKLSF_DOUBLESIDE_6_HSS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_6_HSS").getState())) out.println("checked"); %>>
            </li>
          </ul></td>
          <td><%=oddMap.get("GDKLSF_DOUBLESIDE_6_HSS").getRealOdds() %></td>
          <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_6_HSS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_6_HSS")%></span></td>
        </tr>
       
      </tbody></table>	  </td>
      <td valign="top"><table width="100%" cellspacing="0" cellpadding="0" border="0" class="king">
  <colgroup>
        <col width="20%">
        <col width="36%">
        <col width="22%">
        <col width="22%">
        </colgroup>
  <tbody><tr>
    <th>號</th>
    <th>賠率</th>
    <th>賠率</th>
    <th>注額</th>
  </tr>
  <tr>
    <td class="even">總大</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHDA").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHDA">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHDA" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHDA").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHDA").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHDA")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHDA")%></span></td>
  </tr>
    <tr>
    <td class="even">總小</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHX">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHX" name="notPlay"  <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHX").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHX").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHX")%></span></td>
  </tr>
  <tr>
    <td class="even">總單</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHDAN").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHDAN">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHDAN" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHDAN").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHDAN").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHDAN")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHDAN")%></span></td>
  </tr>
  <tr>
    <td class="even">總雙</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHS").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHS">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHS" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHS").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHS").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHS")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHS")%></span></td>
  </tr>
  
<tr>
    <td class="even">總尾大</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHWD").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHWD">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHWD" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHWD").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHWD").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHWD")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHWD")%></span></td>
  </tr>
  <tr>
    <td class="even">總尾小</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_ZHWX").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_ZHWX">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_ZHWX" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_ZHWX").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_ZHWX").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_ZHWX")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_ZHWX")%></span></td>
  </tr>
  
  
  
  
  
  
  <tr>
    <td class="even">龍</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_LONG").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_LONG">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_LONG" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_LONG").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_LONG").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_LONG")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_LONG")%></span></td>
  </tr>
 <tr>
    <td class="even">虎</td>
    <td><ul class="jj clearfix">
      <li class="pic"><a href="javascript:void(0)" name="jia"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jia.gif"></a></li>
      <li class="in">
        <input class="b_g" maxlength="9" size="4" value="<%=oddMap.get("GDKLSF_DOUBLESIDE_HU").getRealOdds() %>" name="GDKLSF_DOUBLESIDE_HU">
      </li>
      <li class="pic"><a href="javascript:void(0)" name="jian"><img width="16" height="16" src="${pageContext.request.contextPath}/images/admin/jian.gif"></a></li>
      <li class="cb">
        <input type="checkbox" value="GDKLSF_DOUBLESIDE_HU" name="notPlay" <%if("1".equals(oddMap.get("GDKLSF_DOUBLESIDE_HU").getState())) out.println("checked"); %>>
      </li>
    </ul></td>
    <td><%=oddMap.get("GDKLSF_DOUBLESIDE_HU").getRealOdds() %></td>
    <td><span class="orange"><%=moneyMap.get("GDKLSF_DOUBLESIDE_HU")==null?0:moneyMap.get("GDKLSF_DOUBLESIDE_HU")%></span></td>
  </tr>
  
</tbody></table>
</td>
      <td valign="top"> 
      <cache:cache key='gdklsftwoside' duration='40s'>  
      <s:action name="twoSideAndDragonRank" executeResult="true" flush="false" namespace="/member">
<s:param name="from">GDKLSF</s:param>
</s:action>
</cache:cache>
</td>
    </tr>
  </tbody></table>
  <div id="tj">
    <div class="tj">
    	<span>
    		<s:hidden name="subType" value="%{#parameters.subType[0]}"/>
    		<input type="submit" value="提 交" class="btn" name="">
    	</span>
    	<span class="ml10">
    		 <input type="reset" value="重 置" class="btn" name="">
    	</span>
    	</div>
   <%--  <div class="all clearfix"><span class="red">统一修改：</span><span class="ml6"><input type="radio" value="" name="">單</span><span class="ml6"><input type="radio" value="" name="">雙</span><span class="ml6"><input type="radio" value="" name="">大</span><span class="ml6"><input type="radio" value="" name="">小</span><span class="ml6"><input type="radio" value="" name="">全部</span><span class="red ml6">赔率</span><input class="b_g ml3 no" maxlength="9" size="6" value="" name="Input22"><span class="ml6"><input type="submit" value="统一修改" class="btn_4" name=""></span></div> --%>
  </div>
  <s:hidden id="periodsState" name="periodsState"></s:hidden>
  </s:form>
</div>

<script type="text/javascript">

$(document).ready(function(){
	
	GetRTime();
});
//設置 封盤 時間 
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
	window.location="${pageContext.request.contextPath}/admin/oddsSet.action?searchTime="+searchTime+"&subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DOUBLESIDE %>";
	
}
</script>
</body></html>
