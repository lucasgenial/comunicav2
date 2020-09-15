setTimeout('refreshDashBoard()', 180000);

setTimeout(function(){
	$("#painel-1").toggle();
	$("#painel-2").toggle();
	
}, 90000);

window.addEventListener('resize', function(){
	refreshDashBoard();
});

function refreshDashBoard() {	
	document.getElementById('grupo').innerHTML = `
			<div class="row">
				<div class="col-12">
					<div id="apresentaPorClassificacao" class="row owl-carousel owl-theme"></div>
				</div>
			</div>

			<div class="row">
				<div id="painel-3" class="col-6">
					<div class="ribbon-wrapper card">
						<div class="ribbon ribbon-info">OCORRÊNCIAS - CIDADES</div>
						<div id="grf_1" style="width: 100%; min-height: 400px;"></div>
					</div>
				</div>
				
				<div id="painel-2" class="col-6">
					<div class="ribbon-wrapper card">
						<div class="ribbon ribbon-info">OCORRÊNCIAS - STATUS</div>
						<div id="apresentacaoPorStatus" class="owl-carousel owl-theme" style="width: 100%; min-height: 400px"></div>
					</div>
				</div>
				
				<div id="painel-1" class="col-6">
					<div class="ribbon-wrapper card">
						<div class="ribbon ribbon-info">OCORRÊNCIAS - HORA</div>
						<div id="grf_2" style="width: 100%; min-height: 400px"></div>						
					</div>
				</div>
				</div>				
			`
	geradorDeGraficosDashboard(document.getElementById("identificador").value);
	setTimeout('refreshDashBoard()', 180000);

	setTimeout(function(){
		$("#painel-1").toggle();
		$("#painel-2").toggle();
		
	}, 90000);

	window.addEventListener('resize', function(){
		refreshDashBoard();
	});
};