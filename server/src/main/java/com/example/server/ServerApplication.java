/*
########################################################################################
## project: citizen-science project (gen + mi = 3)                                    ##
## doel: deze class start het hosten van de website                                   ##
## makers: Elvi Zegeling 14156733, Pleun Emmelot 15169979,                            ##
## Thijmen Masereeuw 15019659, Anna Drenth 14960583                                   ##
## laatste update: 19-04-2025                                                         ##
########################################################################################
*/
package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    //http://localhost:80/
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
