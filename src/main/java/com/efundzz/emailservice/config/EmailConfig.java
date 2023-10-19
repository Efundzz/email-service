package com.efundzz.emailservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.email")
public class EmailConfig {

    private Map<String, List<String>> bcc;
    private Map<String, String> to;

    // Getters and setters
    public Map<String, List<String>> getBcc() {
        return bcc;
    }

    public void setBcc(Map<String, List<String>> bcc) {
        this.bcc = bcc;
    }

    public Map<String, String> getTo() {
        return to;
    }

    public void setTo(Map<String, String> to) {
        this.to = to;
    }
}
