package com.as12.mailing;

import com.as12.builders.MailBuilder;
import com.as12.models.Mail;
import com.as12.services.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import static org.junit.Assert.assertEquals;
import java.io.IOException;	
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;


@SpringBootTest
public class EmailServiceTest {
	
    private EmailService emailService;
	private GreenMail greenMail;

	@Before
	public void startMailServer() throws InterruptedException {
		greenMail = new GreenMail(
			      new ServerSetup[] {
			          new ServerSetup(3025, "localhost", ServerSetup.PROTOCOL_SMTP)
			      }
			  );
		greenMail.start();
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);
        mailSender.setProtocol("smtp");
		emailService = new EmailService(mailSender);
	}
	
	@After
	public void stopMailServer() {
		greenMail.stop();
	}

	@Test
	public void testSendWithTemplate() throws MessagingException, InterruptedException, IOException {
		final String aLongText = "Hello";
		final Mail message = new MailBuilder().Template("sample.txt")
				.Subject("Hello")
				.To("Sam@gmail.com")
				.From("John@gmail.com")
				.AddContext("content", aLongText)
				.createMail();
	    emailService.sendHTMLEmail(message);
	    Message msg = greenMail.getReceivedMessages()[0];
	    String body = getTextFromMessage(msg);
	    body = body.replace("\n", "").replace("\r", "");
	    assertEquals(aLongText, body);
	}
	
	/*
	 * Utility code to translate MIME multipart message to String
	 * Source: https://stackoverflow.com/questions/11240368/how-to-read-text-inside-body-of-mail-using-javax-mail 
	 */
	private String getTextFromMessage(Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}

	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
}

