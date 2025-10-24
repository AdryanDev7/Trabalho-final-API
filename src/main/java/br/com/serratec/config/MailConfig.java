package br.com.serratec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void enviarEmail(String para, String assunto, String texto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("grupo1.api2025.2@gmail.com");
		message.setTo(para);
		message.setSubject(assunto);
		message.setText("Dados do Cliente:\n" +texto + "\n\n\n Loja E-commerce");
		javaMailSender.send(message);
	}
}
