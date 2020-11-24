/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de Mensagem
 */
//var dataAtual = Date.now();
//$('#dataCriacao').value( dataAtual.toISOString().slice(0,19));

//$.ajax({
//	type: "GET",
//	contentType: "application/json;charset=UTF-8",
//	url: "/admin/mensagens/nova/usuarios/0",
//	success: function(dados) {
//		for (var i = 0; i < dados.length; i++) {
//			var option = new Option(dados[i].servidor.nome.toUpperCase(), dados[i].servidor.id);
//			var select = document.getElementById("listaUsuario");
//
//			select.append(option);
//		}
//	},
//	fail: function() {
//		alert('Falha ao Buscar os Usuários');
//	}
//});

//$.ajax({
//	type: "GET",
//	contentType: "application/json;charset=UTF-8",
//	url: "/admin/mensagens/nova/grupos/",
//	success: function(dados) {
//		for (var i = 0; i < dados.length; i++) {
//			var option = new Option(dados[i].nome.toUpperCase(), dados[i].id);
//			var select = document.getElementById("listaGrupo");
//
//			select.append(option);
//		}
//	},
//	fail: function() {
//		alert('Falha ao Buscar os Grupos');
//	}
//});
//var selecionados = [];
//var gruposSelecionados = [];
//var usuariosAtivos = [];
//var aux1 = [];
//var aux2 = [];
//
//$('#listaUsuario').on("change", function() {
//	
//	
//	if(gruposSelecionados.length > 0){
//		//Selecionou um grupo
//		//E adicinou um usuario Manualmente
//		aux1 = $('#listaUsuario').val();
//		
//		//Recupera os Usuarios selecionados manualmente
//		selecionados = selecionados.concat(atualizaSelecionados(aux1, usuariosAtivos));	
//		
//		//Concatena Lista de UsuariosAtivos e Selecionados Manualmente
//		usuariosAtivos = aux1;
//	}else{
//		//Ainda não selecionou nenhum grupo
//		//Apenas seleção manual de usuários
//		selecionados = $('#listaUsuario').val();
//				
//		usuariosAtivos = selecionados;
//	}
//	///ordena as listas "elas são ordenadas como array de String"
//	selecionados.sort();
//	usuariosAtivos.sort();
//	
//});
//
//function atualizaSelecionados(array1, array2) {
//    var r1 = array1.filter(function (element, index, array) {
//        if(array2.indexOf(element) == -1)
//            return element;
//    });
//
//    return r1;
//}
//
//$('#listaGrupo').on("change", function() {
//	
//	gruposSelecionados = $('#listaGrupo').val().map(Number);
//	
//	usuariosAtivos = $('#listaUsuario').val();
//	
//	if(gruposSelecionados.length > 0){
//		$.ajax({
//			type: "GET",
//			contentType: "application/json;charset=UTF-8",
//			url: "/admin/mensagens/nova/usuarios/"+gruposSelecionados,
//			success: function(data) {
//				
//				var usuariosConsulta = [];
//				
//				//Limpa as seleções
//				$('#listaUsuario').selectpicker('deselect');
//				
//				for(var i = 0; i < data.length; i++) {
//					usuariosConsulta.push(""+data[i].servidor.id);
//				}
//								
//				//Atualiza a lista de usuários selecionados				
//				usuariosAtivos = selecionados.concat(usuariosConsulta);
//				
//				//Seleciona os usuarios no select
//				$('#listaUsuario').selectpicker('val',usuariosAtivos);
//			},
//			fail: function() {
//				alert('Falha ao Buscar os Usuários');
//				$('#listaUsuario').selectpicker('val',selecionados);
//			}
//		});
//	}else{
//		
//		var aux3 = $('#listaUsuario').val();
//		
//		selecionados = selecionados.splice(0,Number.MAX_VALUE, usuariosAtivos);
//		
//		//Atualização dos Selecionados
//		//Após nenhum grupo ser selecionado
//		if(usuariosAtivos > aux3){
//			selecionados = selecionados.concat(atualizaSelecionados(selecionados, atualizaSelecionados(usuariosAtivos, aux3)));
//		}else{
//			selecionados = selecionados.concat(atualizaSelecionados(aux3, usuariosAtivos));	
//		}
//		
//		usuariosAtivos = selecionados;
//
//		$('#listaUsuario').selectpicker('deselect');
//		$('#listaUsuario').selectpicker('val',usuariosAtivos);
//		
//		alert('Nenhum grupo selecionado');
//	}
//	$('#listaUsuario').selectpicker('refresh');
//	
//	///ordena as listas "elas são ordenadas como array de String"
//	selecionados.sort();
//	usuariosAtivos.sort();
//});

document.addEventListener('DOMContentLoaded', function(e) {
	tinymce.init({
		selector: 'textarea',
		language: 'pt_BR',
		width: '100%',
		entity_encoding: "raw",
		height: 230,
		encoding: 'raw',
		elementpath: false,
		statusbar: false,
		menubar: false,
		branding: false,
		plugins: 'print template advlist lists textcolor colorpicker textpattern paste',
		paste_as_text: true,
		advlist_bullet_styles: "default",
		advlist_number_styles: "default",
		toolbar: "undo redo | bold italic strikethrough | removeformat",
		setup: function(editor) {
			editor.on('keyup', function() {
				// Revalidate the comment field
				fv.revalidateField('#mensagem');
			});
		}
	});

	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});