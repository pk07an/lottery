var clDate=1000*Math.floor($("#searchTime").val());
/**
 * 设置定时器，对页面控制
 * 这时候是同时把倒计时，封盘时间，开奖时间都同时进行减的。只是在未封盘时，封盘的标题和时间都是隐藏而已
 */
function GetRTime(){
	if($("#kjTime").is(":hidden")){
		fpDate=fpDate-1000;
	}
    var nMS=fpDate;  
    var nM=Math.floor(nMS/(1000*60)) % 60;  
    if(nM<10) nM='0'+nM;
    var nS=Math.floor(nMS/1000) % 60;
    if(nS<10) nS='0'+nS;  
    //$("#test").val(!$("#kjTime").is(":hidden") + "," +$("#fpTime").html() + ","+fpDate);
    if(fpDate>= 0){
    	if(fpDate>=1000*9&&fpDate<=1000*10){
    		clDate=1000*10;
    	}else{
    		document.getElementById("fpTime").innerHTML=nM+":"+nS;  
    	}
    }  
    else { 
    	$("#fpTime").hide();
    	$("#fpTitle").hide();
    	$("#kjTitle").show();
    	$("#kjTime").show();
    	if(!$("#kjTime").is(":hidden") && $("#fpTime").html()== "00:00"){
    		onChangeSubmit();  
    		
    		/**
    		 * 这里把封盘时间设置为01是为了控制刚封盘时，只刷新一次。
    		 * 为了控制封盘后，开奖前那段时间的刷新。
    		 */
    		$("#fpTime").html("00:01");
    	}
    }
    kjDate=kjDate-1000;
    var kjMS=kjDate;  
    var kjnM=Math.floor(kjMS/(1000*60)) % 60;
    if(kjnM<10) kjnM='0'+kjnM;
  
    var kjnS=Math.floor(kjMS/1000) % 60;
    if(kjnS<10) kjnS='0'+kjnS; 
    if(kjDate>= 0){        
        document.getElementById("kjTime").innerHTML=kjnM+":"+kjnS;  
       
        }  
    else {  
    	/**
    	 * 如果开奖倒时计时小于0时，就把倒数秒数设置为10秒倒数。
    	 */
    	if(clDate>1000*10)
    		clDate=1000*10;
    	if($("#clTime").html()=="0秒")
    		{
    		//if($("#searchTime").val()=="NO"){
    			onChangeSubmit();  //AJAX显示中间内容
    		//}
    		}
    	if($("#searchTime").val()=="NO"){
			onChangeSubmit();  
		}
    } 
    //如果刷新时间选择为NO和页面不是在更新状态中时，计时就减1秒
    if($("#searchTime").val()!="NO" && onrefresh==false){
      clDate=clDate-1000;
    }
    
    var clnS=Math.floor(clDate/1000);
    if(clnS<10) clnS='0'+clnS; 
    if(clDate>= 0){        
        document.getElementById("clTime").innerHTML=clnS+"秒";  
       
        }  
    else {  
    	//alert("时间已经到了");  
    	if($("#searchTime").val()!="NO"){
    		clDate=1000*Math.floor($("#searchTime").val());
    		//clearInterval(timer);
    		clearTimeout(timer);
    		onChangeSubmit();  
    		
    	}
    } 
 
    timer=setTimeout("GetRTime()",1000);  
}  

function changeTime()
{
	onrefresh = false; 
	clearTimeout(timer);//停止定时器
	/**
	 * 如果刷新时间选择为NO,倒数的刷新秒数显示，而更新按钮隐藏，
	 * 如果更新时间选择不为NO，就判断页面是不是正在更新，如果是就显示倒数的刷新秒数，否则就隐藏倒数的刷新秒数。
	 */
	if($("#searchTime").val()=="NO"){
		$("#refreshTime").hide();
		$("#refreshBtn").show();
	}else{
		if(onrefresh == false){
			$("#refreshTime").show();
		}else{
			$("#refreshTime").hide();
		}
		$("#refreshBtn").hide(); 
	}
	$("#onrefresh").show();//显示"更新中..."
	setCltime();
	GetRTime();
}

/**
 * 加载页面时直接
 */
$(document).ready(function(){
	$("td").each(function(){
		if($(this).attr("class")=='rOp')
		{
			if($(this).children().html()<0)
			{
				$(this).attr("style","color:red");
			}else{
				$(this).children().attr("class","");
			}
		}
	
	 });
	
	//**********************************
	$("#refreshBtn").hide();//更新按钮,这个按钮是在NO的情况下才会显示
	onrefresh = false; //判断页面是不是正在"更新中"的状态,更新中时要把定时器关掉
	setCltime();//控制时间的显示
	if($("#searchTime").val()=="NO"){
		$("#refreshTime").hide();
		$("#refreshBtn").show();
	}else{
		if(onrefresh == false){
			$("#refreshTime").show();
		}else{
			$("#refreshTime").hide();
		}
		changeTime(); //加载时触发一下相当于改变刷新时间的选择
	}
	//*********************************
});

