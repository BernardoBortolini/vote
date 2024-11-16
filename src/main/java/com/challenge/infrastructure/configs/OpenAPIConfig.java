package com.challenge.infrastructure.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("/");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("bernardo.bortolini@outlook.com");
        contact.setName("Bernardo");

        Info info = new Info()
                .title("Vote API")
                .version("1.0")
                .contact(contact)
                .description("Esta API expõe endpoints para gerenciar votação das cooperativas.").termsOfService("URL....");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}

