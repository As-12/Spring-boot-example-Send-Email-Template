package com.as12.mailing;


import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MailerTest {
	
	@Autowired
    private Mailer emailService;
	
	private GreenMail greenMail;

	@Before
	public void startMailServer() throws InterruptedException {
		greenMail = new GreenMail(
			      new ServerSetup[] {
			          new ServerSetup(3025, "localhost", ServerSetup.PROTOCOL_SMTP)
			      }
			  );
		greenMail.start();
	}
	
	@After
	public void stopMailServer() {
		greenMail.stop();
	}
	

	@Test
	public void testSend() throws MessagingException, InterruptedException {
		
		//String aLongText = "He determined to drop his litigation with the monastry, and relinguish his claims to the wood-cuting and fishery rihgts at once. He was the more ready to do this becuase the rights had becom much less valuable, and he had indeed the vaguest idea where the wood and river in quedtion were.";
		String aLongText = "Hello";
		Mail message = new EmailBuilder().Template("sample.txt")
				.Subject("Hello")
				.To("Sam@gmail.com")
				.From("John@gmail.com")
				.AddContext("content", aLongText)
				.createMail();
		
	    emailService.sendMail(message, true);
	    System.out.print(GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	    assertEquals(aLongText, GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}
    
}

