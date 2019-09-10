package com.as12.mailing;


import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;

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
			          new ServerSetup(3025, "127.0.0.1", ServerSetup.PROTOCOL_SMTP)
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
		
	    emailService.sendMail();
	    System.out.print(GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	    //assertEquals("some body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}
    
}

