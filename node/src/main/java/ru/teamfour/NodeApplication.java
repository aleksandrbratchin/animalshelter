package ru.teamfour;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableCaching
@EnableScheduling
public class NodeApplication {
    @Generated
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class);
    }
}