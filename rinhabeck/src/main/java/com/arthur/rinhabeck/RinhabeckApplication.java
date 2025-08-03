package com.arthur.rinhabeck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RinhabeckApplication {

    public static void main(String[] args) {
        SpringApplication.run(RinhabeckApplication.class, args);
    }

}
