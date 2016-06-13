function   saveDoc(type)  

{      
    var   strHTML   =   $("#petTable").html();   
    var   winSave   =   window.open(); 
    var now= new Date(); 
    var year=now.getYear(); 
    var month=now.getMonth()+1; 
    var day=now.getDate(); 
    var displayTime=$("#periodsNum").val()+"【" + type + "】";
    winSave.document.open("text/html","utf-8");   
    winSave.document.write(strHTML);   
    winSave.document.execCommand("SaveAs",true,displayTime+".htm");   
    winSave.close();   
}

document.write('<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>');
