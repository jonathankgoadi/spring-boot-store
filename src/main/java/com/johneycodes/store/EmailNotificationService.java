package com.johneycodes.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("email")
@Primary
public class EmailNotificationService implements NotificationService {
    @Value("${server.host}")
    private String host;
    @Value("${server.port}")
    private int port;
    @Override
    public void send(String message, String recipientEmail) {
        System.out.println("Sending email to " + recipientEmail);
        System.out.println(
                "Host: " + host + ", port: " + port + ", message: " + message
        );

    }
}
