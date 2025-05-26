package com.libcode.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))        
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/login", "/css/**", "/js/**", "/img/**", "/webjars/**", "/unauthorized", "/error").permitAll()

            .requestMatchers(HttpMethod.GET, "/dashboard", "/estudiantes", "/grupos", "/asistencias", "/reportes").authenticated()

            .requestMatchers("/api/admin/**", "/api/grupos/**", "/api/estudiantes/**", "/api/asistencias/**", "/api/informes/**").hasRole("administrador")
            .requestMatchers("/api/asistencias/**", "/api/informes/**").hasRole("Maestro")
            .requestMatchers("/api/asistencias/**", "/api/informes/**").hasAnyRole("Alumno", "Familiares")

            .anyRequest().denyAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
            )
        )
        .exceptionHandling(handling -> handling
            .authenticationEntryPoint((request, response, authException) -> {
                String accept = request.getHeader("Accept");
                if (accept != null && accept.contains("text/html")) {
                    response.sendRedirect("/login");
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.sendRedirect("/unauthorized");
            })
        )
        .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("https://" + System.getenv("AUTH0_DOMAIN") + "/");
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
        return jwtConverter;
    }
}
