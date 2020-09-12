package com.covid19.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Covid19Application {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(Covid19Application.class, args);
    }

}
