package com.samsoft.customer;

import com.samsoft.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = "customers", layout = MainView.class)
@PageTitle("Customer")
@PermitAll
class CustomerListView extends VerticalLayout {

    public CustomerListView() {
        add(new H1("Customer Listing Page"));
        log.debug("Customer listing page");
    }
}
