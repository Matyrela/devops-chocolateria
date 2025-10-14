package me.devops.chocolateria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI chocolateriaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Chocolatería")
                        .description("API para gestionar pedidos y productos de una chocolatería")
                        .version("1.0.0"));
    }
}
