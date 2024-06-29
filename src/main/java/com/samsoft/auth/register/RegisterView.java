package com.samsoft.auth.register;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new H1("Registration"));
        log.debug("Register View constructor initialised");
    }
}
