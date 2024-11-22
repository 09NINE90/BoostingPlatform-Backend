package ru.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.platform.*")
@ComponentScan(basePackages = { "ru.platform.*" })
@EntityScan("ru.platform.*")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
