package com.as12.mailing;

import java.io.StringWriter;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class EmailBuilder {

	private String subject;
	private String mailTo;
	private String mailFrom;
	private String template;
	VelocityContext velocityContext;
	VelocityEngine velocityEngine;
	
	public EmailBuilder() {
		this.mailTo = "";
		this.mailFrom = "";
		this.subject = "";
		this.template = "";
		this.velocityContext = new VelocityContext();
		
		// Initialize Velocity Engine
		Properties properties = new Properties();
		properties.setProperty("input.encoding", "UTF-8");
		properties.setProperty("output.encoding", "UTF-8");
		properties.setProperty("resource.loader", "file, class, jar");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		this.velocityEngine = new VelocityEngine(properties);
				
	}
	
	public EmailBuilder Subject(String subject) {
		
		this.subject = subject;
		return this;
	}
	
	public EmailBuilder To(String to) {
		
		this.mailTo = to;
		return this;
	}
	
	public EmailBuilder From(String from) {
		this.mailFrom = from;
		return this;
	}
	
	public EmailBuilder Template(String template) {
		this.template = template;
		return this;
	}
	public EmailBuilder AddContext(String key, String value) {
		velocityContext.put(key, value);
		return this;
	}
	public Mail createMail() throws IllegalArgumentException { 

		//Select Template
		Template templateEngine = velocityEngine.getTemplate("templates/" + this.template);
		
		//Apply template
		StringWriter stringWriter = new StringWriter();		
		templateEngine.merge(this.velocityContext, stringWriter); 
		
		//Check state of the mails.
		if(this.mailTo.isEmpty() || this.mailFrom.isEmpty()) {
			throw new IllegalArgumentException("Missing mail headers");
		}
		
		//Build mail object
		Mail result = new Mail();
		result.setMailTo(this.mailTo);
		result.setMailFrom(this.mailFrom);
		result.setMailContent(stringWriter.toString());
		result.setMailSubject(this.subject); 
		
		return result;
	}
}
