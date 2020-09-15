
setTimeout('refreshDashBoard()', 180000);

setTimeout(function(){
	$("#painel-1").toggle();
	$("#painel-2").toggle();
	
}, 90000);

window.addEventListener('resize', function(){
	refreshDashBoard();
});

function refreshDashBoard() {
	window.location.reload();
};

$(document).ready(function(){
    refreshDashBoard();
});

function refreshDashBoard() {
		var estabelecimento = document.getElementById("estabelecimento");
		window.location.reload();
		$("#estabelecimento").val(estabelecimento);
		setTimeout('refreshDashBoard()', 10000);
//		alert("Recarregou...");
}

//     	setTime();
//    	function setTime(){
//    		var date = new Date().getTime();
//    		setTimeout(setTime, 3000);
//    
//}


