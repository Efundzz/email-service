package com.efundzz.emailservice.service;

import com.efundzz.emailservice.model.EmailRequest;
import com.efundzz.emailservice.model.sns.SNSRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.efundzz.emailservice.util.AppConstants.NOTIFICATION;
import static com.efundzz.emailservice.util.AppConstants.SUBSCRIPTION_CONFIRMATION;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private S3Service s3Service;

    @Autowired
    RestTemplate restTemplate;

    public void sendEmail(String message) throws IOException, MessagingException, TemplateException {
        ObjectMapper objectMapper = new ObjectMapper();
        SNSRequest snsRequest = objectMapper.readValue(message, SNSRequest.class);

        log.info("Received request with requestType: {}",snsRequest.getType());
        if (SUBSCRIPTION_CONFIRMATION.equalsIgnoreCase(snsRequest.getType())) {
            confirmSubscription(snsRequest.getSubscribeURL());
        }
        else if(NOTIFICATION.equalsIgnoreCase(snsRequest.getType())) {
            EmailRequest emailRequest = objectMapper.readValue(snsRequest.getMessage(), EmailRequest.class);
            sendEmail(emailRequest.getDataModel(), emailRequest.getTemplatePath(), emailRequest.getTo(), emailRequest.getSubject());
            //To add a step in stepdata/audit table for result from email being sent
        }
    }

    public void confirmSubscription(String subscribeUrl) {
        restTemplate.getForObject(subscribeUrl, String.class);
    }

    public void sendEmail(Map<String, Object> dataModel, String templatePath, String to, String subject) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Template t = freemarkerConfig.getTemplate(templatePath);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, dataModel);



        helper.setTo(to);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom("purviewef@efundzz.in");

        mailSender.send(message);

        String fileType = templatePath.replaceFirst("\\.ftl", ".pdf");
        StringBuilder fileName = new StringBuilder();
        fileName.append(dataModel.get("referenceNumber"));
        fileName.append("_");
        fileName.append(fileType);


            HtmlConverter.convertToPdf(html, new FileOutputStream(fileName.toString()));
            // Upload To S3 Bucket
            s3Service.uploadFile(fileName.toString());
    }

    public void sendEmailWithoutSave(Map<String, Object> dataModel, String templatePath, String to, String subject) throws MessagingException, IOException, TemplateException {
        sendEmailWithoutSave(dataModel, templatePath, to, subject, null);
    }

    public void sendEmailWithoutSave(Map<String, Object> dataModel, String templatePath, String to, String subject, List<String> bcc) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Template t = freemarkerConfig.getTemplate(templatePath);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, dataModel);


        helper.setTo(to);
        if(bcc != null && !bcc.isEmpty()) {
            helper.setBcc(bcc.toArray(new String[0]));
        }
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom("purviewef@efundzz.in");

        mailSender.send(message);
    }
}