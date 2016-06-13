//去除空格
String.prototype.NoSpace = function() {
    return this.replace(/\s+/g, "");  
} 
var _change = {
		ary0:["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
		ary1:["", "十", "百", "千"],
		ary2:["", "万", "亿", "兆"],
		init:function (name) {
		this.name = name;  
		},
		strrev:function () {
		var ary = [];
		for (var i = this.name.length; i >= 0; i--) {
		//ary.push(this.name[i]);
			ary.push(this.name.charAt(i));
		}
		return ary.join("");
		}, //倒转字符串。
		pri_ary:function () {
		var $this = this;
		var arys = this.strrev();
		var zero = "";
		var newary = "";
		var i4 = -1;
		var ary=[];
		for(var j=0;j<arys.length;j++)
		{
			ary.push(arys.charAt(j));
		}
		
		for (var i = 0; i < ary.length; i++) {
		if (i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
		i4++;
		newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
		zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

		}
		//关于0的处理与判断。
		if (ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
		switch (i % 4) {
		case 0:
		break;
		//如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
		case 1:
		case 2:
		case 3:
		if (ary[i - 1] != '0') {
		zero = "零";
		}
		; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
		break;

		}

		newary = zero + newary;
		zero = '';
		}
		else { //如果不是“0”
		newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
		}

		}
		if (newary.indexOf("零") == 0) {
		newary = newary.substr(1);
		}//处理前面的0
		
		return newary.replace("亿万", "亿");
		}
		};

		//创建class类
		function change() {
		this.init.apply(this, arguments);
		}
		change.prototype = _change; 
		
		
		
		
		
		Loginmgmt = {
				passwordChecker:{
					passwordReg:/^[\w\W]{8,20}$/,
					specialReg:/[\W_]+/,
					numberReg:/[0-9]+/,
					letter:/([a-z]|[A-Z])+/,
					capLetter:/[A-Z]/,
					lowLetter:/[a-z]/,
					specialLetter:/[\W_]/,
					check:function(password){
						if (!this.passwordReg.test(password)) {
							//alert("1");
							return false;
						}
						
						if (this.checkSeqChar(password)) {
							//alert("連續的passwrod");
							return false;
						}
						if(this.isRepeat4Times(password))
						{
							//alert("重複的passwrod");
							return false;
						}
						return true;
						//return this.isCategoryAmountEnough(password, 3);
					},
					isCategoryAmountEnough:function(password, minCategoryAmount){
						var numIsExist = 0;
					    var letterIsExist = 0;
					    var specialIsExist = 0;
					    if (this.specialReg.test(password)) {
					    	specialIsExist = 1;
					    }
					    if (this.numberReg.test(password)) {
					    	numIsExist = 1;
					    }
					    if (this.letter.test(password)) {
					    	letterIsExist = 1;
					    }
					    return (numIsExist + letterIsExist + specialIsExist) == minCategoryAmount;
					},
					isRepeat:function(password) {
						for(var i = 0;i < password.length; i ++){
					        for(var j = i + 1; j < password.length; j ++){
					            if(password.charAt(i) == password.charAt(j)){
					                 return true;
					            }
					        }
					    }
						return false;
					},
					checkNumSeq:function(password){
						if(password){
							var len = password.length;
							for(var i = 0; i < len; ++i){
								if(i + 3 < len){
									var c1 = password.charAt(i) - 0;
									var c2 = password.charAt(i + 1) - 0;
									var c3 = password.charAt(i + 2) - 0;
									var c4 = password.charAt(i + 3) - 0;

									if(isNaN(c1) || isNaN(c2) || isNaN(c3) || isNaN(c4))
										continue;

									var m = c2 - c1;
									if(m == 1 || m == -1){
										if((c3 - c2) == m && (c4 - c3) == m)
											return true;
									}
								}
							}
						}
						return false;
					},
					isRepeat4Times:function(password){
						if(password){
							var pwd = password.toUpperCase();
							var len = pwd.length;
							for(var i = 0; i < len; ++i){
								if(i + 4 < len){
									var u1 = pwd.charAt(i);
									var u2 = pwd.charAt(i + 1);
									var u3 = pwd.charAt(i + 2);
									var u4 = pwd.charAt(i + 3);

									if(u1 == u2 && u2 == u3 && u3 == u4)
										return true;
								}
							}
						}
						return false;
					},
					isNotComplex:function(password){
						if(password){
							var comp = 0;
							if(this.capLetter.test(password))
								comp += 1;
							if(this.lowLetter.test(password))
								comp += 1;
							if(this.numberReg.test(password))
								comp += 1;
							if(this.specialLetter.test(password))
								comp += 1;
							if(comp < 3)
								return true;
						}
						return false;
					},
					checkSeqChar:function(password){
						if(password){
							var pwd = password.toUpperCase();
							var len = pwd.length;
							for(var i = 0; i < len; ++i){
								if(i + 3 < len){
									var u1 = pwd.charCodeAt(i);
									var u2 = pwd.charCodeAt(i + 1);
									var u3 = pwd.charCodeAt(i + 2);
									var u4 = pwd.charCodeAt(i + 3);
									
									var m = u2 - u1;
									if(m == 1 || m == -1){
										if((u3 - u2 == m) && (u4 - u3 == m))
											return true;
									}
								}
							}
						}
						return false;
					},
					isStartOrEndBySF:function(password){
						if(password && password.length >= 2){
							var c1 = password.charAt(0);
							var c2 = password.charAt(1);
							var c3 = password.charAt(password.length - 2);
							var c4 = password.charAt(password.length - 1);
							if((c1 == 's' || c1 == 'S') && (c2 == 'f' || c2 == 'F')){
									return true;
							}
							if((c3 == 's' || c3 == 'S') && (c4 == 'f' || c4 == 'F')){
								return true;
							}
						}
						return false;
					},
					isIncludeUsername:function(username, password){
						if(password && username){
							var idx = password.indexOf(username);
							if(idx >= 0)
								return true;
						}
						return false;
					},
					isIncludeBenWord:function(password){
						var a = Loginmgmt.benWords;
						for(var i = 0; i < a.length; ++i){
							if(this.isIncludeUsername(a[i], password))
								return true;
						}
						return false;
					}
				},
				benWords:['admin', 'pass']
			};



			function passwordCheck(pwd)
			{
				
				var mes1="'密碼' 最小長度 必須 8位 或以上 (最長20位)";
				var mes2="出於安全考慮  '密碼' 需包含兩個以上英文字母和數字等符號組成。其中不包含四個連續 '相同字符' 或'順序數字' 如  aaaa,1234,4321 (特別注意：不要重複使用老密碼)";
				if(pwd.length<8)
				{
					alert(mes1);
					return false;
				}
				else if(!Loginmgmt.passwordChecker.check(pwd))
				{
					alert(mes2);
					return false;
				}
				var filter=/^(?![a-z]+$)(?!\d+$)[a-z0-9]{8,20}$/i;
				var numbCnt=0; 
				var charCnt=0; 
				 if (!filter.test(pwd)) { 
					 alert(mes2);			  
				  return (false); 
				 }
				 
					var len = pwd.length;
					var c=/[a-zA-Z]/;
					var n=/[0-9]/;
					for(var i = 0; i < len; ++i){
						
							var u= pwd.charAt(i);
							if(c.test(u)  )
							{
								numbCnt++;
							}
							if(n.test(u))
							{
								charCnt++;
							}
							
					}
					if(numbCnt<2||charCnt<2)
						{
						
						alert(mes2);
						return false;
						}
				 

				return true;

			}
	function ajaxCreateUser()
	{
		 var options = { 
			        success:showResponse,	 
			        type:      'post',
			        dataType:  'json'		     
			    }; 
		      
		$("#sForm").ajaxSubmit(options);	
	
	}
	function showResponse(jsonData)
	{
		var account=$("#account").val();
		if(jsonData&&jsonData.code==0)
		{
			alert("账户'"+account+"' 已成功新增，请设定退水及投注限额");
			var forward="${pageContext.request.contextPath}"+$("#jq_forward").val();	
			self.location=forward;
		}
		
	}
	
	$(document).ready(function() {
		
		 $("#credLine").focus(function(){
			 if($(this).val()==0)
				 $(this).val(""); 
			 
		 }).blur(function(){
			 if($.trim($(this).val())=="")
				 $(this).val("0"); 
		 });

		  
		  
		
	});	
	
	//alert(passwordCheck("a2121122"));
		
	document.write('<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>');
	
//add by peter for 用户修改优化 start
function checkHasBet(id, userType) {
	var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserTreeHasBet.do';
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {
			"id" : id,
			"userType" : userType
		},
		dataType : "json",
		success : onChechHasBetSuccess
	});
}


function getMaxRate(id, userType) {
	var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryBelowMaxRate.do';
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {
			"id" : id,
			"userType" : userType
		},
		dataType : "json",
		success : function(json){
			onGetMaxRateSuccess(json);
			checkHasBet(id,userType);
		}
	});
}
//add by peter for 用户修改优化 end