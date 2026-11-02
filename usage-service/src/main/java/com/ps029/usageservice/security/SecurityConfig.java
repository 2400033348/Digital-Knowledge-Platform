
package com.ps029.usageservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
            )

            .authorizeHttpRequests(auth -> auth

                // USER and ADMIN can track usage
                .requestMatchers(
                    HttpMethod.POST,
                    "/usage/track"
                ).hasAnyRole("USER", "ADMIN")

                // USER and ADMIN can check the service instance
                .requestMatchers(
                    HttpMethod.GET,
                    "/usage/instance"
                ).hasAnyRole("USER", "ADMIN")

                // Only ADMIN can view usage history
                .requestMatchers(
                    HttpMethod.GET,
                    "/usage/history",
                    "/usage/history/**"
                ).hasRole("ADMIN")

                // Only ADMIN can update usage history
                .requestMatchers(
                    HttpMethod.PUT,
                    "/usage/history/**"
                ).hasRole("ADMIN")

                // Only ADMIN can delete usage history
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/usage/history/**"
                ).hasRole("ADMIN")

                // Reject all other requests
                .anyRequest().denyAll()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
