package com.gundaero.alley.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SCHEME = "bearerAuth";

    @Value("${swagger.server.local:http://localhost:8080}")
    private String localServer;

    @Value("${swagger.server.prod:https://gundaero.kozow.com}")
    private String prodServer;

    @Bean
    public OpenAPI openAPI(Environment env) {
        boolean isProd = Arrays.asList(env.getActiveProfiles()).contains("prod");

        List<Server> servers = new ArrayList<>();
        if (isProd) {
            servers.add(new Server().url(prodServer).description("운영 서버"));
        } else {
            servers.add(new Server().url(localServer).description("로컬 서버"));
        }

        return new OpenAPI()
                .info(new Info()
                        .title("근대로 API")
                        .description("근대로의 Swagger API 문서입니다.")
                        .version("v1.0.0"))
                .servers(servers)
                .addSecurityItem(new SecurityRequirement().addList(SCHEME))
                .components(new Components().addSecuritySchemes(
                        SCHEME,
                        new SecurityScheme()
                                .name(SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ));
    }
}
