package com.samsoft.auth.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PageTitle("Login")
@Route(value = "login", layout = LoginLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements HasUrlParameter<String> {

    private final String GOOGLE_OAUTH_URL = "/oauth2/authorization/google";

    private LoginForm loginForm;

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        var loginArea = loginArea();
        add(loginArea);
    }

    private VerticalLayout loginArea() {
        var vl = new VerticalLayout();
        vl.addClassNames(LumoUtility.JustifyContent.CENTER, LumoUtility.AlignItems.CENTER);
        loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");
        Button registerButton = new Button("Register", e -> getUI().ifPresent(ui -> ui.navigate("register")));
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        Anchor google = new Anchor(GOOGLE_OAUTH_URL, "Google");
        google.setRouterIgnore(true);
        FlexLayout flexLayout = new FlexLayout(registerButton, google);
        flexLayout.setWidth(15, Unit.PERCENTAGE);
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        flexLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        flexLayout.addClassNames(LumoUtility.AlignItems.BASELINE);
        vl.add(loginForm, flexLayout);
        vl.setSpacing(false);
        return vl;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        QueryParameters queryParameters = event.getLocation().getQueryParameters();
        if (queryParameters.getParameters().containsKey("error")) {
            loginForm.setError(true);
            loginForm.showErrorMessage("Invalid username or password", "Please try again.");
        }
    }
}
