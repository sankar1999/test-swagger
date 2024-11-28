package com.test.swagger.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyAppInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-ui/**",        // Exclude the Swagger UI paths
                        "/v3/api-docs/**",       // Exclude OpenAPI docs paths (if using Springdoc OpenAPI)
                        "/swagger-resources/**", // Exclude Swagger resources
                        "/webjars/**"            // Exclude webjars (used by Swagger UI)
                );
    }

}
