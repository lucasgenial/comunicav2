setTimeout('refreshDashBoard()', 60000);

$("#painel-2").toggle();



window.addEventListener('resize', function(){
	refreshDashBoard();
});

function refreshDashBoard() {
	window.location.reload();
};
