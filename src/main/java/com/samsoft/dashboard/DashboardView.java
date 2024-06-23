package com.samsoft.dashboard;

import com.samsoft.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = "/", layout = MainLayout.class)
@PageTitle("Customer")
@PermitAll
class DashboardView extends VerticalLayout {
    public DashboardView() {
        add(new H1("Dashboard"));
    }
}
