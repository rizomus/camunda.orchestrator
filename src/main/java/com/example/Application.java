package com.example;

import com.example.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

  public static void main(String... args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    List<String> profiles = Arrays.stream(context.getEnvironment().getActiveProfiles()).toList();

    if (profiles.contains("local")) {
      Util.SetOrderStatusUrl("http://localhost:8011/order/set-order-status");
    } else {
      Util.SetOrderStatusUrl("http://order-svc:8011/order/set-order-status");
    }



  }
}