package com.as12.controllers;

import com.as12.builders.MailBuilder;
import com.as12.models.Mail;
import com.as12.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EMailController {

    private final EmailService emailService;
    private final String mailTo;

    public EMailController(EmailService emailService, @Value("${mail.to}") String mailTo) {
        this.emailService = emailService;
        this.mailTo = mailTo;
    }

    // Send a simple HTML email to the mail.to
    @RequestMapping(value ="/test")
    public String sendTestReport(HttpServletRequest request){
        final Mail mail = new MailBuilder()
                .From(null) // For gmail, this field is ignored.
                .To(this.mailTo)
                .Template("mail-template.html")
                .AddContext("subject", "Test Email")
                .AddContext("content", "Hello World!")
                .Subject("Hello")
                .createMail();
        String responseMessage = request.getRequestURI();
        try {
            this.emailService.sendHTMLEmail(mail);
        }
        catch (Exception e) {
            responseMessage = "Request Unsuccessful \n" + e.getMessage() + "\n" + responseMessage;
            return responseMessage;
        }
        responseMessage = "Request Successful \n" + responseMessage;
        return responseMessage;
    }

    @RequestMapping(value ="/")
    public String homePage(HttpServletRequest request) {
        String responseMessage = request.getRequestURI();
        responseMessage = "Welcome to mailing service. \n" +
            "Please use /test to send sample report \n" + responseMessage;
        return responseMessage;
    }

}
