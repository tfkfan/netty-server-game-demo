package com.tfkfan.nettywebgame;

import com.tfkfan.nettywebgame.networking.WebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements CommandLineRunner {
    private final WebSocketServer webSocketServer;

    public Application(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws InterruptedException {
        webSocketServer.start();
    }
}
