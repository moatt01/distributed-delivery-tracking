package com.moa.shipment_service.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    // For now we disable CSRF because we are building REST API (no browser forms)
                    .csrf(csrf -> csrf.disable())

                    // Authorization rules
                    .authorizeHttpRequests(auth -> auth
                            // Swagger / OpenAPI
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-ui.html",
                                    "/swagger"
                            ).permitAll()

                            // Temporary: allow health/basic endpoints if you add them later
                            .requestMatchers("/actuator/**").permitAll()

                            // Everything else: allow for now (we will secure later with JWT)
                            .anyRequest().permitAll()
                    )

                    // Disable default login form
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(form -> form.disable());

            return http.build();
        }
    }

