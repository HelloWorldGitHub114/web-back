package com.web10.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MusicApplication {

    public static void main(String[] args) {
        System.out.println("Hello, world!");
        SpringApplication.run(MusicApplication.class, args);
    }

}
