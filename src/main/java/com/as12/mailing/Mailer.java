package com.as12.mailing;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
@Service
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
 
 
	public void sendMail(Mail message) {
		
		try {
			MimeMessage emailMessage = mailSender.createMimeMessage();
			MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
			
			mailBuilder.setTo("to@localhost.com");
			mailBuilder.setSubject("Hello");
			
			//Template template = velocityEngine.getTemplate("./templates/mail-template.html");
			 
			//VelocityContext velocityContext = new VelocityContext();
			//StringWriter stringWriter = new StringWriter();
			 
			//template.merge(velocityContext, stringWriter);
	 
			mailBuilder.setText(message.getMailContent());
			
			mailSender.send(emailMessage);
			
		}
		catch (MessagingException e) {
		}
	}
}