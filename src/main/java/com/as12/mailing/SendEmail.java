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
	      System.out.print("Hello World");
	      sendMail.sendMail();
	    };
	  }
}

