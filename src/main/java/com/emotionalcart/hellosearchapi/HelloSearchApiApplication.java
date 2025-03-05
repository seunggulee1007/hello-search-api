package com.emotionalcart.hellosearchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class HelloSearchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSearchApiApplication.class, args);
    }

}
