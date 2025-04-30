package com.housing.back.provider;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailProvider {

    private static final String SUBJECT = "[임대주택 가격 서비스] 인증메일입니다.";

    private final JavaMailSender javaMailSender;

    public boolean sendCertificationMail(String email, String certificationNumber) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getCertificationMessage(String certificationNumber) {
        StringBuilder certificationMessage = new StringBuilder();
        certificationMessage
                .append("<h1 style='text-align: center;'>[임대주택 가격 서비스] 인증메일</h1>")
                .append("<h3 style='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>")
                .append(certificationNumber)
                .append("</string></h3>");

        return certificationMessage.toString();
    }
}
