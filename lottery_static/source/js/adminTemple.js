var context="";
function forward(optValue)
	{
	  if(optValue=='GDKLSF')
	      window.location=context + "/admin/oddsSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST %>";
	  else if(optValue=='CQSSC')
		  window.location=context + "/admin/oddsSet.action?subType=<%=Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST %>";
	  else if(optValue=='HKLHC')
		  window.location=context + "/admin/oddsSet.action?subType=<%=Constant.LOTTERY_HKLHC_SUBTYPE_TE_MA %>";
	   
	}
	
	function forwardToLotteryOpenOdds(optValue)
	{window.location=context + "/admin/enterOpenOdds.action?type=privateAdmin&&subType="+optValue;}

	function forwardToTradingSet(optValue)
	{window.location=context + "/admin/enterTradingSet.action?type=privateAdmin&&subType="+optValue;}

	//退出
	function exit(){
		if(confirm("确定退出吗？")){
			if(parent.document.exitForm.isFrame){
				//框架内部的，则跳转到上一级退出
				parent.document.exitForm.submit();
				return true;
			}else{
				document.exitForm.submit();
				return true;
			}
		}
		
		return false;
	}
	function forwardToLotteryHistory(optValue)
    {
    window.location=context + "/admin/enterLotResultHistoryAdmin.action?subType="+optValue;
    }
	
	var searchTime = 30;
	function changeTime()
	{
		searchTime = parseInt($("#searchTime").val())*1000;
		clDate=$("#searchTime").val();
		document.getElementById("clTime").innerHTML=clDate+"秒";  
		
	}