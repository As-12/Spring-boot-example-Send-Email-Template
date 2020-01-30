package com.as12.controllers;

import com.as12.mailing.EmailBuilder;
import com.as12.mailing.Mail;
import com.as12.models.Employee;
import com.as12.models.ReportRequest;
import com.as12.services.Mailer;
import com.as12.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EMailController {

    @Autowired
    private ReportService reporter;
    @Autowired
    private Mailer mailService;

    @Value("${mail.from}")
    private String testEmail;

    @RequestMapping(value ="/test")
    public String sendTestReport(HttpServletRequest request){
        Mail mail = new EmailBuilder()
                .From(testEmail) // For gmail, this field is ignored.
                .To(testEmail)
                .Template("mail-template.html")
                .AddContext("subject", "Test Email")
                .AddContext("content", "Hello World!")
                .Subject("Hello")
                .createMail();

        String responseMessage = request.getRequestURI();

        try {
            mailService.sendMail(mail, true);
        }
        catch (Exception e) {
            responseMessage = "Request Unsuccessful \n" + e.getMessage() + "\n" + responseMessage;
            return responseMessage;
        }
        responseMessage = "Request Successful \n" + responseMessage;
        return responseMessage;
    }

    @PostMapping("/report")
    public String newEmployee(@RequestBody ReportRequest postRequest, HttpServletRequest request) {
        String recipient = postRequest.getRecipient();
        if(postRequest.getRecipient() == null || postRequest.getRecipient().isEmpty() || postRequest.getRecipient() == "") {
            recipient = testEmail;
        }
        Mail mail = reporter.createSampleReport(testEmail,recipient);
        String responseMessage = request.getRequestURI();

        try {
            mailService.sendMail(mail, true);
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
