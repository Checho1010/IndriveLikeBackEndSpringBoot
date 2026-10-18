package com.optic.apirest.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerStarter {

    @Bean
    public CommandLineRunner runSocket(SocketIOServer server) {
        return args -> server.start();
    }
}
