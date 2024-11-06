package com.tboostAI_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TboostAiCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TboostAiCoreApplication.class, args);
    }

}
