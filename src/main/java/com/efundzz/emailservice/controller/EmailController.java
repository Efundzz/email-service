package com.efundzz.emailservice.controller;

import com.efundzz.emailservice.model.EmailRequest;
import com.efundzz.emailservice.service.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            // For simplicity, assuming that your EmailRequest object contains fields such as "to", "templatePath" and "dataModel".
            // Adjust according to your application's requirements.
            emailService.sendEmail(emailRequest.getDataModel(), emailRequest.getTemplatePath(), emailRequest.getTo(), emailRequest.getSubject());
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
