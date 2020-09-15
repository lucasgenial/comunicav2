package br.com.cicom.comunicacicom.DSPrimary.model;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

/**
 *
 * @author Administrador
 */

public class GerenciadorDeEnvioPorEmail {

	private static final Logger LOGGER = Logger.getAnonymousLogger();
	private String SERVIDOR_SMTP = "smtp.office365.com";
	private static final int PORTA_SERVIDOR_SMTP = 25;
	private String CONTA_PADRAO = "";
	private String SENHA_CONTA_PADRAO = "";

	public String enviarEmail(String nome, String estabelecimento, String descricaoDaOcorrenciaFormatado, String emails,
			String conta, String senha) {

		CONTA_PADRAO = conta;
		SENHA_CONTA_PADRAO = senha;
			
		if(conta.contains("@gmail")) {
			this.SERVIDOR_SMTP = "smtp.gmail.com";
		}
		
		final Session session = Session.getInstance(this.getEmailProperties(), new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
			}
		});

		try {
			final Message message = new MimeMessage(session);

			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emails));
			message.setFrom(new InternetAddress(CONTA_PADRAO));
			message.setSubject(estabelecimento);
			message.setSentDate(new Date());

			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = descricaoDaOcorrenciaFormatado;
			messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");

			MimeBodyPart MimeBodyPart = new MimeBodyPart();

			DataSource fds = new FileDataSource(nome);
			if (!nome.isEmpty()) {
				MimeBodyPart.setDisposition(Part.ATTACHMENT);
				MimeBodyPart.setDataHandler(new DataHandler(fds));
				MimeBodyPart.setFileName(fds.getName());
			}

			multipart.addBodyPart(messageBodyPart);
			if (!nome.isEmpty()) {
				multipart.addBodyPart(MimeBodyPart);
			}

			message.setContent(multipart);

			Transport.send(message, InternetAddress.parse(emails));

		} catch (javax.mail.AuthenticationFailedException e) {
			return "Autenticacao";
		}catch (com.sun.mail.util.MailConnectException exe) {
			return "Conexao";
		} catch (javax.mail.MessagingException ex) {
			LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
		}

		File file = new File(nome);
		file.delete();
		return "Enviado";
	}

	public String enviarSenha(String nome, String estabelecimento, String descricaoDaOcorrenciaFormatado, String emails,
			String conta, String senha) {

		CONTA_PADRAO = conta;
		SENHA_CONTA_PADRAO = senha;
			
		if(conta.contains("@gmail")) {
			this.SERVIDOR_SMTP = "smtp.gmail.com";
		}
		
		final Session session = Session.getInstance(this.getEmailProperties(), new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
			}
		});

		try {
			final Message message = new MimeMessage(session);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));
			message.setFrom(new InternetAddress(CONTA_PADRAO));
			message.setSubject(estabelecimento);
			message.setSentDate(new Date());

			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = descricaoDaOcorrenciaFormatado;
			messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");

			MimeBodyPart MimeBodyPart = new MimeBodyPart();

			DataSource fds = new FileDataSource(nome);
			if (!nome.isEmpty()) {
				MimeBodyPart.setDisposition(Part.ATTACHMENT);
				MimeBodyPart.setDataHandler(new DataHandler(fds));
				MimeBodyPart.setFileName(fds.getName());
			}

			multipart.addBodyPart(messageBodyPart);
			if (!nome.isEmpty()) {
				multipart.addBodyPart(MimeBodyPart);
			}

			message.setContent(multipart);

			Transport.send(message, InternetAddress.parse(emails));

		} catch (javax.mail.AuthenticationFailedException e) {
			return "Autenticacao";
		}catch (com.sun.mail.util.MailConnectException exe) {
			return "Conexao";
		} catch (javax.mail.MessagingException ex) {
			LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
		}

		File file = new File(nome);
		file.delete();
		return "Enviado";
	}
	
	/**
	 * 
	 * ENVIO DE EMAIL PARA O SUPORTE
	 */
	
	public String enviarEmailContato(String mensagem) {

		CONTA_PADRAO = "comunica.cicom@ssp.ba.gov.br";
		SENHA_CONTA_PADRAO = "P4$$c0mc1c0m";			
		
		final Session session = Session.getInstance(this.getEmailProperties(), new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
			}
		});

		try {
			final Message message = new MimeMessage(session);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CONTA_PADRAO));
			message.setFrom(new InternetAddress(CONTA_PADRAO));

			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = mensagem;
			messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message, InternetAddress.parse(CONTA_PADRAO));

		} catch (javax.mail.MessagingException ex) {
			LOGGER.log(Level.WARNING, "Erro ao enviar mensagem: " + ex.getMessage(), ex);
		}

		return "Enviado";
	}
	
	/* *********************************************************** */

	
	public Properties getEmailProperties() {
		final Properties config = new Properties();
		config.put("mail.smtp.auth", "true");
		config.put("mail.smtp.starttls.enable", "true");
		config.put("mail.smtp.ssl.trust", SERVIDOR_SMTP);
		config.put("mail.smtp.host", SERVIDOR_SMTP);
		config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);

		return config;
	}
}