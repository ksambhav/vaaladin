package com.samsoft.auth;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
public class AuthConfig extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/images/*.png")).permitAll();
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/actuator/**")).permitAll();
            auth.requestMatchers("/h2-console/**").permitAll();
            auth.requestMatchers("/registration").permitAll();
        });
        setLoginView(http, LoginView.class);
        http.oauth2Login(c -> c.loginPage(LOGIN_URL).permitAll());
        super.configure(http);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
