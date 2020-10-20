$(document).ready(function() {
	setInterval('refreshPage()', 180000);

	 setInterval(function(){
		$("#painel-2").toggle();
		$("#painel-1").toggle();
	}, 6000);	
});

// Criado um Observador de mutações do código para gerar um refresh da tela
// caso o Menu fosse fechado
var observer = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
	if($('body').hasClass("mini-sidebar")){
		//geraGraficoTipificacao(codigo);
		$('#apresentaPorClassificacao').owlCarousel().trigger('refresh.owl.carousel');
		$('#apresentacaoPorStatus').owlCarousel().trigger('refresh.owl.carousel');
		grafico1.resize();
		grafico2.resize();
	}else{
		$('#apresentaPorClassificacao').owlCarousel().trigger('refresh.owl.carousel');
		$('#apresentacaoPorStatus').owlCarousel().trigger('refresh.owl.carousel');
		grafico1.resize();
		grafico2.resize();
	}
  });
});

observer.observe(document.body, {
  attributes: true
});

function refreshPage() { 
	location.reload(1); 
}
//
//var te2 = setTimeout(function(){
//	$("#painel-1").toggle();
//	$("#painel-2").toggle();
//}, 90000);
//
//window.addEventListener('resize', function(){
//	document.location.reload();
////	window.location.reload();
////	refreshDashBoard();
//});


//function refreshDashBoard() {	
//	document.getElementById('grupo').innerHTML = `
//			<div class="row">
//				<div class="col-12">
//					<div id="apresentaPorClassificacao" class="row owl-carousel owl-theme"></div>
//				</div>
//			</div>
//
//			<div class="row">
//				<div id="painel-3" class="col-6">
//					<div class="ribbon-wrapper card">
//						<div class="ribbon ribbon-info">OCORRÊNCIAS - CIDADES</div>
//						<div id="grf_1" style="width: 100%; min-height: 400px;"></div>
//					</div>
//				</div>
//				
//				<div id="painel-2" class="col-6">
//					<div class="ribbon-wrapper card">
//						<div class="ribbon ribbon-info">OCORRÊNCIAS - STATUS</div>
//						<div id="apresentacaoPorStatus" class="owl-carousel owl-theme" style="width: 100%; min-height: 400px"></div>
//					</div>
//				</div>
//				
//				<div id="painel-1" class="col-6">
//					<div class="ribbon-wrapper card">
//						<div class="ribbon ribbon-info">OCORRÊNCIAS - HORA</div>
//						<div id="grf_2" style="width: 100%; min-height: 400px"></div>						
//					</div>
//				</div>
//				</div>				
//			`
//	geradorDeGraficosDashboard(document.getElementById("identificador").value);
//	setTimeout('refreshDashBoard()', 180000);
//
//	setTimeout(function(){
//		$("#painel-1").toggle();
//		$("#painel-2").toggle();
//		
//	}, 90000);
//
//	window.addEventListener('resize', function(){
//		refreshDashBoard();
//	});
//};