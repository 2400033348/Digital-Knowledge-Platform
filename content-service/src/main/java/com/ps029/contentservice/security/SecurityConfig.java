
package com.ps029.contentservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
            )

            .authorizeHttpRequests(auth -> auth

                // USER and ADMIN can READ content
                .requestMatchers(
                    HttpMethod.GET,
                    "/content",
                    "/content/**"
                ).hasAnyRole("USER", "ADMIN")

                // Only ADMIN can CREATE content
                .requestMatchers(
                    HttpMethod.POST,
                    "/content"
                ).hasRole("ADMIN")

                // Only ADMIN can UPDATE content
                .requestMatchers(
                    HttpMethod.PUT,
                    "/content/**"
                ).hasRole("ADMIN")

                // Only ADMIN can DELETE content
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/content/**"
                ).hasRole("ADMIN")

                // Deny everything else
                .anyRequest().denyAll()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
