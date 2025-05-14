package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    //http://localhost:1888/
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
