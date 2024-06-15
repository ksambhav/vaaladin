package com.samsoft;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@Slf4j
@SpringBootApplication
@PWA(name = "Vaaladin", shortName = "Vaaladin")
@Theme("my-theme")
public class VaaladinApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaaladinApplication.class, args);
    }
}
