package com.efundzz.emailservice.controller;

import com.efundzz.emailservice.service.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody String message) {
        try {
            //TODO: To write proper logs
            emailService.sendEmail(message);
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Failed to send email: {}",e.getMessage());
        }
        return ResponseEntity.ok("Received SNS request");
    }
}
