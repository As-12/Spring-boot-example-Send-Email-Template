package com.as12.builders;

import java.io.StringWriter;
import java.util.Properties;

import com.as12.models.Mail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class MailBuilder {
	private String subject;
	private String mailTo;
	private String mailFrom;
	private String template;
	private final VelocityContext velocityContext;
	private final VelocityEngine velocityEngine;
	
	public MailBuilder() {
		this.mailTo = "";
		this.mailFrom = "";
		this.subject = "";
		this.template = "";
		this.velocityContext = new VelocityContext();
		final Properties properties = new Properties();
		properties.setProperty("input.encoding", "UTF-8");
		properties.setProperty("output.encoding", "UTF-8");
		properties.setProperty("resource.loader", "file, class, jar");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		this.velocityEngine = new VelocityEngine(properties);
	}
	
	public MailBuilder Subject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public MailBuilder To(String to) {
		this.mailTo = to;
		return this;
	}
	
	public MailBuilder From(String from) {
		this.mailFrom = from;
		return this;
	}
	
	public MailBuilder Template(String template) {
		this.template = template;
		return this;
	}

	public MailBuilder AddContext(String key, String value) {
		velocityContext.put(key, value);
		return this;
	}

	public MailBuilder AddContext(String key, Object value) {
		velocityContext.put(key, value);
		return this;
	}

	public Mail createMail() throws IllegalArgumentException {
		final Template templateEngine = velocityEngine.getTemplate("templates/" + this.template);
		final StringWriter stringWriter = new StringWriter();
		templateEngine.merge(this.velocityContext, stringWriter);
		if(this.mailTo.isEmpty() || this.mailFrom.isEmpty()) {
			throw new IllegalArgumentException("Missing mail headers");
		}
		final Mail result = new Mail();
		result.setMailTo(this.mailTo);
		result.setMailFrom(this.mailFrom);
		result.setMailContent(stringWriter.toString());
		result.setMailSubject(this.subject);

		return result;
	}
}
