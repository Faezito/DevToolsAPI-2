package com.faezito.devToolsAPI.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
@Bean
public OpenAPI customOpenAPI() {

    final String apiKeyScheme = "ApiKeyAuth";
    final String bearerScheme = "BearerAuth";

    return new OpenAPI()
            .addSecurityItem(
                    new SecurityRequirement()
                            .addList(bearerScheme)
            )
            .components(new Components()
                    .addSecuritySchemes(
                            apiKeyScheme,
                            new SecurityScheme()
                                    .name("X-API-Key")
                                    .type(SecurityScheme.Type.APIKEY)
                                    .in(SecurityScheme.In.HEADER)
                    )
                    .addSecuritySchemes(
                            bearerScheme,
                            new SecurityScheme()
                                    .name("Authorization")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                    )
            )
            .info(new Info()
                    .title("DevTools API")
                    .version("1.0")
                    .description(
                            "API para facilitar a comunicação entre devs e usuários nos sistemas"
                    )
            );
}}
