package com.samsoft.auth.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class LoginLayout extends VerticalLayout implements RouterLayout {
    public LoginLayout() {
        // You can add minimal styling or branding here if needed
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}