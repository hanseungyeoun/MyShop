package com.example.myshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyShopApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyShopApiApplication.class, args);
  }

}
