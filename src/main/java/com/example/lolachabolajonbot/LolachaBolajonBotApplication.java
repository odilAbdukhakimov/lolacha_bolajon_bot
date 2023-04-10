package com.example.lolachabolajonbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LolachaBolajonBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolachaBolajonBotApplication.class, args);
    }
}
