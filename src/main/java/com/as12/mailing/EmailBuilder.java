package com.as12.mailing;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.velocity.app.Velocity;

public class EmailBuilder {

	private VelocityEngine velocityEngine;
 
	private String subject;
	private String mailTo;
	private String mailFrom;
	private emailTemplate template;
	enum emailTemplate { DEFAULT, TEMPLATE}
	
	public EmailBuilder(VelocityEngine templateEngine) {
		this.template = emailTemplate.DEFAULT;
		this.mailTo = "";
		this.mailFrom = "";
		this.subject = "";
		this.velocityEngine = templateEngine;
	}
	
	public EmailBuilder Template(emailTemplate templateType) {
		
		this.template = templateType;
		return this;
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

	
	public Mail createMail() throws IllegalArgumentException { 
		
		
		
		Properties properties = new Properties();
	    properties.setProperty("input.encoding", "UTF-8");
	    properties.setProperty("output.encoding", "UTF-8");
	    properties.setProperty("resource.loader", "file, class, jar");
	    properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    
	    Velocity.init(properties);
	   // Template template = Velocity.getTemplate("./templates/mail-template.html");
	    Template template = Velocity.getTemplate("./templates/sample.txt");
	    VelocityContext context = new VelocityContext();
	    StringWriter writer = new StringWriter();
	    context.put("var", new String("Oh my"));
	    template.merge(context, writer);
		/*VelocityEngine myEngine = new VelocityEngine(properties);
		Template templateEngine = myEngine.getTemplate("./templates/mail-template.html");
		VelocityContext velocityContext = new VelocityContext();
		StringWriter stringWriter = new StringWriter();
		//velocityContext.put("hello", new String(this.template.toString()));
		velocityContext.put("hello", new String("Oh my"));
		
		templateEngine.merge(velocityContext, stringWriter); */
		Mail result = new Mail();
		
		if(this.mailTo.isEmpty() || this.mailFrom.isEmpty()) {
			throw new IllegalArgumentException("Missing mail headers");
		}
		
		result.setMailTo(this.mailTo);
		result.setMailFrom(this.mailFrom);
		result.setMailContent(writer.toString());
		//result.setMailContent("Hey");
		result.setMailSubject(this.subject); 
		
		
		return result;
	}
}
