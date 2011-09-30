	var scrollSpeed = 70;	
	var current = 0;
	var direction = 'h';

	function bgscroll(){
	    current -= 1;   
	    $('div#nav').css("backgroundPosition", (direction == 'h') ? (current * 4)+"px 0" : "0 " + (current * 4) +"px");   
	    $('div#header').css("backgroundPosition", (direction == 'h') ? (current / 2) + "px 0" : "0 " + (current / 2) + "px");   
	}

	 setInterval("bgscroll()", scrollSpeed);
