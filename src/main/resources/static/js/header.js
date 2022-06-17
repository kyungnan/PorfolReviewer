var socket = null;
$(document).ready(function(){
	connectWs();
});


//소켓
function connectWs(){
	var ws = new SockJS("/push");
	socket = ws;
	
	ws.onopen = function() {
		console.log('open');
	};

	ws.onmessage = function(event) {
		console.log(event.data);
		let $socketAlert = $('#socketAlert');
		$socketAlert.html(event.data)
		$socketAlert.css('display', 'block');
		
		setTimeout(function(){
			$socketAlert.css('display','none');
		}, 5000);
	};

	ws.onclose = function() {
		console.log('close');
	};
 
};
//소켓끝