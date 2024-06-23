package com.samsoft;

import com.samsoft.auth.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        String u = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log Out ", e -> securityService.logout());
        var header = new HorizontalLayout(toggle, title, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        addToNavbar(header);
        log.debug("Main layout constructor");
    }

    private static SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Dashboard", "/",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Orders", "/orders", VaadinIcon.CART.create()),
                new SideNavItem("Customers", "/customers",
                        VaadinIcon.USER_HEART.create())
        );
        sideNav.setExpanded(true);
        sideNav.setCollapsible(false);
        return sideNav;
    }
}



