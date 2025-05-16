package com.samsoft;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Theme(value = "my-app", variant = Lumo.LIGHT)
@EnableJpaAuditing
@Push
public class VaadinKickStartApp implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinKickStartApp.class, args);
    }

}
