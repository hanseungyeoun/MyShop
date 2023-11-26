package com.example.myshop.service.notification;

import com.example.myshop.NotificationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationAdapter implements NotificationService {

    private final NotificationExecutor executor;

    @Override
    public void sendEmail(String email, String title, String description) {
        executor.sendEmail(email, title, description);
    }

    @Override
    public void sendKakao(String phoneNo, String description) {
        executor.sendKakao(phoneNo, description);
    }

    @Override
    public void sendSms(String phoneNo, String description) {
        executor.sendKakao(phoneNo, description);
    }
}
