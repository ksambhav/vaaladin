package com.samsoft.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private static final String OAUTH_URL = "/oauth2/authorization/google";
    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        login.setForgotPasswordButtonVisible(true);
        add(login);
        Anchor loginLink = new Anchor(OAUTH_URL, "Login with Google");
        loginLink.setRouterIgnore(true);
        add(loginLink);
        Button register = new Button("Register");
        register.addClickListener(e ->
                register.getUI().ifPresent(ui ->
                        ui.navigate("registration"))
        );
        add(register);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
