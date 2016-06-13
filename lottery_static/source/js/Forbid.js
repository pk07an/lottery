//if (window.Event) document.captureEvents(Event.MOUSEUP);
function nocontextmenu(){
//	event.cancelBubble = true
//	event.returnValue = false;
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
document.oncontextmenu = nocontextmenu; // for IE5+
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

//document.onkeydown=forbid_key;