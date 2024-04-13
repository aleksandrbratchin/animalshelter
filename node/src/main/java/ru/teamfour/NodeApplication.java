package ru.teamfour;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class NodeApplication {
    @Generated
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class);
    }
}