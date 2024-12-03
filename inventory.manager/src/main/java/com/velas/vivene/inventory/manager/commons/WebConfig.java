package com.velas.vivene.inventory.manager.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir requisições de http://44.201.138.244:3000, sem a parte /host
        registry.addMapping("/**")  // Permite todas as rotas do backend
                .allowedOrigins("http://18.212.185.46:3000")  // URL do frontend (sem /host)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // Permitir credenciais (cookies, autenticação)
    }
}
