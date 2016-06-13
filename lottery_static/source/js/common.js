
/*
 * 页面辅助类
 * @author zhouxiaolong
 */



function WebUtils(){

	this.testStr = 'testStr';
	
	
	this.testMethod = function(){
		alert("This is testMethod.");
	};
	
	//@author unknow
	this.addEvent = function(elm, evType, fn, useCapture){
		this.widget.addEvent(elm, evType, fn, useCapture);
	};
	
	this.ifrmLoadChange = function(id){
		if(id === undefined){
			id = 'mainFrame';
		}
		this.widget.ifrmLoadChange(id);
	};
	
	//@author wutianhui
	this.setMenuAndMainHeight = function(){
		this.widget.setMenuAndMainHeight();
	};
	
	//@author wutianhui
	this.switchSubMenu = function(obj,id,parent){
		if(parent === undefined){
			parent = "tree_list";
		}
		this.widget.switchSubMenu(obj,id,parent);
	};
	
	this.loadSelectInput = function(param){
		//[winObj|orig|dest]
		if(param.winObj === undefined){
			param.winObj = frames['mainFrame'].window;
		}
		this.widget.loadSelectInput(param);
	};
	
	this.removeSelectInput = function(param){
		//[winObj|origSelect|origText]
		if(param.winObj === undefined){
			param.winObj = frames['mainFrame'].window;
		}
		this.widget.removeSelectInput(param);
	};
	
	this.addSelectInput = function(param){
		//[winObj|orig|destText|destSelect]
		if(param.winObj === undefined){
			param.winObj = frames['mainFrame'].window;
		}
		this.widget.addSelectInput(param);
	};
	
	this.changeMenuToTree = function(param){
		//param check... [showId|action|href|contextPath|target]
		if(param.showId === undefined){
			param.showId = 'tree_list';
		}
		if(param.target === undefined){
			param.target = 'mainFrame';
		}
		if(param.title === undefined){
			param.title = '\u83DC\u5355';
		}
		this.data.exec(param.action,this.data.get,"",this.widget.treeView,param);
	};
	
	
	this.changeMenuToList = function(param){
		//[showId|action|title]
		
		if(param.title === undefined){
			param.title = "";
		}
		
		if(param.showId === undefined){
			param.showId = 'tree_list';
		}
		//不规范的写法
		$("#sidebar_title").html(param.title);
		this.data.load(param.showId,param.action,'',this.widget.changeMenuToList,param);
	};
	
	this.menuView = function(obj){
		this.widget.switchMenu(obj);
	};
	
	this.trunPage = function(tableId,oper){
		var totalRows = "20";
		var currentPage = "1";
		var parameter = {};
		var tempStr = "";
		
		if(this.widget.attr[tableId] !== null){
			totalRows = (this.widget.attr[tableId]).totalRows;
			currentPage = (this.widget.attr[tableId]).currentPage;
		}
		
		parameter.tableId = tableId;
		parameter.attr = this.widget.attr;
		tempStr = "method=listByJSON&totalRows="+totalRows+"&currentPage="+currentPage+"&pageMethod=" + oper;
		
		this.data.exec(this.widget.attr["url"],this.data.get,tempStr,this.widget.loadTable,parameter);
		
	};
	
	this.initTableLink = function(init){
		this.widget.initTableLink(init);
	};
	
	this.fullScreen = function(){
		//不规范的写法
		ui.widget.fullScreen();
	};
	
	this.exitFullScreen = function(){
		this.widget.exitFullScreen();
	};
	
	this.showNotice = function(param){
		//[title|handle|autoHide]

		if(param.autoHide == undefined){
			param.autoHide = true;
		}
		this.widget.showNotice(param);
	}
	
	this.hideNotice = function(param){
		//[title|handle|autoHide]
		this.widget.hideNotice(param);
	}
	
	
	this.showMask = function(left,top,width,height){
		this.widget.showMask(left,top,width,height);
	};
	
	this.hideMask = function(){
		this.widget.hideMask();
	};
	
	this.start = function(page){
		if(page !== undefined){
			this.widget.attr["url"] = page;
		}	
		if(page != undefined){
			if(page.onlyFrame != undefined){
				this.widget.attr.onlyFrame = page.onlyFrame;
			}
		}
		this.data.setup();
		logger.start();
	};
	
	this.init = function(page){
		ui.start(page);
		
		//加载框架高度
		window.setInterval(function(){
			ui.setMenuAndMainHeight();
		},100);
		
	};
}

function initUI(page){

	
	ui.start(page);
	
	//加载框架高度
	//window.setInterval(function(){
	//	ui.setMenuAndMainHeight();
	//},100);
}


/*
 * 构建视图界面
 */
 
WebUtils.prototype.widget = {

	attr : {
		onlyFrame : false,
		ifrmURL : '',
		isFullScreen : false
	},
	
	showNotice : function(param){
		
		var strBuf = [];
		strBuf.push("<div id='note' style='z-index:2000;filter:alpha(opacity=100);-moz-opacity:1;opacity:1;" 
					+ "position:absolute;width:110px;line-height:36px;padding-left:10px;top:260px;left:420px;"
					+"background-color:#97d3f4;border:1px solid #74e8ce;'>");
		strBuf.push("<strong>"+param.title+"</strong>");
		strBuf.push("</div>");
		ui.showMask(420,260,121,37);
		$("body").prepend(strBuf.join(''));
		if(param.autoHide){
			setTimeout(function(){
				ui.hideNotice();
				if((typeof param.handle) == 'function'){
					param.handle();
				}
			},3000);
		}
	},
	
	hideNotice : function(param){
		ui.hideMask();
		$("#note").remove();
	},
	
	ifrmLoadChange : function(id){
		var _this = document.getElementById(id);
		if(_this.readyState == undefined || _this.readyState == 'complete'){
			this.attr.ifrmURL = frames[id].window.document.URL;
		}
	},
	
	
	changeMenuToList : function(param){
		$("#sidebar_title").html(param);
	},
	
	fullScreen : function(){
		this.attr.isFullScreen = true;
		var doc = frames['mainFrame'].window.document;
		$("#head").hide();
		$("#sidebar").hide();
		$("#main").css("padding-left",0);
		$("#head_nav").hide();
		$(doc).find("#foot").hide();
	},
	
	exitFullScreen : function(){
		this.attr.isFullScreen = false;
		var doc = frames['mainFrame'].window.document;
		$("#head").show();
		$("#sidebar").show();
		$("#main").css("padding-left",220);
		$("#head_nav").show();
		$(doc).find("#foot").show();
	},
	
	showMask : function(left,top,width,height){
		var strBuf = [];
		strBuf.push("<iframe id='csFrm' src='javascript:void(0)' "
		+"style='position:absolute;z-index:700;background-color:#fff;"
		+"top:"+top+"px;left:"+left+"px;'  width='"+width+"' height='"+height+"' frameborder='0'></iframe>");
		$("body").prepend(strBuf.join('')); 
	},
	
	hideMask : function(){
		$("#csFrm").remove();
	},	
	
	//@author wutianhui
	switchSubMenu : function(obj,id,parent){
		$("#"+parent+" a").each(function()
	    {
			if($(this).attr("id") == id)
			{
				$(this).removeAttr("id");
				$(this).next("ul").slideUp();
			}
	    });
		$(obj).attr("id",id);
		if($(obj).next("ul").is(":hidden"))
		{
			$(obj).next("ul").slideDown();
		}
		else
		{
			$(obj).next("ul").slideUp();
		}
		return false
	},
	
	
	//@author wutianhui  
	setMenuAndMainHeight : function(){ 
		//整屏幕的高度
		var webH = document.documentElement.clientHeight;
		//头部的高度
		var headH = document.getElementById("head").offsetHeight;
		var siderHeadH = document.getElementById("sidebar_title").offsetHeight;
		
		//左菜单的高度
        var tempMenuH = webH - headH;
        tempMenuH = tempMenuH < 0 ? 0 : tempMenuH ;
		document.getElementById("sidebar").style.height = tempMenuH  + "px";
		
		var tempMenuUlH = webH - headH - siderHeadH;
        tempMenuUlH = tempMenuUlH < 0 ? 0 : tempMenuUlH;
        document.getElementById("tree_list").style.height = tempMenuUlH + "px";
        
        /*
        if((typeof setSidebarHeight) == 'function'){
        	setSidebarHeight();
        }
		*/
		//嵌入iframe的高度
		var tempFrameH = webH - headH;
        tempFrameH = tempFrameH < 0 ? 0 : tempFrameH ;
        var tempFrameHOfs = 38;
        if(this.attr.isFullScreen){
        	tempFrameHOfs = 0;
        }
        
		document.getElementById("mainFrame").style.height = (tempFrameH - tempFrameHOfs) + "px";
		
		if(!this.attr.onlyFrame){
			//当iframe地址改变时，不访问iframe里面的dom
			if(frames['mainFrame'].window.document.URL == this.attr.ifrmURL){
				var fwin = frames['mainFrame'].window;
				
				//var positionH = fwin.document.getElementById("position_nav").offsetHeight;
				var positionH = 38;
				if(this.attr.isFullScreen){
					positionH = 0;
				}
				var footH = fwin.document.getElementById("foot").offsetHeight;
				var dataHeadH = fwin.document.getElementById("data_head").offsetHeight;
				
				//数据显示区域的高度
				var tempDataH = webH - headH - positionH - footH - dataHeadH -  65 ;
			    tempDataH = tempDataH < 0 ? 0 : tempDataH ;
				
				fwin.document.getElementById("data_section").style.height = (tempDataH)  + "px";
				
				//注册嵌入iframe的执行函数(用户设定嵌入iframe里面元素的高)
				if((typeof fwin.setPageHeight) == 'function'){
					fwin.setPageHeight();
				}
				
			}
		}
		
	},
	
	changeMenuToList : function(param){
		//$("#sidebar_title").html(param.title);
	},
	
	addEvent : function(elm, evType, fn, useCapture){
		if(useCapture == undefined){
			useCapture = false;
		}
		if (elm.addEventListener) {
			elm.addEventListener(evType, fn, useCapture);//DOM2.0
			return true;
		}else if (elm.attachEvent) {
			var r = elm.attachEvent('on' + evType, fn);//IE5+
			return r;
		}else {
			elm['on' + evType] = fn;//DOM 0
		}
	},
	
	loadSelectInput : function(param){
		var doc = param.winObj.document;
		var aryFormats = ($(doc).find("input[name='"+param.orig+"']").val()).split(',');
		var selectInput = $(doc).find("select[name='"+param.dest+"']");
		selectInput.empty();
		if(aryFormats[0] == ''){
			aryFormats.length = 0;
		}
		$.each(aryFormats , function(name,value){
			selectInput.append("<option value='" + value + "'>" + value + "</option>");
		});
	},
	
	removeSelectInput : function(param){
		var doc = param.winObj.document;
		var origSelect = doc.getElementsByName(param.origSelect)[0];
		var origText = doc.getElementsByName(param.origText)[0].value;
		var aryText = origText.split(',');
		var arySelect = [];
		for (var i=0;i<origSelect.length;i++){
		   if(origSelect.options[i].selected == true){
				arySelect.push(origSelect.options[i]);
		   }
		}
		for(var i = 0;i < arySelect.length;i++){
			for(var j = 0;j < aryText.length;j++){
				if(arySelect[i].value == aryText[j]){
					aryText.splice(j,1);	
				}
			}
		}
		for(var i = 0;i < arySelect.length;i++){
			for (var j=0;j<origSelect.length;j++){
			    if(arySelect[i].value == origSelect.options[j].value){
				 	origSelect.remove(origSelect.options[j].index);
				}
			}
		}
		doc.getElementsByName(param.origText)[0].value = aryText.join(',');
	},
	
	addSelectInput : function(param){
		var doc = param.winObj.document;
		var orig = $(doc).find("input[name='" + param.orig + "']");
		var destSelect = $(doc).find("select[name='" + param.destSelect + "']");
		var destText = $(doc).find("input[name='" + param.destText + "']");
		var aryDestText = (destText.val()).split(',');
		orig.val((orig.val()).replace(/,/g,''));
		if(orig.val() != ''){
			destSelect.append("<option value='" + orig.val() + "'>" + orig.val() + "</option>");
			aryDestText.push(orig.val());
			destText.val(aryDestText.join(','));
			if(destText.val().charAt(0) == ','){
				destText.val(destText.val().replace(/,/,''));
			}		
			orig.val('');
		}
	},
	
	
	
	//@author wutianhui
	switchMenu : function(obj){
		$(obj).parent().siblings().children("a").removeAttr("id");
		if($(obj).next("ul").find("li").size() == 0)
		{
			$(obj).parent().parent().parent().siblings().find("a").removeAttr("id");
		}
		$(obj).attr("id","menu_on");
		$(obj).parent().siblings().find("ul").slideUp("fast");
		if($(obj).next("ul").is(":hidden"))
		{
			$(obj).next("ul").slideDown("fast");
		}
		else
		{
			$(obj).parent().find("ul").slideUp("fast");
		}
	},
	

	treeView : function(data,textStatus){

		var param = this.parameter;
		var url = '';
		var urlModeEnum = ["server","client"];
		var uParam = '';
		var count = 0;
		var size = 0;
		var ch = '';
		var contextPath = '';
		tree = new dTree('tree'); 
		tree.add('0','-1',param.title);//'\u83DC\u5355');
		
		if(param.contextPath != undefined){
			contextPath = param.contextPath;
		}
		
		
		$.each(data,function(i,v){
			url = "";
			if(v.urlMode == ''){
				v.urlMode = urlModeEnum[1];
			}
			if (v.urlMode == urlModeEnum[0]){
				if((v.url == '') || (v.url == '#')){
					url = 'javascript:void(0)';
				}else {
					url = v.url;
				}
			}else if (v.urlMode == urlModeEnum[1]){
				if((param.href == '') || (param.href == '#')){
					url = 'javascript:void(0)';
				}else {
					url = param.href;
				}								
			}
			
			if(url != 'javascript:void(0)'){
				if(v.urlParam != null){
					count = 0;
					uParam = '';
					size = 0;
					$.each(v.urlParam,function(n,val){
						size = size + 1;
					});
					$.each(v.urlParam , function(name,value){
						count = count + 1;
						uParam = uParam + name + '=' + value;
						if(count < size ){
							uParam = uParam + '&';
						}
					});
					if(uParam != ''){
						ch = '?';
						if(url.search(/\?/) != -1){
							ch = '&';
						}
						url = url + ch + uParam;
					}
				}
				//url = contextPath + url; 如果是绝对的URL,则不自动加本机前辍了
				var url_ = url.toLowerCase();
				if(url_.indexOf("http://")==-1 || url_.indexOf("https://")==-1){
					url = contextPath + url;
				}
			}
			
			tree.add(v.id,v.pid,v.name,url,'',param.target,v.imgLink,'',true);
		});
		
		
		$("#" + param.showId).empty();
		$("#" + param.showId).html(tree.toString());
	},
	
	
	loadTable : function(data,textStatus){
		var pagination = {};
		var tableId = this.parameter.tableId;
		var attr = this.parameter.attr;
		var _table = $("#" + tableId);
		var strBuf = [];
		var tdEnum = ["name","desc","alarmType"];
		var _tableBody = _table.find(".tableBody");
		var _trs = _tableBody.find("tr");
		var count = 0;
		
		_tableBody.html("");
		
		$.each(data.collectionTypes,function(i,v){
			count = count + 1;
			strBuf.push("<tr ");
			strBuf.push((count % 2 == 0) ? 'class=\'even\'':'class=\'odd\'');
			strBuf.push(" >");
			strBuf.push("<td>");
				strBuf.push(count);
			strBuf.push("</td>");
			$.each(tdEnum,function(i,value){
				strBuf.push("<td>");
				strBuf.push(v[value]);
				strBuf.push("</td>");
			});
			strBuf.push("<td>");
				strBuf.push("<a href='#'>编辑</a>");
			strBuf.push("</td>");
			strBuf.push("</tr>");
		});
		
		_tableBody.html(strBuf.join(' '));
		
		pagination.totalRows = data.page.totalCount;
		pagination.currentPage = data.page.currentPage;
		attr[tableId] = pagination;
	},
	
	initTableLink : function(init){
		//参数处理
		var pgId = "thead"; //选取表头，用于设置分页链接
		var bdId = ".tableBody"; //选取表体，用于设置编辑、删除链接
		if(init.pgId !== undefined){
			pgId = init.pgId;
		}
		if(init.bdId !== undefined){
			bdId = init.bdId;
		}
		
		var _table = $("#" + init.tableId);
		
		//设置分页链接
		var _pg = _table.find(pgId);
		var imgs = _pg.find("img");
		$.each(imgs,function(i,v){
			if((v.src).search(/firstPage.gif/) != -1){
				$(v).closest("a").attr("href","javascript:void(ui.trunPage('" + init.tableId + "','first'))");
			}
			if((v.src).search(/lastPage.gif/) != -1){
				$(v).closest("a").attr("href","javascript:void(ui.trunPage('" + init.tableId + "','previous'))");
			}
			if((v.src).search(/prevPage.gif/) != -1){
				$(v).closest("a").attr("href","javascript:void(ui.trunPage('" + init.tableId + "','next'))");
			}
			if((v.src).search(/nextPage.gif/) != -1){
				$(v).closest("a").attr("href","javascript:void(ui.trunPage('" + init.tableId + "','last'))");
			}
			if(((v.src).search(/separator.gif/) != -1) || ((v.src).search(/xls.gif/) != -1)){
				$(v).hide();
			}
		});
		//_pg.find("select").hide();
		
		//设置排序为disable
		var _sort = _table.find(pgId);
		_sort = _sort.find("tr:last");
		//Not be completed
		
		
		//设置编辑、删除链接
		var _sort_td = _sort.find("td:last");
		if(_sort_td.html() == '\u64CD\u4F5C'){
			var oper = _table.find(bdId);
			var oper_trs = oper.find("tr");
			
			$.each(oper_trs,function(i,v){
				var _td = $(v).find("td:last");
				var _td_str = _td.html();
				_td.html('');
				var start = 0;
				var end = 0;
				var href_str = "";
				var src_str = "";
				
				start = _td_str.indexOf("location.href='") + "location.href='".length;
				end = _td_str.indexOf("'",start);
				href_str = _td_str.substring(start,end);
				
				start = _td_str.indexOf("src=\"") + "src=\"".length;
				end = _td_str.indexOf("\"",start);
				src_str = _td_str.substring(start,end);
				
				//logger.info("a: " + href_str + " b: " + src_str);
				
				//_td.insertAfter()
				
				_td.append("<img src='"+src_str+"'/>");
				
			});
			
		}
		

	}
	
}

/*
 * 数据请求
 */
 
WebUtils.prototype.data = {

	html : "html",
	
	json : "json",
	
	post : "POST",
	
	get : "GET",

	setup : function(){
		$.ajaxSetup({
			cache : false,
			complete : function (XMLHttpRequest, textStatus){},
			error : function (XMLHttpRequest, textStatus, errorThrown){
					var stringBuffer = new Array();
					stringBuffer.push("<style>#cale_error{");
					stringBuffer.push("background-color:#000;");
					stringBuffer.push("position:absolute;");
					stringBuffer.push("left:0px;");
					stringBuffer.push("top:0px;");
					stringBuffer.push("width:0px;");
					stringBuffer.push("height:0px;");
					stringBuffer.push("z-index:2000;");
					stringBuffer.push("filter:alpha(opacity=50);");
					stringBuffer.push("-moz-opacity:0.5;");
					stringBuffer.push("opacity:0.5;}");
					stringBuffer.push("#cale_error p {");
					stringBuffer.push("font-size:medium;");
					stringBuffer.push("font-weight:bolder;");
					stringBuffer.push("text-align:center;");
					stringBuffer.push("color:#EEE;");
					stringBuffer.push("padding:80px;}</style>");
					$("head").prepend(stringBuffer.join(" "));
					$("body").prepend("<div id='cale_error'><p>\u7F51\u7EDC\u8FDE\u63A5\u5931\u8D25\uFF0C\u8BF7\u7A0D\u5019\u518D\u8BD5\u002E\u002E\u002E</p></div>");
					$("#cale_error").css("width",$(document).width());
					$("#cale_error").css("height",$(document).height());
					//$("#cale_error > p").append("<br/><br/>" + XMLHttpRequest +"<br/><br/>" + textStatus + "<br/><br/>" + errorThrown);
					setTimeout("$('#cale_error').hide()",2500);
					
			}
		});
	},
	
	exec : function(link,method,rdata,action,param,cpl){
		logger.info("data.exec json " + link);
		var tempData = "";
		try {
			if(method == this.post){
				tempData = $(rdata).serialize();
			}else if (method == this.get){
				tempData = rdata;
			}
			$.ajax({
				url:link,
				dataType : "json",
				type : method,
				data : tempData,
				success : action,
				parameter : param,
				complete : cpl
			});
		}catch(ex){
			logger.info(ex);
		}
	},
	
	load : function(id,url,paramForUrl,action,paramForAction){
		$("#" + id).load(url,paramForUrl,action);
	}
}

/*
 * logger 日志记录
 */
 var logger = {
	// on
	on : true,
	msg : new Array(),
	start : function (){
		var status = false;
		if(this.on){
			var css = new Array();
			var element = "<div id='log_layout'></div>";
			css.push("<style> #log_layout {");
			css.push("font-family:Arial, Helvetica, sans-serif;");
			css.push("font-size:small;");
			css.push("font-weight:bold;");
			css.push("background-color:#575757;");
			css.push("color:#FEFEFE;");
			css.push("padding:5px;");
			css.push("margin:0px;");
			css.push("border:0px;");
			css.push("width:400px;");
			css.push("height:320px;");
			css.push("position:fixed;");
			css.push("left:300px;");
			css.push("top:120px;");
			css.push("z-index:1200;");
			css.push("display:none;");
			css.push("overflow-x:hidden;");
			css.push("overflow-y:auto;");
			css.push("filter:alpha(opacity=60);");
			css.push("-moz-opacity:0.6;");
			css.push("opacity:0.6;");
			css.push("}</style>");
			css.push("<!--[if IE 6]><style>#log_layout {");
			css.push("position:absolute;");
			css.push("top:expression(documentElement.scrollTop +'px');");
			css.push("}</style><![endif]-->");
			$("head").prepend(css.join(" "));
			$("body").prepend(element);
			$(document).keydown(function (event){
				//Register shift  key for start logger
				if(event.keyCode == 16){
					if($("#log_layout").css("display") == "none"){
						$("#log_layout").show();	
					}else{
						$("#log_layout").hide();	
					}
				}
			});		
			status = true;
		}
		return status;
	},
	info : function (msg){
		if(this.on){
			this.msg.push("INFO: ");
			this.msg.push(msg);
			this.msg.push("<br/>");
			if(arguments.length > 1){
				this.msg.push("SORS: ");
				this.msg.push(arguments[1]);	
				this.msg.push("<br/>");
			}
			$("#log_layout").html(this.msg.join(""));
		}
	}
};//end
	
/*
 * 工具类
 * 
 */
	 Date.prototype.format = function(fmt) {
		var o = {
			"M+": this.getMonth() + 1,
			"d+": this.getDate(),
			"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
			"H+": this.getHours(),
			"m+": this.getMinutes(),
			"s+": this.getSeconds(),
			"q+": Math.floor((this.getMonth() + 3) / 3),
			"S": this.getMilliseconds() 
		};
		var week = {
			"0": "\u65e5",
			"1": "\u4e00",
			"2": "\u4e8c",
			"3": "\u4e09",
			"4": "\u56db",
			"5": "\u4e94",
			"6": "\u516d"
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		if (/(E+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f": "\u5468") : "") + week[this.getDay() + ""]);
		}
		for (var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	}
			
	//String parse to date
	Date.parseFormat = function (timeStr){
		//String style : yyyy-MM-dd HH:mm:ss
		return 	new Date(timeStr.substr(0,4),(Number(timeStr.substr(5,2)) - 1),timeStr.substr(8,2),timeStr.substr(11,2),timeStr.substr(14,2),timeStr.substr(17,2));
	}
			
			
	//Strint cut 
	String.prototype.cut = function (str,num){
		var returnValue = "";
		if(str.length >= num){
			returnValue = str.substring(0,num) + "...";
		}else {
			returnValue = str;
		}
		return returnValue;
	}
			
			
			
/*
* 单独的控件(还没有整理好)
*
*/

////////////////////////
//[title|msg|ok|cancel|width|height|top|left|btn|type:{type|param1}]
	function Dialog(){
		
		this.currentWindow = window;
		
		var _param = {}
		
		var dialogId = [];
	
		this.show = function(param){
			
			if(param.btn != undefined){
				param.btn = true;
			}

			if(param.type == undefined){
				param.type = {type:'text'};
			}
			
			_param = param;
			
			if($("#dialog_maindiv").size() == 0){
				var strBuf = [];
				strBuf.push("<div id='dialog_maindiv' >");
				strBuf.push("<div id='dialog_top'><div id='dialog_title'>" + param.title + "</div><div id='dialog_close1'><a href='javascript:void(dialog._close())' class='dialog_close'>×</a></div></div>");
				strBuf.push("<div id='dialog_content'>" + param.msg + "</div>");
				if(!param.btn){
					strBuf.push("<div id='dialog_button'>");
					strBuf.push("<div class='dialog_menu1'><a href='javascript:void(dialog._cancel())' > 取 消</a></div>");
					strBuf.push("<div class='dialog_menu1'> <a href='javascript:void(dialog._ok())'>确 定</a></div>");
					strBuf.push("</div>");
				}
				strBuf.push("</div>");
				
				$("body").prepend(strBuf.join(' '));
				
				if(param.width != undefined){
					$("#dialog_maindiv").width(param.width);
				}
				if(param.height != undefined){
					$("#dialog_content").height(param.height);
				}
				if(param.top != undefined){
					$("#dialog_maindiv").css("top",param.top + 'px');
				}
				if(param.left != undefined){
					$("#dialog_maindiv").css("left",param.left + 'px');
				}
				//$("#dialog_maindiv").draggable();
				
				//注册键盘esc关闭事件
				$(this.currentWindow.document).keydown(function (event){
					if(event.keyCode == 27){
						$("#dialog_maindiv").remove();
					}
				});
			}
		}
		
		this._ok = function (){
			if((typeof _param.ok) == 'function'){
				if(_param.type.type == "text"){
					_param.ok.call(this.currentWindow,window);
				}else if(_param.type.type == "iframe"){
					if(_param.type.param1 == undefined){
						_param.type.param1 = 0;
					}
					_param.ok.call(this.currentWindow,window.frames[_param.type.param1]);
				}
			}
			$("#dialog_maindiv").remove();
		}
		
		this._cancel = function (){
			if((typeof _param.cancel) == 'function'){
				if(_param.type.type == "text"){
					_param.cancel.call(this.currentWindow,window);
				}else if(_param.type.type == "iframe"){
					if(_param.type.param1 == undefined){
						_param.type.param1 = 0;
					}
					_param.cancel.call(this.currentWindow,window.frames[_param.type.param1]);
				}
			}
			$("#dialog_maindiv").remove();
		}
		
		this._close = function(){
			
			if((typeof _param.close) == 'function'){
				if(_param.type.type == "text"){
					_param.close.call(this.currentWindow,window);
				}else if(_param.type.type == "iframe"){
					if(_param.type.param1 == undefined){
						_param.type.param1 = 0;
					}
					_param.close.call(this.currentWindow,window.frames[_param.type.param1]);
				}
			}
			$("#dialog_maindiv").remove();
		}
		
		this.setCurrentWindow = function(_currentWindow){
			this.currentWindow = _currentWindow;
		}
		
	}
	var dialog = new Dialog(); 
////////////////////////////
 
 
 












var ui = new WebUtils();//有待该进




 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 