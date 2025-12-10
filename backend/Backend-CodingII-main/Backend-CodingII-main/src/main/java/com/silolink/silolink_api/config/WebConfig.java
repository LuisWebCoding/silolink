package com.silolink.silolink_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera para todos os endpoints da API
                .allowedOrigins("http://localhost:3000", "http://localhost:4200", "http://127.0.0.1:5500" , "http://localhost:5173/" ) // Adicione aqui a URL do seu Frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                .allowedHeaders("*") // <-- CORREÇÃO: Permite todos os cabeçalhos
                .allowCredentials(true); // <-- CORREÇÃO: Permite credenciais
    }
}
