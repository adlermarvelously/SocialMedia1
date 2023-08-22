package com.merveadler;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerGitApplication {

    @Value("${spring.cloud.config.server.git.password}")
    private String gitToken;

    @PostConstruct
    public void init(){
        System.out.println("gitToken: " + gitToken);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerGitApplication.class, args);
    }
}
