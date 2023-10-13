package com.company.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Contact Manager")
                        .description("API for applicants create their contacts")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Elshod Nuriddinov ")
                                .email("nuriddinovelshod2003@gmail.com")
                                .url("https://github.com/elshod1018"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                        .termsOfService("https://swagger.io/terms/"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Wikipedia Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .servers(List.of(
                                new Server().url("http://localhost:9090").description("Development Server"),
                                new Server().url("http://localhost:8080").description("Web Server")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components((new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                ));

    }
}
