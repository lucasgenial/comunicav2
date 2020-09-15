//document.addEventListener('DOMContentLoaded', function(e) {
//	FormValidation.formValidation( document.getElementById('formInstituicao'), {
//		fields : {
//			nome : {
//				icon : true,
//				validators : {
//					notEmpty : {
//						message : 'O campo nome não pode está em branco'
//					},
//					stringLength : {
//						min : 2,
//						max : 50,
//						message : 'valores entre 2 e 50 caracteres'
//					},
//				}
//			}
//		}
//	});
//    
//    /**
//     * 
//     * INFORMAÇÃOES SOBRE INSTITUIÇÃO
//     * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUIÇÃO
//     * 
//     * **/
//    $('.btn-nova-instituicao').on("click", function(){
//    	var titulo = $('.btn-nova-instituicao').data("titulo");
//    	$('#modal-instituicao').modal().find('.modal-title').text(titulo);
//    	
//    	$('.form-instituicao').attr("action","/admin/cadastraInstituicao");
//    	
//    	$(".id").hide();
//    	$("input[name='id']").val('');
//    	$("input[name='nome']").val('');
//    	$('#btn-salvar-instituicao').text("Salvar");
//    	$("#modal-instituicao").modal("show");
//    });
//
//    $('.btn-editar-instituicao').on("click", function(event){
//    	event.preventDefault();
//    	var titulo = $('.btn-editar-instituicao').data("titulo");
//    	var id = this.id;
//    	var href = "/admin/configuracao/"+ $(this).attr('href');
//    	
//    	$('#modal-instituicao').modal().find('.modal-title').text(titulo);
//    	
//    	$('.form-instituicao').attr("action", href);
//    	$('#btn-salvar-instituicao').text("Editar");
//
//    	$.ajax({
//          type : "GET",
//          url : "admin/configuracao/instituicoes/consultar/"+id,
//          success: function(data) {
//        	  console.log(data);
//              $("input[name='id']").val(data.id);
//              $("input[name='nome']").val(data.nome);
//              $("input[name='cor']").val(data.cor);
//          }
//    	});
//    	$("#modal-instituicao").modal("show");
//    });
//
//    $('.btn-excluir-instituicao').on("click", function(event){
//    	event.preventDefault();
//    	var href = $(this).attr('href');
//    	$("#delRef").attr("href",href);
//    	$("#modal-excluir").modal();
//    });
//});

$('#instituicao').on("change", function(e){
	document.getElementById("hierarquia").options.length = 1;
    $.ajax({
	      type : "GET",
	      url : "admin/cadastro/instituicoes/hierarquias/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("hierarquia");
	    		  select.add(option);
	    	  }	
	      }
    });
});