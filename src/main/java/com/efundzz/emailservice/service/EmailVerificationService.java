package com.efundzz.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.util.regex.Pattern;

@Service
public class EmailVerificationService {

    @Autowired
    private final MXLookupService mxLookupService;

    public EmailVerificationService(MXLookupService mxLookupService) {
        this.mxLookupService = mxLookupService;
    }

    public boolean verifyEmail(String email) {
        if (!checkEmailSyntax(email)) {
            return false;
        }
        String domain = email.substring(email.indexOf("@") + 1);
        try {
            String mxRecord = mxLookupService.getMXRecord(domain);
            return mxRecord.contains("MX");
        } catch (NamingException e) {
            return false;
        }
    }

    private boolean checkEmailSyntax(String email) {
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
