package com.example.myshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationExecutor {

  public void sendEmail(String email, String title, String description) {
    log.info("sendEmail");
  }

  public void sendKakao(String phoneNo, String description) {
    log.info("sendKakao");
  }

  public void sendSms(String phoneNo, String description) {
    log.info("sendSms");
  }
}
