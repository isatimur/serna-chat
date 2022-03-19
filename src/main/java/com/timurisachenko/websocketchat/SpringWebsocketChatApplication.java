package com.timurisachenko.websocketchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpringWebsocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebsocketChatApplication.class, args);
    }

}
