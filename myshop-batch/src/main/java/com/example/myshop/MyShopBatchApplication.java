package com.example.myshop;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@EnableBatchProcessing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyShopBatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyShopBatchApplication.class, args);
  }

}
