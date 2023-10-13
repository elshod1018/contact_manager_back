package com.company.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(@NotNull CorsRegistry registry) {
//                registry
//                        .addMapping("/**")
//                        .allowedOrigins("http://localhost:8080",
//                                "http://localhost:9090"
//                        )
//                        .allowedHeaders("*")
//                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
//                        .allowCredentials(true);
//            }
//        };
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:8080",
                        "http://localhost:9090")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
