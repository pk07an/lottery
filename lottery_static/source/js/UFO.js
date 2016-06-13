/*	Unobtrusive Flash Objects (UFO) v3.12 <http://www.bobbyvandersluis.com/ufo/>
	Copyright 2005, 2006 Bobby van der Sluis
	This software is licensed under the CC-GNU LGPL <http://creativecommons.org/licenses/LGPL/2.1/>
*/

var UFO = {
	requiredAttrParams: ["movie", "width", "height", "majorversion", "build"],
	optionalAttrEmb: ["name", "swliveconnect", "align"],
	optionalAttrObj: ["id", "align"],
	optionalAttrParams: ["play", "loop", "menu", "quality", "scale", "salign", "wmode", "bgcolor", "base", "flashvars", "devicefont", "allowscriptaccess", "seamlesstabbing"],
	ximovie: "ufo.swf",
	xiwidth: "215",
	xiheight: "138",
	
	is_w3cdom: (typeof document.getElementById != "undefined" && typeof document.getElementsByTagName != "undefined" && (typeof document.createElement != "undefined" || typeof document.createElementNS != "undefined")),
	is_ie: (navigator.userAgent.toLowerCase().indexOf("msie") != -1 && navigator.userAgent.toLowerCase().indexOf("opera") == -1),
	is_safari: (navigator.userAgent.toLowerCase().indexOf("safari") != -1),
	is_win: (navigator.userAgent.toLowerCase().indexOf("win") != -1),
	is_mac: (navigator.userAgent.toLowerCase().indexOf("mac") != -1),
	is_XML: (typeof document.contentType != "undefined" && document.contentType.indexOf("xml") > -1),
	
	foList: [],
		
	create: function(FO, id) {
		if (!UFO.is_w3cdom) return;
		UFO.foList[id] = UFO.updateFO(FO);
		UFO.createStyleRule("#" + id, "visibility:hidden;");
		UFO.domLoad(id);
	},

	updateFO: function(FO) {
		if (typeof FO.xi != "undefined" && FO.xi == "true") {
			if (typeof FO.ximovie == "undefined") FO.ximovie = UFO.ximovie;
			if (typeof FO.xiwidth == "undefined") FO.xiwidth = UFO.xiwidth;
			if (typeof FO.xiheight == "undefined") FO.xiheight = UFO.xiheight;
		}
		else {
			FO.xi = false;
		}
		FO.domLoaded = false;
		return FO;
	},

	domLoad: function(id) {
		var timer = setInterval(function() { // doesn't work in IE/Mac
			if((document.getElementsByTagName("body")[0] != null || document.body != null) &&  document.getElementById(id) != null) {
				UFO.main(id);
				clearInterval(timer);
			}
		}, 250);
		if (typeof document.addEventListener != "undefined") {
			document.addEventListener("DOMContentLoaded", function() { UFO.main(id); clearInterval(timer); } , null); // Mozilla only
		}
	},

	main: function(id) {
		var FO = UFO.foList[id];
		if (FO.domLoaded) return; // for Mozilla, only execute once
		UFO.foList[id].domLoaded = true;
		document.getElementById(id).style.visibility = "hidden";
		if (UFO.hasRequiredAttrParams(id)) {
			if (UFO.hasFlashVersion(FO.majorversion, FO.build)) {
				if (typeof FO.setcontainercss != "undefined" && FO.setcontainercss == "true") {
					UFO.setContainerCSS(id);
				}
				UFO.writeFlashObject(id);
			}
			else if (FO.xi && UFO.hasFlashVersion("6", "65")) {
				UFO.createModalDialog(id);
			}
		}
		document.getElementById(id).style.visibility = "visible";
	},
	
	createStyleRule: function(selector, declaration) {
		if (UFO.is_ie && UFO.is_mac) return; // bugs in IE/Mac
		var head = document.getElementsByTagName("head")[0]; 
		var style = UFO.createElement("style");
		if (!(UFO.is_ie && UFO.is_win)) {
			var styleRule = document.createTextNode(selector + " {" + declaration + "}");
			style.appendChild(styleRule); // bugs in IE/Win
		}
		style.setAttribute("type", "text/css");
		style.setAttribute("media", "screen"); 
		head.appendChild(style);
		if (UFO.is_safari && UFO.is_XML) { head.innerHTML += ""; } // force Safari repaint for MIME type application/xhtml+xml
		if (UFO.is_ie && UFO.is_win && document.styleSheets && document.styleSheets.length > 0) {
			var lastStyle = document.styleSheets[document.styleSheets.length - 1];
			if (typeof lastStyle.addRule == "object") {
				lastStyle.addRule(selector, declaration);
			}
		}
	},
	
	setContainerCSS: function(id) {
		var FO = UFO.foList[id];
		var wUnit = (FO.width.indexOf("%") == -1) ? "px" : "";
		var hUnit = (FO.height.indexOf("%") == -1) ? "px" : "";
		UFO.createStyleRule("#" + id, "width:" + FO.width + wUnit +"; height:" + FO.height + hUnit +";");
		if (FO.width == "100%") {
			UFO.createStyleRule("body", "margin-left:0; margin-right:0; padding-left:0; padding-right:0;");
		}
		if (FO.height == "100%") {
			UFO.createStyleRule("html", "height:100%; overflow:hidden;");
			UFO.createStyleRule("body", "margin-top:0; margin-bottom:0; padding-top:0; padding-bottom:0; height:100%;");
		}
	},

	createElement: function(el) {
		return (typeof document.createElementNS != "undefined") ?  document.createElementNS("http://www.w3.org/1999/xhtml", el) : document.createElement(el);
	},

	hasRequiredAttrParams: function(id) {
		var FO = UFO.foList[id];
		for (var i = 0; i < UFO.requiredAttrParams.length; i++) {
			if (typeof FO[UFO.requiredAttrParams[i]] == "undefined") return false;
		}
		return true;
	},
	
	hasFlashVersion: function(majorVersion, buildVersion) {
		var reqVersion = parseFloat(majorVersion + "." + buildVersion);
		if (navigator.plugins && typeof navigator.plugins["Shockwave Flash"] == "object") {
			var desc = navigator.plugins["Shockwave Flash"].description;
			if (desc) {
				var versionStr = desc.replace(/^.*\s+(\S+\s+\S+$)/, "$1");
				var major = parseInt(versionStr.replace(/^(.*)\..*$/, "$1"));
				var build = (versionStr.indexOf("r") == -1) ? 0 : parseInt(versionStr.replace(/^.*r(.*)$/, "$1"));
				var flashVersion = parseFloat(major + "." + build);
			}
		}
		else if (window.ActiveXObject) {
			try {
				var flashObj = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
				var desc = flashObj.GetVariable("$version");
				if (desc) {
					var versionArr = desc.replace(/^\S+\s+(.*)$/, "$1").split(",");
					var major = parseInt(versionArr[0]);
					var build = parseInt(versionArr[2]);
					var flashVersion = parseFloat(major + "." + build);
				}
			}
			catch(e) {}
		}
		if (typeof flashVersion != "undefined"){
			return (flashVersion >= reqVersion ? true : false); 
		}
		return false;
	},

	writeFlashObject: function(id) {
		var el = document.getElementById(id);
		if (typeof el.innerHTML == "undefined") return;
		var FO = UFO.foList[id];
		if (navigator.plugins && typeof navigator.plugins["Shockwave Flash"] == "object") {
			try	{ // older versions of Gecko only support innerHTML get and not set
				el.innerHTML = "ufo-test";
			}
			catch (e) {}
			if (el.innerHTML != "ufo-test") {
				while(el.hasChildNodes()) {
					el.removeChild(el.firstChild);
				}
				var embed = UFO.createElement("embed");
				embed.setAttribute("type", "application/x-shockwave-flash");
				embed.setAttribute("pluginspage", "http://www.macromedia.com/go/getflashplayer");
				embed.setAttribute("src", FO.movie);
				embed.setAttribute("width", FO.width);
				embed.setAttribute("height", FO.height);
				for (var i = 0; i < UFO.optionalAttrEmb.length; i++) {
					if (typeof FO[UFO.optionalAttrEmb[i]] != "undefined") {
						embed.setAttribute(UFO.optionalAttrEmb[i], FO[UFO.optionalAttrEmb[i]]);
					}
				}
				for (var i = 0; i < UFO.optionalAttrParams.length; i++) {
					if (typeof FO[UFO.optionalAttrParams[i]] != "undefined") {
						embed.setAttribute(UFO.optionalAttrParams[i], FO[UFO.optionalAttrParams[i]]);
					}
				}	
				el.appendChild(embed);
			}
			else {
				var embHTML = "";
				for (var i = 0; i < UFO.optionalAttrEmb.length; i++) {
					if (typeof FO[UFO.optionalAttrEmb[i]] != "undefined") {
						embHTML += ' ' + UFO.optionalAttrEmb[i] + '="' + FO[UFO.optionalAttrEmb[i]] + '"';
					}
				}
				for (var i = 0; i < UFO.optionalAttrParams.length; i++) {
					if (typeof FO[UFO.optionalAttrParams[i]] != "undefined") {
						embHTML += ' ' + UFO.optionalAttrParams[i] + '="' + FO[UFO.optionalAttrParams[i]] + '"';
					}
				}
				el.innerHTML = '<embed type="application/x-shockwave-flash" src="' + FO.movie + '" width="' + FO.width + '" height="' + FO.height + '" pluginspage="http://www.macromedia.com/go/getflashplayer"' + embHTML + '></embed>';
			}
		}
		else {
			var objAttrHTML = "";
			for (var i = 0; i < UFO.optionalAttrObj.length; i++) {
				if (typeof FO[UFO.optionalAttrObj[i]] != "undefined") {
					objAttrHTML += ' ' + UFO.optionalAttrObj[i] + '="' + FO[UFO.optionalAttrObj[i]] + '"';
				}
			}
			var objParamHTML = "";
			for (var i = 0; i < UFO.optionalAttrParams.length; i++) {
				if (typeof FO[UFO.optionalAttrParams[i]] != "undefined") {
					objParamHTML += '<param name="' + UFO.optionalAttrParams[i] + '" value="' + FO[UFO.optionalAttrParams[i]] + '" />';
				}
			}
			var protocol = (window.location.protocol == "https:" ? "https:" : "http:");
			el.innerHTML = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"' + objAttrHTML + ' width="' + FO.width + '" height="' + FO.height + '" codebase="' + protocol + '//download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=' + FO.majorversion + ',0,' + FO.build + ',0"><param name="movie" value="' + FO.movie + '" />' + objParamHTML + '</object>';
		}
	},
	
	cleanupIELeaks: function() {
		var objects = document.getElementsByTagName("OBJECT");
		for (var i = 0; i < objects.length; i++) {
			for (var x in objects[i]) {
				if (typeof objects[i][x] == "function") {
					objects[i][x] = null;
				}
			}
		}
	},

	createModalDialog: function(id) {
		var FO = UFO.foList[id];
		UFO.createStyleRule("html", "height:100%; overflow:hidden;");
		UFO.createStyleRule("body", "height:100%; overflow:hidden;");
		UFO.createStyleRule("#xi-con", "position:absolute; left:0; top:0; z-index:1000; width:100%; height:100%; background-color:#333; filter:alpha(opacity:50); -khtml-opacity:0.5; -moz-opacity:0.5; opacity:0.5;");
		UFO.createStyleRule("#xi-mod", "position:absolute; left:50%; top:50%; margin-left: -" + (parseInt(FO.xiwidth)/2) + "px; margin-top: -" + (parseInt(FO.xiheight)/2) + "px; width:" + FO.xiwidth + "px; height:" + FO.xiheight + "px;");
		var body = document.getElementsByTagName("body")[0];
		var container = UFO.createElement("div");
		container.setAttribute("id", "xi-con");
		var dialog = UFO.createElement("div");
		dialog.setAttribute("id", "xi-mod");
		container.appendChild(dialog);
		body.appendChild(container);
		var MMredirectURL = window.location;
		document.title = document.title.slice(0, 47) + " - Flash Player Installation";
		var MMdoctitle = document.title;
		var MMplayerType = (UFO.is_ie && UFO.is_win) ? "ActiveX" : "PlugIn";
		var xiUrlCancel = (typeof FO.xiurlcancel != "undefined") ? "&xiUrlCancel=" + FO.xiurlcancel : "";
		var xiUrlFailed = (typeof FO.xiurlfailed != "undefined") ? "&xiUrlFailed=" + FO.xiurlfailed : "";
		UFO.foList["xi-mod"] = { movie:FO.ximovie, width:FO.xiwidth, height:FO.xiheight, majorversion:"6", build:"65", flashvars:"MMredirectURL=" + MMredirectURL + "&MMplayerType=" + MMplayerType + "&MMdoctitle=" + MMdoctitle + xiUrlCancel + xiUrlFailed };
		UFO.writeFlashObject("xi-mod");
	},

	expressInstallCallback: function() {
		var body = document.getElementsByTagName("body")[0];
		var dialog = document.getElementById("xi-con");
		body.removeChild(dialog);
		UFO.createStyleRule("body", "height:auto; overflow:auto;");
		UFO.createStyleRule("html", "height:auto; overflow:auto;");
	}

};

if (typeof window.attachEvent != "undefined" && UFO.is_ie && UFO.is_win) {
	window.attachEvent("onunload", UFO.cleanupIELeaks);
}
