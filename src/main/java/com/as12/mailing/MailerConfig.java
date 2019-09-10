package com.as12.mailing;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailerConfig {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("127.0.0.1");
		mailSender.setPort(3025);
		mailSender.setProtocol("smtp");
		return mailSender;
	}
	
	@Bean
	public VelocityEngine velocityEngine() throws Exception {
	    Properties properties = new Properties();
	    properties.setProperty("input.encoding", "UTF-8");
	    properties.setProperty("output.encoding", "UTF-8");
	    properties.setProperty("resource.loader", "class");
	    properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    VelocityEngine velocityEngine = new VelocityEngine(properties);
	    return velocityEngine;
	}
	
}
