package com.samsoft.order;

import com.samsoft.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = "orders", layout = MainLayout.class)
@PageTitle("Customer")
@PermitAll
class OrderListView extends VerticalLayout {
    public OrderListView() {
        add(new H1("Order List view"));
    }
}
