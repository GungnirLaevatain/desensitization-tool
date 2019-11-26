package com.github.gungnirlaevatain.desensitization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SampleApplication.class);
        springApplication.run(args);
    }
}
