
$('#parametroDePesquisa').on("keyup", function(event){
	
	$("#formDaTabelaPaginada").submit();	
});

$('#resultadosPorPagina').on("change", function(event){
	
	$("#formDaTabelaPaginada").submit();
	
});

/**
 * 
 * 
 * Aqui encontra-se as ações para renderizar o calendario para data e hora
 * 
 * **/
function maiuscula(z){
    v = z.value.toUpperCase();
    z.value = v;
}

//$('.form_dateDeEmissao').datetimepicker({
//	language:  'pt-BR',
//	weekStart: 1,
//	todayBtn:  1,
//	autoclose: 1,
//	todayHighlight: 1,
//	startView: 4,
//	minView: 2,
//	maxView: 4,
//	forceParse: 0,
//	minuteStep: 1
//});
//
//$('.form_datetime').datetimepicker({
//	language:  'pt-BR',
//	todayBtn:  1,
//	autoclose: 1,
//	todayHighlight: 1,
//	startView: 3,
//	forceParse: 0,
//	minuteStep: 1
//});

/**
 * 
 * INFORMAÇÃOES SOBRE CIDADES
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
 * 
 * **/
$('.btn-nova-cidade').on("click", function(){
	var titulo = $('.btn-nova-cidade').data("titulo");
	$('#modal-cidade').modal().find('.modal-title').text(titulo)
	$('.form-cidade').attr("action","/admin/cadastraCidade");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$('#btn-salvar-cidade').text("Salvar");
	$("#modal-cidade").modal("show");
});

$('.btn-editar-cidade').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-cidade').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
	
	$('#modal-cidade').modal().find('.modal-title').text(titulo);
	$('.form-cidade').attr("action", href);
	$('#btn-salvar-cidade').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/cadastros/cidades/consultar/"+id,
      success: function(data) {
          console.log(data);
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
      }
	});
	$("#modal-cidade").modal("show");
});

$('.btn-excluir-cidade').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});


/**
 * 
 * INFORMAÇÃOES SOBRE EMAIL
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à EMAIL
 * 
 * **/
$('.btn-novo-email').on("click", function(){
	var titulo = $('.btn-novo-email').data("titulo");
	$('#modal-email').modal().find('.modal-title').text(titulo)
	$('.form-email').attr("action","/admin/cadastraEmail");
	$("input[name='nome']").val('');
	$('#btn-salvar-email').text("Salvar");
	$("#modal-email").modal("show");
});

$('.btn-editar-email').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-email').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
	
	$('#modal-email').modal().find('.modal-title').text(titulo);
	$('.form-email').attr("action", href);
	$('#btn-salvar-email').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/cadastros/emails/consultar/"+id,
      success: function(data) {
          console.log(data);
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='enderecoDeEmail']").val(data.enderecoDeEmail);
      }
	});
	$("#modal-email").modal("show");
});

$('.btn-excluir-email').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});



/**
 * 
 * INFORMAÇÃOES SOBRE USUARIO
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à USUARIOS
 * 
 * **/
$('.btn-novo-usuario').on("click", function(){
	var titulo = $('.btn-novo-usuario').data("titulo");
	$('#modal-usuario').modal().find('.modal-title').text(titulo)
	$('.form-usuario').attr("action","/admin/cadastraUsuario");
	$("#grupo").val('');
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='login']").val('');
	$("input[name='senha']").val('');
	$('#btn-salvar-usuario').text("Salvar");
	$("#modal-usuario").modal("show");
});

$('.btn-editar-usuario').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-usuario').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
		
	$('.form-usuario').attr("action", href);
	$('#btn-salvar-usuario').text("Editar");
	$('#modal-usuario').modal().find('.modal-title').text(titulo);

	$.ajax({
      type : "GET",
      url : "admin/configuracao/usuarios/consultar/"+id,
      success: function(data) {
    	   console.log(data);
          $("#grupo").val(data.grupo.id);
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='login']").val(data.login);
      }
	});
	$("#modal-usuario").modal("show");
});

$('.btn-excluir-usuario').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

/**
 * 
 * INFORMAÇÃOES SOBRE PERMISSÃO
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à PERMISSÃO
 * 
 * **/
$('.btn-nova-permissao').on("click", function(){
	var titulo = $('.btn-nova-permissao').data("titulo");
	$('#modal-permissao').modal().find('.modal-title').text(titulo)
	$('.form-permissao').attr("action","/admin/cadastraPermissao");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-permissao').text("Salvar");
	$("#modal-permissao").modal("show");
});

$('.btn-editar-permissao').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-permissao').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-permissao').modal().find('.modal-title').text(titulo);
	$('.form-permissao').attr("action", href);
	$('#btn-salvar-permissao').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/permissoes/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-permissao").modal("show");
});

$('.btn-excluir-permissao').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});


/**
 * 
 * INFORMAÇÃOES SOBRE OCORRÊNCIAS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à OCORRÊNCIAS
 * 
 * **/
$('.btn-novo-ocorrencia').on("click", function(){
	var titulo = $('.btn-novo-ocorrencia').data("titulo");

	$('#modal-ocorrencia').modal().find('.modal-title').text(titulo);
	$('.form-ocorrencia').attr("action","/admin/cadastraOcorrencia");
	$("input[name='sic']").val('');
	$("input[name='guarnicao']").val('');
	
	$('#btn-salvar-ocorrencia').text("Salvar");
	$("#modal-ocorrencia").modal("show");
});

$('.btn-editar-ocorrencia').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-ocorrencia').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');

	$('#modal-ocorrencia').modal().find('.modal-title').text(titulo);
	$('.form-ocorrencia').attr("action", href);
	$('#btn-salvar-ocorrencia').text("Editar");
		   
	$.ajax({
	   type : "GET",
	   url : "admin/cadastros/ocorrencias/consultar/"+id,
	      success: function(data){
	      console.log(data) 	  
	      
	   $("input[name='sic']").val(data.sic);
	   $("input[name='descricao']").val(data.descricao);
	   $("input[name='guarnicao']").val(data.guarnicao);	   
	   }
	});
	
		
	$.ajax({
	type : "GET",
	   url : "admin/cadastros/ocorrencias/consultarEndereco/"+id,
	      success: function(datas){
	    	console.log(datas)
	    	
	      $("#endereco.cidade").val(datas.cidade); 
	      $("option[selected='nome']").val(datas.cidade.nome);
	      $("#endereco.bairro").val(datas.bairro.id); 
	      $("#endereco.referencia").val(datas.referencia); 
	      
	      }
	      });
	
	$("#modal-ocorrencia").modal("show");
});

$('.btn-excluir-ocorrencia').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});




/**
 * 
 * HABILITA O BOTAO DE ENVIAR UMA OCORRENCIA PARA VARIOS EMAILS DE UMA VEZ
 * 
 * **/
$('.listaDeEmails').on("change", function(e){
	var lista = document.getElementsByTagName("input");
	var listaDeChekboxes = new Array();
	var temAlgumChecado = false;
	
	for (var i=0;i < lista.length;i++) {
		if (lista[i].type=="checkbox" && lista[i].name=="emailLista") {
			listaDeChekboxes[listaDeChekboxes.length]= lista[i];
		}
	}

	for(var i=0; i < listaDeChekboxes.length;i++){
		if(listaDeChekboxes[i].checked==true){
			temAlgumChecado = true;
		}
	if(temAlgumChecado){
	
		document.getElementById('btn-enviarEmails_Selecionados').disabled=false;
	}else{
	
		document.getElementById('btn-enviarEmails_Selecionados').disabled=true;
	}
	}

});

$('.btn-sucesso').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	//$("#modal-excluir #delRef").attr("href",href);
	$("#modal-sucesso").modal();
});


/**
 * 
 * @param event
 * @returns
 * 
 * Adicao de grupo no usuário
 */

$('.btn-usuario-grupo').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$('#formUsuarioGrupo').attr("action","/admin/cadastro/usuarios/editarGrupo/" + this.id );
	$("#delRef").attr("href",href);
	$('#btn-usuarioEditar').attr("name",this.id);
	$("#modal-usuario-grupo").modal();
});

$('.btn-alterarGrupo').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$('#formUsuarioGrupo').attr("action","/admin/cadastro/grupos/alterarGrupo/" + this.id );
	$("#delRef").attr("href",href);
	$('#btn-usuarioEditar').attr("name",this.id);
	$("#modal-usuario-grupo").modal();
});

$('.btn-registro').on("click", function(){
	/*var titulo = $('.btn-registro').data("titulo");
	$('#modal-registro').modal().find('.modal-title').text(titulo)*/
	/*$('.form-registro').attr("action","/admin/cadastraCidade");*/
	$("input[name='login']").val('');
	$('#btn-salvar-usuario').text("Salvar");
	$("#modal-registro").modal("show");
});




/**
 * 
 * INFORMAÇÃOES SOBRE INSTITUICAO
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-instituicao').on("click", function(){
	var titulo = $('.btn-nova-instituicao').data("titulo");
	$('#modal-instituicao').modal().find('.modal-title').text(titulo)
	$('.form-instituicao').attr("action","/admin/cadastraInstituicao");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-instituicao').text("Salvar");
	$("#modal-instituicao").modal("show");
});

$('.btn-editar-instituicao').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-instituicao').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-instituicao').modal().find('.modal-title').text(titulo);
	$('.form-instituicao').attr("action", href);
	$('#btn-salvar-instituicao').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/instituicoes/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-instituicao").modal("show");
});

$('.btn-excluir-instituicao').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});



/**
 * 
 * INFORMAÇÃOES SOBRE UNIDADE
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-unidade').on("click", function(){
	var titulo = $('.btn-nova-unidade').data("titulo");
	$('#modal-unidade').modal().find('.modal-title').text(titulo)
	$('.form-unidade').attr("action","/admin/cadastraUnidade");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-unidade').text("Salvar");
	$("#modal-unidade").modal("show");
});

$('.btn-editar-unidade').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-unidade').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-unidade').modal().find('.modal-title').text(titulo);
	$('.form-unidade').attr("action", href);
	$('#btn-salvar-unidade').text("Editar");

	$.ajax({

	      type : "GET",
	      url : "admin/cadastros/unidades/consultar/"+id,
	      success: function(data) {
	          $("input[name='id']").val(data.id);
	          $("input[name='nome']").val(data.nome);
	      }
		});
		
		$.ajax({
		      type : "GET",
		      url : "/admin/cadastros/unidades/consultar/email/enderecoDeEmail/"+id,
		      success: function(data) {	
		          $("#enderecoDeEmail").val(data.enderecoDeEmail);	
		      }
			});	
		
		$.ajax({
		      type : "GET",
		      url : "/admin/cadastros/unidades/consultar/cidade/estado/"+id,
		      success: function(data) {	
		          $("#uf-unidade").val(data.id);	
		          
		      }
			});	
		$.ajax({
		      type : "GET",
		      url : "/admin/cadastros/unidades/consultar/cidade/"+id,
		      success: function(data1) {	          	         
		          $("#cidade-unidade").val(data1.id);         
		      }
			});

	  $.ajax({	
      type : "GET",
      url : "admin/cadastros/unidades/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
      }
	});
	
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/unidades/consultar/email/enderecoDeEmail/"+id,
	      success: function(data) {	
	          $("#enderecoDeEmail").val(data.enderecoDeEmail);	
	      }
		});	
	
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/unidades/consultar/cidade/estado/"+id,
	      success: function(data) {	
	          $("#uf-unidade").val(data.id);	
	          
	      }
		});	
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/unidades/consultar/cidade/"+id,
	      success: function(data1) {	          	         
	          $("#cidade-unidade").val(data1.id);         
	      }
		});
	
	
	

	$("#modal-unidade").modal("show");
});

$('.btn-excluir-unidade').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});


/**
 * 
 * INFORMAÇÃOES SOBRE RECURSOS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-recurso').on("click", function(){
	var titulo = $('.btn-nova-recurso').data("titulo");
	$('#modal-recurso').modal().find('.modal-title').text(titulo)
	$('.form-recurso').attr("action","/admin/cadastraRecurso");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-recurso').text("Salvar");
	$("#modal-recurso").modal("show");
});

$('.btn-editar-recurso').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-recurso').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-recurso').modal().find('.modal-title').text(titulo);
	$('.form-recurso').attr("action", href);
	$('#btn-salvar-recurso').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/recursos/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-recurso").modal("show");
});

$('.btn-excluir-recurso').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});



/**
 * 
 * INFORMAÇÃOES SOBRE FUNCOES
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-funcao').on("click", function(){
	var titulo = $('.btn-nova-funcao').data("titulo");
	$('#modal-funcao').modal().find('.modal-title').text(titulo)
	$('.form-funcao').attr("action","/admin/cadastraFuncao");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-funcao').text("Salvar");
	$("#modal-funcao").modal("show");
});

$('.btn-editar-funcao').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-funcao').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-funcao').modal().find('.modal-title').text(titulo);
	$('.form-funcao').attr("action", href);
	$('#btn-salvar-funcao').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/funcao/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-funcao").modal("show");
});

$('.btn-excluir-funcao').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

/**
 * 
 * INFORMAÇÃOES ADICIONAR SERVIDOR NA MESA
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
 * 
 * **/
$('.btn-modal-adicionarServidorMesa').on("click", function(){
	var titulo = $('.btn-modal-adicionarServidorMesa').data("titulo");
	$('#modal-adicionarServidorMesa').modal().find('.modal-title').text(titulo)
	//$('.form-modal-adicionarServidorMesa').attr("action","/admin/cadastraCidade");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$('#btn-salvar-adicionarServidorMesa').text("Salvar");
	$("#modal-adicionarServidorMesa").modal("show");
});


/**
 * 
 * INFORMAÇÃOES SOBRE ADICIONAR MESA
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-servidorFuncao').on("click", function(){
	var titulo = $('.btn-nova-servidorFuncao').data("titulo");
	$('#modal-adicionarServidorMesa').modal().find('.modal-title').text(titulo)
	$('.form-servidorFuncao').attr("action","/diarioMesa/servidor");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-servidorFuncao').text("Salvar");
	$("#modal-servidorFuncao").modal("show");
});

$('.btn-editar-servidorFuncao').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-servidorFuncao').data("titulo");
	var id = this.id;
	var href = "/diarioMesa/servidor"+ $(this).attr('href');
	
	$('#modal-adicionarServidorMesa').modal().find('.modal-title').text(titulo);
	$('.form-adicionarServidorMesa').attr("action", href);
	$('#btn-salvar-adicionarServidorMesa').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/servidorfuncao/consultar"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-servidorFuncao").modal("show");
});

$('.btn-excluir-servidorFuncao').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

/**
 * 
 * INFORMAÇÃOES SOBRE TIPO DE SERVICO
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/

$('.btn-nova-tipoServico').on("click", function(){
	var titulo = $('.btn-nova-tipoServico').data("titulo");
	$('#modal-tipoServico').modal().find('.modal-title').text(titulo)
	$('.form-tipoServico').attr("action","/admin/cadastraTipoServico");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-tipoServico').text("Salvar");
	$("#modal-tipoServico").modal("show");
});

$('.btn-editar-tipoServico').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-tipoServico').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-tipoServico').modal().find('.modal-title').text(titulo);
	$('.form-tipoServico').attr("action", href);
	$('#btn-salvar-tipoServico').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/tipoServico/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-tipoServico").modal("show");
});

$('.btn-excluir-tipoServico').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

/**
 * 
 * INFORMAÇÃOES ADICIONAR SERVIDOR NA MESA
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
 * 
 * **/
$('.btn-modal-adicionarServidorTabela').on("click", function(){
	var titulo = $('.btn-modal-adicionarServidorTabela').data("titulo");
	$('#modal-adicionarServidorTabela').modal().find('.modal-title').text(titulo)
	//$('.form-modal-adicionarServidorMesa').attr("action","/admin/cadastraCidade");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$('#btn-salvar-adicionarServidorTabela').text("Salvar");
	$("#modal-adicionarServidorTabela").modal("show");
});

