package com.dist.canal.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.dist.canal.enumeration.VerificationType;
import com.dist.canal.exception.ApiException;
import com.dist.canal.exception.BlogAPIException;
import com.dist.canal.payload.TokenResponse;
import com.dist.canal.service.EmailService;
import com.dist.canal.service.OrangeSmsService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final OrangeSmsService orangeSmsService;
    @Override
    public void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("douklifsa93@gmail.com");
            message.setTo(email);
            message.setText(getEmailMessage(firstName, verificationUrl, verificationType));
            message.setSubject(String.format("Canal Plus - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mailSender.send(message);
            log.info("Email sent to {}", firstName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @Override
    public void sendSMS(String recipient, String message) {
        TokenResponse tokenResponse = orangeSmsService.getOAuthToken();
        CompletableFuture.runAsync(() -> {
            if (tokenResponse.getStatus() != 200) {
                throw new ApiException("Failed to get OAuth token");
            }
            try {
                orangeSmsService.sendSms(tokenResponse.getToken(), recipient, "GUIDIPRESS", message);
            } catch (Exception e) {
                log.error("Failed to send SMS", e);
            }
        }).exceptionally(ex -> {
            log.error("Exception in sendSMS", ex);
            return null;
        });
    }

    private String getEmailMessage(String firstName, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> { return "Hello " + firstName + "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl + "\n\nThe Support Team"; }
            case ACCOUNT -> { return "Hello " + firstName + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl + "\n\nThe Support Team"; }
            default -> throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unable to send email. Email type unknown");
        }
    }
}
