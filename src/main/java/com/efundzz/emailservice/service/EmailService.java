package com.efundzz.emailservice.service;

import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private S3Service s3Service;

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