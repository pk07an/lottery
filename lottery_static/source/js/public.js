
   //使用这个函数截取字符串中的引号（包括单引和双引）
   function CutQuotation(myString)
   {
     var reA=/'/g;
     var reB=/"/g;
     var tempStr="";
     
     tempStr=myString.replace(reA,"");
     tempStr=tempStr.replace(reB,"");
     
     return tempStr;
   }

   function isBlank(param) {
    var v = param.value;
    if(v.length>0)
      return 0;
     else
      return 1; 
   }
   

//验证身份证输入框
function CheckSID(src){
    thisvalue=src.value;
    if(isNaN(thisvalue) || (thisvalue.length!=15 && thisvalue.length!=18)){
        alert("身份证号码只能输入15或18位数字");
        src.value="";
    }
}

//换成大写
function toUpper(aa){
    var a=aa.value;
    aa.value=a.toUpperCase();
}


/********************
*功能：日期检验
*参数：字符串
*返回：true 合法;false 非法
*********************/
function valiDate( strValue ){
  var objRegExp = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
  //check to see if in correct format
  if(strValue=="") return true;
  //else if(!objRegExp.executeSearch(strValue))
  else if(!objRegExp.test(strValue))
    return false; //doesn't match pattern, bad date
  else{
    var arrayDate = strValue.split(RegExp.$1); //split date into month, day, year
    var intDay = parseInt(arrayDate[2],10);
    var intYear = parseInt(arrayDate[0],10);
    var intMonth = parseInt(arrayDate[1],10);
    //window.alert(intYear+"?"+intMonth+"?"+intDay+"?");
    if(intYear<1900)
    {
        return false;
    }
    //check for valid month
    if(intMonth > 12 || intMonth < 1) {
    return false;
  }

    //create a lookup for months not equal to Feb.
    var arrayLookup = { '01' : 31,'03' : 31, '04' : 30,'05' : 31,'06' : 30,'07' : 31,'08' : 31,'09' : 30,'10' : 31,'11' : 30,'12' : 31,'1' : 31,'3' : 31, '4' : 30,'5' : 31,'6' : 30,'7' : 31,'8' : 31,'9' : 30}

    //check if month value and day value agree
    if(arrayLookup[arrayDate[1]] != null) {
      if(intDay <= arrayLookup[arrayDate[1]] && intDay != 0)
        return true; //found in lookup table, good date
    }

    //check for February
    var booLeapYear = (intYear % 4 == 0 && (intYear % 100 != 0 || intYear % 400 == 0));
    if( ((booLeapYear && intDay <= 29) || (!booLeapYear && intDay <=28)) && intDay !=0)
    return true; //Feb. had valid number of days
  }
  return false; //any other values, bad date
}

//鼠标移动到的列表记录颜色
function listSelect(listPosition)
{
    listPosition.runtimeStyle.color = "#000000";
    listPosition.runtimeStyle.backgroundColor = "#FDF8C4";
    listPosition.runtimeStyle.cursor = "hand";
}
//鼠标离开时恢复列表记录颜色
function listUnSelect(listPosition)
{
    listPosition.runtimeStyle.color = "";
    listPosition.runtimeStyle.backgroundColor = "";
}

//跳转到指定页面
function dispatchDestinPage(url) {
    document.location = url;
}

/**
 * 打开模式窗口
 * 
 * @param url   窗口对应的url
 * @param param 参数
 * @param width 窗口宽度，默认值为屏幕尺寸的90%
 * @param height窗口高度，默认值为屏幕尺寸的90%
 */
function openModalDialog(url, param, width, height)
{
    if(null == width){
        width = screen.width * 0.9;
    }
    if(null == height){
        height = screen.height * 0.9;
    }
        
    var newwin;
    t = (screen.height - height) / 2;
    l = (screen.width - width) / 2;
    style="fullscreen=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0";

    width=width+10;
    height=height+20;
    newwin=showModalDialog(url,param,"status:no;center:yes;help:no;minimize:yes;maximize:yes;dialogWidth:" +
            width + "px;scroll:no;dialogHeight:" + height + "px;border:think");

    return newwin;
}

/**
 * 打开模式窗口
 * 
 * @param url   窗口对应的url
 * @param param 参数
 * @param width 窗口宽度，默认值为屏幕尺寸的90%
 * @param height窗口高度，默认值为屏幕尺寸的90%
 */
function openModalDialogReport(url, param, width, height)
{
    if(null == width){
        width = screen.width * 0.9;
    }
    if(null == height){
        height = screen.height * 0.9;
    }
        
    var newwin;
    t = (screen.height - height) / 2;
    l = (screen.width - width) / 2;
    style="fullscreen=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0";

    width=width+10;
    height=height+20;
    newwin=showModalDialog(url,param,"status:no;center:yes;help:no;minimize:yes;maximize:no;dialogWidth:" +
            width + "px;scroll:yes;dialogHeight:" + height + "px;border:think");

    return newwin;
}

    
/**
 * 打开非模式窗口
 * 
 * @param url   窗口对应的url
 * @param param 参数
 * @param width 窗口宽度，默认值为屏幕尺寸的90%
 * @param height窗口高度，默认值为屏幕尺寸的90%
 */
function openModallessDialog(url, param, width, height)
{
    if(null == width){
        width = screen.width * 0.9;
    }
    if(null == height){
        height = screen.height * 0.9;
    }
        
    var newwin;
    t = (screen.height - height) / 2;
    l = (screen.width - width) / 2;
    style="fullscreen=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0";

    width=width+10;
    height=height+20;
    newwin=showModelessDialog(url,param,"status:no;center:yes;help:no;minimize:yes;maximize:yes;dialogWidth:" +
            width + "px;scroll:no;dialogHeight:" + height + "px;border:think");
}


/**
 *  限制输入的字符数目
 *
 *  inputObjName 被限制的输入区域名称，必须
 *  maxSize  允许输入的最多字符数目，必须
 *  showInfoObjName 显示剩余可输入字符的数量的区域名称，可选，如果无需显示，则留空，或以''作为参数
 */
function limitInput(inputObjName,maxSize,showInfoObjName){

    var limitLength = maxSize;//允许的最大字符数目
    //获得输入区域的对象
    if(null == inputObjName || 0 == inputObjName.length){
        return true;
    }
    var inputObj = eval("document.all." + inputObjName);//获得对象
    if(null == inputObj){
        return false;
    }
    
    var useLength = inputObj.value.length;//当前已经使用的字符数目
    
    if(useLength > limitLength){
        //截断多余的字符
        inputObj.value = inputObj.value.substring(0,limitLength);
        event.returnValue = false;
    }
    
    //设置显示的可输入字符的提示
    useLength = inputObj.value.length;
    var content = "剩余可输入字符数目：";
    if(useLength > ((limitLength * 0.9) - 2)){
        content += "<font color='#FF8040'>" + (limitLength - useLength) + "</font>"
    }else{
        content += "<font color='#008000'>" + (limitLength - useLength) + "</font>"
    }
    //获得显示的对象
    if(null == showInfoObjName || 0 == showInfoObjName.length){
        return true;
    }
    var showInfoObj = eval("document.all." + showInfoObjName);//获得对象
    if(null == showInfoObj){
        return fasle;
    }
    
    showInfoObj.innerHTML = content;
    
    return true;
}
/**
 *  变化图片
 *  
 *  object 图片对象
 *  imgPath 新图片路径
 */ 
function changeImg(object,imgPath){
    object.src = imgPath;
}

/**
 * 跳转到指定页面
 * 
 * submitFormName   提交表单名称
 * actionUrl        提交表单URL
 */
function checkPageGo(submitFormName, actionUrl){
    
    var pageGoNum = document.all.pageGoNumInput.value;
    
    if(null == pageGoNum || 0 == pageGoNum.length){
        alert("请输入跳转到的页码！");
        document.all.pageGoNumInput.focus();
        return false;
    }
    
    //校验输入的是否是数字
    if(!isDigit(pageGoNum)){
        alert("页码为数字类型！");
        document.all.pageGoNumInput.focus();
        return false;
    }
    
    pageGoNum = new Number(pageGoNum);
    
    //如果输入的页码大于总页码，则设置其值为总页码
    if(pageGoNum > new Number(document.all.originSumPages.value)){
        pageGoNum = document.all.originSumPages.value;
    }
    
    //如果输入的页面小于1，则设置其值为1
    if(pageGoNum < 1){
        pageGoNum = 1;
    }
    
    //将 actionUrl 中的原来的 _pagecount 参数删除
    var paramIndex = actionUrl.indexOf("_pagecount=");    
    var actionUrlNew = "";
    if(-1 != paramIndex){
        var actionUrlFir = actionUrl.substring(0, paramIndex - 1);
        actionUrlNew = actionUrlFir;
        
        var paramIndex02 = actionUrl.indexOf("&",paramIndex);
        if(-1 != paramIndex02){
            var actionUrlLast = actionUrl.substring(paramIndex02, actionUrl.length - 1);
            actionUrlNew = actionUrlNew + actionUrlLast;
        }
    } else {
        actionUrlNew = actionUrl;
    }
    
    var submitForm = eval("document." + submitFormName);
    submitForm.action = actionUrlNew + "&_pagecount=" + pageGoNum;
    
    submitForm.submit(); 
    
    return true;
}

//校验是否是数字
function isDigit(str){
    var patrn=/^[0-9]*$/;
    if (!patrn.exec(str)){
        return false
    }
    return true
}   

/**
 * 打开选择日期的控件页面
 * 
 * contextPath  项目根路径
 * ctrlobj      设置对象
 */
function openCalendarDlg(contextPath, ctrlobj){
    showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
    showy = event.screenY - event.offsetY + 18; // + deltaY;
    newWINwidth = 210 + 4 + 18;

    retval = window.showModalDialog(contextPath + "/js/calendarDlg.htm", "", "dialogWidth:197px; dialogHeight:225px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no; "  );

    if( retval != null ){
        ctrlobj.value = retval;
    }
}

/**
 *  控制文本区域输入内容的长度
 *  
 *  obj     文本域
 *  length  可输入内容的长度
 */
function textAreaLength(obj, length){
    if(obj.value.length > (length -1)){
        obj.value = obj.value.substring(0,length);
    }
}

/**
 *  控制输入的长度
 */
function isOver(obj,lengthLimit) 
  {  
    var byteLength = 0;   
    var strInput = document.getElementById(obj).value; 
    
    for(var i=0;i<strInput.length;i++) 
    {  
        var ch = strInput.charAt(i);  
        if(isNormalChar(ch))  
            byteLength += 1;  
        else  
            byteLength += 2;       
    }  
    
    if(byteLength<lengthLimit) 
    { 
    document.getElementById(obj).maxLength = lengthLimit; 
    } 
    if(byteLength>=lengthLimit) 
    { 
    var str = document.getElementById(obj).value; 
    var byteLength2 = 0; 
    var temp = ""; 
    for(var i=0;i<str.length;i++) 
    {  
        var ch = str.charAt(i);  
        if(isNormalChar(ch))  
            byteLength2 += 1;  
        else  
            byteLength2 += 2; 
        if(byteLength2>lengthLimit) 
        { 
          break; 
        } 
        temp = temp+ch;        
    }  
    
   document.getElementById(obj).maxLength = temp.length; 
   document.getElementById(obj).value=temp; 
    }   
}  
 
function   isNormalChar(ch)  
{  
    if(ch.length>1){  
         return false;  
    }  
    if(ch == ""){  
         return true;  
    }  

    var pattern = /^([\\uFF66-\\uFF9F]|[\\u0000-\\u00FF])*$/gi;  
    if (pattern.test(ch)){  
         return true;  
    }else{  
         return false;  
    }  
} 



//JS 校验工具类
function CheckUtil() {
}

// 校验是否为空(先删除二边空格再验证)
CheckUtil.isNull = function(obj) {
 if (typeof(obj) == "null"
  || typeof(obj) == "undefined")
  return true;
 else
  return false;
};

/**
 *  函数名称：IsBlank 函数功能：判断给定字符串是否为空 函数参数：str,需要处理的字符串
 */
CheckUtil.isBlank = function(obj) {
 if (!CheckUtil.isNull(obj) &&
   CheckUtil.Trim(obj).length > 0)
  return false;
 else
  return true;
}

// 校验是否全是数字
CheckUtil.isDigit = function(str) {
 var patrn = /^\d+$/;
 return patrn.test(str);
};

// 校验是否是整数
CheckUtil.isInteger = function(str) {
 var patrn = /^([+-]?)(\d+)$/;
 return patrn.test(str);
};

// 校验是否为正整数
CheckUtil.isPlusInteger = function(str) {
 var patrn = /^([+]?)(\d+)$/;
 return patrn.test(str);
};

// 校验是否为负整数
CheckUtil.isMinusInteger = function(str) {
 var patrn = /^-(\d+)$/;
 return patrn.test(str);
};

// 校验是否为浮点数
CheckUtil.isFloat = function(str) {
 var patrn = /^([+-]?)\d*\.\d+$/;
 return patrn.test(str);
};

// 校验是否为正浮点数
CheckUtil.isPlusFloat = function(str) {
 var patrn = /^([+]?)\d*\.\d+$/;
 return patrn.test(str);
};

// 校验是否为负浮点数
CheckUtil.isMinusFloat = function(str) {
 var patrn = /^-\d*\.\d+$/;
 return patrn.test(str);
};

// 校验是否仅中文
CheckUtil.isChinese = function(str) {
 var patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
 return patrn.test(str);
};

// 校验是否仅ACSII字符
CheckUtil.isAcsii = function(str) {
 var patrn = /^[\x00-\xFF]+$/;
 return patrn.test(str);
};

// 校验手机号码
CheckUtil.isMobile = function(str) {
 var patrn = /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
 return patrn.test(str);
};

// 校验电话号码
CheckUtil.isPhone = function(str) {
 var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
 return patrn.test(str);
};

// 校验URL地址
CheckUtil.isUrl = function(str) {
 var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
 return patrn.test(str);
};

// 校验电邮地址
CheckUtil.isEmail = function(str) {
 var patrn = /^[\w-]+@[\w-]+(\.[\w-]+)+$/;
 return patrn.test(str);
};

// 校验邮编
CheckUtil.isZipCode = function(str) {
 var patrn = /^\d{6}$/;
 return patrn.test(str);
};

// 校验合法时间
CheckUtil.isDate = function(str) {
 if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str))
  return false;
 
 var r = str.match(/\d{1,4}/g);
 if (r == null) {
  return false;
 }
 var d = new Date(r[0], r[1] - 1, r[2]);
 return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d
   .getDate() == r[2]);
};

// 校验字符串：只能输入6-20个字母、数字、下划线(常用手校验用户名和密码)
CheckUtil.isString6_20 = function(str) {
 var patrn = /^(\w){6,20}$/;
 return patrn.test(str);
};

/**
 * 函数名称：IsLegality 函数功能：检查字符串的合法性，即是否包含"
 * '字符，包含则返回false;反之返回true 函数参数：obj,需要检测的字符串
 */
CheckUtil.IsLegality = function(obj) {
 var intCount1 = obj.indexOf("\"", 0);
 var intCount2 = obj.indexOf("\'", 0);
 return (intCount1 > 0 || intCount2 > 0) ? false : true;
}

/**
 * 函数名称：IsNumber 函数功能：检测字符串是否全为数字 函数参数：str,需要检测的字符串
 */
CheckUtil.IsNumber = function(str) {
 var number_chars = "1234567890";
 var i;
 for (i = 0; i < str.length; i++) {
  if (number_chars.indexOf(str.charAt(i)) == -1)
   return false;
 }
 return true;
}

/**
 * 函数名称：Trim 函数功能：去除字符串两边的空格 函数参数：str,需要处理的字符串 
 */
CheckUtil.Trim = function(str) {
 return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 函数名称：LTrim 函数功能：去除左边的空格 函数参数：str,需要处理的字符串
 */
CheckUtil.LTrim = function(str) {
 return str.replace(/(^\s*)/g, "");
}

/**
 *  函数名称：RTrim 函数功能：去除右边的空格 函数参数：str,需要处理的字符串
 */
CheckUtil.RTrim = function(str) {
 return this.replace(/(\s*$)/g, "");
}



//if (window.Event) document.captureEvents(Event.MOUSEUP);
function nocontextmenu(){
	event.cancelBubble = true
	event.returnValue = false;
	return false;
} 
function norightclick(e){
	if (window.Event){
		if (e.which == 2 || e.which == 3)
		return false;
	} else if (event.button == 2 || event.button == 3){
		event.cancelBubble = true
		event.returnValue = false;
		return false;
	}
} 
//document.oncontextmenu = nocontextmenu; // for IE5+
//document.onmousedown = norightclick; // for all others


function forbid_key(){ 
    /*
	//禁止F5
	if(event.keyCode==116){
        event.keyCode=0;
        event.returnValue=false;
    }
    */
    
    if(event.shiftKey){
        event.returnValue=false;
    }
    //禁止shift
    
    if(event.altKey){
        event.returnValue=false;
    }
    //禁止alt
    
    if(event.ctrlKey){
        event.returnValue=false;
    }
    //禁止ctrl
    return true;
}

function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

function accSub(arg1, arg2) {
   var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
 function oddsChangeAddOrSub(typeCode,jiajian,num){
 var obj=$("td[id="+typeCode+"][width=19]").next().children().children();
		var number=obj.html();
		if($("#kjTitle").attr("style").replace(/\s+/g,"")=="display:none"){
			var zhi;
			if(jiajian=='jia'){
				zhi=accAdd(number,num);
				
			}else{
				zhi=accSub(number,num);
			}
			obj.text(zhi);
		}
 }
 
//document.onkeydown=forbid_key;


 
