package com.efundzz.emailservice.controller;

import com.efundzz.emailservice.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/verify")
public class EmailVerificationController {

    @Autowired
    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }


    @GetMapping("/{email}")
    public ResponseEntity<Map<String, Boolean>> verifyEmail(@PathVariable String email) {
        Map<String, Boolean> response = Map.of("isValid", emailVerificationService.verifyEmail(email));
        return ResponseEntity.ok(response);
    }
}
