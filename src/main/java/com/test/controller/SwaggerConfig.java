package com.test.controller;

import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer openApiCustomiser(HttpServletRequest request) {
        return openApi -> {
            String prefix = request.getHeader("X-Forwarded-Prefix");
            String serverUrl = (prefix != null) ? prefix : "";
            openApi.setServers(List.of(new Server().url(serverUrl)));
        };
    }


}
