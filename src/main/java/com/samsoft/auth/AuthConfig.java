package com.samsoft.auth;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfig extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/images/*.png")).permitAll();
            auth.requestMatchers(antMatcher(HttpMethod.GET, "/actuator/**")).permitAll();
        });
        http.oauth2Login(c -> c.loginPage(LOGIN_URL).permitAll());
        super.configure(http);
    }

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());  // <3>
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.formLogin(c -> c.defaultSuccessUrl("/", true));
        http.oauth2Login(c -> {
            c.loginPage(LOGIN_URL).permitAll();
        });
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); // <5>
    }*/
}
