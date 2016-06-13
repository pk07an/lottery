function changeColor($this)
{
	if($this.className=="blueTr t_right"){
		$this.className="noneTr t_right";
	}else{
		$this.className="blueTr t_right";
	}
}

//***************交收报表****************start
//处理行变色,主要对应收下线，贡献上级，应付上级进行处理
function changeColorToYL($this){
	$this.children[5].className = "t_right";
	$this.children[14].className = "t_right";
	$this.children[15].className = "t_right";
	if($this.className!="blueTr t_right"){
         $this.className="yl t_right";
         $this.children.className="yl t_right";
	}
     
}
function changeColorToOld($this){
	if($this.className!="blueTr t_right"){
         $this.className="t_right";
         $this.children.className="t_right";
         $this.children[5].className = "even5 t_right"
         $this.children[14].className = "even6 t_right"
         $this.children[15].className = "even6 t_right"
	}
}

//处理行变色,主要对应收下线，贡献上级，应付上级进行处理
function changeColorToYL_chief($this){
	$this.children[5].className = "t_right";
	if($this.className!="blueTr t_right"){
	     $this.className="yl t_right";
	     $this.children.className="yl t_right";
	}
     
}
function changeColorToOld_chief($this){
	if($this.className!="blueTr t_right"){
	     $this.className="t_right";
	     $this.children.className="t_right";
	     $this.children[5].className = "even5 t_right"
	}
}
//***************交收报表****************end

//***************分类报表****************start
//处理行变色,主要对应收下线，贡献上级，应付上级进行处理
function changeColorToYLClass($this){
	$this.children[4].className = "t_right";
	$this.children[13].className = "t_right";
	$this.children[14].className = "t_right";
	if($this.className!="blueTr t_right"){
	     $this.className="yl t_right";
	     $this.children.className="yl t_right";
	}
     
}
function changeColorToOldClass($this){
	if($this.className!="blueTr t_right"){
	     $this.className="t_right";
	     $this.children.className="t_right";
	     $this.children[4].className = "even5 t_right"
	     $this.children[13].className = "even6 t_right"
	     $this.children[14].className = "even6 t_right"
	}
}

//处理行变色,主要对应收下线，贡献上级，应付上级进行处理
function changeColorToYLClass_chief($this){
	$this.children[4].className = "t_right";
	if($this.className!="blueTr t_right"){
	     $this.className="yl t_right";
	     $this.children.className="yl t_right";
	}
     
}
function changeColorToOldClass_chief($this){
	if($this.className!="blueTr t_right"){
     $this.className="t_right";
     $this.children.className="t_right";
     $this.children[4].className = "even5 t_right"
	}
}
//***************分类报表****************end