
 $(document).ready(function() {
	 var currentSel=new Array(); 

	            
$("#cancel").click(function() {	
	   //var a=1;
	   //alert($("#"+a).val());
		$("#moneySubmit").hide();
	});

 $("#quick a").click(function() {
	 var selName=$(this).attr("name");
	   if(selName=="red")	
	    currentSel=red;
	   else if(selName=="blue")
		   currentSel=blue;
	   else if(selName=="green")
		   currentSel=green; 
	   else if(selName=="w0")
		   currentSel=w0; 
	   else if(selName=="w1")
		   currentSel=w1; 
	   else if(selName=="w2")
		   currentSel=w2; 
	   else if(selName=="w3")
		   currentSel=w3; 
	   else if(selName=="w4")
		   currentSel=w4; 
	   else if(selName=="w5")
		   currentSel=w5; 
	   else if(selName=="w6")
		   currentSel=w6; 
	   else if(selName=="w7")
		   currentSel=w7; 
	   else if(selName=="w8")
		   currentSel=w8; 
	   else if(selName=="w9")
		   currentSel=w9;   
	   else if(selName=="shu")
		   currentSel=shu; 
	   else if(selName=="niu")
		   currentSel=niu;
	   else if(selName=="hu")
		   currentSel=hu;
	   else if(selName=="tu")
		   currentSel=tu;
	   else if(selName=="long")
		   currentSel=lon;
	   else if(selName=="se")
		   currentSel=se;
	   else if(selName=="ma")
		   currentSel=ma;
	   else if(selName=="yang")
		   currentSel=yang;
	   else if(selName=="hou")
		   currentSel=hou;
	   else if(selName=="ji")
		   currentSel=ji;
	   else if(selName=="gou")
		   currentSel=gou;
	   else if(selName=="zhu")
		   currentSel=zhu;
	   else if(selName=="jq")
		   currentSel=jq;
	   else if(selName=="ys")
		   currentSel=ys;
	   else if(selName=="da")
		   currentSel=da;
	   else if(selName=="xiao")
		   currentSel=xiao;
	   else if(selName=="dan")
		   currentSel=dan;
	   else if(selName=="shuang")
		   currentSel=shuang;
	   else if(selName=="hedan")
		   currentSel=hedan;
	   else if(selName=="heshuang")
		   currentSel=heshuang;
	   else if(selName=="wd")
		   currentSel=wd;
	   else if(selName=="wx")
		   currentSel=wx;
	   else if(selName=="dadan")
		   currentSel=dadan;
	   else if(selName=="dashuang")
		   currentSel=dashuang;
	   else if(selName=="xiaodan")
		   currentSel=xiaodan;
	   else if(selName=="xiaoshuang")
		   currentSel=xiaoshuang;
	   else if(selName=="redda")
		   currentSel=redda;
	   else if(selName=="redxiao")
		   currentSel=redxiao;
	   else if(selName=="reddan")
		   currentSel=reddan;
	   else if(selName=="redshuang")
		   currentSel=redshuang;
	   else if(selName=="blueda")
		   currentSel=blueda;
	   else if(selName=="bluexiao")
		   currentSel=bluexiao;
	   else if(selName=="bluedan")
		   currentSel=bluedan;
	   else if(selName=="blueshuang")
		   currentSel=blueshuang;
		   else if(selName=="greenda")
			   currentSel=greenda;
		   else if(selName=="greenxiao")
			   currentSel=greenxiao;
		   else if(selName=="greendan")
			   currentSel=greendan;
		   else if(selName=="greenshuang")
			   currentSel=greenshuang;
	  //alert($(this).attr("name"))
	 
	 $("#moneySubmit").show();
		
	});
 
 
 
 
 $("#qs :input[type:radio],#sxs :input[type:radio]").click(function() {
	 var selName=$(this).attr("name");
	   if(selName=="red")	
	    currentSel=red;
	   else if(selName=="blue")
		   currentSel=blue;
	   else if(selName=="green")
		   currentSel=green; 
	   else if(selName=="w0")
		   currentSel=w0; 
	   else if(selName=="w1")
		   currentSel=w1; 
	   else if(selName=="w2")
		   currentSel=w2; 
	   else if(selName=="w3")
		   currentSel=w3; 
	   else if(selName=="w4")
		   currentSel=w4; 
	   else if(selName=="w5")
		   currentSel=w5; 
	   else if(selName=="w6")
		   currentSel=w6; 
	   else if(selName=="w7")
		   currentSel=w7; 
	   else if(selName=="w8")
		   currentSel=w8; 
	   else if(selName=="w9")
		   currentSel=w9;   
	   else if(selName=="shu")
		   currentSel=shu; 
	   else if(selName=="niu")
		   currentSel=niu;
	   else if(selName=="hu")
		   currentSel=hu;
	   else if(selName=="tu")
		   currentSel=tu;
	   else if(selName=="long")
		   currentSel=lon;
	   else if(selName=="se")
		   currentSel=se;
	   else if(selName=="ma")
		   currentSel=ma;
	   else if(selName=="yang")
		   currentSel=yang;
	   else if(selName=="hou")
		   currentSel=hou;
	   else if(selName=="ji")
		   currentSel=ji;
	   else if(selName=="gou")
		   currentSel=gou;
	   else if(selName=="zhu")
		   currentSel=zhu;
	   else if(selName=="jq")
		   currentSel=jq;
	   else if(selName=="ys")
		   currentSel=ys;
	   else if(selName=="da")
		   currentSel=da;
	   else if(selName=="xiao")
		   currentSel=xiao;
	   else if(selName=="dan")
		   currentSel=dan;
	   else if(selName=="shuang")
		   currentSel=shuang;
	   else if(selName=="hedan")
		   currentSel=hedan;
	   else if(selName=="heshuang")
		   currentSel=heshuang;
	   else if(selName=="wd")
		   currentSel=wd;
	   else if(selName=="wx")
		   currentSel=wx;
	   else if(selName=="dadan")
		   currentSel=dadan;
	   else if(selName=="dashuang")
		   currentSel=dashuang;
	   else if(selName=="xiaodan")
		   currentSel=xiaodan;
	   else if(selName=="xiaoshuang")
		   currentSel=xiaoshuang;
	   else if(selName=="redda")
		   currentSel=redda;
	   else if(selName=="redxiao")
		   currentSel=redxiao;
	   else if(selName=="reddan")
		   currentSel=reddan;
	   else if(selName=="redshuang")
		   currentSel=redshuang;
	   else if(selName=="blueda")
		   currentSel=blueda;
	   else if(selName=="bluexiao")
		   currentSel=bluexiao;
	   else if(selName=="bluedan")
		   currentSel=bluedan;
	   else if(selName=="blueshuang")
		   currentSel=blueshuang;
		   else if(selName=="greenda")
			   currentSel=greenda;
		   else if(selName=="greenxiao")
			   currentSel=greenxiao;
		   else if(selName=="greendan")
			   currentSel=greendan;
		   else if(selName=="greenshuang")
			   currentSel=greenshuang;
	  //alert($(this).attr("name"));
	 
		$.each(currentSel,function(name,value){
			$("#q"+value).addClass('selectnumber');
			 	//alert($("#q"+value).html());
			 	}
		
		)
		
	});
 

 
 
 
 
 
 
 $("#reset").click(function() {
	 	
		var fillValue=$("#qsrmb").val();
		$("#qsnum tr td").removeClass('selectnumber');
		$("#qs input[type=radio][@checked],#sxs input[type=radio][@checked]").attr("checked",false);
	
	});
 
 
 $("#qsall").click(function() {
	 	
	 var td=$("#qsnum").find("tr td");
		$.each(td,function(){			
			if(!$(this).hasClass('empty'))
				$(this).addClass('selectnumber');	 	
		})
		//$("#qsnum tr td").addClass('selectnumber');

	});

 
 $("#qsnum td").click(function() {
	 	
     if(!$(this).hasClass('empty'))
	 $(this).addClass('selectnumber');	 	

	});
 
 $("#qsnum td").mouseover(function() {	 	
     if(!$(this).hasClass('empty'))
	 $(this).css(
			 { cursor: 'default'}	 
);	 	

	});
 
 
	
$("#quickfill").click(function() {
	//alert("ccc");
	
	var fillValue=$("#txtrmb").val();
	$.each(currentSel,function(name,value){
           //alert(fillValue);
		 	$("#"+value).val(fillValue);
		 	}
	
	)
	$("#moneySubmit").hide();
});
 

}); 