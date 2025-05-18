package com.samsoft.views.jdbc;

import com.samsoft.grid.JdbcGrid;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import javax.sql.DataSource;

@PageTitle("JDBC Grid")
@Route("jdbc")
@Menu(order = 4, icon = LineAwesomeIconUrl.ACQUISITIONS_INCORPORATED)
@Uses(Icon.class)
@PermitAll
public class DemoGrid extends VerticalLayout {

    String fqn = "sample_person";

    public DemoGrid(DataSource dataSource) {
        setSizeFull();
        addClassNames(LumoUtility.Padding.Left.NONE);
        JdbcGrid jdbcGrid = new JdbcGrid(dataSource, fqn);
        add(jdbcGrid);
    }
}
