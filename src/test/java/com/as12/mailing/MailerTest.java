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
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MailerTest {
	
	@Autowired
    private Mailer emailService;

	private GreenMail greenMail;

	@Before
	public void startMailServer() {
		greenMail = new GreenMail(
			      new ServerSetup[] {
			          new ServerSetup(5000, "localhost", ServerSetup.PROTOCOL_SMTP)
			      }
			  );
		greenMail.start();
	}
	
	@After
	public void stopMailServer() {
		greenMail.stop();
	}
	@Test
	public void testSend() throws MessagingException {
	    GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com",
	      "some subject", "some body"); // --- Place your sending code here instead
	    
	   // MimeMessage[] receivedMessages = greenMail.getMessages();
       // assertEquals(1, receivedMessages.length);
//
       // MimeMessage current = receivedMessages[0];
        
	    //emailService.sendMail();
	    System.out.print(GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	    //assertEquals("some body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}
    
}

