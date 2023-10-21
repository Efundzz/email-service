package com.efundzz.emailservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
    private String to;
    private List<String> bcc;
    //TODO: to get the brand name and the email_name instead of tempaltePath
    private String templatePath;
    private Map<String, Object> dataModel;
    private String subject;
}