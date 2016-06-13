//十二生肖
var shu=new Array("5","17","29","41");
var niu=new Array("4","16","28","40");
var hu=new Array("3","15","27","39");
var tu=new Array("2","14","26","38");
var lon=new Array("1","13","25","37","49");
var se=new Array("12","24","36","48");
var ma=new Array("11","23","35","47");
var yang=new Array("10","22","34","46");
var hou=new Array("9","21","33","45");
var ji=new Array("8","20","32","44");
var gou=new Array("7","9","31","43");
var zhu=new Array("6","18","30","42");	
//家禽 野兽
var jq=zhu.concat(gou).concat(ji).concat(yang).concat(ma).concat(niu);
var ys=hou.concat(se).concat(lon).concat(tu).concat(hu).concat(shu);
//0-9尾
var w0=new Array("10","20","30","40");
var w1=new Array("1","11","21","31","41");
var w2=new Array("2","12","22","32","42");
var w3=new Array("3","13","23","33","43");
var w4=new Array("4","14","24","34","44");
var w5=new Array("5","15","25","35","45");
var w6=new Array("6","16","26","36","46");
var w7=new Array("7","17","27","37","47");
var w8=new Array("8","18","28","38","48");
var w9=new Array("9","19","29","39","49");

var t0=new Array("1","2","3","4","5","6","7","8","9");
var t1=new Array("10","11","12","13","14","15","16","17","18","19");
var t2=new Array("20","21","22","23","24","25","26","27","28","29");
var t3=new Array("30","31","32","33","34","35","36","37","38","39");
var t4=new Array("40","41","42","43","44","45","46","47","48","49");

//单双
var dan=new Array("1","3","5","7","9","11","13","15","17","19","21","23","25","27","29","31","33","35","37","39","41","43","45","47","49");
var shuang=new Array("2","4","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40","42","44","46","48");
//大小
var da=new Array("25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49");
var xiao=new Array("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
//合单双
var hedan=new Array("1","3","5","7","9","10","12","14","16","18","21","23","25","27","29","30","32","34","36","38","41","43","45","47","49");
var heshuang=new Array("2","4","6","8","11","13","15","17","19","20","22","24","26","28","31","33","35","37","39","40","42","44","46","48");


//尾大小
var wx=new Array("1","2","3","4","10","11","12","13","14","20","21","22","23","24","30","31","32","33","34","40","41","42","43","44");
var wd=new Array("5","6","7","8","9","15","16","17","18","19","25","26","27","28","29","35","36","37","38","39","45","46","47","48","49");

//大单双
var dadan=new Array("25","27","29","31","33","35","37","39","41","43","45","47");
var dashuang=new Array("26","28","30","32","34","36","38","40","42","44","46","48");
//小单双
var xiaodan=new Array("1","3","5","7","9","11","13","15","17","19","21","23");
var xiaoshuang=new Array("2","4","6","8","10","12","14","16","18","20","22","24");

//波色
var red=new Array("1","2","7","8","12","13","18","19","23","24","29","30","34","35","40","45","46");
//紅單
var reddan=new Array("1","7","13","19","23","29","35","45");
//紅雙
var redshuang=new Array("2","8","12","18","24","30","34","40","46");
//紅大
var redda=new Array("29","30","34","35","40","45","46");
//紅小
var redxiao=new Array("1","2","7","8","12","13","18","19","23","24");


var blue=new Array("3","4","9","10","14","15","20","25","26","31","36","37","41","42","47","48");
         var bluedan=new Array("3","9","15","25","31","37","41","47");
       	  var blueshuang=new Array("4","10","14","20","26","36","42","48");
       		  var blueda=new Array("25","26","31","36","37","41","42","47","48");
       			  var bluexiao=new Array("3","4","9","10","14","15","20");
      
var green=new Array("5","6","11","16","17","21","22","27","28","32","33","38","39","43","44","49");
           var greendan=  new Array("5","11","17","21","27","33","39","43"); 
           var greenshuang=  new Array("6","16","22","28","32","38","44");
           var greenda=  new Array("27","28","32","33","38","39","43","44");
           var greenxiao=  new Array("5","6","11","16","17","21","22");
    
var typeArray = new Array();;
function HKBall()
{
	typeArray[0] = new map("W0",w0);
	typeArray[1] = new map("W1",w1);
	typeArray[2] = new map("W2",w2);
	typeArray[3] = new map("W3",w3);
	typeArray[4] = new map("W4",w4);
	typeArray[5] = new map("W5",w5);
	typeArray[6] = new map("W6",w6);
	typeArray[7] = new map("W7",w7);
	typeArray[8] = new map("W8",w8);
	typeArray[9] = new map("W9",w9);
	typeArray[10] = new map("SHU",shu);
	typeArray[11] = new map("NIU",niu);
	typeArray[12] = new map("HU",hu);
	typeArray[13] = new map("TU",tu);
	typeArray[14] = new map("LONG",lon);
	typeArray[15] = new map("SHE",se);
	typeArray[16] = new map("MA",ma);
	typeArray[17] = new map("YANG",yang);
	typeArray[18] = new map("HOU",hou);
	typeArray[19] = new map("JI",ji);
	typeArray[20] = new map("GOU",gou);
	typeArray[21] = new map("ZHU",zhu);
	typeArray[22] = new map("DAN",dan);
	typeArray[23] = new map("SHUANG",shuang);
	typeArray[24] = new map("DA",da);
	typeArray[25] = new map("XIAO",xiao);
	typeArray[26] = new map("HSD",hedan);
	typeArray[27] = new map("HSS",heshuang);
	
	typeArray[28] = new map("RDAN",reddan);
	typeArray[29] = new map("RS",redshuang);
	typeArray[30] = new map("RDA",redda);
	typeArray[31] = new map("RX",redxiao);
	typeArray[32] = new map("BDAN",bluedan);
	typeArray[33] = new map("BS",blueshuang);
	typeArray[34] = new map("BDA",blueda);
	typeArray[35] = new map("BX",bluexiao);
	typeArray[36] = new map("GDAN",greendan);
	typeArray[37] = new map("GS",greenshuang);
	typeArray[38] = new map("GDA",greenda);
	typeArray[39] = new map("GX",greenxiao);
	typeArray[40] = new map("RED",red);
	typeArray[41] = new map("BLUE",blue);
	typeArray[42] = new map("GREEN",green);
	
	typeArray[43] = new map("T0",t0);
	typeArray[44] = new map("T1",t1);
	typeArray[45] = new map("T2",t2);
	typeArray[46] = new map("T3",t3);
	typeArray[47] = new map("T4",t4);
	
	typeArray[48] = new map("X",xiao);
	
}
function getMatchList(typeP)
{
	for(var i=0;i<typeArray.length;i++)
	{
		var map  = typeArray[i];
		if(map.type==typeP)
		{
			return map.typeList;
		}
//		alert(map.typeList.length);
	}
	return new Array();
}
function map(type,typeList)
{
	this.type = type;
	this.typeList = typeList;
}

function changeNumToBall(numBerBall)
{
	
	var newBall=$("<div>");

	$.each(numBerBall,function(name,value){
		
			if($.inArray(value,red)!=-1)
				{
				
				newBall.append("<span class=\"ball-bg ball-red\">"+value+"</span>&nbsp;");
				
				}
			else if($.inArray(value,blue)!=-1)
	{
				newBall.append("<span class=\"ball-bg ball-blue\">"+value+"</span>&nbsp;");
				
	}
			else if($.inArray(value,green)!=-1)
				{
					
					newBall.append("<span class=\"ball-bg ball-green\">"+value+"</span>&nbsp;");
					
				}
		})
		return newBall.html();
		
}