package com.samsoft.views.jdbc;

import com.samsoft.grid.JdbcDataCallback;
import com.samsoft.grid.JdbcDataProvider;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import javax.sql.DataSource;
import java.util.Map;

@PageTitle("JDBC Grid")
@Route("jdbc")
@Menu(order = 4, icon = LineAwesomeIconUrl.ACQUISITIONS_INCORPORATED)
@Uses(Icon.class)
@PermitAll
public class DemoGrid extends VerticalLayout {

    String fqn = "sample_person";

    public DemoGrid(DataSource dataSource) {
        setSizeFull();
        final Grid<Map<String, Object>> grid = new Grid<>(100);
        JdbcDataCallback jdbcDataCallback = new JdbcDataCallback(grid, dataSource, fqn);
        JdbcDataProvider dataProvider = new JdbcDataProvider(jdbcDataCallback);
        grid.setDataProvider(dataProvider);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        add(new Button("Refresh", e -> grid.getDataProvider().refreshAll()), grid);
    }
}
