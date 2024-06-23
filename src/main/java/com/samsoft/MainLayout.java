package com.samsoft;

import com.samsoft.auth.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("")
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class MainLayout extends AppLayout {

    private final transient AuthenticationContext authContext;
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService, AuthenticationContext authContext) {
        this.authContext = authContext;
        this.securityService = securityService;
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Vaaladin");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        SideNav nav = getSideNav();
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        addToDrawer(scroller);
        addToNavbar(toggle, title);
       /* if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            header = new HorizontalLayout(logo, logout);
        } else {
            header = new HorizontalLayout(logo);
        }*/
        log.debug("Main layout constructor");

    }

    private static SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Dashboard", "/dashboard",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Orders", "/orders", VaadinIcon.CART.create()),
                new SideNavItem("Customers", "/customers",
                        VaadinIcon.USER_HEART.create()),
                new SideNavItem("Products", "/products",
                        VaadinIcon.PACKAGE.create()),
                new SideNavItem("Documents", "/documents",
                        VaadinIcon.RECORDS.create()),
                new SideNavItem("Tasks", "/tasks", VaadinIcon.LIST.create()),
                new SideNavItem("Analytics", "/analytics",
                        VaadinIcon.CHART.create()));
        sideNav.setExpanded(true);
        return sideNav;
    }
}



