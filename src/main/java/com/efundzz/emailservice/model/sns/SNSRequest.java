package com.efundzz.emailservice.model.sns;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSRequest {
    @JsonAlias("Type")
    private String type;

    @JsonAlias("MessageId")
    private String messageId;

    @JsonAlias("Token")
    private String token;

    @JsonAlias("TopicArn")
    private String topicArn;

    @JsonAlias("Subject")
    private String subject;

    @JsonAlias("Message")
    private String message;

    @JsonAlias("Timestamp")
    private String timestamp;

    @JsonAlias("SignatureVersion")
    private String signatureVersion;

    @JsonAlias("Signature")
    private String signature;

    @JsonAlias("SigningCertURL")
    private String signingCertURL;

    @JsonAlias("SubscribeURL")
    private String subscribeURL;

    @JsonAlias("UnsubscribeURL")
    private String unsubscribeURL;
}
