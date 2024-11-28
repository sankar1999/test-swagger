package com.test.swagger.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Configuration
    public class ResourceServerSecurityConfiguration {

        @Bean
        @Order(10)
        public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                    .securityMatcher("/api/**") // Match only requests to /api/**
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all requests
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                    .httpBasic(AbstractHttpConfigurer::disable) // Disable basic authentication
                    // Optionally configure CORS, if needed:
                    // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .build();
        }

        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control", "Content-Type", "Auth-Token", "operatorToken"));
            configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }

    @Configuration
    public static class HttpSecurityConfiguration {

        @Bean
        @Order(30)
        public SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .headers(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeHttpRequest -> {
                        authorizeHttpRequest.requestMatchers(
                                antMatcher("/**")
                        ).permitAll();
                        authorizeHttpRequest.requestMatchers(EndpointRequest.to("info", "health", "refresh", "busrefresh")).permitAll();
                        authorizeHttpRequest.anyRequest().authenticated();
                    })
                    .sessionManagement(sessionManagement ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .httpBasic(withDefaults());

            return http.build();
        }
    }
}