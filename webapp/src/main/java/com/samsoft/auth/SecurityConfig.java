package com.samsoft.auth;

import com.samsoft.auth.view.LoginView;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String LOGIN_URL = "/login";

    private final GoogleOIDCService oidcUserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // add oidcUserService to user info endpoint
        http.oauth2Login(c -> c.userInfoEndpoint(a -> a.oidcUserService(oidcUserService)));
        return http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.enableRequestCacheConfiguration(true);
            configurer.loginView(LoginView.class);
            configurer.oauth2LoginPage(LOGIN_URL);
        }).build();
    }
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var requestCache = new VaadinDefaultRequestCache();
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/images/*.png")).permitAll();
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/actuator/**")).permitAll();
            auth.requestMatchers("/h2-console/**").permitAll();
            auth.requestMatchers("/register").permitAll();
            auth.requestMatchers("/login").permitAll();
        });
        http.requestCache(cache -> cache.requestCache(requestCache));
        setLoginView(http, LoginView.class, LOGIN_URL);
        http.oauth2Login(c -> {
            c.loginPage(LOGIN_URL).permitAll();
            c.userInfoEndpoint(a -> a.oidcUserService(oidcUserService));
        });
        super.configure(http);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
