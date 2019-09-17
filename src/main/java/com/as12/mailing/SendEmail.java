package com.as12.mailing;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SendEmail {
	@Bean
	  CommandLineRunner initDatabase(Mailer sendMail) {
	    return args -> {
	      Mail mail = new EmailBuilder()
	    		  .From("username@gmail.com") // For gmail, this field is ignored.
	    		  .To("Sue@gmail.com")
	    		  .Template("mail-template.html")
	    		  .AddContext("subject", "Dear Sue")
	    		  .AddContext("content", "Hello World!")
	    		  .Subject("Hello")
	    		  .createMail();
	      
	      sendMail.sendMail(mail,true);
	    };
	  }
}

