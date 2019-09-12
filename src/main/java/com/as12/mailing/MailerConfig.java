package com.as12.mailing;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailerConfig {

	
	@Value("${spring.mail.host:127.0.0.1}")
	private String mailServerHost;
	
	@Value("${spring.mail.port:25}")
	private int mailServerPort;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailServerHost);
		mailSender.setPort(mailServerPort);
		mailSender.setProtocol("smtp");
		return mailSender;
	}

}
