package com.samsoft.auth;

import com.samsoft.auth.view.LoginView;
import com.vaadin.flow.spring.security.VaadinDefaultRequestCache;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/login";

    private final GoogleOIDCService oidcUserService;

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
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
