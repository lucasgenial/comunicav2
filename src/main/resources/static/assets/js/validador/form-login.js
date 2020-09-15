/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de login
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(document.getElementById('loginform'),
    {
		fields : {
			login : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo Login não pode está em branco'
					}
				}
			},
			senha : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo Senha não pode está em branco'
					},
					numeric: {
                        message: 'Somente números'
					}
				}
			}
		}
	});
//	plugins: {
//        trigger: new FormValidation.plugins.Trigger(),
//        bootstrap: new FormValidation.plugins.Bootstrap(),
//        defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
//        submitButton: new FormValidation.plugins.SubmitButton()
//    },
});

function abrirmodal_alerta_paginainicial(){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-mensagem").modal();
}

function abrirmodal_alerta_paginainicial_com_tutorial(){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-alertaComTutorial").modal();
}

jQuery(function($){
	$("#cpf").mask("999.999.999-99");
	$("#login").mask("99.999.999");
	$("#matricula").mask("99.999.999");
});
