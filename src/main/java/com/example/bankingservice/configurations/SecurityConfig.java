package com.example.bankingservice.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    DaoAuthenticationProvider authenticationProvider;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Customizer<CsrfConfigurer<HttpSecurity>> csrfConfigurerCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                httpSecurityCsrfConfigurer.disable();
            }
        };

        Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingConfigurerCustomizer = new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
            @Override
            public void customize(ExceptionHandlingConfigurer exceptionHandlingConfigurer) {
                exceptionHandlingConfigurer.authenticationEntryPoint((request, response, ex) -> {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"resource.not.found\"}");
                    response.getWriter().flush();
                    ex.printStackTrace();
                });
            }
        };

        Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizationRequestsCustomizer = new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/banking-service/auth/login").permitAll()
                        .requestMatchers("/banking-service/auth/signup/customer").permitAll()
                        .anyRequest().authenticated();
            }
        };

        Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer = new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin("*");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        };

        Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementConfigurerCustomizer = new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
            @Override
            public void customize(SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer) {
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }
        };
        http.cors(corsCustomizer).csrf(csrfConfigurerCustomizer)
                .exceptionHandling(exceptionHandlingConfigurerCustomizer)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationRequestsCustomizer)
                .sessionManagement(sessionManagementConfigurerCustomizer)
                .build();
        http.authenticationProvider(authenticationProvider);
        return http.getOrBuild();
    }


}
