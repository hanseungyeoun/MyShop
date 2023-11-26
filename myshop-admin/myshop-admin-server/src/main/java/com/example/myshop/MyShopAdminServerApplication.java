package com.example.myshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MyShopAdminServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyShopAdminServerApplication.class, args);
  }

}
