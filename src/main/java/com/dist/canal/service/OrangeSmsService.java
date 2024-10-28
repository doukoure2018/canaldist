package com.dist.canal.service;

import com.dist.canal.payload.TokenResponse;

public interface OrangeSmsService {

    public TokenResponse getOAuthToken();

    public void sendSms(String token, String recipient, String senderName, String message);

    int getSmsBalance(String token);
}
