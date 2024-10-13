package com.tlijani.inventory_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productServiceAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Service API")
                        .description("This is a REST API for the inventory service")
                        .version("v0.0.1"));
    }
}
