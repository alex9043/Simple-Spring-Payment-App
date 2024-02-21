package ru.alex9043.simplespringpaymentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SimpleSpringPaymentAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringPaymentAppApplication.class, args);
    }

}
