package com.gdsc_solutionchallenge.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;


@Configuration
public class SwaggerConfig {
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .components(new Components())
//                .info(apiInfo());
//    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("VishingGuard API")
//                .description("VishingGuard API 모음")
//                .version("1.0.0");
//    }

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }
    private Info apiInfo() {
        return new Info()
                .title("VishingGuard API")
                .description("VishingGuard API 모음")
                .version("1.0.0");
    }

}
