package br.com.cicom.comunicacicom.DSPrimary.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.criptografia.CriptografiaDeUrl;

public final class Formatador {

	@Autowired
	AuditoriaService serviceAuditoria;


	public String formataEmailParaResetDeSenha(String senha) {

		return "<!DOCTYPE HTML>\r\n" + "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"pt-br\">\r\n" + "<head>\r\n"
				+ "<!--[if gte mso 9]><xml>\r\n" + "		<o:OfficeDocumentSettings>\r\n" + "		<o:AllowPNG/>\r\n"
				+ "		<o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + "		</o:OfficeDocumentSettings>\r\n"
				+ "	</xml><![endif]-->\r\n" + "\r\n" + "<!-- fix outlook zooming on 120 DPI windows devices -->\r\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "<!-- So that mobile will display zoomed in -->\r\n"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "<!-- enable media queries for windows phone 8 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"date=no\">\r\n"
				+ "<!-- disable auto date linking in iOS 7-9 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"telephone=no\">\r\n"
				+ "<!-- disable auto telephone linking in iOS 7-9 -->\r\n" + "\r\n"
				+ "<title>Ocorrência nº 0129212</title>\r\n" + "\r\n" + "<style type=\"text/css\">\r\n" + "body {\r\n"
				+ "	background-color: #F0F0F0;\r\n" + "	margin: 0;\r\n" + "	padding: 0;\r\n"
				+ "	-ms-text-size-adjust: 100%;\r\n" + "	-webkit-text-size-adjust: 100%;\r\n"
				+ "	font-size: 12px;\r\n" + "	font-family: Helvetica, Arial, sans-serif;\r\n" + "}\r\n" + "\r\n"
				+ "table.principal {\r\n" + "	width: 100%;\r\n" + "	height: 100%\";\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "table.mensagem {\r\n" + "	width: 600px;\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "img {\r\n"
				+ "	-ms-interpolation-mode: bicubic;\r\n" + "}\r\n" + "\r\n" + "p {\r\n" + "	margin-top: 1px;\r\n"
				+ "	margin-bottom: 1px;\r\n" + "}\r\n" + "\r\n" + "h5 {\r\n" + "	margin: 0;\r\n"
				+ "	mso-line-height-rule: exactly;\r\n" + "	text-transform: uppercase;\r\n" + "	font-size: 12px;\r\n"
				+ "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "	text-align: center;\r\n"
				+ "	margin: 5px;\r\n" + "}\r\n" + "\r\n" + "b {\r\n" + "	font-size: 13px;\r\n"
				+ "	font-weight: 600;\r\n" + "}\r\n" + "\r\n" + ".container {\r\n" + "	width: 600px;\r\n"
				+ "	margin-top: 10px;\r\n" + "	background-color: #FFF;\r\n" + "}\r\n" + "\r\n" + ".borda {\r\n"
				+ "	border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + ".oc-title {\r\n"
				+ "	text-transform: uppercase;\r\n" + "	padding: 10px;\r\n" + "	background-color: #FFF;\r\n"
				+ "	font-size: 18px;\r\n" + "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "}\r\n" + "\r\n"
				+ ".oc-label {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #333333\r\n" + "}\r\n" + "\r\n"
				+ ".oc-atualizado {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 10px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #FF0000;\r\n"
				+ "	text-align: center;\r\n" + "}\r\n" + "\r\n" + ".oc-resp {\r\n" + "	text-transform: uppercase;\r\n"
				+ "	padding-left: 10px;\r\n" + "	font-weight: 500;\r\n" + "	line-height: 1.6;\r\n"
				+ "	font-size: 12px;\r\n" + "	line-height: 20px;\r\n" + "	text-align: left;\r\n"
				+ "	color: #333333\r\n" + "}\r\n" + "\r\n" + ".oc-footer {\r\n"
				+ "	font-family: Helvetica, Arial, sans-serif;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 16px;\r\n" + "	color: #aaaaaa;\r\n" + "	padding-left: 24px;\r\n"
				+ "	padding-right: 24px;\r\n" + "}\r\n" + "\r\n" + "img {\r\n" + "	height: auto !important;\r\n"
				+ "	max-width: 100% !important;\r\n" + "	width: auto !important;\r\n" + "}\r\n" + "\r\n"
				+ "@media only screen and ( max-width : 413px) {\r\n" + "	table.principal {\r\n"
				+ "		width: 100%;\r\n" + "		height: 100%\";\r\n" + "		border-spacing: 0;\r\n" + "	}\r\n"
				+ "	table.mensagem {\r\n" + "		width: 100%;\r\n" + "		height: 100%\";\r\n"
				+ "		border-spacing: 0;\r\n" + "	}\r\n" + "	.container {\r\n" + "		width: 100%;\r\n"
				+ "		margin-top: 10px;\r\n" + "		background-color: #FFF;\r\n" + "	}\r\n" + "	.esconder {\r\n"
				+ "		display: none;\r\n" + "		visibility: hidden;\r\n" + "	}\r\n" + "	img {\r\n"
				+ "		height: auto !important;\r\n" + "		max-width: 100% !important;\r\n"
				+ "		width: auto !important;\r\n" + "	}\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "\r\n"
				+ "<body style=\"background-color: #F0F0F0; margin-top: 0; margin-bottom: 0; margin-right: 0; margin-left: 0; padding-top: 0; padding-bottom: 0; padding-right: 0; padding-left: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; font-size: 12px; font-family: Helvetica, Arial, sans-serif;\">\r\n"
				+ "	<!-- 100% background wrapper (grey background) -->\r\n"
				+ "	<table class=\"principal\" align=\"center\" style=\"width: 100%; height: 100%'; border-spacing: 0;\">\r\n"
				+ "		<tr>\r\n" + "			<td align=\"center\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\"\r\n"
				+ "					style=\"margin-bottom: 3px; width: 600px; margin-top: 10px; background-color: #FFF;\">\r\n"
				+ "					<tr class=\"oc-cabecalho\">\r\n" + "						<td>\r\n"
				+ "							<table class=\"mensagem\" style=\"width: 600px; border-spacing: 0;\">\r\n"
				+ "								<tr align=\"left\">\r\n"
				+ "										<td align=\"left\" style=\"width: 50%; max-width: 50%;\">\r\n"
				+ "										<h5 style=\"margin-top: 5px; padding-left: 26px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">GOVERNO\r\n"
				+ "											DO ESTADO DA BAHIA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SECRETARIA\r\n"
				+ "											DA SEGURANÇA PÚBLICA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SUPERITENDÊNCIA\r\n"
				+ "											DE TELECOMUNICAÇÕES</h5>\r\n"
				+ "									</td>\r\n"
				+ "									<td class=\"esconder\" align=\"center\" style=\"width: 25%; max-width: 25%;\">\r\n"
				+ "										<img class=\"pattern\" src=\"https://docs.google.com/uc?id=1HQJkIH2lbMAY-i4a4huVOKXDCaSsNp2L\" alt=\"Spain\" width=\"185\" height=\"115\" style=\"-ms-interpolation-mode: bicubic;  margin-left: -47px; height: auto !important; overflow:hidden; max-width: 100% !important; width: auto !important;\" />\r\n"
				+ "									</td>\r\n" + "								</tr>\r\n"
				+ "							</table>\r\n" + "						</td>\r\n"
				+ "					</tr>\r\n" + "				</table>\r\n" + "			</td>\r\n"
				+ "		</tr>\r\n" +

				"		<tr>\r\n" + "			<td align=\"left\" colspan=\"3\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\" style=\"margin-top: 0px; margin-bottom: 0px; width: 600px; background-color: #FFF;\">\r\n"
				+ "					<tr>\r\n"
				+ "						<td class=\"oc-title\" align=\"left\"  padding-top: 10px; padding-bottom: 10px; padding-right: 10px; padding-left: 10px; background-color: #FFF; font-size: 18px; font-weight: 600; color: #374550;\" colspan=\"3\">\r\n"
				+ "							<p style=\"margin-top: 1px; margin-bottom: 1px; \">Sua nova senha de acesso ao Comunica é: "
				+ " </p>\r\n"
				+ "							<p style=\"margin-top: 1px; margin-bottom: 1px; text-transform: lowercase;\"> "
				+ senha + "</p>\r\n"
				+ "							<p class=\"oc-atualizado\" style=\"margin-top: 1px; margin-bottom: 1px; text-transform: uppercase; padding-left: 10px; font-weight: 600; line-height: 20px; font-size: 10px; text-align: center; color: #FF0000;\">Mensagem gerada automaticamente!"
				+ "</p>\r\n" + "						</td>\r\n" + "					</tr>\r\n"
				+ "				</table>\r\n" + "			</td>\r\n" + "		</tr>\r\n" + "	</table>\r\n"
				+ "	<!--/100% background wrapper-->\r\n" + "</body>\r\n" + "</html>\r\n" + "";
	}

	public List<Ocorrencia> formataParaListaDeOcorrencias(List<String> listaDeIds,
			OcorrenciaService serviceOcorrencia) {
		List<Ocorrencia> ListaOcorrencias = new ArrayList<>();

		for (int i = 0; i < listaDeIds.size(); i++) {
			ListaOcorrencias.add(serviceOcorrencia.buscaPorId(Long.parseLong(listaDeIds.get(i))).get());
		}
		return ListaOcorrencias;
	}


	public String formataParaEmail(Ocorrencia ocorrencia) {

		return "<!DOCTYPE HTML>\r\n" + "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"pt-br\">\r\n" + "<head>\r\n"
				+ "<!--[if gte mso 9]><xml>\r\n" + "		<o:OfficeDocumentSettings>\r\n" + "		<o:AllowPNG/>\r\n"
				+ "		<o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + "		</o:OfficeDocumentSettings>\r\n"
				+ "	</xml><![endif]-->\r\n" + "\r\n" + "<!-- fix outlook zooming on 120 DPI windows devices -->\r\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "<!-- So that mobile will display zoomed in -->\r\n"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "<!-- enable media queries for windows phone 8 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"date=no\">\r\n"
				+ "<!-- disable auto date linking in iOS 7-9 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"telephone=no\">\r\n"
				+ "<!-- disable auto telephone linking in iOS 7-9 -->\r\n" + "\r\n"
				+ "<title>Ocorrência nº 0129212</title>\r\n" + "\r\n" + "<style type=\"text/css\">\r\n" + "body {\r\n"
				+ "	background-color: #F0F0F0;\r\n" + "	margin: 0;\r\n" + "	padding: 0;\r\n"
				+ "	-ms-text-size-adjust: 100%;\r\n" + "	-webkit-text-size-adjust: 100%;\r\n"
				+ "	font-size: 12px;\r\n" + "	font-family: Helvetica, Arial, sans-serif;\r\n" + "}\r\n" + "\r\n"
				+ "table.principal {\r\n" + "	width: 100%;\r\n" + "	height: 100%\";\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "table.mensagem {\r\n" + "	width: 600px;\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "img {\r\n"
				+ "	-ms-interpolation-mode: bicubic;\r\n" + "}\r\n" + "\r\n" + "p {\r\n" + "	margin-top: 1px;\r\n"
				+ "	margin-bottom: 1px;\r\n" + "}\r\n" + "\r\n" + "h5 {\r\n" + "	margin: 0;\r\n"
				+ "	mso-line-height-rule: exactly;\r\n" + "	text-transform: uppercase;\r\n" + "	font-size: 12px;\r\n"
				+ "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "	text-align: center;\r\n"
				+ "	margin: 5px;\r\n" + "}\r\n" + "\r\n" + "b {\r\n" + "	font-size: 13px;\r\n"
				+ "	font-weight: 600;\r\n" + "}\r\n" + "\r\n" + ".container {\r\n" + "	width: 600px;\r\n"
				+ "	margin-top: 10px;\r\n" + "	background-color: #FFF;\r\n" + "}\r\n" + "\r\n" + ".borda {\r\n"
				+ "	border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + ".oc-title {\r\n"
				+ "	text-transform: uppercase;\r\n" + "	padding: 10px;\r\n" + "	background-color: #FFF;\r\n"
				+ "	font-size: 18px;\r\n" + "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "}\r\n" + "\r\n"
				+ ".oc-label {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #333333\r\n" + "}\r\n" + "\r\n"
				+ ".oc-atualizado {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 10px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #FF0000;\r\n"
				+ "	text-align: center;\r\n" + "}\r\n" + "\r\n" + ".oc-resp {\r\n" + "	text-transform: uppercase;\r\n"
				+ "	padding-left: 10px;\r\n" + "	font-weight: 500;\r\n" + "	line-height: 1.6;\r\n"
				+ "	font-size: 12px;\r\n" + "	line-height: 20px;\r\n" + "	text-align: left;\r\n"
				+ "	color: #333333\r\n" + "}\r\n" + "\r\n" + ".oc-footer {\r\n"
				+ "	font-family: Helvetica, Arial, sans-serif;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 16px;\r\n" + "	color: #aaaaaa;\r\n" + "	padding-left: 24px;\r\n"
				+ "	padding-right: 24px;\r\n" + "}\r\n" + "\r\n" + "img {\r\n" + "	height: auto !important;\r\n"
				+ "	max-width: 100% !important;\r\n" + "	width: auto !important;\r\n" + "}\r\n" + "\r\n"
				+ "@media only screen and ( max-width : 413px) {\r\n" + "	table.principal {\r\n"
				+ "		width: 100%;\r\n" + "		height: 100%\";\r\n" + "		border-spacing: 0;\r\n" + "	}\r\n"
				+ "	table.mensagem {\r\n" + "		width: 100%;\r\n" + "		height: 100%\";\r\n"
				+ "		border-spacing: 0;\r\n" + "	}\r\n" + "	.container {\r\n" + "		width: 100%;\r\n"
				+ "		margin-top: 10px;\r\n" + "		background-color: #FFF;\r\n" + "	}\r\n" + "	.esconder {\r\n"
				+ "		display: none;\r\n" + "		visibility: hidden;\r\n" + "	}\r\n" + "	img {\r\n"
				+ "		height: auto !important;\r\n" + "		max-width: 100% !important;\r\n"
				+ "		width: auto !important;\r\n" + "	}\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "\r\n"
				+ "<body style=\"background-color: #F0F0F0; margin-top: 0; margin-bottom: 0; margin-right: 0; margin-left: 0; padding-top: 0; padding-bottom: 0; padding-right: 0; padding-left: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; font-size: 12px; font-family: Helvetica, Arial, sans-serif;\">\r\n"
				+ "	<!-- 100% background wrapper (grey background) -->\r\n"
				+ "	<table class=\"principal\" align=\"center\" style=\"width: 100%; height: 100%'; border-spacing: 0;\">\r\n"
				+ "		<tr>\r\n" + "			<td align=\"center\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\"\r\n"
				+ "					style=\"margin-bottom: 3px; width: 600px; margin-top: 10px; background-color: #FFF;\">\r\n"
				+ "					<tr class=\"oc-cabecalho\">\r\n" + "						<td>\r\n"
				+ "							<table class=\"mensagem\" style=\"width: 600px; border-spacing: 0;\">\r\n"
				+ "								<tr align=\"left\">\r\n"
				+ "										<td align=\"left\" style=\"width: 50%; max-width: 50%;\">\r\n"
				+ "										<h5 style=\"margin-top: 5px; padding-left: 26px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">GOVERNO\r\n"
				+ "											DO ESTADO DA BAHIA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SECRETARIA\r\n"
				+ "											DA SEGURANÇA PÚBLICA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SUPERINTENDÊNCIA\r\n"
				+ "											DE TELECOMUNICAÇÕES</h5>\r\n"
				+ "									</td>\r\n"
				+ "									<td class=\"esconder\" align=\"center\" style=\"width: 25%; max-width: 25%;\">\r\n"
				+ "										<img class=\"pattern\" src=\"https://docs.google.com/uc?id=1HQJkIH2lbMAY-i4a4huVOKXDCaSsNp2L\" alt=\"Spain\" width=\"185\" height=\"115\" style=\"-ms-interpolation-mode: bicubic;  margin-left: -47px; height: auto !important; overflow:hidden; max-width: 100% !important; width: auto !important;\" />\r\n"
				+ "									</td>\r\n" + "								</tr>\r\n"
				+ "							</table>\r\n" + "						</td>\r\n"
				+ "					</tr>\r\n" + "				</table>\r\n" + "			</td>\r\n"
				+ "		</tr>\r\n" +

				"		<tr>\r\n" + "			<td align=\"left\" colspan=\"3\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\" style=\"margin-top: 0px; margin-bottom: 0px; width: 600px; background-color: #FFF;\">\r\n"
				+ "					<tr>\r\n"
				+ "						<td class=\"oc-title\" align=\"left\" style=\"text-transform: uppercase; padding-top: 10px; padding-bottom: 10px; padding-right: 10px; padding-left: 10px; background-color: #FFF; font-size: 18px; font-weight: 600; color: #374550;\" colspan=\"3\">\r\n"
				+ "							<p class=\"title\" style=\"margin-top: 1px; margin-bottom: 1px;\">Ocorrência nº "
				+ ocorrencia.getSic() + "</p>\r\n"
				+ "							<p class=\"oc-atualizado\" style=\"margin-top: 1px; margin-bottom: 1px; text-transform: uppercase; padding-left: 10px; font-weight: 600; line-height: 20px; font-size: 10px; text-align: center; color: #FF0000;\">ATUALIZADO EM "
				+ ocorrencia.getDataUltimaModificao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"))
				+ "</p>\r\n" + "						</td>\r\n" + "					</tr>\r\n"
				+ "				</table>\r\n" + "			</td>\r\n" + "		</tr>\r\n" + "	</table>\r\n"
				+ "	<!--/100% background wrapper-->\r\n" + "</body>\r\n" + "</html>\r\n" + "";
	}

	public String formataParaEmail(List<String> lista, OcorrenciaService serviceOcorrencia) {
		@SuppressWarnings("unused")
		String a;

		String descricoes = new String("");
		for (int i = 0; i < lista.size(); i++) {

			descricoes += formataParaEmail(serviceOcorrencia.buscaPorId(Long.parseLong(lista.get(i))).get());

			if (i != lista.size()) {
				descricoes += "\n";
			}
		}
		return descricoes;
	}
	
	public String formataParaHtml(Ocorrencia ocorrencia) {
		String descricao = ocorrencia.getDescricao().replaceAll("<p>", "");
		descricao = descricao.replaceAll("</p>", "");

		String historico = ocorrencia.getHistorico().replaceAll("<p>", "");
		historico = historico.replaceAll("</p>", "");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
		String datahorastr = LocalDateTime.now().format(formatter); 

		
		return  "<div class=\"container-fluid\">\r\n" + 
				"			<!-- INICIO TITULO DA PÁGINA -->\r\n" + 
				"			<div class=\"row container-fluid\">\r\n" + 
				"				<div class=\"col-md-5 align-self-center\">\r\n" + 
				"				    <h4 class=\"text-themecolor\"></h4>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"col-md-7 align-self-center text-right\">\r\n" + 
				"				    <div class=\"d-flex justify-content-end align-items-center\">\r\n" + 
				"				        <ol class=\"breadcrumb\">\r\n" + 
				"				            <!-- <li class=\"breadcrumb-item\"><a href=\"javascript:void(0)\">Home</a></li>\r\n" + 
				"				            <li class=\"breadcrumb-item\"><a href=\"javascript:void(0)\">Servidores</a></li>\r\n" + 
				"				            <li class=\"breadcrumb-item active\">Vizualizar Servidor</li> -->\r\n" + 
				"				        </ol>\r\n" + 
				"				    </div>\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"pt-5 mb-2 pb-5 row\" style=\"border:2px solid #333;\">"
				+ "				<div class=\"container-fluid\" style=\"padding-top: 33px;\">\r\n" + 
				"					<div class=\"form-row ml-2 mr-2 justify-content-between\" style=\"border:2px solid #333;\">					\r\n" + 
				"<!-- 						<div class=\"col-md-5 ml-3 mt-3 mb-2 h6\" >\r\n" + 
				" -->						<div class=\"col-md-7 ml-3 mt-3 mb-2 h6\" >\r\n" + 
				"							<label class=\"font-weight-bold\">GOVERNO DO ESTADO DA BAHIA</label>\r\n" + 
				"							<label class=\"font-weight-bold\">SECRETARIA DA SEGURANÇA PÚBLICA</label>\r\n" + 
				"							<label class=\"font-weight-bold\">SUPERINTENDÊNCIA DE TELECOMUNICAÇÕES</label>\r\n" + 
				"						</div>\r\n" + 
				"							<div class=\"col-md-3 imagem-logoCicomVisualizacao\"></div>\r\n" + 
				"					</div>\r\n" + 
				"					<br/>\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2\" style=\"height: 22px; \">\r\n" + 
				"					<div class=\"col-md-12 text-center border-bottom-0\" style=\"border:2px solid #333; \">\r\n" + 
				"						<label class=\"row \">\r\n" + 
				"							<font class=\"col-7 font-weight-bold text-right\">Ocorrência</font>\r\n" + 
				"							<font class=\"col-5 text-right\">Gerado em:&emsp;"+datahorastr+"</font>\r\n" + 
				"						</label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mt-0 mr-2\" style=\"border:2px solid #333;\">					\r\n" + 
				"					<div class=\"form-row ml-3 \">\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label style=\"margin-bottom: 5px; margin-top:5px;\"><font class=\"font-weight-bold\">N°:&nbsp; </font><font>"+ocorrencia.getSic()+"</font></label>\r\n" + 
				"						</div>	\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label style=\"margin-bottom: 5px; margin-top:5px;\"><font class=\"font-weight-bold\">DATA/HORA:&nbsp; </font>"+ ocorrencia.getDataOcorrencia().format(formatter) +"</label>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label style=\"margin-bottom: 5px; margin-top:5px;\"><font class=\"font-weight-bold\">TIPIFICAÇÃO:&nbsp; </font><font>"+ocorrencia.getTipificacao().getNome()+"</font></label>\r\n" + 
				"						</div>											\r\n" + 
				"					</div>				\r\n" + 
				"				</div><!-- DIV GERADOR USUSARIO -->\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2 mt-3 \">\r\n" + 
				"					<div class=\"col-md-12 text-center border-bottom-0\" style=\"border:2px solid #333;\">\r\n" + 
				"						<label style=\"margin-bottom: 0px;\"><font class=\"font-weight-bold\">Localização&nbsp;</font></label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2\" style=\"border:2px solid #333;\">					\r\n" + 
				"					<div class=\"form-row ml-3 mt-2\">\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label><font class=\"font-weight-bold\">CIDADE:&nbsp; </font><font>"+ocorrencia.getEndereco().getCidade().getNome()+"</font></label>\r\n" + 
				"						</div>	\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label ><font class=\"font-weight-bold\">LOCALIDADE/DISTRITO:&nbsp; </font><font>"+ocorrencia.getEndereco().getBairro().getLocalidade().getNome()+"</font></label>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label><font class=\"font-weight-bold\">BAIRRO:&nbsp; </font><font>"+ocorrencia.getEndereco().getBairro().getNome()+"</font></label>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"col-md-6\">\r\n" + 
				"							<label ><font class=\"font-weight-bold\">RUA:&nbsp; </font><font>"+ocorrencia.getEndereco().getRua()+"</font></label>\r\n" + 
				"						</div>\r\n" + 
				"														\r\n" + 
				"						<!-- <div class=\"col-md-6\">\r\n" + 
				"							<label><font class=\"font-weight-bold\">N°:&nbsp; </font><font>"+ocorrencia.getEndereco().getNumero()+"</font></label>\r\n" + 
				"						</div> -->\r\n" + 
				"						<div class=\"col-md-12\">\r\n" + 
				"							<label><font class=\"font-weight-bold\">REFERÊNCIA:&nbsp; </font><font>"+ocorrencia.getEndereco().getReferencia()+"</font></label>\r\n" + 
				"						</div>\r\n" + 
				"						<div class=\"col-md-12\">\r\n" + 
				"							<label><font class=\"font-weight-bold\">VIATURA/GUARNIÇÃO:&nbsp; </font><font>"+ocorrencia.getGuarnicao()+"</font></label>\r\n" + 
				"						</div>										\r\n" + 
				"					</div>				\r\n" + 
				"				</div><!-- DIV LOCALIZAÇÃO -->\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2 mt-3 \">\r\n" + 
				"					<div class=\"col-md-12 text-center border-bottom-0\" style=\"border:2px solid #333;\">\r\n" + 
				"						<label style=\"margin-bottom: 0px;\"><font class=\"font-weight-bold\">DESCRIÇÃO DO FATO&nbsp;</font></label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2\" style=\"border:2px solid #333;\">\r\n" + 
				"					<div class=\"form-row ml-2 mr-2 mt-2 text-justify\">\r\n" + 
				"						<label \"style=\"margin-bottom: 3px; margin-top:3px;margin-right: 6px;\">"+descricao+"</label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2 mt-3\">\r\n" + 
				"					<div class=\"col-md-12 text-center border-bottom-0\" style=\"border:2px solid #333;\">\r\n" + 
				"						<label style=\"margin-bottom: 0px;\"><font class=\"font-weight-bold\">HISTÓRICO&nbsp;</font></label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"collapse show ml-2 mr-2\" style=\"border:2px solid #333;\">\r\n" + 
				"					<div class=\"form-row ml-2 mr-2 mt-2 text-justify\">\r\n" + 
				"						<label style=\"margin-bottom: 3px; margin-top:3px;margin-right: 6px;\">"+historico+"</label>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"				\r\n" + 
				"				</div>"
				+ ""
				+ "</div><!-- ROW -->\r\n" + 
				"		   \r\n" + 
				"			\r\n" + 
				"		</div><!-- div do cabeçalho -->\r\n";
		
	}
	
	public String limpaFormatacao(String texto) {
		texto = texto.replaceAll(" <strong>", "<b> ");
		texto = texto.replaceAll("<strong>", "<b>");
		texto = texto.replaceAll(" </strong>", "</b> ");
		texto = texto.replaceAll("</strong>", "</b>");
		texto = texto.replaceAll(" <em>", "<i> ");
		texto = texto.replaceAll("<em>", "<i>");
		texto = texto.replaceAll(" </em>", "</i> ");
		texto = texto.replaceAll("</em>", "</i>");

		return texto;
	}

	public String formataEspaco(String texto, String formatador) {

		if (texto.contains("/") && texto.contains(" ")) {
			formatador = formatador + " ";
		} else if (!texto.contains("/") && texto.contains(" ")) {
			formatador = " " + formatador;
		}

		return formatador;
	}

	public String converteHtmlParaTxt(String html) {

		html = html.replaceAll("\n", "%0A");
		html = html.replaceAll("-: CLEANED :-", "");

		Pattern pattern = Pattern.compile("(\\s+)?<\\/([^>]*>)|<([^>]*>)(\\s+)?");

		Matcher matcher = pattern.matcher(html);

		while (matcher.find()) {

			if (matcher.group().contains("strong")) {

				html = html.replace(matcher.group(), this.formataEspaco(matcher.group(), "*"));

			} else if (matcher.group().contains("span")) {

				html = html.replace(matcher.group(), this.formataEspaco(matcher.group(), "~"));
			} else if (matcher.group().contains("em")) {

				html = html.replace(matcher.group(), this.formataEspaco(matcher.group(), "_"));

			} else if (matcher.group().contains("p")) {

				html = html.replace(matcher.group(), this.formataEspaco(matcher.group(), ""));

			} else {

				html = html.replace(matcher.group(), this.formataEspaco(matcher.group(), ""));
			}
		}
		
		return html;
	}

	public String formataParaWhatsapp(Ocorrencia ocorrencia, Usuario user) {

		String descricao = ocorrencia.getDescricao();
		String historico = ocorrencia.getHistorico();

		descricao = this.converteHtmlParaTxt(descricao);
		historico = this.converteHtmlParaTxt(historico);
		
		DateTimeFormatter dformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		DateTimeFormatter dhformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String EstabelecimentoCab = "*CICOM/" + ocorrencia.getEstabelecimento().getNome().toString() + "*%0A%0A";

		return EstabelecimentoCab + "*Nº:* " + ocorrencia.getSic() + "%0A" + "*ID:* 0000" + user.getId()
				+ " Gerado em: " + LocalDateTime.now().format(dhformat) + "%0A" + "*FATO/OCORRÊNCIA:* "
				+ ocorrencia.getTipificacao().getNome() + "%0A" + "*DATA/HORA:* "
				+ ocorrencia.getDataOcorrencia().format(dformat) + "%0A" + "*VIATURA/GUARNIÇÃO:* "
				+ ocorrencia.getGuarnicao() + "%0A" + "*CIDADE:* " + ocorrencia.getEndereco().getCidade().getNome()
				+ "%0A" + "*LOCALIDADE:* " + ocorrencia.getEndereco().getBairro().getLocalidade().getNome() + " */* "
				+ ocorrencia.getEndereco().getBairro().getNome() + "%0A" + "*RUA:* " + ocorrencia.getEndereco().getRua()
				+ ". *Nº:* " + ocorrencia.getEndereco().getNumero() + "%0A" + "*REFERÊNCIA:* "
				+ ocorrencia.getEndereco().getReferencia() + "%0A %0A" + "*DESCRIÇÃO DO FATO:* %0A" + descricao.replaceAll("&", " %26")
				+ "%0A %0A" + "*HISTÓRICO:* %0A" + historico.replaceAll("&", "	%26") + "%0A";
	}
	
	/* INCLUSÃO DO FORMATADOR PARA ENVIO VIA LINK*/
	public String formataParaWhatsappViaLink(Ocorrencia ocorrencia, Usuario user) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH");
		String datahorastr = LocalDateTime.now().format(formatter); 		
		datahorastr = datahorastr.replace("-", "").replace(" ", "");
		String dataCriptografada = CriptografiaDeUrl.criptografarNumerosString(datahorastr);
		String idCriptografado 	 = CriptografiaDeUrl.criptografarNumerosString(user.getId().toString());
		
		String EstabelecimentoCab = "*CICOM/" + ocorrencia.getEstabelecimento().getNome().toString() + "*%0A%0A";
		
		return    "*CIDADE:* " + ocorrencia.getEndereco().getCidade().getNome()+ "%0A"
				+ "*FATO/OCORRÊNCIA:* "+ ocorrencia.getTipificacao().getNome() + "%0A" 
				+ "*ACESSE:* "+ CriptografiaDeUrl.criptografarUrl("http://comunica.stelecom.ba.gov.br/admin/ocorrencias/visualizar/externo/"+idCriptografado+"/"+dataCriptografada+"/"+ocorrencia.getId())+"%0A"
				+ "*FONTE:* "+EstabelecimentoCab;
		
				
	}
	
	
	/* ***************************************** */
	
	public String formataParaWhatsappComHTML(Ocorrencia ocorrencia, Usuario user) {

		String descricao = ocorrencia.getDescricao();
		String historico = ocorrencia.getHistorico();

		descricao = this.converteHtmlParaTxt(descricao);
		historico = this.converteHtmlParaTxt(historico);
		

		DateTimeFormatter dformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//		DateTimeFormatter hformat = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dhformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		String EstabelecimentoCab = "<font  class='font-weight-bold' >CICOM/" + ocorrencia.getEstabelecimento().getNome()+"</font><br><br>";

		return  EstabelecimentoCab + "<font  class='font-weight-bold' >Nº:</font> " + ocorrencia.getSic() + "<br>" + "<font  class='font-weight-bold' >ID:</font> 0000" + user.getId()
				+ " Gerado em: " + LocalDateTime.now().format(dhformat) + "<br>" + "<font  class='font-weight-bold' >FATO/OCORRÊNCIA:</font> "
				+ ocorrencia.getTipificacao().getNome() + "<br>" + "<font  class='font-weight-bold' >DATA/HORA:</font> "
				+ ocorrencia.getDataOcorrencia().format(dformat) + "<br>" + "<font  class='font-weight-bold' >VIATURA/GUARNIÇÃO:</font> "
				+ ocorrencia.getGuarnicao() + "<br>" + "<font  class='font-weight-bold' >CIDADE:</font> " + ocorrencia.getEndereco().getCidade().getNome()
				+ "<br>" + "<font  class='font-weight-bold' >LOCALIDADE:</font> " + ocorrencia.getEndereco().getBairro().getLocalidade().getNome() + " <font  class='font-weight-bold' >/</font> "
				+ ocorrencia.getEndereco().getBairro().getNome() + "<br>" + "<font  class='font-weight-bold' >RUA:</font> " + ocorrencia.getEndereco().getRua()
				+ ". <font  class='font-weight-bold' >Nº:</font> " + ocorrencia.getEndereco().getNumero() + "<br>" + "<font  class='font-weight-bold' >REFERÊNCIA:</font> "
				+ ocorrencia.getEndereco().getReferencia() + "<br> <br>" + "<font  class='font-weight-bold' >DESCRIÇÃO DO FATO:</font> <br>" + descricao
				+ "<br> <br>" + "<font  class='font-weight-bold' >HISTÓRICO:</font> <br>" + historico + "<br>";
	}

	
	public String listaDeEmails(List<String> lista, EmailService serviceEmail) {
		String emails = new String("");
		for (int i = 0; i < lista.size(); i++) {
			emails += serviceEmail.buscaPorId(Long.parseLong(lista.get(i))).get().getEnderecoDeEmail();
			if (i != lista.size()) {
				emails += ",";
			}
		}
		return emails;
	}
	
	
	/**
	 * FORMATADOR DE ENVIO DE EMAIL PARA O SUPORTE
	 */
	
	public String formataEmailSuporte(String nome, String email, String estabelecimento, String mensagem) {

		return "<!DOCTYPE HTML>\r\n" + "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"pt-br\">\r\n" + "<head>\r\n"
				+ "<!--[if gte mso 9]><xml>\r\n" + "		<o:OfficeDocumentSettings>\r\n" + "		<o:AllowPNG/>\r\n"
				+ "		<o:PixelsPerInch>96</o:PixelsPerInch>\r\n" + "		</o:OfficeDocumentSettings>\r\n"
				+ "	</xml><![endif]-->\r\n" + "\r\n" + "<!-- fix outlook zooming on 120 DPI windows devices -->\r\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "<!-- So that mobile will display zoomed in -->\r\n"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "<!-- enable media queries for windows phone 8 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"date=no\">\r\n"
				+ "<!-- disable auto date linking in iOS 7-9 -->\r\n"
				+ "<meta name=\"format-detection\" content=\"telephone=no\">\r\n"
				+ "<!-- disable auto telephone linking in iOS 7-9 -->\r\n" + "\r\n"
				+ "<title>Contato</title>\r\n" + "\r\n" + "<style type=\"text/css\">\r\n" + "body {\r\n"
				+ "	background-color: #F0F0F0;\r\n" + "	margin: 0;\r\n" + "	padding: 0;\r\n"
				+ "	-ms-text-size-adjust: 100%;\r\n" + "	-webkit-text-size-adjust: 100%;\r\n"
				+ "	font-size: 12px;\r\n" + "	font-family: Helvetica, Arial, sans-serif;\r\n" + "}\r\n" + "\r\n"
				+ "table.principal {\r\n" + "	width: 100%;\r\n" + "	height: 100%\";\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "table.mensagem {\r\n" + "	width: 600px;\r\n"
				+ "	border-spacing: 0;\r\n" + "}\r\n" + "\r\n" + "img {\r\n"
				+ "	-ms-interpolation-mode: bicubic;\r\n" + "}\r\n" + "\r\n" + "p {\r\n" + "	margin-top: 1px;\r\n"
				+ "	margin-bottom: 1px;\r\n" + "}\r\n" + "\r\n" + "h5 {\r\n" + "	margin: 0;\r\n"
				+ "	mso-line-height-rule: exactly;\r\n" + "	text-transform: uppercase;\r\n" + "	font-size: 12px;\r\n"
				+ "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "	text-align: center;\r\n"
				+ "	margin: 5px;\r\n" + "}\r\n" + "\r\n" + "b {\r\n" + "	font-size: 13px;\r\n"
				+ "	font-weight: 600;\r\n" + "}\r\n" + "\r\n" + ".container {\r\n" + "	width: 600px;\r\n"
				+ "	margin-top: 10px;\r\n" + "	background-color: #FFF;\r\n" + "}\r\n" + "\r\n" + ".borda {\r\n"
				+ "	border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + ".oc-title {\r\n"
				+ "	text-transform: uppercase;\r\n" + "	padding: 10px;\r\n" + "	background-color: #FFF;\r\n"
				+ "	font-size: 18px;\r\n" + "	font-weight: 600;\r\n" + "	color: #374550;\r\n" + "}\r\n" + "\r\n"
				+ ".oc-label {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #333333\r\n" + "}\r\n" + "\r\n"
				+ ".oc-atualizado {\r\n" + "	text-transform: uppercase;\r\n" + "	padding-left: 10px;\r\n"
				+ "	font-weight: 600;\r\n" + "	line-height: 1.6;\r\n" + "	font-size: 10px;\r\n"
				+ "	line-height: 20px;\r\n" + "	text-align: left;\r\n" + "	color: #FF0000;\r\n"
				+ "	text-align: center;\r\n" + "}\r\n" + "\r\n" + ".oc-resp {\r\n" + "	text-transform: uppercase;\r\n"
				+ "	padding-left: 10px;\r\n" + "	font-weight: 500;\r\n" + "	line-height: 1.6;\r\n"
				+ "	font-size: 12px;\r\n" + "	line-height: 20px;\r\n" + "	text-align: left;\r\n"
				+ "	color: #333333\r\n" + "}\r\n" + "\r\n" + ".oc-footer {\r\n"
				+ "	font-family: Helvetica, Arial, sans-serif;\r\n" + "	font-size: 12px;\r\n"
				+ "	line-height: 16px;\r\n" + "	color: #aaaaaa;\r\n" + "	padding-left: 24px;\r\n"
				+ "	padding-right: 24px;\r\n" + "}\r\n" + "\r\n" + "img {\r\n" + "	height: auto !important;\r\n"
				+ "	max-width: 100% !important;\r\n" + "	width: auto !important;\r\n" + "}\r\n" + "\r\n"
				+ "@media only screen and ( max-width : 413px) {\r\n" + "	table.principal {\r\n"
				+ "		width: 100%;\r\n" + "		height: 100%\";\r\n" + "		border-spacing: 0;\r\n" + "	}\r\n"
				+ "	table.mensagem {\r\n" + "		width: 100%;\r\n" + "		height: 100%\";\r\n"
				+ "		border-spacing: 0;\r\n" + "	}\r\n" + "	.container {\r\n" + "		width: 100%;\r\n"
				+ "		margin-top: 10px;\r\n" + "		background-color: #FFF;\r\n" + "	}\r\n" + "	.esconder {\r\n"
				+ "		display: none;\r\n" + "		visibility: hidden;\r\n" + "	}\r\n" + "	img {\r\n"
				+ "		height: auto !important;\r\n" + "		max-width: 100% !important;\r\n"
				+ "		width: auto !important;\r\n" + "	}\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "\r\n"
				+ "<body style=\"background-color: #F0F0F0; margin-top: 0; margin-bottom: 0; margin-right: 0; margin-left: 0; padding-top: 0; padding-bottom: 0; padding-right: 0; padding-left: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; font-size: 12px; font-family: Helvetica, Arial, sans-serif;\">\r\n"
				+ "	<!-- 100% background wrapper (grey background) -->\r\n"
				+ "	<table class=\"principal\" align=\"center\" style=\"width: 100%; height: 100%'; border-spacing: 0;\">\r\n"
				+ "		<tr>\r\n" + "			<td align=\"center\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\"\r\n"
				+ "					style=\"margin-bottom: 3px; width: 600px; margin-top: 10px; background-color: #FFF;\">\r\n"
				+ "					<tr class=\"oc-cabecalho\">\r\n" + "						<td>\r\n"
				+ "							<table class=\"mensagem\" style=\"width: 600px; border-spacing: 0;\">\r\n"
				+ "								<tr align=\"left\">\r\n"
				+ "										<td align=\"left\" style=\"width: 50%; max-width: 50%;\">\r\n"
				+ "										<h5 style=\"margin-top: 5px; padding-left: 26px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">GOVERNO\r\n"
				+ "											DO ESTADO DA BAHIA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SECRETARIA\r\n"
				+ "											DA SEGURANÇA PÚBLICA</h5>\r\n"
				+ "										<h5 style=\"margin-top: 5px; margin-bottom: 5px; margin-right: 5px; margin-left: 5px; mso-line-height-rule: exactly; text-transform: uppercase; font-size: 12px; font-weight: 600; color: #374550; text-align: left;\">SUPERINTENDÊNCIA\r\n"
				+ "											DE TELECOMUNICAÇÕES</h5>\r\n"
				+ "									</td>\r\n"
				+ "									<td class=\"esconder\" align=\"center\" style=\"width: 25%; max-width: 25%;\">\r\n"
				+ "										<img class=\"pattern\" src=\"https://docs.google.com/uc?id=1HQJkIH2lbMAY-i4a4huVOKXDCaSsNp2L\" alt=\"Spain\" width=\"185\" height=\"115\" style=\"-ms-interpolation-mode: bicubic;  margin-left: -47px; height: auto !important; overflow:hidden; max-width: 100% !important; width: auto !important;\" />\r\n"
				+ "									</td>\r\n" + "								</tr>\r\n"
				+ "							</table>\r\n" + "						</td>\r\n"
				+ "					</tr>\r\n" + "				</table>\r\n" + "			</td>\r\n"
				+ "		</tr>\r\n" +

				"		<tr>\r\n" + "			<td align=\"left\" colspan=\"3\">\r\n"
				+ "				<!-- 600px container (white background) -->\r\n"
				+ "				<table class=\"container\" style=\"margin-top: 0px; margin-bottom: 0px; width: 600px; background-color: #FFF;\">\r\n"
				+ "					<tr>\r\n"
				+ "						<td class=\"oc-title\" align=\"left\" style=\"padding-top: 10px; padding-bottom: 10px; padding-right: 10px; padding-left: 10px; background-color: #FFF; font-size: 18px; font-weight: 600; color: #374550;\" colspan=\"3\">\r\n"
				+ "							<p class=\"title\" style=\"margin-top: 1px; margin-bottom: 1px;\">Nome: "
				+ nome + "</p>\r\n"
				+ "							<p class=\"title\" style=\"margin-top: 1px; margin-bottom: 1px;\">Email: "
				+ email + "</p>\r\n"
				+ "							<p class=\"title\" style=\"margin-top: 1px; margin-bottom: 1px;\">Estabelecimento: "
				+ estabelecimento + "</p>\r\n"
				+ "							<p class=\"oc-atualizado\" style=\"margin-top: 1px; margin-bottom: 1px; padding-left: 10px; font-weight: 600; line-height: 20px; font-size: 10px; text-align: center; color: #FF0000;\">Mensagem: "
				+ mensagem + "</p>\r\n" + "						</td>\r\n" + "					</tr>\r\n"
				+ "				</table>\r\n" + "			</td>\r\n" + "		</tr>\r\n" + "	</table>\r\n"
				+ "	<!--/100% background wrapper-->\r\n" + "</body>\r\n" + "</html>\r\n" + "";
	}

}
