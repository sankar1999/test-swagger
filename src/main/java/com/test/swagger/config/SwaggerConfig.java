package com.test.swagger.config;

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
    public OpenAPI BulkBookingOpenAPI() {
        final String securitySchemeName = "Authorization";
        return new OpenAPI()
                .info(getInfo())
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, getSecurityScheme(securitySchemeName)))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)
                );
    }

    private static SecurityScheme getOperatorSecurityScheme(String securitySchemeNameOperator) {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(securitySchemeNameOperator)
                .scheme("Bearer");
    }

    private static SecurityScheme getSecurityScheme(String securitySchemeName) {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(securitySchemeName)
                .scheme("Bearer");
    }


    private static Info getInfo() {
        return new Info().title("MTicket Booking Service")
                .description("API Resources & Documentation")
                .version("1.0");
    }



}
