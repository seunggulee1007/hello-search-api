package com.emotionalcart.hellosearchapi.infra.config;

import com.emotionalcart.hellosearchapi.infra.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .sessionManagement(handleSessionPolicy())
            .authorizeHttpRequests(request ->
                                       getAuthorizedUrl(request).permitAll()
                                           .anyRequest().authenticated());
        return http.build();
    }

    private static Customizer<SessionManagementConfigurer<HttpSecurity>> handleSessionPolicy() {
        return session -> session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS);
    }

    protected AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl getAuthorizedUrl(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        return request.requestMatchers(getPermissionUrl());
    }

    protected String[] getPermissionUrl() {
        return new String[] {"/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/actuator/**"};
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowDomains = appProperties.getAllowDomains();
        config.setAllowedOrigins(allowDomains);
        config.setAllowedMethods(List.of("GET", "PUT", "DELETE", "POST", "PATCH", "OPTIONS", "HEAD"));
        config.setExposedHeaders(List.of("Access-Control-Allow-Headers",
                                         "Access-Token",
                                         "Refresh-Token",
                                         "Access-Control-Allow-Origin",
                                         "strict-origin-when-cross-origin"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
