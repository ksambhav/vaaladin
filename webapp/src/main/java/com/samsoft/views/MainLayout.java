package com.samsoft.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private final AuthenticationContext authContext;
    public Icon DARK_ICON = new Icon(VaadinIcon.MOON);
    public Icon LIGHT_ICON = new Icon(VaadinIcon.LIGHTBULB);
    public String js = "document.documentElement.setAttribute('theme', $0)";

    private H1 viewTitle;

    public MainLayout(AuthenticationContext authenticationContext) {
        this.authContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("SamSoft");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.XXLARGE, LumoUtility.AlignItems.CENTER);
        Header header = new Header(appName);
        Scroller scroller = new Scroller(createNavigation());
        var themeButton = new Button(DARK_ICON);
        themeButton.setTooltipText("Change Theme");
        themeButton.addClickListener(new ComponentEventListener<>() {
            boolean dark = false;

            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                dark = !dark;
                themeButton.setIcon(dark ? DARK_ICON : LIGHT_ICON);
                getElement().executeJs(js, dark ? Lumo.DARK : Lumo.LIGHT);
            }
        });
        Button logoutButton = new Button(new Icon(VaadinIcon.EXIT), event -> authContext.logout());
        logoutButton.setTooltipText("Exit");
        HorizontalLayout hl = new HorizontalLayout(themeButton, logoutButton);
        hl.setWidthFull();
        hl.setAlignItems(FlexComponent.Alignment.CENTER);
        hl.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        addToDrawer(header, scroller, createFooter(), hl);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });
        return nav;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        return footer;
    }

    /*@Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }*/

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}

