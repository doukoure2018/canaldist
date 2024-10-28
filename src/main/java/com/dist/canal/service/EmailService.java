package com.dist.canal.service;

import com.dist.canal.enumeration.VerificationType;

public interface EmailService {

    void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType);

    void sendSMS(String recipient, String message);
}
